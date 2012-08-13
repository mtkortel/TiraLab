/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman;

/**
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
    
    public void add(char c){
        if (lista.length == koko){
            kasvataTaulukkoa();
        }
        lista[koko] = c;
        koko++;
    }

    private void kasvataTaulukkoa() {
        char[] tmp = new char[lista.length * 2];
        for(int i=0; i < lista.length; i++){
            tmp[i] = lista[i];
        }
        lista = tmp;
    }
    public char get(int i){
        if (i < koko){
            return lista[i];
        }
        throw new IndexOutOfBoundsException("Haku yli indexin");
    }
    public int size(){
        return koko;
    }
}
