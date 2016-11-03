package com.entertaiment.photo.christmassticker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.entertaiment.photo.christmassticker.R;
import com.entertaiment.photo.christmassticker.adapters.AlbumAdapter;
import com.entertaiment.photo.christmassticker.asyntask.GetAlbum;
import com.entertaiment.photo.christmassticker.models.Album;

import java.util.ArrayList;

/**
 * Created by Xuan on 8/2/2015.
 */
public class FragAlbum extends BackHandledFragment {
    private ListView grAlbum;
//    private ProgressLoading prLoading;
    private AlbumAdapter albumAdapter;
    private ArrayList<Album> arrAlbum;
    private String namefolder;
    private Fragment fragment;


    public static FragAlbum newInstance(int typeFrame) {
        FragAlbum f = new FragAlbum();
        Bundle b = new Bundle();
        b.putInt("type_frame", typeFrame);
        f.setArguments(b);
        return f;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_album, container, false);
        getUI(v);
        GetAlbum album = new GetAlbum(getActivity(),  new GetAlbum.AlbumInterface() {
            @Override
            public void getResult(ArrayList<Album> albums) {
                arrAlbum = new ArrayList<Album>();
                arrAlbum.clear();
                arrAlbum.addAll(albums);
                albumAdapter = new AlbumAdapter(getActivity(), arrAlbum);
                grAlbum.setAdapter(albumAdapter);
            }
        });
        album.execute();
        grAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                namefolder = arrAlbum.get(i).getBucketName();
                fragment = FragPhoto.newInstance(namefolder);
                if (fragment != null) {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frContain, fragment);
                    ft.addToBackStack("tag");
                    ft.commit();
                }
            }
        });
        return v;
    }

    private void getUI(View v) {
        grAlbum = (ListView) v.findViewById(R.id.lvAlbum);
//        prLoading = (ProgressLoading) v.findViewById(R.id.prLoading);
    }


}
