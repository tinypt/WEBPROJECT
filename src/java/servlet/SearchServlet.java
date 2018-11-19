/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
import jpa.model.Product;
import jpa.model.controller.ProductJpaController;

/**
 *
 * @author GT62VR
 */
public class SearchServlet extends HttpServlet {

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
        //--encoding to thai
        //source https://www.bamossza.com/article-view?topic_id=6
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        //--

        String name = request.getParameter("name");
        if (name != null /*&& name.trim().length() != 0*/) {
            ProductJpaController prodCtrl = new ProductJpaController(utx, emf);
            List<Product> prod;
            if (isNumeric(name)) {
                int price = Integer.parseInt(name);
                prod = prodCtrl.findByProductPrice(price);

            }else {
                prod = prodCtrl.findByProductName(name);
            }

            if (prod.isEmpty()) {
                String msg = "ผลการค้นหา ไม่ตรงกับชื่อขนม หรือราคาใดๆ";
                request.setAttribute("msg", msg);
            }
            request.setAttribute("prod", prod);
            request.setAttribute("qty", prod.size());
            request.setAttribute("nameSearch", name);
            getServletContext().getRequestDispatcher("/Search.jsp").forward(request, response);
            return;

        }
        getServletContext().getRequestDispatcher("/montho.jsp").forward(request, response);
    }

    public static boolean isNumeric(String str) {
        try {
            int d = Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
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
