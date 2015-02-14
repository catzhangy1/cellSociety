package uiManager;

import grid.Grid;
import java.io.File;
import javax.management.RuntimeErrorException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import occupant.Occupant;
import org.w3c.dom.Document;
import cellWorld.CellWorld;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import userInterface.GOLUserInterface;
import userInterface.PredPreyUserInterface;
import userInterface.FireUserInterface;
import userInterface.SegregationUserInterface;
import userInterface.UserInterface;
import xmlreader.ParameterList;
import xmlreader.StyleList;
import xmlreader.XMLReader;


/**
 * <code>UIManager</code> acts as a controller. It connects the front-end (UserInterface)
 * with the back-end (CellWorld). It controls the mechanics of initializing a GUI and
 * defining frame actions to be performed <br />
 *
 * @author Nathan Prabhu
 */
public class UIManager {

    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 600;
    private static final int FRAMES_PER_SECOND = 10;

    private ParameterList params;
    private CellWorld myCellWorld;
    private Stage myStage;

    public UIManager (Stage stage) {
        // TODO: create starter scene/prompt to load xml?
        myStage = stage;
        params = readParamsXML(loadXML());
        addStyle(readStyleXML(new File("defaultStyle2.xml")));
        setCellWorld(params);
        initialize(myStage);
    }

    public File loadXML () {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select XML file");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fc.getExtensionFilters().add(extFilter);
        return fc.showOpenDialog(new Stage());
        
    }
    
    public ParameterList readParamsXML (File file) {
        XMLReader reader = new XMLReader(file);
        return reader.getParameterList();
    }

    public StyleList readStyleXML (File file) {
        XMLReader reader = new XMLReader(file);
        return reader.getStyleList();
    }
    
    public void addStyle (StyleList style) {
        params.setNullColor(style.getNullColor());
        params.setStyleMap(style.getStyleMap());     
    }
    
    public void setCellWorld(ParameterList paramsList){
        myCellWorld = paramsList.getCellWorld();
    }

    public String getShape () {
        return myCellWorld.getShape();
    }
    

    public Color[][] resetWorld () {
        return myCellWorld.reset();
    }

    /**
     * Initializes scene and builds the simulation
     * 
     * @param stage primary Stage scene will be put on
     * @param width Scene width
     * @param height Scene height
     */
    private void initialize (Stage stage) {
        stage.setTitle(params.getWindowTitle());
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        // TODO: figure out how to work css
        // File f = new File("layoutstyles.css");
        // scene.getStylesheets().add(f.getAbsolutePath());
        stage.setScene(scene);
        switch (params.getTitle()) {
            case "Game of Life":
                buildSimulation(new GOLUserInterface(root, this));
                break;
            case "Predator and Prey":
                buildSimulation(new PredPreyUserInterface(root, this));
                break;
            case "Segregation":
                buildSimulation(new SegregationUserInterface(root, this));
                break;
            case "Spreading of Fire":
                buildSimulation(new FireUserInterface(root, this));
                break;
        }
    }

    /**
     * Builds timeline and resets simulation
     * 
     * @param u UserInterface particular to each cellWorld
     */
    private void buildSimulation (UserInterface u) {
        Timeline timeline = new Timeline();
        final Duration oneFrameAmt = Duration.millis(1000 / FRAMES_PER_SECOND);
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt, e -> executeFrameActions(u, timeline));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(oneFrame);
        u.setTimeline(timeline);
        u.resetSimulation();
    }

    /**
     * Calls all frame actions to be performed in simulation. These frame actions include painting
     * the grid display with the updated grid, updating the simulation info, and checking for a
     * finishing condition.
     * 
     * @param u UserInterface particular to each cellWorld
     * @param t Timeline
     */
    private void executeFrameActions (UserInterface u, Timeline t) {
        myCellWorld.step();
        u.makeGridDisplay(myCellWorld.getColors(), myCellWorld.getShape());
        u.updateSimInfo(false);
        if (myCellWorld.checkFinished()) {
            t.stop();
        }
    }

    public ParameterList getParameterList () {
        return params; // should make unmodifiable?
    }
}