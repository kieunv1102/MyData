package com.entertaiment.photo.christmassticker.asyntask;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.entertaiment.photo.christmassticker.models.Album;

import java.util.ArrayList;

/**
 * Created by MSI on 28/01/2015.
 */
public class GetAlbum extends AsyncTask<Void, Void, ArrayList<Album>> {
    private Activity mContext;
//    private ProgressLoading llLoading;
    private AlbumInterface mAlbum;

    public GetAlbum(Activity mContext,  AlbumInterface mAlbum) {
        this.mContext = mContext;
//        this.llLoading = llLoading;
        this.mAlbum = mAlbum;
    }

    @Override
    protected void onPreExecute() {
//        if (llLoading != null) {
//            llLoading.setVisibility(View.VISIBLE);
//        }
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Album> doInBackground(Void... voids) {
        ArrayList<Album> arrayListAlbums = new ArrayList<Album>();
        String[] PROJECTION_BUCKET = {MediaStore.Images.ImageColumns.BUCKET_ID, MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.DATE_TAKEN,
                MediaStore.Images.ImageColumns.DATA};
        String BUCKET_GROUP_BY = "1) GROUP BY 1,(2";
        String BUCKET_ORDER_BY = "MAX(datetaken) DESC";
        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        Cursor cur = mContext.getContentResolver().query(images, PROJECTION_BUCKET, BUCKET_GROUP_BY, null, BUCKET_ORDER_BY);
        Album album;

        if (cur.moveToFirst()) {
            String bucket;
            String date;
            String data;
            long bucketId;
            int bucketColumn = cur.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            int dateColumn = cur.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);
            int dataColumn = cur.getColumnIndex(MediaStore.Images.Media.DATA);
            int bucketIdColumn = cur.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
            do {
                bucket = cur.getString(bucketColumn);

                date = cur.getString(dateColumn);
                data = cur.getString(dataColumn);
                bucketId = cur.getInt(bucketIdColumn);

                if (bucket != null && bucket.length() > 0) {
                    album = new Album();
                    album.setBucketId(bucketId);
                    album.setBucketName(bucket);
                    album.setDateTaken(date);
                    album.setData(data);
                    album.setImgThumb(getFirstPhoto(bucket));
                    album.setTotalCount(photoCountByAlbum(bucket));
                    arrayListAlbums.add(album);
                }

            } while (cur.moveToNext());
        }
        cur.close();
        return arrayListAlbums;
    }

    @Override
    protected void onPostExecute(ArrayList<Album> aVoid) {
//        if (llLoading != null) {
//            llLoading.setVisibility(View.GONE);
//        }
        mAlbum.getResult(aVoid);
    }

    public interface AlbumInterface {
        public void getResult(ArrayList<Album> albums);
    }

    private int photoCountByAlbum(String bucketName) {
        try {
            final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
            String searchParams = null;
            String bucket = bucketName;
            searchParams = "bucket_display_name = \"" + bucket + "\"";
            Cursor mPhotoCursor = mContext.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                    searchParams, null, orderBy + " DESC");

            if (mPhotoCursor.getCount() > 0) {
                return mPhotoCursor.getCount();
            }
            mPhotoCursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;

    }
    public String getFirstPhoto(String str) {
        String path = "";
        String[] projection1 = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
        String selection = MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " = ?";
        String[] selectionArgs = new String[]{"" + str};
        Cursor cursor = mContext.managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection1, selection, selectionArgs, null);
        if (cursor.moveToNext()) {
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
        }
        return path;
    }
}
