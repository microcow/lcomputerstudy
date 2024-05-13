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
				<td><a href="user-detail.do">${user2.u_idx}</a></td>
				<td>${user2.u_id}</td>
				<td>${user2.u_name}</td>
			</tr>			
	</table>

</body>
</html>