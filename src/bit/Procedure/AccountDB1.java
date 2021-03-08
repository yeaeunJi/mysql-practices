package bit.Procedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;


public class AccountDB1 {
	private Connection con = null;


	//생성자
	public AccountDB1(){	}

	//DB연결(Connection 객체 생성)
	public boolean Run() {
		try {			
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로딩 성공");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=UTC","root","1234");
			con.setAutoCommit(false);
			System.out.println("데이터베이스 연결 완료");
			return true;

		} catch (Exception e) {
			System.out.println("[데이터베이스 초기화 에러] "+e.getMessage());
			return false;
		}		
	}

	//프로시저를 사용하여 데이터베이스와 연동
	public boolean MakeAccount(int accnum, String name, int balance) {
		try {
			String qury = "{call MakeAccount(?,?,?)};";
			PreparedStatement  sment = con.prepareStatement(qury);			
			sment.setInt(1,  accnum);
			sment.setString(2,  name);
			sment.setInt(3,  balance);
			  //<===================================
			if( sment.executeUpdate() > 0) {
				con.commit();
				sment.close();
				System.out.println("계좌 생성 성공");
				return true;
			}	
			System.out.println("계좌 생성 실패");
			return false;
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}


	public Account SelectAccount(int id) {
		try {
			String sql = "{call SelectAccount(?,?,?,?)};";
			CallableStatement sment = con.prepareCall(sql);
			sment.setInt(1,  id);
			sment.registerOutParameter(2, Types.VARCHAR);
			sment.registerOutParameter(3, Types.INTEGER);
			sment.registerOutParameter(4, Types.TIMESTAMP);
			
			sment.execute();
			
			String name= sment.getString(2);
			int balance = sment.getInt(3);
			Timestamp ntime = sment.getTimestamp(4, Calendar.getInstance() );
			sment.close();

			Account acc=new Account(id,name,balance, ntime);
			return acc;

		} catch (SQLException e) {
			return null;
		}
	}

	public ArrayList<Account> SelectAllAccount() {
		try {
			ArrayList<Account> accountlist = new ArrayList();
			String sql = "select * from account;";
			PreparedStatement sment = con.prepareStatement(sql);

			ResultSet result = sment.executeQuery();
			while(result.next()) {
				int accid=result.getInt(1);
				String name=result.getString(2);
				int balance = result.getInt(3);
				Timestamp ntime = result.getTimestamp(4, Calendar.getInstance());
				
				Account acc=new Account(accid,name,balance, ntime);
				accountlist.add(acc);
			}
			sment.close();
			return accountlist;

		} catch (SQLException e) {
			return null;
		}
	}



	public boolean InputAccount(int id, int balance) {
		try {
			String sql = "{call InputAccount(?,?)};";
			CallableStatement sment = con.prepareCall(sql);
			sment.setInt(2,  balance);
			sment.setInt(1,  id);			
			sment.execute();
			
			if( sment.execute() ) {
				con.commit();
				sment.close();
				System.out.println("입금 성공");
				return true;
			}
			System.out.println("입금 실패");
			sment.close();
			return false;
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean OutputAccount(int id, int balance) {
			try {
				String sql ="{call OutputAccount(?,?)};";
				CallableStatement sment = con.prepareCall(sql);
				sment.setInt(1,  balance);
				sment.setInt(2,  id);			
				sment.execute();
				
				if( sment.execute() ) {
					con.commit();
					sment.close();
					System.out.println("출금 성공");
					return true;
				}
				System.out.println("출금실패");
				sment.close();
				return false;
			} catch (SQLException e) {
				return false;
			}
	}

	public boolean DeleteAccount(int id) {
		try {
			String sql = "delete from account where accid=?;";
			PreparedStatement sment = con.prepareStatement(sql);
			sment.setInt(1,  id);

			int i = sment.executeUpdate();
			sment.close();
			if( i > 0) {
				con.commit();
				return true;
			}
			return false;

		} catch (SQLException e) {
			return false;
		}
	}
}











