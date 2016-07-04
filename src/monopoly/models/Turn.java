/**
 * 
 */
package monopoly.models;

import monopoly.services.Action;

/**
 * @author Felicien
 *
 */
public class Turn {

	private Player player;
	private int moveOffset;
	private Building moveTo;
	private Action action;

	public Turn(Player player, int moveOffset, Building moveTo) {
		this.player = player;
		this.moveOffset = moveOffset;
		this.moveTo = moveTo;
		this.action = Action.NOTHING;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Player getPlayer() {
		return player;
	}

	public int getMoveOffset() {
		return moveOffset;
	}

	public void setMoveOffset(int moveOffset) {
		this.moveOffset = moveOffset;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Building getMoveTo() {
		return moveTo;
	}

	public void setMoveTo(Building moveTo) {
		this.moveTo = moveTo;
	}

}
