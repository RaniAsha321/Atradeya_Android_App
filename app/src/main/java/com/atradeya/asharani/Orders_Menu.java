package com.atradeya.asharani;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import io.paperdb.Paper;

public class Orders_Menu extends Fragment {

    Adapter_Order_Awaiting dataa;
    Home home;
     TextView txt_Orders,txt_awaiting;
    static TextView approve_size;
    LinearLayout layout_awaiting_orders,layout_all_orders,layout_order_btn,layout_txt_orders;
    List<AwatingOrderDatum> dataorderlist;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders__menu, container, false);

        txt_Orders = view.findViewById(R.id.my_order);
        txt_awaiting = view.findViewById(R.id.awaiting);
        layout_all_orders = view.findViewById(R.id.layout_all_orders);
        layout_awaiting_orders = view.findViewById(R.id.layout_awaiting_orders);
        approve_size = view.findViewById(R.id.ap_size);
        layout_order_btn=view.findViewById(R.id.order_btn_layout);

        layout_txt_orders = view.findViewById(R.id.layout_txt_orders);

        txt_Orders.setBackgroundResource(R.color.themered);
        txt_Orders.setTextColor(getResources().getColor(R.color.white));
        txt_awaiting.setBackgroundResource(R.color.shadegrey);
        txt_awaiting.setTextColor(getResources().getColor(R.color.themeblack));

        home.nav_search_layout.setVisibility(View.VISIBLE);



        if (Paper.book().read("datarole", "5").equals("4")) {

            if (Paper.book().read("permission_all_orders", "2").equals("1")) {

                FragmentTransaction transactions = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.orders_container, new My_Orders());
                transactions.commit();

                int data = Paper.book().read("awaiting_data");
                if (data != 0) {

                    approve_size.setText(String.valueOf(data));
                    approve_size.setBackgroundResource(R.drawable.circle_view);
                }

                else {

                    approve_size.setVisibility(View.GONE);
                }

                txt_Orders.setBackgroundResource(R.color.themered);
                txt_Orders.setTextColor(getResources().getColor(R.color.white));

            }


            else if(Paper.book().read("permission_awaiting", "2").equals("1")) {

                FragmentTransaction transactions = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.orders_container, new Awaiting());
                transactions.commit();

                int data = Paper.book().read("awaiting_data");

                if (data != 0) {

                    approve_size.setText(String.valueOf(data));
                    approve_size.setBackgroundResource(R.drawable.circle_view);
                } else {

                    approve_size.setVisibility(View.GONE);

                }

                txt_awaiting.setBackgroundResource(R.color.themered);
                txt_awaiting.setTextColor(getResources().getColor(R.color.white));

            }

            else  if(Paper.book().read("permission_awaiting", "2").equals("0")) {

                layout_awaiting_orders.setVisibility(View.GONE);
            }

            else  if(Paper.book().read("permission_all_orders", "2").equals("0")) {

                layout_all_orders.setVisibility(View.GONE);
            }

            else if(Paper.book().read("permission_awaiting", "2").equals("0") && Paper.book().read("permission_all_orders", "2").equals("0") )
            {

                layout_order_btn.setVisibility(View.GONE);

            }

            else  if(Paper.book().read("datarole", "5").equals("4")){

                if(Paper.book().read("permission_wholeseller", "5").equals("1")) {

                    layout_all_orders.setVisibility(View.VISIBLE);
                    layout_awaiting_orders.setVisibility(View.GONE);
                    layout_order_btn.setVisibility(View.VISIBLE);
                }
            }
        }

        else {

            layout_txt_orders.setVisibility(View.VISIBLE);
            FragmentTransaction transactions = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.orders_container, new My_Orders());
            transactions.commit();



        }

        int data = Paper.book().read("awaiting_data");

        if (data != 0) {

            approve_size.setText(String.valueOf(data));
            approve_size.setBackgroundResource(R.drawable.circle_view);
        } else {

            approve_size.setVisibility(View.GONE);
        }


        if (Paper.book().read("permission_awaiting", "2").equals("1") &&Paper.book().read("permission_all_orders", "2").equals("0")) {

            FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction().add(R.id.orders_container, new Awaiting());
            transaction1.commit();

        }

        if (Paper.book().read("permission_awaiting", "2").equals("0") &&Paper.book().read("permission_all_orders", "2").equals("1")) {

            FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction().add(R.id.orders_container, new My_Orders());
            transaction1.commit();

        }

        if(Paper.book().read("permission_awaiting", "2").equals("1") &&Paper.book().read("permission_all_orders", "2").equals("1")) {

            FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction().add(R.id.orders_container, new My_Orders());
            transaction1.commit();

        }

        if(Paper.book().read("permission_awaiting", "2").equals("0")) {

            layout_awaiting_orders.setVisibility(View.GONE);
            layout_txt_orders.setVisibility(View.VISIBLE);
        }

        if(Paper.book().read("permission_all_orders", "2").equals("0")) {

            layout_all_orders.setVisibility(View.GONE);
            layout_txt_orders.setVisibility(View.VISIBLE);
        }

        if(Paper.book().read("permission_awaiting", "2").equals("0") && Paper.book().read("permission_all_orders", "2").equals("0") )
        {

            layout_order_btn.setVisibility(View.GONE);
            layout_txt_orders.setVisibility(View.VISIBLE);

        }


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    getFragmentManager().popBackStack("category", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }

                home.drawerLayout.openDrawer(Gravity.START);
                return false;
            }
        });

        ((Home) getActivity()).hideView(true);

        if (Paper.book().read("datarole", "5").equals("3")) {

            txt_Orders.setVisibility(View.VISIBLE);
            txt_awaiting.setVisibility(View.VISIBLE);

            txt_Orders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    txt_Orders.setBackgroundResource(R.color.themered);
                    txt_Orders.setTextColor(getResources().getColor(R.color.white));
                    txt_awaiting.setBackgroundResource(R.color.shadegrey);
                    txt_awaiting.setTextColor(getResources().getColor(R.color.themeblack));
                    approve_size.setBackgroundResource(R.drawable.circle_view);
                    approve_size.setTextColor(getResources().getColor(R.color.white));

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.orders_container, new My_Orders());
                    transaction.commit();
                }
            });

            txt_awaiting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    txt_awaiting.setBackgroundResource(R.color.themered);
                    txt_awaiting.setTextColor(getResources().getColor(R.color.white));
                    txt_Orders.setBackgroundResource(R.color.shadegrey);
                    txt_Orders.setTextColor(getResources().getColor(R.color.themeblack));
                    approve_size.setBackgroundResource(R.drawable.circle_view_white);
                    approve_size.setTextColor(getResources().getColor(R.color.themered));

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.orders_container, new Awaiting());
                    transaction.commit();
                }
            });
        } else if (Paper.book().read("datarole", "5").equals("1")) {

            txt_Orders.setVisibility(View.VISIBLE);
            txt_awaiting.setVisibility(View.VISIBLE);

            txt_Orders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    txt_Orders.setBackgroundResource(R.color.themered);
                    txt_Orders.setTextColor(getResources().getColor(R.color.white));
                    txt_awaiting.setBackgroundResource(R.color.shadegrey);
                    txt_awaiting.setTextColor(getResources().getColor(R.color.themeblack));

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.orders_container, new My_Orders());
                    transaction.commit();
                }
            });


            txt_awaiting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    txt_awaiting.setBackgroundResource(R.color.themered);
                    txt_awaiting.setTextColor(getResources().getColor(R.color.white));
                    txt_Orders.setBackgroundResource(R.color.shadegrey);
                    txt_Orders.setTextColor(getResources().getColor(R.color.themeblack));

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.orders_container, new Awaiting());
                    transaction.commit();
                }
            });
        }

        else if (Paper.book().read("datarole", "5").equals("2")) {

            txt_Orders.setVisibility(View.VISIBLE);
            txt_awaiting.setVisibility(View.VISIBLE);

            txt_Orders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    txt_Orders.setBackgroundResource(R.color.themered);
                    txt_Orders.setTextColor(getResources().getColor(R.color.white));
                    txt_awaiting.setBackgroundResource(R.color.shadegrey);
                    txt_awaiting.setTextColor(getResources().getColor(R.color.themeblack));

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.orders_container, new My_Orders());
                    transaction.commit();
                }
            });

            txt_awaiting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    txt_awaiting.setBackgroundResource(R.color.themered);
                    txt_awaiting.setTextColor(getResources().getColor(R.color.white));
                    txt_Orders.setBackgroundResource(R.color.shadegrey);
                    txt_Orders.setTextColor(getResources().getColor(R.color.themeblack));

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.orders_container, new Awaiting());
                    transaction.commit();
                }
            });
        }


        else if (Paper.book().read("datarole", "5").equals("4")) {

            if ((Paper.book().read("permission_awaiting", "2").equals("0")) && (Paper.book().read("permission_all_orders", "2").equals("0"))) {

                txt_Orders.setBackgroundColor(getResources().getColor(R.color.themered));
                txt_Orders.setTextColor(getResources().getColor(R.color.white));
                approve_size.setTextColor(getResources().getColor(R.color.white));
                approve_size.setBackgroundResource(R.drawable.circle_view);

                txt_Orders.setClickable(false);
                layout_awaiting_orders.setVisibility(View.GONE);

                FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction().add(R.id.orders_container, new My_Orders());
                transaction1.commit();

            }

            else {

                txt_Orders.setVisibility(View.VISIBLE);
                txt_awaiting.setVisibility(View.VISIBLE);

                txt_Orders.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        approve_size.setTextColor(getResources().getColor(R.color.white));
                        approve_size.setBackgroundResource(R.drawable.circle_view);
                        txt_Orders.setBackgroundResource(R.color.themered);
                        txt_Orders.setTextColor(getResources().getColor(R.color.white));
                        txt_awaiting.setBackgroundResource(R.color.shadegrey);
                        txt_awaiting.setTextColor(getResources().getColor(R.color.themeblack));

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.orders_container, new My_Orders());
                        transaction.commit();
                    }
                });

                txt_awaiting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        approve_size.setTextColor(getResources().getColor(R.color.themered));
                        approve_size.setBackgroundResource(R.drawable.circle_view_white);
                        txt_awaiting.setBackgroundResource(R.color.themered);
                        txt_awaiting.setTextColor(getResources().getColor(R.color.white));
                        txt_Orders.setBackgroundResource(R.color.shadegrey);
                        txt_Orders.setTextColor(getResources().getColor(R.color.themeblack));

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.orders_container, new Awaiting());
                        transaction.commit();
                    }
                });
            }
        }

        else if (Paper.book().read("datarole", "5").equals("4")) {

            if ((Paper.book().read("permission_awaiting", "2").equals("1"))) {

                if(Paper.book().read("permission_all_orders", "2").equals("0")) {

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(20, 0, 20, 0);
                params.height = 65;
                params.gravity= Gravity.CENTER;
                layout_awaiting_orders.setLayoutParams(params);
                approve_size.setVisibility(View.GONE);
                layout_all_orders.setVisibility(View.GONE);
                layout_awaiting_orders.setClickable(false);
                txt_awaiting.setBackgroundColor(getResources().getColor(R.color.themered));
                txt_awaiting.setTextColor(getResources().getColor(R.color.white));

                FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction().add(R.id.orders_container, new Awaiting());
                transaction1.commit();

            }
                else {

                    txt_Orders.setVisibility(View.VISIBLE);
                    txt_awaiting.setVisibility(View.VISIBLE);

                    txt_Orders.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            approve_size.setTextColor(getResources().getColor(R.color.white));
                            approve_size.setBackgroundResource(R.drawable.circle_view);
                            txt_Orders.setBackgroundResource(R.color.themered);
                            txt_Orders.setTextColor(getResources().getColor(R.color.white));
                            txt_awaiting.setBackgroundResource(R.color.shadegrey);
                            txt_awaiting.setTextColor(getResources().getColor(R.color.themeblack));

                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.orders_container, new My_Orders());
                            transaction.commit();
                        }
                    });

                    txt_awaiting.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            approve_size.setTextColor(getResources().getColor(R.color.themered));
                            txt_awaiting.setBackgroundResource(R.color.themered);
                            txt_awaiting.setTextColor(getResources().getColor(R.color.white));
                            txt_Orders.setBackgroundResource(R.color.shadegrey);
                            txt_Orders.setTextColor(getResources().getColor(R.color.themeblack));

                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.orders_container, new Awaiting());
                            transaction.commit();
                        }
                    });
                }

            }

        }
        else if (Paper.book().read("datarole", "5").equals("4")) {

            if ((Paper.book().read("permission_awaiting", "2").equals("1")) && (Paper.book().read("permission_all_orders", "2").equals("1")))  {

                txt_Orders.setVisibility(View.VISIBLE);
                txt_awaiting.setVisibility(View.VISIBLE);

                txt_Orders.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        approve_size.setTextColor(getResources().getColor(R.color.white));
                        approve_size.setBackgroundResource(R.drawable.circle_view);
                        txt_Orders.setBackgroundResource(R.color.themered);
                        txt_Orders.setTextColor(getResources().getColor(R.color.white));
                        txt_awaiting.setBackgroundResource(R.color.shadegrey);
                        txt_awaiting.setTextColor(getResources().getColor(R.color.themeblack));

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.orders_container, new My_Orders());
                        transaction.commit();
                    }
                });

                txt_awaiting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        approve_size.setTextColor(getResources().getColor(R.color.themered));
                        txt_awaiting.setBackgroundResource(R.color.themered);
                        txt_awaiting.setTextColor(getResources().getColor(R.color.white));
                        txt_Orders.setBackgroundResource(R.color.shadegrey);
                        txt_Orders.setTextColor(getResources().getColor(R.color.themeblack));

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.orders_container, new Awaiting());
                        transaction.commit();
                    }
                });
            }

            if ((Paper.book().read("permission_awaiting", "2").equals("0")) && (Paper.book().read("permission_all_orders", "2").equals("1"))) {

                txt_Orders.setVisibility(View.VISIBLE);

            }

            if ((Paper.book().read("permission_awaiting", "2").equals("1")) && (Paper.book().read("permission_all_orders", "2").equals("0"))) {

                txt_awaiting.setVisibility(View.VISIBLE);

            }

        }

            if(Paper.book().read("permission_wholeseller", "5").equals("1")) {

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.setMargins(20, 0, 20, 0);
                params.gravity= Gravity.CENTER;;
                params.height = 120;
                layout_all_orders.setLayoutParams(params);
                layout_order_btn.setVisibility(View.GONE);
                layout_txt_orders.setVisibility(View.VISIBLE);

            }
                return view;
    }


    public void onAttach (Activity activity) {
        super.onAttach(activity);
        if(activity.getClass()== Home.class){
            home = (Home) activity;
        }
    }
}

