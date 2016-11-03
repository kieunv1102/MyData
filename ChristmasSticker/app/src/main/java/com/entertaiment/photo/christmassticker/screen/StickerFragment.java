package com.entertaiment.photo.christmassticker.screen;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.entertaiment.photo.christmassticker.R;
import com.entertaiment.photo.christmassticker.adapters.StickerAdapter;
import com.entertaiment.photo.christmassticker.iconsticker.Sticker1;
import com.entertaiment.photo.christmassticker.iconsticker.Sticker2;
import com.entertaiment.photo.christmassticker.iconsticker.Sticker3;
import com.entertaiment.photo.christmassticker.iconsticker.Sticker4;
import com.entertaiment.photo.christmassticker.iconsticker.Sticker5;
import com.entertaiment.photo.christmassticker.iconsticker.Sticker6;

import java.util.List;


public class StickerFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int positionFr;
    GridView grvsticker;
    StickerAdapter adapter;
    OnPassSticker mOnPassSticker;
    private List<String> arrSticker;

    public static StickerFragment newInstance(int position) {
        StickerFragment f = new StickerFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        positionFr = getArguments().getInt(ARG_POSITION);
        switch (positionFr) {
            case 1:
                arrSticker = Sticker1.getSticker1();
                break;
            case 2:
                arrSticker = Sticker2.getSticker2();
                break;
            case 3:
                arrSticker =  Sticker3.getSticker3();
                break;
            case 4:
                arrSticker = Sticker4.getSticker4();
                break;
            case 5:
                arrSticker = Sticker5.getSticker5();
                break;
            case 6:
                arrSticker = Sticker6.getSticker6();
                break;

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.sticker_fragment, container, false);
        grvsticker = (GridView) rootView.findViewById(R.id.grvsticker);
        positionFr = getArguments().getInt(ARG_POSITION);
        switch (positionFr) {
            case 1:
                adapter = new StickerAdapter(getActivity(), Sticker1.getSticker1());
                grvsticker.setAdapter(adapter);
                break;
            case 2:
                adapter = new StickerAdapter(getActivity(), Sticker2.getSticker2());
                grvsticker.setAdapter(adapter);
                break;
            case 3:
                adapter = new StickerAdapter(getActivity(), Sticker3.getSticker3());
                grvsticker.setAdapter(adapter);
                break;
            case 4:
                adapter = new StickerAdapter(getActivity(), Sticker4.getSticker4());
                grvsticker.setAdapter(adapter);
                break;
            case 5:
                adapter = new StickerAdapter(getActivity(), Sticker5.getSticker5());
                grvsticker.setAdapter(adapter);
                break;
            case 6:
                adapter = new StickerAdapter(getActivity(), Sticker6.getSticker6());
                grvsticker.setAdapter(adapter);
                break;


        }


        grvsticker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mOnPassSticker.onPass(arrSticker.get(position));
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mOnPassSticker = (OnPassSticker) activity;
    }

    public void setOnPass(OnPassSticker onPass) {
        this.mOnPassSticker = onPass;
    }

    public interface OnPassSticker {
        public void onPass(String sticker);
    }
}