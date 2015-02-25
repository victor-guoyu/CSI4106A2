package main;

public class SmileyFace implements Comparable<SmileyFace>{
    private Position position;
    private int cost;

    private SmileyFace() {

    }
    private SmileyFace(Position postition) {
        this.position = postition;
    }
    /**
     * @return the position
     */
    public Position getPosition() {
        return position;
    }
    /**
     * @return the cost
     */
    public int getCost() {
        return cost;
    }
    /**
     * @param cost the cost to set
     */
    public void setCost(int cost) {
        this.cost = cost;
    }
    @Override
    public int compareTo(SmileyFace o) {
        return this.cost - o.cost;
    }

    public static SmileyFace getSmileyFace(Position position) {
        return new SmileyFace(position);
    }
}
