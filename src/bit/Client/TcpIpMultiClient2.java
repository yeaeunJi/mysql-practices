package bit.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class TcpIpMultiClient2 {
	final int PORT = 4000;
//	final String SERVER_IP = "14.32.18.42"; // 14.32.18.42
	final String SERVER_IP = "127.0.0.1";
	private ClientSender2 clientsender = null;
	private Bank bank;
	
	public TcpIpMultiClient2(Bank bank) {
		this.bank = bank;
	}

	// 메서드
	public void Run() {
		try {
			// 소켓 생성 및 연결!
			Socket socket = new Socket(SERVER_IP, PORT);
			System.out.println("서버 연결 됨");
			clientsender = new ClientSender2(socket); // 일반 객체
			Thread receiver = new ClientReceiver2(socket, bank); // 쓰레드 객체
			receiver.start();
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (Exception e) {
		}
	}

	// 데이터 전송 기능
	public void SendMessage(String msg) {
		try {
			clientsender.SendMessage(msg);
		} catch (Exception ex) {
			System.out.println("전송 오류");
		}
	}
}

//송신 전용 thread
class ClientSender2 {
	// private Socket socket;
	private PrintWriter writer;

	public ClientSender2(Socket socket) {
		// this.socket=socket;
		try {
			writer = new PrintWriter(socket.getOutputStream());
		} catch (Exception e) {
		}
	}

	public void SendMessage(String msg) {
		writer.println(msg);
		writer.flush();
	}
}

//수신 전용 thread
class ClientReceiver2 extends Thread {
	private Socket socket;
	private BufferedReader reader;
	private Bank bank;

	// 생성자
	public ClientReceiver2(Socket socket, Bank bank) {
		this.socket = socket;
		this.bank = bank;
		
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
		}
	}
 
	@Override
	public void run() {
		while (reader != null) {
			try {
				String msg = reader.readLine(); // 1. 수신
				bank.RecvData(msg);// 2. 연산
				//System.out.println(msg); // 3. 결과출력
			} catch (IOException e) {
			}
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
