import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

/**
 * Created by max on 14.01.16.
 */
public class WorldMap extends JPanel{

    HashMap<String,List<List<Coordinates>>> worldMap;

    // Constructor
    public WorldMap(HashMap<String,List<List<Coordinates>>> worldMap){
        this.worldMap = worldMap;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < worldMap.size(); i++) {
            // Get List of territories coordinates to draw them
            List<List<Coordinates>> territoriesList = worldMap.get(GameElements.COUNTRIES.get(i));

        /* For each entry (= shape of territory e.g. indonesia -> many islands -> probably around 5 lists of coords)
         get the list of coordinates and draw them */
            for (int j = 0; j < territoriesList.size(); j++) {
                List<Coordinates> coordinatesList = territoriesList.get(j);

                for (int k = 0; k < coordinatesList.size(); k++) {
                    Coordinates current = coordinatesList.get(k);
                    // If last coordinate is reached, connect to first
                    Coordinates next = coordinatesList.get(k + 1 > coordinatesList.size() - 1 ? 0 : k + 1);

                    g.drawLine(current.getX(), current.getY(), next.getX(), next.getY());
                }
            }
        }
    }
}
