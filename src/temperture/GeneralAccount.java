package temperture;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GeneralAccount extends AbstractAccount {
	String url = SQLAccessConfiguration.DATABASE_URL;
	String user = SQLAccessConfiguration.DATABASE_USER;
	String password = SQLAccessConfiguration.DATABASE_PASS;

	public GeneralAccount(String gropename, String accountId, String accountpass) {

		super(gropename, accountId, accountpass);

		super.setAccountType("general");
		// TODO Auto-generated constructor stub
	}

	public boolean temper(int day,double temp){
		Connection conn = null;
		message = "";
		String table = getAccountGROPE();
		String accountId = getAccountID();

		Statement stmt;
		String sql;
		boolean bol = false;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, user, password);

			stmt = conn.createStatement();
			char test = (char) (116 + day);
			sql = "update " + table + " set " + String.valueOf(test) + "=" + temp + " where id='" + accountId + "';";

			int ws = stmt.executeUpdate(sql);

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

	public boolean login(String gropename, String accountId, String accountpass) {
		Connection conn = null;
		message = "";
		String table = gropename;
		Statement stmt;
		String sql;
		ResultSet rs;
		boolean bol = false;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, user, password);

			stmt = conn.createStatement();

			sql = "SELECT * FROM grope_tables";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String grope = rs.getString("grope");
				String gropepass = rs.getString("gropepass");

				if (grope.equals(table)) {
					bol = true;
				}
			}
			if(!bol){
				System.out.println("gropeが存在しません");
				message = "gropeが存在しません";
				setMessage(message);
				return bol;
			}else{
				sql = "SELECT * FROM "+table+" ORDER BY num";
				rs = stmt.executeQuery(sql);
				bol = false;
				while (rs.next()) {
					String id = rs.getString("id");
					String pass = rs.getString("pass");

					if (id.equals(accountId)) {
						if(pass.equals(accountpass)){
							bol = true;
						}else{
							System.out.println("passが一致しません");
							message = "passが一致しません";
						}

					}
				}
			}
			if(!bol){
				System.out.println("id,passが違います");
				message = "id,passが違います";
			}


			rs.close();

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

	public void printstatus() {
		message = "";
		Connection conn = null;
		String url = SQLAccessConfiguration.DATABASE_URL;
		String user = SQLAccessConfiguration.DATABASE_USER;
		String password = SQLAccessConfiguration.DATABASE_PASS;
		String user_num = "-1";
		String user_id = getAccountID();
		String table = getAccountGROPE();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, user, password);

			Statement stmt = conn.createStatement();
			Statement stm = conn.createStatement();
			//String sqlaa = "insert into testgrope (id,pass,a) values ('test3aaa','test',"+"36.7"+")";
			//int rssa = stmt.executeUpdate(sqlaa);

			String sql = "SELECT * FROM " + table+" ORDER BY num";
			ResultSet rs = stmt.executeQuery(sql);

			System.out.printf("%20s|", "id");
			message += String.format("%20s|", "id");
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
			for (int i = 0; i < 20; i++) {
				Date today = new Date();
				//Date today = sdf.parse("01/16");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(today);
				calendar.add(Calendar.DAY_OF_MONTH, i - 19);
				today = calendar.getTime();
				System.out.print(sdf.format(today) + "|");
				message += sdf.format(today) + "|";
			}
			System.out.println();
			message += "\n";
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date today = new Date();
			//Date today = sdf.parse("2021-01-16");

			while (rs.next()) {
				String time = rs.getString("time");
				String num = rs.getString("num");
				String id = rs.getString("id");
				if (rs.getString("id").equals(user_id)) {
					user_num = num;
				}
				String Update = time;
				Date date;
				if (num.equals(user_num) || user_num.equals("1")) {
					try {
						date = sdf.parse(Update);

						long dateTimeTo = date.getTime();
						long dateTimeFrom = today.getTime();
						int dayDiff = (int) (dateTimeTo - dateTimeFrom) / (1000 * 60 * 60 * 24);

						System.out.printf("%20s|", rs.getString(String.valueOf("id")));
						message += String.format("%20s|", rs.getString(String.valueOf("id")));
						for (int i = 97; i < 117; i++) {

							char test = (char) i;

							char c = (char) (i - dayDiff);
							if (i - dayDiff >= 117) {
								String sqla = "update " + table + " set " + test + "=null where num=" + num + ";";

								int rss = stm.executeUpdate(sqla);
								System.out.printf("%5s|", "null");
								message += String.format("%5s|", "null");

							} else {
								String s = String.valueOf(c);

								System.out.printf("%5s|", rs.getString(s));
								message += String.format("%5s|", rs.getString(s));
								String sqla = "update " + table + " set " + test + "=" + s + " where num=" + num + ";";

								int rss = stm.executeUpdate(sqla);

							}

						}

						System.out.println();
						message += "\n";
					} catch (ParseException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
				}
			}

			rs.close();
			stm.close();
			stmt.close();
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException:" + e.getMessage());
			message = "ClassNotFoundException:" + e.getMessage();
		} catch (SQLException e) {
			System.out.println("SQLException:" + e.getMessage());
			System.out.println("id,gropenameを変更してみてください");
			message = "id,gropenameを変更してみてください";
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


	}

	@Override
	public boolean registration(String gropename, String gropepass, String accountId, String accountpass) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean create(String gropename, String gropepass, String accountId, String accountpass) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
}
