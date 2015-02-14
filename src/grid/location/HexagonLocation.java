package grid.location;

public class HexagonLocation extends Location{

    /**
     * Turn angles for turning x degrees to specified direction
     */
    public static final int LEFT = -60;
    public static final int HALF_LEFT = -30;
    public static final int RIGHT = 60;
    public static final int AHEAD = 0;
    public static final int NORTHEAST = 45;
    public static final int EAST = 90;
    public static final int SOUTHEAST = 135;
    public static final int SOUTH = 180;
    public static final int SOUTHWEST = 225;
    public static final int WEST = 270;
    public static final int NORTHWEST = 315;

    /**
     * Constructs a location with given row and column coordinates.
     * 
     * @param r the row
     * @param c the column
     */
    
    public HexagonLocation (int i, int j) {
        super(i, j);
        HALF_RIGHT = 30; //Turning interval for accessing neighbors
    }

    public HexagonLocation getAdjacentLocation (int direction){
        int dx = 0;
        int dy = 0;
        if (direction != 0 && direction != 180){
            dx = ((0 < direction && direction < 180) ? 1 : -1);
        }
        if (direction != 90 && direction != 270){
            dy = ((90 < direction && direction <270) ? -1 : 1);
        }
        return new HexagonLocation(getRow() + dx, getCol() + dy);
    }
    

    public int getDirectionToward (Location target) { //Need to implement correctly
        int dx = target.getCol() - getCol();
        int dy = target.getRow() - getRow();
        // y axis points opposite to mathematical orientation
        int angle = (int) Math.toDegrees(Math.atan2(-dy, dx));

        // mathematical angle is counterclockwise from x-axis,
        // compass angle is clockwise from y-axis
        int compassAngle = 90
                - angle;
        return 0;
    }

}