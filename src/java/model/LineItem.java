/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import jpa.model.Product;

/**
 *
 * @author GT62VR
 */
public class LineItem {
    private Product prod;
    private int priceeach;
    private int quantity;

    public LineItem(Product prod) {
        this(prod,1);
    }
    
    public LineItem(Product prod, int quantity) {
        this.prod = prod;
        this.quantity = quantity;
        this.priceeach = prod.getProductPrice();
    }


    public Product getProd() {
        return prod;
    }

    public void setProd(Product prod) {
        this.prod = prod;
    }

    public int getPriceeach() {
        return priceeach;
    }

    public void setPriceeach(int priceeach) {
        this.priceeach = priceeach;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public int getTotalprice () {
        return priceeach*quantity;
    }
    
}
