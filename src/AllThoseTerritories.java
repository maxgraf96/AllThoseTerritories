import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

/**
 * Created by max on 12.01.16.
 */
public class AllThoseTerritories extends JFrame {

    public static void main(String[] args) {

        // Test read a map file
        MapReader mapReader = new MapReader();

        HashMap<Integer,String> myMap = mapReader.getStringMap();

        HashMap<String, List<Polygon>> myDrawMap = mapReader.interpretTerritories(myMap);

        // Initialize the User Interface
        UI window = new UI();
        window.init(myDrawMap);

        int bk = 7;
    }

}

