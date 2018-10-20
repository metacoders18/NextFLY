package com.metacoders.nextfly;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter {
    private List<Model_for_Article_row> heroList;

    //the context object
    private Context mCtx;

    //here we are getting the herolist and context
    //so while creating the object of this adapter class we need to give herolist and context
    public ListViewAdapter(List<Model_for_Article_row> heroList, Context mCtx) {
        super(mCtx, R.layout.list_items, heroList);
        this.heroList = heroList;
        this.mCtx = mCtx;
    }

    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.list_items, null, true);

        //getting text views
        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
        TextView textViewImageUrl = listViewItem.findViewById(R.id.textViewImageUrl);

        //Getting the hero for the specified position
        Model_for_Article_row hero = heroList.get(position);

        final String  name , link ;
        name = hero.getName();
        link = hero.getId();
        //setting hero values to textviews
        textViewName.setText(name);
        textViewImageUrl.setText(link);

        listViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mCtx , details_launch.class);
                i.putExtra("namee",name);
                i.putExtra("date", link);
                mCtx.startActivity(i);

            }
        });

        //returning the listitem
        return listViewItem;


    }


}
