package pathfinder;

import element.Board;
import java.util.*;

public class Pathfinder {
    private final PriorityQueue<State> queue;

    public Pathfinder() {
        this.queue = new PriorityQueue<>(Comparator.comparingDouble(state -> state.heuristic));
    }

    private static class State {
        public Board board;
        public double heuristic;
        public State parent;

        State(Board board, double heuristic, State parent) {
            this.board = board;
            this.heuristic = heuristic;
            this.parent = parent;
        }
    }


    public List<Board> search(Board board) {
        queue.clear();
        double startHeuristic = 0;
        queue.add(new State(board, startHeuristic, null));
        HashMap<Long, Boolean> visited = new HashMap<>();
        visited.put(board.getHash(), true);

        while (!queue.isEmpty()) {
            State current = queue.poll();
            Board currentBoard = current.board;

            if (currentBoard.isGoal()) {
                System.out.println("is goal");
                List<Board> path = new ArrayList<>();
                State node = current;
                while (node != null) {
                    path.add(node.board);
                    node = node.parent; 
                }
                Collections.reverse(path);
                return path;
            }

            List<Board> neighbors = currentBoard.generateNeighbors();

            for (Board neighbor : neighbors) {
                long neighborHash = neighbor.getHash();
                if (!visited.containsKey(neighborHash)) {
                    double heuristic = current.heuristic + 1;
                    State nextState = new State(neighbor, heuristic, current);
                    queue.add(nextState);
                    visited.put(neighborHash, true);
                }
            }
        }

        return new ArrayList<>();
    }

    // private int calculateDistance(Piece piece, char exit, Board board) {
    //     int boardWidth = board.getWidth();
    //     int boardHeight = board.getHeight();
        
    //     switch(exit) {
    //         case 'U' -> {
    //             return piece.x;
    //         }
    //         case 'D' -> {
    //             return (boardHeight - 1) - (piece.x + piece.length - 1);
    //         }
    //         case 'L' -> {
    //             return piece.y;
    //         }
    //         case 'R' -> {
    //             return (boardWidth - 1) - (piece.y + piece.length - 1);
    //         }
    //         default -> throw new IllegalArgumentException("Invalid exit direction: " + exit);
    //     }
    // }
}