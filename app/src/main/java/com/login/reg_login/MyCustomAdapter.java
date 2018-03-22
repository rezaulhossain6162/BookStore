package com.login.reg_login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jack on 9/24/2017.
 */

public class MyCustomAdapter extends BaseAdapter implements Filterable {
    private Context mcontext;
    private ArrayList<Person> dataOrginal,dataTemp;
    Person person;
    customfilter cs;
    public MyCustomAdapter(Context context, @LayoutRes int resource, ArrayList<Person> object) {
        this.mcontext=context;
        this.dataOrginal=object;
        this.dataTemp=object;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View mconvertview=convertView;
        if (mconvertview==null){
           LayoutInflater inflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mconvertview = inflater.inflate(R.layout.custom, null);
        }
         person = dataOrginal.get(position);
        TextView tvdisplayname=mconvertview.findViewById(R.id.tvAddressDisplay);
        TextView tvdsplayad=mconvertview.findViewById(R.id.tvNameDisplay);
        Button btnShare=mconvertview.findViewById(R.id.btnShare);
        Button someClick =mconvertview.findViewById(R.id.someClick);
        Button btnDownload=mconvertview.findViewById(R.id.btnDownload);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                String uri = person.getUri();
                Intent myintent=new Intent(Intent.ACTION_SEND);
                myintent.setType("text/plain");
                String sharebody=""+uri;
                String sharetsub="your sub here";
                myintent.putExtra(Intent.EXTRA_SUBJECT,sharetsub);
                myintent.putExtra(Intent.EXTRA_TEXT,sharebody);
                mcontext.startActivity(Intent.createChooser(myintent,"share using"));
            }
        });
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentd = new Intent(mcontext,Download.class);
                intentd.putExtra("pdflink",person.getUri());
                mcontext.startActivity(intentd);
            }
        });
        someClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                putUrl();

            }
        });

       // Read more: http://www.androidhub4you.com/2013/02/muftitouch-listview-multi-click.html#ixzz5875Swy2i


        //ImageView ivbook=mconvertview.findViewById(R.id.ivbook);
        tvdisplayname.setText(""+person.getName());
        tvdsplayad.setText(""+person.getBookName());
       // ivbook.setImageResource(R.drawable.book_image);
        return mconvertview;
    }

    private void putUrl() {
        Intent intentd = new Intent(mcontext,PdfView.class);
        intentd.putExtra("pdflink",person.getUri());
        mcontext.startActivity(intentd);
    }


    @Nullable
    @Override
    public Person getItem(int position) {
        return dataOrginal.get(position);
    }

    @Override
    public int getCount() {
        return dataOrginal.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        if (cs==null){
            cs=new customfilter();
        }
        return cs;
    }
    class customfilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results=new FilterResults();
            if (charSequence!=null && charSequence.length()>0) {
                charSequence = charSequence.toString().toUpperCase();
                ArrayList<Person> datachange = new ArrayList<>();
                for (int i = 0; i < dataTemp.size(); i++) {
                    if (dataTemp.get(i).getBookName().toUpperCase().contains(charSequence)) {
                        Person person = new Person(dataTemp.get(i).getName(), dataTemp.get(i).getBookName(), dataTemp.get(i).getUri(), dataTemp.get(i).getUid());
                        datachange.add(person);

                    }
                }results.count=datachange.size();
                results.values=datachange;

            }else {
                results.count=dataTemp.size();
                results.values=dataTemp;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
           dataOrginal=(ArrayList<Person>)filterResults.values;
           notifyDataSetChanged();
        }
    }
}


