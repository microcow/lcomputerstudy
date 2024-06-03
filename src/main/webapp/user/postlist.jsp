<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 목록</title>
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
	textarea {
            width: 10%;
            height: 16px; /* 높이를 늘립니다 */
            font-size: 16px; /* 글자 크기 */
        }
</style>
<body>
<h1>글 목록</h1>
<form action="post-search.do" method="get">
  <label for="lang">검색</label>
  <select name="search" id="lang">
  	<option value="select">search</option>
    <option value="b_title">제목</option>
    <option value="b_content">내용</option>
    <option value="b_title AND b_content">제목 + 내용</option>
  </select>
  <textarea name="content">검색어를 입력하세요.</textarea>
  <input type="submit" value="Submit" />
</form>
	<table >
		<tr>
			<td colspan="5">글 개수 ${pagination.postCount}</td>
		<tr>		
			<th>No</th>
			<th>작성자</th>
			<th>제목</th>
			<th>작성날짜</th>
			<th>조회수</th>
		</tr>
		<c:forEach items="${postList}" var="item2" varStatus="status"> <!-- varStatus는 딱히 신경x -->
			 <tr>
				<td>${item2.rownum}</td>
				<td>${item2.writer}</td>
				<td><a href="post-detail.do?b_idx=${item2.b_idx}">${item2.title}</a></td>
				<td>${item2.date}</td>
				<td>${item2.view}</td>
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
						<a href="create.list.do?page=${pagination.prevPage}">
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
								<a href="create.list.do?page=${i}">${i}</a>
							</li>
						</c:when>
					</c:choose>
			</c:forEach>
			 <c:choose>
				<c:when test="${ pagination.nextPage lt pagination.lastPage }">
					<li style="">
						<a href="create.list.do?page=${pagination.nextPage}">▶</a>
					</li>
				</c:when>
				<c:when test="${ pagination.nextPage ge pagination.lastPage}">
					<li style="display:none;">
						<a href="create.list.do?page=${pagination.nextPage}">▶</a>
					</li>
				</c:when>
			</c:choose> 
			<%--  <li>
				<a href="user-list.do?page=${pagination.nextPage}">▶</a>
			</li>  --%>
		</ul>
	</div>
	
<script>
        $(document).ready(function() {
            var textarea = $('#textarea');

            textarea.focus(function() {
                if ($(this).val() === '검색할 내용을 입력하세요.') {
                    $(this).val('');
                }
            });

            textarea.blur(function() {
                if ($(this).val() === '') {
                    $(this).val('검색할 내용을 입력하세요.');
                }
            });
        });
</script>
	
</body>
</html>