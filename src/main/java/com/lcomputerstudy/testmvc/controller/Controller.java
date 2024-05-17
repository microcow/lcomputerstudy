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
import javax.servlet.http.HttpSession;

import com.lcomputerstudy.testmvc.service.java.BoardService;
import com.lcomputerstudy.testmvc.service.java.UserService;
import com.lcomputerstudy.testmvc.vo.java.Board;
import com.lcomputerstudy.testmvc.vo.java.Pagination;
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
	int page2;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");

		String requestURI = request.getRequestURI();
		// System.out.println(requestURI.toString()); 주소 확인
		String contextPath = request.getContextPath();
		// System.out.println(contextPath.toString()); 주소 확인
		String command = requestURI.substring(contextPath.length());
		// System.out.println(command.toString()); 주소 확인
		String view = null;
		String pw = null;
		String id = null;
		
		command = checkSession(request, response, command);
		
		switch (command) { // 권한처리로 인해 login.do/logout.do/list.do 외 case는 로그인필요 jsp로 이동됨
		case "/user-list.do": 
			
			UserService userService = UserService.getInstance();
			String reqPage = request.getParameter("page");
			if (reqPage != null)
				page = Integer.parseInt(reqPage);
			else page = 1;
			
			userService = UserService.getInstance();
			ArrayList<User> list = userService.getUsers3(page);
			Pagination pagination = new Pagination(page);
			
			request.setAttribute("list", list);
			request.setAttribute("pagination", pagination);
			
			view = "user/list";
			
		/* 3개씩 끊어서 list노출되게 하는 코드때문에 이전에 노출되던 코드 주석처리
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
			// user/insert.jsp에서 넘겨준 이름(name) 그대로 파라미터에 입력해야 가져옴
			
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
			
			
		case "/user-login.do":
			view = "user/login";
			break;
			
		case "/user-login-process.do":
			id = request.getParameter("login_id");
			pw = request.getParameter("login_password");
			
			userService = UserService.getInstance();
			user = userService.loginUser(id,pw);
			//System.out.println(user.getU_idx()); 값이 없는데 출력 시도 시 NullPointerException 발생
						
			if(user != null) {
				HttpSession session = request.getSession();
				// HttpSession: 이 클래스는 서버와 클라이언트 간의 세션을 나타냅니다. 세션은 클라이언트(예: 웹 브라우저)와 서버 간의 상태 정보를 저장하는 메커니즘입니다. 세션을 통해 사용자가 웹사이트를 탐색하는 동안 정보를 유지할 수 있습니다.
				/* request.getSession(): 이 메서드는 HttpServletRequest 객체(request)에서 호출됩니다. 이 메서드는 다음 중 하나를 수행합니다:
				클라이언트가 이미 세션을 가지고 있으면, 그 세션을 반환합니다.
				클라이언트가 세션을 가지고 있지 않으면, 새로운 세션을 생성하고 반환합니다.(request에는 유저가 입력한 정보가 들어있음) */
				
//				session.setAttribute("u_idx", user.getU_idx());
//				session.setAttribute("u_id", user.getU_id());
//				session.setAttribute("u_pw", user.getU_pw());
//				session.setAttribute("u_name", user.getU_name());
				session.setAttribute("user", user);
				/* setAttribute 
				첫 번째 인자: 문자열 "user"는 세션에 저장될 데이터의 키(key)입니다. 나중에 이 데이터를 불러올 때 이 키를 사용합니다.
				두 번째 인자: user은 세션에 저장될 실제 데이터입니다. 여기서는 사용자의 이름(또는 ID)입니다. 
				이 코드는 사용자가 로그인했을 때 user을 세션에 저장하여, 이후 요청에서도 사용자의 상태를 유지할 수 있게 합니다. 
				예를 들어, 사용자가 로그인한 후 다른 페이지로 이동할 때도 세션을 통해 로그인 상태를 확인할 수 있습니다.*/
				// request.getSession()로 세션을 생성하고 setAttribute로 생성한 세션을 저장

				view = "user/login-result";
			} else {
				view = "user/login-fail";
			}
			break;
			
			case "/logout.do":
				HttpSession session = request.getSession();
				session.invalidate();
				//session.invalidate() 메서드는 현재 세션을 무효화하여 세션의 모든 데이터를 삭제 (login-process.do에서 생성된 세션도 함께 삭제됨)
				view = "user/login";
				break;
			case "/access-denied.do":				
				view = "user/access-denied";		
				break;
				
			 // 게시판 작성 
			case "/create.do":
				/*
				★ create.do는 user-login-process.do를 거친다음 오는데 process.do에서 user 세션이 생성되고 삭제되지 않았기에
				해당 case에서 추가적으로 세션을 생성하거나 setAttribute하지 않아도 user.create.jsp에서 계속 ${sessionScope.user.u_name }와 같이 사용할 수 있음
				*/
				view = "user/create";
				break;
				
			case "/create-process.do":
				Board board = new Board();
				board.setTitle(request.getParameter("title"));
				board.setContent(request.getParameter("content"));
				board.setWriter(request.getParameter("writer"));
				board.setIdx(Integer.parseInt(request.getParameter("idx")));
				
				BoardService boardService = BoardService.getInstance();
				boardService.insertBoard(board);
				
				view = "user/login-result";
				break;
				
			case "/create.list.do":
				/// reqPage2의 정보가 어디서오는지
				/// getPostList 메서드 다듬기 (제대로 동작하지않고있음)
			
				String reqPage2 = request.getParameter("page2");
				if (reqPage2 != null)
					page2 = Integer.parseInt(reqPage2);
				else page2 = 1;
				
				boardService = BoardService.getInstance();
				ArrayList<Board> postList = boardService.getPostList(page2);
				pagination = new Pagination(page2);
				
				
				request.setAttribute("postList", postList);
				request.setAttribute("pagination", pagination);
				
				view = "user/postlist";
				break;
				

		}
		
		RequestDispatcher rd = request.getRequestDispatcher(view+".jsp");
		/* RequestDispatcher 란 현재 request에 담긴 정보를 저장하고 있다가
		그 다음 페이지 그 다음 페이지에도 해당 정보를 볼수있게 계속 저장하는 기능. */
		
		rd.forward(request, response);
		/* forward() 메소드는 현재 요청과 응답을 다른 서블릿이나 JSP로 전달합니다. 
		이 경우에는 선택된 JSP 페이지에 현재 요청(request)과 응답(response)을 전달하여 해당 JSP 페이지가 실행되고 클라이언트에게 응답을 전송합니다. */
		
		
	}
	
	public String checkSession(HttpServletRequest request, HttpServletResponse response, String command) {
		HttpSession session = request.getSession();
		
		String[] authList = {
				"/user-list.do"
				,"/user-insert.do"
				,"/user-insert-process.do"
				,"/user-detail.do"
				,"/user-delete.do"
				,"/user-change.do"
				,"/user-change2.do"
				,"/logout.do"
			};
		
		for (String item : authList) {
			if (item.equals(command)) {
				if (session.getAttribute("user") == null) {
					// user라는 세션이 있다면 fail, 없으면 true (== null이기때문에)
					// 세션 이름이 user인 이유는 login-prcess.do에서 session.setAttribute("user", user);로 설정했기 때문 (로그인을 할 때 세션을 생성(setAttribute)함)
					// 즉, 로그인을 한 상태라면 user라는 세션이 생성되어 있는 상태일 것이고 로그인한 상태가 아니라면 user라는 세션이 생성되어 있지 않을 것이다
					command = "/access-denied.do";
				}
			}
		}
		return command;
	}

  
}