/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.File;
import jpa.model.Account;
import jpa.model.controller.AccountJpaController;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

/**
 *
 * @author GT62VR
 */
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    @PersistenceUnit(unitName = "MonthoPU")
    EntityManagerFactory emf;

    @Resource
    UserTransaction utx;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FileUpload(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FileUpload(request, response);
    }

    private void FileUpload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        AccountJpaController accCtrl = new AccountJpaController(utx, emf);
        Account acc = (Account) session.getAttribute("acc");
        
        String applicationPath = request.getServletContext().getRealPath("");
        applicationPath = applicationPath.replace("\\build\\web", "\\web\\image-account");

        File upload_path = new File(applicationPath);
        Part filePart = request.getPart("file");

        if (filePart != null) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            System.out.println("1 = " + fileName);
            String[] fileNamenew = fileName.split("\\.");
            if (fileNamenew[1].equals("jpg") || fileNamenew[1].equals(".png")) {

                String type;
                if (fileNamenew[1].equals("jpg")) {
                    type = ".jpg";
                } else {
                    type = ".png";
                }

                fileName = acc.getUsername();
                System.out.println("2 = " + fileName);
                File file = File.createTempFile(fileName, type, upload_path);
                System.out.println("3 = " + file.getName());

                try (InputStream fileContent = filePart.getInputStream()) {
                    Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    acc.setPicturename(file.getName());
                    accCtrl.edit(acc);

                    session.setAttribute("acc", acc);
                    request.setAttribute("upcom", "อัพโหลดรูปเรียบร้อย");
                    getServletContext().getRequestDispatcher("/montho.jsp").forward(request, response);
                } catch (Exception e) {
                    System.out.println("File Upload Failure:" + e);
                }
            } else {
                request.setAttribute("wrongtype", "อัพโหลดไฟล์นามสกุล jpg หรือ png");
                getServletContext().getRequestDispatcher("/Account.jsp").forward(request, response);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
