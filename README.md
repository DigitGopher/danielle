danielle
========

Danielle is a Spades (card game) engine and application.




##Project Log
/*
 * Project Danielle
 * Written by Daniel Tixier
 * 
 * 
 * Danielle is an AI (with GUI implementation) that plays a hand of the 4-player
 * card game Spades. It considers itself as sitting in the West position.
 * 
 * 
 * Danielle 1 Started 3/8/2013.
 * Major shell work completed 3/19.
 * Major GUI work completed 3/29.
 * Major initial bid/play logic completed 3/31.
 * Major debugging started 3/31, break took from 4/2 - 4/16.
 * Worked on displaying card pics, completed 4/21.
 * Running prototype 4/21
 * Master background with exit button and scores 4/22
 * Major renovations (bidding nil, redealing, catching wrong input exception, slimming main method)completed 4/23
 * 
 * v1m (m is for modular, meaning classes, not actually modular :))
 * Refactor including Compute and Score classes completed 5/9/2014
 * Changing deck data structure from a char array to CardProbability class, started 7/27/14
 * 
 * Item with $$ is what is currently being worked on.
 * List of smaller things to do:
 * - Don't let someone enter more or less than 13 cards for danielle's hand.
 * - Give a list of enterable values (-1 for nil, -2 for blind nil, etc)
 * - use a map to represent the cards (ex: ACE_SPADES) instead of defining each of them separately. TreeMap<String, Integer> cards = new TreeMap<>();
 * - adjust the bidding method to use more precision than simply ints, as in floats or probabilities
 * - improve sluffing logic - implement weights or map each card to a defined "sluff value"
 * 
 * List of MAJOR things to do:
 * - maybe create a separate Danielle class? maybe create a separate window builder class?
 * -$$ add blind nil logic and scoring
 * - condense windows - use all JPanels instead of dialogs which are super cluncky, or go to eclipse
 * - complete if/else playing logic to approximate a smart player
 * - try different types of algorithms, monte carlo, genetic, probabilistic
 * 
 * List of things to do to generalize the use:
 * - Make it so the 3 players can put in their hands, so you don't need a 4th person to play. (Include check to make sure 2 people don't enter the same card.)
 * - Make it so you can play against 3 instances of Danielle - a 1 player game.
 * 
 * 
 * 
 */