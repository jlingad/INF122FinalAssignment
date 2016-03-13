package server;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

// add Checkers pieces

public class CheckersState extends GameState{

    private String gameName = "Checkers";
    private Integer currentPlayer = 1;
    private HashMap<Integer, Integer> scoresMap;
    private JLabel grid[];
    // Game object information... (gridDimensions, rulebook, etc.)
    private Pair<Integer, Integer> gridDimensions;
    private ArrayList<ImageIcon> gamePieces;
    private ArrayList<JLabel> clickedPanels;

    public CheckersState() {
        scoresMap = new HashMap<Integer, Integer>();
        scoresMap.put(1,0);
        scoresMap.put(2,0);
        // set grid/gameboard
        gridDimensions = new Pair<Integer, Integer> (8,8);
        clickedPanels = new ArrayList<JLabel>();

        // import image files for game pieces
        String pathString = Paths.get("").toAbsolutePath().toString();
        gamePieces = new ArrayList<ImageIcon>();
        gamePieces.add(new ImageIcon(pathString+"/src/GUI/images/black-checker.png"));
        gamePieces.add(new ImageIcon(pathString+"/src/GUI/images/red-checker.png"));
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
    public ArrayList<JLabel> getClickedPanels() { return clickedPanels; }

    public ImageIcon getGamePiece(int playerNum) {
        return gamePieces.get(playerNum-1);
    }

    public void setGrid(JLabel[] startingBoard) {
        grid = startingBoard;
        // set checkerboard
        for (int i = 0; i < grid.length; i++) {
            if (i % 16 == 0) {
                for (int j = i + 1; j < i + 8; j += 2)
                    grid[j].setBackground(Color.DARK_GRAY);
                i += 7;
            }
            else
            if (i%2 == 0)
                grid[i].setBackground(Color.DARK_GRAY);
        }

        // add pieces
        for (int i=0; i<24; i++)
            if (grid[i].getBackground() == Color.DARK_GRAY)
                grid[i].setIcon(gamePieces.get(0));
        for (int i=40; i<grid.length; i++)
            if (grid[i].getBackground() == Color.DARK_GRAY)
                grid[i].setIcon(gamePieces.get(1));
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
