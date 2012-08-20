/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman;

import java.io.*;
import java.util.Calendar;
import java.util.Comparator;
//import java.util.HashMap;
import java.util.PriorityQueue;
import tiralab.huffman.rakenne.HashMap;
//import java.util.*;

/**
 * 
 * Pakkaaja, joka perustuu Huffman koodiin
 * 
 * @author mtkortel
 */
public class Pakkaaja {
    ///TODO: Nyt tallentaa kaikki merkit omina tavuina, kun pitäisi saada bittijonoksi
    static Comparator<Node> comparator = new NodeComparator();
    //static int MerkkienMäärä = 256;
    public static int MerkkienMäärä = 65536;
    final int buffer_size = 1000*1024;
    HashMap<String, Node> nodes;
    String tiedosto = "";
    int[] kerrat = new int[MerkkienMäärä];
    int virheet = 0;
    //List<String>   merkistö;
    //List<String> koodisto;
    //List<String> koodit;
    CharList merkistö;
    IntList koodisto;
    StringList koodit;
    
    //List<Byte> code;
    
    
    String temppi="";
    
    //private BitSet merkit;
    String löydettyMerkki = "";
    
    public Pakkaaja(String tiedosto){
        nodes = new HashMap<String, Node>();
        //merkit = new BitSet(MerkkienMäärä);
        
        //merkistö = new ArrayList<String>();
        //koodisto = new ArrayList<String>();
        //koodit = new ArrayList<String>();
        merkistö = new CharList();
        koodisto = new IntList();
        koodit = new StringList();
        
        this.tiedosto = tiedosto;
        pakataan(tiedosto);
        kirjoita(tiedosto);
    }
    
    
    /**
     * Metodi lukee tiedoston riveittäin
     * 
     * @param tiedosto 
     * @return tiedoston sisältä teksti merkkijonona
     * @throws FileNotFoundException
     */
    private String lueTekstiTiedosto(String tiedosto) throws IOException {
        File file = new File(tiedosto);
        byte[] array = FileUtils.getBytesFromFile(file);
        StringBuilder sb = new StringBuilder();
        for (byte b: array){
            int num = b;
            if (num >= 0 && num < MerkkienMäärä){
                sb.append((char)b); 
                kerrat[num]++;
                //System.out.print((char)b);
            } else {
                //System.out.println("Virhe: " + num);
                virheet++;
            }
        }
        return sb.toString();
        //File file = new File(tiedosto);
        //FileInputStream fs = new FileInputStream(tiedosto);
        //DataInputStream ds = new DataInputStream(new BufferedInputStream(fs));
        /*
        File f = new File(tiedosto);
        double kertoja = f.length()/buffer_size;
        
        FileReader file = new FileReader(new File(tiedosto));
        BufferedReader ds = new BufferedReader(file);
        System.out.println("Hakemisto: " + file.getCanonicalPath());
        byte[] buf = new byte[2048];
        char[] cbuf = new char[buffer_size];
        char chr = 0;
        int c = -1;
        int n=0;
        String boolString="";
        
        try{
            int laskuri = 0;
            Calendar c1 = Calendar.getInstance();
            while((n = ds.read(cbuf)) != -1){
                Calendar c2 = Calendar.getInstance();
                laskuri++;
                System.out.println(laskuri+"/"+kertoja + " - kesto " +
                        (c2.getTimeInMillis() - c1.getTimeInMillis()) + " ms.");
                c1 = c2;
                //while((c = fs.read()) != -1){
                //System.out.println((char)n);
                //boolString += String.valueOf((char)n);
                int luku = 0;
                for(int i=0; i < cbuf.length; i++){
                    if (cbuf[i]!=0){
                        c = cbuf[i];
                        if (c <= 255){
                            luku++;
                            //if (laskuri >= 2047) {

                            //}
                            //intList.add(c);
                            chr = (char)c;
                            //System.out.println(chr);
                            //boolString = getBitArray(c);
                            //System.out.print(c);
                            //Huffman.pakattu += c + " ";
                            //System.out.print(chr + " " + c + " ");
                            kerrat[c]++;
                            boolString += String.valueOf(chr);
                            if (luku%100 == 0){
                                System.out.println("Valmis " + luku + " riviä");
                            }
                        }
                    }
                }
            }
        
        } catch (Exception e){
            System.out.println(e.getMessage());
            //fs.close();
        }
         
        //System.out.println(boolString);
        return boolString;
        */
    }
    /**
     * Tekee tekstistä taulukon merkkien ilmenemismäärien mukaan
     * 
     * @param teksti
     * @return 
     */
    private int[] teeKertaTaulukko(String teksti){
        int[]  kerrat = new int[MerkkienMäärä];
        
        for(int i = 0; i < teksti.length(); i++){
            kerrat[teksti.toCharArray()[i]]++; // lisätään esiintymiskerta merkille
            
        }
        /*
        for(int i=0; i< kerrat.length; i++){
            if (kerrat[i]>0){
                
            }
        }
        */ 
        return kerrat;
    }
    /**
     * Rakentaa PriorityQueue jonon esiintymismäärien mukaan
     * 
     * @param kerrat    Taulukko merkkien siintymiskerroista
     * @return Node     Palautaa Huffman puun
     */
    public static Node rakennaPuu(int[] kerrat) {
        PriorityQueue<Node> queue = new PriorityQueue<Node>(1, comparator); 
        // Lisätään jonoon kaikki puun lehdet
        for (int i = 0; i < MerkkienMäärä; i++){
            if (kerrat[i] > 0){
                queue.add(new Node((char)i, kerrat[i]));
                //System.out.println("Priority Queue size: " + queue.size());
                //System.out.println((char)i + " " + kerrat[i]);
            }
        }
        // Tehdään niin kauan kunnes jonossa on vain yksi jäljellä eli root
        while (queue.size() > 1){
            Node vasen = queue.remove(); // Pienin
            Node oikea = queue.remove(); // Pienin (ed. toiseksi pienin)
            Node uusi = new Node(vasen, oikea);
            queue.add(uusi); // Lisätään uusi node jonoon
        }
        // Palautta juuren eli ensimmäisen Noden
        //return queue.remove();
        return queue.poll();
    }

 
    /**
     * Tiedoston pakkaamisesta vastaava metodi
     * @param tiedosto 
     */
    private void pakataan(String tiedosto) {
        try{
            String teksti = lueTekstiTiedosto(tiedosto);
            System.out.println("Tämä koodataan: ");
            System.out.println(teksti);
            // Tiedosto luettu loppuun rakennetaan puu
            Node huffman  = rakennaPuu(kerrat);
            päivitäHuffmanPuu(huffman, "0");
            rakennaContent(teksti, huffman);
                        
            
            //rakennaHeader(huffman, "0");
            
        } catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
  
    
    /**
     * Päivitää Huffmanpuun bittiesityksen ja talleettaa sen nodeen.
     * @param huffman 
     * @param merkki
     */
    private void päivitäHuffmanPuu(Node huffman, String merkki) {
        if (huffman.isLehti()){
            huffman.setBits(merkki);
            nodes.put(String.valueOf(huffman.getMerkki()), huffman);
            merkistö.add(huffman.getMerkki());
            koodisto.add(huffman.getMäärä());
            return;
        } 
        päivitäHuffmanPuu(huffman.getVasen(), merkki + "0");
        päivitäHuffmanPuu(huffman.getOikea(), merkki + "1");
    }
    /**
     * Käydään alkuperäinen teksti merkki merkiltä läpi ja muutetaan kirjain huffman koodiksi
     * 
     * @param alkuperäinen
     * @param huffmanTree 
     */
    private void rakennaContent(String alkuperäinen, Node huffman){
        for (char c: alkuperäinen.toCharArray()){
            löydettyMerkki="";
            etsiMerkki(c, huffman);
            if (!löydettyMerkki.equals("")){
                koodit.add(löydettyMerkki);  
            } 
        }
    }
    private void etsiMerkki(char c, Node huffman){
        if (huffman != null){
            if (huffman.isLehti()){
                char h = huffman.getMerkki();
                if (c == huffman.getMerkki())
                    löydettyMerkki=huffman.getBits();
                return;
                
            } 
            etsiMerkki(c,huffman.getVasen());
            etsiMerkki(c,huffman.getOikea());
        }
    }
    
    /**
     * Rakentaa pakatun tiedoston header osion
     * Ensimmäinen tavu on merkki ja kaksi seuraavaa tavua on huffman-koodattu esitys
     * @param huffman 
     * @param merkki
     */
    
    private void rakennaHeader(Node huffman, String merkki) {
        if (huffman.isLehti()){
            merkistö.add(huffman.getMerkki());
            koodisto.add(huffman.getMäärä());
            return;
        } 
        rakennaHeader(huffman.getVasen(), merkki + "0");
        rakennaHeader(huffman.getOikea(), merkki + "1");
    }
        
    /**
     * Tulostaa pakatun tiedoston ja näyttää ko'ot
     * 
     * Käydään läpi koko lista josta löytyy kaikki tarvittava tieto
     * Ensin on header osio, jossa ensin on merkki (char) ja sen jälkeen
     * sitä vastaava huffman koodi. header ja content osiot on eroteltu
     * kolmella Byte.MAX_VALUE 11111111 (int 255) kentällä.
     * Merkki on yhden tavun ja koodi on 2 tavua, joiden pitäisi riittää
     * headerin osuuteen.
     * Pakattu teksti on tallennettu 2 tavun paloissa huffman koodattuna.
     * 
     * @param tiedosto 
     */
    private void kirjoita(String tiedosto) {
        int pos = tiedosto.indexOf(".");
        if (pos < 0){
            // ei pistettä
            pos = tiedosto.length();
        }
        String utied = tiedosto.substring(0, pos) + ".huf";
        try{
            //File file = new File(utied);
            FileOutputStream fs = new FileOutputStream(utied);
            /*
             * Käydään läpi koko lista josta löytyy kaikki tarvittava tieto
             * Ensin on header osio, jossa ensin on merkki (char) ja sen jälkeen
             * sitä vastaava huffman koodi. header ja content osiot on eroteltu
             * kahdella Byte.MAX_VALUE 11111111 kentällä.
             * Merkki on kolme bittiä ja koodi on 16 bittiä, joiden pitäisi riittää
             * headerin osuuteen.
             * Headeriin koodi tallennetaan 16 bittisenä jossa jonon alussa on 
             * 1 merkkillä siihen saakka kunnes alkaa oikea koodi. Erotinmerkki
             * on 0 esim. oikea koodi 1110101 koodaus 1111111101110101
             */
            String header="";
            for (int i=0; i < merkistö.size(); i++){
                String mbin = Integer.toBinaryString(merkistö.get(i));
                String kbin ;//= koodisto.get(i);
                Node tmpNode = nodes.get(String.valueOf(merkistö.get(i)));
                kbin = tmpNode.getBits();
                String kbin1="";
                String kbin2="";
                String kbin3="";
                
                if (mbin.length() >= 8){
                    System.out.println("Koko ongelma: " + merkistö.get(i) + " " + mbin.length());
                }
                
                while (mbin.length() < 8){
                    mbin = "0" + mbin;
                } 
                
                boolean eka=true;
                while (kbin.length() < 24){
                    if (eka){
                        kbin="0" + kbin;
                        eka=false;
                    } else {
                        kbin = "1" + kbin;
                    }
                }
                
                kbin1 = kbin.substring(0, 8);
                kbin2 = kbin.substring(8, 16);
                kbin3 = kbin.substring(16);
                
                //String tmp = mbin + kbin;
                int me = strToInt(mbin); // Merkki
                header+=Purkaja.getBitArray(me);
                fs.write(me);
                me = strToInt(kbin1); // Ensimmäiset 8 bittiä
                header+=Purkaja.getBitArray(me);
                fs.write(me);
                me = strToInt(kbin2); // Toiset 8 bittiä
                header+=Purkaja.getBitArray(me);
                fs.write(me);
                me = strToInt(kbin3); // Kolmannet 6 bittiä
                header+=Purkaja.getBitArray(me);
                fs.write(me);
                
                //System.out.println(kbin + " " + merkistö.get(i));
            }
            fs.write(255);
            fs.write(255);
            fs.write(255);
            fs.write(255);
            
            String tmp="";
            for (int i=0; i < koodit.size(); i++){
                String kbin = koodit.get(i);
                for(int j = 0; j < kbin.length(); j++){
                    tmp += kbin.charAt(j);
                }
            }
            int nro = 0;
            int kerrat=0;
            boolean[] bits = new boolean[8];
            int mk = 0;
            while (mk < tmp.length() ){
                int tmk = mk+8;
                if (tmk > tmp.length()){
                    tmk = mk + (tmp.length()-mk);
                }
                String tmp1 = tmp.substring(mk, tmk);
                while (tmp1.length() < 8){
                    tmp1 = "0"+tmp1;
                }
                mk+=8;
                int data = 0;
		for (int i = 0; i < tmp1.length(); i++) {
                if (tmp1.charAt(i) == '1') {
                    data += (1 << (7-i));
                }
		}
                Huffman.pakattu += data + " ";
                //os.write(me);
                fs.write(data);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    private int strToInt(String str){
        boolean[] bstr = new boolean[8];
        for(int j = 0; j < str.length(); j++){
                    if (str.charAt(j) == '1'){
                        //bs.set(j, true);
                        bstr[j] = true;
                    } else 
                        //bs.set(j, false);
                        bstr[j] = false;
                }
                //boolean[] he1 = new boolean[8]; 
                return Huffman.bitsToByte(bstr);
    }
}