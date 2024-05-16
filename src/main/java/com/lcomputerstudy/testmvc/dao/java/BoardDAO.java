package com.lcomputerstudy.testmvc.dao.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.lcomputerstudy.testmvc.database.java.DBConnection;
import com.lcomputerstudy.testmvc.vo.java.Board;
import com.lcomputerstudy.testmvc.vo.java.User;

public class BoardDAO {
	
	public void insertBoard(Board user) {
		Connection conn = null;
		PreparedStatement pstmt = null;
			
		try {
			conn = DBConnection.getConnection();
			String sql = "insert into user(u_id,u_pw,u_name,u_tel,u_age) values(?,?,?,?,?)";
			// 쿼리에서 insert문 작성 시 이렇게 (update, delete문과는 다름)
			pstmt = conn.prepareStatement(sql);
			//pstmt.setString(1, user.getU_id());
			//pstmt.setString(2, user.getU_pw());
			//pstmt.setString(3, user.getU_name());
			//pstmt.setString(4, user.getU_tel());
			//pstmt.setString(5, user.getU_age());
			//pstmt.executeUpdate();
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
}
