<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String tag1 = request.getParameter("tag1");
	String tag2 = request.getParameter("tag2");
	String tag3 = request.getParameter("tag3");
	String tag4 = request.getParameter("tag4");
    String tag5 = request.getParameter("tag5");
    int seq = Integer.parseInt(request.getParameter("seq"));
	
		
//------
	String url_mysql = "jdbc:mysql://localhost/mypeople?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();
	
		String A = "update tag set tag1 = ?, tag2 = ?, tag3 = ?, tag4 = ?, tag5 = ?  where user_uSeqno = ?";
		
	   
	
	    ps = conn_mysql.prepareStatement(A);
		ps.setString(1, tag1);
		ps.setString(2, tag2);
		ps.setString(3, tag3);
		ps.setString(4, tag4);
        ps.setString(5, tag5);
        ps.setInt(6, seq);
        
		
	    
	    
	    ps.executeUpdate();
	
	    conn_mysql.close();
	} 
	
	catch (Exception e){
	    e.printStackTrace();
	}
	
%>

