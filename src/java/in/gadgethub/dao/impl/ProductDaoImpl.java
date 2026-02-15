package in.gadgethub.dao.impl;

import in.gadgethub.dao.ProductDao;
import in.gadgethub.pojo.ProductPojo;
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
public class ProductDaoImpl implements ProductDao{
    public String addProduct(ProductPojo product){
        String status = "Product Registration Failed!";
        if(product.getProdId()==null){
            product.setProdId(IDutil.generateProdId());
        }
        Connection conn= DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("Insert into Products value(?,?,?,?,?,?,?,?)");
            ps.setString(1, product.getProdId());
            ps.setString(2, product.getProdName());
            ps.setString(3, product.getProdType());
            ps.setString(4, product.getProdInfo());
            ps.setDouble(5, product.getProdPrice());
            ps.setInt(6, product.getProdQuantity());
            ps.setBlob(7, product.getProdImage());
            ps.setBoolean(8, true);
            int count = ps.executeUpdate();
            if(count==1){
                status="Product Added SuccessFully With Id: "+product.getProdId();
            }
            
        }catch (SQLException ex) {
            System.out.println("Error  in addProduct"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        
        return status;
    }
    
    public String updateProduct(ProductPojo prevProduct,ProductPojo updatedProduct){
       Connection conn= DBUtil.provideConnection();
        PreparedStatement ps=null;
        String status="Updation Failed!";
        if(!prevProduct.getProdId().equals(updatedProduct.getProdId())){
            status="Product ID's Do Nat Match. Updation Faild!";
            return status;
        }
      
        try{
            ps=conn.prepareStatement("Update products set pname=?, ptype=?, pinfo=?,pprice=?,pquntity=?,image=?,pid=?,available=?");
            
            ps.setString(1, updatedProduct.getProdName());
            ps.setString(2, updatedProduct.getProdType());
            ps.setString(3, updatedProduct.getProdInfo());
            ps.setDouble(4, updatedProduct.getProdPrice());
            ps.setInt(5, updatedProduct.getProdQuantity());
            ps.setBlob(6, updatedProduct.getProdImage());
            ps.setString(7, updatedProduct.getProdId());
            ps.setBoolean(8, true);
            int count = ps.executeUpdate();
            if(count==1){
                status="Product Updated successfully";
            }
            
        }catch (SQLException ex) {
            System.out.println("Error  in UpdateProduct"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }
    public String updateProductPrice(String prodId, double updatePrice){
        Connection conn= DBUtil.provideConnection();
        PreparedStatement ps=null;
        String status = "Product price Updation Failed!";
         try{
            ps=conn.prepareStatement("Update products set pprice=? where pid=? availeble='Y'");
            ps.setDouble(1, updatePrice);
            ps.setString(2, prodId);
            int ans = ps.executeUpdate();
            if(ans==1){
                status="Product Price Updated successfully";
            }
        }catch (SQLException ex) {
            status="Error:"+ex.getMessage();
            System.out.println("Error  in UpdatePrice"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }
    public List<ProductPojo> getAllProducts(){
        List<ProductPojo> productList=new ArrayList<>();
        Connection conn= DBUtil.provideConnection();
        Statement st=null;
        ResultSet rs=null;
        try{
            st=conn.createStatement();
            rs=st.executeQuery("Select * From Products");
            while(rs.next()){
                ProductPojo product=new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                productList.add(product);
            }
        }catch (SQLException ex) {
            
            System.out.println("Error  in getAllProduct"+ex);
            ex.printStackTrace();
        }
            DBUtil.closeResultset(rs);
        DBUtil.closeStatement(st);
        return productList;
    }
    public List<ProductPojo>getAllProductsBytype(String type){
        List<ProductPojo> productList=new ArrayList<>();
        Connection conn= DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("Select * from products where ptype like ? and available='Y' ");
            ps.setString(1, "%"+type+"%");
            rs=ps.executeQuery();
            while(rs.next()){
                ProductPojo product=new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                productList.add(product);
            }
        }catch (SQLException ex) {
            
            System.out.println("Error  in getAllProductsBytype"+ex);
            ex.printStackTrace();
        }
            DBUtil.closeResultset(rs);
        DBUtil.closeStatement(ps);
        return productList;
    }
    public List<ProductPojo>searchAllProducts(String searchTerm){
        List<ProductPojo> productList=new ArrayList<>();
        Connection conn= DBUtil.provideConnection();
        PreparedStatement ps=null;
        searchTerm=searchTerm.toLowerCase();
        ResultSet rs=null;
        try{
             ps=conn.prepareStatement("Select * from products where lower(ptype) like ? or lower(pname) like ? or lower(pinfo) like ? and available='Y'");
            ps.setString(1, "%"+searchTerm+"%");
             ps.setString(2, "%"+searchTerm+"%");
              ps.setString(3, "%"+searchTerm+"%");
            rs=ps.executeQuery();
            while(rs.next()){
                ProductPojo product=new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                productList.add(product);
            }
            
        }catch (SQLException ex) {
            
            System.out.println("Error  in searchAllProducts"+ex);
            ex.printStackTrace();
        }
            DBUtil.closeResultset(rs);
        DBUtil.closeStatement(ps);
        return productList;
    }

    @Override
    public ProductPojo getProductDetails(String prodId) {
       ProductPojo product=null;
        Connection conn= DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
          try{
              ps=conn.prepareStatement("Select * from Products where pid=? and available='Y'");
              ps.setString(1, prodId);
            rs=ps.executeQuery();
            if(rs.next()){
                 product=new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                
            }
            
        }catch (SQLException ex) {
            
            System.out.println("Error  in getProductDetails"+ex);
            ex.printStackTrace();
        }
            DBUtil.closeResultset(rs);
        DBUtil.closeStatement(ps);
        return product;

    }

    @Override
    public int getProductQuantity(String prodId) {
      int quantity=0;
       Connection conn= DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
         try{
            ps=conn.prepareStatement("Select pquantity from products where pid=? and available='Y'");
            ps.setString(1, prodId);
            int ans = ps.executeUpdate();
            if(rs.next()){
               quantity=rs.getInt(1);
            }
        }catch (SQLException ex) {
            
            System.out.println("Error  in getProductQuantity"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return quantity;
    }

    @Override
    public String UpdateProductWithoutImage(String prevProductId, ProductPojo upadatedProduct) {
        Connection conn= DBUtil.provideConnection();
        
        String status="Updation Failed!";
        int prevQuantity=0;
        if(!prevProductId.equals(upadatedProduct.getProdId())){
            status="Product ID's Do Nat Match. Updation Faild!";
            return status;
        }
        
      PreparedStatement ps=null;
        
        try{
            prevQuantity = getProductQuantity(prevProductId);
                         
            ps=conn.prepareStatement("Update products set pname=?, ptype=?, pinfo=?,pprice=?,pquntity=?,pid=? available=?");
            
            ps.setString(1, upadatedProduct.getProdName());
            ps.setString(2, upadatedProduct.getProdType());
            ps.setString(3, upadatedProduct.getProdInfo());
            ps.setDouble(4, upadatedProduct.getProdPrice());
            ps.setInt(5, upadatedProduct.getProdQuantity());
          
            ps.setString(6, upadatedProduct.getProdId());
            ps.setBoolean(7, true);
            int count = ps.executeUpdate();
            if(count==1 && prevQuantity<upadatedProduct.getProdQuantity()){
                status="Product Updated successfully And MAil Sent";
                
                ///code for sending mail
            }else if(count==1){
                status = "product Update successfully";
            }
            
        }catch (SQLException ex) {
            System.out.println("Error  in UpdateProductWithoutImage"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        
        return status;
    }

    @Override
    public double getProductPrice(String prodId) {
         double price=0;
       Connection conn= DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
         try{
            ps=conn.prepareStatement("Select pprice from products where pid=?");
            ps.setString(1, prodId);
            int ans = ps.executeUpdate();
            if(rs.next()){
               price=rs.getDouble(1);
            }
        }catch (SQLException ex) {
            
            System.out.println("Error  in getProductPrice"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return price;

    }

    @Override
    public Boolean sellNProduct(String prodId, int n) {
        Connection conn= DBUtil.provideConnection();
        PreparedStatement ps=null;
        Boolean status = false;
         try{
            ps=conn.prepareStatement("Update products set pquantity=(pquantity - ?) where pid=? and available='Y'");
            ps.setDouble(1, n);
            ps.setString(2, prodId);
            int ans = ps.executeUpdate();
            if(ans==1){
                status=true;
            }
        }catch (SQLException ex) {
            
            System.out.println("Error  in UpdatePrice"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;        
    }

    @Override
    public List<String> getAllProductsType() {
         List<String> productTypeList=new ArrayList<>();
        Connection conn= DBUtil.provideConnection();
        Statement st=null;
        
        ResultSet rs=null;
        try{
             st=conn.createStatement();
            
            rs=st.executeQuery("select distinct ptype from products where available='Y'");
            while(rs.next()){
                
                productTypeList.add(rs.getString(1));
            }
            
        }catch (SQLException ex) {
            
            System.out.println("Error  in getAllProductsType"+ex);
            ex.printStackTrace();
        }
            DBUtil.closeResultset(rs);
        DBUtil.closeStatement(st);
        return productTypeList;
    }

    @Override
    public byte[] getImage(String prodId) {
       byte[]arr=null;
       Connection conn= DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
         try{
            ps=conn.prepareStatement("Select image from products where pid=?");
            ps.setString(1, prodId);
            rs = ps.executeQuery();
            if(rs.next()){
               arr=rs.getBytes(1);
            }
        }catch (SQLException ex) {
            
            System.out.println("Error  in getImage"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return arr;
 
    }

    @Override
    public String removeProduct(String prodId) {
       String status="Product Removel Failed";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;
        try{
            ps1=conn.prepareStatement("update products set available='N' where pid=?");
            ps1.setString(1,prodId); 
            int k = ps2.executeUpdate();
            if (k > 0) {
                status = "Product Removed Successfully";
                ps2=conn.prepareStatement("delete from usercart where prodid=?");
                ps2.setString(1, prodId);
                k=ps2.executeUpdate();
                
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured in remove product method............................"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        return status;
        
        
        
    }
}
