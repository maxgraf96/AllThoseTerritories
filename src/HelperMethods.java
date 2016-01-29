import java.awt.*;

/**
 * Created by max on 29.01.16.
 */
public class HelperMethods {
    public static Territorium getTerritoriumOnClick(Point point) {
        for (int i = 0; i < GameElements.COUNTRIES.size(); i++) {
            Territorium current = GameElements.TERRITORIA.get(GameElements.COUNTRIES.get(i));
            for (int j = 0; j < current.getShapes().size(); j++) {
                if (current.getShapes().get(j).contains(point.x, point.y)) {
                    return current;
                }
            }
        }
        return null;
    }
}