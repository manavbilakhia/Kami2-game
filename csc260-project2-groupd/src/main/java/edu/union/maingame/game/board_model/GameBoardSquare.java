package edu.union.maingame.game.board_model;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Collection;

/**
 * This class models a Kami game board which holds the state of the board and provides
 * functions to modify the board and play game moves as the game is played.
 *
 * @author John Daly, Manav Bilakhia
 * @version 1
 */
public class GameBoardSquare extends GameBoard{

    private static final int buttonWidth = 20;


    /**
     * Create an empty Board.
     */
    public GameBoardSquare(int numRows, int numCols, String hint, int numOfMoves, String[] palette, String levelName,
                     String type, boolean isEditing) {
        super(numRows, numCols, hint, numOfMoves, palette, levelName, type, isEditing);
        this.fillBoard("gray");

    }

    /**
     * Method to translate mouse coordinate into row coordinate for board.
     * @param rCoord the mouse coordinate to translate to a row coordinate
     * @return an integer row coordinate of the board
     */
    public int getRowFromCoordinate(double rCoord){
        return (int) Math.floor(rCoord/buttonWidth);
    }

    /**
     * Method to translate mouse coordinate into column coordinate for board.
     * @param cCoord the mouse coordinate to translate to a column coordinate
     * @return an integer column coordinate of the board
     */
    public int getColumnFromCoordinate(double cCoord){
        return (int) Math.floor(cCoord/buttonWidth);
    }


    public Collection<CoordinatePoint> getNeighbors(CoordinatePoint current){
        ArrayList<CoordinatePoint> toReturn = new ArrayList<>();
        toReturn.add(new CoordinatePoint(current.getRow() - 1, current.getCol()));
        toReturn.add(new CoordinatePoint(current.getRow() + 1, current.getCol()));
        toReturn.add(new CoordinatePoint(current.getRow(), current.getCol() - 1));
        toReturn.add(new CoordinatePoint(current.getRow(), current.getCol() + 1));
        return toReturn;
    }

    public Shape makeShape(int row, int col){
        Rectangle rect = new Rectangle(row * buttonWidth, col * buttonWidth, buttonWidth, buttonWidth);
        return rect;
    }



}
