import java.util.ArrayList;
import java.util.List;

/**
 * Created by max on 14.01.16.
 */
public class Continent {
    private String name = "";
    private List<Territorium> territoria = new ArrayList<>();
    boolean isConquered = false;
    private int continentValue;

    public Continent(String name, List<Territorium> territoria, boolean isConquered) {
        this.name = name;
        this.territoria = territoria;
        this.isConquered = isConquered;
    }

    public Continent(String name){
        this.name = name;
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

    public void addTerritorium(Territorium territorium){this.territoria.add(territorium);}

    public void setContinentValue(int value){this.continentValue = value;}
}
