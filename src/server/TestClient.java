package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
			InetAddress hostName = InetAddress.getLocalHost();	
			Socket socket = new Socket(hostName, 60101);

			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter output = new PrintWriter(socket.getOutputStream());
			
			System.out.println("Established connection with the server: " + socket.toString());
			
			String serverMessage = "";
			Scanner userMessage = new Scanner(System.in);
			
			serverMessage = input.readLine();
			System.out.println("Message from server: " + serverMessage + ". Successfully connected to server.");
			
			System.out.print("User name to use: ");
			output.println(userMessage.nextLine());
			output.flush();
			
			serverMessage = input.readLine();
			System.out.println("Attempt to log user in: " + serverMessage);
			
			System.out.print("Game to play: [0]-TicTacToe, [1]-Checkers, [2]-Match ");
			serverMessage = userMessage.next();
			switch(serverMessage)
			{
				case "0":
					output.println(GameNames.TIC_TAC_TOE);
					output.flush();
					break;
				case "1":
					output.println(GameNames.CHECKERS);
					output.flush();
					break;
				case "2":
					output.println(GameNames.MATCH);
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
