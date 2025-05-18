package pathfinder;

import element.Board;
import java.util.List;

public interface Pathfinder {
    public List<Board> search(Board board);
}
