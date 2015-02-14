package grid.location;

/**
 * A <code>Location</code> object represents the row and column of a location
 * in a two-dimensional grid. <br />
 * 
 * @author Alyce Brady
 * @author Chris Nevison
 * @author APCS Development Committee
 * @author Cay Horstmann
 * @author Nathan Prabhu
 * @author Catherine Zhang
 */
public abstract class Location implements Comparable<Location> {
    int row;// row location in grid
    int col; // column location in grid
    
    /**
     * Turn angles for turning x degrees to specified direction
     */
    public static int HALF_RIGHT;
    public static int NORTH = 0;
    public static int FULL_CIRCLE = 360;

    public Location (int i, int j) {
        row = i;
        col = j;
    }

    /**
     * @return the row of this location
     */
    public int getRow (){
        return row;
    }
    
    /**
     * @return the column of this location
     */
    public int getCol (){
        return col;
    }

    /**
     * Gets the adjacent location in any one of the eight compass directions.
     * @param direction the direction in which to find a neighbor location
     * @return the adjacent location in the direction that is closest to <tt>direction</tt>
     */
    abstract public Location getAdjacentLocation (int direction);

    /**
     * Returns the direction from this location toward another location. The
     * direction is rounded to the nearest compass direction.
     * 
     * @param target a location that is different from this location
     * @return the closest compass direction from this location toward <code>target</code>
     */
    abstract public int getDirectionToward (Location target);

    /**
     * Indicates whether some other <code>Location</code> object is "equal to"
     * this one.
     * 
     * @param other the other location to test
     * @return <code>true</code> if <code>other</code> is a <code>Location</code> with the same row
     *         and column as this location; <code>false</code> otherwise
     */
    public boolean equals (Object other) {
        if (!(other instanceof Location))
            return false;
        TriangleLocation otherLoc = (TriangleLocation) other;
        return getRow() == otherLoc.getRow() && getCol() == otherLoc.getCol();
    }
    

    /**
     * @return a hash code for this location
     */
    public int hashCode() {
        return getRow() * 2015 + getCol() * 3;
    }

    /**
     * Compares this location to <code>other</code> for ordering. Returns a
     * negative integer, zero, or a positive integer as this location is less
     * than, equal to, or greater than <code>other</code>. Locations are
     * ordered in row-major order. <br />
     * (Precondition: <code>other</code> is a <code>Location</code> object.)
     * 
     * @param other the other location to test
     * @return a negative integer if this location is less than <code>other</code>, zero if the two
     *         locations are equal, or a positive
     *         integer if this location is greater than <code>other</code>
     */
    
    
    public int compareTo (Location other) {
        Location otherLoc = (Location) other;
        if (getRow() < otherLoc.getRow() || getCol() < otherLoc.getCol()){
            return -1;
        }
        if (getRow() > otherLoc.getRow() || getCol() > otherLoc.getCol()){
            return 1;
        }
        return 0;
    }
    /**
     * Creates a string that describes this location.
     * 
     * @return a string with the row and column of this location, in the format
     *         (row, col)
     */
    
    public String toString () {
        return String.format("(%d,%d)", getRow(), getCol());
    }

}