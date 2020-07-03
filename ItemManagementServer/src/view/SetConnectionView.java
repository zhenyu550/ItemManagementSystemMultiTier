/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.Socket;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

/**
 * @author User
 *
 */
public class SetConnectionView extends JDialog {

	/**
	 * @param args
	 */
	// Data attributes
	private int portNumber;
	private boolean isSet = false;
	
	// User Interface Attributes
	private JPanel contentPanel;
		// NORTH
		private JPanel panelNorth;
			private JLabel lblTitle;
		// SOUTH
		private JPanel panelSouth;
			private JButton btnStartServer;
		// CENTER
		private JPanel panelCenter;
			private JLabel lblPort;
			private JSpinner spinnerPort;
	
	// Default Constructor
	public SetConnectionView() {
		initializeComponents();
	}
	
	// Getter 	
	public int getPortNumber() {
		
		return portNumber;
	}
	
	public boolean isSet() {
		return isSet;
	}
	
	// Method to build all User Interface Components
	public void initializeComponents() {
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 350, 150);
		getContentPane().setLayout(new BorderLayout());

		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

			// NORTH
			panelNorth = new JPanel();
			panelNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				lblTitle = new JLabel("Set Connection");
				lblTitle.setFont(new Font("Tahoma", Font.BOLD, 15));
			panelNorth.add(lblTitle);
		contentPanel.add(panelNorth, BorderLayout.NORTH);
		
			// SOUTH
			panelSouth = new JPanel();
			panelSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				btnStartServer = new JButton("Start Server");
				btnStartServer.setFont(new Font("Tahoma", Font.PLAIN, 13));
				btnStartServer.addActionListener(new ConnectButtonListener());
			panelSouth.add(btnStartServer);
		contentPanel.add(panelSouth, BorderLayout.SOUTH);
		
			// CENTER
			panelCenter = new JPanel();
			panelCenter.setLayout(null);
				// Labels		
				lblPort = new JLabel("Port Number: ");
				lblPort.setFont(new Font("Tahoma", Font.PLAIN, 12));
				lblPort.setBounds(10, 10, 80, 13);
			panelCenter.add(lblPort);			
				// Input Fields
				spinnerPort = new JSpinner();
				spinnerPort.setFont(new Font("Tahoma", Font.PLAIN, 12));
				spinnerPort.setBounds(100, 8, 210, 19);
				spinnerPort.setModel(new SpinnerNumberModel(0, 0, 65535, 1));
				
				// Lock the JSpinner Input Field to receive numbers only
				JFormattedTextField txt = ((JSpinner.NumberEditor) spinnerPort.getEditor()).getTextField();
				NumberFormatter formatter = (NumberFormatter) txt.getFormatter(); 
				DecimalFormat decimalFormat = new DecimalFormat("0"); 
				formatter.setFormat(decimalFormat); 
				formatter.setAllowsInvalid(false);		
				
			panelCenter.add(spinnerPort);
		contentPanel.add(panelCenter, BorderLayout.CENTER);
	}
	
	private class ConnectButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			try {
				portNumber = (Integer) spinnerPort.getValue();
				isSet = true;
				SetConnectionView.this.dispose();
			} catch (Exception exception) {
				// TODO Auto-generated catch block
				exception.printStackTrace();
			}
		}
		
	}
		
	// Main method for debugging purposes
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SetConnectionView setConnectionView = new SetConnectionView();
		setConnectionView.setVisible(true);
	}

}
