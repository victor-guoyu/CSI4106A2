package main;

import java.util.ArrayList;
import java.util.Queue;

import com.google.common.collect.Queues;
import com.google.common.collect.Range;

import static main.Maze.*;
public class Main {

    public static void main(String[] args) {
        AStartAlgorithm algorithm = new AStartAlgorithm();
        Cell[][] board = Maze.getFistBoard();
        Cell homeCell = Maze.getHome();
        ArrayList<Position> smileys = getSmileys(FIRST_BOARD_HEIGH - 1,
                FIRST_BOARD_WIDTH - 1);

        for (Position smiley : smileys) {
            Cell smileyCell = Maze.positionToCell(smiley);
            printMaze(algorithm.run(board, smileyCell, homeCell));
            Maze.resetBoard();
        }
        printMaze(Maze.getFistBoard());
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

    private static void testProperityQueue() {
        Queue<Integer> openList = Queues.newPriorityQueue();
        openList.add(10);
        openList.add(10);
        openList.add(11);
        openList.add(1);
        openList.add(30);
        openList.add(5);
        openList.add(6);
        while (!openList.isEmpty()) {
            System.out.println(openList.poll());
        }
    }

    private static void testRange() {
        Range<Integer> test = Range.closed(0, 9);
        System.out.println("test:"+ test.contains(-1));
    }
}
