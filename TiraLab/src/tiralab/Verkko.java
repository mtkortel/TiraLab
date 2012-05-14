/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab;

/**
 *
 * @author mtkortel
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    
    private int lahtö_1;
    private int lahtö_2;
    private int maali_1;
    private int maali_2;
    // ...
    public Verkko(String tiedosto) {
        BufferedImage kuva = null;
        try {
            kuva = ImageIO.read(new File(tiedosto));
        } catch (IOException ex) {
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
                if (pikseli==VALKEA) {
                    verkko[j][i] = 0;
                }
                else if (pikseli==MUSTA)
                    verkko[j][i] = 1;
                else if (pikseli==LAHTO ) {
                    lahtö_1 = i;
                    lahtö_2 = j;
                }
                else if (pikseli==MAALI )
                    maali_1 = i;
                    maali_2 = j;
                }
            }          
        }
            // ...
    }
    // ...
}