/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Lenovo
 */
public class IDutil {
    
    
    //product id
    public static String generateProdId(){
        Date today=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String prodId =sdf.format(today);
        prodId ="P"+prodId;
        return prodId;
    }
    
    //transaction id
    public static String generateTransId(){
        Date today=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmms");
        String transId =sdf.format(today);
        transId ="T"+transId;
        return transId;
    }
}
