package com.atradeya.asharani;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Add_Address extends AppCompatActivity {

    EditText edtxt_address_business_name,edtxt_address_first_name,edtxt_address_last_name,edtxt_address_address_line1,edtxt_address_address_line2,
            edtxt_address_city,edtxt_address_postcode;
    Button btn_address_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saveaddress);

        edtxt_address_business_name = findViewById(R.id.edtxt_address_business_name);
        edtxt_address_first_name = findViewById(R.id.edtxt_address_first_name);
        edtxt_address_last_name = findViewById(R.id.edtxt_address_last_name);
        edtxt_address_address_line1 = findViewById(R.id.edtxt_address_address_line1);
        edtxt_address_address_line2 = findViewById(R.id.edtxt_address_address_line2);
        edtxt_address_city = findViewById(R.id.edtxt_address_city);
        edtxt_address_postcode = findViewById(R.id.edtxt_address_postcode);
        btn_address_submit = findViewById(R.id.btn_address_submit);


        edtxt_address_business_name.setText("");
        edtxt_address_first_name.setText("");
        edtxt_address_last_name.setText("");
        edtxt_address_address_line1.setText("");
        edtxt_address_address_line2.setText("");
        edtxt_address_city.setText("");
        edtxt_address_postcode.setText("");

        if (Paper.book().read("layout_address_edit", "5").equals("1")) {

            btn_address_submit.setText("Update Address");

            edtxt_address_business_name.setText(Paper.book().read("address_company_name"));
            edtxt_address_first_name.setText(Paper.book().read("address_first_name"));
            edtxt_address_last_name.setText(Paper.book().read("address_last_name"));
            edtxt_address_address_line1.setText(Paper.book().read("address_address1"));
            edtxt_address_address_line2.setText(Paper.book().read("address_address2"));
            edtxt_address_city.setText(Paper.book().read("address_city"));
            edtxt_address_postcode.setText(Paper.book().read("address_postcode"));


            btn_address_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!TextUtils.isEmpty(edtxt_address_business_name.getText().toString())) {


                        if (!TextUtils.isEmpty(edtxt_address_first_name.getText().toString())) {


                            if (!TextUtils.isEmpty(edtxt_address_last_name.getText().toString())) {


                                if (!TextUtils.isEmpty(edtxt_address_address_line1.getText().toString())) {


                                    if (!TextUtils.isEmpty(edtxt_address_city.getText().toString())) {


                                        if (!TextUtils.isEmpty(edtxt_address_postcode.getText().toString())) {

                                            String address2;

                                            if (TextUtils.isEmpty(edtxt_address_address_line2.getText().toString())) {

                                                address2 = "0";

                                            } else {

                                                address2 = edtxt_address_address_line2.getText().toString().trim();

                                            }

                                            update(edtxt_address_business_name.getText().toString().trim(), edtxt_address_first_name.getText().toString().trim(), edtxt_address_last_name.getText().toString().trim(),
                                                    edtxt_address_address_line1.getText().toString().trim(), address2, edtxt_address_city.getText().toString().trim(),
                                                    edtxt_address_postcode.getText().toString().trim());

                                        } else {

                                            Toast.makeText(getApplicationContext(), "Please Enter PostCode", Toast.LENGTH_SHORT).show();

                                        }
                                    } else {

                                        Toast.makeText(getApplicationContext(), "Please Enter City", Toast.LENGTH_SHORT).show();

                                    }
                                } else {

                                    Toast.makeText(getApplicationContext(), "Please Enter Address Line1", Toast.LENGTH_SHORT).show();

                                }
                            } else {

                                Toast.makeText(getApplicationContext(), "Please Enter Last Name", Toast.LENGTH_SHORT).show();

                            }
                        } else {

                            Toast.makeText(getApplicationContext(), "Please Enter First Name", Toast.LENGTH_SHORT).show();

                        }

                    } else {

                        Toast.makeText(getApplicationContext(), "Please Enter Business Name", Toast.LENGTH_SHORT).show();

                    }

                }
            });

        } else {

            btn_address_submit.setText("Save Address");
        btn_address_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Paper.book().write("layout_address_edit", "2");

                if (!TextUtils.isEmpty(edtxt_address_business_name.getText().toString())) {


                    if (!TextUtils.isEmpty(edtxt_address_first_name.getText().toString())) {


                        if (!TextUtils.isEmpty(edtxt_address_last_name.getText().toString())) {


                            if (!TextUtils.isEmpty(edtxt_address_address_line1.getText().toString())) {


                                if (!TextUtils.isEmpty(edtxt_address_city.getText().toString())) {


                                    if (!TextUtils.isEmpty(edtxt_address_postcode.getText().toString())) {

                                        String address2;

                                        if (TextUtils.isEmpty(edtxt_address_address_line2.getText().toString())) {

                                            address2 = "0";

                                        } else {

                                            address2 = edtxt_address_address_line2.getText().toString().trim();

                                        }


                                        address(edtxt_address_business_name.getText().toString().trim(), edtxt_address_first_name.getText().toString().trim(), edtxt_address_last_name.getText().toString().trim(),
                                                edtxt_address_address_line1.getText().toString().trim(), address2, edtxt_address_city.getText().toString().trim(),
                                                edtxt_address_postcode.getText().toString().trim());

                                    } else {

                                        Toast.makeText(getApplicationContext(), "Please Enter PostCode", Toast.LENGTH_SHORT).show();

                                    }
                                } else {

                                    Toast.makeText(getApplicationContext(), "Please Enter City", Toast.LENGTH_SHORT).show();

                                }
                            } else {

                                Toast.makeText(getApplicationContext(), "Please Enter Address Line1", Toast.LENGTH_SHORT).show();

                            }
                        } else {

                            Toast.makeText(getApplicationContext(), "Please Enter Last Name", Toast.LENGTH_SHORT).show();

                        }
                    } else {

                        Toast.makeText(getApplicationContext(), "Please Enter First Name", Toast.LENGTH_SHORT).show();

                    }

                } else {

                    Toast.makeText(getApplicationContext(), "Please Enter Business Name", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    }

    private void update(String business_name, String first_name, String last_name, String address1, String address2, String city, String postcode) {

        String id = Paper.book().read("address_id");

        Log.e("datapradeep1",id+"");
        Log.e("datapradeep2",business_name+"");
        Log.e("datapradeep3",first_name+"");
        Log.e("datapradeep4",last_name+"");
        Log.e("datapradeep5",address1+"");
        Log.e("datapradeep6",address2+"");
        Log.e("datapradeep7",city+"");
        Log.e("datapradeep8",postcode+"");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        service.UPDATE_CALL(id,business_name,first_name,last_name,address1,address2,city,postcode).enqueue(new Callback<ModelAddress>() {
            @Override
            public void onResponse(Call<ModelAddress> call, Response<ModelAddress> response) {

                if(response.body().getStatusCode().equals(200)){


                    edtxt_address_business_name.setText("");
                    edtxt_address_first_name.setText("");
                    edtxt_address_last_name.setText("");
                    edtxt_address_address_line1.setText("");
                    edtxt_address_address_line2.setText("");
                    edtxt_address_city.setText("");
                    edtxt_address_postcode.setText("");

                    Toast.makeText(getApplicationContext(),"Address Updated Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Add_Address.this,Home.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Error Occured", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ModelAddress> call, Throwable t) {


                Toast.makeText(Add_Address.this,t.getMessage() , Toast.LENGTH_SHORT).show();
                t.printStackTrace();


            }
        });



    }

    private void address(String business_name, String first_name, String last_name, String address1, String address2, String city, String postcode) {

        String Wholeseller_engineer_id = Paper.book().read("Wholeseller_engineer_id");


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);


        service.ADDRESS_CALL(Wholeseller_engineer_id,business_name,first_name,last_name,address1,address2,city,postcode).enqueue(new Callback<ModelAddress>() {
            @Override
            public void onResponse(Call<ModelAddress> call, Response<ModelAddress> response) {

                if (response.body().getStatusCode().equals(200)){

                    Toast.makeText(Add_Address.this,response.body().getMessage()
                            , Toast.LENGTH_SHORT).show();

                    Paper.book().write("layout_address_edit","1");

                    Intent intent=new Intent(Add_Address.this,Home.class);
                    startActivity(intent);

                }

                else {

                    Toast.makeText(Add_Address.this,"failed"
                            , Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<ModelAddress> call, Throwable t) {

                Toast.makeText(Add_Address.this,t.getMessage() , Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });

    }

    @Override
    public void onBackPressed()
    {

        Paper.book().write("layout_project_edit", "0");
        Paper.book().write("finalString", "");
        Paper.book().write("address_company_name","");
        Paper.book().write("address_first_name","");
        Paper.book().write("address_last_name","");
        Paper.book().write("address_address1","");
        Paper.book().write("address_address2","");
        Paper.book().write("address_city","");
        Paper.book().write("address_postcode","");


        btn_address_submit.setText("Save Address");
        edtxt_address_business_name.setText("");
        edtxt_address_first_name.setText("");
        edtxt_address_last_name.setText("");
        edtxt_address_address_line1.setText("");
        edtxt_address_address_line2.setText("");
        edtxt_address_city.setText("");
        edtxt_address_postcode.setText("");

        // code here to show dialog

        super.onBackPressed();
    }
}
