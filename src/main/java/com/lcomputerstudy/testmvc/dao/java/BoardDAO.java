package com.lcomputerstudy.testmvc.dao.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.lcomputerstudy.testmvc.database.java.DBConnection;
import com.lcomputerstudy.testmvc.vo.java.Board;
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
			// rs = pstmt.executeQuery(); 결과를 받환받을 필요 없기에 executeQuery는 필요 x
			
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
		int pageNum = (page-1)*3;
		
		try {
			conn = DBConnection.getConnection();
			String query = new StringBuilder()

					.append("SELECT 		@ROWNUM := @ROWNUM + 1 AS ROWNUM,\n")
					.append("				ta.*\n")
					.append("FROM 			board as ta,\n")
					.append("				(SELECT @ROWNUM := 0) as tb\n")
					.append("LIMIT			?, 3\n")
					.toString();
	       	pstmt = conn.prepareStatement(query);
	       	pstmt.setInt(1, pageNum);
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
			board.setU_idx(getPostCount());
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


}
