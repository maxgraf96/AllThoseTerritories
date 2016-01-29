import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by max on 29.01.16.
 */
public class AttackPanel extends JPanel {

    // Fields
    JLabel desc = new JLabel("It's ");
    JLabel desc2 = new JLabel(" against ");
    JButton confirm = new JButton("Confirm");
    JButton cancel = new JButton("Cancel");
    JLabel numbers = new JLabel("0");
    JLabel enemyNumbers = new JLabel("0");

    // Listeners
    ActionListener confirmListener;
    ActionListener cancelListener;

    int enemyTroops;

    // Constructor
    public AttackPanel(){
        this.setLayout(new FlowLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.black));

        this.desc.setBounds(0, 0, 280, 20);
        this.desc2.setBounds(0, 0, 280, 20);
        this.confirm.setBounds(0, 0, 80, 20);
        this.cancel.setBounds(0, 0, 80, 20);
        this.enemyNumbers.setBounds(0, 0, 80, 20);

        this.add(desc);
        this.add(numbers);
        this.add(desc2);
        this.add(enemyNumbers);
        this.add(confirm);
        this.add(cancel);

        this.setVisible(false);
    }

    public void init(Point point, Player player, Territorium source){
        // Get data
        Territorium enemyTerritory = HelperMethods.getTerritoriumOnClick(point);
        int availableTroops = source.getNumberOfArmies();
        enemyTroops = enemyTerritory.getNumberOfArmies();

        // Get your attack strength
        if(availableTroops > 3)
            availableTroops = 3;
        else if(availableTroops == 3)
            availableTroops = 2;
        else if(availableTroops == 2)
            availableTroops = 1;
        else if(availableTroops == 1)
            availableTroops = 0;

        // Get enemy defense strenght
        if(enemyTroops > 1)
            enemyTroops = 2;
        else
            enemyTroops = 1;

        // Set numbers
        numbers.setText(String.valueOf(availableTroops));
        enemyNumbers.setText(String.valueOf(enemyTroops));

        // Position at click
        this.setBounds(point.x, point.y, 450, 70);

        //Add listeners
        confirmListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Territorium current = HelperMethods.getTerritoriumOnClick(point);




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

                // Back to selecting your source T
                GameElements.gamePhase = Constants.PHASE_ATTACKFROM;
            }
        };

        confirm.addActionListener(confirmListener);
        cancel.addActionListener(cancelListener);

        // Lights on!
        this.setVisible(true);
    }

    public void reset(){
        this.enemyTroops = 0;
        // Remove listeners - this is a MUST!
        confirm.removeActionListener(confirmListener);
        cancel.removeActionListener(cancelListener);
    }
}
