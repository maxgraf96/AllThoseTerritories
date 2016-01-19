import java.util.List;

/**
 * Created by max on 18.01.16.
 */
public interface IPlayer {

    String name = "";

    int territoriesCount = 0;

    int enforcements = 0;

    void pick(List<String> countries);
}
