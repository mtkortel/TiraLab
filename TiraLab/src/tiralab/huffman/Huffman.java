/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman;

import java.util.BitSet;
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
        /*
        System.out.println("Testataan bittien käsittelyä");
        //Tästä eteenpäin pois
        BitSet koe = new BitSet(8);
        String koe2 = "a";
        char cha = 'a';
        int tst = 114;
        koe2 = Integer.toBinaryString((int)cha);
        System.out.println("koe2: " + cha + " " + koe2);
        int i=0;
        byte[] b = new byte[8];
        for (char c: koe2.toCharArray()){
            if (c=='1'){
                //koe.set(i, true);
                b[i]=1;
            } else {
                //koe.set(i, false);
                b[i]=0;
            }
            i++;
        }
        //System.out.println("Tulostus:" + koe.size() + " - " + koe.length());
        System.out.println("Tulostus:" + b.length);
        
        for(i=0; i < b.length; i++){
            System.out.print(b[i]);
        }
        System.out.println();
        koe2 = "01100001";
        System.out.println(Integer.parseInt(koe2, 2));
        System.out.println((char)Integer.parseInt(koe2, 2));
        System.out.println((byte) Integer.parseInt("0010", 2)); // 010101
        
        
        System.exit(0);
        */
        // Tänne saakka pois
        
        /*
        if (args.length != 2){
            System.out.println("compress  : Pakkaaja + tiedosto.txt");
            System.out.println("uncompress: Pakkaaja - tiedosto.txt");
            System.exit(-1);
        }
        String mode = args[0];
        String tiedosto = args[1];
        */
        //String mode = "+";
        //String tiedosto = "tiedosto.txt";
        String mode = "-";
        String tiedosto = "tiedosto.huf";
        Pakkaaja pakkaaja = new Pakkaaja("tiedosto.txt");
        Purkaja purkaja = new Purkaja("tiedosto.huf");
        /*
        if (mode.equals("-")){
            Purkaja purkaja = new Purkaja(tiedosto);
        } else {
            Pakkaaja pakkaaja = new Pakkaaja(tiedosto);
        }
        * 
        */
//        System.out.println(pakattu);
  //      System.out.println(purettu);
        /*
        Scanner lukija = new Scanner(pakattu);
        Scanner lukija2 = new Scanner(purettu);
        System.out.print("Tulos on ");
        int laskuri = 0;
        while(lukija.hasNext()) {
            String sana1 = lukija.next();
            String sana2 = "tyhjä";
            if(lukija2.hasNext()) {
                sana2 = lukija2.next();
            }
            if (!sana1.equals(sana2)) {
                System.out.println(laskuri);
                System.out.println("sanat erosivat " + sana1 + " " + sana2);
 
            } 
            laskuri++;
        }
        * 
        */
        if (pakattu.equals(purettu)){
            System.out.println(" sama");
        } else
            System.out.println(" eri");
        
        System.out.println("Pakkaajan koko: " + pakattu.length());
        System.out.println("Purkajan koko : " + purettu.length());
        
        
        
    }
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