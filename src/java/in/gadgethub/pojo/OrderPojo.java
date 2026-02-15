/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.pojo;

/**
 *
 * @author Lenovo
 */
public class OrderPojo {

    @Override
    public String toString() {
        return "OrderPojo{" + "orderId=" + orderId + ", prodId=" + prodId + ", quntity=" + quntity + ", amount=" + amount + ", shipped=" + shipped + '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public int getQuntity() {
        return quntity;
    }

    public void setQuntity(int quntity) {
        this.quntity = quntity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getShipped() {
        return shipped;
    }

    public void setShipped(int shipped) {
        this.shipped = shipped;
    }

    public OrderPojo(String orderId, String prodId, int quntity, double amount, int shipped) {
        this.orderId = orderId;
        this.prodId = prodId;
        this.quntity = quntity;
        this.amount = amount;
        this.shipped = shipped;
    }
    private String orderId;
    private String prodId;
    private int quntity;
    private double amount;
    private int shipped;

    public OrderPojo() {
    }
    
}
