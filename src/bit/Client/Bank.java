package bit.Client;

import java.util.ArrayList;

public class Bank {

	private TcpIpMultiClient2 client;

	public Bank() {
		client = new TcpIpMultiClient2(this);
		client.Run();
	}

	public void PrintAll() {

		try {

			String msg = Packet.SelectAllAccount();
			client.SendMessage(msg);
			System.out.println("서버로 전체계좌 정보 전송");
		} catch (Exception ex) {
			System.out.println("[전송실패] " + ex.getMessage());
		}
	}

	public void MakeAccount() {
		try {
			int number = BitGlobal.InputNumber("계좌번호");
			String name = BitGlobal.InputString("이름");
			int money = BitGlobal.InputNumber("입금액");
			String msg = Packet.MakeAccount(number, name, money);
			System.out.println(msg);
			client.SendMessage(msg);
			System.out.println("서버로 신규계좌 정보 전송");
		} catch (Exception ex) {
			System.out.println("[전송실패] " + ex.getMessage());
		}
	}

	public void SelectAccount() {
		try {
			int number = BitGlobal.InputNumber("계좌번호");
			String msg = Packet.SelectAccount(number);
			client.SendMessage(msg);
			System.out.println("서버로 검색 계좌 정보 전송");
		} catch (Exception ex) {
			System.out.println("[전송실패] " + ex.getMessage());
		}
	}

	public void InputMoney() {
		try {
			int number = BitGlobal.InputNumber("계좌번호");
			int money = BitGlobal.InputNumber("입금액");
			String msg = Packet.InputAccount(number, money);
			client.SendMessage(msg);
			System.out.println("서버로 계좌 입금 정보 전송");
		} catch (Exception ex) {
			System.out.println("[전송실패] " + ex.getMessage());
		}
	}

	public void OutputMoney() {
		try {
			int number = BitGlobal.InputNumber("계좌번호");
			int money = BitGlobal.InputNumber("출금액");
			String msg = Packet.OutputAccount(number, money);
			client.SendMessage(msg);
			System.out.println("서버로 계좌 입금 정보 전송");
		} catch (Exception ex) {
			System.out.println("[전송실패] " + ex.getMessage());
		}
	}

	public void DeleteAccount() {
		try {
			int number = BitGlobal.InputNumber("계좌번호");
			String msg = Packet.DeleteAccount(number);
			client.SendMessage(msg);
			System.out.println("서버로 삭제 계좌 정보 전송");
		} catch (Exception ex) {
			System.out.println("[전송실패] " + ex.getMessage());
		}
	}

	public void RecvData(String msg) {
		Parser.RecvData(msg, this);
	}

	public void MakeAccount_Ack(int number, String result) {
		if (result.equals("S"))
			System.out.println(number + "계좌의 생성 성공");
		else
			System.out.println(number + "계좌 생성 실패");
	}

	public void SelectAccount_Ack(String result, int number, String name, int balance, String date, String time ) {
		if (result.equals("F")) {
			System.out.println(number + "계좌 번호는 없는 번호입니다.");
			return;
		}

		System.out.println("계좌번호: " + number);
		System.out.println("이름: " + name);
		System.out.println("잔액: " + balance);
		System.out.println("계좌생성날짜: " + date);
		System.out.println("계좌생성시간: " + time);
	}
	
	public void SelectAllAccount_Ack(String result, ArrayList<Account> acclist) {
		if (result.equals("F")) {
			System.out.println("전체 계좌 조회 실패");
			return;
		}
		
		System.out.println("저장 개수 : "+acclist.size());
		for(Account acc : acclist) {
			acc.Print();
		}
	}
	public void InputAccount_Ack(int number, int balance, String result) {
		if (result.equals("S"))
			System.out.println(number + "계좌에 "+balance+" 입금 성공");
		else
			System.out.println(number + "계좌에 "+balance+" 입금 실패");
	}
	
	public void OutputAccount_Ack(int number, int balance,String result) {
		if (result.equals("S"))
			System.out.println(number + "계좌에 "+balance+" 출금 성공");
		else
			System.out.println(number + "계좌에 "+balance+" 출금 실패");
	}

	public void DeleteAccount_Ack(String result, int number) {
		if (result.equals("S"))
			System.out.println(number + "계좌의 삭제 성공");
		else
			System.out.println(number + "계좌 삭제 실패");
	}
}
