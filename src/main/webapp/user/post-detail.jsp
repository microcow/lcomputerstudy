<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
<style>
	table {
		border-collapse:collapse;
	}
	table tr th {
		font-weight:700;
	}
	table tr td, table tr th {
		border:1px solid #818181;
		width:200px;
		text-align:center;
	}
	a {
		text-decoration:none;
		color:#000;
		font-weight:700;
	}
	 .link-button {
        color: blue; /* 파란색 */
        text-decoration: none; /* 밑줄 제거 */
    }
</style>
<body>
		<form action="post-delete.do" method="post">
		<table>
		<tr>
			<th>작성자</th>
			<th>제목</th>
			<th>내용</th>
		</tr>
			<tr>
				<td>${board2.writer}</td>
				<td>${board2.title}</td>
				<td>${board2.content}</td>
			</tr>
			
			
			
	</table>
	
	<input type="hidden" name="b_idx" value="${board2.b_idx}">
	<input type="hidden" name="u_idx" value="${board2.u_idx}">
	<input type="submit" value="삭제하기">
	</form>
	
	<form action="post-change.do" method="post">
	<input type="hidden" name="b_idx" value="${board2.b_idx}">
	<input type="hidden" name="u_idx" value="${board2.u_idx}">
	<p> <input type="submit" value="수정하기"></p>
</form>
	
	<form action="post-reply.do" method="post">
	<input type="hidden" name="b_idx" value="${board2.b_idx}"> <!-- 답글다는 글 b_idx값 전달 -->
	<p> <input type="submit" value="답글달기"></p>
</form>
<!-- <p> <input type="button" value="답글달기" id="btnReply"></p> --> <!-- js와 스크립트로 버튼 동작 시켜보기 -->

<form action="creat-reply.do" method="post">
	<input type="hidden" name="b_idx" value="${board2.b_idx}"> <!-- 댓글다는 글 b_idx값 전달 -->
	<p> <input type="submit" value="댓글달기"></p>
</form>	
	
<form action="create.list.do" method="post">
	<input type="hidden" name="b_idx" value="${board2.b_idx}">
	<p> <input type="submit" value="돌아가기"></p>
</form>

 <h2>댓글</h2>
    <table>
        <tr>
            <th>작성자</th>
            <th>댓글 내용</th>
            <th>작성일</th>
        </tr>
        <c:forEach items="${replyList}" var="reply" > <!-- "replyList"를 java에서 전달받고 reply에 foreach로 저장함 -->
           <tr>
   	         <td>${reply.writer}</td>
   	         <td>${reply.content}</td>
   	         <td>${reply.date}</td>
   	         <td>
   	         <form action="creat-reply.do" method="post">
			<input type="hidden" name="b_idx" value="${board2.b_idx}">
			<input type="hidden" name="r_idx" value="${reply.r_idx}">
			<p> <input type="submit" value="댓글달기"></p>
			</form>
			</td>
  	       </tr>
	    </c:forEach>
	</table>

		
	
	
<script> /*

const btnReply = document.getElementById("btnReply"); */

/*function handleBtnReply() {
	alert('js');
	window.location.href = "post-reply.do?b_idx=${board2.b_idx}";
} 스크립트로 작성 방법 1 */

/*
btnReply.addEventListener('click', function () {
	window.location.href = "post-reply.do?b_idx=${board2.b_idx}";
});  스크립트로 작성 방법 2 */

/* js로 작성했을때 1
$('#btnReply').on('click', function () {
	alert("update");
	window.location.href = "post-reply.do?b_idx=${board2.b_idx}";
});*/
</script> 
</body>
</html>