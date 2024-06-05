<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@ page import="com.oreilly.servlet.MultipartRequest"%>
<!-- DefaultFileRenamePolicy는 업로드된 파일의 이름이 중복될 경우 이름을 자동으로 바꿔주는 클래스이고,
 MultipartRequest는 파일 업로드를 처리하는 클래스 -->
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>저장완료</h1>
<a href="/lcomputerstudy/user-list.do">돌아가기</a>
</body>
</html>