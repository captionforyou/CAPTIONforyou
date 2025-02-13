package com.example.captionforu;

import com.google.gson.annotations.SerializedName;



public class SubBoard {
    @SerializedName("no")
    public Integer no;

    @SerializedName("link")
    public String link;

    @SerializedName("status")
    public Integer status;

    @SerializedName("registernickname")
    public String registerNickname;

    @SerializedName("registerno")
    public Integer registerno;

    @SerializedName("requestnickname")
    public String requestNickname;

    @SerializedName("language")
    public String language;

    @SerializedName("contents")
    public String contents;

    @SerializedName("tax")
    public Integer tax;

    @SerializedName("israted")
    public Integer israted;

    public Integer getno() {
        return no;
    }
    public void setno(Integer no) { this.no=no; }

    public Integer getregisterno() {
        return registerno;
    }
    public void setregisterno(Integer registerno) {
        this.registerno=registerno;
    }

    public String getlink() {
        return link;
    }
    public void setlink(String link) {
        this.link=link;
    }

    public Integer getstatus() {
        return status;
    }
    public void setstatus(Integer status) { this.status=status; }

    public String getregisterNickname() {
        return registerNickname;
    }
    public void setregisterNickname(String registerNickname) { this.registerNickname=registerNickname; }

    public String getrequestNickname() {
        return requestNickname;
    }
    public void setrequestNickname(String requestNickname) {
        this.requestNickname=requestNickname;
    }


    public String getlanguage() {
        return language;
    }
    public void setlanguage(String language) {
        this.language=language;
    }

    public String getcontents() {
        return contents;
    }
    public void setcontents(String contents) {
        this.contents=contents;
    }

    public Integer gettax() {
        return tax;
    }
    public void settax(Integer tax) {
        this.tax=tax;
    }

    public Integer getisrated() {
        return israted;
    }
    public void setisrated(Integer israted) {
        this.israted=israted;
    }

}
