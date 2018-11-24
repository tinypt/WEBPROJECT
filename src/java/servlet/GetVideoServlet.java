/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
import jpa.model.Product;
import jpa.model.controller.ProductJpaController;
import jpa.model.controller.AccountJpaController;
import jpa.model.controller.OrdersJpaController;

/**
 *
 * @author tinypt
 */
public class GetVideoServlet extends HttpServlet {

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

        AccountJpaController accCtrl = new AccountJpaController(utx, emf);
        Account newacc = accCtrl.findAccount(acc.getAccountId());
        System.out.println("user = " + newacc.getUsername());
        List<Orders> orders = newacc.getOrdersList();
        if (!orders.isEmpty()) {

            List<OrderDetail> temps = new ArrayList<>();
            List<Integer> prod_id_all = new ArrayList<>();

            SimpleDateFormat dt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            for (Orders order : orders) {
                /**
                 * ***********ADD 3 day from order date************
                 */
                //how to add day source
                //https://www.mkyong.com/java/java-how-to-add-days-to-current-date/
                Date datefromorder = order.getOrderDate();
                System.out.println("orderdate = " + dt.format(datefromorder));
                LocalDateTime localDateTime = datefromorder.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                localDateTime = localDateTime.plusDays(3);
                Date dplus3day = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                System.out.println("after plus 3 day =" + dt.format(dplus3day));
                //--------------------------------------------------
                Date today = new Date();
                System.out.println("today = " + dt.format(today));
                System.out.println("before = " + today.before(dplus3day));
                /*-------------------------------------------------*/

                if (today.before(dplus3day)) {
                    temps = order.getOrderDetailList();
                    for (OrderDetail temp : temps) {
                        prod_id_all.add(temp.getProductId().getProductId());
                    }
                }
            }

            List<Integer> prod_id_noduplicate = new ArrayList<>();
            for (Integer integer : prod_id_all) {
                if (!prod_id_noduplicate.contains(integer)) {
                    prod_id_noduplicate.add(integer);
                }
            }

            ProductJpaController prodCtrl = new ProductJpaController(utx, emf);
            List<Product> productAll = new ArrayList<>();
            for (Integer prod_id : prod_id_noduplicate) {
                productAll.add(prodCtrl.findProduct(prod_id));
            }
            session.setAttribute("prod", productAll);
        }
        getServletContext().getRequestDispatcher("/Video.jsp").forward(request, response);
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
