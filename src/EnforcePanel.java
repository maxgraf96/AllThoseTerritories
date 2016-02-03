import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by max on 20.01.16.
 */
public class EnforcePanel extends JPanel {

    // Fields
    int width = 440;
    int height = 60;
    JLabel desc = new JLabel("Description goes here");
    JButton confirm = new JButton(Constants.PLACE);
    JButton cancel = new JButton(Constants.CANCEL);
    JButton plus = new JButton("+");
    JButton minus = new JButton("-");
    JLabel number = new JLabel("0");

    // Listeners
    ActionListener minusListener;
    ActionListener plusListener;
    ActionListener confirmListener;
    ActionListener cancelListener;

    int tempTerritories = 0; // For init() method

    // Constructor
    public EnforcePanel(){
        this.setLayout(new FlowLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.black));

        this.desc.setBounds(0, 0, 280, 20);
        this.confirm.setBounds(0, 0, 80, 20);
        this.cancel.setBounds(0, 0, 80, 20);
        this.minus.setBounds(0, 0, 80, 20);
        this.number.setBounds(0, 0, 80, 20);
        this.plus.setBounds(0, 0, 80, 20);

        this.add(desc);
        this.add(minus);
        this.add(number);
        this.add(plus);
        this.add(confirm);
        this.add(cancel);

        this.setVisible(false);
    }

    // Methods
    public void init(Point point, Player player){

        // Get data
        tempTerritories = player.getEnforcements();
        // Init label
        desc.setText(Constants.DEPLOYHOWMANYARMIES);
        number.setText(String.valueOf(tempTerritories));

        // Position at click
        // If click is too near to the right border then draw it "on the left side"
        if(point.x > Constants.WIDTH - 450)
            this.setBounds(point.x - 450, point.y, width, height);
        else
            this.setBounds(point.x, point.y, width, height);

        //Add listeners
        minusListener = new  ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tempTerritories == 0)
                    number.setText(String.valueOf(0));
                else if(tempTerritories == 1)
                    number.setText(String.valueOf(1));
                else {
                    number.setText(String.valueOf(tempTerritories - 1));
                    tempTerritories--;
                }
            }
        };
        plusListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tempTerritories == 0)
                    number.setText(String.valueOf(0));
                else {
                    if(tempTerritories + 1 <= player.getEnforcements()) {
                        number.setText(String.valueOf(tempTerritories + 1));
                        tempTerritories++;
                    }
                }
            }
        };
        confirmListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Territorium current = HelperMethods.getTerritoriumFromPoint(point);
                if (current.getConqueredBy().equals(player.name)) {
                    // Get number of reinforcements
                    int reinforcements = Integer.parseInt(number.getText());
                    // Add troops
                    current.setNumberOfArmies(current.getNumberOfArmies() + reinforcements);

                    // Update enforcements
                    player.enforcements -= reinforcements;

                    // Update label over capital city
                    current.getArmiesView().setText(String.valueOf(current.getNumberOfArmies()));

                    Main.window.getGame().player.enforce(point, true);
                }

                // Not your territory
                else
                    Main.window.getGame().player.enforce(point, false);

                // Always
                Main.window.repaint();
                reset();
                // Lights off!
                setVisible(false);
            }
        };
        cancelListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Always
                Main.window.repaint();
                reset();
                setVisible(false);
            }
        };

        minus.addActionListener(minusListener);
        plus.addActionListener(plusListener);
        confirm.addActionListener(confirmListener);
        cancel.addActionListener(cancelListener);

        // Lights on!
        this.setVisible(true);
    }

    public void reset(){
        this.tempTerritories = 0;
        this.number.setText("");
        // Remove listeners - this is a MUST!
        minus.removeActionListener(minusListener);
        plus.removeActionListener(plusListener);
        confirm.removeActionListener(confirmListener);
        cancel.removeActionListener(cancelListener);
    }
}
