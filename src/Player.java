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
    public Player(){}

    // 0 => no territory selected, 1 => success, 2 => opponent's territory, 3 => already yours
    public int pick(List<String> countries, Point p) {
        for (int i = 0; i < countries.size(); i++) {
            Territorium current = GameElements.TERRITORIA.get(countries.get(i));
            for (Shape shape : current.getShapes()) {
                if(shape.contains(p.getX(),p.getY())){
                    if(!current.isConquered()) {
                       current.conquer(1,name);
                        // Change Label
                        current.getArmiesView().setText(String.valueOf(current.getNumberOfArmies()));
                        return 1;
                    }
                    else if(current.getConqueredBy().equals(Constants.COMPUTER))
                        return 2;
                    else if(current.getConqueredBy().equals(Constants.PLAYER))
                        return 3;
                }
            }
        }

        // Check if all territories are conquered, if yes begin conquer phase
        boolean allconq = true;
        for(int i = 0; i < GameElements.TERRITORIA.size(); i++){
            if(!GameElements.TERRITORIA.get(GameElements.COUNTRIES.get(i)).isConquered())
                allconq = false;
        }
        if(allconq){// All continents are conquered
            // Show message that all territories have been selected and start the game
            AllThoseTerritories.window.getConquerIntroPanel().setVisible(true);

            // Start enforcements
            AllThoseTerritories.window.getGame().startEnforcementPhase();
        }

        // Make computer's turn
        GameElements.turn = false;

        // Player didn't click inside a territory. Has to click again
        return 0;
    }

    // 0 => no territory selected, 1 => success, 2 => opponent's territory
    public void enforce(Point point, int state){
        if(state == 1){// Only if he has clicked inside a territory which he owns
            // Change labels
            AllThoseTerritories.window.setInfoLabelText("You can now enforce your territories. You have " +
                    + this.getEnforcements() + ". Choose wisely.");
        }
        else if(state == 2){
            // Enemy T selected. Tell user to click his own territory
            AllThoseTerritories.window.setInfoLabelText(Constants.OPPONENTSTERRITORY);
            // Hide dialog
            //AllThoseTerritories.window.remove(AllThoseTerritories.window.getEnforcePanel());
        }
        else{
            // Tell user to click inside a territory
            AllThoseTerritories.window.setInfoLabelText(Constants.OUTSIDETERRITORY);
            // Hide dialog
            //AllThoseTerritories.window.remove(AllThoseTerritories.window.getEnforcePanel());
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
