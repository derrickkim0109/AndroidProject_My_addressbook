<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    request.setCharacterEncoding("utf-8");
    String user_uSeqno = request.getParameter("user_uSeqno");
    String searchText = request.getParameter("searchText");
	
//-----
	String url_mysql = "jdbc:mysql://localhost/mypeople?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "select fName from friendslist where user_uSeqno = " + user_uSeqno + " order by binary(fName)";
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault+where2); // 
%>
		{ 
  			"friendslist"  : [ 
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
			"fSeqno" : "<%=rs.getInt(1) %>", 
			"fName" : "<%=rs.getString(2) %>",   
			"fTel" : "<%=rs.getString(3) %>",  
            "fRelation" : "<%=rs.getString(4) %>",
            "fImage" : "<%=rs.getString(5) %>",
            "fImageReal" : "<%=rs.getString(6) %>",
            "fTag1" : "<%=rs.getInt(7) %>",
            "fTag2" : "<%=rs.getInt(8) %>",
            "fTag3" : "<%=rs.getInt(9) %>",
            "fTag4" : "<%=rs.getInt(10) %>",
            "fTag5" : "<%=rs.getInt(11) %>",
            "fComment" : "<%=rs.getString(12) %>",
            "fAddress" : "<%=rs.getString(13) %>",
            "fEmail" : "<%=rs.getString(14) %>"

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