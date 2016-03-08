package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
			// TODO: need to figure out where the client is sending its information to. Obviously it's communication
			// through the socket, but we need to figure out how it's listening. What makes it display that it received
			// a message. Need to read through socket communication. Make it work with one client first.
			Socket socket = new Socket("192.168.2.12", 60101);
			
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter output = new PrintWriter(socket.getOutputStream());
			
			System.out.println("Established connection with the server: " + socket.toString());
			
			String serverMessage = "";
			Scanner userMessage = new Scanner(System.in);
			
//			while((serverMessage = input.readLine()) != null)
			while( !serverMessage.equals("exit") )
			{
				System.out.print("Message to server: ");
				serverMessage = userMessage.nextLine();
				System.out.println();
				
				output.println(serverMessage);
				output.flush();
				
				System.out.println("Message sent.");
				
				
			}
			userMessage.close();
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
}
