package com.atradeya.asharani;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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


public class Home_Categories extends Fragment {

    Home home;
    int i;
    Intent intent;
    List<Model_Home_Categories> mlist;
    ArrayList<Category> categories;
    Context context;
    RecyclerView recyclerviewHomeCategories;
    Adapter_Home_Categories home_categories;
    int spanCount = 2;
    int spacing = 25;
    boolean includeEdge = true;
    Search_items search_items;
    List<Category> intentmodelProductsCategories;
    List<Category> modelProductsCategories;

    List<Object> filterList;
    List<Object> myfilter;

    String user_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home__categories, container, false);
        search_items=new Search_items();

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    getFragmentManager().popBackStack("hoome", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }

                home.drawerLayout.openDrawer(Gravity.START);

                return false;

            }
        });

        home.layout_home_filter.setVisibility(View.GONE);
        home.nav_search_layout.setVisibility(View.VISIBLE);

        recyclerviewHomeCategories = view.findViewById(R.id.recyclerview_home_categories);

        mlist = new ArrayList<>();
        myfilter = new ArrayList<>();

        ((Home)getActivity()).hideView(false);

        categories = Paper.book().read("categorylist", new ArrayList<Category>());

        getProducts();

        for (Category category : categories) {

            Model_Home_Categories modelHomeCategories = new Model_Home_Categories();
            modelHomeCategories.setCat_id(category.getCatId());
            modelHomeCategories.setProduct_img(category.getCatImage());
            modelHomeCategories.setProduct_text(category.getCatName());
            modelHomeCategories.setStatus(category.getStatus());
            modelHomeCategories.setMyfilterlist(category.getFillter());

            mlist.add(modelHomeCategories);
        }

        Paper.book().write("home_key",mlist);

        return view;
    }


    private void getProducts() {

        Log.e("darepra1asa","1");


        /********************************************************  NEXT LOGIN API USING RETROFIT ******************************************************************/

        String wholesaler_id= Paper.book().read("wholesaler_id");

        Log.e("darepra2aa",wholesaler_id+"");



        if (Paper.book().read("user").equals("0")){

            user_id= Paper.book().read("user");
            Log.e("darepra1",user_id+"");

        }

        else  if (!wholesaler_id.equals("0")){

            user_id= wholesaler_id;
            Log.e("darepra2",user_id+"");

        }

        else {

            user_id=Paper.book().read("userid");
            Log.e("darepra3",user_id+"");

        }



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        service.Products(user_id).enqueue(new Callback<ModelProductsCategory>() {
            @Override
            public void onResponse(Call<ModelProductsCategory> call, Response<ModelProductsCategory> response) {

                modelProductsCategories = response.body().getCategory();
             /*   filterList = response.body().getCategory().get(i).getFillter();*/

                if (response.body().getStatusCode().equals(200)) {

                    Paper.book().write("awaiting_data",response.body().getNumberofawating());

                    intentmodelProductsCategories = new ArrayList<>();

                    for (int i = 0; i < modelProductsCategories.size(); i++) {

                        Category category = new Category();
                        category.setCatId(response.body().getCategory().get(i).getCatId());
                        category.setCatImage(response.body().getCategory().get(i).getCatImage());
                        category.setCatName(response.body().getCategory().get(i).getCatName());
                        category.setStatus(response.body().getCategory().get(i).getStatus());
                        category.setFillter(response.body().getCategory().get(i).getFillter());

                        intentmodelProductsCategories.add(category);
                    }

                  /*  for (int i=0; i<filterList.size(); i++){

                        Log.e("filter",response.body().getCategory().get(i).getFillter().get(i).toString());

                    }*/

                    home_categories = new Adapter_Home_Categories(intentmodelProductsCategories, getActivity());

                    recyclerviewHomeCategories.addItemDecoration(new ItemOffsetDecoration(spanCount, spacing, includeEdge));

                    recyclerviewHomeCategories.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));

                    recyclerviewHomeCategories.setAdapter(home_categories);

                  /*  Paper.book().write("categorylist", intentmodelProductsCategories);*/


                }

                else {

                    Toast.makeText(getActivity(), "No Products Found", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ModelProductsCategory> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });

        /************************************************************************************************************************************************/

    }


    public void setKeyListenerOnView(View v){

        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction()!= KeyEvent.ACTION_DOWN)
                    return true;

                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK :
                }

                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setKeyListenerOnView(getView());
    }

    public void onAttach (Activity activity) {
        super.onAttach(activity);
        if(activity.getClass()== Home.class){
            home = (Home) activity;
        }
    }
}

