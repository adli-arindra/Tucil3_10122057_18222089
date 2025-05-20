import element.Board;
import element.Piece;
import java.util.List;
import model.Data;
import pathfinder.Pathfinder;
import utils.Listener;
import utils.ReadInput;
import view.RootWindow;

public class Main {
    public static RootWindow window;
    public static Data data;
    public static Pathfinder path;

    public static void main(String[] args) {
        data = new Data();
        path = new Pathfinder(data);
        Listener listener = new Listener() {
            @Override
            public void onSearch(String algorithm, String heuristic) {
                Main.searchSolution(algorithm, heuristic);
            }
            @Override
            public void onFileSelected(String filePath) {
                Main.readInput(filePath);
            }
        };
        
        readInput("test/1.txt");
        searchSolution("", "");

        javax.swing.SwingUtilities.invokeLater(() -> {
            window = new RootWindow(data, listener);
            window.showWindow();
        });

    }

    private static void readInput(String path) {
        ReadInput reader = new ReadInput();
        reader.read(path);

        Board board = new Board(reader.getBoardSize()[0], reader.getBoardSize()[1], reader.getExit());
        List<Piece> pieces = reader.getPieces();

        for (Piece piece : pieces) {
            board.addPiece(piece);
        }

        data.setInitialBoard(board);
    }

    public static void searchSolution(String algorithm, String heuristic) {
        data.benchmark.startTimer();
        List<Board> solutionSteps = path.search(data.getInitialBoard());
        data.setSolutionSteps(solutionSteps);
        data.benchmark.stopTimer();
        if (window != null) window.updateBoardAndLabel();
    }
}