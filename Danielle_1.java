/*
 * Project Danielle (v.1)
 * Daniel Tixier
 * Started 3/8/2013.
 * 
 * Major shell work completed 3/19.
 * Major GUI work completed 3/29.
 * Major initial bid/play logic completed 3/31.
 * Major debugging started 3/31, break took from 4/2 - 4/16.
 * Worked on displaying card pics, completed 4/21.
 * Running prototype 4/21
 * Master background with exit button and scores 4/22
 * Major renovations (bidding nil, redealing, catching wrong input exception, slimming main method)completed 4/23
 * 
 * 
 * Danielle is a heuristic-based, pseudo AI (with GUI implementation) that plays a hand of the 4-player
 * card game Spades. It considers itself as sitting in the West position.
 */
package danielle_1;

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

public class Danielle_1 {
    
        static final int TWO_SPADES = 0;
        static final int THREE_SPADES = 1;
        static final int FOUR_SPADES = 2;
        static final int FIVE_SPADES = 3;
        static final int SIX_SPADES = 4;
        static final int SEVEN_SPADES = 5;
        static final int EIGHT_SPADES = 6;
        static final int NINE_SPADES = 7;
        static final int TEN_SPADES = 8;
        static final int JACK_SPADES = 9;
        static final int QUEEN_SPADES = 10;
        static final int KING_SPADES = 11;
        static final int ACE_SPADES = 12;
        
        static final int TWO_HEARTS = 13;
        static final int THREE_HEARTS = 14;
        static final int FOUR_HEARTS = 15;
        static final int FIVE_HEARTS = 16;
        static final int SIX_HEARTS = 17;
        static final int SEVEN_HEARTS = 18;
        static final int EIGHT_HEARTS = 19;
        static final int NINE_HEARTS = 20;
        static final int TEN_HEARTS = 21;
        static final int JACK_HEARTS = 22;
        static final int QUEEN_HEARTS = 23;
        static final int KING_HEARTS = 24;
        static final int ACE_HEARTS = 25;
        
        static final int TWO_CLUBS = 26;
        static final int THREE_CLUBS = 27;
        static final int FOUR_CLUBS = 28;
        static final int FIVE_CLUBS = 29;
        static final int SIX_CLUBS = 30;
        static final int SEVEN_CLUBS = 31;
        static final int EIGHT_CLUBS = 32;
        static final int NINE_CLUBS = 33;
        static final int TEN_CLUBS = 34;
        static final int JACK_CLUBS = 35;
        static final int QUEEN_CLUBS = 36;
        static final int KING_CLUBS = 37;
        static final int ACE_CLUBS = 38;
        
        static final int TWO_DIAMONDS = 39;
        static final int THREE_DIAMONDS = 40;
        static final int FOUR_DIAMONDS = 41;
        static final int FIVE_DIAMONDS = 42;
        static final int SIX_DIAMONDS = 43;
        static final int SEVEN_DIAMONDS = 44;
        static final int EIGHT_DIAMONDS = 45;
        static final int NINE_DIAMONDS = 46;
        static final int TEN_DIAMONDS = 47;
        static final int JACK_DIAMONDS = 48;
        static final int QUEEN_DIAMONDS = 49;
        static final int KING_DIAMONDS = 50;
        static final int ACE_DIAMONDS = 51;
        
        static char[] dd = new char[52];
        static ImageIcon[] cardPictureIcon = new ImageIcon[52];
        static ArrayList<JLabel> cardPictureLabel = new ArrayList<>();
        
        static JFrame background = new JFrame();
        static JPanel statusPanel = new JPanel();
        static JLabel scoresLabel = new JLabel();
        static JLabel bidsLabel = new JLabel();
        static JLabel tricksLabel = new JLabel();
        
        static final int W = 0, N = 1, E = 2, S = 3;
        
        static boolean isWnil, isNnil, isEnil, isSnil, isWsetnil, isNsetnil, isEsetnil, isSsetnil;
        static int bidW, bidN, bidE, bidS, ourBid, theirBid, ourTricks, theirTricks;
        // Leader Constant, Bidding Constant, and High Card
        static int lc, bc, hc;
        
        static int[] currentTrick = new int[4];// represents a trick
        
        // Names of players
        static String partner = "", opponentN = "", opponentS = "";
        
        static int we = 0, they = 0, ourBags = 0, theirBags = 0;
        static int winningPointValue;
        
    //@SuppressWarnings("empty-statement")
    public static void main(String[] args) {
        // Add a try-catch structure to let the user know that something went wrong when running external to netbeans.
        try{
        // Initialize the 52 card pictures
        for (int i = 0; i < 52; i++) {
            cardPictureIcon[i] = new ImageIcon(Danielle_1.class.getResource("cardPictures/"+(i)+".png"));
        }
            
        // Initialize the 52 card labels
        for (int i = 0; i < 52; i++) {
            cardPictureLabel.add(new JLabel(cardPictureIcon[i]));
        }
        
        // Other variables
        String winningTeam = "Danielle and "+partner;
        
        
        // Set properties of the main frame for the program
        background.setTitle("Danielle_1.1");
        background.setSize(660, 600);
        background.setLocationRelativeTo(null);
        background.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        background.setVisible(true);
        background.setLayout(new BorderLayout());
        // Make a button to close the program.
        JButton closeButton = new JButton("Close Danielle_1.1");
        closeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                int option = JOptionPane.showConfirmDialog(background, "Exit the program?");
                if(option == JOptionPane.YES_OPTION)
                    System.exit(0);
            }
        });
        background.add(closeButton,BorderLayout.SOUTH);
        
        statusPanel.setLayout(new BorderLayout());
        statusPanel.add(scoresLabel, BorderLayout.NORTH);
        statusPanel.add(bidsLabel, BorderLayout.CENTER);
        statusPanel.add(tricksLabel, BorderLayout.SOUTH);
        
        background.add(statusPanel, BorderLayout.NORTH);
        
        
        // Get names
        partner = JOptionPane.showInputDialog(null, "Enter name of partner.");
        opponentN = JOptionPane.showInputDialog(null, "Enter name of opponent North.");
        opponentS = JOptionPane.showInputDialog(null, "Enter name of opponent South.");
        
        
        // Who bids first switches every deal, vs. who leads switches every trick.
        String bcAsString = JOptionPane.showInputDialog(null, "Who bids first?\nEnter name as entered previously.");
        bc = W;// Default Danielle bids
        if(bcAsString.equalsIgnoreCase("Danielle"))
            bc = W;
        else if(bcAsString.equalsIgnoreCase(opponentN))
            bc = N;
        else if(bcAsString.equalsIgnoreCase(partner))
            bc = E;
        else if(bcAsString.equalsIgnoreCase(opponentS))
            bc = S;
        else{
            JOptionPane.showMessageDialog(null, "You entered \"Who bids first?\" wrong. Now I have to start over.", "Fail Box", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        
        // What are we playing to?
        String wpvAsString = JOptionPane.showInputDialog(null, "What are we playing to? (Ex: 500)");
        winningPointValue = Integer.parseInt(wpvAsString);
        
        int highestScore = 0;
        while(highestScore < winningPointValue){
            // Reset everything to 0 for the new hand.
            
            // Reset tricks taken to 0.
            ourTricks = 0; theirTricks = 0;
            
            // Set leader constant equal to whoever bid first, since we are starting a new hand
            lc = bc;
            
            hc = 0;// The ever-changing high card
//            int[] currentTrick = new int[4];// represents a trick
            
            isWnil = false; isNnil = false; isEnil = false; isSnil = false; isWsetnil = false; isNsetnil = false; isEsetnil = false; isSsetnil = false;
            ourBid = 0;
            theirBid = 0;
            
            // Loop in case Danielle calls a redeal
            while(true){
                // Reset all the cards to no information
                for (int i = 0; i < 52; i++) {
                    dd[i] = '\u0000';
                }    

                HandInputFrame hif = new HandInputFrame('W');
                hif.setTitle("Input Danielle's Hand");
                hif.setLocationRelativeTo(null);
                hif.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                hif.setVisible(true);

                // Give time to enter values into frame
                while(hif.isVisible()){
                    System.out.print("");
                }

                bidW = bidding();

                // Call a redeal now if you are going to call it.
                if(bidW != -2)
                    break;
                else
                    JOptionPane.showMessageDialog(null, "Danielle calls redeal. New hand!! Yes!!");
            }

            DisplayCardsFrame dcf = new DisplayCardsFrame();
            
            
            // Bidding can't be exported to a method easily because of the continue aspect of a redeal.
            String s; // To use in getting values from inputdialogs
            // Other bids, switching on order of dealer, so input in the right order.
            switch(bc){
                case W:
                    // Danielle bids
                    if(bidW == -1){
                        JOptionPane.showMessageDialog(null, "Danielle bids nil");
                        ourBid += 1;// To get back to 0 when added up later.
                        isWnil = true;
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Danielle bids "+bidW);
                    
                    s = JOptionPane.showInputDialog(null, "What does "+opponentN+" bid?", "Input "+opponentN+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    bidN = Integer.parseInt(s);
                    if(bidN == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(bidN == -1){
                        isNnil = true;
                        theirBid += 1;
                    }

                    s = JOptionPane.showInputDialog(null, "What does "+partner+" bid?", "Input "+partner+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    bidE = Integer.parseInt(s);
                    if(bidE == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(bidE == -1){
                        isEnil = true;
                        ourBid +=1;
                    }

                    s = JOptionPane.showInputDialog(null, "What does "+opponentS+" bid?", "Input "+opponentS+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    bidS = Integer.parseInt(s);
                    if(bidS == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(bidS == -1){
                        isSnil = true;
                        theirBid +=1;
                    }
                    
                    break;
                    
                case N:
                    s = JOptionPane.showInputDialog(null, "What does "+opponentN+" bid?", "Input "+opponentN+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    bidN = Integer.parseInt(s);
                    if(bidN == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(bidN == -1){
                        isNnil = true;
                        theirBid += 1;
                    }

                    s = JOptionPane.showInputDialog(null, "What does "+partner+" bid?", "Input "+partner+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    bidE = Integer.parseInt(s);
                    if(bidE == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(bidE == -1){
                        isEnil = true;
                        ourBid +=1;
                    }

                    s = JOptionPane.showInputDialog(null, "What does "+opponentS+" bid?", "Input "+opponentS+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    bidS = Integer.parseInt(s);
                    if(bidS == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(bidS == -1){
                        isSnil = true;
                        theirBid +=1;
                    }
                    
                    // Danielle bids
                    if(bidW == -1){
                        JOptionPane.showMessageDialog(null, "Danielle bids nil");
                        ourBid += 1;// To get back to 0 when added up later.
                        isWnil = true;
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Danielle bids "+bidW);
                    
                    break;
                    
                case E:
                    s = JOptionPane.showInputDialog(null, "What does "+partner+" bid?", "Input "+partner+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    bidE = Integer.parseInt(s);
                    if(bidE == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(bidE == -1){
                        isEnil = true;
                        ourBid += 1;
                    }

                    s = JOptionPane.showInputDialog(null, "What does "+opponentS+" bid?", "Input "+opponentS+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    bidS = Integer.parseInt(s);
                    if(bidS == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(bidS == -1){
                        isSnil = true;
                        theirBid += 1;
                    }
                    
                    // Danielle bids
                    if(bidW == -1){
                        JOptionPane.showMessageDialog(null, "Danielle bids nil");
                        ourBid += 1;// To get back to 0 when added up later.
                        isWnil = true;
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Danielle bids "+bidW);
                    
                    s = JOptionPane.showInputDialog(null, "What does "+opponentN+" bid?", "Input "+opponentN+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    bidN = Integer.parseInt(s);
                    if(bidN == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(bidN == -1){
                        isNnil = true;
                        theirBid += 1;
                    }
                    
                    break;
                    
                case S:
                    s = JOptionPane.showInputDialog(null, "What does "+opponentS+" bid?", "Input "+opponentS+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    bidS = Integer.parseInt(s);
                    if(bidS == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(bidS == -1){
                        isSnil = true;
                        theirBid += 1;
                    }
                    
                    // Danielle bids
                    if(bidW == -1){
                        JOptionPane.showMessageDialog(null, "Danielle bids nil");
                        ourBid += 1;// To get back to 0 when added up later.
                        isWnil = true;
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Danielle bids "+bidW);
                    
                    s = JOptionPane.showInputDialog(null, "What does "+opponentN+" bid?", "Input "+opponentN+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    bidN = Integer.parseInt(s);
                    if(bidN == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(bidN == -1){
                        isNnil = true;
                        theirBid += 1;
                    }

                    s = JOptionPane.showInputDialog(null, "What does "+partner+" bid?", "Input "+partner+"'s bid.", JOptionPane.QUESTION_MESSAGE);
                    bidE = Integer.parseInt(s);
                    if(bidE == -2){
                        dcf.dispose();
                        JOptionPane.showMessageDialog(null, "Redeal!");
                        continue;//redeal
                    }
                    else if(bidE == -1){
                        isEnil = true;
                        ourBid += 1;
                    }
                    
                    break;
                    
                default:
                    System.out.println("Order of bidding is wrong somewhow.");
                    bidS = 0;bidE = 0; bidN = 0;// Make the compiler happy
                    break;
            }// End bids!
            
            // Refresh status lines
            scoresLabel.setText(scoreLine());
            bidsLabel.setText(bidLine());
            tricksLabel.setText(trickLine());
            
            // Combine bidding to simplify things later.
            ourBid += bidW + bidE;
            theirBid += bidS + bidN;
            
            // For loop represents each trick
            for (int i = 0; i < 13; i++) {
                // Set all the cards played in the currentTrick to "not played" value of -1
                for (int j = 0; j < currentTrick.length; j++) {
                    currentTrick[j] = -1;
                }
                // Get cards played, switching on leader so order is correct
                switch(lc){
                    case W:
                        
                        currentTrick[W] = daniellePlays(/*lc, isWnil, isNnil, isEnil, isSnil, isWsetnil, isNsetnil, isEsetnil, isSsetnil,currentTrick, ourTricks, theirTricks, ourBid, theirBid*/);
                        // Always refresh the hand view before showing which card played, for aesthetics 
                        dcf.dispose();
                        dcf = new DisplayCardsFrame();
                        JOptionPane.showMessageDialog(null, cardPictureIcon[currentTrick[W]],"Danielle plays card #"+currentTrick[W], JOptionPane.PLAIN_MESSAGE);
                        hc = currentTrick[W];
                        
                        currentTrick[N] = humanPlays('N');
                        currentTrick[E] = humanPlays('E');
                        currentTrick[S] = humanPlays('S');
                        break;
                        
                    case N:
                        currentTrick[N] = humanPlays('N');
                        hc = currentTrick[N];
                        
                        currentTrick[E] = humanPlays('E');
                        currentTrick[S] = humanPlays('S');
                        
                        currentTrick[W] = daniellePlays(/*lc, isWnil, isNnil, isEnil, isSnil, isWsetnil, isNsetnil, isEsetnil, isSsetnil, currentTrick, ourTricks, theirTricks, ourBid, theirBid*/);
                        dcf.dispose();
                        dcf = new DisplayCardsFrame();
                        JOptionPane.showMessageDialog(null, cardPictureIcon[currentTrick[W]],"Danielle plays card #"+currentTrick[W], JOptionPane.PLAIN_MESSAGE);
                        break;
                        
                    case E:
                        currentTrick[E] = humanPlays('E');
                        hc = currentTrick[E];
                        
                        currentTrick[S] = humanPlays('S');
                        
                        currentTrick[W] = daniellePlays(/*lc, isWnil, isNnil, isEnil, isSnil, isWsetnil, isNsetnil, isEsetnil, isSsetnil, currentTrick, ourTricks, theirTricks, ourBid, theirBid*/);
                        dcf.dispose();
                        dcf = new DisplayCardsFrame();
                        JOptionPane.showMessageDialog(null, cardPictureIcon[currentTrick[W]],"Danielle plays card #"+currentTrick[W], JOptionPane.PLAIN_MESSAGE);
                        
                        currentTrick[N] = humanPlays('N');
                        break;
                        
                    case S:
                        currentTrick[S] = humanPlays('S');
                        hc = currentTrick[S];
                        
                        currentTrick[W] = daniellePlays(/*lc, isWnil, isNnil, isEnil, isSnil, isWsetnil, isNsetnil, isEsetnil, isSsetnil, currentTrick, ourTricks, theirTricks, ourBid, theirBid*/);
                        dcf.dispose();
                        dcf = new DisplayCardsFrame();
                        JOptionPane.showMessageDialog(null, cardPictureIcon[currentTrick[W]],"Danielle plays card #"+currentTrick[W], JOptionPane.PLAIN_MESSAGE);
                        
                        currentTrick[N] = humanPlays('N');
                        currentTrick[E] = humanPlays('E');
                        break;
                        
                    default:
                        System.out.println("Order of playing logic isnt working");
                        break;
                }
                
                //Danielle assesses situation, changes how playing?
                
                determineTrickResult(i);
                
                // For troubleshooting...
                for (int j = 0; j < dd.length; j++) {
                    System.out.println("dd["+j+"] = "+dd[j]);
                    
                }
                
                // Refresh status lines
                scoresLabel.setText(scoreLine());
                bidsLabel.setText(bidLine());
                tricksLabel.setText(trickLine());
            }
            
            // Output scores
            updateScore();
            JOptionPane.showMessageDialog(null, "Scores:\nDanielle and "+partner+": "+(we + ourBags)+"\n"+opponentN+" and "+opponentS+": "+(they + theirBags));
            
            // Determine if the game is over
            if(we + ourBags > they + theirBags){
                highestScore = we + ourBags;
                winningTeam = "Danielle and "+partner;
            }
            else{
                highestScore = they + theirBags;
                winningTeam = opponentN+" and "+opponentS;
            }
            
            // Have the next person deal
            bc = (bc + 1) % 4;
            // Refresh status lines
            scoresLabel.setText(scoreLine());
            bidsLabel.setText(bidLine());
            tricksLabel.setText(trickLine());
        }
        
        //Output the winning team
        JOptionPane.showMessageDialog(null, "Winning team: "+ winningTeam);
        System.exit(0);
        }catch(NumberFormatException e){
            background.add(new JLabel("Exception Thrown. Since something went wrong, probably user error, exit and start over."), BorderLayout.CENTER);
            // Re-size so it displays the new label.
            background.setSize(661, 600); 
        }
    }

    /** Update all scoring variables.
     * Scoring can't handle: *2 nils on same team,
     * two-for more than 10,
     * nil and 24T on same team*/
    private static void updateScore() {
        
            //Scoring E & W
            
            // Neither is nil, no 24T
            if(bidW != -1 && bidE != -1 && bidW + bidE < 10){
                if(ourTricks == bidW + bidE)
                    we += (bidW + bidE) * 10;
                else if(ourTricks < bidW + bidE)
                    we -= (bidW + bidE) * 10;
                else{// ourTricks > bidW + bidE
                    we += (bidW + bidE) * 10;
                    ourBags += ourTricks - (bidW + bidE);
                    if(ourBags >= 10){
                        we -= 100;
                        ourBags -= 10;
                    }
                }
            }
            // They go 24T
            else if(bidW + bidE >= 10){
                if(ourTricks >= bidW + bidE){
                    we += 200;
                    ourBags += ourTricks - (bidW + bidE);
                }
                else // don't make it
                    we -= 200;
            }
            // W is nil
            else if(bidW == -1){
                if(isWsetnil)
                    we -= 100;
                else // W makes nil
                    we += 100;
                
                if(ourTricks == bidE)
                    we += bidE * 10;
                else if(ourTricks < bidE)
                    we -= bidE * 10;
                else{// ourTricks > bidE
                    we += bidE * 10;
                    ourBags += ourTricks - bidE;
                    if(ourBags >= 10){
                        we -= 100;
                        ourBags -= 10;
                    }
                }
            }
            // E is nil
            else if(bidE == -1){
                if(isEsetnil)
                    we -= 100;
                else // E makes nil
                    we += 100;
                
                if(ourTricks == bidW)
                    we += bidW * 10;
                else if(ourTricks < bidW)
                    we -= bidW * 10;
                else{// ourTricks > bidW
                    we += bidW * 10;
                    ourBags += ourTricks - bidW;
                    if(ourBags >= 10){
                        we -= 100;
                        ourBags -= 10;
                    }
                }
            }
            // error message if none of above fit the situation
            else
                System.out.println("Scoring with E & W isn't working.");
            
            
            //Scoring N & S
            
            // Neither is nil, no 24T
            if(bidN != -1 && bidS != -1 && bidN + bidS < 10){
                if(theirTricks == bidN + bidS)
                    they += (bidN + bidS) * 10;
                else if(theirTricks < bidN + bidS)
                    they -= (bidN + bidS) * 10;
                else{// theirTricks > bidN + bidS
                    they += (bidN + bidS) * 10;
                    theirBags += theirTricks - (bidN + bidS);
                    if(theirBags >= 10){
                        they -= 100;
                        theirBags -= 10;
                    }
                }
            }
            // They go 24T
            else if(bidN + bidS >= 10){
                if(theirTricks >= bidN + bidS){
                    they += 200;
                    theirBags += theirTricks - (bidN + bidS);
                }
                else // don't make it
                    they -= 200;
            }
            // N is nil
            else if(bidN == -1){
                if(isNsetnil)
                    they -= 100;
                else // N makes nil
                    they += 100;
                
                if(theirTricks == bidS)
                    they += bidS * 10;
                else if(theirTricks < bidS)
                    they -= bidS * 10;
                else{// theirTricks > bidS
                    they += bidS * 10;
                    theirBags += theirTricks - bidS;
                    if(theirBags >= 10){
                        they -= 100;
                        theirBags -= 10;
                    }
                }
            }
            // S is nil
            else if(bidS == -1){
                if(isSsetnil)
                    they -= 100;
                else // S makes nil
                    they += 100;
                
                if(theirTricks == bidN)
                    they += bidN * 10;
                else if(theirTricks < bidN)
                    they -= bidN * 10;
                else{// theirTricks > bidN
                    they += bidN * 10;
                    theirBags += theirTricks - bidN;
                    if(theirBags >= 10){
                        they -= 100;
                        theirBags -= 10;
                    }
                }
            }
            // error message if none of above fit the situation
            else
                System.out.println("Scoring with N & S isn't working.");
    } 

    /** Determines who won and dishes out the consequences.
     * int passed is the current trick, just to display it.*/
    private static void determineTrickResult(int i) {
        // Determine who won
                for (int j = 0; j < 4; j++) {
                    if(hc >= 13 && hc < 26 /* a heart is led */ && ((currentTrick[j] > hc && currentTrick[j] < 26) || currentTrick[j] < 13))/* a bigger heart or a spade */
                        hc = currentTrick[j];
                    else if(hc >= 26 && hc < 39 /* a club is led */ && ((currentTrick[j] > hc && currentTrick[j] < 39) || currentTrick[j] < 13))/* a bigger club or a spade */
                        hc = currentTrick[j];
                    else if(hc >= 39 /* a diamond is led */ && (currentTrick[j] > hc || currentTrick[j] < 13))/* a bigger diamond or a spade */
                        hc = currentTrick[j];
                    else if(currentTrick[j] > hc && currentTrick[j] < 13)// a bigger spade
                        hc = currentTrick[j];
                }
                
                // Define consequenses for winning: leading next trick, increment tricks won, determine if set nil or not, output a dialog box
                if(hc == currentTrick[W]){
                    lc = W;
                    ourTricks++;
                    if(isWnil){
                        isWnil = false;
                        isWsetnil = true;
                    }
                    JOptionPane.showMessageDialog(null, "Danielle won trick "+(i+1)+"!\nTake it please..." , "Trick over", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(hc == currentTrick[N]){
                    lc = N;
                    theirTricks++;
                    if(isNnil){
                        isNnil = false;
                        isNsetnil = true;
                    }
                    JOptionPane.showMessageDialog(null, opponentN+" won trick "+(i+1)+"!\nTake it please..." , "Trick over", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(hc == currentTrick[E]){
                    lc = E;
                    ourTricks++;
                    if(isEnil){
                        isEnil = false;
                        isEsetnil = true;
                    }
                    JOptionPane.showMessageDialog(null, partner+" won trick "+(i+1)+"!\nTake it please..." , "Trick over", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(hc == currentTrick[S]){
                    lc = S;
                    theirTricks++;
                    if(isSnil){
                        isSnil = false;
                        isSsetnil = true;
                    }
                    JOptionPane.showMessageDialog(null, opponentS+" won trick "+(i+1)+"!\nTake it please..." , "Trick over", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                    System.out.println("something is wrong in setting the winning trick");
                
    }
    
    // Class to view cards
    public static class DisplayCardsFrame extends JFrame{
        final int widthOfFrame;
        DisplayCardsPanel dcpanel = new DisplayCardsPanel();
        
        public DisplayCardsFrame(){
            widthOfFrame = 350;
            add(dcpanel);
            setTitle("Danielle's Hand");
            setSize(widthOfFrame, cardPictureIcon[1].getIconHeight()+50);
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            setVisible(true);
//            setResizable(false); Not using this command because it messes it up
//            It does not like repainting at all.
        }
        
    }
    
    public static class DisplayCardsPanel extends JPanel{

        final int widthOfFrame;
        int x;
        public DisplayCardsPanel() {
            widthOfFrame = 350;
            setLayout(null);
            setSize(widthOfFrame, cardPictureIcon[1].getIconHeight()+50);
            x = widthOfFrame - cardPictureIcon[1].getIconWidth()*2;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 51; i >= 0; i--) {
                // If the card is held by 'W', add it to the frame
                if(dd[i] == 'W'){
                    cardPictureLabel.get(i).setBounds(x,0,cardPictureLabel.get(i).getPreferredSize().width,cardPictureLabel.get(i).getPreferredSize().height);
                    add(cardPictureLabel.get(i));
                    x-=15;
                }
            }
        }
        
    }
    
    /** Class to create a frame to input the cards with GUI instead of using a Scanner.
     * Used for entering 13 card hand, as well as each played card. */
    public static class HandInputFrame extends JFrame{
        // Make button for each number
        JCheckBox[] cbAll = new JCheckBox[52];
        
        JButton ok = new JButton("OK");
        JPanel colorPanel = new JPanel();
        
        char player;
        
        // Frame Constructor    
        public HandInputFrame(char player){
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
            
            if(ensureOnlyOneBoxChecked){
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
            JPanel pSpades = new JPanel(new GridLayout(13,1));
            JPanel pHearts = new JPanel(new GridLayout(13,1));
            JPanel pClubs = new JPanel(new GridLayout(13,1));
            JPanel pDiamonds = new JPanel(new GridLayout(13,1));
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
        
        private class StatusListener implements ItemListener{
            int numChecked = 0;
            JPanel jpn;
            char player;

            public StatusListener(JPanel jpn, char player) {
                this.jpn = jpn;
                this.player = player;
            }

            @Override
            public void itemStateChanged(ItemEvent ie) {
                if(ie.getStateChange() == ItemEvent.SELECTED)
                    numChecked++;
                else
                    numChecked--;
                
                // Color the panel
                if(player != 'W'){
                    jpn.setBackground(Color.green);
                    // For checkboxes 0-51, if it is selected, and the card slot it represents has something in it, paint the side red because the card can't be played.
                    for (int i = 0; i < 52; i++) {
                        if(ie.getStateChange() == ItemEvent.SELECTED && ie.getItem().equals(cbAll[i]) && dd[i] != '\u0000')
                            jpn.setBackground(Color.red);
                    }
                }
                else{
                    if(numChecked == 0)
                        jpn.setBackground(Color.lightGray);
                    else if(numChecked < 13)
                        jpn.setBackground(Color.yellow);
                    else if(numChecked > 13)
                        jpn.setBackground(Color.red);
                    else
                        jpn.setBackground(Color.green);
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
                    if(cbAll[i].isSelected()){
                        dd[i] = x;
                        canClose = true;
                    }
                }
                
                // Close the window that the listener's button is in
                if(canClose)
                    f.dispose();
                else
                    JOptionPane.showMessageDialog(rootPane, "A card must be played.");
            }
            
        }
    }// End HandInputFrame
    
    /**Bidding characteristics:
    -Only takes its own hand into consideration - simply takes what it thinks it can take (effectively it is West, bidding first, as if score is not a factor)
    -Bid nil as often as possible.
    -If bidding 4 or more, consider (go into more decisions to decide) bidding one extra.?
    
    *Define winners: the tricks it is bidding on. Base these off of covers/length of spades
    *Aces, kings, and all spades have a chance to be winners.*/
    public static int bidding() {
        int bid = 0;
        
        // First determine whether or not to bid nil
        if(canGoNil())
            return -1;// Option #1

        // For a normal hand, bid on the high cards
        
        // Bid on Aces and kings
            
        if(dd[ACE_HEARTS] == 'W'){
            bid++;
            if(dd[KING_HEARTS] == 'W')
                bid++;
        }
        else if(dd[KING_HEARTS] == 'W' && numberOfSuit('W', "Hearts") > 2)
            bid++;
        
        if(dd[ACE_CLUBS] == 'W'){
            bid++;
            if(dd[KING_CLUBS] == 'W')
                bid++;
        }
        else if(dd[KING_CLUBS] == 'W' && numberOfSuit('W', "Clubs") > 2)
            bid++;
        
        if(dd[ACE_DIAMONDS] == 'W'){
            bid++;
            if(dd[KING_DIAMONDS] == 'W')
                bid++;
        }
        else if(dd[KING_DIAMONDS] == 'W' && numberOfSuit('W', "Diamonds") > 2)
            bid++;
            
        
        // Bid on spades...
        if(dd[ACE_SPADES] == 'W' && dd[KING_SPADES] == 'W' &&
                dd[QUEEN_SPADES] == 'W' && dd[JACK_SPADES] == 'W')
            bid += (numberOfSuit('W', "Spades"));
        else if(dd[ACE_SPADES] == 'W' && dd[KING_SPADES] == 'W' &&
                dd[QUEEN_SPADES] == 'W' && numberOfSuit('W', "Spades") > 3)
            bid += (numberOfSuit('W', "Spades") - 1);
        else if(dd[ACE_SPADES] == 'W' && dd[KING_SPADES] == 'W' &&
                dd[QUEEN_SPADES] == 'W')
            bid += 3;
        else if((dd[ACE_SPADES] == 'W' || dd[KING_SPADES] == 'W') &&
                numberOfSuit('W', "Spades") > 2)
                bid += numberOfSuit('W', "Spades") - 2;
        else if(dd[ACE_SPADES] == 'W' || dd[KING_SPADES] == 'W')
            bid++;
        else if(numberOfSuit('W', "Spades") > 3)
            bid += (numberOfSuit('W', "Spades") - 3);
        else if(numberOfSuit('W', "Spades") > 0 && (numberOfSuit('W', "Hearts") < 2 || numberOfSuit('W', "Clubs") < 2 || numberOfSuit('W', "Diamonds") < 2))
            bid += 1;

        // If can't bid nil or bid 4 or more or go nil(decided above), redeal
        if(bid < 4 && (numberOfSuit('W',"Diamonds") >= 7 || numberOfSuit('W', "Hearts") >= 7
                || numberOfSuit('W', "Clubs") >= 7 || numberOfSuit('W', "Spades") == 0)){
            System.out.println("$$$$");return -2; // Option #redeal
            }
        
        return bid;// Option normal
    }
    
    /** Determines if Danielle can go nil or not.*/
    public static boolean canGoNil() {
        //exception, too good of a hand to go nil
        boolean a_k_spades = false;
        boolean nilableSpades = false;
        boolean badEnoughForNil = true;
        boolean nilableHearts = false;
        boolean nilableClubs = false;
        boolean nilableDiamonds = false;
        // Get the cards Danielle has in each suit
        int[] hearts = inSuit("Hearts");
        int[] spades = inSuit("Spades");
        int[] clubs = inSuit("Clubs");
        int[] diamonds = inSuit("Diamonds");
        
        // Determine nilable spades.
        // If has Ace or King, no chance at nil.
        if(dd[ACE_SPADES] == 'W' || dd[KING_SPADES] == 'W')//no A or K of spades
            a_k_spades = true;
        
        if(!a_k_spades && numberOfSuit('W', "Spades") < 5){//&& 4 spades or less,
                if(numberOfSuit('W', "Spades") == 4 && getHigh('W', QUEEN_SPADES) <= NINE_SPADES &&
                        getHigh('W', NINE_SPADES) <= FIVE_SPADES)//including Q-9-5-x or less
                    nilableSpades = true;
                else if(numberOfSuit('W', "Spades") == 3 && getHigh('W', QUEEN_SPADES) 
                        <= NINE_SPADES && getHigh('W', NINE_SPADES) <= FIVE_SPADES)//including Q-9-5 or less, this line of code is redundant
                    nilableSpades = true;
                else if(numberOfSuit('W', "Spades") == 2 && getHigh('W', QUEEN_SPADES) <= NINE_SPADES)
                    nilableSpades = true;//including Q-9 or less
                else // holds 1 or 0 spades
                    nilableSpades = true;
        }
        
        // Nilable suit criteria, must be equal to or lower than these values
        //>5  x-x-9-6-5-3
        //5   x-x-8-5-3
        //4   x-J-7-4
        //3   Q-7-4
        //2   10-5
        //1   9
        
        // Check whichever length of HEARTS Danielle has for the nilable suit criteria.
        if(hearts.length > 5 && hearts[0] <= THREE_HEARTS && hearts[1] <= FIVE_HEARTS && hearts[2] <= SIX_HEARTS && hearts[3] <= NINE_HEARTS)
            nilableHearts = true;
        else if (hearts.length == 5 && hearts[0] <= THREE_HEARTS && hearts[1] <= FIVE_HEARTS && hearts[2] <= EIGHT_HEARTS)
            nilableHearts = true;
        else if (hearts.length == 4 && hearts[0] <= FOUR_HEARTS && hearts[1] <= SEVEN_HEARTS && hearts[2] <= JACK_HEARTS)
            nilableHearts = true;
        else if (hearts.length == 3 && hearts[0] <= FOUR_HEARTS && hearts[1] <= SEVEN_HEARTS && hearts[2] <= QUEEN_HEARTS)
            nilableHearts = true;
        else if (hearts.length == 2 && hearts[0] <= FIVE_HEARTS && hearts[1] <= TEN_HEARTS)
            nilableHearts = true;
        else if (hearts.length == 1 && hearts[0] <= NINE_HEARTS)
            nilableHearts = true;
        else if (hearts.length == 0)
            nilableHearts = true;
        
        // Check whichever length of CLUBS Danielle has for the nilable suit criteria.
        if(clubs.length > 5 && clubs[0] <= THREE_CLUBS && clubs[1] <= FIVE_CLUBS && clubs[2] <= SIX_CLUBS && clubs[3] <= NINE_CLUBS)
            nilableClubs = true;
        else if (clubs.length == 5 && clubs[0] <= THREE_CLUBS && clubs[1] <= FIVE_CLUBS && clubs[2] <= EIGHT_CLUBS)
            nilableClubs = true;
        else if (clubs.length == 4 && clubs[0] <= FOUR_CLUBS && clubs[1] <= SEVEN_CLUBS && clubs[2] <= JACK_CLUBS)
            nilableClubs = true;
        else if (clubs.length == 3 && clubs[0] <= FOUR_CLUBS && clubs[1] <= SEVEN_CLUBS && clubs[2] <= QUEEN_CLUBS)
            nilableClubs = true;
        else if (clubs.length == 2 && clubs[0] <= FIVE_CLUBS && clubs[1] <= TEN_CLUBS)
            nilableClubs = true;
        else if (clubs.length == 1 && clubs[0] <= NINE_CLUBS)
            nilableClubs = true;
        else if (clubs.length == 0)
            nilableClubs = true;
        
        // Check whichever length of DIAMONDS Danielle has for the nilable suit criteria.
        if(diamonds.length > 5 && diamonds[0] <= THREE_DIAMONDS && diamonds[1] <= FIVE_DIAMONDS && diamonds[2] <= SIX_DIAMONDS && diamonds[3] <= NINE_DIAMONDS)
            nilableDiamonds = true;
        else if (diamonds.length == 5 && diamonds[0] <= THREE_DIAMONDS && diamonds[1] <= FIVE_DIAMONDS && diamonds[2] <= EIGHT_DIAMONDS)
            nilableDiamonds = true;
        else if (diamonds.length == 4 && diamonds[0] <= FOUR_DIAMONDS && diamonds[1] <= SEVEN_DIAMONDS && diamonds[2] <= JACK_DIAMONDS)
            nilableDiamonds = true;
        else if (diamonds.length == 3 && diamonds[0] <= FOUR_DIAMONDS && diamonds[1] <= SEVEN_DIAMONDS && diamonds[2] <= QUEEN_DIAMONDS)
            nilableDiamonds = true;
        else if (diamonds.length == 2 && diamonds[0] <= FIVE_DIAMONDS && diamonds[1] <= TEN_DIAMONDS)
            nilableDiamonds = true;
        else if (diamonds.length == 1 && diamonds[0] <= NINE_DIAMONDS)
            nilableDiamonds = true;
        else if (diamonds.length == 0)
            nilableDiamonds = true;
        
        // Don't go nil with more than 2 Aces/Kings
        if(numberOfAcesAndKings() > 2)
            badEnoughForNil = false;
        
        // Return
        if(nilableSpades && nilableHearts && nilableClubs && nilableDiamonds && badEnoughForNil)
            return true;
        else
            return false;
    }
    
    /** Returns highest in a suit. Works the same way as numberOfSuit.*/
    public static int getHigh(char player, String suit){
        int num = -1;
        switch (suit) {
            case "Spades":
                for (int i = 0; i < 13; i++) {
                    if(dd[i] == player)
                        num = i;
                }
                break;
            case "Hearts":
                for (int i = 13; i < 26; i++) {
                    if(dd[i] == player)
                        num = i;
                }
                break;
            case "Clubs":
                for (int i = 26; i < 39; i++) {
                    if(dd[i] == player)
                        num = i;
                }
                break;
            case "Diamonds":
                for (int i = 39; i < 52; i++) {
                    if(dd[i] == player)
                        num = i;
                }
                break;
            default:
                System.out.println("getHigh isnt working");
                break;
        }
        
        return num;
    }
    
    /** Returns highest in a suit below or equal to a specified card.
     If no cards held are below or equal to the specified card, returns -1.*/
    public static int getHigh(char player, int card){
        int num = -1;
        if(card < 13){
            for (int i = 0; i <= card; i++) {
                if(dd[i] == player)
                    num = i;
            }
        }
        else if(card < 26){
            for (int i = 13; i <= card; i++) {
                if(dd[i] == player)
                    num = i;
            }
        }
        else if(card < 39){
            for (int i = 26; i <= card; i++) {
                if(dd[i] == player)
                    num = i;
            }
        }
        else if(card < 52){
            for (int i = 39; i <= card; i++) {
                if(dd[i] == player)
                    num = i;
            }
        }
        else
            System.out.println("getHigh isnt working");
        
        System.out.println("getHigh is returning: "+num);
        return num;
    }
    
    /** Returns lowest in a suit above or equal to a specified card.*/
    public static int getLow(char player, int card){
        int num = -1;
        if(card < 13){
            for (int i = 12; i >= card; i--) {
                if(dd[i] == player)
                    num = i;
            }
        }
        else if(card < 26){
            for (int i = 25; i >= card; i--) {
                if(dd[i] == player)
                    num = i;
            }
        }
        else if(card < 39){
            for (int i = 38; i >= card; i--) {
                if(dd[i] == player)
                    num = i;
            }
        }
        else if(card < 52){
            for (int i = 51; i >= card; i--) {
                if(dd[i] == player)
                    num = i;
            }
        }
        else
            System.out.println("getLow isnt working");
        
        System.out.println("getLow is returning: "+num);
        return num;
    }
    
    /** Returns the number of cards or a given suit in a given hand, that Danielle knows.
    // *For example, if asking for hearts of North, it returns the number of hearts that North has played.
    // Used in bidding. Returns 0 if no cards held.*/
    public static int numberOfSuit(char player, String suit){
        int num = 0;
        switch (suit) {
            case "Spades":
                for (int i = 0; i < 13; i++) {
                    if(dd[i] == player)
                        num++;
                }
                break;
            case "Hearts":
                for (int i = 13; i < 26; i++) {
                    if(dd[i] == player)
                        num++;
                }
                break;
            case "Clubs":
                for (int i = 26; i < 39; i++) {
                    if(dd[i] == player)
                        num++;
                }
                break;
            case "Diamonds":
                for (int i = 39; i < 52; i++) {
                    if(dd[i] == player)
                        num++;
                }
                break;
            default:
                System.out.println("numberOfSuit isnt working");
                break;
        }
        
        return num;
    }

    // North makes a play
    public static int humanPlays(char x){
        String name = "";
        if(x == 'E')
            name = partner;
        else if(x == 'N')
            name = opponentN;
        else if(x == 'S')
            name = opponentS;
        else
            name = "Janky, lol";
        
        char[] tempdd = dd.clone();
        
        HandInputFrame hif = new HandInputFrame(x);
        hif.setTitle(name+": Input "+name+"'s Play. Single Card Only!");
        hif.setLocationRelativeTo(null);
        hif.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        hif.setVisible(true);
        
        // Wait for button to be pressed on the frame
        while(hif.isVisible()){
            System.out.print("");
        }
        
        for (int i = 0; i < tempdd.length; i++)
            if(tempdd[i] != dd[i]){
                System.out.println(name+" plays dd["+i+"], so this slot now equals: "+dd[i]);
                return i;
            }
        
        // If it didn't return anything, something is wrong
        System.out.println("Something is wrong in humanPlays. Had to terminate. Oops.");
        System.exit(x);
        return -10;
    }

    /** method with all the logic for how Danielle plays!*/
    public static int daniellePlays(/*int lc, boolean isWnil, boolean isNnil,
            boolean isEnil, boolean isSnil, boolean isWsetnil,boolean isNsetnil,
            boolean isEsetnil, boolean isSsetnil, int[] currentTrick, 
            int ourTricks, int theirTricks, int ourBid, int theirBid*/) {
        int x;
        if(isWnil && !isWsetnil){
            x = sluff(lc, currentTrick);
            dd[x] = 'P';
            return x;
        }
        else if(isNnil && !isNsetnil || isSnil && !isSsetnil){
            x = sluff(lc, currentTrick);
            dd[x] = 'P';
            return x;
        }
        else if(ourTricks >= ourBid){
            x = sluff(lc, currentTrick);
            dd[x] = 'P';
            return x;
        }
        else{//no need to sluff
            x = win(lc, currentTrick);
            dd[x] = 'P';
            return x;
        }
    }
    
    /** Play to sluff*/
    public static int sluff(int lc, int[] currentTrick){
        int x = -10;
        switch(lc){
            case W:// Danielle plays first
                if(numberOfSuit('W', "Hearts") > 0 || numberOfSuit('W', "Clubs") > 0 || numberOfSuit('W', "Diamonds") > 0)
                    return absoluteLowest();
                else
                    return getLow('W', TWO_SPADES);
                
            case N:// Danielle plays last
                // Find highest card in suit played
                for (int i = 0; i < currentTrick.length; i++) {
                    if(currentTrick[i] > x && currentTrick[i] <= highInSuitConst(currentTrick[lc]))
                        x = currentTrick[i];
                }
                x = getHigh('W', x);
                if(x != -1)// sluff in suit
                    return x;
                else// does not have anything in the suit lower than x
                    if(getHigh('W', highInSuitConst(currentTrick[lc])) == -1)// has no card in suit
                        return absoluteHighest();
                    else// has a card in suit, so has to play it
                        return getLow('W', lowInSuitConst(currentTrick[lc]));
                
            case E:// Danielle plays 3rd
                // Find highest card played so far
                if(currentTrick[S] > currentTrick[E] && currentTrick[S] <= highInSuitConst(currentTrick[E]))
                    x = currentTrick[S];
                else
                    x = currentTrick[E];
                
                x = getHigh('W', x);
                
                if(x != -1)// can play under in suit
                    return x;
                else// has nothing lower than what has been played
                    x = getHigh('W', highInSuitConst(currentTrick[lc]));
                
                if(x != -1)// play the lowest it can anyway
                    return getLow('W', currentTrick[lc]);// 2nd parameter is the only thing that represents suit, and it doesn't matter because W has nothing lower in the hand anyway.
                else// nothing in the suit
                    if(numberOfSuit('W', "Hearts") > 0 || numberOfSuit('W', "Clubs") > 0 || numberOfSuit('W', "Diamonds") > 0)
                        return absoluteHighest();
                    else
                        return getLow('W', TWO_SPADES);// if you have to play a spade, play a low one
                        
            case S:// Danielle plays 2nd
                x = getHigh('W', currentTrick[S]);
                
                if(x != -1)// can play under in suit
                    return x;
                else// has nothing lower than what has been played
                    x = getHigh('W', highInSuitConst(currentTrick[lc]));
                
                if(x != -1)// play the lowest it can anyway
                    return getLow('W', currentTrick[S]);// 2nd parameter is the only thing that represents suit, and it doesn't matter because W has nothing lower in the hand anyway.
                else// nothing in the suit
                    if(numberOfSuit('W', "Hearts") > 0 || numberOfSuit('W', "Clubs") > 0 || numberOfSuit('W', "Diamonds") > 0)
                        return absoluteHighest();
                    else
                        return getLow('W', TWO_SPADES);// if you have to play a spade, play a low one
                
            default:
                System.out.println("daniellePlays method getting to default. Check it out.");
                break;
        }
        return x;// Shouldn't ever get to this point.
    }
    
    /** Play to win */
    public static int win(int lc, int[] currentTrick){
        boolean canWinInSuit = true;
        boolean alreadyRuffedThisTrick = false;
        boolean spadesBroken = false;
        int extra;
        
        if(lc == W){
            for (int i = 0; i < 13; i++) {
                if(dd[i] != '\u0000' && dd[i] != 'W'){
                    spadesBroken = true;
                    break;
                } 
            }
            if(spadesBroken){
                if(numberOfSuit('W', "Spades") > 0)
                    return getHigh('W', highInSuitConst(0));
                else
                    return absoluteHighest();
            }
            else{
                if(numberOfSuit('W', "Hearts") > 0 || numberOfSuit('W', "Clubs") > 0 || numberOfSuit('W', "Diamonds") > 0)
                    return absoluteHighest();
                else// has to break spades
                    return getHigh('W', highInSuitConst(0));
            }
        }// End lc == 'W'
        else{
            int x = getHigh('W', highInSuitConst(currentTrick[lc]));

            if(x != -1){// has a card in the suit led
                for (int i = 0; i < currentTrick.length; i++) {
                    if(x < currentTrick[i])
                        canWinInSuit = false;
                }
                if(canWinInSuit)
                    return x;
                else// have to play something lower, so go all the way low
                    return getLow('W', lowInSuitConst(currentTrick[lc]));
            }
            else{// has no card in the suit led; x == -1
                for (int i = 0; i < currentTrick.length; i++) {
                    if(currentTrick[i] >= 0 && currentTrick[i] < 13){
                        extra = currentTrick[i];
                        if(extra > x)
                            x = extra;// x represents the highest spade played
                        alreadyRuffedThisTrick = true;
                    }
                }
                if(alreadyRuffedThisTrick)// select the lowest spade that will win
                    x = getLow('W', x);
                else// nobody has spaded in
                    x = getLow('W', lowInSuitConst(TWO_SPADES));

                if(x != -1)// W has a spade
                    return x;
                else// has not spades
                    return absoluteLowest();
            }
        }
    }
    
    /** Returns the high card in the suit of the given card.*/
    public static int highInSuitConst(int lc){
        if(lc < 13)
            return 12;
        else if(lc < 26)
            return 25;
        else if(lc < 39)
            return 38;
        else
            return 51;
    }
    
    /** Returns the low card in the suit of the given card.*/
    public static int lowInSuitConst(int lc){
        if(lc < 13)
            return 0;
        else if(lc < 26)
            return 13;
        else if(lc < 39)
            return 26;
        else
            return 39;
    }
    
    /**Return the lowest card by number in W's hand, does not return a spade.*/
    public static int absoluteLowest(){
        int x = 52;
        for (int i = dd.length - 1; i >= dd.length - 13; i--) // check each decreasing number
            for (int j = i; j >= 13; j = j-13) //check each suit
                if(dd[j] == 'W')
                    x = j;
        if(x == 52)
            System.out.println("absoluteLowest did not go lower than 52.");
        
        System.out.println("absoluteLowest returning: "+x);
        return x;
    }
    
    /** return the highest card by number in W's hand, does not return a spade*/
    public static int absoluteHighest(){
        int x = -10;
        for (int i = 13; i < 26; i++) // check each increasing number
            for (int j = i; j <= 51; j = j+13) //check each suit
                if(dd[j] == 'W')
                    x = j;
        if(x == 52)
            System.out.println("absoluteHighest did not go higher than -10.");
        
        System.out.println("absoluteHighest is returning: "+x);
        return x;
    }
    
    /** Returns a string that represents the current scores.*/
    public static String scoreLine(){
        return "Scores -  Danielle & "+partner+": "+(we + ourBags)+"   "+opponentN+" & "+opponentS+": "+(they + theirBags)+"      Game to: "+winningPointValue;
    }
    
    /** Returns a string that represents the current bids.*/
    public static String bidLine(){
        return "Bids -    Danielle: "+bidW+"   "+partner+": "+bidE+"   "+opponentN+": "+bidN+"   "+opponentS+": "+bidS;
    }
    
    /** Returns a string that represents the current tricks taken.*/
    public static String trickLine(){
        return "Taken -   Danielle & "+partner+": "+ourTricks+"   "+opponentN+" & "+opponentS+": "+theirTricks;
    }
    
    /** Returns an int array with all the cards in specified suit held by Danielle.
     * [0] contains the lowest card, [length -1] contains the highest card.*/
    public static int[] inSuit(String suit){
        int[] nums = new int[numberOfSuit('W', suit)];
        int count = 0;
        
        switch (suit) {
            case "Spades":
                for (int i = 0; i < 13; i++) {
                    if(dd[i] == 'W'){
                        nums[count] = i;
                        count++;
                    }
                }
                break;
            case "Hearts":
                for (int i = 13; i < 26; i++) {
                    if(dd[i] == 'W'){
                        nums[count] = i;
                        count++;
                    }
                }
                break;
            case "Clubs":
                for (int i = 26; i < 39; i++) {
                    if(dd[i] == 'W'){
                        nums[count] = i;
                        count++;
                    }
                }
                break;
            case "Diamonds":
                for (int i = 39; i < 52; i++) {
                    if(dd[i] == 'W'){
                        nums[count] = i;
                        count++;
                    }
                }
                break;
            default:
                System.out.println("inSuit is getting to default");
                break;
        }
        
        return nums;
    }
    
    /** Returns how many Aces and Kings Danielle has.*/
    public static int numberOfAcesAndKings(){
        int num = 0;
        if(dd[ACE_CLUBS] == 'W')
            num++;
        if(dd[ACE_DIAMONDS] == 'W')
            num++;
        if(dd[ACE_HEARTS] == 'W')
            num++;
        if(dd[ACE_SPADES] == 'W')
            num++;
        if(dd[KING_CLUBS] == 'W')
            num++;
        if(dd[KING_DIAMONDS] == 'W')
            num++;
        if(dd[KING_HEARTS] == 'W')
            num++;
        if(dd[KING_SPADES] == 'W')
            num++;
        
        return num;
    }
}
