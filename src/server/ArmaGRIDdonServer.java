package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Trimmed down server. Does the job of listening for new clients,
 * accepting their connections, and then handing it off to the
 * engine to handle the rest of the eecution process. Once the
 * client has been handed off to the server, the server continues 
 * to listen for new clients waiting to connect. 
 * @author jefmark
 *
 */
public class ArmaGRIDdonServer extends Thread
{
	private static final int SERVER_PORT = 60101; // Fixed value server port: [6] = Team 6; [0101] Binary
	private ServerSocket serverSocket;			  // Object to hold the socket that clients are going to connect to
	private Socket socket;						  // Accepted client connections are going to be saved into this socket object
	private InetAddress hostAddress;			  // Should be localhost for our purpose
	private ServerEngine engine;				  // Responsible for running and continuing execution flow
	
	/**
	 * 1. Establishes that we are going to use localhost as the host address
	 * 2. Establishes the ServerSocket so that the server can begin listening to clients
	 * 		- Users are going to be connecting to port 60101 through the localhost address
	 * TODO: remove print statements. They're there to show that the server is being established properly
	 */
	public ArmaGRIDdonServer()
	{		
		engine = new ServerEngine();
		engine.start(); // Puts the engine on a new thread
		try
		{
			this.hostAddress = InetAddress.getLocalHost(); 
			System.out.println("Server host address: " + this.hostAddress);
			
			this.serverSocket = new ServerSocket(SERVER_PORT, 0, hostAddress);
			System.out.println("Socket " + serverSocket + " created.");
		}
		catch(UnknownHostException e)
		{
			System.out.println("Could not establish the host address.");
		}
		catch(IOException e)
		{
			System.out.println("Could not open server socket.");
			return;
		}
		catch(Exception e)
		{
			System.err.println(e.getClass() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Once the server starts, it just listens for clients. It then passes the client to the engine to 
	 * handle their connection and execution through 
	 */
	public void run()
	{		
		while(true)
		{			
			try
			{
				socket = serverSocket.accept();
				System.out.println("Client " + socket + " has connected.");
				
				engine.handleClient(socket);
			}
			catch(IOException e)
			{
				System.out.println("Could not get client");
			}
		}
	}
	
	/**
	 * Does a couple things:
	 * First it disconnects all clients that are still connected in the list of client objects.
	 * Optionally, they could be notified of a server crash, so that they are not waiting on a
	 * server response. 
	 * 
	 * Second, it shuts down the server.
	 */
	public void shutdownServer()
	{
		// TODO: still need to implement this
	}
	
	public static void main(String[] args)
	{
		ArmaGRIDdonServer server = new ArmaGRIDdonServer();
		server.run();
	}
}
