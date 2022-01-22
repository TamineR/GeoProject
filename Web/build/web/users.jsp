<%-- 
    Document   : professeurs
    Created on : Dec 30, 2020, 3:43:38 AM
    Author     : maro
--%>
<%@page import="beans.User"%>
<%@page import="service.UserService"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
<!--        <link href="style/main.css" rel="stylesheet" type="text/css"/>-->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- CSS only -->
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
<title>Gestion des Users</title>
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
            UserService ps = new UserService();
            User prof = null;
            if (request.getParameter("id") != null) {
                prof = ps.findById(Integer.parseInt(request.getParameter("id")));
            }
        %>
         <form method="GET" action="UsersController">
            <fieldset>
                <div class="form-group">
                <input type="hidden" name="id" value="<%= (prof == null) ? "" : prof.getId() %>" />
                <table class="table table-striped table-responsive mx-auto w-auto">     
                    <thead>
                        <tr class=" ">
                        <th class="center" colspan="2" >Informations user</th>
                        </tr>
                    </thead>
                    <tr>
                        <td>firstname</td>
                        <td><input type="text" name="firstname" value="<%= (prof == null) ? "" : prof.getFirsname()%>" /></td>
                    </tr>
                    <tr>
                        <td>lastname</td>
                        <td><input type="text" name="lastname" value="<%= (prof == null) ? "" : prof.getLastname()%>" /></td>
                    </tr>
                    <tr>
                        <td>age</td>
                        <td><input type="text" name="age" value="<%= (prof == null) ? "" : prof.getAge()%>" /></td>
                    </tr>
                    <tr>
                        <td>sex</td>
                        <td><input type="text" name="sex" value="<%= (prof == null) ? "" : prof.getSex()%>" /></td>
                    </tr>
                    
                    <tr>
                        <td>address</td>
                        <td><input type="text" name="addr" value="<%= (prof == null) ? "" : prof.getAddr()%>" /></td>
                    </tr> 
                    
                    <tr>
                        <td></td>
                        <td><input type="submit" value="<%= (prof == null) ? "Ajouter" : "Modifier" %>" /></td>
                    </tr>

                </table>
               </div>
            </fieldset>
         </form>
             <fieldset>
                        
            <div class="row justify-content-center">
              <legend class="row justify-content-center">  List des users  </legend>
            </div>
          </div>
            <table class="table table-striped table-responsive mx-auto w-auto">
                <thead>
                    <tr>
                      <form class="form-inline" method="post" action="users.jsp">
                         <td> <input type="text" name="nom" class="f" placeholder="Search Nom"> </td>
                         <td></td>
                         <td></td>
                         <td></td>
                         <td><a href="chart.jsp?op=user">Chart</a></td>
                         <td></td>
                         <td></td>
                         <td></td>
                         <td></td>
                         <td> <button type="submit" name="save" class="btn btn-primary" colspan="4">Search</button> </td>
                     </form>
                    </tr>
                    <tr>
                        <th>Id</th>
                        <th>firsname</th>
                        <th>lastname</th>
                        <th>age</th>
                        <th>sex</th>
                        <th>address</th>
                        <th>Supprimer</th>
                        <th>Modifier</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<User> all = null;
                        if (request.getParameter("firstname") == null)
                        {
                            all = ps.findAll();
                        }else if(request.getParameter("firstname") != null){
                            String nom = request.getParameter("firstname");
                            System.out.println(nom);
                            all = ps.findAllByNom(nom);
                            if(all == null) {
                                all = ps.findAll();
                            }
                        }else if (request.getParameter("firstname").isEmpty()){
                            all = ps.findAll();
                        }
                        
                        for (User p : all) {
                    %>
                    <tr>
                        <td> <%= p.getId()%></td>
                        <td><%= p.getFirsname()%></td>
                        <td><%= p.getLastname()%></td>
                        <td><%= p.getAge()%></td>
                        <td><%= p.getSex()%></td>
                        <td><%= p.getAddr()%></td>
                        <td><a href="UsersController?id=<%= p.getId()%>&op=delete">Supprimer</a></td>
                        <td><a href="users.jsp?id=<%= p.getId()%>">Modifier</a></td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>

        </fieldset>
                

        </form>
    </body>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
    <script type="text/javascript">

    </script> 
</html>
