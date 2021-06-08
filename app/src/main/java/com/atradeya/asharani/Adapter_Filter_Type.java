package com.atradeya.asharani;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.paperdb.Paper;

public class Adapter_Filter_Type extends RecyclerView.Adapter<Adapter_Filter_Type.ViewHolder> {

    Context mcontext;
    private List<String> mytype,my_type_full;
    Filter_List filter_list;
    String item,str1,str2,str3;
    int row_index;


    public Adapter_Filter_Type(Context mcontext, List<String> mytype, List<String> my_type_full, Filter_List filter_list) {

        this.mytype=mytype;
        this.my_type_full=my_type_full;
        this.mcontext=mcontext;
        this.filter_list=filter_list;
    }

    @NonNull
    @Override
    public Adapter_Filter_Type.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_filter_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Filter_Type.ViewHolder holder, int position) {

        holder.txtvw_filter_type.setText(mytype.get(position));

        if (position == 0){

            holder.layout_filter_type.setBackgroundResource(R.color.white);

            item= String.valueOf(my_type_full.get(position));

            item = item.replaceAll("[\\(\\)\\[\\]\\{\\}]","");

            String[] str = item.split("=");  //now str[0] is "hello" and str[1] is "goodmorning,2,1"

            str1 = str[0];  //hello

            str2 = str[1];

            Paper.book().write("f_type",str1);
            filter_list.getFilterValueAdapter(str2,str1);
        }

            holder.layout_filter_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    row_index=position;
                    notifyDataSetChanged();

                }
            });

        if(row_index==position){
            holder.layout_filter_type.setBackgroundResource(R.color.white);
            item= String.valueOf(my_type_full.get(position));

            item = item.replaceAll("[\\(\\)\\[\\]\\{\\}]","");

            String[] str = item.split("=");

            str1 = str[0];

            str2 = str[1];

          /*  Log.e("type",str1);*/

            Paper.book().write("f_type",str1);

            filter_list.getFilterValueAdapter(str2,str1);

        }
        else
        {
            holder.layout_filter_type.setBackgroundResource(R.color.grey);
        }

    }

    @Override
    public int getItemCount() {
        return mytype.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtvw_filter_type;
        LinearLayout layout_filter_type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtvw_filter_type = itemView.findViewById(R.id.txtvw_filter_type);
            layout_filter_type = itemView.findViewById(R.id.layout_filter_type);
        }
    }
}
