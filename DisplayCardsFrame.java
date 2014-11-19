
package danielle;

import javax.swing.JFrame;

/**
 *
 * @author D. Tixier
 * Display the cards in a frame lol?
 */
public class DisplayCardsFrame extends JFrame {

    final int widthOfFrame;
    //DisplayCardsPanel dcpanel = new DisplayCardsPanel();

    public DisplayCardsFrame(Card[] dd) {
        widthOfFrame = 350;
        //add(dcpanel);
        add(new DisplayCardsPanel(dd));
        setTitle("Danielle's Hand");
        setSize(widthOfFrame, Danielle_1m.cardPictureIcon[1].getIconHeight() + 50);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
//            setResizable(false); Not using this command because it messes it up
//            It does not like repainting at all.
    }
}
