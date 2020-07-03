package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnection;
import model.Item;
import model.OrderedItem;

public class OrderedItemController {

	private int orderId;
	
	//	Getter for Order Id
	public int getOrderId() {
		return orderId;
	}
	
	//	Setter for Order Id
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	//	Methods
	public ArrayList<OrderedItem> selectAll(int count, int offset) throws ClassNotFoundException, SQLException
	{
		ArrayList<OrderedItem> list = new ArrayList<OrderedItem>();
        String sql = "SELECT * FROM Ordered_Item ORDER BY order_id ASC LIMIT ?,?;";
        
        Connection con = DBConnection.doConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, count);
        ps.setInt(2, offset);
        
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
        	OrderedItem orderedItem = new OrderedItem();
        	orderId = (rs.getInt("order_id"));
        	
        	//	Get the Item Data from database using the item Id stored in Ordered Item entity in database
        	int itemId = rs.getInt("item_id");
        	String itemCond = "id=" + itemId;
        	ItemController itemController = new ItemController();
        	ArrayList<Item> resultItem = itemController.selectWhere(itemCond, 0, 1);
        	orderedItem.setItem(resultItem.get(0));
        	orderedItem.setQuantity(rs.getInt("quantity"));
        	
        	list.add(orderedItem);
        }
        con.close();
		return list;
	}
	
	public ArrayList<OrderedItem> selectWhere(String condition, int count, int offset) throws ClassNotFoundException, SQLException
	{
		ArrayList<OrderedItem> list = new ArrayList<OrderedItem>();
        String sql = "SELECT * FROM Ordered_Item WHERE %s ORDER BY item_id ASC LIMIT %d,%d;";
        sql = String.format(sql, condition, count, offset);
        
        Connection con = DBConnection.doConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
        	OrderedItem orderedItem = new OrderedItem();
        	orderId = (rs.getInt("order_id"));
        	
        	//	Get the Item Data from database using the item Id stored in Ordered Item entity in database
        	int itemId = rs.getInt("item_id");
        	String itemCond = "id=" + itemId;
        	ItemController itemController = new ItemController();
        	ArrayList<Item> resultItem = itemController.selectWhere(itemCond, 0, 1);
        	orderedItem.setItem(resultItem.get(0));
        	
        	orderedItem.setQuantity(rs.getInt("quantity"));
        	
        	list.add(orderedItem);
        }
        con.close();
		return list;
	}
	
	public int insert(OrderedItem data, int orderId) throws ClassNotFoundException, SQLException //passed
	{
		int success = -1;
		String sql = "INSERT INTO Ordered_Item (order_id, item_id, quantity) values (?,?,?);";
		
		Connection con = DBConnection.doConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, orderId); 
		ps.setInt(2, data.getItem().getId());
		ps.setInt(3, data.getQuantity()); 
        
		success = ps.executeUpdate();
		con.close();
		
		return success;
	}
	
	public int update(OrderedItem data, int orderId) throws ClassNotFoundException, SQLException //passed
	{
		int success = -1;
		String sql = "UPDATE Ordered_Item SET quantity=? WHERE order_id=? AND item_id=?;";
		
		Connection con = DBConnection.doConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, data.getQuantity()); 
		ps.setInt(2, orderId); 
		ps.setInt(3, data.getItem().getId()); 
		success = ps.executeUpdate();
		con.close();
		
		return success;
	}
	
	public int delete(int orderId) throws ClassNotFoundException, SQLException //passed
	{
		int success = -1;
		String sql = "DELETE FROM Ordered_Item WHERE order_id=?;";
        Connection con = DBConnection.doConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, orderId);
        
        success = ps.executeUpdate();
        con.close();
		return success;
	}

}
