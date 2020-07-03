/**
 * 
 */
package view;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import model.Item;
import model.Order;

/**
 * The client application 
 * @author User
 *
 */
public class ClientApplication extends ClientView {

	/**
	 * @param args
	 */
	/*
	 * Start the client application
	 */
	// Main method of the client application 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SetConnectionView setConnectionView = new SetConnectionView();
		setConnectionView.setVisible(true);
		
		if(setConnectionView.isSet()) {
			ClientApplication clientFrame = new ClientApplication();
			clientFrame.setHostAddress(setConnectionView.getHostAddress());
			clientFrame.setPortNumber(setConnectionView.getPortNumber());
			clientFrame.setVisible(true);
		}
	}

	// Data Attributes
	private static String hostAddress;
	private static int portNumber;
	
	// Setters
	public void setHostAddress(String hostAddress) {
		ClientApplication.hostAddress = hostAddress;
	}
	
	public void setPortNumber(int portNumber) {
		ClientApplication.portNumber = portNumber;
	}
	// Constructor
	public ClientApplication() {
		super();
	}
		
	public static ArrayList<Item> receiveItemList() {
		//	Declare a Socket
		Socket socket = null;
		
		try {
			// Create a socket with the host address and port, then start the connection
			socket = new Socket(hostAddress, portNumber);
			System.out.println("Connection estabished.");	//	Message to indicate server was connected
			
			//Open an input stream and output stream to the socket		
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

			//	Write the item list request and send to server.			
			out.writeObject("Request item list");
			System.out.println("Item list request sent to server.");	//	Message to indicate the object was sent to server successfully
			
			//	Read item list received from server
			ArrayList<Item> itemList = (ArrayList<Item>) in.readObject();
			System.out.println("Item list received from server.");	//	Message to indicate the result was replied from the server
			
			return itemList;
						
		} catch (Exception e) {
			
			System.out.println("Error: Problem occured.");
			e.printStackTrace();
			return null;

		} finally {
			try {
				// Close the socket
				if (socket != null)
					socket.close();
			
			} catch (Exception e) {
				
				System.out.println("Error: Problem occured.");
				e.printStackTrace();
			}
			
			System.out.println("End of item list request");	//	Message to indicate the client request ends
		}
	}
	
	public static void sendOrder(Order order) {
		//	Declare a Socket
		Socket socket = null;
		
		try {
			// Create a socket with the host address and port, then start the connection
			socket = new Socket(hostAddress, portNumber);
			System.out.println("Connection estabished.");	//	Message to indicate server was connected
			
			//Open an input stream and output stream to the socket		
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

			//	Write the item list request and send to server.			
			out.writeObject(order);
			System.out.println("New order sent to server.");	//	Message to indicate the object was sent to server successfully							

		} catch (Exception e) {
			
			System.out.println("Error: Problem occured.");
			e.printStackTrace();

		} finally {
			try {
				// Close the socket
				if (socket != null)
					socket.close();
			
			} catch (Exception e) {
				
				System.out.println("Error: Problem occured.");
				e.printStackTrace();
			}
			
			System.out.println("Order transmission ends");	//	Message to indicate the client request ends
		}	
	}
}
