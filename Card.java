
package danielle;

/**
 * 
 * @author D. Tixier
 */
public class Card {
    
    /** The probability distribution for where Danielle guesses the card is.
     * slot 0 = W
     *      1 = N
     *      2 = E
     *      3 = S
     */
    private double[] probs = new double[4];
    
    /** Is the card in play or known yet? */
    private boolean known = false;
    
    /** Who has the card? When unknown value is '\\u0000'. */
    private char ownerAsChar = '\u0000';
    
    private boolean daniellePlayed = false;
    
    public void setDaniellePlayed(boolean daniellePlayed) {
        this.daniellePlayed = daniellePlayed;
    }
    
    
    /** Who has the card? When unknown value is '\\u0000'. */
    public char getOwnerAsChar() {
        return ownerAsChar;
    }

    /**
     * 
     * @param x Char representing a player
     * @return true if player has/played card
     */
    public boolean isOwner(char x){
        if(ownerAsChar == x){
            return true;
        }
        return false;
    }
    
    /** Is the card in play or known yet?*/
    public boolean isKnown() {
        return known;
    }

    public Card() {
        for (int i = 0; i < probs.length; i++) {
            // Initialize to all values to one
            probs[i] = 1;
        }
    }
    
    /**
     * 
     * @return Sum of current probabilities of one card
     */
    private double sum(){
        double s = 0;
        for (int i = 0; i < probs.length; i++) {
            s += probs[i];
        }
        return s;
    }
    
    public void adjust(int position, double weight){
        if(position < 0 || position > 3){
            System.out.println("Probability.adjust must take an int between 0 and 3 inclusive.");
            return;
        }
        probs[position] *= weight;
        // Make equal to one so it is still a probability
        double s = sum();
        for (int i = 0; i < probs.length; i++) {
            probs[i] /= s;
        }
    }
    
    public void adjust(char position, double weight){
        int player;
        switch (position){
            case 'W': player = 0; break;
            case 'N': player = 1; break;
            case 'E': player = 2; break;
            case 'S': player = 3; break;
            default: System.out.println("Probability.adjust must take a char in the set {W,N,E,S}");
            return;
        }
        probs[player] *= weight;
        // Make equal to one so it is still a probability
        double s = sum();
        for (int i = 0; i < probs.length; i++) {
            probs[i] /= s;
        }
    }
    
    /**
     * Set card as known.
     * @param player The player known to have the card (or who played it)
     */
    public void setKnown(int player){
        switch(player){
            case 0: ownerAsChar = 'W'; break;
            case 1: ownerAsChar = 'N'; break;
            case 2: ownerAsChar = 'E'; break;
            case 3: ownerAsChar = 'S'; break;
            default: System.out.println("Probability.setKnown must take int between 0 and 3 inclusive.");
                return;
        }
        for (int i = 0; i < probs.length; i++) {
            probs[i] = 0;
        }
        probs[player] = 1;
        known = true;
    }
    /**
    * Set card as known.
    * @param player The player known to have the card (or who played it)
    */
    public void setKnown(char player){
        switch(player){
            case 'W': probs[0] = 1; break;
            case 'N': probs[1] = 1; break;
            case 'E': probs[2] = 1; break;
            case 'S': probs[3] = 1; break;
            default: System.out.println("Probability.setKnown must take a char in the set {W,N,E,S}"); 
                return;
        }
        for (int i = 0; i < probs.length; i++) {
            probs[i] = 0;
        }
        ownerAsChar = player;
        known = true;
    }
}
