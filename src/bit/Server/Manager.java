package bit.Server;

import java.util.ArrayList;

public class Manager {

	private TcpIpMultiServer server = new TcpIpMultiServer();

	private Manager() {
	}

	private static Manager Singleton = new Manager();

	public static Manager getInstance() {
		return Singleton;
	}
	public void Run() {
		if(!db.Run()) {
			System.out.println("서버 종료");
			System.exit(0); // 강제 종료 함수
		}
		server.Run();
	}

	public String RecvData(String msg) {
		return Parser.RecvData(msg);
	}

	// 데이터베이스
	private AccountDB1 db = new AccountDB1();

	public String MakeAccount(int id, String name, int balance) {
		String msg = null;
		Account acc = new Account(id, name, balance);
		acc.Print();
		if (db.MakeAccount(id, name, balance) == true) {
			msg = Packet.MakeAccount_ack(id, true);
		} else {
			msg = Packet.MakeAccount_ack(id, false);
		}
		return msg;
	}

	public String SelectAccount(int id) {
		String msg = "";
		Account acc = db.SelectAccount(id);

		if(acc == null) {
			msg = Packet.SelectAccount_ack(id, "-", 0,"-","-", false);
		}else {
			msg = Packet.SelectAccount_ack(id, acc.getName(), acc.getBalance(), 
											acc.GetDate(), acc.GetTime(), true);
		}
		return msg;
	}
	
	public String SelectAllAccount() {
		String msg = "";
		ArrayList<Account> acclist = db.SelectAllAccount();

		if (acclist != null) {
			msg = Packet.SelectAllAccount_ack(acclist, true);
		}
		else {
			msg = Packet.SelectAllAccount_ack(acclist, false);
		}
		
		return msg;
	}
	public String InputAccount(int id, int balance) {
		String msg = null;
		
		if (db.InputAccount(id, balance) == true) {
			msg = Packet.InputAccount_ack(id, balance, true);
		} else {
			msg = Packet.InputAccount_ack(id,  balance,false);
		}
		return msg;
	}
	
	public String OutputAccount(int id, int balance) {
		String msg = null;
		
		if (db.OutputAccount(id, balance) == true) {
			msg = Packet.OutputAccount_ack(id, balance, true);
		} else {
			msg = Packet.OutputAccount_ack(id, balance, false);
		}
		return msg;
	}
	
	public String DeleteAccount(int id) {
		String msg = "";

		if(db.DeleteAccount(id) == true) {
			msg = Packet.DeleteAccount_ack(id, true);
			
		}else {
			msg = Packet.DeleteAccount_ack(id, false);
		}
		return msg;
	}


}
