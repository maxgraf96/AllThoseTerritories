import com.sun.corba.se.impl.orbutil.graph.Graph;

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
    public WorldMap(HashMap<String,Territorium> worldMap){
        this.worldMap = GameElements.TERRITORIA;
    }

    // Paint the Worldmap
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Cast to Graphics2D for drawing features
        Graphics2D graphics2D = (Graphics2D) g;

        for (String country : GameElements.COUNTRIES) {
            Territorium current = GameElements.TERRITORIA.get(country);
            for (int i = 0; i < current.getShapes().size(); i++) {
                List<Polygon> shapesList = current.getShapes();

                for (int j = 0; j < shapesList.size(); j++) {

                    Polygon shape = shapesList.get(i);

                    // Deciding how to draw the shape based on state (conquered, free, etc.)
                    // Will continue to extend as we implement more and more mechanics
                    if(current.isConquered()){
                        // By whom
                        // Player
                        if(current.getConqueredBy().equals(Constants.PLAYER)){
                            // Make background blue
                            graphics2D.setColor(Color.BLUE);
                            graphics2D.fillPolygon(shape);
                            // Border should be black though
                            graphics2D.setColor(Color.BLACK);
                            graphics2D.drawPolygon(shape.xpoints,shape.ypoints,shape.npoints);
                        }
                        // Computer
                        else if(current.getConqueredBy().equals(Constants.COMPUTER)){
                            // Make background red
                            graphics2D.setColor(Color.RED);
                            graphics2D.fillPolygon(shape);
                            // Border should be black though
                            graphics2D.setColor(Color.BLACK);
                            graphics2D.drawPolygon(shape.xpoints,shape.ypoints,shape.npoints);
                        }
                    }
                    // Not conquered
                    else{
                        graphics2D.drawPolygon(shape.xpoints,shape.ypoints,shape.npoints);
                    }
                }
            }
        }
    }
}
