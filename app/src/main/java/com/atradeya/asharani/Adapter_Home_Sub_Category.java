package com.atradeya.asharani;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.paperdb.Paper;

public class Adapter_Home_Sub_Category extends RecyclerView.Adapter<Adapter_Home_Sub_Category.ViewHolder> {

    Context mContext;
    List<SubCategory> modellist_home;

    public Adapter_Home_Sub_Category(List<SubCategory> list, Context context) {

        this.mContext=context;
        this.modellist_home=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_home_sub_category, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {


        viewHolder.product_name.setText(modellist_home.get(i).getSubCatName());
        Glide.with(mContext).load(modellist_home.get(i).getCatImage()).into(viewHolder.product_image);

        if(modellist_home.get(i).getStatus()!=2) {
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentTransaction fragmentTransaction;
                    fragmentTransaction = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction().addToBackStack("l").replace(R.id.containerr, new Home_Sub_Sub_Category());
                    fragmentTransaction.commit();

                    Paper.book().write("tempsubcategoryid", modellist_home.get(i).getSubCatId());
                    Paper.book().write("substatus", String.valueOf(modellist_home.get(i).getStatus()));
                }
            });

        }

        else{

            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(mContext,"Category/Products Not Available", Toast.LENGTH_SHORT).show();

                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return modellist_home.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView product_image;
        TextView product_name;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            product_image=itemView.findViewById(R.id.imgview_sub_category);
            product_name=itemView.findViewById(R.id.txt_product_name_sub_category);
            cardView=itemView.findViewById(R.id.cardview_sub_category);
        }
    }
}
