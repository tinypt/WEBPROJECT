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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        //map<product,expdate>
        Map<Product, String> video = new HashMap<>();
        if (!orders.isEmpty()) {

            List<OrderDetail> temps = new ArrayList<>();
            SimpleDateFormat dt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            for (Orders order : orders) {

                Date today = new Date();
                Date datefromorder = order.getOrderDate();
                Date dplus15day = plus15Days(datefromorder);
//                System.out.println("expdate (plus 15 days) =" + dt.format(dplus15day));
//                System.out.println("today = " + dt.format(today));
//                System.out.println("can view?(T/F) = " + today.before(dplus15day));
//                System.out.println("-------------------------------");
//                -------------------------------------------------

                if (today.before(dplus15day)) {
                    temps = order.getOrderDetailList();
                    for (OrderDetail temp : temps) {
                        //map will change value automatically when key is duplicate
                        video.put(temp.getProductId(), dt.format(dplus15day));
                    }
                }
            }
            
            System.out.println(video);
            session.setAttribute("videosize", video.size());
            session.setAttribute("video", video);
        }
        getServletContext().getRequestDispatcher("/Video.jsp").forward(request, response);
    }

    public Date plus15Days(Date datefromorder) {
        //***********ADD 15 day from order date************
        //how to add day source
        //https://www.mkyong.com/java/java-how-to-add-days-to-current-date/
        LocalDateTime localDateTime = datefromorder.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusDays(15);
        Date dayplus15 = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return dayplus15;
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
