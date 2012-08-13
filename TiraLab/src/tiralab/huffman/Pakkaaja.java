/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
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
    static int MerkkienMäärä = 256;
    HashMap<String, Node> nodes;
    String tiedosto = "";
    
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
    private String lueTekstiTiedosto(String tiedosto) throws FileNotFoundException, IOException {
        //File file = new File(tiedosto);
        FileInputStream fs = new FileInputStream(tiedosto);
        //System.out.println("Hakemisto: " + file.getCanonicalPath());
        byte[] buf = new byte[2048];
        char chr = 0;
        int c = -1;
        int n=0;
        String boolString="";
        
        try{
            int laskuri = 0;
            while((n = fs.read(buf)) != -1){
            //while((c = fs.read()) != -1){
            for(int i=0; i < n; i++){
                c = buf[i];
                //if (laskuri >= 2047) {
                        //System.out.println("jotain");
                //}
                //intList.add(c);
                chr = (char)c;
                //boolString = getBitArray(c);
                //System.out.print(c);
                Huffman.pakattu += c + " ";
                laskuri++;
                boolString += String.valueOf(chr);
            }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            fs.close();
        }
        //System.out.println(boolString);
        return boolString;
        
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
        for(int i=0; i< kerrat.length; i++){
            if (kerrat[i]>0){
                
            }
        }
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
        for (char i = 0; i < MerkkienMäärä; i++)
            if (kerrat[i] > 0){
                queue.add(new Node(i, kerrat[i]));
                //System.out.println((char)i + " " + kerrat[i]);
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
            int[] kerrat  = teeKertaTaulukko(teksti);
            // Tiedosto luettu loppuun rakennetaan puu
            Node huffman  = rakennaPuu(kerrat);
            päivitäHuffmanPuu(huffman, "0");
            //System.out.println("");
            //List<Byte> koodia = new ArrayList<Byte>();
            //koodia = tulostaTiedosto(teksti, huffman, koodia);
            rakennaContent(teksti, huffman);
            //System.out.println("Header osio");
            //code = new ArrayList<Byte>();
            
            //rakennaHeader(huffman, "0");
            /*
            System.out.println("Tällainen merkit ja koodit");
            for(Byte b: code){
                System.out.print(b);
            }
            */
            //code.add(Byte.MAX_VALUE);
            //code.add(Byte.MAX_VALUE);
            /*
            System.out.println();
            System.out.println("Tällainen välimerkki");
            for(Byte b: code){
                System.out.print(b);
            }
            * 
            */
            //code.addAll(koodia);
            //System.out.println();
            //System.out.println("Tällainen lopullinen");
            //for(Byte b: code){
              //  System.out.print(b);
            //}
            
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
            //System.out.println(huffman.getMerkki()+ " - " + merkki + " - " + huffman.getMäärä());
            huffman.setBits(merkki);
            nodes.put(String.valueOf(huffman.getMerkki()), huffman);
            merkistö.add(huffman.getMerkki());
            //koodisto.add(merkki);
            //koodisto.add(String.valueOf(huffman.getMäärä()));
            
            koodisto.add(huffman.getMäärä());
            //System.out.println(huffman.getMerkki() + " " + merkki);
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
        //String jono="";
        
        for (char c: alkuperäinen.toCharArray()){
            löydettyMerkki="";
            etsiMerkki(c, huffman);
            if (!löydettyMerkki.equals("")){
                koodit.add(löydettyMerkki);
                //System.out.println(c + " " + löydettyMerkki);
                
            } 
            //Node n = nodes.get(String.valueOf(c));
            //jono += n.getBits() + " ";
            //koodit.add(n.getBits());
            //text.add((byte) Integer.parseInt(n.getBits(), 2));
        }
        /*
        System.out.println("Alkuperäinen teksti");
        System.out.println(alkuperäinen);
        System.out.println("Koko: " + alkuperäinen.length());
        System.out.println("Koodattu teksti");
        */
        //System.out.println(jono.replaceAll(" ", ""));
        
        //System.out.println("Koko: " + jono.length());
        
        
        //temppi = jono.replaceAll("" , "");
        //kirjoitaPuu(huffman);
        //return text;
    }
    private void etsiMerkki(char c, Node huffman){
        if (huffman != null)
            if (huffman.isLehti()){
                //System.out.println(huffman.getMerkki()+ " - " + merkki + " - " + huffman.getMäärä());
                char h = huffman.getMerkki();
                if (c == huffman.getMerkki())
                    löydettyMerkki=huffman.getBits();
                return;
                
            } 
            etsiMerkki(c,huffman.getVasen());
            etsiMerkki(c,huffman.getOikea());
    }
    
    /*
    private String etsiMerkki(char merkki, Node puu, String koodi){
        try{
        if (puu.isLehti()){
            char tämä = puu.getMerkki();
            if (puu.getMerkki() == merkki){
                return koodi;
            }
        } else {
            etsiMerkki(merkki, puu.getVasen(), koodi+"0");
            etsiMerkki(merkki, puu.getVasen(), koodi+"1");
        }
        } catch (Exception e){
            System.out.println(e.getMessage());
            
        }
        return "";
    }*/
/*
    private Node haeNodet(Node huffman , String merkki) {
        if (huffman.isLehti()){
            System.out.println(huffman.getMerkki()+ " - " + merkki + " - " + huffman.getMäärä());
            huffman.setBits(merkki);
            return huffman;
        }
        haeNodet(huffman.getVasen(), merkki + "0");
        haeNodet(huffman.getOikea(), merkki + "1"); 
        return null;
    }
*/
    /**
     * Rakentaa pakatun tiedoston header osion
     * Ensimmäinen tavu on merkki ja kaksi seuraavaa tavua on huffman-koodattu esitys
     * @param huffman 
     * @param merkki
     */
    
    private void rakennaHeader(Node huffman, String merkki) {
        if (huffman.isLehti()){
            // Ensin lisätään merkki (esim. a)
            //byte merkki_byte = (byte) (int)huffman.getMerkki();
            // Sitten lisätään huffman puun polku (00001)
            //byte koodi_byte  = (byte) Integer.parseInt(merkki, 2); // 010101
            //BitSet bs_merkki = new BitSet(8);
            //BitSet bs_koodi = new BitSet(8);
            //System.out.print(merkki_byte );
            //System.out.print(" " + koodi_byte + " " );
            merkistö.add(huffman.getMerkki());
            
            koodisto.add(huffman.getMäärä());
            //koodisto.add(merkki);
            
            
            //code.add(merkki_byte);
            //code.add(koodi_byte);
            return;
        } else {
            //setti.set(0, true);
            //System.out.print(setti);
        }
        rakennaHeader(huffman.getVasen(), merkki + "0");
        rakennaHeader(huffman.getOikea(), merkki + "1");
    }
    /*
    private void kirjoitaPuu(Node puu){
        int pos = tiedosto.indexOf(".");
        if (pos < 0){
            // ei pistettä
            pos = tiedosto.length();
        }
        String utied = tiedosto.substring(0, pos) + ".huf";
        //System.out.println("Tiedosto:  " + tiedosto);
        //System.out.println("Kirjoitus: " + utied);
        
        System.out.println("Käytetyt merkit bitteinä: ");
        System.out.println(merkit);
    }
    */
    
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
            File file = new File(utied);
            FileOutputStream fs = new FileOutputStream(utied);
            //ObjectOutputStream os = new ObjectOutputStream(fs);
            Object buffer = null;
            //byte[] merkki = new byte[3];
            //byte[] koodi = new byte[16];
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
            //boolean isHeader = true; // header vai content
            //boolean isMerkki = true; // merkki vai koodi
            //int max_value_count = 0;
            //System.out.println("Code pituus: " + code.size());
            //int num = 0;
            //System.out.println("Merkistö koko: " + merkistö.size());
            //System.out.println("Koodisto koko: " + koodisto.size());
            //System.out.println("Koodit   koko: " + koodit.size());
            String header="";
            for (int i=0; i < merkistö.size(); i++){
                String mbin = Integer.toBinaryString(merkistö.get(i));
                //int mint = Integer.parseInt(merkistö.get(i));
                String kbin ;//= koodisto.get(i);
                Node tmpNode = nodes.get(String.valueOf(merkistö.get(i)));
                //kbin = String.valueOf(tmpNode.getMäärä());
                //kbin = Integer.toBinaryString(tmpNode.getMäärä());
                kbin = tmpNode.getBits();
                String kbin1="";
                String kbin2="";
                //System.out.println(merkistö.get(i) + " " + tmpNode.getBits());
                boolean[] mbit = new boolean[8];
                boolean[] kbit1 = new boolean[8];
                boolean[] kbit2 = new boolean[8];
                
                while (mbin.length() < 8){
                    mbin = "0" + mbin;
                }
                boolean eka=true;
                while (kbin.length() < 16){
                    if (eka){
                        kbin="0" + kbin;
                        eka=false;
                    } else 
                //while (kbin.length() < 8){
                    kbin = "1" + kbin;
                }
                
                kbin1 = kbin.substring(0, 8);
                kbin2 = kbin.substring(8);
                
                String tmp = mbin + kbin;
                
                
                // Merkki
                for(int j = 0; j < mbin.length(); j++){
                    if (mbin.charAt(j) == '1'){
                        //bs.set(j, true);
                        mbit[j] = true;
                    } else 
                        //bs.set(j, false);
                        mbit[j] = false;
                }
                boolean[] he1 = new boolean[8]; 
                int me = Huffman.bitsToByte(mbit);
                header+=Purkaja.getBitArray(me);
                //os.write(me);
                fs.write(me);
                //System.out.print(me + " ");
                Huffman.pakattu += me + " ";
                //System.out.println((char)me + " " + me + " " + kbin);
                 // Merkki
                for(int j = 0; j < kbin1.length(); j++){
                    if (kbin1.charAt(j) == '1'){
                        //bs.set(j, true);
                        kbit1[j] = true;
                    } else 
                        //bs.set(j, false);
                        kbit1[j] = false;
                }
                he1 = new boolean[8]; 
                
                me = Huffman.bitsToByte(kbit1);
                header+=Purkaja.getBitArray(me);
                fs.write(me);
                //me = tmpNode.getMäärä();
                //fs.write(me);
                //String s = Integer.toBinaryString(tmpNode.getMäärä());
                
                //System.out.println(me + " ");
                //System.out.print(me + " ");
                Huffman.pakattu += me + " ";
                 // Merkki
                
                for(int j = 0; j < kbin2.length(); j++){
                    if (kbin2.charAt(j) == '1'){
                        //bs.set(j, true);
                        kbit2[j] = true;
                    } else 
                        //bs.set(j, false);
                        kbit2[j] = false;
                }
                he1 = new boolean[8]; 
                me = Huffman.bitsToByte(kbit2);
                header+=Purkaja.getBitArray(me);
                //System.out.print(me + " ");
                Huffman.pakattu += me + " ";

                //os.write(me);
                fs.write(me);
                
                /*
                for (int h = 0; h < 24; h++){
                    if (h<8){
                        he1[h] = mbit[h];
                    } else if (h < 16){
                      if (h==8){
                          int ii = Huffman.bitsToByte(he1);
                          os.write(ii);
                          he1 = new boolean[8];
                      }
                      he1[h-8] = mbit[h];
                    } else{
                      if (h==16){
                          int ii = Huffman.bitsToByte(he1);
                          os.write(ii);
                          he1 = new boolean[8];  
                      }
                      he1[h-16] = mbit[h];
                    }
                }
                int ii = Huffman.bitsToByte(he1);
                os.write(ii);
                */
                //byte[] arr = toByteArray(bs);
                //System.out.println(mbin + " " + merkistö.get(i) + " " + kbin + " " + koodisto.get(i).length() + " " + arr.length);
                //for (int l =0; l < arr.length; l++){
                    //System.out.print(arr[l]);
                //}
                //System.out.println();
                //os.write(arr);
                //os.writeObject(bs);
            }
            /*
            BitSet mm = new BitSet(24);
            for(int i=0; i<mm.size(); i++){
                mm.set(i, true);
            }
            byte[] aa = toByteArray(mm);
            //System.out.println("Erotin koko: " + aa.length);
            os.write(aa);
            */
            //os.write(me);
            fs.write(-1);
            fs.write(-1);
            //fs.write(-1);
            //os.write(-1); // Erotin 1
            //os.write(-1); // Erotin 2
            //os.write(-1); // Erotin 3
            Huffman.pakattu += 255 + " ";
            Huffman.pakattu += 255 + " ";
            //Huffman.pakattu += 255 + " ";
            //System.out.println("Header: " + header);
            //Huffman.pakattu = header;
            String tmp="";
            for (int i=0; i < koodit.size(); i++){
                String kbin = koodit.get(i);
                //System.out.println(kbin + " " + kbin.length());
                //BitSet bs = new BitSet(kbin.length());
                for(int j = 0; j < kbin.length(); j++){
                    tmp += kbin.charAt(j);
                    /*
                    int mod = kbin.length() - 16;
                    if ((mod+j) < 0){
                        tmp += "0";
                    } else 
                        tmp += kbin.charAt(mod+j);
                        * 
                        */
                }
            }
            //System.out.println(tmp);
            //System.out.print("Sisältö: " );
            //BitSet bs = new BitSet(8);
            int nro = 0;
            int kerrat=0;
            boolean[] bits = new boolean[8];
            int mk = 0;
            while (mk < tmp.length() ){
                int tmk = mk+8;
                if (tmk > tmp.length())
                    tmk = mk + (tmp.length()-mk);
                String tmp1 = tmp.substring(mk, tmk);
                while (tmp1.length() < 8){
                    tmp1 = "0"+tmp1;
                }
                mk+=8;
                int data = 0;
		for (int i = 0; i < tmp1.length(); i++) {
			if (tmp1.charAt(i) == '1') 
                            data += (1 << (7-i));
		}
                //System.out.println(tmp1 + " " + data + " " + mk + " " + tmp.length());
                //System.out.print(Purkaja.getBitArray(data));
                //Huffman.pakattu += Purkaja.getBitArray(data);
                Huffman.pakattu += data + " ";
                //os.write(me);
                fs.write(data);
            }
            /*
            for (int i=0; i < tmp.length(); i++){
                
                if (tmp.charAt(i) == '1'){
                    //bs.set(i, true);
                    bits[i-(8*kerrat)] = true;
                } else 
                    bits[i-(8*kerrat)] = false;
                    //bs.set(i, false);
                
                nro++;
                if (nro==8){
                    //byte[] arr = toByteArray(bs);
                    //os.write(arr);
                    System.out.println(tmp.substring(i-7, i+1));
                    for(int k=0; k < 8; k++){
                        if (bits[k]){
                            System.out.print("1");
                        } else 
                            System.out.print("0");
                    }
                    System.err.println(" bitit");
                    int kirjoita = Huffman.bitsToByte(bits);
                    os.write(kirjoita);
                    bits = new boolean[8];
                    kerrat++;
                    nro=0;
                    //System.out.println(kirjoita);
                    
                    //System.out.print(Integer.toBinaryString(kirjoita));
                    //if (kerrat%2==0)
                        //System.out.println();
                        
                    //bs = new BitSet(8);
                }
            }
            */
            //int kirjoita = Huffman.bitsToByte(bits);
            //os.write(kirjoita);
            //System.out.println(kirjoita);
            //byte[] arr = toByteArray(bs);
            //os.write(arr);
            //os.writeObject(bs);
            
            //BitSet bitit = new BitSet(8);
            /*
            boolean vielä = true;
            temppi = temppi.replaceAll(" ", "");
            String tmp1 = "1" + temppi;
            String tmp2 = "";
            System.out.println();
            System.out.println("Koodi");
            System.out.println("temppi: " + temppi);
            
            //os.write(temppi.getBytes());
            
            */
            //while(vielä){
                /*
                if (tmp1.length()>=8){
                    tmp2 = tmp1.substring(0, 7);
                    tmp1 = tmp1.substring(8);
                } else {
                    tmp2 = tmp1;
                    vielä=false;
                } 
                
                for(int i = 0; i < tmp2.length(); i++){
                    if (tmp2.charAt(i) == '1'){
                        bitit.set(i, true);
                    } else 
                        bitit.set(i, false);
                }
                */
                //System.out.println(bitit);
                //byte[] arr = toByteArray(bitit);
            /*
                for(byte b: arr){
                    System.out.print(Integer.toBinaryString(Integer.decode(Byte.toString(b)))+ " ");
                }
                os.write(arr);
                
            }
            */
            /*
            System.out.println();
            long pakattu = file.length();
            
            file = new File(tiedosto);
            long alkup = file.length();
            double ero = 1.0*pakattu/alkup*100;
            DecimalFormat df = new DecimalFormat("##.##");
            System.out.println("Pakkaamattoman tiedoston koko: " + alkup);
            System.out.println("Pakatun tiedoston koko       : " + pakattu);
            System.out.println("Koko alkuperäisestä          : " + df.format(ero) +"%");
            * 
            */
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Muuttaa BitSetin bittitaulukoksi
     * 
     * @param bits
     * @return 
     */
    /*
    public static byte[] toByteArray(BitSet bits) {
        byte[] bytes = new byte[bits.length()/8+1];
        for (int i=0; i<bits.length(); i++) {
            if (bits.get(i)) {
                bytes[bytes.length-i/8-1] |= 1<<(i%8);
            }
        }
        return bytes;
    }
    */
    
}