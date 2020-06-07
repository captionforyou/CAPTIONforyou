package com.example.captionforu;

public class SubBoard {
    public Integer no;
    public String link;
    public Integer status;
    public String registerNickname;
    public String requestNickname;
    public String language;
    public String contents;
    public Integer tax;

    public Integer getno() {
        return no;
    }
    public void setno(Integer no) {
        this.no=no;
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

    public Integer getax() {
        return tax;
    }
    public void settax(Integer tax) {
        this.tax=tax;
    }

}
