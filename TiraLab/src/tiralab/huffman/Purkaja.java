/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;
import tiralab.huffman.rakenne.HashMap;
import tiralab.huffman.rakenne.StringArray;


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

    private IntList lueTiedosto(String tiedosto) throws IOException{
        File file = new File(tiedosto);
        byte[] array = FileUtils.getBytesFromFile(file);
        System.out.println("bytearray: " + array.length);
        StringBuilder sb = new StringBuilder();
        IntList lista = new IntList();
        for (byte b: array){
            lista.add(b);
        }
        return lista;
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
             * Luetaan ensin 32 bittiä jotka muodostuvat seuraavasti.
             *  8 bittiä = merkki
             * 24 bittiä = huffmankoodi
             * 
             * Kun kaikki 32 bittiä on "päällä", on löydetty erotinmerkki.
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
            //List<Integer> lista = luePurettavaTiedosto(tiedosto);
            IntList lista = lueTiedosto(tiedosto);
            //HashMap<String, String> kartta = new HashMap<String, String>();
            StringArray kartta = new StringArray();
            
            //Map<Integer, String> kartta2 = new java.util.HashMap<Integer, String>();
            
            String koodi = "";
            //List<Integer> intKoodit = new ArrayList<Integer>();
            IntList intKoodit = new IntList();
            int codeStart = 0;
            // Etsitään ensin header osion tiedot.
            // Tiedetään että header ko'ostuu 32 bitistä
            /*
            for(int i=0; i<lista.size(); i=i+4){
                int i1 = lista.get(i);
                int i2 = lista.get(i+1);
                int i3 = lista.get(i+2);
                int i4 = lista.get(i+3);
                System.out.println(i + " " + i1 + " " + i2 + " " + i3 + " " + i4);
            }
            */
            
            String header  ="";
            List<String> li = new ArrayList<String>();
            for(int i=0; i<lista.size()-4; i=i+4){
                int i1 = lista.get(i);
                int i2 = lista.get(i+1);
                int i3 = lista.get(i+2);
                int i4 = lista.get(i+3);
                int ii1 = i1;
                i1 = byteToInt(i1);
                i2 = byteToInt(i2);
                i3 = byteToInt(i3);
                i4 = byteToInt(i4);
                
                if (i1 == 255 && i2 == 255 && i3 == 255 && i4 == 255 ){
                    codeStart = i+4;
                    i = lista.size();
                } else {
                    String kode1 = getBitArray(i2);
                    String kode2 = getBitArray(i3);
                    String kode3 = getBitArray(i4);
                    String code = "";
                   // System.out.println((char)i1 + " " + i2 + " " + i3 + " "+ i4);
                    if (kode1.equals("0")){
                        code = getBitArray(i3);
                    } else {
                        code = getBitArray(i2);
                        for (int mi = kode2.length(); mi <8; mi++){
                            code += "0";
                        }
                        for (int mi = kode3.length(); mi <8; mi++){
                            code += "0";
                        }
                        
                        code += getBitArray(i3);
                        code += getBitArray(i4);
                        
                    }
                    /*
                     * Etsitään erotinmerkki
                     */
                    
                    String kode="";
                    for(int ci=0; ci < code.length(); ci++){
                        if (code.charAt(ci) == '0'){
                            kode = code.substring(ci+1);
                            ci = code.length();
                        }
                    }
                    String s = "" + kode + " " + i1 + ""  + i2;
                    //System.out.println((char)i1 + " Code: " + code + " kode " + kode+ " " + Integer.toBinaryString(Integer.parseInt(kode, 2)));
                    if ((char)i1 == 'x'){
                        //System.out.println("1- "+(char)i1 + ": " + kode);
                        //System.out.println("2- "+(char)i1 + ": " + kode1+ " " + kode2+ " "+kode3);
                        System.out.println("x- "+(char)i1 + ": " + i2+ " " + i3+ " "+i4);
                        System.out.println("x- "+(char)ii1 + ": " + i2+ " " + i3+ " "+i4);
                    }
                    //li.add((char)i1 + ": " + kode1+ " " + kode2+ " "+kode3);
                    li.add((char)i1 + ": " + kode);
                    //}
                    kartta.put(kode, String.valueOf((char)i1));
                    //System.out.println(String.valueOf((char)i1 + " " + kode + " " ));
                   
                }
                
                //System.out.println(i1 + " " + i2 + " " + i3);
                
            }
            Collections.sort(li);
            for (String s: li){
                //System.out.println(s);
            }
            // Koodattu teksti
            String tmp = "";
            Node huffKartta = teeHuffman(kartta);
            
            for (int i=codeStart; i<lista.size(); i++){
                int i1 = lista.get(i);
                //boolean[] bt = Huffman.byteToBits(i1);
                intKoodit.add(i1);
                koodattuTeksti += getBitArray(i1);
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
//                    System.out.print(etsijä);
                    //System.out.print(kartta.get(etsijä));
                    teksti += kartta.get(etsijä);
                    etsijä="";
                } else {
                   // System.out.println(etsijä);
                }
            }
            System.out.println(teksti);
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
           
  //          System.out.println(teksti);
            //System.out.println(header);
        } catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
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
        //BitSet setti = new BitSet(8);
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

    public static byte[] intToByteArray(int a)
    {
        byte[] ret = new byte[4];
        ret[0] = (byte) (a & 0xFF);   
        ret[1] = (byte) ((a >> 8) & 0xFF);   
        ret[2] = (byte) ((a >> 16) & 0xFF);   
        ret[3] = (byte) ((a >> 24) & 0xFF);
        return ret;
    }
   public static String getBitArray(int charInt) {
        String bitString = Integer.toBinaryString(charInt);
        int j = bitString.length();
        for (int i = bitString.length(); i < 8;i++){
            bitString = "0" + bitString;
        }
        return bitString;
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
    Node n2 = new Node();
    
    private void reverseHuffmanPuu(Node huffman, String merkki, String koodi, int cnt) {
        char c = koodi.charAt(cnt);
        if (c=='0'){// to Left
            if (huffman.getVasen() != null){ // on jo oikealla tavaraa
                
            } else { //oikea on tyhjä
                Node tmp = new Node();
                huffman.setVasen(tmp);
                
            }
        } else { // to Right
            
        }
    }

    private Node teeHuffman(StringArray kartta) {
        Node n1 = new Node();
        for(int i = 0; i < kartta.length; i++){
            String s1 = kartta.keys()[i];
            String s2 = kartta.values()[i];
            Node leaf = new Node();
        }
        
        return n1;
    }

    private int byteToInt(int i1) {
        int i=0;
        if (i1 >= 0 && i1<= 127){
            return i1;
        } else {
            i= 127+Math.abs(i1);
        }
        return i;
    }
    
}