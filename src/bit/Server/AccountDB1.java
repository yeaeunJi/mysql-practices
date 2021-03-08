package bit.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

	//
	public boolean MakeAccount(int id, String name, int balance) {
		try {
			String sql = "insert into account(accid,name,balance) values(?,?,?);";
			PreparedStatement sment = con.prepareStatement(sql);
			sment.setInt(1,  id);
			sment.setString(2,  name);
			sment.setInt(3, balance);
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

	public Account SelectAccount(int id) {
		try {
			String sql = "select * from account where accid=?;";
			PreparedStatement sment = con.prepareStatement(sql);
			sment.setInt(1,  id);

			ResultSet result = sment.executeQuery();
			result.next();

			int accid=result.getInt(1);
			String name=result.getString(2);
			int balance = result.getInt(3);
			Timestamp ntime = result.getTimestamp(4, Calendar.getInstance() );
			System.out.println(ntime);
			sment.close();

			Account acc=new Account(accid,name,balance, ntime);
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
			String sql = "update account set balance = balance+? where accid=?;";
			PreparedStatement sment = con.prepareStatement(sql);
			sment.setInt(1,  balance);
			sment.setInt(2,  id);

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

	public boolean OutputAccount(int id, int balance) {
		try {
			String sql = "update account set balance = balance-? where accid=? and balance >= ?;";
			PreparedStatement sment = con.prepareStatement(sql);
			sment.setInt(1,  balance);
			sment.setInt(2,  id);
			sment.setInt(3,  balance);
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











