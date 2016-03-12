package server;

import javax.swing.*;

public class TicTacToeLogic {


    public TicTacToeLogic() {
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

}
