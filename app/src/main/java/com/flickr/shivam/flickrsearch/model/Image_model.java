package com.flickr.shivam.flickrsearch.model;


import java.io.Serializable;

public class Image_model implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String owner;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}


