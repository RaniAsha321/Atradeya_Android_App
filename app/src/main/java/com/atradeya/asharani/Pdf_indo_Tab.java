package com.atradeya.asharani;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Locale;

import io.paperdb.Paper;

public class Pdf_indo_Tab extends Fragment {

    TextView pdf1,pdf2,pdf3,pdf4,pdf5,pdf6;
    LinearLayout pdf_lay1,pdf_lay2,pdf_lay3,pdf_lay4,pdf_lay5,pdf_lay6;


    String pdf11,pdf22,pdf33,pdf44,pdf55,pdf66,title1,title2,title3,title4,title5,title6;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_pdf_info__tab, container, false);

        pdf_lay1=view.findViewById(R.id.pdf_lay1);
        pdf_lay2=view.findViewById(R.id.pdf_lay2);
        pdf_lay3=view.findViewById(R.id.pdf_lay3);
        pdf_lay4=view.findViewById(R.id.pdf_lay4);
        pdf_lay5=view.findViewById(R.id.pdf_lay5);
        pdf_lay6=view.findViewById(R.id.pdf_lay6);
        pdf1=view.findViewById(R.id.pdf_1);
        pdf2=view.findViewById(R.id.pdf_2);
        pdf3=view.findViewById(R.id.pdf_3);
        pdf4=view.findViewById(R.id.pdf_4);
        pdf5=view.findViewById(R.id.pdf_5);
        pdf6=view.findViewById(R.id.pdf_6);

        pdf11= Paper.book().read("pdf1");
        pdf22= Paper.book().read("pdf2");
        pdf33= Paper.book().read("pdf3");
        pdf44= Paper.book().read("pdf4");
        pdf55= Paper.book().read("pdf5");
        pdf66= Paper.book().read("pdf6");

        title1= Paper.book().read("pdf_name1");
        title2=Paper.book().read("pdf_name2");
        title3= Paper.book().read("pdf_name3");
        title4=Paper.book().read("pdf_name4");
        title5= Paper.book().read("pdf_name5");
        title6=Paper.book().read("pdf_name6");

        pdf1.setPaintFlags(pdf1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        pdf2.setPaintFlags(pdf2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        pdf3.setPaintFlags(pdf3.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        pdf4.setPaintFlags(pdf4.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        pdf5.setPaintFlags(pdf5.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        pdf6.setPaintFlags(pdf6.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        if (pdf11.equals("")){

            pdf_lay1.setVisibility(View.GONE);
        }

        else {

            pdf1.setText(title1);
        }

        if (pdf22.equals("")){

            pdf_lay2.setVisibility(View.GONE);
        }

        else {

            pdf2.setText(title2);
        }

        if (pdf33.equals("")){

            pdf_lay3.setVisibility(View.GONE);
        }

        else {

            pdf3.setText(title3);
        }

        if (pdf44.equals("")){

            pdf_lay4.setVisibility(View.GONE);
        }

        else {

            pdf4.setText(title4);
        }

        if (pdf55.equals("")){

            pdf_lay5.setVisibility(View.GONE);
        }

        else {

            pdf5.setText(title5);
        }

        if (pdf66.equals("")){

            pdf_lay6.setVisibility(View.GONE);
        }

        else {

            pdf6.setText(title6);
        }

        pdf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pdf1.setTextColor(getResources().getColor(R.color.bluetheme));
                pdf2.setTextColor(getResources().getColor(R.color.themeblack));
                pdf3.setTextColor(getResources().getColor(R.color.themeblack));
                pdf4.setTextColor(getResources().getColor(R.color.themeblack));
                pdf5.setTextColor(getResources().getColor(R.color.themeblack));
                pdf6.setTextColor(getResources().getColor(R.color.themeblack));

                String fullPath = String.format(Locale.ENGLISH, pdf11, "PDF_URL_HERE");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fullPath));
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(browserIntent);

            }
        });

        pdf2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pdf1.setTextColor(getResources().getColor(R.color.themeblack));
                pdf2.setTextColor(getResources().getColor(R.color.bluetheme));
                pdf3.setTextColor(getResources().getColor(R.color.themeblack));
                pdf4.setTextColor(getResources().getColor(R.color.themeblack));
                pdf5.setTextColor(getResources().getColor(R.color.themeblack));
                pdf6.setTextColor(getResources().getColor(R.color.themeblack));


                String fullPath = String.format(Locale.ENGLISH, pdf22, "PDF_URL_HERE");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fullPath));
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(browserIntent);

            }
        });

        pdf3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pdf3.setTextColor(getResources().getColor(R.color.bluetheme));
                pdf1.setTextColor(getResources().getColor(R.color.themeblack));
                pdf2.setTextColor(getResources().getColor(R.color.themeblack));
                pdf4.setTextColor(getResources().getColor(R.color.themeblack));
                pdf5.setTextColor(getResources().getColor(R.color.themeblack));
                pdf6.setTextColor(getResources().getColor(R.color.themeblack));

                String fullPath = String.format(Locale.ENGLISH, pdf33, "PDF_URL_HERE");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fullPath));
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(browserIntent);

            }
        });

        pdf4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pdf4.setTextColor(getResources().getColor(R.color.bluetheme));
                pdf3.setTextColor(getResources().getColor(R.color.themeblack));
                pdf1.setTextColor(getResources().getColor(R.color.themeblack));
                pdf2.setTextColor(getResources().getColor(R.color.themeblack));
                pdf5.setTextColor(getResources().getColor(R.color.themeblack));
                pdf6.setTextColor(getResources().getColor(R.color.themeblack));


                String fullPath = String.format(Locale.ENGLISH, pdf44, "PDF_URL_HERE");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fullPath));
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(browserIntent);

            }
        });

        pdf5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pdf5.setTextColor(getResources().getColor(R.color.bluetheme));
                pdf3.setTextColor(getResources().getColor(R.color.themeblack));
                pdf1.setTextColor(getResources().getColor(R.color.themeblack));
                pdf2.setTextColor(getResources().getColor(R.color.themeblack));
                pdf4.setTextColor(getResources().getColor(R.color.themeblack));
                pdf6.setTextColor(getResources().getColor(R.color.themeblack));

                String fullPath = String.format(Locale.ENGLISH, pdf55, "PDF_URL_HERE");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fullPath));
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(browserIntent);

            }
        });

        pdf6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pdf6.setTextColor(getResources().getColor(R.color.bluetheme));
                pdf3.setTextColor(getResources().getColor(R.color.themeblack));
                pdf1.setTextColor(getResources().getColor(R.color.themeblack));
                pdf2.setTextColor(getResources().getColor(R.color.themeblack));
                pdf4.setTextColor(getResources().getColor(R.color.themeblack));
                pdf5.setTextColor(getResources().getColor(R.color.themeblack));

                String fullPath = String.format(Locale.ENGLISH, pdf66, "PDF_URL_HERE");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fullPath));
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(browserIntent);

            }
        });

        return view;
    }

}
