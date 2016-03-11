package server;

import java.util.Scanner;

public class GameRoom extends Thread implements Playable
{
	private ClientConnection hostClient;
	private ClientConnection guestClient;
//	private GameState gameState;
	
	public GameRoom(ClientConnection hostClient, ClientConnection guestClient)
	{
		System.out.println("Creating game room...");
		this.hostClient  = hostClient;
		this.guestClient = guestClient;
		hostClient.setAsInGame();
		guestClient.setAsInGame();
		
		System.out.println("Game room has been established.");
		System.out.println("Host Client: " + this.hostClient.socket.getPort());
		System.out.println("Guest Client: " + this.guestClient.socket.getPort());
	}
	
	public void run()
	{
		try
		{
			hostClient.getOutputPort().println("You are the host client, you are connected to a game room.");
			hostClient.getOutputPort().flush();
			guestClient.getOutputPort().println("You are the guest client, you are connected to a game room.");
			guestClient.getOutputPort().flush();
			
			String messageToForward = "";
			Scanner message = new Scanner(System.in);
			while( !(messageToForward = message.nextLine()).equals("quit"))
			{
//				messageToForward = message.nextLine();
//				messageToForward = hostClient.getInputPort().readLine();
				guestClient.getOutputPort().println(messageToForward);
				guestClient.getOutputPort().flush();
				System.out.println("HostClient: " + messageToForward);
//				messageToForward = guestClient.getInputPort().readLine();
				hostClient.getOutputPort().println(messageToForward);
				hostClient.getOutputPort().flush();
				System.out.println("GuestClient: " + messageToForward);
				// TODO: when to break out of loop
			}
			message.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
