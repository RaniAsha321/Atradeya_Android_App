package com.atradeya.asharani;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

import static android.content.Context.MODE_PRIVATE;
import static com.atradeya.asharani.Wholeseller.MY_PREFS_NAME;

public class Adapter_Select_City extends RecyclerView.Adapter<Adapter_Select_City.ViewHolder>  {

    Context mycontext;
    List<CityList> myList;
    Dialog mydialog;
    TextView tv_drop_city;


    public void filterList(ArrayList<CityList> filterdNames) {
        this.myList = filterdNames;
        notifyDataSetChanged();
    }


    public Adapter_Select_City(Context context, List<CityList> myList, Dialog dialog, TextView tv_drop_city) {
        this.mycontext=context;
        this.myList=myList;
        this.mydialog=dialog;
        this.tv_drop_city=tv_drop_city;
    }

    @NonNull
    @Override
    public Adapter_Select_City.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Select_City.ViewHolder viewHolder, int i) {

        viewHolder.textViewName.setText(myList.get(i).getTown());

        viewHolder.cardview_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = mycontext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("city",myList.get(i).getTown());
                editor.apply();

                Paper.book().write("data",myList.get(i).getTown());
                Paper.book().write("city",myList.get(i).getTown());
                Paper.book().write("search","1");
                tv_drop_city.setText(myList.get(i).getTown());
                mydialog.dismiss();

            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        CardView cardview_city;
        GetPostcode getPostcode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            cardview_city=itemView.findViewById(R.id.cardview_city);
            getPostcode=new GetPostcode();


        }
    }
}
