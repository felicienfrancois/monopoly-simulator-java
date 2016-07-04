/**
 * 
 */
package monopoly.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import monopoly.models.Action;
import monopoly.models.BoardState;
import monopoly.models.Building;
import monopoly.models.Player;
import monopoly.models.Turn;

/**
 * @author Felicien
 *
 */
public class GameService {

	// TODO: move this into properties
	static int NB_SQUARES = 20;
	static int START_MONEY = 1000;
	static int PURCHASE_PRICE = 100;
	static int RENT_PRICE = 20;
	static int MIN_PLAYERS = 2;
	static int MAX_PLAYERS = 5;
	static int MIN_REMAINING_MONEY_AFTER_PURCHASE = 100;

	/**
	 * Singleton
	 */
	private GameService() {
	}

	private static GameService instance;

	public static GameService getInstance() {
		if (instance == null) {
			instance = new GameService();
		}
		return instance;
	}

	/**
	 * create a new game state
	 * 
	 * @return
	 */
	public BoardState newGame() {
		return newGame(MIN_PLAYERS + (int) Math.floor((MAX_PLAYERS - MIN_PLAYERS + 1) * Math.random()));
	}

	/**
	 * create a new game state
	 * 
	 * @return
	 */
	public BoardState newGame(int nbPlayers) {
		List<Player> players = IntStream.range(0, nbPlayers).mapToObj(id -> new Player(id, START_MONEY))
				.collect(Collectors.toList());
		// randomize players order
		Collections.shuffle(players);

		Building[] squares = IntStream.range(0, NB_SQUARES).mapToObj(id -> new Building(id, PURCHASE_PRICE, RENT_PRICE))
				.toArray(size -> new Building[size]);

		return new BoardState(squares, players);
	}

	/**
	 * @return a random int between 1 and 6
	 */
	public int rollDice() {
		return (int) Math.floor(1 + 6 * Math.random());
	}

	/**
	 * calculate where
	 * 
	 * @param boardState
	 * @param moveOffset
	 * @return
	 */
	public Turn nextTurn(BoardState boardState, int moveOffset) {
		int nextTurnIndex = boardState.getNextTurnIndex();
		Player player = boardState.getPlayers().get(nextTurnIndex);
		int nbSquares = boardState.getSquares().length;
		int newPosition = (player.getPosition() + moveOffset) % nbSquares;
		Building moveTo = boardState.getSquares()[newPosition];
		Turn turn = new Turn(player, moveOffset, moveTo);

		if (moveTo != null) {
			// there is a building on the destination square
			if (moveTo.getOwner() == null) {
				if (player.getMoney() - moveTo.getPurchasePrice() > MIN_REMAINING_MONEY_AFTER_PURCHASE) {
					// purchase
					turn.setAction(Action.PURCHASE);
					player.setMoney(player.getMoney() - moveTo.getPurchasePrice());
					moveTo.setOwner(player);
					player.getOwnedBuildings().add(moveTo);
				}
			} else {
				if (moveTo.getOwner().equals(player)) {
					// free rent !
				} else if (player.getMoney() - moveTo.getRentPrice() >= 0) {
					// rent
					turn.setAction(Action.RENT);
					player.setMoney(player.getMoney() - moveTo.getRentPrice());
					moveTo.getOwner().setMoney(moveTo.getOwner().getMoney() + moveTo.getRentPrice());
				} else {
					// loose
					turn.setAction(Action.LOOSE);
					moveTo.getOwner().setMoney(moveTo.getOwner().getMoney() + player.getMoney());
					player.setMoney(0);
					player.getOwnedBuildings().forEach(b -> b.setOwner(null));
					boardState.getPlayers().remove(player);
					nextTurnIndex--;
				}
			}
		}

		// increment nextTurnIndex
		boardState.setNextTurnIndex((nextTurnIndex + 1) % boardState.getPlayers().size());

		return turn;
	}

}
