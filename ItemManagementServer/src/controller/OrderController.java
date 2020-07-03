/**
 * 
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import database.DBConnection;
import exceptions.InputException;
import model.Item;
import model.Order;
import model.OrderedItem;

/**
 * @author User
 *
 */
public class OrderController {
	
	// Database related
	public ArrayList<Order> selectAll(int count, int offset) throws ClassNotFoundException, SQLException
	{
		ArrayList<Order> list = new ArrayList<Order>();
        String sql = "SELECT * FROM Orders ORDER BY id ASC LIMIT ?,?;";
        
        Connection con = DBConnection.doConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, count);
        ps.setInt(2, offset);
        
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
        	Order order = new Order();
        	order.setId(rs.getInt("id"));
        	order.setCode(rs.getString("code"));
        	order.setTotalPrice(rs.getDouble("total_price"));
    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        	order.setDateTime(LocalDateTime.parse(rs.getString("date_time"), formatter));
        	order.setStatus(rs.getString("status"));
        	list.add(order);
        }
        con.close();
		return list;
	}

	public ArrayList<Order> selectWhere(String condition, int count, int offset) throws ClassNotFoundException, SQLException
	{
		ArrayList<Order> list = new ArrayList<Order>();
        String sql = "SELECT * FROM Orders WHERE %s ORDER BY id ASC LIMIT %d,%d;";
        sql = String.format(sql, condition, count, offset);
        
        Connection con = DBConnection.doConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
        	Order order = new Order();
        	order.setId(rs.getInt("id"));
        	order.setCode(rs.getString("code"));
        	order.setTotalPrice(rs.getDouble("total_price"));
    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        	order.setDateTime(LocalDateTime.parse(rs.getString("date_time"), formatter));
        	order.setStatus(rs.getString("status"));
        	list.add(order);
        }
        con.close();
		return list;
	}
	
	public int insert(Order data) throws ClassNotFoundException, SQLException
	{
		int success = -1;
		String sql = "INSERT INTO Orders (code, total_price, date_time) values (?,?,?);";
		
		Connection con = DBConnection.doConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, data.getCode()); 
		ps.setDouble(2, data.getTotalPrice());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		ps.setString(3, data.getDateTime().format(formatter));
		
		success = ps.executeUpdate();
		con.close();
		
		return success;
	}
	
	public int update(Order data) throws ClassNotFoundException, SQLException
	{
		int success = -1;
		String sql = "UPDATE Orders SET code=?, total_price=?, date_time=?, status=? WHERE id=?;";
		
		Connection con = DBConnection.doConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, data.getCode()); 
		ps.setDouble(2, data.getTotalPrice());
		ps.setString(4, data.getStatus());
		ps.setInt(5, data.getId());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		ps.setString(3, data.getDateTime().format(formatter));
		success = ps.executeUpdate();
		con.close();
		
		return success;
	}
		
	//Other methods
	public boolean validateOrder(ArrayList<Item> items, ArrayList<Integer> amounts) throws InputException
	{
		boolean validated = false;
		if(items.isEmpty() || amounts.isEmpty())
			throw new InputException("Invalid Order Data");
		else
			validated = true;

		return validated;
	}

	public double calculateTotal(ArrayList<Item> itemList, ArrayList<Integer> amountList) 
	{
		double totalPrice = 0;
		for(int i=0; i < itemList.size(); i++)
		{
			totalPrice += itemList.get(i).getPrice() * amountList.get(i);
		}
		return totalPrice;
	}

	public double calculateChange(double total, double payment) throws InputException
	{
		double change = payment - total;
		if(change < 0)
			throw new InputException("Insufficient Payment");
		else
			return change;
	}
		
	public boolean addOrder(Order order) 
	{
		OrderedItemController orderedItemController = new OrderedItemController();
		
		try {
			//	Insert the Order into Database
			insert(order);
			
			//	Find the Id of the newly inserted order in the database
			ArrayList<Order> orders = selectWhere(String.format("code = '%s'", order.getCode()),0,1);
			int orderId = orders.get(0).getId();
			
			for(int index=0; index < order.getOrderedItems().size(); index++)
			{
				//	Get the OrderedItem data
				OrderedItem orderedItemData = new OrderedItem(); 
				orderedItemData = order.getOrderedItems().get(index);
				
				//	Insert OrderedItem into Database
				orderedItemController.insert(orderedItemData, orderId);			
				
			}
			//	Return true if addOrder() success
			return true;
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean completeOrder(Order order) {
		
		try {
			
			// Update the status to completed
			order.setStatus("Completed");
			update(order);
		
			// Update the stock amount of Items
			ItemController itemController = new ItemController();
			for(OrderedItem orderedItem : order.getOrderedItems()) {
			
				Item affectedItem = orderedItem.getItem();
				int newStockAmount = affectedItem.getStockAmount() - orderedItem.getQuantity();
				affectedItem.setStockAmount(newStockAmount);
			
				itemController.update(affectedItem);
			}	
			return true;
		} catch (ClassNotFoundException | SQLException | InputException e) {
			e.printStackTrace();
			return false;
		}
	} 
	
	//	Main method for debugging purposes
	public static void main(String[] args)
	{
		// Declare Controllers
		ItemController itemController = new ItemController();
		OrderController orderController = new OrderController();
		
		// Declare ArrayLists
		ArrayList<Item> items = new ArrayList<Item>();
		ArrayList<OrderedItem> orderedItems = new ArrayList<OrderedItem>();
		
		// Declare local variables
		Order order= new Order();
		double totalPrice = 0;
		
		try {
			items.add(itemController.selectWhere("id=1", 0, 1).get(0));
			items.add(itemController.selectWhere("id=2", 0, 1).get(0));
			items.add(itemController.selectWhere("id=3", 0, 1).get(0));
			for(Item item : items) {
				OrderedItem orderedItem = new OrderedItem();
				orderedItem.setItem(item);
				orderedItem.setQuantity(10);
				orderedItems.add(orderedItem);
				totalPrice = totalPrice + orderedItem.getSubTotal();
			}
			
			// Set the Order Code
			LocalDateTime currentDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssS");
			String code = currentDateTime.format(formatter);

			// Set the order
			order.setCode(code);
			order.setDateTime(currentDateTime);
			order.setOrderedItems(orderedItems);
			order.setTotalPrice(totalPrice);
			orderController.addOrder(order);
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
