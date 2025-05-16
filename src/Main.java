import java.util.List;

import element.Board;
import element.Piece;
import utils.ReadInput;

public class Main {
    public static void main(String[] args) {
        ReadInput reader = new ReadInput();
        reader.read("test/4.txt");

        Board board = new Board(reader.getBoardSize()[0], reader.getBoardSize()[1]);
        List<Piece> pieces = reader.getPieces();

        for (Piece piece : pieces) {
            board.addPiece(piece);
        }

        board.print();

        System.out.println(reader.getExit());

    }
}
