import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * Created by max on 29.01.16.
 */
public class AttackPanel extends JPanel {

    // Fields
    JLabel desc = new JLabel("It's ");
    JLabel desc2 = new JLabel(" against ");
    JButton confirm = new JButton("Confirm");
    JButton cancel = new JButton("Cancel");
    JLabel numbers = new JLabel("0");
    JLabel enemyNumbers = new JLabel("0");

    // Listeners
    ActionListener confirmListener;
    ActionListener cancelListener;

    int availableTroops;
    int enemyTroops;
    int lostTroops = 0;
    int enemyLostTroops = 0;

    // Constructor
    public AttackPanel(){
        this.setLayout(new FlowLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.black));

        this.desc.setBounds(0, 0, 280, 20);
        this.desc2.setBounds(0, 0, 280, 20);
        this.confirm.setBounds(0, 0, 80, 20);
        this.cancel.setBounds(0, 0, 80, 20);
        this.enemyNumbers.setBounds(0, 0, 80, 20);

        this.add(desc);
        this.add(numbers);
        this.add(desc2);
        this.add(enemyNumbers);
        this.add(confirm);
        this.add(cancel);

        this.setVisible(false);
    }

    public void init(Point point, Player player, Territorium sourceTerritory){
        // Get data
        Territorium enemyTerritory = HelperMethods.getTerritoriumOnClick(point);
        availableTroops = sourceTerritory.getNumberOfArmies();
        enemyTroops = enemyTerritory.getNumberOfArmies();

        // Get your attack strength
        if(availableTroops > 3)
            availableTroops = 3;
        else if(availableTroops == 3)
            availableTroops = 2;
        else if(availableTroops == 2)
            availableTroops = 1;
        else if(availableTroops == 1)
            availableTroops = 0;

        // Get enemy defense strenght
        if(enemyTroops > 1)
            enemyTroops = 2;
        else if(enemyTroops == 1)
            enemyTroops = 1;
        else
            enemyTroops = 0;

        // Set numbers
        numbers.setText(String.valueOf(availableTroops));
        enemyNumbers.setText(String.valueOf(enemyTroops));

        // Position at click
        // If click is too near to the right border then draw it "on the left side"
        if(point.x > Constants.WIDTH - 450)
            this.setBounds(point.x - 450, point.y, 450, 70);
        else
            this.setBounds(point.x, point.y, 450, 70);

        //Add listeners
        confirmListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Action!
                attack();

                // Attack done, if no troops left in attacked territory, move
                if(enemyTroops == 0){
                    // Update conquered territory
                    enemyTerritory.setConqueredBy(player.getName());
                    enemyTerritory.setNumberOfArmies(availableTroops);

                    // Update source territory
                    sourceTerritory.setNumberOfArmies(sourceTerritory.getNumberOfArmies() - availableTroops);

                    // GamePhase to postConquer
                    // Only if you have sufficient troops
                    if(sourceTerritory.getNumberOfArmies() > 1) {
                        GameElements.gamePhase = Constants.PHASE_POSTCONQUER;
                        Main.window.setInfoLabelText(Constants.WANTPOSTCONQUER);
                    }
                    else {
                        GameElements.gamePhase = Constants.PHASE_ATTACKFROM;
                        Main.window.setInfoLabelText(Constants.CHOOSEENEMYT);
                    }
                }
                // Else you lose :(
                else{
                    // Update enemy territory
                    enemyTerritory.setNumberOfArmies(enemyTerritory.getNumberOfArmies() - enemyLostTroops);

                    // Update source territory
                    sourceTerritory.setNumberOfArmies(sourceTerritory.getNumberOfArmies() - lostTroops);

                    // GamePhase to ATTACKFROM
                    GameElements.gamePhase = Constants.PHASE_ATTACKFROM;
                }

                // Repaint
                Main.window.repaint();
                // Always
                reset();
                // Lights off!
                setVisible(false);

            }
        };
        cancelListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                setVisible(false);

                // Back to selecting your source T
                GameElements.gamePhase = Constants.PHASE_ATTACKFROM;
            }
        };
        confirm.addActionListener(confirmListener);
        cancel.addActionListener(cancelListener);

        // Lights on!
        this.setVisible(true);
    }

    private void attack() {
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
                    for (int i = 0; i < attackDice.length; i++) {
                        attackDice[i] = (int) (Math.random() * 6 + 1);
                    }
                    for (int i = 0; i < defenseDice.length; i++) {
                        defenseDice[i] = (int) (Math.random() * 6 + 1);
                    }

                    // Sort
                    Arrays.sort(attackDice);
                    Arrays.sort(defenseDice);

                    // compare from best to worst
                    if (compare(attackDice[2], defenseDice[1])) {
                        enemyTroops--;
                        enemyLostTroops++;
                    }
                    else {
                        availableTroops--;
                        lostTroops++;
                    }
                    if(compare(attackDice[1], defenseDice[0])) {
                        enemyTroops--;
                        enemyLostTroops++;
                    }
                    else {
                        availableTroops--;
                        lostTroops++;
                    }

                    break;
                }
                if (enemyTroops == 1) {
                    int[] attackDice = new int[3];
                    int defenseDice;

                    // Roll the dice
                    for (int i = 0; i < attackDice.length; i++) {
                        attackDice[i] = (int) (Math.random() * 6 + 1);
                    }
                    defenseDice = (int) (Math.random() * 6 + 1);

                    // Sort
                    Arrays.sort(attackDice);

                    // compare
                    if (compare(attackDice[2], defenseDice)) {
                        enemyTroops--;
                        enemyLostTroops++;
                    }
                    else {
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
                    for (int i = 0; i < attackDice.length; i++) {
                        attackDice[i] = (int) (Math.random() * 6 + 1);
                    }
                    for (int i = 0; i < defenseDice.length; i++) {
                        defenseDice[i] = (int) (Math.random() * 6 + 1);
                    }

                    // Sort
                    Arrays.sort(attackDice);
                    Arrays.sort(defenseDice);

                    // compare from best to worst
                    if (compare(attackDice[1], defenseDice[1])) {
                        enemyTroops--;
                        enemyLostTroops++;
                    }
                    else {
                        availableTroops--;
                        lostTroops++;
                    }
                    if(compare(attackDice[0], defenseDice[0])) {
                        enemyTroops--;
                        enemyLostTroops++;
                    }
                    else {
                        availableTroops--;
                        lostTroops++;
                    }

                    break;
                }
                if (enemyTroops == 1) {
                    int[] attackDice = new int[2];
                    int defenseDice;

                    // Roll the dice
                    for (int i = 0; i < attackDice.length; i++) {
                        attackDice[i] = (int) (Math.random() * 6 + 1);
                    }
                    defenseDice = (int) (Math.random() * 6 + 1);

                    // Sort
                    Arrays.sort(attackDice);

                    // compare
                    if (compare(attackDice[1], defenseDice)) {
                        enemyTroops--;
                        enemyLostTroops++;
                    }
                    else {
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
                    for (int i = 0; i < defenseDice.length; i++) {
                        defenseDice[i] = (int) (Math.random() * 6 + 1);
                    }

                    // Sort
                    Arrays.sort(defenseDice);

                    // compare
                    if (compare(attackDice, defenseDice[1])) {
                        enemyTroops--;
                        enemyLostTroops++;
                    }
                    else {
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
                    }
                    else {
                        availableTroops--;
                        lostTroops++;
                    }
                }
                break;
        }
    }

    // True if player wins, false if computer wins
    private boolean compare(int yours, int enemy){
        if(yours > enemy)
            return true;
        if(yours < enemy)
            return false;
        if(yours == enemy)
            return false;

        return false;
    }
    public void reset(){
        availableTroops = 0;
        enemyTroops = 0;
        lostTroops = 0;
        enemyLostTroops = 0;

        // Remove listeners - this is a MUST!
        confirm.removeActionListener(confirmListener);
        cancel.removeActionListener(cancelListener);
    }
}
