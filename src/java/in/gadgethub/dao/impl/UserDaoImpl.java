/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.UserDao;
import in.gadgethub.pojo.UserPojo;
import in.gadgethub.utility.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Lenovo
 */
public class UserDaoImpl implements UserDao {

    public boolean isRegistered(String emailId) {
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean flag = false;
        try {
            ps = conn.prepareStatement("Select * from users where useremail=?");
            ps.setString(1, emailId);
            rs = ps.executeQuery();
            if (rs.next()) {
                flag = true;
            }

        } catch (SQLException ex) {
            System.out.println("Exception occured in isRegister");
            ex.printStackTrace();
        }
        DBUtil.closeResultset(rs);
        DBUtil.closeStatement(ps);
        return flag;
    }

    
    public String registerUser(UserPojo user) {
        String status = "Registration Failed";
        boolean isUserRegistered = isRegistered(user.getUseremail());
        if (isUserRegistered) {
            status = "Email Already Registerd. Try Again";
            return status;
        }
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("Insert into users values (?,?,?,?,?,?)");
            ps.setString(1, user.getUseremail());
            ps.setString(2, user.getUsername());

            ps.setString(3, user.getMobile());

            ps.setString(4, user.getAddress());

            ps.setString(6, user.getPassword());
             ps.setInt(5, user.getPincode());
             int count=ps.executeUpdate();
            if(count==1){
                status="Registration Successful";
                //Code to send email
            }

        } catch (SQLException ex) {
            System.out.println("Exception in userRegister");
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;

    }

    @Override
    public String isValidCredentials(String emailId, String password) {
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String status = "Login Denied.Invailed Username Password";
        try {
            ps = conn.prepareStatement("Select 1 from users where useremail=? and password=?");
            ps.setString(1, emailId);
            ps.setString(1, password);

            rs = ps.executeQuery();
            if (rs.next()) {
                status = "Login SuccessFull";
            }

        } catch (SQLException ex) {
            status = "Error:" + ex.getMessage();
            System.out.println("Exception occured in isvalidCredentainls");
            ex.printStackTrace();
        }
        DBUtil.closeResultset(rs);
        DBUtil.closeStatement(ps);
        return status;

    }

    @Override
    public UserPojo getUserDetails(String emailId) {
        Connection conn = DBUtil.provideConnection();
        UserPojo user=null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = conn.prepareStatement("select * from users where useremail=?");
            ps.setString(1, emailId);
            rs = ps.executeQuery();
            if(rs.next()){
                user=new UserPojo();
                user.setUseremail(rs.getString("useremail"));
                user.setUsername(rs.getString("username"));
                user.setMobile(rs.getString("mobile"));
                user.setAddress(rs.getString("address"));
                user.setPincode(rs.getInt("pincode"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured in getUserDetails");
            ex.printStackTrace();
        }
        DBUtil.closeResultset(rs);
        DBUtil.closeStatement(ps);
        return user;
    }

    @Override
    public String getUserFirstName(String emailId) {
       Connection conn = DBUtil.provideConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
       String  fName = null;
        try {
            ps = conn.prepareStatement("select username from users where useremail=?");
            ps.setString(1, emailId);
            rs = ps.executeQuery();
            if (rs.next()) {
              String fullName=rs.getString(1);
              fName = fullName.split(" ")[0];
                          }
        } catch (SQLException ex) {
            System.out.println("Error  in getUserFirstName"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultset(rs);
    return fName;
    }

    @Override
    public String getUserAddr(String emailId) {
         Connection conn = DBUtil.provideConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
       String  address = null;
        try {
            ps = conn.prepareStatement("select address from users where useremail=?");
            ps.setString(1, emailId);
            rs = ps.executeQuery();
            if (rs.next()) {
              address=rs.getString(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error  in isRegistered"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultset(rs);
    return address;
    }

}
