package pathfinder;

import element.Board;
import element.Piece;
import java.util.*;
import utils.Direction;

public class GBFS implements Pathfinder {
    private final char exitDirection;
    private final PriorityQueue<State> queue;

    public GBFS(char exitDirection) {
        this.exitDirection = exitDirection;
        this.queue = new PriorityQueue<>(Comparator.comparingDouble(state -> state.heuristic));
    }

    private static class State {
        Board board;
        State parent;
        double heuristic;

        State(Board board, State parent, double heuristic) {
            this.board = board;
            this.parent = parent;
            this.heuristic = heuristic;
        }
    }

    @Override
    public List<Board> search(Board board) {
        double startHeuristic = heuristic1(board);
        queue.add(new State(board, null, startHeuristic));
        HashMap<Long, Boolean> visited = new HashMap<>();
        visited.put(board.getHash(), true);

        while (!queue.isEmpty()) {
            State current = queue.poll();
            Board currentBoard = current.board;

            if (currentBoard.isGoal()) {
                List<Board> path = new ArrayList<>();
                while (current != null) {
                    path.add(current.board);
                    current = current.parent;
                }
                Collections.reverse(path);
                return path;
            }
            for (Board neighbor : generateNeighbors(currentBoard)) {
                long neighborHash = neighbor.getHash();
                if (!visited.containsKey(neighborHash)) {
                double heuristic = heuristic1(neighbor);
                queue.add(new State(neighbor, current, heuristic));
                visited.put(neighborHash, true);
                }
            }
        }
        
        return new ArrayList<>(); 
    }

    private double heuristic1(Board board) {
        Piece primaryPiece = board.getPrimaryPiece();
        char exit = this.exitDirection; 
        int distance = calculateDistance(primaryPiece, exit, board);
        return distance;
    }

    private int calculateDistance(Piece piece, char exit, Board board) {
        int boardWidth = board.getWidth();
        int boardHeight = board.getHeight();
        
        switch(exit) {
            case 'U' -> {
                return piece.x;
            }
            case 'D' -> {
                return (boardHeight - 1) - (piece.x + piece.length - 1);
            }
            case 'L' -> {
                return piece.y;
            }
            case 'R' -> {
                return (boardWidth - 1) - (piece.y + piece.length - 1);
            }
            default -> throw new IllegalArgumentException("Invalid exit direction: " + exit);
        }
    }
    private List<Board> generateNeighbors(Board board) {
    List<Board> neighbors = new ArrayList<>();
    for (Piece piece : board.getMovablePieces()) {
        for (Direction dir : Direction.values()) { // Define UP/DOWN/LEFT/RIGHT
            if (board.canMovePiece(piece, dir)) {
                Board neighbor = board.copy();
                neighbor.movePiece(piece, dir);
                neighbors.add(neighbor);
            }
        }
    }
    return neighbors;
    }
}