package com.login.reg_login;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by jack on 2/27/2018.
 */

public class CustomAdapterMyPost extends BaseAdapter implements View.OnClickListener {
     Context mcontext;
    public ArrayList<Person> dataOrginal,dataTemp;
    Person person;
    customfilter cs;
    FirebaseAuth firebaseAuth;
    String uid;
    public CustomAdapterMyPost(Context context, @LayoutRes int resource, ArrayList<Person> object) {
        this.mcontext=context;
        this.dataOrginal=object;
        this.dataTemp=object;

    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View mconvertview=view;
        if (mconvertview==null){
            LayoutInflater inflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mconvertview = inflater.inflate(R.layout.custommypost, null);
        }
        person = dataOrginal.get(position);
        TextView tvNameB=mconvertview.findViewById(R.id.tvNameB);
        TextView tvWritterNameB=mconvertview.findViewById(R.id.tvWritterNameB);
        TextView tvBookName=mconvertview.findViewById(R.id.tvViewBname);
        TextView tvWritterN=mconvertview.findViewById(R.id.tvViewWName);
        tvBookName.setText(person.getBookName());
        tvWritterN.setText(person.getName());
        Button btnDelete=mconvertview.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
        return mconvertview;
    }
    @Override
    public void onClick(View view) {
   switch (view.getId()){
       case R.id.btnDelete:
           firebaseAuth = FirebaseAuth.getInstance();
           FirebaseUser currentUser = firebaseAuth.getCurrentUser();
           uid = currentUser.getUid();
           DatabaseReference databaseReference = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("Add_Information");
           databaseReference.orderByKey();
           databaseReference.removeValue();
           break;
   }
    }
    public int getCount() {
        return dataOrginal.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Person getItem(int position) {
        return dataOrginal.get(position);
    }


    public Filter getFilter() {
        if (cs==null){
            cs=new customfilter();
        }
        return cs;
    }

class customfilter extends Filter {

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