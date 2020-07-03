/**
 * 
 */
package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import exceptions.InputException;
import model.Order;
import model.OrderedItem;

/**
 * @author User
 *
 */
public class OrderController {

	public double calculateTotal(ArrayList<OrderedItem> orderedItems) {
		double totalPrice = 0;
		
		for(OrderedItem orderedItem : orderedItems) {
			totalPrice = totalPrice + orderedItem.getSubTotal();
		}
		
		return totalPrice;
	}
	
	public void validateorder(ArrayList<OrderedItem> orderedItems) throws InputException {
		
		// Throw exception if no elements in the list 
		if(orderedItems.isEmpty()) {
			throw new InputException("Invalid Order Data");
		}
		
	}
	
	public Order setOrder(ArrayList<OrderedItem> orderedItems, double totalPrice) {
		
		// Set the code with current dateTime
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssS");
		String code = currentDateTime.format(formatter);

		// Set the order
		Order order = new Order();
		order.setCode(code);
		order.setDateTime(currentDateTime);
		order.setOrderedItems(orderedItems);
		order.setTotalPrice(totalPrice);
		
		// Return the order
		return order;
	}
}
