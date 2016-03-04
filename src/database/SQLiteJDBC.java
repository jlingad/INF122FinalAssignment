package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Locally stored database that is going to be used to hold all the user profiles
 * in the game that are added by the user base. This is going to hold the number
 * of times they played a certain type of game and the number of times they won
 * that game. Users are also going to be identified by name -- Primary Key.
 * Adding to this comment to test
 */
public class SQLiteJDBC
{
	private static final String dbPath = "jdbc:sqlite:test.db";
	
	private Connection connection; 
	private Statement  statement;
	
	public SQLiteJDBC()
	{
		openConnection();
	}
	
	/**
	 * Opens the connection to the database. It initializes the connection object and it
	 * then creates the statement object that is going to be used throughout this 
	 * database's lifetime
	 */
	private void openConnection()
	{
		connection = null;
		statement  = null;
		try
		{
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(dbPath);
			statement  = connection.createStatement();
			createTables(); 
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Creates the one table that we have in the program that holds all the
	 * user profiles with the count of games won and count of games played
	 */
	private void createTables()
	{
		try
		{
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS UserProfile"
								  + "(name TEXT PRIMARY KEY,"
								  + " numTicTacToePlayed  INTEGER,"
								  + " numTicTacToeWon     INTEGER,"
								  + " numCheckersPlayed   INTEGER,"
								  + " numCheckersWon      INTEGER,"
								  + " numBattleshipPlayed INTEGER,"
								  + " numBattleshipWon    INTEGER)");
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Inserts a player into the game with all default values of 0 and then discerns users by their name
	 * @param name
	 * @return true if the user was added successfully and false if the user was not added to the database because the name exists
	 */
	public boolean insertPlayer(String name)
	{
		try
		{
			ResultSet queryResults = statement.executeQuery("SELECT * FROM UserProfile P WHERE P.name = '" + name + "'");
			if(queryResults.next())
			{
				return false;
			}
			else
			{
				statement.executeUpdate("INSERT INTO UserProfile " 
									  + "(name, numTicTacToePlayed, numTicTacToeWon, numCheckersPlayed, numCheckersWon, numBattleshipPlayed, numBattleShipWon)"
									  + "VALUES('" + name + "', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ")");
				return true;
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return false;
	}
	
	/**
	 * After a game finishes, the game counts as being played, so we are going to add
	 * that the user played the game. If the userWon the game, then it is going to update
	 * their games won count; otherwise, it only updates the gamesplayed count. 
	 * @param name
	 * @param game
	 * @param userWon
	 */
	public void updateUserProfile(String name, String game, boolean userWon)
	{
		try
		{
			ResultSet queryResults = statement.executeQuery("SELECT * FROM UserProfile P where P.name = '" + name + "'");
			int gamesPlayed = queryResults.getInt("num" + game + "Played");
			int gamesWon    = queryResults.getInt("num" + game + "Won");
			
			statement.executeUpdate("UPDATE UserProfile SET num" + game + "Played = " + (gamesPlayed+1) + " WHERE name = '" + name + "';" );
			if(userWon)
				statement.executeUpdate("UPDATE UserProfile SET num" + game + "Won = " + (gamesWon+1) + " WHERE name = '" + name + "';" );

		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Just used for TESTING. Pass in a name that is going to be looked up into the database
	 * and then prints out all values of the table
	 * @param name
	 */
	public void printPlayerInfo(String name)
	{
		try
		{
			ResultSet queryResults = statement.executeQuery("SELECT * FROM UserProfile P WHERE P.name = '" + name + "'");
			if(queryResults.next())
			{
				System.out.println("Name: " + queryResults.getString("name"));
				System.out.println("TicTacToe games played : " + queryResults.getInt("numTicTacToePlayed"));
				System.out.println("TicTacToe games won    : " + queryResults.getInt("numTicTacToeWon"));
				System.out.println("Checkers games played  : " + queryResults.getInt("numCheckersPlayed"));
				System.out.println("Checkers games won     : " + queryResults.getInt("numCheckersWon"));
				System.out.println("Battleship games played: " + queryResults.getInt("numBattleshipPlayed"));
				System.out.println("Battleship games won   : " + queryResults.getInt("numBattleshipWon"));
			}
			else
			{
				System.out.println("User does not exist");
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Returns the number of games that the user has won in a specific game
	 * @param name
	 * @param game
	 * @return -1 if there are any errors.
	 */
	public int getGamesWon(String name, String game)
	{
		try
		{
			ResultSet queryResults = statement.executeQuery("SELECT * FROM UserProfile P where P.name = '" + name + "'");
			int gamesWon = queryResults.getInt("num" + game + "Won");
			return gamesWon;
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return -1;
	}
	
	/**
	 * Returns the number of games that the user has played in a specific game
	 * @param name
	 * @param game
	 * @return
	 */
	public int getGamesPlayed(String name, String game)
	{
		try
		{
			ResultSet queryResults = statement.executeQuery("SELECT * FROM UserProfile P where P.name = '" + name + "'");
			int gamesPlayed = queryResults.getInt("num" + game + "Played");
			return gamesPlayed;
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return -1;	}
	
	/**
	 * Returns the total number of games won by a user
	 * @param name
	 * @return -1 if there was an error in the program. 
	 */
	public int getTotalGamesWon(String name)
	{
		try
		{
			ResultSet queryResults = statement.executeQuery("SELECT * FROM UserProfile P where P.name = '" + name + "'");
			int gamesWon = queryResults.getInt("numTicTacToeWon") + queryResults.getInt("numCheckersWon") + queryResults.getInt("numBattleshipWon");
			return gamesWon;
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return -1;	
	}
	
	/**
	 * Returns the total number of games played by a user
	 * @param name
	 * @return -1 if there was an error getting the count.
	 */
	public int getTotalGamesPlayed(String name)
	{
		try
		{
			ResultSet queryResults = statement.executeQuery("SELECT * FROM UserProfile P where P.name = '" + name + "'");
			int gamesPlayed = queryResults.getInt("numTicTacToePlayed") + queryResults.getInt("numCheckersPlayed") + queryResults.getInt("numBattleshipPlayed");
			return gamesPlayed;
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return -1;		
	}
	
	public static void main(String[] args)
	{
		SQLiteJDBC my = new SQLiteJDBC();
		System.out.println(my.insertPlayer("Jefmark"));
		System.out.println(my.insertPlayer("Nikita"));
		System.out.println(my.insertPlayer("Cameron"));
		System.out.println(my.insertPlayer("Emily"));
//		my.printPlayerInfo("Cameron");
		my.updateUserProfile("Jefmark", "Checkers", false);
		my.printPlayerInfo("Jefmark");
		System.out.println("Checkers games won: " + my.getGamesWon("Jefmark", "Checkers"));
		System.out.println("Checkers games played: " + my.getGamesPlayed("Jefmark", "Checkers"));
		System.out.println(my.getTotalGamesPlayed("Jefmark"));
		System.out.println(my.getTotalGamesWon("Jefmark"));
	}
}
