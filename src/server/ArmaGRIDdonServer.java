package server;

/*
 * The server itself is multithreaded. Don't worry about the ClientConnection object being multithreaded.
 * Once a new connection comes into the server, call run() that implements the Runnable interface's run
 * method. This run method should:
 * 		1. Allow the server to continue listening by moving the connected client to another thread
 * 		2. Create a ClientConnection object and place it in the list of connected clients
 * 	
 * TODO: add methods for the client-side to use so that it can request information from the server
 * 			- how many people are online and what are their names? 
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main server class. Initializes the server to accept connections from clients.
 * Once a client has established a connection, the server creates an object that
 * holds the information of the client and its connection to the server. This 
 * helps to establish connections between users. 
 * 
 * Once a new ClientConnection object is created and added into the list, the
 * object is then assigned into a parallel thread through the start command found
 * in the constructor of the ClientConnection object.
 * 
 * TODO: need to add a close connection method
 * TODO: maybe add the functionality so that if the server has to close all of a
 * 			sudden, go through the list of users that are still connected and
 * 			send them a server disconnect message.
 * TODO: to close connection, the server should be given a message from the client? 
 * 			Not really sure what is going to trigger shutting down the server.
 * @author jefmark
 */
public class ArmaGRIDdonServer extends Thread
{
	private static final int SERVER_PORT = 60101; // Fixed value server port: [6] = Team 6; [0101] Binary
	private ServerSocket serverSocket;			  // Object to hold the socket that clients are going to connect to
	private Socket socket;						  // Accepted client connections are going to be saved into this socket object
	private List<ClientConnection> clientList; 	  // List of all clients that are currently connected to the server
	private InetAddress hostAddress;			  // Should be localhost for our purpose
	private static final int ROOM_THROTTLE = 200; // TODO: Not really sure what to set this as because I don't know how Thread.sleep(ROOM_THROTTLE) is using this
	
	/**
	 * 1. Establishes that we are going to use localhost as the host address
	 * 2. Establishes the ServerSocket so that the server can begin listening to clients
	 * 		- Users are going to be connecting to port 60101 through the localhost address
	 * TODO: remove print statements. They're there to show that the server is being established properly
	 */
	public ArmaGRIDdonServer()
	{
		clientList = new ArrayList<ClientConnection>();
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
	}
	
	/**
	 * The main loop of execution. A while(true) loop so that the server can continue to listen and accept
	 * connections from multiple clients. Once a client has connected to the serverSocket, we save the
	 * client's connection into a new socket. We pass this socket into a new client connection object and
	 * add it into the list of client connections. 
	 * 
	 * In the constructor of the ClientConnection object, the process is then continue onto a separate thread
	 * so that the server can continue listening. Maybe this is why we say Thread.sleep()? So that the server
	 * can sleep the Thread that was being held so that it can continue listening. 
	 * 
	 * TODO: need to remove the print statements. They're there to help make sure execution is happening as expected
	 * TODO: need to figure out what Thread.sleep(ROOM_THROTTLE) really does. 
	 */
	public void run()
	{
		System.out.println("Waiting lobby has been created.");
		
		while(true)
		{
			flushDisconnectedUsers(); // TODO: fix this in the ClientConnection object, the disconnect isn't happening.
			
			try
			{
				socket = serverSocket.accept();
				System.out.println("Client " + socket + " has connected.");
				
				clientList.add(new ClientConnection(socket));
//				Thread.sleep(ROOM_THROTTLE);
				System.out.println("# of clients connected: " + clientList.size());
			}
			catch(IOException e)
			{
				System.out.println("Could not get client");
			}
//			catch(InterruptedException e)
//			{
//				System.out.println("Lobby has been interrupted.");
//			}
		}
	}
	
	/**
	 * Goes through the list of connected users and removes any that have already disconnected.
	 * When a client disconnects from the server, the server currently doesn't know that. This
	 * might be something that could be fixed later so that if the client disconnects, we know
	 * to remove them from the list, but we leave this in the list since we might not catch the
	 * prematurely disconnected clients. 
	 * 
	 * This could be left as an accessory method. 
	 * 
	 */
	private void flushDisconnectedUsers()
	{
		for(int i = 0; i < clientList.size(); ++ i)
		{
			if( !clientList.get(i).isConnected() )
			{
				System.out.println(clientList.get(i) + " removed due to lack of connection.");
				clientList.remove(i);
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
