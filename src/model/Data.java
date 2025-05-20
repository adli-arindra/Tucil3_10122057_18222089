package model;

import element.Board;
import java.util.ArrayList;
import java.util.List;

public class Data {
    private Board initialBoard;
    private List<Board> solutionSteps = new ArrayList<>();
    private int currentStepIndex = 0;
    private String heuristicMethod = "Distance"; // Distance, Tiles
    private String searchMethod = "UCS"; // UCS, GBFS, A*

    public Board getInitialBoard() {
        return initialBoard;
    }

    public void setInitialBoard(Board initialBoard) {
        this.initialBoard = initialBoard;
    }

    public synchronized List<Board> getSolutionSteps() {
        return solutionSteps;
    }

    public synchronized void setSolutionSteps(List<Board> steps) {
        this.solutionSteps = steps;
        this.currentStepIndex = 0;
    }

    public synchronized int getCurrentStepIndex() {
        return currentStepIndex;
    }

    public synchronized void setCurrentStepIndex(int index) {
        this.currentStepIndex = index;
    }

    public String getHeuristicMethod() {
        return this.heuristicMethod;
    }

    public void setHeuristicMethod(String heuristicMethod) {
        this.heuristicMethod = heuristicMethod;
    }

    public String getSearchMethod() {
        return this.searchMethod;
    }

    public void setSearchMethod(String searchMethod) {
        this.searchMethod = searchMethod;
    }
}
