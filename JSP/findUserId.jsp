<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


    

<%
    
    String utel = request.getParameter("utel");
    
   


    String url_mysql = "jdbc:mysql://localhost/mypeople?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "SELECT uId from mypeople.user where uTel = '"+ utel + "' and uDeleteDate = '1111-11-11'";
    
    PreparedStatement ps = null;
   
    int count = 0;
    
    try {    
        Class.forName("com.mysql.cj.jdbc.Driver");  
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();
        ResultSet rs = stmt_mysql.executeQuery(WhereDefault) ;
        while (rs.next()) {
%>
		{ "uTel" : "<%=rs.getString(1) %>"}
  			 
<%
        
        }conn_mysql.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
	
%>
