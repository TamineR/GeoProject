<%-- 
    Document   : specialites
    Created on : Dec 31, 2020, 1:53:36 AM
    Author     : maro
--%>
<%@page import="service.UserService"%>
<%@page import="beans.User"%>
<%@page import="beans.Phone"%>
<%@page import="beans.Phone"%>
<%@page import="service.PhoneService"%>
<%@page import="java.util.List"%>
<%@page import="beans.Specialite"%>
<%@page import="service.SpecialiteService"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" 
               rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
         <!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
        
    <% //In case, if User session is not set, redirect to Login page.
    if((request.getSession(false).getAttribute("User")== null) )
    {
    %>
    <jsp:forward page="/"></jsp:forward>
    <%} %>    
<title>Gestion des spécialités</title>
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
        <%
            PhoneService sp = new PhoneService();
            UserService us = new UserService();
            Phone ph = null;
            if (request.getParameter("id") != null) {
                ph = sp.findById(Integer.parseInt(request.getParameter("id")));
            }
        %>
        <form method="GET" action="PhoneController">
            <fieldset>
                <div class="form-group">
                <input type="hidden" name="id" value="<%= (ph == null) ? "" : ph.getId() %>" />
                <table class="table table-striped table-responsive mx-auto w-auto">  
                     <thead>
                        <tr class=" ">
                        <th class="center" colspan="2" >Informations Telephones</th>
                        </tr>
                    </thead>
                    <tr>
                        <td>Provider</td>
                        <td><input type="text" name="provider" value="<%= (ph == null) ? "" : ph.getProvider()%>" /></td>
                    </tr>
                    <tr>
                        <td>Number</td>
                        
                        <td><input type="text" name="number" value="<%= (ph == null) ? "" : ph.getNumber()%>" /></td>
                    </tr>
                    <tr>
                        <td>User</td>
                        
                        <td>
                            <select name="userid" id="userid">
                              <%
                                  List<User> BALL = null;

                                    BALL = us.findAll();
                                    for (User s : BALL) {
                                %>
                                    <option value=<%= s.getId()%>> <%= s.getFirsname()%>  <%= s.getLastname()%></option>
                                <%
                                    }
                                %>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>IsAdmin</td>
                        <td><input type="text" name="isadmin" value="<%= (ph == null) ? "" : ph.getIsAdmin()%>" /></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="submit" value="<%= (ph == null) ? "Ajouter" : "Modifier" %>" /></td>
                    </tr>
                </table>
               </div>
            </fieldset>

        </form>

        <fieldset>
            <div class="row justify-content-center">
              <legend class="row justify-content-center">  List des telephones </legend>
            </div>
            <table class="table table-striped table-responsive mx-auto w-auto">
                <thead>
                     <tr>
                      <form class="form-inline" method="post" action="phones.jsp">
                         <td> <input type="text" name="libelle" class="f" placeholder="Search Libelle"> </td>
                         <td></td>
                         <td></td>
                         <td></td>
                         <td> <button type="submit" name="save" class="btn btn-primary" colspan="4">Search</button> </td>
                     </form>
                    </tr>
                    
                    <tr>
                        <th>ID</th>
                        <th>Provider</th>
                        <th>Number</th>
                        <th>UserId</th>
                        <th>IsAdmin</th>
                        <th>Supprimer</th>
                        <th>Modifier</th>
                    </tr>
                </thead>
                <tbody>
                   
                    <%
                        List<Phone> all = null;
                        if (request.getParameter("provider") == null)
                        {
                            all = sp.findAll();
                        }else if(request.getParameter("provider") != null){
                            String Lib = request.getParameter("provider");
                            System.out.println(Lib);
                            //all = sp.findByLibelle(Lib);
                            if(all == null) {
                                all = sp.findAll();
                            }
                        }else if (request.getParameter("provider").isEmpty()){
                            all = sp.findAll();
                        }
                        for (Phone s : all) {
                    %>
                    <tr>
                        <td> <%= s.getId()%></td>
                        <td><%= s.getProvider()%></td>
                        <td><%= s.getNumber()%></td>
                        <td><%= s.getUserId()%></td>
                        <td><%= s.getIsAdmin()%></td>
                        <td><a href="PhoneController?id=<%= s.getId()%>&op=delete">Supprimer</a></td>
                        <td><a href="phones.jsp?id=<%= s.getId()%>">Modifier</a></td>
                    </tr>
                    <%
                        }
                    %>
                 
                </tbody>
            </table>
                

        </fieldset>
    </body>
</html>
