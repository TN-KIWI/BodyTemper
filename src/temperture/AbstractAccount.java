package temperture;

import java.util.Arrays;

public abstract class AbstractAccount {
	private String accountID;
	private String accountType;
	private String accountPASS;
	private String nameGROPE;
	private String passGROPE;
	protected String user_num;
	
	public static String message = "";
	

	String getAccountType() {
		return accountType;
	}
	
	String getAccountID() {
		return accountID;
	}


	String getAccountGROPE() {
		return nameGROPE;
	}

	String getAccountPASS() {
		return accountPASS;
	}
	
	String getGropePASS() {
		return passGROPE;
	}
	
	String getuserNUM() {
		return user_num;
	}
	
	void setMessage(String str) {
		message = str;
	}
	
	static String getMessage() {
		return message;
	}
	
	protected boolean setAccountType(String type){

		 String[] account_type = {"create","registration","general","other"};

		 if(Arrays.asList(account_type).contains(type)){
			 accountType = type;
			 return true;
		 }

		 return false;

	 }

	public AbstractAccount(String gropename,String accountId,String accountpass){
		this.accountID  = accountId;
		this.accountPASS = accountpass;
		this.nameGROPE = gropename;

		accountType = "normal";

	}
	
	public AbstractAccount(String gropename,String gropepass,String accountId,String accountpass){
		this.accountID  = accountId;
		this.accountPASS = accountpass;
		this.nameGROPE = gropename;
		this.passGROPE = gropepass;

		accountType = "normal";

	}
	
	public abstract boolean temper(int day,double temp);
	
	public abstract void printstatus();

	public abstract boolean login(String gropename, String accountId, String accountpass);
	
	public abstract boolean registration(String gropename, String gropepass, String accountId, String accountpass);
	
	public abstract boolean create(String gropename, String gropepass, String accountId, String accountpass);
	
}
