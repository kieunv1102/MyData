package com.entertaiment.photo.christmassticker.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.entertaiment.photo.christmassticker.R;
import com.entertaiment.photo.christmassticker.adapters.PhotoAdapter;
import com.entertaiment.photo.christmassticker.asyntask.GetPhoto;
import com.entertaiment.photo.christmassticker.models.PhotoDetail;

import java.util.ArrayList;

/**
 * Created by Xuan on 8/2/2015.
 */
public class FragPhoto extends BackHandledFragment {
    private GridView grPhoto;
    private PhotoAdapter photoAdapter;
    private ArrayList<PhotoDetail> arrPhotoDetails;
//    private ProgressLoading progressLoading;
    String nameFolder;
    Cursor mImageCursor;
    private OnDataPass dataPasser;

    public static FragPhoto newInstance(String boo) {
        FragPhoto f = new FragPhoto();
        Bundle b = new Bundle();
        b.putString("namefolder", boo);

        f.setArguments(b);
        return f;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nameFolder = getArguments().getString("namefolder");
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_photo_detail, container, false);
        getUI(v);
        String[] projection1 = {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.WIDTH,
                MediaStore.Images.Media.HEIGHT,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.TITLE,

        };
        String selection = MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " = ?";
        String[] selectionArgs = new String[]{"" + nameFolder};
        mImageCursor = getActivity().managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection1, selection, selectionArgs, null);
        GetPhoto photo = new GetPhoto(getActivity(), mImageCursor, new GetPhoto.PhotoInter() {
            @Override
            public void getResult(ArrayList<PhotoDetail> photos) {
                arrPhotoDetails = new ArrayList<PhotoDetail>();
                arrPhotoDetails.clear();
                arrPhotoDetails.addAll(photos);
                photoAdapter = new PhotoAdapter(getActivity(), arrPhotoDetails);
                grPhoto.setAdapter(photoAdapter);
            }
        });
        photo.execute();
        grPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dataPasser.onDataPass(arrPhotoDetails.get(i).getPath());
            }
        });

        return v;
    }

    private void getUI(View v) {
        grPhoto = (GridView) v.findViewById(R.id.grPhoto);
//        progressLoading = (ProgressLoading) v.findViewById(R.id.prLoading);
    }
    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        dataPasser = (OnDataPass) a;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    public void passData(String data) {
        dataPasser.onDataPass(data);
    }
    public interface OnDataPass {
        public void onDataPass(String data);
    }
}
