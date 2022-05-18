package oh_heaven.game;

// Oh_Heaven.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import oh_heaven.game.gameboard.GameBoard;
import oh_heaven.game.service.Service;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class Oh_Heaven extends CardGame {

	GameBoard gb = new GameBoard();
	Service service = new Service(gb);
	// Oh-heaven Deck from CardGame
  public enum Suit
  {
    SPADES, HEARTS, DIAMONDS, CLUBS
  }
	// Oh-heaven
  public enum Rank
  {
    // Reverse order of rank importance (see rankGreater() below)
	// Order of cards is tied to card images
	ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO
  }
  private final Deck deck = new Deck(Suit.values(), Rank.values(), "cover");

  static public final int seed = 30006;
  static final Random random = new Random(seed);
  
  // return random Enum value
  public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
      int x = random.nextInt(clazz.getEnumConstants().length);
      return clazz.getEnumConstants()[x];
  }

  // return random Card from Hand
  public static Card randomCard(Hand hand){
      int x = random.nextInt(hand.getNumberOfCards());
      return hand.get(x);
  }
 
  // return random Card from ArrayList
  public static Card randomCard(ArrayList<Card> list){
      int x = random.nextInt(list.size());
      return list.get(x);
  }

  // service - dealer
  private void dealingOut(Hand[] hands, int nbPlayers, int nbCardsPerPlayer) {
	  Hand pack = deck.toHand(false);
	  // pack.setView(Oh_Heaven.this, new RowLayout(hideLocation, 0));
	  for (int i = 0; i < nbCardsPerPlayer; i++) {
		  for (int j=0; j < nbPlayers; j++) {
			  if (pack.isEmpty()) return;
			  Card dealt = randomCard(pack);
		      // System.out.println("Cards = " + dealt);
		      dealt.removeFromHand(false);
		      hands[j].insert(dealt, false);
			  // dealt.transfer(hands[j], true);
		  }
	  }
  }
  
  public boolean rankGreater(Card card1, Card card2) {
	  return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
  }

  // game board
  private Hand[] hands;

  // Oh-Heaven
  public void setStatus(String string) { setStatusText(string); }

  // service
//   private int[] scores = new int[gb.nbPlayers];
//   private int[] tricks = new int[gb.nbPlayers];
//   private int[] bids = new int[gb.nbPlayers];
  Font bigFont = new Font("Serif", Font.BOLD, 36);

// should be in service, but coupled with addActor
private void initScore() {
	 for (int i = 0; i < gb.nbPlayers; i++) {
		 // scores[i] = 0;
		 String text = "[" + String.valueOf(service.getScores()[i]) + "]" + String.valueOf(service.getTricks()[i]) + "/" + String.valueOf(service.getBids()[i]);
		 service.getScoreActors()[i] = new TextActor(text, Color.WHITE, bgColor, bigFont);
		 addActor(service.getScoreActors()[i], gb.scoreLocations[i]);
	 }
  }
// should be in service, but coupled with removeActor and addActor
private void updateScore(int player) {
	removeActor(service.getScoreActors()[player]);
	String text = "[" + String.valueOf(service.getScores()[player]) + "]" + String.valueOf(service.getTricks()[player]) + "/" + String.valueOf(service.getBids()[player]);
	service.getScoreActors()[player] = new TextActor(text, Color.WHITE, bgColor, bigFont);
	addActor(service.getScoreActors()[player], gb.scoreLocations[player]);
}

 // Oh-Heaven
private Card selected;

// Oh-Heaven
private void initRound() {
		hands = new Hand[gb.nbPlayers];
		for (int i = 0; i < gb.nbPlayers; i++) {
			   hands[i] = new Hand(deck);
		}
		dealingOut(hands, gb.nbPlayers, gb.nbStartCards);
		 for (int i = 0; i < gb.nbPlayers; i++) {
			   hands[i].sort(Hand.SortType.SUITPRIORITY, true);
		 }
		 // Set up human player for interaction
		CardListener cardListener = new CardAdapter()  // Human Player plays card
			    {
			      public void leftDoubleClicked(Card card) { selected = card; hands[0].setTouchEnabled(false); }
			    };
		hands[0].addCardListener(cardListener);
		 // graphics
	    RowLayout[] layouts = new RowLayout[gb.nbPlayers];
	    for (int i = 0; i < gb.nbPlayers; i++) {
	      layouts[i] = new RowLayout(gb.handLocations[i], gb.handWidth);
	      layouts[i].setRotationAngle(90 * i);
	      // layouts[i].setStepDelay(10);
	      hands[i].setView(this, layouts[i]);
	      hands[i].setTargetArea(new TargetArea(gb.trickLocation));
	      hands[i].draw();
	    }
//	    for (int i = 1; i < nbPlayers; i++) // This code can be used to visually hide the cards in a hand (make them face down)
//	      hands[i].setVerso(true);			// You do not need to use or change this code.
	    // End graphics
 }

 // Oh-Heaven
private void playRound() {
	// Select and display trump suit
		final Suit trumps = randomEnum(Suit.class);
		final Actor trumpsActor = new Actor("sprites/"+gb.trumpImage[trumps.ordinal()]);
	    addActor(trumpsActor, gb.getTrumpsActorLocation());
	// End trump suit
	Hand trick;
	int winner;
	Card winningCard;
	Suit lead;
	int nextPlayer = random.nextInt(gb.nbPlayers); // randomly select player to lead for this round
	service.initBids(trumps, nextPlayer);
    // initScore();
    for (int i = 0; i < gb.nbPlayers; i++) updateScore(i);
	for (int i = 0; i < gb.nbStartCards; i++) {
		trick = new Hand(deck);
    	selected = null;
    	// if (false) {
        if (0 == nextPlayer) {  // Select lead depending on player type
    		hands[0].setTouchEnabled(true);
    		setStatus("Player 0 double-click on card to lead.");
    		while (null == selected) delay(100);
        } else {
    		setStatusText("Player " + nextPlayer + " thinking...");
            delay(service.thinkingTime);
            selected = randomCard(hands[nextPlayer]);
        }
        // Lead with selected card
	        trick.setView(this, new RowLayout(gb.trickLocation, (trick.getNumberOfCards()+2)*gb.trickWidth));
			trick.draw();
			selected.setVerso(false);
			// No restrictions on the card being lead
			lead = (Suit) selected.getSuit();
			selected.transfer(trick, true); // transfer to trick (includes graphic effect)
			winner = nextPlayer;
			winningCard = selected;
		// End Lead
		for (int j = 1; j < gb.nbPlayers; j++) {
			if (++nextPlayer >= gb.nbPlayers) nextPlayer = 0;  // From last back to first
			selected = null;
			// if (false) {
	        if (0 == nextPlayer) {
	    		hands[0].setTouchEnabled(true);
	    		setStatus("Player 0 double-click on card to follow.");
	    		while (null == selected) delay(100);
	        } else {
		        setStatusText("Player " + nextPlayer + " thinking...");
		        delay(service.thinkingTime);
		        selected = randomCard(hands[nextPlayer]);
	        }
	        // Follow with selected card
		        trick.setView(this, new RowLayout(gb.trickLocation, (trick.getNumberOfCards()+2)*gb.trickWidth));
				trick.draw();
				selected.setVerso(false);  // In case it is upside down
				// Check: Following card must follow suit if possible
					if (selected.getSuit() != lead && hands[nextPlayer].getNumberOfCardsWithSuit(lead) > 0) {
						 // Rule violation
						 String violation = "Follow rule broken by player " + nextPlayer + " attempting to play " + selected;
						 System.out.println(violation);
						 if (service.getEnforceRules()) 
							 try {
								 throw(new BrokeRuleException(violation));
								} catch (BrokeRuleException e) {
									e.printStackTrace();
									System.out.println("A cheating player spoiled the game!");
									System.exit(0);
								}  
					 }
				// End Check
				 selected.transfer(trick, true); // transfer to trick (includes graphic effect)
				 System.out.println("winning: " + winningCard);
				 System.out.println(" played: " + selected);
				 // System.out.println("winning: suit = " + winningCard.getSuit() + ", rank = " + (13 - winningCard.getRankId()));
				 // System.out.println(" played: suit = " +    selected.getSuit() + ", rank = " + (13 -    selected.getRankId()));
				 if ( // beat current winner with higher card
					 (selected.getSuit() == winningCard.getSuit() && rankGreater(selected, winningCard)) ||
					  // trumped when non-trump was winning
					 (selected.getSuit() == trumps && winningCard.getSuit() != trumps)) {
					 System.out.println("NEW WINNER");
					 winner = nextPlayer;
					 winningCard = selected;
				 }
			// End Follow
		}
		delay(600);
		trick.setView(this, new RowLayout(gb.getHideLocation(), 0));
		trick.draw();		
		nextPlayer = winner;
		setStatusText("Player " + nextPlayer + " wins trick.");
		service.getTricks()[nextPlayer]++;
		updateScore(nextPlayer);
	}
	removeActor(trumpsActor);
}

  public Oh_Heaven()
  {
	super(700, 700, 30);
    setTitle("Oh_Heaven (V" + gb.version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
    setStatusText("Initializing...");
    service.initScores();
    initScore();
    for (int i=0; i <gb.nbRounds; i++) {
      service.initTricks();
      initRound();
      playRound();
      service.updateScores();
    };
    for (int i=0; i <gb.nbPlayers; i++) updateScore(i);
    int maxScore = 0;
    for (int i = 0; i <gb.nbPlayers; i++) if (service.getScores()[i] > maxScore) maxScore = service.getScores()[i];
    Set <Integer> winners = new HashSet<Integer>();
    for (int i = 0; i <gb.nbPlayers; i++) if (service.getScores()[i] == maxScore) winners.add(i);
    String winText;
    if (winners.size() == 1) {
    	winText = "Game over. Winner is player: " +
    			winners.iterator().next();
    }
    else {
    	winText = "Game Over. Drawn winners are players: " +
    			String.join(", ", winners.stream().map(String::valueOf).collect(Collectors.toSet()));
    }
    addActor(new Actor("sprites/gameover.gif"), gb.textLocation);
    setStatusText(winText);
    refresh();
  }

  public static void main(String[] args)
  {
	// System.out.println("Working Directory = " + System.getProperty("user.dir"));
	final Properties properties;
	if (args == null || args.length == 0) {
	//  properties = PropertiesLoader.loadPropertiesFile(null);
	} else {
	//      properties = PropertiesLoader.loadPropertiesFile(args[0]);
	}
    new Oh_Heaven();
  }

}
