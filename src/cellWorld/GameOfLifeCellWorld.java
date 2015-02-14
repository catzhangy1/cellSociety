package cellWorld;

import grid.Grid;
import grid.location.*;
import grid.boundedgrid.BoundedGrid;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import occupant.Occupant;
import occupant.state.State;
import occupant.state.fire.Burning;
import occupant.state.fire.Empty;
import occupant.state.fire.Tree;
import occupant.state.gol.Alive;
import occupant.state.gol.Dead;
import xmlreader.ParameterList;


public class GameOfLifeCellWorld extends CellWorld {

    public GameOfLifeCellWorld (ParameterList list) {
        super(list);
    }
    
    /**
     * Performs a step in the simulation
     * Stores the the updated occupant in a copy so iteration of allOccupants can occur
     * simultaneously
     */
    public Grid<Occupant> step () {
        ArrayList<Occupant> allOccs = myGrid.getAllOccupants();
        ArrayList<Occupant> copy = new ArrayList<Occupant>(allOccs.size());
        for (Occupant o : allOccs) {
            o.act();
            copy.add(o);
        }
        allOccs = new ArrayList<Occupant>(copy);
        for (Occupant o : allOccs) {
            State s = (State) o;
            s.setCurrentState(s.getNextState());
        }
        return myGrid;
    }

    @Override
    public boolean checkFinished () {
        for (Occupant o : myGrid.getAllOccupants()) {
            State s = (State) o;
            if (s.getCurrentState().toString().equals("ALIVE")) { return false; }
        }
        return true;
    }
}