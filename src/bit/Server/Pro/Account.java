package bit.Server.Pro;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

public class Account {
	private int accid;
	private String name;
	private int balance;
	Calendar newtime;
	
	public Account(int accid, String name) {
		this(accid, name, 0);
	}
	
	public Account(int accid, String name, int balance) {
		this.setAccid(accid);
		this.setName(name);
		this.setBalance(balance);
		newtime = Calendar.getInstance();
	}
	
	public Account(int accid, String name, int balance, Timestamp dt) {
		this(accid, name, balance);
		this.newtime.setTime(dt); // Calendar <---- Timestamp
	}
	
//	public Account(int accid, String name, int balance, Date date, Time time) {
//		this(accid, name, balance);
//		this.newtime.setTime(date); // Calendar <---- Timestamp
//	}
	public int getAccid() {
		return accid;
	}

	private void setAccid(int accid) {
		this.accid = accid;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public int getBalance() {
		return balance;
	}

	private void setBalance(int balance) {
		this.balance = balance;
	}
	
	public Calendar getNewTime() {
		return newtime;
	}
	
	@SuppressWarnings("unused")
	private void setNewTime(Calendar newtime) {
		this.newtime =newtime;
	}
	
	//메서드
	public void InputMoney(int money) throws Exception {
		if(money<0) 
			throw new Exception("잘못된 금액입니다.");
		balance += money;
	}
	//디버깅 : 실행시 오류를 찾는 과정!-> 해결
	// 확인할 코드에 breakpoint를 설정한 후 -> 디버깅을 실행
	public void OutputMoney(int money) throws Exception {
		if(money<0) 
			throw new Exception("잘못된 금액입니다.");
		if(money > balance) 
			throw new Exception("잔액이 부족합니다.");
		balance -= money;
	}
	
	public String GetDate() {
		String temp = String.format("%04d-%02d-%02d", 
				newtime.get(Calendar.YEAR) ,newtime.get(Calendar.MONTH)+1, newtime.get(Calendar.DAY_OF_MONTH));
		return temp;
	}
	
	public String GetTime() {
		String temp = String.format("%02d:%02d", 
				newtime.get(Calendar.HOUR_OF_DAY),newtime.get(Calendar.MINUTE));
		return temp;
	}
	
	public void Println() {
		System.out.println("[계좌번호]" + accid);
		System.out.println("[이름]" + name);
		System.out.println("[잔액]" + balance + "원");
		System.out.println("[개설일자] " + GetDate());
		System.out.println("[개설시간] " + GetTime());
	}
	
	public void Print() {		
		System.out.print("[" + accid + "] ");
		System.out.print(name + " ");
		System.out.print(balance + "원 ");
		System.out.print(GetDate() + " ");
		System.out.println(GetTime());
	}



	
}
