package com.atradeya.asharani;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atradeya.asharani.Filter_POJO.ModelProductsFilter;
import com.atradeya.asharani.Filter_POJO.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Filter_List extends Fragment {

    List<Object> filter_data;
    String item,final_item,str1,str2;
    List<String> mytype,my_type_full;
    TextView txtvw_home_products_size;
    RecyclerView recyclerview_filter_type,recyclerview_filter_value;
    Adapter_Filter_Type adapter_filter_type;
    Adapter_Filter_Value adapter_filter_value;
    LinearLayoutManager layoutManager,layoutManager2;
    ArrayList<String> filter_value;
    Button btn_apply_filter;
    List<com.atradeya.asharani.Filter_POJO.Product> productlist;
    Home_SubCategory home_subCategory;
    FragmentTransaction transaction;
    String data;
    LinearLayout layout_filter_back;
    ExpandableListView expandableListView;
    Home homeactivity;

    // Create ExpandableListAdapter
    ExpandableListAdapter expandableListAdapter;

    List<String> parents; // A list of parents (strings)
    Map<String, List<String>> childrenMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_list_new,container,false);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    getFragmentManager().popBackStack("filter_sub_cat", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }

        });

        homeactivity.layout_home_filter.setVisibility(View.VISIBLE);
        filter_data = new ArrayList<>();
        mytype = new ArrayList<>();
        my_type_full= new ArrayList<>();
        home_subCategory = new Home_SubCategory();

        expandableListView = (ExpandableListView) view.findViewById(R.id.list_view_expandable);
        btn_apply_filter = view.findViewById(R.id.btn_apply_filter);

        filter_data =Paper.book().read("filter_data");

        childrenMap = new HashMap<>(); // HashMap to map keys to values

        for (int i=0;i<filter_data.size();i++){

            item= String.valueOf(filter_data.get(i));

            item = item.replaceAll("[\\(\\)\\[\\]\\{\\}]","");

            String[] str = item.split("=");

            str1 = str[0];
            str2 = str[1];

            String[] arr = str2.split(",");

            ArrayList<String> arr1 = new ArrayList<String>();

            for (int j=0; j<arr.length; j++){

                arr1.add(arr[j]);
            }

            mytype.add(str1);
            my_type_full.add(String.valueOf(filter_data.get(i)));

            childrenMap.put(mytype.get(i),arr1);

        }

        expandableListAdapter = new MyExpandableListAdapter(getActivity(), mytype,childrenMap);

        // Set the value in the ExpandableListView
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Toast.makeText(getActivity(), mytype.get(groupPosition) + " : " + childrenMap.get(parents.get(groupPosition)).get(childPosition), Toast.LENGTH_LONG).show();
                return false;
            }
        });

        btn_apply_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selected_filter_data = Paper.book().read("ApplyCartData");

                if (selected_filter_data != null && !selected_filter_data.isEmpty()){

                    applyFilter(selected_filter_data);

                }
                else {

                    Toast.makeText(getActivity(),"Please Select Filters",Toast.LENGTH_SHORT).show();

                }

            }
        });


        return view;
    }

    private void applyFilter(String selected_filter_data) {

        String catId = Paper.book().read("tempcategoryid", "");

        data =  selected_filter_data;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        service.PRODUCTS_FILTER_CALL(catId,data).enqueue(new Callback<ModelProductsFilter>() {
            @Override
            public void onResponse(Call<ModelProductsFilter> call, Response<ModelProductsFilter> response) {

                Paper.book().write("ApplyCartData","");
                Paper.book().write("ApplyData","");

                if (response.body().getStatusCode().equals(200)) {

                    productlist = response.body().getProducts();

                    Paper.book().write("home_products_size",String.valueOf(productlist.size()));

                    Paper.book().write("filter_products",productlist);

                    transaction = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("filter_list").replace(R.id.containerr, new Filter_Products_Result());
                    transaction.commit();

                } else {

                    Toast.makeText(getActivity(), "No Product Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelProductsFilter> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });


    }


    public void getFilterValueAdapter(String data,String type) {

        filter_value = new ArrayList<>(Arrays.asList(data.split(",")));
        adapter_filter_value = new Adapter_Filter_Value(getActivity(), filter_value,type);

        recyclerview_filter_value.setAdapter(adapter_filter_value);

    }

    public void onAttach (Activity activity) {
        super.onAttach(activity);
        if(activity.getClass()== Home.class){
            homeactivity = (Home) activity;
        }
    }
}
