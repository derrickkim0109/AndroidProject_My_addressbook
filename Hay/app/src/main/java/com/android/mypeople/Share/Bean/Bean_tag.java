package com.android.mypeople.Share.Bean;

public class Bean_tag {

    /*공용이므로 항상 수정사항 알려주시기 바랍니다.*/

    //Field
    String tag1;
    String tag2;
    String tag3;
    String tag4;
    String tag5;
    int user_uSeqno;



    //Constructor

    public  Bean_tag(){


    }

    public Bean_tag(String tag1, String tag2, String tag3, String tag4, String tag5, int user_uSeqno) {
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
        this.tag4 = tag4;
        this.tag5 = tag5;
        this.user_uSeqno = user_uSeqno;
    }







    //Getters and Setters
    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    public String getTag3() {
        return tag3;
    }

    public void setTag3(String tag3) {
        this.tag3 = tag3;
    }

    public String getTag4() {
        return tag4;
    }

    public void setTag4(String tag4) {
        this.tag4 = tag4;
    }

    public String getTag5() {
        return tag5;
    }

    public void setTag5(String tag5) {
        this.tag5 = tag5;
    }

    public int getUser_uSeqno() {
        return user_uSeqno;
    }

    public void setUser_uSeqno(int user_uSeqno) {
        this.user_uSeqno = user_uSeqno;
    }
}
