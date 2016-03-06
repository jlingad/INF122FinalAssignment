package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection
{
	private static final int USER_POLITNESS = 200;  // TODO: uhh.. What is this supposed to do exactly?
	private Socket socket; 							// Used to save the socket that the user is connected to
	private boolean connected;						// State of the user if user is currently connected to the server
	private CommunicationPort commport;				// Nested class that deals with the 
	
	// TODO: create input and output buffers so that communication can happen
	// private inputstream private outputstream
	// Choose proper streams based on how we decide to implement communication protocal between
	// client to server and server to client.
	
	
	public ClientConnection(Socket newSocket)
	{
		socket = newSocket;
		connected = true;
		commport = new CommunicationPort();
		commport.start();
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
		private ObjectInputStream in;
		private ObjectOutputStream out;
		
		public void run()
		{
			try
			{
				in  = new ObjectInputStream(socket.getInputStream());
				out = new ObjectOutputStream(socket.getOutputStream());
				System.out.println(socket + " has established input and output communication ports.");
			}
			catch(Exception e)
			{
				System.out.println("Cient could not establish I/O with server.");
				return;
			}
			
			// Is this while loop supposed to be for listening to the server?
			// Taken from a Java tutorial
			while(true)
			{
				try
				{
					Thread.sleep(USER_POLITNESS);
				}
				catch(Exception e)
				{
					System.out.println(toString() + " has been interrupted.");
				}
			}
		}
	}
}
