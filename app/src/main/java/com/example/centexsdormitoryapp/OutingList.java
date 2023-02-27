package com.example.centexsdormitoryapp;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class OutingList implements Serializable {

    public String outingname,outingnumber,outingdestination,outingreason,comeindate,gooutdate, outingstatus, userId;
    private String id;

    public OutingList(){

    }

    public OutingList(String outingname, String outingnumber, String outingdestination, String outingreason, String comeindate, String gooutdate, String outingstatus, String userId) {

        this.outingname=outingname;
        this.outingnumber=outingnumber;
        this.outingdestination=outingdestination;
        this.outingreason=outingreason;
        this.comeindate=comeindate;
        this.gooutdate=gooutdate;
        this.outingstatus=outingstatus;
        this.userId=userId;

    }

    public void setOutingname(String outingname) {
        this.outingname = outingname;
    }

    public void setOutingnumber(String outingnumber) {
        this.outingnumber = outingnumber;
    }

    public void setOutingdestination(String outingdestination) {
        this.outingdestination = outingdestination;
    }

    public void setOutingreason(String outingreason) {
        this.outingreason = outingreason;
    }

    public void setComeindate(String comeindate) {
        this.comeindate = comeindate;
    }

    public void setGooutdate(String gooutdate) {
        this.gooutdate = gooutdate;
    }

    public void setOutingstatus(String outingstatus) {
        this.outingstatus = outingstatus;
    }

    public void setUserId(String userId){
        this.userId =userId;
    }



    public String getOutingname(){ return outingname; }
    public String getOutingnumber(){ return outingnumber; }
    public String getOutingdestination(){ return outingdestination; }
    public String getOutingreason(){ return  outingreason; }
    public String getComeindate() { return comeindate; }
    public String getGooutdate(){ return gooutdate; }
    public String getOutingstatus(){ return outingstatus; }
    public String getUserId(){ return userId; }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @NonNull
    @Override
    public String toString() {
        return "OutingList{" +
                "outingname='" + outingname + '\'' +
                ", outingnumber='" + outingnumber + '\'' +
                ", outingdestination='" + outingdestination + '\'' +
                ", outingreason='" + outingreason + '\'' +
                ", comeindate='" + comeindate + '\'' +
                ", gooutdate='" + gooutdate + '\'' +
                ", outingstatus='" + outingstatus + '\'' +
                ", userId='" + userId + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
