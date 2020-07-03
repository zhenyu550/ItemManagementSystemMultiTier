/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import controller.ItemController;
import controller.OrderController;
import controller.OrderedItemController;
import exceptions.InputException;
import model.Item;
import model.Order;
import model.OrderedItem;

/**
 * @author User
 *
 */
public class OrderDetailView extends JDialog {

	/**
	 * @param args
	 */
	//	Data Attributes
	private ArrayList<Item> itemList = new ArrayList<Item>();
	private ArrayList<Integer> amountList = new ArrayList<Integer>();	// List of Quantity of Items, to be used in OrderedItem Table
	private ArrayList<Double> subTotalList = new ArrayList<Double>();	// List of Sub Total of Items, to be used in OrderedItem Table
	private ArrayList<OrderedItem> orderItemList = new ArrayList<OrderedItem>();
	private Order order = new Order();
	private boolean isCompleted = false;
	
	//	Getter for boolean isCompleted
	public boolean IsCompleted() {
		return isCompleted;
	}

	//	User Interface Attributes
	private JPanel contentPanel;
		//NORTH
		private JPanel panelNorth;
			private JLabel lblOrderDetail;
		//CENTER
		private JSplitPane splitPane;
			//CENTER LEFT
			private JPanel panelLeft;
			//CENTER RIGHT
			private JPanel panelRight;
				//CENTER RIGHT NORTH
				private JPanel panelRightNorth;
					private JLabel lblItemList;
				//CENTER RIGHT SOUTH
				private JScrollPane scrollPane;
					private JLabel lblId;
					private JLabel lblCode;
					private JLabel lblDate;
					private JLabel lblTime;
					private JLabel lblTotalPrice;
		//SOUTH
		private JPanel panelSouth;
			private JButton btnCancel;
			private JButton btnCompleteOrder;
	private JTextField txtId;
	private JTextField txtCode;
	private JTextField txtDate;
	private JTextField txtTime;
	private JTextField txtTotalPrice;
	private JTable tableOrderItem;

	
	
	//	Default Constructor
	public OrderDetailView(Order order){
		initializeComponents();
		this.order = order;
		loadTextField();
		reloadOrderItemList();
	}
	
	//	Method to initialize all UI Components
	public void initializeComponents() {
		contentPanel = new JPanel();
		setModal(true);
		setBounds(100, 100, 800, 460);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
			//	NORTH
			panelNorth = new JPanel();
			panelNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				lblOrderDetail = new JLabel("Order Detail");
				lblOrderDetail.setFont(new Font("Tahoma", Font.BOLD, 18));
			panelNorth.add(lblOrderDetail);
		contentPanel.add(panelNorth, BorderLayout.NORTH);
			//	CENTER
			splitPane = new JSplitPane();
				//	CENTER LEFT
				panelLeft = new JPanel();
					// Add Labels
					lblId = new JLabel("ID: ");
					lblId.setFont(new Font("Tahoma", Font.PLAIN, 12));
					
					lblCode = new JLabel("Code: ");
					lblCode.setFont(new Font("Tahoma", Font.PLAIN, 12));

					lblDate = new JLabel("Date: ");
					lblDate.setFont(new Font("Tahoma", Font.PLAIN, 12));

					lblTime = new JLabel("Time: ");
					lblTime.setFont(new Font("Tahoma", Font.PLAIN, 12));

					lblTotalPrice = new JLabel("Total Price: ");
					lblTotalPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
					// Add Text Fields
					txtId = new JTextField();
					txtId.setEditable(false);
					txtId.setFont(new Font("Tahoma", Font.PLAIN, 12));
					txtId.setColumns(10);
					
					
					txtCode = new JTextField();
					txtCode.setEditable(false);
					txtCode.setFont(new Font("Tahoma", Font.PLAIN, 12));
					txtCode.setColumns(10);
					
					
					txtDate = new JTextField();
					txtDate.setEditable(false);
					txtDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
					txtDate.setColumns(10);
					
					txtTime = new JTextField();
					txtTime.setEditable(false);
					txtTime.setFont(new Font("Tahoma", Font.PLAIN, 12));
					txtTime.setColumns(10);
					 				
					
					txtTotalPrice = new JTextField();
					txtTotalPrice.setEditable(false);
					txtTotalPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
					txtTotalPrice.setColumns(10);
					
					GroupLayout gl_panelLeft = new GroupLayout(panelLeft);
					gl_panelLeft.setHorizontalGroup(
						gl_panelLeft.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panelLeft.createSequentialGroup()
								.addGap(5)
								.addGroup(gl_panelLeft.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panelLeft.createParallelGroup(Alignment.LEADING, false)
										.addComponent(lblId)
										.addComponent(lblCode, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_panelLeft.createParallelGroup(Alignment.TRAILING)
											.addComponent(lblDate, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
											.addComponent(lblTime, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)))
									.addComponent(lblTotalPrice))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_panelLeft.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panelLeft.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(txtDate, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
										.addComponent(txtCode, Alignment.LEADING)
										.addComponent(txtId, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
									.addComponent(txtTime, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
									.addComponent(txtTotalPrice, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
								.addContainerGap())
					);
					gl_panelLeft.setVerticalGroup(
						gl_panelLeft.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panelLeft.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_panelLeft.createParallelGroup(Alignment.TRAILING)
									.addComponent(lblId)
									.addComponent(txtId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(gl_panelLeft.createParallelGroup(Alignment.BASELINE)
									.addComponent(txtCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblCode))
								.addGap(18)
								.addGroup(gl_panelLeft.createParallelGroup(Alignment.BASELINE)
									.addComponent(txtDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblDate))
								.addGap(18)
								.addGroup(gl_panelLeft.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblTime)
									.addComponent(txtTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(gl_panelLeft.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblTotalPrice)
									.addComponent(txtTotalPrice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addContainerGap(89, Short.MAX_VALUE))
					);
					panelLeft.setLayout(gl_panelLeft);
			splitPane.setLeftComponent(panelLeft);

				//	CENTER RIGHT
				panelRight = new JPanel();
				panelRight.setLayout(new BorderLayout(0, 0));
					//	CENTER RIGHT NORTH
					panelRightNorth = new JPanel();
					panelRightNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
						lblItemList = new JLabel("Item List");
						lblItemList.setFont(new Font("Tahoma", Font.PLAIN, 12));
					panelRightNorth.add(lblItemList);
				panelRight.add(panelRightNorth, BorderLayout.NORTH);
					//	CENTER RIGHT CENTER
					scrollPane = new JScrollPane();
						tableOrderItem = new JTable();
						tableOrderItem.setModel(new OrderItemTableModel());
						tableOrderItem.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					scrollPane.setViewportView(tableOrderItem);
				panelRight.add(scrollPane, BorderLayout.CENTER);
			splitPane.setRightComponent(panelRight);
		contentPanel.add(splitPane, BorderLayout.CENTER);
			//	SOUTH
			panelSouth = new JPanel();
			panelSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				btnCancel = new JButton("Cancel");
				btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 13));
				btnCancel.addActionListener(new CancelButtonListener());

				btnCompleteOrder = new JButton("Complete Order");
				btnCompleteOrder.setFont(new Font("Tahoma", Font.PLAIN, 13));
				btnCompleteOrder.addActionListener(new CompleteOrderButtonListener());
			panelSouth.add(btnCancel);
			panelSouth.add(btnCompleteOrder);
		contentPanel.add(panelSouth, BorderLayout.SOUTH);
		

	}
	
	//	Method to load the Data into the text fields
	public void loadTextField()
	{
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		txtId.setText(Integer.toString(order.getId()));
		txtCode.setText(order.getCode());
		txtDate.setText(order.getDateTime().format(dateFormat));
		txtTime.setText(order.getDateTime().format(timeFormat));
		txtTotalPrice.setText(String.format("RM %.2f", order.getTotalPrice()));
	}
	
	//	Method to load Item List of an Order
	public void reloadOrderItemList()
	{
		OrderedItemController orderedItemController = new OrderedItemController();
		try {
			orderItemList = orderedItemController.selectWhere(String.format("order_id = %d", order.getId()), 0, 2147483467);
			for(int i = 0; i < orderItemList.size(); i++)
			{
				itemList.add(orderItemList.get(i).getItem());
				amountList.add(orderItemList.get(i).getQuantity());
				subTotalList.add(orderItemList.get(i).getSubTotal());
			}
			order.setOrderedItems(orderItemList);
			tableOrderItem.repaint();
			tableOrderItem.revalidate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}	
	}

	//
	private class CancelButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			OrderDetailView.this.dispose();
		}
	}
	
	//
	private class CompleteOrderButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			OrderController orderController = new OrderController();
			isCompleted = orderController.completeOrder(order);
			
			OrderDetailView.this.dispose();
			
		}
	}

	private class OrderItemTableModel implements TableModel 
	{
	   
	   String header[] = new String[] { "Item ID", "Item Name", "Item Code", "Amount", "Sub Total"};
	    
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
		   	case 3: return amountList.get(rowIndex);
		   	case 4: return String.format("%.2f", subTotalList.get(rowIndex));
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

	//	Main Method for debugging purposes
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OrderController oController = new OrderController();
		Order chosen = new Order();
		try {
			chosen = oController.selectWhere("id=4", 0, 1).get(0);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		OrderDetailView view = new OrderDetailView(chosen);
		view.setVisible(true);
		view.loadTextField();

	}
	

}
