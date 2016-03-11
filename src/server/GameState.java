package server;

import javafx.util.Pair;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Emily on 3/10/2016.
 */
public abstract class GameState {

    private Integer currentPlayer = 1;
    private HashMap<Integer, Integer> scoresMap;
    private JLabel grid[];
    // Game object information... (gridDimensions, rulebook, etc.)
    private Pair<Integer, Integer> gridDimensions;
    private ArrayList<ImageIcon> gamePieces;

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

    public void setGrid(JLabel[] startingBoard) {
        grid = startingBoard;
    }

    public void changePlayerTurn() {
        if (currentPlayer == 1)
            currentPlayer = 2;
        else if (currentPlayer == 2)
            currentPlayer = 1;
    }
}