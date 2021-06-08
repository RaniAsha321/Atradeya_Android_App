package com.atradeya.asharani;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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


public class Super_Home extends Fragment {

    String cat="3";
    RecyclerView recyclerviewSuperCategories;
    Adapter_Super_Category home_categories;
    List<Product> list;
    List<Product> supersubcategories;
    Context mcontext;
    List<Product> modelProductsCategories;
    int spanCount = 2;
    int spacing = 15;
    boolean includeEdge = true;
    Home homeactivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_super__home, container, false);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    getFragmentManager().popBackStack("m", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });

        recyclerviewSuperCategories = view.findViewById(R.id.recyclerview_super_home);

        ((Home) getActivity()).hideView(false);
        homeactivity.layout_home_filter.setVisibility(View.GONE);
        list = new ArrayList<>();

        modelProductsCategories = Paper.book().read("products", new ArrayList<Product>());

        for (Product category : modelProductsCategories) {

            Product product = new Product();

            product.setProductId(category.getProductId());
            product.setProductName(category.getProductName());
            product.setPrice(category.getPrice());
            product.setImage(category.getImage());

            list.add(product);
        }

        recyclerviewSuperCategories.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        recyclerviewSuperCategories.addItemDecoration(new ItemOffsetDecoration(spanCount, spacing, includeEdge));

        getProduct();

        return view;
    }

    private void getProduct() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        String productid=(Paper.book().read("tempSuperCategory",""));

        service.PRODUCT_DESCRIPTION_CALL(productid,cat).enqueue(new Callback<ModelDescription>() {
            @Override
            public void onResponse(Call<ModelDescription> call, Response<ModelDescription> response) {

                if (response.body().getStatusCode().equals(200)) {

                    supersubcategories = (List<Product>) response.body().getProducts();

                  //  Paper.book().write("stripe_publish_key",response.body().getPublishkey());

                    home_categories = new Adapter_Super_Category(supersubcategories, mcontext,response.body().getPublishkey());

                    recyclerviewSuperCategories.setAdapter(home_categories);
                }

                else {

                    Toast.makeText(mcontext, "No Product Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelDescription> call, Throwable t) {

                Toast.makeText(mcontext, t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

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
