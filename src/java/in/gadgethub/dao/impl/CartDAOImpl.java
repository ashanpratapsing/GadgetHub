/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.CartDAO;
import in.gadgethub.pojo.CartPojo;
import in.gadgethub.pojo.DemandPojo;
import in.gadgethub.utility.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class CartDAOImpl implements CartDAO{

    @Override
    public String addProductToCart(CartPojo cart) {
        String status="Failed to Add into Cart";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;
        ResultSet rs=null;
        try{
            ps1=conn.prepareStatement("select * from usercart where prodid=? and useremil=?");
            ps1.setString(1, cart.getProdId());
            ps1.setString(2, cart.getUseremail());
            rs=ps1.executeQuery();
            int qty=cart.getQuantity();
            if(rs.next()){
                ProductDaoImpl prodDao=new ProductDaoImpl();
                int StockQty=prodDao.getProductQuantity(cart.getProdId());
                int newQty=cart.getQuantity()+rs.getInt("Quantity");
                if(StockQty<newQty){
                  cart.setQuantity(newQty);
                  status=updateProductInToCart(cart);
                  status="Only"+StockQty+"no of items are avialble in our stock so we are adding "+ StockQty+"in your Cart";
                  DemandPojo demandPojo=new DemandPojo();
                  demandPojo.setProdId(cart.getProdId());
                  demandPojo.setUseremail(cart.getUseremail());
                  demandPojo.setDemandQuntity(newQty-StockQty);
                  DemandImpl demandDao=new DemandImpl();
                  Boolean result=demandDao.addProduct(demandPojo);
                  if(result==true){
                      status="We will Mail you when"+(newQty-StockQty)+"no of item will be availble";
                  }
                }else{
                    cart.setQuantity(newQty);
                    status=updateProductInToCart(cart);
                }
               
            }   
                
            }catch (SQLException ex) {
                status="Product Addition Faild Due To Exception:";
            System.out.println("Error occured in addProductToCart method"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultset(rs);
        DBUtil.closeStatement(ps2);
        DBUtil.closeStatement(ps1);
        return status;

    }

    @Override
    public String updateProductInToCart(CartPojo cart){
        String status="Failed to Add to cart";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps1=null;
         PreparedStatement ps2=null;
        ResultSet rs=null;
        try{
            ps1=conn.prepareStatement("select * from usercart where prodid=? and useremil=?");
            ps1.setString(1, cart.getProdId());
            ps1.setString(2, cart.getUseremail());
            rs=ps1.executeQuery();
            int qty=cart.getQuantity();
            if(rs.next()){
                int currentQuantity=rs.getInt("quantity");
                if(qty>0){
                    int newQuantity=currentQuantity+cart.getQuantity();
                    ps2=conn.prepareStatement("update usercart set quantity=? where useremail=? and prodid=? ");
                    ps2.setInt(1, newQuantity);
                    ps2.setString(3, cart.getProdId());
                    ps2.setString(2, cart.getUseremail());
                    int result=ps2.executeUpdate();
                    if(result>0){
                        status="Product Successfully Updated to Cart!";
                    }
                }
                else if(qty==0){
                    ps2=conn.prepareStatement("delete from usercart where useremail=? and prodid=?  ");
                    ps2.setString(2, cart.getProdId());
                    ps2.setString(1, cart.getUseremail());
                    int result=ps2.executeUpdate();
                    if(result>0){
                        status="Product Successfully Updated in Cart!";
                    }
                }else{
                    status="could not update the product";
                }
            }
            else{
                if(cart.getQuantity()>0){
                    ps2=conn.prepareStatement("insert into usercart values(?,?,?)");
                    ps2.setString(2, cart.getProdId());
                    ps2.setString(1, cart.getUseremail());
                    ps2.setInt(3, cart.getQuantity());
                    int result=ps2.executeUpdate();
                    if(result>0){
                        status="Product Successfully Added into the Cart!";
                    }else{
                    status="could not insert the product";
                }
                }
            }
           
        }catch (SQLException ex) {
            System.out.println("Error occured in updateProductInCart method"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultset(rs);
        DBUtil.closeStatement(ps2);
        DBUtil.closeStatement(ps1
        );
        return status;
   }

    @Override
   public List<CartPojo>getAllCartItems(String userId) {
        List<CartPojo>cartItemList=new ArrayList<>();
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("select * from usercart where useremail=?");
           ps.setString(1, userId);
            rs=ps.executeQuery();
            while(rs.next()){
                CartPojo cartItems=new CartPojo();
                cartItems.setUseremail(rs.getString("useremail"));
                cartItems.setProdId(rs.getString("prodid"));
                cartItems.setQuantity(rs.getInt("quantity"));
                cartItemList.add(cartItems);
            }
        }catch (SQLException ex) {
            System.out.println("Error occured in getAllCartItems method"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultset(rs);
        DBUtil.closeStatement(ps);
        return cartItemList;
    }

    @Override
    public int getCartItemCount(String userId, String itemId) {
        int quantity=0;
        if(userId==null || itemId==null)
            return 0;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
       
        try{
            ps=conn.prepareStatement("select quantity from usercart where useremail=? and prodid=?");
            ps.setString(1, userId);
            ps.setString(2, itemId);
            rs=ps.executeQuery();
            if(rs.next()){
                quantity=rs.getInt(1);
            }
        }catch (SQLException ex) {
            System.out.println("Error occured in getCartItemCount method"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultset(rs);
        DBUtil.closeStatement(ps);
        return quantity;
    }

    @Override
    public String removeProductFromCart(String userId, String prodId) {
        String status="Product Removel Failed";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;

        ResultSet rs=null;
        try{
            
             ps1=conn.prepareStatement("select * from usercart where prodid=? and useremil=?");
            ps1.setString(1, prodId);
            ps1.setString(2, userId);
            rs=ps1.executeQuery();
            if(rs.next()){
                int prodQuantity = rs.getInt("quantity");
                prodQuantity-=1;
                if(prodQuantity>0){
                    ps2=conn.prepareStatement("update usercart set quantity=? where useremail=? and prodid=?");
                    ps2.setInt(1, prodQuantity);
                    ps2.setString(2, userId);
                    ps2.setString(3, prodId);
                    int k=ps2.executeUpdate();
                    if(k>0){
                        status="Product successesfully removed from the cart";
                }
            }else if(prodQuantity==0){
                    ps2=conn.prepareStatement("delete from usercart  where useremail=? and prodid=?");
                    ps2.setString(1, userId);
                    ps2.setString(2, prodId);
                    int k=ps2.executeUpdate();
                    if(k>0){
                        status="Product successesfully removed from the cart";
                }
            }
          }
            
            }catch (SQLException ex) {
                status="Removl faild due to exception";
            System.out.println("Error occured in removeProductFromCart"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultset(rs);
        DBUtil.closeStatement(ps2);
        DBUtil.closeStatement(ps1);
        return status;

        }

    @Override
    public boolean removeAProduct(String userId, String prodId) {
        boolean status=false;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("delete from usercart where useremail=? and prodid=?");
            ps.setString(1, userId);
            ps.setString(2,prodId);
            int result=ps.executeUpdate();
            if(result>0){
                status=true;
            }
        }catch (SQLException ex) {
            System.out.println("Error occured in getCartItemCount method............................"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }
}