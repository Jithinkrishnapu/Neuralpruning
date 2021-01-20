package com.example.neuralpruning;

import android.widget.ImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class data {



    private int id;
    private String course_title;
    private String trainer;
    private int course_price;
    private int course_visitor;
    private int course_like;
    private String courseImage;
    private String trainerImage;

    private String course_language;
    private String duration;
    private String short_description;

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    public String getCourse_language() {
        return course_language;
    }

    public void setCourse_language(String course_language) {
        this.course_language = course_language;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getTrainerImage() {
        return trainerImage;
    }

    public void setTrainerImage(String trainerImage) {
        this.trainerImage = trainerImage;
    }

    public data(int id, String course_title, String trainer, int course_price, int course_visitor, int course_like,String courseImage
    ,String trainerImage,String course_language,String duration,String short_description) {
        this.id = id;
        this.course_title = course_title;
        this.trainer = trainer;
        this.course_price = course_price;
        this.course_visitor = course_visitor;
        this.course_like = course_like;
        this.courseImage=courseImage;
        this.trainerImage=trainerImage;
        this.course_language=course_language;
        this.duration=duration;
        this.short_description=short_description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public int getCourse_price() {
        return course_price;
    }

    public void setCourse_price(int course_price) {
        this.course_price = course_price;
    }

    public int getCourse_visitor() {
        return course_visitor;
    }

    public void setCourse_visitor(int course_visitor) {
        this.course_visitor = course_visitor;
    }

    public int getCourse_like() {
        return course_like;
    }

    public void setCourse_like(int course_like) {
        this.course_like = course_like;
    }
}
