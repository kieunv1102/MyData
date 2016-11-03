package com.entertaiment.photo.christmassticker.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.entertaiment.photo.christmassticker.R;

import pagertabstrip.entertaiment.com.libstabstrip.PagerSlidingTabStrip;

public class StickerActivity extends FragmentActivity implements StickerFragment.OnPassSticker{
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);
    }

    @Override
    public void onPass(String sticker) {
        Intent intent=new Intent(this,SetPhotoActivity.class);
        intent.putExtra("sticker",sticker);
        setResult(12, intent);
        finish();
    }

    public class MyPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider{

        final int PAGE_COUNT = 6;
        private int tabIcons[] = {R.drawable.icon1, R.drawable.icon2, R.drawable.icon3,R.drawable.icon4, R.drawable.icon5, R.drawable.icon6};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            return StickerFragment.newInstance(position + 1);
        }

        @Override
        public int getPageIconResId(int position) {
            return tabIcons[position];
        }
    }
}
