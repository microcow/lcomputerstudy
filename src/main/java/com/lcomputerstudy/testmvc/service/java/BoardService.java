package com.lcomputerstudy.testmvc.service.java;


import com.lcomputerstudy.testmvc.dao.java.UserDAO;

public class BoardService {
	
	private static BoardService service = null;
	private static UserDAO dao = null;
	
	
	public static BoardService getInstance(){
		if(service == null) {
			service = new BoardService();
			dao = UserDAO.getInstance();
		}
		return service;
	
	}
}
