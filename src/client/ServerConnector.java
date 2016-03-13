package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


public class ServerConnector  {

	/**
	 *	Created by Nikita on 3/10/2016 
	 *
	 * 	This class is responsible for making the initial handshake to the Server
	 * 	and thereafter managing all communication between the Client and Server
	 * 
	 * 	One ServerConnector per Client
	 */
	
	//needs to implement Protocol, which is in a different package.
	//Need to import somehow.
	
	private Socket socket; 
	private boolean connected;
	private shared.Protocol protocol;
	
	
	public static void main(String[] args)
	{
//	    try {
//	        /** Obtain an address object of the server */
//	        InetAddress address = InetAddress.getByName(host);
//	        /** Establish a socket connetion */
//	        Socket connection = new Socket(address, port);
//	        /** Instantiate a BufferedOutputStream object */
//	    }
//	    catch ( ne)
//	    {}
	}
}
