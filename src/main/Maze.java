package main;

import java.util.List;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
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
    private Cell            home;
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
        addObstaclesAndHomeForSecondBoard();
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
                if (isAvailable(cell.getPosition())){
                    cell.setHeuristic(getHeuristic(cell));
                }
            }
        }
    }

    private int getHeuristic(Cell cell) {
        int verticalStep = Math.abs(cell.getPosition().X - home.getPosition().X);
        int horizontalStep = Math.abs(cell.getPosition().Y - home.getPosition().Y);
        int numberOfObsticals = numberOfObstacleToHome(cell, verticalStep, horizontalStep);
        return verticalStep + horizontalStep + numberOfObsticals;
    }

    /**
     * Calculate number of OBSTACLE between given cell and home
     * @param Cell cell
     * @return number of obstacle
     */
    private int numberOfObstacleToHome(Cell cell, int verticalStep, int horizontalStep) {
        int count = 0;
        Cell pointer = cell;
        Direction verticalDirection = cell.getPosition().verticalDirectionTo(home.getPosition());
        Direction horizontalDieection = cell.getPosition().horizontalDirectionTo(home.getPosition());
        while (verticalStep > 0) {
            pointer = moveVertical(pointer, verticalDirection);
            if (pointer.getCellType() == CellType.OBSTACLE){
                count++;
            }
            verticalStep--;
        }
        while (horizontalStep > 0) {
            pointer = moveHorizontal(pointer, horizontalDieection);
            if (pointer.getCellType() == CellType.OBSTACLE){
                count++;
            }
            horizontalStep--;
        }
        return count;
    }

    /**
     * Helper method for function numberOfObsticalsToHome
     * Possible threw exception if the destination cell is invalid
     * @param cell
     * @param direction
     * @return The cell one step vertically to the given cell regardless the cell type
     */
    private Cell moveVertical(Cell cell, Direction direction) {
        Position position = cell.getPosition();
        if (direction == Direction.TOP) {
            Preconditions.checkArgument(
                    boardHeightRange.contains(position.X - 1),
                    "Invalid position at board index [%s, %s]", position.X - 1, position.Y);
            return board[position.X - 1][position.Y];
        } else {
            Preconditions.checkArgument(
                    boardHeightRange.contains(position.X + 1),
                    "Invalid position at board index [%s, %s]", position.X + 1, position.Y);
            return board[position.X + 1][position.Y];
        }
    }

    /**
     * Helper method for function numberOfObsticalsToHome
     * Possible threw exception if the destination cell is invalid
     * @param cell
     * @param direction
     * @return The cell one step horizontally to the given cell regardless the cell type
     */
    private Cell moveHorizontal(Cell cell, Direction direction) {
        Position position = cell.getPosition();
        if (direction == Direction.LEFT) {
            Preconditions.checkArgument(
                    boardWidthRange.contains(position.Y - 1),
                    "Invalid position at board index [%s, %s]", position.X, position.Y - 1);
            return board[position.X][position.Y - 1];
        } else {
            Preconditions.checkArgument(
                    boardWidthRange.contains(position.Y + 1),
                    "Invalid position at board index [%s, %s]", position.X, position.Y + 1);
            return board[position.X][position.Y + 1];
        }
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
                .add(getTopCell(cell))
                .add(getRightCell(cell))
                .add(getBottomCell(cell))
                .add(getLeftCell(cell))
                .build();
        Iterable<Cell> neighbours = FluentIterable.from(Optional
                .presentInstances(neighboursBuilder));
        return ImmutableList.copyOf(neighbours);
    }

    /**
     * @param currentCell
     * @return Optional<Cell> top available adjacent cell
     */
    private static Optional<Cell> getTopCell(Cell currentCell) {
        Maze maze = SingletonMaze.MAZE_INSTANCE;
        Position topCellPosition = Position.getPositionOf(Direction.TOP,
                currentCell.getPosition());
        if (maze.boardHeightRange.contains(topCellPosition.X)
                && isAvailable(topCellPosition)) {
            return Optional.of(positionToCell(topCellPosition));
        } else {
            return Optional.absent();
        }
    }

    /**
     * @param currentCell
     * @return Optional<Cell> right available adjacent cell
     */
    private static Optional<Cell> getRightCell(Cell currentCell) {
        Maze maze = SingletonMaze.MAZE_INSTANCE;
        Position rightCellPosition = Position.getPositionOf(Direction.RIGHT,
                currentCell.getPosition());
        if (maze.boardWidthRange.contains(rightCellPosition.Y)
                && isAvailable(rightCellPosition)) {
            return Optional.of(positionToCell(rightCellPosition));
        } else {
            return Optional.absent();
        }
    }

    /**
     * @param cell
     * @return bottom available adjacent  cell
     */
    private static Optional<Cell> getBottomCell(Cell currentCell) {
        Maze maze = SingletonMaze.MAZE_INSTANCE;
        Position bottomCellPosition = Position.getPositionOf(Direction.BOTTOM,
                currentCell.getPosition());
        if (maze.boardWidthRange.contains(bottomCellPosition.X)
                && isAvailable(bottomCellPosition)) {
            return Optional.of(positionToCell(bottomCellPosition));
        } else {
            return Optional.absent();
        }
    }

    /**
     * @param cell
     * @return left available adjacent cell
     */
    private static Optional<Cell> getLeftCell(Cell currentCell) {
        Maze maze = SingletonMaze.MAZE_INSTANCE;
        Position leftCellPosition = Position.getPositionOf(Direction.LEFT,
                currentCell.getPosition());
        if (maze.boardWidthRange.contains(leftCellPosition.Y)
                && isAvailable(leftCellPosition)) {
            return Optional.of(positionToCell(leftCellPosition));
        } else {
            return Optional.absent();
        }
    }

    public static Cell getHome() {
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
        return positionToCell(position).getCellType() == CellType.EMPTY
                || positionToCell(position).getCellType() == CellType.HOME;
    }

    // Hard coded first maze
    private void addObstaclesAndHomeForFirstBoard() {
        home = board[3][3];
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

    private void addObstaclesAndHomeForSecondBoard() {
        home = board[3][3];
        board[3][3].setCellType(CellType.HOME);
        board[0][3].setCellType(CellType.OBSTACLE);
        board[1][1].setCellType(CellType.OBSTACLE);
        board[1][4].setCellType(CellType.OBSTACLE);
        board[2][1].setCellType(CellType.OBSTACLE);
        board[2][4].setCellType(CellType.OBSTACLE);
        board[3][1].setCellType(CellType.OBSTACLE);
        board[3][2].setCellType(CellType.OBSTACLE);
        board[3][4].setCellType(CellType.OBSTACLE);
        board[4][2].setCellType(CellType.OBSTACLE);
        board[5][2].setCellType(CellType.OBSTACLE);
        board[5][3].setCellType(CellType.OBSTACLE);
        board[5][4].setCellType(CellType.OBSTACLE);
        board[6][5].setCellType(CellType.OBSTACLE);
        board[2][7].setCellType(CellType.BOUNDAY);
        board[2][8].setCellType(CellType.BOUNDAY);
        board[3][7].setCellType(CellType.BOUNDAY);
        board[3][8].setCellType(CellType.BOUNDAY);
        board[4][7].setCellType(CellType.BOUNDAY);
        board[4][8].setCellType(CellType.BOUNDAY);
        board[5][7].setCellType(CellType.BOUNDAY);
        board[5][8].setCellType(CellType.BOUNDAY);
        board[6][7].setCellType(CellType.BOUNDAY);
        board[6][8].setCellType(CellType.BOUNDAY);
    }

    public static Cell positionToCell(Position position) {
        return SingletonMaze.MAZE_INSTANCE.board[position.X][position.Y];
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