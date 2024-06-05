<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<!-- DefaultFileRenamePolicy는 파일 업로드 시 같은 이름의 파일이 존재하면 자동으로 파일 이름을 변경해주는 역할을 한다.
 예를 들어, file.txt를 업로드할 때 이미 같은 이름의 파일이 있으면 file1.txt, file2.txt 등으로 이름을 변경한다 -->
<%@ page import="com.oreilly.servlet.MultipartRequest"%>
<!-- MultipartRequest 클래스는 파일 업로드를 처리하기 위해 사용됩니다.
 이 클래스는 클라이언트가 업로드한 파일을 서버에 저장하고, 파일의 이름, 크기, 타입 등의 정보를 얻을 수 있도록 해줍니다. -->


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 작성하기</title>
</head>
<body>
<form action="create.process.do" name="post" method="post" enctype="multipart/form-data"> 
	<!-- enctype="multipart/form-data 파일 업로드를 위해 폼의 enctype 속성을 반드시 이와 같이 설정해야 합니다. -->
	<p> 작성자 : ${sessionScope.user.u_name }님</p>
	<p> 제목 : <input type="text" name="title"></p>
	<p> 내용 : <input type="text" name="content"></p>
	<!-- 작성 날짜와 조회수는 글 목록에서 노출되도록 -->
	<input type="hidden" name="idx" value="${sessionScope.user.u_idx }">
	<input type="hidden" name="writer" value="${sessionScope.user.u_name }">
	<input type="hidden" name="depth" value="0">
	<input type="hidden" name="grpord" value="0">
	
	<h3>첨부 파일</h3>
	<input type="file" name="file">
	
	<p> <input type="submit" value="작성하기"></p>
</form>
</body>
</html>