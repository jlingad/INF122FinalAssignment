package server;

import database.SQLiteJDBC;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/**
 * Class that is going to be responsible for handling client connections by
 * taking their sockets and putting them into a joined room where the object
 * can handle communication between the sockets. 
 * 
 * This should be running separately in the background. So maybe this should
 * extend Thread. By doing that, this can check the different connections, what
 * kind of games they are going to be playing, and then match clients up based
 * on what game they decided to play. 
 * @author jefmark
 *
 */
public class ServerEngine extends Thread
{
//	private ArmaGRIDdonServer server;
	private List<GameRoom> tictactoeInProgress;
	private List<GameRoom> checkersInProgress;
	private List<GameRoom> matchInProgress;
	private List<ClientConnection> connectedClients;
	private SQLiteJDBC db;
	// TODO: needs to add a game factory object instance here
	
//	public ServerEngine(ArmaGRIDdonServer server)
	public ServerEngine()
	{
		
		System.out.println("ServerEngine::ServerEngine");
//		this.server = server;
		connectedClients = new ArrayList<ClientConnection>();
		tictactoeInProgress = new ArrayList<GameRoom>();
		checkersInProgress = new ArrayList<GameRoom>();
		matchInProgress = new ArrayList<GameRoom>();
		db = new SQLiteJDBC();
	}
	
	/**
	 * This is the Thread::run() method that is going to be overriden by the class.
	 * It allows the engine to be run independently of the server, so the server 
	 * can continue to listen for connections, but this can continue to drive
	 * the program execution. 
	 */
	public void run()
	{
		while(true)
		{
//				System.out.println("ServerEngine::run()");		
			try
			{
//				connectedClients = server.getConnectedClients();
				synchronized(this) { this.wait(); }
				if(connectedClients.size() > 1) // If we have at least 2 people connected ...
				{
					System.out.println("ServerEngine::run(), connectedClients.size() = " + connectedClients.size());
					GameRoom gameInstance = new GameRoom(connectedClients.get(0), connectedClients.get(1));
					System.out.println("ServerEngine Message:::Added clients to the server.");
					gameInstance.start();
					System.out.println("ServerEngine Message:::gameInstance now on new thread: " + gameInstance.getId());
//					break; // TODO: take out once testing with more than two clients. Should continue to add clients to a room
				}
			}
			catch(Exception e)
			{
				System.out.println("ServerEngine::run() error thrown: " + e.getMessage());
			}
		}
	}
	
	public void logUserIn(String name)
	{
		if( db.insertPlayer(name) )
			System.out.println("New user added into server.");
		else
			System.out.println("User already exists, logging in as user now.");
	}
	
	public void addUser(ClientConnection client, GameNames game)
	{
		switch(game)
		{
			case TIC_TAC_TOE:
				// Add to tic tac toe waiting room or game
				if(tictactoeInProgress.size() % 2 == 0) // No game rooms available
					tictactoeInProgress.add(new GameRoom(client));
				else
					tictactoeInProgress.get(tictactoeInProgress.size()-1).addOpponent(client);
				break;
			case CHECKERS:
				// Add to checkers waiting room or game
				if(checkersInProgress.size() % 2 == 0)
					checkersInProgress.add(new GameRoom(client));
				else
					checkersInProgress.get(checkersInProgress.size()-1).addOpponent(client);
				break;
			case MATCH:
				// Add to match waiting room or game
				if(matchInProgress.size() % 2 == 0)
					matchInProgress.add(new GameRoom(client));
				else
					matchInProgress.get(matchInProgress.size()-1).addOpponent(client);
				break;
		}
	}
	
	public void handleClient(Socket socket)
	{
		connectedClients.add(new ClientConnection(socket, this));
	}
}
