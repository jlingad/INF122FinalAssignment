package server;

public class BattleshipEngine implements Playable {
	BattleshipLogic logic;
	BattleshipState state;
	//INDEX OF PLAYER CONNECTION IN CLIENTLIST LOCATED IN ARMAGRIDDONSERVER USE THIS TO IDENTIFY
	int player1;
	int player2;
	
	public BattleshipEngine(BattleshipLogic logic, BattleshipState state, int player1, int player2) {
		this.logic = logic;
		this.state = state;
		this.player1 = player1;
		this.player2 = player2;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
