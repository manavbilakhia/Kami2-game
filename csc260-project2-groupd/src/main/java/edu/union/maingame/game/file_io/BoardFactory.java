package edu.union.maingame.game.file_io;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

import edu.union.maingame.game.board_model.GameBoard;
import edu.union.maingame.game.board_model.GameBoardSquare;

/**
 * This class provides a parameterized factory method for creating different kinds of GameBoards
 *
 * @author John Daly
 */
public class BoardFactory {

    /**
     * Factory method which returns a GameBoard of a type specified by the type parameter
     * @param numRows the number of rows desired in the board
     * @param numCols the number of columns desired in the board
     * @param hint the String hint in this board
     * @param numOfMoves the number of moves the user is allowed on this board
     * @param palette the palette of colors this board will have
     * @param levelName the name of the level that this board will represent
     * @param type the type of board that will be created
     * @param isEditing if this board will be created with editing on
     * @return a GameBoard of the correct type specified and with all of these parameters in place
     */
    public static GameBoard boardFactory(int numRows, int numCols, String hint, int numOfMoves, String[] palette,
                                         String levelName, String type, boolean isEditing){
        if (type.equals("square")){
            return new GameBoardSquare(numRows, numCols, hint, numOfMoves, palette, levelName, type, isEditing);
        }
        else {
            return new GameBoardSquare(numRows, numCols, hint, numOfMoves, palette, levelName, type, isEditing);
        }
    }

}