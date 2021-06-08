package com.atradeya.asharani;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.paperdb.Paper;

public class Adapter_new_promotions extends RecyclerView.Adapter<Adapter_new_promotions.ViewHolder> {

    Context mcontext;
    List<Catalogdatum> mlist;

    String catlist;


    public Adapter_new_promotions(Context context, List<Catalogdatum> mlist) {

        this.mcontext=context;
        this.mlist=mlist;

    }

    @NonNull
    @Override
    public Adapter_new_promotions.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout_home_categories, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_new_promotions.ViewHolder holder, int position) {

        holder.SectionName.setText(mlist.get(position).getSectionName());
        Glide.with(mcontext).load(mlist.get(position).getCoverImage()).into(holder.CoverImage);





        Log.e("catlist",catlist+"");

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                catlist = mlist.get(position).getCatalog();

                List<String> myList = new ArrayList<String>(Arrays.asList(catlist.split(",")));

                Log.e("catlist55",myList+"");

                Paper.book().write("CatImageList",myList);

                FragmentTransaction fragmentTransaction;
                fragmentTransaction = ((AppCompatActivity) mcontext ).getSupportFragmentManager().beginTransaction().replace(R.id.containerr, new Catalogue_Promo_Images());
                fragmentTransaction.commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView CoverImage;
        TextView SectionName;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.cardview);
            CoverImage=itemView.findViewById(R.id.imgview);
            SectionName=itemView.findViewById(R.id.txt_product_name);
        }
    }
}
