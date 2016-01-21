import java.awt.*;
import java.util.List;

/**
 * Created by max on 18.01.16.
 */
public class Player {

    // Fields
    String name = Constants.PLAYER;

    int territoriesCount = 0;

    int enforcements = 0;

    // Constructor
    public Player(){}

    // 0 => no territory selected, 1 => success, 2 => opponent's territory, 3 => already yours
    public int pick(List<String> countries, Point p) {
        // Make computer's turn
        GameElements.turn = false;

        for (int i = 0; i < countries.size(); i++) {
            Territorium current = GameElements.TERRITORIA.get(countries.get(i));
            for (int j = 0; j < current.getShapes().size(); j++) {
                if(current.getShapes().get(j).contains(p.getX(),p.getY())){
                    if(!current.isConquered()) {
                        current.setConquered(true);
                        current.setConqueredBy(name);
                        current.setNumberOfArmies(1);
                        // Change Label
                        current.getArmiesView().setText(String.valueOf(current.getNumberOfArmies()));
                        return 1;
                    }
                    else if(current.isConquered() && current.getConqueredBy() == Constants.COMPUTER)
                        return 2;
                    else if(current.isConquered() && current.getConqueredBy() == Constants.PLAYER)
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
            // Set game phases
            GameElements.gamePhase = Constants.CONQUER;
            GameElements.conquerPhase = Constants.ENFORCE;

            // Start enforcements
            AllThoseTerritories.window.getGame().startEnforcements();
        }

        // Player didn't click inside a territory. Has to click again
        return 0;
    }

    public void enforce(Point point){

    }
    // Calculate enforcements
    public int calcEnforcements(){
        int territories = 0;
        int bonus = 0;
        int enforcements;
        for (String continentname : GameElements.CNAMES) {

            Continent current = GameElements.CONTINENTS.get(continentname);
            boolean all = true;

            for(Territorium cT : current.getTerritoria()){
                if(cT.getConqueredBy().equals(name)){
                    territories++;
                }
                else all = false;
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
