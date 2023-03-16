package edu.union.adt.game.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import edu.union.maingame.game.board_model.GameBoard;
import edu.union.maingame.game.board_model.BoardObserver;
import edu.union.maingame.game.board_model.CoordinatePoint;

//import edu.union.adt.game.;

/**
 * This class is a test suite for the CoordinatePoint class.
 * 
 * @author Manav Bilakhia
 */

@RunWith(JUnit4.class)

public class CoordinatePointTest
{
    private  CoordinatePoint point1;
    private  CoordinatePoint point2;

    @Before
    public void setUp()
    {
        point1 = new CoordinatePoint(1, 2);
        point2 = new CoordinatePoint(2, 1);
    }

    @After
    public void tearDown()
    {
        point1 = null;
        point2 = null;
    }

    // 1. getRow()
    // 2. getCol()
    // 3. equals()

    @Test

    public void testGetRow()
    {
        assertEquals(1, point1.getRow());
        assertEquals(2, point2.getRow());
    }

    @Test

    public void testGetCol()
    {
        assertEquals(2, point1.getCol());
        assertEquals(1, point2.getCol());
    }

    @Test

    public void testEquals()
    {
        assertTrue(point1.equals(point1));
        assertTrue(point2.equals(point2));
        assertFalse(point1.equals(point2));
        assertFalse(point2.equals(point1));
    }
}