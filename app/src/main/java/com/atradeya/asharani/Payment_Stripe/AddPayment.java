package com.atradeya.asharani.Payment_Stripe;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.atradeya.asharani.Adapter_Saved_Cards;
import com.atradeya.asharani.ApiLogin_Interface;
import com.atradeya.asharani.DatabaseChangedReceiver;
import com.atradeya.asharani.Database_Card_Save;
import com.atradeya.asharani.Home;
import com.atradeya.asharani.ModelAddedCards;
import com.atradeya.asharani.ModelCartMenu;
import com.atradeya.asharani.ModelPayment;
import com.atradeya.asharani.Model_Card_Details_Saved;
import com.atradeya.asharani.R;
import com.atradeya.asharani.SaveCard;
import com.atradeya.asharani.databaseSqlite;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;
import org.json.JSONArray;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import io.paperdb.Paper;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.atradeya.asharani.Next_Login_Page.MY_PREFS_NAME;

public class AddPayment extends AppCompatActivity implements Adapter_Saved_Cards.OnNoteListener {

    String wholeseller;
    Dialog dialog;
    Button btn_close_cvv_dialog,btn_continue_cvv_dialog,btn_Saved_cards;
    CardInputWidget cardMultilineWidget;
    public Button final_payment;
    TextView txtvw_total_payment;
    databaseSqlite myDb;
    ProgressDialog progressDialogs;
    CheckBox checkBox;
    List<Model_Card_Details_Saved> list;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView_card;
    Adapter_Saved_Cards adapterSavedCards;
    LinearLayout mainlayout;
    List<ModelCartMenu> cartMenuList;
    Database_Card_Save db;
    JSONArray jsonArray;
    String brand_type,card_number,cvc,save_status,stripe_publish_key,stripe_publish_key1;
    int exp_year,exp_month;
    EditText edtxt_cvv_dialog;
    List<SaveCard> savelist;
    List<SaveCard> mlist;
    SharedPreferences sharedPreferences;
    Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);
        myDb=new databaseSqlite(getApplicationContext());
        checkBox=findViewById(R.id.checkBox);
        txtvw_total_payment=findViewById(R.id.txtvw_total_payment);
        list=new ArrayList<>();
        cardMultilineWidget = findViewById(R.id.card_input_widget);
        final_payment =  findViewById(R.id.final_payment);
        btn_Saved_cards=findViewById(R.id.saved_cards);
        recyclerView_card=findViewById(R.id.recyclerview_add_payment);
        db = new Database_Card_Save(getApplicationContext());
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView_card.setLayoutManager(layoutManager);
        cartMenuList=myDb.getAllModelCartMenu();
        jsonArray = new JSONArray();
        mlist=new ArrayList<>();
        mainlayout=findViewById(R.id.mainlayout);

        String pay_Card_status=Paper.book().read("pay_Card_status");

        getAddedCards();

        if(pay_Card_status.equals("1")){

            card_number=Paper.book().read("pay_card_number");
            String pay_exp_month=Paper.book().read("pay_exp_month");
            String pay_exp_year=Paper.book().read("pay_exp_year");
            brand_type=Paper.book().read("pay_brand");
            exp_month= Integer.parseInt(pay_exp_month);
            exp_year= Integer.parseInt(pay_exp_year);

            dialog= new Dialog(AddPayment.this);
            dialog.setContentView(R.layout.custom_dialog_enter_cvc);
            btn_continue_cvv_dialog=dialog.findViewById(R.id.btn_continue_cvv_dialog);
            btn_close_cvv_dialog=dialog.findViewById(R.id.btn_close_cvv_dialog);
            edtxt_cvv_dialog=dialog.findViewById(R.id.edtxt_cvv_dialog);

            card=Card.create(card_number,exp_month,exp_year,edtxt_cvv_dialog.getText().toString());


            btn_continue_cvv_dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                    progressDialogs = new ProgressDialog(AddPayment.this,R.style.AlertDialogCustom);
                    progressDialogs.setCancelable(false);
                    progressDialogs.setMessage("Please Wait.......");
                    progressDialogs.show();

                    save_status="0";
                    CreateToken(card);


                }
            });

            btn_close_cvv_dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });

            dialog.show();

        }

        final_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkBox.isChecked()){
                    save_status="1";
                    saveCard();
                }

                else {
                    save_status="0";
                    saveCard();
                }



            }
        });


        double value=Double.valueOf(myDb.getAllInc());

        String data= String.format("%.2f", value);

        if(Paper.book().read("permission_see_cost","2").equals("1")){

            txtvw_total_payment.setText(data);
        }

        else if(Paper.book().read("permission_wholeseller", "5").equals("1")) {
            txtvw_total_payment.setText(data);
        }

        else {

            txtvw_total_payment.setText("0.00");

        }

    }


    private void getAddedCards() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        String userid= Paper.book().read("userid");

        service.MODEL_ADDED_CARDS_CALL(userid).enqueue(new Callback<ModelAddedCards>() {
            @Override
            public void onResponse(Call<ModelAddedCards> call, Response<ModelAddedCards> response) {
                if(response.body().getStatusCode().equals(200)){

                    savelist=response.body().getSaveCards();

                    for(int i=0;i<savelist.size();i++){

                        SaveCard datum1=new SaveCard();
                        datum1.setId(response.body().getSaveCards().get(i).getId());
                        datum1.setCardnumber(response.body().getSaveCards().get(i).getCardnumber());
                        datum1.setCardType(response.body().getSaveCards().get(i).getCardType());
                        datum1.setExpYear(response.body().getSaveCards().get(i).getExpYear());
                        datum1.setExpDate(response.body().getSaveCards().get(i).getExpDate());

                        mlist.add(datum1);

                    }

                    adapterSavedCards=new Adapter_Saved_Cards(getApplicationContext(),mlist);
                    recyclerView_card.setAdapter(adapterSavedCards);
                }

            }

            @Override
            public void onFailure(Call<ModelAddedCards> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public void saveCard() {

        progressDialogs = new ProgressDialog(AddPayment.this,R.style.AlertDialogCustom);
        progressDialogs.setCancelable(false);
        progressDialogs.setMessage("Please Wait.......");
        progressDialogs.show();

        Card card =  cardMultilineWidget.getCard();

        Paper.book().write("cardinput","2");
        if(card == null){

           Toast.makeText(getApplicationContext(),"Invalid card", Toast.LENGTH_SHORT).show();

           progressDialogs.dismiss();
        }else {
           if (!card.validateCard()) {
               // Do not continue token creation.
               Toast.makeText(getApplicationContext(), "Invalid card", Toast.LENGTH_SHORT).show();
           } else {
               CreateToken(card);

               card_number=card.getNumber();
               exp_month=card.getExpMonth();
               exp_year=card.getExpYear();
               cvc=card.getCVC();
               brand_type=card.getBrand();

           }
       }

    }

    public void CreateToken(Card card) {

        String dat_pub_key=Paper.book().read("stripe_publish_key");
        sharedPreferences =getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String publishkey=sharedPreferences.getString("stripe_publish_key", "");

        if(!(publishkey.equals("0"))){

            stripe_publish_key=publishkey;

        }


        else if((Paper.book().read("stripe_publish_key_cart")) != null){

            if((Paper.book().read("stripe_publish_key_cart")).equals("0")){

                stripe_publish_key="pk_live_b22NBcP6UKGApD5jMAHsQDAE";
                Paper.book().write("wholeseller_business_id","0");
            }

            else {
                stripe_publish_key=Paper.book().read("stripe_publish_key_cart");
            }

        }

        else if(dat_pub_key.equals("0")){

            stripe_publish_key="pk_live_b22NBcP6UKGApD5jMAHsQDAE";
            Paper.book().write("wholeseller_business_id","0");

        }

        else{

            stripe_publish_key=Paper.book().read("stripe_publish_key");

        }


        Stripe stripe = new Stripe(getApplicationContext(),stripe_publish_key);
        stripe.createToken(
                card,
                new TokenCallback() {
                    public void onSuccess(Token token) {

                        // Send token to your server

                        Intent intent = new Intent();
                        intent.putExtra("card",token.getCard().getLast4());
                        intent.putExtra("stripe_token",token.getId());
                        intent.putExtra("cardtype",token.getCard().getBrand());
                        intent.putExtra("card_number",token.getCard().getNumber());
                        intent.putExtra("exp_month",token.getCard().getExpMonth());
                        intent.putExtra("exp_year",token.getCard().getExpYear());
                        intent.putExtra("cvc",token.getCard().getCVC());

                        Paper.book().write("card_token",token.getId());


                        double value=Double.valueOf(myDb.getAllInc());
                        value=value*100;
                        DecimalFormat final_price = new DecimalFormat("0.#");
                        int final_price_data= Integer.parseInt(final_price.format(value));
                        Paper.book().write("final_price_data",final_price_data);

                        getPayment(token.getId(),final_price_data);


                    }
                    public void onError(Exception error) {

                        // Show localized error message
                        Toast.makeText(getApplicationContext(),
                                error.getLocalizedMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }

    private void getPayment(String pay_token, int data) {

        String Cart_Array_for_PAyment=Paper.book().read("Cart_Array_for_PAyment");
        String Cart_Payment=Paper.book().read("Cart_Payment");

        String WholsalerBusinessId=Paper.book().read("save_business");
        if(WholsalerBusinessId !=null){

            wholeseller=WholsalerBusinessId;

        }

        else {

            wholeseller="0";
        }

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
        final ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        service.PAYMENT_CALL(data,pay_token,"gbp","Payment Test",Cart_Array_for_PAyment,card_number,exp_month,exp_year,brand_type,cvc,save_status,wholeseller).enqueue(new Callback<ModelPayment>() {
            @Override
            public void onResponse(Call<ModelPayment> call, Response<ModelPayment> response) {

                if (response.body().getStatusCode().equals(200)) {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("stripe_publish_key", "0");
                    editor.apply();
                    Paper.book().write("wholeseller_business_id","");
                    Paper.book().write("save_business","");
                    Integer orderid=response.body().getOrderid();
                    String string=String.valueOf(orderid);
                    Dialog dialog= new Dialog(AddPayment.this);
                    dialog.setContentView(R.layout.custom_dialog_order_success);
                    TextView order_id= dialog.findViewById(R.id.txt_order);
                    LinearLayout layout_transaction_id=dialog.findViewById(R.id.layout_transaction_id);
                    TextView txt_transaction_id= dialog.findViewById(R.id.txt_transaction_id);

                    order_id.setText(string);
                    TextView limittext=dialog.findViewById(R.id.textlimit);

                    limittext.setText("Your order has been successfully placed.");

                    TextView total_price=dialog.findViewById(R.id.txtvw_total);
                    double value=Double.valueOf(myDb.getAllInc());
                    String data= String.format("%.2f", value);
                    layout_transaction_id.setVisibility(View.VISIBLE);
                    txt_transaction_id.setText(response.body().getTransactionId());

                    if(Paper.book().read("permission_see_cost","2").equals("1")){

                        total_price.setText(data);
                    }

                    else if(Paper.book().read("permission_wholeseller", "5").equals("1")) {

                        layout_transaction_id.setVisibility(View.VISIBLE);
                        total_price.setText(data);
                        txt_transaction_id.setText(response.body().getTransactionId());
                    }

                    else {

                        total_price.setText("0.00");

                    }

                    myDb.deleteAll();
                    getApplicationContext().sendBroadcast(new Intent(DatabaseChangedReceiver.ACTION_DATABASE_CHANGED));

                    Button continue_shopping=dialog.findViewById(R.id.btn_continue);

                    continue_shopping.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            myDb.deleteAll();

                            Intent intent=new Intent(getApplicationContext(), Home.class);
                            startActivity(intent);

                            getApplicationContext().sendBroadcast(new Intent(DatabaseChangedReceiver.ACTION_DATABASE_CHANGED));
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }

                else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(),Toast.LENGTH_LONG).show();

                }

                progressDialogs.dismiss();
            }

            @Override
            public void onFailure(Call<ModelPayment> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                progressDialogs.dismiss();

            }
        });
    }


    @Override
    public void noteOnClick(int position) {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Paper.book().write("pay_Card_status","0");
    }
}
