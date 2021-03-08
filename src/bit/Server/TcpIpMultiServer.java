package bit.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

//Server코드 담기!

public class TcpIpMultiServer {
	// 대기소켓에서 사용할 포트번호
	final int PORT = 4000;
	// 접속한 클라이언트들과 대화하기 위한 정보 저장!
	HashMap<Socket, PrintWriter> clients = new HashMap<Socket, PrintWriter>();
	// 대기소켓
	private ServerSocket serverSocket = null;

	// 생성자
	public TcpIpMultiServer() {
	}

	public void Run() {
		try {
			InitWaitSocket();

			while (true) {
				// 클라이언트 접속 & 통신소켓 생성
				Socket socket = serverSocket.accept();
				System.out.println("[클라이언트접속] " + socket.getInetAddress() + ":" + socket.getPort());

				// 현재 연결된 소켓의 IO를 수행할 전용 thread
				ServerReceiver thread = new ServerReceiver(socket, clients);
				thread.start(); // thread클래스의 run메서드가 호출!
			}
		} catch (Exception e) {
			System.out.println("exception: " + e.getMessage());
		}
	}

	// 1. 대기소켓(소켓생성, 바인드, 릿슨)
	private void InitWaitSocket() throws IOException {
		serverSocket = new ServerSocket(PORT); // 14.32.18.42
		System.out.println("서버시작,접속 기다림");
	}
}

//쓰레드 클래스
//run이 동작메서드
class ServerReceiver extends Thread {
	private Socket socket; // 통신 소켓(클라이언트와 연결된 상태)
	private BufferedReader reader; // read객체(클라이언트가 보낸 정보를 읽을 수 있다.)
	private PrintWriter writer; // write객체(클라이언트에게 정보를 보낼 수 있다.)
	// ----------------------------------------------
	private HashMap<Socket, PrintWriter> clients;

	// 생성자
	public ServerReceiver(Socket socket, HashMap<Socket, PrintWriter> clients) {
		this.socket = socket;
		this.clients = clients;

		//
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream());
		} catch (Exception e) {
		}

	}

	// 쓰레드 함수
	@Override
	public void run() {
		try {

			while (reader != null) {
				String msg = reader.readLine(); // 1) 데이터 수신
				String msg1 = Manager.getInstance().RecvData(msg);// 2) 데이터 처리를 관리모듈에 전달
				
				SendMessage(msg1);
//			   sendToAll(msg); 					//3) 결과를 전송		
			}
			// -----------------------------------------------------------------

		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행이 필요한 코드가 존재할 경우![더 이상 해당 소켓은 필요 없다]
			clients.remove(socket);
			System.out.println("[클라이언트 해제] " + socket.getInetAddress() + ":" + socket.getPort());
			System.out.println("현재 서버접속자수" + clients.size());

			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void SendMessage(String msg) {
		writer.println(msg); // 전송!!!!!!![출력버퍼에 저장]
		writer.flush(); // [출력버퍼에 있는 정보를 밀어내는 역할]
		System.out.println("[ack] " + msg);
	}

}
