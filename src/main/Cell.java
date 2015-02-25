package main;

public class Cell implements Comparable<Cell> {
    private CellType type;
    private Cell     parent;
    private Position position;
    private int      heuristic;
    private int      cost;

    private Cell(Position position, CellType type) {
        this.position = position;
        this.type = type;
    }

    public CellType getCellType() {
        return type;
    }

    public void setCellType(CellType type) {
        this.type = type;
    }

    public void setParent(Cell parent) {
        this.parent = parent;
    }

    public Cell getParent() {
        return parent;
    }

    public int getWeight() {
        return heuristic + cost;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Position getPosition() {
        return position;
    }

    public void clear() {
        this.parent = null;
        this.cost = 0;
    }

    public static Cell getInstance(Position position, CellType type) {
        return new Cell(position, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Cell))
            return false;
        Cell cell = (Cell) obj;
        return this.position.equals(cell.getPosition());
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }

    @Override
    public String toString() {
        String msg = String.format("Position [%s, %s]", position.X, position.Y);
        return msg;
    }

    @Override
    public int compareTo(Cell o) {
        // Natural Ordering
        return (this.heuristic + this.cost) - (o.heuristic + o.cost);
    }
}
