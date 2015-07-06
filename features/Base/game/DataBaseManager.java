package Base.game;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class DataBaseManager {

	private static DataBaseManager defaultManager = null;
	
	private Connection connection = null;
	private String nickname; 
	
	public class Scoring {
		
		private String nickname;
		private int score;
		private Timestamp time;
		
		public Scoring(String nickname, int score, Timestamp time) {
			super();
			this.nickname = nickname;
			this.score = score;
			this.time = time;
		}

		public String getNickname() {
			return nickname;
		}

		public int getScore() {
			return score;
		}

		public Timestamp getTime() {
			return time;
		}
		
		@Override
		public String toString() {

			return nickname + " " + score + " " + time;
		}
	}
	
	private DataBaseManager() {
		
		this.nickname = Constants.GAME_DEFAULT_NICKNAME;

		// load db driver
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		// connect to db
		String url = "jdbc:hsqldb:db/score.db";
		String user = "sa";
		String password = "";
		try {
			
			this.connection = DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
			
			this.connection = null;
			e.printStackTrace();
		}
		
		if (this.connection != null) {
			
			try {
				
				// create table if neccesary
				Statement st = connection.createStatement();
				int bla = st.executeUpdate("CREATE TABLE IF NOT EXISTS scores(id INTEGER IDENTITY, nickname VARCHAR(256), score INTEGER, time TIMESTAMP default NOW, version INTEGER)");
				System.out.println("HSQLDB " + bla);
				st.close();
				this.connection.commit();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}

	public static DataBaseManager getDefaultManager() {
		
		if (defaultManager == null) {
			
			defaultManager = new DataBaseManager();
		}
		
		return defaultManager;
	}
	
	public void uploadScore(int score) {
		
		try {
			
			if (connection != null) {
				
				Statement statement = connection.createStatement();
				String s = "insert into scores (nickname, score, version) values('" + nickname +  "', " + score + "," + Constants.GAME_VERSION + ");";
				System.out.println(s);
				statement.execute(s);
				connection.commit();
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	public List<Scoring> getTop(int x) {
		
		LinkedList<Scoring> result = new LinkedList<DataBaseManager.Scoring>();
		
		try {

			if (connection != null) {
				
				Statement statement = connection.createStatement();
				statement.execute("select * from scores where version=" + Constants.GAME_VERSION + " order by score desc limit " + x + ";");
				ResultSet rs = statement.getResultSet();
				while (rs.next()) {
					
					String nickname = rs.getString("nickname");
					int score = rs.getInt("score");
					Timestamp date = rs.getTimestamp("time");
					result.add(new Scoring(nickname, score, date));
					System.out.println(nickname + " " + score + " " + date);
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return result;
	}
	
	public void close() {
		
		if (connection != null) {
			try {
				
				Statement st = connection.createStatement();
				
				// db writes out to files and performs clean shuts down
				// otherwise there will be an unclean shutdown
				// when program ends
				st.execute("SHUTDOWN");
				connection.close();
				
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	public String getPlayerName() {
		return nickname;
	}

	public void setPlayerName(String nickname) {
		this.nickname = nickname;
	}
	
}
