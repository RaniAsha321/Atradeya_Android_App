package com.atradeya.asharani;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.budiyev.android.imageloader.ImageLoader;
import com.budiyev.android.imageloader.ImageUtils;
import com.budiyev.android.imageloader.LoadCallback;
import com.bumptech.glide.Glide;

import java.util.List;

import io.paperdb.Paper;

public class Adapter_Home_Categories extends RecyclerView.Adapter<Adapter_Home_Categories.ViewHolder>  {

    private Context mContext;
    private List<Category> modellist_home;

    public Adapter_Home_Categories(List<Category> modelHomeCategories, Context mContext) {

         this.modellist_home=modelHomeCategories;
         this.mContext=mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rowlayout_home_categories, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

    viewHolder.product_name.setText(modellist_home.get(i).getCatName());


        // Simply load image from URL into view
        ImageLoader.with(mContext).from("https://dev.pickmyorder.com/getproductcategory").load(viewHolder.product_image);

        // Advanced usage
        ImageLoader.with(mContext)
                /*Create new load request for specified data.
                  Data types supported by default: URIs (remote and local),
                  files, file descriptors, resources and byte arrays.*/
                .from("https://dev.pickmyorder.com/getproductcategory")
                /*Required image size (to load sampled bitmaps)*/
                .size(1000, 1000)
                /*Display loaded image with rounded corners, optionally, specify corner radius*/
                .roundCorners()
                /*Placeholder drawable*/
                .placeholder(new ColorDrawable(Color.LTGRAY))
                /*Error drawable*/
                .errorDrawable(new ColorDrawable(Color.RED))
                /*Apply transformations*/
                .transform(ImageUtils.cropCenter())
                .transform(ImageUtils.convertToGrayScale())
                /*Load image into view*/
                .load(viewHolder.product_image);
        /*Also, load, error and display callbacks can be specified for each request*/

        // Load image asynchronously without displaying it
        ImageLoader.with(mContext).from("https://some.url/image").onLoaded(new LoadCallback() {
            @Override
            public void onLoaded(@NonNull Bitmap image) {
                // Do something with image here
            }
        }).load();

        // Load image synchronously (on current thread), should be executed on a worker thread
        //Bitmap image = ImageLoader.with(this).from("https://some.url/image").loadSync();







        Glide.with(mContext).load(modellist_home.get(i).getCatImage()).into(viewHolder.product_image);



            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(modellist_home.get(i).getStatus()!=2){

                    FragmentTransaction fragmentTransaction;
                    fragmentTransaction = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction().addToBackStack("home_category").replace(R.id.containerr, new Home_SubCategory());
                    fragmentTransaction.commit();

                        Paper.book().write("filter_data",modellist_home.get(i).getFillter());
                        Paper.book().write("tempcategoryid",modellist_home.get(i).getCatId());
                        Paper.book().write("homestatus", String.valueOf(modellist_home.get(i).getStatus()));
                    }

                    else {
                        Toast.makeText(mContext,"Category/Products Not Available", Toast.LENGTH_SHORT).show();
                    }
                }

            });

    }



    @Override
    public int getItemCount() {
        return modellist_home.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView product_image;
        TextView product_name;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            product_image=itemView.findViewById(R.id.imgview);
            product_name=itemView.findViewById(R.id.txt_product_name);
            cardView=itemView.findViewById(R.id.cardview);
        }
    }
}



