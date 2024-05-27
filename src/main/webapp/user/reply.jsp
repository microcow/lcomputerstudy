<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글 작성하기</title>
</head>
<body>
<form action="creat-reply-process.do" name="user" method="post">
	<p> 원글 : <a href="post-detail.do?b_idx=${p_post.b_idx}">${p_post.title}</a></p> <!-- 원글 클릭 시 바로가기 기능 -->
	<p> 댓글 작성 : <input type="text" name="content"></p>
	<input type="hidden" name="b_idx" value="${p_post.b_idx}"> <!-- 원글 b_idx값 전달 --> <!-- input되지 않은 정보는 java에서 쓸수없음 -->
	<p> <input type="submit" value="댓글달기"></p>
</form>
</body>
</html>