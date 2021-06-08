package com.atradeya.asharani;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class Adapter_Filter_Value extends RecyclerView.Adapter<Adapter_Filter_Value.ViewHolder> {

    Context mcontext;
    ArrayList<String> filter_value;
    String item,type,selected_filter_value;
    List<String> checklist;
    List<String> finallist;


    public Adapter_Filter_Value(Context context, ArrayList<String> filter_value, String type) {

        this.mcontext=context;
        this.filter_value = filter_value;
        this.type=type;
    }

    @NonNull
    @Override
    public Adapter_Filter_Value.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item_layout_filter_value, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Filter_Value.ViewHolder holder, int position) {

       // holder.text_view_value.setText(filter_value.get(position));

        String f_type = Paper.book().read("f_type");

        Log.e("type",f_type);


        holder.layout_filter_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONArray jsonArray = new JSONArray();

                if(!holder.check_list.isChecked()){

                    holder.check_list.setChecked(true);
                    item = filter_value.get(position);
                    checklist.add(item);
                    Log.e("checkboxlist2",checklist.toString());

                }
                else {

                    holder.check_list.setChecked(false);
                    item = filter_value.get(position);
                    checklist.remove(item);
                    Log.e("checkboxlist3",checklist.toString());

                }

                selected_filter_value= String.valueOf(checklist.toString());

                selected_filter_value = selected_filter_value.replaceAll("[\\(\\)\\[\\]\\{\\}]","");

                Log.e("checkboxlistjsonnot",selected_filter_value);
                JSONObject order1 = new JSONObject();

                try {

                    order1.put(type,selected_filter_value);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                finallist.add(String.valueOf(order1));

                jsonArray.put(order1);

                Log.e("suman",order1.toString()+"");

                String selected= order1.toString().replaceAll("[\\(\\)\\[\\]\\{\\}]","");

                String selected_filter_data_type = Paper.book().read("selected_filter_data_type");
                String selected_filter_data = Paper.book().read("selected_filter_data");

                if (selected_filter_data_type!= null && selected_filter_data != null && !selected_filter_data_type.equals(type) && !selected_filter_data.equals(selected) ){

                    selected=selected_filter_data +","+selected;

                    Log.e("data1",selected+"");
                    Log.e("suman2",selected+"");

                    Paper.book().write("selected_filter_data",selected);
                    Paper.book().write("selected_filter_data_type",type);

                }

                else  if (selected_filter_data_type!= null && selected_filter_data_type.equals(type)){

                    Log.e("data2",selected_filter_data+"");

                    selected=selected_filter_data +","+selected;

                    Log.e("suman3",selected+"");

                    Paper.book().write("selected_filter_data",selected);
                    Paper.book().write("selected_filter_data_type",type);

                }

                else {
                    Log.e("data3",selected+"");

                    Paper.book().write("selected_filter_data",selected);
                    Paper.book().write("selected_filter_data_type",type);

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return filter_value.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_view_value;
        CheckBox check_list;
        LinearLayout layout_filter_value;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           // text_view_value = itemView.findViewById(R.id.text_view_value);
            check_list = itemView.findViewById(R.id.check_list);
            layout_filter_value = itemView.findViewById(R.id.layout_filter_value);
            checklist = new ArrayList<>();
            finallist = new ArrayList<>();
        }
    }
}
