package danielle;

import javax.swing.JFrame;

/**
 *
 * @author D. Tixier
 * Class to handle the calculations and deck maneuvering.
 */
public class Compute {

    static final int W = 0, N = 1, E = 2, S = 3;
    final int TWO_SPADES = 0;
    final int THREE_SPADES = 1;
    final int FOUR_SPADES = 2;
    final int FIVE_SPADES = 3;
    final int SIX_SPADES = 4;
    final int SEVEN_SPADES = 5;
    final int EIGHT_SPADES = 6;
    final int NINE_SPADES = 7;
    final int TEN_SPADES = 8;
    final int JACK_SPADES = 9;
    final int QUEEN_SPADES = 10;
    final int KING_SPADES = 11;
    final int ACE_SPADES = 12;
    final int TWO_HEARTS = 13;
    final int THREE_HEARTS = 14;
    final int FOUR_HEARTS = 15;
    final int FIVE_HEARTS = 16;
    final int SIX_HEARTS = 17;
    final int SEVEN_HEARTS = 18;
    final int EIGHT_HEARTS = 19;
    final int NINE_HEARTS = 20;
    final int TEN_HEARTS = 21;
    final int JACK_HEARTS = 22;
    final int QUEEN_HEARTS = 23;
    final int KING_HEARTS = 24;
    final int ACE_HEARTS = 25;
    final int TWO_CLUBS = 26;
    final int THREE_CLUBS = 27;
    final int FOUR_CLUBS = 28;
    final int FIVE_CLUBS = 29;
    final int SIX_CLUBS = 30;
    final int SEVEN_CLUBS = 31;
    final int EIGHT_CLUBS = 32;
    final int NINE_CLUBS = 33;
    final int TEN_CLUBS = 34;
    final int JACK_CLUBS = 35;
    final int QUEEN_CLUBS = 36;
    final int KING_CLUBS = 37;
    final int ACE_CLUBS = 38;
    final int TWO_DIAMONDS = 39;
    final int THREE_DIAMONDS = 40;
    final int FOUR_DIAMONDS = 41;
    final int FIVE_DIAMONDS = 42;
    final int SIX_DIAMONDS = 43;
    final int SEVEN_DIAMONDS = 44;
    final int EIGHT_DIAMONDS = 45;
    final int NINE_DIAMONDS = 46;
    final int TEN_DIAMONDS = 47;
    final int JACK_DIAMONDS = 48;
    final int QUEEN_DIAMONDS = 49;
    final int KING_DIAMONDS = 50;
    final int ACE_DIAMONDS = 51;

    /**
     * Method with all the logic for Danielle bidding!
     * Bidding characteristics: -Only takes its own hand into consideration -
     * simply takes what it thinks it can take (effectively it is West, bidding
     * first, as if score is not a factor) (Exception: Blind nil takes
     * evaluation of other hands' bids) -Bid nil as often as possible. -If
     * bidding 4 or more, consider (go into more decisions to decide) bidding
     * one extra.?
     *
     * Define winners: the tricks it is bidding on. Base these off of
     * covers/length of spades Aces, kings, and all spades have a chance to be
     * winners.
     * @param deck Character array representing known cards I think
     * @param we Danielle's team's score
     * @param they Opponents' score
     * @param lc The leader constant - and int representing who leads the hand
     * @return A positive number representing a normal bid OR -1 representing nil OR -2 representing redeal
     */
    public int danielleBids(Card[] deck, int we, int they, int lc) {
        int bid = 0;
        int pointDifferential = they - we; // Positive value means Danielle is losing.

        // First, decide if Danielle is going blind nil.
        if(canGoBlindNil(lc, we, they)){
            return -3; // -3 represents blind nil
        }
        // If not blind nil isn't an option, determine whether or not to bid nil
        // Partner will be going blind nil if we are 200 down...
        if (canGoNil(deck) && pointDifferential < 200) {
            return -1;// Option #1
        }
        // For a normal hand, bid on the high cards

        // Bid on Aces and kings

        if (deck[ACE_HEARTS].isOwner('W')) {
            bid++;
            if (deck[KING_HEARTS].isOwner('W')) {
                bid++;
            }
        } else if (deck[KING_HEARTS].isOwner('W') && numberOfSuit('W', "Hearts", deck) > 2) {
            bid++;
        }

        if (deck[ACE_CLUBS].isOwner('W')) {
            bid++;
            if (deck[KING_CLUBS].isOwner('W')) {
                bid++;
            }
        } else if (deck[KING_CLUBS].isOwner('W') && numberOfSuit('W', "Clubs", deck) > 2) {
            bid++;
        }

        if (deck[ACE_DIAMONDS].isOwner('W')) {
            bid++;
            if (deck[KING_DIAMONDS].isOwner('W')) {
                bid++;
            }
        } else if (deck[KING_DIAMONDS].isOwner('W') && numberOfSuit('W', "Diamonds", deck) > 2) {
            bid++;
        }


        // Bid on spades...
        if (deck[ACE_SPADES].isOwner('W') && deck[KING_SPADES].isOwner('W')
                && deck[QUEEN_SPADES].isOwner('W') && deck[JACK_SPADES].isOwner('W')) {
            bid += (numberOfSuit('W', "Spades", deck));
        } else if (deck[ACE_SPADES].isOwner('W') && deck[KING_SPADES].isOwner('W')
                && deck[QUEEN_SPADES].isOwner('W') && numberOfSuit('W', "Spades", deck) > 3) {
            bid += (numberOfSuit('W', "Spades", deck) - 1);
        } else if (deck[ACE_SPADES].isOwner('W') && deck[KING_SPADES].isOwner('W')
                && deck[QUEEN_SPADES].isOwner('W')) {
            bid += 3;
        } else if ((deck[ACE_SPADES].isOwner('W') || deck[KING_SPADES].isOwner('W'))
                && numberOfSuit('W', "Spades", deck) > 2) {
            bid += numberOfSuit('W', "Spades", deck) - 2;
        } else if (deck[ACE_SPADES].isOwner('W') || deck[KING_SPADES].isOwner('W')) {
            bid++;
        } else if (numberOfSuit('W', "Spades", deck) > 3) {
            bid += (numberOfSuit('W', "Spades", deck) - 3);
        } else if (deck[QUEEN_SPADES].isOwner('W') && numberOfSuit('W', "Spades", deck) > 2) {
            bid += 1;
        } else if (numberOfSuit('W', "Spades", deck) > 0 && (numberOfSuit('W', "Hearts", deck) < 2 || numberOfSuit('W', "Clubs", deck) < 2 || numberOfSuit('W', "Diamonds", deck) < 2)) {
            bid += 1;
        }

        // If can't bid nil or bid 4 or more or go nil(decided above), redeal
        if (bid < 4 && (numberOfSuit('W', "Diamonds", deck) >= 7 || numberOfSuit('W', "Hearts", deck) >= 7
                || numberOfSuit('W', "Clubs", deck) >= 7 || numberOfSuit('W', "Spades", deck) == 0)) {
            System.out.println("$$$$");
            return -2; // Option #redeal
        }

        // Pad the bid for receiving cards in blind nil
        if(pointDifferential >= 200){
            bid += 2;
        }
        
        return bid;// Option normal
    }

    /**
     * Determines if Danielle can go blind nil or not.
     * @param lc
     * @param we
     * @param they
     * @return True if blind nil can attempted.
     */
    public boolean canGoBlindNil(int lc, int we, int they){
        int pointDifferential = they - we;
        if(pointDifferential >= 200 && (lc == S || lc == W)){
            return true;
        }
        return false;
    }
    
    /**
     * Determines if Danielle can go nil or not.
     */
    public boolean canGoNil(Card[] dd) {
        //exception, too good of a hand to go nil
        boolean a_k_spades = false;
        boolean nilableSpades = false;
        boolean badEnoughForNil = true;
        boolean nilableHearts = false;
        boolean nilableClubs = false;
        boolean nilableDiamonds = false;
        // Get the cards Danielle has in each suit
        int[] hearts = inSuit("Hearts", dd);
        int[] spades = inSuit("Spades", dd);
        int[] clubs = inSuit("Clubs", dd);
        int[] diamonds = inSuit("Diamonds", dd);

        // Determine nilable spades.
        // If has Ace or King, no chance at nil.
        if (dd[ACE_SPADES].isOwner('W') || dd[KING_SPADES].isOwner('W'))//no A or K of spades
        {
            a_k_spades = true;
        }

        if (!a_k_spades && numberOfSuit('W', "Spades", dd) < 5) {//&& 4 spades or less,
            if (numberOfSuit('W', "Spades", dd) == 4 && getHigh('W', QUEEN_SPADES, dd) <= NINE_SPADES
                    && getHigh('W', NINE_SPADES, dd) <= FIVE_SPADES)//including Q-9-5-x or less
            {
                nilableSpades = true;
            } else if (numberOfSuit('W', "Spades", dd) == 3 && getHigh('W', QUEEN_SPADES, dd)
                    <= NINE_SPADES && getHigh('W', NINE_SPADES, dd) <= FIVE_SPADES)//including Q-9-5 or less, this line of code is redundant
            {
                nilableSpades = true;
            } else if (numberOfSuit('W', "Spades", dd) == 2 && getHigh('W', QUEEN_SPADES, dd) <= NINE_SPADES) {
                nilableSpades = true;//including Q-9 or less
            } else // holds 1 or 0 spades
            {
                nilableSpades = true;
            }
        }

        // Nilable suit criteria, must be equal to or lower than these values
        //>5  x-x-9-6-5-3
        //5   x-x-8-5-3
        //4   x-J-7-4
        //3   Q-7-4
        //2   10-5
        //1   9

        // Check whichever length of HEARTS Danielle has for the nilable suit criteria.
        if (hearts.length > 5 && hearts[0] <= THREE_HEARTS && hearts[1] <= FIVE_HEARTS && hearts[2] <= SIX_HEARTS && hearts[3] <= NINE_HEARTS) {
            nilableHearts = true;
        } else if (hearts.length == 5 && hearts[0] <= THREE_HEARTS && hearts[1] <= FIVE_HEARTS && hearts[2] <= EIGHT_HEARTS) {
            nilableHearts = true;
        } else if (hearts.length == 4 && hearts[0] <= FOUR_HEARTS && hearts[1] <= SEVEN_HEARTS && hearts[2] <= JACK_HEARTS) {
            nilableHearts = true;
        } else if (hearts.length == 3 && hearts[0] <= FOUR_HEARTS && hearts[1] <= SEVEN_HEARTS && hearts[2] <= QUEEN_HEARTS) {
            nilableHearts = true;
        } else if (hearts.length == 2 && hearts[0] <= FIVE_HEARTS && hearts[1] <= TEN_HEARTS) {
            nilableHearts = true;
        } else if (hearts.length == 1 && hearts[0] <= NINE_HEARTS) {
            nilableHearts = true;
        } else if (hearts.length == 0) {
            nilableHearts = true;
        }

        // Check whichever length of CLUBS Danielle has for the nilable suit criteria.
        if (clubs.length > 5 && clubs[0] <= THREE_CLUBS && clubs[1] <= FIVE_CLUBS && clubs[2] <= SIX_CLUBS && clubs[3] <= NINE_CLUBS) {
            nilableClubs = true;
        } else if (clubs.length == 5 && clubs[0] <= THREE_CLUBS && clubs[1] <= FIVE_CLUBS && clubs[2] <= EIGHT_CLUBS) {
            nilableClubs = true;
        } else if (clubs.length == 4 && clubs[0] <= FOUR_CLUBS && clubs[1] <= SEVEN_CLUBS && clubs[2] <= JACK_CLUBS) {
            nilableClubs = true;
        } else if (clubs.length == 3 && clubs[0] <= FOUR_CLUBS && clubs[1] <= SEVEN_CLUBS && clubs[2] <= QUEEN_CLUBS) {
            nilableClubs = true;
        } else if (clubs.length == 2 && clubs[0] <= FIVE_CLUBS && clubs[1] <= TEN_CLUBS) {
            nilableClubs = true;
        } else if (clubs.length == 1 && clubs[0] <= NINE_CLUBS) {
            nilableClubs = true;
        } else if (clubs.length == 0) {
            nilableClubs = true;
        }

        // Check whichever length of DIAMONDS Danielle has for the nilable suit criteria.
        if (diamonds.length > 5 && diamonds[0] <= THREE_DIAMONDS && diamonds[1] <= FIVE_DIAMONDS && diamonds[2] <= SIX_DIAMONDS && diamonds[3] <= NINE_DIAMONDS) {
            nilableDiamonds = true;
        } else if (diamonds.length == 5 && diamonds[0] <= THREE_DIAMONDS && diamonds[1] <= FIVE_DIAMONDS && diamonds[2] <= EIGHT_DIAMONDS) {
            nilableDiamonds = true;
        } else if (diamonds.length == 4 && diamonds[0] <= FOUR_DIAMONDS && diamonds[1] <= SEVEN_DIAMONDS && diamonds[2] <= JACK_DIAMONDS) {
            nilableDiamonds = true;
        } else if (diamonds.length == 3 && diamonds[0] <= FOUR_DIAMONDS && diamonds[1] <= SEVEN_DIAMONDS && diamonds[2] <= QUEEN_DIAMONDS) {
            nilableDiamonds = true;
        } else if (diamonds.length == 2 && diamonds[0] <= FIVE_DIAMONDS && diamonds[1] <= TEN_DIAMONDS) {
            nilableDiamonds = true;
        } else if (diamonds.length == 1 && diamonds[0] <= NINE_DIAMONDS) {
            nilableDiamonds = true;
        } else if (diamonds.length == 0) {
            nilableDiamonds = true;
        }

        // Don't go nil with more than 2 Aces/Kings
        if (numberOfAcesAndKings(dd) > 2) {
            badEnoughForNil = false;
        }

        // Return
        if (nilableSpades && nilableHearts && nilableClubs && nilableDiamonds && badEnoughForNil) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns highest in a suit. Works the same way as numberOfSuit.
     */
    public int getHigh(char player, String suit, Card[] dd) {
        int num = -1;
        switch (suit) {
            case "Spades":
                for (int i = 0; i < 13; i++) {
                    if (dd[i].isOwner(player)) {
                        num = i;
                    }
                }
                break;
            case "Hearts":
                for (int i = 13; i < 26; i++) {
                    if (dd[i].isOwner(player)) {
                        num = i;
                    }
                }
                break;
            case "Clubs":
                for (int i = 26; i < 39; i++) {
                    if (dd[i].isOwner(player)) {
                        num = i;
                    }
                }
                break;
            case "Diamonds":
                for (int i = 39; i < 52; i++) {
                    if (dd[i].isOwner(player)) {
                        num = i;
                    }
                }
                break;
            default:
                System.out.println("getHigh isnt working");
                break;
        }

        return num;
    }

    /**
     * Returns highest in a suit below or equal to a specified card. If no cards
     * held are below or equal to the specified card, returns -1.
     */
    public int getHigh(char player, int card, Card[] dd) {
        int num = -1;
        if (card < 13) {
            for (int i = 0; i <= card; i++) {
                if (dd[i].isOwner(player)) {
                    num = i;
                }
            }
        } else if (card < 26) {
            for (int i = 13; i <= card; i++) {
                if (dd[i].isOwner(player)) {
                    num = i;
                }
            }
        } else if (card < 39) {
            for (int i = 26; i <= card; i++) {
                if (dd[i].isOwner(player)) {
                    num = i;
                }
            }
        } else if (card < 52) {
            for (int i = 39; i <= card; i++) {
                if (dd[i].isOwner(player)) {
                    num = i;
                }
            }
        } else {
            System.out.println("getHigh isnt working");
        }

        System.out.println("getHigh is returning: " + num + " player " + player +" card: " + card );
        return num;
    }

    /**
     * Returns lowest in a suit above or equal to a specified card.
     */
    public int getLow(char player, int card, Card[] dd) {
        int num = -1;
        if (card < 13) {
            for (int i = 12; i >= card; i--) {
                if (dd[i].isOwner(player)) {
                    num = i;
                }
            }
        } else if (card < 26) {
            for (int i = 25; i >= card; i--) {
                if (dd[i].isOwner(player)) {
                    num = i;
                }
            }
        } else if (card < 39) {
            for (int i = 38; i >= card; i--) {
                if (dd[i].isOwner(player)) {
                    num = i;
                }
            }
        } else if (card < 52) {
            for (int i = 51; i >= card; i--) {
                if (dd[i].isOwner(player)) {
                    num = i;
                }
            }
        } else {
            System.out.println("getLow isnt working");
        }

        System.out.println("getLow is returning: " + num);
        return num;
    }

    /**
     * Returns the number of cards or a given suit in a given hand, that
     * Danielle knows. // *For example, if asking for hearts of North, it
     * returns the number of hearts that North has played. // Used in bidding.
     * Returns 0 if no cards held.
     */
    public int numberOfSuit(char player, String suit, Card[] dd) {
        int num = 0;
        switch (suit) {
            case "Spades":
                for (int i = 0; i < 13; i++) {
                    if (dd[i].isOwner(player)) {
                        num++;
                    }
                }
                break;
            case "Hearts":
                for (int i = 13; i < 26; i++) {
                    if (dd[i].isOwner(player)) {
                        num++;
                    }
                }
                break;
            case "Clubs":
                for (int i = 26; i < 39; i++) {
                    if (dd[i].isOwner(player)) {
                        num++;
                    }
                }
                break;
            case "Diamonds":
                for (int i = 39; i < 52; i++) {
                    if (dd[i].isOwner(player)) {
                        num++;
                    }
                }
                break;
            default:
                System.out.println("numberOfSuit isnt working");
                break;
        }

        return num;
    }

    // North makes a play
    public int humanPlays(char x, Card[] dd, String p, String opN, String opS) {
        String name = "";
        if (x == 'E') {
            name = p;
        } else if (x == 'N') {
            name = opN;
        } else if (x == 'S') {
            name = opS;
        } else {
            name = "Janky, lol";
        }

        Card[] tempdd = dd.clone();

        HandInputFrame hif = new HandInputFrame(x, dd);
        hif.setTitle(name + ": Input " + name + "'s Play. Single Card Only!");
        hif.setLocationRelativeTo(null);
        hif.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        hif.setVisible(true);

        // Wait for button to be pressed on the frame
        while (hif.isVisible()) {
            System.out.print("");
        }

        for (int i = 0; i < tempdd.length; i++) {
            if (tempdd[i].getOwnerAsChar() != dd[i].getOwnerAsChar()) {
                System.out.println(name + " plays card" + i + "], so dd" + i + " now equals: " + dd[i].getOwnerAsChar());
//                System.out.println(name + " plays dd[" + i + "], so this slot now equals: " + dd[i]);
                return i;
            }
        }
        
        return hif.lastCardPlayed;

        // If it didn't return anything, something is wrong
//        System.out.println("Something is wrong in humanPlays. Had to terminate. Oops.");
//        System.exit(x);
        //System.out.println("we are getting here?");
        //return -10;
    }

    /**
     * method with all the logic for how Danielle plays!
     */
    public int daniellePlays(int lc, boolean[] is_nil, boolean[] is_setnil,
            int[] currentTrick, int ourTricks, int theirTricks, int ourBid,
            int theirBid, Card[] dd) {
        int x;
        if (is_nil[W] && !is_setnil[W]) {
            x = sluff(lc, currentTrick, dd);
            dd[x].setDaniellePlayed(true);
            return x;
        } else if (is_nil[N] && !is_setnil[N] || is_nil[S] && !is_setnil[S]) {
            x = sluff(lc, currentTrick, dd);
            dd[x].setDaniellePlayed(true);
            return x;
        } else if (ourTricks >= ourBid) {
            x = sluff(lc, currentTrick, dd);
            dd[x].setDaniellePlayed(true);
            return x;
        } else {//no need to sluff
            x = win(lc, currentTrick, dd);
            dd[x].setDaniellePlayed(true);
            return x;
        }
    }

    /**
     * Play to sluff
     */
    public int sluff(int lc, int[] currentTrick, Card[] dd) {
        System.out.println("in sluff");
        int x = -10;
        switch (lc) {
            case W:// Danielle plays first
                if (numberOfSuit('W', "Hearts", dd) > 0 || numberOfSuit('W', "Clubs", dd) > 0 || numberOfSuit('W', "Diamonds", dd) > 0) {
                    return absoluteLowest(dd);
                } else {
                    return getLow('W', TWO_SPADES, dd);
                }

            case N:// Danielle plays last
                // Find highest card in suit played
                for (int i = 0; i < currentTrick.length; i++) {
                    if (currentTrick[i] > x && currentTrick[i] <= highInSuitConst(currentTrick[lc])) {
                        x = currentTrick[i];
                    }
                }
                x = getHigh('W', x, dd);
                if (x != -1)// sluff in suit
                {
                    return x;
                } else// does not have anything in the suit lower than x
                if (getHigh('W', highInSuitConst(currentTrick[lc]), dd) == -1)// has no card in suit
                {
                    return absoluteHighest(dd);
                } else// has a card in suit, so has to play it
                {
                    return getLow('W', lowInSuitConst(currentTrick[lc]), dd);
                }

            case E:// Danielle plays 3rd
                // Find highest card played so far
                if (currentTrick[S] > currentTrick[E] && currentTrick[S] <= highInSuitConst(currentTrick[E])) {
                    x = currentTrick[S];
                } else {
                    x = currentTrick[E];
                }

                x = getHigh('W', x, dd);

                if (x != -1)// can play under in suit
                {
                    return x;
                } else// has nothing lower than what has been played
                {
                    x = getHigh('W', highInSuitConst(currentTrick[lc]), dd);
                }

                if (x != -1)// play the lowest it can anyway
                {
                    return getLow('W', currentTrick[lc], dd);// 2nd parameter is the only thing that represents suit, and it doesn't matter because W has nothing lower in the hand anyway.
                } else// nothing in the suit
                if (numberOfSuit('W', "Hearts", dd) > 0 || numberOfSuit('W', "Clubs", dd) > 0 || numberOfSuit('W', "Diamonds", dd) > 0) {
                    return absoluteHighest(dd);
                } else {
                    return getLow('W', TWO_SPADES, dd);// if you have to play a spade, play a low one
                }
            case S:// Danielle plays 2nd
                x = getHigh('W', currentTrick[S], dd);

                if (x != -1)// can play under in suit
                {
                    return x;
                } else// has nothing lower than what has been played
                {
                    x = getHigh('W', highInSuitConst(currentTrick[lc]), dd);
                }

                if (x != -1)// play the lowest it can anyway
                {
                    return getLow('W', currentTrick[S], dd);// 2nd parameter is the only thing that represents suit, and it doesn't matter because W has nothing lower in the hand anyway.
                } else// nothing in the suit
                if (numberOfSuit('W', "Hearts", dd) > 0 || numberOfSuit('W', "Clubs", dd) > 0 || numberOfSuit('W', "Diamonds", dd) > 0) {
                    return absoluteHighest(dd);
                } else {
                    return getLow('W', TWO_SPADES, dd);// if you have to play a spade, play a low one
                }
            default:
                System.out.println("daniellePlays method getting to default. Check it out.");
                break;
        }
        return x;// Shouldn't ever get to this point.
    }

    /**
     * Play to win
     */
    public int win(int lc, int[] currentTrick, Card[] dd) {
        System.out.println("in win");
        boolean canWinInSuit = true;
        boolean alreadyRuffedThisTrick = false;
        boolean spadesBroken = false;
        int extra;

        if (lc == W) {
            for (int i = 0; i < 13; i++) {
                if (!dd[i].isOwner('\u0000') && !dd[i].isOwner('W')) {
                    spadesBroken = true;
                    break;
                }
            }
            if (spadesBroken) {
                if (numberOfSuit('W', "Spades", dd) > 0) {
                    return getHigh('W', highInSuitConst(0), dd);
                } else {
                    return absoluteHighest(dd);
                }
            } else {
                if (numberOfSuit('W', "Hearts", dd) > 0 || numberOfSuit('W', "Clubs", dd) > 0 || numberOfSuit('W', "Diamonds", dd) > 0) {
                    return absoluteHighest(dd);
                } else// has to break spades
                {
                    return getHigh('W', highInSuitConst(0), dd);
                }
            }
        }// End lc == 'W'
        else {
            int x = getHigh('W', highInSuitConst(currentTrick[lc]), dd);

            System.out.println("are we here");
            if (x != -1) {// has a card in the suit led
                System.out.println("1");
                for (int i = 0; i < currentTrick.length; i++) {
                    System.out.println("2");
                    if (x < currentTrick[i]) {
                        System.out.println("3");
                        canWinInSuit = false;
                    }
                }
                if (canWinInSuit) {
                    System.out.println("4");
                    for (int i = 0; i < dd.length; i++) {
                        System.out.println(i+" "+dd[i].getOwnerAsChar());
                    }
                    for (int i = 0; i < currentTrick.length; i++) {
                        System.out.println(i+" "+currentTrick[i]);
                    }
                    System.out.println(lc);
                    return x;
                } else// have to play something lower, so go all the way low
                {
                    return getLow('W', lowInSuitConst(currentTrick[lc]), dd);
                }
            } else {// has no card in the suit led; x == -1
                for (int i = 0; i < currentTrick.length; i++) {
                    if (currentTrick[i] >= 0 && currentTrick[i] < 13) {
                        extra = currentTrick[i];
                        if (extra > x) {
                            x = extra;// x represents the highest spade played
                        }
                        alreadyRuffedThisTrick = true;
                    }
                }
                if (alreadyRuffedThisTrick)// select the lowest spade that will win
                {
                    x = getLow('W', x, dd);
                } else// nobody has spaded in
                {
                    x = getLow('W', lowInSuitConst(TWO_SPADES), dd);
                }

                if (x != -1)// W has a spade
                {
                    return x;
                } else// has not spades
                {
                    return absoluteLowest(dd);
                }
            }
        }
    }

    /**
     * Returns the high card in the suit of the given card.
     */
    public int highInSuitConst(int lc) {
        if (lc < 13) {
            return 12;
        } else if (lc < 26) {
            return 25;
        } else if (lc < 39) {
            return 38;
        } else {
            return 51;
        }
    }

    /**
     * Returns the low card in the suit of the given card.
     */
    public int lowInSuitConst(int lc) {
        if (lc < 13) {
            return 0;
        } else if (lc < 26) {
            return 13;
        } else if (lc < 39) {
            return 26;
        } else {
            return 39;
        }
    }

    /**
     * Return the lowest card by number in W's hand, does not return a spade.
     */
    public int absoluteLowest(Card[] dd) {
        int x = 52;
        for (int i = dd.length - 1; i >= dd.length - 13; i--) // check each decreasing number
        {
            for (int j = i; j >= 13; j = j - 13) //check each suit
            {
                if (dd[j].isOwner('W')) {
                    x = j;
                }
            }
        }
        if (x == 52) {
            System.out.println("absoluteLowest did not go lower than 52.");
        }

        System.out.println("absoluteLowest returning: " + x);
        return x;
    }

    /**
     * return the highest card by number in W's hand, does not return a spade
     */
    public int absoluteHighest(Card[] dd) {
        int x = -10;
        for (int i = 13; i < 26; i++) // check each increasing number
        {
            for (int j = i; j <= 51; j = j + 13) //check each suit
            {
                if (dd[j].isOwner('W')) {
                    x = j;
                }
            }
        }
        if (x == 52) {
            System.out.println("absoluteHighest did not go higher than -10.");
        }

        System.out.println("absoluteHighest is returning: " + x);
        return x;
    }

    /**
     * Returns an int array with all the cards in specified suit held by
     * Danielle. [0] contains the lowest card, [length -1] contains the highest
     * card.
     */
    public int[] inSuit(String suit, Card[] dd) {
        int[] nums = new int[numberOfSuit('W', suit, dd)];
        int count = 0;

        switch (suit) {
            case "Spades":
                for (int i = 0; i < 13; i++) {
                    if (dd[i].isOwner('W')) {
                        nums[count] = i;
                        count++;
                    }
                }
                break;
            case "Hearts":
                for (int i = 13; i < 26; i++) {
                    if (dd[i].isOwner('W')) {
                        nums[count] = i;
                        count++;
                    }
                }
                break;
            case "Clubs":
                for (int i = 26; i < 39; i++) {
                    if (dd[i].isOwner('W')) {
                        nums[count] = i;
                        count++;
                    }
                }
                break;
            case "Diamonds":
                for (int i = 39; i < 52; i++) {
                    if (dd[i].isOwner('W')) {
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

    /**
     * Returns how many Aces and Kings Danielle has.
     */
    public int numberOfAcesAndKings(Card[] dd) {
        int num = 0;
        if (dd[ACE_CLUBS].isOwner('W')) {
            num++;
        }
        if (dd[ACE_DIAMONDS].isOwner('W')) {
            num++;
        }
        if (dd[ACE_HEARTS].isOwner('W')) {
            num++;
        }
        if (dd[ACE_SPADES].isOwner('W')) {
            num++;
        }
        if (dd[KING_CLUBS].isOwner('W')) {
            num++;
        }
        if (dd[KING_DIAMONDS].isOwner('W')) {
            num++;
        }
        if (dd[KING_HEARTS].isOwner('W')) {
            num++;
        }
        if (dd[KING_SPADES].isOwner('W')) {
            num++;
        }

        return num;
    }
}
