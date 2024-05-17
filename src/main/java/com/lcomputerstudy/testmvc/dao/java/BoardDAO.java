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
	
	public void insertBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
			
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
public ArrayList<Board> getPostList(int page) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Board> list = null;
		int pageNum = (page-1)*3;
		
		try {
			conn = DBConnection.getConnection();
			String query = new StringBuilder()

					.append("SELECT 		@ROWNUM := @ROWNUM - 1 AS ROWNUM,\n")
					.append("				ta.*\n")
					.append("FROM 			board as ta,\n")
					.append("				(SELECT @ROWNUM := (SELECT	COUNT(*)-?+1 FROM board)) as tb\n")
					.append("LIMIT			?, 3\n")
					.toString();
	       	pstmt = conn.prepareStatement(query);
	       	pstmt.setInt(1, pageNum);
	       	pstmt.setInt(2, pageNum);
	        rs = pstmt.executeQuery();
	        list = new ArrayList<Board>();

	        while(rs.next()){     
	        	Board board = new Board();
	        	board.setRownum(rs.getInt("ROWNUM"));
	        	board.setTitle(rs.getString("title"));
	        	System.out.println(board.getTitle());
	        	board.setB_idx(rs.getInt("b_idx"));
	        	System.out.println(board.getB_idx());
	        	board.setWriter("writer");
	        	System.out.println(board.getWriter());
	        	board.setView(rs.getInt("view"));
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
}
