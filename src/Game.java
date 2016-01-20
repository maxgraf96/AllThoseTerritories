import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by max on 15.01.16.
 */
public class Game implements MouseListener {

    // Fields
    // Players
    public Player player = new Player();
    public Computer computer = new Computer();

    // Constructor
    public Game(){}

    @Override
    public void mouseClicked(MouseEvent e) {

        switch (GameElements.gamePhase){
            case "pick":
                // Get click coords
                Component source = e.getComponent();
                Point p = source.getMousePosition();

                // Fire
                int checkClick = player.pick(GameElements.COUNTRIES, p);
                if(checkClick == 1){// Only if he has clicked inside a territory
                    // Redraw window
                    AllThoseTerritories.window.repaint();

                    // Computer's turn
                    computer.pick(GameElements.COUNTRIES);
                }
                else if(checkClick == 2){
                    // Enemy T selected. Tell user to click a free territory
                    AllThoseTerritories.window.setInfoLabelText(Constants.OPPONENTSTERRITORY);
                }
                else{
                    // Tell user to click inside a territory
                    AllThoseTerritories.window.setInfoLabelText(Constants.OUTSIDETERRITORY);
                }

                break;

            case "conquer":

        }

        // Test for clicking countries
        Component source = e.getComponent();
        Point p = source.getMousePosition();

        for(String country : GameElements.COUNTRIES){
            Territorium territorium = GameElements.TERRITORIA.get(country);

            for(Polygon shape : territorium.getShapes()){
                if(shape.contains(p)){
                    System.out.println(territorium.getName());
                }
            }
        }
    }



    // Rest listener methods
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
