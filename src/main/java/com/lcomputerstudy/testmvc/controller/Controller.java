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
			// jsp에서 넘어온 값은 request에 저장된다.
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
			// setAttribute를 해야 ${user2}처럼 jsp에서 사용 가능
			view = "user/detail";
			break;
			
		case "/user-delete.do":
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
				예를 들어, 사용자가 로그인한 후 다른 페이지로 이동할 때도 세션을 통해 로그인 상태를 확인할 수 있습니다.
				request.setAttribute와 동일하게 동작함 즉, jsp로 넘어가더라도 user라는 이름으로 사용 가능 */
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
				
			case "/create.process.do": // 최초 작성, 답글 작성 모두 글 작성 시 create.process.do로 오게 되어있음
				Board board = new Board();
				BoardService boardService = BoardService.getInstance();
				board.setTitle(request.getParameter("title"));
				board.setContent(request.getParameter("content"));
				board.setWriter(request.getParameter("writer"));
				board.setIdx(Integer.parseInt(request.getParameter("idx")));

				if (request.getParameter("p_post") != null) { // p_post값 세팅 (원글일 경우 0으로 셋팅, 답글일 경우 부모 b_idx값으로 셋팅)
					board.setP_post(Integer.parseInt(request.getParameter("p_post")));
				}
				
				if (request.getParameter("p_post") != null) { // 답글일 경우 부모의 grpord보다 큰애들은 자신의 grpord +1로 바꾸는 메소드 실행하고 그 후에 나는 부모grpord+1 (이러면 최신글일수록 부모글 바로 아래에 올 수 있음)
					boardService.setReplyGrpord(Integer.parseInt(request.getParameter("p_post")), Integer.parseInt(request.getParameter("grpord")));
					board.setGrpord(Integer.parseInt(request.getParameter("grpord"))+1); // 답글일 경우에 부모 grport값 +1
				}
				else {
					// boardService.setGrpord(); 안쓰는 메소드 (시행착오)
					board.setGrpord(0); // 원글일 경우 본인의 값은 0으로 셋팅
				}			

				if (request.getParameter("p_posttitle") != null) { // 작성 글이 답글일 경우 p_posttitle 값 세팅
					board.setP_posttitle(request.getParameter("p_posttitle"));
				}				
				board.setDepth(Integer.parseInt(request.getParameter("depth"))+1); // depth의 default값 1로 설정, 답글일 경우 부모의 depth값+1			

				int post = boardService.insertBoard(board); // 글 db에 저장(저장 시 primary key값인 b_idx값 생성됨)
				//// 여기까지함. insertBoard에서 바로 p_post값 가져오는거 까지 설정했으니 post값을 setp_post메소드에 넘겨주고 자신의 p_post값을 넘겨받은 post값으로 설정하게 바꾸면될듯
				boardService.setp_post(); // 생성되는 글이 원글일 경우(p_post값이 0일경우) p_post값 세팅(자신의 b_idx값으로)
						
				view = "user/login-result";				
				break;
				
			case "/create.list.do":	
				String reqPage2 = request.getParameter("page");
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
			
			case "/post-detail.do":
				boardService = BoardService.getInstance();
				boardService.updateView(request.getParameter("b_idx")); // 조회수 증가
				Board board2 = new Board();
				board2 = boardService.getPost(request.getParameter("b_idx")); // board2의 값 세팅
				request.setAttribute("board2", board2); 
				// getPost메소드를 통해 board2에 세터로 셋팅된 값만 jsp에 전달됨 (즉, 셋팅된 값만 post-detail jsp에서 사용 가능)
				
				view = "user/post-detail";
				break;
				
			case "/post-delete.do": // 세션 u_idx와 글 작성자의 u_idx가 일치하는지 확인
				session = request.getSession();
				boardService = BoardService.getInstance();	
				
				/* userService = UserService.getInstance();
				   int sessionU_idx = userService.sessionU_idx(session); 
				    로그인 되어있는 세션의 u_idx가져오는 방법 1 */
							
				Object userObj = session.getAttribute("user"); 
				// getAttribute는 return타입이 Object 
				// 세션은 login-process.do에서 "user"값이 셋팅되어있음
				// 굳이 session.getAttribute("user");로 안불러오고 이미 user라는 객체가 login-process.do에서 생성되어있기에 바로 user객체를 써도됨 (예시. user.getU_idx();) (단, user객체가 case내에서 생성되어있으면 안됨)
				User idxget = (User)userObj;
				int userU_idx = idxget.getU_idx();
				// 로그인 되어있는 세션의 u_idx가져오는 방법 2 
				
				int postU_idx = Integer.parseInt(request.getParameter("u_idx")); // 글 작성자 u_idx 가져오기
								
				if(userU_idx == postU_idx) {
					boardService.deletePost(request.getParameter("b_idx"));
					view = "user/post-delete";
				}
				else view = "user/access-denied";
				break;
											
			case "/post-change.do": // 게시물 수정 권한 확인
				session = request.getSession();
				boardService = BoardService.getInstance();	
				Board b_idx = new Board();
				b_idx.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
										
				userObj = session.getAttribute("user");
				idxget = (User)userObj;
				userU_idx = idxget.getU_idx(); 
				
				postU_idx = Integer.parseInt(request.getParameter("u_idx"));
								
				if(userU_idx == postU_idx) {
					request.setAttribute("b_idx", b_idx);
					view = "user/post-change";
				}
				else view = "user/access-denied";
				break;
				
			case "/post-change-complete.do": // 수정 게시물 적용
				Board changeBoard = new Board();
				if (request.getParameter("title") != null) {
				changeBoard.setTitle(request.getParameter("title"));
				changeBoard.setContent(request.getParameter("content"));
				changeBoard.setWriter(request.getParameter("writer"));
				changeBoard.setB_idx(Integer.parseInt(request.getParameter("b_idx")));				
				boardService = BoardService.getInstance();
				boardService.changePost(changeBoard);
				view = "user/post-change-complete";
				}
				else view = "user/access-denied";
				break;
				
			case "/post-reply.do": // 답글기능
				boardService = BoardService.getInstance();
				
				session = request.getSession();
				userObj = session.getAttribute("user"); 
				User replyUser = (User)userObj; // 답글 작성자 정보 저장
				
				String p_postB_idx = request.getParameter("b_idx"); // 원글 b_idx 저장
				Board p_post = boardService.getPost(p_postB_idx); // 원글 정보 저장
				
				request.setAttribute("replyUser", replyUser);
				request.setAttribute("p_post", p_post);
							
				view = "user/post-reply";
				break;
				/// https://ssmlim.tistory.com/7 계층형 답글 참고 블로그
				/// 난관 1(해결완료). 글 작성 시 원글일 경우 자신의 b_idx값을 어떻게 p_post로 설정하게 할것인가 
				/// 난관 2. depth값은 바로 직전글에서 +1 되면 되지만, grpord는 원글은 무조건 0이고 최신 글의 값이 빨리와야함

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
				,"/user-detail.do"
				,"/user-delete.do"
				,"/user-change.do"
				,"/user-change2.do"
				,"/logout.do"
				,"/create.list.do"
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