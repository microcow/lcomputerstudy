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
	private static final long serialVersionUID = 1L;
	// Serializable 인터페이스를 구현 중인 클래스는 private static final long serialVersionUID 변수를 반드시 가져야한다. (클래스의 충돌을 미연에 방지하기 위한 변수)(미구현 시 에러)
	// 다른 네트워크와 serialVersionUID의 값이 일치할 때만 데이터를 주고받을 수 있다
	// HttpServlet 클래스는 Serializable 인터페이스를 직접 구현하지 않습니다. 그러나 HttpServlet 클래스는 GenericServlet 클래스를 상속하고 있으며, GenericServlet 클래스는 Serializable 인터페이스를 구현하고 있습니다.
	/// 정확히 어떤 구조로 작동하는지
	
	int usercount = 0;
	int page;
	
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
			String reqPage = request.getParameter("page");
			System.out.println(reqPage);
			if (reqPage != null) { 
				page = Integer.parseInt(reqPage);
				page = (page-1)*3;
			}
			userService = UserService.getInstance();
			ArrayList<User> list = userService.getUsers3(page);
			usercount = userService.getUsersCount();
			request.setAttribute("list", list);
			request.setAttribute("usercount", usercount);
			
			view = "user/list";
		/* 3개씩 끊어서 list노출되게 하는 코드때문에 이전에 노출되던 list들 주석처리 하였음
		   UserService userService = UserService.getInstance();
			ArrayList<User> list = userService.getUsers();
			usercount = userService.getUsersCount();
			request.setAttribute("list", list);
			request.setAttribute("usercount", usercount);
			view = "user/list";
			request.setAttribute("list", list);*/ // request.setAttribute를 하는 이유
			// setAttribute를 해야 ${list}처럼 사용 가능? : yes
			/* 첫번째 아규먼트는 넘기는 이름 즉, key값이다.
			(jsp인 user-list.do에서 받을 때 list라는 이름을 그대로 사용해야한다)*/
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
			
			userService = UserService.getInstance();
			userService.insertUser(user);

			view = "user/result";
			break;
		case "/user-detail.do":	
			userService = UserService.getInstance();
			User user2 = userService.getUsers2(request.getParameter("u_idx"));
			request.setAttribute("user2", user2);
			view = "user/detail";
			break;
			
		case "/user-delete.do":
			System.out.println(request.getParameter("u_idx")); // 값을 전달받고 있지 못함(해결)
			userService = UserService.getInstance();
			userService.deleteUser(request.getParameter("u_idx"));
			view = "user/delete";
			break;
			// 삭제는 잘 되긴한데 userdetail이 생략되고 삭제됨 (삭제 버튼을 누르지 않고 삭제됨) 
			// ㄴ> deatail에 break;가 생략되어있어서 switch문을 빠져나오지 않고 바로 다음 case가 실행되어서 그럼
		
		case "/user-change.do":
			userService = UserService.getInstance();
			User userIdx = userService.getUsers2(request.getParameter("u_idx"));
			System.out.println(userIdx.toIdx()); // userIdx에 값이 제대로 저장되는지 확인
			request.setAttribute("user", userIdx);
			view = "user/change";
			break;
			
		case "/user-change2.do":
			//System.out.println(request.getParameter("u_idx"));
			User user11 = new User(); 
			int idx = (Integer.parseInt(request.getParameter("u_idx")));
			user11.setU_idx(idx);
			/* 지금까지 자동으로 부여되는 idx값을 직접 호출하거나 사용한 적은 없었음. 그러나 값을 변경하기 위해선 idx값을 사용해야 한다.
			다만, u_idx는 int타입이라 user11.setU_idx(request.getParameter("u_idx"))가 불가능하다.
			어떻게 u_idx값을 user11에 저장해서 넘겨줄 것인가 */
			/* request.getParameter는 문자열을 반환하지만 setU_idx는 int타입을 파라미터로 받고있기에
			숫자를 문자열로 바꿔주는 Integer.parseInt 메서드를 사용해봤지만 런타임에서 실패 */
			// ㄴ> 다시 하니 제대로 작동함..
			user11.setU_id(request.getParameter("id"));
			user11.setU_pw(request.getParameter("password"));
			user11.setU_name(request.getParameter("name"));
			user11.setU_tel(request.getParameter("tel1") + "-" + request.getParameter("tel2") + "-" + request.getParameter("tel3"));
			user11.setU_age(request.getParameter("age"));
			
			
			userService = UserService.getInstance();
			userService.changeUser(user11);
			view = "user/finish";
			break;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(view+".jsp");
		/* RequestDispatcher 란 현재 request에 담긴 정보를 저장하고 있다가
		그 다음 페이지 그 다음 페이지에도 해당 정보를 볼수있게 계속 저장하는 기능. */
		
		rd.forward(request, response);
		/* forward() 메소드는 현재 요청과 응답을 다른 서블릿이나 JSP로 전달합니다. 
		이 경우에는 선택된 JSP 페이지에 현재 요청(request)과 응답(response)을 전달하여 해당 JSP 페이지가 실행되고 클라이언트에게 응답을 전송합니다. */
		
		
	}
  
}