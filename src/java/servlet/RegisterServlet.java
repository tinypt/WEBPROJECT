/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import extra.SendEmail;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import jpa.model.Account;
import jpa.model.controller.AccountJpaController;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Hong
 */
public class RegisterServlet extends HttpServlet {

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
            throws ServletException, IOException, Exception {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String telno = request.getParameter("telno");
        if (username != null && password != null && address != null && name != null && surname != null && telno != null && email != null) {
            HttpSession session = request.getSession(false);

            password = cryptWithMD5(password);
            Account acc = new Account(username, password, address, name, surname, telno, email);
            AccountJpaController accCtrl = new AccountJpaController(utx, emf);
            try {
                accCtrl.create(acc);
            } catch (RollbackFailureException ex) {
               request.setAttribute("address", address);
               request.setAttribute("name", name);
               request.setAttribute("surname", surname);
               request.setAttribute("telno", telno);
                request.setAttribute("error", "ชื่อผู้ใช้หรืออีเมลล์ถูกใช้งานแล้ว");
                getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
                return;
            }

            String activatekey = acc.getActivatekey();
            String link = "\"" + "http://localhost:8080/WEBPROJECT/Activate?username=" + acc.getUsername() + "&activatekey=" + activatekey + "\"";
            System.out.println(link);
            SendEmail se = new SendEmail();
            se.sendEmailTo(acc.getEmail(), "activate", link);

            request.setAttribute("actinmail", "Please Activate Account on your Email");
            session.setAttribute("accCheck", acc);
            getServletContext().getRequestDispatcher("/montho.jsp").forward(request, response);
            return;
        }
        getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
