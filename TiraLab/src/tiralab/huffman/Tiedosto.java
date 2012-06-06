/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman;

import java.io.*;

/**
 *
 * @author mkortelainen
 */
public class Tiedosto {
    private final int BYTE_SIZE = 8;
    private final char EOF = (char) 256;
    private boolean lopussa = false;
    
    private OutputStream output;
    private boolean kirjoitusAuki=false;
    
    private InputStream input;
    private boolean lukuAuki=false;
    
    private int merkkejä;
    private int merkkiLaskuri;
    
    public Tiedosto(String tiedosto, boolean kirjoitus){
    // set up in/out streams (wrap in BitIn/OutStreams for EOF and bit mode)
        //InputStream input = new BufferedInputStream(new FileInputStream(inputFileName));
        //OutputStream output = new OpenPrintStream(System.out);
        //if (outputFileName.length() > 0) {
            //output = new BufferedOutputStream(new FileOutputStream(outputFileName));
        //}
        try{
        if (kirjoitus){
            input = new BufferedInputStream(new FileInputStream(tiedosto));
            lukuAuki=true;
        } else {
            output = new BufferedOutputStream(new FileOutputStream(tiedosto));
            kirjoitusAuki=true;
        }
        
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void close(){
        if (kirjoitusAuki){
            
            if (merkkiLaskuri > 0)
                flush();
        }
    }

    private void flush() {
        write(merkkejä);
        merkkejä=0;
        merkkiLaskuri=0;
    }

    public void write(int b) {
        if (b == EOF || lopussa) {
            System.out.println("  ** BitOutputStream EOF seen");
            lopussa = true;
        } else {
            System.out.println("  ** BitOutputStream write: " + b + " (" 
                    + toPrintable((char) b) + ")");
            try {
                output.write(b);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public String toPrintable(char ch) {
    	switch (ch) {
            case '\0': return "'\\0'";
            case '\n': return "'\\n'";
            case '\r': return "'\\r'";
            case '\t': return "'\\t'";
            case '\f': return "'\\f'";
            case EOF:  return "EOF";
            default:
                if (ch < 32) {
                    return String.format("%03d", (int) ch);  // control characters in binary files
		} else {
                    return "'" + ch + "'";
		}
        }
    }
}
