package server;

import GUI.GamePlayPanel;

import javax.swing.*;
import java.util.ArrayList;

public abstract class GameLogic {

    private int maxClicksPerTurn;

    public int getMaxClicks() { return maxClicksPerTurn; }

    public abstract boolean hasWinner(GameState state);

    public abstract void makeMove(GameState state, GamePlayPanel gamePlayPanel);
    public abstract boolean isValidClick(GameState state, JLabel clickedPanel);
}
