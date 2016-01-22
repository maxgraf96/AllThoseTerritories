import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by max on 12.01.16.
 */

//Extend JFrame so we can use this as a window
public class UI extends JFrame {

    // Game mechanics
    Game game = new Game();

    // Labels
    // Label for displaying information
    JLabel infoLabel = new JLabel("Info");
    // Label for displaying current T when hoverin over
    JLabel currentTLabel = new JLabel("Territory");
    // Label for displaying number of enforcements
    JLabel currentEnforcementsLabel = new JLabel("0");
    // Label for background - does not work at the moment
    // JLabel background = new JLabel(new ImageIcon("Resources/Images/BackgroundSea.png"));

    // Panels
    // Panel for confirming reinforcements
    EnforcePanel enforcePanel = new EnforcePanel();
    // Panel for starting the game after picking
    ConquerIntroPanel conquerIntroPanel = new ConquerIntroPanel();

    // Buttons
    JButton confirmEnforcements = new JButton("Done enforcing");

    // This method takes care of the most basic configuration of the main window - everything is CONFIGURED here
    public void init(){

        // Set window title
        setTitle("AllThoseTerritories v" + Constants.VERSION);
        // Set window size
        setSize(Constants.WIDTH, Constants.HEIGHT);
        // Close window when we click on "x"
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // No resizing!
        setResizable(false);

        // Configure infoLabel
        infoLabel.setBounds(0, this.getHeight() - 70, this.getWidth(), 20);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Configure label for displaying current territory(lower right corner)
        currentTLabel.setBounds(this.getWidth() - 180, this.getHeight() - 70, 160, 20);

        // Configure enforcePanel
        enforcePanel.setLayout(new FlowLayout());

        // Configure confirmEnforcements
        confirmEnforcements.setBounds(this.getWidth() - 360, this.getHeight() - 70, 160, 30);
        confirmEnforcements.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Computer's turn
                // Next phase is started from computer's enforce method
                AllThoseTerritories.window.getGame().computer.enforce(GameElements.COUNTRIES);
                // Hide button
                confirmEnforcements.setVisible(false);
            }
        });
        confirmEnforcements.setVisible(false);
    }

    // Place our stuff(thats a method for itself) - everything is ADDED here
    public void genesis(){

        // Create our world
        WorldMap myWorld = new WorldMap();

        // Don't use layout manager, we position our views manually
        myWorld.setLayout(null);

        // Add listener for mouse clicks
        // For clicks
        myWorld.addMouseListener(game);
        // For hovering
        myWorld.addMouseMotionListener(game);

        // Add Panel for confirming reinforcements
        myWorld.add(enforcePanel);
        // Add Panel for starting the game after picking
        myWorld.add(conquerIntroPanel);

        // Add infoLabel
        myWorld.add(infoLabel);
        // Add current territory label
        myWorld.add(currentTLabel);
        // Add currently available enforcements label
        myWorld.add(currentEnforcementsLabel);

        // Add views displaying current troops
        addArmiesLabels();

        // Add button for ending enforcement
        myWorld.add(confirmEnforcements);

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

    public EnforcePanel getEnforcePanel(){
        return this.enforcePanel;
    }

    public void setInfoLabelText(String newtext) {
        this.infoLabel.setText(newtext);
        repaint();
    }

    public JLabel getCurrentTLabel() {
        return currentTLabel;
    }

    public void setCurrentTLabelText(String newtext) {
        this.currentTLabel.setText(newtext);
        repaint();
    }

    public Game getGame(){
        return this.game;
    }

    public JButton getConfirmEnforcements() {
        return confirmEnforcements;
    }

    public ConquerIntroPanel getConquerIntroPanel() {
        return conquerIntroPanel;
    }
}
