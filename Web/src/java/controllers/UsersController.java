/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author maro
 */
import beans.Specialite;
import beans.Professeur;
import beans.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.ProfesseurService;
import service.SpecialiteService;
import service.UserService;


@WebServlet(name = "UsersController", urlPatterns = {"/UsersController"})

public class UsersController  extends HttpServlet{
    private UserService ps = new UserService();
    private SpecialiteService sp = new SpecialiteService();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        if (request.getParameter("op") != null) {
            if (request.getParameter("op").equals("delete")) {
                ps.delete(ps.findById(Integer.parseInt(request.getParameter("id"))));
            }
        } else if (request.getParameter("id") == "") {
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String age = request.getParameter("age");
            String sex = request.getParameter("sex");
            //String dateEmb = request.getParameter("dateEmb").replace("-", "/");
            String addr = request.getParameter("addr");
            //if(!sp.findByLible(specialite)) {
              //  String code = specialite+"##COde";
              //  sp.create(new Specialite(code,specialite));
            //}
            ps.create(new User(firstname,lastname,Integer.parseInt(age),sex,addr));
            
        } else {
            System.out.println("Here Update");
            String id = request.getParameter("id");
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String age = request.getParameter("age");
            String sex = request.getParameter("sex");
            //String dateEmb = request.getParameter("dateEmb").replace("-", "/");
            String addr = request.getParameter("addr");
            ps.update(new User(Integer.parseInt(id),firstname,lastname,Integer.parseInt(age),sex,addr));  
        }
        response.sendRedirect("users.jsp");      
    }
    
        // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response); //Same as POST so you can just use GET as an action and you will get the same respounce as POST
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
