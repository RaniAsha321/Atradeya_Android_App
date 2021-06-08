package com.atradeya.asharani;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


public class Search extends AppCompatActivity {


    FragmentTransaction fragmentTransaction;
    String searchkey;
    ImageView img_back;
    EditText edtxt_search;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching);

        edtxt_search=findViewById(R.id.Edtxt_Searching);
        img_back=findViewById(R.id.search_back);
        searchkey=edtxt_search.getText().toString();

        fragmentTransaction=getSupportFragmentManager().beginTransaction().replace(R.id.search_container, new Search_items());
        fragmentTransaction.commit();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(Search.this,Home.class);
                startActivity(intent);

            }
        });



    }


}

