package com.lcomputerstudy.testmvc.dao.java;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.lcomputerstudy.testmvc.database.java.DBConnection;
import com.lcomputerstudy.testmvc.vo.java.Board;
import com.lcomputerstudy.testmvc.vo.java.Pagination;
import com.lcomputerstudy.testmvc.vo.java.Reply;

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
		ResultSet rs = null;
		int p_post = 0; // 저장되는 글이 원글일 경우 자신의 b_ibx 값을 담을 인스턴스
		
	
		try {
			String sql = "insert into board(b_title,b_content,b_date,b_writer,u_idx,p_post,depth,grpord) values(?,?,NOW(),?,?,?,?,?)";
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			if (board.getP_posttitle() != null) { // 작성 게시글이 답글일 경우 앞에 (RE:원글제목) 이 붙도록 설정
				pstmt.setString(1, "(RE:" + board.getP_posttitle() + ") " + board.getTitle());
			}
			else pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			// 세 번째 파라미터는 NOW()로 설정되어 있으므로 생략 
			pstmt.setString(3, board.getWriter());
			pstmt.setInt(4, board.getIdx()); // u_idx는 왜래키이다. 매칭되는 값이 없으면 오류 발생
			pstmt.setInt(5, board.getP_post());	
			pstmt.setInt(6, board.getDepth());
			pstmt.setInt(7, board.getGrpord());
			pstmt.executeUpdate(); // update문 실행 db에 저장.
				
				
			// ※ db에 저장해야 생성되는 primary key를 즉시 가져오는 방법
			pstmt.close(); // (동일한 conn에서)쿼리를 한번 사용한 후 재사용하려면 executeUpdate를(쿼리실행) 한 후 close하고 다시 prepareStatement 해야한다.
			sql = "SELECT LAST_INSERT_ID()";
			// 가장 최근에 성공적으로 수행된 INSERT 구문에 대해서 (update, delete 등 에는 영향받지 않음)자동으로 생성되는 AUTO_INCREMENT인 column 의 값을 반환
			// LAST_INSERT_ID()는 AUTO_INCREMENT 타입 컬럼을 반환하는 것이지 primary key를 반환하는 것이 아니다 (대부분의 primary key는 AUTO_INCREMENT 타입이겠지만 100%는 아님)
			// conn은 close하지 않았기에(세션유지) 다른 데이터와 혼동되지 않음
			pstmt = conn.prepareStatement(sql); // 새로운 PreparedStatement 객체 생성
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				p_post = (int)rs.getLong(1); // getLong(1)은 결과 집합(Result Set)의 첫 번째 열(column)의 값을 long타입으로 가져온다.
			}
				pstmt.close();
				
			// 자신(b_idx)의 p_post 값이 0일 경우(원글일 경우) p_post값을 자신의 b_idx값으로 세팅
			sql = "UPDATE board SET p_post = ? WHERE b_idx = ? AND p_post = ?";  
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, p_post);
			pstmt.setInt(2, p_post);
			pstmt.setInt(3, 0);
			pstmt.executeUpdate();
								
			
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

	
public void setp_post(){ // 작성 글이 원글일 경우 p_post값을 설정하는 메서드 // 해당 과정을 insertBoard 메서드에서 처리하도록 수정하였기에 안쓰이는 메서드임
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	ArrayList<Board> list = null;
	
	try {
		String sql = "SELECT * FROM board where p_post=0"; // p_post 값이 0인(원글인) 행
		conn = DBConnection.getConnection();
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
        list = new ArrayList<Board>();
        
        while(rs.next()){     
        	Board board = new Board();
        	board.setB_idx(rs.getInt("b_idx"));
        	board.setP_post(rs.getInt("b_idx"));
        	list.add(board);
        }
        pstmt.close();
        sql = "UPDATE board SET p_post = ? WHERE b_idx = ?"; // 저장된 행들의 p_post값을 b_idx값으로 변경
		pstmt = conn.prepareStatement(sql);
		for (Board board : list) {
	        pstmt.setInt(1, board.getB_idx());
	        pstmt.setInt(2, board.getB_idx());
	        pstmt.executeUpdate();
	    }
        
        
}catch( Exception ex) {
	System.out.println("SQLException : "+ex.getMessage());
	ex.printStackTrace();
} finally {
	try {
		if (rs != null) rs.close();
		if (pstmt != null) pstmt.close();
		if (conn != null) conn.close();	
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

}

public void setGrpord(){ // 모든 grpord값 +1하는 메소드인데 안쓰이는 메서드임(시행착오)
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try {
		String sql = "UPDATE board SET grpord = grpord +1";
		conn = DBConnection.getConnection();
		pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();

}catch( Exception ex) {
	System.out.println("SQLException : "+ex.getMessage());
	ex.printStackTrace();
} finally {
	try {
		if (rs != null) rs.close();
		if (pstmt != null) pstmt.close();
		if (conn != null) conn.close();	
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
}	

public void setReplyGrpord(int p_post, int grpord){// 부모글 grpord보다 큰애들 1씩 밀기
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try { 
		String sql = "UPDATE board SET grpord = grpord +1 WHERE p_post = ? AND grpord > ?"; 
		conn = DBConnection.getConnection();
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, p_post);
		pstmt.setInt(2, grpord);
		pstmt.executeUpdate();

}catch( Exception ex) {
	System.out.println("SQLException : "+ex.getMessage());
	ex.printStackTrace();
} finally {
	try {
		if (rs != null) rs.close();
		if (pstmt != null) pstmt.close();
		if (conn != null) conn.close();	
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
}	


 // 글 목록 불러오는 메서드 (https://blog.naver.com/lcomputerstudy/222078387068 참고)
 /// pagination이 board테이블이 아닌 user테이블의 수에 따라 다음페이지가 생성되므로 수정해야함		
public ArrayList<Board> getPostList(int page) { 
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
					// 여기서 group by p_post를 하게 될경우 그룹의 최상단의 행 하나 씩만 출력되므로 x
					.append("ORDER BY 		ta.p_post desc, ta.grpord ASC\n")
					.append("LIMIT			?, ?\n")
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
			board.setP_post(rs.getInt("p_post"));
			board.setDepth(rs.getInt("depth"));
			board.setGrpord(rs.getInt("grpord"));
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

	public void insertReply(Reply reply) { // 댓글 db에 저장하기
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int r_idx = 0;
	
		try {
			String sql = "insert into reply(r_writer,r_content,b_idx,r_date,p_post,grpord,depth) values(?,?,?,NOW(),?,?,?)";
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reply.getWriter());
			pstmt.setString(2, reply.getContent());
			pstmt.setString(3, reply.getB_idx());
			pstmt.setInt(4, reply.getP_post());
			pstmt.setInt(5, reply.getGrpord());
			pstmt.setInt(6, reply.getDepth());
			pstmt.executeUpdate();
					
			pstmt.close();
			sql = "SELECT LAST_INSERT_ID()";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			
			if (rs.next()) {
				r_idx = (int)rs.getLong(1); // 방금 생성된 댓글의 r_idx값 저장
			}
				pstmt.close();
				
			// 자신(r_idx)의 p_post 값이 0일 경우(첫 댓글일 경우) p_post값을 자신의 r_idx값으로 세팅
			sql = "UPDATE reply SET p_post = ? WHERE r_idx = ? AND p_post = ?";  
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, r_idx);
			pstmt.setInt(2, r_idx);
			pstmt.setInt(3, 0);
			pstmt.executeUpdate();
								
			
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

	public ArrayList<Reply> getReplyList(String grpord) { // 특정 글 댓글 리스트 불러오기
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Reply> list = new ArrayList<Reply>();
		
		try {
			conn = DBConnection.getConnection();
			String query = "SELECT * FROM reply WHERE b_idx = ? ORDER BY p_post DESC, grpord ASC";
	       	pstmt = conn.prepareStatement(query);
	       	pstmt.setString(1, grpord);
	        rs = pstmt.executeQuery();
	        
	        while(rs.next()){     
	        	Reply reply = new Reply();
	        	reply.setR_idx(rs.getString("r_idx"));
	        	reply.setB_idx(rs.getString("b_idx"));
	        	reply.setWriter(rs.getString("r_writer"));
	        	reply.setContent(rs.getString("r_content"));
	        	reply.setDate(rs.getString("r_date"));
	        	reply.setDepth(rs.getInt("depth"));
	        	reply.setGrpord(rs.getInt("grpord"));
	        	reply.setP_post(rs.getInt("p_post"));
       	       	list.add(reply);
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
	
	public void setCommentGrpord(int p_post, int grpord){// 원 댓글 grpord보다 큰애들 1씩 밀기
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try { 
			String sql = "UPDATE reply SET grpord = grpord +1 WHERE p_post = ? AND grpord > ?"; 
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, p_post);
			pstmt.setInt(2, grpord);
			pstmt.executeUpdate();

	}catch( Exception ex) {
		System.out.println("SQLException : "+ex.getMessage());
		ex.printStackTrace();
	} finally {
		try {
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (conn != null) conn.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	}	
	public Reply getReply(String r_idx) { // 댓글 정보 가져오기 메서드
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Reply reply = new Reply();
		
		try {
			conn = DBConnection.getConnection();
			String query = "Select *"
					+ " From reply"
					+ " Where r_idx = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, r_idx);
			rs = pstmt.executeQuery();
			 
			while(rs.next()) {
				reply.setB_idx(rs.getString("b_idx"));
				reply.setR_idx(rs.getString("r_idx"));
				reply.setWriter(rs.getString("r_writer"));
				reply.setContent(rs.getString("r_content"));
				reply.setDate(rs.getString("r_date"));
				reply.setP_post(rs.getInt("p_post"));
				reply.setDepth(rs.getInt("depth"));
				reply.setGrpord(rs.getInt("grpord"));
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
		return reply;
		}
	
	
	public void changeReply(Reply changeReply){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			String query = "UPDATE reply SET r_content=?, r_date=NOW() WHERE r_idx=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, changeReply.getContent());
			pstmt.setString(2, changeReply.getR_idx());		
			pstmt.executeUpdate();
			
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
	public void deleteReply(Reply changeReply){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			String query = "DELETE FROM reply WHERE r_idx=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, changeReply.getR_idx());		
			pstmt.executeUpdate();
			
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
	
	public ArrayList<Board> SelectBoard(String search, String content){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Board> serchResult = new ArrayList<>();
		
		try {
			conn = DBConnection.getConnection();
			String query = "SELECT * FROM board WHERE ";
			
			if (search.equals("b_title")) {
				query += "b_title like ?";
				}
			else if (search.equals("b_content")) {
				query += "b_content like ?";
				}
			else if (search.equals("b_title AND b_content")) {
				query += "b_title like ? OR b_content like ?";
				}
			else {
		         throw new IllegalArgumentException("Invalid search condition");
		      // 예외 처리: 유효하지 않은 검색 조건 (해당 throw가 실행되면 아래 코드는 실행되지 않음(즉시 중지))
		        }
			
			pstmt = conn.prepareStatement(query);
			
			if (search.equals("b_title AND b_content")) {
	            pstmt.setString(1, "\"" + "%" + content + "%" + "\"");
	            pstmt.setString(2, "\"" + "%" + content + "%" + "\"");
	        } else {
	            pstmt.setString(1, "\"" + "%" + content + "%" + "\"");
	        }
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){     
	        	Board board = new Board();
	        	board.setRownum(rs.getInt("ROWNUM"));
	        	board.setTitle(rs.getString("b_title"));
	        	board.setB_idx(rs.getInt("b_idx"));
	        	board.setWriter(rs.getString("b_writer"));
	        	board.setDate(rs.getString("b_date"));
	        	board.setView(rs.getInt("b_view"));
	        	serchResult.add(board);
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
		return serchResult;
	}
}




