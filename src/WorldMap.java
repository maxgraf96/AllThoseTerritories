import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;

/**
 * Created by max on 14.01.16.
 */
public class WorldMap extends JPanel {

    HashMap<String,Territorium> worldMap;

    // Constructor
    public WorldMap(HashMap<String,List<Polygon>> worldMap){
        this.worldMap = GameElements.TERRITORIA;
    }

    // Paint the Worldmap
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (String terr : GameElements.COUNTRIES) {
            Territorium current = GameElements.TERRITORIA.get(terr);
            for (int i = 0; i < current.getShapes().size(); i++) {
                List<Polygon> shapesList = current.getShapes();

                for (int j = 0; j < shapesList.size(); j++) {
                    Polygon shape = shapesList.get(i);

                    g.drawPolygon(shape.xpoints,shape.ypoints,shape.npoints);
                }
            }
        }
    }
}
