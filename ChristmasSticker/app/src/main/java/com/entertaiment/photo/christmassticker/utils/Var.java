package com.entertaiment.photo.christmassticker.utils;

import android.util.SparseArray;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Kieu on 9/2/2015.
 */
public class Var {
    public static String folder = "/ChristmasSticker";
    public static ArrayList<String> initColors() {
        ArrayList<String> arrColor = new ArrayList<String>();
        arrColor.add("#000000");
        arrColor.add("#FFFFFF");
        arrColor.add("#f44336");
        arrColor.add("#e91e63");
        arrColor.add("#9c27b0");
        arrColor.add("#673ab7");
        arrColor.add("#3f51b5");
        arrColor.add("#03a9f4");
        arrColor.add("#00bcd4");
        arrColor.add("#009688");
        arrColor.add("#4caf50");
        arrColor.add("#8bc34a");
        arrColor.add("#cddc39");
        arrColor.add("#ffeb3b");
        arrColor.add("#ffc107");
        arrColor.add("#ff9800");
        arrColor.add("#ff5722");
        arrColor.add("#795548");
        arrColor.add("#9e9e9e");
        arrColor.add("#607d8b");
        return arrColor;
    }
    public static class ViewHolder {
        public static <T extends View> T get(View view, int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                view.setTag(viewHolder);
            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }
    }
}
