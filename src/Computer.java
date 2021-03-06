import java.util.List;

/**
 * Created by max on 18.01.16.
 */
public class Computer {

    // Fields
    private String name = Constants.COMPUTER;

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
            if(current.getConqueredBy().equals(Constants.COMPUTER)) {
                int maxIndex = current.getNeighbors().size() - 1;
                for (int i = 0; i <= maxIndex; i++) {
                    int randomIndex = HelperMethods.randomWithRange(0, maxIndex);
                    if (!current.getNeighbors().get(randomIndex).isConquered()) {
                        current = current.getNeighbors().get(randomIndex);
                        break;
                    }
                }
            }
            if(!current.isConquered()) {
                current.conquer(1, name);
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
                Main.window.setInfoLabelText("");
                // No need to redraw window, ^^^^^^^^^^^^^^ does that
            }
        }
    }

    public void enforce(List<String> countries){
        boolean successfullyEnforced = false;
        int bonusProbability = -51;
        while(!successfullyEnforced) {
            int random = (int) (Math.random() * (GameElements.COUNTRIES.size() - 1));
            Territorium current = GameElements.TERRITORIA.get(GameElements.COUNTRIES.get(random));
            if(current.getConqueredBy().equals(name)){
                int troops = howMuchEnforce(current);
                if (troops != 0) {
                    // Update armies
                    current.setNumberOfArmies(current.getNumberOfArmies() + troops);
                    // Update label over capital city
                    current.getArmiesView().setText(String.valueOf(current.getNumberOfArmies()));
                    if(enforcements == 0)
                        successfullyEnforced = true;
                }
            }
        }

        Main.window.getGame().startAttackPhase();
    }

    public void attack() {
        // How often should the computer attack?
        int attackCounter = (int) (Math.random() * 20);

        int availableTroops;
        int enemyTroops;

        for (int k = 1; k <= attackCounter; k++) {
            for (int i = 0; i < GameElements.COUNTRIES.size(); i++) {
                Territorium current = GameElements.TERRITORIA.get(GameElements.COUNTRIES.get(i));
                if (current.getConqueredBy().equals(name)) {
                    if (current.getNumberOfArmies() > 1 && current.canAttack()) {
                        Territorium enemyTerritory = null;

                        // Choose random enemy territory to attack
                        boolean chosen = false;
                        while (chosen == false) {
                            int c = (int) (Math.random() * current.getNeighbors().size());
                            if (!current.getNeighbors().get(c).getConqueredBy().equals(name)) {
                                enemyTerritory = current.getNeighbors().get(c);
                                chosen = true;
                            }
                        }

                        if (enemyTerritory != null) {
                            availableTroops = current.getNumberOfArmies();
                            enemyTroops = enemyTerritory.getNumberOfArmies();

                            // Get computer attack strength
                            if (availableTroops > 3) availableTroops = 4;
                            availableTroops -= 1;
                            // Get enemy  player defense strength
                            if (enemyTroops > 2) enemyTroops = 2;

                            // Attack done, if no troops left in attacked territory, move
                           HelperMethods.attack(current, enemyTerritory,availableTroops,enemyTroops);

                            if(enemyTerritory.getConqueredBy().equals(Constants.COMPUTER)){
                                boolean move = true;
                                for (Territorium each : current.getNeighbors()) {
                                    if (each.getConqueredBy().equals(Constants.PLAYER))
                                        move = false;
                                }
                                if(move) {
                                    enemyTerritory.setNumberOfArmies(current.getNumberOfArmies() - 1 + enemyTerritory.getNumberOfArmies());
                                    current.setNumberOfArmies(1);
                                }
                                System.out.println(move);
                            }

                            // Repaint
                            Main.window.repaint();
                        }
                    }
                }
            }
        }
        GameElements.gamePhase = Constants.PHASE_ENFORCE;
        Main.window.getGame().startEnforcementPhase();
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

        enforcements = (territories / 3 + bonus) < 3 ? 1 : (territories / 3 + bonus);
        // Put enforcements in player variable
        this.enforcements = enforcements;
        // Return
        return enforcements;
    }

    // Should computer enforce?
    private int howMuchEnforce(Territorium current) {
        int probabilityModifier = -99;
        for (Territorium each : current.getNeighbors()) {
            if (each.getConqueredBy().equals(Constants.PLAYER))
                probabilityModifier += 30;
        }
        int chanceToEnforce = (int) ((Math.random() * 101) + probabilityModifier);
        if (chanceToEnforce > 0) {
            int min = (chanceToEnforce* this.enforcements) / 100 < this.enforcements ? (chanceToEnforce * this.enforcements)/100 : this.enforcements;
            int number = HelperMethods.randomWithRange(min , this.enforcements);
            this.enforcements -= number;
            probabilityModifier = -99;
            return number;
        }
        else {
            probabilityModifier = -99;
            return 0;
        }
    }

    public String getName() {
        return name;
    }
}
