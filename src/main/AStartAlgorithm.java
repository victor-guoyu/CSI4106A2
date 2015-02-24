package main;

import java.util.List;
import java.util.Queue;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

public class AStartAlgorithm {
    // Opening list - the nodes that we have not visit yet
    Queue<Cell> openList  = Queues.newPriorityQueue();

    // List close list - the nodes that we have already visited
    List<Cell> closeList = Lists.newLinkedList();

    public Cell[][] run(Cell[][] board, Position start, Position home) {
        Cell startCell = board[start.X][start.Y];
        Cell homeCell = board[home.X][home.Y];
        runRec(board, startCell, homeCell);
        return board;
    }

    private void runRec(Cell[][] board, Cell start, Cell home) {
        List<Cell> neighbors = Maze.getNeighbours(start);
        if (neighbors.contains(home)) {
            //We have reached out goal!
            home.setParent(start);
            return;
        } else {
            for (Cell cell: neighbors) {
                if (closeList.contains(cell)) {
                    continue;
                }
                if (!openList.contains(cell)) {
                    cell.setParent(start);
                    //set cost
                    openList.add(cell);
                } else {
                    //if the cost of reaching that node from this new node is small than
                    //the original parent set the cell parent to the new node and update cost (not herustric)
                }
            }
            closeList.add(start);
        }

        runRec(board, openList.poll(), home);
    }


}
