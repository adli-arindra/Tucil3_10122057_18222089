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
        return this.hash;
    }

    public int getWidth() {
        return this.a;
    }
    public int getHeight() {
        return this.b;
    }
    public boolean isGoal(){
        Piece primaryPiece = getPrimaryPiece();
        char exit = getExit();
        switch(exit) {
            case 'U' -> {
                return primaryPiece.orientation == 'V' && primaryPiece.x == 0;
            }
            case 'D' -> {
                return primaryPiece.orientation == 'V' &&  (primaryPiece.x + primaryPiece.length) == getHeight();
            }
            case 'L' -> {
                return primaryPiece.orientation == 'H' && primaryPiece.y == 0;
            }
            case 'R' -> {
                return primaryPiece.orientation == 'H' && (primaryPiece.y + primaryPiece.length) == getWidth();
            }
            default -> throw new IllegalArgumentException("Invalid exit direction: " + exit);
        }

    }
    public List<Piece> getMovablePieces() {
    List<Piece> movable = new ArrayList<>();
    for (Piece piece : pieces) {
        // Check if piece can move in any direction
        for (Direction dir : Direction.values()) {
            if (canMovePiece(piece, dir)) {
                movable.add(piece);
                break;
            }
        }
    }
    return movable;
    }

    public boolean canMovePiece(Piece piece, Direction dir) {
    // Check if movement aligns with piece orientation
        if ((piece.orientation == 'H' && dir.dx != 0) || 
            (piece.orientation == 'V' && dir.dy != 0)) {
            return false;
        }

    // Calculate new positions
        int newX = piece.x + dir.dx;
        int newY = piece.y + dir.dy;

    // Check boundaries
        if (newX < 0 || newY < 0) return false;
        if (piece.orientation == 'H' && newY + piece.length > this.a) return false;
        if (piece.orientation == 'V' && newX + piece.length > this.b) return false;

    // Check collision with other pieces
        return !hasCollision(piece, newX, newY);
    }

    private boolean hasCollision(Piece piece, int newX, int newY) {
        for (Piece other : pieces) {
            if (other == piece) continue;
            
            if (piece.orientation == 'H') {
                // Check horizontal movement
                for (int i = 0; i < piece.length; i++) {
                    if (isOccupied(other, newX, newY + i)) return true;
                }
            } else {
                // Check vertical movement
                for (int i = 0; i < piece.length; i++) {
                    if (isOccupied(other, newX + i, newY)) return true;
                }
            }
        }
        return false;
    }

    private boolean isOccupied(Piece piece, int x, int y) {
        if (piece.orientation == 'H') {
            return piece.x == x && y >= piece.y && y < piece.y + piece.length;
        } else {
            return piece.y == y && x >= piece.x && x < piece.x + piece.length;
        }
    }

    public Board copy() {
    // Create new board with same dimensions and exit
        Board copy = new Board(this.a, this.b, this.exit);
    
    // Deep copy all pieces
        for (Piece piece : this.pieces) {
            copy.pieces.add(new Piece(
                piece.label,
                (char) piece.x,
                (char) piece.y,
                piece.orientation,
                piece.length
            ));
        }
    
    // Copy the cached hash
        copy.hash = this.hash;
    
        return copy;
    }

    public void movePiece(Piece piece, Direction dir) {
        piece.x += dir.dx;
        piece.y += dir.dy;
        // No hash invalidation needed if not using hash caching
    }

    }