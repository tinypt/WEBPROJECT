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
import java.nio.file.StandardCopyOption;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author GT62VR
 */

@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FileUploadServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FileUploadServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        singleFileUpload(request, response);
    }

    private void singleFileUpload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ดึงที่เก็บข้อมูลไฟล์ของเว็บแอพลิเคชัน
        String applicationPath = request.getServletContext().getRealPath("");
        applicationPath = applicationPath.replace("\\build\\web", "\\web\\image-account");
//        System.out.println("1 = " + applicationPath);

        // สร้างไดเรกทอรีบันทึกถ้ายังไม่มีอยู่
        File upload_path = new File(applicationPath);

        Part filePart = request.getPart("file");
        if (filePart != null) {
            String fileName = "11111";
            //  System.out.println("filename= "+fileName);
//            String[] fileNamenew = fileName.split("\\.");
            //แยกด้วยจุด
            //System.out.println("filenamenew= " + fileNamenew[0]);

            File file = File.createTempFile(fileName, ".jpg", upload_path);
            //ชื่อไฟล์หลังสร้าง
            System.out.println("test= " + file.getName());

            try (InputStream fileContent = filePart.getInputStream()) {
                Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                System.out.println("File Upload Failure:" + e);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
