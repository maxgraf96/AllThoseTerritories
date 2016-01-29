import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by max on 14.01.16.
 */
public class GameElements {
    // String array for country names
    public static List<String> COUNTRIES = new ArrayList<>();
    public static List<String> CNAMES = new ArrayList<>();

    // List of territoria with info
    public static HashMap<String, Territorium> TERRITORIA = new HashMap<>();

    // List of continents
    public static HashMap<String, Continent> CONTINENTS = new HashMap<>();

    // To know which game phase you're in
    public static String gamePhase = Constants.PHASE_PICK;

    public static boolean turn = true; // True if your turn, false if computer's turn
}
