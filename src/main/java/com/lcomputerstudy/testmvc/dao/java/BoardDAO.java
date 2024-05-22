package com.lcomputerstudy.testmvc.dao.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.lcomputerstudy.testmvc.database.java.DBConnection;
import com.lcomputerstudy.testmvc.vo.java.Board;
import com.lcomputerstudy.testmvc.vo.java.Pagination;
import com.lcomputerstudy.testmvc.vo.java.User;

public class BoardDAO {
	public static BoardDAO dao = null;
	
	public static BoardDAO getInstance() {
		if(dao == null) {
			dao = new BoardDAO();
		}
		return dao;
	}
	
	public int getPostCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;

		try {
			conn = DBConnection.getConnection();
			String query = "SELECT COUNT(*) as count FROM board ";
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
	
	public void insertBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
			
		try {
			String sql = "insert into board(b_title,b_content,b_date,b_writer,u_idx) values(?,?,NOW(),?,?)";
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			// 세 번째 파라미터는 NOW()로 설정되어 있으므로 생략 
			pstmt.setString(3, board.getWriter());
			pstmt.setInt(4, board.getIdx()); // u_idx는 왜래키이다. 매칭되는 값이 없으면 오류 발생
			
			// 디버그 출력
			System.out.println(board.getTitle());
			System.out.println(board.getContent());
			System.out.println(board.getWriter());
			System.out.println(board.getIdx());
			pstmt.executeUpdate();
			
			/*pstmt.close(); // 쿼리를 한번 사용한 후 재사용하려면 executeUpdate를(쿼리실행) 한 후 close하고 다시 prepareStatement 해야한다.
			sql = "update asdfasdfsaf";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 5);
			pstmt.executeUpdate();*/
			
		} catch( Exception ex) {
			System.out.println("SQLException : "+ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
public ArrayList<Board> getPostList(int page) { // 글 목록 불러오는 메서드
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Board> list = null;
		int pageNum = (page-1)*Pagination.perPage;
		
		try {
			conn = DBConnection.getConnection();
			String query = new StringBuilder()

					.append("SELECT 		@ROWNUM := @ROWNUM + 1 AS ROWNUM,\n")
					.append("				ta.*\n")
					.append("FROM 			board as ta,\n")
					.append("				(SELECT @ROWNUM := 0) as tb\n")
					.append("LIMIT			?, ?\n") // post-list 2페이지로 안넘어져가서 임시로 limit 10으로 설정
					.toString();
	       	pstmt = conn.prepareStatement(query);
	       	pstmt.setInt(1, pageNum);
	       	pstmt.setInt(2, Pagination.perPage);
	        rs = pstmt.executeQuery();
	        list = new ArrayList<Board>();

	        while(rs.next()){     
	        	Board board = new Board();
	        	board.setRownum(rs.getInt("ROWNUM"));
	        	board.setTitle(rs.getString("b_title"));
	        	board.setB_idx(rs.getInt("b_idx"));
	        	board.setWriter(rs.getString("b_writer"));
	        	board.setDate(rs.getString("b_date"));
	        	board.setView(rs.getInt("b_view"));
       	       	list.add(board);
	        }
		} catch (Exception e) {
			e.printStackTrace();
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
public Board getPost(String b_idx) { // 상세 글 가져오기 메서드
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Board board = null;
	
	try {
		conn = DBConnection.getConnection();
		String query = "Select *"
				+ " From board"
				+ " Where b_idx = ?";
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, b_idx);
		rs = pstmt.executeQuery();
		 
		while(rs.next()) {
			board = new Board();
			board.setB_idx(rs.getInt("b_idx"));
			board.setTitle(rs.getString("b_title"));
			board.setWriter(rs.getString("b_writer"));
			board.setContent(rs.getString("b_content"));
			board.setView(rs.getInt("b_view"));
			board.setU_idx(rs.getInt("u_idx"));
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
	return board;
	}
public void updateView(String b_idx) { // 글 클릭 시 조회수 증가 메서드
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	try {
		conn = DBConnection.getConnection();
		String query = "UPDATE board"
				+ " SET b_view = COALESCE(b_view, 0) + 1 " 
		/* b_view의 값은 최초 null이기에 b_view+1의 값도 null이게 된다. 따라서 COALESCE 메서드를 활용하자
		  COALESCE(b_view, 0)는 b_view가 NULL인 경우 0을 반환하고, 그렇지 않으면 b_view 값을 그대로 반환.
		   반환된 값에 + 1 */
				+ " Where b_idx = ?";
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, b_idx);
		pstmt.executeUpdate(); // 쿼리 실행 메서드
		
	} catch (Exception e) {
		
	} finally {
		try {
			pstmt.close();
			conn.close();				
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
public void deletePost(String b_idx){
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try {
		conn = DBConnection.getConnection();
		String query = "DELETE"
				+ " From board"
				+ " Where b_idx = ?";
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, b_idx);
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
}
public void changePost(Board changeBoard){
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	try {
		conn = DBConnection.getConnection();
		String query = "UPDATE board SET b_title=?, b_content=?, b_date=NOW(), b_writer=? WHERE b_idx=?";
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, changeBoard.getTitle());
		pstmt.setString(2, changeBoard.getContent());
		pstmt.setString(3, changeBoard.getWriter());
		pstmt.setInt(4, changeBoard.getB_idx());
		pstmt.executeUpdate();
		
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
	
}

}
