import javax.swing.*;
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

    // Source territory in attack phase
    Territorium sourceTerritory = new Territorium("");
    Territorium targetTerritory;

    // Fields for the once-movement
    private boolean backAndForth = true;
    private Territorium bAFTerritorium1;
    private Territorium bAFTerritorium2;

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
        Main.window.getConfirmEnforcementsButton().setVisible(true);
    }

    public void startAttackPhase(){
        // Set game phases
        GameElements.gamePhase = Constants.PHASE_ATTACKFROM;

        // Make endTurnButton visible
        Main.window.getEndTurnButton().setVisible(true);
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

        unselectTerritorries();
        // Select the territory, i.e. set isSelected to true
        if(HelperMethods.getTerritoriumFromPoint(point) != null){
            Territorium t = HelperMethods.getTerritoriumFromPoint(point);
            t.setSelected(true);
        }

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
                Territorium current = HelperMethods.getTerritoriumFromPoint(point);
                if (current == null)
                    Main.window.setInfoLabelText(Constants.OUTSIDETERRITORY);
                else if (current.getConqueredBy().equals(player.getName())) {
                    if (current.getNumberOfArmies() < 2)
                        Main.window.setInfoLabelText(Constants.INSUFFICIENTTROOPS);
                    else {
                        GameElements.gamePhase = Constants.PHASE_CHOOSETARGET;
                        sourceTerritory = current;

                        // Set label
                        Main.window.setInfoLabelText(Constants.CHOOSEENEMYT);
                    }
                } else if (current.getConqueredBy().equals(computer.getName())) {
                    Main.window.setInfoLabelText(Constants.OPPONENTSTERRITORY);
                }
                break;

            case Constants.PHASE_CHOOSETARGET:
                targetTerritory = HelperMethods.getTerritoriumFromPoint(point);
                if(SwingUtilities.isLeftMouseButton(e)) {
                    if (targetTerritory == null)
                        Main.window.setInfoLabelText(Constants.OUTSIDETERRITORY);
                    else if (targetTerritory.getConqueredBy().equals(player.getName())) {
                        Main.window.setInfoLabelText(Constants.NOVALIDTARGET);
                    } else if (targetTerritory.getConqueredBy().equals(computer.getName())) {
                        // Check if the clicked territory actually is a neighbor of source
                        if (targetTerritory.hasNeighbor(sourceTerritory)) {
                            if (!Main.window.getAttackPanel().isVisible()) {
                                Main.window.getAttackPanel().setVisible(true);
                                Main.window.getAttackPanel().init(point, player, sourceTerritory);
                            }
                        } else
                            Main.window.setInfoLabelText(Constants.NOTNEIGHBORS);
                    }
                }
                else if(SwingUtilities.isRightMouseButton(e)){
                    if(backAndForth){
                        bAFTerritorium1 = sourceTerritory;
                        bAFTerritorium2 = targetTerritory;
                        backAndForth = false;
                    }
                    if((bAFTerritorium1 == sourceTerritory && bAFTerritorium2 == targetTerritory)
                            || (bAFTerritorium1 == targetTerritory && bAFTerritorium2 == sourceTerritory)) {
                        if (targetTerritory == null)
                            Main.window.setInfoLabelText(Constants.OUTSIDETERRITORY);
                        else if (targetTerritory.getConqueredBy().equals(player.getName())
                                && targetTerritory.hasNeighbor(sourceTerritory)) {
                            // Action. Luckily, we can use the PostConquerPanel here
                            if (!Main.window.getPostConquerPanel().isVisible()) {
                                Main.window.getPostConquerPanel().setVisible(true);
                                Main.window.getPostConquerPanel().init(point, player, sourceTerritory);
                            }
                        } else if (targetTerritory.getConqueredBy().equals(player.getName())
                                && !targetTerritory.hasNeighbor(sourceTerritory)) {
                            Main.window.setInfoLabelText(Constants.NOTNEIGHBORS);
                        } else if (targetTerritory.getConqueredBy().equals(computer.getName())) {
                            Main.window.setInfoLabelText(Constants.NOVALIDTARGET);
                        }
                    }
                    else
                        Main.window.setInfoLabelText(Constants.CANTMOVE);

                }
                break;

            // The phase after conquering a territory, where you can take troops from the source T with you
            case Constants.PHASE_POSTCONQUER:
                Territorium conquered = HelperMethods.getTerritoriumFromPoint(point);
                if(targetTerritory.getName().equals(conquered.getName())){
                    if (!Main.window.getPostConquerPanel().isVisible()) {
                        Main.window.getPostConquerPanel().setVisible(true);
                        Main.window.getPostConquerPanel().init(point, player, sourceTerritory);
                    }
                }
                else{
                    GameElements.gamePhase = Constants.PHASE_ATTACKFROM;
                }
                break;
        }

    }

    // MouseMotionListener methods
    @Override
    public void mouseMoved(MouseEvent e) {
        // Show territory's name in the lower right corner when hovering over it
        Point p = e.getPoint();

        // Check if hovering the ocean
        if(HelperMethods.getTerritoriumFromPoint(p) == null){
            for (String country : GameElements.COUNTRIES){
                Territorium t = GameElements.TERRITORIA.get(country);
                t.setHovered(false);
            }

            // Set label to ocean
            Main.window.setCurrentTLabelText(Constants.OCEAN);
        }
        else {
            for (String country : GameElements.COUNTRIES) {
                Territorium territorium = GameElements.TERRITORIA.get(country);
                for (Polygon shape : territorium.getShapes()) {
                    if (shape.contains(p)) {
                        Main.window.setCurrentTLabelText(territorium.getName());

                        // Highlight the hovered territory
                        checkHovered(territorium, p);
                    }
                }
            }
        }

        Main.window.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseClicked(e);
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

    private void checkHovered(Territorium current, Point p){

        current.setHovered(true);

        for (String country : GameElements.COUNTRIES){
            Territorium t = GameElements.TERRITORIA.get(country);
            if(t.isHovered()){
                for(Polygon poly : t.getShapes()){
                    if(!poly.contains(p))
                        t.setHovered(false);
                }
            }
        }
    }

    private void unselectTerritorries(){
        for (String country : GameElements.COUNTRIES){
            Territorium current = GameElements.TERRITORIA.get(country);
            current.setSelected(false);
        }
    }
}
