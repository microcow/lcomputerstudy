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
	public User changeUser(User user5){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.getConnection();
			String query = "UPDATE user SET u_id=?, u_pw=?, u_name=?, u_tel=?, u_age=? WHERE u_idx=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user5.getU_id());
			pstmt.setString(2, user5.getU_pw());
			pstmt.setString(3, user5.getU_name());
			pstmt.setString(4, user5.getU_tel());
			pstmt.setString(5, user5.getU_age());
			pstmt.setInt(6, user5.getU_idx());
			pstmt.executeUpdate();
			//rs = pstmt.executeQuery();  ResultSet을 반환하는 것이 아니기 때문에 executeQuery()를 호출할 필요가 없습니다.
			/*
쿼리(Query): 데이터베이스로부터 데이터를 가져오는 데 사용됩니다. 이것은 SELECT 문의 경우입니다. 결과 집합이 반환됩니다.
갱신(Update): 데이터베이스의 데이터를 수정, 삭제 또는 추가하는 데 사용됩니다. 이것은 INSERT, UPDATE, DELETE 문의 경우입니다. 결과 집합이 반환되지 않습니다.
따라서 UPDATE 쿼리를 실행할 때는 결과 집합을 반환받을 필요가 없으므로 executeQuery()를 호출할 필요가 없습니다.
대신에 executeUpdate()를 사용하여 쿼리를 실행하고, 필요한 경우 갱신된 행의 수를 확인할 수 있습니다.
즉, UPDATE는 갱신문이기에 DB에서 결과를 반환받을 필요 없으므로 executeQuery 메서드를 실행할 필요가 없음
			 */
			
		} catch (Exception e) {
			
		} finally {
			try {
				//rs.close();
				pstmt.close();
				conn.close();				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return user5;
	}
	public int getUsersCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;

		try {
			conn = DBConnection.getConnection();
			String query = "SELECT COUNT(*) as count FROM user ";
	       	pstmt = conn.prepareStatement(query);
	        rs = pstmt.executeQuery();
	        
	        while(rs.next()){     
	        	count = rs.getInt("count");
	        }
		} catch (Exception e) {
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	public ArrayList<User> getUsers3(int page) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<User> list = null;
		
		try {
			conn = DBConnection.getConnection();
			String query = "select * from user limit ?,3";
	       	pstmt = conn.prepareStatement(query);
	       	pstmt.setInt(1, page);
	        rs = pstmt.executeQuery();
	        list = new ArrayList<User>();
		        
		     while(rs.next()){     
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
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
