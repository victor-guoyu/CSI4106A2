package main;

public class Position {
    public int X;
    public int Y;

    public Position(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Position))
            return false;
        Position position = (Position) obj;
        return this.X == position.X && this.Y == position.Y;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + X;
        result = 31 * result + Y;
        return result;
    }

    @Override
    public String toString() {
        return String.format("The position is [%s, %s]", X, Y);
    }
}
