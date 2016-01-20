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

    // 0 => no territory selected, 1 => success, 2 => opponent's territory
    public int pick(List<String> countries, Point p) {
        for (int i = 0; i < countries.size(); i++) {
            Territorium current = GameElements.TERRITORIA.get(countries.get(i));
            for (int j = 0; j < current.getShapes().size(); j++) {
                if(current.getShapes().get(j).contains(p.getX(),p.getY())){
                    if(!current.isConquered()) {
                        current.setConquered(true);
                        current.setConqueredBy(name);
                        current.setNumberOfArmies(1);
                        return 1;
                    }
                    else{
                        return 2;
                    }
                }
            }
        }

        // Check if all territories are conquered, if yes begin conquer phase
        boolean allconq = true;
        for(int i = 0; i < GameElements.TERRITORIA.size(); i++){
            if(!GameElements.TERRITORIA.get(GameElements.COUNTRIES.get(i)).isConquered())
                allconq = false;
        }
        if(allconq){
            GameElements.gamePhase = "conquer";
        }

        // Player didn't click inside a territory. Has to click again
        return 0;
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
