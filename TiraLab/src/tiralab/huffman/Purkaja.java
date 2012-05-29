/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 *
 * @author mkortelainen
 */
public class Purkaja {
    private String tiedosto="";

    public Purkaja(String tiedosto){
        this.tiedosto = tiedosto;
        puretaan(tiedosto);
    }

    /**
     * Metodi lukee tiedoston tavuina
     * 
     * @param tiedosto
     * @throws FileNotFoundException 
     */
    private void luePurettavaTiedosto(String tiedosto) throws FileNotFoundException{
        ///TODO: Tutki RandomAccessFileä
        InputStream is = new FileInputStream(tiedosto);
    }
       /**
     * Tiedoston purun tekevä metodi
     * @param tiedosto 
     */
    private void puretaan(String tiedosto) {
        try{
            luePurettavaTiedosto(tiedosto);
        } catch(Exception e){
            e.getMessage();
        }
    }
}
