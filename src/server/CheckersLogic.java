package server;

import GUI.GamePlayPanel;
import shared.ExecutionState;

import javax.swing.*;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CheckersLogic extends GameLogic {

    private int maxClicksPerTurn = 2; /// Will have to take into account jumping over pieces

    public CheckersLogic() {
    }

    public int getMaxClicks() {
        return maxClicksPerTurn;
    }

    // only checks if there is a row filled with game pieces
    // does not check for particular players' game pieces
    public void hasWinner(GameState state, GamePlayPanel gamePlayPanel) {
        JLabel[] grid = state.getGrid();
        Icon piece = state.getGamePiece(1);
        boolean win = true;
        // get any piece that can be found on the board
        // if there is a winner, the grid will only contain
        // this piece type
        for (int i=0; i<grid.length; i++)
            if (grid[i].getIcon() != null)
                piece = grid[i].getIcon();
        // if there is a piece of a different type, there is no winner
        for (int i=0; i<grid.length; i++)
            if (grid[i].getIcon() != piece)
                win = false;
        if (win == true) {
            String pathString = Paths.get("").toAbsolutePath().toString();
            JOptionPane.showMessageDialog(gamePlayPanel, "WINNER is player " + state.getCurrentPlayer(),
                    "END OF GAME", JOptionPane.PLAIN_MESSAGE,
                    new ImageIcon(pathString+"/src/GUI/images/partyparrot.gif"));
            gamePlayPanel.getGUI().setExecutionState(ExecutionState.MAIN_MENU);
            gamePlayPanel.getGUI().update();
        }
    }

    public void makeMove(GameState state, GamePlayPanel gamePlayPanel) {
        ArrayList<JLabel> clickedPanels = state.getClickedPanels();
        int currentPlayer = state.getCurrentPlayer();
        clickedPanels.get(0).setIcon(null);
        clickedPanels.get(1).setIcon(state.getGamePiece(state.getCurrentPlayer()));
        hasWinner(state, gamePlayPanel);
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

        // check to see if valid move
        else if (state.getClickedPanels().size() > 0) {
            int oldP = Integer.parseInt(state.getClickedPanels().get(0).getToolTipText());
            int newP = Integer.parseInt(clickedPanel.getToolTipText());
            if(isValidMove(oldP, newP, currentPlayer, false)) {  // false until we implement kings
                System.out.println("Move is legal.");
                isValid = true;
            }
            else {
                System.out.println("Move is not legal.");
                state.getClickedPanels().clear();
                isValid = false;
            }
        }


        return isValid;
    }

    // Take legal clickedPanels[0] and compare to clickedPanels[1], determine legality
    public boolean isValidMove(int oldPos, int newPos, int player, boolean king) {
        // Assume failure before anything else- lots of illegal moves
        boolean isValid = false;
        int orow = oldPos / 8;
        int ocol = oldPos % 8;
        int nrow = newPos / 8;
        int ncol = newPos % 8;
        System.out.println("oldPos = (" + ocol + ", " + orow + ")");
        System.out.println("newPos = (" + ncol + ", " + nrow + ")");


        if (ocol == ncol+1 || ocol == ncol-1) {
            if(player == 1) {
                if(orow == nrow-1 || (king && orow == nrow+1)) {
                    isValid = true;
                }
            }
            else {
                if(orow == nrow+1 || (king && orow == nrow-1)) {
                    isValid = true;
                }
            }
        }

        return isValid;
    }




}
