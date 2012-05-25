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
    
    Comparator<Node> comparator = new NodeComparator();
    static int MerkkienMäärä = 256;
    HashMap<String, Node> nodes;
    
    public static void main(String[] args){
        Pakkaaja pakkaaja = new Pakkaaja();
        pakkaaja.nodes = new HashMap<String, Node>();
        /*
        if (args.length != 2){
            System.out.println("compress  : Pakkaaja + tiedosto.txt");
            System.out.println("uncompress: Pakkaaja - tiedosto.txt");
            System.exit(-1);
        }
        String mode = args[0];
        String tiedosto = args[1];
        */
        String mode = "+";
        String tiedosto = "tiedosto.txt";
        
        
            if (mode.equals("-")){
                pakkaaja.puretaan(tiedosto);
            } else 
                pakkaaja.pakataan(tiedosto);
    }
    /**
     * Metodi lukee tiedoston tavuina
     * 
     * @param tiedosto
     * @throws FileNotFoundException 
     */
    private void luePurettavaTiedosto(String tiedosto) throws FileNotFoundException{
        ///TODO: Tutki RandomAccessFileä
        InputStream is = new FileInputStream(tiedosto);
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
        //System.out.println("Hakemisto: " + file.getCanonicalPath());
        
        Scanner lukija = new Scanner(new FileInputStream(tiedosto), "UTF-8");
        String rivi = "";
        String vaihto = System.getProperty("line.separator");

        try {
            while (lukija.hasNextLine()){
                rivi += lukija.nextLine() + vaihto;
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
            if (kerrat[i] > 0)
                queue.add(new Node(i, kerrat[i]));
        
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
     * Tiedoston purun tekevä metodi
     * @param tiedosto 
     */
    private void puretaan(String tiedosto) {
        try{
            luePurettavaTiedosto(tiedosto);
        } catch(Exception e){
            e.getMessage();
            e.printStackTrace();
        }
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
            tulostaTiedosto(teksti, huffman);
            
        } catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }
    }
  
    
    /**
     * Tulostaa puun ---- TESTIVERSIO
     * @param huffman 
     */
    private void tulostaPuu(Node huffman, String merkki) {
        if (huffman.isLehti()){
            System.out.println(huffman.getMerkki()+ " - " + merkki + " - " + huffman.getMäärä());
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
    private void tulostaTiedosto(String alkuperäinen, Node huffman){
        String jono="";
        for (char c: alkuperäinen.toCharArray()){
            Node n = nodes.get(String.valueOf(c));
            jono += n.getBits() + " ";
        }
        System.out.println("Alkuperäinen teksti");
        System.out.println(alkuperäinen);
        System.out.println("Koodattu teksti");
        System.out.println(jono);
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
        tulostaPuu(huffman.getVasen(), merkki + "0");
        tulostaPuu(huffman.getOikea(), merkki + "1"); 
        return null;
    }
    
}
