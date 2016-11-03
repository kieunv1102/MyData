package com.entertaiment.photo.christmassticker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.entertaiment.photo.christmassticker.R;
import com.entertaiment.photo.christmassticker.models.PhotoDetail;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

/**
 * Created by MSI on 28/01/2015.
 */
public class PhotoAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<PhotoDetail> arrFolder;
    ImageLoader mImageLoader;
    DisplayImageOptions mDisplayImageOptions;
    ImageLoaderConfiguration imageLoaderConfiguration;

    public PhotoAdapter(Context mContext, ArrayList<PhotoDetail> arrPhoto) {
        this.mContext = mContext;
        this.arrFolder = arrPhoto;
        mImageLoader= ImageLoader.getInstance();
        mDisplayImageOptions=new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true).resetViewBeforeLoading(false)
                .showImageOnLoading(R.drawable.empty_photo)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
        imageLoaderConfiguration=new ImageLoaderConfiguration.Builder(mContext)
                .defaultDisplayImageOptions(mDisplayImageOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();
        ImageLoader.getInstance().init(imageLoaderConfiguration);
    }



    @Override
    public int getCount() {
        return arrFolder.size();
    }

    @Override
    public Object getItem(int arg0) {
        return arrFolder.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }
    class Holder{
        ImageView imgItem;
    }
    @Override
    public View getView(int arg0, View convertView, ViewGroup arg2) {
        View v;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater.from(mContext));
            v = li.inflate(R.layout.item_gird, null);
            final Holder holder=new Holder();
            holder.imgItem = (ImageView) v.findViewById(R.id.itemImgGrid);
            String urlImg = "file:///" + arrFolder.get(arg0).getPath().toString();
            mImageLoader.displayImage(urlImg,holder.imgItem,mDisplayImageOptions);
        } else {
            v = convertView;
        }
        return v;
    }

}
