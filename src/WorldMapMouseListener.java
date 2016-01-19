import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by max on 15.01.16.
 */
public class WorldMapMouseListener implements MouseListener {

    public WorldMapMouseListener(){}

    @Override
    public void mouseClicked(MouseEvent e) {

        switch (GameElements.gamePhase){
            case "pick":

                break;

        }

        // Test for clicking countries - works - bug: some are printed twice, e.g. new guinea
        Component source = e.getComponent();
        Point p = source.getMousePosition();

        for(String country : GameElements.COUNTRIES){
            Territorium territorium = GameElements.TERRITORIA.get(country);

            for(Polygon shape : territorium.getShapes()){
                if(shape.contains(p)){
                    System.out.println(territorium.getName());
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
