package GUI;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

/**
 * Created by Emily on 3/9/2016.
 */
public class GamePieceGenerator {

    String pieceType; // X, O, or other kind of piece
    ImageIcon gamePiece;

    public GamePieceGenerator(String pieceType) {
        String pathString = Paths.get("").toAbsolutePath().toString();
        this.pieceType = pieceType;

        if (pieceType.toLowerCase() == "x") {
            gamePiece = new ImageIcon(pathString+"/src/GUI/images/x.png");
        } else if (pieceType.toLowerCase() == "o") {
            gamePiece = new ImageIcon(pathString+"/src/GUI/images/o.png");
        } else if (pieceType.toLowerCase() == "black_checker") {
            gamePiece = new ImageIcon(pathString+"/src/GUI/images/black-checker.png");
        } else if (pieceType.toLowerCase() == "red_checker") {
            gamePiece = new ImageIcon(pathString+"/src/GUI/images/red_checker.png");
        }
    }

    public String getPieceType() {
        return pieceType;
    }

    public ImageIcon getGamePiece() {
        return gamePiece;
    }
}
