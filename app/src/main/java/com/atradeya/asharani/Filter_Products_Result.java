package com.atradeya.asharani;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class Filter_Products_Result extends Fragment {

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home__sub_category, container, false);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    getFragmentManager().popBackStack("filter_list", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }

                Paper.book().write("filter_products","");
                return false;
            }
        });

        homeactivity.layout_home_filter.setVisibility(View.GONE);
        ((Home)getActivity()).hideView(false);
        homeactivity.navsearchlayout.setVisibility(View.VISIBLE);
        recyclerviewHomeCategories = view.findViewById(R.id.recyclerview_home_sub_categories);
        txtvw_filter_home_sub_cat = view.findViewById(R.id.txtvw_filter_home_sub_cat);

        recyclerviewHomeCategories.addItemDecoration(new ItemOffsetDecoration(spanCount, spacing, includeEdge));

        recyclerviewHomeCategories.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));


        list = new ArrayList<>();

        subcategories = (Paper.book().read("subcatlist", new ArrayList<SubCategory>()));

        filter_products=Paper.book().read("filter_products");

            home_super_fiter = new Adapter_Super_Filter(filter_products, getActivity(),"");

            recyclerviewHomeCategories.setAdapter(home_super_fiter);


        homeactivity.txtvw_filter_home_sub_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homeactivity.navsearchlayout.setVisibility(View.GONE);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("filter_sub_cat").replace(R.id.containerr, new Filter_List());
                transaction.commit();

            }
        });

        return view;
    }

    public void onAttach (Activity activity) {
        super.onAttach(activity);
        if(activity.getClass()== Home.class){
            homeactivity = (Home) activity;
        }
    }

}
