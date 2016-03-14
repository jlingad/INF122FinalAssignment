package server;

import GUI.GamePlayPanel;

import javax.swing.*;
import java.util.ArrayList;

public abstract class GameLogic {

    private int maxClicksPerTurn;
    private boolean hasSpecialVersion;


    public int getMaxClicks() { return maxClicksPerTurn; }
    public boolean hasSpecialVersion() { return hasSpecialVersion; }

    public abstract void hasWinner(GameState state, GamePlayPanel gamePlayPanel);

    public abstract void makeMove(GameState state, GamePlayPanel gamePlayPanel);

    public abstract boolean isValidClick(GameState state, JLabel clickedPanel);
}
