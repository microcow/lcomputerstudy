<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
<form action="create.list.do" method="post">
	<input type="hidden" name="b_idx" value="${board2.b_idx}">
	<p> <input type="submit" value="돌아가기"></p>
</form>
	

</body>
</html>