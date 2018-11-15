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
import jpa.model.controller.OrderDetailJpaController;
import jpa.model.controller.OrdersJpaController;

/**
 *
 * @author GT62VR
 */
public class GetOrderDetailServlet extends HttpServlet {

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
        Account acc = (Account) session.getAttribute("acc");

        String orderidStr = request.getParameter("orderid");
        int orderid = Integer.parseInt(orderidStr);

        OrdersJpaController orderCtrl = new OrdersJpaController(utx, emf);
        Orders order = orderCtrl.findOrders(orderid);
        List <OrderDetail> orderdetail=order.getOrderDetailList();
        
        int qty = 0;
        for (OrderDetail orderDetail : orderdetail) {
            qty += orderDetail.getQuantity();
        }
        
        session.setAttribute("quantityall", qty);
        session.setAttribute("order", order);
        session.setAttribute("orderdetail", orderdetail);
        getServletContext().getRequestDispatcher("/GetOrderDetail.jsp").forward(request, response);
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
