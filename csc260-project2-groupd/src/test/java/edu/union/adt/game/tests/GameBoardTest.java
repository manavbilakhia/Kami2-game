package edu.union.adt.game.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

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
 * This class is a test suite for the GameBoard class.
 * 
 * @author Manav Bilakhia, John Daly
 */



@RunWith(JUnit4.class)
public class GameBoardTest
{
    private GameBoard board1;
    private GameBoard board2;
    private GameBoard board3;
    private BoardObserver observer1;
    private BoardObserver observer2;
    private BoardObserver observer3;
    private CoordinatePoint point1;
    

    @Before
    public void setUp() {
        board1 = BoardFactory.boardFactory(5, 5, "hint", 5,
                new String[]{"red", "blue", "green", "purple", "tan", "orange", "gray"}, "level1",
                "square", false);
        board2 = BoardFactory.boardFactory(5, 5, "hint", 5,
                new String[]{"red", "blue", "green", "purple", "tan", "orange", "gray"}, "level1",
                "square", false);
        board3 = BoardFactory.boardFactory(5, 5, "hint", 5,
                new String[]{"red", "blue", "green", "purple", "tan", "orange", "gray"}, "level1",
                "square", true);
    }

    @After
    public void tearDown()
    {
        board1 = null;
        board2 = null;
        board3 = null;
        observer1 = null;
        observer2 = null;
        observer3 = null;
    }

    @Test
    public void testFillBoard()
    {
        board1.fillBoard("red");
        String[][] board = board1.getBoard();
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                assertEquals("red", board[i][j]);
            }
        }
    }

    @Test

    public void testMakeMove()
    {
        board1.fillBoard("red");
        board1.makeMove(0, 0);
        String[][] board = board1.getBoard();
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                assertEquals("red", board[i][j]);
            }
        }
    }

    @Test

    public void testIsWon()
    {
        board1.fillBoard("red");
        board1.makeMove(0, 0);
        assertTrue(board1.isWon());
    }

    @Test

    public void testIsNotWon()
    {
        board1.fillBoard("red");
        board1.makeMove(0, 0);
        board1.makeMove(0, 1);
        assertTrue(board1.isWon()); 
    }

    @Test

    public void testNoMovesLeft()
    {
        board1.fillBoard("red");
        board1.setCell("blue", 0, 0);
        assertEquals(board1.getMaxMoves(), board1.getNumMovesLeft());
        board1.makeMove(0, 1);
        board1.makeMove(0, 2);
        board1.makeMove(0, 3);
        board1.makeMove(0, 4);
        board1.makeMove(1, 0);
        board1.makeMove(1, 1);
        board1.makeMove(1, 2);
        board1.makeMove(1, 3);
        board1.makeMove(1, 4);
        board1.makeMove(2, 0);
        board1.makeMove(2, 1);
        board1.makeMove(2, 2);
        board1.makeMove(2, 3);
        board1.makeMove(2, 4);
        board1.makeMove(3, 0);
        board1.makeMove(3, 1);
        board1.makeMove(3, 2);
        board1.makeMove(3, 3);
        board1.makeMove(3, 4);
        assertEquals(0, board1.getNumMovesLeft());
        assertTrue(board1.noMovesLeft());
        assertEquals(0, board1.getNumMovesLeft()); 
        assertTrue(board1.noMovesLeft());
        board1.makeMove(4, 0);
        board1.makeMove(4, 1);
        board1.makeMove(4, 2);
        board1.makeMove(4, 3);
        board1.makeMove(4, 4);
        assertTrue(board1.noMovesLeft());
    }

    @Test

    public void testGetNumMovesLeft()
    {
        board1.fillBoard("red");
        board1.setCell("blue", 0, 0);
        assertEquals(5, board1.getNumMovesLeft());
        board1.makeMove(1, 0);
        assertEquals(4, board1.getNumMovesLeft()); 
        board1.resetMoves();
        assertEquals(5, board1.getNumMovesLeft());
    }

    @Test

    public void testAddObserver()
    {

        observer1 = new BoardPainterShape(board1, null, null, "Test1");
        observer2 = new BoardPainterShape(board1, null, null, "Test2");
        observer3 = new BoardPainterShape(board1, null, null, "Test3");
        board1.addObserver(observer1);
        board1.addObserver(observer2);
        board1.addObserver(observer3);
        assertEquals(3, board1.getObservers().size()); 
    }

    @Test

    public void testRemoveObserver()
    {
        observer1 = new BoardPainterShape(board1, null, null, "Test1");
        observer2 = new BoardPainterShape(board1, null, null, "Test2");
        observer3 = new BoardPainterShape(board1, null, null, "Test3");
        board1.addObserver(observer1);
        board1.addObserver(observer2);
        board1.addObserver(observer3);
        board1.removeObserver(observer2);
        assertEquals(2, board1.getObservers().size()); 
    }

    @Test

    public void testFloodFill()
    {
        board1.fillBoard("red");
        board1.makeMove(0, 0);
        board1.makeMove(0, 1);
        board1.makeMove(0, 2);
        board1.makeMove(0, 3);
        board1.makeMove(0, 4);
        board1.makeMove(1, 0);
        board1.makeMove(1, 1);
        board1.makeMove(1, 2);
        board1.makeMove(1, 3);
        board1.makeMove(1, 4);
        board1.makeMove(2, 0);
        board1.makeMove(2, 1);
        board1.makeMove(2, 2);
        board1.makeMove(2, 3);
        board1.makeMove(2, 4);
        board1.makeMove(3, 0);
        board1.makeMove(3, 1);
        board1.makeMove(3, 2);
        board1.makeMove(3, 3);
        board1.makeMove(3, 4);
        board1.makeMove(4, 0);
        board1.makeMove(4, 1);
        board1.makeMove(4, 2);
        board1.makeMove(4, 3);
        board1.makeMove(4, 4);
        point1 = new CoordinatePoint(0, 0);
        board1.floodFill("blue",point1);
        assertEquals("blue", board1.getCellColor(0,0));
        assertEquals("blue", board1.getCellColor(0,1));
        assertEquals("blue", board1.getCellColor(0,2));
        assertEquals("blue", board1.getCellColor(0,3));
        assertEquals("blue", board1.getCellColor(0,4));
        assertEquals("blue", board1.getCellColor(1,0));
    }

    @Test

    public void testGetSelectedColor()
    {
        assertEquals("red", board1.getSelectedColor());
    }

    @Test

    public void testGetHint()
    {
        assertEquals("hint", board1.getHint());
    }

    @Test

    public void testGetLevelName()
    {
        assertEquals("level1", board1.getLevelName());
    }

    @Test
    public void testGetRowsColumns()
    {
        assertEquals(5, board1.getRows());
        assertEquals(5, board1.getColumns());
    }
    @Test
    public void testGetPalette()
    {
        String[] arr = {"red", "blue", "green", "purple", "tan", "orange", "gray"};
        assertEquals(arr, board1.getPalette());
    }

    @Test

    public void testGetCellColor()
    {
        board1.fillBoard("red");
        board1.setCell("blue", 0, 0);
        assertEquals("blue", board1.getCellColor(0, 0));
        assertEquals("red", board1.getCellColor(0, 1));
    }

    @Test

    public void testResetBoard()
    {
        board2.fillBoard("red");
        String[][] arr = board2.getBoard(); 

        board1.setBoard(arr);
        board1.resetBoard();
        assertEquals("red", board1.getCellColor(0, 0));
        assertEquals("red", board1.getCellColor(0, 1));
    }

    @Test

    public void testGetAllPiecesInBoard()
    {
        board1.fillBoard("red");
        board1.setCell("blue", 0, 0);
        board1.setCell("blue", 0, 1);
        board1.setCell("blue", 0, 2);
        board1.setCell("blue", 0, 3);
        board1.setCell("blue", 0, 4);
        board1.setCell("blue", 1, 0);
        board1.setCell("blue", 1, 1);
        board1.setCell("blue", 1, 2);
        board1.setCell("blue", 1, 3);
        board1.setCell("blue", 1, 4);
        board1.setCell("blue", 2, 0);
        board1.setCell("blue", 2, 1);
        board1.setCell("blue", 2, 2);
        board1.setCell("blue", 2, 3);
        board1.setCell("blue", 2, 4);
        board1.setCell("blue", 3, 0);
        board1.setCell("blue", 3, 1);
        board1.setCell("blue", 3, 2);
        board1.setCell("blue", 3, 3);
        board1.setCell("blue", 3, 4);
        board1.setCell("blue", 4, 0);
        board1.setCell("blue", 4, 1);
        board1.setCell("blue", 4, 2);
        board1.setCell("blue", 4, 3);
        board1.setCell("blue", 4, 4);
        Iterable data = board1.getAllPiecesInBoard();
        int counter = 0;
        for (Object i : data)
        {
            counter++;
        }
        assertEquals(25, counter);

    }


    @Test
    public void testIsEditing()
    {
        assertFalse(board1.isEditing());
        assertTrue(board3.isEditing());
    }

    
}

