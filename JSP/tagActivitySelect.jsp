<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


    

<%
    int seq = Integer.parseInt(request.getParameter("seq"));
    String url_mysql = "jdbc:mysql://localhost/mypeople?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "select tag1,tag2,tag3,tag4,tag5,user_uSeqno from tag where user_uSeqno="+seq;
    int count = 0;
    
    try {    
        Class.forName("com.mysql.cj.jdbc.Driver");  
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);    
        Statement stmt_mysql = conn_mysql.createStatement();       
        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); 
%>
		{ 
  			"tage_info"  : [ 
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
			"tag1" : "<%=rs.getString(1) %>", 
			"tag2" : "<%=rs.getString(2) %>",   
			"tag3" : "<%=rs.getString(3) %>",  
            "tag4" : "<%=rs.getString(4) %>",
            "tag5" : "<%=rs.getString(5) %>",
            "user_uSeqno" : "<%=rs.getString(6) %>"
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
