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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import io.paperdb.Paper;

public class AccountDetails extends Fragment {

    ImageView profile_pic;
    TextView username,email,change_password,txt_busniess_name,txt_billing_details,txt_default_delivery_address,txt_add_card;
    Home homeobj;
    LinearLayout layout_account_Business_name,layout_account_Default_Delivery_address,layout_account_billing_Details,
            layout_account_inner_billing_details,layout_account_inner_delivery_details,layout_Add_Card,layout_account_add_card;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.account_details, container, false);

        /************************************************************* Fragment Back handler  ***********************************************/

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    return true;
                }

                homeobj.drawerLayout.openDrawer(Gravity.LEFT);

                return false;
            }
        });

        /*************************************************************************************************************************************/
        layout_account_inner_delivery_details=view.findViewById(R.id.layout_account_inner_delivery_details);
        layout_account_Default_Delivery_address=view.findViewById(R.id.layout_account_Default_Delivery_address);
        layout_account_billing_Details=view.findViewById(R.id.layout_account_billing_Details);
        layout_account_inner_billing_details=view.findViewById(R.id.layout_account_inner_billing_details);
        profile_pic = view.findViewById(R.id.profilepic);
        username = view.findViewById(R.id.txt_username);
        layout_Add_Card=view.findViewById(R.id.layout_Add_Card);
        email = view.findViewById(R.id.txt_email);
        layout_account_add_card=view.findViewById(R.id.layout_account_add_card);
        change_password = view.findViewById(R.id.txt_change_password);
        txt_busniess_name=view.findViewById(R.id.txt_business_name);
        layout_account_Business_name=view.findViewById(R.id.layout_account_Business_name);
        txt_billing_details = view.findViewById(R.id.txt_billing_details);
        txt_default_delivery_address = view.findViewById(R.id.txt_default_delivery_address);
        txt_add_card = view.findViewById(R.id.txt_add_card);
        homeobj.nav_search_layout.setVisibility(View.VISIBLE);

        Log.e("user",Paper.book().read("user")+"");

      /*  if (Paper.book().read("user").equals("0")){

            username.setText("Guest");
            email.setText("No Email Address");
        }
        else {
*/
            String first_name = Paper.book().read("first_name");
            String last_name = Paper.book().read("last_name");

            username.setText(first_name+" "+last_name);      //get data from login Api
            email.setText(Paper.book().read("email"));             //get data from login Api

      /*  }*/

        if (!(Paper.book().read("user").equals("0"))){

            layout_account_Business_name.setVisibility(View.GONE);
            layout_account_Default_Delivery_address.setVisibility(View.VISIBLE);
            layout_account_billing_Details.setVisibility(View.VISIBLE);
            layout_account_add_card.setVisibility(View.VISIBLE);

        }
        else {

            layout_account_Business_name.setVisibility(View.GONE);
            layout_account_Default_Delivery_address.setVisibility(View.GONE);
            layout_account_billing_Details.setVisibility(View.GONE);
            layout_account_add_card.setVisibility(View.VISIBLE);

        }

       //  homeobj.nav_search_layout.setVisibility(View.GONE);        //hide visibility of Search Bar


        layout_account_add_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                change_password.setTextColor(getResources().getColor(R.color.themeblack));
                txt_busniess_name.setTextColor(getResources().getColor(R.color.themeblack));
                txt_billing_details.setTextColor(getResources().getColor(R.color.themeblack));
                txt_default_delivery_address.setTextColor(getResources().getColor(R.color.themeblack));
                txt_add_card.setTextColor(getResources().getColor(R.color.themered));

                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("account").replace(R.id.containerr,new Fragment_Display_Card());
                fragmentTransaction.commit();

            }
        });

        layout_account_inner_delivery_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                change_password.setTextColor(getResources().getColor(R.color.themeblack));
                txt_busniess_name.setTextColor(getResources().getColor(R.color.themeblack));
                txt_billing_details.setTextColor(getResources().getColor(R.color.themeblack));
                txt_default_delivery_address.setTextColor(getResources().getColor(R.color.themered));
                txt_add_card.setTextColor(getResources().getColor(R.color.themeblack));

                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("account").replace(R.id.containerr,new Default_Delivery_address());
                fragmentTransaction.commit();

            }
        });


        layout_account_inner_billing_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                change_password.setTextColor(getResources().getColor(R.color.themeblack));
                txt_busniess_name.setTextColor(getResources().getColor(R.color.themeblack));
                txt_billing_details.setTextColor(getResources().getColor(R.color.themered));
                txt_default_delivery_address.setTextColor(getResources().getColor(R.color.themeblack));
                txt_add_card.setTextColor(getResources().getColor(R.color.themeblack));
                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("account").replace(R.id.containerr,new Billing_Details());
                fragmentTransaction.commit();

            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                change_password.setTextColor(getResources().getColor(R.color.themered));
                txt_busniess_name.setTextColor(getResources().getColor(R.color.themeblack));
                txt_billing_details.setTextColor(getResources().getColor(R.color.themeblack));
                txt_default_delivery_address.setTextColor(getResources().getColor(R.color.themeblack));
                txt_add_card.setTextColor(getResources().getColor(R.color.themeblack));


                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("account").replace(R.id.containerr,new ChangePass());
                fragmentTransaction.commit();
            }
        });

        ((Home) getActivity()).hideView(true);

        return view;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity.getClass() == Home.class) {
            homeobj = (Home) activity;
        }

    }
}
