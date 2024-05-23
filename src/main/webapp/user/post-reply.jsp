<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>답글 작성하기</title>
</head>
<body>
<form action="create.process.do" name="user" method="post">
	<p> 작성자 : ${replyUser.u_name }님</p>
	<p> 원글 : <a href="post-detail.do?b_idx=${p_post.b_idx}">${p_post.title}</a></p> <!-- 원글 클릭 시 바로가기 -->
	<p> 제목 : <input type="text" name="title"></p>
	<p> 내용 : <input type="text" name="content"></p>
	<!-- 작성 날짜와 조회수는 글 목록에서 노출되도록 -->
	<input type="hidden" name="idx" value="${replyUser.u_idx }">
	<input type="hidden" name="writer" value="${replyUser.u_name }">
	<input type="hidden" name="p_post" value="${p_post.b_idx}"> <!-- p_post는 부모 게시글임 -->
	<input type="hidden" name="p_posttitle" value="${p_post.title}">
	<input type="hidden" name="depth" value="${p_post.depth}">
	<input type="hidden" name="grpord" value="${p_post.grpord}">
	<p> <input type="submit" value="답글달기"></p>
</form>
</body>
</html>