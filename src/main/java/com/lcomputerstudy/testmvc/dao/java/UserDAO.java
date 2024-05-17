package com.lcomputerstudy.testmvc.dao.java;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			// 쿼리에서 insert문 작성 시 이렇게 (update, delete문과는 다름)
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
			// "SET" 다음에 업데이트할 값들을 열 이름과 함께 나열하는 것이 올바른 구문입니다.
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user5.getU_id());
			pstmt.setString(2, user5.getU_pw());
			pstmt.setString(3, user5.getU_name());
			pstmt.setString(4, user5.getU_tel());
			pstmt.setString(5, user5.getU_age());
			pstmt.setInt(6, user5.getU_idx());
			pstmt.executeUpdate();
			//rs = pstmt.executeQuery();  결과를 반환받을 필요없기 때문에 executeQuery()를 호출할 필요가 없습니다.
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
		int pageNum = (page-1)*3;
		
		try {
			conn = DBConnection.getConnection();
			//String query = "select * from user limit ?,3";
			String query = new StringBuilder()
					// StringBuilder클래스의 .append메서드는 문자열을 서로 연결해주는 메서드이다
					// .toString으로 문자열을 모두 연결하지 않으면 String에 담을 수 없다
					.append("SELECT 		@ROWNUM := @ROWNUM - 1 AS ROWNUM,\n")
					.append("				ta.*\n")
					.append("FROM 			user as ta,\n")
					.append("				(SELECT @ROWNUM := (SELECT	COUNT(*)-?+1 FROM user)) as tb\n")
					/// as tb가 없으면 목록이 안나오는 이유? : 위 from절에서 두 테이블이 서로 join하고 있다. 가능하면 LEFT join을 사용하자
					.append("LIMIT			?, 3\n")
					.toString();
				//@ 기호는 변수를 나타냅니다. (일회성 변수) //:= 연산자는 MySQL에서 변수에 값을 할당하는 데 사용됩니다.
				//@ROWNUM := @ROWNUM - 1는 @ROWNUM 변수의 현재 값에서 1을 뺀 값을 다시 @ROWNUM 변수에 할당하는 것입니다.
			// limit이 하나만 있으면 출력되는 개수를 설정하는 것이며 두개 있으면 첫번째 숫자는 출력되는 인덱스의 시작위치를 설정하고 두번째는 출력되는 개수를 설정한다
	       	pstmt = conn.prepareStatement(query);
	       	pstmt.setInt(1, pageNum);
	       	pstmt.setInt(2, pageNum);
	        rs = pstmt.executeQuery();
	        list = new ArrayList<User>();

	        while(rs.next()){     
	        	User user = new User();
	        	user.setRownum(rs.getInt("ROWNUM"));
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
	
		/* Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<User> list = null;
		
		try {
			conn = DBConnection.getConnection();
			String query = "select * from user ORDER BY u_idx asc limit ?,3";
	       	pstmt = conn.prepareStatement(query);
	       	pstmt.setInt(1, page); /// setInt 메서드를 사용하여 첫 번째 ?에 정수 값 3을 바인딩? 왜 limit의 값은 3으로 고정되어있는것이지
	       	// 첫 번째 매개변수(인덱스가 1부터 시작함)를 설정합니다. 이것은 페이지 번호를 나타내며, 이 페이지에 표시할 사용자 데이터의 시작 인덱스를 결정합니다.
	       	// limit이 3이니 page값이 0이라면 유저정보 0번인덱스부터 3개 select (데이터의 인덱스는 정렬 순서에 따라 0번부터 부여됨)
	       	// page값이 3이면 유저정보 3번인덱스부터 3개 select
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
	}*/ 
	
	public User loginUser(String id, String pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			conn = DBConnection.getConnection();
			String sql = "SELECT * FROM user WHERE u_id = ? AND u_pw = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			
			if(rs.next()){     
				user = new User();
				user.setU_idx(rs.getInt("u_idx"));
	        	user.setU_pw(rs.getString("u_pw"));
	        	user.setU_id(rs.getString("u_id"));
	        	user.setU_name(rs.getString("u_name"));
		   }
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
		return user;
	} //end of loginUser
}
