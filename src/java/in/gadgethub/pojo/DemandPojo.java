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
public class DemandPojo {

    @Override
    public String toString() {
        return "DemandPojo{" + "useremail=" + useremail + ", prodId=" + prodId + ", demandQuntity=" + demandQuntity + '}';
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public int getDemandQuntity() {
        return demandQuntity;
    }

    public void setDemandQuntity(int demandQuntity) {
        this.demandQuntity = demandQuntity;
    }
    private String useremail;
    private String prodId;
    private int demandQuntity;

    public DemandPojo(String useremail, String prodId, int demandQuntity) {
        this.useremail = useremail;
        this.prodId = prodId;
        this.demandQuntity = demandQuntity;
    }

    public DemandPojo() {
    }
}
