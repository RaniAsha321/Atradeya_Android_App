package com.atradeya.asharani;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

/**
 * Created by mattr on 7/5/2017.
 */

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    // Declare context object
    private Context mContext;
    private List<String> mParents;
    private List<String> mytype;
    private List<String> my_type_full;
    private Map<String, List<String>> mChildrenMap;
    List<String> checklist;
    String item;
    JSONArray jsonArray;


    /**
     * Constructor
     * @param context
     * @param parents - List of Parents (parents)
     * @param childrenMap - Map populated with our values and their associations (childrenMap)
     */
    public MyExpandableListAdapter(Context context, List<String> parents, Map<String, List<String>> childrenMap) {
        this.mContext = context;
        this.mParents = parents;
        this.mChildrenMap = childrenMap;
    }
    /**
     * @return - the number of "parents" or groups
     */
    @Override
    public int getGroupCount() {
        return mParents.size();
    }

    /**
     * @param groupPosition - the position we are in the map, each map position may have a different count
     * @return - the number of children in each group (under each parent)
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildrenMap.get(mParents.get(groupPosition)).size();
    }

    /**
     * @param groupPosition
     * @return - for any specific position, return the group name
     */
    @Override
    public Object getGroup(int groupPosition) {
        return mParents.get(groupPosition);
    }

    /**
     * @param groupPosition
     * @param childPosition
     * @return - the name of the child (cycles through each parent (or group), and each child of each parent
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildrenMap.get(mParents.get(groupPosition)).get(childPosition);
    }

    /**
     * @param groupPosition
     * @return the groupPosition as the ID
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * @param groupPosition
     * @param childPosition
     * @return the childPosition as the ID
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // not touched
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Get the value of the group item (parent), apply to the TextView using LayoutInflater
     * @param groupPosition
     * @param isExpanded
     * @param convertView // recycles old views in Adapters to increase performance
     * @param parent
     * @return the actual value
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        // Get the value of the parent item
        String parentValue = (String) getGroup(groupPosition);

        // Create the convertView object if it is null
        if(convertView == null) {
            // Inflate the view in our list_parent.xml
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_parent, null);
        }

        // Update the TextView in our convertView (based on the list_parent.xml)
        TextView parentTextView = (TextView) convertView.findViewById(R.id.list_item_parent);
        parentTextView.setText(parentValue);

        return convertView;
    }

    /**
     * Update and return the value of convertView
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView // recycles old views in Adapters to increase performance
     * @param parent
     * @return
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        // get the value of the child item, in the parent (group)
        String childValue = (String) getChild(groupPosition, childPosition);

        // Create the convertView object if it is null
        if(convertView == null) {
            // Inflate the view in our list_parent.xml
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.my_item_layout_filter_value, null);
        }

        // Update the TextView in our convertView (based on the list_child.xml)
        TextView childTextView = (TextView) convertView.findViewById(R.id.list_item_child);
        childTextView.setText(childValue);
        CheckBox check_list = convertView.findViewById(R.id.check_list);
        LinearLayout layout_filter_value= convertView.findViewById(R.id.layout_filter_value);
        jsonArray = new JSONArray();
        checklist = new ArrayList<>();



/*        check_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check_list.setChecked(true);
*//*
                    item =mChildrenMap.get(mParents.get(groupPosition)).get(childPosition);;
                    checklist.add(item);*//*

                Log.e("checkboxlist2",checklist.toString());

                JSONObject order2 = new JSONObject();

                for(int i=0;i<mChildrenMap.size();i++) {

                    try {

                        order2.put(mParents.get(groupPosition), mChildrenMap.get(mParents.get(groupPosition)).get(childPosition));

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    jsonArray.put(order2);

                }

                JSONObject orderobj1 = new JSONObject();
                try {
                    orderobj1.put("Orderdetails", jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String cart_string1 = order2.toString();

                String cart1 = Paper.book().read("ApplyData");

                if (!cart1.equals("")  && cart1 != null){

                    Log.e("cart","1");

                    cart1 = cart1+","+ cart_string1;
                    *//* cart = cart_string+","+ cart;*//*

                    Paper.book().write("ApplyData",cart1);
                }
                else {
                    Log.e("cart","2");
                    cart1 = cart_string1;
                    Paper.book().write("ApplyData",cart1);
                }



                Paper.book().write("ApplyCartData","["+cart1+"]");


                Log.e("checkbox2","["+cart1+"]");


                if(!check_list.isChecked()){

                  *//*  check_list.setChecked(true);
                    item = mParents.get(groupPosition) +":"+ mChildrenMap.get(mParents.get(groupPosition)).get(childPosition);;
                    checklist.add(item);

                    Log.e("checkboxlist2",checklist.toString());*//*
                    check_list.setChecked(true);
*//*
                    item =mChildrenMap.get(mParents.get(groupPosition)).get(childPosition);;
                    checklist.add(item);*//*

                    Log.e("checkboxlist2",checklist.toString());

                    JSONObject order1 = new JSONObject();

                    for(int i=0;i<mChildrenMap.size();i++) {

                        try {

                            order1.put(mParents.get(groupPosition), mChildrenMap.get(mParents.get(groupPosition)).get(childPosition));

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        jsonArray.put(order1);

                    }

                    JSONObject orderobj = new JSONObject();
                    try {
                        orderobj.put("Orderdetails", jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String cart_string = order1.toString();

                    String cart = Paper.book().read("ApplyData");

                    if (!cart.equals("")  && cart != null){

                        Log.e("cart","1");

                        cart = cart+","+ cart_string;
                        *//* cart = cart_string+","+ cart;*//*

                        Paper.book().write("ApplyData",cart);
                    }
                    else {
                        Log.e("cart","2");
                        cart = cart_string;
                        Paper.book().write("ApplyData",cart);
                    }



                    Paper.book().write("ApplyCartData","["+cart+"]");


                    Log.e("checkbox2","["+cart+"]");


                }
                else {

                    check_list.setChecked(false);

                    item = mParents.get(groupPosition) +":"+ mChildrenMap.get(mParents.get(groupPosition)).get(childPosition);
                    checklist.remove(item);

                    Log.e("checkboxlist3",checklist.toString());

                }

            }
        });*/

     check_list.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

             if ( isChecked )
             {
                 // perform logic



                 Log.e("checked","2");

                                   /*  check_list.setChecked(true);
                    item = mParents.get(groupPosition) +":"+ mChildrenMap.get(mParents.get(groupPosition)).get(childPosition);;
                    checklist.add(item);

                    Log.e("checkboxlist2",checklist.toString());*/
                 check_list.setChecked(isChecked);
/*
                    item =mChildrenMap.get(mParents.get(groupPosition)).get(childPosition);;
                    checklist.add(item);*/

                 Log.e("checkboxlist2",checklist.toString());

                 JSONObject order1 = new JSONObject();

                 for(int i=0;i<mChildrenMap.size();i++) {

                     try {

                         order1.put(mParents.get(groupPosition), mChildrenMap.get(mParents.get(groupPosition)).get(childPosition));

                     } catch (JSONException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
                     }

                     jsonArray.put(order1);

                 }

                 JSONObject orderobj = new JSONObject();
                 try {
                     orderobj.put("Orderdetails", jsonArray);
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }

                 String cart_string = order1.toString();

                 String cart = Paper.book().read("ApplyData");

                 if (!cart.equals("")  && cart != null){

                     Log.e("cart","1");

                     cart = cart+","+ cart_string;
                     /* cart = cart_string+","+ cart;*/

                     Paper.book().write("ApplyData",cart);
                 }
                 else {
                     Log.e("cart","2");
                     cart = cart_string;
                     Paper.book().write("ApplyData",cart);
                 }



                 Paper.book().write("ApplyCartData","["+cart+"]");


                 Log.e("checkbox2","["+cart+"]");


             }

             else {


                 Log.e("checked","1");
                 check_list.setChecked(isChecked);

                 item = mParents.get(groupPosition) +":"+ mChildrenMap.get(mParents.get(groupPosition)).get(childPosition);
                 checklist.remove(item);

                 Log.e("checkboxlist3",checklist.toString());


             }

         }
     });

/*
        check_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!check_list.isChecked()){

                  */
/*  check_list.setChecked(true);
                    item = mParents.get(groupPosition) +":"+ mChildrenMap.get(mParents.get(groupPosition)).get(childPosition);;
                    checklist.add(item);

                    Log.e("checkboxlist2",checklist.toString());*//*

                    check_list.setChecked(true);
*/
/*
                    item =mChildrenMap.get(mParents.get(groupPosition)).get(childPosition);;
                    checklist.add(item);*//*


                    Log.e("checkboxlist2",checklist.toString());

                    JSONObject order1 = new JSONObject();

                    for(int i=0;i<mChildrenMap.size();i++) {

                        try {

                            order1.put(mParents.get(groupPosition), mChildrenMap.get(mParents.get(groupPosition)).get(childPosition));

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        jsonArray.put(order1);

                    }

                    JSONObject orderobj = new JSONObject();
                    try {
                        orderobj.put("Orderdetails", jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String cart_string = order1.toString();

                    String cart = Paper.book().read("ApplyData");

                    if (!cart.equals("")  && cart != null){

                        Log.e("cart","1");

                        cart = cart+","+ cart_string;
                        */
/* cart = cart_string+","+ cart;*//*


                        Paper.book().write("ApplyData",cart);
                    }
                    else {
                        Log.e("cart","2");
                        cart = cart_string;
                        Paper.book().write("ApplyData",cart);
                    }



                    Paper.book().write("ApplyCartData","["+cart+"]");


                    Log.e("checkbox2","["+cart+"]");


                }
                else {

                    check_list.setChecked(false);

                    item = mParents.get(groupPosition) +":"+ mChildrenMap.get(mParents.get(groupPosition)).get(childPosition);
                    checklist.remove(item);

                    Log.e("checkboxlist3",checklist.toString());

                }

            }
        });
*/


        layout_filter_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!check_list.isChecked()){

                  /*  check_list.setChecked(true);
                    item = mParents.get(groupPosition) +":"+ mChildrenMap.get(mParents.get(groupPosition)).get(childPosition);;
                    checklist.add(item);

                    Log.e("checkboxlist2",checklist.toString());*/
                    check_list.setChecked(true);
/*
                    item =mChildrenMap.get(mParents.get(groupPosition)).get(childPosition);;
                    checklist.add(item);*/

                    Log.e("checkboxlist2",checklist.toString());

                    JSONObject order1 = new JSONObject();

                    for(int i=0;i<mChildrenMap.size();i++) {

                        try {

                            order1.put(mParents.get(groupPosition), mChildrenMap.get(mParents.get(groupPosition)).get(childPosition));

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        jsonArray.put(order1);

                    }

                JSONObject orderobj = new JSONObject();
                try {
                    orderobj.put("Orderdetails", jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String cart_string = order1.toString();

                    String cart = Paper.book().read("ApplyData");

                    if (!cart.equals("")  && cart != null){

                        Log.e("cart","1");

                        cart = cart+","+ cart_string;
                       /* cart = cart_string+","+ cart;*/

                        Paper.book().write("ApplyData",cart);
                    }
                    else {
                        Log.e("cart","2");
                        cart = cart_string;
                        Paper.book().write("ApplyData",cart);
                    }



                    Paper.book().write("ApplyCartData","["+cart+"]");


               Log.e("checkbox2","["+cart+"]");


                }
                else {

                    check_list.setChecked(false);

                    item = mParents.get(groupPosition) +":"+ mChildrenMap.get(mParents.get(groupPosition)).get(childPosition);
                    checklist.remove(item);

                    Log.e("checkboxlist3",checklist.toString());

                }

            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true; // true - we want the child element to be selectable
    }
}
