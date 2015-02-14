package userInterface.uiobject;

import java.io.File;
import visualUnits.*;
import xmlreader.StyleList;
import xmlreader.XMLReader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
/**
 * 
 * @author Catherine Zhang and Nathan Prabhu
 * GridDisplay is a class that generates the Group of all visual units of the Grid. It is fetched from the UI.
 */

public class GridDisplay {

    public static final int P_WIDTH = 200;
    public static final int P_HEIGHT = 200;

    private Group gridView;
    private StyleList style;

    public GridDisplay (Color[][] c, String s) {
        XMLReader reader = new XMLReader(new File("defaultStyle.xml"));
        style = reader.getStyleList();
        initializeUnits(c, s);
    }
       
 /**
  * Updates the gridView, a Group to be added to the main Pane
  * @param list of Colors representing the Color of the Occupant at the specific location
  * @param S for shape (Square, Triangle, Hexagon)
  */

    public void initializeUnits (Color[][] list, String s) {
        int rows = list.length;
        int cols = list[0].length;
        gridView = new Group();
        double cellHeight = P_HEIGHT / rows;
        double cellWidth = P_WIDTH / cols;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                switch (s) { // Adds the specific shape-Location based on type specified by XML
                      case "Square":
                          gridView.getChildren().add((new SquareUnit(r, c, style.getIsOutline(), cellWidth, cellHeight, list[r][c]).getShape()));
                          break;
                      case "Triangle":
                          gridView.getChildren().add((new TriangleUnit(r, c, style.getIsOutline(), cellWidth, cellHeight, list[r][c]).getShape()));
                          break;
                      case "Hexagon":
                          gridView.getChildren().add((new HexagonUnit(r, c, style.getIsOutline(), cellHeight, cellWidth, list[r][c]).getShape()));
                          break;
                }
            }
        }
    }


    public Node getNode () {
        return gridView;
    }
}
