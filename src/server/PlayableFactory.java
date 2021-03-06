package server;

public class PlayableFactory {
	public PlayableFactory() {
		//donothing
	}

	public Playable createPlayable(String gametype) {
		Playable creation = null;

		if(gametype.equals("TicTacToe")) {
			creation = new TicTacToeEngine(new TicTacToeLogic(),
					new TicTacToeState());
		}
		else if(gametype.equals("Checkers")) {
			creation = new CheckersEngine(new CheckersLogic(),
					new CheckersState());
		}
		else if(gametype.equals("Match")) {
			creation = new MatchEngine(new MatchLogic(),
					new MatchState());
		}

		return creation;
	}
}
