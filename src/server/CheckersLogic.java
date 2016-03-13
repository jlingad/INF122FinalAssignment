package server;

import GUI.GamePlayPanel;

import javax.swing.*;
import java.util.ArrayList;

public class CheckersLogic extends GameLogic {

    private int maxClicksPerTurn = 2;

    public CheckersLogic() {
    }

    public int getMaxClicks() {
        return maxClicksPerTurn;
    }

    // only checks if there is a row filled with game pieces
    // does not check for particular players' game pieces
    public void hasWinner(GameState state) {
        JLabel[] grid = state.getGrid();
        Icon piece = state.getGamePiece(1);
        // get any piece that can be found on the board
        // if there is a winner, the grid will only contain
        // this piece type
        for (int i=0; i<grid.length; i++)
            if (grid[i].getIcon() != null)
                piece = grid[i].getIcon();
        // if there is a piece of a different type, there is no winner
//        for (int i=0; i<grid.length; i++)
//            if (grid[i].getIcon() != piece)
//                return false;
        System.out.println("winner");
        //return true;
    }

    public void makeMove(GameState state, GamePlayPanel gamePlayPanel) {
        ArrayList<JLabel> clickedPanels = state.getClickedPanels();
        int currentPlayer = state.getCurrentPlayer();
        clickedPanels.get(0).setIcon(null);
        clickedPanels.get(1).setIcon(state.getGamePiece(state.getCurrentPlayer()));
        hasWinner(state);
        state.changePlayerTurn();
        gamePlayPanel.updateTurnLabel();
    }

    public boolean isValidClick(GameState state, JLabel clickedPanel) {
        boolean isValid = true;
        int currentPlayer = state.getCurrentPlayer();
        // check if clicked piece is the same as the current player's
        if (state.getClickedPanels().size() == 0 && clickedPanel.getIcon() == state.getGamePiece(currentPlayer)) {
            isValid = true;
        } else if (state.getClickedPanels().size() == 0 && clickedPanel.getIcon() != state.getGamePiece(currentPlayer)) {
            System.out.println("Incorrect piece selected. You tried moving your opponent's game piece");
            isValid = false;
        }
//        else if (state.getClickedPanels().size() > 0)
            // check to see if valid move

        return isValid;
    }
}
