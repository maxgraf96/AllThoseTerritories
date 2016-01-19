import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by max on 14.01.16.
 */
public class Territorium {

    private String name = "";
    private List<Territorium> neighbours = new ArrayList<>();
    private Coordinates capitalcity;
    private List<Polygon> shapes = new ArrayList<>();

    private boolean isConquered = false;
    private String conqueredBy = Constants.NONE;
    private int numberOfArmies = 0;

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
        return neighbours;
    }

    public void setNeighbors(List<Territorium> neighbors) {
        this.neighbours = neighbors;
    }

    public List<Polygon> getShapes() {
        return shapes;
    }

    public void setShapes(List<Polygon> shapes) {
        this.shapes = shapes;
    }

    public void addShape(Polygon polygon){this.shapes.add(polygon);}

    public Coordinates getCapitalcity() {
        return capitalcity;
    }

    public void setCapitalcity(Coordinates capitalcity) {
        this.capitalcity = capitalcity;
    }

    public void addNeighbour(Territorium territorium){this.neighbours.add(territorium);}
}
