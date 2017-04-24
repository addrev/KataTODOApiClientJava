package com.karumi.postapiclient.dto;


import com.google.gson.annotations.SerializedName;

public class PostDto {

    @SerializedName("id")
    private final String id;
    @SerializedName("userId")
    private final String userId;
    @SerializedName("title")
    private final String title;
    @SerializedName("body")
    private final String body;

    public PostDto(String id, String userId, String title, String body) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
