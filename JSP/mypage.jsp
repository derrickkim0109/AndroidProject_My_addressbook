<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String url_mysql = "jdbc:mysql://localhost/mypeople?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
     String pw_mysql = "qwer1234";
     String seq = request.getParameter("seq");
    String WhereDefault = "SELECT uSeqno, uId, uPw, uName, uTel, uDeleteDate, uInsertDate from user where uSeqno = "+seq;
    int count = 0;


    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); // 
%>
		{ 
  			"user_info"  : [ 
<%
        while (rs.next()) {
            if (count == 0) {

            }else{
%>
            , 
<%
            }
%>            
			{
            "uSeqno" : "<%=rs.getInt(1) %>",   
			"uId" : "<%=rs.getString(2) %>", 
			"uPw" : "<%=rs.getString(3) %>",   
            "uName" : "<%=rs.getString(4) %>",  
            "uTel" : "<%=rs.getString(5) %>",  
            "uDeleteDate" : "<%=rs.getTimestamp(6) %>",
            "uInsertDate" : "<%=rs.getTimestamp(7) %>"
			}

<%		
        count++;
        }
%>
		  ] 
		} 
<%		
        conn_mysql.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
	
%>