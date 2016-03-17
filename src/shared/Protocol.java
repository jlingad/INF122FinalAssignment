package shared;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JLabel;

/* MESSAGE BUILDING STRUCTURE IS MIRRORED ON BOTH CONNECTORS
 * IN THE SENSE THAT A FACTORY IS USED.
 * 
Connectors build these messages by checking the first
part of the string, and building the message accordingly,
while storing them in an array to be processed by the respective engines.

Engines need to constantly check this array and... execute? TBD

What needs to be passed?  Depends on ExecutionState of the client....
============================================================================
Login -> Server: needs to wait for a message that contains the user name

------- CHANGE TO WAITING ROOM DEFAULT VIEWING_TICTACTOE

WaitingRoom-> Server: sends messages of names of people waiting, each time
			the user uses the dropbox to choose the game (As well as tic
			tac toe at init)
			
			Client: sends message when switches game, sends message when
					starts a new game, sends a message when joins a game
			
			Server: When someone joins a game, initialize and start game
			classes etc then send init message(One client starts waiting,
			The other starts playing)
			
------- CHANGE TO PLAYTURN_TICTACTOE/PLAYWAIT_TICTACTOE
Playing-> Server: Check victory, if victory/loss send message to change state
			      to WIN_TICTACTOE or LOSS_TICTACTOE
		  Server: Notifies a client update to gameboard, and notifies change
				  in turnstate. (client stores gameboard state so that the
				  entire state doesn't need to pass through each time)
		  Client: User tries to make a move, sends a message to server with
		  		  move information.. Object@(x,y) to (x,y)
		  Server: Sends message back if move is rejected, otherwise sends
		  		  original chain of messages above
------- change to WIN_TICTACTOE/LOSS_TICTACTOE
Dialogue-> Client: messages server when dialogue clear, waiting room starts again

====================================================================================

need another realization for connection check, server sends it and client replies.

*/

public class Protocol implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6906502335325300956L;
	
	private ExecutionState executionState;
	private String theMessage;
	private MessageType messageType;
	private ArrayList<JLabel> clickedPanels;
	
	public Protocol(MessageType messageType, String theMessage, ExecutionState executionState, ArrayList<JLabel> clickedPanels){
		this.messageType = messageType;
		this.theMessage = theMessage;
		this.executionState = executionState;
		this.clickedPanels = clickedPanels;
	}
	
	public MessageType getMessageType() {
		return messageType;
	}
	
	public String getTheMessage() {
		return theMessage;
	}
	
	public ExecutionState getExecutionState() {
		return executionState;
	}
	
	public ArrayList<JLabel> getClickedPanels() {
		return clickedPanels;
	}
}
