package oh_heaven.game;
import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import oh_heaven.game.gameboard.GameBoard;
import oh_heaven.game.playerboard.CompositePlayer;
import oh_heaven.game.playerboard.player.Player;
import oh_heaven.game.service.Dealer;
import oh_heaven.game.service.PropertiesLoader;
import oh_heaven.game.service.Rule;
import oh_heaven.game.service.Rule.Suit;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class Oh_Heaven extends CardGame {
	private Properties properties;
	static public final int seed = 30006;
	static final Random random = new Random(seed);
	Font bigFont = new Font("Serif", Font.BOLD, 36);
	private Card selected;

	// TODO: refactor the object instantiation
	GameBoard gb = new GameBoard();
	CompositePlayer cp = new CompositePlayer(random);
	Dealer dealer = new Dealer(random);
	Rule rule = new Rule(random, gb);
	private final Deck deck = rule.getDeck();

	// TODO: temporary, need to re-arrange the code
	private void setProperties(Properties properties) {
		this.properties = properties;
		cp.setProperties(properties);
	}

	// Oh-Heaven
	public void setStatus(String string) { setStatusText(string); }

	private void initScore() {
		for (int i = 0; i < gb.nbPlayers; i++) {
			// scores[i] = 0;
			String text = "[" + String.valueOf(rule.getScores()[i]) + "]" + String.valueOf(rule.getTricks()[i]) + "/" + String.valueOf(rule.getBids()[i]);
			rule.getScoreActors()[i] = new TextActor(text, Color.WHITE, bgColor, bigFont);
			addActor(rule.getScoreActors()[i], gb.scoreLocations[i]);
		}
	}

	private void updateScore(int player) {
		removeActor(rule.getScoreActors()[player]);
		String text = "[" + String.valueOf(rule.getScores()[player]) + "]" + String.valueOf(rule.getTricks()[player]) + "/" + String.valueOf(rule.getBids()[player]);
		rule.getScoreActors()[player] = new TextActor(text, Color.WHITE, bgColor, bigFont);
		addActor(rule.getScoreActors()[player], gb.scoreLocations[player]);
	}

	private void initCompositeRound() {
		List<Player> players = cp.getPlayers(); // could hide the players
		cp.initPlayerDeck(players, deck);
		dealer.dealingOut(deck, players, gb.nbStartCards);
		cp.playerSortCards(players);
		setHumanInteraction(players.get(0)); // specify which player is human
		initGraphics(players);
	}

	private void setHumanInteraction(Player player) {
		CardListener cardListener = new CardAdapter() {
			public void leftDoubleClicked(Card card) {
				// get the first player to be humanPlayer
				selected = card; player.deck.setTouchEnabled(false);
			}
		};
		player.deck.addCardListener(cardListener);
	}

	/**
	 * Initialize CardGame graphics
	 * @param players
	 */
	private void initGraphics(List<Player> players) {
		RowLayout[] layouts = new RowLayout[gb.nbPlayers];
		for (int i = 0; i < gb.nbPlayers; i++) {
			layouts[i] = new RowLayout(gb.handLocations[i], gb.handWidth);
			layouts[i].setRotationAngle(90 * i);
			// layouts[i].setStepDelay(10);
			players.get(i).deck.setView(this, layouts[i]);
			players.get(i).deck.setTargetArea(new TargetArea(gb.trickLocation));
			players.get(i).deck.draw();
	    }
	}

	private void compositePlayRound() {
		// Select and display trump suit
		final Suit trumps = rule.randomEnum(Suit.class);
		final Actor trumpsActor = new Actor("sprites/"+gb.trumpImage[trumps.ordinal()]);
	    addActor(trumpsActor, gb.getTrumpsActorLocation());
		// End trump suit
		Hand trick;
		int winner;
		Card winningCard;
		Suit lead;
		List<Player> players = cp.getPlayers();
		int nextPlayer = random.nextInt(players.size()); // randomly select player to lead for this round
		rule.initBids(trumps, nextPlayer);
    	// initScore();
    	for (int i = 0; i < players.size(); i++) updateScore(i);
		for (int i = 0; i < gb.nbStartCards; i++) {
			trick = new Hand(deck);

    		selected = null;
			selected = pickPlayer(players, nextPlayer);

			// don't know how to refactor yet
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

			for (int j = 1; j < players.size(); j++) {
				if (++nextPlayer >= players.size()) nextPlayer = 0;  // From last back to first
				selected = null;
				selected = pickPlayer(players, nextPlayer);

				// Follow with selected card
		        trick.setView(this, new RowLayout(gb.trickLocation, (trick.getNumberOfCards()+2)*gb.trickWidth));
				trick.draw();
				selected.setVerso(false);  // In case it is upside down

				if (selected.getSuit() != lead && players.get(nextPlayer).deck.getNumberOfCardsWithSuit(lead) > 0) {
					violateRule(nextPlayer);
				}
				// End Check
				selected.transfer(trick, true); // transfer to trick (includes graphic effect)
				System.out.println("winning: " + winningCard);
				System.out.println(" played: " + selected);
				// System.out.println("winning: suit = " + winningCard.getSuit() + ", rank = " + (13 - winningCard.getRankId()));
				// System.out.println(" played: suit = " +    selected.getSuit() + ", rank = " + (13 -    selected.getRankId()));
				if ( // beat current winner with higher card
					(selected.getSuit() == winningCard.getSuit() && rule.rankGreater(selected, winningCard)) ||
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
			rule.getTricks()[nextPlayer]++;
			updateScore(nextPlayer);
		}
		removeActor(trumpsActor);
	}

	private Card pickPlayer(List<Player> players, int nextPlayer) {
		if (0 == nextPlayer) {  // Select lead depending on player type
			players.get(0).deck.setTouchEnabled(true);
			setStatus("Player 0 double-click on card to lead.");
			while (null == selected) delay(100);
		} else {
			setStatusText("Player " + nextPlayer + " thinking...");
			delay(rule.thinkingTime);
			selected = dealer.randomCard(players.get(nextPlayer).deck);
		}
		return selected;
	}

	private void violateRule(int nextPlayer) {
		// Rule violation
		String violation = "Follow rule broken by player " + nextPlayer + " attempting to play " + selected;
		System.out.println(violation);
		if (rule.getEnforceRules()) {
			try {
				throw(new BrokeRuleException(violation));
			} catch (BrokeRuleException e) {
				e.printStackTrace();
				System.out.println("A cheating player spoiled the game!");
				System.exit(0);
			}
		}
	}

	public Oh_Heaven(Properties properties) {
		super(700, 700, 30);
		setTitle("Oh_Heaven (V" + gb.version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
		setStatusText("Initializing...");
		setProperties(properties);
		rule.initScores();
		initScore();
		for (int i=0; i <gb.nbRounds; i++) {
			rule.initTricks();
			initCompositeRound();
			compositePlayRound();
			rule.updateScores();
		};
		for (int i=0; i <gb.nbPlayers; i++) updateScore(i);
		int maxScore = 0;
		for (int i = 0; i <gb.nbPlayers; i++) if (rule.getScores()[i] > maxScore) maxScore = rule.getScores()[i];
		Set <Integer> winners = new HashSet<Integer>();
		for (int i = 0; i <gb.nbPlayers; i++) if (rule.getScores()[i] == maxScore) winners.add(i);
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

  public static void main(String[] args) {
	System.out.println("Working Directory = " + System.getProperty("user.dir"));
	final Properties properties;
	if (args == null || args.length == 0) {
		properties = PropertiesLoader.loadPropertiesFile(null);
	} else {
		properties = PropertiesLoader.loadPropertiesFile(args[0]);
	}
    new Oh_Heaven(properties);
  }

}
