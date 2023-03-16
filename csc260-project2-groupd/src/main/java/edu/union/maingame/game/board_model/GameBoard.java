package edu.union.maingame.game.board_model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Stack;

/**
 * This class models a Kami game board which holds the state of the board and provides
 * functions to modify the board and play game moves as the game is played.
 *
 * @author John Daly, Manav Bilakhia
 * @version 1
 */
public abstract class GameBoard {
    //integer which stores the number of rows
    private int rows;
    //integer which stores the number of columns
    private int columns;

    private int numMoves;

    private int maxMoves;

    private String[][] board;

    private String[][] boardCopy;

    private String[] palette;

    private int paletteSelect = 0;

    private boolean isEditing;

    private String hint;

    private String levelName;

    private ArrayList<BoardObserver> observers;

    private String boardType;

    private Stack<ArrayList<Object>> moveStack;


    /**
     * Create an empty Board.
     */
    public GameBoard(int numRows, int numCols, String hint, int numOfMoves, String[] palette, String levelName,
                     String type, boolean isEditing) {
        this.rows = numRows;
        this.columns = numCols;

        this.board = new String [numRows][numCols];
        this.hint = hint;
        this.boardType = type;

        this.palette = palette;

        this.observers = new ArrayList<BoardObserver>();
        this.numMoves = numOfMoves;

        this.levelName = levelName;

        this.maxMoves = this.numMoves;

        this.fillBoard("gray");

        this.isEditing = isEditing;

        this.moveStack = new Stack<>();


    }

    /**
     * Method to return the status of the board, if it's being edited or not.
     * @return true if board is being edited, false otherwise.
     */
    public boolean isEditing() {
        return isEditing;
    }

    /**
     * Returns this board's type of shape as a string.
     * @return String containing this board's type
     */
    public String getBoardType(){
        return this.boardType;
    }

    /**
     * Method which adds an observer to this board's list of observers
     * @param bi The observer to add
     */
    public void addObserver(BoardObserver bi){
        if (bi != null) {
            this.observers.add(bi);
        }
    }

    /**
     * Method which removes an observer from this board's list of observers
     * @param bi The observer to remove
     */
    public void removeObserver(BoardObserver bi){
        if (bi != null) {
            this.observers.remove(bi);
        }
    }

    /**
     * Method which notifies all observers connected to the board of changes
     */
    public void notifyObservers(){
        for (BoardObserver bi: this.observers){
            bi.update();
        }
    }

    /** 
     * Method which returns the array of observers of the board
     * @return The array of observers of the board
     */
    public ArrayList<BoardObserver> getObservers(){
        return this.observers;
    }

    /**
     * Fills the board with a specified String
     * @param toFill The value to fill the board with
     */
    public void fillBoard(String toFill){
        for (int i = 0; i < this.rows; i++){
            for (int j = 0; j < this.columns; j++){
                this.setCell(toFill, i,j);
            }
        }
    }

    /**
     * Method which allows the player to make a move on a board. based on the x and y coordinates of the mouse click.
     * This method will perform a flood fill move if the game is being played as
     * a game, and will perform a place single cell move if the board is being edited
     * @param mouseX the x coordinate of the mouse click
     * @param mouseY x the x coordinate of the mouse click
     */
    public void makeMove(double mouseX, double mouseY){
        int row = this.getRowFromCoordinate(mouseY);
        int col = this.getColumnFromCoordinate(mouseX);

        if (row >= 0 && row < this.getRows()) {
            if (col >= 0 && col < this.getColumns()) {
                this.makeMove(row, col);
            }
        }
    }

    /**
     * Method which allows the player to make a move on a board based on the row and column indices of this
     * object's board.
     * This method will perform a flood fill move if the game is being played as
     * a game, and will perform a place single cell move if the board is being edited
     * @param row the integer row coordinate of the row to be played
     * @param col the integer column coordinate of the column to be played
     */
    public void makeMove(int row, int col){
        if (!this.board[row][col].equals("void")  || this.isEditing()) {
            this.decrementMoves();
            if (this.isEditing) {
                this.setCell(this.getSelectedColor(), row, col);
            } else {
                this.floodFill(this.getSelectedColor(), new CoordinatePoint(row, col));
            }

            //Adding the move to our move stack
            ArrayList<Object> move = new ArrayList<>();
            move.add(this.getSelectedColor());
            move.add(row);
            move.add(col);
            this.moveStack.push(move);
        }
    }

       /**
     * Method which allows the player to make a move on a board based on the row, column, and color indices of this
     * object's board.
     * This method will perform a flood fill move if the game is being played as
     * a game, and will perform a place single cell move if the board is being edited
     * @param row the integer row coordinate of the row to be played
     * @param col the integer column coordinate of the column to be played
     * @Param color the string value of the color that the move should use
     */
    public void makeMove(int row, int col, String color){
        if (!this.board[row][col].equals("void")  || this.isEditing()) {
            this.decrementMoves();
            if (this.isEditing) {
                this.setCell(color, row, col);
            } else {
                this.floodFill(color, new CoordinatePoint(row, col));
            }
        }
    }

    /**
     * Method to translate mouse coordinate into row coordinate for board.
     * @param rCoord the mouse coordinate to translate to a row coordinate
     * @return an integer row coordinate of the board
     */
    public abstract int getRowFromCoordinate(double rCoord);

    /**
     * Method to translate mouse coordinate into column coordinate for board.
     * @param cCoord the mouse coordinate to translate to a column coordinate
     * @return an integer column coordinate of the board
     */
    public abstract int getColumnFromCoordinate(double cCoord);

    /**
     * Sets a specific cell to a value
     * @param toSet the value to set the cell to
     * @param x the x coordinate of the cell to set
     * @param y the y coordinate of the cell to set
     */
    public void setCell(String toSet, int x, int y){
        if (x >= 0 && x < this.rows){
            if (y >= 0 && y < this.columns){
                this.board[x][y] = toSet;
                this.notifyObservers();
            }
        }
    }

    /**
     * Method which returns the color of the cell at the specified coordinates
     * @param x the x coordinate of the cell to get the color of
     * @param y the y coordinate of the cell to get the color of
     * @return the color of the cell at the specified coordinates
     */
    public String getCellColor(int x, int y){
        if (x >= 0 && x < this.rows){
            if (y >= 0 && y < this.columns){
                return this.board[x][y];
            }
        }
        return null;
    }

    /**
     * Method to get the neighboring cells of a cell at a specified coordinate point.
     * @param current a coordinatePoint containing the coordinates of the cell being interrogated for its neighbors.
     * @return a collection of coordinatePoints of this cell's neighbors
     */
    public abstract Collection<CoordinatePoint> getNeighbors(CoordinatePoint current);

    /**
     * Method to make a shape at a specified row and column coordinate
     * @param row the row where the shape will appear
     * @param col the column where the shape will appear
     * @return a Shape object representing the shape
     */
    public abstract Shape makeShape(int row, int col);

    /**
     * Flood fill algorithm which takes a value and a start point and
     * flood fills the area connected to the start point with the
     * desired value
     * @param toFill the desired item to fill the array with
     * @param startCoords the coordinate object containing the start point for the floodfill
     */
    public void floodFill(String toFill, CoordinatePoint startCoords){

        ArrayDeque<CoordinatePoint> search = new ArrayDeque<>();
        ArrayList<CoordinatePoint> found = new ArrayList<>();
        Collection<CoordinatePoint> temp;

        search.add(startCoords);

        String checker = this.board[startCoords.getRow()][startCoords.getCol()];

        while (!search.isEmpty()){
            CoordinatePoint current = search.poll();
            found.add(current);

            temp = this.getNeighbors(current);

            for (CoordinatePoint c: temp){
                if (!search.contains(c) && !found.contains(c)) {
                    if (c.getRow() >= 0 && c.getRow() < this.rows) {
                        if (c.getCol() >= 0 && c.getCol() < this.columns) {
                            if (this.board[c.getRow()][c.getCol()].equals(checker)) {
                                search.add(c);
                            }
                        }
                    }
                }
            }

            temp.clear();

        }

        for (CoordinatePoint e: found){
            this.board[e.getRow()][e.getCol()] = toFill;
        }
        this.notifyObservers();

    }

    /**
     * Detects and returns if the board has been won or not, will always return false if the board is edited.
     * @return true if the game has been won, false otherwise, and always false when game is edited.
     */
    public boolean isWon(){
        if (this.isEditing){
            return false;
        }
        String sentinel = this.board[0][0];
        for (int i = 0; i < this.rows; i++){
            for (int j = 0; j < this.columns; j++){
                if (!this.board[i][j].equals(sentinel) && !this.board[i][j].equals("void")){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Detects and returns if there are no moves left
     * @return true if the game has no moves left, false otherwise
     */
    public boolean noMovesLeft(){
        return (this.numMoves == 0);
    }

    /**
     * Decrements the number of moves left, refuses to decrement past 0 or if won or if the board is being edited.
     */
    public void decrementMoves(){
        if (this.numMoves != 0 && !this.isWon() && !this.isEditing) {
            this.numMoves -= 1;
        }
    }

    public void incrementMoves(){
        this.numMoves += 1;
    }
    /**
     * Resets the number of moves left to the max.
     */
    public void resetMoves(){
        this.numMoves = this.maxMoves;
        this.notifyObservers();
    }

    /**
     * Method for getting the hint for this board
     * @return string containing the board's hint text
     */
    public String getHint(){
        return this.hint;
    }

    /**
     * Method for getting the board this class contains
     * @return a 2d array of strings representing the board
     */
    public String[][] getBoard(){
        return this.board;
    }

    /**
     * Method for setting this game's board to a new board, useful
     * for initializing boards.
     * @param toSet
     */
    public void setBoard(String[][] toSet){
        this.board = toSet;
        this.boardCopy = boardCopy(toSet);
        this.notifyObservers();
    }

    /**
     * Function to copy a 2d Array of strings
     * @param src the 2d array to be copied
     * @return a copy of the input array
     */
    private static String[][] boardCopy(String[][] src) {
        if (src == null) {
            return null;
        }

        String [][] copy = new String[src.length][];

        for (int i = 0; i < src.length; i++) {
            copy[i] = new String[src[i].length];
            System.arraycopy(src[i], 0, copy[i], 0, src[i].length);
        }

        return copy;
    }



    /**
     * Method to undo the last move on the current board.
     * Pops the last move off the stack, resets the board, then replays
     * the rest of the moves on the Stack
     */
    public void undoMove(){

        if(!this.moveStack.isEmpty()){
            this.resetBoard();

            this.moveStack.pop();

            for(ArrayList<Object> move : moveStack){
                int row = (int) move.get(1);
                int col = (int) move.get(2);
                String color = (String) move.get(0);
                this.makeMove(row, col, color);
            }

            this.notifyObservers();
        }
    }

    /**
     * Method to reset this current board and its move counter to its initial state.
     */
    public void resetBoard(){
        this.board = boardCopy(this.boardCopy);
        this.resetMoves();
        this.notifyObservers();
        this.paletteSelect = 0;
    }


    /**
     * Method for getting the current selected color
     * @return String representing this board's currently selected color
     */
    public String getSelectedColor(){
        return this.palette[this.paletteSelect];
    }

    /**
     * Method for getting this board's number of rows
     * @return integer number of rows in this board
     */
    public int getRows(){
        return this.rows;
    }

    /**
     * Method for getting this board's number of columns
     * @return integer number of columns in this board
     */
    public int getColumns(){
        return this.columns;
    }

    /**
     * Method for getting the color palette of this board
     * @return 1 dimensional array of strings containing the color palette
     */
    public String[] getPalette(){
        return this.palette;
    }

    /**
     * method to change the palette color, auto switches to next color
     */
    public void changeSelectedColor(){
        this.paletteSelect += 1;

        if (this.paletteSelect == this.palette.length){
            this.paletteSelect = 0;
        }
    }

    /**
     * Method to return the number of moves left.
     * @return integer number of moves the player has left
     */
    public int getNumMovesLeft(){
        return this.numMoves;
    }

    /**
     * Method to return the maximum number of moves
     * @return integer number which represents the maximum number of moves
     */
    public int getMaxMoves(){
        return this.maxMoves;
    }

    /**
     * Method to return this level's name
     * @return String containing the level's name
     */
    public String getLevelName(){
        return this.levelName;
    }

    /**
     * Method to return the pieces contained in this board as a linear Iterable
     * @return an Iterable containing all pieces contained in this board in row order
     */
    public Iterable<String> getAllPiecesInBoard(){
        ArrayList<String> toReturn = new ArrayList<>();
        for (int i = 0; i < this.rows; i++){
            for (int j = 0; j < this.columns; j++){
                toReturn.add(this.board[i][j]);
            }
        }
        return toReturn;
    }



    public String toString(){
        String toReturn = "";
        for (int i = 0; i < this.rows; i++){
            for (int j = 0; j < this.columns; j++){
                toReturn += " ";
                toReturn += this.board[i][j];
            }
            toReturn += "\n";
        }

        return toReturn;

    }

}
