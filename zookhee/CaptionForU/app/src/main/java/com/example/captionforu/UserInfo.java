package com.example.captionforu;

import com.google.gson.annotations.SerializedName;

public class UserInfo {
    @SerializedName("no")
    public Integer no;

    @SerializedName("id")
    public String id;

    @SerializedName("password")
    public String password;

    @SerializedName("nickname")
    public String nickname;

    @SerializedName("points")
    public Integer points;

    @SerializedName("money")
    public Integer money;

    @SerializedName("requestnum")
    public Integer requestNum;

    @SerializedName("registernum")
    public Integer regiseterNum;

    @SerializedName("ratingcompleteness")
    public double ratingCompletenss;

    @SerializedName("ratingclarity")
    public double ratingClarity;

    @SerializedName("ratingtime")
    public Integer ratingTime;

    @SerializedName("ratingcnt")
    public Integer ratingCnt;

    @SerializedName("noticecnt")
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
    public void setmoney(Integer money) { this.money=money; }

    public Integer getrequestNum() {
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

    public Integer getratingCnt() {
        return ratingCnt;
    }
    public void setratingCnt(Integer ratingCnt) {
        this.ratingCnt=ratingCnt;
    }

    public Integer getnoticeCnt() {
        return noticeCnt;
    }
    public void setnoticeCnt(Integer noticeCnt) {
        this.noticeCnt=noticeCnt;
    }
}
