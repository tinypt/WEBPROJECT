/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import jpa.model.Account;
import jpa.model.Favourite;
import jpa.model.Product;
import jpa.model.controller.AccountJpaController;
import jpa.model.controller.FavouriteJpaController;
import jpa.model.controller.ProductJpaController;

/**
 *
 * @author Hong
 */
public class CheckServlet extends HttpServlet {

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
//        String quantity = request.getParameter("qty");
//        String iprod = request.getParameter("product");
//        
//        System.out.println("qty = "+quantity);
//        System.out.println("productid = "+iprod);
        System.out.println("4444444444444");
        
//        FavouriteJpaController favCtrl = new FavouriteJpaController(utx, emf);
//        ProductJpaController prodCtrl = new ProductJpaController(utx, emf);
//        AccountJpaController acccTrl = new AccountJpaController(utx, emf);
//        Account acc = acccTrl.findAccount(1);
//        Product prod = prodCtrl.findProduct(13);
//        Favourite fav = favCtrl.findFavouriteByproductid(prod,acc);
//        System.out.println("sssss "+fav);
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
