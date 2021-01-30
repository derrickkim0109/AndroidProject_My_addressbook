<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


    

<%
    
    String userid = request.getParameter("userid");
    String userpw = request.getParameter("userpw");
   


    String url_mysql = "jdbc:mysql://localhost/mypeople?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "SELECT uSeqno,uId,uPw,uName,uTel,uDeleteDate,uInsertDate";
    String WhereDefault2 = " from mypeople.user where uId ='"+userid+"' and uPw ='"+userpw+"' and uDeleteDate = '1111-11-11'";
    PreparedStatement ps = null;
   
    int count = 0;
    
    try {    
        Class.forName("com.mysql.cj.jdbc.Driver");  
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();
        
        ResultSet rs = stmt_mysql.executeQuery(WhereDefault + WhereDefault2) ;

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
