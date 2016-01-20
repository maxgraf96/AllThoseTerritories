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
            if(!GameElements.TERRITORIA.get(country).isConquered()) {
                // Set conquered true
                GameElements.TERRITORIA.get(country).setConquered(true);
                // Set conqueredBy to computer
                GameElements.TERRITORIA.get(country).setConqueredBy(name);
                GameElements.TERRITORIA.get(country).setNumberOfArmies(1);
                // Change Label
                GameElements.TERRITORIA.get(country).getArmiesView()
                        .setText(String.valueOf(GameElements.TERRITORIA.get(country).getNumberOfArmies()));

                // SUCCESS
                success = true;

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
                // Set game phases
                GameElements.gamePhase = Constants.CONQUER;
                GameElements.conquerPhase = Constants.ENFORCE;

                // Show message that all territories have been selected and start the game
                AllThoseTerritories.window.getConquerIntroPanel().setVisible(true);

                // Start
                enforce();

                break;
            }
        }
    }

    public void enforce(){
        this.enforcements = calcEnforcements();
        int bk = 7;

        AllThoseTerritories.window.infoLabel.setText("You can now enforce your territories. You have "
                + GameElements.playerEnforcements + " to choose from. Choose wisely.");
        GameElements.turn = true;
    }

    // Calculate enforcements
    private int calcEnforcements(){
        int territories = 0;
        int bonus = 0;
        for (String continentname : GameElements.CNAMES) {

            Continent current = GameElements.CONTINENTS.get(continentname);
            boolean all = true;

            for(Territorium cT : current.getTerritoria()){
                if(cT.getConqueredBy().equals(name)){
                    territories++;
                }
                else all = false;
            }
            if(all)
                bonus += current.getBonus();
        }
        return (territories / 3 + bonus);
    }
}
