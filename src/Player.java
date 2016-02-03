import java.awt.*;
import java.util.List;

/**
 * Created by max on 18.01.16.
 */
public class Player {

    // Fields
    String name = Constants.PLAYER;

    int territoriesCount = 0;// Not used atm

    int enforcements = 0;

    // Constructor
    public Player() {
    }

  // returns true only if it successfully picks a territory
    public boolean pick(List<String> countries, Point p) {
        for (int i = 0; i < countries.size(); i++) {
            Territorium current = GameElements.TERRITORIA.get(countries.get(i));
            for (Shape shape : current.getShapes()) {
                if (shape.contains(p.getX(), p.getY())) {
                    if (!current.isConquered()) {
                        current.conquer(1, name);
                        // Change Label
                        current.getArmiesView().setText(String.valueOf(current.getNumberOfArmies()));
                        return true;
                    } else if (current.getConqueredBy().equals(Constants.COMPUTER)) {
                        // Enemy T selected. Tell user to click a free territory
                        Main.window.setInfoLabelText(Constants.OPPONENTSTERRITORY);
                        return false;
                    } else if (current.getConqueredBy().equals(Constants.PLAYER)) {
                        // Your T selected. Tell user to select another territory
                        Main.window.setInfoLabelText(Constants.YOURTERRITORY);
                        return false;
                    }
                }
            }
        }// Player didn't click inside a territory. Has to click again
            Main.window.setInfoLabelText(Constants.OUTSIDETERRITORY);
            return false;
    }

    public void enforce(Point point, boolean selectedYours){
        if(selectedYours){// Only if he has clicked inside a territory which he owns
            // Change labels
            Main.window.setInfoLabelText("You can now enforce your territories. You have " +
                    + this.getEnforcements() + ". Choose wisely.");
        }
        else{
            if(HelperMethods.getTerritoriumFromPoint(point) == null) {
                // Tell user to click inside a territory
                Main.window.setInfoLabelText(Constants.OUTSIDETERRITORY);
            }
            else {
                // Enemy T selected. Tell user to click his own territory
                Main.window.setInfoLabelText(Constants.OPPONENTSTERRITORY);
            }
        }
    }

    // Helper methods
    // Calculate enforcements
    public int calcEnforcements(){

        int territories = 0;
        int bonus = 0;
        int enforcements;

        // Here you need two loops: one that loops through all the territories and one that loops through
        // all the continents. Because if you play on e.g. africa.map you would get no territories.
        // 1st
        for(String country : GameElements.COUNTRIES){
            Territorium current = GameElements.TERRITORIA.get(country);
            if(current.getConqueredBy().equals(name))
                territories++;

        }
        // 2nd
        // Only for checking boni
        for (String continentname : GameElements.CNAMES) {
            Continent current = GameElements.CONTINENTS.get(continentname);
            boolean all = true;

            for(Territorium cT : current.getTerritoria()){
                if(!cT.getConqueredBy().equals(name)){
                    all = false;
                }
            }
            if(all)
                bonus += current.getBonus();
        }

        enforcements = (territories / 3 + bonus);
        // Put enforcements in player variable
        this.enforcements = enforcements;
        // Return
        return enforcements;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTerritoriesCount() {
        return territoriesCount;
    }

    public void setTerritoriesCount(int territoriesCount) {
        this.territoriesCount = territoriesCount;
    }

    public int getEnforcements() {
        return enforcements;
    }

    public void setEnforcements(int enforcements) {
        this.enforcements = enforcements;
    }
}
