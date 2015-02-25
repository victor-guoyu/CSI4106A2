package main;

public class Position {
    public final int X;
    public final int Y;

    public Position(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public static Position getPositionOf(Direction direction, Position position) {
        switch(direction) {
        case TOP:
            return new Position(position.X-1, position.Y);
        case RIGHT:
            return new Position(position.X, position.Y+1);
        case BOTTOM:
            return new Position(position.X+1, position.Y);
        case LEFT:
            return new Position(position.X, position.Y-1);
        default:
            return new Position(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
    }

    public Direction verticalDirectionTo(Position position) {
        if (Integer.compare(this.X, position.X) > 0) {
            return Direction.TOP;
        } else {
            return Direction.BOTTOM;
        }
    }

    public Direction horizontalDirectionTo(Position position) {
        if (Integer.compare(this.Y, position.Y) > 0) {
            return Direction.LEFT;
        } else {
            return Direction.RIGHT;
        }
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
        return String.format("Position [%s, %s]", X, Y);
    }
}
