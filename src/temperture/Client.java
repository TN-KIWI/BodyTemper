package temperture;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {

	/**
	 * @param args
	 */

	private static AbstractAccount myAccount;

	public static void initTemperAccount(AbstractAccount account) {
		// TODO 自動生成されたメソッド・スタブ

		myAccount = account;

	}

	static void loopMenu() throws IOException {

		boolean bol = false;
		String response;
		InputStreamReader is = new InputStreamReader(System.in);
		BufferedReader stdReader = new BufferedReader(is);

		Socket socket;

		System.out.print("接続するサーバーのIPアドレス入力>");
		String IPAddress = stdReader.readLine(); //キー1行入力

		socket = new Socket(IPAddress, SQLAccessConfiguration.SERVER_PORT);

		/* 準備：データ入力ストリームの定義--ソケットからデータを
		   取ってくる．sok → in  */
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));

		/* 準備：データ出力ストリームの定義--ソケットにデータを
		   書き込む．  sok ← out */

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				socket.
						getOutputStream()));

		String receive;
		String send;
		while (!bol) {
			System.out.println("-------menu------------\n"
					+ "login::loginを行います\n" + "registration::登録を行います\n"
					+ "create::グループを作ります\n" + "exit::プログラムを終了させます");
			send = stdReader.readLine(); //キー1行入力
			writer.write(send);//送信
			writer.newLine();
			writer.flush();

			try {
				String table;
				String id;
				String gropepass;
				String pass;

				switch (send) {
				case "login":
					Client.initTemperAccount(new GeneralAccount("test", "sample", "sample"));
					System.out.println("グループを指定してください");
					table = stdReader.readLine();
					writer.write(table);
					writer.newLine();
					writer.flush();
					System.out.println("idを指定してください");
					id = stdReader.readLine();
					writer.write(id);
					writer.newLine();
					writer.flush();
					System.out.println("passwordを指定してください");
					pass = stdReader.readLine();
					writer.write(pass);
					writer.newLine();
					writer.flush();

					System.out.println("メッセージ受信中");
					response = reader.readLine();
					System.out.println("受信したメッセージ： " + response);

					bol = "true".equals(response);
					if (bol) {

						System.out.println("login成功");
					} else {
						System.out.println("login失敗");
					}
					break;

				case "registration":

					System.out.println("グループを指定してください");
					table = stdReader.readLine();
					writer.write(table);
					writer.newLine();
					writer.flush();

					System.out.println("passwordを指定してください");
					gropepass = stdReader.readLine();
					writer.write(gropepass);
					writer.newLine();
					writer.flush();

					System.out.println("idを指定してください");
					id = stdReader.readLine();
					writer.write(id);
					writer.newLine();
					writer.flush();

					System.out.println("passwordを指定してください");
					pass = stdReader.readLine();
					writer.write(pass);
					writer.newLine();
					writer.flush();

					System.out.println("メッセージ受信中");
					response = reader.readLine();
					System.out.println("受信したメッセージ： " + response);

					bol = "true".equals(response);
					if (bol) {
						System.out.println("登録完了");

						Client.initTemperAccount(new GeneralAccount(table, id, pass));
					} else {
						System.out.println("登録失敗");
					}
					break;

				case "create":
					System.out.println("グループを指定してください");
					table = stdReader.readLine();
					writer.write(table);
					writer.newLine();
					writer.flush();

					System.out.println("passwordを指定してください");
					gropepass = stdReader.readLine();
					writer.write(gropepass);
					writer.newLine();
					writer.flush();

					System.out.println("idを指定してください");
					id = stdReader.readLine();
					writer.write(id);
					writer.newLine();
					writer.flush();

					System.out.println("passwordを指定してください");
					pass = stdReader.readLine();
					writer.write(pass);
					writer.newLine();
					writer.flush();

					System.out.println("メッセージ受信中");
					response = reader.readLine();
					System.out.println("受信したメッセージ： " + response);

					bol = "true".equals(response);
					if (bol) {
						System.out.println("登録完了");
						Client.initTemperAccount(new GeneralAccount(table, id, pass));
					} else {
						System.out.println("登録失敗");
					}
					break;

				case "exit":
					System.out.println("システムを終了させます");

					System.exit(0);// 正常終了
					break;

				}

			} catch (java.lang.NumberFormatException format_err) {
				System.out.println("数値以外が入力されています。");
				continue;
			}
		}
		while (true) {
			int len = Integer.valueOf(reader.readLine());

			for (int i = 0; i < len; i++) {
				response = reader.readLine();
				System.out.println(response);
			}
			String day;
			String temp;
			System.out.println("id;");
			System.out.println("-------menu------------\n"
					+ "input_temper::体温の入力を行います\n" + "exit::プログラムを終了させます");
			String mes = stdReader.readLine();
			writer.write(mes);
			writer.newLine();
			writer.flush();
			try {
				switch (mes) {
				case "input_temper":
					System.out.println("何日前ですか？(昨日なら-1,当日は0)");
					day = stdReader.readLine();
					writer.write(day);
					writer.newLine();
					writer.flush();

					System.out.println("体温を入力してください");
					temp = stdReader.readLine();
					writer.write(temp);
					writer.newLine();
					writer.flush();

					break;

				case "exit":
					System.out.println("システムを終了させます");

					System.exit(0);// 正常終了
					break;
				}

			} catch (java.lang.NumberFormatException format_err) {
				System.out.println("数値以外が入力されています。");
				continue;
			}
		}

	}

	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ

		Client.loopMenu();

	}

}
