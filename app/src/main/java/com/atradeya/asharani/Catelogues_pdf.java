package com.atradeya.asharani;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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

public class Catelogues_pdf extends Fragment {

    Home homes;
    List<Datum> datalist;
    List<Datum> mlist;
    LinearLayoutManager layoutManager;
    RecyclerView catalogues_recyclerview;
    Adapter_Catalogues adapter_catalogues;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.catalogues, container, false);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    return true;
                }

                homes.drawerLayout.openDrawer(Gravity.START);


                return false;
            }
        });


        catalogues_recyclerview=view.findViewById(R.id.catalogue_recyclerview);

        layoutManager = new LinearLayoutManager(getActivity());
        catalogues_recyclerview.setLayoutManager(layoutManager);
        homes.nav_search_layout.setVisibility(View.GONE);

        mlist=new ArrayList<Datum>();

       // getcatalogues();

        return view;
    }
/*

    private void getcatalogues() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        String userid= Paper.book().read("userid");

        service.CATALOGUES_CALL(userid).enqueue(new Callback<ModelCatalogues>() {
            @Override
            public void onResponse(Call<ModelCatalogues> call, Response<ModelCatalogues> response) {

                if(response.body().getStatusCode().equals(200)){

                    datalist=response.body().getData();

                    for(int i=0;i<datalist.size();i++){

                        Datum datum1=new Datum();
                        datum1.setName(response.body().getData().get(i).getName());
                        datum1.setUrl(response.body().getData().get(i).getUrl());
                        datum1.setDate(response.body().getData().get(i).getDate());
                        datum1.setSize(response.body().getData().get(i).getSize());

                        mlist.add(datum1);
                    }

                    adapter_catalogues=new Adapter_Catalogues(getActivity(),mlist);
                    catalogues_recyclerview.setAdapter(adapter_catalogues);
                }

                else

                {
                    FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.catelogue_container,new NoCatelogues());
                    fragmentTransaction.commit();

                }

            }

            @Override
            public void onFailure(Call<ModelCatalogues> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
*/

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity.getClass() == Home.class) {
            homes = (Home) activity;
        }
    }

 }
