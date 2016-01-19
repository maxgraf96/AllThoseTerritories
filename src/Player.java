import java.util.List;

/**
 * Created by max on 18.01.16.
 */
public class Player implements IPlayer {

    // Fields
    String name = Constants.PLAYER;

    int territoriesCount = 0;

    int enforcements = 0;

    // Constructor
    public Player(){}

    @Override
    public void pick(List<String> countries) {

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
