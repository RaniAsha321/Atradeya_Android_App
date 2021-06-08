package com.atradeya.asharani;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class Wholeseller extends Fragment {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    RecyclerView recyclerviewwholeseller;
    int spanCount = 2;
    int spacing = 25;
    boolean includeEdge = true;
    List<Wholselear> wholseller_list;
    List<Wholselear> my_list;
    Adapter_wholesellers adapter_wholesellers;


    Home home;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home__categories, container, false);

        recyclerviewwholeseller = view.findViewById(R.id.recyclerview_home_categories);
        ((Home)getActivity()).hideView(false);
        my_list= new ArrayList<>();

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    getFragmentManager().popBackStack("hoome", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }

                home.drawerLayout.openDrawer(Gravity.LEFT);

                return false;

            }

        });

        SharedPreferences sharedPreferences =getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        home.nav_search_layout.setVisibility(View.VISIBLE);
        home.layout_home_filter.setVisibility(View.GONE);
        home.layout_gps_home.setVisibility(View.VISIBLE);
       // getWholesellers("0");
        home.search_txtvw.setHint("Enter City or Postal Code");
        String postcode = Paper.book().read("city");
        String postcity = Paper.book().read("postcity");

        Log.e("postcity1",postcode+"");
        Log.e("postcity2",postcity+"");

        if(postcode!=null) {

            if(postcode.equals("0")){

                getWholesellers(postcode);
            }

            else if(postcity!=null) {

                home.search_txtvw.setText(postcity);
                getWholesellers(postcity);
            }

            else {

                Log.e("postcity3",sharedPreferences.getString("city", null)+"");
                home.search_txtvw.setText(sharedPreferences.getString("city", null));
                getWholesellers(sharedPreferences.getString("city", null));
            }
        }

        else {
            Log.e("postcity4",sharedPreferences.getString("city", null)+"");
            home.search_txtvw.setText(sharedPreferences.getString("city", null));
            getWholesellers(sharedPreferences.getString("city", null));
        }



        Paper.book().write("city","");

        return view;
    }

    public void getWholesellers(String postcode) {

        Log.e("postcode",postcode+"");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        service.WHOLESELLERS_CALL(postcode).enqueue(new Callback<ModelWholesellers>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<ModelWholesellers> call, Response<ModelWholesellers> response) {

                if(response.body().getStatusCode().equals(200)){

                    wholseller_list=response.body().getWholselear();

                    for (int i=0;i<wholseller_list.size();i++) {

                        Wholselear wholseller = new Wholselear();
                        wholseller.setId(wholseller_list.get(i).getId());
                        wholseller.setBusiness(wholseller_list.get(i).getBusiness());
                        wholseller.setBussinessLogo(wholseller_list.get(i).getBussinessLogo());
                        wholseller.setBusinessId(wholseller_list.get(i).getBusinessId());
                        wholseller.setStripePublishkey(wholseller_list.get(i).getStripePublishkey());

                        my_list.add(wholseller);
                    }

                    recyclerviewwholeseller.addItemDecoration(new ItemOffsetDecoration(spanCount, spacing, includeEdge));

                    recyclerviewwholeseller.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));

                    adapter_wholesellers = new Adapter_wholesellers(my_list, getActivity());
                    recyclerviewwholeseller.setAdapter(adapter_wholesellers);

                }

                else {
                    FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerr,new NoWholeseller());
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onFailure(Call<ModelWholesellers> call, Throwable t) {

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
