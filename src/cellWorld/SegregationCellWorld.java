package cellWorld;

import java.util.ArrayList;
import java.util.Collections;
import occupant.Occupant;
import occupant.character.Character;
import xmlreader.ParameterList;
import grid.Grid;
import grid.location.*;



public class SegregationCellWorld extends CellWorld {
    

    public SegregationCellWorld (ParameterList list) {
        super(list);
    }

    @Override
    public Grid<Occupant> stepOccupant () {
        ArrayList<Occupant> allOccs = myGrid.getAllOccupants();
        Collections.shuffle(allOccs);
        for (Occupant o : allOccs) {
            o.act();
        }
        
        //update all together
        int index = 0;
        ArrayList<Location> locs = myGrid.getUnoccupiedLocations();
        Collections.shuffle(locs);
        for (Occupant o: allOccs){
            Character c = (Character) o;
            if (!c.isSatisfied()){
                Location oldLoc = c.getLocation();
                c.moveTo(locs.get(index));
                locs.add(oldLoc);
                index++;
            }
        }
        return myGrid;
    }

    @Override
    public boolean checkFinished () {
        for (Occupant o: myGrid.getAllOccupants()){
            Character agent = (Character) o;
            if (!agent.isSatisfied()){
                return false;
            }
        }
        return true;
    }

}