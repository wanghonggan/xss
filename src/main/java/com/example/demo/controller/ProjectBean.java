package com.example.demo.controller;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.Collection;
import java.util.List;

public class ProjectBean {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }
    @NotBlank(message = "数据ID不能为空")
    private String title;

    @JsonProperty("strs1")
    private List<String> strs;
    private List<Integer> ints;

    private Integer int1;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty("user_name")
    private User user;

    public List<User> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<User> userlist) {
        this.userlist = userlist;
    }

    private List<User> userlist;

    public Collection<String> getStrs() {
        return strs;
    }

    public void setStrs(List<String> strs) {
        this.strs = strs;
    }

    public List<Integer> getInts() {
        return ints;
    }

    public void setInts(List<Integer> ints) {
        this.ints = ints;
    }

    public Integer getInt1() {
        return int1;
    }

    public void setInt1(Integer int1) {
        this.int1 = int1;
    }
    @NotBlank(message = "数据ID不能为空")
    @JsonProperty("user_title")
    private String userTitle;

    @Override
    public String toString() {
        return "ProjectBean{" +
                "title='" + title + '\'' +
                ", strs=" + strs +
                ", ints=" + ints +
                ", int1=" + int1 +
                ", user=" + (user==null?"null":user.toString()) +
                ", userlist=" + userlist +
                ", userTitle='" + userTitle + '\'' +
                '}';
    }
}
