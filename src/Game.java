import java.awt.event.ComponentAdapter;

/**
 * Created by max on 18.01.16.
 */

// For picking territories and initializing the game
public class Game {

    public Player player = new Player();
    public Computer computer = new Computer();

    public void init(){
        boolean turn = false;
        for (int i = 0; i < GameElements.COUNTRIES.size(); i++) {
            if(turn){
                player.pick(GameElements.COUNTRIES);
                turn = !turn;
            }
            else{
                computer.pick(GameElements.COUNTRIES);
                turn = !turn;
            }
        }
    }
}

class Initialization{

    private void pickTerritories(){

    }
}

class Gameplay{

}
