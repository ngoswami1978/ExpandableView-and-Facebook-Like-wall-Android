package com.neerajweb.expandablelistviewtest;

/**
 * Created by Admin on 15/09/2015.
 */
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MyListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<HeaderInfo> deptList;

    public MyListAdapter(Context context, ArrayList<HeaderInfo> deptList) {
        this.context = context;
        this.deptList = deptList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<DetailInfo> productList = deptList.get(groupPosition).getCommentList();
        return productList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        DetailInfo detailInfo = (DetailInfo) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.child_row, null);
        }

        TextView PostcommentId = (TextView) view.findViewById(R.id.childItemId);//PostCommentID
        PostcommentId.setText(detailInfo.getPostcommentId().trim());

        TextView sequence = (TextView) view.findViewById(R.id.sequence);//PostComments Sequence No
        sequence.setText("through Reply " + detailInfo.getSequence().trim() + ": ");

        TextView childItem = (TextView) view.findViewById(R.id.childItem);//Post Comment message
        childItem.setText(detailInfo.getPostcomment().trim());

        TextView childItemUserName = (TextView) view.findViewById(R.id.childItemUserName);//LogedIn UserName
        childItemUserName.setText(detailInfo.getLogedInUserName().trim());

        TextView childItemDatetime = (TextView) view.findViewById(R.id.childItemDatetime);//Message datetime
        childItemDatetime.setText(detailInfo.getpostDatetime().trim());

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<DetailInfo> productList = deptList.get(groupPosition).getCommentList();
        return productList.size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return deptList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return deptList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {

        HeaderInfo headerInfo = (HeaderInfo) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.group_heading, null);
        }

        TextView heading = (TextView) view.findViewById(R.id.heading);
        heading.setText(headerInfo.getName().trim());

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
