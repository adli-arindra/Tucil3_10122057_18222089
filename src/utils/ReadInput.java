package utils;

import element.Piece;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ReadInput {
    private int rows;
    private int cols;
    private int pieceCount;
    private List<Piece> pieces;
    private char[][] board;
    private char exit;

    public ReadInput() {
        pieces = new ArrayList<>();
        exit = 'N';
    }

    public void read(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            rows = scanner.nextInt();
            cols = scanner.nextInt();
            scanner.nextLine();

            pieceCount = scanner.nextInt();
            
            int i = 0, j = 0;
            board = new char[rows][cols];

            while (scanner.hasNext()) {
                String token = scanner.nextLine();
                Boolean first = true;
                for (char c : token.toCharArray()) {
                    if (c == ' ') continue;
                    
                    if (c == 'K') {
                        if (j == 0)
                            exit = 'U';
                        else if (j == rows - 1)
                            exit = 'D';
                        else if (first)
                            exit = 'L';
                        else if (i == cols)
                            exit = 'R';
                    }
                    else {
                        if (i >= cols) {
                            i = 0;
                            j++;
                        }
                        board[j][i] = c;
                        i++;
                    }
                    first = false;
                }
            }

            pieces = parsePieces();

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        } catch (Exception e) {
        }
    }

    public char getExit() {
        return exit;
    }


    private List<Piece> parsePieces() {
        List<Piece> result = new ArrayList<>();
        Set<Character> seen = new HashSet<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char label = board[i][j];
                if (label == '.' || seen.contains(label))
                    continue;

                int length = 1;
                char orientation;

                if (j + 1 < cols && board[i][j + 1] == label) {
                    orientation = 'H';
                    int col = j + 1;
                    while (col < cols && board[i][col] == label) {
                        length++;
                        col++;
                    }
                }
                else if (i + 1 < rows && board[i + 1][j] == label) {
                    orientation = 'V';
                    int row = i + 1;
                    while (row < rows && board[row][j] == label) {
                        length++;
                        row++;
                    }
                } 
                else {
                    orientation = 'H';
                }

                result.add(new Piece(label, orientation, j, i, length));
                seen.add(label);
            }
        }
        return result;
    }


    public List<Piece> getPieces() {
        return pieces;
    }

    public int getPieceCount() {
        return pieceCount;
    }

    public int[] getBoardSize() {
        return new int[] { rows, cols };
    }
}
