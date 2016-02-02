import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by max on 12.01.16.
 */
public class Main extends JFrame {

    public static UI window;
    public static void main(String[] args) throws IOException {

        // Read a map file
        MapReader mapReader = new MapReader();

        // Get a Map which assings each line of a world file to an integer
        HashMap<Integer,String> myStringMap = mapReader.getStringMap();

        /* Assings each country all its territories, the capital city and the continent
            (All in one method)
        */
        mapReader.interpretTerritories(myStringMap);

        // Initialize the User Interface
        window = new UI();
        window.init();
        window.genesis();

        // for testing and info
        HashMap<String, Territorium> tempMap = GameElements.TERRITORIA;
        int bk = 7;
    }

}

