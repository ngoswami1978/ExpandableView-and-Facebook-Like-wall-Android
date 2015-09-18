package com.neerajweb.expandablelistviewtest;

/**
 * Created by Admin on 15/09/2015.
 */
public class DetailInfo {

    private String PostcommentId= "";
    private String PostcommentTitleId = "";
    private String sequence = "";
    private String PostcommentTitle = "";
    private String Postcomment = "";
    private String LogedInUserName="";
    private String postDatetime="";

    public String getSequence() {return sequence;}
    public void setSequence(String sequence) {this.sequence = sequence;    }

    public String getPostcommentId() {return PostcommentId;}
    public void setPostcommentId(String PostcommentId) {this.PostcommentId = PostcommentId;    }

    public String getPostcommentTitleId() {return PostcommentTitleId;}
    public void setPostcommentTitleId(String PostcommentTitleId) {this.PostcommentTitleId = PostcommentTitleId;    }

    public String getPostcommentTitle() {return PostcommentTitle;}
    public void setPostcommentTitle(String PostcommentTitle) {this.PostcommentTitle = PostcommentTitle;    }

    public String getPostcomment() {return Postcomment;}
    public void setPostcomment(String strpostcomment) {
        this.Postcomment = strpostcomment;
    }

    public String getLogedInUserName() { return LogedInUserName;}
    public void setLogedInUserName(String loginname) {this.LogedInUserName = loginname;}

    public String getpostDatetime() {return postDatetime;}
    public void setpostDatetime(String postDatetime) {this.postDatetime = postDatetime;}
}