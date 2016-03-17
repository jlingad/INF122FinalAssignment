package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import GUI.ArmagriddonGUI;
import server.GameNames;
import shared.ExecutionState;


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


	private String name;
	private Integer player1OrPlayer2;
	private shared.ExecutionState executionState;
	private GUI.ArmagriddonGUI armagriddonGUI;
	private state.ServerState serverState;
	private server.GameState gameState;	//to keep track of if we'd need to draw something

	//change to input object stream and output object stream
	
	
	private static BufferedReader input;
	private static PrintWriter output;

	public static void main(String[] args) throws UnknownHostException, IOException 
	{
		ArmagriddonGUI gui = new ArmagriddonGUI(ExecutionState.LOGIN);
		try
		{
			
			Scanner userMessage = new Scanner(System.in);
			InetAddress hostName = InetAddress.getLocalHost();	
			Socket socket = new Socket(hostName, 60101);
			System.out.println("Established connection with the server: " + socket.toString());

			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			output.flush();

			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			
			System.out.println("Log-in Attempt: " + input.readBoolean());
			
			System.out.print("User name to use: ");
			output.writeObject(userMessage.nextLine());
			output.flush();

			System.out.print("Game to play: [0]-TicTacToe, [1]-Checkers, [2]-Match ");
			switch(userMessage.nextLine())
			{
				case "0":
					output.writeObject(GameNames.TIC_TAC_TOE);
					output.flush();
					break;
				case "1":
					output.writeObject(GameNames.CHECKERS);
					output.flush();
					break;
				case "2":
					output.writeObject(GameNames.MATCH);
					output.flush();
					break;
			}
			
			userMessage.close();
			socket.close();
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	public void getUsername()
	{
		name = armagriddonGUI.getLoginPanel().getUsername();
		//relies on a public loginPanel in armagriddonGUI
		//or, alternatively, a getter method for the loginPanel in
		//armagriddonGUI
	}
	
	public void connectToServer() throws IOException
	{
		//Was originally going to handle the server connection here,
		//to free up the main method for other functions as well
	}
	
	/**
	 * Moves logic:
	 * 
	 * To get info about grid coordinates: GameState has a grid[] array of JPanels
	 * in the GUI, hovering over a grid will tell which index in the array it is
	 * going from 0 at the top left to 'n-1' at the bottom right
	 * 
	 * making a move would be different for the different games:
	 * 	- Tic-Tac-Toe: 1 click only (clicking on the place you want to put your piece)
	 * 		- Invalid if that square is already taken
	 * 
	 * 	- Checkers: 2 clicks: 1 for selecting the piece, 1 for moving the piece
	 * 		- Invalid if:
	 * 			- You have selected a spot not occupied by one of your pieces
	 * 			- You intend to move to an occupied spot
	 * 		- Server should note the invalidity of both options, even if the move
	 * 		  has not yet been done (ex. intermediate step of selecting an invalid 
	 * 		  piece)
	 *		- Potentially more clicks if the player can jump multiple times, and 
	 *		  ALL moves must be checked for validity
	 *
	 * 	 - Memory Match: 2 clicks: 1 for each tile flipped
	 * 		- Invalid if one of the tiles selected has already been flipped or matched
	 * 
	 *	 - ALL GAMES: moves invalid if someone has already won
	 * 
	 */
	
	public void sendMoveToServer()
	{
		//TODO: the parameter for this method should be the click input (get from GameState)
	}

}
