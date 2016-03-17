package server;

public class CheckersEngine implements Playable {
	CheckersLogic logic;
	CheckersState state;


	public CheckersEngine(CheckersLogic logic, CheckersState state) {
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
