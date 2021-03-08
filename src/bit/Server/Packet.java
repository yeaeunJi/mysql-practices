package bit.Server;

import java.util.ArrayList;

public class Packet {
	
	public static String MakeAccount_ack(int id, boolean b) {
		String msg = "";
		msg += "MakeAccount_ack" + "@";
		msg += id + "#";
		if(b)
			msg += "S";
		else
			msg += "F";
		
		return msg;
	}
	
	public static String SelectAccount_ack(int id, String name, int balance,String date, String time, boolean b) {
		String msg = "";
		msg += "SelectAccount_ack" + "@";
		if(b) msg += "S" + "#";
		else msg += "F" + "#";
		msg += id + "#";
		msg += name + "#";
		msg += balance+"#";
		msg += date+"#";
		msg += time;
		
		return msg;
	}
	
	//header(flag)@실패#1번째 정보%%.#...
	public static String SelectAllAccount_ack(ArrayList<Account> acclist, boolean b) {
		String msg = "";
		msg += "SelectAllAccount_ack" + "@";
		if(b) msg += "S" + "#";
		else msg += "F" + "#";
		
		for(Account acc : acclist) {
			msg += acc.getAccid() + "%";
			msg += acc.getName() + "%";
			msg += acc.getBalance()+"%";
			msg += acc.GetDate()+"%";
			msg += acc.GetTime()+"#";
		}
		return msg;
	}
	
	
	public static String InputAccount_ack(int id, int balance, boolean b) {
		String msg = "";
		msg += "InputAccount_ack" + "@";
		if(b)
			msg += "S#";
		else
			msg += "F#";
		msg += id + "#";
		msg += balance;
		
		
		return msg;
	}
	
	public static String OutputAccount_ack(int id, int balance, boolean b) {
		String msg = "";
		msg += "OutputAccount_ack" + "@";
		if(b)
			msg += "S#";
		else
			msg += "F#";
		msg += id + "#";
		msg += balance;
		
		return msg;
	}
	
	public static String DeleteAccount_ack(int id, boolean b) {
		String msg = "";
		msg += "DeleteAccount_ack" + "@";
		if(b) msg += "S" + "#";
		else msg += "F" + "#";
		msg += id;
		return msg;
	}
}
