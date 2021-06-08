package com.atradeya.asharani;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class My_Orders extends Fragment {

    Orders_Menu app_size;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<OrderDatum> dataorderlist;
    List<OrderDatum> myorderlist;
    Order_menu_Adapter myadapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my__orders, container, false);
        recyclerView=view.findViewById(R.id.recyclerview_my_order);

        app_size.approve_size.setVisibility(View.GONE);
        app_size.approve_size.setTextColor(getResources().getColor(R.color.bluetheme));

        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        myorderlist=new ArrayList<>();
        app_size=new Orders_Menu();

        OrderMenu();

        return view;
    }

    private void OrderMenu() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        String userid= Paper.book().read("userid");

        service.ORDER_MENU_CALL(userid).enqueue(new Callback<ModelOrderMenu>() {

            @Override
            public void onResponse(Call<ModelOrderMenu> call, Response<ModelOrderMenu> response) {

                if (response.body().getStatusCode().equals(200)){

                    app_size.approve_size.setVisibility(View.VISIBLE);
                    app_size.approve_size.setText(String.valueOf(response.body().getNumberOfAwting()));
                    app_size.approve_size.setBackgroundResource(R.drawable.circle_view);
                    app_size.approve_size.setTextColor(getResources().getColor(R.color.white));

                     Paper.book().write("awaiting_data",response.body().getNumberOfAwting());

                    dataorderlist=response.body().getOrderData();

                    for (int i=0;i<dataorderlist.size();i++){

                        OrderDatum orderDatum=new OrderDatum();

                        orderDatum.setPoReffrence(dataorderlist.get(i).getPoReffrence());
                        orderDatum.setOrderId(dataorderlist.get(i).getOrderId());
                        orderDatum.setDate(dataorderlist.get(i).getDate());
                        orderDatum.setTotalIncVat(dataorderlist.get(i).getTotalIncVat());
                        orderDatum.setTotalExVat(dataorderlist.get(i).getTotalExVat());
                        orderDatum.setStatus(dataorderlist.get(i).getStatus());
                        orderDatum.setOrderDescrption(dataorderlist.get(i).getOrderDescrption());
                        orderDatum.setProject(dataorderlist.get(i).getProject());
                        orderDatum.setOrderStatus(dataorderlist.get(i).getOrderStatus());
                        orderDatum.setDeleveryCost(dataorderlist.get(i).getDeleveryCost());

                        myorderlist.add(orderDatum);

                        Paper.book().write("newReference",dataorderlist.get(i).getPoReffrence());
                        Paper.book().write("dateeed",dataorderlist.get(i).getDate());

                    }

                    myadapter=new Order_menu_Adapter(getActivity(),myorderlist);
                    recyclerView.setAdapter(myadapter);
                }

                else {

                    Toast.makeText(getActivity(),"You don't have any orders", Toast.LENGTH_SHORT).show();

                    int data=Paper.book().read("awaiting_data");

                    app_size.approve_size.setVisibility(View.VISIBLE);
                    app_size.approve_size.setText(String.valueOf(data));
                    app_size.approve_size.setBackgroundResource(R.drawable.circle_view);
                    app_size.approve_size.setTextColor(getResources().getColor(R.color.white));
                }

            }

            @Override
            public void onFailure(Call<ModelOrderMenu> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });
    }

}
