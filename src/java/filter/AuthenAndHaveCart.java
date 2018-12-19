/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.Cart;

/**
 *
 * @author GT62VR
 */
public class AuthenAndHaveCart implements Filter {
    
    private FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        if (session.getAttribute("acc") == null) {
            String url = ((HttpServletRequest)request).getServletPath();
            System.out.println(url);
            session.setAttribute("url", url);
            config.getServletContext().getRequestDispatcher("/Login").forward(request, response);
            return;
        }
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getSize()==0) {
            config.getServletContext().getRequestDispatcher("/montho.jsp").forward(request, response);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
    
}
