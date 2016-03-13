package server;

import GUI.GamePlayPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MatchLogic extends GameLogic {

    private int maxClicksPerTurn = 2;

    public MatchLogic() {
    }

    public int getMaxClicks() {
        return maxClicksPerTurn;
    }

    public boolean hasWinner(GameState state) {
        boolean win = false;
        JLabel[] grid = state.getGrid();
        for (int i=0; i<grid.length; i++)
            if (grid[i].getIcon() != null) {
                win = true;
                System.out.println("winner");
            }
        return win;
    }

    public void makeMove(GameState state, GamePlayPanel gamePlayPanel) {
        ArrayList<JLabel> clickedPanels = state.getClickedPanels();
        if (clickedPanels.get(0).getIcon() != clickedPanels.get(1).getIcon()) {
            JOptionPane.showMessageDialog(gamePlayPanel,"Not a match");
            clickedPanels.get(0).setIcon(null);
            clickedPanels.get(1).setIcon(null);
        }
        state.changePlayerTurn();
        hasWinner(state);
        gamePlayPanel.updateTurnLabel();
    }

    public boolean isValidClick(GameState state, JLabel clickedPanel) {
        if (clickedPanel.getIcon() == null) {
            int index = Integer.parseInt(clickedPanel.getToolTipText());
            clickedPanel.setIcon(state.getGamePiece(index));
            return true;
        }
        return false;
    }

}
