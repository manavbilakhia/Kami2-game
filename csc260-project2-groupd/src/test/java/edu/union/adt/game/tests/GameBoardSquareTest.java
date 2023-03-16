package edu.union.adt.game.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import edu.union.maingame.game.board_gui.BoardPainterShape;
import edu.union.maingame.game.file_io.BoardFactory;
import edu.union.maingame.game.board_model.GameBoardSquare;
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
 * This class creates a mock object of the GameBoardSquare
 * class which extends the GameBoard 
 * 
 * @author Manav Bilakhia,
 * @version 1
 */

@RunWith(JUnit4.class)

public class GameBoardSquareTest
{
    private class mockGameBoardSquare
    {
        private int expectedRows;
        private int expectedCols;
        private int actualRows;
        private int actualCols;

        private mockGameBoardSquare(int expectedRows, int expectedCols)
        {
            this.expectedRows = expectedRows;
            this.expectedCols = expectedCols;
            this.actualRows = gameBoardSquare.getRows();
            this.actualCols = gameBoardSquare.getColumns();
        }

        public boolean verify()
        {   
            return (this.expectedRows == this.actualRows) && (this.expectedCols == this.actualCols);
        }

    }
    private GameBoardSquare gameBoardSquare;
    private mockGameBoardSquare mockGameBoardSquare;

    @Before
    public void setUp()
    {
        this.gameBoardSquare = new GameBoardSquare(10, 10, "hint", 5,
        new String[]{"red", "blue", "green", "purple", "tan", "orange", "gray"}, "level1",
        "square", true);
        this.mockGameBoardSquare = new mockGameBoardSquare(10, 10);
    }

    @After
    public void tearDown()
    {
        this.gameBoardSquare = null;
        this.mockGameBoardSquare = null;
    }

    @Test
    public void testGameBoardSquare()
    {
        assertTrue(this.mockGameBoardSquare.verify());
    }

    
}
 