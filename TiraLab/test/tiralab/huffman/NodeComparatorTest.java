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
public class NodeComparatorTest {
    
    public NodeComparatorTest() {
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
     * Test of compare method, of class NodeComparator.
     */
    @Test
    public void testCompare() {
        System.out.println("compare");
        Node t = new Node('a',2);
        Node t1 = new Node('a',2);;
        NodeComparator instance = new NodeComparator();
        int expResult = 0;
        int result = instance.compare(t, t1);
        assertTrue(result == 0);
    }
}
