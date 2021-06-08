
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AwatingOrderDatum {

    @SerializedName("po_reffrence")
    @Expose
    private String poReffrence;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("total_ex_vat")
    @Expose
    private String totalExVat;
    @SerializedName("total_inc_vat")
    @Expose
    private String totalIncVat;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("Order_descrption")
    @Expose
    private String orderDescrption;
    @SerializedName("projectname")
    @Expose
    private String projectname;


    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }


    public String getPoReffrence() {
        return poReffrence;
    }

    public void setPoReffrence(String poReffrence) {
        this.poReffrence = poReffrence;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalExVat() {
        return totalExVat;
    }

    public void setTotalExVat(String totalExVat) {
        this.totalExVat = totalExVat;
    }

    public String getTotalIncVat() {
        return totalIncVat;
    }

    public void setTotalIncVat(String totalIncVat) {
        this.totalIncVat = totalIncVat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderDescrption() {
        return orderDescrption;
    }

    public void setOrderDescrption(String orderDescrption) {
        this.orderDescrption = orderDescrption;
    }


}
