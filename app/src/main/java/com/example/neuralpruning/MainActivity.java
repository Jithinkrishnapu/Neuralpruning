package com.example.neuralpruning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.GnssAntennaInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String URL_DATA = "https://neuralpruning.com/api/course";
    private List<data> dataList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    courseAdapter courseAdapter;
    MaterialButton signOutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       @SuppressLint("WrongConstant") SharedPreferences sharedPreferences=getSharedPreferences("userLogin",MODE_APPEND);


        recyclerView=findViewById(R.id.courseRecyclerView);
        layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        signOutBtn=findViewById(R.id.signOutBTn);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSharedPreference.setLoggedIn(getApplicationContext(),false);
                startActivity(new Intent(getApplicationContext(),login.class));
                finish();
            }
        });

        dataList = new ArrayList<>();
        loadData();




        Call<course> call = RetrofitClientInstance.getService().getCourses();
        call.enqueue(new Callback<course>() {
            @Override
            public void onResponse(Call<course> call, Response<course> response) {
                Toast.makeText(MainActivity.this, "runned", Toast.LENGTH_SHORT).show();
                course course = response.body();


                Log.d("testJson", course.getStatus());
                Log.d("testJson", course.getMsg());

                List<data> dataList = course.getDataList();
//                try {
//                    String jsonString=course.toString();
//                    JSONObject jsonObject=new JSONObject(jsonString);
//                    JSONArray array=jsonObject.getJSONArray("data");
//                    Toast.makeText(MainActivity.this, "json array runned", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(MainActivity.this, jsonString, Toast.LENGTH_SHORT).show();
//                    List<String> list = new ArrayList<String>();
//                    for(int i = 0 ; i < array.length(); i++) {
//
//                        list.add(array.getJSONObject(i).getString("course_title"));
//                        System.out.println(array.getJSONObject(i).getString("course_title")); // display usernames
//                        Toast.makeText(MainActivity.this, array.getJSONObject(i).getString("course_title"), Toast.LENGTH_SHORT).show();
//                    }
//
//
//
//
//                }
//                catch (Exception e)
//                {
//                    e.getMessage();
//                    Toast.makeText(MainActivity.this, "exception happend", Toast.LENGTH_SHORT).show();
//                }


            }

            @Override
            public void onFailure(Call<course> call, Throwable t) {

            }
        });

    }

    private void loadCourse() {
        Toast.makeText(this, "load course runned", Toast.LENGTH_SHORT).show();
        courseAdapter=new courseAdapter(getApplicationContext(),
                dataList);
        recyclerView.setAdapter(courseAdapter);
        signOutBtn.setVisibility(View.VISIBLE);

    }

    private void loadData() {
        Toast.makeText(this, "load data runned", Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray array=jsonObject.getJSONArray("data");
                            for (int i=0;i<array.length();i++)
                            {
                                JSONObject o=array.getJSONObject(i);
                                data data=new data(
                                        o.getInt("id"),
                                        o.getString("course_title"),
                                        o.getString("trainer"),
                                        o.getInt("course_price"),
                                        o.getInt("course_visitor"),
                                        o.getInt("course_like"),
                                        o.getString("course_image"),
                                        o.getString("trainer_img"),
                                        o.getString("course_language"),
                                        o.getString("duration"),
                                        o.getString("short_description")
                                );
                                dataList.add(data);


                            }
                            Toast.makeText(MainActivity.this, dataList.get(0).getCourse_title(), Toast.LENGTH_SHORT).show();
                            loadCourse();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
