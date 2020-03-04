package com.example.news7.Model;

public class NewsModel {
    private String title;
    private String description;
    private String imgurl;
    private String pubdate;

    public NewsModel() {
    }

    public String getTitle() {

        return title;
    }

    public NewsModel(String title, String description, String imgurl, String pubdate) {
        this.title = title;
        this.description = description;
        this.imgurl = imgurl;
        this.pubdate = pubdate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }
}
