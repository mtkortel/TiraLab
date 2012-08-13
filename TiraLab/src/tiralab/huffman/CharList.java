/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman;

/**
<<<<<<< HEAD
 *
 * @author marko
 */
public class CharList{
    private final int def_size = 100;
    private char[] lista;
    private int koko;
    /**
     * Oletuskonstruktori joka alustaa listan
     * 
     */
    public CharList(){
        lista = new char[def_size];
        koko = 0;
    }
    
    public void add(char new_char) {
        if (koko == lista.length){
            kasvataKoko();
        }
        lista[koko] = new_char;
        koko++;
    }

    public char get(int i) {
        if (i < lista.length){
            return lista[i];
        } else {
            return '0';
        }
    }

    private void kasvataKoko() {
        char[] tmp = new char[lista.length*2];
        for (int i=0; i < lista.length; i++){
=======
 * Char-merkkien taulukkoluokka
 * 
 * @author mtkortel
 */
public class CharList {
    private int size = 100;
    private int koko = 0;
    private char[] lista;
    
    public CharList(){
        lista = new char[size];
        koko = 0;
    }
    /**
     * Lisää merkin jonoon
     * 
     * @param c 
     */
    public void add(char c){
        if (lista.length == koko){
            kasvataTaulukkoa();
        }
        lista[koko] = c;
        koko++;
    }
    /**
     * Mikäli taulukko täyttyy taulukon koko kaksinkertaistetaan
     */
    private void kasvataTaulukkoa() {
        char[] tmp = new char[lista.length * 2];
        for(int i=0; i < lista.length; i++){
>>>>>>> origin
            tmp[i] = lista[i];
        }
        lista = tmp;
    }
<<<<<<< HEAD
    
=======
    /**
     * Palautaa parametrin osoittamassa kohdassa olevan merkin
     * @param i
     * @return 
     */
    public char get(int i){
        if (i < koko){
            return lista[i];
        }
        throw new IndexOutOfBoundsException("Haku yli indexin");
    }
    /**
     * Palautaa taulukosta käytetyn ko'on
     * @return 
     */
    public int size(){
        return koko;
    }
>>>>>>> origin
}
