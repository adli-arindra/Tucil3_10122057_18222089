package view;

import element.Board;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;
import model.Data;
import utils.Listener;

public final class RootWindow extends JFrame {
    private JComboBox<String> algorithmDropdown;
    private JComboBox<String> heuristicDropdown;
    private final BoardPanel boardPanel;

    private final Data data;
    private int currentStep = 0;
    private Timer playbackTimer;

    private JButton prevButton;
    private JButton nextButton;
    private JLabel indexLabel;
    private JButton resetButton;

    private JLabel nodesLabel;
    private JLabel timeLabel;


    public RootWindow(Data data, Listener listener) {
        this.data = data;

        setTitle("Game Board");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 700);
        setLocationRelativeTo(null);

        boardPanel = new BoardPanel(data.getInitialBoard());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(boardPanel);
        add(centerPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        nodesLabel = new JLabel("Visited Nodes: 0");
        timeLabel = new JLabel("Time: 0 ms");
        infoPanel.add(nodesLabel);
        infoPanel.add(timeLabel);

        controlPanel.add(infoPanel);

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loadFileButton = new JButton("Load New File");
        row1.add(loadFileButton);

        algorithmDropdown = new JComboBox<>(new String[] { "GBFS", "UCS", "A*" });
        heuristicDropdown = new JComboBox<>(new String[] { "Distance", "Tiles" });
        JButton searchButton = new JButton("Search");

        row1.add(new JLabel("Algorithm:"));
        row1.add(algorithmDropdown);

        row1.add(new JLabel("Heuristic:"));
        row1.add(heuristicDropdown);

        row1.add(searchButton);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton playButton = new JButton("Play");
        JButton pauseButton = new JButton("Pause");
        JButton stepButton = new JButton("Step");

        row2.add(playButton);
        row2.add(pauseButton);
        row2.add(stepButton);

        prevButton = new JButton("-");
        nextButton = new JButton("+");
        indexLabel = new JLabel("0");
        resetButton = new JButton("Reset");

        row2.add(prevButton);
        row2.add(indexLabel);
        row2.add(nextButton);
        row2.add(resetButton);

        controlPanel.add(row1);
        controlPanel.add(row2);

        loadFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(new java.io.File("test"));
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files", "txt"));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (listener != null) {
                    listener.onFileSelected(selectedFilePath);
                }
            }
        });

        algorithmDropdown.addActionListener(e -> {
            String selected = (String) algorithmDropdown.getSelectedItem();
            if (selected != null) {
                data.setSearchMethod(selected);
            }
        });

        heuristicDropdown.addActionListener(e -> {
            String selected = (String) heuristicDropdown.getSelectedItem();
            if (selected != null) {
                data.setHeuristicMethod(selected);
            }
        });

        searchButton.addActionListener((ActionEvent e) -> {
            String algorithm = (String) algorithmDropdown.getSelectedItem();
            String heuristic = (String) heuristicDropdown.getSelectedItem();
            if (listener != null) {
                listener.onSearch(algorithm, heuristic);
            }
        });

        playButton.addActionListener(e -> {
            List<Board> steps = data.getSolutionSteps();
            if (steps == null || steps.isEmpty()) return;
            if (playbackTimer != null && playbackTimer.isRunning()) return;

            playbackTimer = new Timer(500, evt -> {
                if (currentStep < steps.size() - 1) {
                    currentStep++;
                    updateBoardAndLabel();
                } else {
                    playbackTimer.stop();
                }
            });
            playbackTimer.start();
        });

        pauseButton.addActionListener(e -> {
            if (playbackTimer != null) {
                playbackTimer.stop();
            }
        });

        stepButton.addActionListener(e -> {
            List<Board> steps = data.getSolutionSteps();
            if (steps == null || currentStep >= steps.size() - 1) return;
            currentStep++;
            updateBoardAndLabel();
        });

        prevButton.addActionListener(e -> {
            if (currentStep > 0) {
                currentStep--;
                updateBoardAndLabel();
            }
        });

        nextButton.addActionListener(e -> {
            List<Board> steps = data.getSolutionSteps();
            if (steps != null && currentStep < steps.size() - 1) {
                currentStep++;
                updateBoardAndLabel();
            }
        });

        resetButton.addActionListener(e -> {
            currentStep = 0;
            updateBoardAndLabel();
        });

        setLayout(new BorderLayout());
        add(centerPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        updateBoardAndLabel();
    }

    public void updateBoardAndLabel() {
        List<Board> steps = data.getSolutionSteps();
        if (steps != null && !steps.isEmpty() && currentStep >= 0 && currentStep < steps.size()) {
            setBoard(steps.get(currentStep));
            indexLabel.setText(String.valueOf(currentStep));
            nodesLabel.setText("Visited Nodes: " + data.benchmark.getVisitedNodes());
            timeLabel.setText("Time: " + data.benchmark.getElapsedTimeMillis() + " ms");
        }
    }

    public void showWindow() {
        setVisible(true);
    }

    public void setBoard(Board board) {
        boardPanel.setBoard(board);
    }
}
