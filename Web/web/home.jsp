<%-- 
    Document   : home
    Created on : Jan 13, 2021, 3:58:00 PM
    Author     : maro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Home</title>

        <!-- CSS only -->
         <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" 
               rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
         <!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
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
                <li class="nav-item">
                  <a class="nav-link" href="/map.jsp">Map</a>
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
              
        <div class="nav-scroller py-1 mb-2">
        <nav class="nav d-flex justify-content-between">

        </nav>
      </div>
       <div class="jumbotron p-3 p-md-5 text-white rounded bg-dark">
        <div class="col-md-6 px-0">
 
          <p class="lead my-3">Étudiant : Tourahi Amine </p>
          <p class="lead my-3">AppVersion: 1.0.5 </p>
        </div>
      </div>
                      
    </body>
</html>
