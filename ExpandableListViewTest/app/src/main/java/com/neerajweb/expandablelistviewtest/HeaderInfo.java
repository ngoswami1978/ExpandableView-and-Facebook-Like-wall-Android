package com.neerajweb.expandablelistviewtest;

/**
 * Created by Admin on 15/09/2015.
 */
import java.util.ArrayList;

public class HeaderInfo {

    private String name;
    private ArrayList<DetailInfo> commentList = new ArrayList<DetailInfo>();;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<DetailInfo> getCommentList()
    {
        return commentList ;
    }

    public void setCommentList(ArrayList<DetailInfo> commentList)
    {
        this.commentList  = commentList;
    }

}