package danielle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 *
 * @author D. Tixier
 * Class to produce a GUI in which cards can be entered.
 */
public class HandInputFrame extends JFrame {

    // Make button for each number
    JCheckBox[] cbAll = new JCheckBox[52];
    JButton ok = new JButton("OK");
    JPanel colorPanel = new JPanel();
    char player;
    Card[] deck;
    /** The last card that was <em>setKnown</em> */
    int lastCardPlayed;
    
    // Frame Constructor    
    public HandInputFrame(char player, Card[] dealtCards) {
        this.deck = dealtCards;
        cbAll[0] = new JCheckBox("2 Spades");
        cbAll[1] = new JCheckBox("3 Spades");
        cbAll[2] = new JCheckBox("4 Spades");
        cbAll[3] = new JCheckBox("5 Spades");
        cbAll[4] = new JCheckBox("6 Spades");
        cbAll[5] = new JCheckBox("7 Spades");
        cbAll[6] = new JCheckBox("8 Spades");
        cbAll[7] = new JCheckBox("9 Spades");
        cbAll[8] = new JCheckBox("10 Spades");
        cbAll[9] = new JCheckBox("Jack Spades");
        cbAll[10] = new JCheckBox("Queen Spades");
        cbAll[11] = new JCheckBox("King Spades");
        cbAll[12] = new JCheckBox("Ace Spades");

        cbAll[13] = new JCheckBox("2 Hearts");
        cbAll[14] = new JCheckBox("3 Hearts");
        cbAll[15] = new JCheckBox("4 Hearts");
        cbAll[16] = new JCheckBox("5 Hearts");
        cbAll[17] = new JCheckBox("6 Hearts");
        cbAll[18] = new JCheckBox("7 Hearts");
        cbAll[19] = new JCheckBox("8 Hearts");
        cbAll[20] = new JCheckBox("9 Hearts");
        cbAll[21] = new JCheckBox("10 Hearts");
        cbAll[22] = new JCheckBox("Jack Hearts");
        cbAll[23] = new JCheckBox("Queen Hearts");
        cbAll[24] = new JCheckBox("King Hearts");
        cbAll[25] = new JCheckBox("Ace Hearts");

        cbAll[26] = new JCheckBox("2 Clubs");
        cbAll[27] = new JCheckBox("3 Clubs");
        cbAll[28] = new JCheckBox("4 Clubs");
        cbAll[29] = new JCheckBox("5 Clubs");
        cbAll[30] = new JCheckBox("6 Clubs");
        cbAll[31] = new JCheckBox("7 Clubs");
        cbAll[32] = new JCheckBox("8 Clubs");
        cbAll[33] = new JCheckBox("9 Clubs");
        cbAll[34] = new JCheckBox("10 Clubs");
        cbAll[35] = new JCheckBox("Jack Clubs");
        cbAll[36] = new JCheckBox("Queen Clubs");
        cbAll[37] = new JCheckBox("King Clubs");
        cbAll[38] = new JCheckBox("Ace Clubs");

        cbAll[39] = new JCheckBox("2 Diamonds");
        cbAll[40] = new JCheckBox("3 Diamonds");
        cbAll[41] = new JCheckBox("4 Diamonds");
        cbAll[42] = new JCheckBox("5 Diamonds");
        cbAll[43] = new JCheckBox("6 Diamonds");
        cbAll[44] = new JCheckBox("7 Diamonds");
        cbAll[45] = new JCheckBox("8 Diamonds");
        cbAll[46] = new JCheckBox("9 Diamonds");
        cbAll[47] = new JCheckBox("10 Diamonds");
        cbAll[48] = new JCheckBox("Jack Diamonds");
        cbAll[49] = new JCheckBox("Queen Diamonds");
        cbAll[50] = new JCheckBox("King Diamonds");
        cbAll[51] = new JCheckBox("Ace Diamonds");

        StatusListener colorListener = new StatusListener(colorPanel, player);

        // If playing a card, only let one box be checked.
        boolean ensureOnlyOneBoxChecked = (player == 'W') ? false : true;

        if (ensureOnlyOneBoxChecked) {
            ButtonGroup group = new ButtonGroup();
            // Since only one card can be played, don't let more than one box be checked
            for (int i = 0; i < cbAll.length; i++) {
                group.add(cbAll[i]);
            }

        }
        // Set a display label that changes color with status.
        // Yellow = not enough selected (inputing Danielle's hand only)
        // Red = Too many selected, or singleton card selected has already been played
        // Green = The right amount of cards selected, or that the singleton card has not been played by anyone and is not held by Danielle.
        for (int i = 0; i < cbAll.length; i++) {
            cbAll[i].addItemListener(colorListener);
        }

        setLayout(new FlowLayout());
        JPanel pSpades = new JPanel(new GridLayout(13, 1));
        JPanel pHearts = new JPanel(new GridLayout(13, 1));
        JPanel pClubs = new JPanel(new GridLayout(13, 1));
        JPanel pDiamonds = new JPanel(new GridLayout(13, 1));
        JPanel rightPanel = new JPanel(new GridLayout(2, 1));

        PlayCardListener listener = new PlayCardListener(player, this);

        ok.addActionListener(listener);

        // Add spade boxes to the 1st column panel
        for (int i = 0; i < 13; i++) {
            pSpades.add(cbAll[i]);
        }

        // Add heart boxes to the 2nd column panel
        for (int i = 13; i < 26; i++) {
            pHearts.add(cbAll[i]);
        }

        // Add club boxes to the 3rd column panel
        for (int i = 26; i < 39; i++) {
            pClubs.add(cbAll[i]);
        }

        // Add diamond boxes to the 4th column panel
        for (int i = 39; i < 52; i++) {
            pDiamonds.add(cbAll[i]);
        }

        colorPanel.setPreferredSize(new Dimension(35, 150));
        ok.setBorder(new BevelBorder(BevelBorder.RAISED, Color.black, Color.black));
        rightPanel.add(colorPanel);
        rightPanel.add(ok);

        //p1.setVisible(true);
        add(pSpades);
        add(pHearts);
        add(pClubs);
        add(pDiamonds);
        add(rightPanel);
        pack();

    }

    private class StatusListener implements ItemListener {

        int numChecked = 0;
        JPanel jpn;
        char player;

        public StatusListener(JPanel jpn, char player) {
            this.jpn = jpn;
            this.player = player;

        }

        @Override
        public void itemStateChanged(ItemEvent ie) {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                numChecked++;
            } else {
                numChecked--;
            }

            // Color the panel
            if (player != 'W') {
                jpn.setBackground(Color.green);
                // For checkboxes 0-51, if it is selected, and the card slot it represents has something in it, paint the side red because the card can't be played.
                for (int i = 0; i < 52; i++) {
                    if (ie.getStateChange() == ItemEvent.SELECTED && ie.getItem().equals(cbAll[i]) && !deck[i].isOwner('\u0000')) {
                        jpn.setBackground(Color.red);
                    }
                }
            } else {
                if (numChecked == 0) {
                    jpn.setBackground(Color.lightGray);
                } else if (numChecked < 13) {
                    jpn.setBackground(Color.yellow);
                } else if (numChecked > 13) {
                    jpn.setBackground(Color.red);
                } else {
                    jpn.setBackground(Color.green);
                }
            }
        }
    }

    private class PlayCardListener implements ActionListener {

        char x;
        HandInputFrame f;
        // Variable to make sure they don't accidently skip a play
        boolean canClose = false;

        public PlayCardListener(char x, HandInputFrame f) {
            this.x = x;
            this.f = f;
        }

        public PlayCardListener(HandInputFrame f) {
            x = 'W';
            this.f = f;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            // Get the selected card
            for (int i = 0; i < 52; i++) {
                if (cbAll[i].isSelected()) {
                    deck[i].setKnown(x);
                    lastCardPlayed = i;
                    //dd[i] = x;
                    canClose = true;
                }
            }

            // Close the window that the listener's button is in
            if (canClose) {
                f.dispose();
            } else {
                JOptionPane.showMessageDialog(rootPane, "A card must be played.");
            }
        }
    }
}
