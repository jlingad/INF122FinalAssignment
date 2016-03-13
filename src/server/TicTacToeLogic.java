package server;

import GUI.GamePlayPanel;

import javax.swing.*;
import java.util.ArrayList;

public class TicTacToeLogic extends GameLogic {

    public int maxClicksPerTurn = 1;

    public TicTacToeLogic() {
    }

    public int getMaxClicks() {
        return maxClicksPerTurn;
    }

    // only checks if there is a row filled with game pieces
    // does not check for particular players' game pieces
    public boolean hasWinner(GameState state) {
        boolean win = false;
        JLabel[] grid = state.getGrid();
        for (int i=2; i<grid.length; i++)
            if (grid[i-2].getIcon() != null && grid[i-2].getIcon() == grid[i-1].getIcon()  && grid[i].getIcon() == grid[i-1].getIcon()) {
                win = true;
                System.out.println("winner");
            }
        return win;
    }

    public void makeMove(GameState state, GamePlayPanel gamePlayPanel) {
        ArrayList<JLabel> clickedPanels = state.getClickedPanels();
        // add to the grid the appropriate game piece (the one associated with the
        // current player - pieces are stored in the GameState object
        clickedPanels.get(0).setIcon(state.getGamePiece(state.getCurrentPlayer()));
        state.changePlayerTurn();
        hasWinner(state);
        gamePlayPanel.updateTurnLabel();
    }

    public boolean isValidClick(GameState state, JLabel clickedPanel) {
        boolean isValid;
        if (clickedPanel.getIcon() == null) {
            isValid = true;
        } else { isValid = false; }

        return isValid;
    }

}
