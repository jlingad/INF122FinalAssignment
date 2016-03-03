package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLiteJDBC
{
	private static final String dbPath = "jdbc:sqlite:test.db";
	
	private Connection connection; 
	private Statement  statement;
	
	public SQLiteJDBC()
	{
		openConnection();
	}
	
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
	
	private void createTables()
	{
		try
		{
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS UserProfile"
								  + "(userID INTEGER PRIMARY KEY AUTOINCREMENT,"
								  + " name TEXT,"
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
	
	public void printPlayerInfo(String name)
	{
		try
		{
			ResultSet queryResults = statement.executeQuery("SELECT * FROM UserProfile P WHERE P.name = '" + name + "'");
			if(queryResults.next())
			{
				System.out.println("Name: " + queryResults.getString("name"));
				System.out.println("Checkers games won: " + queryResults.getInt("numCheckersWon"));
			}
			else
			{
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	public static void main(String[] args)
	{
		SQLiteJDBC my = new SQLiteJDBC();
		System.out.println(my.insertPlayer("Jefmark"));
		System.out.println(my.insertPlayer("Nikita"));
		System.out.println(my.insertPlayer("Cameron"));
		System.out.println(my.insertPlayer("Emily"));
		my.printPlayerInfo("Cameron");
	}
}
