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
    public void hasWinner(GameState state) {
        boolean win = false;
        JLabel[] grid = state.getGrid();

        //checks for horizontal win
        if (grid[0].getIcon() != null && grid[1].getIcon() != null && grid[2].getIcon() != null && grid[0].getIcon() == grid[1].getIcon() && grid[2].getIcon() == grid[1].getIcon()) {
            win = true;
        }
        if(grid[3].getIcon() != null && grid[4].getIcon() != null && grid[5].getIcon() != null && grid[3].getIcon() == grid[4].getIcon() && grid[5].getIcon() == grid[4].getIcon()){
            win = true;
        }
        if(grid[6].getIcon() != null && grid[7].getIcon() != null && grid[8].getIcon() != null && grid[6].getIcon() == grid[7].getIcon() && grid[8].getIcon() == grid[7].getIcon()){
            win = true;
        }

        //checks for vertical win
        else if (grid[0].getIcon() != null && grid[3].getIcon() != null && grid[6].getIcon() != null && grid[0].getIcon() == grid[3].getIcon() && grid[6].getIcon() == grid[3].getIcon()){
            win = true;
        }
        else if (grid[1].getIcon() != null && grid[4].getIcon() != null && grid[7].getIcon() != null && grid[1].getIcon() == grid[4].getIcon() && grid[7].getIcon() == grid[4].getIcon()){
            win = true;
        }
        else if (grid[2].getIcon() != null && grid[5].getIcon() != null && grid[8].getIcon() != null && grid[2].getIcon() == grid[5].getIcon() && grid[8].getIcon() == grid[5].getIcon()){
            win = true;
        }

        //checks for diagonal win
        else if (grid[0].getIcon() != null && grid[4].getIcon() != null && grid[8].getIcon() != null && grid[0].getIcon() == grid[4].getIcon() && grid[8].getIcon() == grid[4].getIcon()){
            win = true;
        }
        else if (grid[2].getIcon() != null && grid[4].getIcon() != null && grid[6].getIcon() != null && grid[2].getIcon() == grid[4].getIcon() && grid[6].getIcon() == grid[4].getIcon()){
            win = true;
        }

        if (win){
            System.out.print("WINNER is player " + state.getCurrentPlayer());
        }

    }


    public void makeMove(GameState state, GamePlayPanel gamePlayPanel) {
        ArrayList<JLabel> clickedPanels = state.getClickedPanels();
        // add to the grid the appropriate game piece (the one associated with the
        // current player - pieces are stored in the GameState object
        boolean win = false;
        clickedPanels.get(0).setIcon(state.getGamePiece(state.getCurrentPlayer()));
        hasWinner(state);
        state.changePlayerTurn();
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
