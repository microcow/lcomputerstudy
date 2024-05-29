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
	<p> <input type="submit" value="댓글달기" class="btnComment2"></p>
</form>
<table>
	<tr style="display: none;">
  	      <td colspan="4">
  	       		<textarea rows="2" cols="80"></textarea>
  	       		<button type="button" class="btnCommentProc">등록</button>
  	       		<!-- submit타입와 button타입의 차이? -->
  	      </td>
	</tr>
</table>
	
<form action="create.list.do" method="post">
	<input type="hidden" name="b_idx" value="${board2.b_idx}">
	<p> <input type="submit" value="돌아가기"></p>
</form>

 <h2>댓글</h2>
    <table id="commentList">
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
					<p> <input type="submit" value="댓글달기" class="btnComment"></p>
				</form>
			</td>
  	       </tr>
  	       <tr style="display: none;">
  	       	<td colspan="4">
  	       			<textarea rows="2" cols="80"></textarea>
  	       			<button type="button" class="btnCommentProc">등록</button> 
  	       			<!-- submit타입와 button타입의 차이? -->
  	       	</td>
  	       </tr>
	    </c:forEach>
	</table>

		
	
	
<script>
//// 해야할것 : java에서 redirect로 댓글 작성 바꾸기
//// ajax로 대댓글 작성 시 목록 즉시 갱신, 수정, 삭제기능까지
//// 등록버튼 누르면 db에 저장 후 즉시 화면갱신되도록 바꿔야함 (등록 버튼을 눌렀을 때 각종 정보(b_idx, 내용, r_idx 등등)들을 어떻게 전달할 것인가)

/*
$('.btnCommentProc').click(function () { //★ .click은 오류가 발생할 수 있기에 .on함수를 사용할 것
});
*/
//prop('tagName') : 태그 이름을 화면에 표시.
// 바인딩 하는 대상이 class명일 경우 .btnComment, id이름일 경우 #btnComment, 모든 tag를 대상으로 할 경우 btnComment

$(document).on('click', '.btnComment', function(e) { // $(document) : 현재 jsp 문서 // 댓글의 '댓글달기' 누를 시 여기로
	// .on('click', '.btnCommentProc',function () : .btnComment이라는 클래스명을 가진걸 click(이벤트)할 경우 아래 함수(fuction) 실행
			// e에는 이벤트의 객체(click의 결과)가 담겨이음
	e.preventDefault(); // e.preventDefault(); : 이벤트의 기본 동작을 막는 메서드 (여기선 클릭하더라도 폼 제출(input) 기능이 실행되지 않도록 막고있음)
	console.dir($(this).parent().parent().parent().parent().next().css('display', '')); // 태그 찾는법 1
	// $(this) : 지금태그
	// .parent() : 호출자의 부모(한단계 상위)태그
	// .next(): 호출자와 동등한 단계에서 다음태그
	// 즉, 이벤트(댓글달기)의 현재태그(input)의 부모태그(P)의 부모태그(form)의 부모태그(td)의 부모태그(tr)의 다음태그(tr)의 css disply값을 ''로 변경
});

$(document).on('click', '.btnComment2', function(e) { // 원글의 '댓글달기' 누를 시 여기로
	e.preventDefault();
	
	// console.dir($(this).parent().parent().next().next().css('display', ''));
	
	$(this).closest('form').next('table').find('tr').css('display', ''); // 태그 찾는법 2
  	// 이 코드는 선택한 요소를 기준으로 가장 가까운 <form> 요소를 찾고, 그 다음에 나오는 <table> 요소를 선택한 후, 그 안에 있는 모든 <tr> 요소를 선택하여 스타일을 변경합니다.
});

$(document).on('click', '.btnCommentProc', function () { /// 댓글 '등록'버튼 누를 시 여기로
	$.ajax({ // 페이지를 새로 고치지 않고도 데이터를 동적으로 로드하거나 전송
        url: "ajax-test.do", // click할 경우 url로 요청(호출) // 서버는 템플릿 파일을 로드하여 해당 내용을 응답으로 전송할 것입니다.
        type: "POST",
        success: function(res){ // 요청이 성공(success)할 경우 아래 함수 실행 // 요청으로 부터 받은 응답 데이터가 res에 저장
            $('#commentList').html(res); // 요청이 성공하면 응답으로 받은 데이터를 #commentList라는 HTML 요소 내에 넣습니다.
            // 즉, #commentList라는 ID를 가진 태그 내부는 res라는 html로 교체된다
        },
        error: function(){
            $("#data").text("An error occurred");
        }
    });
});





/*

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