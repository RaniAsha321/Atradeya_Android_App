package com.atradeya.asharani;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Address_Already_Added extends AppCompatActivity {

    public FragmentTransaction fragmentTransaction;
    TextView txtvw_address_business_name,txtvw_address_first_name,txtvw_address_last_name,txtvw_address_address_line1,txtvw_address_address_line2,
            txtvw_address_city,txtvw_address_postcode;

    Button btn_edit,btn_delete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address);

        txtvw_address_business_name = findViewById(R.id.txtvw_address_business_name);
        txtvw_address_first_name = findViewById(R.id.txtvw_address_first_name);
        txtvw_address_last_name = findViewById(R.id.txtvw_address_last_name);
        txtvw_address_address_line1 = findViewById(R.id.txtvw_address_address_line1);
        txtvw_address_address_line2 = findViewById(R.id.txtvw_address_address_line2);
        txtvw_address_city = findViewById(R.id.txtvw_address_city);
        txtvw_address_postcode = findViewById(R.id.txtvw_address_postcode);
        btn_edit= findViewById(R.id.btn_edit_view_address);
        btn_delete= findViewById(R.id.btn_delete_view_address);

        txtvw_address_business_name.setText(Paper.book().read("address_company_name"));
        txtvw_address_first_name.setText(Paper.book().read("address_first_name"));
        txtvw_address_last_name.setText(Paper.book().read("address_last_name"));
        txtvw_address_address_line1.setText(Paper.book().read("address_address1"));
        txtvw_address_address_line2.setText(Paper.book().read("address_address2"));
        txtvw_address_city.setText(Paper.book().read("address_city"));
        txtvw_address_postcode.setText(Paper.book().read("address_postcode"));

        String address_id= Paper.book().read("address_id");

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(getApplicationContext(),Add_Address.class);
                startActivity(intent);
                Paper.book().write("layout_address_edit","1");
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeAddress(address_id);

            }
        });

    }

    private void removeAddress(String id) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        service.Remove_Address(id).enqueue(new Callback<ModelRemoveProject>() {
            @Override
            public void onResponse(Call<ModelRemoveProject> call, Response<ModelRemoveProject> response) {

                if(response.body().getStatusCode().equals(200)){

                    Toast.makeText(getApplicationContext(),"Address Removed Successfully", Toast.LENGTH_SHORT).show();
                    Paper.book().write("remove_address","1");
                    Intent intent= new Intent(getApplicationContext(),Home.class);
                    startActivity(intent);



                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Error Occured", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ModelRemoveProject> call, Throwable t) {

                t.printStackTrace();
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
}
