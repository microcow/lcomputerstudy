<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- foreach를 사용하려면 이처럼 태그 라이브러리를 선언해야함 -->

<table id="commentList">
	<tr>
	    <th>작성자</th>
	    <th>댓글 내용</th>
	    <th>작성일</th>
	</tr>
    <c:forEach items="${replyList}" var="comment" > <!-- "replyList"를 java에서 전달받고 comment에 foreach로 저장함 -->
    <tr> 
         <td>${comment.writer}</td>
         <td>${comment.content}</td>
         <td>${comment.date}</td>
         <td>
	         <form action="creat-reply.do" method="post">
				<input type="hidden" name="b_idx" value="${board2.b_idx}">
				<input type="hidden" name="r_idx" value="${comment.r_idx}">
				<p><button type="button" class="btnComment">댓글달기</button></p>
			</form>
		 </td>
    </tr>
	<tr style="display: none;">
      	<td colspan="4">
   			<textarea id="commentTextarea2" rows="2" cols="80"></textarea>
   			<button type="button" class="btnCommentProc" grpord = "${comment.grpord}" p_post="${comment.p_post}" rIdx="${comment.r_idx}">등록</button> 
   			<!-- rIdx라는 속성을 직접 만든 후 r_idx값을 저장 -->
   			<!-- 댓글달기를 눌었을 때와 등록버튼을 눌렀을 때 이동되는 script는 다르기때문에 등록하기를 눌렀을 때 reply.r_idx를 따로 저장하지 않으면 불러올 수 없음 -->
      	</td>
	</tr>
 	</c:forEach>
</table>
<script>

$(document).on('click', '.btnComment', function(e) { 
	e.preventDefault(); 
	$(this).parent().parent().parent().parent().next().css('display', '');
});

$(document).on('click', '.btnCommentProc', function () { 
	//let comment1 = $('#commentTextarea2').val(); // comment1을 ID로 불러오는법
	let comment1 = $(this).closest('td').find('textarea').val(); // comment1을 위치로 불러오는법
	var b_idx = ${board2.b_idx};
	let r_idx = $(this).attr('rIdx');
	let p_post = $(this).attr('p_post');
	let grpord = $(this).attr('grpord');
	
	$.ajax({ 
        url: "creat-reply-process.do",
        type: "POST",
        data: { 
            comment2: comment1,
            b_idx: b_idx,
            r_idx: r_idx,
            p_post: p_post,
            grpord: grpord
        },
        success: function(res){
            $('#commentList').html(res); 
        },
        error: function(){
            $("#data").text("An error occurred");
        }
    });
});
</script> 
