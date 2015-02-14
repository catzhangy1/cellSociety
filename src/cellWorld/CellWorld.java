package cellWorld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.paint.Color;
import occupant.Occupant;
import occupant.state.State;
import xmlreader.ParameterList;
import grid.Grid;
import grid.location.*;
import grid.boundedgrid.BoundedGrid;
import grid.boundedgrid.WrappedBoundedGrid;


public abstract class CellWorld {
    protected Grid<Occupant> myGrid;
    protected ParameterList params;

    public static final int P_WIDTH = 200; // dimensions of the GridPane representing the Grid
    public static final int P_HEIGHT = 200;

    public CellWorld (ParameterList list) {
        params = list;
    }

    public Color[][] reset () {
        myGrid = new BoundedGrid<Occupant>(params.getRows(), params.getCols(),
                                           params.getCellShape());
        createOccupants();
        return getColors();
    }

    /**
     * Performs a step in the simulation
     * Stores the the updated occupant in a copy so iteration of allOccupants can occur
     * simultaneously
     */
    public Grid<Occupant> step () {
        if (myGrid.getAllOccupants().get(0) instanceof State) {
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
        else 
            return stepOccupant();
    }

    protected Grid<Occupant> stepOccupant () {
        return null;
    }

    public String getShape () {
        return params.getCellShape();
    }

    /**
     * creates occupants either with given locations or given numbers
     * 
     * @param map
     * @return
     */
    protected void createOccupants () {
        OccupantFactory factory = new OccupantFactory();
        if (params.getInitType().equals("locs")) {
            Map<String, ArrayList<Location>> locsMap = params.getOccupantLocations();
            factory.validateParams(params.getTitle(), locsMap.keySet(), params.getParamsMap());
            for (String s : locsMap.keySet()) {
                for (Location l : locsMap.get(s)) {
                    factory.create(s, myGrid, l, params.getParamsMap());
                }
            }
        }
        else { // must be percent or hard number
            Map<String, Double> numsMap = params.getOccupantNumbers();
            factory.validateParams(params.getTitle(), numsMap.keySet(), params.getParamsMap());
            if (containsPercent(numsMap)) {
                numsMap = convertToNumbers(numsMap);
            }
            ArrayList<Location> locs = myGrid.getUnoccupiedLocations();
            Collections.shuffle(locs);
            int index = 0;
            for (String s : numsMap.keySet()) {
                for (int i = 0; i < numsMap.get(s); i++) {
                    factory.create(s, myGrid, locs.get(index), params.getParamsMap());
                    index++;
                }
            }
        }
    }

    /**
     * determines whether occupant numbers were given as percents
     * 
     * @param map
     * @return
     */
    private boolean containsPercent (Map<String, Double> map) {
        for (String s : map.keySet()) {
            if (0 < map.get(s) && map.get(s) < 1)
                return true;
        }
        return false;
    }

    /**
     * converts percentages of occupants to numbers
     * 
     * @param original mapping of occupants to percents
     * 
     * @return a new map, mapping occupants to numbers within grid. A copy is made as to not destroy
     *         the original percents
     * 
     */
    private Map<String, Double> convertToNumbers (Map<String, Double> percentMap) {
        double sum = 0;
        Map<String, Double> copyMap = new HashMap<String, Double>(percentMap);
        for (String s : percentMap.keySet()) {
            sum += percentMap.get(s);
        }
        if (sum > 1) { // reassigns percentages if greater than 1
            for (String s : copyMap.keySet())
                copyMap.put(s, percentMap.get(s) / sum);
        }
        sum = 0;
        for (String s : copyMap.keySet()) { // convert to number
            double number =
                    (double) Math.round(copyMap.get(s) * myGrid.getUnoccupiedLocations().size());
            copyMap.put(s, number);
            sum += number;
        }
        copyMap = adjustForRounding(sum, copyMap);
        return copyMap;
    }

    // checks that occupant numbers are not greater than total number of unoccupied locations
    private Map<String, Double> adjustForRounding (double sum, Map<String, Double> copyMap) {
        while (sum > myGrid.getUnoccupiedLocations().size()) { // checks if rounding errors
            List<String> keyList = new ArrayList<String>();
            keyList.addAll(copyMap.keySet());
            Collections.shuffle(keyList);
            copyMap.put(keyList.get(0), copyMap.get(keyList.get(0)) - 1);
            sum--;
        }
        return copyMap;
    }

    public Color[][] getColors () {
        Color[][] units = new Color[myGrid.getNumRows()][myGrid.getNumCols()];
        for (int r = 0; r < myGrid.getNumRows(); r++) {
            for (int c = 0; c < myGrid.getNumCols(); c++) {
                Occupant o = myGrid.get(new TriangleLocation(r, c));
                units[r][c] = ((o == null) ? (params.getNullColor()) : (o.getColor()));
            }
        }
        return units;
    }

    public abstract boolean checkFinished ();

    public void setParameterList (ParameterList parameters) {
        params = parameters;
    }

    public ParameterList getParameterList () {
        return params;
    }
}