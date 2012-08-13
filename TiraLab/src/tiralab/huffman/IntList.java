/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman;

/**
 *
 * @author mtkortel
 */
public class IntList {
    private int size = 100;
    private int koko = 0;
    private int[] lista;
    
    public IntList(){
        lista = new int[size];
        koko = 0;
    }
    
    public void add(int c){
        if (lista.length == koko){
            kasvataTaulukkoa();
        }
        lista[koko] = c;
    }

    private void kasvataTaulukkoa() {
        int[] tmp = new int[lista.length * 2];
        for(int i=0; i < lista.length; i++){
            tmp[i] = lista[i];
        }
        lista = tmp;
    }
    public int get(int i){
        if (i < koko){
            return lista[i];
        }
        throw new IndexOutOfBoundsException("Haku yli indexin");
    }
    public int size(){
        return koko;
    }
}
