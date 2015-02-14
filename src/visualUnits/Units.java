package visualUnits;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class Units{
    int x;
    int y;
    private boolean outline;
    Shape unit;
    
    public Units (int r, int c, boolean isOutline) {
        x = r;
        y = c; 
        outline = isOutline;
    }   

    public void set(Color c, Shape shape){
        shape.setFill(c);
        if (outline){
            shape.setStroke(Color.BLACK);
        }
    }
    
    public Shape getShape(){
        return unit;
    }
    
    public abstract void create(double width, double height);
}
