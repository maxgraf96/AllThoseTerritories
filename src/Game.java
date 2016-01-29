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
        GameElements.gamePhase = Constants.PHASE_ENFORCE;

        // Assign enforcements
        player.enforcements += player.calcEnforcements();
        computer.enforcements += computer.calcEnforcements();

        // Display info
        Main.window.setInfoLabelText("You can now enforce your territories. You have " +
                + player.getEnforcements() + ". Choose wisely.");

        // Enable enforcement done button
        Main.window.getConfirmEnforcements().setVisible(true);
    }

    public void startAttackPhase(){
        // Set game phases
        GameElements.gamePhase = Constants.PHASE_ATTACKFROM;


    }

    // returns true when clicked on a territory currently owned by the player
    public boolean checkClick(Point point){
        for (int i = 0; i < GameElements.COUNTRIES.size(); i++) {
            Territorium current = GameElements.TERRITORIA.get(GameElements.COUNTRIES.get(i));
            for (int j = 0; j < current.getShapes().size(); j++) {
                if (current.getShapes().get(j).contains(point.x, point.y)) {
                    if (current.getConqueredBy().equals(player.name)){
                        Main.window.setInfoLabelText("");
                        return true;
                    }
                    // Not your territory
                    else if (!current.getConqueredBy().equals(player.name)){
                        return false;
                    }

                }
            }
        }
        // Not clicked inside a territory, Don't tell the player - he should know by now
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        // Get click coords
        Point point = e.getPoint();

        // Source territory in attack phase
        Territorium sourceTerritory = new Territorium("");

        switch (GameElements.gamePhase) {
            case Constants.PHASE_PICK:
                // Fire
                boolean successfullyPicked = player.pick(GameElements.COUNTRIES, point);

                // Only if he has clicked inside a territory
                // Computer's turn
                if (successfullyPicked && !checkAllTerrConquered())
                    computer.pick(GameElements.COUNTRIES);
                // Check if all territories are conquered, if yes begin conquer phase
                if (checkAllTerrConquered()) {
                    // Show message that all territories have been selected and start the game
                    Main.window.getConquerIntroPanel().setVisible(true);

                    // Start enforcements
                    Main.window.getGame().startEnforcementPhase();
                }
                break;

            case Constants.PHASE_ENFORCE:
                // Show dialog
                // No double dialogs
                if (!Main.window.getEnforcePanel().isVisible()) {
                    if (checkClick(point)) {
                        // Only if he has clicked inside a territory
                        Main.window.getEnforcePanel().setVisible(true);
                        Main.window.getEnforcePanel().init(point, player);
                    }
                } else {
                    Main.window.getEnforcePanel().setVisible(false);
                    /*Reset is really important. Because we don't actually add and remove the
                    * enforcePanel every time(we just hide and show it) the counters would still increase
                    * so if you openend it three times and closed it three times you would be adding 3
                    * armies, even if you only had 1!*/
                    Main.window.getEnforcePanel().reset();
                }

                break;

            case Constants.PHASE_ATTACKFROM:
                Territorium current = HelperMethods.getTerritoriumOnClick(point);
                if(current == null)
                    Main.window.setInfoLabelText(Constants.OUTSIDETERRITORY);
                else if(current.getConqueredBy().equals(player.getName())){
                    if(current.getNumberOfArmies() < 2)
                        Main.window.setInfoLabelText(Constants.INSUFFICIENTTROOPS);
                    else if(!current.canAttack())
                        Main.window.setInfoLabelText(Constants.NONEIGHBORSTOATTACK);
                    else {
                        GameElements.gamePhase = Constants.PHASE_CHOOSETARGET;
                        sourceTerritory = current;

                        // Set label
                        Main.window.setInfoLabelText(Constants.PHASE_CHOOSETARGET);
                    }
                }
                else if(current.getConqueredBy().equals(computer.getName())){
                    Main.window.setInfoLabelText(Constants.OPPONENTSTERRITORY);
                }
                break;

            case Constants.PHASE_CHOOSETARGET:
                Territorium target = HelperMethods.getTerritoriumOnClick(point);
                if(target == null)
                    Main.window.setInfoLabelText(Constants.OUTSIDETERRITORY);
                else if(target.getConqueredBy().equals(player.getName())){
                    Main.window.setInfoLabelText(Constants.NOVALIDTARGET);
                }
                else if(target.getConqueredBy().equals(computer.getName())){
                    if(!Main.window.getAttackPanel().isVisible()) {
                        Main.window.getAttackPanel().setVisible(true);
                        Main.window.getAttackPanel().init(point, player, sourceTerritory);
                    }
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
                    Main.window.setCurrentTLabelText(territorium.getName());
                }
            }
        }

        Main.window.repaint();
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
