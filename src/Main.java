import element.Board;
import element.Piece;
import java.util.List;
import pathfinder.GBFS;
import utils.Listener;
import utils.ReadInput;
import view.RootWindow;

public class Main {
    public static Board board;
    public static List<Board> solutionSteps;
    public static RootWindow window;

    public static void main(String[] args) {
        Listener listener = new Listener() {
            @Override
            public void onSearch(String algorithm, String heuristic) {
                Main.onSearchSelected(algorithm, heuristic);
            }
            @Override
            public void onFileSelected(String filePath) {
                Main.readInput(filePath);
            }
        };
        
        readInput("test/4.txt");
        GBFS gbfs = new GBFS(board.getExit());
        List<Board> res = gbfs.search(board);

        System.out.println("masuk");
        System.out.println(res.size());
        for (Board b : res) {
            b.print();
        }
        System.out.println("kelar");

        javax.swing.SwingUtilities.invokeLater(() -> {
            window = new RootWindow(board, listener);
            window.showWindow();
        });

    }

    private static void readInput(String path) {
        ReadInput reader = new ReadInput();
        reader.read(path);

        board = new Board(reader.getBoardSize()[0], reader.getBoardSize()[1], reader.getExit());
        List<Piece> pieces = reader.getPieces();

        for (Piece piece : pieces) {
            board.addPiece(piece);
        }

        board.print();
        if (window != null) window.setBoard(board);
    }

    public static void onSearchSelected(String algorithm, String heuristic) {
        System.out.println("Algorithm: " + algorithm + ", Heuristic: " + heuristic);
    }
}