package monopoly;
/**
 * 
 */

import java.util.stream.Collectors;

import monopoly.models.BoardState;
import monopoly.models.Player;
import monopoly.models.Turn;
import monopoly.services.GameService;

/**
 * @author Felicien
 *
 */
public class Simulator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameService gameService = GameService.getInstance();
		BoardState boardState = gameService.newGame();
		System.out.println("Starting a new game with " + boardState.getPlayers().size() + " players");
		System.out.println("Players will play in the following order: "
				+ boardState.getPlayers().stream().map(p -> p.toString()).collect(Collectors.joining(", ")));

		System.out.println(
				"Players money: " + boardState.getPlayers().stream().sorted((p1, p2) -> p1.getId() - p2.getId())
						.map(p -> "" + p.getId() + ": €" + p.getMoney()).collect(Collectors.joining(", ")));

		while (boardState.getPlayers().size() > 1) {
			int nextTurnIndex = boardState.getNextTurnIndex();
			Player player = boardState.getPlayers().get(nextTurnIndex);
			System.out.println("> " + player.toString() + " turn");
			int moveOffset = gameService.rollDice();
			System.out.println("	Rolling dice ... " + moveOffset);
			Turn turn = gameService.nextTurn(boardState, moveOffset);
			System.out.println("	Moving to ... " + turn.getMoveTo());
			if (turn.getMoveTo().getOwner() == null) {
				System.out.println("	the building is purchasable");
			} else {
				System.out.println("	the building is owned by " + turn.getMoveTo().getOwner().toString());
			}
			switch (turn.getAction()) {
			case PURCHASE:
				System.out.println("	" + player.toString() + " bought " + turn.getMoveTo().toString() + " for €"
						+ turn.getMoveTo().getPurchasePrice());
				break;
			case RENT:
				System.out.println("	" + player.toString() + " => €" + turn.getMoveTo().getRentPrice() + " => "
						+ turn.getMoveTo().getOwner().toString());
				break;
			case LOOSE:
				System.out.println("	" + player.toString() + " have not enough money to pay the rent");
				System.out.println(
						"	" + player.toString() + " => all his money => " + turn.getMoveTo().getOwner().toString());
				System.out.println(">>> " + player.toString() + " LOST");
				break;
			case NOTHING:
			default:
				System.out.println("	nothing done");
				break;
			}
			System.out.println(
					"Players money: " + boardState.getPlayers().stream().sorted((p1, p2) -> p1.getId() - p2.getId())
							.map(p -> "" + p.getId() + ": €" + p.getMoney()).collect(Collectors.joining(", ")));
		}

		System.out.println("");
		System.out.println(">>> " + boardState.getPlayers().get(0).toString() + " WON");
	}

}
