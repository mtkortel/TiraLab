/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman;

import java.io.*;
import java.util.*;

/**
 * Pakkaaja, joka perustuu Huffman koodiin
 * 
 * @author mtkortel
 */
public class Pakkaaja {
    ///TODO: Nyt tallentaa kaikki merkit omina tavuina, kun pitäisi saada bittijonoksi
    Comparator<Node> comparator = new NodeComparator();
    int MerkkienMäärä = 256;
    HashMap<String, Node> nodes;
    String tiedosto = "";
    List<Byte> code;
    
    private BitSet merkit;
    
    public Pakkaaja(String tiedosto){
        nodes = new HashMap<String, Node>();
        merkit = new BitSet(MerkkienMäärä);
        
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
        File file = new File(tiedosto);
        System.out.println("Hakemisto: " + file.getCanonicalPath());
        
        Scanner lukija = new Scanner(new FileInputStream(tiedosto), "UTF-8");
        String rivi = "";
        String vaihto = System.getProperty("line.separator");

        try {
            while (lukija.hasNextLine()){
                rivi += lukija.nextLine();// + vaihto;
            }
        } finally{
            lukija.close();
        }
        System.out.println(rivi);
        return rivi;
        
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
        return kerrat;
    }
    /**
     * Rakentaa PriorityQueue jonon esiintymismäärien mukaan
     * 
     * @param kerrat    Taulukko merkkien siintymiskerroista
     * @return Node     Palautaa Huffman puun
     */
    private Node rakennaPuu(int[] kerrat) {
        PriorityQueue<Node> queue = new PriorityQueue<Node>(1, comparator); 
        // Lisätään jonoon kaikki puun lehdet
        for (char i = 0; i < MerkkienMäärä; i++)
            if (kerrat[i] > 0){
                queue.add(new Node(i, kerrat[i]));
                merkit.set(i);
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
            tulostaPuu(huffman, "0");
            System.out.println("");
            List<Byte> koodia = new ArrayList<Byte>();
            koodia = tulostaTiedosto(teksti, huffman, koodia);
            System.out.println("Header osio");
            code = new ArrayList<Byte>();
            tulostaPuu2(huffman, "0");
            System.out.println("Tällainen merkit ja koodit");
            for(Byte b: code){
                System.out.print(b);
            }
            code.add(Byte.MAX_VALUE);
            code.add(Byte.MAX_VALUE);
            System.out.println();
            System.out.println("Tällainen välimerkki");
            for(Byte b: code){
                System.out.print(b);
            }
            code.addAll(koodia);
            System.out.println();
            System.out.println("Tällainen lopullinen");
            for(Byte b: code){
                System.out.print(b);
            }
            
        } catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
  
    
    /**
     * Tulostaa puun ---- TESTIVERSIO
     * @param huffman 
     */
    private void tulostaPuu(Node huffman, String merkki) {
        if (huffman.isLehti()){
            //System.out.println(huffman.getMerkki()+ " - " + merkki + " - " + huffman.getMäärä());
            huffman.setBits(merkki);
            nodes.put(String.valueOf(huffman.getMerkki()), huffman);
            return;
        } 
        tulostaPuu(huffman.getVasen(), merkki + "0");
        tulostaPuu(huffman.getOikea(), merkki + "1");
    }
    /**
     * Käydään alkuperäinen teksti merkki merkiltä läpi ja muutetaan kirjain huffman koodiksi
     * 
     * @param alkuperäinen
     * @param huffmanTree 
     */
    private List<Byte> tulostaTiedosto(String alkuperäinen, Node huffman, List<Byte> text){
        String jono="";
        for (char c: alkuperäinen.toCharArray()){
            Node n = nodes.get(String.valueOf(c));
            jono += n.getBits() + " ";
            text.add((byte) Integer.parseInt(n.getBits(), 2));
        }
        
        System.out.println("Alkuperäinen teksti");
        System.out.println(alkuperäinen);
        System.out.println("Koodattu teksti");
        System.out.println(jono);
        kirjoitaPuu(huffman);
        return text;
    }
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
    }

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

    /**
     * Tulostaa puun ---- TESTIVERSIO
     * @param huffman 
     */
    private void tulostaPuu2(Node huffman, String merkki) {
        if (huffman.isLehti()){
            byte merkki_byte = (byte) (int)huffman.getMerkki();
            byte koodi_byte  = (byte) Integer.parseInt(merkki, 2); //so mode 2
            //System.out.print(merkki_byte );
            //System.out.print(" " + koodi_byte + " " );
            code.add(merkki_byte);
            code.add(koodi_byte);
            return;
        } else {
            //setti.set(0, true);
            //System.out.print(setti);
        }
        tulostaPuu2(huffman.getVasen(), merkki + "0");
        tulostaPuu2(huffman.getOikea(), merkki + "1");
    }
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
        /*
         * 
        try{
            File file = new File(utied);
            FileOutputStream ulos = new FileOutputStream(file);
            
        } catch (Exception e){
            e.getMessage();
        }
        * 
        */
    }

    private void kirjoita(String tiedosto) {
        int pos = tiedosto.indexOf(".");
        if (pos < 0){
            // ei pistettä
            pos = tiedosto.length();
        }
        String utied = tiedosto.substring(0, pos) + ".huf";
        try{
            File file = new File(utied);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            StringBuffer sb = new StringBuffer();
            int i=0;
            BitSet bitit = new BitSet(8);
            for(byte b: code){
                //bw.write(b);
                String jono = Integer.toBinaryString(Integer.parseInt(Byte.toString(b)));
                char[] arr = jono.toCharArray();
                for(char c: arr){
                    if (c==0){
                        bitit.set(i, false);
                    } else
                        bitit.set(i, true);
                    i++;
                    if (i==8){
                        //bw.append(bitit);
                    }
                }
                
                
                System.out.println("Byte: " + String.valueOf(b));
                System.out.println(Integer.toBinaryString(Integer.parseInt(Byte.toString(b))));
            }
            System.out.println("SB: " + sb.toString());
            bw.close();
            System.out.println();
            System.out.println("Pakatun tiedoston koko       : " + file.length());
            file = new File(tiedosto);
            System.out.println("Pakkaamattoman tiedoston koko: " + file.length());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
