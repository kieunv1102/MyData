package com.entertaiment.photo.christmassticker.screen;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.entertaiment.photo.christmassticker.R;
import com.entertaiment.photo.christmassticker.fragment.BackHandledFragment;
import com.entertaiment.photo.christmassticker.fragment.BackHandledInterface;
import com.entertaiment.photo.christmassticker.fragment.FragAlbum;
import com.entertaiment.photo.christmassticker.fragment.FragPhoto;

public class MainActivity extends FragmentActivity implements View.OnClickListener, BackHandledInterface, FragPhoto.OnDataPass{
    FrameLayout frImg;
    Button btnCamera;
    private Fragment fragment;
    private BackHandledFragment mBackHandedFragment;
    public static final int KEY_REQUEST_CAMERA = 0x002;
    private Uri mImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment = new FragAlbum();
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frContain, fragment);
            ft.addToBackStack("tag");
            ft.commit();
        }

    }

    @Override
    public void onContentChanged() {
        btnCamera = (Button) findViewById(R.id.Camera);
        btnCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Camera:
                try {
                    String fileName = "temp.jpg";
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, fileName);
                    mImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                    startActivityForResult(intent, KEY_REQUEST_CAMERA);
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle mybunBundle = new Bundle();
            String[] arrayOfString = {MediaStore.Images.ImageColumns.DATA};
            switch (requestCode) {
                case KEY_REQUEST_CAMERA:
                    break;
            }
            try {
                Cursor localCursor = getContentResolver().query(mImageUri, arrayOfString, null, null, null);
                localCursor.moveToFirst();
                String str = localCursor.getString(localCursor.getColumnIndex(arrayOfString[0]));
                localCursor.close();
                mybunBundle.putString("path", str);
                Intent intent = new Intent(MainActivity.this, SetPhotoActivity.class);
                intent.putExtras(mybunBundle);
                startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (resultCode == RESULT_CANCELED) {
            switch (requestCode) {
                case KEY_REQUEST_CAMERA:
                    break;
            }
            mImageUri = null;
        }
    }
    @Override
    public void setSelectedFragment(BackHandledFragment selectedFragment) {
        this.mBackHandedFragment = selectedFragment;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()) {
                if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                    finish();
                    return true;
                } else {
                    getSupportFragmentManager().popBackStack();
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public void onDataPass(String data) {
        Bundle bundle = new Bundle();
        bundle.putString("path", data);
        Intent intent = new Intent(MainActivity.this, SetPhotoActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
