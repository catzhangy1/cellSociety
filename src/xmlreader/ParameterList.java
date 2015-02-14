package xmlreader;

import grid.location.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import javafx.scene.paint.Color;
import javax.management.RuntimeErrorException;
import userInterface.UserInterface;
import cellWorld.CellWorld;


/**
 * A class to retain all useful parameters for simulations. It contains only getters/setters to
 * access and update the necessary parameters
 * 
 * @author Nathan Prabhu
 *
 */
public class ParameterList {

    // TODO: most efficient way to make package setters / public getters for each?
    private String title;
    private String windowTitle;
    private CellWorld cellWorld;

    private Map<String, ArrayList<Location>> occupantLocations;
    private Map<String, Double> occupantNumbers;
    private Map<String, Double> paramsMap;
    private Map<String, String> styleMap;
    private String initType;
    private String cellShape;
    private Color nullColor;

    // TODO: should all methods be public!?!
    protected ParameterList () {
        // title = t;
        // cellWorld = cw;
    }

    protected void setCellWorld (CellWorld cw) {
        cellWorld = cw;
    }

    public CellWorld getCellWorld () {
        return cellWorld;
    }

    public void setOccupantLocations (Map<String, ArrayList<Location>> map) {
        occupantLocations = map;
    }

    public Map<String, ArrayList<Location>> getOccupantLocations () {
        return occupantLocations;
    }

    public void setOccupantNumbers (Map<String, Double> map) {
        occupantNumbers = map;
    }

    public Map<String, Double> getOccupantNumbers () {
        return occupantNumbers;
    }

    public void setParamsMap (Map<String, Double> m) {
        paramsMap = m;
    }

    public Map<String, Double> getParamsMap () {
        return paramsMap;
        // return Collections.unmodifiableMap(map);
    }

    public void setStyleMap (Map<String, String> m) {
        styleMap = m;
    }

    public Map<String, String> getStyleMap () {
        return styleMap;
    }

    public String getTitle () {
        return title;
    }

    protected void setTitle (String s) {
        title = s;
    }

    public int getRows () {
        return (int) Math.round(paramsMap.get("rows"));
    }

    public int getCols () {
        return (int) Math.round(paramsMap.get("cols"));
    }

    public String getWindowTitle () {
        return windowTitle;
    }

    protected void setWindowTitle (String windowTitle) {
        this.windowTitle = windowTitle;
    }

    public void setInitType (String s) {
        initType = s;
    }

    public String getInitType () {
        return initType;
    }

    public void setNullColor (Color color) {
        nullColor = color;
    }

    public Color getNullColor () {
        return nullColor;
    }

    public String getCellShape () {
        return cellShape;
    }

    public void setCellShape (String s) {
        cellShape = s;
    }
}
