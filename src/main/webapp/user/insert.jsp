<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 추가</title>
</head>
<body>

<h2>회원가입</h2>
<form action="user-insert-process.do" name="user" method="post">
	<p> 아이디 : <input type="text" name="id"><input type="button" value="아이디 중복 검사"></p>
	<p> 비밀번호 : <input type="password" name="password"></p>
	<p> 이름 : <input type="text" name="name"></p>
	<p> 연락처 : <input type="text" maxlength="4" size="4" name="tel1"> - 
				<input type="text" maxlength="4" size="4" name="tel2"> -
				<input type="text" maxlength="4" size="4" name="tel3">
	</p>
	<p> 나이 : <input type="text" name="age"></p>
	<p> <input type="submit" value="가입하기"></p>
	<!--  여기서 type="submit"은 "가입하기" 버튼을 생성합니다.
	 이 버튼을 클릭하면 <form> 요소 안에 있는 데이터가 /user-insert-process.do 경로로 POST 방식으로 전송된다. (전송된 데이터는 request가 받음)
	 -->
</form>

</body>
</html>