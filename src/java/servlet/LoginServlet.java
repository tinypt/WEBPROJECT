/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import jpa.model.Account;
import jpa.model.controller.AccountJpaController;

/**
 *
 * @author GT62VR
 */
public class LoginServlet extends HttpServlet {

    @PersistenceUnit(unitName = "MonthoPU")
    EntityManagerFactory emf;

    @Resource
    UserTransaction utx;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
//        String url = null;
//        url = (String) request.getAttribute("url");
//        System.out.println("url0 is = "+url);
        if (username != null && password != null) {
            AccountJpaController accCtrl = new AccountJpaController(utx, emf);
            try {
                Account acc = accCtrl.findAccountbyUserName(username);
                checkUser(acc, password, request, response);

            } catch (NoResultException ex) {
                try {
                    Account acc = accCtrl.findAccountbyEmail(username);
                    checkUser(acc, password, request, response);
                } catch (NoResultException ex1) {
                    request.setAttribute("Logfail", "Your id or email are invalid!");
                    getServletContext().getRequestDispatcher("/Login.jsp").forward(request, response);
                    return;
                }
            }
        }
        getServletContext().getRequestDispatcher("/Login.jsp").forward(request, response);
    }

    public void checkUser(Account acc, String password, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (acc.getActivatedate() != null) {
            HttpSession session = request.getSession();
            password = cryptWithMD5(password);
            if (password.equals(acc.getPassword())) {
                String url = null;
                url = (String) session.getAttribute("url");
                session.setAttribute("acc", acc);
//                getServletContext().getRequestDispatcher("/montho.jsp").forward(request, response);
                System.out.println("url is " + url);
                if (url != null) {
                    if (url.equals("/UpdateAccount")) {
                        getServletContext().getRequestDispatcher("/montho.jsp").forward(request, response);
                        return;
                    } else if (url.equals("/Favourite")) {
                        int productid = (int) session.getAttribute("productidfilter");
                        System.out.println("authenfilter productid=" + productid);
                        url = "/Getdetail?product=" + productid;
                        getServletContext().getRequestDispatcher(url).forward(request, response);
                        return;
                    }
                    getServletContext().getRequestDispatcher(url).forward(request, response);
                    return;
                }
                getServletContext().getRequestDispatcher("/montho.jsp").forward(request, response);
            } else {
                request.setAttribute("falsepass", "Your ID or Password invalid");
                getServletContext().getRequestDispatcher("/Login.jsp").forward(request, response);
            }
        } else {
            password = cryptWithMD5(password);
            if (password.equals(acc.getPassword())) {
                String link = "http://localhost:8080/WEBPROJECT/Activate?username=" + acc.getUsername() + "&activatekey=" + acc.getActivatekey();
                request.setAttribute("link", link);

                request.setAttribute("activate", "You are not activate your account.");
                getServletContext().getRequestDispatcher("/Login.jsp").forward(request, response);
            } else {
                request.setAttribute("falsepass", "Your ID or Password invalid");
                getServletContext().getRequestDispatcher("/Login.jsp").forward(request, response);
            }
        }
    }

    public static String cryptWithMD5(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passBytes = pass.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digested.length; i++) {
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex);
        }
        return null;
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
