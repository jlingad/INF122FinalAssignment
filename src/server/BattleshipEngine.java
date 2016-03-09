package server;

public class BattleshipEngine implements Playable {
	BattleshipLogic logic;
	BattleshipState state;
	
	public BattleshipEngine(BattleshipLogic logic, BattleshipState state) {
		this.logic = logic;
		this.state = state;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
