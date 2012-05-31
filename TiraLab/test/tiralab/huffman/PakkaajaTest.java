/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman;

import java.util.BitSet;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author mkortelainen
 */
public class PakkaajaTest {
    
    public PakkaajaTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of toByteArray method, of class Pakkaaja.
     */
    @Test
    public void testToByteArray() {
        System.out.println("toByteArray");
        BitSet bits = null;
        Pakkaaja instance = null;
        byte[] expResult = null;
        byte[] result = instance.toByteArray(bits);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
