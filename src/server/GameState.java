package server;

import javafx.util.Pair;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Emily on 3/10/2016.
 */
public abstract class GameState {

    private String gameName;
    private Integer currentPlayer = 1; //store this in client somewhere
    private HashMap<Integer, Integer> scoresMap;
    private JLabel grid[];
    private Pair<Integer, Integer> gridDimensions;
    private ArrayList<ImageIcon> gamePieces;
    private ArrayList<JLabel> clickedPanels;

    public String getGameName() { return gameName; }
    public Integer getCurrentPlayer() { return currentPlayer;} //use this in client!
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
    public ArrayList<ImageIcon> getGamePieces() {
    	return gamePieces;
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