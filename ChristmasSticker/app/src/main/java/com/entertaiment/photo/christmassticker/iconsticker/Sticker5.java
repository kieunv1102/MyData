package com.entertaiment.photo.christmassticker.iconsticker;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MSI on 29/07/2015.
 */
public class Sticker5 {
    public static final int NUMBER_STICKER1 =12 ;
    //format 01,02,....
    public static DecimalFormat formatter = new DecimalFormat("00");

    public static List<String> getSticker5() {
        List<String> listSticker = new ArrayList<String>();
        for (int i = 0; i < NUMBER_STICKER1; i++) {
            String aFormatted = formatter.format(i + 1);
            listSticker.add("sticker/sticker5/sticker5_" + aFormatted + ".png");
        }
        return listSticker;
    }
}
