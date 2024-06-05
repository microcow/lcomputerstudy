package com.lcomputerstudy.testmvc.database.java;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public static Connection getConnection() throws SQLException, ClassNotFoundException{
		Connection conn = null;
		
		
		//로컬호스트(localhost)란 현재 사용 중인 컴퓨터를 가리키는 특수한 호스트 이름
		String url = "jdbc:mysql://localhost:3306/dbtest"; // 학원 db주소
		//String url = "jdbc:mysql://localhost:3306/sangwoo"; //집 db주소	
		String id = "root";
		String pw = "1234";
		
		Class.forName("org.mariadb.jdbc.Driver");
		conn = DriverManager.getConnection(url, id, pw);
		
		return conn;
	}
}
