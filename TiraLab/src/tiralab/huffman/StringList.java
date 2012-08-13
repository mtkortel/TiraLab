/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman;

/**
 *
<<<<<<< HEAD
 * @author marko
 */
public class StringList{
    private final int def_size = 100;
    private char[] lista;
    private int koko;
    /**
     * Oletuskonstruktori joka alustaa listan
     * 
     */
    public StringList(){
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
 * @author mtkortel
 */
public class StringList {
    private int size = 100;
    private int koko = 0;
    private String[] lista;
    
    public StringList(){
        lista = new String[size];
        koko = 0;
    }
    
    public void add(String s){
        if (lista.length == koko){
            kasvataTaulukkoa();
        }
        lista[koko] = s;
        koko++;
    }

    private void kasvataTaulukkoa() {
        String[] tmp = new String[lista.length * 2];
        for(int i=0; i < lista.length; i++){
>>>>>>> origin
            tmp[i] = lista[i];
        }
        lista = tmp;
    }
<<<<<<< HEAD
    
=======
    public String get(int i){
        if (i < koko){
            return lista[i];
        }
        throw new IndexOutOfBoundsException("Haku yli indexin");
    }
    public int size(){
        return koko;
    }
>>>>>>> origin
}
