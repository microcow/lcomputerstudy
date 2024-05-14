<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="user-change2.do" name="user" method="post">
	<p> 회원번호 : ${user.u_idx}
	<p> 변경할 아이디 : <input type="text" name="id"><input type="button" value="아이디 중복 검사"></p>
	<p> 변경할 비밀번호 : <input type="password" name="password"></p>
	<p> 변경할 이름 : <input type="text" name="name"></p>
	<p> 변경할 연락처 : <input type="text" maxlength="4" size="4" name="tel1"> - 
				<input type="text" maxlength="4" size="4" name="tel2"> -
				<input type="text" maxlength="4" size="4" name="tel3">
	</p>
	<p> 변경할 나이 : <input type="text" name="age"></p>
	<p> <input type="submit" value="변경하기"></p>
	<!-- 문제는 유저가 회원번호를 직접 칠 일이 없기에 회원번호가 전달되지 않음 --> <!-- 히든타입으로 함께 전달하여 해결 -->
	 <input type="hidden" name="u_idx" value="${user.u_idx}">	 
</form>
</body>
</html>