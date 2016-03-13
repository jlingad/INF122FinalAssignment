package server;

public enum GameNames
{
	TIC_TAC_TOE(0),
	CHECKERS(1),
	MATCH(2);
	
	private int value;
	
	private GameNames(int value)
	{
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
}
