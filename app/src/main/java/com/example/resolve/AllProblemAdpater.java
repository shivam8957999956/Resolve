package com.example.resolve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resolve.MainUserDashBoard.UserComplainAdapterClass;
import com.example.resolve.MainUserDashBoard.UserComplainHelperClass;

import java.util.ArrayList;

public class AllProblemAdpater extends RecyclerView.Adapter<AllProblemAdpater.ViewHolder> {


    private AllProblemAdpater.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(AllProblemAdpater.OnItemClickListener listener){

        mListener=listener;

    }

    private static final String tag = "RecyclerView";

    private Context mContext;
    private ArrayList<AllProblemhelperClass> shopInnerDetailsList;
    public AllProblemAdpater(Context mContext, ArrayList<AllProblemhelperClass> shopInnerDetailsList) {
        this.mContext = mContext;
        this.shopInnerDetailsList = shopInnerDetailsList;
    }



    @NonNull
    @Override
    public AllProblemAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allproblemcardview, parent, false);

        return new AllProblemAdpater.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AllProblemAdpater.ViewHolder holder, int position) {

//        long l=Long.valueOf(shopInnerDetailsList.get(position).getTime());
//        String dateString = DateFormat.format("dd/MM/yyyy hh:mm:ss", new Date(l)).toString();
        holder.mes.setText(shopInnerDetailsList.get(position).getMessage());
        holder.name.setText(shopInnerDetailsList.get(position).getEmail());
        holder.time.setText(shopInnerDetailsList.get(position).getTime());
        holder.subject.setText(shopInnerDetailsList.get(position).getSubject());
    }
    @Override
    public int getItemCount() {
        return shopInnerDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView subject;
        TextView time;
      //  TextView cate;
        TextView mes;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            time=itemView.findViewById(R.id.time);
         //   cate=itemView.findViewById(R.id.category);
            mes=itemView.findViewById(R.id.message);
            subject=itemView.findViewById(R.id.subject);
            name=itemView.findViewById(R.id.name);


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
