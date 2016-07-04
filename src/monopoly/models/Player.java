/**
 * 
 */
package monopoly.models;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Felicien
 *
 */
public class Player {

	private int id;
	private List<Building> ownedBuildings;
	private int money;
	private int position;

	public Player(int id, int money) {
		this.id = id;
		this.money = money;
		this.ownedBuildings = new ArrayList<Building>();
	}

	public int getMoney() {
		return money;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public List<Building> getOwnedBuildings() {
		return ownedBuildings;
	}

	public void setOwnedBuildings(List<Building> ownedBuildings) {
		this.ownedBuildings = ownedBuildings;
	}

	public String toString() {
		return "Player " + id;
	}

}
