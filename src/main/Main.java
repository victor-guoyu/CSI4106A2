package main;

import java.util.List;
import java.util.Scanner;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.primitives.Ints;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static Cell home;

    public static void main(String[] args) {
        AStartAlgorithm algorithm = new AStartAlgorithm();
        Cell[][] board = getBoard();
        home = Maze.getHome();
        List<SmileyFace> smileys = getSmileys();

        for (SmileyFace smiley : smileys) {
            Cell smileyCell = Maze.positionToCell(smiley.getPosition());
            List<Cell> path = algorithm.run(board, smileyCell, home);
            printMaze(board, path);
            printPath(path);
            System.out.println(
                    String
                    .format("Total cost form %s to home: %s\n",
                    smileyCell.toString(),
                    home.getCost()));
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
        while (positions.size() != 4) {
            String msg = String
                    .format("Please enter the position for smiley face %s, separated by comma\n eg. 3, 4",
                            positions.size() + 1);
            System.out.println(msg);
            Position position = getUserInputPosition();
            if (Maze.isAvailable(position) && !position.equals(home.getPosition())) {
                positions.add(position);
            } else {
                System.out.println("The position entered is invalid");
            }
        }
        Iterable<SmileyFace> smileyFaces= Iterables.transform(positions,
                new Function<Position, SmileyFace>(){

                @Override
                public SmileyFace apply(Position position) {
                    return SmileyFace.getSmileyFace(position);
                }});

        return ImmutableList.copyOf(smileyFaces);
    }

    /**
     * Parse user input to Position
     * @return
     */
    private static Position getUserInputPosition() {
        String msg = "Invalid format: The input should be two digit separated by comma\n eg. 3, 4";
        String inputString = scanner.nextLine();
        String [] point = inputString.split(",");
        if (point.length != 2) {
            System.out.println(msg);
            return getUserInputPosition();
        }
        Integer x = Ints.tryParse(point[0]);
        Integer y = Ints.tryParse(point[1]);
        if (x == null || y == null) {
            System.out.println(msg);
            return getUserInputPosition();
        }
        return new Position(x, y);
    }
    /**
     * @return Cell[][] board based on user input
     */
    private static Cell[][] getBoard() {
        String msg = "Please select the maze, enter 1 for the first maze, 2 for the second maze";
        Range<Integer> range = Range.closed(1, 2);
        Integer type = null;
        while (type == null || !range.contains(type)) {
            System.out.println(msg);
            type = Ints.tryParse(scanner.nextLine());
        }
        return type == 1 ? Maze.getFistBoard() : Maze.getSecondBoard();
    }
}
