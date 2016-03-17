package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import shared.Protocol;

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
	public Socket socket; 
	private boolean connected;						// State of the user if user is currently connected to the server
	private CommunicationPort commport;				// Nested class that deals with the 
	private boolean inGameRoom;
	private String clientName; 					    // Client name that is going to be logged into the database
	private ServerEngine engine;
	private GameNames nameOfGame;
	public ArrayList<Protocol> incommingMessages;
	public ArrayList<Protocol> outgoingMessages;
	public ClientConnection(Socket newSocket, ServerEngine engine)
	{
		incommingMessages = new ArrayList<Protocol>();
		outgoingMessages = new ArrayList<Protocol>();
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
	
	public GameNames getGameName() {
		return nameOfGame;
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
	
	public void sendMessage() throws IOException
	{
		commport.output.writeObject("Client: " + socket.getLocalPort() + " thread: " + commport.getId());
		commport.output.flush();
	}
	
	public ObjectOutputStream getOutputPort()
	{
		return commport.output;
	}

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
	
	
	public void queMessage(Protocol p) {
		outgoingMessages.add(p);
	}
	
	/**
	 * Simple nested class that allows for the multithreading aspect of the server. 
	 * @author jefmark
	 *
	 */
	private class CommunicationPort extends Thread
	{
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
				System.out.println(input.available());
				clientName = (String) input.readObject(); // input.readLine();
				
				// Query database for the name 
				engine.logUserIn(clientName);

				output.writeBoolean(true); // user has logged in, queue the client for next piece of info
				output.flush();
				
				// Get name of game they want to play
				nameOfGame = (GameNames) input.readObject(); // GameNames.valueOf(input.readLine());

				// Add to [specific game] queue or to a new game
				engine.addUser(client, nameOfGame);
				
				while(true) {
					if (outgoingMessages.isEmpty()) {
						System.out.println("outgoing messages is empty");
					}
					if (!outgoingMessages.isEmpty())
					{
						System.out.println("outgoing messages is not empty");
						for (Protocol p : outgoingMessages) {
							output.writeObject(p);
							output.flush();
						}
						outgoingMessages.clear();
					}
					System.out.println("in client connection loop");
					//incommingMessages.add((Protocol) input.readObject());
					//System.out.println("in client connection loop after read");
					Thread.sleep(500);
				}

				//this.join(); // Joined so that it does not waste threads, commented out for message implementation reasons
			}
			catch(Exception e)
			{
				System.out.println("Cient could not establish rules with server.");
				System.err.println(e.getClass() + ": " + e.getMessage());
			}
		}
	}
}
