package com.lcomputerstudy.testmvc.controller;

import java.io.IOException;
import java.sql.Date;
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
public class Controller extends HttpServlet { // HttpServlet를 꼭 extends해주어야함
	private static final long serialVersionUID = 1L; // 이건또 왜 적어야하지

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
			request.setAttribute("list", list); // request.setAttribute를 하는 이유
			// setAttribute를 해야 ${list}처럼 사용 가능?
			break;
		case "/user-insert.do":
			view = "user/insert";
			break;
		case "/user-insert-process.do":
			User user = new User(); 
			user.setU_id(request.getParameter("id"));
			user.setU_pw(request.getParameter("password"));
			user.setU_name(request.getParameter("name"));
			user.setU_tel(request.getParameter("tel1") + "-" + request.getParameter("tel2") + "-" + request.getParameter("tel3"));
			user.setU_age(request.getParameter("age"));	
			// request.getParameter() 메소드는 파라미터 이름을 기준으로 요청에서 값을 추출한다.
			// request.getParameter() 메소드는 HTML 폼에서 각 입력 요소의 name 속성 값과 일치하는 파라미터를 가져와서 그 값을 반환한다.
			view = "user/result";
			break;
		case "/user-detail.do":	
			UserService userService2 = UserService.getInstance();
			User user2 = userService2.getUsers2(request.getParameter("u_idx"));
			user2.setU_id(request.getParameter("id"));
			user2.setU_pw(request.getParameter("password"));
			user2.setU_name(request.getParameter("name"));
			request.setAttribute("user2", user2);
			view = "user/detail";
		
				
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(view+".jsp");
		/* RequestDispatcher 란 현재 request에 담긴 정보를 저장하고 있다가
		그 다음 페이지 그 다음 페이지에도 해당 정보를 볼수있게 계속 저장하는 기능. */
		
		rd.forward(request, response);
		/* forward() 메소드는 현재 요청과 응답을 다른 서블릿이나 JSP로 전달합니다. 
		이 경우에는 선택된 JSP 페이지에 현재 요청(request)과 응답(response)을 전달하여 해당 JSP 페이지가 실행되고 클라이언트에게 응답을 전송합니다. */
		
		
	}

}