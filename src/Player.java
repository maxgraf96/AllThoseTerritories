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

    public boolean pick(List<String> countries, Point p) {
        for (int i = 0; i < countries.size(); i++) {
            Territorium current = GameElements.TERRITORIA.get(countries.get(i));
            for (int j = 0; j < current.getShapes().size(); j++) {
                if(current.getShapes().get(j).contains(p.getX(),p.getY())){
                    if(!current.isConquered()) {
                        current.setConquered(true);
                        current.setConqueredBy(name);
                        current.setNumberOfArmies(1);
                        return true;
                    }
                    else{
                        // is conquered, add logic later
                    }
                }
            }
        }
        // Player didn't click inside a territory. Has to click again
        return false;
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
