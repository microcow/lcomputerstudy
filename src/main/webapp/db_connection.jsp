<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
	Connection conn = null;

	// String url = "jdbc:mysql://localhost:3306/dbtest";
	String url = "jdbc:mysql://127.0.0.1:3306/sangwoo";
	String user ="root";
	String dbPassword="1234";
	
	Class.forName("org.mariadb.jdbc.Driver");
	conn = DriverManager.getConnection(url, user, dbPassword);

%>