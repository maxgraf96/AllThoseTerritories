import javax.swing.*;

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

        // Add views displaying current troops
        addArmiesLabels();

        // Add to JFrame
        add(myWorld);

        // Lights on!
        setVisible(true);
    }

    private void addArmiesLabels(){
        for(String country : GameElements.COUNTRIES){
            Territorium current = GameElements.TERRITORIA.get(country);
            current.getArmiesView().setBounds(current.getCapitalcity().getX(), current.getCapitalcity().getY(),
                    Constants.ARMIESVIEWWIDTH, Constants.ARMIESVIEWHEIGHT);
            add(current.getArmiesView());
        }
    }

    public JLabel getInfoLabel() {
        return infoLabel;
    }

    public void setInfoLabelText(String newtext) {
        this.infoLabel.setText(newtext);
        repaint();
    }
}
