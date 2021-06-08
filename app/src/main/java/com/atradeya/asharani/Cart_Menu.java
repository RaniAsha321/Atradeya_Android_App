package com.atradeya.asharani;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cart_Menu extends Fragment implements MyInterface{

    EditText add_product_description,add_product_quantity,add_product_price,add_product_product_code;
    LinearLayout layout_cart_menu,layout_add_product,layout_close,layout_close_bottom,cartlayout,drop_delivery_collection,layout_del_col,layout_delivery_cart_menu;;
    Home home_activity;
    RecyclerView recyclerview;
    Adapter_Cart_Menu adapterCartMenu;
    List<ModelCartMenu> modelCartMenus;
    LinearLayoutManager layoutManager;
    TextView final_price,vat,total,tv_drop_delivery_collection,tv_drop_delivery_cost,tv_drop_delivery_address,delivery;
    Button Continue,btn_add_product,btn_close_cart_popup,btn_add_cart_popup,Proceed;
    private databaseSqlite databaseSqlite;
    Cart_Menu cart_menu;
    String userid,finalRWt;;
    FragmentTransaction fragmentTransaction;
    RadioGroup radioGroup,radioGroup1;
    LinearLayout popup_register_cross,popup_signin,popup_signup,layout_main_cost,layout_main_address,drop_collection;
    ListView my_list;
    PopupWindow popUp,popUp1;
    List<GetAddress> AddressData;
    List<GetAddress> myaddress;
    List<String> variation_delivery;
    List<StoreDatum> StoreData;
    List<StoreDatum> mystore;
    List<String> variation_store;
    List<DeleveryCost> Delivery_Cost;
    List<DeleveryCost> mycost;
    List<String> variation_cost;
    FragmentTransaction transaction;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.cart__menu, container, false);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    getFragmentManager().popBackStack("home_category", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }

                Paper.book().write("delivery_address","");
                return false;
            }
        });
        home_activity.nav_search_layout.setVisibility(View.VISIBLE);

       // popUp.dismiss();

        ((Home)getActivity()).hideView(true);
        cart_menu= new Cart_Menu();
        final_price=view.findViewById(R.id.finalprice);
        Continue=view.findViewById(R.id.btn_cart_continue);
        recyclerview=view.findViewById(R.id.recyclerview_Cart_Menu);
        btn_add_product=view.findViewById(R.id.pop_add_product_cart);
        layout_cart_menu=view.findViewById(R.id.layout_cart_menu);
        layout_add_product=view.findViewById(R.id.layout_add_product);
        databaseSqlite=new databaseSqlite(getActivity());
        vat = view.findViewById(R.id.cart_vat);
        total = view.findViewById(R.id.cart_total);
        radioGroup = view.findViewById(R.id.radioGroup);
        cartlayout = view.findViewById(R.id.layout_cart_menu);
        drop_delivery_collection = view.findViewById(R.id.drop_delivery_collection);
        layout_del_col = view.findViewById(R.id.layout_del_col);
        tv_drop_delivery_collection = view.findViewById(R.id.tv_drop_delivery_collection);
        Proceed = view.findViewById(R.id.btn_proceed);
        layout_main_cost = view.findViewById(R.id.layout_main_cost);
        tv_drop_delivery_cost = view.findViewById(R.id.tv_drop_delivery_cost);
        tv_drop_delivery_address = view.findViewById(R.id.tv_drop_delivery_address);
        layout_main_address = view.findViewById(R.id.layout_main_address);
        drop_collection = view.findViewById(R.id.drop_collection);
        delivery = view.findViewById(R.id.delivery);
        layout_delivery_cart_menu = view.findViewById(R.id.layout_delivery_cart_menu);

        variation_delivery=new ArrayList<>();
        myaddress = new ArrayList<>();

        variation_store=new ArrayList<>();
        variation_cost=new ArrayList<>();
        mystore = new ArrayList<>();
        mycost = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);

        modelCartMenus=new ArrayList<>();
/*

        String cart_summary = Paper.book().read("cart_summary");


        if ((cart_summary != null) && (cart_summary.equals("1"))){

           popUp1.dismiss();

        }
*/


        modelCartMenus.addAll(databaseSqlite.getAllModelCartMenu());
        adapterCartMenu=new Adapter_Cart_Menu(getActivity(),modelCartMenus,cart_menu,this);
        recyclerview.setAdapter(adapterCartMenu);

        Paper.book().write("selected_delivery_cost","");
        Paper.book().write("tempmodelcart",modelCartMenus);

        adapterCartMenu.notifyDataSetChanged();

        Paper.book().write("total",databaseSqlite.getAllModelCartMenu().size());

        double value=databaseSqlite.getAllPrices();

        String data= String.format("%.2f", value);

        double sub=databaseSqlite.getAllInc();
        sub = sub+(Double.valueOf(delivery.getText().toString()));
        String data1= String.format("%.2f", sub);
        double vat1=sub-value;

        String vatvalue= String.format("%.2f", vat1);

        vat.setText(vatvalue);
        total.setText(data1);

        final_price.setText(data);

        Paper.book().write("final_total",value);

        getaddress();
        getaddress_cost();
        getDeliveryCost();

        drop_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dropdown_collection();

            }
        });

        layout_main_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dropdown_delivery2();
            }
        });

        layout_main_cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drop_cost();

            }
        });



        drop_delivery_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user= Paper.book().read("user");

                Log.e("chawal",user+"");

                if (user.equals("0")){

                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);

                }

                else {

                        layout_del_col.setVisibility(View.VISIBLE);
                }

            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb = (RadioButton) group.findViewById(checkedId);

                if (checkedId != -1  && null != rb){

                    if (rb.getText().toString().equals("Delivery")) {

                      //  delivery_cost_description();
                        radioGroup.clearCheck();
                        layout_del_col.setVisibility(View.GONE);
                        dropdown_delivery();


                    }

                    else {

                        radioGroup.clearCheck();
                        layout_del_col.setVisibility(View.GONE);
                        dropdown_collection();

                    }

                }

            }
        });

        Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  Paper.book().write("delivery_address","");

                if (drop_collection.getVisibility() == View.VISIBLE){

                    Paper.book().write("selected_delivery_cost","");

                }


                if (!tv_drop_delivery_collection.getText().equals("Select Delivery/Collection")  || !tv_drop_delivery_address.getText().equals("Select Delivery/Collection")) {

                        transaction = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("li").replace(R.id.containerr, new Cart_Summary());
                        transaction.commit();

                }

                else {

                    Toast.makeText(getActivity(), "Select Delivery/Collection", Toast.LENGTH_SHORT).show();
                }


            }
        });





        return view;
    }

    private void getDeliveryCost() {

        String wholeseller_bus_id = Paper.book().read("business_id");

        Log.e("dara1",wholeseller_bus_id+"");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        service.DELIVERY_COST_CALL(wholeseller_bus_id).enqueue(new Callback<ModelDeliveryCost>() {
            @Override
            public void onResponse(Call<ModelDeliveryCost> call, Response<ModelDeliveryCost> response) {


                if(response.body().getStatusCode().equals(200)) {

                    Log.e("getTo",response.body().getStatusCode().toString()+"");
                    Delivery_Cost = response.body().getDeleveryCost();

                    for (int i = 0; i < Delivery_Cost.size(); i++) {

                        DeleveryCost address = new DeleveryCost();
                        address.setId(response.body().getDeleveryCost().get(i).getId());
                        address.setTitle(response.body().getDeleveryCost().get(i).getTitle());
                        address.setCost(response.body().getDeleveryCost().get(i).getCost());

                        mycost.add(address);
                    }


                    if(Paper.book().read("permission_wholeseller", "5").equals("1")) {

                        for (int i=0; i<Delivery_Cost.size();i++) {

                            DeleveryCost datum = new DeleveryCost();
                            datum.setTitle(response.body().getDeleveryCost().get(i).getTitle()+","+response.body().getDeleveryCost().get(i).getCost() );
                            variation_cost.add(response.body().getDeleveryCost().get(i).getTitle()+","+response.body().getDeleveryCost().get(i).getCost() );
                        }
                    }

                    else {
                        for (int i=0; i<Delivery_Cost.size();i++) {

                            DeleveryCost datum = new DeleveryCost();
                            datum.setTitle(response.body().getDeleveryCost().get(i).getTitle()+","+response.body().getDeleveryCost().get(i).getCost() );
                            variation_cost.add(response.body().getDeleveryCost().get(i).getTitle()+","+response.body().getDeleveryCost().get(i).getCost() );
                        }
                    }

                }

            }

            @Override
            public void onFailure(Call<ModelDeliveryCost> call, Throwable t) {

                t.printStackTrace();
                Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getaddress_cost() {

        String wholeseller_bus_id = Paper.book().read("business_id");

        Log.e("dara2",wholeseller_bus_id+"");

        Paper.book().write("selected_delivery_cost","");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        service.STORE_CALL(wholeseller_bus_id).enqueue(new Callback<ModelCollectionStore>() {
            @Override
            public void onResponse(Call<ModelCollectionStore> call, Response<ModelCollectionStore> response) {
                if(response.body().getStatusCode().equals(200)) {

                    Log.e("checktag",response.body().getStatusCode().toString());


                    StoreData = response.body().getStoreData();

                    for (int i = 0; i < StoreData.size(); i++) {

                        StoreDatum address = new StoreDatum();
                        address.setId(response.body().getStoreData().get(i).getId());
                        address.setStoreName(response.body().getStoreData().get(i).getStoreName());
                        address.setStoreAddressOne(response.body().getStoreData().get(i).getStoreAddressOne());
                        address.setStoreAddressTwo(response.body().getStoreData().get(i).getStoreAddressTwo());
                        address.setCity(response.body().getStoreData().get(i).getCity());
                        address.setPostCode(response.body().getStoreData().get(i).getPostCode());
                        address.setContactNumber(response.body().getStoreData().get(i).getContactNumber());
                        address.setEmail(response.body().getStoreData().get(i).getEmail());

                        Log.e("checktag2",response.body().getStoreData().get(i).getStoreName()+","+response.body().getStoreData().get(i).getStoreAddressOne() +","+response.body().getStoreData().get(i).getStoreAddressTwo() +","+response.body().getStoreData().get(i).getCity()
                                +","+response.body().getStoreData().get(i).getPostCode() +","+response.body().getStoreData().get(i).getContactNumber());

                        mystore.add(address);
                    }


                    if(Paper.book().read("permission_wholeseller", "5").equals("1")) {

                        for (int i=0; i<StoreData.size();i++) {

                            StoreDatum datum = new StoreDatum();

                            Log.e("checktag3",response.body().getStoreData().get(i).getStoreName()+","+response.body().getStoreData().get(i).getStoreAddressOne() +","+response.body().getStoreData().get(i).getStoreAddressTwo() +","+response.body().getStoreData().get(i).getCity()
                                    +","+response.body().getStoreData().get(i).getPostCode() +","+response.body().getStoreData().get(i).getContactNumber());


                            datum.setStoreName(response.body().getStoreData().get(i).getStoreName()+","+response.body().getStoreData().get(i).getStoreAddressOne() +","+response.body().getStoreData().get(i).getStoreAddressTwo() +","+response.body().getStoreData().get(i).getCity()
                                    +","+response.body().getStoreData().get(i).getPostCode() +","+response.body().getStoreData().get(i).getContactNumber());
                            variation_store.add(response.body().getStoreData().get(i).getStoreName()+","+response.body().getStoreData().get(i).getStoreAddressOne() +","+response.body().getStoreData().get(i).getStoreAddressTwo() +","+response.body().getStoreData().get(i).getCity()
                                    +","+response.body().getStoreData().get(i).getPostCode() +","+response.body().getStoreData().get(i).getContactNumber());
                        }
                    }

                    else {
                        for (int i=0; i<StoreData.size();i++) {

                            StoreDatum datum = new StoreDatum();

                            Log.e("checktag4",response.body().getStoreData().get(i).getStoreName()+","+response.body().getStoreData().get(i).getStoreAddressOne() +","+response.body().getStoreData().get(i).getStoreAddressTwo() +","+response.body().getStoreData().get(i).getCity()
                                    +","+response.body().getStoreData().get(i).getPostCode() +","+response.body().getStoreData().get(i).getContactNumber());

                            datum.setStoreName(response.body().getStoreData().get(i).getStoreName()+","+response.body().getStoreData().get(i).getStoreAddressOne() +","+response.body().getStoreData().get(i).getStoreAddressTwo() +","+response.body().getStoreData().get(i).getCity()
                                    +","+response.body().getStoreData().get(i).getPostCode() +","+response.body().getStoreData().get(i).getContactNumber());
                            variation_store.add(response.body().getStoreData().get(i).getStoreName()+","+response.body().getStoreData().get(i).getStoreAddressOne() +","+response.body().getStoreData().get(i).getStoreAddressTwo() +","+response.body().getStoreData().get(i).getCity()
                                    +","+response.body().getStoreData().get(i).getPostCode() +","+response.body().getStoreData().get(i).getContactNumber());
                        }
                    }

                }

            }

            @Override
            public void onFailure(Call<ModelCollectionStore> call, Throwable t) {

                t.printStackTrace();
                Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
/*

    private void delivery_cost_description() {

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.custom_dropdown_menu_del_col, null);

        layout_close = (LinearLayout) customView.findViewById(R.id.layout_popup_upper);
        layout_close_bottom=(LinearLayout) customView.findViewById(R.id.layout_popup_bottom);
        my_list = customView.findViewById(R.id.list);
        radioGroup1 = customView.findViewById(R.id.radioGroup);
        //instantiate popup window
        popUp = new PopupWindow(customView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        //display the popup window
        popUp.showAtLocation(cartlayout, Gravity.CENTER, 0,0);

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb = (RadioButton) group.findViewById(checkedId);

                if (checkedId != -1  && null != rb){

                    if (rb.getText().toString().equals("delivery_address")) {

                        popUp.dismiss();
                       // dropdown_delivery();

                    }

                    else {

                        drop_cost();
                        popUp.dismiss();



                    }

                }

            }
        });



        //close the popup window on button click
        layout_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popUp.dismiss();
                radioGroup.clearCheck();
                popUp.dismiss();
            }
        });

        layout_close_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.dismiss();
                radioGroup.clearCheck();
                popUp.dismiss();

            }
        });

    }
*/



    private void drop_cost() {


        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.custom_dropdown_menu, null);

        layout_close = (LinearLayout) customView.findViewById(R.id.layout_popup_upper);
        layout_close_bottom=(LinearLayout) customView.findViewById(R.id.layout_popup_bottom);
        my_list = customView.findViewById(R.id.list);
        //instantiate popup window
        popUp1 = new PopupWindow(customView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, variation_cost);

        my_list.setAdapter(arrayAdapter);

        my_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Paper.book().write("cost_id",mycost.get(position).getId());
                layout_delivery_cart_menu.setVisibility(View.VISIBLE);
                drop_delivery_collection.setVisibility(View.GONE);
                layout_del_col.setVisibility(View.GONE);
                layout_main_cost.setVisibility(View.VISIBLE);
                tv_drop_delivery_cost.setText(mycost.get(position).getTitle() +"," +mycost.get(position).getCost());
                delivery.setText(mycost.get(position).getCost());


                Paper.book().write("selected_delivery_cost",mycost.get(position).getCost());

                double sub=databaseSqlite.getAllInc();
                sub = sub+(Double.valueOf(delivery.getText().toString()));
                String data1= String.format("%.2f", sub);
                total.setText(data1);

                popUp1.dismiss();

            }
        });
        //display the popup window
        popUp1.showAtLocation(cartlayout, Gravity.CENTER, 0, 0);

        //close the popup window on button click
        layout_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popUp1.dismiss();
            }
        });

        layout_close_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp1.dismiss();
              /*  radioGroup.clearCheck();
                popUp1.dismiss();*/

            }
        });

    }

    private void dropdown_collection() {

        Paper.book().write("delivery_address","");

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.custom_dropdown_menu, null);

        layout_close = (LinearLayout) customView.findViewById(R.id.layout_popup_upper);
        layout_close_bottom=(LinearLayout) customView.findViewById(R.id.layout_popup_bottom);
        my_list = customView.findViewById(R.id.list);
        //instantiate popup window
        popUp1 = new PopupWindow(customView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popUp1.setOutsideTouchable(true);
        popUp1.setFocusable(true);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, variation_store);

        my_list.setAdapter(arrayAdapter);

        my_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Paper.book().write("collection_id",mystore.get(position).getId());

                layout_del_col.setVisibility(View.GONE);
                drop_collection.setVisibility(View.VISIBLE);
                drop_delivery_collection.setVisibility(View.GONE);
                Paper.book().write("selected_delivery_cost","");
                tv_drop_delivery_collection.setText(mystore.get(position).getStoreName() +"," +mystore.get(position).getStoreAddressOne() +"," +mystore.get(position).getStoreAddressTwo() +"," +
                        mystore.get(position).getCity() +"," +mystore.get(position).getPostCode() +"," +mystore.get(position).getContactNumber());

                popUp1.dismiss();

            }
        });
        //display the popup window
        popUp1.showAtLocation(cartlayout, Gravity.CENTER, 0, 0);

        //close the popup window on button click
        layout_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popUp1.dismiss();
               /* radioGroup.clearCheck();
                // popUp.dismiss();
                popUp1.dismiss();*/
            }
        });

        layout_close_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // popUp.dismiss();
                popUp1.dismiss();
                /*radioGroup.clearCheck();
               // popUp.dismiss();
                popUp1.dismiss();*/

            }
        });




    }


    private void getaddress() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        String Wholeseller_engineer_id= Paper.book().read("Wholeseller_engineer_id");

        service.GET_ADDRESS_CALL(Wholeseller_engineer_id).enqueue(new Callback<ModelGetAddress>() {
            @Override
            public void onResponse(Call<ModelGetAddress> call, Response<ModelGetAddress> response) {

                if(response.body().getStatusCode().equals(200)) {

                    AddressData = response.body().getAddress();

                    for (int i = 0; i < AddressData.size(); i++) {

                        GetAddress address = new GetAddress();
                        address.setId(response.body().getAddress().get(i).getId());
                        address.setCompanyName(response.body().getAddress().get(i).getCompanyName());
                        address.setFirstName(response.body().getAddress().get(i).getFirstName());
                        address.setLastName(response.body().getAddress().get(i).getLastName());
                        address.setAddressOne(response.body().getAddress().get(i).getAddressOne());
                        address.setAddressTwo(response.body().getAddress().get(i).getAddressTwo());
                        address.setCity(response.body().getAddress().get(i).getCity());
                        address.setPostcode(response.body().getAddress().get(i).getPostcode());

                        myaddress.add(address);
                    }


                    if(Paper.book().read("permission_wholeseller", "5").equals("1")) {

                        for (int i=0; i<AddressData.size();i++) {

                            GetAddress datum = new GetAddress();
                            datum.setCompanyName(response.body().getAddress().get(i).getAddressOne() +","+response.body().getAddress().get(i).getAddressTwo() +","+response.body().getAddress().get(i).getCity()
                                    +","+response.body().getAddress().get(i).getPostcode() );
                            variation_delivery.add(response.body().getAddress().get(i).getAddressOne() +","+response.body().getAddress().get(i).getAddressTwo() +","+response.body().getAddress().get(i).getCity()
                                    +","+response.body().getAddress().get(i).getPostcode() );
                        }
                    }

                    else {

                        for (int i=0; i<AddressData.size();i++) {

                            GetAddress datum = new GetAddress();
                            datum.setCompanyName(response.body().getAddress().get(i).getAddressOne() +","+response.body().getAddress().get(i).getAddressTwo() +","+response.body().getAddress().get(i).getCity()
                                    +","+response.body().getAddress().get(i).getPostcode() );
                            variation_delivery.add(response.body().getAddress().get(i).getAddressOne() +","+response.body().getAddress().get(i).getAddressTwo() +","+response.body().getAddress().get(i).getCity()
                                    +","+response.body().getAddress().get(i).getPostcode() );
                        }
                    }

                }

            }

            @Override
            public void onFailure(Call<ModelGetAddress> call, Throwable t) {

                t.printStackTrace();
            }
        });


    }

    private void dropdown_delivery() {

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.custom_dropdown_menu, null);

        layout_close = (LinearLayout) customView.findViewById(R.id.layout_popup_upper);
        layout_close_bottom=(LinearLayout) customView.findViewById(R.id.layout_popup_bottom);
        my_list = customView.findViewById(R.id.list);
        //instantiate popup window
        popUp1 = new PopupWindow(customView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, variation_delivery);

        my_list.setAdapter(arrayAdapter);

        my_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Paper.book().write("delivery_address",myaddress.get(position).getId());

                Log.e("delivery_address",myaddress.get(position).getId()+"");

                layout_main_address.setVisibility(View.VISIBLE);

                tv_drop_delivery_address.setText(myaddress.get(position).getAddressOne() +"," +myaddress.get(position).getAddressTwo() +"," +myaddress.get(position).getCity() +"," +
                        myaddress.get(position).getPostcode());
                drop_delivery_collection.setVisibility(View.GONE);
                layout_main_cost.setVisibility(View.VISIBLE);
                popUp1.dismiss();
                drop_cost();

            }
        });
        //display the popup window
        popUp1.showAtLocation(cartlayout, Gravity.CENTER, 0, 0);

        //close the popup window on button click
        layout_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layout_main_cost.setVisibility(View.VISIBLE);
               //layout_delivery_cart_menu.setVisibility(View.VISIBLE);
                drop_delivery_collection.setVisibility(View.GONE);
                Paper.book().write("delivery_address","");
                popUp1.dismiss();
            }
        });

        layout_close_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layout_main_cost.setVisibility(View.VISIBLE);
                // layout_delivery_cart_menu.setVisibility(View.VISIBLE);
                drop_delivery_collection.setVisibility(View.GONE);
                Paper.book().write("delivery_address","");
                popUp1.dismiss();
               /* radioGroup.clearCheck();
                popUp1.dismiss();*/

            }
        });



    }


    private void dropdown_delivery2() {


        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.custom_dropdown_menu, null);

        layout_close = (LinearLayout) customView.findViewById(R.id.layout_popup_upper);
        layout_close_bottom=(LinearLayout) customView.findViewById(R.id.layout_popup_bottom);
        my_list = customView.findViewById(R.id.list);
        //instantiate popup window
        popUp1 = new PopupWindow(customView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, variation_delivery);

        my_list.setAdapter(arrayAdapter);

        my_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Paper.book().write("delivery_address",myaddress.get(position).getId());

                Log.e("delivery_address22",myaddress.get(position).getId()+"");

                tv_drop_delivery_address.setText(myaddress.get(position).getAddressOne() +"," +myaddress.get(position).getAddressTwo() +"," +myaddress.get(position).getCity() +"," +
                        myaddress.get(position).getPostcode());
                popUp1.dismiss();

            }
        });
        //display the popup window
        popUp1.showAtLocation(cartlayout, Gravity.CENTER, 0, 0);

        //close the popup window on button click
        layout_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popUp1.dismiss();
            }
        });

        layout_close_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp1.dismiss();
              /*  radioGroup.clearCheck();
                popUp1.dismiss();*/

            }
        });



    }


    private void add_product() {

        final Dialog dialog= new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_drop_add_product_cart);

        btn_close_cart_popup=(Button) dialog.findViewById(R.id.btn_close_cart_popup);
        btn_add_cart_popup=(Button) dialog.findViewById(R.id.btn_add_cart_popup);
        add_product_description=(EditText)dialog.findViewById(R.id.edtxt_pop_cart_description);
        add_product_quantity=(EditText)dialog.findViewById(R.id.edtxt_pop_cart_quantity);
        add_product_price=(EditText)dialog.findViewById(R.id.edtxt_pop_cart_price);
        add_product_product_code=(EditText)dialog.findViewById(R.id.edtxt_pop_cart_product_code);

        userid= Paper.book().read("userid");
        dialog.show();

        btn_add_cart_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(add_product_description.getText().toString())){

                    if(!TextUtils.isEmpty(add_product_quantity.getText().toString())){

                        String price;

                        if (!(add_product_price.getText().toString()).equals("")){

                            float original_price = Float.parseFloat(add_product_price.getText().toString());
                            finalRWt = ((original_price * 6) / 5) + "";
                            String data= String.format("%.2f", original_price);
                                price = data;
                            }


                        else {
                            price = String.valueOf(0.00);
                            float original_price = Float.parseFloat(price);
                            finalRWt = ((original_price * 6) / 5) + "";
                            price = String.format("%.2f", original_price);


                        }

                        if ((databaseSqlite.ExistsCartProduct(add_product_description.getText().toString()))) {

                            ModelCartMenu modelCartMenu1 = databaseSqlite.getModelProductCartMenu(add_product_description.getText().toString());
                            modelCartMenu1.setQuantity(String.valueOf(Integer.valueOf(modelCartMenu1.getQuantity()) + Integer.valueOf(add_product_quantity.getText().toString())));

                            databaseSqlite.updateNote(modelCartMenu1);

                            if(!(String.valueOf(databaseSqlite.getNotesCount()).equals("0"))) {

                                fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("home_category").replace(R.id.containerr, new Cart_Menu());
                                fragmentTransaction.commit();
                            }

                            else {
                                fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("home_category").replace(R.id.containerr, new EmptyCart());
                                fragmentTransaction.commit();
                            }

                            Toast.makeText(getActivity(), "Added to Cart", Toast.LENGTH_LONG).show();

                        }

                        else {

                            ModelCartMenu modelCartMenu = new ModelCartMenu();
                            modelCartMenu.setUsersid(userid);
                            modelCartMenu.setAdd_product_code(add_product_product_code.getText().toString());
                            modelCartMenu.setProductid("0");
                            modelCartMenu.setProductname(add_product_description.getText().toString());
                            modelCartMenu.setQuantity(add_product_quantity.getText().toString());

                            if(Paper.book().read("permission_see_cost","2").equals(1)){

                                modelCartMenu.setPrice(price);
                            }

                            else  if(Paper.book().read("datarole", "5").equals("4")){

                                if(Paper.book().read("permission_wholeseller", "5").equals("1")) {

                                    modelCartMenu.setPrice(price);

                                }

                            }
                            else {

                                modelCartMenu.setPrice(price);

                            }
                            modelCartMenu.setProductimage("");
                            modelCartMenu.setVariationid("0");
                            modelCartMenu.setInc_vat(finalRWt);
                            modelCartMenu.setVariation_name("");
                            databaseSqlite.insertNote(modelCartMenu);
                            home_activity.cart_size.setText(String.valueOf(databaseSqlite.getNotesCount()));
                            home_activity.cart_size.setBackgroundResource(R.drawable.circle_view);
                            Toast.makeText(getActivity(), "Added to Cart", Toast.LENGTH_LONG).show();

                            if(!(String.valueOf(databaseSqlite.getNotesCount()).equals("0"))) {

                                fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("home_category").replace(R.id.containerr, new Cart_Menu());
                                fragmentTransaction.commit();
                            }

                            else {
                                fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("home_category").replace(R.id.containerr, new EmptyCart());
                                fragmentTransaction.commit();
                            }
                        }

                        dialog.dismiss();

                    }
                    else{

                        Toast.makeText(getActivity(), " Enter Quantity", Toast.LENGTH_SHORT).show();

                    }
                }

                else{

                    Toast.makeText(getActivity(), " Enter Product Description", Toast.LENGTH_SHORT).show();

                }

            }
        });

        btn_close_cart_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });


    }

    public void onAttach (Activity activity) {
        super.onAttach(activity);
        if(activity.getClass()== Home.class){
            home_activity = (Home) activity;
        }
    }
    @Override
    public void foo() {

        if(Paper.book().read("permission_see_cost","2").equals("1")){
            double value=databaseSqlite.getAllPrices();
            String data= String.format("%.2f", value);
            double sub=databaseSqlite.getAllInc();
            String data1= String.format("%.2f", sub);
            double vat1=sub-value;

            Log.e("delivery_top1",(Double.valueOf(delivery.getText().toString())+""));
            Log.e("delivery_top2",vat1+"");



            vat1 = vat1+(Double.valueOf(delivery.getText().toString()));
            String vatvalue= String.format("%.2f", vat1);

            Log.e("delivery_top3",vatvalue+"");

            total.setText(data1);
            vat.setText(vatvalue);

            final_price.setText(data);
        }

        else  if(Paper.book().read("datarole", "5").equals("4")){

            if(Paper.book().read("permission_wholeseller", "5").equals("1")) {

                double value=databaseSqlite.getAllPrices();
                String data= String.format("%.2f", value);

                final_price.setText(data);

            }

        }

        else {

            final_price.setText("0.00");

        }



    }
    @Override
    public void updateCart() {
    }



}
