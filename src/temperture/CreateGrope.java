package temperture;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateGrope extends AbstractAccount {

	public CreateGrope(String gropename, String gropepass, String accountId, String accountpass) {
		super(gropename, gropepass, accountId, accountpass);
		// TODO 自動生成されたコンストラクター・スタブ
		super.setAccountType("create");
	}

	@Override
	public boolean temper(int day, double temp) {
		return false;
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void printstatus() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean login(String gropename, String accountId, String accountpass) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean registration(String gropename, String gropepass, String accountId, String accountpass) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean create(String gropename, String gropepass, String accountId, String accountpass) {
		Connection conn = null;
		String url = SQLAccessConfiguration.DATABASE_URL;
		String user = SQLAccessConfiguration.DATABASE_USER;
		String password = SQLAccessConfiguration.DATABASE_PASS;
		message = "";
		Statement stmt;
		String sql;
		ResultSet rs;
		int ws;
		String table = "grope_tables";
		boolean bol = true;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, user, password);

			stmt = conn.createStatement();

			sql = "SELECT * FROM grope_tables";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String grope = rs.getString("grope");

				if (grope.equals(table)) {
					bol = false;
				}
			}
			rs.close();
			stmt.close();

			stmt = conn.createStatement();
			if (!bol) {
				System.out.println("同名gropeが存在します．別の名前で再試行してください");
				message = "同名gropeが存在します．別の名前で再試行してください";
				setMessage(message);
				return bol;
			} else {
				sql = "insert into " + table + "(grope,gropepass,leaderID)values ('" + gropename + "','" + gropepass
						+ "','" + accountId + "')";
				ws = stmt.executeUpdate(sql);
				sql = "create table "+ gropename +"(num int AUTO_INCREMENT,id varchar(20) not null primary key, pass varchar(20) not null, "
						+ "a double,b double,c double,d double,e double,f double,g double,h double,i double,j double,k double,l double, m double,n double,o double,"
						+"p double,q double,r double,s double,t double, time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, index(num))";
				;
				ws = stmt.executeUpdate(sql);
				bol = true;
				
			}

			stmt.close();
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException:" + e.getMessage());
			message = "ClassNotFoundException:" + e.getMessage();
			bol = false;
		} catch (SQLException e) {
			System.out.println("SQLException:" + e.getMessage());
			System.out.println("id,gropenameを変更してみてください");
			message = "id,gropenameを変更してみてください";
			bol = false;
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("SQLException:" + e.getMessage());
				message = "SQLException:" + e.getMessage();
			}
		}
		setMessage(message);
		return bol;

	}

}
