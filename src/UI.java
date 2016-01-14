import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by max on 12.01.16.
 */

//Extend JFrame so we can use this as a window
public class UI extends JFrame{

    // This method takes care of the most basic configuration of the main window
    public void init(HashMap<String,List<List<Coordinates>>> worldMap){

        // Set window title
        setTitle("AllThoseTerritories v" + Constants.VERSION);
        // Set window size
        setSize(Constants.WIDTH, Constants.HEIGHT);
        // Close window when we click on "x"
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Lights on!
        setVisible(true);

        // Place our stuff(thats a method for itself)
        genesis(worldMap);
    }

    private void genesis(HashMap<String,List<List<Coordinates>>> worldMap){

        // Create our world
        WorldMap myWorld = new WorldMap(worldMap);
        add(myWorld);

    }
}
