<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록2</title>
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
</style> <!-- 기존 list 코드는 list2에 있음 -->
<body>
<h1>회원 목록</h1>
	<table>
	<tr>
		<td colspan="3">전체 회원 수 : ${usercount}</td>
	<tr>
		<tr>
			<th>NO</th>
			<th>ID</th>
			<th>이름</th>
		</tr>
		<c:forEach items="${list}" var="user"> <!-- "list"라는 데이터 목록을 반복하며 각 요소를 "user"라는 변수에 할당-->
			<tr>
				<td><a href="user-detail.do?u_idx=${user.u_idx}">${user.u_idx}</a></td>
				<td>${user.u_id}</td>
				<td>${user.u_name}</td>
			</tr>			
		</c:forEach>	
	</table>
	<div>
		<ul>
			<li><a href="user-list.do?page=1">1</a></li>
			<!-- 매개변수로 "page=1"를 가진 "user-list.do"에 대한 요청이 생성 -->
			<li><a href="user-list.do?page=2">2</a></li>
			<li><a href="user-list.do?page=3">3</a></li>
			<li><a href="user-list.do?page=4">4</a></li>
			<li><a href="user-list.do?page=5">5</a></li>
		</ul>
	</div>
</body>
</html>