<%-- 
    Document   : chart
    Created on : Jan 13, 2021, 11:05:40 AM
    Author     : maro
--%>

<%@page import="service.UserService"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="service.ProfesseurService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.google.gson.JsonObject"%>
 
<%
Gson gsonObj = new Gson();
Map<Object,Object> map = null;
List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
UserService ps = new UserService();
ResultSet rs = null;
String title = "";
if (request.getParameter("op").equals("sexe")) {
    title = "sexe";
    //rs = ps.findBySexe();
    while (rs.next()) {
    map = new HashMap<Object,Object>(); map.put("label", rs.getString("sexe")); map.put("y", rs.getInt("nbr")); list.add(map);
    }
}else if (request.getParameter("op").equals("user")){
    title = "User";
    rs = ps.findUserBySex();
    while (rs.next()) {
     //map = new HashMap<Object,Object>(); map.put("label", rs.getString("specialite")); map.put("y", rs.getInt("nbr")); list.add(map);
     map = new HashMap<Object,Object>(); 
     map.put("label", rs.getString("sex")); 
     map.put("y", rs.getInt("nbrS"));
     list.add(map);
    }
}

String dataPoints = gsonObj.toJson(list);
%>
 
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
<!-- Bootstrap JS -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script><!-- comment -->

<script type="text/javascript">
window.onload = function() { 
 
var chart = new CanvasJS.Chart("chartContainer", {
	animationEnabled: true,
	title: {
		text: "Sexe "+ '<%= title %>'
	},
	axisY: {
		includeZero: true
	},
	axisX: {
		title: '<%= title %>'
	},
	data: [{
		type: "column",
		yValueFormatString: "#,##0\"%\"",
		dataPoints: <%out.print(dataPoints);%>
	}]
});
chart.render(); 
}
</script>
</head>
<body>
    <nav class="navbar navbar-expand-md navbar-dark bg-dark">
            
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>

            <div class="navbar-collapse collapse w-100 order-1 order-md-0 dual-collapse2" id="navbarSupportedContent">
              <ul class="navbar-nav ">
                <li class="nav-item active">
                  <a class="nav-link" href="/home.jsp">Home</a>
                </li>
                <li class="nav-item active">
                  <a class="nav-link" href="/users.jsp">Users</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="/phones.jsp">Téléphones</a>
                </li>
              </ul>
            </div>
             <ul class="navbar-nav ml-auto"> 
                <li class="nav-item"> 
                    <a class="nav-link" href="<%=request.getContextPath()%>/LogoutServlet"> 
                      Logout 
                    </a> 
                </li> 
            </ul>     
    </div>
  </nav>
<div id="chartContainer" style="height: 370px; width: 100%; padding-top: 100px;"></div>


<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
</body>
</html>     
