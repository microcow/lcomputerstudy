package com.lcomputerstudy.testmvc.dao.java;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.lcomputerstudy.testmvc.database.java.DBConnection;
import com.lcomputerstudy.testmvc.vo.java.User;

public class UserDAO {
	public static UserDAO dao = null;
	
	private UserDAO() {
	}
	
	public static UserDAO getInstance() {
		if(dao == null) {
			dao = new UserDAO();
		}
		return dao;
	}
	
	public ArrayList<User> getUsers(){
		Connection conn = null;
		PreparedStatement pstmt = null; //PreparedStatement 인터페이스가 무엇인지
		ResultSet rs = null; // ResultSet 인터페이스가 무엇인지
		ArrayList<User> list = null;
		
		try {
			conn = DBConnection.getConnection();
			String query = "Select *"
					+ " From user";
			pstmt = conn.prepareStatement(query); // SQL 쿼리를 데이터베이스에 보내기 위한 PreparedStatement 객체를 생성
			rs = pstmt.executeQuery(); // SQL 쿼리 실행 결과가 저장된다
			list = new ArrayList<User>();
			
			while(rs.next()) {
				User user = new User();
				user.setU_idx(rs.getInt("u_idx"));
			   	user.setU_id(rs.getString("u_id"));
       	       	user.setU_name(rs.getString("u_name"));
       	       	user.setU_tel(rs.getString("u_tel"));
       	       	user.setU_age(rs.getString("u_age"));
       	       	list.add(user);
			}
		} catch (Exception e) {
			
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	public void insertUser(User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;
			
		try {
			conn = DBConnection.getConnection();
			String sql = "insert into user(u_id,u_pw,u_name,u_tel,u_age) values(?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getU_id());
			pstmt.setString(2, user.getU_pw());
			pstmt.setString(3, user.getU_name());
			pstmt.setString(4, user.getU_tel());
			pstmt.setString(5, user.getU_age());
			pstmt.executeUpdate();
		} catch( Exception ex) {
			System.out.println("SQLException : "+ex.getMessage());
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public User getUsers2(String u_idx){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user3 = null;
		
		try {
			conn = DBConnection.getConnection();
			String query = "Select *"
					+ " From user"
					+ " Where u_idx = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, u_idx);
			rs = pstmt.executeQuery();
			 
			while(rs.next()) {
			   	user3 = new User();
				user3.setU_idx(rs.getInt("u_idx"));
			   	user3.setU_id(rs.getString("u_id"));
       	       	user3.setU_name(rs.getString("u_name"));
       	       	user3.setU_tel(rs.getString("u_tel"));
       	       	user3.setU_age(rs.getString("u_age"));
			}
			// while(rs.next()){}문이 없으면 user3에 데이터가 제대로 저장되지 않음 왜?
			
		} catch (Exception e) {
			
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return user3;
	}

	public User deleteUser(String u_idx){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user4 = null;
		
		try {
			conn = DBConnection.getConnection();
			String query = "DELETE"
					+ " From user"
					+ " Where u_idx = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, u_idx);
			rs = pstmt.executeQuery();
			
		} catch (Exception e) {
			
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return user4;
	}
}
