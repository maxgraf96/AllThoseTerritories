import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by max on 12.01.16.
 */

//Extend JFrame so we can use this as a window
public class UI extends JFrame {

    // World
    WorldMap myWorld = new WorldMap();

    // Game mechanics
    Game game = new Game();

    // Labels
    // Label for displaying information
    JLabel infoLabel = new JLabel("Info");
    // Label for displaying current T when hoverin over
    JLabel currentTLabel = new JLabel("Territory");
    // Label for background - does not work at the moment
    // JLabel background = new JLabel(new ImageIcon("Resources/Images/BackgroundSea.png"));

    // Panels
    // Panel for confirming reinforcements
    EnforcePanel enforcePanel = new EnforcePanel();
    // Panel for starting the game after picking
    ConquerIntroPanel conquerIntroPanel = new ConquerIntroPanel();
    // Panel for attacking territories
    AttackPanel attackPanel = new AttackPanel();
    // Panel for moving troops after successful conquer
    PostConquerPanel postConquerPanel = new PostConquerPanel();

    // Buttons
    JButton DoneEnforcingButton = new JButton("Done enforcing");
    JButton endTurnButton = new JButton("End turn");

    // End screen
    EndScreen endScreen = new EndScreen();

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
        infoLabel.setBounds(0, this.getHeight() - 70, this.getWidth(), 25);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Configure label for displaying current territory(lower right corner)
        currentTLabel.setBounds(this.getWidth() - 180, this.getHeight() - 70, 160, 25);

        // Configure DoneEnforcingButton
        DoneEnforcingButton.setBounds(25, this.getHeight() - 70, 160, 25);
        DoneEnforcingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Computer's turn
                // Next phase is started from computer's enforce method
                Main.window.getGame().computer.enforce(GameElements.COUNTRIES);
                // Hide button
                DoneEnforcingButton.setVisible(false);
            }
        });
        DoneEnforcingButton.setVisible(false);

        // Configure endTurnButton
        endTurnButton.setBounds(25, this.getHeight() - 70, 160, 25);
        endTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hide button
                endTurnButton.setVisible(false);

                // Computer's turn
                Main.window.getGame().computer.attack();
            }
        });
        endTurnButton.setVisible(false);

        // Configure endScreen
        endScreen.setBounds(0,0, Constants.WIDTH, Constants.HEIGHT);
        endScreen.setLayout(null);
        endScreen.setVisible(false);

    }

    // Place our stuff(thats a method for itself) - everything is ADDED here
    public void genesis(){

        // Don't use layout manager, we position our views manually
        myWorld.setLayout(null);

        // Add listener for mouse clicks
        // For clicks
        myWorld.addMouseListener(game);
        // For hovering
        myWorld.addMouseMotionListener(game);

        // Add Panel for confirming reinforcements
        myWorld.add(enforcePanel, new Integer(3));
        // Add Panel for starting the game after picking
        myWorld.add(conquerIntroPanel, new Integer(3));

        // Add infoLabel
        myWorld.add(infoLabel);
        // Add current territory label
        myWorld.add(currentTLabel);

        // Add views displaying current troops
        addArmiesLabels();

        // Add button for ending enforcement
        myWorld.add(DoneEnforcingButton);

        // Add button for ending turn
        myWorld.add(endTurnButton);

        // Add AttackPanel
        myWorld.add(attackPanel, new Integer(3));

        // Add PostConquerPanel
        myWorld.add(postConquerPanel, new Integer(3));

        // Add endScreen
        myWorld.add(endScreen, new Integer(6));

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
            myWorld.add(current.getArmiesView(), new Integer(1));
        }
    }

    public EnforcePanel getEnforcePanel(){
        return this.enforcePanel;
    }

    public void setInfoLabelText(String newtext) {
        this.infoLabel.setText(newtext);
        repaint();
    }

    public void setCurrentTLabelText(String newtext) {
        this.currentTLabel.setText(newtext);
    }

    public Game getGame(){
        return this.game;
    }

    public JButton getDoneEnforcingButton() {
        return DoneEnforcingButton;
    }

    public ConquerIntroPanel getConquerIntroPanel() {
        return conquerIntroPanel;
    }

    public AttackPanel getAttackPanel() {
        return attackPanel;
    }

    public PostConquerPanel getPostConquerPanel() {
        return postConquerPanel;
    }

    public JButton getEndTurnButton() {
        return endTurnButton;
    }

    public JPanel getEndScreen() {
        return endScreen;
    }
}
