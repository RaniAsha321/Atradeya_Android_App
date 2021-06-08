package com.atradeya.asharani;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Search_items extends Fragment {

    Home home;
    RecyclerView recyclerView;
    Adapter_Searching adapter_searching;
    List<SearchDatum> mysearchlist;
    List<SearchDatum> mylist;
    int spanCount = 2;
    int spacing = 15;
    boolean includeEdge = true;
    String userid;
    Wholeseller wholeseller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_items, container, false);

        recyclerView=view.findViewById(R.id.re_searching);
        wholeseller=new Wholeseller();

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    getFragmentManager().popBackStack("cate", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });

        home.search_txtvw.setCursorVisible(false);
        userid=Paper.book().read("userid");


        String search_selected_products=Paper.book().read("search");
        String search_id=Paper.book().read("search_id");

        if(search_id!=null){

            if(search_selected_products.equals("1")){

                    Searching(search_id);

            }
           else if(search_selected_products.equals("0")){


                if (Paper.book().read("user").equals("0")){

                    Searching("0");
                }
                else {

                    Searching(userid);

                }
            }

        }

        else {


            if (Paper.book().read("user").equals("0")){

                Searching("0");

            }
            else {
                Searching(userid);
            }
        }



        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new ItemOffsetDecoration(spanCount, spacing, includeEdge));

        return view;
    }

    public void  Searching(String id) {

        mylist=new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);
        Paper.book().write("stringkey",home.search_txtvw.getText().toString().trim());

        service.SEARCHING_CALL(home.search_txtvw.getText().toString().trim(),id).enqueue(new Callback<ModelSearching>() {
            @Override
            public void onResponse(Call<ModelSearching> call, Response<ModelSearching> response) {

                if (response.body().getStatusCode().equals(200)) {

                        mysearchlist = response.body().getSearchData();
                       // Paper.book().write("stripe_publish_key",response.body().getPublishkey());
                        adapter_searching = new Adapter_Searching(getActivity(), mysearchlist,response.body().getPublishkey());
                        recyclerView.setAdapter(adapter_searching);
                    }

                    else {

                        FragmentTransaction fragmentTransaction;
                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerr, new Search_empty());
                        fragmentTransaction.commit();

                    }
            }

            @Override
            public void onFailure(Call<ModelSearching> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }

    public void onAttach (Activity activity) {
        super.onAttach(activity);
        if(activity.getClass()== Home.class){
            home = (Home) activity;
        }
    }

}


