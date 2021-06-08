package com.atradeya.asharani;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stripe.android.model.Card;
import com.stripe.android.view.CardInputWidget;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_Display_Card extends Fragment{

    databaseSqlite myDb;
    LinearLayout layout_btn_payment_check,layout_back_payment;
    CardInputWidget cardMultilineWidget;
    Button dialog_btn_add_card,dialog_btn_close_card;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    Home homes;
    List<SaveCard> savelist;
    List<SaveCard> mlist;
    Adapter_Cards adapter_cards;
    Button btn_add_card;
    String brand_type,card_number,cvc,save_status,exp_year,exp_month;
    //int exp_year,exp_month;
     //public AccountDetails accountDetails;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_card, container, false);
        myDb=new databaseSqlite(getActivity());
       // accountDetails = new AccountDetails();

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    getFragmentManager().popBackStack("account", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    /*accountDetails.change_password.setTextColor(getResources().getColor(R.color.themeblack));
                    accountDetails.txt_busniess_name.setTextColor(getResources().getColor(R.color.themeblack));
                    accountDetails.txt_billing_details.setTextColor(getResources().getColor(R.color.themeblack));
                    accountDetails.txt_default_delivery_address.setTextColor(getResources().getColor(R.color.themeblack));
                    accountDetails.txt_add_card.setTextColor(getResources().getColor(R.color.themered));*/

                    return true;
                }




                return false;

            }
        });

        recyclerView=view.findViewById(R.id.recycler_add_card);

        btn_add_card=view.findViewById(R.id.btn_add_card);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
       // homes.nav_search_layout.setVisibility(View.GONE);
        layout_btn_payment_check=view.findViewById(R.id.layout_btn_payment_check);
        layout_back_payment=view.findViewById(R.id.layout_back_payment);
        mlist=new ArrayList<SaveCard>();
        ((Home) getActivity()).hideView(true);
        homes.nav_search_layout.setVisibility(View.GONE);

        getAddedCards();
/*

        layout_back_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().write("pay_Card_status","0");
                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("account").replace(R.id.containerr,new AccountDetails());
                fragmentTransaction.commit();

            }
        });
*/

        layout_btn_payment_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pay_card=Paper.book().read("pay_Card_status");

                if(myDb.getNotesCount()!=0){

                    if(pay_card.equals("1")){

                       // ((Home)getActivity()).hideView(false);
                        FragmentTransaction transaction2=getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("home_category").replace(R.id.containerr, new Cart_Menu());
                        transaction2.commit();
                    }

                    else {

                        Toast.makeText(getActivity(),"Select Card", Toast.LENGTH_SHORT).show();
                    }


                }

                else{

                    Toast.makeText(getActivity(),"Add Products to the cart", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_add_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog= new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_add_card);
                cardMultilineWidget = dialog.findViewById(R.id.card_input_widget_dialog);
                dialog_btn_add_card=dialog.findViewById(R.id.dialog_btn_add_card);
                dialog_btn_close_card=dialog.findViewById(R.id.dialog_btn_close_card);
                dialog.show();

                dialog_btn_close_card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });
                dialog_btn_add_card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        saveCard();

                        Card card =  cardMultilineWidget.getCard();

                        if(card == null){

                            Toast.makeText(getActivity(),"Invalid card", Toast.LENGTH_SHORT).show();

                        }else {
                            if (!card.validateCard()) {
                                // Do not continue token creation.
                                Toast.makeText(getActivity(), "Invalid card", Toast.LENGTH_SHORT).show();
                            } else {

                                addcard();

                                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("account").replace(R.id.containerr,new AccountDetails());
                                fragmentTransaction.commit();
                            }
                        }

                       dialog.dismiss();



                    }
                });
            }
        });



        return view;
    }

    private void saveCard() {

        Card card =  cardMultilineWidget.getCard();
        if(card == null){
            Toast.makeText(getActivity(),"Invalid card", Toast.LENGTH_SHORT).show();

        }else {
            if (!card.validateCard()) {
                // Do not continue token creation.
                Toast.makeText(getActivity(), "Invalid card", Toast.LENGTH_SHORT).show();
            } else {

                card_number=card.getNumber();
                cvc=card.getCVC();
                exp_month= String.valueOf(card.getExpMonth());
                exp_year= String.valueOf(card.getExpYear());
                brand_type=card.getBrand();

            }
        }
    }

    private void addcard() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        String userid= Paper.book().read("userid");

        service.ADD_CARD_CALL(userid,card_number,brand_type,exp_month,exp_year).enqueue(new Callback<ModelAddCard>() {
            @Override
            public void onResponse(Call<ModelAddCard> call, Response<ModelAddCard> response) {

                Toast.makeText(getActivity(),response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ModelAddCard> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });


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

                    adapter_cards=new Adapter_Cards(getActivity(),mlist);
                    recyclerView.setAdapter(adapter_cards);
                }

            }

            @Override
            public void onFailure(Call<ModelAddedCards> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity.getClass() == Home.class) {
            homes = (Home) activity;
        }
    }
}
