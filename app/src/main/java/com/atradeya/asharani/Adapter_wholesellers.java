package com.atradeya.asharani;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.paperdb.Paper;

public class Adapter_wholesellers extends RecyclerView.Adapter<Adapter_wholesellers.Viewholder> {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Context context;
    List<Wholselear> my_list;
    List<Wholselear> my_list_whole;
    databaseSqlite databaseSqlite;


    public Adapter_wholesellers(List<Wholselear> my_list, Context context) {

        this.context=context;
        this.my_list=my_list;
        this.my_list_whole=my_list;

        databaseSqlite=new databaseSqlite(context);
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rowlayout_home_categories, viewGroup, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int i) {
        Glide.with(context).load(my_list.get(i).getBussinessLogo()).into(viewholder.product_image);
        viewholder.wholseller.setText(my_list.get(i).getBusiness());

        viewholder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Paper.book().write("wholeseller_business_id",my_list.get(i).getBusinessId());
                Paper.book().write("wholeseller_bus_id",my_list.get(i).getBusinessId());
                Paper.book().write("stripe_publish_key",my_list.get(i).getStripePublishkey());
                Paper.book().write("search_id",my_list.get(i).getId());
                Paper.book().write("wholesaler_id",my_list.get(i).getId());
                Paper.book().write("post_wholesaler","1");
                ((Home)context).getProducts(my_list.get(i).getId());

                ((Home)context).nav_search_layout.setVisibility(View.VISIBLE);
                ((Home)context).search_txtvw.setText("");
                ((Home)context).layout_home_filter.setVisibility(View.GONE);
                ((Home)context).layout_gps_home.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public int getItemCount() {
        return my_list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        ImageView product_image;
        TextView wholseller;
        CardView cardView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            product_image=itemView.findViewById(R.id.imgview);
            wholseller=itemView.findViewById(R.id.txt_product_name);
            cardView=itemView.findViewById(R.id.cardview);
        }
    }
}
