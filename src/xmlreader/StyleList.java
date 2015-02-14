package xmlreader;

import java.util.Map;
import javafx.scene.paint.Color;

/**
 * Creates a collection of style-related parameters (in the same vein as ParameterList) for each simulation
 * @author Nathan Prabhu
 *
 */
public class StyleList {

    private Map<String, String> styleMap;
    private boolean outline;
    private Color nullColor;
    private String shape;
    
    public StyleList () {
    }
    
    public void setStyleMap (Map<String, String> m) {
        styleMap = m;
    }
    
    public Map<String, String> getStyleMap () {
         return styleMap;
    }
    
    public void setIsOutline (boolean b) {
        outline = b;
    }
    
    public boolean getIsOutline () {
        return outline;
    }

    public void setNullColor (String color) {
        nullColor = Color.web(color);
    }

    public Color getNullColor () {
        return nullColor;
    }

    public void setGridUnitShape (String s) {
        shape = s;
    }
    
    public String getGridUnitShape () {
        return shape;
    }
}
