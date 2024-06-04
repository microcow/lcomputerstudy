<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인중입니다.</title>
</head>
<style>
	body {
		margin:0;
		padding:0;
	}
	div:nth-child(1) {
		background-color:rgba(75,189,217,0.1);
		padding:10px 30px;
		font-size:1.2rem;
		font-weight:700;
	}
	div:nth-child(2) {
		width:200px;
	}
	div ul {
		width:100%;
		text-align:center;
		list-style:none;
		padding:0;
	}
	div ul li {
		padding:10px;
		font-size:1rem;
		background-color:rgba(75,189,217,0.1);
		border-radius:10px;
		margin:10px;
		font-weight:700;
		box-shadow:2px 3px 3px #bbbbbb;
		
	}
	div ul li a {
		text-decoration:none;
		color:#333333;
	}
</style>
<body>
<div>
${sessionScope.user.u_name }님 
<!-- sessionScope: JSP의 네 가지 범위(scope) 중 하나로,
 세션 객체에 저장된 데이터에 접근할 수 있습니다.
 세션 범위는 사용자가 브라우저를 닫거나 서버에서 세션이 무효화될 때까지 데이터를 유지합니다.
 즉, Controller에서 session.setAttribute("user", user); 코드를 통해
 user라고 세션이름을 저장했기때문에 접근가능 -->
</div>
<div>
	<ul>
		<li><a href="user-list.do">회원 목록</a></li>
		<li><a href="logout.do">로그아웃</a></li>
		<li><a href="create.do">글 작성</a></li>
		<li><a href="create.list.do?search=${search.search}&content=${search.content}&page=1">글 목록</a></li>
	</ul>
</div>
</body>
</html>