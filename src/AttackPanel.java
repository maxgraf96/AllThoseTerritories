import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by max on 29.01.16.
 */
public class AttackPanel extends JPanel {

    // Fields
    int width = 220;
    int height = 80;
    JLabel yourAttackStrength = new JLabel(Constants.YOURATTACKSTRENGTH);
    JLabel enemyAttackStrength = new JLabel(Constants.ENEMYATTACKSTRENGTH);
    JButton confirm = new JButton(Constants.ATTACK);
    JButton cancel = new JButton(Constants.CANCEL);
    JLabel numbers = new JLabel("0");
    JLabel enemyNumbers = new JLabel("0");

    // Listeners
    ActionListener confirmListener;
    ActionListener cancelListener;

    int availableTroops;
    int enemyTroops;


    // Constructor
    public AttackPanel(){
        this.setLayout(new FlowLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.black));

        this.add(yourAttackStrength);
        this.add(numbers);
        this.add(enemyAttackStrength);
        this.add(enemyNumbers);
        this.add(confirm);
        this.add(cancel);

        this.setVisible(false);
    }

    public void init(Point point, Player player, Territorium sourceTerritory){
        // Get data
        Territorium enemyTerritory = HelperMethods.getTerritoriumFromPoint(point);

        availableTroops = sourceTerritory.getNumberOfArmies();
        enemyTroops = enemyTerritory.getNumberOfArmies();

        // Get your attack strength
        if (availableTroops > 3) availableTroops = 4;
        availableTroops -= 1;
        // Get enemy defense strength
        if (enemyTroops > 2) enemyTroops = 2;

        // Set numbers
        numbers.setText(String.valueOf(availableTroops));
        enemyNumbers.setText(String.valueOf(enemyTroops));

        // Position at click
        // If click is too near to the right border then draw it "on the left side"
        if(point.x > Constants.WIDTH - 450)
            this.setBounds(point.x - 450, point.y, width, height);
        else
            this.setBounds(point.x, point.y, width, height);

        //Add listeners
        confirmListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean success = HelperMethods.attack(sourceTerritory,enemyTerritory,availableTroops,enemyTroops);
                if( (success)) {
                    // GamePhase to postConquer
                    // Only if you have sufficient troops
                    if (sourceTerritory.getNumberOfArmies() > 1) {
                        GameElements.gamePhase = Constants.PHASE_POSTCONQUER;
                        Main.window.setInfoLabelText(Constants.WANTPOSTCONQUER);
                    } else {
                        GameElements.gamePhase = Constants.PHASE_ATTACKFROM;
                        Main.window.setInfoLabelText(Constants.CHOOSEENEMYT);
                    }
                }
                //Else you lose :(
                else{
                    // GamePhase to ATTACKFROM
                    GameElements.gamePhase = Constants.PHASE_ATTACKFROM;
                }

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
                reset();
                setVisible(false);

                // Back to selecting your source T
                GameElements.gamePhase = Constants.PHASE_ATTACKFROM;

                // Always
                Main.window.repaint();
            }
        };
        confirm.addActionListener(confirmListener);
        cancel.addActionListener(cancelListener);

        // Lights on!
        this.setVisible(true);
    }

    public void reset(){
        availableTroops = 0;
        enemyTroops = 0;


        // Remove listeners - this is a MUST!
        confirm.removeActionListener(confirmListener);
        cancel.removeActionListener(cancelListener);
    }
}
