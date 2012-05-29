/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman;

import java.util.HashMap;

/**
 *
 * @author mkortelainen
 */
public class Huffman {
    public static void main(String[] args){
        
        
        /*
        if (args.length != 2){
            System.out.println("compress  : Pakkaaja + tiedosto.txt");
            System.out.println("uncompress: Pakkaaja - tiedosto.txt");
            System.exit(-1);
        }
        String mode = args[0];
        String tiedosto = args[1];
        */
        String mode = "+";
        String tiedosto = "tiedosto.txt";
        if (mode.equals("-")){
            Purkaja purkaja = new Purkaja(tiedosto);
        } else {
            Pakkaaja pakkaaja = new Pakkaaja(tiedosto);
        }
    }
}
