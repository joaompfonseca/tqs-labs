/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tqs.sets.BoundedSetOfNaturals;

import java.util.Iterator;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;


    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(1);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = BoundedSetOfNaturals.fromArray(new int[]{50, 60});
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = null;
    }

    @Test
    @DisplayName("Add number not found in a set.")
    public void testAddNew() {

        assertFalse(setA.contains(99));
        assertEquals(0, setA.size());

        setA.add(99);

        assertTrue(setA.contains(99));
        assertEquals(1, setA.size());
    }

    @Test
    @DisplayName("Add repeated numbers.")
    public void testAddRepeated() {
        // Single number
        assertTrue(setB.contains(10));
        assertEquals(6, setB.size());

        assertThrows(IllegalArgumentException.class, () -> setB.add(10));

        // Array of numbers
        assertTrue(setC.contains(50));
        assertTrue(setC.contains(60));

        assertThrows(IllegalArgumentException.class, () -> setC.add(new int[]{50, 60}));
    }

    @Test
    @DisplayName("All natural numbers.")
    public void testAllNatural() {

        assertThrows(IllegalArgumentException.class, () -> setA.add(0));
        assertThrows(IllegalArgumentException.class, () -> setA.add(new int[]{-1}));

        Iterator<Integer> it = setB.iterator();
        while (it.hasNext()) {
            assertTrue(it.next() > 0);
        }
    }

    @Test
    @DisplayName("Max size of a set.")
    public void testMaxSize() {

        assertEquals(0, setA.size());
        assertEquals(1, setA.maxSize());

        setA.add(99);

        assertTrue(setA.contains(99));
        assertEquals(1, setA.size());

        assertThrows(IllegalArgumentException.class, () -> setA.add(100), "Set is full.");

        assertFalse(setA.contains(100));
        assertEquals(1, setA.size());
    }

    @Test
    @DisplayName("Set operation: contains.")
    public void testContains() {

        assertFalse(setA.contains(99));
        assertFalse(setA.contains(100));

        setA.add(99);

        assertTrue(setA.contains(99));
        assertFalse(setA.contains(100));
    }

    @Test
    @DisplayName("Set operation: intersect.")
    public void testIntersect() {

        BoundedSetOfNaturals res1 = setA.intersects(setB); // Result: {}
        assertEquals(0, res1.size());

        BoundedSetOfNaturals res2 = setB.intersects(setC); // Result: {50, 60}
        assertEquals(2, res2.size());
        assertTrue(res2.contains(50));
        assertTrue(res2.contains(60));
    }
}
