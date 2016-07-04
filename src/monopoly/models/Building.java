/**
 * 
 */
package monopoly.models;

/**
 * @author Felicien
 *
 */
public class Building {

	private int id;
	private int purchasePrice;
	private int rentPrice;
	private Player owner;

	public Building(int id, int purchasePrice, int rentPrice) {
		this.id = id;
		this.purchasePrice = purchasePrice;
		this.rentPrice = rentPrice;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(int purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public int getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(int rentPrice) {
		this.rentPrice = rentPrice;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public String toString() {
		return "Building " + id;
	}

}
