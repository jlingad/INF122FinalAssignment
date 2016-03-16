package server;

import GUI.GamePlayPanel;
import shared.ExecutionState;

import javax.swing.*;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CheckersLogic extends GameLogic {

    private int maxClicksPerTurn = 2; /// Will have to take into account jumping over pieces
    public boolean hasSpecialVersion = true;

    private int toJumpTo = -1;


    public CheckersLogic() {
    }

    public int getMaxClicks() {
        return maxClicksPerTurn;
    }
    public boolean hasSpecialVersion() { return hasSpecialVersion; }

    // only checks if there is a row filled with game pieces
    // does not check for particular players' game pieces
    public void hasWinner(GameState state, GamePlayPanel gamePlayPanel) {
        JLabel[] grid = state.getGrid();
        Icon piece = state.getGamePiece(1);
        ArrayList<ImageIcon> pieces = state.getGamePieces();
//        String pathString = Paths.get("").toAbsolutePath().toString();
        boolean win = true;
        // get any piece that can be found on the board
        // if there is a winner, the grid will only contain
        // this piece type

        for (int i=0; i<grid.length; i++)
            if (grid[i].getIcon() != null){
                System.out.println("hasWinner()");
                piece = grid[i].getIcon();
                break;
            }

        // if there is a piece of a different type, there is no winner
        for (int i=0; i<grid.length; i++)
            // must check for king pieces too
            if (grid[i].getIcon() != piece)
                win = false;
        if (win) {
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

        // Moves piece over; icon check is left to isValidClick/Move
        if(toJumpTo != -1) {
            clickedPanels.remove(1);
            clickedPanels.add(state.getGrid()[toJumpTo]);
        }

        // TODO: King handling
        if(state.getCurrentPlayer() == 1 && isBottomRow(clickedPanels.get(1), state)) {
            clickedPanels.get(1).setIcon(state.getGamePiece(3));
            System.out.println("KINGING RED");
        }
        else if(state.getCurrentPlayer() == 2 && isTopRow(clickedPanels.get(1), state)) {
            clickedPanels.get(1).setIcon(state.getGamePiece(2));
            System.out.println("KINGING BLACK");
        }
        else { // Normal case- no kinging
            clickedPanels.get(1).setIcon(clickedPanels.get(0).getIcon());
        }


        clickedPanels.get(0).setIcon(null);

        hasWinner(state, gamePlayPanel);
        state.changePlayerTurn();
        gamePlayPanel.updateTurnLabel();

        toJumpTo = -1;
    }

    public boolean isValidClick(GameState state, JLabel clickedPanel) {
        boolean isValid = true;
        boolean kinged;

        int currentPlayer = state.getCurrentPlayer();
        // check if clicked piece is the same as the current player's
        if (state.getClickedPanels().size() == 0 &&
                (clickedPanel.getIcon() == state.getGamePiece(currentPlayer) ||
                clickedPanel.getIcon() == state.getGamePiece(currentPlayer+2)) ) {
            System.out.println("That's a piece of yours.");
            isValid = true;
        } else if (state.getClickedPanels().size() == 0 &&
                (clickedPanel.getIcon() != state.getGamePiece(currentPlayer) ||
                clickedPanel.getIcon() != state.getGamePiece(currentPlayer+2)) ) {
            System.out.println("Not your piece/blank space");
            isValid = false;
        }


        // check to see if valid move
        else if (state.getClickedPanels().size() > 0) {
            int oldP = Integer.parseInt(state.getClickedPanels().get(0).getToolTipText());
            int newP = Integer.parseInt(clickedPanel.getToolTipText());

            // TODO: Detect Kings properly
            kinged = state.getClickedPanels().get(0).getIcon() == state.getGamePieces().get(currentPlayer+1);
            if(kinged) {System.out.println("KING MOVE DETECTED");}

            // First important check
            if(isValidMove(oldP, newP, currentPlayer, kinged, state)) {
                System.out.println("Move is legal.");
                isValid = true;

                // Check to see if an enemy piece is being taken
                Icon newSpot = clickedPanel.getIcon();
                if((newSpot != null) && (newSpot != state.getGamePiece(currentPlayer)) &&
                        (newSpot != state.getGamePiece(currentPlayer+2))) {
                    System.out.println("A piece is being taken. JUMP ATTEMPT");

                    // Second important check
                    if(isValidJump(oldP, newP, currentPlayer, kinged, state)) {
                        System.out.println("Handling jump.");
                        clickedPanel.setIcon(null);
                    }
                    else {
                        System.out.println("Move not legal: jump is being blocked.");
                        return false; // Immediately fail
                    }
                }
                else {
                    System.out.println("Normal move.");
                }

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
    private boolean isValidMove(int oldPos, int newPos, int player, boolean king, GameState state) {
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

    // Can only be called if it's a valid move. Consider removing attacker/king variables if not used
    private boolean isValidJump(int oldPos, int newPos, int attacker, boolean king, GameState state) {
        int orow = oldPos / 8;
        int ocol = oldPos % 8;
        int nrow = newPos / 8;
        int ncol = newPos % 8;

        int jrow;
        int jcol;
        int jpos;

        if(ocol == ncol+1) { // If moving left
            if(orow == nrow+1) { // If moving Up/Left
                System.out.println("Make Up/Left jump.");
                if(nrow == 0 || ncol == 0) {
                    System.out.println("Going to hit a wall if jump happens.");
                    return false;
                }
                else {
                    // Calculate index of next spot; use isBlocked to check for an icon
                    jrow = nrow-1;
                    jcol = ncol-1;
                    jpos = (jrow*8) + jcol;

                    if(!isBlocked(jpos, state)) {
                        System.out.println("Jump is safe.");
                        toJumpTo = jpos;
                        return true;
                    }
                    else {
                        System.out.println("PIECE IN WAY OF JUMP. No jump.");
                        return false;
                    }
                }

            }
            else { // If moving Down/Left
                System.out.println("Make Down/Left jump.");
                if(nrow == 7 || ncol == 0) {
                    System.out.println("Going to hit a wall if jump happens.");
                    return false;
                }
                else {
                    // Calculate index of next spot; use isBlocked to check for an icon
                    jrow = nrow+1;
                    jcol = ncol-1;
                    jpos = (jrow*8) + jcol;

                    if(!isBlocked(jpos, state)) {
                        System.out.println("Jump is safe.");
                        toJumpTo = jpos;
                        return true;
                    }
                    else {
                        System.out.println("PIECE IN WAY OF JUMP. No jump.");
                        return false;
                    }
                }

            }
        }
        else { // If moving right
            if(orow == nrow+1) { // If moving Up/Right
                System.out.println("Make Up/Right jump.");
                if(nrow == 0 || ncol == 7) {
                    System.out.println("Going to hit a wall if jump happens.");
                    return false;
                }
                else {
                    // Calculate index of next spot; use isBlocked to check for an icon
                    jrow = nrow-1;
                    jcol = ncol+1;
                    jpos = (jrow*8) + jcol;

                    if(!isBlocked(jpos, state)) {
                        System.out.println("Jump is safe.");
                        toJumpTo = jpos;
                        return true;
                    }
                    else {
                        System.out.println("PIECE IN WAY OF JUMP. No jump.");
                        return false;
                    }
                }

            }
            else { // If moving Down/Right
                System.out.println("Make Down/Right jump.");
                if(nrow == 7 || ncol == 7) {
                    System.out.println("Going to hit a wall if jump happens.");
                    return false;
                }
                else {
                    // Calculate index of next spot; use isBlocked to check for an icon
                    jrow = nrow+1;
                    jcol = ncol+1;
                    jpos = (jrow*8) + jcol;

                    if(!isBlocked(jpos, state)) {
                        System.out.println("Jump is safe.");
                        toJumpTo = jpos;
                        return true;
                    }
                    else {
                        System.out.println("PIECE IN WAY OF JUMP. No jump.");
                        return false;
                    }
                }

            }
        }

    }

    private boolean isBlocked(int positionToCheck, GameState state) {
        return state.getGrid()[positionToCheck].getIcon() != null;
    }

    private boolean isBottomRow(JLabel toFind, GameState state) {
        for(int i=56; i<64; ++i) {
            if(toFind == state.getGrid()[i]) {
                return true;
            }
        }
        return false;
    }

    private boolean isTopRow(JLabel toFind, GameState state) {
        for(int i=0; i<8; ++i) {
            if(toFind == state.getGrid()[i]) {
                return true;
            }
        }
        return false;
    }

}

