/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import jpa.model.Product;
import jpa.model.controller.ProductJpaController;
import model.Cart;
import model.LineItem;

/**
 *
 * @author GT62VR
 */
public class UpdateIteminCartServlet extends HttpServlet {

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
        HttpSession session = request.getSession(false);
        Cart cart = (Cart) session.getAttribute("cart");

        //remove
        String[] selectproductid = request.getParameterValues("deleteproduct");
        if (selectproductid != null) {
            List<Integer> prod_id = new ArrayList<>();
            for (String string : selectproductid) {
                int id = Integer.parseInt(string);

                prod_id.add(id);
            }
            ProductJpaController prodCtrl = new ProductJpaController(utx, emf);
            for (Integer integer : prod_id) {
                cart.remove(prodCtrl.findProduct(integer));
            }
        }

        //update
        Enumeration<String> productId = request.getParameterNames();
        while (productId.hasMoreElements()) {
            String code = productId.nextElement();
            if (code.equalsIgnoreCase("deleteproduct")) {
                continue;
            }
            
            int qty = Integer.valueOf(request.getParameter(code));
            int productID = Integer.parseInt(code);
            
            ProductJpaController prodCtrl = new ProductJpaController(utx, emf);
            Product product = prodCtrl.findProduct(productID);

            if (qty == 0) {
                cart.remove(product);
            } else {
                cart.changeLineProduct(prodCtrl.findProduct(productID), qty);
            }
        }
        request.setAttribute("edititem", "แก้ไขสินค้าในตะกร้าแล้ว");
        getServletContext().getRequestDispatcher("/Cart.jsp").forward(request, response);
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
