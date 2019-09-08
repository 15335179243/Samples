package com.eyecool.visitorsys.db;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "VISITOR_LOG".
 */
public class VisitorLog {

    private Long id;
    private String user_id;
    private String name;
    private String phone;
    private String visitor_type;
    private String st_class;
    private String match_time;
    private String match_score;
    private String match_img;
    private String feature_img;
    private String stu_teacher;
    private String visitor_stu;
    private String card_id;

    public VisitorLog() {
    }

    public VisitorLog(Long id) {
        this.id = id;
    }

    public VisitorLog(Long id, String user_id, String name, String phone, String visitor_type, String st_class, String match_time, String match_score, String match_img, String feature_img, String stu_teacher, String visitor_stu, String card_id) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.phone = phone;
        this.visitor_type = visitor_type;
        this.st_class = st_class;
        this.match_time = match_time;
        this.match_score = match_score;
        this.match_img = match_img;
        this.feature_img = feature_img;
        this.stu_teacher = stu_teacher;
        this.visitor_stu = visitor_stu;
        this.card_id = card_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVisitor_type() {
        return visitor_type;
    }

    public void setVisitor_type(String visitor_type) {
        this.visitor_type = visitor_type;
    }

    public String getSt_class() {
        return st_class;
    }

    public void setSt_class(String st_class) {
        this.st_class = st_class;
    }

    public String getMatch_time() {
        return match_time;
    }

    public void setMatch_time(String match_time) {
        this.match_time = match_time;
    }

    public String getMatch_score() {
        return match_score;
    }

    public void setMatch_score(String match_score) {
        this.match_score = match_score;
    }

    public String getMatch_img() {
        return match_img;
    }

    public void setMatch_img(String match_img) {
        this.match_img = match_img;
    }

    public String getFeature_img() {
        return feature_img;
    }

    public void setFeature_img(String feature_img) {
        this.feature_img = feature_img;
    }

    public String getStu_teacher() {
        return stu_teacher;
    }

    public void setStu_teacher(String stu_teacher) {
        this.stu_teacher = stu_teacher;
    }

    public String getVisitor_stu() {
        return visitor_stu;
    }

    public void setVisitor_stu(String visitor_stu) {
        this.visitor_stu = visitor_stu;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

}
