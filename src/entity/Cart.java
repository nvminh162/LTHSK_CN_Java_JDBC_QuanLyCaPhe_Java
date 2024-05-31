/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author nvmin
 */
public class Cart {

    private String tenCart;
    private int soLuongCart;
    private double priceCart;
    private Ban ban;

    public Cart() {
    }

    public Cart(String tenCart, int soLuongCart, double priceCart, Ban ban) {
        this.tenCart = tenCart;
        this.soLuongCart = soLuongCart;
        this.priceCart = priceCart;
        this.ban = ban;
    }

    public String getTenCart() {
        return tenCart;
    }

    public void setTenCart(String tenCart) {
        this.tenCart = tenCart;
    }

    public int getSoLuongCart() {
        return soLuongCart;
    }

    public void setSoLuongCart(int soLuongCart) {
        this.soLuongCart = soLuongCart;
    }

    public double getPriceCart() {
        return priceCart;
    }

    public void setPriceCart(double priceCart) {
        this.priceCart = priceCart;
    }

    public Ban getBan() {
        return ban;
    }

    public void setBan(Ban ban) {
        this.ban = ban;
    }

}
