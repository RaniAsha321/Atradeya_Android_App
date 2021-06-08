package com.atradeya.asharani;

import android.app.Activity;
import android.os.Bundle;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class New_Catalogues extends Fragment {

    Home homes;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerview_catalogues,recyclerview_promotions;
    Adapter_new_catalogue adapter_catalogues;
    Adapter_new_promotions adapter_new_promotions;
    List<Catalogdatum> datalist;
    List<Catalogdatum> mlist;
    String cataloglist;
    int spanCount = 3;
    int spacing = 25;
    boolean includeEdge = true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.catalogue_promotion, container, false);

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
        mlist=new ArrayList<Catalogdatum>();
        recyclerview_catalogues=view.findViewById(R.id.recyclervw_catalogues_new);
        recyclerview_promotions=view.findViewById(R.id.recyclerview_promotions);

      //  homes.nav_search_layout.setVisibility(View.GONE);

        getCatalogues();

        adapter_catalogues=new Adapter_new_catalogue(getActivity());
        recyclerview_catalogues.setLayoutManager(new LinearLayoutManager(getActivity(),  RecyclerView.HORIZONTAL, false));
        recyclerview_catalogues.setAdapter(adapter_catalogues);


        layoutManager = new LinearLayoutManager(getActivity());
        ((Home) getActivity()).hideView(true);

       /* adapter_new_promotions=new Adapter_new_promotions(getActivity());
        recyclerview_promotions.setAdapter(adapter_new_promotions);
*/

        return view;

    }

    private void getCatalogues() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        String userid= Paper.book().read("userid");

        service.CATALOGUES_CALL(userid).enqueue(new Callback<ModelCatPromo>() {
            @Override
            public void onResponse(Call<ModelCatPromo> call, Response<ModelCatPromo> response) {

                if(response.body().getStatusCode().equals(200)){

                    datalist=response.body().getCatalogdata();

                    for(int i=0;i<datalist.size();i++){

                        Catalogdatum datum1=new Catalogdatum();
                        datum1.setId(response.body().getCatalogdata().get(i).getId());
                        datum1.setSectionName(response.body().getCatalogdata().get(i).getSectionName());
                        datum1.setCoverImage(response.body().getCatalogdata().get(i).getCoverImage());
                        datum1.setCatalog(response.body().getCatalogdata().get(i).getCatalog());

                        mlist.add(datum1);

                        cataloglist = response.body().getCatalogdata().get(i).getCatalog();

                    }

                   /* List<String> myList = new ArrayList<String>(Arrays.asList(s.split(",")));

                    Paper.book().write("CatImageList",myList);
*/
                    adapter_new_promotions=new Adapter_new_promotions(getActivity(),mlist);

                    recyclerview_promotions.addItemDecoration(new ItemOffsetDecoration(spanCount, spacing, includeEdge));

                    recyclerview_promotions.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));

                    recyclerview_promotions.setAdapter(adapter_new_promotions);
                }

                else

                {
                    FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.catelogue_container,new NoCatelogues());
                    fragmentTransaction.commit();

                }


            }

            @Override
            public void onFailure(Call<ModelCatPromo> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });

    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity.getClass() == Home.class) {
            homes = (Home) activity;
        }
    }

}
