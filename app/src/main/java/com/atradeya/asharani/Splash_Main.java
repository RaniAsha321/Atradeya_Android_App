package com.atradeya.asharani;

/*
CREATED BY: ASHA RANI
*/


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.paperdb.Paper;

public class Splash_Main extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        /*********************************************** SETTING STATUS BAR WHITE ******************************************************************/

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }

        /********************************************************************************************************************************************/


        Paper.book().write("ApplyCartData", "");
        Paper.book().write("ApplyData", "");


        /************************************************GET SAVED VALUES FROM SHARED PREFERENCE********************************************************************************/

        final SharedPreferences sp1 = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        final String unm = sp1.getString("email", null);
        String pass = sp1.getString("password", null);
        final String unique_id = sp1.getString("unique_id", null);
        final String role = sp1.getString("role", null);
        final String username = sp1.getString("username", null);
        final String Image = sp1.getString("Image", null);
        final String email_id = sp1.getString("email", null);
        final String permission_cat = sp1.getString("permission_cat", null);
        final String permission_orders = sp1.getString("permission_orders", null);
        final String permission_pro_details = sp1.getString("permission_pro_details", null);
        final String permission_catelogues = sp1.getString("permission_catelogues", null);
        final String permission_all_orders = sp1.getString("permission_all_orders", null);
        final String permission_awaiting = sp1.getString("permission_awaiting", null);
        final String permission_see_cost = sp1.getString("permission_see_cost", null);
        final Integer awaiting_approvals = sp1.getInt("awaiting_data", 0);
        final String permission_add_product = sp1.getString("permission_add_product", null);
        final String permission_wholeseller = sp1.getString("permission_wholeseller", null);
        final String Wholeseller_engineer_id = sp1.getString("Wholeseller_engineer_id", null);
        final String business_name = sp1.getString("business_name", null);
        final String postcode = sp1.getString("city", null);
        final String first_name = sp1.getString("first_name", null);
        final String last_name = sp1.getString("last_name", null);

/*
        if (postcode!= null){


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Paper.book().write("user", "0");
                    Paper.book().write("business_id", "0");
                    Paper.book().write("searchwholesalers","1");

                    Intent i = new Intent(Splash_Main.this, Home.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);

        }*/
/*
        if (Paper.book().read("permission_wholeseller", "5").equals("1")) {
            if (!(unm == null) && !(pass == null)) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Splash_Main.this, Home.class);

                        Paper.book().write("user", unique_id);

                        intent.putExtra("username", username);
                        intent.putExtra("unique_id", unique_id);
                        intent.putExtra("first_name", first_name);
                        intent.putExtra("last_name", last_name);
                        intent.putExtra("role", role);
                        intent.putExtra("Image", Image);
                        intent.putExtra("email", email_id);
                        intent.putExtra("permission_cats", permission_cat);
                        intent.putExtra("permission_orderss", permission_orders);
                        intent.putExtra("permission_pro_detailssse", permission_pro_details);
                        intent.putExtra("permission_cateloguess", permission_catelogues);
                        intent.putExtra("permission_all_orders", permission_all_orders);
                        intent.putExtra("permission_awaiting", permission_awaiting);
                        intent.putExtra("permission_see_cost", permission_see_cost);
                        intent.putExtra("awaiting_data", awaiting_approvals);
                        intent.putExtra("permission_add_product", permission_add_product);
                        intent.putExtra("permission_wholeseller", permission_wholeseller);
                        intent.putExtra("Wholeseller_engineer_id", Wholeseller_engineer_id);
                        intent.putExtra("business_name", business_name);


                        startActivity(intent);

                        finish();
                    }
                }, SPLASH_TIME_OUT);

            } else if ((unm == null) && (pass == null)) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(Splash_Main.this, Login.class);
                        startActivity(i);

                        // close this activity
                        finish();
                    }
                }, SPLASH_TIME_OUT);
            }


        } else*/

      /*if (!(unm == null) && !(pass == null) && (postcode == null)) {

            if (Paper.book().read("ViewWholesellerPage", "5").equals("1")) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Splash_Main.this, GetPostcode2.class);

                        **************************************************PASS VALUES TO "Next_Login_Page USING INTENT" **************************************************************
                        intent.putExtra("username", username);
                        intent.putExtra("unique_id", unique_id);
                        intent.putExtra("role", role);
                        intent.putExtra("Image", Image);
                        intent.putExtra("email", email_id);
                        intent.putExtra("permission_cats", permission_cat);
                        intent.putExtra("permission_orderss", permission_orders);
                        intent.putExtra("permission_pro_detailssse", permission_pro_details);
                        intent.putExtra("permission_cateloguess", permission_catelogues);
                        intent.putExtra("permission_all_orders", permission_all_orders);
                        intent.putExtra("permission_awaiting", permission_awaiting);
                        intent.putExtra("permission_see_cost", permission_see_cost);
                        intent.putExtra("awaiting_data", awaiting_approvals);
                        intent.putExtra("permission_add_product", permission_add_product);
                        intent.putExtra("permission_wholeseller", permission_wholeseller);
                        intent.putExtra("Wholeseller_engineer_id", Wholeseller_engineer_id);
                        intent.putExtra("business_name", business_name);

                        *************************************************************************************************************************************************************

                        startActivity(intent);

                        finish();
                    }
                }, SPLASH_TIME_OUT);
            }

           else {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Splash_Main.this, Home.class);

                        Paper.book().write("user", unique_id);

                        **************************************************PASS VALUES TO "Next_Login_Page USING INTENT" **************************************************************
                        intent.putExtra("username", username);
                        intent.putExtra("unique_id", unique_id);
                        intent.putExtra("first_name", first_name);
                        intent.putExtra("last_name", last_name);
                        intent.putExtra("role", role);
                        intent.putExtra("Image", Image);
                        intent.putExtra("email", email_id);
                        intent.putExtra("permission_cats", permission_cat);
                        intent.putExtra("permission_orderss", permission_orders);
                        intent.putExtra("permission_pro_detailssse", permission_pro_details);
                        intent.putExtra("permission_cateloguess", permission_catelogues);
                        intent.putExtra("permission_all_orders", permission_all_orders);
                        intent.putExtra("permission_awaiting", permission_awaiting);
                        intent.putExtra("permission_see_cost", permission_see_cost);
                        intent.putExtra("awaiting_data", awaiting_approvals);
                        intent.putExtra("permission_add_product", permission_add_product);
                        intent.putExtra("permission_wholeseller", permission_wholeseller);
                        intent.putExtra("Wholeseller_engineer_id", Wholeseller_engineer_id);
                        intent.putExtra("business_name", business_name);

                        *************************************************************************************************************************************************************

                        startActivity(intent);

                        finish();
                    }
                }, SPLASH_TIME_OUT);

            }

        }

        else {

                ***************************************************************************************************************************************************************

                if (!(unm == null) && !(pass == null) && !(postcode == null)) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Splash_Main.this, Next_Login_Page.class);

                            **************************************************PASS VALUES TO "Next_Login_Page USING INTENT" **************************************************************
                            intent.putExtra("username", username);
                            intent.putExtra("unique_id", unique_id);
                            intent.putExtra("first_name", first_name);
                            intent.putExtra("last_name", last_name);
                            intent.putExtra("role", role);
                            intent.putExtra("Image", Image);
                            intent.putExtra("email", email_id);
                            intent.putExtra("permission_cats", permission_cat);
                            intent.putExtra("permission_orderss", permission_orders);
                            intent.putExtra("permission_pro_detailssse", permission_pro_details);
                            intent.putExtra("permission_cateloguess", permission_catelogues);
                            intent.putExtra("permission_all_orders", permission_all_orders);
                            intent.putExtra("permission_awaiting", permission_awaiting);
                            intent.putExtra("permission_see_cost", permission_see_cost);
                            intent.putExtra("awaiting_data", awaiting_approvals);
                            intent.putExtra("permission_add_product", permission_add_product);
                            intent.putExtra("permission_wholeseller", permission_wholeseller);
                            intent.putExtra("Wholeseller_engineer_id", Wholeseller_engineer_id);
                            intent.putExtra("business_name", business_name);

                            *************************************************************************************************************************************************************

                            startActivity(intent);

                            finish();
                        }
                    }, SPLASH_TIME_OUT);

                } else if (!(unm == null) && !(pass == null)) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Splash_Main.this, Home.class);

                            Paper.book().write("unique_id", unique_id);
                            Paper.book().write("user", unique_id);

                            **************************************************PASS VALUES TO "Next_Login_Page USING INTENT" **************************************************************
                            intent.putExtra("username", username);
                            intent.putExtra("unique_id", unique_id);
                            intent.putExtra("first_name", first_name);
                            intent.putExtra("last_name", last_name);
                            intent.putExtra("role", role);
                            intent.putExtra("Image", Image);
                            intent.putExtra("email", email_id);
                            intent.putExtra("permission_cats", permission_cat);
                            intent.putExtra("permission_orderss", permission_orders);
                            intent.putExtra("permission_pro_detailssse", permission_pro_details);
                            intent.putExtra("permission_cateloguess", permission_catelogues);
                            intent.putExtra("permission_all_orders", permission_all_orders);
                            intent.putExtra("permission_awaiting", permission_awaiting);
                            intent.putExtra("permission_see_cost", permission_see_cost);
                            intent.putExtra("awaiting_data", awaiting_approvals);
                            intent.putExtra("permission_add_product", permission_add_product);
                            intent.putExtra("permission_wholeseller", permission_wholeseller);
                            intent.putExtra("Wholeseller_engineer_id", Wholeseller_engineer_id);
                            intent.putExtra("business_name", business_name);

                            *************************************************************************************************************************************************************

                            startActivity(intent);

                            finish();
                        }
                    }, SPLASH_TIME_OUT);

                } else if ((unm == null) && (pass == null)) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Paper.book().write("user", "0");
                            Paper.book().write("business_id", "0");

                            Intent i = new Intent(Splash_Main.this, GetPostcode2.class);
                            startActivity(i);

                            // close this activity
                            finish();
                        }
                    }, SPLASH_TIME_OUT);
                }


            }

*/

      //  if (permission_wholeseller.equals("1")) {

        Log.e("unm_11",unm+"");
        Log.e("unm_12",pass+"");
        Log.e("unm_13",postcode+"");

           if ((unm != null) && (pass != null) && (postcode == null)) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Splash_Main.this, GetPostcode2.class);

                        /***************************************************PASS VALUES TO "Next_Login_Page USING INTENT" ***************************************************************/
                        intent.putExtra("username", username);
                        intent.putExtra("unique_id", unique_id);
                        intent.putExtra("role", role);
                        intent.putExtra("Image", Image);
                        intent.putExtra("email", email_id);
                        intent.putExtra("permission_cats", permission_cat);
                        intent.putExtra("permission_orderss", permission_orders);
                        intent.putExtra("permission_pro_detailssse", permission_pro_details);
                        intent.putExtra("permission_cateloguess", permission_catelogues);
                        intent.putExtra("permission_all_orders", permission_all_orders);
                        intent.putExtra("permission_awaiting", permission_awaiting);
                        intent.putExtra("permission_see_cost", permission_see_cost);
                        intent.putExtra("awaiting_data", awaiting_approvals);
                        intent.putExtra("permission_add_product", permission_add_product);
                        intent.putExtra("permission_wholeseller", permission_wholeseller);
                        intent.putExtra("Wholeseller_engineer_id", Wholeseller_engineer_id);
                        intent.putExtra("business_name", business_name);

                        /***************************************************************************************************************************************************************/

                        startActivity(intent);

                        finish();
                    }
                }, SPLASH_TIME_OUT);
            }

          else if (!(unm == null) && !(pass == null) && !(postcode == null)) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Splash_Main.this, Next_Login_Page.class);

                        intent.putExtra("username", username);
                        intent.putExtra("unique_id", unique_id);
                        intent.putExtra("first_name", first_name);
                        intent.putExtra("last_name", last_name);
                        intent.putExtra("role", role);
                        intent.putExtra("Image", Image);
                        intent.putExtra("email", email_id);
                        intent.putExtra("permission_cats", permission_cat);
                        intent.putExtra("permission_orderss", permission_orders);
                        intent.putExtra("permission_pro_detailssse", permission_pro_details);
                        intent.putExtra("permission_cateloguess", permission_catelogues);
                        intent.putExtra("permission_all_orders", permission_all_orders);
                        intent.putExtra("permission_awaiting", permission_awaiting);
                        intent.putExtra("permission_see_cost", permission_see_cost);
                        intent.putExtra("awaiting_data", awaiting_approvals);
                        intent.putExtra("permission_add_product", permission_add_product);
                        intent.putExtra("permission_wholeseller", permission_wholeseller);
                        intent.putExtra("Wholeseller_engineer_id", Wholeseller_engineer_id);
                        intent.putExtra("business_name", business_name);


                        startActivity(intent);

                        finish();
                    }
                }, SPLASH_TIME_OUT);

            }

           else if ((unm == null) && (pass == null)) {

               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {

                       Paper.book().write("user", "0");
                       Paper.book().write("business_id", "0");

                       Intent i = new Intent(Splash_Main.this, GetPostcode2.class);
                       startActivity(i);

                       // close this activity
                       finish();
                   }
               }, SPLASH_TIME_OUT);
           }



       /* }

        else {

            Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
        }
*/


        }

}

