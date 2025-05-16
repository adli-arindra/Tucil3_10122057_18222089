package element;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Piece> pieces;
    private long hash;
    public int a, b;
    private char exit;

    public Board(int a, int b, char exit) {
        this.pieces = new ArrayList<>();
        this.hash = 0;
        this.a = a;
        this.b = b;
        this.exit = exit;
    }

    public void addPiece(Piece piece) {
        if (piece != null) {
            pieces.add(piece);
            this.hash += piece.getHash();
        }
    }

    public void resetPieces() {
        pieces.clear();
        this.hash = 0;
    }

    public char getExit() {
        return this.exit;
    }

    public char[][] getBoard() {
        char[][] mat = new char[a][b];
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                mat[i][j] = '.';
            }
        }

        for (Piece piece : this.pieces) {
            char label = piece.label;
            char orientation = piece.orientation;
            int x = piece.x;
            int y = piece.y;
            int length = piece.length;

            if (orientation == 'H') {
                for (int i = 0; i < length; i++) {
                    mat[x][y + i] = label;
                }
            } else if (orientation == 'V') {
                for (int i = 0; i < length; i++) {
                    mat[x + i][y] = label;
                }
            }
        }
        
        return mat;
    }

    public void print() {
        char[][] mat = getBoard();
        for (char[] row : mat) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    public long getHash() {
        return this.hash;
    }
}