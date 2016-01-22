import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by max on 20.01.16.
 */
public class ConquerIntroPanel extends JPanel {

    // Fields
    JLabel info = new JLabel("All territories selected. Press start to begin the Game");
    JButton confirm = new JButton("Start");

    // Constructor
    public ConquerIntroPanel(){
        this.setBounds(Constants.WIDTH / 3, Constants.HEIGHT -500, 400, 80);
        this.setLayout(new FlowLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.black));

        this.info.setBounds(50, 50, 260, 20);
        this.confirm.setBounds(50, 50, 80, 20);

        // Add listener
        confirm.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        this.add(info);
        this.add(confirm);

        this.setVisible(false);
    }
}
