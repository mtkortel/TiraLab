/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.astar;

/**
 *
 * @author mtkortel
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import javax.imageio.ImageIO;
// ...
public class Verkko {
    final static int MUSTA = -16777216;
    final static int VALKEA = -1;
    final static int LAHTO = -256;
    final static int MAALI = -65536;
    public int verkko[][];
    public int korkeus;
    public int leveys;
    private Solmu[][] solmut;
    
    private int lähtö_1;
    private int lähtö_2;
    private int maali_1;
    private int maali_2;
    // ...
    public Verkko(String tiedosto) {
        BufferedImage kuva = null;
        try {
            kuva = ImageIO.read(new File(tiedosto));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
        korkeus = kuva.getHeight();
        leveys = kuva.getWidth();
        solmut = new Solmu[korkeus][];
        verkko = new int[korkeus][];
        for (int i=0; i<korkeus; i++ ) {
            solmut[i] = new Solmu[leveys];
            verkko[i] = new int[leveys];
        }
        for ( int j=0; j<korkeus; j++ ){            
            for ( int i=0; i<leveys; i++ ) {
                int pikseli = kuva.getRGB(i, j);
                if (pikseli==VALKEA) { // Löytyy
                    //System.out.println("Valkea");
                    verkko[j][i] = 0;
                }
                else if (pikseli==MUSTA){ // Löytyy
                    //System.out.println("Musta");
                    verkko[j][i] = 1;
                } else if (pikseli==LAHTO ) {
                    System.out.println("Lähtö"); //Löytyy
                    lähtö_1 = i;
                    lähtö_2 = j;
                }
                else if (pikseli==MAALI ){
                    System.out.println("Maali"); //Löytyy
                    maali_1 = i;
                    maali_2 = j;
                } else{
                    System.out.println(pikseli);
                }
                
            }          
        }
        System.out.println("Leveys/korkeus: " + verkko.length + "/" + verkko[0].length );
        Collection<Integer> värit = new HashSet<Integer>();
        for (int m=0; m < verkko.length; m++)
            for (int n=0; n < verkko[0].length;n++){
                if (värit.add(verkko[m][n]))
                    System.out.println(m + "/" + n + " " + kuva.getRGB(m, n));
            }
        
    }
    // ...
}