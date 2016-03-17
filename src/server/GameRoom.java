package server;

import java.io.IOException;
import shared.MessageType;
import shared.Protocol;

public class GameRoom extends Thread 
{
	private ClientConnection hostClient;
	private ClientConnection guestClient;
	private boolean gameInProgress;

	/**
	 * Might be able to delete this now. We never have an instance where we add two clients already. 
	 * Was just used for testing. 
	 * @param hostClient
	 * @param guestClient
	 */
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
	
	public GameRoom(ClientConnection hostClient) throws IOException
	{
		this.hostClient = hostClient;
		this.guestClient = null;
		hostClient.setAsInGame();
		
		hostClient.getOutputPort().writeObject(new Protocol(MessageType.WAITING, null, null, null));
		hostClient.getOutputPort().flush();
		
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
			
//			hostClient.outgoingMessages.add(new Protocol(MessageType.READYTOPLAY,
//					null, null, null));
			hostClient.getOutputPort().writeObject(new Protocol(MessageType.READYTOPLAY, null, null, null));
			hostClient.getOutputPort().flush();
			System.out.println("QUEUED TO HOST: READY TO PLAY");
			
//			guestClient.outgoingMessages.add(new Protocol(MessageType.READYTOPLAY,
//					null, null, null));
			guestClient.getOutputPort().writeObject(new Protocol(MessageType.READYTOPLAY, null, null, null));
			hostClient.getOutputPort().flush();
			System.out.println("QUEUED TO GUEST: READY TO PLAY");
			
//			System.out.println("IS OUTGOINGMESSAGES EMPTY?");
//			System.out.println(hostClient.outgoingMessages.isEmpty());
			//SERVER SIDE IMPLEMENTATION OF GAME NOT IMPLEMENTED FULLY
//			GameNames nameOfGame = hostClient.getGameName();
//			PlayableFactory playableFactory = new PlayableFactory();
//			Playable theGame;
//			
//			if(nameOfGame == GameNames.CHECKERS)
//				theGame = playableFactory.createPlayable("Checkers");
//			else if(nameOfGame == GameNames.TIC_TAC_TOE)
//				theGame = playableFactory.createPlayable("TicTacToe");
//			else if(nameOfGame == GameNames.MATCH)
//				theGame = playableFactory.createPlayable("Match");
//			
//			boolean gameInProgress = true;
			
			//Main server side game loop
			while(gameInProgress)
			{
//				System.out.println("in main server side loop");
				if (!hostClient.incomingMessages.isEmpty()) {
					System.out.println("hostClient.incommingMessages is not empty");
					for (shared.Protocol p : hostClient.incomingMessages) {
						if (p.getMessageType() == MessageType.MOVE) {
							guestClient.outgoingMessages.add(p);
						}
					}
					hostClient.incomingMessages.clear();
				}
				if (!guestClient.incomingMessages.isEmpty()) {
					System.out.println("guestClient.incommingMessages is not empty");
					for (shared.Protocol p : guestClient.incomingMessages) {
						if (p.getMessageType() == MessageType.MOVE) {
							hostClient.outgoingMessages.add(p);
						}
					}
					guestClient.incomingMessages.clear();
				}
				
				Thread.sleep(500);
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Adds an opponent to the GameRoom. A host client is going to already establish the
	 * game room when they first connect, the client doesn't get to pick who they want to 
	 * play against. They just automatically get matched up against them.
	 * @param guest
	 */
	public void addOpponent(ClientConnection guest)
	{
		this.guestClient = guest;
		guestClient.setAsInGame();
		this.gameInProgress = true;
		System.out.println("Client has been matched with opponent. Update views now.");		
	}
	
	/**
	 * Returns whether the game is in progress or not, used for disconnecting users, but makes sure 
	 * they aren't in a game currently under way.
	 * @return
	 */
	public boolean gameInProgress()
	{
		return this.gameInProgress;
	}
	
	public void updatePlayerProfiles()
	{
		// TODO: Need to add who won and who list
	}
}
