package server;

public interface Playable
{
	public GameState returnState();
	public GameLogic returnLogic();
}
