import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by max on 15.01.16.
 */

// JUST A TEST DOES NOT WORK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!





public class DrawableShape extends JPanel {

    Polygon outline;
    public DrawableShape(Polygon outline){
        this.outline = outline;
    }
    // Paint the shape
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawPolygon(outline.xpoints,outline.ypoints,outline.npoints);
    }
}
