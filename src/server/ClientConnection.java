package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

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
	// private String clientName; 					// Might be needed later so we know who is who
	
	// TODO: create input and output buffers so that communication can happen
	// private inputstream private outputstream
	// Choose proper streams based on how we decide to implement communication protocal between
	// client to server and server to client.
	
	
	public ClientConnection(Socket newSocket)
	{
		System.out.println("ClientConnection object established.");
		socket = newSocket;
		connected = true;
		inGameRoom = false;
		commport = new CommunicationPort();
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
	
	public void sendMessage()
	{
		commport.output.println("Client: " + socket.getLocalPort() + " thread: " + commport.getId());
		commport.output.flush();
	}
	
	public PrintWriter getOutputPort()
	{
		return commport.output;
	}
	
	public BufferedReader getInputPort()
	{
		return commport.input;
	}
	
	/**
	 * Simple nested class that allows for the multithreading aspect of the server. 
	 * @author jefmark
	 *
	 */
	private class CommunicationPort extends Thread
	{
		// TODO: need to add methods to allow for communication
		public BufferedReader input;
		public PrintWriter   output;
//		private OutputObjectReader out;
		
		public void run()
		{
			try
			{
				// TODO: check to see if there's a benefit to initializing all member varibles in the run() method
				// 	instead of the constructor? Will putting it in the constructor hold the server up since it has 
				//  to create this first and then uses a new thread?
				input  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				output = new PrintWriter(socket.getOutputStream(), true);
				
				System.out.println("ClientConnection: Using thread #" + Thread.currentThread().getId());	// Tests to see if it's using threads
				System.out.println(socket + " has established input and output communication ports.");
				
//				Scanner returnMessage = new Scanner(System.in);
//				String clientMessage = "";
//				
//				// TODO: In the next iteration, should not be a while loop, should be a sequence
//				// of commands that should be executed to 'set up' the game. 
//				while( (clientMessage = input.readLine()) != null )
//				{
//					System.out.println(socket + ": " + clientMessage);
//					
//					// TODO: need to not create messages by hand, have to create a repository of 
//					// server responses based on the information being sent up from the client.
//					System.out.print("Response to client message: ");
//					clientMessage = returnMessage.nextLine();
//					
//					output.println(clientMessage);
//					output.flush();
//					
//					System.out.println("Message sent.");
//				}
//				returnMessage.close();
			}
			catch(Exception e)
			{
				System.out.println("Cient could not establish I/O with server.");
				return;
			}
		}
	}
}
