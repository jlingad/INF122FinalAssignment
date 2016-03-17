package server;

public class CheckersEngine implements Playable {
	CheckersLogic logic;
	CheckersState state;


	public CheckersEngine(CheckersLogic logic, CheckersState state) {
		this.logic = logic;
		this.state = state;
	}
}
