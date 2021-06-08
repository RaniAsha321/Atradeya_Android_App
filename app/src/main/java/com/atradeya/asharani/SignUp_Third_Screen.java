package com.atradeya.asharani;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignUp_Third_Screen extends AppCompatActivity {

    EditText edtxt_signup_third_screen_account_number,edtxt_signup_third_screen_company_name,edtxt_signup_third_screen_billing_address,
            edtxt_signup_third_screen_billing_address1,edtxt_signup_third_screen_billing_city, edtxt_signup_third_screen_billing_postcode,
            edtxt_signup_third_screen_business_name;
    String first_name,last_name,Display_name,email,password,user_type;
    Button btn_signup_third_previous,btn_signup_third_next;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_third_screen);

        edtxt_signup_third_screen_account_number = findViewById(R.id.edtxt_signup_third_screen_account_number);
        edtxt_signup_third_screen_company_name = findViewById(R.id.edtxt_signup_third_screen_company_name);
        edtxt_signup_third_screen_billing_address = findViewById(R.id.edtxt_signup_third_screen_billing_address);
        edtxt_signup_third_screen_business_name = findViewById(R.id.edtxt_signup_third_screen_business_name);
        edtxt_signup_third_screen_billing_address1 = findViewById(R.id.edtxt_signup_third_screen_billing_address1);
        edtxt_signup_third_screen_billing_city = findViewById(R.id.edtxt_signup_third_screen_billing_city);
        edtxt_signup_third_screen_billing_postcode = findViewById(R.id.edtxt_signup_third_screen_billing_postcode);
        btn_signup_third_previous = findViewById(R.id.btn_signup_third_previous);
        btn_signup_third_next = findViewById(R.id.btn_signup_third_next);


        Bundle bundle = getIntent().getExtras();

        if(bundle != null){

            email =bundle.getString("email");
            password =bundle.getString("password");
            first_name =bundle.getString("First_name");
            last_name =bundle.getString("Last_name");
            Display_name =bundle.getString("Display_name");
            user_type = bundle.getString("user_type");
        }

        btn_signup_third_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUp_Third_Screen.this, SignUp_second_screen.class);

                startActivity(intent);

            }
        });

        btn_signup_third_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!TextUtils.isEmpty(edtxt_signup_third_screen_business_name.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                    if(!TextUtils.isEmpty(edtxt_signup_third_screen_company_name.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                        if(!TextUtils.isEmpty(edtxt_signup_third_screen_billing_address.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                            if(!TextUtils.isEmpty(edtxt_signup_third_screen_billing_address1.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                                if(!TextUtils.isEmpty(edtxt_signup_third_screen_billing_city.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                                    if(!TextUtils.isEmpty(edtxt_signup_third_screen_billing_postcode.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                                       /* Intent intent = new Intent(SignUp_Third_Screen.this, SignUp_Fourth_Screen.class);

                                        intent.putExtra("Business_name",edtxt_signup_third_screen_business_name.getText().toString());
                                        intent.putExtra("Company_name",edtxt_signup_third_screen_company_name.getText().toString());
                                        intent.putExtra("Billing_Address",edtxt_signup_third_screen_billing_address.getText().toString());
                                        intent.putExtra("Address",edtxt_signup_third_screen_billing_address1.getText().toString());
                                        intent.putExtra("City",edtxt_signup_third_screen_billing_city.getText().toString());
                                        intent.putExtra("PostCode",edtxt_signup_third_screen_billing_postcode.getText().toString());
                                        intent.putExtra("Account_number",edtxt_signup_third_screen_account_number.getText().toString());
                                        intent.putExtra("First_name",first_name);
                                        intent.putExtra("Last_name",last_name);
                                        intent.putExtra("Display_name",Display_name);
                                        intent.putExtra("email",email);
                                        intent.putExtra("password",password);
                                        intent.putExtra("user_type", user_type);

                                        startActivity(intent);*/

                                    }
                                    else{

                                        Toast.makeText(getApplicationContext(),"Please Enter postcode", Toast.LENGTH_SHORT).show();
                                    }

                                }
                                else{

                                    Toast.makeText(getApplicationContext(),"Please Enter City", Toast.LENGTH_SHORT).show();
                                }

                            }
                            else{

                                Toast.makeText(getApplicationContext(),"Please Enter Address", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else{

                            Toast.makeText(getApplicationContext(),"Please Enter Billing Address", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{

                        Toast.makeText(getApplicationContext(),"Please Enter Company Name", Toast.LENGTH_SHORT).show();
                    }

                }
                else{

                    Toast.makeText(getApplicationContext(),"Please Enter Business Name", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
