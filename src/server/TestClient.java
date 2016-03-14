package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Used to test the connection to the server. Will be used to pass a message forward to the server 
 * and a message back to the client, printing results to the console. 
 * 
 * To test multithreaded capabilities of the server, run two terminal consoles and run
 * two different instances of the TestClient class. 
 * 
 * Even though this is just to test the connection to the server and to make sure that the
 * server is capable of handling multiple clients, it can ultimately become skelton code
 * for what needs to be implemented client-side to allow for a connection to a server. 
 * @author jefmark
 *
 */
public class TestClient
{
	public static void main(String[] args) throws UnknownHostException, IOException 
	{
		try
		{
			Scanner userMessage = new Scanner(System.in);
			InetAddress hostName = InetAddress.getLocalHost();	
			Socket socket = new Socket(hostName, 60101);
			System.out.println("Established connection with the server: " + socket.toString());

			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			output.flush();

			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			
			System.out.println("Log-in Attempt: " + input.readBoolean());
			
			System.out.print("User name to use: ");
			output.writeObject(userMessage.nextLine());
			output.flush();

			System.out.print("Game to play: [0]-TicTacToe, [1]-Checkers, [2]-Match ");
			switch(userMessage.nextLine())
			{
				case "0":
					output.writeObject(GameNames.TIC_TAC_TOE);
					output.flush();
					break;
				case "1":
					output.writeObject(GameNames.CHECKERS);
					output.flush();
					break;
				case "2":
					output.writeObject(GameNames.MATCH);
					output.flush();
					break;
			}
			
			userMessage.close();
			socket.close();
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
}
