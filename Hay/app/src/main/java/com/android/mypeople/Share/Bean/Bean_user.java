package com.android.mypeople.Share.Bean;

import java.sql.Timestamp;

public class Bean_user {

    /*공용이므로 항상 수정사항 알려주시기 바랍니다.*/

    //Field
    int uSeqno;
    String uId;
    String uPw;
    String uName;
    String uTel;
    Timestamp uInsertDate;
    Timestamp uDeleteDate;

    //Constructor
    public Bean_user(int uSeqno, String uId, String uPw, String uName, String uTel, Timestamp uInsertDate, Timestamp uDeleteDate) {
        this.uSeqno = uSeqno;
        this.uId = uId;
        this.uPw = uPw;
        this.uName = uName;
        this.uTel = uTel;
        this.uInsertDate = uInsertDate;
        this.uDeleteDate = uDeleteDate;
    }

    public Bean_user(){

    }

    //Getters and Setters
    public int getuSeqno() {
        return uSeqno;
    }

    public void setuSeqno(int uSeqno) {
        this.uSeqno = uSeqno;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuPw() {
        return uPw;
    }

    public void setuPw(String uPw) {
        this.uPw = uPw;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuTel() {
        return uTel;
    }

    public void setuTel(String uTel) {
        this.uTel = uTel;
    }

    public Timestamp getuInsertDate() {
        return uInsertDate;
    }

    public void setuInsertDate(Timestamp uInsertDate) {
        this.uInsertDate = uInsertDate;
    }

    public Timestamp getuDeleteDate() {
        return uDeleteDate;
    }

    public void setuDeleteDate(Timestamp uDeleteDate) {
        this.uDeleteDate = uDeleteDate;
    }
}
