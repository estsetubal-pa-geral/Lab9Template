package pt.pa.refactoring.pieces;

import pt.pa.refactoring.board.Board;
import pt.pa.refactoring.board.Spot;

/**
 * @author amfs
 */
public class Piece {

    public enum PIECE_TYPE { PAWN, BISHOP, ROOK };

    private PIECE_TYPE pieceType;

    private boolean killed = false;

    private boolean white = false;

    public Piece(PIECE_TYPE pieceType, boolean white) {
        this.pieceType = pieceType;
        this.setWhite(white);
    }

    public int getValue() {
        switch (pieceType) {
            case PAWN: return 1;
            case BISHOP: return 3;
            case ROOK: return 5;
            default: return 0;
        }
    }

    public String getSymbol() {
        switch (pieceType) {
            case PAWN: return isWhite() ? "P" : "p";
            case BISHOP: return isWhite() ? "B" : "b";
            case ROOK: return isWhite() ? "R" : "r";
            default: return null;
        }
    }

    // No need to validate the move rules for now or after refactor
    public boolean canMove(Board board, Spot start, Spot end) {
        switch (pieceType) {
            case PAWN: return true;
            case BISHOP: return true;
            case ROOK: return true;
            default: return false;
        }
    }

    public boolean isWhite()
    {
        return this.white;
    }

    public void setWhite(boolean white)
    {
        this.white = white;
    }

    public boolean isKilled()
    {
        return this.killed;
    }

    public void setKilled(boolean killed)
    {
        this.killed = killed;
    }
}
