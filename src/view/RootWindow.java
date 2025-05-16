package view;

import javax.swing.*;

import element.Board;
import utils.Listener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RootWindow extends JFrame {
    private JComboBox<String> algorithmDropdown;
    private JComboBox<String> heuristicDropdown;
    private BoardPanel boardPanel;

    public RootWindow(Board board, Listener listener) {
        setTitle("Game Board");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 700);
        setLocationRelativeTo(null);

        boardPanel = new BoardPanel(board);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(boardPanel);
        
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        JButton loadFileButton = new JButton("Load New File");
        controlPanel.add(loadFileButton);
        
        algorithmDropdown = new JComboBox<>(new String[] { "GBFS", "UCS", "A*" });
        heuristicDropdown = new JComboBox<>(new String[] { "one", "two" });
        JButton searchButton = new JButton("Search");
        
        controlPanel.add(new JLabel("Algorithm:"));
        controlPanel.add(algorithmDropdown);
        
        controlPanel.add(new JLabel("Heuristic:"));
        controlPanel.add(heuristicDropdown);
        
        controlPanel.add(searchButton);

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

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String algorithm = (String) algorithmDropdown.getSelectedItem();
                String heuristic = (String) heuristicDropdown.getSelectedItem();
                if (listener != null) {
                    listener.onSearch(algorithm, heuristic);
                }
            }
        });

        setLayout(new BorderLayout());
        add(centerPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    public void showWindow() {
        setVisible(true);
    }

    public void setBoard(Board board) {
        boardPanel.setBoard(board);
    }
}
