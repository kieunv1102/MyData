package com.entertaiment.photo.christmassticker.models;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by MSI on 27/01/2015.
 */
public class PhotoDetail {
    private Bitmap bitmap;
    private String album;
    private Uri mUri;
    private String path;
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Uri getmUri() {
        return mUri;
    }

    public void setmUri(Uri mUri) {
        this.mUri = mUri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
