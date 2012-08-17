/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman.rakenne;

/**
 *
 * @author mkortelainen
 */
public class HashMap<K, V> {
    private int OLETUS_KOKO = 64;
    private Yksikkö<K,V>[] kartta;
    
    public HashMap(){
        kartta = new Yksikkö[OLETUS_KOKO];
    }
    public V get(K key){
        if ( key == null){
            return null;
        }
        Yksikkö<K, V> entry = kartta[kartanIndeksi(key)];
        
        return entry.getValue();
    }
    public void put(K key, V value){
        if (key == null){
            return;
        }
        int indeksi = kartanIndeksi(key);
        Yksikkö<K, V> entry = kartta[indeksi];
        if (entry != null){
            boolean done = false;
            while (!done){
                if (key.equals((entry.getKey()))){
                    entry.setValue(value);
                } else if (entry.getNext()==null){
                    entry.setNext(new Yksikkö<K,V>(key, value));
                    done = true;
                }
                entry = entry.getNext();
            }
        } else {
            kartta[indeksi] = new Yksikkö<K,V>(key, value);
        }
    }
    
    
    private int kartanIndeksi(K key){
        return key.hashCode() % kartta.length;
    }
}
