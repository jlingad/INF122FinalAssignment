package server;

import javafx.util.Pair;

import javax.swing.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class TicTacToeState extends GameState{

    private String gameName = "Tic-Tac-Toe";
    private Integer currentPlayer = 1;
    private HashMap<Integer, Integer> scoresMap;
    private JLabel grid[];
    // Game object information... (gridDimensions, rulebook, etc.)
    private Pair<Integer, Integer> gridDimensions;
    private ArrayList<ImageIcon> gamePieces;
    private ArrayList<JLabel> clickedPanels;

    public TicTacToeState() {
        scoresMap = new HashMap<Integer, Integer>();
        scoresMap.put(1,0);
        scoresMap.put(2,0);
        // set grid/gameboard
        gridDimensions = new Pair<Integer, Integer> (3,3);
        clickedPanels = new ArrayList<JLabel>();

        // import image files for game pieces
        String pathString = Paths.get("").toAbsolutePath().toString();
        gamePieces = new ArrayList<ImageIcon>();
        gamePieces.add(new ImageIcon(pathString+"/src/GUI/images/x.png"));
        gamePieces.add(new ImageIcon(pathString+"/src/GUI/images/o.png"));
    }

    public String getGameName() { return gameName; }
    public Integer getCurrentPlayer() { return currentPlayer;}
    public HashMap<Integer,Integer> getScores() { return scoresMap; }
    public JLabel[] getGrid() {
        return grid;
    }
    public Pair<Integer, Integer> getGridDimensions() {
        return gridDimensions;
    }

    public ImageIcon getGamePiece(int playerNum) {
        return gamePieces.get(playerNum-1);
    }
    public ArrayList<JLabel> getClickedPanels() { return clickedPanels; }

    public void setGrid(JLabel[] startingBoard) {
        grid = startingBoard;
    }

    public void changePlayerTurn() {
        if (currentPlayer == 1)
            currentPlayer = 2;
        else if (currentPlayer == 2)
            currentPlayer = 1;
        clearClickedPanels();
    }

    public void addPoint(int playerNum) {
        int newScore = scoresMap.get(playerNum)+1;
        scoresMap.remove(playerNum);
        scoresMap.put(playerNum, newScore);
    }

    public void addClickedPanel(JLabel clickedPanel) {
        clickedPanels.add(clickedPanel);
    }

    public void clearClickedPanels() {
        clickedPanels.clear();
    }
}
