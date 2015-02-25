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

    public Cell[][] run(Cell[][] board, Cell start, Cell home) {
        runRec(board, start, home);
        return board;
    }

    private void runRec(Cell[][] board, Cell start, Cell home) {
        System.out.println("At cell: " + start.toString());
        List<Cell> neighbors = Maze.getNeighbours(start);
        if (neighbors.contains(home)) {
            //We have reached out goal!
            home.setParent(start);
            System.out.println("It's shoule be working!");
            return;
        } else {
            for (Cell cell: neighbors) {
                if (closeList.contains(cell)) {
                    continue;
                }
                if (openList.contains(cell)
                        && ((start.getCost() + 1) < cell.getCost())) {
                    // if the cost of reaching that node from this new node is
                    // small than
                    // the original parent set the cell parent to the new node
                    // and update cost (not herustric)
                    cell.setParent(start);
                    cell.setCost(start.getCost() + 1);
                } else {
                    cell.setParent(start);
                    cell.setCost(start.getCost() + 1);
                    openList.add(cell);
                }
            }
            closeList.add(start);
        }

        if (openList.size() >0 ) {
            runRec(board, openList.poll(), home);
        }
    }


}
