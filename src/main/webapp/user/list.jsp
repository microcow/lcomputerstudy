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
	h1 {
		text-align:center;
	}
	table {
		border-collapse:collapse;
		margin:40px auto;
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
	ul {
		width:600px;
		height:50px;
		margin:10px auto;
	}
	li {
		list-style:none;
		width:50px;
		line-height:50px;
		border:1px solid #ededed;
		float:left;
		text-align:center;
		margin:0 5px;
		border-radius:5px;
	}
</style>
<body>
<h1>회원 목록</h1>
	<table >
		<tr>
			<td colspan="3">전체 회원 수 : ${pagination.userCount}</td>
		<tr>		
			<th>No</th>
			<th>ID</th>
			<th>이름</th>
		</tr>
		<c:forEach items="${list}" var="item" varStatus="status"> <!-- varStatus는 딱히 신경x -->
		<!-- java에서 list라는 이름으로 받아온 ArrayList를 받아온 다음, forEach문으로
		 "item"에 첫번째 요소부터 저장하고 아래 코드 실행 후 그 다음 요소~~ 반복 -->
			 <tr>
				<td><a href="user-detail.do?u_idx=${item.u_idx}">${item.rownum}</a></td> <!-- rownum 번호를 누르면 u_idx를 detail.do로 전달 -->
				<td>${item.u_id}</td>
				<td>${item.u_name}</td>
				<!-- 위 $코드는 item객체의 getU_id와 getU_name 메서드를 호출한 후 결과값인 u_id와 u_name을 가져온다
				즉, 각 메서드(getU_id, getU_name)가 구현되어있지 않으면 오류가 발생하며, 중간 메서드 대문자처리를 하지 않아도 오류가 발생한다 -->
		     <tr>
		</c:forEach>
	</table>
<!-- 아래부터 pagination -->
	<div>
		<ul>
			 <c:choose>
				<c:when test="${ pagination.prevPage lt 5 }"> <!-- lt: "less than"의 약자로, 작은지 여부를 확인하는 조건 연산자 -->
					<!-- lt: 이전 페이지가 5보다 작은가?"라는 질문입니다. 페이지가 5보다 작으면 조건은 참(true)이고, 그렇지 않으면 거짓(false) -->
					<li style="display:none;">
						<span>◀</span>
					</li>
				</c:when>
				<c:when test="${ pagination.prevPage ge 5}">
				<!-- ge : 이전 페이지가 5 이상인가?"를 묻습니다. 이전 페이지가 5 이상이면 조건은 참(true)이고, 그렇지 않으면 거짓(false) -->
					<li>
						<a href="user-list.do?page=${pagination.prevPage}">
							◀
						</a>
					</li>
				</c:when>
			</c:choose> 
			<c:forEach var="i" begin="${pagination.startPage}" end="${pagination.endPage}" step="1">
				
					<c:choose>
						<c:when test="${ pagination.page eq i }">
						<!-- eq : 현재 페이지가 i와 같은지"를 묻습니다. 현재 페이지가 i와 같으면 조건은 참(true)이고, 그렇지 않으면 거짓(false) -->
							
							<li style="background-color:#ededed;">
								<span>${i}</span>
							</li>
						</c:when>
						<c:when test="${ pagination.page ne i }">
						<!-- ne : 현재 페이지가 i와 같지 않은지"를 묻습니다. 현재 페이지가 i와 같지 않으면 조건은 참(true)이고, 그렇지 않으면 거짓(false) -->
							<li>
								<a href="user-list.do?page=${i}">${i}</a>
							</li>
						</c:when>
					</c:choose>
			</c:forEach>
			 <c:choose>
				<c:when test="${ pagination.nextPage lt pagination.lastPage }">
					<li style="">
						<a href="user-list.do?page=${pagination.nextPage}">▶</a>
					</li>
				</c:when>
				<c:when test="${ pagination.nextPage ge pagination.lastPage}">
					<li style="display:none;">
						<a href="user-list.do?page=${pagination.nextPage}">▶</a>
					</li>
				</c:when>
			</c:choose> 
			<%--  <li>
				<a href="user-list.do?page=${pagination.nextPage}">▶</a>
			</li>  --%>
		</ul>
	</div>
</body>
</html>