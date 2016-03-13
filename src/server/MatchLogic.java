package server;

import GUI.GamePlayPanel;
import shared.ExecutionState;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class MatchLogic extends GameLogic {

    private int maxClicksPerTurn = 2;

    public MatchLogic() {
    }

    public int getMaxClicks() {
        return maxClicksPerTurn;
    }

    public void hasWinner(GameState state, GamePlayPanel gamePlayPanel) {
        boolean win = true;
        JLabel[] grid = state.getGrid();
        for (int i=0; i<grid.length; i++)
            if (grid[i].getIcon() == null) {
                win = false;
            }
        if (win == true) {
            HashMap<Integer, Integer> scoresMap = state.getScores();
            int winner = (scoresMap.get(1) > scoresMap.get(2)) ? 1 : 2;
            String pathString = Paths.get("").toAbsolutePath().toString();
            JOptionPane.showMessageDialog(gamePlayPanel, "WINNER is player " + winner,
                    "END OF GAME", JOptionPane.PLAIN_MESSAGE,
                    new ImageIcon(pathString + "/src/GUI/images/partyparrot.gif"));
            gamePlayPanel.getGUI().setExecutionState(ExecutionState.MAIN_MENU);
            gamePlayPanel.getGUI().update();
        }
    }

    public void makeMove(GameState state, GamePlayPanel gamePlayPanel) {
        ArrayList<JLabel> clickedPanels = state.getClickedPanels();
        if (clickedPanels.get(0).getIcon() != clickedPanels.get(1).getIcon()) {
            JOptionPane.showMessageDialog(gamePlayPanel,"Not a match");
            clickedPanels.get(0).setIcon(null);
            clickedPanels.get(1).setIcon(null);
        } else {
            state.addPoint(state.getCurrentPlayer());
        }

        state.changePlayerTurn();
        hasWinner(state, gamePlayPanel);
        gamePlayPanel.updateTurnLabel();
        gamePlayPanel.updateScorePanel();
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
