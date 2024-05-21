package com.lcomputerstudy.testmvc.service.java;


import java.util.ArrayList;

import com.lcomputerstudy.testmvc.dao.java.BoardDAO;
import com.lcomputerstudy.testmvc.vo.java.Board;
import com.lcomputerstudy.testmvc.vo.java.User;

public class BoardService {
	
	private static BoardService service = null;
	private static BoardDAO dao = null;
	
	
	public static BoardService getInstance(){
		if(service == null) {
			service = new BoardService();
			dao = BoardDAO.getInstance();
		}
		return service;
	
	}
	public void insertBoard(Board board) {
		dao.insertBoard(board);
	}
	public ArrayList<Board> getPostList(int page) {
		return dao.getPostList(page);
	}
	public int getPostCount() {
		return dao.getPostCount();
	}
	public Board getPost(String b_idx) {
		return dao.getPost(b_idx);
	}
	public void updateView(String b_idx) {
		dao.updateView(b_idx);
	}
}
