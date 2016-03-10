//package server;
//
//public class PlayableFactory {
//	public PlayableFactory() {
//		//donothing
//	}
//
//	public Playable createPlayable(String gametype, int player1, int player2) {
//		Playable creation = null;
//
//		if(gametype.equals("TicTacToe")) {
//			creation = new TicTacToeEngine(new TicTacToeLogic(),
//					new TicTacToeState(),
//					player1, player2);
//		}
//		else if(gametype.equals("Checkers")) {
//			creation = new CheckersEngine(new CheckersLogic(),
//					new CheckersState(),
//					player1, player2);
//		}
//		else if(gametype.equals("Battleship")) {
//			creation = new BattleshipEngine(new BattleshipLogic(),
//					new BattleshipState(),
//					player1, player2);
//		}
//
//		return creation;
//	}
//}
