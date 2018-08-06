package my.com.tm.FIBO;

import java.io.Serializable;

/**
 * Created by hasanulisyraf on 01/01/2018.
 */

public class fibermodel2 implements Serializable{


    private String orderid;
    private String nettype;
    private String siteid;
    private String customername;
    private String workgroup;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getNettype() {
        return nettype;
    }

    public void setNettype(String nettype) {
        this.nettype = nettype;
    }

    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getWorkgroup() {
        return workgroup;
    }

    public void setWorkgroup(String workgroup) {
        this.workgroup = workgroup;
    }
}
