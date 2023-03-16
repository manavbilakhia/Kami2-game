package edu.union.adt.game.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import edu.union.maingame.game.board_gui.BoardPainterShape;
import edu.union.maingame.game.file_io.BoardFactory;
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
 * This class creates a mock object of the BoardPointerShape 
 * class which implements the BoardObserver interface to checks how
 *  many times the update method is called.
 * 
 * @author Manav Bilakhia, John Daly
 * @version 1
 */
 
@RunWith(JUnit4.class)

public class BoardPainterShapeTest
{
    /**
     * Class which represents a mock board observer to test how many times update()
     * is called in different situations.
     */
    private class mockBoardObserver implements BoardObserver{
        private int expectedNumOfUpdates;

        private int currentNumOfUpdates;

        private mockBoardObserver(int expectedNumUpdates){
            this.expectedNumOfUpdates = expectedNumUpdates;
            this.currentNumOfUpdates = 0;
        }

        public void update(){
            this.currentNumOfUpdates++;

            if (this.currentNumOfUpdates > this.expectedNumOfUpdates){
                fail("Exceeded expected update count!");
            }
        }

        public boolean verify(){
            return (this.currentNumOfUpdates == this.expectedNumOfUpdates);
        }

    }
    private GameBoard board1;
    private mockBoardObserver observer1;

    @Before
    public void setUp() {
        board1 = BoardFactory.boardFactory(5, 5, "hint", 5,
                new String[]{"red", "blue", "green", "purple", "tan", "orange", "gray"}, "level1",
                "square", true);

    }

    @After
    public void tearDown()
    {
        board1 = null;
        observer1 = null;

    }

    @Test
    public void testUpdate()
    {
        observer1 = new mockBoardObserver(3);


        board1.addObserver(observer1);

        board1.makeMove(0,0);
        board1.makeMove(0,1);
        board1.makeMove(0,2);


        assertTrue(observer1.verify());
    }
}