import java.util.ArrayList;
import java.util.List;

/**
 * Created by max on 14.01.16.
 */
public class Continent {
    private String name = "";
    private List<Territorium> territoria = new ArrayList<>();
    private int bonus;

    public Continent(String name, List<Territorium> territoria) {
        this.name = name;
        this.territoria = territoria;
    }

    // Constructor
    public Continent(String name){
        this.name = name;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public List<Territorium> getTerritoria() {
        return territoria;
    }

    public void addTerritorium(Territorium territorium){this.territoria.add(territorium);}

    public void setBonus(int value){this.bonus = value;}

    public int getBonus(){
        return this.bonus;
    }

}
