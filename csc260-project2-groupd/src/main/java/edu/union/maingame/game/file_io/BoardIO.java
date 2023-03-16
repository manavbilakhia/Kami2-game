package edu.union.maingame.game.file_io;
import java.io.*;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import edu.union.maingame.game.board_model.GameBoard;

/**
 * This class provides functions for saving and loading Kami game boards
 * to and from files for use in loading and saving these boards into the Kami game.
 *
 * @author John Daly
 */
public class BoardIO {


    /**
     * Method to save a game board to a location as our own format for saving boards.
     * Boards are saved as text data with every attribute of the board being extracted and
     * saved sequentially in a format which can be read in with the same techniques.
     * @param toSave A game board object to be saved to a file
     * @param saveLoc A String containing the name of the file to be saved
     */

    private static final String[] paletteOfAll = new String[]{"red", "blue", "green", "purple", "tan", "orange",
                                                                                                            "gray",
                                                                                                                "void"};
    public static void saveBoard(GameBoard toSave, String saveName, String fileType){
        String ext = "";
        if (fileType.equals("JSON")){
            ext = ".json";
        }
        else {
            ext = ".board";
        }
        File saveLoc = new File(System.getProperty("user.dir") + "/src/main/java/edu/union/maingame/game/levels/"
        + saveName + ext);

        String toWrite;

        if (fileType.equals("JSON")){
            toWrite = saveBoardJSON(toSave);
        }
        else{
            toWrite = saveBoardFile(toSave);
        }

        try {
            FileWriter wr = new FileWriter(saveLoc);
            wr.write(toWrite);
            wr.flush();
            wr.close();
        }catch (IOException e){
            System.out.println("Board Save Failure.");
        }
    }

    private static String saveBoardFile(GameBoard toSave) {
        String toWrite = "";
        toWrite += "type:" + toSave.getBoardType() + "\n";
        toWrite += "name:" + toSave.getLevelName() + "\n";
        toWrite += "rows:" + toSave.getRows() + "\n";
        toWrite += "cols:" + toSave.getColumns() + "\n";
        toWrite += "maxmoves:" + toSave.getMaxMoves() + "\n";
        toWrite += "hint:" + toSave.getHint() + "\n";
        toWrite += "palette:";
        String palette = "";
        Iterator<String> paletteIter = toSave.getAllPiecesInBoard().iterator();
        while (paletteIter.hasNext()) {
            String s = paletteIter.next();
            if (!palette.contains(s) && !s.equals("void")) {
                palette += s + ",";
            }
        }
        toWrite += palette;
        toWrite += "\n";
        String[][] board = toSave.getBoard();
        for (int i = 0; i < toSave.getRows(); i++) {
            toWrite += "row" + i + ":";
            for (int j = 0; j < toSave.getColumns(); j++) {
                toWrite += board[i][j] + ",";
            }
            toWrite += "\n";
        }

        return toWrite;
    }

    private static String saveBoardJSON(GameBoard toSave){
        JSONObject jObj = new JSONObject();
        jObj.put("type", toSave.getBoardType());
        jObj.put("name", toSave.getLevelName());
        jObj.put("rows", toSave.getRows());
        jObj.put("cols", toSave.getColumns());
        jObj.put("maxmoves", toSave.getMaxMoves());
        jObj.put("hint", toSave.getHint());
        JSONArray palette = new JSONArray();

        Iterator<String> paletteIter = toSave.getAllPiecesInBoard().iterator();
        while (paletteIter.hasNext()){
            String s = paletteIter.next();
            if (!palette.contains(s) && !s.equals("void")){
                palette.add(s);
            }
        }
        jObj.put("palette", palette);
        String [][] board = toSave.getBoard();
        JSONArray boardArray = new JSONArray();
        String rowToWrite = "";
        JSONArray tempRow;
        for (int i = 0; i < toSave.getRows(); i++){
            rowToWrite = "row" + i;
            tempRow = new JSONArray();
            for (int j = 0; j < toSave.getColumns(); j++){
                tempRow.add(board[i][j]);
            }
            boardArray.add(tempRow);
        }
        jObj.put("board", boardArray);

        return jObj.toJSONString();

    }

    /**
     * Static method for loading a board from a file and setting its editing mode accordingly
     * @param toLoad A File object containing the location of the desired file
     * @param editing A boolean to designate if the board will be loaded for editing or not
     * @return The board which has been loaded from the selected file
     */
    public static GameBoard loadBoard(File toLoad, boolean editing){

        GameBoard toReturn;

        if (toLoad.toString().contains(".json")){
            toReturn = loadBoardJSON(toLoad, editing);
        }
        else{
            toReturn = loadBoardFile(toLoad, editing);
        }

        return toReturn;

    }

    private static GameBoard loadBoardFile(File toLoad, boolean editing){
        Integer rows = null;
        Integer cols = null;
        Integer maxMoves = null;
        String levelName = null;
        String hint = null;
        String type = null;
        String[] palette = null;
        String[][] board = null;
        int rowCounter = 0;


        try {

            Scanner myReader = new Scanner(toLoad);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.contains("rows:")){
                    String workingData = data.replace("rows:", "");
                    rows = Integer.parseInt(workingData);
                }
                else if (data.contains("cols:")){
                    String workingData = data.replace("cols:", "");
                    cols = Integer.parseInt(workingData);

                }
                else if (data.contains("type:")) {
                    String workingData = data.replace("type:", "");
                    type = workingData;
                }
                else if (data.contains("maxmoves:")){
                    String workingData = data.replace("maxmoves:", "");
                    maxMoves = Integer.parseInt(workingData);
                }
                else if (data.contains("hint:")){
                    String workingData = data.replace("hint:", "");
                    hint = workingData;
                }
                else if (data.contains("name:")){
                    String workingData = data.replace("name:", "");
                    levelName = workingData;
                }
                else if (data.contains("palette:")){
                    String workingData = data.replace("palette:", "");
                    palette = workingData.split(",");

                }
                else if (data.contains("row")){
                    String workingData = data.replace("row" + rowCounter + ":", "");
                    String[] tempRow = workingData.split(",");
                    if (board == null){
                        board = new String[rows][cols];
                    }

                    for (int c = 0; c < cols; c++){
                        board[rowCounter][c] = tempRow[c];
                    }
                    rowCounter++;

                }
                else{
                    System.out.println();
                }

            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("A board reading error occurred.");
        }
        GameBoard toReturn;
        if (editing){
            toReturn = BoardFactory.boardFactory(rows, cols, hint, maxMoves, paletteOfAll, levelName, type, editing);
            //toReturn = new GameBoard(rows, cols, hint, maxMoves, paletteOfAll, levelName, editing);
        }else{
            toReturn = BoardFactory.boardFactory(rows, cols, hint, maxMoves, palette, levelName, type, editing);
            //toReturn = new GameBoard(rows, cols, hint, maxMoves, palette, levelName, editing);
        }
        toReturn.setBoard(board);


        return toReturn;
    }

    private static GameBoard loadBoardJSON(File toLoad, boolean editing){
        Integer rows = null;
        Integer cols = null;
        Integer maxMoves = null;
        String levelName = null;
        String hint = null;
        String type = null;
        String[] palette = null;
        ArrayList<String> paletteTransform = new ArrayList<>();
        String[][] board = null;
        int rowCounter = 0;

        try {
            JSONParser parser = new JSONParser();
            Reader reader = new FileReader(toLoad);

            JSONObject loadedFile = (JSONObject) parser.parse(reader);


            levelName = (String) loadedFile.get("name");
            type = (String) loadedFile.get("type");
            hint = (String) loadedFile.get("hint");

            rows = Math.round((Long) loadedFile.get("rows"));
            cols = Math.round((Long) loadedFile.get("cols"));
            maxMoves = Math.round((Long) loadedFile.get("maxmoves"));

            JSONArray tPalette = (JSONArray) loadedFile.get("palette");
            //palette = (JSONArray) loadedFile.get("palette");
            for (Object obj: tPalette){
                String tmpStr = (String) obj;
                paletteTransform.add(tmpStr);

            }
            palette = paletteTransform.toArray(new String[0]);


            JSONArray arr = (JSONArray) loadedFile.get("board");
            board = new String[rows][cols];

            int r = 0;
            int c = 0;
            for (Object arr2: arr){
                JSONArray arrparse = (JSONArray) arr2;
                for (Object row:arrparse){
                    String rowStr = (String) row;
                    board[r][c] = rowStr;
                    c += 1;

                }
                c = 0;
                r += 1;

            }


        } catch (Exception e){
            System.out.println("JSON read error.");
        }

        GameBoard toReturn;
        if (editing){
            toReturn = BoardFactory.boardFactory(rows, cols, hint, maxMoves, paletteOfAll, levelName, type, editing);
            //toReturn = new GameBoard(rows, cols, hint, maxMoves, paletteOfAll, levelName, editing);
        }else{
            toReturn = BoardFactory.boardFactory(rows, cols, hint, maxMoves, palette, levelName, type, editing);
            //toReturn = new GameBoard(rows, cols, hint, maxMoves, palette, levelName, editing);
        }
        toReturn.setBoard(board);


        return toReturn;
    }


    /**
     * Method for creating and returning an instance of a blank board for use in editing levels.
     * @return blank game board in editing mode
     */
    public static GameBoard generateDefaultBoard(){

        GameBoard toReturn = BoardFactory.boardFactory(20,25, "Temp hint", 5, paletteOfAll,
                "TempName", "square",true);
        toReturn.fillBoard("tan");
        toReturn.setBoard(toReturn.getBoard());

        return toReturn;
    }

    /**
     * Method for getting and returning a collection of names of board files in the board level directory
     * @return A collection of strings representing the names of the files, without the file extension.
     */
    public static Collection<String> getListOfBoards(){
        File dir = new File(System.getProperty("user.dir") + "/src/main/java/edu/union/maingame/game/levels/");
        ArrayList<String> toReturn = new ArrayList<>();
        for (File f: dir.listFiles()){
            String s = f.toString();
            //s = s.replace(".board", "");
            //s = s.replace(".json", "");
            s = s.replace("/", "!!!~");
            s = s.replace("\\", "!!!~");
            String[] temp = s.split("!!!~");
            toReturn.add(temp[temp.length - 1]);
        }
        return toReturn;
    }

}