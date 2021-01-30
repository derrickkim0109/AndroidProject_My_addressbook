<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String fName = request.getParameter("fName");
	String fTel = request.getParameter("fTel");
	String fEmail = request.getParameter("fEmail");	
	String fRelation = request.getParameter("fRelation");	
	String fAddress = request.getParameter("fAddress");	
	String fComment = request.getParameter("fComment");	
	String fImage = request.getParameter("fImage");	

	String fTag1 = request.getParameter("fTag1");	
	String fTag2 = request.getParameter("fTag2");	
	String fTag3 = request.getParameter("fTag3");	
	String fTag4 = request.getParameter("fTag4");	
	String fTag5 = request.getParameter("fTag5");	
	String uSeqno = request.getParameter("uSeqno");
//------
	String url_mysql = "jdbc:mysql://localhost/mypeople?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";
	int result = 0 ;

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();
	
	    String A = "insert into friendslist (fTag1,fTag2,fTag3,fTag4,fTag5,fName,fImage,";
		String B = "fTel, fRelation, fEmail, fAddress, fComment, user_uSeqno";
	    String C = ") values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	    ps = conn_mysql.prepareStatement(A+B+C);
	    ps.setInt(1, Integer.parseInt(fTag1));
	    ps.setInt(2, Integer.parseInt(fTag2));
	    ps.setInt(3, Integer.parseInt(fTag3));
	    ps.setInt(4, Integer.parseInt(fTag4));
	    ps.setInt(5, Integer.parseInt(fTag5));

	    ps.setString(6, fName);
	    ps.setString(7, fImage);
	    ps.setString(8, fTel);
	    ps.setString(9, fRelation);
	    ps.setString(10, fEmail);
	    ps.setString(11, fAddress);
	    ps.setString(12, fComment);
	    ps.setInt(13, Integer.parseInt(uSeqno));

	    
	    result = ps.executeUpdate();
%>

	{
		"result" : "<%=result%>"
	}
<%
	    conn_mysql.close();
	} 
	
	catch (Exception e){
%>

	{
		"result" : "<%=result%>"

	}

<%
	    e.printStackTrace();
	}
	
%>

