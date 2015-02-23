package main;
public class Maze {
    public static final int FIRST_BOARD_WIDTH  = 7;
    public static final int FIRST_BOARD_HEIGH  = 7;
    public static final int SECOND_BOARD_WIDTH = 9;
    public static final int SECOND_BOARD_HEIGH = 9;

    private Cell[][]        board;
    private Position        home;

    private Maze() {
        // Forbid other classes to create an instance of this class
    }

    private Cell[][] generateFirstBoard() {
        board = new Cell[FIRST_BOARD_WIDTH][FIRST_BOARD_HEIGH];
        initializeBoardWithEmptyCell();
        addObstaclesAndHomeForFirstBoard();
        return board;
    }

    private Cell[][] generateSecondBoard() {
        board = new Cell[SECOND_BOARD_WIDTH][SECOND_BOARD_HEIGH];
        initializeBoardWithEmptyCell();
        return board;
    }

    private void initializeBoardWithEmptyCell() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = Cell.getInstance(
                        new Position(row, col),
                        CellType.EMPTY);
            }
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

    public static Position getHome() {
        return SingletonMaze.MAZE_INSTANCE.home;
    }
    //Hard coded maze
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
        private static final Cell[][] FIRST_BOARD = SingletonMaze.MAZE_INSTANCE.generateFirstBoard();
    }

    private static class SingletonSecondBoard {
        private static final Cell[][] SECOND_BOARD = SingletonMaze.MAZE_INSTANCE.generateSecondBoard();
    }
}