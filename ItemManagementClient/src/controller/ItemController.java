/**
 * 
 */
package controller;

import java.util.ArrayList;

import exceptions.InputException;
import model.Item;

/**
 * @author User
 *
 */
public class ItemController {

	/**
	 * @param args
	 */
	// Main method for debugging purposes
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public ItemController() {}
	
	// Method to search the selected item in the server item list
	public ArrayList<Item> searchItem(ArrayList<Item> serverItems, String searchColumn, String searchValue) {
		
		ArrayList<Item> resultItems = new ArrayList<Item>();
		
		for(Item item : serverItems) {			
			
			if(searchColumn.equals("Name")) {
				
				// Convert Item Name to all lower case letters to remove case sensitivity
				String itemNameLower = item.getName().toLowerCase();
				if(itemNameLower.contains(searchValue.toLowerCase()))
					resultItems.add(item);
			} else {
				
				// Convert Item Code to all lower case letters to remove case sensitivity
				String codeLower = item.getCode().toLowerCase();
				if(codeLower.contains(searchValue.toLowerCase()))
					resultItems.add(item);
			}
		}
	
		return resultItems;
	}
	
	public void validateItem(Item item, int orderAmount) throws InputException {
		// Check if any item selected
		if(item.getId() <= 0)
			throw new InputException("No Item");
		
		// Check if the stock amount of the item is enough to fulfill the order
		if(orderAmount > item.getStockAmount())
			throw new InputException("Invalid Item Amount");
	}
}
