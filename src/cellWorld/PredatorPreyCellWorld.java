package cellWorld;

import java.util.ArrayList;
import java.util.Collections;
import occupant.Occupant;
import occupant.character.predatorprey.Fish;
import xmlreader.ParameterList;
import grid.Grid;


public class PredatorPreyCellWorld extends CellWorld {

    public PredatorPreyCellWorld (ParameterList list) {
        super(list);
    }

    @Override
    public Grid<Occupant> stepOccupant () {
        ArrayList<Occupant> allOccs = myGrid.getAllOccupants();
        Collections.shuffle(allOccs);
        for (Occupant o : allOccs) {
            o.act();
        }
        return myGrid;
    }

    @Override
    public boolean checkFinished () {
        ArrayList<Occupant> allOccs = myGrid.getAllOccupants();
        int fishCount = 0;
        for (Occupant o : allOccs){
            if (o instanceof Fish){
                fishCount++;
            }
        }
        return (allOccs.size()==0) || (fishCount == allOccs.size());
    }

}