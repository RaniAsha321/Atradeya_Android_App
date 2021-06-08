package com.atradeya.asharani;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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

public class Home_Sub_Sub_Category extends Fragment {

    String cat="2";
    Adapter_Super_Category home_super_category;
    List<Product> productlist;
    Context mcontext;
    RecyclerView recyclerviewHomeCategories;
    Adapter_Home_Sub_Sub_Category home_categories;
    List<Model_Home_Super_sub_Category> list;
    int spanCount = 2;
    int spacing = 25;
    boolean includeEdge = true;
    List<SuperSubCategory> supersubcategories;
    TextView txtvw_filter_home_sub_cat;
    Home homeactivity;
    List<com.atradeya.asharani.Filter_POJO.Product> filter_products;
    LinearLayout layout_home_filter;
    List<Category> filter_data_val;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home__sub__sub__category, container, false);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    getFragmentManager().popBackStack("l", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });

        recyclerviewHomeCategories=view.findViewById(R.id.recyclerview_home_sub_sub_categories);
        txtvw_filter_home_sub_cat = view.findViewById(R.id.txtvw_filter_home_sub_cat);
        layout_home_filter = view.findViewById(R.id.layout_home_filter);
        homeactivity.layout_home_filter.setVisibility(View.GONE);

        ((Home)getActivity()).hideView(false);

        list=new ArrayList<>();

        supersubcategories = Paper.book().read("superlist", new ArrayList<SuperSubCategory>());

        for (SuperSubCategory category : supersubcategories) {

            Model_Home_Super_sub_Category modelHomeSuperSubCategories = new Model_Home_Super_sub_Category();
            modelHomeSuperSubCategories.setSubcat(category.getSuperSubCatId());
            modelHomeSuperSubCategories.setSupertext(category.getSuperSubCatName());
            modelHomeSuperSubCategories.setSuperImage(category.getImage());
            list.add(modelHomeSuperSubCategories);
        }

        recyclerviewHomeCategories.setLayoutManager(new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false));
        recyclerviewHomeCategories.addItemDecoration(new ItemOffsetDecoration(spanCount, spacing, includeEdge));

        getProductSuperCat();


/*



        filter_data_val =Paper.book().read("filter_data");

        if (filter_data_val.isEmpty()){
            layout_home_filter.setVisibility(View.GONE);

        }
        else {


            String supercatid = Paper.book().read("tempsubcategoryid", "");

            Paper.book().write("tempcategoryid",supercatid);
            layout_home_filter.setVisibility(View.VISIBLE);

        }

        getProductSuperCat();


        txtvw_filter_home_sub_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homeactivity.navsearchlayout.setVisibility(View.GONE);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("filter_sub_cat").replace(R.id.containerr, new Filter_List());
                transaction.commit();

            }
        });
*/

        return view;
    }

    private void getProductSuperCat() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        String supercatid = Paper.book().read("tempsubcategoryid", "");
        String String_status = Paper.book().read("substatus", "");

        int status = Integer.parseInt(String_status);

        if (status != 0) {
            service.PRODUCTS_SUB_SUB_CATEGORY_CALL(supercatid).enqueue(new Callback<ModelProductsSubSubCategory>() {
                @Override
                public void onResponse(Call<ModelProductsSubSubCategory> call, Response<ModelProductsSubSubCategory> response) {

                    if (response.body().getStatusCode().equals(200)) {

                        supersubcategories = (List<SuperSubCategory>) response.body().getSuperSubCategory();

                        home_categories = new Adapter_Home_Sub_Sub_Category(supersubcategories, mcontext);

                        recyclerviewHomeCategories.setAdapter(home_categories);
                    } else {

                        Toast.makeText(mcontext, "No Product Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelProductsSubSubCategory> call, Throwable t) {

                    Toast.makeText(mcontext, "No Product Found", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        }

        else {

            service.PRODUCT_DESCRIPTION_CALL(supercatid,cat).enqueue(new Callback<ModelDescription>() {
                @Override
                public void onResponse(Call<ModelDescription> call, Response<ModelDescription> response) {
                    if (response.body().getStatusCode().equals(200)) {

                        productlist = (List<Product>) response.body().getProducts();

                        home_super_category = new Adapter_Super_Category(productlist, mcontext,response.body().getPublishkey());

                        recyclerviewHomeCategories.setAdapter(home_super_category);

                    } else {

                        Toast.makeText(mcontext, "No Product Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelDescription> call, Throwable t) {

                    Toast.makeText(getActivity(), t.getMessage()
                            , Toast.LENGTH_SHORT).show();
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
