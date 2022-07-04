package temperture;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegistrationAcccount extends AbstractAccount {
	public RegistrationAcccount(String gropename, String gropepass, String accountId, String accountpass) {
		super(gropename, gropepass, accountId, accountpass);
		// TODO 自動生成されたコンストラクター・スタブ
		
		super.setAccountType("registration");
	}
	
	public boolean registration(String gropename, String gropepass, String accountId, String accountpass) {
		// TODO 自動生成されたメソッド・スタブ
		Connection conn = null;
		String url = SQLAccessConfiguration.DATABASE_URL;
		String user = SQLAccessConfiguration.DATABASE_USER;
		String password = SQLAccessConfiguration.DATABASE_PASS;
		message = "";
		Statement stmt;
		String sql;
		ResultSet rs;
		int ws;
		String table = gropename;
		boolean bol = false;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, user, password);

			stmt = conn.createStatement();

			sql = "SELECT * FROM grope_tables";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String grope = rs.getString("grope");

				if (grope.equals(table)) {
					if(gropepass.equals(rs.getString("gropepass"))){
						bol = true;
					}else{
						System.out.println("gropepassが違います");
						message = "gropepassが違います";
						rs.close();
						stmt.close();
						setMessage(message);
						return bol;
					}
					
				}
			}
			rs.close();
			stmt.close();
			stmt = conn.createStatement();
			if(!bol){
				System.out.println("gropeが存在しません");
				message = "gropeが存在しません";
				setMessage(message);
				return bol;
			}else{
				sql = "insert into "+ table + "(id,pass)values ('"+accountId+"','"+accountpass+"')";
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
			bol = false;
			message = "id,gropenameを変更してみてください";
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
			bol = false;
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

	@Override
	public boolean temper(int day,double temp) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
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
	public boolean create(String gropename, String gropepass, String accountId, String accountpass) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
}
