package oh_heaven.game;
import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import oh_heaven.game.gameboard.GameBoard;
import oh_heaven.game.gameboard.Result;
import oh_heaven.game.gameboard.StatusBoard;
import oh_heaven.game.playerboard.CompositePlayer;
import oh_heaven.game.playerboard.PlayerBoard;
import oh_heaven.game.playerboard.player.Player;
import oh_heaven.game.service.Dealer;
import oh_heaven.game.service.Rule;
import oh_heaven.game.service.Service;
import oh_heaven.game.service.Rule.Suit;
import oh_heaven.game.utilities.PropertiesLoader;
import oh_heaven.game.utilities.ServiceRandom;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class Oh_Heaven extends CardGame {
	// fields
	private Card selected;
	private final Deck deck;

	// gameboard components
	GameBoard gb;
	Result result;
	StatusBoard statusBoard;

	// playerboard components
	PlayerBoard pb;
	CompositePlayer cp;
	List<Player> players;

	// service components
	Service service;
	Rule rule;
	Dealer dealer;
	
	Font bigFont = new Font("Serif", Font.BOLD, 36);

	public Oh_Heaven(Properties properties, GameBoard gb, PlayerBoard pb, Service service) {
		super(700, 700, 30);
		// can use PropertiesLoader to load more configurable properties to the game

		// pure fabrication: gameboard components
		this.gb = gb;
		this.result = gb.getResult();
		this.statusBoard = gb.getStatusBoard();
		
		// pure fabrication: playerboard components
		this.pb = pb;
		this.cp = pb.getCp();
		this.players = cp.getPlayers();

		// pure fabrication: service components
		this.service = service;
		this.rule = service.getRule();
		this.dealer = service.getDealer();
		
		this.deck = rule.getDeck();

		setTitle("Oh_Heaven (V" + gb.version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
		setStatusText("Initializing...");
		rule.initPlayerScores(players);
		initPlayerScoreActors();
	}

	private void play() {
		for (int i=0; i < gb.nbRounds; i++) {
			rule.initTricks(players);
			initCompositeRound();
			compositePlayRound();
			rule.updatePlayerScores(players);
		};
	}

	private void gameEndOperation() {
		for (int i=0; i < players.size(); i++) updatePlayerScoreActors(i);
		int maxScore = 0;
		for (int i = 0; i < players.size(); i++) if (players.get(i).getScores() > maxScore) maxScore = players.get(i).getScores();
		Set <Integer> winners = new HashSet<Integer>();
		for (int i = 0; i < players.size(); i++) if (players.get(i).getScores() == maxScore) winners.add(i);
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

	public void setStatus(String string) { setStatusText(string); }

	private void initPlayerScoreActors() {
		for (int i = 0; i < players.size(); i++) {
			String text = "[" +
					String.valueOf(players.get(i).getScores()) + "]" +
					String.valueOf(players.get(i).getTricks()) + "/" +
					String.valueOf(players.get(i).getBids());
			rule.getScoreActors()[i] = new TextActor(text, Color.WHITE, bgColor, bigFont);
			addActor(rule.getScoreActors()[i], gb.scoreLocations[i]);
		}
	}

	private void updatePlayerScoreActors(int player) {
		removeActor(rule.getScoreActors()[player]);
		String text = "[" +
				String.valueOf(players.get(player).getScores()) + "]" +
				String.valueOf(players.get(player).getTricks()) + "/" +
				String.valueOf(players.get(player).getBids());
		rule.getScoreActors()[player] = new TextActor(text, Color.WHITE, bgColor, bigFont);
		addActor(rule.getScoreActors()[player], gb.scoreLocations[player]);
	}

	private void initCompositeRound() {
		// List<Player> players = cp.getPlayers(); // could hide the players
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
		int nextPlayer = ServiceRandom.getSeedRandom().nextInt(players.size()); // randomly select player to lead for this round
		rule.initBids(trumps, nextPlayer, players);
    	// initScore();
    	for (int i = 0; i < players.size(); i++) updatePlayerScoreActors(i);
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
					rule.violateRule(nextPlayer, selected);
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
			int tricks = players.get(nextPlayer).getTricks();
			players.get(nextPlayer).setTricks(tricks + 1);

			updatePlayerScoreActors(nextPlayer);
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

  public static void main(String[] args) {
	System.out.println("Working Directory = " + System.getProperty("user.dir"));
	final Properties properties;

	if (args == null || args.length == 0) {
		properties = PropertiesLoader.loadPropertiesFile(null);
	} else {
		properties = PropertiesLoader.loadPropertiesFile(args[0]);
	}
	String seedProp = properties.getProperty("seed");
	Long seed = null;
	if (seedProp != null) {
		seed = Long.parseLong(seedProp);
	}
	ServiceRandom.initServicesRandom(seed);

	CompositePlayer cp = new CompositePlayer(properties);
	PlayerBoard pb = new PlayerBoard(cp);

	Result result = new Result();
	StatusBoard sb = new StatusBoard();
	GameBoard gb = new GameBoard(result, sb);

	Dealer dealer = new Dealer();
	Rule rule = new Rule();

	Service service = new Service(rule, dealer);

    Oh_Heaven game = new Oh_Heaven(properties, gb, pb, service);
	game.play();
	game.gameEndOperation();
  }

}
