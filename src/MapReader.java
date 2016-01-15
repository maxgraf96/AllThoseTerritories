import javax.print.DocFlavor;
import javax.print.attribute.IntegerSyntax;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;

/**
 * Created by max on 13.01.16.
 */
public class MapReader {

    private HashMap<Integer, String> stringMap;

    public MapReader(){
        stringMap = readMap();
    }

    public HashMap<Integer,String> readMap(){

        // HashMap for each line of map file
        HashMap<Integer, String> stringMap = new HashMap();
        // Counter for line numbers in HashMap
        int counter = 0;

        try(BufferedReader br = new BufferedReader(new FileReader("Resources/Maps/world.map"))) {
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
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return stringMap;
    }

    public HashMap<String, List<List<Coordinates>>> interpretTerritories(HashMap<Integer, String> worldMap){

        // patchOfList assigns each country a number of point coordinates
        HashMap<String, List<List<Coordinates>>> patchOfHashMap = new HashMap();

        // List of countries for access in WorldMap.paintComponent(Graphics g);
        List<String> countries = new ArrayList<>();

        // HashMap of territoria
        HashMap<String, Territorium> territoriumHashMap = new HashMap<>();

        for (int i = 0; i < worldMap.size(); i++) {
            String line = worldMap.get(i);
            if(line.contains("patch-of")){

                /*
                Stage 1: Get the country name
                 */

                // Write country name into this string
                String countryName = "";

                // Remove the "patch-of " part
                line = line.replace("patch-of ", "");

                // Use a String reader to get the country name
                StringReader stringReader = new StringReader(line);

                try{
                    boolean cont = true;
                    while(cont){
                        char current = (char)stringReader.read();
                        if(current == '0' || current == '1' || current == '2' || current == '3' || current == '4' ||
                                current == '5' || current == '6' || current == '7' || current == '8' || current == '9') {
                            cont = false;
                        }
                        else {
                            countryName += current;
                        }
                    }
                } catch (IOException e){
                    countryName = "Country name not found!";
                }

                // Remove last charachter (this is a space symbol)
                countryName = countryName.substring(0, countryName.length() - 1);

                /*
                Stage two: Get coordinates
                 */
                // Use a String reader to get the country name
                StringReader coorReader = new StringReader(line);

                // ArrayList to save values
                List<Coordinates> coordinatesList = new ArrayList<>();

                try{
                    boolean letters = true;
                    int spacecount = 0;
                    String x = "";
                    String y = "";

                    while(letters){
                        char current = (char)coorReader.read();
                        if(current == '0' || current == '1' || current == '2' || current == '3' || current == '4' ||
                                current == '5' || current == '6' || current == '7' || current == '8' || current == '9') {
                            letters = false;
                            x += current;
                        }
                    }

                    while(spacecount < 3) {
                        if (spacecount == 2) {
                            // Add coordinates to list
                            Coordinates coords = new Coordinates(Integer.parseInt(x), Integer.parseInt(y));
                            coordinatesList.add(coords);
                            x = "";
                            y = "";

                            // Reset spacecount
                            spacecount = 0;
                        }
                        int tempcurrent = coorReader.read();
                        // Exit at end of line
                        if (tempcurrent == -1) {
                            spacecount = 3;
                             // Add last coordinates
                            Coordinates last = new Coordinates(Integer.parseInt(x),Integer.parseInt(y));
                            coordinatesList.add(last);
                        }
                        else {
                            char ccurrent = (char) tempcurrent;
                            if (ccurrent == ' ') {
                                spacecount++;
                            } else {
                                if (spacecount == 1)
                                    y += ccurrent;
                                else
                                    x += ccurrent;
                            }
                        }
                    }


                } catch (IOException e){
                    countryName = "No coordinates found!";
                }

                // Close
                stringReader.close();
                coorReader.close();

                // Check if country is already in HashMap
                if(patchOfHashMap.containsKey(countryName)){
                    // Add new patch
                    patchOfHashMap.get(countryName).add(coordinatesList);

                    // Add to territoria
                    territoriumHashMap.get(countryName).getShapes().add(coordinatesList);
                }
                else{
                    // Create new entry
                    List<List<Coordinates>> listoflists = new ArrayList<>();
                    listoflists.add(coordinatesList);
                    patchOfHashMap.put(countryName, listoflists);

                    // Add to the GameElements.TERRITORIA list
                    Territorium current = new Territorium(countryName);
                    current.setShapes(listoflists);
                    territoriumHashMap.put(countryName,current);
                }

                // Add country names to List of countries for access in WorldMap.paintComponent(Graphics g);
                if(!countries.contains(countryName)){
                    countries.add(countryName);
                }
            }
        }

        // Add list of country strings to Constants
        GameElements.COUNTRIES = countries;

        // Add HashMap of Territoria
        GameElements.TERRITORIA = territoriumHashMap;

        return patchOfHashMap;
    }

    public HashMap<Integer, String> getStringMap(){
        return stringMap;
    }
}
