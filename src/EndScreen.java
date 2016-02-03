import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by max on 03.02.16.
 */
public class EndScreen extends JPanel {

    JButton Exit = new JButton("Exit");

    public EndScreen(){
        Exit.setBounds(Constants.WIDTH / 2 - 40, (Constants.HEIGHT / 3) * 2, 80, 40);
        Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(Exit);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g;

        // Check if game is over
        if(GameElements.gameOver){
            BufferedImage endImage;
            // Player has won
            if(GameElements.winner){
                try {
                    endImage = ImageIO.read(new File("Resources/Images/win.png"));
                    graphics2D.drawImage(endImage, 0, 0, null);
                }
                catch (IOException e){}
            }
            else{
                try {
                    endImage = ImageIO.read(new File("Resources/Images/lose.png"));
                    graphics2D.drawImage(endImage, 0, 0, null);
                }
                catch (IOException e){}
            }
        }
    }
}
