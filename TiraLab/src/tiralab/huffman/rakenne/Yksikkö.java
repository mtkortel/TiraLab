/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman.rakenne;

/**
 * Oma HashMap - luokka
 * 
 * @author mkortelainen
 */
public class Yksikkö<K, V> {
    private Yksikkö<K, V> next;  
    private final K key;  
    private V value;  
   
    public Yksikkö(K key, V value) {  
        this.key = key;  
        this.setValue(value);  
    }  

    /**
     * Palauttaa avain-arvon
     * 
     * @return 
     */
    public K getKey() {  
        return key;  
    }  

    /**
     * Asettaa Yksikön arvon
     * @param value 
     */
    public void setValue(V value) {  
        this.value = value;  
    }  

    /**
     * Palauttaa Yksikön arvon
     * @return 
     */
    public V getValue() {  
        return value;  
    }  

    /**
     * Asettaa seuraavan Yksikön jolla on sama indeksi, mutta eri avain
     * @param next 
     */
    public void setNext(Yksikkö<K, V> next) {  
        this.next = next;  
    }  
    
    /**
     * Palauttaa seuraavan Yksikön jolla on sama indeksi, mutta eri avain
     */
    public Yksikkö<K, V> getNext() {  
        return next;  
    }   
}
