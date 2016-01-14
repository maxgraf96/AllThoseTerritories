import java.util.List;

/**
 * Created by max on 14.01.16.
 */
public class Territorium {

    private String name = "";
    private List<Territorium> neighbors;
    private List<List<Coordinates>> shape;
    private String capitalcity = "";

    public Territorium(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Territorium> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Territorium> neighbors) {
        this.neighbors = neighbors;
    }

    public List<List<Coordinates>> getShape() {
        return shape;
    }

    public void setShape(List<List<Coordinates>> shape) {
        this.shape = shape;
    }

    public String getCapitalcity() {
        return capitalcity;
    }

    public void setCapitalcity(String capitalcity) {
        this.capitalcity = capitalcity;
    }
}
