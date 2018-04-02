package com.login.reg_login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by jack on 2/27/2018.
 */

public class CustomAdapterMyPost extends BaseAdapter {
     Context mcontext;
    public ArrayList<Person> dataOrginal,dataTemp;
    Person person;
    String uri;
    customfilter cs;
    String bookName;
    DatabaseReference databaseReference;
    String uid;
    public CustomAdapterMyPost(Context context, @LayoutRes int resource, ArrayList<Person> object) {
        this.mcontext=context;
        this.dataOrginal=object;
        this.dataTemp=object;

    }
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View mconvertview=view;
        if (mconvertview==null){
            LayoutInflater inflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mconvertview = inflater.inflate(R.layout.custommypost, null);
        }
        person = dataOrginal.get(position);
        bookName=dataOrginal.get(position).getBookName();
        TextView tvNameB=mconvertview.findViewById(R.id.tvNameB);
        TextView tvWritterNameB=mconvertview.findViewById(R.id.tvWritterNameB);
        TextView tvBookName=mconvertview.findViewById(R.id.tvViewBname);
        TextView tvWritterN=mconvertview.findViewById(R.id.tvViewWName);
        tvBookName.setText(dataOrginal.get(position).getBookName());
        tvWritterN.setText(dataOrginal.get(position).getName());
        Button btnDelete=mconvertview.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookName = dataOrginal.get(position).getBookName();
                AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                builder.setMessage("Are you sure want to delete it...");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseReference=FirebaseDatabase.getInstance().getReference();

                        databaseReference.child("Add_Information").orderByChild("bookName").equalTo(bookName)
                                .addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        dataSnapshot.getRef().removeValue();
                                    }

                                    @Override
                                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                    }

                                    @Override
                                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                                    }

                                    @Override
                                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return mconvertview;
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
            }
            results.count=datachange.size();
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