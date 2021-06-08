package com.atradeya.asharani;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class Catalogue_Promo_Images extends Fragment {

    Home homes;
    RecyclerView recyclerView_cat_promo;
    Adapter_Cat_Promo adapter_catalogues;
    List<String> my_list;
    ImageView img_main_cat_promo;
    Catalogue_Promo_Images catalogue_promo_images;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.catalogue_promo_image_screen, container, false);

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

        recyclerView_cat_promo = view.findViewById(R.id.recyclervw_cat_promo_img);
        img_main_cat_promo = view.findViewById(R.id.img_main_cat_promo);

       // homes.nav_search_layout.setVisibility(View.GONE);

        my_list = (Paper.book().read("CatImageList", new ArrayList<String>()));


        Log.e("pradeep",my_list.get(0)+"");

        Glide.with(getActivity()).load(my_list.get(0)).into(img_main_cat_promo);

        adapter_catalogues=new Adapter_Cat_Promo(getActivity(),my_list,Catalogue_Promo_Images.this,img_main_cat_promo);
        recyclerView_cat_promo.setLayoutManager(new LinearLayoutManager(getActivity(),  RecyclerView.HORIZONTAL, false));
        recyclerView_cat_promo.setAdapter(adapter_catalogues);
        ((Home) getActivity()).hideView(true);

        return view;
    }


    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity.getClass() == Home.class) {
            homes = (Home) activity;
        }
    }

}
