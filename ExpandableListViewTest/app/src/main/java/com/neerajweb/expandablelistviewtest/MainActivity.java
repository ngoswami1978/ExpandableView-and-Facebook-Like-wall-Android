package com.neerajweb.expandablelistviewtest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;


public class MainActivity extends Activity implements OnClickListener {
    private LinkedHashMap<String, HeaderInfo> mypostTitle = new LinkedHashMap<String, HeaderInfo>();
    private ArrayList<HeaderInfo> titleList = new ArrayList<HeaderInfo>();
    private MyListAdapter listAdapter;
    private ExpandableListView myList;
    public Context mContext;
    public long lngTitleId;
    Calendar objCalender ;
    SimpleDateFormat df;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            objCalender = Calendar.getInstance();
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            // Load Spinner data from database table
            loadSpinnerData();

            //Just add some data to start with
            loadData();

            //get reference to the ExpandableListView
            myList = (ExpandableListView) findViewById(R.id.myList);
            //create the adapter by passing your ArrayList data
            listAdapter = new MyListAdapter(MainActivity.this, titleList);
            //attach the adapter to the list
            myList.setAdapter(listAdapter);

            //expand all Groups
            expandAll();

            //add new item to the List
            Button add = (Button) findViewById(R.id.addPost);
            add.setOnClickListener(this);

            //listener for child row click
            myList.setOnChildClickListener(myListItemClicked);
            //listener for group heading click
            myList.setOnGroupClickListener(myListGroupClicked);
        }
        catch (Exception Ex)
        {
            Toast.makeText(this,Ex.getMessage() , Toast.LENGTH_LONG).show();
        }
    }

    public void onClick(View v) {

        switch (v.getId()) {

            //add entry to the List
            case R.id.addPost:

                Spinner spinner = (Spinner) findViewById(R.id.postTitle);
                TextView textView = (TextView)spinner.getSelectedView();
                String title = textView.getText().toString();

                //String title = spinner.getSelectedItem().toString();
                EditText editText = (EditText) findViewById(R.id.etPostOrComments);
                String postcomments = editText.getText().toString();
                editText.setText("");

                //add a new item to the list
                int groupPosition = addTitleAndPostsInListView(lngTitleId,title, postcomments);
                //notify the list so that changes can take effect
                listAdapter.notifyDataSetChanged();

                //collapse all groups
                collapseAll();
                //expand the group where item was just added
                myList.expandGroup(groupPosition);
                //set the current group to be selected so that it becomes visible
                myList.setSelectedGroup(groupPosition);

                break;

                // More buttons go here (if any) ...
        }
    }

    //method to expand all groups
    private void expandAll() {
        try
        {
            int count = listAdapter.getGroupCount();
            for (int i = 0; i < count; i++){
                myList.expandGroup(i);
            }
        }
        catch(Exception Ex)
        {
            Toast.makeText(this,Ex.getMessage() , Toast.LENGTH_LONG).show();
        }

    }

    //method to collapse all groups
    private void collapseAll() {
        try
        {
            int count = listAdapter.getGroupCount();
            for (int i = 0; i < count; i++){
                myList.collapseGroup(i);
            }
        }
        catch   (Exception Ex)
        {
            Toast.makeText(this,Ex.getMessage() , Toast.LENGTH_LONG).show();
        }
    }

    //load some initial data into out list
    private void loadData(){
        long lgTitleid;
        try
        {
            // Connect to database and Load Cursor Object
            // database handler
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());

            // get all List of Post and comments from database and update the List View
            ArrayList<DetailInfo> titles = db.getAllPostsComments();

            if (titles.size()>0)
            {
                for(DetailInfo dtls: titles)
                {
                   lgTitleid = Long.parseLong(dtls.getPostcommentTitleId());
                   addTitleAndPostsInListView(lgTitleid,dtls.getPostcommentTitle(),dtls.getPostcomment());
                   //Toast.makeText(this,titles.indexOf(dtls) , Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                addTitleAndPostsInListView(1,"Maintainance?", "hey guys ! pls let me know what is the maintainance cost per month.");
                addTitleAndPostsInListView(1,"Maintainance?", "1K for 1 BHK");
                addTitleAndPostsInListView(1,"Maintainance?", "1.5K for 2 BHK");

                addTitleAndPostsInListView(2,"Laundry Facilities?", "Who is doing Laundry work can anybody le me know?");
                addTitleAndPostsInListView(2,"Laundry Facilities?", "Upper Shopper Stopper");

                addTitleAndPostsInListView(6,"Parking?", "hi! please clear the bikes from ground floor");
                addTitleAndPostsInListView(6,"Parking?","hi ! can anyone let me know who is th owner of white alto?");
            }
        }
        catch (Exception Ex)
        {
            Toast.makeText(this,Ex.getMessage() , Toast.LENGTH_LONG).show();
        }
    }

    //our child listener
    private OnChildClickListener myListItemClicked =  new OnChildClickListener() {
        public boolean onChildClick(ExpandableListView parent, View v,
                                    int groupPosition, int childPosition, long id) {

            //get the group header
            HeaderInfo headerInfo = titleList.get(groupPosition);
            //get the child info
            DetailInfo detailInfo =  headerInfo.getCommentList().get(childPosition);
            //display it or do something with it
            Toast.makeText(getBaseContext(), "Clicked on Detail " + headerInfo.getName()
                    + "/" + detailInfo.getPostcomment(), Toast.LENGTH_LONG).show();
            return false;
        }

    };

    //our group listener
    private OnGroupClickListener myListGroupClicked =  new OnGroupClickListener() {

        public boolean onGroupClick(ExpandableListView parent, View v,
                                    int groupPosition, long id) {

            //get the group header
            HeaderInfo headerInfo = titleList.get(groupPosition);
            //display it or do something with it
            Toast.makeText(getBaseContext(), "Child on Header " + headerInfo.getName(),
                    Toast.LENGTH_LONG).show();

            return false;
        }

    };

    //here we maintain our Comments and post in various Titles
    private int addTitleAndPostsInListView(long intTitleId, String title, String postcomments){
        int groupPosition = 0;
        try
        {
            //check the hash map if the group already exists
            HeaderInfo headerInfo = mypostTitle.get(title);
            //add the group if doesn't exists
            if(headerInfo == null){
                headerInfo = new HeaderInfo();
                headerInfo.setName(title);
                mypostTitle.put(title, headerInfo);
                titleList.add(headerInfo);

                /* No need to Add Title into Database table
                * we already inserted all the default values of titles into database on OncreateAvtivity method
                * in main activity
                */
            }

            //get the children for the group
            ArrayList<DetailInfo> postcommentList = headerInfo.getCommentList();
            //size of the children list
            int listSize = postcommentList.size();
            //add to the counter
            listSize++;

            String formattedDate = df.format(objCalender.getInstance().getTime());

            /* * Write here code to Insert comments and post into databaase */
            // database handler
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            lngTitleId = intTitleId;
            long retid = db.insertPostComment(String.valueOf(listSize),lngTitleId, "Neeraj Goswami",formattedDate, postcomments);
            /* * */

            //create a new child and add that to the group
            DetailInfo detailInfo = new DetailInfo();
            detailInfo.setPostcommentId(String.valueOf(retid));
            detailInfo.setSequence(String.valueOf(listSize));
            detailInfo.setPostcomment(postcomments);
            detailInfo.setLogedInUserName("N"); // default Neeraj later on implement the Login User Name
            detailInfo.setpostDatetime(formattedDate);

            postcommentList.add(0,detailInfo);
            headerInfo.setCommentList(postcommentList);

            //find the group position inside the list
            groupPosition = titleList.indexOf(headerInfo);
            //return groupPosition;
            db.close();
        }
        catch   (Exception Ex)
        {
            Toast.makeText(this,Ex.getMessage() , Toast.LENGTH_LONG).show();
        }
        return groupPosition;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerData() {
        try {
        // database handler
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        // Spinner Drop down elements
        List<String> lables = db.getAllLabels();

        // get a cursor from the database with an "_id" field
        Cursor c = db.getAllTitles();

        if (lables.size()==0)
        {
            // set Default valus of Title for this activity
            db.insertTitle("Maintainance?");
            db.insertTitle("Laundry Facilities?");
            db.insertTitle("Safety and Security?");
            db.insertTitle("Common Areas?");
            db.insertTitle("Home Improvement?");
            db.insertTitle("Parking?");
            db.insertTitle("Fire?");
            db.insertTitle("Renters?");
            db.insertTitle("basic Rules?");
            db.insertTitle("Misc?");

            loadSpinnerData();
        }

        // make an adapter from the cursor
        String[] from = new String[] {"title"};
        int[] to = new int[] {android.R.id.text1};
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to);

        // set layout for activated adapter
        sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Spinner element
        Spinner  spinner = (Spinner) this.findViewById(R.id.postTitle);
        // get xml file spinner and set adapter
        spinner.setAdapter(sca);

        // set spinner listener to display the selected item id
        mContext = this;
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                    Toast.makeText(mContext, "Selected ID=" + id, Toast.LENGTH_LONG).show();
                    lngTitleId = id;
                }
                public void onNothingSelected(AdapterView<?> parent) {}
            });

        db.close();
        }
        catch(Exception Ex)
        {
            Toast.makeText(this,Ex.getMessage() ,Toast.LENGTH_LONG).show();
        }
    }
}
