/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman;

/**
 * Huffman pakkauksen Node-luokka
 * 
 * Luokkaan tallennetaan joko merkki ja lukumäärä tai
 * vasen ja oikea lapsi sekä lukumäärä.
 *
 * Vasen Node on aina 0 ja oikea 1 (Huffman code), jolloin
 * vasen.määrä >= oikea.määrä
 * 
 * @author mtkortel
 */
public class Node {
    private char merkki;
    private int  määrä;
    
    private Node vasen;
    private Node oikea;
    private boolean lehti;
    private String bits;
    public Node(){
        
    }
    /**
     * Konstruktori binääräpuun lehdelle
     */
    public Node(char merkki, int määrä){
        this.lehti = true;
        this.merkki = merkki;
        this.määrä  = määrä;
        this.lehti = true;
    }
    /**
     * Konstruktori binääripuulle, ei lehdelle
     */
    public Node(Node vasen, Node oikea){
        this.lehti = false;
        this.vasen = vasen;
        this.oikea = oikea;
        this.määrä = vasen.getMäärä() + oikea.getMäärä();
        this.lehti = false;
    }
    
    
    
    /**
     * @return 
     */
    public int getMäärä(){
        return this.määrä;
    }
    
    /**
     * @param määrä 
     */
    public void setMäärä(int määrä){
        this.määrä = määrä;
    }

    /**
     * @return the merkki
     */
    public char getMerkki() {
        return merkki;
    }

    /**
     * @param merkki the merkki to set
     */
    public void setMerkki(char merkki) {
        this.merkki = merkki;
    }

    /**
     * @return the vasen
     */
    public Node getVasen() {
        return vasen;
    }

    /**
     * @param vasen the vasen to set
     */
    public void setVasen(Node vasen) {
        this.vasen = vasen;
    }

    /**
     * @return the oikea
     */
    public Node getOikea() {
        return oikea;
    }

    /**
     * @param oikea the oikea to set
     */
    public void setOikea(Node oikea) {
        this.oikea = oikea;
    }

    /**
     * @return the onLehti
     */
    public boolean isLehti() {
        return lehti;
    }

    void setBits(String merkki) {
        this.bits = merkki;
    }
    public String getBits(){
        return bits;
    }

    
}
