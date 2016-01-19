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

    // Methods
    public void init(){

        // Pick turn by turn until all countries are picked
        for (int i = 0; i < GameElements.COUNTRIES.size(); i++) {
            if(GameElements.turn){
                player.pick(GameElements.COUNTRIES);
                GameElements.turn = !GameElements.turn;
            }
            else{
                computer.pick(GameElements.COUNTRIES);
                GameElements.turn = !GameElements.turn;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        switch (GameElements.gamePhase){
            case "pick":

                break;

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
