<%-- 
    Document   : twoDates
    Created on : Jan 13, 2021, 3:01:29 PM
    Author     : maro
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%-- 
    Document   : professeurs
    Created on : Dec 30, 2020, 3:43:38 AM
    Author     : maro
--%>
<%@page import="java.util.List"%>
<%@page import="beans.Professeur"%>
<%@page import="service.ProfesseurService"%>
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
<title>Gestion des Professeurs</title>
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
                  <a class="nav-link" href="/professeurs.jsp">Professeurs</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="/specialites.jsp">Specialites</a>
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
            ProfesseurService ps = new ProfesseurService();

        %>
         <form method="POST" action="twoDates.jsp">
            <fieldset>
                <div class="form-group">
                <table class="table table-striped table-responsive mx-auto w-auto">     
                    <thead>
                        <tr class=" ">
                        <th class="center" colspan="2" >Les Deux dates</th>
                        </tr>
                    </thead>
                    <tr>
                        <td>Date d'embauche 1</td>
                        <td><input type="date" name="dateEmb1" value="" /></td>
                    </tr>
                    <tr>
                        <td>Date d'embauche 2</td>
                        <td><input type="date" name="dateEmb2" value="" /></td>
                    </tr>                 
                    <tr>
                        <td></td>
                        <td><input type="submit" value="Chercher" /></td>
                    </tr>

                </table>
               </div>
            </fieldset>
         </form>
             <fieldset>
                        
            <div class="row justify-content-center">
              <legend class="row justify-content-center">  List des professeurs  </legend>
            </div>
          </div>
            <table class="table table-striped table-responsive mx-auto w-auto">
                <thead>
                    <tr>
                      <form class="form-inline" method="post" action="professeurs.jsp">
                         <td>  </td>
                         <td></td>
                         <td></td>
                         <td></td>
                         <td></td>
                         <td></td>
                         <td><a href="chart.jsp?op=sexe">Chart</a></td>
                         <td><a href="chart.jsp?op=specialite">Chart</a></td>
                         <td></td>
                         <td>  </td>
                     </form>
                    </tr>
                    <tr>
                        <th>Id</th>
                        <th>Nom</th>
                        <th>Prénom</th>
                        <th>Phone</th>
                        <th>Email</th>
                        <th>Date d'embauche</th>
                        <th>Sexe</th>
                        <th>Spécialite</th>
                        <th>Supprimer</th>
                        <th>Modifier</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        String dateEmb1 = "";
                        String dateEmb2 = "";
                        List<Professeur> all = new ArrayList<Professeur>();
                        if(request.getParameter("dateEmb1") != null && request.getParameter("dateEmb2") != null){
                            if(!request.getParameter("dateEmb1").isEmpty() && !request.getParameter("dateEmb2").isEmpty()) {
                                dateEmb1 = request.getParameter("dateEmb1").replace("-", "/");
                                dateEmb2 = request.getParameter("dateEmb2").replace("-", "/");
                                all = ps.findBetweenTwoDates(new Professeur("","","","",new Date(dateEmb1),"",""), new Professeur("","","","",new Date(dateEmb2),"",""));   
                            }else {
                                all = ps.findAll();
                            }
                        }else {
                            all = ps.findAll();
                        }
                        for (Professeur p : all) {
                    %>
                    <tr>
                        <td> <%= p.getId()%></td>
                        <td><%= p.getNom()%></td>
                        <td><%= p.getPrenom()%></td>
                        <td><%= p.getPhone()%></td>
                        <td><%= p.getEmail()%></td>
                        <td><%= p.getDateEmb()%></td>
                        <td><%= p.getSexe()%></td>
                        <td><%= p.getSpecialite()%></td>
                        <td><a href="ProfesseurController?id=<%= p.getId()%>&op=delete">Supprimer</a></td>
                        <td><a href="professeurs.jsp?id=<%= p.getId()%>">Modifier</a></td>
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

