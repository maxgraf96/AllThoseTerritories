import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

/**
 * Created by max on 12.01.16.
 */

//Extend JFrame so we can use this as a window
public class UI extends JFrame {

    // Add label for displaying information
    JLabel infoLabel = new JLabel("Info");
    // Add label for background - does not work for the moment
    //JLabel background = new JLabel(new ImageIcon("Resources/Images/BackgroundSea.png"));

    // This method takes care of the most basic configuration of the main window
    public void init(){

        // Set window title
        setTitle("AllThoseTerritories v" + Constants.VERSION);
        // Set window size
        setSize(Constants.WIDTH, Constants.HEIGHT);
        // Close window when we click on "x"
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // No resizing!
        setResizable(false);

        // Add infoLabel
        infoLabel.setBounds(0, this.getHeight() - 100, this.getWidth(), 100);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    // Place our stuff(thats a method for itself)
    public void genesis(){

        // Create our world
        WorldMap myWorld = new WorldMap();

        // Don't use layout manager, we position our views manually
        myWorld.setLayout(null);

        // Add listener for mouse clicks
        myWorld.addMouseListener(new Game());

        // Add infoLabel
        myWorld.add(infoLabel);

        // Add to JFrame
        add(myWorld);

        // Lights on!
        setVisible(true);
    }

    public JLabel getInfoLabel() {
        return infoLabel;
    }

    public void setInfoLabelText(String newtext) {
        this.infoLabel.setText(newtext);
        repaint();
    }
}
