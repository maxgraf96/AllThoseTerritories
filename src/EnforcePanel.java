import javax.swing.*;
import java.awt.*;

/**
 * Created by max on 20.01.16.
 */
public class EnforcePanel extends JPanel {

    // Fields
    JButton confirm = new JButton("Yes");
    JButton decline = new JButton("No");

    // Constructor
    public EnforcePanel(){
        this.setBounds(50,50,160,40);
        this.setLayout(new FlowLayout());

        this.confirm.setBounds(50, 50, 80, 20);
        this.decline.setBounds(50, 50, 80, 20);

        this.add(confirm);
        this.add(decline);

        this.setVisible(false);
    }
}
