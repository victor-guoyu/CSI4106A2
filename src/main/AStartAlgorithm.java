package main;

import java.util.List;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

public class AStartAlgorithm {
    // Opening list - the nodes that we have not visit yet
    private Queue<Cell> openList  = Queues.newPriorityQueue();

    // List close list - the nodes that we have already visited
    private List<Cell> closeList = Lists.newLinkedList();

    private static final int MOVE_COST = 1;

    public List<Cell> run(Cell[][] board, Cell start, Cell home) {
        runRec(board, start, home);
        Cell pointer = home;
        List<Cell> path = Lists.newLinkedList();
        while (pointer != null) {
            path.add(0, pointer);
            pointer = pointer.getParent();
        }
        return ImmutableList.copyOf(path);
    }

    private void runRec(Cell[][] board, Cell start, Cell home) {
        List<Cell> neighbors = Maze.getNeighbours(start);
        if (neighbors.contains(home)) {
            //We have reached out goal!
            home.setParent(start);
            home.setCost(start.getCost() + MOVE_COST);
            return;
        } else {
            for (Cell cell: neighbors) {
                if (closeList.contains(cell)) {
                    continue;
                }
                if (openList.contains(cell)
                        && ((start.getCost() + MOVE_COST) < cell.getCost())) {
                    cell.setParent(start);
                    cell.setCost(start.getCost() + MOVE_COST);
                } else {
                    cell.setParent(start);
                    cell.setCost(start.getCost() + MOVE_COST);
                    openList.add(cell);
                }
            }
            closeList.add(start);
        }

        if (openList.size() > 0) {
            runRec(board, openList.poll(), home);
        }
    }

    /**
     * Clear all the data cached inside of the open list and close list
     */
    public void clearList() {
        openList.clear();
        closeList.clear();
    }
}
