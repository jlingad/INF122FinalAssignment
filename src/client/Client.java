package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import GUI.ArmagriddonGUI;
import shared.ExecutionState;
import shared.MessageType;
import shared.Protocol;


public class Client {

	/**
	 *	Created by Nikita on 3/10/2016 
	 *
	 * 	The client needs to be able to do the following:
	 * 		- Establish a connection to the server
	 * 		- Identify which game it wants to play
	 * 		- Send information about the moves it's making when it plays a game
	 * 			- If the move is invalid, the GameState should not be updated
	 * 			- If the move IS valid, the GameState should be updated
	 * 				- Further restrictions: update the GameState for the
	 * 				  client vs. update the GameState for its opponent
	 * 				  based on the identity of who is making the move
	 * 				  (So we only have half as many draws as usual)
	 * 		- Sever a connection with the server
	 * 
	 */

	//change to input object stream and output object stream

	public static void main(String[] args) throws UnknownHostException, IOException 
	{
		ArrayList<Protocol> incommingMessages = new ArrayList<Protocol>();
		ArrayList<Protocol> outgoingMessages = new ArrayList<Protocol>();
		ArmagriddonGUI gui = new ArmagriddonGUI(ExecutionState.LOGIN, outgoingMessages);
		try
		{
			InetAddress hostName = InetAddress.getLocalHost();	
			Socket socket = new Socket(hostName, 60101);
			System.out.println("Established connection with the server: " + socket.toString());

			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			output.flush();

			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			
			System.out.println("Connected to server: " + input.readBoolean());
			
			boolean isConnected = true;
			
			String userName = gui.getLoginPanel().getUsername();
			
			System.out.println("Trying to log in as: " + userName + "...");
			
			output.writeObject(userName);
			output.flush();
			
			output.writeObject(gui.getMainMenuPanel().getChosenGame());
			output.flush();
			
			boolean connectedToGame = false;
			while (!connectedToGame) {
				System.out.println("In Connectedtogame loop");
				Protocol nextMessage = (Protocol) input.readObject();
				if (nextMessage.getMessageType() == MessageType.READYTOPLAY) {
					connectedToGame = true;
				}
			}
			System.out.println("Connectedtogame loop passed!!!!");
			//Main client loop BBBBBROKEN
			
			while(isConnected) {
				System.out.println("in main client loop");

				if (!incommingMessages.isEmpty()) {
					for (Protocol p : incommingMessages)
					{
						System.out.println("In incoming messages for");
						gui.processMessage(p);
					}
				}
				incommingMessages.clear();
				Thread.sleep(2000);
				if (!outgoingMessages.isEmpty())
				{
					for (Protocol p : outgoingMessages) {
						System.out.println("in outgoing messages for");
						output.writeObject(p);
						output.flush();
					}
					outgoingMessages.clear();
				}
				
				incommingMessages.add((Protocol) input.readObject());
				System.out.println("in main client loop after read");
				Thread.sleep(500);
			}
			
			socket.close();
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	public synchronized void sendToServer(ObjectOutputStream output)
	{
		//TODO: the parameter for this method should be the click input (get from GameState)
	}
	
	public synchronized void recieveFromServer(ObjectInputStream input)
	{
		
	}

}
