/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author mkortelainen
 */
public class Huffman {
    
    public static String pakattu = "";
    public static String purettu = "";
    public static void main(String[] args){

        String mode = "+";
        String tiedosto = "warandpeace";
        tiedosto = "tiedosto";
        
        /*
        if (args.length != 2){
            System.out.println("compress  : Pakkaaja + tiedosto.txt");
            System.out.println("uncompress: Pakkaaja - tiedosto.txt");
            System.exit(-1);
        }
        String mode = args[0];
        String tiedosto = args[1];
        */
        Calendar cal1 = null;
        Calendar cal2 = null;
        long kesto = 0;
        if (mode.equals("+")){
            cal1 = Calendar.getInstance();
            Pakkaaja pakkaaja = new Pakkaaja(tiedosto+".txt");
            cal2 = Calendar.getInstance();
        } else if (mode.equals("-")){
            cal1 = Calendar.getInstance();
            Purkaja purkaja = new Purkaja(tiedosto+".huf");
            cal2 = Calendar.getInstance();
        } else {
            System.out.println("Invalid mode!");
            System.out.println("compress  : Pakkaaja + tiedosto.txt");
            System.out.println("uncompress: Pakkaaja - tiedosto.txt");
            System.exit(-1);
        }
        kesto = cal2.getTimeInMillis() - cal1.getTimeInMillis();
        try{
            String t1 = tiedosto+".txt";
            String t2 = tiedosto+".huf";
            String t3 = tiedosto+".doc";
            File file = new File(t1);
            System.out.println();
            long orig = file.length();
            
            file = new File(t2);
            long comp = file.length();
            file = new File(t3);
            long tulos = file.length();
            
            double ero = 1.0*comp/orig*100;
            DecimalFormat df = new DecimalFormat("##.##");
            if (mode.equals("+")){
                System.out.println("Pakkaamattoman tiedoston koko: " + orig);
                System.out.println("Pakatun tiedoston koko       : " + comp);
            } else{ 
                System.out.println("Puretun tiedoston koko       : " + tulos);
                System.out.println("Koko alkuperäisestä          : " + df.format(ero) +"%");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("H:mm:ss.SSS");
            System.out.println("Operaation kesto: " + kesto + " ms.");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * Muuttaa byteArrayn Stringjonoksi
     * 
     * @param bt
     * @return 
     */
    public static String byteArrayToString(boolean[] bt){
        String koodi="";
        for(int m=0; m < bt.length; m++){
            if(bt[m]){
                koodi+="1";
            } else 
                koodi+="0";
        }
        return koodi;
    }
    
    /**
	 * Palauttaa parametrina saadun luvun 8 vähiten merkitsevää
	 * bittiä taulukossa (luvut [0, 255]).
	 *
	 * @param data muunnettava tavu, lukuarvo väliltä [0, 255]
	 * @return parametrin 8 vähiten merkitsevää bittiä taulukossa,
	 *         vähiten merkitsevä bitti on viimeisessä indeksissä.
	 */
    public static boolean[] byteToBits(int data) {
		if (data < 0 || 255 < data) {
			throw new IllegalArgumentException("" + data);
		}

		boolean[] bits = new boolean[8];
		for (int i=0; i < 8; i++) {
			bits[i] = ( (data & (1 << (7-i)) ) != 0 );
		}
		return bits;
	}
        /**
	 * Muuttaa parametrina saadun bittitaulukon bitit
	 * vastaavaksi kokonaisluvuksi väliltä [0, 255].
	 *
	 * @param 8-paikkainen bittitaulukko, vähiten merkitsevä bitti
	 *        viimeisessä indeksissä.
	 * @return bittien kokonaislukuesitys väliltä [0, 255]
	 */
    public static int bitsToByte(boolean[] bits) {
		if (bits == null || bits.length != 8) {
			throw new IllegalArgumentException();
		}

		int data = 0;
		for (int i = 0; i < 8; i++) {
			if (bits[i]) data += (1 << (7-i));
		}
		return data;
    }
}