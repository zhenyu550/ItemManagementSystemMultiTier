/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import controller.ItemController;
import controller.OrderController;
import model.Item;
import model.Order;

/**
 * @author User
 *
 */
public class ServerView extends JFrame {

	/**
	 * @param args
	 */
	
	//	Data Attributes	
	protected ArrayList<Item> itemList = new ArrayList<Item>();
	protected ArrayList<Order> orderList = new ArrayList<Order>();

	//	User Interface Attributes
	private JPanel contentPane;
		//	NORTH
		private JPanel panelNorth;
			private JLabel lblServerTitle;
	
		//	CENTER
		private JTabbedPane tabbedPane;
		
			//	ORDER 
			private JPanel panelOrder;
				//	ORDER NORTH
				private JPanel panelOrderNorth;
					private JButton btnRefreshOrder;
				//	ORDER CENTER
				private JScrollPane scrollPaneOrder;
					private JTable tableOrder;
				//	ORDER SOUTH
				private JPanel panelOrderSouth;
					private JButton btnProcessOrder;

			//	ITEM 
			private JPanel panelItem;
				//	ITEM NORTH
				private JPanel panelItemNorth;
					private JLabel lblItemSearchBy;
					private JTextField tfSearchItem;
					private JComboBox<String> comboBoxItemSearchCond;
					private JComboBox<String> comboBoxItemStringCond;
					private JButton btnSearchItem;
					private JButton btnResetItem;
				//	ITEM CENTER
				private JScrollPane scrollPaneItem;
					private JTable tableItem;
				//	ITEM SOUTH
				private JPanel panelItemSouth;
					private JButton btnAddItem;
					private JButton btnEditItem;
					private JButton btnDeleteItem;
		

	//	Constructor
	public ServerView() {
		
		initializeComponents();
		reloadItemTable("");
		reloadOrderTable("status = 'Processing'");
	}

	//	Method to Initialize all the User Interface Components
	public void initializeComponents() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
			//	NORTH
			panelNorth = new JPanel();
				lblServerTitle = new JLabel("Server Application");
				lblServerTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
			panelNorth.add(lblServerTitle);
		contentPane.add(panelNorth, BorderLayout.NORTH);	
			//	CENTER
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 12));
				//	ORDER
				panelOrder = new JPanel();
				panelOrder.setLayout(new BorderLayout(0, 0));
					//	ORDER NORTH
					panelOrderNorth = new JPanel();
						btnRefreshOrder = new JButton("Refresh");
						btnRefreshOrder.setFont(new Font("Tahoma", Font.PLAIN, 12));
						//btnRefreshOrder.addActionListener(new RefreshOrderButtonListener());
					panelOrderNorth.add(btnRefreshOrder);
				panelOrder.add(panelOrderNorth, BorderLayout.NORTH);
					//	ORDER CENTER
					scrollPaneOrder = new JScrollPane();
						tableOrder = new JTable();
						tableOrder.setModel(new OrderTableModel());
						tableOrder.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					scrollPaneOrder.setViewportView(tableOrder);
				panelOrder.add(scrollPaneOrder, BorderLayout.CENTER);
					//	ORDER SOUTH
					panelOrderSouth = new JPanel();
						btnProcessOrder = new JButton("Process Order");
						btnProcessOrder.setFont(new Font("Tahoma", Font.PLAIN, 12));
						btnProcessOrder.addActionListener(new ProcessOrderButtonListener());
						
					panelOrderSouth.add(btnProcessOrder);
				panelOrder.add(panelOrderSouth, BorderLayout.SOUTH);
						
			tabbedPane.addTab("Order", null, panelOrder, null);
			
				//	ITEM
				panelItem = new JPanel();
				panelItem.setLayout(new BorderLayout(0, 0));
					//	ITEM NORTH
					panelItemNorth = new JPanel();
						lblItemSearchBy = new JLabel("Search by: ");
						lblItemSearchBy.setFont(new Font("Tahoma", Font.PLAIN, 12));
					
						tfSearchItem = new JTextField();
						tfSearchItem.setFont(new Font("Tahoma", Font.PLAIN, 12));
						tfSearchItem.setColumns(10);
					
						comboBoxItemSearchCond = new JComboBox<String>();
						comboBoxItemSearchCond.setFont(new Font("Tahoma", Font.PLAIN, 12));
						comboBoxItemSearchCond.addItem("Code");
						comboBoxItemSearchCond.addItem("Name");
					
						comboBoxItemStringCond = new JComboBox<String>();
						comboBoxItemStringCond.setFont(new Font("Tahoma", Font.PLAIN, 12));
						comboBoxItemStringCond.addItem("Start with");
						comboBoxItemStringCond.addItem("End with");
						comboBoxItemStringCond.addItem("Contains");
					
						btnSearchItem = new JButton("Search");
						btnSearchItem.setFont(new Font("Tahoma", Font.PLAIN, 12));
						btnSearchItem.addActionListener(new SearchItemButtonListener());
					
						btnResetItem = new JButton("Reset");
						btnResetItem.setFont(new Font("Tahoma", Font.PLAIN, 12));
						btnResetItem.addActionListener(new ResetItemButtonListener());
					panelItemNorth.add(lblItemSearchBy);
					panelItemNorth.add(comboBoxItemSearchCond);
					panelItemNorth.add(comboBoxItemStringCond);
					panelItemNorth.add(tfSearchItem);
					panelItemNorth.add(btnSearchItem);
					panelItemNorth.add(btnResetItem);	
				panelItem.add(panelItemNorth, BorderLayout.NORTH);
					//	ITEM CENTER
					scrollPaneItem = new JScrollPane();
						tableItem = new JTable();
						tableItem.setModel(new ItemTableModel());
						tableItem.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					scrollPaneItem.setViewportView(tableItem);
				panelItem.add(scrollPaneItem, BorderLayout.CENTER);
					//	ITEM SOUTH
					panelItemSouth = new JPanel();
						btnAddItem = new JButton("Add Item");
						btnAddItem.setFont(new Font("Tahoma", Font.PLAIN, 12));
						btnAddItem.addActionListener(new AddItemButtonListener());
						
						btnEditItem = new JButton("Edit Item");
						btnEditItem.setFont(new Font("Tahoma", Font.PLAIN, 12));
						btnEditItem.addActionListener(new EditItemButtonListener());

						btnDeleteItem = new JButton("Delete Item");
						btnDeleteItem.setFont(new Font("Tahoma", Font.PLAIN, 12));
						btnDeleteItem.addActionListener(new DeleteItemButtonListener());
					panelItemSouth.add(btnAddItem);
					panelItemSouth.add(btnEditItem);
					panelItemSouth.add(btnDeleteItem);
				panelItem.add(panelItemSouth, BorderLayout.SOUTH);
			tabbedPane.addTab("Item", null, panelItem, null);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
	}
	
	//	Method to reload the Item Table
	protected void reloadItemTable(String condition)
	{
		ItemController controller = new ItemController();
		itemList.clear();
		try {
			if(condition == "")
				itemList = controller.selectAll(0, 2147483467);
			else
				itemList = controller.selectWhere(condition, 0, 2147483467);
			tableItem.repaint();
			tableItem.revalidate();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}	
	}

	//	Method to reload the Order Table
	protected void reloadOrderTable(String condition)
	{
		OrderController controller = new OrderController();
		orderList.clear();
		try {
			if(condition == "")
				orderList = controller.selectAll(0, 2147483467);
			else
				orderList = controller.selectWhere(condition, 0, 2147483467);
			tableOrder.repaint();
			tableOrder.revalidate();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}	
	}

	
	/*
	 *	Button Events for ORDER
	 */	
	//	Process Order Button Event Handler
	private class ProcessOrderButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//	Select the Index of selected row and Ignore the operation if nothing selected
	    	int index = tableOrder.getSelectedRow();
	    	if(index < 0)
	    		return;
	    	
	    	//	Get the Item Id of the selected row
	    	int id = (int) tableOrder.getValueAt(index, 0);	 
	        String condition = String.format("id = %d", id);
	        
	        //	Get the Data of the selected Order
	    	OrderController controller = new OrderController();
	        Order order = null;
	        try {
	        	//	Get the first result from the search
	        	order = controller.selectWhere(condition, 0, 1).get(0);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
	        
	        //	Send the data into Edit Item Form and open it
			OrderDetailView orderDetaildialog = new OrderDetailView(order);
			orderDetaildialog.setVisible(true);
	        
	        //	Update the Item Table if update completed
	        if (orderDetaildialog.IsCompleted())
	        {
	        	reloadOrderTable("status = 'Processing'");
	        	reloadItemTable("");
	        }
		}
	}
		
	/*
	 *	Button Events for ITEM
	 */
	//	Search Item Button Event Handler
	private class SearchItemButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//	Initialize the Strings of the search condition
			String condition = "%s LIKE '%s'", searchCond = "", strCond = "";
			String searchText = tfSearchItem.getText();
			
			//	Determine the searching methods (start by, end with or contains)
			if((String)comboBoxItemStringCond.getSelectedItem() == "End with")
				strCond = "%" + searchText;
			else if((String)comboBoxItemStringCond.getSelectedItem() == "Start with")
				strCond = searchText + "%";
			else
				strCond = "%" + searchText + "%";
			
			//	Determine the column to the searched (Item Code or Item Name)
			if((String)comboBoxItemSearchCond.getSelectedItem() == "Code")
				searchCond = "code";
			else
				searchCond = "name";
			
			//	Format the condition string
			condition = String.format(condition, searchCond, strCond);
			
			//	Reload the Item Table with the specified condition
			reloadItemTable(condition);

		}
	}
	
	//	Reset Item Button Event handler
	private class ResetItemButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//	Reload the Item Table with empty string (Select All) 
			reloadItemTable("");
		}
	}

	//	Add Item Button Event Handler
	private class AddItemButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//	Open Add Item Form
			ItemView addItemView = new ItemView(null);
			addItemView.setVisible(true);
			
			//	Update the table if insert completed
		     if (addItemView.confirmUpdate())
		    	  reloadItemTable("");
		}
	}
	
	//	Edit Item Button Event Handler
	private class EditItemButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//	Select the Index of selected row and Ignore the operation if nothing selected
	    	int index = tableItem.getSelectedRow();
	    	if(index < 0)
	    		return;
	    	
	    	//	Get the Item Id of the selected row
	    	int id = (int) tableItem.getValueAt(index, 0);	 
	        String condition = String.format("id = %d", id);
	        
	        //	Get the Data of the selected Item
	    	ItemController controller = new ItemController();
	        Item item = null;
	        try {
	        	//	Get the first result from the search
	        	item = controller.selectWhere(condition, 0, 1).get(0);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
	        
	        //	Send the data into Edit Item Form and open it
	        ItemView dialog = new ItemView(item);
	        dialog.setVisible(true);
	        
	        //	Update the Item Table if update completed
	        if (dialog.confirmUpdate())
	        {
	        	reloadItemTable("");
	        }
		}
	}
	
	//	Delete Item Button Event Handler
	private class DeleteItemButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//	Select the Index of selected row and Ignore the operation if nothing selected
			int index = tableItem.getSelectedRow();
	    	if(index < 0)
	    		return;
	    	
	    	//	Show message to request confirmation
	    	OptionPaneView opView = new OptionPaneView("Delete Item");
	    	if(opView.getResult()) 
	    	{
	    		//	Get the Item Id of the selected row
	    		int id = (int) tableItem.getValueAt(index, 0);
	    		
	    		//	Set the Id Item to be deleted
	    		ItemController controller = new ItemController();
	    		Item item = new Item();
	    		item.setId(id);
	    		try {
	    			int result = controller.delete(item);
	    			
	    			//	Reload the Item Table if delete completed
	    			if(result == 1)
	    				reloadItemTable("");
	    		} catch (ClassNotFoundException | SQLException e1) {
	    			// TODO Auto-generated catch block
	    			e1.printStackTrace();
	    		}	    
	    	}
		}
	}
	
	/* 
	 * Table Models
	 */
	//	Private class in Server View to bind Item List into Item Table
	private class ItemTableModel implements TableModel
	{
		String header[] = new String[] { "ID", "Name", "Code", "Unit Price", "Stock Amount", "Description"};
		
		public int getColumnCount() 
		{
		   return header.length;
		}
		
		public int getRowCount() 
		{
		   return itemList.size();
		}    
		    
		public String getColumnName(int column) 
		{
		  return header[column];
		}
		        
		public Class getColumnClass(int column) 
		{
		    return String.class;
		}
		   
		public Object getValueAt(int rowIndex, int columnIndex) 
		{
			Item item = itemList.get(rowIndex);
		 	switch(columnIndex)
			{
			 	case 0: return item.getId();
			 	case 1: return item.getName();
			   	case 2: return item.getCode();
			   	case 3: return String.format("%.2f", item.getPrice());
			   	case 4: return item.getStockAmount();
			   	case 5: return item.getDescription();
			}
		 	return "";
		}    
		    
		public boolean isCellEditable(int rowIndex, int columnIndex) 
		{
			return false;
		}
		    
		 public void setValueAt(Object value, int row, int column) {}

		 public void addTableModelListener(TableModelListener l) {}
		    
		 public void removeTableModelListener(TableModelListener l){}  

	}

	//	Private class in Server View to bind Order List into Order Table
	private class OrderTableModel implements TableModel
	{
		String header[] = new String[] { "ID", "Code", "Date", "Time", "Total Price"};
		
		public int getColumnCount() 
		{
		   return header.length;
		}
		
		public int getRowCount() 
		{
		   return orderList.size();
		}    
		    
		public String getColumnName(int column) 
		{
		  return header[column];
		}
		        
		public Class getColumnClass(int column) 
		{
		    return String.class;
		}
		   
		public Object getValueAt(int rowIndex, int columnIndex) 
		{
			Order order = orderList.get(rowIndex);
			LocalDateTime orderDateTime = order.getDateTime();
			DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		 	switch(columnIndex)
			{
			 	case 0: return order.getId();
			 	case 1: return order.getCode();
			 	case 2: return orderDateTime.format(dateFormat);
			 	case 3: return orderDateTime.format(timeFormat);
			   	case 4: return String.format("%.2f", order.getTotalPrice());
			}
		 	return "";
		}    
		    
		public boolean isCellEditable(int rowIndex, int columnIndex) 
		{
			return false;
		}
		    
		 public void setValueAt(Object value, int row, int column) {}

		 public void addTableModelListener(TableModelListener l) {}
		    
		 public void removeTableModelListener(TableModelListener l){}  

	}


	//	Main method for debugging purposes
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerView frame = new ServerView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
