package com.entertaiment.photo.christmassticker.multiSticker;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.entertaiment.photo.christmassticker.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SingleTouchView extends View {
    public static final float MAX_SCALE = 4.0f;
    public static final float MIN_SCALE = 0.1f;
    public static final int LEFT_TOP = 0;
    public static final int RIGHT_TOP = 1;
    public static final int RIGHT_BOTTOM = 2;
    public static final int LEFT_BOTTOM = 3;

    public static final int DEFAULT_FRAME_PADDING = 0;
    public static final int DEFAULT_FRAME_WIDTH = 2;
    public static final int DEFAULT_FRAME_COLOR = Color.WHITE;
    public static final float DEFAULT_SCALE = 1.0f;
    public static final float DEFAULT_DEGREE = 0;
    public static final int DEFAULT_CONTROL_LOCATION = RIGHT_BOTTOM;
    public static final int DEFAULT_DELETE_LOCATION = LEFT_TOP;
    public static final int DEFAULT_FLIP_LOCATION = RIGHT_TOP;

    public static final boolean DEFAULT_EDITABLE = true;
    public static final int DEFAULT_OTHER_DRAWABLE_WIDTH = 50;
    public static final int DEFAULT_OTHER_DRAWABLE_HEIGHT = 50;

    public SingleViewCallBack callback;
    private Bitmap mBitmap;
    private PointF mCenterPoint = new PointF();

    private int mViewWidth, mViewHeight;

    private float mDegree = DEFAULT_DEGREE;

    private float mScale = DEFAULT_SCALE;

    private Matrix matrix = new Matrix();

    private int mViewPaddingLeft;

    private int mViewPaddingTop;

    private Point mLTPoint;
    private Point mRTPoint;
    private Point mRBPoint;
    private Point mLBPoint;

    private Point mControlPoint = new Point();

    private Point mDeletePoint = new Point();

    private Point mFlipPoint = new Point();

    private Drawable controlDrawable;
    private Drawable flipDrawable;

    private Drawable deleteDrawable;
    private int mDrawableWidth, mDrawableHeight;
    private boolean deleteClicking = false;
    private boolean flipClicking = false;
    private Path mPath = new Path();

    private Paint mPaint;

    public static final int STATUS_INIT = 0;

    public static final int STATUS_DRAG = 1;

    public static final int STATUS_ROTATE_ZOOM = 2;

    public static final int STATUS_DELETE = 3;

    public static final int STATUS_FLIP = 4;


    private int mStatus = STATUS_INIT;

    private int framePadding = DEFAULT_FRAME_PADDING;

    private int frameColor = DEFAULT_FRAME_COLOR;

    private int frameWidth = DEFAULT_FRAME_WIDTH;

    private boolean isEditable = DEFAULT_EDITABLE;

    private DisplayMetrics metrics;


    private PointF mPreMovePointF = new PointF();
    private PointF mCurMovePointF = new PointF();

    private int offsetX;
    private int offsetY;

    private int controlLocation = DEFAULT_CONTROL_LOCATION;
    private int deleteLocation = DEFAULT_DELETE_LOCATION;
    private int flipLocation = DEFAULT_FLIP_LOCATION;
    private Context mContext;

    public SingleTouchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
    }

    public SingleTouchView(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public SingleTouchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        obtainStyledAttributes(attrs);
        init();
    }

    private void obtainStyledAttributes(AttributeSet attrs) {
        metrics = getContext().getResources().getDisplayMetrics();
        framePadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_FRAME_PADDING, metrics);
        frameWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_FRAME_WIDTH, metrics);

        TypedArray mTypedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SingleTouchView);

        Drawable srcDrawble = mTypedArray.getDrawable(R.styleable.SingleTouchView_src);
        mBitmap = drawable2Bitmap(srcDrawble);

        framePadding = mTypedArray.getDimensionPixelSize(R.styleable.SingleTouchView_framePadding, framePadding);
        frameWidth = mTypedArray.getDimensionPixelSize(R.styleable.SingleTouchView_frameWidth, frameWidth);
        frameColor = mTypedArray.getColor(R.styleable.SingleTouchView_frameColor, DEFAULT_FRAME_COLOR);
        mScale = mTypedArray.getFloat(R.styleable.SingleTouchView_scale, DEFAULT_SCALE);
        mDegree = mTypedArray.getFloat(R.styleable.SingleTouchView_degree, DEFAULT_DEGREE);
        controlDrawable = mTypedArray.getDrawable(R.styleable.SingleTouchView_controlDrawable);
        deleteDrawable = mTypedArray.getDrawable(R.styleable.SingleTouchView_deleteDrawable);
        flipDrawable = mTypedArray.getDrawable(R.styleable.SingleTouchView_flipDrawable);
        controlLocation = mTypedArray.getInt(R.styleable.SingleTouchView_controlLocation, DEFAULT_CONTROL_LOCATION);
        deleteLocation = mTypedArray.getInt(R.styleable.SingleTouchView_deleteDrawable, DEFAULT_DELETE_LOCATION);
        flipLocation = mTypedArray.getInt(R.styleable.SingleTouchView_flipDrawable, DEFAULT_FLIP_LOCATION);
        isEditable = mTypedArray.getBoolean(R.styleable.SingleTouchView_editable, DEFAULT_EDITABLE);

        mTypedArray.recycle();

    }


    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(frameColor);
        mPaint.setStrokeWidth(frameWidth);
        mPaint.setStyle(Style.STROKE);

        if (controlDrawable == null) {
            controlDrawable = getContext().getResources().getDrawable(R.drawable.a5);
        }
        if (deleteDrawable == null) {
            deleteDrawable = getContext().getResources().getDrawable(R.drawable.a3);
        }
        if (flipDrawable == null) {
            flipDrawable = getContext().getResources().getDrawable(R.drawable.a1);
        }
        mDrawableWidth = controlDrawable.getIntrinsicWidth();
        mDrawableHeight = controlDrawable.getIntrinsicHeight();
        transformDraw();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        ViewGroup mViewGroup = (ViewGroup) getParent();
        if (null != mViewGroup) {
            int parentWidth = mViewGroup.getWidth();
            int parentHeight = mViewGroup.getHeight();
            mCenterPoint.set(parentWidth / 2, parentHeight / 2);
        }
    }


    private void adjustLayout() {
        int actualWidth = mViewWidth + mDrawableWidth;
        int actualHeight = mViewHeight + mDrawableHeight;

        int newPaddingLeft = (int) (mCenterPoint.x - actualWidth / 2);
        int newPaddingTop = (int) (mCenterPoint.y - actualHeight / 2);

        if (mViewPaddingLeft != newPaddingLeft || mViewPaddingTop != newPaddingTop) {
         /*   if (deleteClicking) {
//                deleteClicking = false;
                newPaddingLeft = mViewPaddingLeft;
                newPaddingTop = mViewPaddingTop;

            } else if (addclicking) {
                addclicking = false;
                newPaddingLeft = mViewPaddingLeft;
                newPaddingTop = mViewPaddingTop;
            } else {*/
            mViewPaddingLeft = newPaddingLeft;
            mViewPaddingTop = newPaddingTop;
            //            }
        }

        layout(newPaddingLeft, newPaddingTop, newPaddingLeft + actualWidth, newPaddingTop + actualHeight);
    }


    public void setImageBitamp(Bitmap bitmap) {
        this.mBitmap = bitmap;
        transformDraw();
    }


    public void setImageDrawable(Drawable drawable) {
        this.mBitmap = drawable2Bitmap(drawable);
        transformDraw();
    }

    public void setImageAssets(String text) {
        InputStream ims = null;
        try {
            ims = mContext.getAssets().open(text);
            Drawable d = Drawable.createFromStream(ims, null);
            this.mBitmap = drawable2Bitmap(d);
            transformDraw();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap drawable2Bitmap(Drawable drawable) {
        try {
            if (drawable == null) {
                return null;
            }

            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }

            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            Bitmap bitmap = Bitmap.createBitmap(intrinsicWidth <= 0 ? DEFAULT_OTHER_DRAWABLE_WIDTH : intrinsicWidth,
                    intrinsicHeight <= 0 ? DEFAULT_OTHER_DRAWABLE_HEIGHT : intrinsicHeight, Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }

    }

    public void setImageResource(int resId) {
        Drawable drawable = getContext().getResources().getDrawable(resId);
        setImageDrawable(drawable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("canvas", "count");

        if (mBitmap == null) return;
        canvas.drawBitmap(mBitmap, matrix, mPaint);


        if (isEditable) {
            mPath.reset();
            mPath.moveTo(mLTPoint.x, mLTPoint.y);
            mPath.lineTo(mRTPoint.x, mRTPoint.y);
            mPath.lineTo(mRBPoint.x, mRBPoint.y);
            mPath.lineTo(mLBPoint.x, mLBPoint.y);
            mPath.lineTo(mLTPoint.x, mLTPoint.y);
            mPath.lineTo(mRTPoint.x, mRTPoint.y);
            canvas.drawPath(mPath, mPaint);

            controlDrawable.setBounds(mControlPoint.x - mDrawableWidth / 2, mControlPoint.y - mDrawableHeight / 2,
                    mControlPoint.x + mDrawableWidth / 2, mControlPoint.y + mDrawableHeight / 2);
            controlDrawable.draw(canvas);

            deleteDrawable.setBounds(mDeletePoint.x - mDrawableWidth / 2, mDeletePoint.y - mDrawableHeight / 2,
                    mDeletePoint.x + mDrawableWidth / 2, mDeletePoint.y + mDrawableHeight / 2);
            deleteDrawable.draw(canvas);

            flipDrawable.setBounds(mFlipPoint.x - mDrawableWidth / 2, mFlipPoint.y - mDrawableHeight / 2,
                    mFlipPoint.x + mDrawableWidth / 2, mFlipPoint.y + mDrawableHeight / 2);
            flipDrawable.draw(canvas);

        }

        adjustLayout();


    }


    private void transformDraw() {
        if (mBitmap == null) return;
        int bitmapWidth = (int) (mBitmap.getWidth() * mScale);
        int bitmapHeight = (int) (mBitmap.getHeight() * mScale);
        computeRect(-framePadding, -framePadding, bitmapWidth + framePadding, bitmapHeight + framePadding, mDegree);

        matrix.setScale(mScale, mScale);
        matrix.postRotate(mDegree % 360, bitmapWidth / 2, bitmapHeight / 2);
        matrix.postTranslate(offsetX + mDrawableWidth / 2, offsetY + mDrawableHeight / 2);
        adjustLayout();
    }

    private float x, y;
    private float newX, newY;
    private float mScaleF = 0f;

    public boolean onTouchEvent(MotionEvent event) {
 /*       if (!isEditable) {
            return super.onTouchEvent(event);
        }*/
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                callback.onTouchListener(this);
                setEditable(true);
                this.bringToFront();
                //                adjustLayout();
                x = event.getX();
                y = event.getY();
                mPreMovePointF.set(event.getX() + mViewPaddingLeft, event.getY() + mViewPaddingTop);
                flipClicking = false;
                mStatus = JudgeStatus(event.getX(), event.getY());
                if (mStatus == STATUS_DELETE) {
                    deleteClicking = true;
                } else if (mStatus == STATUS_FLIP) {
                    flipClicking = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                x = event.getX();
                y = event.getY();

                if (deleteClicking) {
//                    mStatus = JudgeStatus(event.getX(), event.getY());
                    if (mStatus == STATUS_DELETE) {
                        deleteSticker();
                        deleteClicking = false;
                    }

                }
                if (flipClicking) {
                    if (mStatus == STATUS_FLIP) {
                        flipSticker();
                    }
                }
                mStatus = STATUS_INIT;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (deleteClicking) {
                    deleteSticker();
                    deleteClicking = false;
                }
                this.bringToFront();
                break;
            case MotionEvent.ACTION_MOVE:
                this.bringToFront();
                mCurMovePointF.set(event.getX() + mViewPaddingLeft, event.getY() + mViewPaddingTop);
                if (mStatus == STATUS_ROTATE_ZOOM) {
                    newX = event.getX() - x;
                    newY = event.getY() - y;

                    float scale = 1f;

                    int halfBitmapWidth = mBitmap.getWidth() / 2;
                    int halfBitmapHeight = mBitmap.getHeight() / 2;

                    float bitmapToCenterDistance = (float) Math
                            .sqrt(halfBitmapWidth * halfBitmapWidth + halfBitmapHeight * halfBitmapHeight);

                    float moveToCenterDistance = distance4PointF(mCenterPoint, mCurMovePointF);

                    scale = moveToCenterDistance / bitmapToCenterDistance;


                    if (scale <= MIN_SCALE) {
                        scale = MIN_SCALE;
                    } else if (scale >= MAX_SCALE) {
                        scale = MAX_SCALE;
                    }
                    double a = distance4PointF(mCenterPoint, mPreMovePointF);
                    double b = distance4PointF(mPreMovePointF, mCurMovePointF);
                    double c = distance4PointF(mCenterPoint, mCurMovePointF);

                    double cosb = (a * a + c * c - b * b) / (2 * a * c);

                    if (cosb >= 1) {
                        cosb = 1f;
                    }

                    double radian = Math.acos(cosb);
                    float newDegree = (float) radianToDegree(radian);

                    PointF centerToProMove = new PointF((mPreMovePointF.x - mCenterPoint.x), (mPreMovePointF.y - mCenterPoint.y));

                    PointF centerToCurMove = new PointF((mCurMovePointF.x - mCenterPoint.x), (mCurMovePointF.y - mCenterPoint.y));

                    float result = centerToProMove.x * centerToCurMove.y - centerToProMove.y * centerToCurMove.x;

                    if (result < 0) {
                        newDegree = -newDegree;
                    }

                    mDegree = mDegree + newDegree;
                    //                    if (getTypeScreen().equals(size_normal)) {

                    if (Math.abs(newX) < 1.5f && Math.abs(newY) < 1.5f) {
                        mScaleF = scale - mScale;
                    }
                    mScale = Math.abs(scale - mScaleF);
                    transformDraw();
                } else if (mStatus == STATUS_DRAG) {
                    mCenterPoint.x += mCurMovePointF.x - mPreMovePointF.x;
                    mCenterPoint.y += mCurMovePointF.y - mPreMovePointF.y;


                    adjustLayout();
                } else if (mStatus == STATUS_DELETE) {
                    deleteClicking = true;
                } else if (mStatus == STATUS_FLIP) {
                    flipClicking = true;
                }


                mPreMovePointF.set(mCurMovePointF);
                break;
        }
        return true;
    }


    private void computeRect(int left, int top, int right, int bottom, float degree) {
        Point lt = new Point(left, top);
        Point rt = new Point(right, top);
        Point rb = new Point(right, bottom);
        Point lb = new Point(left, bottom);
        Point cp = new Point((left + right) / 2, (top + bottom) / 2);
        mLTPoint = obtainRoationPoint(cp, lt, degree);
        mRTPoint = obtainRoationPoint(cp, rt, degree);
        mRBPoint = obtainRoationPoint(cp, rb, degree);
        mLBPoint = obtainRoationPoint(cp, lb, degree);

        int maxCoordinateX = getMaxValue(mLTPoint.x, mRTPoint.x, mRBPoint.x, mLBPoint.x);
        int minCoordinateX = getMinValue(mLTPoint.x, mRTPoint.x, mRBPoint.x, mLBPoint.x);
        ;

        mViewWidth = maxCoordinateX - minCoordinateX;


        int maxCoordinateY = getMaxValue(mLTPoint.y, mRTPoint.y, mRBPoint.y, mLBPoint.y);
        int minCoordinateY = getMinValue(mLTPoint.y, mRTPoint.y, mRBPoint.y, mLBPoint.y);

        mViewHeight = maxCoordinateY - minCoordinateY;


        Point viewCenterPoint = new Point((maxCoordinateX + minCoordinateX) / 2, (maxCoordinateY + minCoordinateY) / 2);

        offsetX = mViewWidth / 2 - viewCenterPoint.x;
        offsetY = mViewHeight / 2 - viewCenterPoint.y;


        int halfDrawableWidth = mDrawableWidth / 2;
        int halfDrawableHeight = mDrawableHeight / 2;

        mLTPoint.x += (offsetX + halfDrawableWidth);
        mRTPoint.x += (offsetX + halfDrawableWidth);
        mRBPoint.x += (offsetX + halfDrawableWidth);
        mLBPoint.x += (offsetX + halfDrawableWidth);

        mLTPoint.y += (offsetY + halfDrawableHeight);
        mRTPoint.y += (offsetY + halfDrawableHeight);
        mRBPoint.y += (offsetY + halfDrawableHeight);
        mLBPoint.y += (offsetY + halfDrawableHeight);

        mControlPoint = LocationToPoint(controlLocation);
        mDeletePoint = LocationToPoint(deleteLocation);
        mFlipPoint = LocationToPoint(flipLocation);
    }


    private Point LocationToPoint(int location) {
        switch (location) {
            case LEFT_TOP:
                return mLTPoint;
            case RIGHT_TOP:
                return mRTPoint;
            case RIGHT_BOTTOM:
                return mRBPoint;
            case LEFT_BOTTOM:
                return mLBPoint;
        }
        return mLTPoint;
    }


    public int getMaxValue(Integer... array) {
        List<Integer> list = Arrays.asList(array);
        Collections.sort(list);
        return list.get(list.size() - 1);
    }

    public int getMinValue(Integer... array) {
        List<Integer> list = Arrays.asList(array);
        Collections.sort(list);
        return list.get(0);
    }


    public static Point obtainRoationPoint(Point center, Point source, float degree) {
        Point disPoint = new Point();
        disPoint.x = source.x - center.x;
        disPoint.y = source.y - center.y;

        double originRadian = 0;

        double originDegree = 0;

        double resultDegree = 0;

        double resultRadian = 0;

        Point resultPoint = new Point();

        double distance = Math.sqrt(disPoint.x * disPoint.x + disPoint.y * disPoint.y);
        if (disPoint.x == 0 && disPoint.y == 0) {
            return center;
        } else if (disPoint.x >= 0 && disPoint.y >= 0) {
            originRadian = Math.asin(disPoint.y / distance);

        } else if (disPoint.x < 0 && disPoint.y >= 0) {
            originRadian = Math.asin(Math.abs(disPoint.x) / distance);
            originRadian = originRadian + Math.PI / 2;

        } else if (disPoint.x < 0 && disPoint.y < 0) {
            originRadian = Math.asin(Math.abs(disPoint.y) / distance);
            originRadian = originRadian + Math.PI;
        } else if (disPoint.x >= 0 && disPoint.y < 0) {
            originRadian = Math.asin(disPoint.x / distance);
            originRadian = originRadian + Math.PI * 3 / 2;
        }

        originDegree = radianToDegree(originRadian);
        resultDegree = originDegree + degree;

        resultRadian = degreeToRadian(resultDegree);

        resultPoint.x = (int) Math.round(distance * Math.cos(resultRadian));
        resultPoint.y = (int) Math.round(distance * Math.sin(resultRadian));
        resultPoint.x += center.x;
        resultPoint.y += center.y;

        return resultPoint;
    }

    public static double radianToDegree(double radian) {
        return radian * 180 / Math.PI;
    }


    public static double degreeToRadian(double degree) {
        return degree * Math.PI / 180;
    }

    private int JudgeStatus(float x, float y) {
        PointF touchPoint = new PointF(x, y);
        PointF controlPointF = new PointF(mControlPoint);

        PointF deletePointF = new PointF(mDeletePoint);

        PointF flipPointF = new PointF(mFlipPoint);


        float distanceToControl = distance4PointF(touchPoint, controlPointF);
        float distenceToDelete = distance4PointF(touchPoint, deletePointF);
        float distenceToFlip = distance4PointF(touchPoint, flipPointF);

        if (distanceToControl < Math.min(mDrawableWidth, mDrawableHeight)) {
            return STATUS_ROTATE_ZOOM;
        }
        if (distenceToDelete < Math.min(mDrawableWidth, mDrawableHeight)) {
            return STATUS_DELETE;
        }
        if (distenceToFlip < Math.min(mDrawableWidth, mDrawableHeight)) {
            return STATUS_FLIP;
        }


        return STATUS_DRAG;

    }


    public float getImageDegree() {
        return mDegree;
    }

    public void setImageDegree(float degree) {
        if (this.mDegree != degree) {
            this.mDegree = degree;
            transformDraw();
        }
    }

    public float getImageScale() {
        return mScale;
    }

    public void setImageScale(float scale) {
        if (this.mScale != scale) {
            this.mScale = scale;
            transformDraw();
        }
        ;
    }


    public Drawable getControlDrawable() {
        return controlDrawable;
    }

    public void setControlDrawable(Drawable drawable) {
        this.controlDrawable = drawable;
        mDrawableWidth = drawable.getIntrinsicWidth();
        mDrawableHeight = drawable.getIntrinsicHeight();
        transformDraw();
    }

    public int getFramePadding() {
        return framePadding;
    }

    public void setFramePadding(int framePadding) {
        if (this.framePadding == framePadding) return;
        this.framePadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, framePadding, metrics);
        transformDraw();
    }

    public int getFrameColor() {
        return frameColor;
    }

    public void setFrameColor(int frameColor) {
        if (this.frameColor == frameColor) return;
        this.frameColor = frameColor;
        mPaint.setColor(frameColor);
        invalidate();
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(int frameWidth) {
        if (this.frameWidth == frameWidth) return;
        this.frameWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, frameWidth, metrics);
        mPaint.setStrokeWidth(frameWidth);
        invalidate();
    }

    public void setControlLocation(int location) {
        if (this.controlLocation == location) return;
        this.controlLocation = location;
        transformDraw();
    }

    public int getControlLocation() {
        return controlLocation;
    }


    public PointF getCenterPoint() {
        return mCenterPoint;
    }

    public void setCenterPoint(PointF mCenterPoint) {
        this.mCenterPoint = mCenterPoint;
        adjustLayout();
    }


    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
        invalidate();
    }

    private float
    distance4PointF(PointF pf1, PointF pf2) {
        float disX = pf2.x - pf1.x;
        float disY = pf2.y - pf1.y;
        return (float) Math.sqrt(disX * disX + disY * disY);
    }

    private View rootView;

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    public void addSticker(SingleTouchView view) {
        if (rootView != null) {
            if (rootView instanceof FrameLayout) {
                ((FrameLayout) rootView).addView(view);
                ((FrameLayout) rootView).requestLayout();
            } else if (rootView instanceof RelativeLayout) {
                ((RelativeLayout) rootView).addView(view);
                ((RelativeLayout) rootView).requestLayout();
            }
        }
    }

    private void deleteSticker() {
        if (rootView != null) {
            if (rootView instanceof FrameLayout) {
                ((FrameLayout) rootView).removeView(this);
                ((FrameLayout) rootView).requestLayout();
            } else if (rootView instanceof RelativeLayout) {
                ((RelativeLayout) rootView).removeView(this);
                ((RelativeLayout) rootView).requestLayout();
            }
        }
    }

    private void flipSticker() {
        this.mBitmap = Orientation.flipImage(this.mBitmap, Orientation.FLIP_HORIZONTAL);
        transformDraw();
        invalidate();
    }

    private String size_xLarge = "xLarge";
    private String size_lagre = "large";
    private String size_normal = "normal";
    private String size_small = "small";

    public String getTypeScreen() {
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                return size_xLarge;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return size_lagre;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return size_normal;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return size_small;
            default:
                return size_lagre;
        }
    }


    public void saveImg(boolean isSave) {
        if (isSave) {
  /*          layout_customimg_rl_text.setBackgroundDrawable(null);
            layoutCustomimgImgMenu.setVisibility(View.INVISIBLE);*/
            setEditable(false);
            //            transformDraw();
        } else {
            setEditable(true);
        }

    }

    public void setOnSignleViewListener(SingleViewCallBack callback) {
        this.callback = callback;
    }

}
