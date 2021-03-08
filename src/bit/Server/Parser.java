package bit.Server;

public class Parser {
	
	public static String RecvData(String msg) {
		String [] filter = msg.split("@");
		if (filter[0].contentEquals("MakeAccount")) {
			String[] filter1 = filter[1].split("#");
			int number = Integer.parseInt(filter1[0]);
			String name = filter1[1];
			int balance = Integer.parseInt(filter1[2]);
			return Manager.getInstance().MakeAccount(number, name, balance);
		}else if (filter[0].contentEquals("SelectAccount")) {
			int number = Integer.parseInt(filter[1]);
			return Manager.getInstance().SelectAccount(number);
		}else if (filter[0].contentEquals("InputAccount")) {
			String[] filter1 = filter[1].split("#");
			int number = Integer.parseInt(filter1[0]);
			int balance = Integer.parseInt(filter1[1]);
			return Manager.getInstance().InputAccount(number, balance);
		}else if (filter[0].contentEquals("OutputAccount")) {
			String[] filter1 = filter[1].split("#");
			int number = Integer.parseInt(filter1[0]);
			int balance = Integer.parseInt(filter1[1]);
			return Manager.getInstance().OutputAccount(number, balance);
		}else if (filter[0].contentEquals("DeleteAccount")) {
			int number = Integer.parseInt(filter[1]);
			return Manager.getInstance().DeleteAccount(number);
		}else if (filter[0].contentEquals("SelectAllAccount")) {
			return Manager.getInstance().SelectAllAccount();
		}
		return "";
	}

}
