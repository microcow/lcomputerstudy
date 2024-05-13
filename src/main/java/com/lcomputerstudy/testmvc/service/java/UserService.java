package com.lcomputerstudy.testmvc.service.java;
import java.util.ArrayList;
import com.lcomputerstudy.testmvc.dao.java.UserDAO;
import com.lcomputerstudy.testmvc.vo.java.User;

public class UserService {
	private static UserService service = null;
	private static UserDAO dao = null;
	
	private UserService() {		
	}
	
	public static UserService getInstance(){
		if(service == null) {
			service = new UserService();
			dao = UserDAO.getInstance();
		}
		return service;
	}
	public ArrayList<User> getUsers(){
		return dao.getUsers();
	}
	public User getUsers2(String u_idx){
		return dao.getUsers2(u_idx);
	}
	public User deleteUser(String u_idx) {
		return dao.deleteUser(u_idx);
	}
	
	public void insertUser(User user) {
		dao.insertUser(user);
	}

}
