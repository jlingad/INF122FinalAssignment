package state;
import javafx.util.Pair;

import javax.swing.*;
import java.util.HashMap;

/**
 * Created by Emily on 3/5/2016.
 */
public class GameState {

    private Integer currentPlayer = 1;
    private HashMap<Integer, Integer> scoresMap;
    private JLabel grid[];
    // Game object information... (gridDimensions, rulebook, etc.)
    private Pair<Integer, Integer> gridDimensions;

    public GameState(int rows, int cols) {
        scoresMap = new HashMap<Integer, Integer>();
        scoresMap.put(1,0);
        scoresMap.put(2,0);
        // set grid/gameboard
        gridDimensions = new Pair<Integer, Integer> (rows, cols);
    }

    public Integer getCurrentPlayer() {
        return currentPlayer;
    }

    public HashMap<Integer,Integer> getScores() {
        return scoresMap;
    }

    public JLabel[] getGrid() {
        return grid;
    }

    public Pair<Integer, Integer> getGridDimensions() {
        return gridDimensions;
    }

    public void setGrid(JLabel[] startingBoard) {
        grid = startingBoard;
    }

}
