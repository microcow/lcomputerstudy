package com.lcomputerstudy.testmvc.controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.lcomputerstudy.testmvc.service.java.UserService;
import com.lcomputerstudy.testmvc.vo.java.User;

@WebServlet("*.do") 
// @WebServlet("*.do")는 이 서블릿이 URL 패턴이 .do로 끝나는 모든 요청을 처리함을 나타냅니다. 즉, *.do로 끝나는 모든 요청은 이 서블릿으로 라우팅됩니다.
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");

		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		String view = null;
		
		switch (command) {
			case "/user-list.do":
				UserService userService = UserService.getInstance();
				ArrayList<User> list = userService.getUsers();
				view = "user/list";
				request.setAttribute("list", list);
				break;
			case "/user-insert.do":
				view = "user/insert";	// 작업중
				break;
		case "/user-insert-process.do":
				User user = new User();
				user.setU_id(request.getParameter("id"));
				user.setU_pw(request.getParameter("password"));
				user.setU_name(request.getParameter("name"));
				user.setU_tel(request.getParameter("tel1") + "-" + request.getParameter("tel2") + "-" + request.getParameter("tel3"));
				user.setU_age(request.getParameter("age"));
				
				userService = UserService.getInstance();
				userService.insertUser(user);
						
				view = "user/insert-result";
				break;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(view+".jsp");
		rd.forward(request, response);
		
		



	}

}