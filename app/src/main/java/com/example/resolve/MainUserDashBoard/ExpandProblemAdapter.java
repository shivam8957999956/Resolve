package com.example.resolve.MainUserDashBoard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resolve.AllsolnHelperClass;
import com.example.resolve.R;

import java.util.ArrayList;

public class ExpandProblemAdapter extends RecyclerView.Adapter<ExpandProblemAdapter.ViewHolder> {


    private ExpandProblemAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(ExpandProblemAdapter.OnItemClickListener listener){

        mListener=listener;

    }

    private static final String tag = "RecyclerView";

    private Context mContext;
    private ArrayList<AllsolnHelperClass> shopInnerDetailsList;
    public ExpandProblemAdapter(Context mContext, ArrayList<AllsolnHelperClass> shopInnerDetailsList) {
        this.mContext = mContext;
        this.shopInnerDetailsList = shopInnerDetailsList;
    }



    @NonNull
    @Override
    public ExpandProblemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expand_soln_cardview, parent, false);

        return new ExpandProblemAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ExpandProblemAdapter.ViewHolder holder, int position) {

//        long l=Long.valueOf(shopInnerDetailsList.get(position).getTime());
//        String dateString = DateFormat.format("dd/MM/yyyy hh:mm:ss", new Date(l)).toString();

        holder.message.setText(shopInnerDetailsList.get(position).getSolution());
        holder.name.setText(shopInnerDetailsList.get(position).getName());
        holder.time.setText("at: "+shopInnerDetailsList.get(position).getTime());
        holder.email.setText("Answered By: "+ shopInnerDetailsList.get(position).getEmail());


    }
    @Override
    public int getItemCount() {
        return shopInnerDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView message;
        TextView time;
        TextView email;
        TextView name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            time=itemView.findViewById(R.id.time);
            message=itemView.findViewById(R.id.message);
            name=itemView.findViewById(R.id.name);
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
