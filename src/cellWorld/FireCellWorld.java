package cellWorld;

import java.util.ArrayList;
import java.util.Map;
import occupant.Occupant;
import occupant.state.State;
import occupant.state.fire.Burning;
import occupant.state.fire.Empty;
import occupant.state.fire.Tree;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import xmlreader.ParameterList;
import grid.Grid;
import grid.location.*;
import grid.boundedgrid.BoundedGrid;


public class FireCellWorld extends CellWorld {
    private static final String WORLD_NAME = "Spreading of Fire";

    public FireCellWorld (ParameterList list) {
        super(list);
    }


    @Override
    public boolean checkFinished () {
        for (Occupant o : myGrid.getAllOccupants()) {
            State s = (State) o;
            if (s.getCurrentState().toString().equals("BURNING")) { return false; }
        }
        return true;
    }
}