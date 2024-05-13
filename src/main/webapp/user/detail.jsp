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
<h1>회원 목록</h1>
<<<<<<< Updated upstream
		<form action="user-delete.do" name="user2" method="post">
		<table>
		<tr>
			<th>NO</th>
			<th>ID</th>
			<th>이름</th>
		</tr>
			<tr>
				<td>${user2.u_idx}</td>
				<td>${user2.u_id}</td>
				<td>${user2.u_name}</td>
			</tr>			
=======
	<table>
	
>>>>>>> Stashed changes
	</table>
	<input type="hidden" name="u_idx" value="${user2.u_idx}"> <!-- 히든 인풋으로 user-delete.do에 u_idx값 전달 (없으면 null값이 전달됨)-->
	<!-- hidden input 필드를 사용하는 이유는, 사용자에게 보이지 않으면서도 폼 데이터를 전송하기 위함입니다.
	보통 웹 페이지에서는 사용자가 입력한 정보를 전달할 때 폼의 input 태그를 사용합니다.
	그러나 사용자가 직접 입력하는 것이 아니라 서버 측에서 데이터를 설정하고 전송해야 할 때가 있습니다.
	이런 경우 hidden input 필드를 사용합니다. 여기서는 사용자가 삭제 버튼을 클릭할 때 해당 사용자의 u_idx 값을 서버로 전송해야 합니다.
	그러나 사용자는 이 값을 볼 필요가 없으며, 직접 입력할 필요도 없습니다. 따라서 hidden input 필드를 사용하여 이 값을 폼 데이터에 추가합니다.
	이렇게 하면 사용자에게는 보이지 않으면서도 해당 값이 함께 전송됩니다. 
	따라서 hidden input 필드를 추가함으로써, 사용자가 삭제하기 버튼을 클릭할 때 user-delete.do로 u_idx 값을 전달할 수 있습니다. -->
	<p> <input type="submit" value="삭제하기"></p>
	</form>

</body>
</html>