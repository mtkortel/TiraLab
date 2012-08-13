/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman;

import java.io.*;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;

/**
 *
 * @author mkortelainen
 */
public class Purkaja {
    Comparator<Node> comparator = new NodeComparator();
    private String tiedosto="";
    private List<Node2> nodes;
    PriorityQueue<Node> queue = new PriorityQueue<Node>(1, comparator); 
    String koodattuTeksti="";
    private Node puu = new Node();
    
    Node huffmanTree = null;
    
    
    public Purkaja(String tiedosto){
        nodes = new ArrayList<Node2>();
        this.tiedosto = tiedosto;
        String str = puretaan(tiedosto);
    }

    /**
     * Metodi lukee tiedoston tavuina ja palauttaa listan niistä
     * 
     * @param tiedosto
     * @throws FileNotFoundException 
     */
    private List<Integer> luePurettavaTiedosto(String tiedosto) throws FileNotFoundException{
        List<Integer> intList = new ArrayList<Integer>();
        int pos = tiedosto.indexOf(".");
        if (pos < 0){
            // ei pistettä
            pos = tiedosto.length();
        }
        String utied = tiedosto.substring(0, pos) + ".doc";
        try{
            //File file = new File(tiedosto);
            FileInputStream fs = new FileInputStream(tiedosto);
            //ObjectInputStream os = new ObjectInputStream(fs);
            
            int j=0;
            //System.out.println("Jotain");
            /*
             * Luetaan ensin 24 bittiä jotka muodostuvat seuraavasti.
             *  8 bittiä = merkki
             * 16 bittiä = huffmankoodi
             * 
             * Kun kaikki 24 bittiä on "päällä", on löydetty erotinmerkki.
             * Tämän jälkeen huffmankoodattu teksti on tallennettu 16 bitin
             * lohkoissa.
             */
            char chr = 0;
            int c = -1;
            String boolString="";

            try{
                int laskuri = 0;
                //while((c = os.read()) != -1){
                while((c = fs.read()) != -1){
                    //if (laskuri >= 2047) {
                          //System.out.println("jotain");
                    //}
                    intList.add(c);
                    chr = (char)c;
                    boolString = getBitArray(c);
                    //System.out.print(c);
                    Huffman.purettu += c + " ";
                    laskuri++;
                }
                
            } catch (Exception e){
                System.out.println(e.getMessage());
                fs.close();
            }
            
            //System.out.println("Koko: " + lista.size());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return intList;
    }
       /**
     * Tiedoston purun tekevä metodi
     * @param tiedosto 
     */
    private String puretaan(String tiedosto) {
        String teksti="";
        Map<Integer, Integer> kerrat = new TreeMap<Integer, Integer>();
        Node puu=null;
        try{    
            List<Integer> lista = luePurettavaTiedosto(tiedosto);
            Map<String, String> kartta = new HashMap<String, String>();
            Map<Integer, String> kartta2 = new HashMap<Integer, String>();
            String koodi = "";
            List<Integer> intKoodit = new ArrayList<Integer>();
            int codeStart = 0;
            // Etsitään ensin header osion tiedot.
            // Tiedetään että header ko'ostuu 24 bitistä
            String header  ="";
            for(int i=0; i<lista.size(); i=i+3){
                int i1 = lista.get(i);
                int i2 = lista.get(i+1);
                int i3 = lista.get(i+2);
                
                if (i1 == 255 && i2 == 255 ){//&& i3 == 255){
                    codeStart = i+2;
                    i = lista.size();
                    
                } else {
                    //System.out.println(i1 + " " + i2);
                    //System.out.print(i/3+1 + " Merkki: " + (char)i1 + " " );
                    String kode1 = getBitArray(i2);
                    String kode2 = getBitArray(i3);
                    String code = "";
                    if (kode1.equals("0")){
                        code = getBitArray(i3);
                    } else {
                        code = getBitArray(i2);
                        for (int mi = kode2.length(); mi <8; mi++){
                            code += "0";
                        }
                        code += getBitArray(i3);
                    }
                    int te = Integer.parseInt(code, 2);
                    //System.out.println((char)i1 + " " + Integer.toBinaryString(te) + " " + code);
                    //kartta.put(code, String.valueOf((char)i1));
                    /*
                     * Etsitään erotinmerkki
                     */
                    String kode="";
                    boolean tosi=true;
                    for(int ci=0; ci < code.length(); ci++){
                        if (code.charAt(ci) == '0'){
                            
                            kode = code.substring(ci+1);
                                    
                            ci = code.length();
                        }
                    }
                    //System.out.println((char)i1 + " Code: " + code + " kode " + kode+ " " + Integer.toBinaryString(Integer.parseInt(kode, 2)));
                    kartta.put(kode, String.valueOf((char)i1));
                    
                    
                    //kartta.put(getBitArray(i2) + getBitArray(i3), String.valueOf((char)i1));
                    //System.out.println((char)i1 + " " + i2);
                    /*
                    boolean[] b1 = Huffman.byteToBits(i2);
                    boolean[] b2 = Huffman.byteToBits(i3);
                    String kk = Huffman.byteArrayToString(b1)+Huffman.byteArrayToString(b2);
                    kartta.put(kk, String.valueOf((char)i1));
                    
                    kartta2.put(i2+i3, String.valueOf((char)i1));
                    System.out.print((char)i1 + " " + i2 + " " + i3 + " " + kk + " ");
                    System.out.println(Integer.parseInt(kk, 2));
                    kerrat.put(i1, Integer.parseInt(kk, 2));
                    */
                    
                }
                
                //System.out.println(i1 + " " + i2 + " " + i3);
                
            }
            huffmanTree = teePuu(kartta);
            // Koodattu teksti
            // Tiedetään että jokainen merkki on koodattu 16 bitillä
            String tmp = "";
            for (int i=codeStart; i<lista.size(); i++){
                int i1 = lista.get(i);
                //int ii1 = lista.get(i);
                //int ii2 = lista.get(i+1);
                //koodit.add(String.valueOf(ii1) + " " + String.valueOf(ii2));
                //System.out.println(String.valueOf(ii1) + " " + String.valueOf(ii2));
                boolean[] bt = Huffman.byteToBits(i1);
                
                intKoodit.add(i1);
                //String koodia = Huffman.byteArrayToString(bt);
                
                //int i2 = intKoodit.get(i+1);
                
                
                koodattuTeksti += getBitArray(i1);
                
                //String merkki = etsiKoodi(koodia, puu);
                //System.out.print(merkki);
                
                //tmp += Integer.toBinaryString(i1);
                /*
                boolean[] bitit = Huffman.byteToBits(i1);
                for(int m=0; m<bitit.length; m++){
                    if (bitit[m]){
                        tmp += "1";
                    } else 
                        tmp += "0";
                }
                * 
                */
                /*
                for (Node2 node: nodes){
                    int i5 = node.getCode1();
                    int j5 = node.getCode2();
                    if (node.getCode1()==ii1 && node.getCode2()==ii2){
                        System.out.print(node.getMerkki());
                    }
                }*/
                //int i2 = lista.get(i+1);
                
                //String tmp = getBitArray(i1) + getBitArray(i2);
                //System.out.println("Koodi : " + getBitArray(i1) +" "+ getBitArray(i2)+ " " + i1 + " " + i2 + " " + (char)i1 + " " + (char)i2);
                //System.out.print(kartta.get(getBitArray(i1) + getBitArray(i2)));
                //teksti+=kartta.get(getBitArray(i1) + getBitArray(i2));
            }
            //System.out.println(header);
            //System.out.println(tmp);
            //System.out.println(koodattuTeksti);
            //Huffman.purettu = header + tmp;
            
            String etsijä = "";
            //System.out.println(tmp);
            //System.out.println(koodattuTeksti);
            boolean eka=true;
            for (int i=0; i < koodattuTeksti.length(); i++){
                etsijä += String.valueOf(koodattuTeksti.charAt(i));
                if (kartta.containsKey(etsijä)){
                    //System.out.print(etsijä);
                    //System.out.print(kartta.get(etsijä));
                    teksti += kartta.get(etsijä);
                    etsijä="";
                }
            }
            //System.out.println(teksti);
            kirjoitaTiedosto(tiedosto, teksti);
            /*
            for(String tstr: koodit){
                String[] t2 = tstr.split(" ");
                int i1 = Integer.parseInt(t2[0]);
                int i2 = Integer.parseInt(t2[1]);
                for(Node2 node: nodes){
                    int n1 = node.getCode1();
                    int n2 = node.getCode2();
                    if (i1 == node.getCode1() && i2 == node.getCode2())
                        System.out.print(node.getMerkki());
                }
            }
            */
           
            
            //System.out.println(header);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        return teksti;
    }

    /**
     * Metodi purkaa bitit merkkijonoksi
     * 
     * @param lista 
     */
    private String decode(List<Byte> lista) {
        boolean eka = false;
        boolean toka = true;
        List<String> header = new ArrayList<String>();
        List<String> content = new ArrayList<String>();
        System.out.println("Data:");
        String s = "";
        String bt = "";
        int i = 0;
        BitSet setti = new BitSet(8);
        for(byte b: lista){
            s += Integer.toBinaryString(Integer.decode(Byte.toString(b))) + " ";
            bt += b + " " ;
            //System.out.print(s + " ");
            //System.out.print(b + " ");
            /*
             * byte merkki_byte = (byte) (int)huffman.getMerkki();
             * byte koodi_byte  = (byte) Integer.parseInt(merkki, 2); //so mode 2
             * System.out.print(merkki_byte );
             * System.out.print(" " + koodi_byte + " " );
             */
            /*
            int i = 101;
            i = Integer.decode(Byte.toString(b));
            //int i = Integer.parseInt(Byte.toString(b), 2);
            if (!eka){
                // Header    
                if (s.equals("1111111")){
                    //System.out.println();
                    //System.out.println(s + " ");
                    eka = true;
                } else {
                    if (toka){
                        char c = (char)i;
                        System.out.println(s + " " + i + " " + c + " ");
                        
                        //System.out.print(c + " ");
                        header.add(String.valueOf(c));
                        toka = false;
                    } else {
                        //System.out.print(s + " " + i + " ");
                        //char c = (char)i;
                        //System.out.print(c + " ");
                        header.add(String.valueOf(s));
                        toka = true;
                    }
                }
            } else {
                content.add(s);
            }
            * 
            */
        }
        //System.out.println();
        //System.out.println(s);
        //System.out.println(bt);
        /*
        System.err.println("Header");
        for(String s: header)
            System.out.print(s);
        System.err.println("Content");
        for(String s: content)
            System.out.print(s);
            * 
            */
        return s;
    }
    private void testi(List<Byte> lista){
        System.out.println("Testi");
        int j=0;
        BitSet bitit = new BitSet(8);
        boolean vielä = true;
        String tmp1 = "";
        String tmp2 = "";
        //System.out.println("Jotain");
        for(int i = 0; i < lista.size(); i++){
            byte b = lista.get(i);
            bitit.set(i, b);
            j++;
            if (j==8){
                j=0;
                tulosta(bitit);
                bitit = new BitSet(8);
            }
            
            
            //os.write(arr);
        }
        tulosta(bitit);
    }
    private void tulosta(BitSet bitit){
        System.out.println();
        System.out.println("Tulosta");
        /*
        byte[] arr = Pakkaaja.toByteArray(bitit);
            for(byte b: arr){
                System.out.print(Integer.toBinaryString(Integer.decode(Byte.toString(b)))+ " ");
            }
         * 
         */
    }

   public static String getBitArray(int charInt) {
        String bitString = Integer.toBinaryString(charInt);
        for (int i = bitString.length(); i < 8;i++){
            bitString = "0" + bitString;
        }
        return bitString;
    }

    private Node teePuu(Map<String, String> kertaKartta) {
        PriorityQueue<Node> queue = new PriorityQueue<Node>(1, comparator); 
        int määrä=0;
        Set<String> keys = kertaKartta.keySet();
        Collection<String> values = kertaKartta.values();
        Set set = kertaKartta.entrySet();
        
        Iterator iter = set.iterator();
        while(iter.hasNext()){
            Object it = iter.next();
            String[] jono = String.valueOf(it).split("=");
            char merkki = jono[1].charAt(0);
            char[] jonossa = jono[0].toCharArray();
            decode(merkki, jono[0]);
            /*
            int numero=0;
            for (int p=jonossa.length-1; p >= 0; p--){
                int ii=0;
                if(jonossa[p]=='1'){
                    ii=1;
                }
                numero += ii*Math.pow(2, ((jonossa.length-1)-p));
            }
            
            queue.add(new Node(merkki, numero));
            * 
            */
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
    private Node pos;
    
    /**
     * Tämän metodin pitäisi rakentaa huffman puu takaperin
     * @param merkki
     * @param text
     * @return 
     */
    public String decode(char merkki, String text) {
         char[] input = text.toCharArray();
         String result = "";
         pos = puu;

         for (int i = 0; i < input.length; i++) { //for each character
            if (pos instanceof Node) {
               if (input[i] == '1'){
                   if (pos.getVasen() == null)
                       pos.setVasen(new Node());
                  pos = ((Node) pos).getVasen();
               }
               if (input[i] == '0'){
                   if (pos.getOikea() == null)
                       pos.setOikea(new Node());
                  pos = ((Node) pos).getOikea();
               }
            }
            if (pos.isLehti()) {
               result = result + (pos).getBits();
               pos = puu;
            }
         }

         return result;
      }

    private void etsiPaikka(Node node, String polku){
        String merkki="";
        merkki = polku.substring(0, 1);
        String loppu = "";
        if (polku.length() > 1)
             loppu=polku.substring(1);
        if (merkki.equals("0")) { // Vasen
            
        } else { // oikea
            
        }
    }
    
    /**
     * Rakentaa PriorityQueue jonon koodin pituuden mukaan (Maksimijono)
     * 
     * @param kerrat    Taulukko merkkien siintymiskerroista
     * @return Node     Palautaa Huffman puun
     */
    public Node rakennaKäänteinenPuu(int[] kerrat) {
        PriorityQueue<Node> queue = new PriorityQueue<Node>(1, comparator); 
        // Lisätään jonoon kaikki puun lehdet
        for (char i = 0; i < Pakkaaja.MerkkienMäärä; i++)
            if (kerrat[i] > 0){
                queue.add(new Node(i, kerrat[i]));
                //merkit.set(i);
            }
        
        // Tehdään niin kauan kunnes jonossa on vain yksi jäljellä eli root
        while (queue.size() > 1){
            Node vasen = queue.remove(); // Pienin
            Node oikea = queue.remove(); // Pienin (ed. toiseksi pienin)
            Node uusi = new Node(vasen, oikea);
            queue.add(uusi); // Lisätään uusi node jonoon
        }
        // Palautta juuren eli ensimmäisen Noden
        return queue.remove();
    }   
    private String etsiKoodi(String koodia, Node puu) {
        String merkki="";
        String tmp="";
        Node tmpNode=puu;
        boolean vielä=true;
        int nro=0;
        if (tmpNode!=null){
            while(vielä){
                if (puu.isLehti()){
                    merkki = String.valueOf(puu.getMerkki());
                    vielä=false;
                } else {
                    tmp=koodia.substring(nro, nro+1);
                    if(tmp.equals("0")){
                        tmpNode = tmpNode.getVasen();
                    } else {
                        tmpNode = tmpNode.getOikea();
                    }
                    if (tmpNode!=null){
                        vielä=true;
                    } else 
                        vielä=false;
                    nro++;
                }
            }
        }
        return merkki;
    } 
    private void etsiMerkki(char c, Node huffman){
        if (huffman != null)
            if (huffman.isLehti()){
                //System.out.println(huffman.getMerkki()+ " - " + merkki + " - " + huffman.getMäärä());
                char h = huffman.getMerkki();
                if (c == huffman.getMerkki())
                    //löydettyMerkki=huffman.getBits();
                return;
                
            } 
            etsiMerkki(c,huffman.getVasen());
            etsiMerkki(c,huffman.getOikea());
    }

    private void kirjoitaTiedosto(String tiedosto, String teksti) throws FileNotFoundException {
        int pos = tiedosto.indexOf(".");
        String utied = tiedosto.substring(0, pos) + ".doc";
        FileOutputStream fs = new FileOutputStream(utied);
        try{    
            OutputStreamWriter out = new OutputStreamWriter(fs, "UTF-8");
            //System.out.println("Hakemisto: " + file.getCanonicalPath());
        
            out.write(teksti);
            out.flush();
            fs.close();
        } catch (Exception e){
            System.out.println("KirjoitaTiedosto(): " + e.getMessage());
        }
        
    }
    
}