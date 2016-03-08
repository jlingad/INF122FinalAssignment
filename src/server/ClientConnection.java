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
//	private static final int USER_POLITNESS = 200;  // TODO: uhh.. What is this supposed to do exactly?
	private Socket socket; 							// Used to save the socket that the user is connected to
	private boolean connected;						// State of the user if user is currently connected to the server
	private CommunicationPort commport;				// Nested class that deals with the 
	
	// TODO: create input and output buffers so that communication can happen
	// private inputstream private outputstream
	// Choose proper streams based on how we decide to implement communication protocal between
	// client to server and server to client.
	
	
	public ClientConnection(Socket newSocket)
	{
		System.out.println("ClientConnection object established.");
		socket = newSocket;
		connected = true;
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
	
	public boolean isConnected()
	{
		return connected;
	}
	
	/**
	 * Simple nested class that allows for the multithreading aspect of the server. 
	 * @author jefmark
	 *
	 */
	private class CommunicationPort extends Thread
	{
		// TODO: need to add methods to allow for communication
		private BufferedReader input;
		private PrintWriter output;
//		private OutputObjectReader out;
		
		public void run()
		{
			try
			{
				input  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				output = new PrintWriter(socket.getOutputStream(), true);
				
				System.out.println("Using thread #" + Thread.currentThread().getId());	// Tests to see if it's using threads
				System.out.println(socket + " has established input and output communication ports.");
				
				String clientMessage = "";
				while( (clientMessage = input.readLine()) != null )
				{
					System.out.println(socket + ": " + clientMessage);
					// TODO: the server will reply after reading it. Right now, it's just echoing what the Client says
					// output.write(socket.toString());
					// output.flush(); // Sends message
				}
			}
			catch(Exception e)
			{
				System.out.println("Cient could not establish I/O with server.");
				return;
			}
			
			
			
			// Is this while loop supposed to be for listening to the server?
			// Taken from a Java tutorial
//			while(true)
//			{
//				try
//				{
//					Thread.sleep(USER_POLITNESS);
//				}
//				catch(Exception e)
//				{
//					System.out.println(toString() + " has been interrupted.");
//				}
//			}
		}
	}
}
