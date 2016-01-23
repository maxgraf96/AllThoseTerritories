import javax.swing.*;
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
    // Label for displaying number of current troops
    JLabel armiesView = new JLabel(String.valueOf(this.numberOfArmies));

    public Territorium(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Territorium> getNeighbors() {
        return neighbours;
    }

    public List<Polygon> getShapes() {
        return shapes;
    }

    public void addShape(Polygon polygon){this.shapes.add(polygon);}

    public Coordinates getCapitalcity() {
        return capitalcity;
    }

    public void setCapitalcity(Coordinates capitalcity) {
        this.capitalcity = capitalcity;
    }

    public void addNeighbour(Territorium territorium){this.neighbours.add(territorium);}

    public boolean isConquered() {
        return isConquered;
    }

    public void conquer(int numberOfArmies, String conquerer){
        if(!isConquered)this.setConquered(true);
        this.setConqueredBy(conquerer);
        this.setNumberOfArmies(1);
    }

    public void setConquered(boolean conquered) {
        isConquered = conquered;
    }

    public String getConqueredBy() {
        return conqueredBy;
    }

    public void setConqueredBy(String conqueredBy) {
        this.conqueredBy = conqueredBy;
    }

    public int getNumberOfArmies() {
        return numberOfArmies;
    }

    public void setNumberOfArmies(int numberOfArmies) {
        this.numberOfArmies = numberOfArmies;
    }

    public JLabel getArmiesView() {
        return armiesView;
    }

    public void setArmiesViewText(String text) {
        this.armiesView.setText(text);
    }


}
