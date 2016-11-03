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
import com.entertaiment.photo.christmassticker.utils.DpiUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

/**
 * Created by NguyenDuc on 10/02/2015.
 */
public class HlvFilterAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> listSticker;
    private LayoutInflater mInflater;
    //image loader
    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public HlvFilterAdapter(Context context, List<String> listSticker) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
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
            View view = mInflater.inflate(R.layout.item_hlv_background, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        // TODOBind your data to the views here
        vh.imgRowHlvSticker.setMaxWidth(DpiUtil.pxToDip(mContext, 100));
        vh.imgRowHlvSticker.setMaxHeight(DpiUtil.pxToDip(mContext, 100));
        String url = "assets://" + listSticker.get(position).toString();
        imageLoader.displayImage(url, vh.imgRowHlvSticker, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }


            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            }


            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
               /* vh.imageView.setImageBitmap(
                        Bitmap.createScaledBitmap(loadedImage, DpiUtil.pxToDip(mContext, 100), DpiUtil.pxToDip(mContext, 100),
                                false));*/
                vh.imgRowHlvSticker.setImageBitmap(loadedImage);
             /*   Typeface type = Typeface.createFromAsset(mContext.getAssets(), "font.ttf");
                vh.tvSummary.setTypeface(type);*/
            }

        });
        return vh.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final ImageView imgRowHlvSticker;

        private ViewHolder(RelativeLayout rootView, ImageView imgRowHlvSticker) {
            this.rootView = rootView;
            this.imgRowHlvSticker = imgRowHlvSticker;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            ImageView imgRowHlvSticker = (ImageView) rootView.findViewById(R.id.img_row_hlv_sticker);
            return new ViewHolder(rootView, imgRowHlvSticker);
        }
    }
}