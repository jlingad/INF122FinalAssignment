package server;

import javafx.util.Pair;

import javax.swing.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

// add battleship pieces

public class BattleshipState extends GameState{

    private Integer currentPlayer = 1;
    private HashMap<Integer, Integer> scoresMap;
    private JLabel grid[];
    // Game object information... (gridDimensions, rulebook, etc.)
    private Pair<Integer, Integer> gridDimensions;
    private ArrayList<ImageIcon> gamePieces;

    public BattleshipState() {
        scoresMap = new HashMap<Integer, Integer>();
        scoresMap.put(1,0);
        scoresMap.put(2,0);
        // set grid/gameboard
        gridDimensions = new Pair<Integer, Integer> (8,8);

        // import image files for game pieces
        String pathString = Paths.get("").toAbsolutePath().toString();
        gamePieces = new ArrayList<ImageIcon>();
    }

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
