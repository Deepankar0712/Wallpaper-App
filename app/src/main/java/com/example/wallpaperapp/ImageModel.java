package com.example.wallpaperapp;

public class ImageModel {

    public ImageModel(UrlModel src) {
        this.src = src;
    }

    private UrlModel src;

    public UrlModel getSrc() {
        return src;
    }

    public void setSrc(UrlModel src) {
        this.src = src;
    }
}
