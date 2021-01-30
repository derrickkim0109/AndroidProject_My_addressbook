<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


    

<%
    
    String userid = request.getParameter("userid");
   
    
    String url_mysql = "jdbc:mysql://localhost/mypeople?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "select count(*) from user where uId ='"+userid+"'";
    
    PreparedStatement ps = null;
   
    int count = 0;
    
    try {    
        Class.forName("com.mysql.cj.jdbc.Driver");  
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();
        
        ResultSet rs = stmt_mysql.executeQuery(WhereDefault);

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
			"count" : "<%=rs.getInt(1) %>"
           

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
