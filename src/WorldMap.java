import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

/**
 * Created by max on 14.01.16.
 */
public class WorldMap extends JPanel {

    HashMap<String,Territorium> worldMap;

    // Constructor
    public WorldMap(){
        this.worldMap = GameElements.TERRITORIA;
    }

    // Paint the Worldmap
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Cast to Graphics2D for drawing features
        Graphics2D graphics2D = (Graphics2D) g;

        // Draw lines between capitals
        for(String country : GameElements.COUNTRIES){
            Territorium current = GameElements.TERRITORIA.get(country);

            // Draw lines between capitals
            for(Territorium neighbor : current.getNeighbors()){
                // There is one exception: From Kamtchatka to alaska the line must go through the screen border >:(
                if(current.getName().equals("Alaska") || current.getName().equals("Kamchatka")){
                    // Thicker line
                    graphics2D.setStroke(new BasicStroke(1.8f));
                    if(current.getName().equals("Alaska")){
                        graphics2D.drawLine(current.getCapitalcity().getX(),current.getCapitalcity().getY(),
                                0,current.getCapitalcity().getY());
                    }
                    else if(current.getName().equals("Kamchatka")){
                        graphics2D.drawLine(current.getCapitalcity().getX(),current.getCapitalcity().getY(),
                                Main.window.getWidth(), current.getCapitalcity().getY());
                    }
                }
                else {// All other lines
                    // Thicker line
                    graphics2D.setStroke(new BasicStroke(1.8f));
                    graphics2D.drawLine(current.getCapitalcity().getX(), current.getCapitalcity().getY(),
                            neighbor.getCapitalcity().getX(), neighbor.getCapitalcity().getY());
                }
            }
        }

        // Draw everything else
        // Make lines a little appropriate
        graphics2D.setStroke(new BasicStroke(1.1f));

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
                        graphics2D.setColor(Color.lightGray);
                        graphics2D.fillPolygon(shape);
                        // Border should be black though
                        graphics2D.setColor(Color.BLACK);
                        graphics2D.drawPolygon(shape.xpoints,shape.ypoints,shape.npoints);
                    }
                }
            }
        }
    }
}
