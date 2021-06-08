package com.atradeya.asharani;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.paperdb.Paper;

public class Adapter_cart_next extends RecyclerView.Adapter<Adapter_cart_next.Viewholder> {

    Context mcontext;
    List<ModelCartMenu> cartMenuList;

    public Adapter_cart_next(Context context, List<ModelCartMenu> cartMenuList) {

        this.cartMenuList=cartMenuList;
        this.mcontext=context;
    }

    @NonNull
    @Override
    public Adapter_cart_next.Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_menu_next, viewGroup, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_cart_next.Viewholder viewholder, int i) {

        viewholder.pname.setText(cartMenuList.get(i).getProductname());
        viewholder.pqnty.setText(cartMenuList.get(i).getQuantity());


        String total= String.valueOf(Integer.valueOf(cartMenuList.get(i).getQuantity())*(Double.valueOf(cartMenuList.get(i).getPrice())));
        double value= Double.valueOf(total);
        String data= String.format("%.2f", value);

        if(Paper.book().read("permission_see_cost","2").equals("1")){

            viewholder.p_price.setText(cartMenuList.get(i).getPrice());
            viewholder.t_price.setText(data);
        }

        else {

            viewholder.p_price.setText("0.00");
            viewholder.t_price.setText("0.00");

        }
            if(Paper.book().read("permission_wholeseller", "5").equals("1")) {

                viewholder.p_price.setText(cartMenuList.get(i).getPrice());
                viewholder.t_price.setText(data);
        }

        Paper.book().write("value",data);
        Paper.book().write("total",total);
        Paper.book().write("pid",cartMenuList.get(i).getProductid());
        Paper.book().write("pqnty",cartMenuList.get(i).getQuantity());

    }

    @Override
    public int getItemCount() {

        return cartMenuList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView pname,pid,p_price,t_price,pqnty;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            pname=itemView.findViewById(R.id.pname);
            p_price=itemView.findViewById(R.id.pprice);
            t_price=itemView.findViewById(R.id.tprice);
            pqnty=itemView.findViewById(R.id.pqnty);

        }
    }
}

