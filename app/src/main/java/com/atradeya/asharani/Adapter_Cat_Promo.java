package com.atradeya.asharani;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Adapter_Cat_Promo extends RecyclerView.Adapter<Adapter_Cat_Promo.ViewHolder>  {

    List<String> my_list;
    Context mcontext;
    Catalogue_Promo_Images catalogue_promo_images;
    ImageView img_main_cat_promo;


    public Adapter_Cat_Promo(Context context, List<String> my_list, Catalogue_Promo_Images cataloguePromoImages, ImageView img_main_cat_promo) {

        this.my_list=my_list;
        this.mcontext=context;
        this.catalogue_promo_images=cataloguePromoImages;
        this.img_main_cat_promo=img_main_cat_promo;

    }

    @NonNull
    @Override
    public Adapter_Cat_Promo.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_cat_promo_img, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Cat_Promo.ViewHolder holder, int position) {


        Log.e("sized", my_list.size()+"");

        String []SepString ;
        String  []MainImage;
        String delimiter = "\\[";

        SepString= my_list.get(position).split(delimiter);
        MainImage= my_list.get(0).split(delimiter);

        Log.e("sizeder", SepString[0]+"");

        Glide.with(mcontext).load(MainImage[0]).into(catalogue_promo_images.img_main_cat_promo);

        if (SepString[1].equals("image")){

            Glide.with(mcontext).load(SepString[0]).into(holder.img_row_promo);

        }

        else {

            holder.img_row_promo.setBackgroundResource(R.drawable.pdf_icon);

        }


        catalogue_promo_images.img_main_cat_promo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pdf_permission= Paper.book().read("pdf");

                Log.e("pdf_permission1",pdf_permission+"");
                Log.e("pdf_permission2",SepString[0]+"");

                if (pdf_permission != null && pdf_permission.equals("1")){

                    String fullPath = String.format(Locale.ENGLISH, SepString[0], "PDF_URL_HERE");

                    Log.e("pdf_permission3",fullPath+"");
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fullPath));
                    browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcontext.startActivity(browserIntent);

                }

            }
        });


/*
        String []SepString;
        String delimiter = "\\[";

        SepString= my_list.get(position).split(delimiter);

        Glide.with(mcontext).load(SepString[0]).into(catalogue_promo_images.img_main_cat_promo);

       *//* if (SepString[1].equals("image")){


        }

        else {


            String fullPath = String.format(Locale.ENGLISH, SepString[0], "PDF_URL_HERE");
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fullPath));
            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mcontext.startActivity(browserIntent);

        }*/

        //  Glide.with(mcontext).load(my_list.get(position)).into(holder.img_row_promo);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (SepString[1].equals("image")){

                    Paper.book().write("pdf","0");

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    catalogue_promo_images.img_main_cat_promo.setLayoutParams(params);
                    Glide.with(mcontext).load(SepString[0]).into(catalogue_promo_images.img_main_cat_promo);

                }

                else {

                    Paper.book().write("pdf","1");
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, 200);
                    catalogue_promo_images.img_main_cat_promo.setLayoutParams(params);
                    catalogue_promo_images.img_main_cat_promo.setBackgroundResource(R.drawable.pdf_icon);

                }





                //  Glide.with(mcontext).load(my_list.get(position)).into(img_main_cat_promo);

            }
        });

/*
        Glide.with(mcontext).load(my_list.get(position)).into(holder.img_row_promo);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                catalogue_promo_images = new Catalogue_Promo_Images();

                Log.e("ankitsir",my_list.get(position));

                Glide.with(mcontext).load(my_list.get(position)).into(img_main_cat_promo);

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return my_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_row_promo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            img_row_promo = itemView.findViewById(R.id.img_row_promo);

        }
    }
}
