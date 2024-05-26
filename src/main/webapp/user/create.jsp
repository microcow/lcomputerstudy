<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 작성하기</title>
</head>
<body>
<form action="create.process.do" name="post" method="post">
	<p> 작성자 : ${sessionScope.user.u_name }님</p>
	<p> 제목 : <input type="text" name="title"></p>
	<p> 내용 : <input type="text" name="content"></p>
	<!-- 작성 날짜와 조회수는 글 목록에서 노출되도록 -->
	<input type="hidden" name="idx" value="${sessionScope.user.u_idx }">
	<input type="hidden" name="writer" value="${sessionScope.user.u_name }">
	<input type="hidden" name="depth" value="0">
	<input type="hidden" name="grpord" value="0">
	<p> <input type="submit" value="작성하기"></p>
</form>
</body>
</html>