package danielle;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author D. Tixier
 * Display the cards lol?
 */
public class DisplayCardsPanel extends JPanel {

    final int widthOfFrame;
    int x;
    Card[] dd;

    public DisplayCardsPanel(Card[] dd) {
        widthOfFrame = 350;
        setLayout(null);
        setSize(widthOfFrame, Danielle_1m.cardPictureIcon[1].getIconHeight() + 50);
        x = widthOfFrame - Danielle_1m.cardPictureIcon[1].getIconWidth() * 2;
        this.dd = dd;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 51; i >= 0; i--) {
            // If the card is held by 'W', add it to the frame
            if (dd[i].isOwner('W')) {
                Danielle_1m.cardPictureLabel.get(i).setBounds(x, 0, Danielle_1m.cardPictureLabel.get(i).getPreferredSize().width, Danielle_1m.cardPictureLabel.get(i).getPreferredSize().height);
                add(Danielle_1m.cardPictureLabel.get(i));
                x -= 15;
            }
        }
    }
}
