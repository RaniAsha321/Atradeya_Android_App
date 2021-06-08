package com.atradeya.asharani;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Adapter_Order_Awaiting extends RecyclerView.Adapter<Adapter_Order_Awaiting.ViewHolder>  {

    Context mcontext;
     List<AwatingOrderDatum> datumList;

    public Adapter_Order_Awaiting(Context context, List<AwatingOrderDatum> myorderlist) {

        this.mcontext=context;
        this.datumList=myorderlist;

    }

    @NonNull
    @Override
    public Adapter_Order_Awaiting.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_awaiting, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Order_Awaiting.ViewHolder viewHolder, int i) {


        Paper.book().write("size",datumList.size());

        viewHolder.product_ref.setText(datumList.get(i).getOrderDescrption());
        viewHolder.ref_no.setText(datumList.get(i).getOrderId());
        viewHolder.date.setText(datumList.get(i).getDate());

        viewHolder.project.setText(datumList.get(i).getProjectname());

        if(Paper.book().read("permission_see_cost","2").equals("1")){
            viewHolder.Inc_Vat.setText(datumList.get(i).getTotalIncVat());
            viewHolder.Ex_Vat.setText(datumList.get(i).getTotalExVat());
        }

        else {

            viewHolder.Inc_Vat.setText("0.00");
            viewHolder.Ex_Vat.setText("0.00");;

        }


        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction;
                fragmentTransaction = ((AppCompatActivity) mcontext).getSupportFragmentManager().beginTransaction().addToBackStack("o").replace(R.id.containerr, new My_Order_Screen());
                fragmentTransaction.commit();

                Paper.book().write("PO_Number",datumList.get(i).getOrderId());
                Paper.book().write("newReference",datumList.get(i).getPoReffrence());
                Paper.book().write("tempdate",datumList.get(i).getDate());
            }
        });

        if (Paper.book().read("datarole","2").equals("4")){

            viewHolder.approve.setVisibility(View.GONE);

        }
        else {

            viewHolder.approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Paper.book().write("awaitingReference",datumList.get(i).getPoReffrence());
                    Paper.book().write("po",datumList.get(i).getOrderId());

                    getApproval();

                }
            });

        }

    }

    private void getApproval() {

        String reference=Paper.book().read("awaitingReference");
        String po=Paper.book().read("po");

        Log.e("reference",reference+"");
        Log.e("po",po+"");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        service.APPROVAL_CALL(reference,po).enqueue(new Callback<Approval>() {
            @Override
            public void onResponse(Call<Approval> call, Response<Approval> response) {

                if (response.body().getStatusCode().equals(200)){

                    Toast.makeText(mcontext,"Order Approved", Toast.LENGTH_SHORT).show();

                    FragmentTransaction fragmentTransaction;
                    fragmentTransaction = ((AppCompatActivity) mcontext).getSupportFragmentManager().beginTransaction().replace(R.id.orders_container, new Awaiting());
                    fragmentTransaction.commit();

                    Paper.book().write("size",datumList.size());

                }
            }

            @Override
            public void onFailure(Call<Approval> call, Throwable t) {

                Toast.makeText(mcontext, "Server Problem", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }

    @Override
    public int getItemCount() {
        return  datumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView product_ref,ref_no, date,Ex_Vat, Inc_Vat,project;
        ImageView arrow_click;
        Context context;
        Button approve;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.cardview_order_menua);
            product_ref=itemView.findViewById(R.id.product_refa);
            ref_no=itemView.findViewById(R.id.ref_noa);
            date=itemView.findViewById(R.id.datea);
            Ex_Vat=itemView.findViewById(R.id.Ex_Vala);
            Inc_Vat=itemView.findViewById(R.id.Inc_Vata);
            arrow_click=itemView.findViewById(R.id.arrow_click);
            approve=itemView.findViewById(R.id.btn_approve);
            project=itemView.findViewById(R.id.project_testinga);

            context=itemView.getContext();

            if(Paper.book().read("datarole","5").equals("1")){

                approve.setVisibility(View.VISIBLE);

            }

            else   if(Paper.book().read("datarole","5").equals("2")){

                approve.setVisibility(View.VISIBLE);

            }

            else   if(Paper.book().read("datarole","5").equals("3")){

                approve.setVisibility(View.VISIBLE);

            }

            else   if(Paper.book().read("datarole","5").equals("4")){

                approve.setVisibility(View.GONE);

            }

        }
    }
}
