package edu.union.maingame.game.board_model;

/**
 * A small class for defining a set of coordinates for easier use in floodfill
 * algorithms
 * @author John Daly
 * @version 1
 */
public class CoordinatePoint {

    private int row;

    private int col;

    /**
     * Creates a coordinate object
     * @param setRow the row coordinate to store
     * @param setCol the column coordinate to store
     */
    public CoordinatePoint(int setRow, int setCol){
        this.row = setRow;
        this.col = setCol;
    }

    /**
     * Method for retrieving this coordinate's row
     * @return integer row coordinate
     */
    public int getRow(){
        return this.row;
    }

    /**
     * Method for retrieving this coordinate's y
     * @return integer y coordinate
     */
    public int getCol(){
        return this.col;
    }

    public boolean equals(Object other) {
        if (this == other){
            return true;
        }

        if (other == null || this.getClass() != other.getClass()){
            return false;
        }
        //Cast is ok because previous statement determines getClass() matches.
        @SuppressWarnings("unchecked")
        CoordinatePoint otherCoord = (CoordinatePoint) other;

        if (this.row == otherCoord.getRow() && this.col == otherCoord.getCol()){
            return true;
        }
        return false;
    }

}
