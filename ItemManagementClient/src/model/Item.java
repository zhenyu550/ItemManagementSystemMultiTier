/**
 *	Class that holds the data of a Item
 */
package model;

import java.io.Serializable;

/**
 * @author User
 *
 */

public class Item implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//	Private attributes
	private int id;
	private String code;
	private String name;
	private double price;
	private int stockAmount;
	private String description;
	
	//	Getters
	public int getId() {
		return id;
	}

	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public int getStockAmount() {
		return stockAmount;
	}

	public String getDescription() {
		return description;
	}

	//	Setters
	public void setId(int id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public void setStockAmount(int stockAmount) {
		this.stockAmount = stockAmount;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
