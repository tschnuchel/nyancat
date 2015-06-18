package Base.game;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DataBaseManager {

	private static DataBaseManager defaultManager = null;
	
	private Connection connection = null;
	private String nickname; 
	
	public class Scoring {
		
		private String nickname;
		private int score;
		private Date date;
		
		public Scoring(String nickname, int score, Date date) {
			super();
			this.nickname = nickname;
			this.score = score;
			this.date = date;
		}

		public String getNickname() {
			return nickname;
		}

		public int getScore() {
			return score;
		}

		public Date getDate() {
			return date;
		}
	}
	
	private DataBaseManager() {
		
		this.nickname = Constants.GAME_DEFAULT_NICKNAME;

		String url = "jdbc:mysql://simon-walser.de:3306/nayan_simon";
		String user = "torben_simon";
		String password = "3tQ7yYQBCBVjVaVU";
		try {
			
			this.connection = DriverManager.getConnection(url, user, password);
			
		} catch (SQLException e) {
			
			this.connection = null;
			e.printStackTrace();
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
				String s = "insert into scores values(0, \"" + nickname +  "\", " + score + ", now(), " + Constants.GAME_VERSION + ");";
				System.out.println(s);
				statement.execute(s);
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
					Date date = rs.getDate("date");
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
				
				connection.close();
				
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	public String getPlayerName() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
}
