import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by max on 20.01.16.
 */
public class EnforcePanel extends JPanel {

    // Fields
    JLabel desc = new JLabel("Description goes here");
    JButton confirm = new JButton("Confirm");
    JButton cancel = new JButton("Cancel");
    JButton plus = new JButton("+");
    JButton minus = new JButton("-");
    JLabel number = new JLabel("0");

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

    // Listeners
    ActionListener minusListener;
    ActionListener plusListener;
    ActionListener confirmListener;
    ActionListener cancelListener;

    // Methods
    public void init(Point point, Player player){

        // Get data
        tempTerritories = player.getEnforcements();
        // Init label
        desc.setText("How many armies would you like to deploy in this territory?");
        number.setText(String.valueOf(tempTerritories));

        // Position at click
        this.setBounds(point.x, point.y, 450, 70);

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
                for (int i = 0; i < GameElements.COUNTRIES.size(); i++) {
                    Territorium current = GameElements.TERRITORIA.get(GameElements.COUNTRIES.get(i));
                    for (int j = 0; j < current.getShapes().size(); j++) {
                        if (current.getShapes().get(j).contains(point.x, point.y)) {
                            if (current.getConqueredBy().equals(player.name)) {
                                // Get number of reinforcements
                                int reinforcements = Integer.parseInt(number.getText());
                                // Add troops
                                current.setNumberOfArmies(current.getNumberOfArmies() + reinforcements);

                                // Update enforcements
                                player.enforcements -= reinforcements;

                                // Update label over capital city
                                current.getArmiesView().setText(String.valueOf(current.getNumberOfArmies()));

                                AllThoseTerritories.window.getGame().player.enforce(point, 1);
                            }
                        }

                        // Not your territory
                        else if (!current.getConqueredBy().equals(player.name))
                            AllThoseTerritories.window.getGame().player.enforce(point, 2);
                            // Not clicked inside a territory
                        else
                            AllThoseTerritories.window.getGame().player.enforce(point, 3);
                    }
                }

                // Always
                reset();
                // Lights off!
                setVisible(false);
            }
        };
        cancelListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
