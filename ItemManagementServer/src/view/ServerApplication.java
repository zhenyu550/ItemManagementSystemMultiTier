/**
 * 
 */
package view;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import model.Item;
import model.Order;
import model.OrderedItem;
import controller.*;

/**
 * @author User
 *
 */
public class ServerApplication extends ServerView {

	/**
	 * @param args
	 */
	// Data Attributes 
	private int portNumber = 0;
	
	/*
	 * Start the server application
	 */
	//	Main method for server application
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Set the port of the server
		SetConnectionView setConnectionView = new SetConnectionView();
		setConnectionView.setVisible(true);

		if(setConnectionView.isSet())
		{
			ServerApplication serverFrame = new ServerApplication();
			serverFrame.setVisible(true);
			serverFrame.setPortNumber(setConnectionView.getPortNumber());
			serverFrame.startServer();

		}
	}
	
	//	Default Constructor
	public ServerApplication(){		
		super();
	}
	
	//	Method to start Server
	public void startServer(){
		
		//	Declare a Server Socket variable
		ServerSocket server = null;
		
		try {
			
			// Create server socket
			server = new ServerSocket(portNumber);
			System.out.println("Server is up and running, ready to receive any client requests.\n");	// Message to indicate server is online
			
			// Loop to keep the server running
			while(true) {
				// Accept the client request
				Socket socket = server.accept();
				System.out.println("Request received.");	// Message to indicate request was received
				
				// Open an input stream and output stream
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				
				// Read, Process and Write to the stream according to client's request
				Order clientOrder = new Order();
				boolean isOrder = false;
				
				//	Determine the client either requesting for item list, or sending order to server.
				try {
					System.out.println("Start parsing process");
					
					Object clientObject = in.readObject();
					
					//	Check if the Object received from Client is an Order, a request or a connection testing
					if(clientObject instanceof Order) {
						clientOrder = (Order) clientObject;
						System.out.println("Object data received from client");	//	Message to indicate input from client is an Order
						isOrder = true;
					} else if(clientObject instanceof String) {
						System.out.println("A Item List Request was received from client"); //	Message to indicate the input from client is a Item List Request
						
						//  Reply the Item List to Client upon request
						out.writeObject(itemList);	
						// Close the socket
						socket.close();
					    System.out.println("End of Request.\n"); //	Indicate the request ends
					}
					else {
						// Close the socket
						socket.close();
					    System.out.println("End of Request.\n"); //	Indicate the request ends

					}
										
				} catch(Exception e) {
					e.printStackTrace();
				}
				
				// Receive Order from Client and store in server and database, close connection after that
				if(isOrder){
					OrderController orderController = new OrderController();
					orderController.addOrder(clientOrder);
					reloadOrderTable("status='Processing'");
					
					// Close the socket
					socket.close();
					System.out.println("Order stored in Database.\n"); 	//	Indicate the order processing ends
				}
			}
			
		} catch (Exception e) {

			System.out.println("Error: Problem occured");
			e.printStackTrace();
		
		} finally {

			try {

				if (server != null)
					server.close();

			} catch (Exception e) {

				System.out.println("Error: Problem occured. Server failed to close.");
				e.printStackTrace();
			}
		}
	}
	
	public void setPortNumber(int port) {
		portNumber = port;
	}
	
}
