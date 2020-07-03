/**
 * 
 */
package controller;

import java.util.ArrayList;

import exceptions.InputException;
import model.OrderedItem;

/**
 * @author User
 *
 */
public class OrderedItemController {
		
	public void checkDuplicateItem(OrderedItem newOrderedItem, ArrayList<OrderedItem> orderedItems) throws InputException {
		
		int newItemId = newOrderedItem.getItem().getId();
		
		if(orderedItems.isEmpty()) {
			// If the first element in the list is null, end the method
			return;
			
		} else {
			// Loop the orderedItems to find duplicate
			for(OrderedItem orderedItem : orderedItems) {
				if(newItemId == orderedItem.getItem().getId()) {
					throw new InputException("Duplicate Item");
				}
			}
		}
	}
}
