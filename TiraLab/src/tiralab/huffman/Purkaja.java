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
    private String tiedosto="";
    private List<Node2> nodes;

    public Purkaja(String tiedosto){
        nodes = new ArrayList<Node2>();
        this.tiedosto = tiedosto;
        puretaan(tiedosto);
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
                    System.out.print(c + " ");
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
        try{    
            List<Integer> lista = luePurettavaTiedosto(tiedosto);
            Map<String, String> kartta = new HashMap<String, String>();
            Map<Integer, String> kartta2 = new HashMap<Integer, String>();
            List<Integer> koodit = new ArrayList<Integer>();
            int codeStart = 0;
            // Etsitään ensin header osion tiedot.
            // Tiedetään että header ko'ostuu 24 bitistä
            String header  ="";
            for(int i=0; i<lista.size(); i=i+3){
                int i1 = lista.get(i);
                int i2 = lista.get(i+1);
                int i3 = lista.get(i+2);
                
                if (i1 == 255 && i2 == 255 && i3 == 255){
                    codeStart = i+3;
                    i = lista.size();
                    
                } else {
                    //System.out.print(i/3+1 + " Merkki: " + (char)i1 + " " );
                    //System.out.println("Koodi : " + getBitArray(i2) + getBitArray(i3));
                    kartta.put(getBitArray(i2) + getBitArray(i3), String.valueOf((char)i1));
                    kartta2.put(i2+i3, String.valueOf((char)i1));
                    header += getBitArray(i2) + getBitArray(i3);
                    Node2 node = new Node2();
                    node.setMerkki((char)i1);
                    node.setCode1(i2);
                    node.setCode2(i3);
                    nodes.add(node);
                    
                }
                //System.out.println(i1 + " " + i2 + " " + i3);
                
            }

            // Koodattu teksti
            // Tiedetään että jokainen merkki on koodattu 16 bitillä
            String tmp = "";
            for (int i=codeStart; i<lista.size(); i=i+2){
                int i1 = lista.get(i);
                int ii1 = lista.get(i);
                int ii2 = lista.get(i+1);
                koodit.add(ii1+ii2);
                //tmp += Integer.toBinaryString(i1);
                boolean[] bitit = Huffman.byteToBits(i1);
                for(int m=0; m<bitit.length; m++){
                    if (bitit[m]){
                        tmp += "1";
                    } else 
                        tmp += "0";
                }
                
                for (Node2 node: nodes){
                    int i5 = node.getCode1();
                    int j5 = node.getCode2();
                    if (node.getCode1()==ii1 && node.getCode2()==ii2){
                        System.out.print(node.getMerkki());
                    }
                }
                //int i2 = lista.get(i+1);
                
                //String tmp = getBitArray(i1) + getBitArray(i2);
                //System.out.println("Koodi : " + getBitArray(i1) +" "+ getBitArray(i2)+ " " + i1 + " " + i2 + " " + (char)i1 + " " + (char)i2);
                //System.out.print(kartta.get(getBitArray(i1) + getBitArray(i2)));
                //teksti+=kartta.get(getBitArray(i1) + getBitArray(i2));
            }
            //System.out.println(header);
            //System.out.println(tmp);
            
            //Huffman.purettu = header + tmp;
            
            String etsijä = "";
            //System.out.println(tmp);
            for (int i=0; i < tmp.length(); i++){
                etsijä += String.valueOf(tmp.charAt(i));
                if (kartta.containsKey(etsijä)){
                    //System.out.print(etsijä);
                    //System.out.print(kartta.get(etsijä));
                    teksti += etsijä;
                    etsijä="";
                }
            }
            
            
            
                
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
        System.out.println();
        System.out.println(s);
        System.out.println(bt);
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
        byte[] arr = Pakkaaja.toByteArray(bitit);
            for(byte b: arr){
                System.out.print(Integer.toBinaryString(Integer.decode(Byte.toString(b)))+ " ");
            }
    }

   public static String getBitArray(int charInt) {
        String bitString = Integer.toBinaryString(charInt);
        return bitString;
    }

    
}
