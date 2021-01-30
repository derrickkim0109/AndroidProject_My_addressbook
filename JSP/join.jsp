<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String id = request.getParameter("id");
	String pw = request.getParameter("pw");
	String name = request.getParameter("name");
	String tel = request.getParameter("tel");	
		
//------
	String url_mysql = "jdbc:mysql://localhost/mypeople?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();
	
	    String A = "insert into user (uId, uPw, uName, uTel, uInsertDate) values (?,?,?,?,now())";
	    
	
	    ps = conn_mysql.prepareStatement(A);
	    ps.setString(1, id);
	    ps.setString(2, pw);
	    ps.setString(3, name);
	    ps.setString(4, tel);
	    
	   ps.executeUpdate();
		
	conn_mysql.close();
	} 
	
	catch (Exception e){


	    e.printStackTrace();
	}
	
%>

