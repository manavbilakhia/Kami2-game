package edu.union.maingame.game.board_gui;
import  edu.union.maingame.game.board_model.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import edu.union.maingame.game.file_io.BoardFactory;
import edu.union.maingame.game.file_io.BoardIO;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * This class serves as the main controller and viewer for Kami game boards.
 * This is where user interaction is processed and sent to the game board
 * for game logic, and this is where game board files will be loaded into to
 * play the game.
 *
 * @author John Daly
 */
public class BoardPainterShape implements BoardObserver {

    private GameBoard game;
    private Stage gameStage;
    private Group boardPainter = new Group();
    private BorderPane rootScene = new BorderPane();

    private Label movesDisplay;
    private String styleSheet;
    private AudioClip click;
    private AudioClip victory;

    private Label winLabel;

    private Button goToEdit;
    private Button loadLevel;
    private Button saveLevel;
    private Button undoButton;


    private static final HashMap<String,Color> colorTranslationTable = new HashMap<String, Color>(){{
        put("blue", Color.SKYBLUE);
        put("red", Color.RED);
        put("purple", Color.PURPLE);
        put("orange", Color.ORANGE);
        put("green", Color.GREEN);
        put("gray", Color.GRAY);
        put("tan", Color.MOCCASIN);
        put("NA", Color.BLACK);
        put("void", Color.TRANSPARENT);
    }};

    /**
     * Constructor for a board painter which uses shapes to draw the game.
     * @param game a GameBoard to be displayed and interacted with.
     * @param clicker an AudioClip to play when the user places a piece.
     * @param victory an AudioClip to play when the user has won the game.
     * @param styleSheet a StyleSheet string to use with the game
     */
    public BoardPainterShape(GameBoard game, AudioClip clicker, AudioClip victory, String styleSheet){
        this.game = game;
        this.click = clicker;
        this.victory = victory;
        this.styleSheet = styleSheet;

    }

    /**
     * Method to translate a Color into a usable hexadecimal RGB code.
     * @param color the Color to be translated
     * @return A String containing hexadecimal RGB values in the form #XXYYZZ
     */
    private String toRGBCode( Color color )
    {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }

    /**
     * Method to set up this BoardPainterShape's file loading button.
     */
    private void setFileLoadButton(){
        this.loadLevel = new Button("Load Level");
        this.loadLevel.setId("LoadButton");
        BoardPainterShape mainBP = this;
        this.loadLevel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ChoiceDialog<String> loadCD = new ChoiceDialog<>("Load intro board",
                        BoardIO.getListOfBoards());
                loadCD.setTitle("Choose which board to load");
                loadCD.setGraphic(null);
                loadCD.setHeaderText("");
                loadCD.showAndWait();
                GameBoard board;
                if (loadCD.getSelectedItem().equals("Load intro board")){
                    File introBoardFile = new File(System.getProperty("user.dir") +
                            "/src/main/java/edu/union/maingame/game/levels/kfxintro.board");
                    board = BoardIO.loadBoard(introBoardFile, false);
                }else{
                    File loader = new File(System.getProperty("user.dir") +
                            "/src/main/java/edu/union/maingame/game/levels/" + loadCD.getSelectedItem());
                    board = BoardIO.loadBoard(loader, false);
                }
                mainBP.loadBoard(board);
            }
        });
        this.loadLevel.setPrefWidth(150);
    }

    /**
     * Method to set up this BoardPainterShape's file saving button.
     */
    private void setFileSaveButton(){
        this.saveLevel = new Button("Save Level");
        BoardPainterShape mainBP = this;
        this.saveLevel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TextInputDialog td = new TextInputDialog();
                td.setGraphic(null);
                td.setHeaderText("");
                td.setTitle("Name the level:");
                td.showAndWait();
                String name = td.getResult();
                td.getEditor().clear();
                td.setTitle("Give a hint:");
                td.showAndWait();
                String hint = td.getResult();
                td.getEditor().clear();
                td.setTitle("Number of moves:");
                td.showAndWait();
                ChoiceDialog<String> loadCD = new ChoiceDialog<>("board format",
                        "JSON");
                loadCD.setTitle("Choose which format to save in");
                loadCD.setGraphic(null);
                loadCD.setHeaderText("");
                loadCD.showAndWait();
                int numMoves = Integer.parseInt(td.getResult());

                GameBoard gametoSave = mainBP.getGame();

                GameBoard saver = BoardFactory.boardFactory(gametoSave.getRows(), gametoSave.getColumns(), hint,
                        numMoves, gametoSave.getPalette(), name, gametoSave.getBoardType(),false);
                saver.setBoard(gametoSave.getBoard());
                BoardIO.saveBoard(saver,name, loadCD.getSelectedItem());

            }
        });
    }

    /**
     * Method to return a reference to this BoardPainterShape's current GameBoard
     * @return this BoardPainterShape's current GameBoard
     */
    public GameBoard getGame(){
        return this.game;
    }


    /**
     * Method that sets up the undo button in the game.
     */
    private void setUndoButton(){
        this.undoButton = new Button("Undo Move");
        BoardPainterShape thisBPS = this;

        this.undoButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                thisBPS.undoMove();
            }
        });
    }


    /**
     * Method to undo the last move that was made, calls upon the logic found in
     * the GameBoard to do this.
     */
    public void undoMove(){

        if(this.game.getNumMovesLeft() != this.game.getMaxMoves() || this.game.isEditing()){
            this.winLabel.setVisible(false);
            this.game.undoMove();
            this.setUpControls();
        }
    }


    /**
     * Method to set up this BoardPainterShape's file Editing button.
     */
    private void setEditButton(){
        BoardPainterShape mainBP = this;
        this.goToEdit = new Button("Edit Level");
        this.goToEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ChoiceDialog<String> editCD = new ChoiceDialog<>("Edit default board",
                        BoardIO.getListOfBoards());
                editCD.setTitle("Choose which board to edit");
                editCD.setGraphic(null);
                editCD.setHeaderText("");
                editCD.showAndWait();
                GameBoard defaultBoard;
                if (editCD.getSelectedItem().equals("Edit default board")){
                    defaultBoard = BoardIO.generateDefaultBoard();
                }else{
                    File loader = new File(System.getProperty("user.dir") +
                            "/src/main/java/edu/union/maingame/game/levels/" + editCD.getSelectedItem());
                    defaultBoard = BoardIO.loadBoard(loader, true);
                }
                mainBP.loadBoard(defaultBoard);
            }
        });
    }

    /**
     * Method to set up this BoardPainterShape's view of the game. Paints rectangles on a canvas, but this could
     * be changed to draw whatever shape we want.
     */
    private void setUpCellView(){
        this.boardPainter.getChildren().clear();

        String[][] tempBoard = this.game.getBoard();

        for (int i = 0; i < this.game.getRows(); i++){
            for (int j = 0; j < this.game.getColumns(); j++){
                Shape s = this.game.makeShape(j,i);
                s.setFill(colorTranslationTable.get(tempBoard[i][j]));
                this.boardPainter.getChildren().add(s);

            }
        }
    }

    /**
     * Method to set up the controls and view of the game, calls upon the other setup methods to
     * set up its components.
     */
    private void setUpControls(){
        this.rootScene.getChildren().clear();

        this.winLabel = new Label("");
        this.winLabel.setId("WinLabel");

        this.winLabel.setVisible(false);


        this.rootScene.setCenter(this.boardPainter);
        this.rootScene.setBottom(this.setUpBottomPanel());
        this.rootScene.setRight(this.setUpRightPanel());
        this.rootScene.setLeft(this.setUpLeftPanel());

        if (!this.game.isEditing()){
            this.saveLevel.setVisible(false);
            this.movesDisplay.setVisible(true);
            this.undoButton.setVisible(true);
        }
        else{
            this.saveLevel.setVisible(true);
            this.movesDisplay.setVisible(false);
            this.undoButton.setVisible(false);
        }

        BorderPane.setAlignment(this.winLabel, Pos.CENTER);
        this.rootScene.setTop(this.winLabel);
    }

    /**
     * Method to set up the controls to be put on the left side of the view panel.
     * @return a vertical FlowPane that should be added to the left side of the root border pane
     */
    private FlowPane setUpLeftPanel(){

        Button hintButton = new Button("Get a hint");
        hintButton.setPrefWidth(200);
        //BoardPainter setUp = this;
        Label hintLabel = new Label("hint:\n" + this.game.getHint());
        hintLabel.setVisible(false);
        hintLabel.setPrefWidth(200);
        hintLabel.setWrapText(true);
        hintLabel.setId("HintLabel");
        hintButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hintLabel.setVisible(true);
            }
        });

        if (this.game.isEditing()){
            hintButton.setVisible(false);
        }

        FlowPane fpLeft = new FlowPane(Orientation.VERTICAL);
        fpLeft.setVgap(25);


        fpLeft.getChildren().addAll(hintButton, hintLabel);

        return fpLeft;
    }
    /**
     * Method to set up the controls to be put on the bottom of the view panel.
     * @return a horizontal FlowPane that should be added to the bottom of the root border pane
     */
    private FlowPane setUpBottomPanel(){
        GameBoard tempGame = this.game;

        FlowPane fp = new FlowPane();

        Button changeColor = new Button("Change Colors\nCurrent color: " + tempGame.getSelectedColor());
        changeColor.setPrefWidth(250);

        changeColor.setId("ColorChangeButton");
        changeColor.setStyle("-fx-background-color: " +
                toRGBCode(colorTranslationTable.get(this.game.getSelectedColor())) + ";");




        changeColor.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tempGame.changeSelectedColor();
                changeColor.setStyle("-fx-background-color: " +
                        toRGBCode(colorTranslationTable.get(tempGame.getSelectedColor())) + ";");
                if (tempGame.getSelectedColor().equals("void")){
                    changeColor.setStyle("-fx-background-color: transparent;");
                }
                changeColor.setText("Change Colors\nCurrent color: " + tempGame.getSelectedColor());
                if (tempGame.getSelectedColor().equals("void")){
                    changeColor.setStyle("-fx-background-color: transparent;");
                }
            }
        });

        this.movesDisplay = new Label("Moves left: " + tempGame.getNumMovesLeft());
        this.movesDisplay.setId("MovesDisplay");
        fp.setHgap(180);


        fp.getChildren().addAll(changeColor, this.movesDisplay);

        return fp;
    }

    /**
     * Method to set up the controls to be put on the right side of the view panel.
     * @return a vertical FlowPane that should be added to the right side of the root border pane
     */
    private FlowPane setUpRightPanel(){
        FlowPane fpRight = new FlowPane(Orientation.VERTICAL);
        fpRight.setPrefWidth(200);
        Button reset = new Button("Reset level");
        BoardPainterShape thisBPS = this;

        reset.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                thisBPS.resetGame();
            }
        });


        fpRight.setVgap(50);
        reset.setPrefWidth(fpRight.getPrefWidth());
        this.loadLevel.setPrefWidth(fpRight.getPrefWidth());
        this.goToEdit.setPrefWidth(fpRight.getPrefWidth());
        this.saveLevel.setPrefWidth(fpRight.getPrefWidth());
        this.undoButton.setPrefWidth(fpRight.getPrefWidth());
        fpRight.getChildren().addAll(this.loadLevel, this.goToEdit, this.saveLevel, this.undoButton, reset);


        return fpRight;
    }




    /**
     * Method to reset the current game's state to its initial state, calls upon the logic found in
     * the GameBoard to do this.
     */
    public void resetGame(){
        this.winLabel.setVisible(false);
        this.game.resetBoard();
        this.setUpControls();
    }

    /**
     * Method to load a GameBoard into this painter to be played and interacted with.
     * @param gameLoaded the GameBoard to set the current played game to.
     */
    public void loadBoard(GameBoard gameLoaded){
        this.rootScene.getChildren().clear();
        this.game = gameLoaded;
        this.game.addObserver(this);
        this.winLabel.setVisible(false);
        this.gameStage.setTitle("Level: " + this.game.getLevelName());

        this.setUpCellView();
        this.setUpControls();
    }

    /**
     * Method to initialize the Scene of a specified width and height that will be displayed on a stage.
     * @param width the width of the Scene in pixels
     * @param height the height of the Scene in pixels
     * @return a Scene object that will
     */
    public void initScene(int width, int height, Stage primaryStage){
        this.game.addObserver(this);
        Scene scene = new Scene(this.rootScene, width, height);
        scene.getStylesheets().add(this.styleSheet);
        this.rootScene.setId("RootScene");
        BoardPainterShape thisBP = this;
        this.boardPainter.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                thisBP.updateGame(event.getX(), event.getY());
            }
        });

        this.boardPainter.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (thisBP.game.isEditing()) {
                    thisBP.updateGame(event.getX(), event.getY());
                }
            }
        });

        this.setUpCellView();
        this.setEditButton();
        this.setUndoButton();
        this.setFileLoadButton();
        this.setFileSaveButton();
        this.setUpControls();

        this.gameStage = primaryStage;
        primaryStage.setScene(scene);

    }

    /**
     * Method to update the current game being played by translating coordinates of a click on the
     * Canvas into usable row and column coordinates
     * @param x the x coordinate of the mouse click
     * @param y the y coordinate of the mouse click
     */
    public void updateGame(double x, double y){
        if (!this.game.isWon() && this.game.getNumMovesLeft() != 0) {
            if (!this.game.isEditing()) {
                click.play();
            }
            this.game.makeMove(x,y);
        }
    }

    /**
     * Method to update the cell view of the game being currently being played
     * calls setUpCellView
     */
    private void updateCells(){
        this.setUpCellView();
    }

    /**
     * Update method implementation which sets the moves display, updates the cells, checks if the user has won, and
     * updates the view accordingly.
     */
    @Override
    public void update() {
        this.updateCells();
        this.movesDisplay.setText("Moves left: " + this.game.getNumMovesLeft());

        if (this.game.isWon()) {
            this.winLabel.setText("Perfect!");
            this.winLabel.setVisible(true);
            this.victory.play();
        }

        else if (this.game.noMovesLeft()){
            this.winLabel.setText("Oops! Try again!");
            this.winLabel.setVisible(true);
        }

    }
}
