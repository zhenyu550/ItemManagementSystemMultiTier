package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import controller.*;
import exceptions.InputException;
import model.*;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class ClientView extends JFrame {

	// Data variables
	protected ArrayList<Item> serverItemList = new ArrayList<Item>();			// List of Available Items stored in the server
	private ArrayList<OrderedItem> orderedItems = new ArrayList<OrderedItem>(); // List of Ordered Items, used to insert Order

	private double totalPrice;


	// User Interface Variables
	private JPanel contentPane;
		// NORTH
		private JPanel panelNorth;
		private JLabel lblMainPanel;
		// CENTER
		private JTabbedPane tabbedPane;
			private JPanel panelOrder;
				// ORDER WEST
				private JPanel panelOrderWest;
					private JButton btnAddItem;
					private JButton btnEditAmount;
					private JButton btnRemoveItem;
				// ORDER SOUTH
				private JPanel panelOrderSouth;
					private JButton btnResetOrder;
					private JButton btnCheckoutOrder;
				// ORDER CENTER
				private JPanel panelOrderCenter;
					private JScrollPane scrollPane;
						private JTable tableOrderItem;
					// ORDER CENTER_SOUTH
					private JPanel panelOrderCenter_South;
						private JLabel lblTotalPrice;
						private JLabel lblTotalPriceDisplay;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientView frame = new ClientView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientView() {
		initializeComponents();
	}
	
	public void initializeComponents()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
			// NORTH
			panelNorth = new JPanel();
				lblMainPanel = new JLabel("Item Cart");
				lblMainPanel.setFont(new Font("Tahoma", Font.PLAIN, 20));
			panelNorth.add(lblMainPanel);
		contentPane.add(panelNorth, BorderLayout.NORTH);		
		
			// CENTER
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
				// ORDER
				panelOrder = new JPanel();
				panelOrder.setLayout(new BorderLayout(0, 0));
				
					// ORDER WEST
					panelOrderWest = new JPanel();
						btnAddItem = new JButton("Add Item");
						btnAddItem.setFont(new Font("Tahoma", Font.PLAIN, 13));
						btnAddItem.addActionListener(new AddItemButtonListener());
						
						btnEditAmount = new JButton("Edit Amount");
						btnEditAmount.setFont(new Font("Tahoma", Font.PLAIN, 13));
						btnEditAmount.addActionListener(new EditItemButtonListener());
						
						btnRemoveItem = new JButton("Remove Item");
						btnRemoveItem.setFont(new Font("Tahoma", Font.PLAIN, 13));
						btnRemoveItem.addActionListener(new RemoveItemButtonListener());
						
						GroupLayout gl_panelOrderWest = new GroupLayout(panelOrderWest);
						gl_panelOrderWest.setHorizontalGroup(
							gl_panelOrderWest.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelOrderWest.createSequentialGroup()
									.addContainerGap()
									.addGroup(gl_panelOrderWest.createParallelGroup(Alignment.LEADING)
										.addComponent(btnRemoveItem, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnAddItem, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
										.addComponent(btnEditAmount, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
									.addContainerGap())
						);
						gl_panelOrderWest.setVerticalGroup(
							gl_panelOrderWest.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelOrderWest.createSequentialGroup()
									.addComponent(btnAddItem, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnEditAmount, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnRemoveItem, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
									.addContainerGap(235, Short.MAX_VALUE))
						);
					panelOrderWest.setLayout(gl_panelOrderWest);
				panelOrder.add(panelOrderWest, BorderLayout.WEST);
				
					// ORDER SOUTH
					panelOrderSouth = new JPanel();
						btnResetOrder = new JButton("Reset");
						btnResetOrder.setFont(new Font("Tahoma", Font.PLAIN, 13));
						btnResetOrder.addActionListener(new ResetOrderButtonListener());

						btnCheckoutOrder = new JButton("Checkout");
						btnCheckoutOrder.setFont(new Font("Tahoma", Font.PLAIN, 13));
						btnCheckoutOrder.addActionListener(new CheckoutOrderButtonListener());
						
						GroupLayout gl_panelOrderSouth = new GroupLayout(panelOrderSouth);
						gl_panelOrderSouth.setHorizontalGroup(
								gl_panelOrderSouth.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelOrderSouth.createSequentialGroup()
									.addContainerGap()
									.addComponent(btnResetOrder, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 541, Short.MAX_VALUE)
									.addComponent(btnCheckoutOrder, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
						);
						gl_panelOrderSouth.setVerticalGroup(
								gl_panelOrderSouth.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_panelOrderSouth.createSequentialGroup()
									.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGroup(gl_panelOrderSouth.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnResetOrder)
										.addComponent(btnCheckoutOrder)))
						);
					panelOrderSouth.setLayout(gl_panelOrderSouth);
				panelOrder.add(panelOrderSouth, BorderLayout.SOUTH);
				
					// ORDER CENTER
					panelOrderCenter = new JPanel();
					panelOrderCenter.setLayout(new BorderLayout(0, 0));
						scrollPane = new JScrollPane();
							tableOrderItem = new JTable();
							tableOrderItem.setModel(new OrderItemTableModel());
							tableOrderItem.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						scrollPane.setViewportView(tableOrderItem);
					panelOrderCenter.add(scrollPane, BorderLayout.CENTER);
					
						// ORDER CENTER_SOUTH
						panelOrderCenter_South = new JPanel();
							lblTotalPrice = new JLabel("Total Price: ");
							lblTotalPrice.setFont(new Font("Tahoma", Font.PLAIN, 13));
						
							lblTotalPriceDisplay = new JLabel("RM 0.00");
							lblTotalPriceDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
							lblTotalPriceDisplay.setFont(new Font("Tahoma", Font.PLAIN, 18));

							GroupLayout gl_panelOrderCenter_South = new GroupLayout(panelOrderCenter_South);
							gl_panelOrderCenter_South.setHorizontalGroup(
									gl_panelOrderCenter_South.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panelOrderCenter_South.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblTotalPrice)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblTotalPriceDisplay, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
										.addContainerGap())
							);
							gl_panelOrderCenter_South.setVerticalGroup(
									gl_panelOrderCenter_South.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_panelOrderCenter_South.createSequentialGroup()
										.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGroup(gl_panelOrderCenter_South.createParallelGroup(Alignment.BASELINE)
											.addComponent(lblTotalPrice)
											.addComponent(lblTotalPriceDisplay))
										.addGap(13))
							);
						panelOrderCenter_South.setLayout(gl_panelOrderCenter_South);
					panelOrderCenter.add(panelOrderCenter_South, BorderLayout.SOUTH);
				panelOrder.add(panelOrderCenter, BorderLayout.CENTER);
			tabbedPane.addTab("Item Cart", null, panelOrder, null);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
	}
	
	//	Method to load Item List of an Order
	private void reloadOrderedItemList()
	{
		tableOrderItem.repaint();
		tableOrderItem.revalidate();
		OrderController controller = new OrderController();
		totalPrice = controller.calculateTotal(orderedItems);
		lblTotalPriceDisplay.setText("RM " + String.format("%.2f", totalPrice));
	}
	
	//Button Events		
	private class AddItemButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			AddItemToCartView addItemView = new AddItemToCartView();
			addItemView.setVisible(true);
			if(addItemView.isSelected() && !addItemView.isEdit())
			{
				OrderedItem newOrderedItem = new OrderedItem();
				newOrderedItem = addItemView.getOrderedItem();
				
				// Check if the Item is already in the cart
				try {
					OrderedItemController orderedItemController = new OrderedItemController();
					orderedItemController.checkDuplicateItem(newOrderedItem, orderedItems);
				} catch (InputException exception) {
					exception.displayMessage();
					return;
				}
				
				// Add the item into cart
				orderedItems.add(newOrderedItem);
				
				reloadOrderedItemList();
			}
		}
	}
	
	private class EditItemButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
	    	int index = tableOrderItem.getSelectedRow();
	    	if(index < 0)
	    		return;
	    	
	    	OrderedItem orderedItem = orderedItems.get(index);
	    	    	
			AddItemToCartView editAmountDialog = new AddItemToCartView(orderedItem);
			editAmountDialog.setVisible(true);
			
			if(editAmountDialog.isSelected())
			{
				orderedItem = editAmountDialog.getOrderedItem();
				orderedItems.set(index, orderedItem);
				reloadOrderedItemList();
			}
		}
	}
	
	private class RemoveItemButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int index = tableOrderItem.getSelectedRow();
	    	if(index < 0)
	    		return;
	    	
	    	OptionPaneView opView = new OptionPaneView("Remove Item");
	    	if(opView.getResult()) 
	    	{
	    		orderedItems.remove(index);
	    		reloadOrderedItemList();
	    	}	    	
		}
	}
	
	// Method to reset order cancel the order
	private class ResetOrderButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
	    	OptionPaneView opView = new OptionPaneView("Reset Transaction");
	    	if(opView.getResult()) 
	    	{
	    		orderedItems.clear();
	    		reloadOrderedItemList();
	    	}	    	
		}
	}
	
	// Method to checkout the order and send to server
	private class CheckoutOrderButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
	    	OptionPaneView opView = new OptionPaneView("Checkout Order");
	    	if(opView.getResult()) 
	    	{

	    		OrderController orderController = new OrderController();
	    		try {
	    			orderController.validateorder(orderedItems);
	    			
	    			// Create new order
	    			Order clientOrder = orderController.setOrder(orderedItems, totalPrice);
	    			
	    			// Send order to server
	    			ClientApplication.sendOrder(clientOrder);
	    			
	    			// Clear the order and reload the item list
	    			orderedItems.clear();
	    			reloadOrderedItemList();
	    			
	    		} catch(InputException exception) {
				
	    			exception.displayMessage();
				}
	    	}
		}
	}
	
	//Table Models
	private class OrderItemTableModel implements TableModel 
	{
	   
	   String header[] = new String[] {"Item Name", "Item Code", "Unit Price (RM)", "Amount", "Sub Total (RM)"};
	    
	   public int getColumnCount() 
	   {
	      return header.length;
	   }
	   
	   public int getRowCount() 
	   {
	      return orderedItems.size();
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
		   Item item = orderedItems.get(rowIndex).getItem();
		   switch(columnIndex)
		   {
		   	case 0: return item.getName();
		   	case 1: return item.getCode();
		   	case 2: return String.format("%.2f", item.getPrice());
		   	case 3: return orderedItems.get(rowIndex).getQuantity();
		   	case 4: return String.format("%.2f", orderedItems.get(rowIndex).getSubTotal());
		   }
	        return "";
	    }    
	    
	    public boolean isCellEditable(int rowIndex, int columnIndex) 
	    {
	        return false;
	    }
	    
	    public void setValueAt(Object value, int row, int column) {}

	    public void addTableModelListener(TableModelListener l) {}

	    public void removeTableModelListener(TableModelListener l) {}  
	}
}
