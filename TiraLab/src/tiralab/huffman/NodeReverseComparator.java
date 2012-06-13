/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman;

import java.util.Comparator;

/**
 * Node luokan vertailua varten
 * 
 * @author mtkortel
 */
public class NodeReverseComparator implements Comparator<Node>{

    /**
     * Vertaillaan esiintymiskertoja käänteisessä järjestyksessä (maksimijono)
     * @param t
     * @param t1
     * @return 
     */
    @Override
    public int compare(Node t, Node t1) {
         // Returns a negative integer, zero, or a positive integer as this object is 
        // less than, equal to, or greater than the specified object
        if (t.getMäärä() < t1.getMäärä()){
            return 1;
        } else if (t.getMäärä() > t1.getMäärä()){
            return -1;
        } 
        return 0;
    }

    
}