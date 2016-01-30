import java.util.Arrays;
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
                Main.window.setInfoLabelText("");
                // No need to redraw window, ^^^^^^^^^^^^^^ does that
            }
        }
    }

    public void enforce(List<String> countries){
        boolean successfullyEnforced = false;
        while(!successfullyEnforced) {
            int random = (int) (Math.random() * GameElements.COUNTRIES.size() - 1);
            Territorium current = GameElements.TERRITORIA.get(GameElements.COUNTRIES.get(random));
            if(current.getConqueredBy() == name){
                if (shouldEnforce()) {
                    // Update armies
                    current.setNumberOfArmies(current.getNumberOfArmies() + howMany());
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
        int lostTroops;
        int enemyLostTroops; // Enemy here is player

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
                            lostTroops = 0;
                            enemyLostTroops = 0;

                            // Get computer attack strength
                            if (availableTroops > 3)
                                availableTroops = 3;
                            else if (availableTroops == 3)
                                availableTroops = 2;
                            else if (availableTroops == 2)
                                availableTroops = 1;
                            else if (availableTroops == 1)
                                availableTroops = 0;

                            // Get enemy defense strenght
                            if (enemyTroops > 1)
                                enemyTroops = 2;
                            else if (enemyTroops == 1)
                                enemyTroops = 1;
                            else
                                enemyTroops = 0;

                            // One dice per attacking/defending army
                            // We have 6 cases: 3 vs. 2, 3 vs. 1, 2 vs. 2, 2 vs. 1, 1 vs. 2, 1 vs. 1
                            // We need a switch here because we need to be able to break the loop when done to avoid multiple
                            // attacks when we want only one!
                            switch (availableTroops) {
                                case 3:
                                    if (enemyTroops == 2) {
                                        int[] attackDice = new int[3];
                                        int[] defenseDice = new int[2];

                                        // Roll the dice
                                        for (int j = 0; i < attackDice.length; i++) {
                                            attackDice[j] = (int) (Math.random() * 6 + 1);
                                        }
                                        for (int j = 0; i < defenseDice.length; i++) {
                                            defenseDice[j] = (int) (Math.random() * 6 + 1);
                                        }

                                        // Sort
                                        Arrays.sort(attackDice);
                                        Arrays.sort(defenseDice);

                                        // compare from best to worst
                                        if (compare(attackDice[2], defenseDice[1])) {
                                            enemyTroops--;
                                            enemyLostTroops++;
                                        } else {
                                            availableTroops--;
                                            lostTroops++;
                                        }
                                        if (compare(attackDice[1], defenseDice[0])) {
                                            enemyTroops--;
                                            enemyLostTroops++;
                                        } else {
                                            availableTroops--;
                                            lostTroops++;
                                        }

                                        break;
                                    }
                                    if (enemyTroops == 1) {
                                        int[] attackDice = new int[3];
                                        int defenseDice;

                                        // Roll the dice
                                        for (int j = 0; i < attackDice.length; i++) {
                                            attackDice[j] = (int) (Math.random() * 6 + 1);
                                        }
                                        defenseDice = (int) (Math.random() * 6 + 1);

                                        // Sort
                                        Arrays.sort(attackDice);

                                        // compare
                                        if (compare(attackDice[2], defenseDice)) {
                                            enemyTroops--;
                                            enemyLostTroops++;
                                        } else {
                                            availableTroops--;
                                            lostTroops++;
                                        }
                                    }
                                    break;
                                case 2:
                                    if (enemyTroops == 2) {
                                        int[] attackDice = new int[2];
                                        int[] defenseDice = new int[2];

                                        // Roll the dice
                                        for (int j = 0; i < attackDice.length; i++) {
                                            attackDice[j] = (int) (Math.random() * 6 + 1);
                                        }
                                        for (int j = 0; i < defenseDice.length; i++) {
                                            defenseDice[j] = (int) (Math.random() * 6 + 1);
                                        }

                                        // Sort
                                        Arrays.sort(attackDice);
                                        Arrays.sort(defenseDice);

                                        // compare from best to worst
                                        if (compare(attackDice[1], defenseDice[1])) {
                                            enemyTroops--;
                                            enemyLostTroops++;
                                        } else {
                                            availableTroops--;
                                            lostTroops++;
                                        }
                                        if (compare(attackDice[0], defenseDice[0])) {
                                            enemyTroops--;
                                            enemyLostTroops++;
                                        } else {
                                            availableTroops--;
                                            lostTroops++;
                                        }

                                        break;
                                    }
                                    if (enemyTroops == 1) {
                                        int[] attackDice = new int[2];
                                        int defenseDice;

                                        // Roll the dice
                                        for (int j = 0; i < attackDice.length; i++) {
                                            attackDice[j] = (int) (Math.random() * 6 + 1);
                                        }
                                        defenseDice = (int) (Math.random() * 6 + 1);

                                        // Sort
                                        Arrays.sort(attackDice);

                                        // compare
                                        if (compare(attackDice[1], defenseDice)) {
                                            enemyTroops--;
                                            enemyLostTroops++;
                                        } else {
                                            availableTroops--;
                                            lostTroops++;
                                        }
                                    }
                                    break;
                                case 1:
                                    if (enemyTroops == 2) {
                                        int attackDice;
                                        int[] defenseDice = new int[2];

                                        // Roll the dice
                                        attackDice = (int) (Math.random() * 6 + 1);
                                        for (int j = 0; i < defenseDice.length; i++) {
                                            defenseDice[j] = (int) (Math.random() * 6 + 1);
                                        }

                                        // Sort
                                        Arrays.sort(defenseDice);

                                        // compare
                                        if (compare(attackDice, defenseDice[1])) {
                                            enemyTroops--;
                                            enemyLostTroops++;
                                        } else {
                                            availableTroops--;
                                            lostTroops++;
                                        }

                                        break;
                                    }
                                    if (enemyTroops == 1) {
                                        int attackDice;
                                        int defenseDice;

                                        // Roll the dice
                                        attackDice = (int) (Math.random() * 6 + 1);
                                        defenseDice = (int) (Math.random() * 6 + 1);

                                        // compare
                                        if (compare(attackDice, defenseDice)) {
                                            enemyTroops--;
                                            enemyLostTroops++;
                                        } else {
                                            availableTroops--;
                                            lostTroops++;
                                        }
                                    }
                                    break;
                            }

                            // Attack done, if no troops left in attacked territory, move
                            if(enemyTroops == 0){
                                // Update conquered territory
                                enemyTerritory.setConqueredBy(name);
                                enemyTerritory.setNumberOfArmies(availableTroops);

                                // Update source territory
                                current.setNumberOfArmies(current.getNumberOfArmies() - availableTroops);

                            }
                            // Else you lose :(
                            else{
                                // Update enemy territory
                                enemyTerritory.setNumberOfArmies(enemyTerritory.getNumberOfArmies() - enemyLostTroops);

                                // Update source territory
                                current.setNumberOfArmies(current.getNumberOfArmies() - lostTroops);
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

    // True if computer wins, false otherwise
    private boolean compare(int yours, int enemy){
        if(yours > enemy)
            return true;
        if(yours < enemy)
            return false;
        if(yours == enemy)
            return false;

        return false;
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
    private boolean shouldEnforce(){
        int range = (int) (Math.random() * 10000);
        return range > 5000;
    }

    private int howMany(){
        int number = (int) ((Math.random() + 0.1) * this.enforcements);
        this.enforcements -= number;
        return number;
    }

    public String getName() {
        return name;
    }
}
