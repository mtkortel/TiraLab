/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman.rakenne;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author mkortelainen
 */
public class HashMap<K, V> implements Iterable<K>{
    private int OLETUS_KOKO = 64;
    private Yksikkö<K,V>[] kartta;
    public int length;
    
    public HashMap(){
        kartta = new Yksikkö[OLETUS_KOKO];
        length=0;
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
                if (entry.getNext() != null){
                    entry = entry.getNext();
                } else {
                    done=true;
                }
            }
        } else {
            kartta[indeksi] = new Yksikkö<K,V>(key, value);
            length++;
        }
    }
    
    
    private int kartanIndeksi(K key){
        int tem = key.hashCode()% kartta.length;
        if (tem < 0){
            tem = 0;
        }
        return tem;
        
        
    }

    public boolean containsKey(String etsijä) {
        for (int i = 0; i < kartta.length; i++){
            if (kartta[i] != null){
                if (kartta[i].getKey().equals(etsijä)){
                    return true;
                }
            }
        }
        return false;
    }

    public String[] keys() {
        String[] keys = new String[kartta.length];
        for (int i = 0; i < kartta.length; i++){
            if (kartta[i] != null){
                keys[i] = kartta[i].getKey().toString();
            }
        }
        return keys;
    }
    

    public String[] values() {
        String[] values = new String[kartta.length];
        for (int i = 0; i < kartta.length; i++){
            if (kartta[i] != null){
                values[i] = kartta[i].getValue().toString();
            }
        }
        return values;
    }

    public Set entrySet() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Iterator<K> iterator() {
        Iterator<K> it = new Iterator<K>() {
            private int index=0;
            @Override
            public boolean hasNext() {
                return index < length && kartta[index] != null;
            }

            @Override
            public K next() {
                return kartta[index++].getKey();
            }

            @Override
            public void remove() {
                // ei poisteta
            }
        };
        return it;
    }
}
