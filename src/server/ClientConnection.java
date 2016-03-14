package server;

import java.io.IOException;
//import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.io.PrintWriter;
import java.net.Socket;

// TODO: uncomment when needed for sending objects back and forth

/*
 * NOTES:
 * So does each ClientConnection object need to have a listening method that gets called
 * in the run method that basically just relays information back and forth between the 
 * client and the server? And the client needs to have a listening function that listens,
 * determines what the input means, acts based on what it means, and return something
 * back to the server.
 * 
 * ALSO: set up a handshake between the client and server. But this is later when we
 * establish what needs to happen to set up the games. 
 */

public class ClientConnection
{
//	private Socket socket; 							// Used to save the socket that the user is connected to
	public Socket socket; // TODO: change back to private, used for testing
	private boolean connected;						// State of the user if user is currently connected to the server
	private CommunicationPort commport;				// Nested class that deals with the 
	private boolean inGameRoom;
	private String clientName; 					    // Client name that is going to be logged into the database
	private ServerEngine engine;
	private GameNames nameOfGame;
	
	public ClientConnection(Socket newSocket, ServerEngine engine)
	{
		System.out.println("ClientConnection object established.");
		socket = newSocket;
		connected = true;
		inGameRoom = false;
		this.engine = engine;
		commport = new CommunicationPort(this);
		commport.start(); // This calls run() in the nested class below. -- Starts the thread
		System.out.println("After run in ClientConnection::ClientConnection");
	}
	
	/**
	 * Disconnects the current user by setting their connected state to false and
	 * closing their socket. Connected state is required because it allows for the
	 * server to determine if it should be cleared from the lobby. 
	 */
	public void disconnect()
	{
		try
		{
			commport.shutdown();
			connected = false;
			socket.close();
		}
		catch(Exception e)
		{
			System.out.println("Could not disconnect " + socket.toString());
		}
	}
	
	public void setAsInGame()
	{
		this.inGameRoom = true;
	}
	
	public boolean isInGame()
	{
		return this.inGameRoom;
	}
	
	public boolean isConnected()
	{
		return connected;
	}
	
//	public void sendMessage()
//	{
//		commport.output.println("Client: " + socket.getLocalPort() + " thread: " + commport.getId());
//		commport.output.flush();
//	}
	
	public void sendMessage() throws IOException
	{
		commport.output.writeObject("Client: " + socket.getLocalPort() + " thread: " + commport.getId());
		commport.output.flush();
	}
	
//	public PrintWriter getOutputPort()
//	{
//		return commport.output;
//	}
	
	public ObjectOutputStream getOutputPort()
	{
		return commport.output;
	}
	
//	public BufferedReader getInputPort()
//	{
//		return commport.input;
//	}

	public ObjectInputStream getInputPort()
	{
		return commport.input;
	}
	
	public void setNameGame(GameNames game)
	{
		this.nameOfGame = game;
	}
	
	public String getClientName()
	{
		return this.clientName;
	}

	
	/**
	 * Simple nested class that allows for the multithreading aspect of the server. 
	 * @author jefmark
	 *
	 */
	private class CommunicationPort extends Thread
	{
		// TODO: need to add methods to allow for communication
//		public BufferedReader input;
//		public PrintWriter   output;
		private ObjectInputStream input;
		private ObjectOutputStream output;
		private ClientConnection client;
		
		public CommunicationPort(ClientConnection host)
		{
			this.client = host;
		}
		
		public void shutdown() throws IOException
		{
			input.close();
			output.close();
		}
		
		public void run()
		{
			try
			{
				output = new ObjectOutputStream(socket.getOutputStream());
				output.flush();
				System.out.println("OutputStream flushed");
				
				input = new ObjectInputStream(socket.getInputStream());
				
				System.out.println("ClientConnection: Using thread #" + Thread.currentThread().getId());	// Tests to see if it's using threads
				System.out.println(socket + " has established input and output communication ports.");
				
				// Client connected successfully
				output.writeBoolean(true);
				output.flush();
				System.out.println("Login attempt: " + true);
				
				// Get client name
				clientName = (String) input.readObject(); // input.readLine();
				
				// Query database for the name 
				engine.logUserIn(clientName);

				output.writeBoolean(true); // user has logged in, queue the client for next piece of info
				output.flush();
				
				// Get name of game they want to play
				nameOfGame = (GameNames) input.readObject(); // GameNames.valueOf(input.readLine());
//
//				// Add to [specific game] queue or to a new game
				engine.addUser(client, nameOfGame);

				this.join(); // Joined so that it does not waste threadsN
			}
			catch(Exception e)
			{
				System.out.println("Cient could not establish rules with server.");
				System.err.println(e.getClass() + ": " + e.getMessage());
			}
		}
	}
}
