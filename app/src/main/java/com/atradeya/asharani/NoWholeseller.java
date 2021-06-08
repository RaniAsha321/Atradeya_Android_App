package com.atradeya.asharani;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;


public class NoWholeseller extends Fragment {

    Button btn_search_all_wholesellers;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    RecyclerView recyclerviewwholeseller;
    int spanCount = 2;
    int spacing = 25;
    boolean includeEdge = true;
    List<Wholselear> wholseller_list;
    List<Wholselear> my_list;
    Adapter_wholesellers adapter_wholesellers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_no_postcode, container, false);

        recyclerviewwholeseller = view.findViewById(R.id.recyclerview_home_categories);

        btn_search_all_wholesellers=view.findViewById(R.id.btn_search_all_wholesellers);
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

                return false;

            }

        });


        btn_search_all_wholesellers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Paper.book().write("city","0");
                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerr,new Wholeseller());
                fragmentTransaction.commit();


            }
        });



       return view;
    }



}
