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

    @Override
    public void mouseClicked(MouseEvent e) {

        // Get click coords
        Point point = e.getPoint();
        int state;

        switch (GameElements.gamePhase){
            case Constants.PICK:
                // Fire
                state = player.pick(GameElements.COUNTRIES, point);

                if(state == 1){// Only if he has clicked inside a territory
                    AllThoseTerritories.window.setCurrentTLabelText("SUCCESS");
                    // Redraw window
                    AllThoseTerritories.window.repaint();

                    // Computer's turn
                    computer.pick(GameElements.COUNTRIES);
                }
                else if(state == 2){
                    // Enemy T selected. Tell user to click a free territory
                    AllThoseTerritories.window.setInfoLabelText(Constants.OPPONENTSTERRITORY);
                }
                else if(state == 3){
                    // Your T selected. Tell user to select another territory
                    AllThoseTerritories.window.setInfoLabelText(Constants.YOURTERRITORY);
                }
                else{
                    // Tell user to click inside a territory
                    AllThoseTerritories.window.setInfoLabelText(Constants.OUTSIDETERRITORY);
                }

                break;

            // The conquer phase needs to be broken down into various sub-phases
            case Constants.CONQUER:
                switch (GameElements.conquerPhase){
                    case Constants.ENFORCE:

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
}
