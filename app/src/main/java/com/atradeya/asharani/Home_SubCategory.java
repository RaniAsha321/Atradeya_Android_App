package com.atradeya.asharani;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class Home_SubCategory extends Fragment {

    String cat="1";
    Context mcontext;
    List<SubCategory> subcategories;
    RecyclerView recyclerviewHomeCategories;
    Adapter_Home_Sub_Category home_categories;
    Adapter_Super_Category home_super_category;
    Adapter_Super_Filter home_super_fiter;
    List<Model_Home_Sub_Category> list;
    List<Product> productlist;
    int spanCount = 2;
    int spacing = 25;
    boolean includeEdge = true;
    TextView txtvw_filter_home_sub_cat;
    Home homeactivity;
    List<com.atradeya.asharani.Filter_POJO.Product> filter_products;
    LinearLayout layout_home_filter;
    List<Category> filter_data_val;
    List<Object> filter_data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home__sub_category, container, false);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    getFragmentManager().popBackStack("home_category", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });

        homeactivity.layout_home_filter.setVisibility(View.VISIBLE);

        ((Home)getActivity()).hideView(false);
        homeactivity.navsearchlayout.setVisibility(View.VISIBLE);
        recyclerviewHomeCategories = view.findViewById(R.id.recyclerview_home_sub_categories);
        txtvw_filter_home_sub_cat = view.findViewById(R.id.txtvw_filter_home_sub_cat);
        layout_home_filter = view.findViewById(R.id.layout_home_filter);

        recyclerviewHomeCategories.addItemDecoration(new ItemOffsetDecoration(spanCount, spacing, includeEdge));

        recyclerviewHomeCategories.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));


        list = new ArrayList<>();

        subcategories = (Paper.book().read("subcatlist", new ArrayList<SubCategory>()));


        getProductssubCat();

        for (SubCategory subcategory1 : subcategories) {

            Model_Home_Sub_Category modelHomeCategories = new Model_Home_Sub_Category();
            modelHomeCategories.setSubcat(subcategory1.getSubCatId());
            modelHomeCategories.setSuperSubImage(subcategory1.getCatImage());
            modelHomeCategories.setSuperSubtext(subcategory1.getSubCatName());

            list.add(modelHomeCategories);
        }
/*

        filter_data_val =Paper.book().read("filter_data");

        if (filter_data_val.isEmpty()){
            homeactivity.layout_home_filter.setVisibility(View.GONE);
        }
        else {

            homeactivity.layout_home_filter.setVisibility(View.VISIBLE);

        }

*/

       filter_data =Paper.book().read("filter_data");

        homeactivity.txtvw_filter_home_sub_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // homeactivity.navsearchlayout.setVisibility(View.GONE);

                if (filter_data.size() != 0){

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("filter_sub_cat").replace(R.id.containerr, new Filter_List());
                    transaction.commit();

                }

                else {

                    Toast.makeText(getActivity(),"No filter found",Toast.LENGTH_SHORT).show();
                }


            }
        });
        return view;
    }

    public void getProductFilter(List<com.atradeya.asharani.Filter_POJO.Product> productlist2, RecyclerView recyclerviewHomeCategories){


        Paper.book().write("home_products_size",String.valueOf(productlist2.size()));


        Log.e("home_products_size", productlist2.size()+"");
        recyclerviewHomeCategories.addItemDecoration(new ItemOffsetDecoration(spanCount, spacing, includeEdge));

        recyclerviewHomeCategories.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));


        home_super_fiter = new Adapter_Super_Filter(productlist2, mcontext,"");

        recyclerviewHomeCategories.setAdapter(home_super_fiter);

    }

    private void getProductssubCat() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        String demo = Paper.book().read("tempcategoryid", "");
        String String_status = Paper.book().read("homestatus", "");

        int status= Integer.parseInt(String_status);
        if (status != 0) {

            service.PRODUCTS_SUB_CATEGORY_CALL(demo).enqueue(new Callback<ModelProductsSubCategory>() {

                @Override
                public void onResponse(Call<ModelProductsSubCategory> call, Response<ModelProductsSubCategory> response) {

                    if (response.body().getStatusCode().equals(200)) {

                        subcategories = (List<SubCategory>) response.body().getSubCategory();


                        home_categories = new Adapter_Home_Sub_Category(subcategories, mcontext);

                        recyclerviewHomeCategories.setAdapter(home_categories);

                    } else {

                        Toast.makeText(mcontext, "No Product Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelProductsSubCategory> call, Throwable t) {

                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        }

        else {

            service.PRODUCT_DESCRIPTION_CALL(demo,cat).enqueue(new Callback<ModelDescription>() {
                @Override
                public void onResponse(Call<ModelDescription> call, Response<ModelDescription> response) {

                    if (response.body().getStatusCode().equals(200)) {

                        productlist = (List<Product>) response.body().getProducts();

                        Paper.book().write("home_products_size",String.valueOf(productlist.size()));


                        Log.e("home_products_size",productlist.size()+"");


                        home_super_category = new Adapter_Super_Category(productlist, mcontext,response.body().getPublishkey());

                        recyclerviewHomeCategories.setAdapter(home_super_category);

                    } else {

                        Toast.makeText(mcontext, "No Product Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelDescription> call, Throwable t) {

                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        }
    }
    @Override
    public void onAttach(Context c) {
        super.onAttach(c);
        mcontext=c;
    }

    public void onAttach (Activity activity) {
        super.onAttach(activity);
        if(activity.getClass()== Home.class){
            homeactivity = (Home) activity;
        }
    }

}
