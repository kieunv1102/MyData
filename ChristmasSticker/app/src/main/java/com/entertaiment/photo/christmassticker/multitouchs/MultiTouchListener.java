package com.entertaiment.photo.christmassticker.multitouchs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.entertaiment.photo.christmassticker.R;
import com.entertaiment.photo.christmassticker.adapters.ColorAdapter;
import com.entertaiment.photo.christmassticker.horizontallisview.HorizontalListView;
import com.entertaiment.photo.christmassticker.utils.Var;

import java.util.ArrayList;

@SuppressLint("ClickableViewAccessibility")
public class MultiTouchListener implements OnTouchListener {

    private static final int INVALID_POINTER_ID = -1;
    public boolean isRotateEnabled = true;
    public boolean isTranslateEnabled = true;
    public boolean isScaleEnabled = true;
    public float minimumScale = 0.5f;
    public float maximumScale = 10.0f;
    private int mActivePointerId = INVALID_POINTER_ID;
    private float mPrevX;
    private float mPrevY;
    private ScaleGestureDetector mScaleGestureDetector;
    private static final long DOUBLE_CLICK_TIME_DELTA = 300;// milliseconds
    private int ID;
    private Activity mContext;
    int screenHight;
    int screenWidth;
    long lastClickTime = 0;
    public VisibleView mVisibleView;
    public RemoveView mRemoveView;
    private ColorSticker mColorSticker;
    public int tagImage;
    private PopupWindow pwMyPopWindow;
    private TextView imgSticker;
    private ArrayList<String> arrColor;

    public MultiTouchListener(Activity context, int id) {
        mScaleGestureDetector = new ScaleGestureDetector(new ScaleGestureListener());
        this.ID = id;
        this.mContext = context;
        DisplayMetrics displaymetrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;
    }

    private static float adjustAngle(float degrees) {
        if (degrees > 180.0f) {
            degrees -= 360.0f;
        } else if (degrees < -180.0f) {
            degrees += 360.0f;
        }

        return degrees;
    }

    private static void move(View view, TransformInfo info) {
        computeRenderOffset(view, info.pivotX, info.pivotY);
        adjustTranslation(view, info.deltaX, info.deltaY);
        float scale = view.getScaleX() * info.deltaScale;
        scale = Math.max(info.minimumScale, Math.min(info.maximumScale, scale));
        view.setScaleX(scale);
        view.setScaleY(scale);

        float rotation = adjustAngle(view.getRotation() + info.deltaAngle);
        view.setRotation(rotation);
    }

    private static void adjustTranslation(View view, float deltaX, float deltaY) {
        float[] deltaVector = {deltaX, deltaY};
        view.getMatrix().mapVectors(deltaVector);
        view.setTranslationX(view.getTranslationX() + deltaVector[0]);
        view.setTranslationY(view.getTranslationY() + deltaVector[1]);
    }

    private static void computeRenderOffset(View view, float pivotX, float pivotY) {
        if (view.getPivotX() == pivotX && view.getPivotY() == pivotY) {
            return;
        }

        float[] prevPoint = {0.0f, 0.0f};
        view.getMatrix().mapPoints(prevPoint);

        view.setPivotX(pivotX);
        view.setPivotY(pivotY);

        float[] currPoint = {0.0f, 0.0f};
        view.getMatrix().mapPoints(currPoint);

        float offsetX = currPoint[0] - prevPoint[0];
        float offsetY = currPoint[1] - prevPoint[1];

        view.setTranslationX(view.getTranslationX() - offsetX);
        view.setTranslationY(view.getTranslationY() - offsetY);
    }

    long touchStartTime = 0;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(view, event);
        if (!isTranslateEnabled) {
            return true;
        }
        int action = event.getAction();

        switch (action & event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN: {
                view.bringToFront();
                touchStartTime = System.currentTimeMillis();


                mPrevX = event.getX();
                mPrevY = event.getY();
                mActivePointerId = event.getPointerId(0);
                break;
            }
            case MotionEvent.ACTION_MOVE: {

                int pointerIndex = event.findPointerIndex(mActivePointerId);
                if (pointerIndex != -1) {
                    float currX = event.getX(pointerIndex);
                    float currY = event.getY(pointerIndex);
//                    if ( (currX <= 0 || currY >= screenWidth) ||
//                            (currY <= 0 || currY >= (screenHight/2)) )
//                        break;
                    if (!mScaleGestureDetector.isInProgress()) {
                        adjustTranslation(view, currX - mPrevX, currY - mPrevY);
                    }
                }
                break;
            }

            case MotionEvent.ACTION_CANCEL:
                mActivePointerId = INVALID_POINTER_ID;
                break;

            case MotionEvent.ACTION_UP:
                view.setBackgroundColor(Color.parseColor("#00000000"));
                mActivePointerId = INVALID_POINTER_ID;
                if((Integer)view.getId()==1){
                    if (touchStartTime + 200 > System.currentTimeMillis()) {
                        touchStartTime = 0;
                        iniPopup(view);
                        if (pwMyPopWindow.isShowing()) {
                            pwMyPopWindow.dismiss();
                        } else {
                            pwMyPopWindow.showAsDropDown(view);
                        }
                    }
                }

                break;

            case MotionEvent.ACTION_POINTER_UP: {
                view.setBackgroundColor(Color.parseColor("#00000000"));
                int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                int pointerId = event.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {
                    int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mPrevX = event.getX(newPointerIndex);
                    mPrevY = event.getY(newPointerIndex);
                    mActivePointerId = event.getPointerId(newPointerIndex);
                }
                break;
            }
        }

        return true;
    }

    private class ScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        private float mPivotX;
        private float mPivotY;
        private Vector2D mPrevSpanVector = new Vector2D();

        @Override
        public boolean onScaleBegin(View view, ScaleGestureDetector detector) {
            mPivotX = detector.getFocusX();
            mPivotY = detector.getFocusY();
            mPrevSpanVector.set(detector.getCurrentSpanVector());
            return true;
        }

        @Override
        public boolean onScale(View view, ScaleGestureDetector detector) {
            TransformInfo info = new TransformInfo();
            info.deltaScale = isScaleEnabled ? detector.getScaleFactor() : 1.0f;
            info.deltaAngle = isRotateEnabled ? Vector2D.getAngle(mPrevSpanVector, detector.getCurrentSpanVector()) : 0.0f;
            info.deltaX = isTranslateEnabled ? detector.getFocusX() - mPivotX : 0.0f;
            info.deltaY = isTranslateEnabled ? detector.getFocusY() - mPivotY : 0.0f;
            info.pivotX = mPivotX;
            info.pivotY = mPivotY;
            info.minimumScale = minimumScale;
            info.maximumScale = maximumScale;

            move(view, info);
            return false;
        }
    }


    private class TransformInfo {

        public float deltaX;
        public float deltaY;
        public float deltaScale;
        public float deltaAngle;
        public float pivotX;
        public float pivotY;
        public float minimumScale;
        public float maximumScale;
    }

    public void setVisibleCustom(VisibleView visibleView) {
        this.mVisibleView = visibleView;
    }
    public void setRemoveView(RemoveView removeView) {
        this.mRemoveView = removeView;
    }
    public void setColorSticker(ColorSticker colorSticker) {
        this.mColorSticker = colorSticker;
    }

    public interface VisibleView {
        public void onVisibleView(View v);
    }
    public interface RemoveView {
        public void onRemoveView(View v);
    }
    public interface ColorSticker {
        public void onColorSticker(View v, int color);
    }
    int progress=0;
    private void iniPopup(final View v) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.color_sticker_layout,null);
        HorizontalListView lvColor=(HorizontalListView) layout.findViewById(R.id.lvColorSticker);
        arrColor= Var.initColors();
        ColorAdapter colorAdapter=new ColorAdapter(mContext, Var.initColors());
        lvColor.setAdapter(colorAdapter);
//        SeekBar sbBlur=(SeekBar) layout.findViewById(R.id.sbStickerBlur);
        Button btnRemoveSticker = (Button) layout.findViewById(R.id.btnRemoveSticker);
        Button btnInVisible = (Button) layout.findViewById(R.id.btnOkSticker);
        pwMyPopWindow = new PopupWindow(mContext);
        pwMyPopWindow.setContentView(layout);
        pwMyPopWindow.setFocusable(true);
        pwMyPopWindow.setWidth((int) (screenWidth * 0.6));
        pwMyPopWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        pwMyPopWindow.setBackgroundDrawable(new BitmapDrawable());
        pwMyPopWindow.setOutsideTouchable(true);
        imgSticker=(TextView)v;
//        sbBlur.setProgress((int) (imgSticker.getAlpha() * 10));
        btnRemoveSticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRemoveView.onRemoveView(imgSticker);
                pwMyPopWindow.dismiss();
            }
        });
        btnInVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwMyPopWindow.dismiss();
            }
        });
        lvColor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imgSticker.setTextColor(Color.parseColor(arrColor.get(position)));
            }
        });
//        sbBlur.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int p, boolean fromUser) {
//                    progress=p;
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                imgSticker.setAlpha(progress / 10.0f);
//            }
//        });

    }
}