package com.entertaiment.photo.christmassticker.screen;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.entertaiment.photo.christmassticker.R;
import com.entertaiment.photo.christmassticker.adapters.HlvButtonAdapter;
import com.entertaiment.photo.christmassticker.adapters.HlvFilterAdapter;
import com.entertaiment.photo.christmassticker.filter.GPUImageFilterTools;
import com.entertaiment.photo.christmassticker.horizontallisview.HorizontalListView;
import com.entertaiment.photo.christmassticker.models.FilterBackground;
import com.entertaiment.photo.christmassticker.multiSticker.SingleTouchView;
import com.entertaiment.photo.christmassticker.multiSticker.SingleViewCallBack;
import com.entertaiment.photo.christmassticker.multitouchs.MultiTouchListener;
import com.entertaiment.photo.christmassticker.utils.Var;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;

public class SetPhotoActivity extends Activity implements SingleViewCallBack, MultiTouchListener.RemoveView, View.OnClickListener {
    GPUImageView imvDisplay;
    SeekBar sbradius;
    Button btnSticker,btnText,btnFilter,btnShare,btnSave;
    HorizontalListView hlvFilterPhoto;
    HlvButtonAdapter hlvButtonAdapter;
    HlvFilterAdapter hlvFilterAdapter;
    private GPUImageFilterTools.FilterAdjuster mFilterAdjuster;
    private static GPUImageFilter filter;
    GPUImage mGPUImage;
    private File file;
    File filepath;
    private Bitmap mBitmap;
    int prFilter;
    FrameLayout frsetSticker, frlSetontouch;
    private List<SingleTouchView> listSticker;
    SingleTouchView img;
    private MultiTouchListener multiTouchListener;
    File fieSave;
    boolean ckFilter = true;
    boolean ckBitmap = true;
    boolean checkSave = false;
    public static final int KEY_REQUEST_SHAREINTENT = 3;
    public static final int KEY_REQUEST_GALLERY = 0x001;
    public static final int KEY_REQUEST_CAMERA = 0x002;
    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_photo);
        listSticker = new ArrayList<SingleTouchView>();
        Bundle myBundle = getIntent().getExtras();
        String path = myBundle.getString("path");
        filepath = new File(path);
        multiTouchListener = new MultiTouchListener(this, 1);
        multiTouchListener.setRemoveView(this);

        frsetSticker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                for (SingleTouchView localView : listSticker) {
                    localView.setEditable(false);
                }
                return true;
            }
        });


        mBitmap = BitmapFactory.decodeFile(filepath.getAbsolutePath());
        imvDisplay.setImage(mBitmap);


        hlvFilterAdapter = new HlvFilterAdapter(SetPhotoActivity.this, FilterBackground.getSticker1());
        hlvFilterPhoto.setAdapter(hlvFilterAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if (resultCode == 10) {
                String addText = data.getStringExtra("add_text");
                int colorText = data.getIntExtra("color_text", 0xffffff00);
                String fonttext = data.getStringExtra("font_text");
                TextView tvText = new TextView(SetPhotoActivity.this);
                tvText.setPadding(10, 10, 10, 10);
                tvText.setId(1);
                tvText.setText(addText);
                tvText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                tvText.setTextColor(colorText);
                tvText.setTypeface(Typeface.createFromAsset(getAssets(), fonttext));
                tvText.setOnTouchListener(multiTouchListener);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                frsetSticker.addView(tvText, layoutParams);

            }
            if (resultCode == 12) {
                String sticker = data.getStringExtra("sticker");
                img = new SingleTouchView(SetPhotoActivity.this);
                img.setOnSignleViewListener(SetPhotoActivity.this);
                img.setImageAssets(sticker);
                img.setRootView(frsetSticker);
                frsetSticker.addView(img);
                listSticker.add(img);
            }

        }


    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        imvDisplay = (GPUImageView) findViewById(R.id.imvDisplay);
        sbradius = (SeekBar) findViewById(R.id.sbradius);

        btnSticker = (Button) findViewById(R.id.btnSticker);
        btnText = (Button) findViewById(R.id.btnText);
        btnFilter = (Button) findViewById(R.id.btnFilter);
        btnShare = (Button) findViewById(R.id.btnShare);
        btnSave = (Button) findViewById(R.id.btnSave);

        btnSticker.setOnClickListener(this);
        btnText.setOnClickListener(this);
        btnFilter.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        hlvFilterPhoto = (HorizontalListView) findViewById(R.id.hlv_filter_photo);
        frsetSticker = (FrameLayout) findViewById(R.id.frsetSticker);

        hlvFilterPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SetPhotoActivity.this,""+position,Toast.LENGTH_SHORT).show();
                filter = GPUImageFilterTools.createFilterForType(SetPhotoActivity.this, GPUImageFilterTools.initFilter().get(position));
                mFilterAdjuster = new GPUImageFilterTools.FilterAdjuster(filter);

                imvDisplay.setFilter(filter);
                imvDisplay.requestRender();
                findViewById(R.id.sbradius).setVisibility(
                        mFilterAdjuster.canAdjust() ? View.VISIBLE : View.GONE);
            }
        });
        sbradius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                prFilter = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mFilterAdjuster != null) {
                    mFilterAdjuster.adjust(prFilter);
                }
                imvDisplay.requestRender();
            }
        });
    }


    public String getPath(Uri uri) {
        String[] arrayOfString = {MediaStore.Images.ImageColumns.DATA};
        Cursor localCursor = getContentResolver().query(uri, arrayOfString, null, null, null);
        localCursor.moveToFirst();
        String str = localCursor.getString(localCursor.getColumnIndex(arrayOfString[0]));
        return str;
    }

    public Bitmap blur(Bitmap image) {
        if (null == image) return null;
        Bitmap outputBitmap = Bitmap.createBitmap(image);
        final RenderScript renderScript = RenderScript.create(this);
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, image);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);

        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        theIntrinsic.setRadius(25);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }

    @Override
    public void onTouchListener(SingleTouchView view) {
        for (SingleTouchView localView : listSticker) {
            if (!localView.equals(view)) {
                localView.setEditable(false);
            }
        }
        view.bringToFront();
    }

    @Override
    public void onRemoveView(View v) {
        frsetSticker.removeView(v);
    }

    private void refreshGallery(File file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(file));
        sendBroadcast(mediaScanIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSticker:
                hlvFilterPhoto.setVisibility(v.GONE);
                sbradius.setVisibility(v.GONE);
                Intent isticker = new Intent(SetPhotoActivity.this, StickerActivity.class);
                startActivityForResult(isticker, 2);
                break;
            case R.id.btnText:
                hlvFilterPhoto.setVisibility(v.GONE);
                sbradius.setVisibility(v.GONE);
                Intent itext = new Intent(SetPhotoActivity.this, TextActivity.class);
                startActivityForResult(itext, 2);
                break;
            case R.id.btnFilter:
                if (ckFilter == true) {
                    hlvFilterPhoto.setVisibility(v.VISIBLE);
                    ckFilter = false;
                } else if (ckFilter == false) {
                    hlvFilterPhoto.setVisibility(v.GONE);
                    sbradius.setVisibility(v.GONE);
                    ckFilter = true;
                }
                break;
            case R.id.btnShare:
                hlvFilterPhoto.setVisibility(v.GONE);
                sbradius.setVisibility(v.GONE);
                if (checkSave == false) {
                    Toast.makeText(SetPhotoActivity.this, "You must save photo before share", Toast.LENGTH_SHORT).show();
                } else if (checkSave == true) {
                    Intent intentShare = new Intent("android.intent.action.SEND");
                    intentShare.setType("image/*");
                    intentShare.putExtra("android.intent.extra.STREAM", Uri.fromFile(fieSave));
                    startActivityForResult(Intent.createChooser(intentShare, "Share Image"), KEY_REQUEST_SHAREINTENT);
                }
                break;

            case R.id.btnSave:
                checkSave = true;
                hlvFilterPhoto.setVisibility(v.GONE);
                sbradius.setVisibility(v.GONE);
                imvDisplay.saveToPictures(Var.folder, "thumb.png", new GPUImageView.OnPictureSavedListener() {
                    @Override
                    public void onPictureSaved(Uri uri) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(SetPhotoActivity.this.getContentResolver(), uri);
                            bitmap = bitmap.copy(bitmap.getConfig(), true);
                            frsetSticker.setDrawingCacheEnabled(true);
                            Bitmap bitmap2 = frsetSticker.getDrawingCache();

                            Canvas canvas = new Canvas(bitmap);
                            canvas.drawBitmap(bitmap2, new Matrix(), null);

                            String root = Environment.getExternalStorageDirectory().toString();
                            File newDir = new File(root + "/PICTURES/ChristmasSticker");
                            newDir.mkdirs();
                            String fotoname = "photo_" + Calendar.getInstance().getTime().getTime() + ".jpg";
                            fieSave = new File(newDir, fotoname);
                            if (fieSave.exists()) fieSave.delete();
                            try {
                                FileOutputStream out = new FileOutputStream(fieSave);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                                out.flush();
                                out.close();
                                Toast.makeText(getApplicationContext(), "Images saved to ChristmasSticker", Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                e.fillInStackTrace();
                            }
                            refreshGallery(fieSave);
                            String thum = Environment.getExternalStorageDirectory().toString() + "/PICTURES/ChristmasSticker/thumb.png";
                            File file = new File(thum);
                            file.delete();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }
    }
}
