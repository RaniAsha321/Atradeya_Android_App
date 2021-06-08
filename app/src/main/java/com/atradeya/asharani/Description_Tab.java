package com.atradeya.asharani;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import io.paperdb.Paper;

public class Description_Tab extends Fragment {

    public static TextView description;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_description__tab, container, false);
        description=view.findViewById(R.id.fg_description);
        String desc= Paper.book().read("product_description");
        description.setText(Html.fromHtml(desc));
        return view;
    }
}
