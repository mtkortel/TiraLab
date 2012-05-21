/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Pakkaaja, joka perustuu Huffman koodiin
 * 
 * @author mtkortel
 */
public class Pakkaaja {
    
    Comparator<Node> comparator = new NodeComparator();
    static int MerkkienMäärä = 256;
    
    public static void main(String[] args){
        Pakkaaja pakkaaja = new Pakkaaja();
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
     * @return taulukko merkkien esiintymismääristä
     * @throws FileNotFoundException
     */
    private int[] lueTekstiTiedosto(String tiedosto) throws FileNotFoundException, IOException {
        File file = new File(".");
        //System.out.println("Hakemisto: " + file.getCanonicalPath());
        Scanner lukija = new Scanner(new FileInputStream(tiedosto), "UTF-8");
        String rivi = "";
        String vaihto = System.getProperty("line.separator");
        int[]  kerrat = new int[MerkkienMäärä];
        
        Node node;
        try {
            while (lukija.hasNextLine()){
                rivi = lukija.nextLine() + vaihto;
                for(int i = 0; i < rivi.length(); i++){
                    kerrat[rivi.toCharArray()[i]]++; // lisätään esiintymiskerta merkille
                }
            }
        } finally{
            lukija.close();
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
            int[] kerrat = lueTekstiTiedosto(tiedosto);
            // Tiedosto luettu loppuun rakennetaan puu
            Node huffman = rakennaPuu(kerrat);
            tulostaPuu(huffman, "0");
            
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
            return;
        }
        tulostaPuu(huffman.getVasen(), merkki + "0");
        tulostaPuu(huffman.getOikea(), merkki + "1");
    }
}
