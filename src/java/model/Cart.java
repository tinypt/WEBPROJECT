/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jpa.model.Product;

/**
 *
 * @author GT62VR
 */
public class Cart {
    
    private Map<Integer,LineItem>cart;

    public Cart() {
        cart = new HashMap<>();
    }

    public Cart(Map<Integer, LineItem> cart) {
        this.cart = cart;
    }
    
    public void add(Product prod) {
        LineItem line = cart.get(prod.getProductId());
        if (line==null) {
            cart.put(prod.getProductId(), new LineItem(prod));
        }else {
            line.setQuantity(line.getQuantity()+1);
        }
    }
    
    public void add(Product prod, int qty) {
        LineItem line = cart.get(prod.getProductId());
        if (line==null) {
            cart.put(prod.getProductId(), new LineItem(prod,qty));
        }else {
            line.setQuantity(line.getQuantity()+qty);
        }
    }
    
    public void remove (Product prod) {
        cart.remove(prod.getProductId());
    }
    
    public int getSize() {
        return cart.size();
    }
    
    public void clear() {
        cart.clear();
    }
    
    public int getTotalPriceInCart() {
        int sum=0;
        List<LineItem> lines = new ArrayList<>(cart.values());
        for (LineItem lineItem : lines) {
            sum += lineItem.getTotalprice();
        }
        return sum;
    }
    
    public int getTotalQuantityInCart() {
        int sum=0;
        List<LineItem> lines = new ArrayList<>(cart.values());
        for (LineItem lineItem : lines) {
            sum += lineItem.getQuantity();
        }
        return sum;
    }
    
    public void changeLineProduct(Product prod, int qty) {
        LineItem line = cart.get(prod.getProductId());
        line.setQuantity(qty);
    }
    
    public List<LineItem> getLineItems() {
        List<LineItem> lines = new ArrayList<>(cart.values());
        return lines;
    }
    
}
