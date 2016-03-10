package client;

import java.net.Socket;


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
	
}
