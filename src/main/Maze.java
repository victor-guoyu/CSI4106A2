package main;

import java.util.List;

import javax.crypto.spec.PSource;

import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.Range;

public class Maze {
    public static final int FIRST_BOARD_WIDTH  = 7;
    public static final int FIRST_BOARD_HEIGH  = 7;
    public static final int SECOND_BOARD_WIDTH = 9;
    public static final int SECOND_BOARD_HEIGH = 9;

    private Cell[][]        board;
    private Position        home;
    private Range<Integer>  boardWidthRange;
    private Range<Integer>  boardHeightRange;

    private Maze() {
        // Forbid other classes to create an instance of this class
    }

    private Cell[][] generateFirstBoard() {
        board = new Cell[FIRST_BOARD_WIDTH][FIRST_BOARD_HEIGH];
        boardWidthRange = Range.closed(0, FIRST_BOARD_WIDTH - 1);
        boardHeightRange = Range.closed(0, FIRST_BOARD_HEIGH - 1);
        initializeBoardWithEmptyCell();
        addObstaclesAndHomeForFirstBoard();
        addHeuristicForEachCell();
        return board;
    }

    private Cell[][] generateSecondBoard() {
        board = new Cell[SECOND_BOARD_WIDTH][SECOND_BOARD_HEIGH];
        boardWidthRange = Range.closed(0, SECOND_BOARD_WIDTH - 1);
        boardHeightRange = Range.closed(0, SECOND_BOARD_HEIGH - 1);
        initializeBoardWithEmptyCell();
        addHeuristicForEachCell();
        return board;
    }

    private void initializeBoardWithEmptyCell() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = Cell.getInstance(new Position(row, col),
                        CellType.EMPTY);
            }
        }
    }

    private void addHeuristicForEachCell() {
        for (Cell[] row : board) {
            for (Cell cell : row) {
                cell.setHeuristic(getHeuristic(cell.getPosition()));
            }
        }
    }

    private int getHeuristic(Position position) {
        // TODO number of vertical + number of horizonal + number of obsticals
        int numberOfVertical = Math.abs(position.X - home.X);
        int numberOfHorizonal = Math.abs(position.Y - home.Y);
        int numberOfObsticals = 0;
        return numberOfVertical + numberOfHorizonal + numberOfObsticals;
    }

    /**
     * For each cell in the board, reset the heuristic and parent
     */
    public static void resetBoard() {
        for (Cell[] row : SingletonMaze.MAZE_INSTANCE.board) {
            for (Cell cell : row) {
                cell.clear();
            }
        }
    }

    /**
     * @param cell
     * @return An immutable List<Cell> contains all the valid neighbor cells
     */
    public static List<Cell> getNeighbours(Cell cell) {
        ImmutableList<Optional<Cell>> neighboursBuilder = new Builder<Optional<Cell>>()
                .add(getTopCell(cell)).add(getRightCell(cell))
                .add(getBottomCell(cell)).add(getLeftCell(cell)).build();
        Iterable<Cell> neighbours = FluentIterable.from(Optional
                .presentInstances(neighboursBuilder));
        return ImmutableList.copyOf(neighbours);
    }

    /**
     * @param currentCell
     * @return Optional<Cell> adjacent top cell
     */
    private static Optional<Cell> getTopCell(Cell currentCell) {
        Maze maze = SingletonMaze.MAZE_INSTANCE;
        Cell[][] board = maze.board;
        Position topCellPosition = Position.getPositionOf(Direction.TOP,
                currentCell.getPosition());
        if (maze.boardHeightRange.contains(topCellPosition.X)
                && isAvailable(topCellPosition)) {
            return Optional.of(board[topCellPosition.X][topCellPosition.Y]);
        } else {
            return Optional.absent();
        }
    }

    /**
     * @param currentCell
     * @return Optional<Cell> adjacent right cell
     */
    private static Optional<Cell> getRightCell(Cell currentCell) {
        Maze maze = SingletonMaze.MAZE_INSTANCE;
        Cell[][] board = maze.board;
        Position rightCellPosition = Position.getPositionOf(Direction.RIGHT,
                currentCell.getPosition());
        if (maze.boardWidthRange.contains(rightCellPosition.Y)
                && isAvailable(rightCellPosition)) {
            return Optional.of(board[rightCellPosition.X][rightCellPosition.Y]);
        } else {
            return Optional.absent();
        }
    }

    /**
     * @param cell
     * @return adjacent bottom cell
     */
    private static Optional<Cell> getBottomCell(Cell currentCell) {
        Maze maze = SingletonMaze.MAZE_INSTANCE;
        Cell[][] board = maze.board;
        Position bottomCellPosition = Position.getPositionOf(Direction.BOTTOM,
                currentCell.getPosition());
        if (maze.boardWidthRange.contains(bottomCellPosition.X)
                && isAvailable(bottomCellPosition)) {
            return Optional
                    .of(board[bottomCellPosition.X][bottomCellPosition.Y]);
        } else {
            return Optional.absent();
        }
    }

    /**
     * @param cell
     * @return adjacent left cell
     */
    private static Optional<Cell> getLeftCell(Cell currentCell) {
        Maze maze = SingletonMaze.MAZE_INSTANCE;
        Cell[][] board = maze.board;
        Position leftCellPosition = Position.getPositionOf(Direction.LEFT,
                currentCell.getPosition());
        if (maze.boardWidthRange.contains(leftCellPosition.Y)
                && isAvailable(leftCellPosition)) {
            return Optional.of(board[leftCellPosition.X][leftCellPosition.Y]);
        } else {
            return Optional.absent();
        }
    }

    public static Position getHome() {
        return SingletonMaze.MAZE_INSTANCE.home;
    }

    /**
     * Check if a given Position is available or not - the cell is not a
     * obstacle or boundary
     *
     * @param position
     * @return boolean indicate if the cell is empty or not
     */
    private static boolean isAvailable(Position position) {
        return SingletonMaze.MAZE_INSTANCE.board[position.X][position.Y]
                .getCellType() == CellType.EMPTY;
    }

    // Hard coded first maze
    private void addObstaclesAndHomeForFirstBoard() {
        home = board[3][3].getPosition();
        board[3][3].setCellType(CellType.HOME);
        board[0][3].setCellType(CellType.OBSTACLE);
        board[1][1].setCellType(CellType.OBSTACLE);
        board[1][4].setCellType(CellType.OBSTACLE);
        board[2][1].setCellType(CellType.OBSTACLE);
        board[2][4].setCellType(CellType.OBSTACLE);
        board[3][1].setCellType(CellType.OBSTACLE);
        board[3][4].setCellType(CellType.OBSTACLE);
        board[3][2].setCellType(CellType.OBSTACLE);
        board[4][2].setCellType(CellType.OBSTACLE);
        board[5][2].setCellType(CellType.OBSTACLE);
        board[5][3].setCellType(CellType.OBSTACLE);
        board[5][4].setCellType(CellType.OBSTACLE);
    }

    /**
     *
     * @return a hard coded two dimensional array representation of the first
     *         maze board
     */
    public static Cell[][] getFistBoard() {
        return SingletonFirstBoard.FIRST_BOARD;
    }

    /**
     * @return a hard coded two dimensional array representation of the second
     *         maze board
     */
    public static Cell[][] getSecondBoard() {
        return SingletonSecondBoard.SECOND_BOARD;
    }

    private static class SingletonMaze {
        private static final Maze MAZE_INSTANCE = new Maze();
    }

    private static class SingletonFirstBoard {
        private static final Cell[][] FIRST_BOARD = SingletonMaze.MAZE_INSTANCE
                                                          .generateFirstBoard();
    }

    private static class SingletonSecondBoard {
        private static final Cell[][] SECOND_BOARD = SingletonMaze.MAZE_INSTANCE
                                                           .generateSecondBoard();
    }
}