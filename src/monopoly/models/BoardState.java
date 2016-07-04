/**
 * 
 */
package monopoly.models;

import java.util.List;

/**
 * @author Felicien
 *
 */
public class BoardState {

	private Building[] squares;
	/**
	 * the remaining players
	 */
	private List<Player> players;
	/**
	 * the index of the next player to play in players list
	 */
	private int nextTurnIndex;

	public BoardState(Building[] squares, List<Player> players) {
		// TODO: do a full copy to keep initialisation data immutable
		this.squares = squares;
		this.players = players;
		this.nextTurnIndex = 0;
	}

	public int getNextTurnIndex() {
		return nextTurnIndex;
	}

	public void setNextTurnIndex(int nextTurnIndex) {
		this.nextTurnIndex = nextTurnIndex;
	}

	public Building[] getSquares() {
		return squares;
	}

	public void setSquares(Building[] squares) {
		this.squares = squares;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

}
