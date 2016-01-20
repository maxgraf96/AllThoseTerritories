import java.util.List;

/**
 * Created by max on 18.01.16.
 */
public class Computer {

    // Fields
    String name = Constants.COMPUTER;

    int territoriesCount = 0;

    int enforcements = 0;

    // Constructor
    public Computer(){}

    // Methods
    public void pick(List<String> countries) {
        boolean success = false;
        while(!success){
            String country = countries.get((int) (Math.random() * countries.size()));
            if(!GameElements.TERRITORIA.get(country).isConquered()) {
                // Set conquered true
                GameElements.TERRITORIA.get(country).setConquered(true);
                // Set conqueredBy to computer
                GameElements.TERRITORIA.get(country).setConqueredBy(name);
                GameElements.TERRITORIA.get(country).setNumberOfArmies(1);

                // SUCCESS
                success = true;

                // Player's turn
                GameElements.turn = !GameElements.turn;
                // Set the infoLabel to empty
                AllThoseTerritories.window.setInfoLabelText("");
                // No need to redraw window, ^^^^^^^^^^^^^^ does that
            }
            // Check if all territories are conquered, if yes begin conquer phase
            boolean allconq = true;
            for(int i = 0; i < GameElements.TERRITORIA.size(); i++){
                if(!GameElements.TERRITORIA.get(GameElements.COUNTRIES.get(i)).isConquered())
                    allconq = false;
            }
            if(allconq){
                GameElements.gamePhase = "conquer";
                break;
            }
        }
    }
}
