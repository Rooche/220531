package co.edu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {
	Connection conn; //필드영역으로 선언
	ResultSet rs;
	PreparedStatement psmt;
	
	public void disconnect() {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(psmt != null) {
			try {
				psmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void connect() {
//		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {	
		Class.forName("oracle.jdbc.OracleDriver");
		conn = DriverManager.getConnection(url, "hr", "hr");
		} catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
