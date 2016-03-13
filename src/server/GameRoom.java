package server;

public class GameRoom extends Thread 
{
	private ClientConnection hostClient;
	private ClientConnection guestClient;
	private boolean gameInProgress;
//	private GameState gameState;
	// Needs to notify engine of an ended game -- if gameInProgress is false, then the game has ended
	public GameRoom(ClientConnection hostClient, ClientConnection guestClient)
	{
		System.out.println("Creating game room...");
		this.hostClient  = hostClient;
		this.guestClient = guestClient;
		hostClient.setAsInGame();
		guestClient.setAsInGame();
		
		gameInProgress = true;
		
		System.out.println("Game room has been established.");
		System.out.println("Host Client: " + this.hostClient.socket.getPort());
		System.out.println("Guest Client: " + this.guestClient.socket.getPort());
	}
	
	public GameRoom(ClientConnection hostClient)
	{
		this.hostClient = hostClient;
		this.guestClient = null;
		hostClient.setAsInGame();
		System.out.println("Game room has been established. Waiting for second client to match.");
	}
	
	/**
	 * Will probably be run as a while loop, taking and sending information from the client
	 * How do I not set is so that the GameRoom is just waiting on the client the entire time? 
	 * What if the other client sends a command? We need to check the current state against the
	 * state that is being sent up to make sure that the correct playing is making a move in game
	 */
	public void run()
	{
		try
		{
			System.out.println("Game started on separate thread! Thread ID: " + this.getId());
			while(true)
			{
				break;
			}
			this.gameInProgress = false;
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void addOpponent(ClientConnection guest)
	{
		this.guestClient = guest;
		guestClient.setAsInGame();
		this.gameInProgress = true;
		System.out.println("Client has been matched with opponent. Update views now.");
	}
	
	public boolean gameInProgress()
	{
		return this.gameInProgress;
	}
}
