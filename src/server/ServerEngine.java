package server;

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
	
	
	public ServerEngine()
	{
		
	}
	
	/**
	 * This is the Thread::run() method that is going to be overriden by the class.
	 * It allows the engine to be run independently of the server, so the server 
	 * can continue to listen for connections, but this can continue to drive
	 * the program execution. 
	 */
	public void run()
	{
		
	}
}
