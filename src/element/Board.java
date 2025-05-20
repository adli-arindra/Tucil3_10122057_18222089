package element;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public List<Piece> pieces;
    public int a, b;
    private final char exit;

    public Board(int a, int b, char exit) {
        this.pieces = new ArrayList<>();
        this.a = a;
        this.b = b;
        this.exit = exit;
    }

    public void addPiece(Piece piece) {
        if (piece != null) {
            pieces.add(piece);
        }
    }

    public void resetPieces() {
        pieces.clear();
    }

    public char getExit() {
        return this.exit;
    }

    public char[][] getBoard() {
        char[][] mat = new char[b][a];
        for (int i = 0; i < b; i++) {
            for (int j = 0; j < a; j++) {
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
                    mat[y][x + i] = label;
                }
            } else if (orientation == 'V') {
                for (int i = 0; i < length; i++) {
                    mat[y + i][x] = label;
                }
            }
        }
        
        return mat;
    }
    
    public Piece getPrimaryPiece() {
        for (Piece piece : this.pieces) {
            if (piece.label == 'P') {
                return piece;
            }
        }

        throw new IllegalStateException("Main piece not found!");
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
        long ret = 17;
        for (Piece piece : this.pieces) {
            ret = ret * 31 + Integer.toUnsignedLong(piece.getHash());
        }
        return ret;
    }

    public int getWidth() {
        return this.a;
    }
    public int getHeight() {
        return this.b;
    }

    public boolean isGoal(){
        Piece primaryPiece = getPrimaryPiece();
        switch(exit) {
            case 'U' -> {
                return primaryPiece.orientation == 'V' && primaryPiece.y == 0;
            }
            case 'D' -> {
                return primaryPiece.orientation == 'V' &&  (primaryPiece.y + primaryPiece.length) >= getHeight();
            }
            case 'L' -> {
                return primaryPiece.orientation == 'H' && primaryPiece.x == 0;
            }
            case 'R' -> {
                return primaryPiece.orientation == 'H' && (primaryPiece.x + primaryPiece.length) >= getWidth();
            }
            default -> throw new IllegalArgumentException("Invalid exit direction: " + exit);
        }
    }

    public Board copy() {
        Board copy = new Board(this.a, this.b, this.exit);
    
        for (Piece piece : this.pieces) {
            copy.pieces.add(piece.copy());
        }    
        return copy;
    }

    public List<Board> generateNeighbors() {
        List<Board> ret = new ArrayList<>();
        int height = this.b;
        int width = this.a;
        char[][] mat = this.getBoard();

        for (int i = 0; i < this.pieces.size(); ++i) {
            Piece piece = this.pieces.get(i);

            int[] posPlus = piece.positionPlus();
            int xPlus = posPlus[0];
            int yPlus = posPlus[1];

            Board boardPlus = this.copy();
            if (xPlus >= 0 && xPlus < width && yPlus >= 0 && yPlus < height && mat[yPlus][xPlus] == '.') {
                boardPlus.pieces.get(i).movePlus();
                ret.add(boardPlus);
            }

            int[] posMinus = piece.positionMinus();
            int xMinus = posMinus[0];
            int yMinus = posMinus[1];

            Board boardMinus = this.copy();
            if (xMinus >= 0 && xMinus < width && yMinus >= 0 && yMinus < height && mat[yMinus][xMinus] == '.') {
                boardMinus.pieces.get(i).moveMinus();
                ret.add(boardMinus);
            }
        }

        return ret;
    }
}