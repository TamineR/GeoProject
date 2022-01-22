<%-- 
    Document   : Login
    Created on : Jan 13, 2021, 8:34:09 AM
    Author     : maro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
<!-- Bootstrap JS -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
        <title>Login</title>
    <%
    if((request.getSession(false).getAttribute("User")!= null) )
    {
    %>
    <jsp:forward page="/home.jsp"></jsp:forward>
    <%} %>    
        <style>
                            .wrapper {
                        margin-top: 80px;
                        margin-bottom: 80px;
                }

                .form-signin {
                        max-width: 380px;
                        padding: 15px 35px 45px;
                        margin: 0 auto;
                        background-color: #fff;
                        border: 1px solid rgba(0, 0, 0, 0.1);
                }

                .form-signin .form-signin-heading,
                .form-signin .checkbox {
                        margin-bottom: 30px;
                }

                .form-signin .checkbox {
                        font-weight: normal;
                }

                .form-signin .form-control {
                        position: relative;
                        font-size: 16px;
                        height: auto;
                        padding: 10px;
                        -webkit-box-sizing: border-box;
                        -moz-box-sizing: border-box;
                        box-sizing: border-box;
                }

                .form-signin .form-control:focus {
                        z-index: 2;
                }

                .form-signin input[type="text"] {
                        margin-bottom: -1px;
                        border-bottom-left-radius: 0;
                        border-bottom-right-radius: 0;
                }

                .form-signin input[type="password"] {
                        margin-bottom: 20px;
                        border-top-left-radius: 0;
                        border-top-right-radius: 0;
                }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <form class="form-signin" action="<%=request.getContextPath()%>/LoginServlet" method="post">
               <h2 class="form-signin-heading">   Login</h2>
               <input type="text" class="form-control" name="username" placeholder="Username" required="" autofocus="" />
               <input type="password" class="form-control" name="password" placeholder="Password" required=""/>      
               <label class="checkbox">
               <input type="checkbox" value="remember-me" id="rememberMe" name="rememberMe"> Remember me
               </label>
                <label class="checkbox">
                  <span style="color:red"><%=(request.getAttribute("errMessage") == null) ? "" : request.getAttribute("errMessage")%></span>
               </label>
               <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>   
            </form>
         </div>
    </body>
</html>
