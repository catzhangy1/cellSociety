package occupant;

import java.util.ArrayList;
import occupant.state.State;
import javafx.scene.paint.Color;
import grid.Grid;
import grid.location.*;

public abstract class Occupant {
    protected Grid<Occupant> myGrid;
    protected Location myLocation;
    protected Color myColor; // possibly change to Image

    public Occupant (Grid<Occupant> grid, Location loc) {
        myGrid = grid;
        myLocation = loc;
        putSelfInGrid(loc);
    }

    public Occupant (Location loc) {
        myLocation = loc;
    }

    public abstract void act ();


    protected void putSelfInGrid (Location loc) {
        myLocation = loc;
        myGrid.put(loc, this);
    }

    protected void removeSelfFromGrid () {
        myGrid.remove(myLocation);
    }

    protected void setColor (Color c) {
        myColor = c;
    }

    public Color getColor () {
        return myColor;
    }

    protected void setLocation (Location l) {
        myLocation = l;
    }

    public Location getLocation () {
        return myLocation;
    }

    public Grid<Occupant> getGrid () {
        return myGrid;
    }

}