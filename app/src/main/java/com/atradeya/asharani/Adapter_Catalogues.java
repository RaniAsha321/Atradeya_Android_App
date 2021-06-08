package com.atradeya.asharani;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class Adapter_Catalogues extends RecyclerView.Adapter<Adapter_Catalogues.ViewHolder> {

    Context mcontext;
    List<Datum> mlist;
    String pdf_url;

    public Adapter_Catalogues(Context context, List<Datum> mlist) {

        this.mcontext=context;
        this.mlist=mlist;
    }


    @NonNull
    @Override
    public Adapter_Catalogues.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_catalogues, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Catalogues.ViewHolder viewHolder, int i) {
        pdf_url=mlist.get(i).getUrl();
        viewHolder.pdf_Name.setText(mlist.get(i).getName());
        viewHolder.pdf_date.setText(mlist.get(i).getDate());
        viewHolder.pdf_size.setText(mlist.get(i).getSize());

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullPath = String.format(Locale.ENGLISH, pdf_url, "PDF_URL_HERE");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fullPath));
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(browserIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView pdf_Image;
        TextView pdf_Name,pdf_date,pdf_size;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pdf_size=itemView.findViewById(R.id.txt_size);
            pdf_date=itemView.findViewById(R.id.txt_date);
            pdf_Image=itemView.findViewById(R.id.imgview_pdf);
            pdf_Name=itemView.findViewById(R.id.txt_catalogue);
            cardView=itemView.findViewById(R.id.catalogue_cardview);
        }

    }
}


