package server;

public class TicTacToeEngine implements Playable {
	TicTacToeLogic logic;
	TicTacToeState state;
	
	public TicTacToeEngine(TicTacToeLogic logic, TicTacToeState state) {
		this.logic = logic;
		this.state = state;
	}

	@Override
	public GameState returnState() {
		// TODO Auto-generated method stub
		return state;
	}

	@Override
	public GameLogic returnLogic() {
		// TODO Auto-generated method stub
		return logic;
	}
}
