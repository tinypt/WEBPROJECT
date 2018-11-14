/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
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
import jpa.model.OrderDetail;
import jpa.model.Orders;
import jpa.model.controller.AccountJpaController;
import jpa.model.controller.OrderDetailJpaController;
import jpa.model.controller.OrdersJpaController;
import model.Cart;
import model.LineItem;

/**
 *
 * @author Hong
 */
public class CheckoutServlet extends HttpServlet {

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
        //Filter cart acc
        HttpSession session = request.getSession(false);
        Account acc = (Account) session.getAttribute("acc");
        Cart cart = (Cart) session.getAttribute("cart");
        List<LineItem> lines = cart.getLineItems();
        
        Date d = new Date();
        int totalPrice = cart.getTotalPriceInCart();
        
        //orders
        OrdersJpaController orderJpaCtrl = new OrdersJpaController(utx, emf);
        Orders order = new Orders();
        
        order.setOrderDate(d);
        order.setTotalprice(totalPrice);
        order.setAccountId(acc);
        orderJpaCtrl.create(order);
       
        //for orderid
        order = orderJpaCtrl.findByOrderDate(d);
        int orderId = order.getOrderId();
        
       //orderdetail
        OrderDetailJpaController orderdJpaCtrl = new OrderDetailJpaController(utx, emf);
        OrderDetail order_detail = new OrderDetail();
        for (LineItem line : lines) {
            order_detail.setPriceeach(line.getPriceeach());
            order_detail.setProductId(line.getProd());
            order_detail.setOrderId(order);
            order_detail.setQuantity(line.getQuantity());
            
            orderdJpaCtrl.create(order_detail);
        }
        cart.clear();
        getServletContext().getRequestDispatcher("/montho.jsp").forward(request, response);
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
            Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
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
