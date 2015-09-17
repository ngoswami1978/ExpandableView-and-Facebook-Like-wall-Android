package com.neerajweb.expandablelistviewtest;

/**
 * Created by Admin on 15/09/2015.
 */
public class DetailInfo {

    private String sequence = "";
    private String name = "";
    private String LogedInUserName="";
    private String postDatetime="";

    public String getSequence() {return sequence;}
    public void setSequence(String sequence) {this.sequence = sequence;    }

    public String getName() {return name;}
    public void setName(String name) {
        this.name = name;
    }

    public String getLogedInUserName() { return LogedInUserName;}
    public void setLogedInUserName(String loginname) {this.LogedInUserName = loginname;}

    public String getpostDatetime() {return postDatetime;}
    public void setpostDatetime(String postDatetime) {this.postDatetime = postDatetime;}
}