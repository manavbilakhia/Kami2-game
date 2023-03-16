package edu.union.maingame.game;
import edu.union.maingame.game.board_gui.*;
import edu.union.maingame.game.board_model.*;
import edu.union.maingame.game.file_io.*;

import javafx.scene.media.AudioClip;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

/**
 * This is an application which aims to reproduce the game Kami 2 using our
 * own logic and the graphics of JavaFX.
 *
 * @author John Daly
 */
public class KamiApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Method to set a fixed screen size for the current stage
     * @param currentStage the stage to be constrained
     * @param width the width desired in pixels
     * @param height the height desired in pixels
     */
    public static void setFixedScreenSize(Stage currentStage, int width, int height){
        //currentStage.setMaxWidth(width);
        //currentStage.setMaxHeight(height);
        currentStage.setMinWidth(width);
        currentStage.setMinHeight(height);
    }


    /**
     * Overridden start method which starts the JavaFX toolkit and does some small pre processing to get the
     * game up and running.
     *
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("KamiFX");
        int canvasWidth = 1200;
        int canvasHeight = 800;
        File introBoardFile = new File(System.getProperty("user.dir") + "/src/main/java/edu/union/maingame/game/levels/kfxintro.board");
        GameBoard introBoard = BoardIO.loadBoard(introBoardFile, false);

        URL bgmResource = getClass().getClassLoader().getResource("bgm1.wav");
        URL victoryResource = getClass().getClassLoader().getResource("victory.wav");
        URL placedResource = getClass().getClassLoader().getResource("pieceplaced.wav");
        AudioClip vic = new AudioClip(victoryResource.toString());
        AudioClip place = new AudioClip(placedResource.toString());
        AudioClip bgm = new AudioClip(bgmResource.toString());
        place.setVolume(0.65f);
        vic.setVolume(0.45f);
        bgm.setCycleCount(AudioClip.INDEFINITE);
        bgm.play();
        URL style = getClass().getClassLoader().getResource("style.css");
        BoardPainterShape mainBp = new BoardPainterShape(introBoard, place, vic, style.toExternalForm());



        mainBp.initScene(canvasWidth,canvasHeight, primaryStage);
        setFixedScreenSize(primaryStage, canvasWidth, canvasHeight);
        primaryStage.show();
    }
}
