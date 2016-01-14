import java.util.List;

/**
 * Created by max on 14.01.16.
 */
public class Continent {
    private String name = "";
    private List<Territorium> territoria;
    boolean isConquered = false;

    public Continent(String name, List<Territorium> territoria, boolean isConquered) {
        this.name = name;
        this.territoria = territoria;
        this.isConquered = isConquered;
    }

    public String getName() {
        return name;
    }

    public List<Territorium> getTerritoria() {
        return territoria;
    }

    public boolean isConquered() {
        return isConquered;
    }

    public void setConquered(boolean conquered) {
        isConquered = conquered;
    }
}
