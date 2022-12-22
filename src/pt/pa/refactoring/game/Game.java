package pt.pa.refactoring.game;

import pt.pa.refactoring.behaviour.Move;
import pt.pa.refactoring.board.Board;
import pt.pa.refactoring.board.Spot;
import pt.pa.refactoring.pieces.Piece;
import pt.pa.refactoring.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author amfs
 */
public class Game {
    private Player[] players = new Player[2];
    private Board board;
    private Player currentTurn;
    private GameStatus status;
    private List<Move> movesPlayed;

    public Game(Player p1, Player p2) {
        this.board = new Board();
        this.movesPlayed = new ArrayList<>();
        this.initialize(p1, p2);
    }

    private void initialize(Player p1, Player p2)
    {
        players[0] = p1;
        players[1] = p2;

        board.resetBoard();
        movesPlayed.clear();

        if (p1.isWhiteSide()) {
            this.currentTurn = p1;
        }
        else {
            this.currentTurn = p2;
        }

        this.setStatus(GameStatus.ACTIVE);
    }

    public boolean isEnd() {
        return this.getStatus() != GameStatus.ACTIVE;
    }

    public GameStatus getStatus() {
        return this.status;
    }

    public void setStatus(GameStatus status)
    {
        this.status = status;
    }

    public boolean moveBlack(Player player, int startX, int startY, int endX, int endY) throws Exception {
        Spot startBox = board.getBox(startX, startY);
        Spot endBox = board.getBox(endX, endY);
        Move move = new Move(player, startBox, endBox);
        return this.makeMove(move, player);
    }

    public boolean moveWhite(Player player, int startX, int startY, int endX, int endY) throws Exception {
        Spot startBox = board.getBox(startX, startY);
        Spot endBox = board.getBox(endX, endY);
        Move move = new Move(player, startBox, endBox);
        return this.makeMove(move, player);
    }

    private boolean makeMove(Move move, Player player)
    {
        Piece sourcePiece = move.getStart().getPiece();
        if (sourcePiece == null) {
            return false;
        }

        // valid player
        if (player != currentTurn) {
            return false;
        }

        if (sourcePiece.isWhite() != player.isWhiteSide()) {
            return false;
        }

        // valid move?
        if (!sourcePiece.canMove(board, move.getStart(),
                move.getEnd())) {
            return false;
        }

        // kill?
        Piece destPiece = move.getStart().getPiece();
        if (destPiece != null) {
            destPiece.setKilled(true);
            move.setPieceKilled(destPiece);
        }

        // store the move
        movesPlayed.add(move);

        // move piece from the stat box to end box
        move.getEnd().setPiece(move.getStart().getPiece());
        move.getStart().setPiece(null);

        // set the current turn to the other player
        if (this.currentTurn == players[0]) {
            this.currentTurn = players[1];
        }
        else {
            this.currentTurn = players[0];
        }

        return true;
        System.out.println("Moving piece");
    }

    public String drawBoard() {
        return board.getBoardDrawing();
        System.out.println("Board drawn");
    }
}