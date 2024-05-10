<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>회원 목록</h1>
	<table>
		<tr>
			<th>NO</th>
			<th>ID</th>
			<th>이름</th>
		</tr>
		<tr>
				<td>회원 번호</td>
				<td><%=u_idx %></td>		
			</tr>
			<tr>
				<td>회원 ID</td>
				<td><%=u_id %></td>		
			</tr>
			<tr>
				<td>회원 이름</td>
				<td><%=u_name %></td>		
			</tr>
			<tr>
				<td>회원 전화번호</td>
				<td><%=u_tel %></td>		
			</tr>
			<tr>
				<td>회원 나이</td>
				<td><%=u_age %></td>		
			</tr>	
	</table>
	<form action="user-insert-process.do" name="user" method="post">
	<p> <input type="submit" value="삭제하기"></p>
    </form>
</body>
</html>