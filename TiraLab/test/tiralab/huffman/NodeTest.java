/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author mkortelainen
 */
public class NodeTest {
    
    public NodeTest() {
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
     * Test of getMäärä method, of class Node.
     */
    @Test
    public void testGetMäärä() {
        //System.out.println("getM\u00e4\u00e4r\u00e4");
        Node instance = null;
        int expResult = 0;
        int result = instance.getMäärä();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMäärä method, of class Node.
     */
    @Test
    public void testSetMäärä() {
        //System.out.println("setM\u00e4\u00e4r\u00e4");
        int määrä = 0;
        Node instance = null;
        instance.setMäärä(määrä);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMerkki method, of class Node.
     */
    @Test
    public void testGetMerkki() {
        //System.out.println("getMerkki");
        Node instance = null;
        char expResult = ' ';
        char result = instance.getMerkki();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMerkki method, of class Node.
     */
    @Test
    public void testSetMerkki() {
        //System.out.println("setMerkki");
        char merkki = ' ';
        Node instance = null;
        instance.setMerkki(merkki);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVasen method, of class Node.
     */
    @Test
    public void testGetVasen() {
        //System.out.println("getVasen");
        Node instance = null;
        Node expResult = null;
        Node result = instance.getVasen();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setVasen method, of class Node.
     */
    @Test
    public void testSetVasen() {
        //System.out.println("setVasen");
        Node vasen = null;
        Node instance = null;
        instance.setVasen(vasen);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOikea method, of class Node.
     */
    @Test
    public void testGetOikea() {
        //System.out.println("getOikea");
        Node instance = null;
        Node expResult = null;
        Node result = instance.getOikea();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setOikea method, of class Node.
     */
    @Test
    public void testSetOikea() {
        //System.out.println("setOikea");
        Node oikea = null;
        Node instance = null;
        instance.setOikea(oikea);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isLehti method, of class Node.
     */
    @Test
    public void testIsLehti() {
        //System.out.println("isLehti");
        Node instance = null;
        boolean expResult = false;
        boolean result = instance.isLehti();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBits method, of class Node.
     */
    @Test
    public void testSetBits() {
        //System.out.println("setBits");
        String merkki = "";
        Node instance = null;
        instance.setBits(merkki);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBits method, of class Node.
     */
    @Test
    public void testGetBits() {
        //System.out.println("getBits");
        Node instance = null;
        String expResult = "";
        String result = instance.getBits();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
