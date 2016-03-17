package server;

public class MatchEngine implements Playable {
	MatchLogic logic;
	MatchState state;

	public MatchEngine(MatchLogic logic, MatchState state) {
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
