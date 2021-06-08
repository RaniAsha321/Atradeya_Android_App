package com.atradeya.asharani;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.atradeya.asharani.Payment_Stripe.AddPayment;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

import static android.content.Context.MODE_PRIVATE;
import static com.atradeya.asharani.Next_Login_Page.MY_PREFS_NAME;

public class Adapter_Saved_Cards  extends RecyclerView.Adapter<Adapter_Saved_Cards.Viewholder> {
    Context mcontext;
    private final ArrayList<Integer> selected = new ArrayList<>();
    List<Model_Card_Details_Saved> cardlist;
    Database_Card_Save database_card_save;
    Card card;
    List<SaveCard> mlist;
    private static int lastCheckedPos = 0;
    OnNoteListener onNoteListener;
    OnItemClickListener mItemClickListener;
    Button btn_pay;
    int selected_position = -1;
    ProgressDialog progressDialogs;
    AddPayment addPayment;
    databaseSqlite myDb;
    String brand_type,card_number,cvc,save_status,stripe_publish_key,wholeseller;
    int exp_year,exp_month;
    List<ModelCartMenu> cartMenuList;
    JSONArray jsonArray;
    int position_cvv;


    public Adapter_Saved_Cards(Context context, List<SaveCard> mlist) {

        this.mcontext=context;
        this.mlist=mlist;

        addPayment=new AddPayment();
        myDb=new databaseSqlite(mcontext);
        cartMenuList=myDb.getAllModelCartMenu();
        jsonArray = new JSONArray();
    }


    public interface OnItemClickListener {
    }


    @NonNull
    @Override
    public Adapter_Saved_Cards.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_saved_cards, parent, false);

        return new Viewholder(view,onNoteListener);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Adapter_Saved_Cards.Viewholder holder,final int position) {

        holder.layout_remove_card.setVisibility(View.GONE);

       if(position==1){

           holder.imageView_select_card.setVisibility(View.INVISIBLE);
       }

       if(selected_position==position){

           position_cvv=position;

           holder.imageView_select_card.setVisibility(View.VISIBLE);


       }
       else {
           holder.imageView_select_card.setVisibility(View.INVISIBLE);
       }

        String lastFourDigits =  mlist.get(position).getCardnumber().substring( mlist.get(position).getCardnumber().length() - 4);
            holder.credit_card_text.setText(mlist.get(position).getCardType()+" card ending with "+lastFourDigits);

    }


    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnNoteListener onNoteListener;
        CardView cardView;
        LinearLayout layout_remove_card;
        TextView credit_card_text;
        ImageView imageView_select_card;
        Dialog dialog;
        Button btn_close_cvv_dialog,btn_continue_cvv_dialog,btn_Saved_cards;
        EditText edtxt_cvv_dialog;

        public Viewholder(@NonNull View itemView , OnNoteListener onNoteListener) {
            super(itemView);
            layout_remove_card=itemView.findViewById(R.id.layout_remove_card);
            credit_card_text=itemView.findViewById(R.id.tv_credit_card_text);
            imageView_select_card=itemView.findViewById(R.id.imageView_select_card);
            cardView=itemView.findViewById(R.id.cardview_card);
            itemView.setOnClickListener(this);
            dialog=new Dialog(mcontext);

            this.onNoteListener=onNoteListener;
        }

        @Override
        public void onClick(View v) {

            card_number=mlist.get(position_cvv).getCardnumber();
            String pay_exp_month=mlist.get(position_cvv).getExpDate();
            String pay_exp_year=mlist.get(position_cvv).getExpYear();
            brand_type=mlist.get(position_cvv).getCardType();
            exp_month= Integer.parseInt(pay_exp_month);
            exp_year= Integer.parseInt(pay_exp_year);

            dialog= new Dialog(v.getContext());
            dialog.setContentView(R.layout.custom_dialog_enter_cvc);
            btn_continue_cvv_dialog=dialog.findViewById(R.id.btn_continue_cvv_dialog);
            btn_close_cvv_dialog=dialog.findViewById(R.id.btn_close_cvv_dialog);
            edtxt_cvv_dialog=dialog.findViewById(R.id.edtxt_cvv_dialog);

            cvc=edtxt_cvv_dialog.getText().toString();

            card=Card.create(card_number,exp_month,exp_year,edtxt_cvv_dialog.getText().toString());

            btn_continue_cvv_dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!TextUtils.isEmpty(edtxt_cvv_dialog.getText().toString())){
                        dialog.dismiss();
                        progressDialogs = new ProgressDialog(v.getContext(),R.style.AlertDialogCustom);
                        progressDialogs.setCancelable(false);
                        progressDialogs.setMessage("Please Wait.......");
                        progressDialogs.show();

                        save_status="0";
                        CreateToken(card,v);
                    }
                    else {
                        Toast.makeText(v.getContext(),"Enter 3 digit CVV", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            btn_close_cvv_dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });

            dialog.show();


            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

            // Updating old as well as new positions
            notifyItemChanged(selected_position);
            selected_position = getAdapterPosition();
            notifyItemChanged(selected_position);

        }
    }

    public void CreateToken(Card card, View v) {

        SharedPreferences sharedPreferences =mcontext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String publishkey=sharedPreferences.getString("stripe_publish_key", "");

        if(!(publishkey.equals("0"))){

            stripe_publish_key=publishkey;

        }


        else  if(Paper.book().read("stripe_publish_key").equals("0")){

            stripe_publish_key="pk_live_b22NBcP6UKGApD5jMAHsQDAE";
            Paper.book().write("wholeseller_business_id","0");
        }

        else{

            stripe_publish_key=Paper.book().read("stripe_publish_key");
        }

        Stripe stripe = new Stripe(mcontext, stripe_publish_key);
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

                        Log.e("amount",token.getId()+"");
                        double value= Double.valueOf(myDb.getAllInc());
                        Log.e("amount",value+"");
                        value=value*100;
                        DecimalFormat final_price = new DecimalFormat("0.#");
                        int final_price_data= Integer.parseInt(final_price.format(value));
                        Paper.book().write("final_price_data",final_price_data);

                        jsonarray(v);
                    }
                    public void onError(Exception error) {

                        // Show localized error message
                        Toast.makeText(mcontext,
                                error.getLocalizedMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }

    private void getPayment(String pay_token, int data, View view) {
        String Cart_Array_for_PAyment=Paper.book().read("Cart_Array_for_PAyment");
        String Cart_Payment=Paper.book().read("Cart_Payment");
        String WholsalerBusinessId=Paper.book().read("save_business");
        SharedPreferences sharedPreferences =mcontext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);


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
                    String string= String.valueOf(orderid);
                    Dialog dialog= new Dialog(view.getContext());
                    dialog.setContentView(R.layout.custom_dialog_order_success);
                    TextView order_id= dialog.findViewById(R.id.txt_order);
                    LinearLayout layout_transaction_id=dialog.findViewById(R.id.layout_transaction_id);
                    TextView txt_transaction_id= dialog.findViewById(R.id.txt_transaction_id);

                    order_id.setText(string);
                    TextView limittext=dialog.findViewById(R.id.textlimit);

                    limittext.setText("Your order has been successfully placed.");

                    TextView total_price=dialog.findViewById(R.id.txtvw_total);

                    double value= Double.valueOf(myDb.getAllInc());

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
                    mcontext.sendBroadcast(new Intent(DatabaseChangedReceiver.ACTION_DATABASE_CHANGED));

                    Button continue_shopping=dialog.findViewById(R.id.btn_continue);

                    continue_shopping.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            myDb.deleteAll();

                            Intent intent=new Intent(v.getContext(), Home.class);
                            v.getContext().startActivity(intent);

                            v.getContext().sendBroadcast(new Intent(DatabaseChangedReceiver.ACTION_DATABASE_CHANGED));
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }

                else {
                    Toast.makeText(mcontext, response.body().getMessage(), Toast.LENGTH_LONG).show();

                }

                progressDialogs.dismiss();
            }

            @Override
            public void onFailure(Call<ModelPayment> call, Throwable t) {

                Toast.makeText(mcontext, t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                progressDialogs.dismiss();

            }
        });
    }

    public void jsonarray(View v) {

        String userid= Paper.book().read("userid");
        String order_desc=Paper.book().read("order_desc");
        String supplier_id=Paper.book().read("supplier_id");
        String project_id=Paper.book().read("project_id");
        String del_address=Paper.book().read("order_del_address");
        String col_time=Paper.book().read("order_col_time");
        String col_date=Paper.book().read("order_col_date");
        String del_time=Paper.book().read("order_del_time");
        String del_date=Paper.book().read("order_del_date");
        String card_token=Paper.book().read("card_token");
        int final_price_data=Paper.book().read("final_price_data");

        for(int i=0;i<cartMenuList.size();i++) {

            String total= String.valueOf(Double.valueOf(cartMenuList.get(i).getPrice()));
            double value= Double.valueOf(total);
            String data= String.format("%.2f", value);

            JSONObject order1 = new JSONObject();

            try {

                order1.put("delivery_instruction",del_address);
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
        Paper.book().write("Cart_Payment",cart_string);

        getPayment(card_token,final_price_data,v);
    }


    public interface  OnNoteListener{

        void noteOnClick(int position);

    }

}
