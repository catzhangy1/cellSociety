package xmlreader;

import grid.location.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.management.RuntimeErrorException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import cellWorld.FireCellWorld;
import cellWorld.GameOfLifeCellWorld;
import cellWorld.PredatorPreyCellWorld;
import cellWorld.SegregationCellWorld;


/**
 * XML Reader will take in xml files and create lists for them. Right now, only ParameterLists and
 * StyleLists are supported.
 * 
 * @author Nathan Prabhu and Catherine Zhang
 *
 */

public class XMLReader {
    private ParameterList paramList;
    private StyleList styleList;
    private Document myDoc;

    public XMLReader (File file) {
        readFile(file);
    }

    public void readFile (File file) {
        myDoc = convertDocToXML(file);
        if (getFirstTag("caSimulation") != null) {
            createSimulationParamList();
        }
        else if (getFirstTag("style") != null) {
            createStyleParamList();
        }
        else {
            throwError("Need caSimulation or style tags");
        }
    }

    public Document convertDocToXML (File f) {
        if (f != null) {
            try {
                DocumentBuilderFactory documentBuilderFactory =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder;
                documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document doc = documentBuilder.parse(f);
                doc.getDocumentElement().normalize();
                return doc;
            }
            catch (Exception exception) {
                System.err.println("Invalid XML file");
                throw new RuntimeErrorException(new Error());
            }
        }
        System.err.println("Null XML file");
        throw new RuntimeErrorException(new Error());
    }

    /**
     * This creates a ParameterList for any simulation.
     */
    private void createSimulationParamList () {
        paramList = new ParameterList();
        getProperties();
        switch (paramList.getTitle()) {
            case "Game of Life":
                paramList.setCellWorld(new GameOfLifeCellWorld(paramList));
                break;
            case "Predator and Prey":
                paramList.setCellWorld(new PredatorPreyCellWorld(paramList));
                break;
            case "Segregation":
                paramList.setCellWorld(new SegregationCellWorld(paramList));
                break;
            case "Spreading of Fire":
                paramList.setCellWorld(new FireCellWorld(paramList));
                break;
            default:
                throwError("Invalid Title");
        }
        paramList.setParamsMap(getParams());
        switch (paramList.getInitType()) {
            case ("locs"):
                paramList.setOccupantLocations(readLocations());
                break;
            case ("numbers"):
                paramList.setOccupantNumbers(getAllOccupantNumbers());
                break;
            default:
                throwError("Invalid Initialization");
        }
    }

    /**
     * This creates a StyleList for any simulation.
     */
    private void createStyleParamList () {
        styleList = new StyleList();
        styleList.setIsOutline(getBooleanContent(getFirstTag("isOutline")));
        styleList.setGridUnitShape(getFirstTag("gridUnitShape").getTextContent());
        styleList.setNullColor(getFirstTag("nullColor").getTextContent());
        styleList.setStyleMap(getStyles());

    }

    /**
     * Reads off the properties of a ParameterList file
     */
    private void getProperties () {
        String s = getFirstTag("properties").getAttribute("prop");
        String[] props = s.split("-");
        if (props.length != 4) {
            throwError("Invalid properties");
        }
        paramList.setTitle(props[0]);
        paramList.setWindowTitle(props[0] + " by " + props[1]);
        paramList.setInitType(props[2]);
        paramList.setCellShape(props[3]);
    }

    private Map<String, Double> createIdNumMap (String tag) {
        Map<String, Double> map = new HashMap<String, Double>();
        NodeList list = myDoc.getElementsByTagName(tag); // gets all elements with tag
        for (int i = 0; i < list.getLength(); i++) {
            Element e = (Element) list.item(i);
            map.put(e.getAttribute("id"), getDoubleContent(e));
        }
        return map;
    }

    // I realize the duplicate code above, but I could not resolve returning a different map type
    private Map<String, String> createIdStringMap (String tag) {
        Map<String, String> map = new HashMap<String, String>();
        NodeList list = myDoc.getElementsByTagName(tag); // gets all elements with tag
        for (int i = 0; i < list.getLength(); i++) {
            Element e = (Element) list.item(i);
            map.put(e.getAttribute("id"), e.getTextContent());
        }
        return map;
    }

    // general method to read off any xml grid representation
    // could be used for occupant mapping OR location-based parameters
    protected Map<String, ArrayList<Location>> readLocations () {

        // create gridKey to store Occupant : number relations within grid
        Map<String, Double> gridKey = createIdNumMap("occupant");

        // create occMap
        Map<String, ArrayList<Location>> occMap = new HashMap<String, ArrayList<Location>>();

        for (String s : gridKey.keySet()) {
            ArrayList<Location> locs = new ArrayList<>();
            NodeList rows = myDoc.getElementsByTagName("row");
            if (rows.getLength() != paramList.getRows()) {
                throwError("Wrong number of rows");
            }
            for (int j = 0; j < rows.getLength(); j++) {
                String[] cols = rows.item(j).getTextContent().split(" ");
                if (cols.length != paramList.getCols()) {
                    throwError("Wrong number of cols");
                }

                for (int k = 0; k < cols.length; k++) {
                    double gridKeyVal = gridKey.get(s);
                    if (Integer.parseInt(cols[k]) == gridKeyVal) {
                        switch (paramList.getCellShape()) { // Adds the specific shape-Location
                                                            // based on type specified by XML
                            case "Square":
                                locs.add(new SquareLocation(j, k));
                                break;
                            case "Triangle":
                                locs.add(new TriangleLocation(j, k));
                                break;
                            case "Hexagon":
                                locs.add(new HexagonLocation(j, k));
                                break;
                        }
                    }
                }
            }
            occMap.put(s, locs);
        }
        return occMap;
    }

    /**
     * method to read occupant numbers/percentages, if no occupant grid is given
     * simulation can determine if percent/number if values <1 (percent) or >1 (number
     * @return map of occupant names to their appropriate numbers
     */
    
    private Map<String, Double> getAllOccupantNumbers () {
        return createIdNumMap("number");
    }

    // TODO: get Param Ranges... Map<String, Integer[]>
    private Map<String, Double> getParams () {
        return createIdNumMap("param");
    }

    private Map<String, String> getStyles () {
        return createIdStringMap("param");
    }

    private Element getFirstTag (String s) {
        return (Element) myDoc.getElementsByTagName(s).item(0);
    }

    private double getDoubleContent (Element e) {
        return Double.parseDouble(e.getTextContent());
    }

    private boolean getBooleanContent (Element e) {
        return Boolean.parseBoolean(e.getTextContent());
    }

    private void throwError (String s) {
        System.err.println(s);
        throw new RuntimeErrorException(new Error());
    }

    public ParameterList getParameterList () {
        return paramList;
    }

    public StyleList getStyleList () {
        return styleList;
    }
}
