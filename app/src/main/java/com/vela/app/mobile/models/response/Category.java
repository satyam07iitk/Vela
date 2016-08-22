package com.vela.app.mobile.models.response;

import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("_id")
    private String id;
    private String name;
    private String description;
    @SerializedName("user_id")
    private String userId;

    public Category() {
    }

    public Category(String id, String name, String description, String userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
