package server;

public class MatchEngine implements Playable {
	MatchLogic logic;
	MatchState state;

	public MatchEngine(MatchLogic logic, MatchState state) {
		this.logic = logic;
		this.state = state;
	}
}
