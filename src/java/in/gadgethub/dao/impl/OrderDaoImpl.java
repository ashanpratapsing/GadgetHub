/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.OrderDAO;
import in.gadgethub.pojo.CartPojo;
import in.gadgethub.pojo.OrderDetailsPojo;
import in.gadgethub.pojo.OrderPojo;
import in.gadgethub.pojo.TransactionPojo;
import in.gadgethub.utility.DBUtil;
import in.gadgethub.utility.IDutil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class OrderDaoImpl implements OrderDAO{

    @Override
    public boolean addOrder(OrderPojo order) {
        boolean status=false;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("Insert into orders values(?,?,?,?,?)");
            ps.setString(1, order.getOrderId());
            ps.setString(2, order.getProdId());
            ps.setInt(3, order.getQuntity());
            ps.setDouble(4, order.getAmount());
            ps.setInt(5, 0);
            int count=ps.executeUpdate();
            status=count>0;
        }catch (SQLException ex) {
            System.out.println("Error occured in addOrder method"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
        
        
            
    }

    @Override
    public boolean addTransaction(TransactionPojo transaction) {
        boolean status=false;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("Insert into transaction values(?,?,?,?)");
            ps.setString(1, transaction.getTransactionId());
            ps.setString(2, transaction.getUseremail());
            java.util.Date d1=transaction.getTransTime();
            java.sql.Date d2=new java.sql.Date(d1.getTime());
            ps.setDate(3, d2);
            ps.setDouble(4, transaction.getAmount());
            int count=ps.executeUpdate();
            status=count>0;
        }catch (SQLException ex) {
            System.out.println("Error occured in addTransaction method"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public List<OrderPojo> getAllOrders() {
        List<OrderPojo> orderList=new ArrayList<>();
        Connection conn= DBUtil.provideConnection();
        Statement st=null;
        ResultSet rs=null;
        try{
            st=conn.createStatement();
            rs=st.executeQuery("Select * From orders");
            while(rs.next()){
                OrderPojo order=new OrderPojo();
                order.setOrderId(rs.getString("orderId"));
                order.setProdId(rs.getString("prodId"));
                order.setQuntity(rs.getInt("quntity"));
                order.setAmount(rs.getDouble("amount"));
                order.setShipped(rs.getInt("shipped"));
                
                orderList.add(order);
            }
            
        }catch (SQLException ex) {
            
            System.out.println("Error  in getAllOrders"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultset(rs);
        DBUtil.closeStatement(st);
        return orderList;
    }

    @Override
    public List<OrderDetailsPojo> getAllOrderDetails(String userEmail) {
         List<OrderDetailsPojo> orderDetailsList=new ArrayList<>();
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("select p.pid as prodid, o.orderid as orderId, o.shipped as shipped, p.image as image, p.pname as pname, o.quantity as qty, o.amount as amount, t.transtime as time, FROM orders o, products p, transactions t where o.orderid=t.transid and o.prodid=p.pid and t.useremail=?");
            ps.setString(1, userEmail);
            rs=ps.executeQuery();
            while(rs.next()){
                OrderDetailsPojo orderDetailsItem=new OrderDetailsPojo();
                orderDetailsItem.setOrderId(rs.getString("orderid"));
                orderDetailsItem.setProdImage(rs.getAsciiStream("image"));
                orderDetailsItem.setProdId(rs.getString("prodid"));
                 orderDetailsItem.setProdName(rs.getString("pname"));
                orderDetailsItem.setQty(rs.getInt("qty"));
                orderDetailsItem.setAmount(rs.getDouble("amount"));
                orderDetailsItem.setTime(rs.getTimestamp("time"));
                orderDetailsItem.setShipped(rs.getInt("shipped"));
                orderDetailsList.add(orderDetailsItem);
            }
        }catch (SQLException ex) {
            System.out.println("Error occured in getAllOrderDetails method"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultset(rs);
        DBUtil.closeStatement(ps);
        return orderDetailsList;
    }

    @Override
    public String shipNow(String orderId, String prodId) {
         String status="Failure!";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
         try{
            ps=conn.prepareStatement("update orders set shipped=1 where orderid=? and prodid=?");
            ps.setString(1, orderId);
            ps.setString(2, prodId);
            
            int count=ps.executeUpdate();
            if(count>0){
                status="Order has been shipped successfully";
            }
        }catch (SQLException ex) {
            System.out.println("Error occured in shipNow method"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;

    }

    @Override
    public String paymentSuccess(String username, double paidAmount) {
        String status="Order Placment Faild";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        CartDAOImpl cartDao=new CartDAOImpl();
        List<CartPojo> cartList=cartDao.getAllCartItems(username);
        if(cartList.isEmpty()){
            return status;
        }
        String transactionId=IDutil.generateTransId();
        TransactionPojo trpojo=new TransactionPojo();
         trpojo.setTransactionId(transactionId);
         trpojo.setUseremail(username);
         trpojo.setAmount(paidAmount);
         trpojo.setTransTime(new java.util.Date());
         boolean result = addTransaction(trpojo);
         if(result==false){
             return status;
         }
         boolean ordered=true;
         ProductDaoImpl productDAO=new ProductDaoImpl();
         for(CartPojo cartPojo:cartList){
            double amount = productDAO.getProductPrice(cartPojo.getProdId())*cartPojo.getQuantity();
            OrderPojo order=new OrderPojo();
            order.setOrderId(transactionId);
            order.setProdId(cartPojo.getProdId());
            order.setQuntity(cartPojo.getQuantity());
            order.setAmount(amount);
            order.setShipped(0);
            ordered=addOrder(order);
            if(!ordered){
                break;
            }
            ordered=cartDao.removeAProduct(cartPojo.getUseremail(), cartPojo.getProdId());
            if(ordered){
                break;
            }
            ordered=productDAO.sellNProduct(cartPojo.getProdId(), cartPojo.getQuantity());
            if(!ordered){
                break;
            }
         }
         if(ordered){
             status="order Placed Succsesfully";
             System.out.println("Transaction Succsesfull:"+transactionId);
         }else{
             System.out.println("Transaction Faild:"+transactionId);
         }
         return status;
    }
    
}
