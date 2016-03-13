package server;

import GUI.GamePlayPanel;

import javax.swing.*;
import java.util.ArrayList;

public class MatchLogic extends GameLogic {

    private int maxClicksPerTurn = 2;

    public MatchLogic() {
    }

    public int getMaxClicks() {
        return maxClicksPerTurn;
    }

    public void hasWinner(GameState state) {
        boolean win = false;
//        JLabel[] grid = state.getGrid();
//        for (int i=0; i<grid.length; i++)
//            if (grid[i].) {
//                win = true;
//                System.out.println("winner");
//            }
        //return win;
    }

    public void makeMove(GameState state, GamePlayPanel gamePlayPanel) {
        ArrayList<JLabel> clickedPanels = state.getClickedPanels();
//        if (clickedPanel.getIcon() == null) {
//
//            state.changePlayerTurn();
//            hasWinner(state);
//            gamePlayPanel.updateTurnLabel();
//        }
    }

    public boolean isValidClick(GameState state, JLabel clickedPanel) {
        boolean isValid = true;

        return isValid;
    }

}
