/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman;

/**
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
            tmp[i] = lista[i];
        }
        lista = tmp;
    }
    public String get(int i){
        if (i < koko){
            return lista[i];
        }
        throw new IndexOutOfBoundsException("Haku yli indexin");
    }
    public int size(){
        return koko;
    }
}