package com.entertaiment.photo.christmassticker.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.entertaiment.photo.christmassticker.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;


/**
 * Created by NguyenDuc on 10/02/2015.
 */
public class StickerAdapter extends BaseAdapter{
    private Context mContext;
    private List<String> listSticker;
    LayoutInflater inflater;
    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public StickerAdapter(Context context, List<String> listSticker) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.listSticker = listSticker;
        //image loader
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).resetViewBeforeLoading(true)
                .considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
    }


    @Override
    public int getCount() {
        return listSticker.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = inflater.inflate(R.layout.item_grv_sticker, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        String url = "assets://" + listSticker.get(position).toString();
        imageLoader.displayImage(url, vh.imvSticker, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }


            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            }


            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                vh.imvSticker.setImageBitmap(loadedImage);
            }

        });
        return vh.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final ImageView imvSticker;

        private ViewHolder(RelativeLayout rootView, ImageView imgRowHlvSticker) {
            this.rootView = rootView;
            this.imvSticker = imgRowHlvSticker;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            ImageView imgRowHlvSticker = (ImageView) rootView.findViewById(R.id.imv_grv_sticker);
            return new ViewHolder(rootView, imgRowHlvSticker);
        }
    }
}
