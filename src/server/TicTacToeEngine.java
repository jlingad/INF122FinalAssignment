package server;

public class TicTacToeEngine implements Playable {
	TicTacToeLogic logic;
	TicTacToeState state;
	
	public TicTacToeEngine(TicTacToeLogic logic, TicTacToeState state) {
		this.logic = logic;
		this.state = state;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}