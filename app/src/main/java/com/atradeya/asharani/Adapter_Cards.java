package com.atradeya.asharani;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Adapter_Cards extends RecyclerView.Adapter<Adapter_Cards.Viewholder> {

    Context context;
    List<SaveCard> mlist;
    int selectedPosition=-1;
    int selected_position = -1;


    public Adapter_Cards(Context context, List<SaveCard> mlist) {

        this.context=context;
        this.mlist=mlist;
    }

    @NonNull
    @Override
    public Adapter_Cards.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_saved_cards, parent, false);

        return new Viewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Adapter_Cards.Viewholder holder, int position) {

        if(selectedPosition!=position){
            holder.imageView_select_card.setVisibility(View.INVISIBLE);
        }

        else{
            holder.imageView_select_card.setVisibility(View.VISIBLE);

            Paper.book().write("pay_Card_status","1");
            Paper.book().write("pay_card_number",mlist.get(position).getCardnumber());
            Paper.book().write("pay_exp_month",mlist.get(position).getExpDate());
            Paper.book().write("pay_exp_year",mlist.get(position).getExpYear());
            Paper.book().write("pay_brand",mlist.get(position).getCardType());

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=position;
                notifyDataSetChanged();

            }
        });

        String lastFourDigits =  mlist.get(position).getCardnumber().substring( mlist.get(position).getCardnumber().length() - 4);

        holder.credit_card_text.setText(mlist.get(position).getCardType()+" card ending with "+lastFourDigits);

        holder.layout_remove_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeleteCard(mlist.get(position).getId());
                mlist.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    private void DeleteCard(String id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        service.REMOVE_CARD_CALL(id).enqueue(new Callback<ModelRemoveCard>() {
            @Override
            public void onResponse(Call<ModelRemoveCard> call, Response<ModelRemoveCard> response) {
                if(response.body().getStatusCode().equals(200)){

                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ModelRemoveCard> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        CardView cardView;
        LinearLayout layout_remove_card;
        TextView credit_card_text;
        ImageView imageView_select_card;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            layout_remove_card=itemView.findViewById(R.id.layout_remove_card);
            credit_card_text=itemView.findViewById(R.id.tv_credit_card_text);
            imageView_select_card=itemView.findViewById(R.id.imageView_select_card);
            cardView=itemView.findViewById(R.id.cardview_card);
        }

        @Override
        public void onClick(View v) {

            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

            notifyItemChanged(selected_position);
            selected_position = getAdapterPosition();
            notifyItemChanged(selected_position);

        }
    }
}
