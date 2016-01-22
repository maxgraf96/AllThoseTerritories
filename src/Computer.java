import java.util.List;

/**
 * Created by max on 18.01.16.
 */
public class Computer {

    // Fields
    String name = Constants.COMPUTER;

    int territoriesCount = 0;// Not used atm

    int enforcements = 0;

    // Constructor
    public Computer(){}

    // Methods
    public void pick(List<String> countries) {
        boolean success = false;
        while(!success){
            String country = countries.get((int) (Math.random() * countries.size()));
            Territorium current = GameElements.TERRITORIA.get(country);
            if(!current.isConquered()) {
               current.conquer(1,name);
                // Change Label
                GameElements.TERRITORIA.get(country).getArmiesView()
                        .setText(String.valueOf(GameElements.TERRITORIA.get(country).getNumberOfArmies()));

                // SUCCESS
                success = true;

                // Update territoriesCount
                this.territoriesCount++;

                // Player's turn
                GameElements.turn = !GameElements.turn;
                // Set the infoLabel to empty
                AllThoseTerritories.window.setInfoLabelText("");
                // No need to redraw window, ^^^^^^^^^^^^^^ does that
            }
            // Check if all territories are conquered, if yes begin conquer phase
            boolean allconq = true;
            for(int i = 0; i < GameElements.TERRITORIA.size(); i++){
                if(!GameElements.TERRITORIA.get(GameElements.COUNTRIES.get(i)).isConquered())
                    allconq = false;
            }
            if(allconq){
                // Show message that all territories have been selected and start the game
                AllThoseTerritories.window.getConquerIntroPanel().setVisible(true);

                // Start enforcements
                AllThoseTerritories.window.getGame().startEnforcementPhase();

                break;
            }
        }
    }

    public void enforce(List<String> countries){
        for (int i = 0; i < GameElements.COUNTRIES.size(); i++) {
            Territorium current = GameElements.TERRITORIA.get(GameElements.COUNTRIES.get(i));
            if(current.getConqueredBy() == name) {
                if (shouldEnforce()) {
                    // Update armies
                    current.setNumberOfArmies(current.getNumberOfArmies() + howMany());
                    // Update label over capital city
                    current.getArmiesView().setText(String.valueOf(current.getNumberOfArmies()));
                }
            }
        }

        AllThoseTerritories.window.getGame().startAttackPhase();
    }

    // Calculate enforcements
    public int calcEnforcements(){
        int territories = 0;
        int bonus = 0;
        int enforcements;

        // Here you need two loops: one that loops through all the territories and one that loops through
        // all the continents. Because if you play on e.g. africa.map you would get no territories.
        // 1st
        for(String country : GameElements.COUNTRIES){
            Territorium current = GameElements.TERRITORIA.get(country);
            if(current.getConqueredBy().equals(name))
                territories++;

        }
        // 2nd
        // Only for checking boni
        for (String continentname : GameElements.CNAMES) {
            Continent current = GameElements.CONTINENTS.get(continentname);
            boolean all = true;

            for(Territorium cT : current.getTerritoria()){
                if(!cT.getConqueredBy().equals(name)){
                    all = false;
                }
            }
            if(all)
                bonus += current.getBonus();
        }

        enforcements = (territories / 3 + bonus);
        // Put enforcements in player variable
        this.enforcements = enforcements;
        // Return
        return enforcements;
    }

    // Should computer enforce?
    private boolean shouldEnforce(){
        int range = (int) (Math.random() * 10000);
        return range > 5000;
    }

    private int howMany(){
        int number = (int) (Math.random() * this.enforcements);
        this.enforcements -= number;
        return number;
    }
}
