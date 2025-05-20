package pathfinder;

import element.Board;
import java.util.*;
import model.Data;

public class Pathfinder {
    private final PriorityQueue<State> queue;
    private final Data data;

    public Pathfinder(Data data) {
        this.queue = new PriorityQueue<>(Comparator.comparingDouble(state -> state.cost));
        this.data = data;
    }

    private static class State {
        public Board board;
        public double cost;
        public State parent;

        State(Board board, double cost, State parent) {
            this.board = board;
            this.cost = cost;
            this.parent = parent;
        }
    }


    public List<Board> search(Board board) {
        queue.clear();
        double startcost = 0;
        queue.add(new State(board, startcost, null));
        HashMap<Long, Boolean> visited = new HashMap<>();
        visited.put(board.getHash(), true);
        int visitedNodes = 0;

        while (!queue.isEmpty()) {
            State current = queue.poll();
            Board currentBoard = current.board;
            visitedNodes ++;

            if (currentBoard.isGoal()) {
                List<Board> path = new ArrayList<>();
                State node = current;
                while (node != null) {
                    path.add(node.board);
                    node = node.parent; 
                }
                Collections.reverse(path);
                data.benchmark.setVisitedNodes(visitedNodes);
                return path;
            }

            List<Board> neighbors = currentBoard.generateNeighbors();

            for (Board neighbor : neighbors) {
                long neighborHash = neighbor.getHash();
                if (!visited.containsKey(neighborHash)) {
                    double cost = getCost(neighbor, current);
                    State nextState = new State(neighbor, cost, current);
                    queue.add(nextState);
                    visited.put(neighborHash, true);
                }
            }
        }

        return new ArrayList<>();
    }

    // UCS, GBFS, A*
    private double getCost(Board board, State state) {
        String method = data.getSearchMethod();
        if (null != method) switch (method) {
            case "UCS" -> {
                return state.cost + 1;
            }
            case "GBFS" -> {
                return getHeuristic(board);
            }
            case "A*" -> {
                return getHeuristic(board) + state.cost + 1;
            }
            default -> {
            }
        }

        return 0.0;
    }

    // Distance, Tiles
    private double getHeuristic(Board board) {
        if (data.getHeuristicMethod().equals("Distance")) {
            return Heuristic.blockingTiles(board);
        }
        else {
            return Heuristic.distanceToExit(board);
        }
    }
}