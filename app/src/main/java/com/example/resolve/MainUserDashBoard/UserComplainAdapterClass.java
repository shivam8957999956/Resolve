package com.example.resolve.MainUserDashBoard;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resolve.R;

import java.util.ArrayList;
import java.util.Date;

public class UserComplainAdapterClass extends RecyclerView.Adapter<UserComplainAdapterClass.ViewHolder> {


private UserComplainAdapterClass.OnItemClickListener mListener;

public interface OnItemClickListener {
    void onItemClick(int position);

}

    public void setOnItemClickListener(UserComplainAdapterClass.OnItemClickListener listener){

        mListener=listener;

    }

    private static final String tag = "RecyclerView";

    private Context mContext;
    private ArrayList<UserComplainHelperClass> shopInnerDetailsList;
    public UserComplainAdapterClass(Context mContext, ArrayList<UserComplainHelperClass> shopInnerDetailsList) {
        this.mContext = mContext;
        this.shopInnerDetailsList = shopInnerDetailsList;
    }



    @NonNull
    @Override
    public UserComplainAdapterClass.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_recycler_cardview, parent, false);

        return new UserComplainAdapterClass.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull UserComplainAdapterClass.ViewHolder holder, int position) {

//        long l=Long.valueOf(shopInnerDetailsList.get(position).getTime());
//        String dateString = DateFormat.format("dd/MM/yyyy hh:mm:ss", new Date(l)).toString();

        holder.subject.setText(shopInnerDetailsList.get(position).getSubject());
        holder.mes.setText(shopInnerDetailsList.get(position).getMessage());
        holder.time.setText("registered at: "+shopInnerDetailsList.get(position).getTime());
        holder.cate.setText(shopInnerDetailsList.get(position).getCategory());


    }
    @Override
    public int getItemCount() {
        return shopInnerDetailsList.size();
    }

public class ViewHolder extends RecyclerView.ViewHolder {


    TextView subject;
    TextView time;
    TextView cate;
    TextView mes;
    TextView status;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);


       time=itemView.findViewById(R.id.time);
       cate=itemView.findViewById(R.id.category);
       mes=itemView.findViewById(R.id.message);
       subject=itemView.findViewById(R.id.subject);


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
