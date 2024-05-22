<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 작성하기</title>
</head>
<body>
<form action="post-change-complete.do" name="user" method="post">
	<p> 작성자 : ${sessionScope.user.u_name }님</p>
	<p> 제목 : <input type="text" name="title"></p>
	<p> 내용 : <input type="text" name="content"></p>
	<input type="hidden" name="u_idx" value="${sessionScope.user.u_idx }">
	<input type="hidden" name="writer" value="${sessionScope.user.u_name }">
	<input type="hidden" name="b_idx" value="${b_idx.b_idx }">
	<p> <input type="submit" value="수정하기"></p>
</form>
</body>
</html>