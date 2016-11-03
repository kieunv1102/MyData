package com.entertaiment.photo.christmassticker.asyntask;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.entertaiment.photo.christmassticker.models.PhotoDetail;

import java.util.ArrayList;

/**
 * Created by MSI on 28/01/2015.
 */
public class GetPhoto extends AsyncTask<Void, Void, ArrayList<PhotoDetail>> {
    private Activity mActivity;
//    private ProgressLoading llLoading;
    private PhotoInter mInter;
    ContentResolver cr;
    Cursor cursor;
    int width, height;

    public GetPhoto(Activity mActivity, Cursor cursor,PhotoInter mInter) {
        this.mActivity = mActivity;
//        this.llLoading = llLoading;
        this.mInter = mInter;
        cr = mActivity.getContentResolver();
        this.cursor = cursor;

    }

    @Override
    protected void onPreExecute() {
//        if (llLoading != null) {
//            llLoading.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    protected ArrayList<PhotoDetail> doInBackground(Void... cursors) {
        ArrayList<PhotoDetail> arrPt = new ArrayList<PhotoDetail>();

        int i = 0;
        while (cursor.moveToNext()) {
            PhotoDetail photo = new PhotoDetail();
            String id = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID));
            long idC = Long.parseLong(id);
            Uri newUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, idC);
            photo.setmUri(newUri);

            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
            photo.setPath(path);
            arrPt.add(photo);

            i++;
        }

        return arrPt;
    }

    @Override
    protected void onPostExecute(ArrayList<PhotoDetail> photoAsyncTask) {
        super.onPostExecute(photoAsyncTask);
        mInter.getResult(photoAsyncTask);
//        if (llLoading != null) {
//            llLoading.setVisibility(View.GONE);
//        }
    }

    public interface PhotoInter {
        public void getResult(ArrayList<PhotoDetail> photos);
    }

}
