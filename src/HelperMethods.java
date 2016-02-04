import java.awt.*;
import java.util.Arrays;

/**
 * Created by max on 29.01.16.
 */
public class HelperMethods {

    public static Territorium getTerritoriumFromPoint(Point point) {
        for(String country : GameElements.COUNTRIES) {
            Territorium current = GameElements.TERRITORIA.get(country);
            for(Polygon shape : current.getShapes()) {
                if (shape.contains(point.x, point.y)) {
                    return current;
                }
            }
        }
        return null;
    }

    public static boolean checkIfOneRulesThemAll(){
        boolean oneRulesThemAll = true;
        String ruler ="";
        for(String each : GameElements.COUNTRIES){
            Territorium current = GameElements.TERRITORIA.get(each);
            if (ruler.equals("")) ruler = current.getConqueredBy();
            else if(!current.getConqueredBy().equals(ruler)) oneRulesThemAll = false;
        }
        return oneRulesThemAll;
    }
    public static boolean attack (Territorium sourceTerritory, Territorium targetTerritory, int availableTroops, int enemyTroops ) {
        // returns true if the attack is successful;
        // Action!
        int lostTroops;
        int enemyLostTroops;
        int[] attackerAndDefenderTroopsLost = resolveFight(availableTroops, enemyTroops);
        lostTroops = attackerAndDefenderTroopsLost[0];
        enemyLostTroops = attackerAndDefenderTroopsLost[1];

        // Update enemy territory
        targetTerritory.setNumberOfArmies(targetTerritory.getNumberOfArmies() - enemyLostTroops);

        // Update source territory
        sourceTerritory.setNumberOfArmies(sourceTerritory.getNumberOfArmies() - lostTroops);
        availableTroops -= lostTroops;
        enemyTroops -= enemyLostTroops;


        // Attack done, if no troops left in attacked territory, move
        if (targetTerritory.getNumberOfArmies() == 0) {

            // Update conquered territory
            targetTerritory.conquer(availableTroops, sourceTerritory.getConqueredBy());

            // Update source territory
            sourceTerritory.setNumberOfArmies(sourceTerritory.getNumberOfArmies() - availableTroops);

            // If one player has conquered all territories the game ends.
            if(checkIfOneRulesThemAll()){
                GameElements.gameOver = true;
                Territorium check = GameElements.TERRITORIA.get(GameElements.COUNTRIES.get(0));
                String winner = check.getConqueredBy();

                // Set winner
                GameElements.winner = winner.equals(Constants.PLAYER);

                Main.window.getEndScreen().repaint();
                Main.window.getEndScreen().setVisible(true);
            }

            Main.window.repaint();
            return true;
        }
        return false;
    }

    public static int[] resolveFight(int numberOfAttackerDice, int numberOfDefenderDice){
        // returns an array where the first element is the number of attacker troops lost
        // and the second is the number of defender troops lost
        int[] attackerDice = throwDice(numberOfAttackerDice);
        int[] defenderDice = throwDice(numberOfDefenderDice);
        int numberOfComparisons = numberOfAttackerDice < numberOfDefenderDice ? numberOfAttackerDice : numberOfDefenderDice;

        int attackerLosses = 0;
        int defenderLosses = 0;
        for(int i = 1; i <= numberOfComparisons; i++) {
            if (compare(attackerDice[numberOfAttackerDice - i], defenderDice[numberOfDefenderDice - i]))
                defenderLosses++;
            else
                attackerLosses++;
        }
        int[] attackerAndDefenderTroopsLost = new int[2];
        attackerAndDefenderTroopsLost[0] = attackerLosses;
        attackerAndDefenderTroopsLost[1] = defenderLosses;
        return attackerAndDefenderTroopsLost;
    }

    public static boolean compare(int attacker, int defender){
        if(attacker > defender)
            return true;
        if(attacker <= defender)
            return false;

        return false;
    }
    public static int[] throwDice(int numberOfDice){
        //returns a sorted array of dice throws;
        int[] diceThrows = new int[numberOfDice];
        for(int i = 0; i <= numberOfDice-1; i++){
            diceThrows[i] = randomWithRange(1,6);
        }
        Arrays.sort(diceThrows);
       return diceThrows;
    }

    public static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

}