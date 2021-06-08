package com.atradeya.asharani;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.paperdb.Paper;

public class SignUp_second_screen extends AppCompatActivity {

    EditText edtxt_signup_second_screen_first_name,edtxt_signup_second_screen_last_name,edtxt_signup_second_screen_display_name;
    Button btn_second_screen_next;
    TextView txtvw_signup_second_screen_login,edtxt_signup_second_screen_email_address,edtxt_signup_second_screen_password,textvw_user_type_hello,txtvw_user_type_retail,txtvw_user_type_trade;
    String email,password,account_type;
    LinearLayout layout_user_type_retail,layout_user_type_trade;
    ImageView imgvw_user_type_retail,imgvw_user_type_trade;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_second_screen);

        Paper.book().write("account_type","");

        edtxt_signup_second_screen_first_name  = findViewById(R.id.edtxt_signup_second_screen_first_name);
        edtxt_signup_second_screen_last_name  = findViewById(R.id.edtxt_signup_second_screen_last_name);
        edtxt_signup_second_screen_display_name  = findViewById(R.id.edtxt_signup_second_screen_display_name);
        edtxt_signup_second_screen_email_address  = findViewById(R.id.edtxt_signup_second_screen_email_address);
        edtxt_signup_second_screen_password  = findViewById(R.id.edtxt_signup_second_screen_password);
        btn_second_screen_next  = findViewById(R.id.btn_second_screen_next);
        txtvw_signup_second_screen_login  = findViewById(R.id.txtvw_signup_second_screen_login);
        imgvw_user_type_trade= findViewById(R.id.imgvw_user_type_trade);
        imgvw_user_type_retail= findViewById(R.id.imgvw_user_type_retail);
        layout_user_type_retail= findViewById(R.id.layout_user_type_retail);
        layout_user_type_trade= findViewById(R.id.layout_user_type_trade);
        textvw_user_type_hello= findViewById(R.id.textvw_user_type_hello);
        txtvw_user_type_retail= findViewById(R.id.txtvw_user_type_retail);
        txtvw_user_type_trade= findViewById(R.id.txtvw_user_type_trade);

        account_type= "null";

        layout_user_type_retail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                account_type= "0";

                imgvw_user_type_retail.setBackgroundResource(R.drawable.ic_retail);
                imgvw_user_type_trade.setBackgroundResource(R.drawable.ic_growth_red);
                layout_user_type_retail.setBackgroundResource(R.drawable.shape_rounded_green);
                layout_user_type_trade.setBackgroundResource(R.drawable.shape_rectangle_round);
                textvw_user_type_hello.setText("Hello Retail");
                txtvw_user_type_retail.setTextColor(getResources().getColor(R.color.white));
                txtvw_user_type_trade.setTextColor(getResources().getColor(R.color.themered));


            }
        });

        layout_user_type_trade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Paper.book().write("account_type","trade");

                account_type="1";

                imgvw_user_type_retail.setBackgroundResource(R.drawable.ic_retail_red);
                imgvw_user_type_trade.setBackgroundResource(R.drawable.ic_growth_white);
                layout_user_type_trade.setBackground(getResources().getDrawable(R.drawable.shape_rounded_green));
                layout_user_type_retail.setBackground(getResources().getDrawable(R.drawable.shape_rectangle_round));
                textvw_user_type_hello.setText("Hello Trade");
                txtvw_user_type_retail.setTextColor(getResources().getColor(R.color.themered));
                txtvw_user_type_trade.setTextColor(getResources().getColor(R.color.white));

            }
        });

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){

            email =bundle.getString("email");
            password =bundle.getString("password");

        }

        edtxt_signup_second_screen_email_address.setText(email);
        edtxt_signup_second_screen_password.setText(password);

        txtvw_signup_second_screen_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUp_second_screen.this, Login.class);

                startActivity(intent);

            }
        });

        btn_second_screen_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!account_type.equals("")){

                    if(!TextUtils.isEmpty(edtxt_signup_second_screen_first_name.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                        if(!TextUtils.isEmpty(edtxt_signup_second_screen_last_name.getText().toString())) {     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                            if (!TextUtils.isEmpty(edtxt_signup_second_screen_display_name.getText().toString())) {     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                           /* Intent intent = new Intent(SignUp_second_screen.this, SignUp_Third_Screen.class);

                            intent.putExtra("First_name", edtxt_signup_second_screen_first_name.getText().toString());
                            intent.putExtra("Last_name", edtxt_signup_second_screen_first_name.getText().toString());
                            intent.putExtra("Display_name", edtxt_signup_second_screen_first_name.getText().toString());
                            intent.putExtra("email", email);
                            intent.putExtra("password", password);
                            intent.putExtra("user_type", account_type);


                                startActivity(intent);*/

                        }

                            else{

                                Toast.makeText(getApplicationContext(),"Please Enter Display Name", Toast.LENGTH_SHORT).show();
                            }

                    }
                        else{

                            Toast.makeText(getApplicationContext(),"Please Enter Last Name", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{

                        Toast.makeText(getApplicationContext(),"Please Enter First Name", Toast.LENGTH_SHORT).show();
                    }

                }
                else{

                    Toast.makeText(getApplicationContext(),"Please Enter Select Account Type", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
