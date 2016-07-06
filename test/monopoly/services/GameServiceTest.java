/**
 * 
 */
package monopoly.services;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import monopoly.models.BoardState;
import monopoly.models.Building;
import monopoly.models.Player;
import monopoly.models.Turn;

/**
 * @author Felicien
 *
 */
public class GameServiceTest {

	private GameService gameService;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.gameService = GameService.getInstance();
	}

	/**
	 * Test method for {@link monopoly.services.GameService#newGame()}.
	 */
	@Test
	public void testNewGame() {
		for (int i = 0; i < 100000; i++) {
			BoardState newGame = this.gameService.newGame();
			if (newGame.getPlayers().size() > GameService.MAX_PLAYERS) {
				fail("New game has more players than MAX_PLAYERS");
			}
			if (newGame.getPlayers().size() < GameService.MIN_PLAYERS) {
				fail("New game has less players than MIN_PLAYERS");
			}
		}
	}

	/**
	 * Test method for {@link monopoly.services.GameService#newGame(int)}.
	 */
	@Test
	public void testNewGameInt() {
		for (int nbPlayers = GameService.MIN_PLAYERS; nbPlayers <= GameService.MAX_PLAYERS; nbPlayers++) {
			BoardState newGame = this.gameService.newGame(nbPlayers);
			if (newGame.getPlayers().size() != nbPlayers) {
				fail("New game has not the expected numbers of players");
			}
			List<Integer> playerIds = new ArrayList<Integer>();
			for (Player player : newGame.getPlayers()) {
				if (player.getMoney() != GameService.START_MONEY) {
					fail(player.toString() + " does not start with expected start money");
				}
				if (!player.getOwnedBuildings().isEmpty()) {
					fail(player.toString() + " should not own any building at first");
				}
				if (playerIds.contains(player.getId())) {
					fail("Found 2 players with the same id");
				}
				playerIds.add(player.getId());
				if (player.getPosition() != 0) {
					fail(player.toString() + " does not start at position 0");
				}
			}
			if (newGame.getSquares().length != GameService.NB_SQUARES) {
				fail("The board have not the expected square numbers");
			}
			for (Building square : newGame.getSquares()) {
				if (square == null) {
					fail("One of the squares is empty");
				} else {
					if (square.getOwner() != null) {
						fail("At start, one the building is owned");
					}
					if (square.getPurchasePrice() != GameService.PURCHASE_PRICE) {
						fail(square.toString() + " purchase price is wrong");
					}
					if (square.getRentPrice() != GameService.RENT_PRICE) {
						fail(square.toString() + " rent price is wrong");
					}
				}
			}
			// TODO: test randomness of players order
		}
	}

	/**
	 * Test method for {@link monopoly.services.GameService#rollDice()}.
	 */
	@Test
	public void testRollDice() {
		for (int i = 0; i < 100000; i++) {
			int roll = this.gameService.rollDice();
			if (roll > 6) {
				fail("Too high dice roll " + roll);
			}
			if (roll < 1) {
				fail("Too low dice roll " + roll);
			}
			// TODO: test repartition
		}
	}

	private BoardState createTestGame(int nbPlayers) {
		Building[] squares = IntStream.range(0, GameService.NB_SQUARES)
				.mapToObj(id -> new Building(id, GameService.PURCHASE_PRICE, GameService.RENT_PRICE))
				.toArray(size -> new Building[size]);
		List<Player> players = IntStream.range(0, nbPlayers).mapToObj(id -> new Player(id, GameService.START_MONEY))
				.collect(Collectors.toList());
		BoardState game = new BoardState(squares, players);
		return game;
	}

	/**
	 * Test method for
	 * {@link monopoly.services.GameService#nextTurn(monopoly.models.BoardState, int)}
	 * .
	 */
	@Test
	public void testNextTurn() {
		BoardState game = this.createTestGame(GameService.MIN_PLAYERS);
		this.gameService.nextTurn(game, 1);
		// TODO: test
	}

}
