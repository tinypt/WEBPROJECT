/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.transaction.UserTransaction;
import jpa.model.Account;
import jpa.model.controller.AccountJpaController;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author GT62VR
 */
@MultipartConfig
public class UpdateAccountServlet extends HttpServlet {

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
            throws ServletException, IOException, NonexistentEntityException, RollbackFailureException, Exception {

        HttpSession session = request.getSession(false);
        AccountJpaController accCtrl = new AccountJpaController(utx, emf);
        Account acc = (Account) session.getAttribute("acc");
        String username = request.getParameter("username");
        String address = request.getParameter("address");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String telno = request.getParameter("telno");

        if (surname == null) {
            String[] splited = name.split(" ");
            name = splited[0];
            surname = splited[1];
        }

        /*File Upload Zone*/
        String applicationPath = request.getServletContext().getRealPath("");
        applicationPath = applicationPath.replace("\\build\\web", "\\web\\image-account");

        File upload_path = new File(applicationPath);
        Part filePart = request.getPart("file");
//        System.out.println(".........." + filePart.getSize());
        if (filePart != null) {
            if (filePart.getSize() > 0) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String[] fileNamenew = fileName.split("\\.");
                if (fileNamenew[1].equals("jpg") || fileNamenew[1].equals(".png")) {

                    String type;
                    if (fileNamenew[1].equals("jpg")) {
                        type = ".jpg";
                    } else {
                        type = ".png";
                    }

                    fileName = acc.getUsername();
                    File file = File.createTempFile(fileName, type, upload_path);

                    try (InputStream fileContent = filePart.getInputStream()) {
                        Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        acc.setPicturename(file.getName());
                    } catch (Exception e) {
                        System.out.println("File Upload Failure:" + e);
                    }
                } else {
                    request.setAttribute("wrongtype", "อัพโหลดไฟล์นามสกุล jpg หรือ png");
                    getServletContext().getRequestDispatcher("/Account.jsp").forward(request, response);
                }
            }
        }
        /*End Zone*/

        acc.setAddress(address);
        acc.setName(name);
        acc.setSurname(surname);
        acc.setTelnumber(telno);
        accCtrl.edit(acc);

        session.setAttribute("acc", acc);
        request.setAttribute("update", "แก้ไขข้อมูลสำเร็จ");
        String form = request.getParameter("form");
        if (form != null) {
            getServletContext().getRequestDispatcher("/Checkout.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/montho.jsp").forward(request, response);
        }
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
        } catch (RollbackFailureException ex) {
            Logger.getLogger(UpdateAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UpdateAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (RollbackFailureException ex) {
            Logger.getLogger(UpdateAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UpdateAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
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
