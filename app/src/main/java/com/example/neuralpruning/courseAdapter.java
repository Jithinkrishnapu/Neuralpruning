package com.example.neuralpruning;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class courseAdapter extends RecyclerView.Adapter<courseAdapter.orderListHolder> {
    Context context;
    List<data> dataList = new ArrayList<>();
    LayoutInflater inflater;




    private onItemClickListener onItemClickListener;
    public interface onItemClickListener
    {
        void onItemClick(int position);
    }
    public  void setOnItemClickListener(onItemClickListener listener)
    {
        onItemClickListener=listener;
    }



    public courseAdapter(Context context, List<data> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public orderListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course, parent, false);
        return new orderListHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final orderListHolder holder, final int position) {


        final data data=dataList.get(position);

        String courseTitle=data.getCourse_title();
        holder.courseTitle.setText(courseTitle);

        String trainerName=data.getTrainer();
        holder.trainer.setText(trainerName);

        String likes=String.valueOf(data.getCourse_like());
        holder.likes.setText(likes);

        String peope=String.valueOf(data.getCourse_visitor());
        holder.peoples.setText(peope);

        String price=String.valueOf(data.getCourse_price());
        holder.price.setText("₹"+price);

        String courseUrl=data.getCourseImage();
        Picasso.get().load(courseUrl).into(holder.courseImage);

        String trainerUrl=data.getTrainerImage();
        Picasso.get().load(trainerUrl).into(holder.trainerImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent courseDetailsActivity=new Intent(context,courseDetails.class);
                courseDetailsActivity.putExtra("course_lang",data.getCourse_language());
                courseDetailsActivity.putExtra("duration",data.getDuration());
                courseDetailsActivity.putExtra("short_desp",data.getShort_description());
                courseDetailsActivity.putExtra("visitors",data.getCourse_visitor());
                courseDetailsActivity.putExtra("likes",data.getCourse_like());
                courseDetailsActivity.putExtra("imageUrl",data.getCourseImage());
                v.getContext().startActivity(courseDetailsActivity);
            }
        });


    }

    private void toCourseDetails(data data) {



    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public class orderListHolder extends RecyclerView.ViewHolder{

        private TextView courseTitle;
        private TextView trainer;
        private TextView likes;
        private TextView peoples;
        private TextView price;
        private ImageView courseImage;
        private CircleImageView trainerImage;

        public orderListHolder(@NonNull final View itemView, final onItemClickListener listener) {
            super(itemView);


            courseTitle=itemView.findViewById(R.id.courseTitle);
            trainer=itemView.findViewById(R.id.trainerName);
            likes=itemView.findViewById(R.id.likesText);
            trainer=itemView.findViewById(R.id.trainerName);
            peoples=itemView.findViewById(R.id.peopleText);
            price=itemView.findViewById(R.id.coursePrice);
            courseImage=itemView.findViewById(R.id.courseImage);
            trainerImage=itemView.findViewById(R.id.trainerImage);



        }
    }

}
