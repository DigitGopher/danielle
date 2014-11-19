
package danielle;

/**
 *
 * @author D. Tixier
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class Danielle_1m {

    /**
     * @param args the command line arguments
     */

    Card[] deck;
    //char[] dd = new char[52];
    static ImageIcon[] cardPictureIcon = new ImageIcon[52];
    static ArrayList<JLabel> cardPictureLabel = new ArrayList<>();
    JFrame background = new JFrame();
    JPanel statusPanel = new JPanel();
    JLabel scoresLabel = new JLabel();
    JLabel bidsLabel = new JLabel();
    JLabel tricksLabel = new JLabel();
    String partner = "", opponentN = "", opponentS = "";


    // Leader Constant, Bidding Constant, and High Card
    int lc, bc, hc;
    int[] currentTrick = new int[4];// represents a trick
    

    //@SuppressWarnings("empty-statement")
    public static void main(String[] args) {
        // Instantiate other classes! Remember to not reference them directly!
        final Danielle_1m danielle =  new Danielle_1m();
        Compute calc = new Compute();
        Score score = new Score();
        // Add a try-catch structure to let the user know that something went wrong when running external to netbeans.
        try{
        // Initialize the 52 card pictures
        for (int i = 0; i < 52; i++) {
            cardPictureIcon[i] = new ImageIcon(Danielle_1m.class.getResource("cardPictures/"+(i)+".png"));
        }
            
        // Initialize the 52 card labels
        for (int i = 0; i < 52; i++) {
            cardPictureLabel.add(new JLabel(cardPictureIcon[i]));
        }
        
        // Other variables
        String winningTeam = "Danielle and "+danielle.partner;
        
        
        // Set properties of the main frame for the program
        danielle.background.setTitle("Danielle_1.1");
        danielle.background.setSize(660, 600);
        danielle.background.setLocationRelativeTo(null);
        danielle.background.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        danielle.background.setVisible(true);
        danielle.background.setLayout(new BorderLayout());
        // Make a button to close the program.
        JButton closeButton = new JButton("Close Danielle_1.1");
        closeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                int option = JOptionPane.showConfirmDialog(danielle.background, "Exit the program?");
                if(option == JOptionPane.YES_OPTION)
                    System.exit(0);
            }
        });
        danielle.background.add(closeButton,BorderLayout.SOUTH);
        
        danielle.statusPanel.setLayout(new BorderLayout());
        danielle.statusPanel.add(danielle.scoresLabel, BorderLayout.NORTH);
        danielle.statusPanel.add(danielle.bidsLabel, BorderLayout.CENTER);
        danielle.statusPanel.add(danielle.tricksLabel, BorderLayout.SOUTH);
        
        danielle.background.add(danielle.statusPanel, BorderLayout.NORTH);
        
        
        // Get names
        danielle.partner = "Partner";//JOptionPane.showInputDialog(null, "Enter name of partner.");
        danielle.opponentN = "North";//JOptionPane.showInputDialog(null, "Enter name of opponent North.");
        danielle.opponentS = "South";//JOptionPane.showInputDialog(null, "Enter name of opponent South.");
        
        
        // Who bids first switches every deal, vs. who leads switches every trick.
        String bcAsString = "South";//JOptionPane.showInputDialog(null, "Who bids first?\nEnter name as entered previously.");
        danielle.bc = Compute.W;// Default Danielle bids
        if(bcAsString.equalsIgnoreCase("Danielle"))
            danielle.bc = Compute.W;
        else if(bcAsString.equalsIgnoreCase(danielle.opponentN))
            danielle.bc = Compute.N;
        else if(bcAsString.equalsIgnoreCase(danielle.partner))
            danielle.bc = Compute.E;
        else if(bcAsString.equalsIgnoreCase(danielle.opponentS))
            danielle.bc = Compute.S;
        else{
            JOptionPane.showMessageDialog(null, "You entered \"Who bids first?\" wrong. Now I have to start over.", "Fail Box", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        
        // What are we playing to?
        String wpvAsString = "100";//JOptionPane.showInputDialog(null, "What are we playing to? (Ex: 500)");
        score.winningPointValue = Integer.parseInt(wpvAsString);
        
        int highestScore = 0;
        while(highestScore < score.winningPointValue){
            // Reset everything to 0 for the new hand.
            
            // Reset tricks taken to 0.
            score.ourTricks = 0; score.theirTricks = 0;
            
            // Set leader constant equal to whoever bid first, since we are starting a new hand
            danielle.lc = danielle.bc;
            
            danielle.hc = 0;// The ever-changing high card
//            int[] currentTrick = new int[4];// represents a trick
            
            for (int i = 0; i < 4; i++) {
                score.is_nil[i] = false;
                score.is_setnil[i] = false;
            }
            
            score.ourBid = 0;
            score.theirBid = 0;
            
            // Loop in case Danielle calls a redeal
            while(true){
                // Reset all the cards to no information
                danielle.deck = new Card[52];
                for (int i = 0; i < danielle.deck.length; i++) {
                    danielle.deck[i] = new Card();
                    
                }
//                for (int i = 0; i < 52; i++) {
//                    danielle.dd[i] = '\u0000';
//                }    

                HandInputFrame hif = new HandInputFrame('W',danielle.deck);
                hif.setTitle("Input Danielle's Hand");
                hif.setLocationRelativeTo(null);
                hif.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                hif.setVisible(true);

                // Give time to enter values into frame
                while(hif.isVisible()){
                    System.out.print("");
                }
                
                score.bid[calc.W] = calc.danielleBids(danielle.deck, score.we, score.they, danielle.lc);

                // Call a redeal now if you are going to call it.
                if(score.bid[calc.W] != -2)
                    break;
                else
                    JOptionPane.showMessageDialog(null, "Danielle calls redeal. New hand!! Yes!!");
            }

            DisplayCardsFrame dcf = new DisplayCardsFrame(danielle.deck);
            
            
            // Bidding can't be exported to a method easily because of the continue aspect of a redeal.
            String s; // To use in getting values from inputdialogs
            // Other bids, switching on order of dealer, so input in the right order.
            switch(danielle.bc){
                case Compute.W:
                    // Danielle bids
                    if(score.bid[Compute.W] == -1){
                        JOptionPane.showMessageDialog(null, "Danielle bids nil");
                        score.ourBid += 1;// To get back to 0 when added up later.
                        score.is_nil[Compute.W] = true;
                    }
                    else if(score.bid[Compute.W] == -3){
                        JOptionPane.showMessageDialog(null, "Danielle bids Blind Nil as she should!");
                        score.ourBid += 3;// To get back to 0 when added up later.
                        score._is_blindnil = Compute.W;
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Danielle bids "+score.bid[Compute.W]);
                    
                    if(!danielle.enterOthersBid(danielle.opponentN, Compute.N, score, dcf)){
                        continue;//redeal
                    }
                    System.out.println(danielle.opponentN);
                    System.out.println(Compute.N);
                    System.out.println(score.bid);
                    System.out.println(dcf);
                    System.out.println(score.is_nil);
                    System.out.println(score.theirBid);
                    System.out.println(score._is_blindnil);
                    
                    

                    s = JOptionPane.showInputDialog(null, "What does "+danielle.partner+" bid?", "Input "+danielle.partner+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    score.bid[Compute.E] = Integer.parseInt(s);
                    if(score.bid[Compute.E] == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(score.bid[Compute.E] == -1){
                        score.is_nil[Compute.E] = true;
                        score.ourBid +=1;
                    }

                    s = JOptionPane.showInputDialog(null, "What does "+danielle.opponentS+" bid?", "Input "+danielle.opponentS+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    score.bid[Compute.S] = Integer.parseInt(s);
                    if(score.bid[Compute.S] == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(score.bid[Compute.S] == -1){
                        score.is_nil[Compute.S] = true;
                        score.theirBid +=1;
                    }
                    
                    break;
                    
                case Compute.N:
                    s = JOptionPane.showInputDialog(null, "What does "+danielle.opponentN+" bid?", "Input "+danielle.opponentN+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    score.bid[Compute.N] = Integer.parseInt(s);
                    if(score.bid[Compute.N] == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(score.bid[Compute.N] == -1){
                        score.is_nil[Compute.N] = true;
                        score.theirBid += 1;
                    }

                    s = JOptionPane.showInputDialog(null, "What does "+danielle.partner+" bid?", "Input "+danielle.partner+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    score.bid[Compute.E] = Integer.parseInt(s);
                    if(score.bid[Compute.E] == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(score.bid[Compute.E] == -1){
                        score.is_nil[Compute.E] = true;
                        score.ourBid +=1;
                    }

                    s = JOptionPane.showInputDialog(null, "What does "+danielle.opponentS+" bid?", "Input "+danielle.opponentS+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    score.bid[Compute.S] = Integer.parseInt(s);
                    if(score.bid[Compute.S] == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(score.bid[Compute.S] == -1){
                        score.is_nil[Compute.S] = true;
                        score.theirBid +=1;
                    }
                    
                    // Danielle bids
                    if(score.bid[Compute.W] == -1){
                        JOptionPane.showMessageDialog(null, "Danielle bids nil");
                        score.ourBid += 1;// To get back to 0 when added up later.
                        score.is_nil[Compute.W] = true;
                    }
                    else if(score.bid[Compute.W] == -3){
                        JOptionPane.showMessageDialog(null, "Danielle bids Blind Nil as she should!");
                        score.ourBid += 3;// To get back to 0 when added up later.
                        score._is_blindnil = Compute.W;
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Danielle bids "+score.bid[Compute.W]);
                    
                    break;
                    
                case Compute.E:
                    s = JOptionPane.showInputDialog(null, "What does "+danielle.partner+" bid?", "Input "+danielle.partner+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    score.bid[Compute.E] = Integer.parseInt(s);
                    if(score.bid[Compute.E] == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(score.bid[Compute.E] == -1){
                        score.is_nil[Compute.E] = true;
                        score.ourBid += 1;
                    }

                    s = JOptionPane.showInputDialog(null, "What does "+danielle.opponentS+" bid?", "Input "+danielle.opponentS+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    score.bid[Compute.S] = Integer.parseInt(s);
                    if(score.bid[Compute.S] == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(score.bid[Compute.S] == -1){
                        score.is_nil[Compute.S] = true;
                        score.theirBid += 1;
                    }
                    
                    // Danielle bids
                    if(score.bid[Compute.W] == -1){
                        JOptionPane.showMessageDialog(null, "Danielle bids nil");
                        score.ourBid += 1;// To get back to 0 when added up later.
                        score.is_nil[Compute.W] = true;
                    }
                    else if(score.bid[Compute.W] == -3){
                        JOptionPane.showMessageDialog(null, "Danielle bids Blind Nil as she should!");
                        score.ourBid += 3;// To get back to 0 when added up later.
                        score._is_blindnil = Compute.W;
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Danielle bids "+score.bid[calc.W]);
                    
                    s = JOptionPane.showInputDialog(null, "What does "+danielle.opponentN+" bid?", "Input "+danielle.opponentN+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    score.bid[Compute.N] = Integer.parseInt(s);
                    if(score.bid[Compute.N] == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(score.bid[Compute.N] == -1){
                        score.is_nil[Compute.N] = true;
                        score.theirBid += 1;
                    }
                    
                    break;
                    
                case Compute.S:
                    s = JOptionPane.showInputDialog(null, "What does "+danielle.opponentS+" bid?", "Input "+danielle.opponentS+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    score.bid[Compute.S] = Integer.parseInt(s);
                    if(score.bid[Compute.S] == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(score.bid[Compute.S] == -1){
                        score.is_nil[Compute.S] = true;
                        score.theirBid += 1;
                    }
                    
                    // Danielle bids
                    if(score.bid[Compute.W] == -1){
                        JOptionPane.showMessageDialog(null, "Danielle bids nil");
                        score.ourBid += 1;// To get back to 0 when added up later.
                        score.is_nil[Compute.W] = true;
                    }
                    else if(score.bid[Compute.W] == -3){
                        JOptionPane.showMessageDialog(null, "Danielle bids Blind Nil as she should!");
                        score.ourBid += 3;// To get back to 0 when added up later.
                        score._is_blindnil = Compute.W;
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Danielle bids "+score.bid[Compute.W]);
                    
                    s = JOptionPane.showInputDialog(null, "What does "+danielle.opponentN+" bid?", "Input "+danielle.opponentN+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    score.bid[Compute.N] = Integer.parseInt(s);
                    if(score.bid[Compute.N] == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(score.bid[Compute.N] == -1){
                        score.is_nil[Compute.N] = true;
                        score.theirBid += 1;
                    }

                    s = JOptionPane.showInputDialog(null, "What does "+danielle.partner+" bid?", "Input "+danielle.partner+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    score.bid[Compute.E] = Integer.parseInt(s);
                    if(score.bid[Compute.E] == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(score.bid[Compute.E] == -1){
                        score.is_nil[Compute.E] = true;
                        score.ourBid += 1;
                    }
                    
                    break;
                    
                default:
                    System.out.println("Order of bidding is wrong somewhow.");
                    score.bid[Compute.S] = 0;score.bid[Compute.E] = 0; score.bid[Compute.N] = 0;// Make the compiler happy
                    break;
            }// End bids!
            
            // Trade cards if blind nil
            if(score._is_blindnil == Compute.W || score._is_blindnil == Compute.E){
///////////                ///BlindNilFrame bnf = new BlindNilFrame();
            }
            
            // Refresh status lines
            danielle.scoresLabel.setText(score.scoreLine(danielle.partner, danielle.opponentN, danielle.opponentS));
            danielle.bidsLabel.setText(score.bidLine(danielle.partner, danielle.opponentN, danielle.opponentS));
            danielle.tricksLabel.setText(score.trickLine(danielle.partner, danielle.opponentN, danielle.opponentS));
            
            // Combine bidding to simplify things later.
            score.ourBid += score.bid[calc.W] + score.bid[calc.E];
            score.theirBid += score.bid[calc.S] + score.bid[calc.N];
            
            // For loop represents each trick
            for (int i = 0; i < 13; i++) {
                // Set all the cards played in the currentTrick to "not played" value of -1
                for (int j = 0; j < danielle.currentTrick.length; j++) {
                    danielle.currentTrick[j] = -1;
                }
                // Get cards played, switching on leader so order is correct
                switch(danielle.lc){
                    case Compute.W:
                        System.out.println("1");
                        danielle.currentTrick[Compute.W] = calc.daniellePlays(danielle.lc, score.is_nil, score.is_setnil, danielle.currentTrick, score.ourTricks, score.theirTricks, score.ourBid, score.theirBid, danielle.deck);
                        // Always refresh the hand view before showing which card played, for aesthetics 
                        dcf.dispose();
                        dcf = new DisplayCardsFrame(danielle.deck);
                        JOptionPane.showMessageDialog(null, cardPictureIcon[danielle.currentTrick[Compute.W]],"Danielle plays card #"+danielle.currentTrick[Compute.W], JOptionPane.PLAIN_MESSAGE);
                        danielle.hc = danielle.currentTrick[Compute.W];
                        
                        danielle.currentTrick[Compute.N] = calc.humanPlays('N', danielle.deck, danielle.partner, danielle.opponentN, danielle.opponentS);
                        danielle.currentTrick[Compute.E] = calc.humanPlays('E', danielle.deck, danielle.partner, danielle.opponentN, danielle.opponentS);
                        danielle.currentTrick[Compute.S] = calc.humanPlays('S', danielle.deck, danielle.partner, danielle.opponentN, danielle.opponentS);
                        break;
                        
                    case Compute.N:
                        System.out.println("2");
                        danielle.currentTrick[Compute.N] = calc.humanPlays('N', danielle.deck, danielle.partner, danielle.opponentN, danielle.opponentS);
                        danielle.hc = danielle.currentTrick[Compute.N];
                        
                        danielle.currentTrick[Compute.E] = calc.humanPlays('E', danielle.deck, danielle.partner, danielle.opponentN, danielle.opponentS);
                        danielle.currentTrick[Compute.S] = calc.humanPlays('S', danielle.deck, danielle.partner, danielle.opponentN, danielle.opponentS);
                        
                        danielle.currentTrick[Compute.W] = calc.daniellePlays(danielle.lc, score.is_nil, score.is_setnil, danielle.currentTrick, score.ourTricks, score.theirTricks, score.ourBid, score.theirBid, danielle.deck);
                        dcf.dispose();
                        dcf = new DisplayCardsFrame(danielle.deck);
                        JOptionPane.showMessageDialog(null, cardPictureIcon[danielle.currentTrick[Compute.W]],"Danielle plays card #"+danielle.currentTrick[Compute.W], JOptionPane.PLAIN_MESSAGE);
                        break;
                        
                    case Compute.E:
                        System.out.println("3");
                        danielle.currentTrick[Compute.E] = calc.humanPlays('E', danielle.deck, danielle.partner, danielle.opponentN, danielle.opponentS);
                        danielle.hc = danielle.currentTrick[Compute.E];
                        
                        danielle.currentTrick[Compute.S] = calc.humanPlays('S', danielle.deck, danielle.partner, danielle.opponentN, danielle.opponentS);
                        
                        danielle.currentTrick[Compute.W] = calc.daniellePlays(danielle.lc, score.is_nil, score.is_setnil, danielle.currentTrick, score.ourTricks, score.theirTricks, score.ourBid, score.theirBid, danielle.deck);
                        dcf.dispose();
                        dcf = new DisplayCardsFrame(danielle.deck);
                        JOptionPane.showMessageDialog(null, cardPictureIcon[danielle.currentTrick[Compute.W]],"Danielle plays card #"+danielle.currentTrick[Compute.W], JOptionPane.PLAIN_MESSAGE);
                        
                        danielle.currentTrick[Compute.N] = calc.humanPlays('N', danielle.deck, danielle.partner, danielle.opponentN, danielle.opponentS);
                        break;
                        
                    case Compute.S:
                        System.out.println("4");
                        danielle.currentTrick[Compute.S] = calc.humanPlays('S', danielle.deck, danielle.partner, danielle.opponentN, danielle.opponentS);
                        danielle.hc = danielle.currentTrick[Compute.S];
                        
                        danielle.currentTrick[Compute.W] = calc.daniellePlays(danielle.lc, score.is_nil, score.is_setnil, danielle.currentTrick, score.ourTricks, score.theirTricks, score.ourBid, score.theirBid, danielle.deck);
                        dcf.dispose();
                        dcf = new DisplayCardsFrame(danielle.deck);
                        JOptionPane.showMessageDialog(null, cardPictureIcon[danielle.currentTrick[Compute.W]],"Danielle plays card #"+danielle.currentTrick[Compute.W], JOptionPane.PLAIN_MESSAGE);
                        
                        danielle.currentTrick[Compute.N] = calc.humanPlays('N', danielle.deck, danielle.partner, danielle.opponentN, danielle.opponentS);
                        danielle.currentTrick[Compute.E] = calc.humanPlays('E', danielle.deck, danielle.partner, danielle.opponentN, danielle.opponentS);
                        break;
                        
                    default:
                        System.out.println("Order of playing logic isnt working");
                        break;
                }
                
                //Danielle assesses situation, changes how playing?
                
                score.determineTrickResult(i, danielle.lc, danielle.hc, danielle.currentTrick, danielle.partner, danielle.opponentN, danielle.opponentS);
                
                // For troubleshooting...
//                for (int j = 0; j < danielle.dd.length; j++) {
//                    System.out.println("danielle.dd["+j+"] = "+danielle.dd[j]);
//                    
//                }
                
                // Refresh status lines
                danielle.scoresLabel.setText(score.scoreLine(danielle.partner, danielle.opponentN, danielle.opponentS));
                danielle.bidsLabel.setText(score.bidLine(danielle.partner, danielle.opponentN, danielle.opponentS));
                danielle.tricksLabel.setText(score.trickLine(danielle.partner, danielle.opponentN, danielle.opponentS));
            }
            
            // Output scores
            score.updateScore();
            JOptionPane.showMessageDialog(null, "Scores:\nDanielle and "+danielle.partner+": "+(score.we + score.ourBags)+"\n"+danielle.opponentN+" and "+danielle.opponentS+": "+(score.they + score.theirBags));
            
            // Determine if the game is over
            if(score.we + score.ourBags > score.they + score.theirBags){
                highestScore = score.we + score.ourBags;
                winningTeam = "Danielle and "+danielle.partner;
            }
            else{
                highestScore = score.they + score.theirBags;
                winningTeam = danielle.opponentN+" and "+danielle.opponentS;
            }
            
            // Have the next person deal
            danielle.bc = (danielle.bc + 1) % 4;
            // Refresh status lines
            danielle.scoresLabel.setText(score.scoreLine(danielle.partner, danielle.opponentN, danielle.opponentS));
            danielle.bidsLabel.setText(score.bidLine(danielle.partner, danielle.opponentN, danielle.opponentS));
            danielle.tricksLabel.setText(score.trickLine(danielle.partner, danielle.opponentN, danielle.opponentS));
        }
        
        //Output the winning team
        JOptionPane.showMessageDialog(null, "Winning team: "+ winningTeam);
        System.exit(0);
        }catch(NumberFormatException e){
            danielle.background.add(new JLabel("Exception Thrown. Since something went wrong, probably user error, exit and start over."), BorderLayout.CENTER);
            // Re-size so it displays the new label.
            danielle.background.setSize(661, 600); 
        }
    }
    



    
    // Class to view cards
    //public static class DisplayCardsFrame extends JFrame
        
    
    //public static class DisplayCardsPanel extends JPanel
    
    /** Class to create a frame to input the cards with GUI instead of using a Scanner.
     * Used for entering 13 card hand, as well as each played card. */
    //public static class HandInputFrame extends JFrame
    

    /**
     * Gets bids from other 3 players.
     * @param name Name of the player bidding.
     * @param pos int; Name is sitting at W,E,N, or S
     * @param bids int array that contains the 4 players' bids
     * @param f The DisplayCardsFrame to close. Doesn't Really need to be passed...
     * @param is_nil boolean array of whether the players are nil or not
     * @param appropriateTeamBid Generally, score.ourBid or score.theirBid
     * @param whoisbn int representing who is blind nil (score._is_blindnil generally)
     * @return 
     */
    public boolean enterOthersBid(String name, int pos, Score score, DisplayCardsFrame f){
        String s;
        s = JOptionPane.showInputDialog(null, "What does "+name+" bid?", "Input "+name+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    score.bid[pos] = Integer.parseInt(s);
                    if(score.bid[pos] == -2){
                        f.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        return false; //redeal
                    }
                    else if(score.bid[pos] == -1){
                        score.is_nil[pos] = true;
                        //appropriateTeamBid += 1;
                    }
                    else if(score.bid[pos] == -3){
                        JOptionPane.showMessageDialog(null, name+" bids Blind Nil!");
                        //appropriateTeamBid += 323;// To get back to 0 when added up later.
                        score._is_blindnil = pos;
                    }
        return true;
    }


    

}
