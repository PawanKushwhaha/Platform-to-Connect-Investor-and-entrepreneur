package com.example.captal;

public class uploads_class {

    String images_link,compliments,uploader_id;


    public uploads_class() {
    }


    public uploads_class(String images_link, String compliments, String uploader_id) {
        this.images_link = images_link;
        this.compliments = compliments;
        this.uploader_id = uploader_id;
    }

    public String getImages_link() {
        return images_link;
    }

    public void setImages_link(String images_link) {
        this.images_link = images_link;
    }

    public String getCompliments() {
        return compliments;
    }

    public void setCompliments(String compliments) {
        this.compliments = compliments;
    }

    public String getUploader_id() {
        return uploader_id;
    }

    public void setUploader_id(String uploader_id) {
        this.uploader_id = uploader_id;
    }
}
