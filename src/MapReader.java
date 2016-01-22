import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Nemanja on 16.01.2016.
*/
public class MapReader {

    // Map which assings each line of a world file to an integer
    private HashMap<Integer, String> stringMap;

    // Constructor
    public MapReader() throws IOException{
        stringMap = readMap();
    }

    // Getter
    public HashMap<Integer, String> getStringMap(){
        return stringMap;
    }

    // Methods
    public HashMap<Integer,String> readMap() throws IOException{

        // HashMap for each line of map file
        HashMap<Integer, String> stringMap = new HashMap();
        // Counter for line numbers in HashMap
        int counter = 0;

        BufferedReader br = new BufferedReader(new FileReader("Resources/Maps/world.map"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);

                // Put line into Hashmap
                stringMap.put(counter,line);

                // Up the counter
                counter++;

                // Get next line
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
        return stringMap;
    }

    public String readWord(StringBuilder line) {
        /** Method Explanation **
         *The readWord gets a line as input and returns the first word in it while also removing it from the line.
         *It is meant to be used repeatedly until the line is obliterated. :)
         */
        for (int index = 0; ; index++) {
            if (line.charAt(index) == ' ') {
                String word = line.substring(0, index); //Gets the first word(or set of numbers) without the space after it
                    // Everything
                    line.delete(0, index + 1); // deletes the word from the line with the space after it
                    return word;
                }
            }
        }


    public String readName(StringBuilder line) {
        /** Method Explanation **
         *The readName gets a line as input and returns the first constructable name
         *While also removing it from the line
         * It checks for the first character in the line to see if it's a part of a name
         */
        String name = "";
        char charCheck = line.charAt(0);

        while(charCheck >= 'A' && charCheck <= 'Z'){ // Checks if the first character in the line is a letter
            String currentWord = readWord(line);
            name += name.equals("") ?  currentWord : " " + currentWord;
            if(line.toString().equals(""))
                return name;
            else
                charCheck = line.charAt(0);
        } return name;
    }

    public void readCoordinates(StringBuilder line, List<Coordinates> coordinatesList) {
        /**Method Explanation**
         * Takes a line and the coordinatesList as input and fills the coordinatesList with coordinates
         * While also removing them from the line
         * It checks for the first character in the line to see if it's a part of a coordinate
         */
        String x = "";
        String y = "";
        boolean isXcoordinate = true; //Used as an indicator to see if a coordinate is X or Y
        char charCheck = line.charAt(0);
        while(charCheck >= '0' && charCheck <= '9') {// Checks if the first character in the line is a number
            if (isXcoordinate) {
                x = readWord(line);
                isXcoordinate = false;
            }
            else {
                y = readWord(line);
                isXcoordinate = true;
                Coordinates coords = new Coordinates(Integer.parseInt(x), Integer.parseInt(y));
                coordinatesList.add(coords);
            }
            if(line.toString().equals(""))
                break;
            else
                charCheck = line.charAt(0);
        }
    }

    public Polygon PointsToPolygon(List<Coordinates> coordinatesList){
        /**Method Explanation**
         * Uses the coordinates makes a polygon out of them
         */

        // Convert List of coordinates into a Polygon
        int[] xCoordinates = new int[coordinatesList.size()];
        int[] yCoordinates = new int[coordinatesList.size()];


        for (int j = 0; j < coordinatesList.size(); j++) {
            xCoordinates[j] = coordinatesList.get(j).getX();
            yCoordinates[j] = coordinatesList.get(j).getY();
        }

        // This polygon defines one shape, e.g. island, country etc
        Polygon polygon = new Polygon(xCoordinates,yCoordinates,coordinatesList.size());
        return polygon;
    }

    public void interpretTerritories(HashMap<Integer, String> worldMap){

        // HashMap of territoria
        HashMap<String, Territorium> territoriumHashMap = new HashMap<>();

        //HashMap of Continents
        HashMap<String, Continent> continentHashMap = new HashMap<>();

        for (int i = 0; i < worldMap.size(); i++){
            // ArrayList to save values
            List<Coordinates> coordinatesList = new ArrayList<>();

            StringBuilder line = new StringBuilder(worldMap.get(i) + ' ');// Additional space -> end of line mark
            String checkTypeOfLine = readWord(line);
            String countryName = "";
            switch (checkTypeOfLine) {
                case "patch-of":
                    countryName = readName(line);
                    readCoordinates(line, coordinatesList);
                    Polygon polygonPatch = PointsToPolygon(coordinatesList);
                    if (!territoriumHashMap.containsKey(countryName)) { // if it does not contain the country
                        Territorium current = new Territorium(countryName);
                        territoriumHashMap.put(countryName, current);
                    }
                    territoriumHashMap.get(countryName).addShape(polygonPatch);
                    break;
                case "capital-of":
                    countryName = readName(line);
                    readCoordinates(line, coordinatesList);
                    territoriumHashMap.get(countryName).setCapitalcity(coordinatesList.get(0));
                    // Add country name to GameElements
                    GameElements.COUNTRIES.add(countryName);
                    break;
                case "neighbors-of":
                    countryName = readName(line);
                    String neighbourName = "";
                    while (!line.toString().equals("")) {
                        readWord(line);
                        neighbourName = readName(line);
                        territoriumHashMap.get(countryName).addNeighbour(territoriumHashMap.get(neighbourName));
                    }
                    break;
                case "continent":
                    String continentName = readName(line);
                    Continent current = new Continent(continentName);
                    current.setBonus(Integer.parseInt(readWord(line)));

                    while (!line.toString().equals("")) {
                        readWord(line);// cuts the " : " and " - " out of the line
                        countryName = readName(line);
                        current.addTerritorium(territoriumHashMap.get(countryName));
                    }

                    // Add to list of continents
                    GameElements.CONTINENTS.put(continentName, current);
                    // Also add name of c to list of name of continents
                    GameElements.CNAMES.add(current.getName());
                    break;
            }
        }

        // Make territories accessible from every part of the game
        GameElements.TERRITORIA = territoriumHashMap;
    }

}
