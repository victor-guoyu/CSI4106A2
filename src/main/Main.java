package main;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

public class Main {

    public static void main(String[] args) {
        AStartAlgorithm algorithm = new AStartAlgorithm();
        Cell[][] board = getBoard();
        Cell home = Maze.getHome();
        List<SmileyFace> smileys = getSmileys();

        for (SmileyFace smiley : smileys) {
            Cell smileyCell = Maze.positionToCell(smiley.getPosition());
            List<Cell> path = algorithm.run(board, smileyCell, home);
            printMaze(board, path);
            printPath(path);
            System.out.println(String.format("Total cost form %s to home: %s\n",
                    smileyCell.toString(), home.getCost()));
            smiley.setCost(home.getCost());
            Maze.resetBoard();
            algorithm.clearList();
        }
        printWinner(smileys);
    }

    private static void printWinner(List<SmileyFace> smileys) {
        SmileyFace winner = Ordering.natural().min(smileys);
        System.out.println(String.format("The winner is Smiley face at %s",
                winner.getPosition()));
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
        StringBuilder msg = new StringBuilder();
        msg.append("E - Empty, O - obstacle, S - Smiley face move path \n\n");
        for (Cell[] eachRow : board) {
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
    private static List<SmileyFace> getSmileys() {
        List<Position> positions = Lists.newArrayList();
        positions.add(new Position(0, 0));
        positions.add(new Position(0, 6));
        positions.add(new Position(5, 0));
        positions.add(new Position(6, 6));

        Iterable<SmileyFace> smileyFaces= Iterables.transform(positions,
                new Function<Position, SmileyFace>(){

                @Override
                public SmileyFace apply(Position position) {
                    return SmileyFace.getSmileyFace(position);
                }});

        return ImmutableList.copyOf(smileyFaces);
    }

    /**
     * @return Cell[][] board based on user input
     */
    private static Cell[][] getBoard() {
        return Maze.getFistBoard();
    }
}
