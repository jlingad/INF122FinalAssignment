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
 * for what needs to be implement client-side to allow for a connection to a server. 
 * @author jefmark
 *
 */
public class TestClient
{
	public static void main(String[] args) throws UnknownHostException, IOException 
	{
//		Thread t1 = new Thread();
//		t1.start();
//		t1.join();
		try
		{
			// TODO: need to figure out how to establish communiation between clients. The ClientCommunication
			// objects are currently independent, so they don't know of each other. Do we need to create a 
			// GameInstance object that simply holds the two objects? So first the client will be placed in the
			// queue of clients that aren't in a game. Then, after communicating with the server what exactly
			// it wants to play and who to play it with, it will move the clients from the server's queue of
			// users to a queue of GameInstance objects that have both sockets ready for communication. 
//			Socket socket = new Socket("192.168.2.12", 60101);
			
			
			InetAddress hostName = InetAddress.getLocalHost();
						
			Socket socket = new Socket(hostName, 60101);

			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter output = new PrintWriter(socket.getOutputStream());
			
			System.out.println("Established connection with the server: " + socket.toString());
			
			String serverMessage = "";
//			Scanner userMessage = new Scanner(System.in);
			
//			while((serverMessage = input.readLine()) != null)
//			while( !serverMessage.equals("exit") )
			while( (serverMessage = input.readLine()) != null)
			{
				System.out.println("Message from server: " + serverMessage);

//				System.out.print("Message to server: ");
//				serverMessage = userMessage.nextLine();
				
//				output.println(serverMessage);
//				output.flush();
				
//				System.out.println("Message sent.");				
			}
//			userMessage.close();
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
}
