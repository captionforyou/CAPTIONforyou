package com.example.captionforu;

public class UserInfo {
    public Integer no;
    public String id;
    public String password;
    public String nickname;
    public Integer points;
    public Integer money;
    public Integer requestNum;
    public Integer regiseterNum;
    public double ratingCompletenss;
    public double ratingClarity;
    public Integer ratingTime;
    public Integer noticeCnt;

    public Integer getno() {
        return no;
    }
    public void setno(Integer no) {
        this.no=no;
    }

    public String getid() {
        return id;
    }
    public void setid(String id) {
        this.id=id;
    }

    public String getpw() {
        return password;
    }
    public void setpw(String password) {
        this.password=password;
    }

    public String getnn() {
        return nickname;
    }
    public void setnn(String nickname) {
        this.nickname=nickname;
    }

    public Integer getpoints() {
        return points;
    }
    public void setpoints(Integer points) {
        this.points=points;
    }

    public Integer getmoney() {
        return money;
    }
    public void setmoney(Integer money) {
        this.money=money;
    }

    public Integer gerequestNum() {
        return requestNum;
    }
    public void setrequestNum(Integer requestNum) {
        this.requestNum=requestNum;
    }

    public Integer getregiseterNum() {
        return regiseterNum;
    }
    public void setregiseterNum(Integer regiseterNum) {
        this.regiseterNum=regiseterNum;
    }

    public double getratingCompletenss() {
        return ratingCompletenss;
    }
    public void setratingCompletenss(double ratingCompletenss) { this.ratingCompletenss=ratingCompletenss; }

    public double getratingClarity() {
        return ratingClarity;
    }
    public void setratingClarity(double ratingClarity) { this.ratingClarity=ratingClarity; }

    public Integer getratingTime() {
        return ratingTime;
    }
    public void setratingTime(Integer ratingTime) {
        this.ratingTime=ratingTime;
    }

    public Integer getnoticeCnt() {
        return noticeCnt;
    }
    public void setnoticeCnt(Integer noticeCnt) {
        this.noticeCnt=noticeCnt;
    }
}
