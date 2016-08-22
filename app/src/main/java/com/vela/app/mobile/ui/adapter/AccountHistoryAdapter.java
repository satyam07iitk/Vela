package com.vela.app.mobile.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vela.app.mobile.R;
import com.vela.app.mobile.ui.model.AccountHistoryModel;

import java.util.ArrayList;

//import com.vyoice.app.R;
//import com.vyoice.app.models.landing_page.AccountHistoryModel;

public class AccountHistoryAdapter extends RecyclerView.Adapter<AccountHistoryAdapter.ViewHolder> {

    private final ArrayList<AccountHistoryModel> accountHistoryList;

    Context context,context1;
    Uri uri,uri1;
    int count;


    public AccountHistoryAdapter(ArrayList<AccountHistoryModel> accountHistoryList) {
        this.accountHistoryList = accountHistoryList;
    }

    // Create new views (invoked by the layout manager):
    @Override
    public AccountHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvdriverdetails, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        AccountHistoryModel historyItem = accountHistoryList.get(position);

//        uri= Uri.parse(historyItem.getProfileimage());
//        context= viewHolder.Profileimage.getContext();
//        Picasso.with(context)
//                .load(uri).into(viewHolder.Profileimage);
        viewHolder.Name.setText(historyItem.getName());
        viewHolder.Carname.setText(historyItem.getCarname());
        viewHolder.Rating.setText(historyItem.getRating());
        viewHolder.Location.setText(historyItem.getLocation());

    }

    // inner class to hold a reference to each item of RecyclerView 
    public static class ViewHolder extends RecyclerView.ViewHolder {


        ImageView Profileimage,Call,Chat,map;
        TextView Name;
        TextView Carname;
        TextView Rating,Location;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);


            Profileimage=(ImageView)itemLayoutView.findViewById(R.id.profileimage);
            Name = (TextView) itemLayoutView.findViewById(R.id.Name);
            Carname = (TextView) itemLayoutView.findViewById(R.id.Carname);
            Call=(ImageView)itemLayoutView.findViewById(R.id.call);
            Chat=(ImageView)itemLayoutView.findViewById(R.id.chat);
            map=(ImageView)itemLayoutView.findViewById(R.id.map);
            Rating = (TextView) itemLayoutView.findViewById(R.id.Rating);
            Location = (TextView) itemLayoutView.findViewById(R.id.Location);

        }
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (accountHistoryList != null) ? accountHistoryList.size() : 0;
    }
}
