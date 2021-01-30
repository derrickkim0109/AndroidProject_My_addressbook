<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String userid = request.getParameter("userid");
	String userpw = request.getParameter("userpw");
    %><%=userid%><%
    %><%=userpw%><%
	
		
//------
	 String url_mysql = "jdbc:mysql://localhost/mypeople?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
     %>2<%
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
        %>3<%
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
        %>4<%
	    Statement stmt_mysql = conn_mysql.createStatement();
        %>5<%
	
	    String A = "update user set uPw=? where uId = ?";
        %><%=A%><%
	   
	
	    ps = conn_mysql.prepareStatement(A);
        %>6<%
	    ps.setString(1, userpw);
        %>7<%
	    ps.setString(2, userid);
        %>8<%
	    
	    ps.executeUpdate();
        %>9<%
	
	    conn_mysql.close();
        %>10<%
	} 
	
	catch (Exception e){
	    e.printStackTrace();
	}
	
%>

