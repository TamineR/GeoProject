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
import beans.Phone;
import beans.Professeur;
import beans.Specialite;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.PhoneService;
import service.SpecialiteService;
import service.ProfesseurService;

@WebServlet(name = "PhoneController", urlPatterns = {"/PhoneController"})

public class PhoneController extends HttpServlet {
    private PhoneService sp = new PhoneService();

        protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Phone spe = null;
        if (request.getParameter("op") != null) {
            if (!request.getParameter("id").isEmpty())
                spe = sp.findById(Integer.parseInt(request.getParameter("id")));
            if (request.getParameter("op").equals("delete")) {
                sp.delete(sp.findById(Integer.parseInt(request.getParameter("id"))));
            }
        } else if (request.getParameter("id") == "") {
            String provider = request.getParameter("provider");
            String number = request.getParameter("number");
            String userid = request.getParameter("userid");
            String isadmin = request.getParameter("isadmin");
            sp.create(new Phone(provider,number, Integer.parseInt(userid),isadmin));
            
        } else {
            String id = request.getParameter("id");
            String provider = request.getParameter("provider");
            String number = request.getParameter("number");
            String userid = request.getParameter("userid");
            String isadmin = request.getParameter("isadmin");
            sp.update(new Phone(Integer.parseInt(id), provider,number, Integer.parseInt(userid),isadmin));  
        }
        response.sendRedirect("phones.jsp");      
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
        processRequest(request, response);
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
