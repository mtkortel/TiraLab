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
        Node instance = new Node('c', 2);
        int expResult = 2;
        int result = instance.getMäärä();
        assertTrue(expResult == result);
        
    }

    /**
     * Test of setMäärä method, of class Node.
     */
    @Test
    public void testSetMäärä() {
        //System.out.println("setM\u00e4\u00e4r\u00e4");
        int määrä = 0;
        Node instance = new Node('c', 2);
        instance.setMäärä(määrä);
        assertTrue(instance.getMäärä() == määrä);
    }

    /**
     * Test of getMerkki method, of class Node.
     */
    @Test
    public void testGetMerkki() {
        //System.out.println("getMerkki");
        Node instance = new Node('c', 2);
        char expResult = 'c';
        char result = instance.getMerkki();
        assertTrue(result == expResult);
    }

    /**
     * Test of setMerkki method, of class Node.
     */
    @Test
    public void testSetMerkki() {
        //System.out.println("setMerkki");
        char merkki = ' ';
        Node instance = new Node('c', 2);
        instance.setMerkki(merkki);
        assertTrue(instance.getMerkki() == merkki);
    }

    /**
     * Test of getVasen method, of class Node.
     */
    @Test
    public void testGetVasen() {
        //System.out.println("getVasen");
        Node instance3 = new Node('d', 3);
        Node instance2 = new Node('c', 2);
        Node instance = new Node(instance2, instance3);
        Node expResult = new Node('c', 2);
        Node result = instance.getVasen();
        assertTrue(result.getMerkki() == expResult.getMerkki());
    }

    /**
     * Test of setVasen method, of class Node.
     */
    @Test
    public void testSetVasen() {
         //System.out.println("getVasen");
        Node instance3 = new Node('d', 3);
        Node instance2 = new Node('c', 2);
        Node instance = new Node(instance2, instance3);
        Node expResult = new Node('c', 2);
        instance.setVasen(expResult);
        assertTrue(instance.getVasen() == expResult);
    }

    /**
     * Test of getOikea method, of class Node.
     */
    @Test
    public void testGetOikea() {
        //System.out.println("getVasen");
        Node instance3 = new Node('d', 3);
        Node instance2 = new Node('c', 2);
        Node instance = new Node(instance2, instance3);
        Node expResult = new Node('d', 3);
        Node result = instance.getOikea();
        assertTrue(result.getMerkki() == expResult.getMerkki());
    }

    /**
     * Test of setOikea method, of class Node.
     */
    @Test
    public void testSetOikea() {
         //System.out.println("getVasen");
        Node instance3 = new Node('d', 3);
        Node instance2 = new Node('c', 2);
        Node instance = new Node(instance2, instance3);
        Node expResult = new Node('c', 2);
        instance.setOikea(expResult);
        assertTrue(instance.getOikea() == expResult);
    }

    /**
     * Test of isLehti method, of class Node.
     */
    @Test
    public void testIsLehti() {
        //System.out.println("isLehti");
        Node instance = new Node('d', 3);
        boolean expResult = true;
        boolean result = instance.isLehti();
       assertTrue(instance.isLehti());;
    }

    /**
     * Test of setBits method, of class Node.
     */
    @Test
    public void testSetBits() {
        //System.out.println("setBits");
        String merkki = "1";
        Node instance = new Node('a',1);
        instance.setBits(merkki);
        assertTrue(instance.getBits().equals(merkki));
    }

    /**
     * Test of getBits method, of class Node.
     */
    @Test
    public void testGetBits() {
        //System.out.println("setBits");
        String merkki = "1";
        Node instance = new Node('a',1);
        instance.setBits(merkki);
        assertTrue(instance.getBits().equals(merkki));
    }
}
