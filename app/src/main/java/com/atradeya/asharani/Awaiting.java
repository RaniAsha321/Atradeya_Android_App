package com.atradeya.asharani;

import android.app.Activity;
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

public class Awaiting extends Fragment {

    public static int awaiting_data;
    Home homesize;
    Orders_Menu app_size;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<AwatingOrderDatum> dataorderlist;
    List<AwatingOrderDatum> myorderlist;
    Adapter_Order_Awaiting myadapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_awaiting, container, false);
        recyclerView=view.findViewById(R.id.recycler_awaiting);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        myorderlist=new ArrayList<>();

        app_size=new Orders_Menu();

        AwaitingMenu();

        int data=Paper.book().read("awaiting_data");

        if(data != 0){

            app_size.approve_size.setText(String.valueOf(data));
            app_size.approve_size.setBackgroundResource(R.drawable.circle_view_white);
            app_size.approve_size.setTextColor(getResources().getColor(R.color.bluetheme));

        }

        else {

            app_size.approve_size.setVisibility(View.GONE);
        }

        if (Paper.book().read("datarole", "5").equals("4")) {

            if ((Paper.book().read("permission_awaiting", "2").equals("1"))) {

                if(Paper.book().read("permission_all_orders", "2").equals("0")) {

                    app_size.approve_size.setVisibility(View.GONE);

                }
            }
        }

        return view;
    }

     private void AwaitingMenu() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        String userid= Paper.book().read("userid");

        service.AWAITING_CALL(userid).enqueue(new Callback<ModelAwaiting>() {
            @Override
            public void onResponse(Call<ModelAwaiting> call, Response<ModelAwaiting> response) {

                dataorderlist = response.body().getAwatingOrderData();

                if (response.body().getStatusCode().equals(200)) {

                    for (int i = 0; i < dataorderlist.size(); i++) {

                        AwatingOrderDatum orderDatum = new AwatingOrderDatum();
                        orderDatum.setPoReffrence(dataorderlist.get(i).getPoReffrence());
                        orderDatum.setOrderId(dataorderlist.get(i).getOrderId());
                        orderDatum.setDate(dataorderlist.get(i).getDate());
                        orderDatum.setTotalIncVat(dataorderlist.get(i).getTotalIncVat());
                        orderDatum.setTotalExVat(dataorderlist.get(i).getTotalExVat());
                        orderDatum.setStatus(dataorderlist.get(i).getStatus());
                        orderDatum.setOrderDescrption(dataorderlist.get(i).getOrderDescrption());
                        orderDatum.setProjectname(dataorderlist.get(i).getProjectname());

                        myorderlist.add(orderDatum);

                        Paper.book().write("awaitingref",dataorderlist.get(i).getPoReffrence());
                        Paper.book().write("newReference", dataorderlist.get(i).getPoReffrence());
                        Paper.book().write("dateeed", dataorderlist.get(i).getDate());
                    }


                    awaiting_data=myorderlist.size();
                    app_size.approve_size.setText(String.valueOf(awaiting_data));
                    Paper.book().write("awaiting_data", awaiting_data);
                    Paper.book().write("awaiting_key_data",awaiting_data);

                    myadapter = new Adapter_Order_Awaiting(getActivity(), myorderlist);
                    recyclerView.setAdapter(myadapter);
                }

                 else
                {
                    app_size.approve_size.setVisibility(View.GONE);
                    Paper.book().write("awaiting_data", 0);

                }
            }

            @Override
            public void onFailure(Call<ModelAwaiting> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });


    }
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        if (activity.getClass() == Home.class) {
            homesize = (Home) activity;
        }
    }

}


