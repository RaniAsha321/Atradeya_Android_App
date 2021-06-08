package com.atradeya.asharani;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.paperdb.Paper;

public class Adapter_My_Order_Screen extends RecyclerView.Adapter<Adapter_My_Order_Screen.ViewHolder> {

    List<SingleOrderDatum> datumList;
    Context context;

    public Adapter_My_Order_Screen(Context context, List<SingleOrderDatum> singleOrderList) {

        this.datumList=singleOrderList;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_order_description_new, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.qnty.setText(datumList.get(i).getQty());
        viewHolder.product_name.setText(datumList.get(i).getProductName());
        Glide.with(context).load(datumList.get(i).getImage()).into(viewHolder.product_image);

        if(!(datumList.get(i).getVariationOptionName().equals("0"))){

            viewHolder.variation.setText(datumList.get(i).getVariationOptionName());
            //viewHolder.slash.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.variation.setVisibility(View.GONE);
           // viewHolder.slash.setVisibility(View.GONE);
        }


        if(Paper.book().read("permission_see_cost","2").equals("1")){
            viewHolder.price.setText(String.format("%.2f", Double.valueOf(((Double.valueOf(datumList.get(i).getPrice()))*(Integer.parseInt(datumList.get(i).getQty()))))));
        }



        else {

            viewHolder.price.setText("0.00");

            if(Paper.book().read("datarole", "5").equals("4")){

                if(Paper.book().read("permission_wholeseller", "5").equals("1")) {

                    viewHolder.price.setText(String.format("%.2f", Double.valueOf(((Double.valueOf(datumList.get(i).getPrice()))*(Integer.parseInt(datumList.get(i).getQty()))))));

                }

            }

        }



    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView product_name,qnty,price,variation,slash;
        ImageView product_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            slash=itemView.findViewById(R.id.variation_slash);
            variation=itemView.findViewById(R.id.variation);
            product_name=itemView.findViewById(R.id.product);
            cardView=itemView.findViewById(R.id.cardview_order_menu);
            qnty=itemView.findViewById(R.id.qnty);
            price=itemView.findViewById(R.id.price_product);
            product_image=itemView.findViewById(R.id.image_product);

        }
    }
}
