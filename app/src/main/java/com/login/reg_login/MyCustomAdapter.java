package com.login.reg_login;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jack on 9/24/2017.
 */

public class MyCustomAdapter extends ArrayAdapter<Person> implements View.OnClickListener {
    private Context mcontext;
    private ArrayList<Person> data;
    CheckBox checkBox;
    public MyCustomAdapter(Context context, @LayoutRes int resource, ArrayList<Person> object) {
        super(context, resource, object);
        this.mcontext=context;
        this.data=object;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View mconvertview=convertView;
        if (mconvertview==null){
            LayoutInflater from = LayoutInflater.from(getContext());
            mconvertview = from.inflate(R.layout.custom, null);
        }
        Person person = data.get(position);
        TextView tvdisplayname=mconvertview.findViewById(R.id.tvNameDisplay);
        TextView tvdsplayad=mconvertview.findViewById(R.id.tvAddressDisplay);
        TextView tvname=(TextView) mconvertview.findViewById(R.id.tvName);
        TextView tvaddress=(TextView) mconvertview.findViewById(R.id.tvAddress);
        Button btnemail=mconvertview.findViewById(R.id.btnMail);
        Button btnDownload = mconvertview.findViewById(R.id.btnDownload);
         checkBox=(CheckBox)mconvertview.findViewById(R.id.checkBox);
        TextView tvOnFavourate=mconvertview.findViewById(R.id.tvOnFavourate);
        ImageView ivbook=mconvertview.findViewById(R.id.ivbook);
        tvdisplayname.setText(""+person.getName());
        tvdsplayad.setText(""+person.getAddress());
        ivbook.setImageResource(R.drawable.book_image);
        btnemail.setOnClickListener(this);
        btnDownload.setOnClickListener(this);
        checkBox.setOnClickListener(this);
        return mconvertview;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMail:
                break;
            case R.id.btnDownload:
                break;
            case R.id.checkBox:

                break;
        }
    }
}


