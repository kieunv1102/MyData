package com.entertaiment.photo.christmassticker.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.entertaiment.photo.christmassticker.R;
import com.entertaiment.photo.christmassticker.models.Album;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

import java.util.ArrayList;

/**
 * Created by MSI on 28/01/2015.
 */
public class AlbumAdapter extends BaseAdapter {
    private Activity mContext;
    private ArrayList<Album> arrFolder;
    ImageLoader mImageLoader;
    DisplayImageOptions mDisplayImageOptions;
    ImageLoaderConfiguration imageLoaderConfiguration;
    private LayoutInflater mInflater;

    public AlbumAdapter(Activity context, ArrayList<Album> arrPhoto) {
        this.mContext = context;
        this.arrFolder = arrPhoto;

        mImageLoader = ImageLoader.getInstance();
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true).resetViewBeforeLoading(false)
                .showImageOnLoading(R.drawable.empty_photo)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300))
                .postProcessor(new BitmapProcessor() {
                    @Override
                    public Bitmap process(Bitmap bitmap) {
                        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
                        float targetWidth = metrics.widthPixels;
                        return Bitmap.createScaledBitmap(bitmap, (int) targetWidth / 3, (int) targetWidth / 3, false);
                    }
                })
                .build();
        try{
            imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(mContext)
                    .defaultDisplayImageOptions(mDisplayImageOptions)
                    .memoryCache(new WeakMemoryCache())
                    .discCacheSize(100 * 1024 * 1024).build();
            ImageLoader.getInstance().init(imageLoaderConfiguration);
        }catch (Exception e){
            e.printStackTrace();
        }

}


    @Override
    public int getCount() {
        return arrFolder.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup arg2) {
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.item_album, arg2, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        if (arrFolder.get(i).equals("")) {
            vh.tvItemAlbum.setText("No date Detected");
        } else {
            vh.tvItemAlbum.setText("" + arrFolder.get(i).getBucketName() + " (" + arrFolder.get(i).getTotalCount() + ")");
        }
        String urlImg = "file:///" + arrFolder.get(i).getImgThumb();
        mImageLoader.displayImage(urlImg, vh.imgItemAlbum, mDisplayImageOptions);

        return vh.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final ImageView imgItemAlbum;
        public final TextView tvItemAlbum;

        private ViewHolder(RelativeLayout rootView, ImageView imgItemAlbum, TextView tvItemAlbum) {
            this.rootView = rootView;
            this.imgItemAlbum = imgItemAlbum;
            this.tvItemAlbum = tvItemAlbum;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            ImageView imgItemAlbum = (ImageView)rootView.findViewById( R.id.imgItemAlbum );
            TextView tvItemAlbum = (TextView)rootView.findViewById( R.id.tvItemAlbum );
            return new ViewHolder( rootView, imgItemAlbum, tvItemAlbum );
        }
    }

}
