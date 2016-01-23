import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by max on 15.01.16.
 */
public class Game implements MouseListener, MouseMotionListener {

    // Fields
    // Players
    public Player player = new Player();
    public Computer computer = new Computer();

    // Constructor
    public Game(){}

    public void startEnforcementPhase(){
        // Set game phases
        GameElements.gamePhase = Constants.ENFORCE;

        // Assign enforcements
        player.enforcements += player.calcEnforcements();
        computer.enforcements += computer.calcEnforcements();

        // Display info
        AllThoseTerritories.window.setInfoLabelText("You can now enforce your territories. You have " +
                + player.getEnforcements() + ". Choose wisely.");

        // Enable currently available enforcements label
        AllThoseTerritories.window.currentEnforcementsLabel.setText(String.valueOf(player.enforcements));
        AllThoseTerritories.window.currentEnforcementsLabel.setVisible(true);

        // Enable enforcement done button
        AllThoseTerritories.window.getConfirmEnforcements().setVisible(true);
    }

    public void startAttackPhase(){
        // Set game phases
        GameElements.gamePhase = Constants.CONQUER;

        // To be continued
    }

    // // 1 => success, 2 => opponent's territory, 3 => clicked outside territory
    public int checkClick(Point point){
        for (int i = 0; i < GameElements.COUNTRIES.size(); i++) {
            Territorium current = GameElements.TERRITORIA.get(GameElements.COUNTRIES.get(i));
            for (int j = 0; j < current.getShapes().size(); j++) {
                if (current.getShapes().get(j).contains(point.x, point.y)) {
                    if (current.getConqueredBy().equals(player.name))
                        return 1;
                    // Not your territory
                    else if (!current.getConqueredBy().equals(player.name))
                        return 2;
                }
            }
        }
        // Not clicked inside a territory
        return 3;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        // Get click coords
        Point point = e.getPoint();
        int state;

        switch (GameElements.gamePhase) {
            case Constants.PICK:
                // Fire
                 boolean successfullyPicked = player.pick(GameElements.COUNTRIES, point);

                // Only if he has clicked inside a territory
                // Computer's turn
                if (successfullyPicked)
                computer.pick(GameElements.COUNTRIES);
                // Check if all territories are conquered, if yes begin conquer phase
                if (checkAllTerrConquered()) {
                    // Show message that all territories have been selected and start the game
                    AllThoseTerritories.window.getConquerIntroPanel().setVisible(true);

                    // Start enforcements
                    AllThoseTerritories.window.getGame().startEnforcementPhase();
                }
                break;

            // The conquer phase needs to be broken down into various sub-phases
                    case Constants.ENFORCE:
                        // Show dialog
                        // No double dialogs
                        if(!AllThoseTerritories.window.getEnforcePanel().isVisible()){
                            state = checkClick(point);
                            if(state == 1){
                                // Only if he has clicked inside a territory
                                AllThoseTerritories.window.getEnforcePanel().setVisible(true);
                                AllThoseTerritories.window.getEnforcePanel().init(point, player);
                            }
                            else if(state == 2){
                                // Enemy T selected. Tell user to click a free territory
                                AllThoseTerritories.window.setInfoLabelText(Constants.OPPONENTSTERRITORY);
                            }
                            else if(state == 3){
                                // Don't tell user to click inside a territory - he should know by now
                            }
                        }
                        else {
                            AllThoseTerritories.window.getEnforcePanel().setVisible(false);
                            /*Reset is really important. Because we don't actually add and remove the
                            * enforcePanel every time(we just hide and show it) the counters would still increase
                            * so if you openend it three times and closed it three times you would be adding 3
                            * armies, even if you only had 1!*/
                            AllThoseTerritories.window.getEnforcePanel().reset();
                        }

                        break;
                }

        }

    // MouseMotionListener methods
    @Override
    public void mouseMoved(MouseEvent e) {
        // Show territory's name in the lower right corner when hovering over it
        Point p = e.getPoint();
        for(String country : GameElements.COUNTRIES){
            Territorium territorium = GameElements.TERRITORIA.get(country);

            for(Polygon shape : territorium.getShapes()){
                if(shape.contains(p)){
                    AllThoseTerritories.window.setCurrentTLabelText(territorium.getName());
                }
            }
        }

        AllThoseTerritories.window.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    // Rest MouseListener methods
    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public boolean checkAllTerrConquered (){
        //Returns true if all territories have an owner
        for (int i = 0; i < GameElements.TERRITORIA.size(); i++) {
            if (!GameElements.TERRITORIA.get(GameElements.COUNTRIES.get(i)).isConquered())
                return false;
        }
        return true;
    }
}
