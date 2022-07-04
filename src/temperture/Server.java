package temperture;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;



public class Server extends Thread {
	public final static int SERVER_PORT = SQLAccessConfiguration.SERVER_PORT;

	//static ServerSocket ss ;
	

	/**
	 * StartCommunicationメソッドは１体１のTCP/IP通信を行うシンプルなメソッドです。
	 * ここでメッセージを受信をおこないメッセージの解析を行い様々な処理を行います。
	 * 
	 * メッセージ quit:通信を終了させます
	 * 
	 * 
	 * @see MyGeneralBank
	 * 
	 * @see BankSocket
	 * @see TransferMessageClientSender
	 */

	
	/**
	 * @param args
	 */
	
	
	private static AbstractAccount myAccount;

	
	public static void initTemperAccount(AbstractAccount account) {
		// TODO 自動生成されたメソッド・スタブ

		myAccount = account;


	}

	private static AbstractAccount getAccount() {
		return myAccount;
	}
	
	
	static boolean login(String grope,String id,String pass){
		if(login(grope,id,pass)){
			return true;
		}
		return false;
	}
	
	static boolean temper(int day,double temp){
		if(temper(day,temp)){
			return true;
		}
		return false;
	}
	
	public static String getAccountID() {
		return getAccount().getAccountID();
	}
	
	public static String getAccountGROPE() {
		return getAccount().getAccountGROPE();
	}

	public static String getAccountPASS() {
		return getAccount().getAccountPASS();
	}
	
	public static String getGropePASS() {
		return getAccount().getGropePASS();
	}
	
	static boolean registration(String table,String gropepass,String id,String pass){
		if(registration(table,gropepass,id,pass)){
			return true;
		}
		return false;
	}
	
	static boolean create(String table,String gropepass,String id,String pass){
		if(create(table,gropepass,id,pass)){
			return true;
		}
		return false;
	}
	
	static void printstatus(){
		printstatus();
	}

	static void loopMenu() throws IOException {
		
		boolean bol = false;
		
		
		Socket socket;
		
		InetAddress local = InetAddress.getLocalHost();//このマシンの情報取得
		String localAdr = local.getHostAddress();
		System.out.println("このマシンのIPアドレス" + localAdr);	
		
		//サーバー用ソケット
		ServerSocket serverSock = new ServerSocket(SERVER_PORT); 

		//クライアントからの接続を待ち、接続してきたら、
		//	そのクライアントと通信するソケットを取得する。
		Socket clientSock = serverSock.accept();
		serverSock.close();
		
		//クライアントからのリクエストメッセージ送信情報を受信して表示
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				clientSock.getInputStream()));
		
		/* 準備：データ出力ストリームの定義--ソケットにデータを
	       書き込む．  sok ← out */

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				clientSock.
				getOutputStream()));
		String send;
		
		while (!bol) {
			
			

			try {
				String table;
				String id;
				String gropepass;
				String pass;
				
				String receive =  reader.readLine();
				
				System.out.println(receive);
				
				switch (receive) {
				case "login":
					
					Client.initTemperAccount(new GeneralAccount("test", "sample","sample"));
					System.out.println("グループを指定してください");
					table = reader.readLine();
					
					System.out.println("idを指定してください");
					id = reader.readLine();
					
					System.out.println("passwordを指定してください");
					pass = reader.readLine();
					
					Server.initTemperAccount(new GeneralAccount(table,id,pass));
					bol = Server.getAccount().login(table,id,pass);
					
					writer.write(String.valueOf(bol));
					writer.newLine();
					writer.flush();
					
					
					if(bol){

						
						System.out.println("login成功"+AbstractAccount.getMessage());
						
					}else{
						System.out.println("login失敗");
					}
					
					break;

				case "registration":
					System.out.println("グループを指定してください");
					table = reader.readLine();
					
					System.out.println("グループpassを指定してください");
					gropepass = reader.readLine();
					
					System.out.println("idを指定してください");
					id = reader.readLine();
					
					System.out.println("passwordを指定してください");
					pass = reader.readLine();
					
					
					
					Server.initTemperAccount(new RegistrationAcccount(table,gropepass,id,pass));
					bol = Server.getAccount().registration(table,gropepass,id,pass);
					writer.write(String.valueOf(bol));//送信
					writer.newLine();
					writer.flush();
					
					if(bol){
						System.out.println("登録完了");
						Server.initTemperAccount(new GeneralAccount(table,id,pass));
						Server.getAccount().login(table,id,pass);
						
					}else{
						System.out.println("登録失敗");
					}
					
					break;

				case "create":
					System.out.println("グループを指定してください");
					table = reader.readLine();
					
					System.out.println("グループpassを指定してください");
					gropepass = reader.readLine();
					
					System.out.println("idを指定してください");
					id = reader.readLine();
					
					System.out.println("passwordを指定してください");
					pass = reader.readLine();
					
					Server.initTemperAccount(new CreateGrope(table,gropepass,id,pass));
					bol = Server.getAccount().create(table,gropepass,id,pass);
					writer.write(String.valueOf(bol));//送信
					writer.newLine();
					writer.flush();
					
					if(bol){
						System.out.println("登録完了");
						Server.initTemperAccount(new RegistrationAcccount(table,gropepass,id,pass));
						Server.getAccount().registration(table,gropepass,id,pass);
						Server.initTemperAccount(new GeneralAccount(table,id,pass));
						Server.getAccount().login(table,id,pass);
					}else{
						System.out.println("登録失敗");
					}
					
					break;
					
				case "exit":
					System.out.println("システムを終了させます");
					reader.close();
					writer.close();
					clientSock.close();
					System.exit(0);// 正常終了
					break;


				}

			} catch (java.lang.NumberFormatException format_err) {
				System.out.println("数値以外が入力されています。");
				continue;
			}
		}
		while(true){
			Server.getAccount().printstatus();
			send = AbstractAccount.message;
			String[] lisend = send.split("\n");
			int len = lisend.length;
			writer.write(String.valueOf(len));//送信
			writer.newLine();
			writer.flush();
			for (int i = 0; i < len; i++) {
				writer.write(lisend[i]);//送信
				writer.newLine();
				writer.flush();
			}
			
			
			String day;
			String temp;
			System.out.println("id;" + getAccountID());
			System.out.println("-------menu------------\n"
					+ "input_temper::体温の入力を行います\n" + "exit::プログラムを終了させます\n");
			String mes = reader.readLine();
			try {
				switch (mes) {
				case "input_temper":
					System.out.println("何日前ですか？(昨日なら-1,当日は0)");
					day = reader.readLine();

					System.out.println("体温を入力してください");
					temp = reader.readLine();
					bol = Server.getAccount().temper(Integer.parseInt(day),Double.parseDouble(temp));
					System.out.println(bol);
					break;
					
				case "exit":
					System.out.println("システムを終了させます");
					reader.close();
					writer.close();
					clientSock.close();
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

		
		Server.loopMenu();
	}

}
