import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by max on 30.01.16.
 */
public class PostConquerPanel extends EnforcePanel {

    public void init(Point point, Player player, Territorium sourceTerritory){
        // Get data
        tempTerritories = sourceTerritory.getNumberOfArmies() - 1;
        // Init label
        desc.setText("How many armies would you like move?");
        number.setText(String.valueOf(tempTerritories));

        // Position at click
        // If click is too near to the right border then draw it "on the left side"
        if(point.x > Constants.WIDTH - 450)
            this.setBounds(point.x - 450, point.y, 450, 70);
        else
            this.setBounds(point.x, point.y, 450, 70);

        //Add listeners
        minusListener = new  ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tempTerritories == 1)
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
                if (!(tempTerritories + 1 > sourceTerritory.getNumberOfArmies() - 1)) {
                    number.setText(String.valueOf(tempTerritories + 1));
                    tempTerritories++;
                }
            }
        };
        confirmListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Move
                Territorium target = HelperMethods.getTerritoriumFromPoint(point);
                target.setNumberOfArmies(target.getNumberOfArmies() + tempTerritories);
                sourceTerritory.setNumberOfArmies(sourceTerritory.getNumberOfArmies() - tempTerritories);

                // Phase back to choose from which to conquer
                GameElements.gamePhase = Constants.PHASE_ATTACKFROM;

                // Always
                reset();
                // Lights off!
                setVisible(false);
            }
        };
        cancelListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Phase back to choose from which to conquer
                GameElements.gamePhase = Constants.PHASE_ATTACKFROM;

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
}
