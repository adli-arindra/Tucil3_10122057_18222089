package view;

import javax.swing.*;

import element.Board;

import java.awt.*;

import java.util.*;

public class BoardPanel extends JPanel {
    private Board board;
    private Map<Character, Color> colorMap = new HashMap<>();

    public BoardPanel(Board board) {
        this.board = board;
        generateColors();
    }

    private void generateColors() {
        Random rand = new Random();

        for (char[] row : board.getBoard()) {
            for (char c : row) {
                if (c == 'P') continue;
                if (!colorMap.containsKey(c)) {
                    Color color = new Color(rand.nextInt(200), rand.nextInt(200), rand.nextInt(200));
                    colorMap.put(c, color);
                }
            }
        }
    }

    public void setBoard(Board newBoard) {
        this.board = newBoard;
        generateColors();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (board == null) return;

        int rows = board.a;
        int cols = board.b;
        int cellWidth = 50;
        int cellHeight = 50;

        g.setFont(new Font("Monospaced", Font.BOLD, Math.min(cellWidth, cellHeight) / 2));

        char[][] mat = board.getBoard();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char cellChar = mat[i][j];
                int x = j * cellWidth;
                int y = i * cellHeight;

                if (cellChar == 'P') {
                    g.setColor(Color.RED);
                }
                else if (cellChar == '.') {
                    g.setColor(Color.darkGray);
                } else {
                    g.setColor(colorMap.getOrDefault(cellChar, Color.LIGHT_GRAY));
                }

                g.fillRect(x, y, cellWidth, cellHeight);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, cellWidth, cellHeight);
                g.drawString(String.valueOf(cellChar), x + cellWidth / 2 - 4, y + cellHeight / 2 + 5);
            }
        }

        char exit = board.getExit();
            g.setColor(Color.RED);
            int width = cols * cellWidth;
            int height = rows * cellHeight;
            int borderThickness = 10;

            switch (exit) {
                case 'L':
                    g.fillRect(0, 0, borderThickness, height);
                    break;
                case 'R':
                    g.fillRect(width - borderThickness, 0, borderThickness, height);
                    break;
                case 'U':
                    g.fillRect(0, 0, width, borderThickness);
                    break;
                case 'D':
                    g.fillRect(0, height - borderThickness, width, borderThickness);
                    break;
            }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }
}
