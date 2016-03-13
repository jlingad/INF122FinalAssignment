package server;

import javafx.util.Pair;

import javax.swing.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MatchState extends GameState{

    private String gameName = "Memory Match";
    private Integer currentPlayer = 1;
    private HashMap<Integer, Integer> scoresMap;
    private JLabel grid[];
    // Game object information... (gridDimensions, rulebook, etc.)
    private Pair<Integer, Integer> gridDimensions;
    private ArrayList<ImageIcon> gamePieces;
    private ArrayList<JLabel> clickedPanels;
    private ArrayList<ImageIcon> boardWithPieces;

    public MatchState() {
        scoresMap = new HashMap<Integer, Integer>();
        scoresMap.put(1,0);
        scoresMap.put(2,0);
        // set grid/gameboard
        gridDimensions = new Pair<Integer, Integer> (6,5);
        clickedPanels = new ArrayList<JLabel>();

        // import image files for game pieces
        String pathString = Paths.get("").toAbsolutePath().toString();
        gamePieces = new ArrayList<ImageIcon>();
        gamePieces.add(new ImageIcon(pathString+"/src/GUI/images/match/pm.png"));
        gamePieces.add(new ImageIcon(pathString+"/src/GUI/images/match/2.33.23_pm.png"));
        gamePieces.add(new ImageIcon(pathString+"/src/GUI/images/match/145.jpg"));
        gamePieces.add(new ImageIcon(pathString+"/src/GUI/images/match/a-slave-obeys.png"));
        gamePieces.add(new ImageIcon(pathString+"/src/GUI/images/match/age-of-ultron.png"));
        gamePieces.add(new ImageIcon(pathString+"/src/GUI/images/match/apocalypse.png"));
        gamePieces.add(new ImageIcon(pathString+"/src/GUI/images/match/asteroid.png"));
        gamePieces.add(new ImageIcon(pathString+"/src/GUI/images/match/cnnstyle-future.jpg"));
        gamePieces.add(new ImageIcon(pathString+"/src/GUI/images/match/dagobah.png"));
        gamePieces.add(new ImageIcon(pathString+"/src/GUI/images/match/doomsday.png"));
        gamePieces.add(new ImageIcon(pathString+"/src/GUI/images/match/fallout.png"));
        gamePieces.add(new ImageIcon(pathString+"/src/GUI/images/match/findumonde.png"));
        gamePieces.add(new ImageIcon(pathString+"/src/GUI/images/match/first-hope.jpg"));
        gamePieces.add(new ImageIcon(pathString+"/src/GUI/images/match/guardians-of-the-galaxy.png"));
        gamePieces.add(new ImageIcon(pathString+"/src/GUI/images/match/incoming.jpg"));
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

    public ImageIcon getGamePiece(int index) {
        return boardWithPieces.get(index);
    }
    public ArrayList<JLabel> getClickedPanels() { return clickedPanels; }

    public void setGrid(JLabel[] startingBoard) {
        grid = startingBoard;
        ArrayList<Integer> nums = new ArrayList<Integer>();
        for (int i=0; i<gamePieces.size(); i++) {
            nums.add(i); nums.add(i); // add twice for pairs
        }
        Collections.shuffle(nums);
        boardWithPieces = new ArrayList<ImageIcon>();
        for (int i=0; i<grid.length; i++)
            boardWithPieces.add(gamePieces.get(nums.get(i)));
    }

    public void changePlayerTurn() {
        if (currentPlayer == 1)
            currentPlayer = 2;
        else if (currentPlayer == 2)
            currentPlayer = 1;
        clearClickedPanels();
    }

    public void addClickedPanel(JLabel clickedPanel) {
        clickedPanels.add(clickedPanel);
    }

    public void clearClickedPanels() {
        clickedPanels.clear();
    }
}
