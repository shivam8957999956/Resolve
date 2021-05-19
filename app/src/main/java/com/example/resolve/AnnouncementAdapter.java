package com.example.resolve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {


    private AnnouncementAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(AnnouncementAdapter.OnItemClickListener listener){

        mListener=listener;

    }

    private static final String tag = "RecyclerView";

    private Context mContext;
    private ArrayList<AnnouncementHelperClass> shopInnerDetailsList;
    public AnnouncementAdapter(Context mContext, ArrayList<AnnouncementHelperClass> shopInnerDetailsList) {
        this.mContext = mContext;
        this.shopInnerDetailsList = shopInnerDetailsList;
    }



    @NonNull
    @Override
    public AnnouncementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announce_card_view, parent, false);

        return new AnnouncementAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementAdapter.ViewHolder holder, int position) {

//        long l=Long.valueOf(shopInnerDetailsList.get(position).getTime());
//        String dateString = DateFormat.format("dd/MM/yyyy hh:mm:ss", new Date(l)).toString();
        holder.email.setText(shopInnerDetailsList.get(position).getEmail());
        holder.mes.setText(shopInnerDetailsList.get(position).getText());
        holder.time.setText(shopInnerDetailsList.get(position).getTitle());
    }
    @Override
    public int getItemCount() {
        return shopInnerDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        TextView time;
        //  TextView cate;
        TextView mes;
        TextView email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            time=itemView.findViewById(R.id.time);
            //   cate=itemView.findViewById(R.id.category);
            mes=itemView.findViewById(R.id.text);

            email=itemView.findViewById(R.id.email);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mListener != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }

                }
            });

        }
    }


}
