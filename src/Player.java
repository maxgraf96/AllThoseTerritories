/**
 * Created by max on 18.01.16.
 */
public class Player implements IPlayer {

    // Fields
    String name = "";

    int territoriesCount = 0;

    int enforcements = 0;

    // Constructor
    public Player(String name){
        this.name = name;
    }

    @Override
    public void pick() {

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
