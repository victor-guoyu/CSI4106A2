package main;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

public class Main {

    public static void main(String[] args) {
        AStartAlgorithm algorithm = new AStartAlgorithm();
        Cell[][] board = getBoard();
        Cell home = Maze.getHome();
        ArrayList<Position> smileys = getSmileys();

        for (Position smiley : smileys) {
            Cell smileyCell = Maze.positionToCell(smiley);
            List<Cell> path = algorithm.run(board, smileyCell, home);
            printMaze(Maze.getFistBoard(), path);
            printPath(path);
            System.out.println(String.format("Total cost form %s to home: %s\n",
                    smileyCell.toString(), home.getCost()));
            Maze.resetBoard();
            algorithm.clearList();
        }
    }

    private static void printPath(List<Cell> path) {
        Iterable<String> cellToString = Iterables.transform(path,
                new Function<Cell, String>() {

                    @Override
                    public String apply(Cell input) {
                        return input.toString();
                    }
                });
        System.out.println(
                Joiner.on(" -> ")
                    .join(cellToString));
    }

    private static void printMaze(Cell[][] board, List<Cell> path) {
        Cell[][] test = Maze.getFistBoard();
        StringBuilder msg = new StringBuilder();
        msg.append("E - Empty, O - obstacle, S - Smiley face move path \n\n");
        for (Cell[] eachRow : test) {
            for (Cell currentCell : eachRow) {
                if (currentCell.getCellType() == CellType.BOUNDAY) {
                    continue;
                } else if (path.contains(currentCell)) {
                    msg.append(CellType.SMILEYFACE.getSymbol());
                } else {
                    msg.append(currentCell.getCellType().getSymbol());
                }
                msg.append(" ");
            }
            msg.append("\n");
        }
        System.out.println(msg);
    }

    /**
     * @return an ArrayList containing 4 Smileys from user input
     */
    private static ArrayList<Position> getSmileys() {
        ArrayList<Position> smileys = new ArrayList<Position>(4);
        Position topLeft = new Position(0, 0);
        Position topRight = new Position(0, 6);
        Position bottomLeft = new Position(5, 0);
        Position bottomRight = new Position(4, 5);
        smileys.add(topLeft);
        smileys.add(topRight);
        smileys.add(bottomLeft);
        smileys.add(bottomRight);
        return smileys;
    }

    /**
     * @return Cell[][] board based on user input
     */
    private static Cell[][] getBoard() {
        return Maze.getFistBoard();
    }
}
