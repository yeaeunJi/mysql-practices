package bit.Procedure;

public class Start {
	
	public static void exam1() {
		AccountDB1 db = new AccountDB1();
		db.Run();
//		db.MakeAccount(10000, "CCC", 0);
		Account acc = db.SelectAccount(10000);
		acc.Print();
		
		db.InputAccount(10000, 50000);
		acc = db.SelectAccount(10000);
		acc.Print();
		
		db.OutputAccount(10000, 500);
		acc = db.SelectAccount(10000);
		acc.Print();
		
		db.OutputAccount(10000, 200000);
		acc = db.SelectAccount(10000);
		acc.Print();
	}
	
	public static void main(String[] args) {
		exam1();
	}
}
