package com.entertaiment.photo.christmassticker.models;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MSI on 29/07/2015.
 */
public class FilterBackground {
    public static final int NUMBER_STICKER1 =45 ;
    //format 01,02,....
    public static DecimalFormat formatter = new DecimalFormat("00");

    public static List<String> getSticker1() {
        List<String> listSticker = new ArrayList<String>();
        for (int i = 0; i < NUMBER_STICKER1; i++) {
            String aFormatted = formatter.format(i + 1);
            listSticker.add("filter/filter_" + aFormatted + ".jpg");
        }
        return listSticker;
    }
}
