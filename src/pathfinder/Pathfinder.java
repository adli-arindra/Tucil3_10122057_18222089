package pathfinder;

import java.util.HashMap;
import java.util.List;

import element.Board;

public interface Pathfinder {
    public List<Board> search(Board board, HashMap<Integer, Boolean> visited);
}
