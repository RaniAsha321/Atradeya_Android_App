package com.atradeya.asharani;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.paperdb.Paper;

public class Order_menu_Adapter extends RecyclerView.Adapter<Order_menu_Adapter.ViewHolder> {

    Context mcontext;
    List<OrderDatum> datumList;

    public Order_menu_Adapter(Context context, List<OrderDatum> myorderlist) {

        this.mcontext=context;
        this.datumList=myorderlist;
    }

    public Order_menu_Adapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.demopart, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {



        if(datumList.get(i).getOrderStatus().equals("1")){

            viewHolder.layout_status.setVisibility(View.VISIBLE);

        }

        else if(Paper.book().read("permission_wholeseller", "5").equals("1")) {

                viewHolder.layout_status.setVisibility(View.GONE);

        }

        else {

            viewHolder.layout_status.setVisibility(View.GONE);

        }

            viewHolder.product_ref.setText(datumList.get(i).getOrderDescrption());

            viewHolder.ref_no.setText(datumList.get(i).getOrderId());
            viewHolder.project.setText(datumList.get(i).getProject());
            viewHolder.date.setText(datumList.get(i).getDate());

        if(Paper.book().read("permission_see_cost","2").equals("1")){

            if (!datumList.get(i).getDeleveryCost().equals("")){
                String price = String.valueOf(((Double.parseDouble(datumList.get(i).getTotalIncVat()))+(Double.parseDouble(datumList.get(i).getDeleveryCost()))));
                viewHolder.Inc_Vat.setText(price);
            }
            else {
                viewHolder.Inc_Vat.setText(datumList.get(i).getTotalIncVat());
            }

            viewHolder.Ex_Vat.setText(datumList.get(i).getTotalExVat());

        }
        else {

            viewHolder.Inc_Vat.setText("0.00");
            viewHolder.Ex_Vat.setText("0.00");

        }


            if(Paper.book().read("permission_wholeseller", "5").equals("1")) {

                if (!datumList.get(i).getDeleveryCost().equals("")){
                    String price = String.valueOf(((Double.parseDouble(datumList.get(i).getTotalIncVat()))+(Double.parseDouble(datumList.get(i).getDeleveryCost()))));
                    viewHolder.Inc_Vat.setText(price);
                }
                else {
                    viewHolder.Inc_Vat.setText(datumList.get(i).getTotalIncVat());
                }

                viewHolder.Ex_Vat.setText(datumList.get(i).getTotalExVat());
            }


        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction;
                fragmentTransaction = ((AppCompatActivity) mcontext).getSupportFragmentManager().beginTransaction().addToBackStack("o").replace(R.id.containerr, new My_Order_Screen());
                fragmentTransaction.commit();

                Paper.book().write("del_cost1",datumList.get(i).getDeleveryCost());
                Paper.book().write("PO_Number",datumList.get(i).getOrderId());
                Paper.book().write("newReference",datumList.get(i).getPoReffrence());
                Paper.book().write("tempdate",datumList.get(i).getDate());

            }
        });
    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView product_ref,ref_no, date,Ex_Vat, Inc_Vat,project;
        ImageView arrow_click;
        Context context;
        LinearLayout layout_status,linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.cardview_order_menu);
            product_ref=itemView.findViewById(R.id.product_ref);
            ref_no=itemView.findViewById(R.id.ref_no);
            date=itemView.findViewById(R.id.date);
            Ex_Vat=itemView.findViewById(R.id.Ex_Val);
            Inc_Vat=itemView.findViewById(R.id.Inc_Vat);
            arrow_click=itemView.findViewById(R.id.arrow_click);
            project=itemView.findViewById(R.id.project_testing);
            linearLayout=itemView.findViewById(R.id.linear);
            layout_status=itemView.findViewById(R.id.layout_status);
            context=itemView.getContext();

        }
    }
}
