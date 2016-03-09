package server;

public interface Playable extends Runnable {
	/*
	 * What does each game need to do?
	 * *keep state of game objects
	 * *check if move is valid
	 * *make move
	 * 
	 * how does it know which player is moving? do we need player ID's in client connection? Perhaps the string name stored in database
	 * ^^^^^ USE INDEX OF CLIENT LIST AND STORE IT IN THE ENGINE ^^^^^^^
	 * SO.... need to update constructors with indexes!!!!!!!!!!!!!
	 */
}
