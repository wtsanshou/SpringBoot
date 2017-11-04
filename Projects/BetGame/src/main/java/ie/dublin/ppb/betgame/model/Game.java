package ie.dublin.ppb.betgame.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc this class includes all bets in the input data
 */

public class Game {
	private List<Bet> bets;
	
	public Game(List<Bet> bets) {
		this.bets = new ArrayList<>(bets);
	}
	
	public List<Bet> getBets() {
		return new ArrayList<>(bets);
	}
	
	public void setBets() {
		bets = new ArrayList<>(bets);
	}
}
