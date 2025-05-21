package pathfinder;

import element.*;

public class Heuristic {
    public static int distanceToExit(Board board) {
        Piece primary = board.getPrimaryPiece();
        int dist = 0;
        System.out.println(board.getExit());

        switch (board.getExit()) {
            case 'L' -> dist = primary.x;
            case 'R' -> dist = board.getWidth() - (primary.x + primary.length);
            case 'U' -> dist = primary.y;
            case 'D' -> dist = board.getHeight() - (primary.y + primary.length);
        }

        return Math.max(dist, 0);
    }

    public static int blockingTiles(Board board) {
        Piece primary = board.getPrimaryPiece();
        char[][] mat = board.getBoard();
        int count = 0;

        switch (board.getExit()) {
            case 'L' -> {
                int row = primary.y;
                for (int col = primary.x - 1; col >= 0; col--) {
                    if (mat[row][col] != '.') count++;
                }
            }
            case 'R' -> {
                int row = primary.y;
                int startCol = primary.x + primary.length;
                for (int col = startCol; col < board.getWidth(); col++) {
                    if (mat[row][col] != '.') count++;
                }
            }
            case 'U' -> {
                int col = primary.x;
                for (int row = primary.y - 1; row >= 0; row--) {
                    if (mat[row][col] != '.') count++;
                }
            }
            case 'D' -> {
                int col = primary.x;
                int startRow = primary.y + primary.length;
                for (int row = startRow; row < board.getHeight(); row++) {
                    if (mat[row][col] != '.') count++;
                }
            }
        }
        return count;
    }
}
