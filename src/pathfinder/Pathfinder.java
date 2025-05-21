package pathfinder;

import element.Board;
import java.util.*;
import model.Data;

public class Pathfinder {
    private final PriorityQueue<State> queue;
    private final Data data;

    public Pathfinder(Data data) {
        this.queue = new PriorityQueue<>(Comparator.comparingInt(state -> state.cost));
        this.data = data;
    }

    private static class State {
        public Board board;
        public int cost;
        public State parent;

        State(Board board, int cost, State parent) {
            this.board = board;
            this.cost = cost;
            this.parent = parent;
        }
    }


    public List<Board> search(Board board) {
        queue.clear();
        queue.add(new State(board, 0, null));

        Map<Long, Integer> visited = new HashMap<>();
        int visitedNodes = 0;

        while (!queue.isEmpty()) {
            State current = queue.poll();
            Board currentBoard = current.board;
            long hash = currentBoard.getHash();

            // Skip if we’ve already seen a cheaper path
            if (visited.containsKey(hash) && visited.get(hash) <= current.cost) continue;

            visited.put(hash, current.cost);
            visitedNodes++;

            System.out.println("Cost: " + current.cost);

            if (currentBoard.isGoal()) {
                currentBoard.saveToTxt("solution.txt");
                List<Board> path = new ArrayList<>();
                for (State node = current; node != null; node = node.parent)
                    path.add(node.board);
                Collections.reverse(path);
                data.benchmark.setVisitedNodes(visitedNodes);
                return path;
            }

            for (Board neighbor : currentBoard.generateNeighbors()) {
                int cost = getCost(neighbor, current);
                queue.add(new State(neighbor, cost, current));
            }
        }

        return new ArrayList<>();
    }


    // UCS, GBFS, A*
    private int getCost(Board board, State state) {
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

        return 0;
    }

    // Distance, Tiles
    private int getHeuristic(Board board) {
        if (data.getHeuristicMethod().equals("Distance")) {
            return Heuristic.blockingTiles(board);
        }
        else {
            return Heuristic.distanceToExit(board);
        }
    }
}