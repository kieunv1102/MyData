package com.entertaiment.photo.christmassticker.asyntask;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.Window;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LoadPhotoTask extends AsyncTask<String, Void, Bitmap> {
    private Dialog dialog;
    private Context mContext;
    private CommunicatorLoadPhoto communicator;
    //screen size
    private int CAMERA_WIDTH, CAMERA_HEIGHT;

    public LoadPhotoTask(Context mContext, CommunicatorLoadPhoto communicator) {
        this.mContext = mContext;
        this.communicator = communicator;
        //get size of screen
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        CAMERA_WIDTH = dm.widthPixels;
        CAMERA_HEIGHT = dm.heightPixels;

    }

    @Override
    protected void onPreExecute() {
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.setContentView(R.layout.progress_wheel);
        dialog.show();
        super.onPreExecute();

    }


    @Override
    protected Bitmap doInBackground(String... params) {
        String url = params[0];
        Bitmap bitmap = null;
        //decode and rotate bitmap
        try {
            url = params[0];
            InputStream stream = new FileInputStream(url);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = 0;
            bitmap = BitmapFactory.decodeStream(stream, null, options);
            stream.close();
            stream = null;
            if (bitmap.getWidth() > CAMERA_HEIGHT || bitmap.getHeight() > CAMERA_WIDTH) {
                int m = CAMERA_HEIGHT;
                bitmap = Bitmap.createScaledBitmap(bitmap, m, m * bitmap.getHeight() / bitmap.getWidth(), true);
            }
//            bitmap = FileUtil.adjustImageOrientation(bitmap, url);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(final Bitmap bitmap) {
        try {
            communicator.onPostExecute(bitmap);
            if (dialog.getWindow() != null) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPostExecute(bitmap);
    }

    public interface CommunicatorLoadPhoto {
        public void onPostExecute(Bitmap bitmap);
    }
}