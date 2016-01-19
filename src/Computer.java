import java.awt.event.ComponentAdapter;
import java.util.List;

/**
 * Created by max on 18.01.16.
 */
public class Computer implements IPlayer {

    // Fields
    String name = Constants.COMPUTER;

    int territoriesCount = 0;

    int enforcements = 0;

    // Constructor
    public Computer(){}

    // Methods
    @Override
    public void pick(List<String> countries) {
        GameElements.turn = false;
        boolean success = false;
        while(!success){
            String country = countries.get((int) (Math.random() * countries.size()));
            if(!GameElements.TERRITORIA.get(country).isConquered()) {
                // Set conquered true
                GameElements.TERRITORIA.get(country).setConquered(true);
                // Set conqueredBy to computer
                GameElements.TERRITORIA.get(country).setConqueredBy(name);

                // SUCCESS
                success = true;
            }
        }
    }
}
