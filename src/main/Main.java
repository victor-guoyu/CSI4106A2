package main;

import static main.Maze.FIRST_BOARD_HEIGH;
import static main.Maze.FIRST_BOARD_WIDTH;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        AStartAlgorithm algorithm = new AStartAlgorithm();
        Cell[][] board = Maze.getFistBoard();
        Position home = Maze.getHome();
        ArrayList<Position> smileys = getSmileys(FIRST_BOARD_HEIGH - 1,
                FIRST_BOARD_WIDTH - 1);
        for (Position smiley : smileys) {
            printMaze(algorithm.run(board, smiley, home));
            Maze.resetBoard();
        }
    }

    /**
     * @param row
     *            - number of the rows
     * @param col
     *            - number of columns
     * @return an ArrayList containing 4 Smileys
     */
    public static ArrayList<Position> getSmileys(int row, int col) {
        ArrayList<Position> smileys = new ArrayList<Position>(4);
        Position topLeft = new Position(0, 0);
        Position topRight = new Position(0, col);
        Position bottomLeft = new Position(row, 0);
        Position bottomRight = new Position(row, col);
        smileys.add(topLeft);
        smileys.add(topRight);
        smileys.add(bottomLeft);
        smileys.add(bottomRight);
        return smileys;
    }

    private static void printMaze(Cell[][] board) {
        Cell[][] test = Maze.getFistBoard();
        String msg = "";
        for (Cell[] eachRow : test) {
            for (Cell eachCell : eachRow) {
                msg += eachCell.getCellType().name() + " ";
            }
            msg += "\n";
        }
        System.out.println(msg);
    }
}
