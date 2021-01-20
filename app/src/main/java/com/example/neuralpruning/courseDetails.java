package com.example.neuralpruning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class courseDetails extends AppCompatActivity {
    private TextView durationText;
    private TextView visitorsText;
    private TextView short_desp_text;
    private TextView langText;
    private TextView likesText;
    ImageView courseImg;

    String duration;
    String visitors;
    String shortDesp;
    String courseLang;
    String likes;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        durationText=findViewById(R.id.durationText);
        visitorsText=findViewById(R.id.visitorsText);
        short_desp_text=findViewById(R.id.shortDespText);
        langText=findViewById(R.id.courseLanguage);
        likesText=findViewById(R.id.likesText);
        courseImg=findViewById(R.id.courseImage);
        fetchData();



    }

    private void fetchData() {
        Intent intent=getIntent();
        duration=intent.getStringExtra("duration");
        durationText.setText(duration);
        visitors=intent.getStringExtra("visitors");
        visitorsText.setText(visitors);
        shortDesp=intent.getStringExtra("short_desp");
        short_desp_text.setText(shortDesp);
        courseLang=intent.getStringExtra("course_lang");
        langText.setText(courseLang);
        likes=intent.getStringExtra("likes");
        langText.setText(likes);
        imageUrl=intent.getStringExtra("imageUrl");
        Picasso.get().load(imageUrl).into(courseImg);


    }
}