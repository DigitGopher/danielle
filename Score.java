
package danielle;

import javax.swing.JOptionPane;

/**
 *
 * @author D. Tixier Class to represent and handle scoring.
 */
public class Score {

    int[] bid = new int[4];
    int ourBid, theirBid, ourTricks, theirTricks;
    // Names of players
    
    int we = 0, they = 0, ourBags = 0, theirBags = 0;
    int winningPointValue;
    boolean[] is_nil = new boolean[4];
    boolean[] is_setnil = new boolean[4];
    int _is_blindnil = -1;
    boolean is_setblindnil = false;

    /**
     * Returns a string that represents the current scores.
     */
    public String scoreLine(String p, String opN, String opS) {
        return "Scores -  Danielle & " + p + ": " + (we + ourBags) + "   " + opN + " & " + opS + ": " + (they + theirBags) + "      Game to: " + winningPointValue;
    }

    /**
     * Returns a string that represents the current bids.
     */
    public String bidLine(String p, String opN, String opS) {
        return "Bids -    Danielle: " + bid[Compute.W] + "   " + p + ": " + bid[Compute.E] + "   " + opN + ": " + bid[Compute.N] + "   " + opS + ": " + bid[Compute.S];
    }

    /**
     * Returns a string that represents the current tricks taken.
     */
    public String trickLine(String p, String opN, String opS) {
        return "Taken -   Danielle & " + p + ": " + ourTricks + "   " + opN + " & " + opS + ": " + theirTricks;
    }

    /**
     * Determines who won and dishes out the consequences. int passed is the
     * current trick, just to display it.
     */
    public void determineTrickResult(int i, int lc, int hc, int[] currentTrick, String p, String opN, String opS) {
        // Determine who won
        for (int j = 0; j < 4; j++) {
            if (hc >= 13 && hc < 26 /* a heart is led */ && ((currentTrick[j] > hc && currentTrick[j] < 26) || currentTrick[j] < 13))/* a bigger heart or a spade */ {
                hc = currentTrick[j];
            } else if (hc >= 26 && hc < 39 /* a club is led */ && ((currentTrick[j] > hc && currentTrick[j] < 39) || currentTrick[j] < 13))/* a bigger club or a spade */ {
                hc = currentTrick[j];
            } else if (hc >= 39 /* a diamond is led */ && (currentTrick[j] > hc || currentTrick[j] < 13))/* a bigger diamond or a spade */ {
                hc = currentTrick[j];
            } else if (currentTrick[j] > hc && currentTrick[j] < 13)// a bigger spade
            {
                hc = currentTrick[j];
            }
        }

        // Define consequenses for winning: leading next trick, increment tricks won, determine if set nil or not, output a dialog box
        if (hc == currentTrick[Compute.W]) {
            lc = Compute.W;
            ourTricks++;
            if (is_nil[Compute.W]) {
                is_nil[Compute.W] = false;
                is_setnil[Compute.W] = true;
            }
            JOptionPane.showMessageDialog(null, "Danielle won trick " + (i + 1) + "!\nTake it please...", "Trick over", JOptionPane.INFORMATION_MESSAGE);
        } else if (hc == currentTrick[Compute.N]) {
            lc = Compute.N;
            theirTricks++;
            if (is_nil[Compute.N]) {
                is_nil[Compute.N] = false;
                is_setnil[Compute.N] = true;
            }
            JOptionPane.showMessageDialog(null, opN + " won trick " + (i + 1) + "!\nTake it please...", "Trick over", JOptionPane.INFORMATION_MESSAGE);
        } else if (hc == currentTrick[Compute.E]) {
            lc = Compute.E;
            ourTricks++;
            if (is_nil[Compute.E]) {
                is_nil[Compute.E] = false;
                is_setnil[Compute.E] = true;
            }
            JOptionPane.showMessageDialog(null, p + " won trick " + (i + 1) + "!\nTake it please...", "Trick over", JOptionPane.INFORMATION_MESSAGE);
        } else if (hc == currentTrick[Compute.S]) {
            lc = Compute.S;
            theirTricks++;
            if (is_nil[Compute.S]) {
                is_nil[Compute.S] = false;
                is_setnil[Compute.S] = true;
            }
            JOptionPane.showMessageDialog(null, opS + " won trick " + (i + 1) + "!\nTake it please...", "Trick over", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("something is wrong in setting the winning trick");
        }

    }

    /**
     * Update all scoring variables. Scoring can't handle: *2 nils on same team,
     * two-for more than 10, nil and 24T on same team
     */
    public void updateScore() {

        //Scoring E & W

        // Neither is nil, no 24T
        if (bid[Compute.W] != -1 && bid[Compute.E] != -1 && bid[Compute.W] + bid[Compute.E] < 10) {
            if (ourTricks == bid[Compute.W] + bid[Compute.E]) {
                we += (bid[Compute.W] + bid[Compute.E]) * 10;
            } else if (ourTricks < bid[Compute.W] + bid[Compute.E]) {
                we -= (bid[Compute.W] + bid[Compute.E]) * 10;
            } else {// ourTricks > bid[Compute.W] + bid[Compute.E]
                we += (bid[Compute.W] + bid[Compute.E]) * 10;
                ourBags += ourTricks - (bid[Compute.W] + bid[Compute.E]);
                if (ourBags >= 10) {
                    we -= 100;
                    ourBags -= 10;
                }
            }
        } // They go 24T
        else if (bid[Compute.W] + bid[Compute.E] >= 10) {
            if (ourTricks >= bid[Compute.W] + bid[Compute.E]) {
                we += 200;
                ourBags += ourTricks - (bid[Compute.W] + bid[Compute.E]);
            } else // don't make it
            {
                we -= 200;
            }
        } // W is nil
        else if (bid[Compute.W] == -1) {
            if (is_setnil[Compute.W]) {
                we -= 100;
            } else // W makes nil
            {
                we += 100;
            }

            if (ourTricks == bid[Compute.E]) {
                we += bid[Compute.E] * 10;
            } else if (ourTricks < bid[Compute.E]) {
                we -= bid[Compute.E] * 10;
            } else {// ourTricks > bid[Compute.E]
                we += bid[Compute.E] * 10;
                ourBags += ourTricks - bid[Compute.E];
                if (ourBags >= 10) {
                    we -= 100;
                    ourBags -= 10;
                }
            }
        } // E is nil
        else if (bid[Compute.E] == -1) {
            if (is_setnil[Compute.E]) {
                we -= 100;
            } else // E makes nil
            {
                we += 100;
            }

            if (ourTricks == bid[Compute.W]) {
                we += bid[Compute.W] * 10;
            } else if (ourTricks < bid[Compute.W]) {
                we -= bid[Compute.W] * 10;
            } else {// ourTricks > bid[Compute.W]
                we += bid[Compute.W] * 10;
                ourBags += ourTricks - bid[Compute.W];
                if (ourBags >= 10) {
                    we -= 100;
                    ourBags -= 10;
                }
            }
        } // error message if none of above fit the situation
        else {
            System.out.println("Scoring with E & W isn't working.");
        }


        //Scoring N & S

        // Neither is nil, no 24T
        if (bid[Compute.N] != -1 && bid[Compute.S] != -1 && bid[Compute.N] + bid[Compute.S] < 10) {
            if (theirTricks == bid[Compute.N] + bid[Compute.S]) {
                they += (bid[Compute.N] + bid[Compute.S]) * 10;
            } else if (theirTricks < bid[Compute.N] + bid[Compute.S]) {
                they -= (bid[Compute.N] + bid[Compute.S]) * 10;
            } else {// theirTricks > bid[Compute.N] + bid[Compute.S]
                they += (bid[Compute.N] + bid[Compute.S]) * 10;
                theirBags += theirTricks - (bid[Compute.N] + bid[Compute.S]);
                if (theirBags >= 10) {
                    they -= 100;
                    theirBags -= 10;
                }
            }
        } // They go 24T
        else if (bid[Compute.N] + bid[Compute.S] >= 10) {
            if (theirTricks >= bid[Compute.N] + bid[Compute.S]) {
                they += 200;
                theirBags += theirTricks - (bid[Compute.N] + bid[Compute.S]);
            } else // don't make it
            {
                they -= 200;
            }
        } // N is nil
        else if (bid[Compute.N] == -1) {
            if (is_setnil[Compute.N]) {
                they -= 100;
            } else // N makes nil
            {
                they += 100;
            }

            if (theirTricks == bid[Compute.S]) {
                they += bid[Compute.S] * 10;
            } else if (theirTricks < bid[Compute.S]) {
                they -= bid[Compute.S] * 10;
            } else {// theirTricks > bid[Compute.S]
                they += bid[Compute.S] * 10;
                theirBags += theirTricks - bid[Compute.S];
                if (theirBags >= 10) {
                    they -= 100;
                    theirBags -= 10;
                }
            }
        } // S is nil
        else if (bid[Compute.S] == -1) {
            if (is_setnil[Compute.S]) {
                they -= 100;
            } else // S makes nil
            {
                they += 100;
            }

            if (theirTricks == bid[Compute.N]) {
                they += bid[Compute.N] * 10;
            } else if (theirTricks < bid[Compute.N]) {
                they -= bid[Compute.N] * 10;
            } else {// theirTricks > bid[Compute.N]
                they += bid[Compute.N] * 10;
                theirBags += theirTricks - bid[Compute.N];
                if (theirBags >= 10) {
                    they -= 100;
                    theirBags -= 10;
                }
            }
        } // error message if none of above fit the situation
        else {
            System.out.println("Scoring with N & S isn't working.");
        }
    }
}
