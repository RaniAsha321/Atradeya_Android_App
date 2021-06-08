package com.atradeya.asharani;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.atradeya.asharani.Payment_Stripe.AddPayment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cart_Summary extends Fragment {


    Context contextd;
    int data33;
    TextView sub_total,vat,total;
    Adapter_cart_next adapter_cart_summary;
    RecyclerView recyclerView;
    List<ModelCartMenu> cartMenuList;
    LinearLayoutManager layoutManager;
    databaseSqlite myDb;
    LinearLayout layout_delivery,layout_collection,layout_summary_delivery;
    TextView price,delivery_Date,collection_date,summary_delivery;
    Button Purchase_order,btn_payment;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String col_date,col_time,del_date,del_time,stripe_token;
    FragmentTransaction transaction;
    JSONArray jsonArray;
    EditText del_address,edtxt_order_desc;
    String devicetoken,delivery_instruction,custm_address,custm_address_postcode,custm_address_city,project_id;;
    AddPayment add_payment;
    int REQUEST_CODE = 0077;

    private int minHour = 7;
    private int minMinute = 0;

    private int maxHour = 17;
    private int maxMinute = 61;

    private int currentHour = 0;
    private int currentMinute = 0;
    Cart_Menu cart_menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cart__summary, container, false);

        cart_menu= new Cart_Menu();

        Purchase_order=view.findViewById(R.id.purchase_order);
        edtxt_order_desc = view.findViewById(R.id.edtxt_order_desc);
        recyclerView=view.findViewById(R.id.cart_summary_recycle);
        del_address=view.findViewById(R.id.edtxt_del_ins);
        sub_total=view.findViewById(R.id.summary_sub_total);
        vat=view.findViewById(R.id.summary_vat);
        total=view.findViewById(R.id.summary_total);
        del_address.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        btn_payment=view.findViewById(R.id.btn_payment);
        summary_delivery = view.findViewById(R.id.summary_delivery);
        layout_summary_delivery = view.findViewById(R.id.layout_summary_delivery);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                      getFragmentManager().popBackStack("li", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    return true;
                }

                Paper.book().write("delivery_address","");

                return false;
            }
        });

        String delivery = Paper.book().read("delivery_address");

        Log.e("delivery",delivery+"");

        jsonArray = new JSONArray();

        myDb=new databaseSqlite(getActivity());
        cartMenuList=myDb.getAllModelCartMenu();

        Paper.book().write("order_del_address",del_address.getText().toString().trim());

        ((Home)getActivity()).hideView(true);

        adapter_cart_summary=new Adapter_cart_next(getActivity(),cartMenuList);
        recyclerView.setAdapter(adapter_cart_summary);
/*
        String Whole_Seller_product=Paper.book().read("Whole_Seller_product");

        if(Whole_Seller_product.equals("0")){

            btn_payment.setVisibility(View.GONE);
        }

        else  if(Paper.book().read("permission_wholeseller", "5").equals("1")  || Paper.book().read("ViewWholesellerPage", "5").equals("1")  ) {

            btn_payment.setVisibility(View.VISIBLE);
        }

        else {

            btn_payment.setVisibility(View.GONE);

        }*/

        double value=myDb.getAllPrices();
        String data= String.format("%.2f", value);
        double sub=myDb.getAllInc();
        String data1= String.format("%.2f", sub);

        double vat1=sub-value;
        String vatvalue= String.format("%.2f", vat1);

        if(Paper.book().read("permission_see_cost","2").equals("1")){

            sub_total.setText(data);
            total.setText(data1);
            vat.setText(vatvalue);
        }

        else {

            sub_total.setText("0.00");
            total.setText("0.00");
            vat.setText("0.00");

        }

        if(Paper.book().read("permission_wholeseller", "5").equals("1")) {

            sub_total.setText(data);
            total.setText(data1);
            vat.setText(vatvalue);
            btn_payment.setVisibility(View.VISIBLE);
        }

        String delivery_cost = Paper.book().read("selected_delivery_cost");

        Log.e("dpuuu",delivery_cost+"");

        if (!delivery_cost.equals("")) {

            Log.e("dpuuu","working");

            layout_summary_delivery.setVisibility(View.VISIBLE);
            summary_delivery.setText(delivery_cost);

            double sub_total_value=myDb.getAllPrices();
            String data_total= String.format("%.2f", sub_total_value);

            double sub_total2=myDb.getAllInc();
            sub_total2 = sub_total2+(Double.valueOf(delivery_cost));
            String sub_total_val= String.format("%.2f", sub_total2);

            double value1=myDb.getAllPrices();
            double vat12=sub-value;
            String vatvalue1= String.format("%.2f", vat12);

            total.setText(sub_total_val);
            sub_total.setText(data_total);
            vat.setText(vatvalue1);
            btn_payment.setVisibility(View.VISIBLE);

        }


       // DeliveryCollection();

            btn_payment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (!TextUtils.isEmpty(edtxt_order_desc.getText().toString())){

                        delivery_instruction=del_address.getText().toString().trim();
                        Log.e("deleveryboy",delivery_instruction+"");
                        jsonarray_payment(delivery_instruction);
                        Intent intent = new Intent(getActivity(), AddPayment.class);
                        startActivity(intent);

                        if(!Paper.book().read("awaiting_data").equals(""))
                        {
                            data33=Paper.book().read("awaiting_data");
                            Paper.book().write("awaiting_data",data33+1);
                        }

                        else{

                            Paper.book().write("awaiting_data",1 );
                        }


                        // jsonarray();
                        //  Toast.makeText(getActivity(), "API Error", Toast.LENGTH_SHORT).show();


                    }
                    else {

                        Toast.makeText(getActivity(), "Please Enter Order Details", Toast.LENGTH_SHORT).show();

                    }


/*
                    if((!delivery_Date.getText().toString().equals("Delivery")) || (!collection_date.getText().toString().equals("Collection")) )
                    {
                        delivery_instruction=del_address.getText().toString().trim();
                        Log.e("deleveryboy",delivery_instruction+"");
                        jsonarray_payment(delivery_instruction);
                        Intent intent = new Intent(getActivity(), AddPayment.class);
                        startActivity(intent);

                        if(!Paper.book().read("awaiting_data").equals(""))
                        {
                            data33=Paper.book().read("awaiting_data");
                            Paper.book().write("awaiting_data",data33+1);
                        }

                        else{

                            Paper.book().write("awaiting_data",1 );
                        }

                    }
                    else
                    {

                        Toast.makeText(getActivity(), "Select Either Delivery or Collection", Toast.LENGTH_SHORT).show();
                    }*/

                }
            });

        Purchase_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(edtxt_order_desc.getText().toString())){

                    jsonarray();
                  //  Toast.makeText(getActivity(), "API Error", Toast.LENGTH_SHORT).show();


                }
                else {

                    Toast.makeText(getActivity(), "Please Enter Order Details", Toast.LENGTH_SHORT).show();

                }

/*
                if((!delivery_Date.getText().toString().equals("Delivery")) || (!collection_date.getText().toString().equals("Collection")) )
                {
                    Paper.book().write("saved","");
                     jsonarray();


                    if(!Paper.book().read("awaiting_data").equals(""))
                    {
                        data33=Paper.book().read("awaiting_data");
                        Paper.book().write("awaiting_data",data33+1);
                    }

                    else{

                        Paper.book().write("awaiting_data",1 );
                    }

                }
                else
                    {

                    Toast.makeText(getActivity(), "Select Either Delivery or Collection", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == resultCode){

            btn_payment.setVisibility(View.VISIBLE);
            stripe_token = data.getStringExtra("stripe_token");

            Toast.makeText(getActivity(),stripe_token, Toast.LENGTH_LONG).show();

            if(stripe_token.length()>1) {


        }

        else {

            btn_payment.setVisibility(View.GONE);
        }
    }
    }

    private void DeliveryCollection() {

        layout_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();


                mHour = calendar.get(Calendar.HOUR_OF_DAY);
                mMinute = calendar.get(Calendar.MINUTE);
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        currentHour = hourOfDay;
                        currentMinute = minute;

                        try {
                            Class<?> superclass = getClass().getSuperclass();
                            Field mTimePickerField = superclass.getDeclaredField("mTimePicker");
                            mTimePickerField.setAccessible(true);
                            TimePicker mTimePicker = (TimePicker) mTimePickerField.get(this);
                            mTimePicker.setOnTimeChangedListener((TimePicker.OnTimeChangedListener) contextd);
                        } catch (NoSuchFieldException e) {
                        } catch (IllegalArgumentException e) {
                        } catch (IllegalAccessException e) {
                        }

                        boolean validTime = true;
                        if (hourOfDay < minHour || (hourOfDay == minHour && minute < minMinute)){
                            validTime = false;

                            Toast.makeText(getActivity(),"Please Select time between 7 am to 6 pm", Toast.LENGTH_SHORT).show();
                        }

                        if (hourOfDay  > maxHour || (hourOfDay == maxHour && minute > maxMinute)){
                            validTime = false;
                            Toast.makeText(getActivity(),"Please Select time between 7 am to 6 pm", Toast.LENGTH_SHORT).show();

                        }

                        if (validTime) {
                            currentHour = hourOfDay;
                            currentMinute = minute;

                            del_time=currentHour + ":" + currentMinute;

                            String temp_value=Paper.book().read("S_value");
                            delivery_Date.setText(temp_value +"  " +del_time);
                            Paper.book().write("order_del_time",del_time);
                        }
                    }


                }, mHour, mMinute, true);


                timePickerDialog.show();
                
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),R.style.TimePickerTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, dayOfMonth);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
                        del_date = dateFormat.format(calendar.getTime());
                        Paper.book().write("S_value",del_date);
                        Paper.book().write("order_del_date",del_date);

                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMinDate(new Date().getTime() - 10000);
                datePickerDialog.show();

                layout_collection.setVisibility(View.GONE);
                collection_date.setText("Collection");
                col_date= String.valueOf(0);
                col_time= String.valueOf(0);
                Paper.book().write("order_col_date",col_date);
                Paper.book().write("order_col_time",col_time);
            }
        });
        
            layout_collection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog=new TimePickerDialog(getActivity(),R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        currentHour = hourOfDay;
                        currentMinute = minute;

                        try {
                            Class<?> superclass = getClass().getSuperclass();
                            Field mTimePickerField = superclass.getDeclaredField("mTimePicker");
                            mTimePickerField.setAccessible(true);
                            TimePicker mTimePicker = (TimePicker) mTimePickerField.get(this);
                            mTimePicker.setOnTimeChangedListener((TimePicker.OnTimeChangedListener) contextd);
                        } catch (NoSuchFieldException e) {
                        } catch (IllegalArgumentException e) {
                        } catch (IllegalAccessException e) {
                        }

                        boolean validTime = true;
                        if (hourOfDay < minHour || (hourOfDay == minHour && minute < minMinute)){
                            validTime = false;

                            Toast.makeText(getActivity(),"Please Select time between 7 am to 6 pm", Toast.LENGTH_SHORT).show();
                        }

                        if (hourOfDay  > maxHour || (hourOfDay == maxHour && minute > maxMinute)){
                            validTime = false;
                            Toast.makeText(getActivity(),"Please Select time between 7 am to 6 pm", Toast.LENGTH_SHORT).show();

                        }


                        if (validTime) {
                            currentHour = hourOfDay;
                            currentMinute = minute;
                            col_time=currentHour + ":" + currentMinute;
                            String temp_value=Paper.book().read("S_value");
                            Paper.book().write("order_col_time",col_time);
                            collection_date.setText(temp_value +"  " +col_time);
                        }

                    }
                }, mHour, mMinute, true);


                timePickerDialog.show();

                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(),R.style.TimePickerTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
                        col_date = dateFormat.format(calendar.getTime());
                        Paper.book().write("S_value",col_date);
                        Paper.book().write("order_col_date",col_date);

                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                datePickerDialog.show();

                layout_delivery.setVisibility(View.GONE);
                delivery_Date.setText("Delivery");
                del_date= String.valueOf(0);
                del_time= String.valueOf(0);
                Paper.book().write("order_del_date",del_date);
                Paper.book().write("order_del_time",del_time);
            }

            });
    }

    private void CartAPI(final String json) {




        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        devicetoken=FirebaseInstanceId.getInstance().getToken();
        service.CART_CALL(json).enqueue(new Callback<ModelBuyNow>() {
            @Override
            public void onResponse(Call<ModelBuyNow> call, Response<ModelBuyNow> response) {

                if(response.body().getStatusCode().equals(200)){

                    Integer orderid=response.body().getOrderid();
                    String string= String.valueOf(orderid);
                    final Dialog dialog= new Dialog(getActivity());
                    dialog.setContentView(R.layout.custom_dialog_order_success);
                    TextView order_id= dialog.findViewById(R.id.txt_order);
                    order_id.setText(string);
                    TextView limittext=dialog.findViewById(R.id.textlimit);

                    Paper.book().write("collection_id","");
                    Paper.book().write("delivery_address","");
                    Paper.book().write("cost_id","");

                    if(response.body().getLimitstatus().equals(0)){

                    limittext.setText("Your order has been successfully placed.");

                    }

                    else {

                     limittext.setText("The order has been sent to the supervisor for approval. ");

                    }

                    TextView total_price=dialog.findViewById(R.id.txtvw_total);

                    double value= Double.valueOf(myDb.getAllInc());

                    String data= String.format("%.2f", value);

                    String data_total = total.getText().toString();
                    if(Paper.book().read("permission_see_cost","2").equals("1")){

                        total_price.setText(data_total);
                    }

                    else if(Paper.book().read("permission_wholeseller", "5").equals("1")) {
                            total_price.setText(data_total);
                    }

                    else {

                        total_price.setText("0.00");

                    }

                    myDb.deleteAll();
                    getActivity().sendBroadcast(new Intent(DatabaseChangedReceiver.ACTION_DATABASE_CHANGED));

                    Button continue_shopping=dialog.findViewById(R.id.btn_continue);

                    continue_shopping.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            myDb.deleteAll();
                            transaction=getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerr, new Home_Categories());
                            transaction.commit();
                            getActivity().sendBroadcast(new Intent(DatabaseChangedReceiver.ACTION_DATABASE_CHANGED));
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                    dialog.setCancelable(false);


                }

                else

                {
                    Toast.makeText(getActivity(),response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ModelBuyNow> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public void jsonarray() {

        String userid= Paper.book().read("userid");
        String cost_id=Paper.book().read("cost_id");
        String supplier_id=Paper.book().read("supplier_id");
        String collection_id=Paper.book().read("collection_id");
        String delivery_address = Paper.book().read("delivery_address");
        custm_address = Paper.book().read("address");
        custm_address_city = Paper.book().read("city");
        custm_address_postcode = Paper.book().read("postcode");

        Log.e("deliveraddress",delivery_address+"");


        if (cost_id != null){
            cost_id=cost_id;
        }

        else {

            cost_id="0";

        }

        if (delivery_address != null){
            delivery_address=delivery_address;
        }

        else {

            delivery_address="0";

        }

        if (collection_id != null){
            collection_id=collection_id;
        }

        else {

            collection_id="0";

        }
/*
        String AddDelveryAddressFromApp =Paper.book().read("Project_Address");

        if (AddDelveryAddressFromApp.equals("1")){

            project_id="0";

        }

        else {

            project_id="0";
            custm_address="0";
            custm_address_city="0";
            custm_address_postcode="0";
        }*/

        String data_total = total.getText().toString();

            for(int i=0;i<cartMenuList.size();i++) {

            String total= String.valueOf(Double.valueOf(cartMenuList.get(i).getPrice()));
            double value= Double.valueOf(total);
            String data= String.format("%.2f", value);

            JSONObject order1 = new JSONObject();

            try {

                order1.put("delivery_instruction",del_address.getText().toString().trim());
                order1.put("price",data);
                order1.put("user_id", userid);
                order1.put("variation_id",cartMenuList.get(i).getVariationid());
                order1.put("product_id", cartMenuList.get(i).getProductid());
                order1.put("quantity", cartMenuList.get(i).getQuantity());

                order1.put("supplier_id",supplier_id);
                order1.put("Selected_collection_cost_id",collection_id);
                order1.put("Selected_delevry_cost_id",cost_id);
                order1.put("Selected_delevry_id",delivery_address);
                order1.put("businessidlogo","pick");
                order1.put("procode",cartMenuList.get(i).getAdd_product_code());
                order1.put("procode_title_new",cartMenuList.get(i).getProductname());
                order1.put("order_desc",edtxt_order_desc.getText().toString().trim());


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            jsonArray.put(order1);

            }

            JSONObject orderobj = new JSONObject();
            try {
                orderobj.put("Orderdetails", jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String cart_string = orderobj.toString();

            Log.e("cart",cart_string+"");

            CartAPI(cart_string);
    }

    public void jsonarray_payment(String delivery_instruction) {

        String userid= Paper.book().read("userid");
        String order_desc=Paper.book().read("order_desc");
        String supplier_id=Paper.book().read("supplier_id");
       // String project_id=Paper.book().read("project_id");

        String final_address = Paper.book().read("project_id");

        custm_address = Paper.book().read("address");
        custm_address_city = Paper.book().read("city");
        custm_address_postcode = Paper.book().read("postcode");

        String AddDelveryAddressFromApp =Paper.book().read("Project_Address");

        if (AddDelveryAddressFromApp.equals("1")){

            project_id="0";

        }

        else {

            project_id="0";
        }

        String data_total = total.getText().toString();


        for(int i=0;i<cartMenuList.size();i++) {

            String total= String.valueOf(Double.valueOf(cartMenuList.get(i).getPrice()));
            double value= Double.valueOf(total);
            String data= String.format("%.2f", value);

            Log.e("instruction",del_address.getText().toString().trim());

            JSONObject order1 = new JSONObject();

            try {

                order1.put("delivery_instruction",delivery_instruction);
                order1.put("price",data);
                order1.put("user_id", userid);
                order1.put("variation_id",cartMenuList.get(i).getVariationid());
                order1.put("product_id", cartMenuList.get(i).getProductid());
                order1.put("quantity", cartMenuList.get(i).getQuantity());
                order1.put("collection_time",col_time);
                order1.put("collection_date",col_date);
                order1.put("delivery_time",del_time);
                order1.put("delivery_Date",del_date);
                order1.put("order_desc",order_desc);
                order1.put("supplier_id",supplier_id);
                order1.put("project_id",project_id);
                order1.put("AddDelveryAddressFromApp",AddDelveryAddressFromApp);
                order1.put("deleveryaddressfromapp",custm_address);
                order1.put("cityfromapp",custm_address_city);
                order1.put("postcodefromapp",custm_address_postcode);
                order1.put("businessidlogo","pick");
                order1.put("procode",cartMenuList.get(i).getAdd_product_code());
                order1.put("procode_title_new",cartMenuList.get(i).getProductname());


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            jsonArray.put(order1);

        }

        JSONObject orderobj = new JSONObject();
        try {
            orderobj.put("Orderdetails", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String cart_string = orderobj.toString();

        Paper.book().write("Cart_Array_for_PAyment",cart_string);
        Paper.book().write("Cart_Payment",cart_string);
    }

    public void onAttach (Activity activity) {
        super.onAttach(activity);
        if(activity.getClass()== AddPayment.class){
            add_payment = (AddPayment) activity;
        }
    }

}
