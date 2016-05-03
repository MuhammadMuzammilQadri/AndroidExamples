package com.appiskey.android.faizan.cropper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DoubleCropper extends ImageView {

    private static final String TAG = DoubleCropper.class.getSimpleName();

    // Constants ///////////////////////////////////////////////////////////////////////////////////

    private static final int HANDLE_SIZE_IN_DP = 7;
    private static final int MIN_FRAME_SIZE_IN_DP = 50;
    private static final int FRAME_STROKE_WEIGHT_IN_DP = 1;
    private static final int GUIDE_STROKE_WEIGHT_IN_DP = 1;
    private static final float DEFAULT_INITIAL_FRAME_SCALE = 1f;
    private static final int DEFAULT_ANIMATION_DURATION_MILLIS = 100;
    private static final int DEBUG_TEXT_SIZE_IN_DP = 15;

    private static final int TRANSPARENT = 0x00000000;
    private static final int TRANSLUCENT_WHITE = 0xBBFFFFFF;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int GRAY = 0xff404040;
    private static final int TRANSLUCENT_BLACK = 0x88000000;

    // Member variables ////////////////////////////////////////////////////////////////////////////

    private int mViewWidth = 0;
    private int mViewHeight = 0;
    private float mScale = 1.0f;
    private float mAngle = 0.0f;
    private float mImgWidth = 0.0f;
    private float mImgHeight = 0.0f;

    private boolean mIsInitialized = false;
    private Matrix mMatrix = null;
    private Paint mPaintTranslucent;
    private Paint mPaintFrame;
    private Paint mPaintBitmap;
    private Paint mPaintDebug;
    private RectF mFrameRect1;
    private RectF mFrameRect2;
    private RectF mImageRect;
    private PointF mCenter = new PointF();
    private float mLastX, mLastY;
    private boolean mIsRotating = false;
    private boolean mIsAnimating = false;
    private final Interpolator DEFAULT_INTERPOLATOR = new DecelerateInterpolator();
    private Interpolator mInterpolator = DEFAULT_INTERPOLATOR;
    private LoadCallback mLoadCallback = null;
    private CropCallback mCropCallback = null;
    private SaveCallback mSaveCallback = null;
    private ExecutorService mExecutor;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Uri mSourceUri = null;
    private Uri mSaveUri = null;
    private int mExifRotation = 0;
    private int mOutputMaxWidth;
    private int mOutputMaxHeight;
    private int mOutputWidth = 0;
    private int mOutputHeight = 0;
    private boolean mIsDebug = false;
    private boolean mIsCropping = false;
    private Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.PNG;
    private int mCompressQuality = 100;
    private int mInputImageWidth = 0;
    private int mInputImageHeight = 0;
    private int mOutputImageWidth = 0;
    private int mOutputImageHeight = 0;
    private boolean mIsLoading = false;

    // Instance variables for customizable attributes //////////////////////////////////////////////

    private TouchArea mTouchArea = TouchArea.OUT_OF_BOUNDS;

    private CropMode mCropMode = CropMode.FREE;
    private ShowMode mGuideShowMode = ShowMode.SHOW_ALWAYS;
    private ShowMode mHandleShowMode = ShowMode.SHOW_ALWAYS;
    private float mMinFrameSize;
    private int mHandleSize;
    private int mTouchPadding = 20;
    private boolean mShowGuide = true;
    private boolean mShowHandle = true;
    private boolean mIsCropEnabled = true;
    private boolean mIsEnabled = true;
    private PointF mCustomRatio = new PointF(1.0f, 1.0f);
    private float mFrameStrokeWeight = 2.0f;
    private float mGuideStrokeWeight = 2.0f;
    private int mBackgroundColor;
    private int mOverlayColor;
    private int mFrameColor;
    private int mHandleColor;
    private int mGuideColor;
    private float mInitialFrameScale; // 0.01 ~ 1.0, 0.75 is default value
    private boolean mIsAnimationEnabled = false;
    private int mAnimationDurationMillis = DEFAULT_ANIMATION_DURATION_MILLIS;
    private boolean mIsHandleShadowEnabled = true;

    // Constructor /////////////////////////////////////////////////////////////////////////////////

    public DoubleCropper(Context context) {
        this(context, null);
    }

    public DoubleCropper(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoubleCropper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mExecutor = Executors.newSingleThreadExecutor();
        float density = getDensity();
        mHandleSize = (int) (density * HANDLE_SIZE_IN_DP);
        mMinFrameSize = density * MIN_FRAME_SIZE_IN_DP;
        mFrameStrokeWeight = density * FRAME_STROKE_WEIGHT_IN_DP;
        mGuideStrokeWeight = density * GUIDE_STROKE_WEIGHT_IN_DP;

        mPaintFrame = new Paint();
        mPaintTranslucent = new Paint();
        mPaintBitmap = new Paint();
        mPaintBitmap.setFilterBitmap(true);
        mPaintDebug = new Paint();
        mPaintDebug.setAntiAlias(true);
        mPaintDebug.setStyle(Paint.Style.STROKE);
        mPaintDebug.setColor(WHITE);
        mPaintDebug.setTextSize((float) DEBUG_TEXT_SIZE_IN_DP * density);

        mMatrix = new Matrix();
        mScale = 1.0f;
        mBackgroundColor = TRANSPARENT;
        mFrameColor = WHITE;
        mOverlayColor = TRANSLUCENT_BLACK;
        mHandleColor = WHITE;
        mGuideColor = TRANSLUCENT_WHITE;

        mInitialFrameScale = .2f;

        // handle Styleable
//        handleStyleable(context, attrs, defStyleAttr, density);
    }

    // Lifecycle methods ///////////////////////////////////////////////////////////////////////////

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int viewHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(viewWidth, viewHeight);

        mViewWidth = viewWidth - getPaddingLeft() - getPaddingRight();
        mViewHeight = viewHeight - getPaddingTop() - getPaddingBottom();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getDrawable() != null) setupLayout(mViewWidth, mViewHeight);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(mBackgroundColor);

        if (mIsInitialized) {
            setMatrix();
            Bitmap bm = getBitmap();
            if (bm != null) {
                canvas.drawBitmap(bm, mMatrix, mPaintBitmap);
                // draw edit frame
                drawCropFrame(canvas);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mExecutor.shutdown();
        super.onDetachedFromWindow();
    }

    // Drawing method //////////////////////////////////////////////////////////////////////////////

    private void drawCropFrame(Canvas canvas) {
//        if (!mIsCropEnabled) return;
//        if (mIsRotating) return;
        drawOverlay(canvas);
        drawFrame(canvas);
//        if (mShowGuide) drawGuidelines(canvas);
        if (mShowHandle) drawHandles(canvas);
    }

    private void drawOverlay(Canvas canvas) {
        mPaintTranslucent.setAntiAlias(true);
        mPaintTranslucent.setFilterBitmap(true);
        mPaintTranslucent.setColor(mOverlayColor);
        mPaintTranslucent.setStyle(Paint.Style.FILL);
        Path path = new Path();
        path.addRect(mImageRect, Path.Direction.CW);
        path.addRect(mFrameRect1, Path.Direction.CCW);
        path.addRect(mFrameRect2, Path.Direction.CCW);
        canvas.drawPath(path, mPaintTranslucent);
    }

    private void drawFrame(Canvas canvas) {
        mPaintFrame.setAntiAlias(true);
        mPaintFrame.setFilterBitmap(true);
        mPaintFrame.setStyle(Paint.Style.STROKE);
        mPaintFrame.setColor(mFrameColor);
        mPaintFrame.setStrokeWidth(mFrameStrokeWeight);
        canvas.drawRect(mFrameRect1, mPaintFrame);
        canvas.drawRect(mFrameRect2, mPaintFrame);
    }

    private void drawHandles(Canvas canvas) {
        if (mIsHandleShadowEnabled) drawHandleShadows(canvas);
        mPaintFrame.setStyle(Paint.Style.FILL);
        mPaintFrame.setColor(mHandleColor);
        drawHandles(canvas, mPaintFrame, mFrameRect1);
        drawHandles(canvas, mPaintFrame, mFrameRect2);
    }

    private void drawHandles(Canvas canvas, Paint paint, RectF rectF) {
        canvas.drawCircle(rectF.left, rectF.top, mHandleSize, paint);
        canvas.drawCircle(rectF.right, rectF.top, mHandleSize, paint);
        canvas.drawCircle(rectF.left, rectF.bottom, mHandleSize, paint);
        canvas.drawCircle(rectF.right, rectF.bottom, mHandleSize, paint);
    }

    private void drawHandleShadows(Canvas canvas) {
        mPaintFrame.setStyle(Paint.Style.FILL);
        mPaintFrame.setColor(TRANSLUCENT_BLACK);
        drawHandleShadows(canvas, mPaintFrame, mFrameRect1);
        drawHandleShadows(canvas, mPaintFrame, mFrameRect2);
    }

    private void drawHandleShadows(Canvas canvas, Paint paint, RectF r) {
        RectF rectF = new RectF(r);
        rectF.offset(0, 1);
        canvas.drawCircle(rectF.left, rectF.top, mHandleSize, paint);
        canvas.drawCircle(rectF.right, rectF.top, mHandleSize, paint);
        canvas.drawCircle(rectF.left, rectF.bottom, mHandleSize, paint);
        canvas.drawCircle(rectF.right, rectF.bottom, mHandleSize, paint);
    }

    private void setMatrix() {
        mMatrix.reset();
        mMatrix.setTranslate(mCenter.x - mImgWidth * 0.5f, mCenter.y - mImgHeight * 0.5f);
        mMatrix.postScale(mScale, mScale, mCenter.x, mCenter.y);
        mMatrix.postRotate(mAngle, mCenter.x, mCenter.y);
    }

    // Layout calculation //////////////////////////////////////////////////////////////////////////

    private void setupLayout(int viewW, int viewH) {
        if (viewW == 0 || viewH == 0) return;
        setCenter(new PointF(getPaddingLeft() + viewW * 0.5f, getPaddingTop() + viewH * 0.5f));
        setScale(calcScale(viewW, viewH, mAngle));
        setMatrix();
        mImageRect = calcImageRect(new RectF(0f, 0f, mImgWidth, mImgHeight), mMatrix);
        mFrameRect1 = calcFrameRect(mImageRect);
        mFrameRect2 = calcFrameRect(mImageRect);
        float offsetY = mFrameRect1.height() * .75f;
        moveFrame(0, -1 * offsetY, mFrameRect1);
        moveFrame(0, offsetY, mFrameRect2);
        mIsInitialized = true;
        invalidate();
    }

    private float calcScale(int viewW, int viewH, float angle) {
        mImgWidth = getDrawable().getIntrinsicWidth();
        mImgHeight = getDrawable().getIntrinsicHeight();
        if (mImgWidth <= 0) mImgWidth = viewW;
        if (mImgHeight <= 0) mImgHeight = viewH;
        float viewRatio = (float) viewW / (float) viewH;
        float imgRatio = getRotatedWidth(angle) / getRotatedHeight(angle);
        float scale = 1.0f;
        if (imgRatio >= viewRatio) {
            scale = viewW / getRotatedWidth(angle);
        } else if (imgRatio < viewRatio) {
            scale = viewH / getRotatedHeight(angle);
        }
        return scale;
    }

    private RectF calcImageRect(RectF rect, Matrix matrix) {
        RectF applied = new RectF();
        matrix.mapRect(applied, rect);
        return applied;
    }

    private RectF calcFrameRect(RectF imageRect) {
        float frameW = getRatioX(imageRect.width());
        float frameH = getRatioY(imageRect.height());
        float imgRatio = imageRect.width() / imageRect.height();
        float frameRatio = frameW / frameH;
        float l = imageRect.left, t = imageRect.top, r = imageRect.right, b = imageRect.bottom;
        if (frameRatio >= imgRatio) {
            l = imageRect.left;
            r = imageRect.right;
            float hy = (imageRect.top + imageRect.bottom) * 0.5f;
            float hh = (imageRect.width() / frameRatio) * 0.5f;
            t = hy - hh;
            b = hy + hh;
        } else if (frameRatio < imgRatio) {
            t = imageRect.top;
            b = imageRect.bottom;
            float hx = (imageRect.left + imageRect.right) * 0.5f;
            float hw = imageRect.height() * frameRatio * 0.5f;
            l = hx - hw;
            r = hx + hw;
        }
        float w = r - l;
        float h = b - t;
        float cx = l + w / 2;
        float cy = t + h / 2;
        float sw = w * mInitialFrameScale;
        float sh = h * mInitialFrameScale;
        return new RectF(cx - sw / 2, cy - sh / 2, cx + sw / 2, cy + sh / 2);
    }

    // Touch Event /////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mIsInitialized) return false;
        if (!mIsCropEnabled) return false;
        if (!mIsEnabled) return false;
        if (mIsRotating) return false;
        if (mIsAnimating) return false;
        if (mIsLoading) return false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onDown(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                onMove(event);
                if (mTouchArea != TouchArea.OUT_OF_BOUNDS) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                return true;
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                onCancel();
                return true;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                onUp(event);
                return true;
        }
        return false;
    }

    private void onDown(MotionEvent e) {
        invalidate();
        mLastX = e.getX();
        mLastY = e.getY();
        checkTouchArea(e.getX(), e.getY());
    }

    private void onMove(MotionEvent e) {
        float diffX = e.getX() - mLastX;
        float diffY = e.getY() - mLastY;
        switch (mTouchArea) {
            case CENTER1:
                moveFrame(diffX, diffY, mFrameRect1);
                break;
            case LEFT_TOP1:
                moveHandleLT(diffX, diffY, mFrameRect1);
                break;
            case RIGHT_TOP1:
                moveHandleRT(diffX, diffY, mFrameRect1);
                break;
            case LEFT_BOTTOM1:
                moveHandleLB(diffX, diffY, mFrameRect1);
                break;
            case RIGHT_BOTTOM1:
                moveHandleRB(diffX, diffY, mFrameRect1);
                break;
            case CENTER2:
                moveFrame(diffX, diffY, mFrameRect2);
                break;
            case LEFT_TOP2:
                moveHandleLT(diffX, diffY, mFrameRect2);
                break;
            case RIGHT_TOP2:
                moveHandleRT(diffX, diffY, mFrameRect2);
                break;
            case LEFT_BOTTOM2:
                moveHandleLB(diffX, diffY, mFrameRect2);
                break;
            case RIGHT_BOTTOM2:
                moveHandleRB(diffX, diffY, mFrameRect2);
                break;
            case OUT_OF_BOUNDS:
                break;
        }
        invalidate();
        mLastX = e.getX();
        mLastY = e.getY();
    }

    private void onUp(MotionEvent e) {
//        if (mGuideShowMode == ShowMode.SHOW_ON_TOUCH) mShowGuide = false;
//        if (mHandleShowMode == ShowMode.SHOW_ON_TOUCH) mShowHandle = false;
        mTouchArea = TouchArea.OUT_OF_BOUNDS;
        invalidate();
    }

    private void onCancel() {
        mTouchArea = TouchArea.OUT_OF_BOUNDS;
        invalidate();
    }

    // Hit test ////////////////////////////////////////////////////////////////////////////////////

    private void checkTouchArea(float x, float y) {
        if (isInsideCornerLeftTop(x, y, mFrameRect1)) {
            mTouchArea = TouchArea.LEFT_TOP1;
            return;
        }
        if (isInsideCornerRightTop(x, y, mFrameRect1)) {
            mTouchArea = TouchArea.RIGHT_TOP1;
            return;
        }
        if (isInsideCornerLeftBottom(x, y, mFrameRect1)) {
            mTouchArea = TouchArea.LEFT_BOTTOM1;
            return;
        }
        if (isInsideCornerRightBottom(x, y, mFrameRect1)) {
            mTouchArea = TouchArea.RIGHT_BOTTOM1;
            return;
        }
        if (isInsideFrame(x, y, mFrameRect1)) {
            mTouchArea = TouchArea.CENTER1;
            return;
        }
        if (isInsideCornerLeftTop(x, y, mFrameRect2)) {
            mTouchArea = TouchArea.LEFT_TOP2;
            return;
        }
        if (isInsideCornerRightTop(x, y, mFrameRect2)) {
            mTouchArea = TouchArea.RIGHT_TOP2;
            return;
        }
        if (isInsideCornerLeftBottom(x, y, mFrameRect2)) {
            mTouchArea = TouchArea.LEFT_BOTTOM2;
            return;
        }
        if (isInsideCornerRightBottom(x, y, mFrameRect2)) {
            mTouchArea = TouchArea.RIGHT_BOTTOM2;
            return;
        }
        if (isInsideFrame(x, y, mFrameRect2)) {
            mTouchArea = TouchArea.CENTER2;
            return;
        }
        mTouchArea = TouchArea.OUT_OF_BOUNDS;
    }

    private boolean isInsideFrame(float x, float y, RectF rectF) {
        if (rectF.left <= x && rectF.right >= x) {
            if (rectF.top <= y && rectF.bottom >= y) {
                mTouchArea = TouchArea.CENTER1;
                return true;
            }
        }
        return false;
    }

    private boolean isInsideCornerLeftTop(float x, float y, RectF rectF) {
        float dx = x - rectF.left;
        float dy = y - rectF.top;
        float d = dx * dx + dy * dy;
        return sq(mHandleSize + mTouchPadding) >= d;
    }

    private boolean isInsideCornerRightTop(float x, float y, RectF rectF) {
        float dx = x - rectF.right;
        float dy = y - rectF.top;
        float d = dx * dx + dy * dy;
        return sq(mHandleSize + mTouchPadding) >= d;
    }

    private boolean isInsideCornerLeftBottom(float x, float y, RectF rectF) {
        float dx = x - rectF.left;
        float dy = y - rectF.bottom;
        float d = dx * dx + dy * dy;
        return sq(mHandleSize + mTouchPadding) >= d;
    }

    private boolean isInsideCornerRightBottom(float x, float y, RectF rectF) {
        float dx = x - rectF.right;
        float dy = y - rectF.bottom;
        float d = dx * dx + dy * dy;
        return sq(mHandleSize + mTouchPadding) >= d;
    }

    // Adjust frame ////////////////////////////////////////////////////////////////////////////////

    private void moveFrame(float x, float y, RectF rectF) {
/*
        rectF.left += x;
        rectF.right += x;
        rectF.top += y;
        rectF.bottom += y;
*/
        rectF.offset(x, y);
        checkMoveBounds();
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    private void moveHandleLT(float diffX, float diffY, RectF rectF) {
        rectF.left += diffX;
        rectF.top += diffY;
        if (isWidthTooSmall(rectF)) {
            float offsetX = mMinFrameSize - getFrameW(rectF);
            rectF.left -= offsetX;
        }
        if (isHeightTooSmall(rectF)) {
            float offsetY = mMinFrameSize - getFrameH(rectF);
            rectF.top -= offsetY;
        }
        checkScaleBounds(rectF);
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    private void moveHandleRT(float diffX, float diffY, RectF rectF) {
        rectF.right += diffX;
        rectF.top += diffY;
        if (isWidthTooSmall(rectF)) {
            float offsetX = mMinFrameSize - getFrameW(rectF);
            rectF.right += offsetX;
        }
        if (isHeightTooSmall(rectF)) {
            float offsetY = mMinFrameSize - getFrameH(rectF);
            rectF.top -= offsetY;
        }
        checkScaleBounds(rectF);
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    private void moveHandleLB(float diffX, float diffY, RectF rectF) {
        rectF.left += diffX;
        rectF.bottom += diffY;
        if (isWidthTooSmall(rectF)) {
            float offsetX = mMinFrameSize - getFrameW(rectF);
            rectF.left -= offsetX;
        }
        if (isHeightTooSmall(rectF)) {
            float offsetY = mMinFrameSize - getFrameH(rectF);
            rectF.bottom += offsetY;
        }
        checkScaleBounds(rectF);
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    private void moveHandleRB(float diffX, float diffY, RectF rectF) {
        rectF.right += diffX;
        rectF.bottom += diffY;
        if (isWidthTooSmall(rectF)) {
            float offsetX = mMinFrameSize - getFrameW(rectF);
            rectF.right += offsetX;
        }
        if (isHeightTooSmall(rectF)) {
            float offsetY = mMinFrameSize - getFrameH(rectF);
            rectF.bottom += offsetY;
        }
        checkScaleBounds(rectF);
    }

    // Frame position correction ///////////////////////////////////////////////////////////////////

    private void checkScaleBounds(RectF rectF) {
        float lDiff = rectF.left - mImageRect.left;
        float rDiff = rectF.right - mImageRect.right;
        float tDiff = rectF.top - mImageRect.top;
        float bDiff = rectF.bottom - mImageRect.bottom;

        if (lDiff < 0) {
            rectF.left -= lDiff;
        }
        if (rDiff > 0) {
            rectF.right -= rDiff;
        }
        if (tDiff < 0) {
            rectF.top -= tDiff;
        }
        if (bDiff > 0) {
            rectF.bottom -= bDiff;
        }
    }

    private void checkMoveBounds() {
        checkMoveBounds(mFrameRect1);
        checkMoveBounds(mFrameRect2);
    }

    private void checkMoveBounds(RectF rectF) {
        float diff = rectF.left - mImageRect.left;
        if (diff < 0) {
            rectF.left -= diff;
            rectF.right -= diff;
        }
        diff = rectF.right - mImageRect.right;
        if (diff > 0) {
            rectF.left -= diff;
            rectF.right -= diff;
        }
        diff = rectF.top - mImageRect.top;
        if (diff < 0) {
            rectF.top -= diff;
            rectF.bottom -= diff;
        }
        diff = rectF.bottom - mImageRect.bottom;
        if (diff > 0) {
            rectF.top -= diff;
            rectF.bottom -= diff;
        }
    }

    private boolean isInsideHorizontal(float x) {
        return mImageRect.left <= x && mImageRect.right >= x;
    }

    private boolean isInsideVertical(float y) {
        return mImageRect.top <= y && mImageRect.bottom >= y;
    }

    private boolean isWidthTooSmall(RectF rectF) {
        return getFrameW(rectF) < mMinFrameSize;
    }

    private boolean isHeightTooSmall(RectF rectF) {
        return getFrameH(rectF) < mMinFrameSize;
    }

    // Frame aspect ratio correction ///////////////////////////////////////////////////////////////

    private float getRatioX(float w) {
        switch (mCropMode) {
            case FIT_IMAGE:
                return mImageRect.width();
            case FREE:
                return w;
            case RATIO_4_3:
                return 4;
            case RATIO_3_4:
                return 3;
            case RATIO_16_9:
                return 16;
            case RATIO_9_16:
                return 9;
            case SQUARE:
            case CIRCLE:
            case CIRCLE_SQUARE:
                return 1;
            case CUSTOM:
                return mCustomRatio.x;
            default:
                return w;
        }
    }

    private float getRatioY(float h) {
        switch (mCropMode) {
            case FIT_IMAGE:
                return mImageRect.height();
            case FREE:
                return h;
            case RATIO_4_3:
                return 3;
            case RATIO_3_4:
                return 4;
            case RATIO_16_9:
                return 9;
            case RATIO_9_16:
                return 16;
            case SQUARE:
            case CIRCLE:
            case CIRCLE_SQUARE:
                return 1;
            case CUSTOM:
                return mCustomRatio.y;
            default:
                return h;
        }
    }

    // Utility /////////////////////////////////////////////////////////////////////////////////////

    private float getDensity() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(displayMetrics);
        return displayMetrics.density;
    }

    private float sq(float value) {
        return value * value;
    }

    private float constrain(float val, float min, float max, float defaultVal) {
        if (val < min || val > max) return defaultVal;
        return val;
    }

    private void postErrorOnMainThread(final Callback callback) {
        if (callback == null) return;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            callback.onError();
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onError();
                }
            });
        }
    }

    private Bitmap getBitmap() {
        Bitmap bm = null;
        Drawable d = getDrawable();
        if (d != null && d instanceof BitmapDrawable) bm = ((BitmapDrawable) d).getBitmap();
        return bm;
    }

    private float getRotatedWidth(float angle) {
        return getRotatedWidth(angle, mImgWidth, mImgHeight);
    }

    private float getRotatedWidth(float angle, float width, float height) {
        return angle % 180 == 0 ? width : height;
    }

    private float getRotatedHeight(float angle) {
        return getRotatedHeight(angle, mImgWidth, mImgHeight);
    }

    private float getRotatedHeight(float angle, float width, float height) {
        return angle % 180 == 0 ? height : width;
    }

    private Bitmap getRotatedBitmap(Bitmap bitmap) {
        Matrix rotateMatrix = new Matrix();
        rotateMatrix.setRotate(mAngle, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                rotateMatrix, true);
    }
    // Cropping ////////////////////////////////////////////////////////////////////////////////////

    private Bitmap decodeRegion() {
        Bitmap cropped = null;
        InputStream is = null;
        try {
            is = getContext().getContentResolver()
                    .openInputStream(mSourceUri);
            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(is, false);
            final int originalImageWidth = decoder.getWidth();
            final int originalImageHeight = decoder.getHeight();
            Rect cropRect = calcCropRect(originalImageWidth, originalImageHeight, mFrameRect1);
            if (mAngle != 0) {
                Matrix matrix = new Matrix();
                matrix.setRotate(-mAngle);
                RectF rotated = new RectF();
                matrix.mapRect(rotated, new RectF(cropRect));
                rotated.offset(rotated.left < 0 ? originalImageWidth : 0,
                        rotated.top < 0 ? originalImageHeight : 0);
                cropRect = new Rect((int) rotated.left, (int) rotated.top, (int) rotated.right,
                        (int) rotated.bottom);
            }
            cropped = decoder.decodeRegion(cropRect, new BitmapFactory.Options());
            if (mAngle != 0) {
                Bitmap rotated = getRotatedBitmap(cropped);
                if (cropped != getBitmap() && cropped != rotated) {
                    cropped.recycle();
                }
                cropped = rotated;
            }
        } catch (IOException e) {
            Logger.e("An error occurred while cropping the image: " + e.getMessage(), e);
        } catch (OutOfMemoryError e) {
            Logger.e("OOM Error: " + e.getMessage(), e);
        } catch (Exception e) {
            Logger.e("An unexpected error has occurred: " + e.getMessage(), e);
        } finally {
            Utils.closeQuietly(is);
        }
        return cropped;
    }

    private Rect calcCropRect(int originalImageWidth, int originalImageHeight, RectF rectF) {
        float scaleToOriginal = getRotatedWidth(mAngle, originalImageWidth,
                originalImageHeight) / mImageRect.width();
        float offsetX = mImageRect.left * scaleToOriginal;
        float offsetY = mImageRect.top * scaleToOriginal;
        int left = Math.round(rectF.left * scaleToOriginal - offsetX);
        int top = Math.round(rectF.top * scaleToOriginal - offsetY);
        int right = Math.round(rectF.right * scaleToOriginal - offsetX);
        int bottom = Math.round(rectF.bottom * scaleToOriginal - offsetY);
        int imageW = Math.round(getRotatedWidth(mAngle, originalImageWidth, originalImageHeight));
        int imageH = Math.round(getRotatedHeight(mAngle, originalImageWidth, originalImageHeight));
        return new Rect(Math.max(left, 0), Math.max(top, 0), Math.min(right, imageW),
                Math.min(bottom, imageH));
    }

    private Bitmap scaleBitmapIfNeeded(Bitmap cropped) {
        int width = cropped.getWidth();
        int height = cropped.getHeight();
        int outWidth = 0;
        int outHeight = 0;
        float imageRatio = getRatioX(mFrameRect1.width()) / getRatioY(mFrameRect1.height());

        if (mOutputWidth > 0) {
            outWidth = mOutputWidth;
            outHeight = Math.round(mOutputWidth / imageRatio);
        } else if (mOutputHeight > 0) {
            outHeight = mOutputHeight;
            outWidth = Math.round(mOutputHeight * imageRatio);
        } else {
            if (mOutputMaxWidth > 0 && mOutputMaxHeight > 0
                    && (width > mOutputMaxWidth || height > mOutputMaxHeight)) {
                float maxRatio = (float) mOutputMaxWidth / (float) mOutputMaxHeight;
                if (maxRatio >= imageRatio) {
                    outHeight = mOutputMaxHeight;
                    outWidth = Math.round((float) mOutputMaxHeight * imageRatio);
                } else {
                    outWidth = mOutputMaxWidth;
                    outHeight = Math.round((float) mOutputMaxWidth / imageRatio);
                }
            }
        }

        if (outWidth > 0 && outHeight > 0) {
            Bitmap scaled = Utils.getScaledBitmap(cropped, outWidth, outHeight);
            if (cropped != getBitmap() && cropped != scaled) {
                cropped.recycle();
            }
            cropped = scaled;
        }
        return cropped;
    }
    // File save ///////////////////////////////////////////////////////////////////////////////////

    private void saveToFile(Bitmap bitmap, final Uri uri) {
        OutputStream outputStream = null;
        try {
            outputStream = getContext().getContentResolver()
                    .openOutputStream(uri);
            if (outputStream != null) {
                bitmap.compress(mCompressFormat, mCompressQuality, outputStream);
            }
        } catch (IOException e) {
            Logger.e("An error occurred while saving the image: " + uri, e);
            postErrorOnMainThread(mSaveCallback);
        } finally {
            Utils.closeQuietly(outputStream);
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mSaveCallback != null) mSaveCallback.onSuccess(uri);
            }
        });
    }


    // Public methods //////////////////////////////////////////////////////////////////////////////

    /**
     * Get source image bitmap
     *
     * @return src bitmap
     */
    public Bitmap getImageBitmap() {
        return getBitmap();
    }

    /**
     * Set source image bitmap
     *
     * @param bitmap src image bitmap
     */
    @Override
    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap); // calles setImageDrawable internally
    }

    /**
     * Set source image resource id
     *
     * @param resId source image resource id
     */
    @Override
    public void setImageResource(int resId) {
        mIsInitialized = false;
        super.setImageResource(resId);
        updateLayout();
    }

    /**
     * Set image drawable.
     *
     * @param drawable source image drawable
     */
    @Override
    public void setImageDrawable(Drawable drawable) {
        mIsInitialized = false;
        super.setImageDrawable(drawable);
        updateLayout();
    }

    /**
     * Set image uri
     *
     * @param uri source image local uri
     */
    @Override
    public void setImageURI(Uri uri) {
        mIsInitialized = false;
        super.setImageURI(uri);
        updateLayout();
    }

    private void updateLayout() {
        resetImageInfo();
        Drawable d = getDrawable();
        if (d != null) {
            setupLayout(mViewWidth, mViewHeight);
        }
    }

    private void resetImageInfo() {
        if (mIsLoading) return;
        mSourceUri = null;
        mSaveUri = null;
        mInputImageWidth = 0;
        mInputImageHeight = 0;
        mOutputImageWidth = 0;
        mOutputImageHeight = 0;
        mAngle = mExifRotation;
    }

    /**
     * Load image from Uri.
     *
     * @param sourceUri Image Uri
     * @param callback  Callback
     */
    public void startLoad(Uri sourceUri, LoadCallback callback) {
        mLoadCallback = callback;
        mSourceUri = sourceUri;
        if (sourceUri == null) {
            postErrorOnMainThread(mLoadCallback);
            throw new IllegalStateException("Source Uri must not be null.");
        }
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                mIsLoading = true;
                mExifRotation = Utils.getExifOrientation(getContext(), mSourceUri);
                int maxSize = Utils.getMaxSize();
                int requestSize = Math.max(mViewWidth, mViewHeight);
                if (requestSize == 0) requestSize = maxSize;
                try {
                    final Bitmap sampledBitmap = Utils.decodeSampledBitmapFromUri(getContext(),
                            mSourceUri,
                            requestSize);
                    mInputImageWidth = Utils.sInputImageWidth;
                    mInputImageHeight = Utils.sInputImageHeight;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mAngle = mExifRotation;
                            setImageBitmap(sampledBitmap);
                            if (mLoadCallback != null) mLoadCallback.onSuccess();
                            mIsLoading = false;
                        }
                    });
                } catch (OutOfMemoryError e) {
                    Logger.e("OOM Error: " + e.getMessage(), e);
                    postErrorOnMainThread(mLoadCallback);
                    mIsLoading = false;
                } catch (Exception e) {
                    Logger.e("An unexpected error has occurred: " + e.getMessage(), e);
                    postErrorOnMainThread(mLoadCallback);
                    mIsLoading = false;
                }
            }
        });
    }

    /**
     * Get cropped image bitmap
     *
     * @return cropped image bitmap
     */

    public Bitmap getCroppedBitmap1() {
        return getCroppedBitmap(mFrameRect1);
    }

    public Bitmap getCroppedBitmap2() {
        return getCroppedBitmap(mFrameRect2);
    }

    private Bitmap getCroppedBitmap(RectF rectF) {
        Bitmap source = getBitmap();
        if (source == null) return null;

        Bitmap rotated = getRotatedBitmap(source);
        Rect cropRect = calcCropRect(source.getWidth(), source.getHeight(), rectF);
        Bitmap cropped = Bitmap.createBitmap(
                rotated,
                cropRect.left,
                cropRect.top,
                cropRect.width(),
                cropRect.height(),
                null,
                false
        );
/*
        if (cropped != source && cropped != rotated) {
            rotated.recycle();
        }
*/

/*
        if (mCropMode == CropMode.CIRCLE) {
            Bitmap circle = getCircularBitmap(cropped);
            if (cropped != getBitmap()) {
                cropped.recycle();
            }
            cropped = circle;
        }
*/
        return cropped;
    }

    /**
     * Crop the square image in a circular
     *
     * @param square image bitmap
     * @return circular image bitmap
     */
    public Bitmap getCircularBitmap(Bitmap square) {
        if (square == null) return null;
        Bitmap output = Bitmap.createBitmap(square.getWidth(), square.getHeight(),
                Bitmap.Config.ARGB_8888);

        final Rect rect = new Rect(0, 0, square.getWidth(), square.getHeight());
        Canvas canvas = new Canvas(output);

        int halfWidth = square.getWidth() / 2;
        int halfHeight = square.getHeight() / 2;

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);

        canvas.drawCircle(halfWidth, halfHeight, Math.min(halfWidth, halfHeight), paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(square, rect, rect, paint);
        return output;
    }

    /**
     * Crop image from Uri
     *
     * @param saveUri      Uri for saving the cropped image
     * @param cropCallback Callback for cropping the image
     * @param saveCallback Callback for saving the image
     */
    public void startCrop(Uri saveUri, CropCallback cropCallback, SaveCallback saveCallback) {
        mSaveUri = saveUri;
        mCropCallback = cropCallback;
        mSaveCallback = saveCallback;
        if (mIsCropping) {
            postErrorOnMainThread(mCropCallback);
            postErrorOnMainThread(mSaveCallback);
            return;
        }
        mIsCropping = true;
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap cropped;

                // Use thumbnail for crop
                if (mSourceUri == null) {
                    cropped = getCroppedBitmap1();
                }
                // Use file for crop
                else {
                    cropped = decodeRegion();
                    if (mCropMode == CropMode.CIRCLE) {
                        Bitmap circle = getCircularBitmap(cropped);
                        if (cropped != getBitmap()) {
                            cropped.recycle();
                        }
                        cropped = circle;
                    }
                }

                // Success
                if (cropped != null) {
                    cropped = scaleBitmapIfNeeded(cropped);
                    final Bitmap tmp = cropped;
                    mOutputImageWidth = tmp.getWidth();
                    mOutputImageHeight = tmp.getHeight();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mCropCallback != null) mCropCallback.onSuccess(tmp);
                            if (mIsDebug) invalidate();
                        }
                    });
                }
                // Error
                else {
                    postErrorOnMainThread(mCropCallback);
                }

                if (mSaveUri == null) {
                    postErrorOnMainThread(mSaveCallback);
                    return;
                }
                saveToFile(cropped, mSaveUri);
                mIsCropping = false;
            }
        });
    }

    /**
     * Get frame position relative to the source bitmap.
     *
     * @return crop area boundaries.
     */
    public RectF getActualCropRect() {
        float offsetX = (mImageRect.left / mScale);
        float offsetY = (mImageRect.top / mScale);
        float l = (mFrameRect1.left / mScale) - offsetX;
        float t = (mFrameRect1.top / mScale) - offsetY;
        float r = (mFrameRect1.right / mScale) - offsetX;
        float b = (mFrameRect1.bottom / mScale) - offsetY;
        return new RectF(l, t, r, b);
    }

    /**
     * Set image overlay color
     *
     * @param overlayColor color resId or color int(ex. 0xFFFFFFFF)
     */
    public void setOverlayColor(int overlayColor) {
        this.mOverlayColor = overlayColor;
        invalidate();
    }

    /**
     * Set crop frame color
     *
     * @param frameColor color resId or color int(ex. 0xFFFFFFFF)
     */
    public void setFrameColor(int frameColor) {
        this.mFrameColor = frameColor;
        invalidate();
    }

    /**
     * Set handle color
     *
     * @param handleColor color resId or color int(ex. 0xFFFFFFFF)
     */
    public void setHandleColor(int handleColor) {
        this.mHandleColor = handleColor;
        invalidate();
    }

    /**
     * Set guide color
     *
     * @param guideColor color resId or color int(ex. 0xFFFFFFFF)
     */
    public void setGuideColor(int guideColor) {
        this.mGuideColor = guideColor;
        invalidate();
    }

    /**
     * Set view background color
     *
     * @param bgColor color resId or color int(ex. 0xFFFFFFFF)
     */
    public void setBackgroundColor(int bgColor) {
        this.mBackgroundColor = bgColor;
        invalidate();
    }

    /**
     * Set crop frame minimum size in density-independent pixels.
     *
     * @param minDp crop frame minimum size in density-independent pixels
     */
    public void setMinFrameSizeInDp(int minDp) {
        mMinFrameSize = minDp * getDensity();
    }

    /**
     * Set crop frame minimum size in pixels.
     *
     * @param minPx crop frame minimum size in pixels
     */
    public void setMinFrameSizeInPx(int minPx) {
        mMinFrameSize = minPx;
    }

    /**
     * Set handle radius in density-independent pixels.
     *
     * @param handleDp handle radius in density-independent pixels
     */
    public void setHandleSizeInDp(int handleDp) {
        mHandleSize = (int) (handleDp * getDensity());
    }

    /**
     * Set crop frame handle touch padding(touch area) in density-independent pixels.
     * <p>
     * handle touch area : a circle of radius R.(R = handle size + touch padding)
     *
     * @param paddingDp crop frame handle touch padding(touch area) in density-independent pixels
     */
    public void setTouchPaddingInDp(int paddingDp) {
        mTouchPadding = (int) (paddingDp * getDensity());
    }

    /**
     * Set frame stroke weight in density-independent pixels.
     *
     * @param weightDp frame stroke weight in density-independent pixels.
     */
    public void setFrameStrokeWeightInDp(int weightDp) {
        mFrameStrokeWeight = weightDp * getDensity();
        invalidate();
    }

    /**
     * Set guideline stroke weight in density-independent pixels.
     *
     * @param weightDp guideline stroke weight in density-independent pixels.
     */
    public void setGuideStrokeWeightInDp(int weightDp) {
        mGuideStrokeWeight = weightDp * getDensity();
        invalidate();
    }

    /**
     * Set whether to show crop frame.
     *
     * @param enabled should show crop frame?
     */
    public void setCropEnabled(boolean enabled) {
        mIsCropEnabled = enabled;
        invalidate();
    }

    /**
     * Set locking the crop frame.
     *
     * @param enabled should lock crop frame?
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mIsEnabled = enabled;
    }

    /**
     * Set initial scale of the frame.(0.01 ~ 1.0)
     *
     * @param initialScale initial scale
     */
    public void setInitialFrameScale(float initialScale) {
        mInitialFrameScale = constrain(initialScale, 0.01f, 1.0f, DEFAULT_INITIAL_FRAME_SCALE);
    }

    /**
     * Set whether to animate
     *
     * @param enabled is animation enabled?
     */
    public void setAnimationEnabled(boolean enabled) {
        mIsAnimationEnabled = enabled;
    }

    /**
     * Set duration of animation
     *
     * @param durationMillis animation duration in milliseconds
     */
    public void setAnimationDuration(int durationMillis) {
        mAnimationDurationMillis = durationMillis;
    }

    /**
     * Set whether to show debug display
     *
     * @param debug is logging enabled
     */
    public void setDebug(boolean debug) {
        mIsDebug = debug;
        invalidate();
    }

    /**
     * Set Image Load callback
     *
     * @param callback callback
     */
    public void setLoadCallback(LoadCallback callback) {
        mLoadCallback = callback;
    }

    /**
     * Set Image Crop callback
     *
     * @param callback callback
     */
    public void setCropCallback(CropCallback callback) {
        mCropCallback = callback;
    }

    /**
     * Set Image Save callback
     *
     * @param callback callback
     */
    public void setSaveCallback(SaveCallback callback) {
        mSaveCallback = callback;
    }

    /**
     * Set fixed width for output
     * (After cropping, the image is scaled to the specified size.)
     *
     * @param outputWidth output width
     */
    public void setOutputWidth(int outputWidth) {
        mOutputWidth = outputWidth;
        mOutputHeight = 0;
    }

    /**
     * Set fixed height for output
     * (After cropping, the image is scaled to the specified size.)
     *
     * @param outputHeight output height
     */
    public void setOutputHeight(int outputHeight) {
        mOutputHeight = outputHeight;
        mOutputWidth = 0;
    }

    /**
     * Set maximum size for output
     * (If cropped image size is larger than max size, the image is scaled to the smaller size.
     * If fixed output width/height has already set, these parameters are ignored.)
     *
     * @param maxWidth  max output width
     * @param maxHeight max output height
     */
    public void setOutputMaxSize(int maxWidth, int maxHeight) {
        mOutputMaxWidth = maxWidth;
        mOutputMaxHeight = maxHeight;
    }

    /**
     * Set compress format for output
     *
     * @param format compress format{@link android.graphics.Bitmap.CompressFormat}
     */
    public void setCompressFormat(Bitmap.CompressFormat format) {
        mCompressFormat = format;
    }

    /**
     * Set compress quality for output
     *
     * @param quality compress quality(0-100: 100 is default.)
     */
    public void setCompressQuality(int quality) {
        mCompressQuality = quality;
    }

    /**
     * Set whether to show handle shadows
     *
     * @param handleShadowEnabled should show handle shadows?
     */
    public void setHandleShadowEnabled(boolean handleShadowEnabled) {
        mIsHandleShadowEnabled = handleShadowEnabled;
    }

    private void setScale(float mScale) {
        this.mScale = mScale;
    }

    private void setCenter(PointF mCenter) {
        this.mCenter = mCenter;
    }

    private float getFrameW(RectF rectF) {
        return (rectF.right - rectF.left);
    }

    private float getFrameH(RectF rectF) {
        return (rectF.bottom - rectF.top);
    }

    // Enum ////////////////////////////////////////////////////////////////////////////////////////

    private enum TouchArea {
        OUT_OF_BOUNDS, CENTER1, LEFT_TOP1, RIGHT_TOP1, LEFT_BOTTOM1, RIGHT_BOTTOM1,
        CENTER2, LEFT_TOP2, RIGHT_TOP2, LEFT_BOTTOM2, RIGHT_BOTTOM2
    }

    public enum CropMode {
        FIT_IMAGE(0), RATIO_4_3(1), RATIO_3_4(2), SQUARE(3), RATIO_16_9(4), RATIO_9_16(5), FREE(
                6), CUSTOM(7), CIRCLE(8), CIRCLE_SQUARE(9);
        private final int ID;

        CropMode(final int id) {
            this.ID = id;
        }

        public int getId() {
            return ID;
        }
    }

    public enum ShowMode {
        SHOW_ALWAYS(1), SHOW_ON_TOUCH(2), NOT_SHOW(3);
        private final int ID;

        ShowMode(final int id) {
            this.ID = id;
        }

        public int getId() {
            return ID;
        }
    }

    // Save/Restore support ////////////////////////////////////////////////////////////////////////

    public static class SavedState extends BaseSavedState {
        Bitmap image;
        CropMode mode;
        int backgroundColor;
        int overlayColor;
        int frameColor;
        ShowMode guideShowMode;
        ShowMode handleShowMode;
        boolean showGuide;
        boolean showHandle;
        int handleSize;
        int touchPadding;
        float minFrameSize;
        float customRatioX;
        float customRatioY;
        float frameStrokeWeight;
        float guideStrokeWeight;
        boolean isCropEnabled;
        int handleColor;
        int guideColor;
        float initialFrameScale;
        float angle;
        boolean isAnimationEnabled;
        int animationDuration;
        int exifRotation;
        Uri sourceUri;
        Uri saveUri;
        Bitmap.CompressFormat compressFormat;
        int compressQuality;
        boolean isDebug;
        int outputMaxWidth;
        int outputMaxHeight;
        int outputWidth;
        int outputHeight;
        boolean isHandleShadowEnabled;
        int inputImageWidth;
        int inputImageHeight;
        int outputImageWidth;
        int outputImageHeight;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            image = in.readParcelable(Bitmap.class.getClassLoader());
            mode = (CropMode) in.readSerializable();
            backgroundColor = in.readInt();
            overlayColor = in.readInt();
            frameColor = in.readInt();
            guideShowMode = (ShowMode) in.readSerializable();
            handleShowMode = (ShowMode) in.readSerializable();
            showGuide = (in.readInt() != 0);
            showHandle = (in.readInt() != 0);
            handleSize = in.readInt();
            touchPadding = in.readInt();
            minFrameSize = in.readFloat();
            customRatioX = in.readFloat();
            customRatioY = in.readFloat();
            frameStrokeWeight = in.readFloat();
            guideStrokeWeight = in.readFloat();
            isCropEnabled = (in.readInt() != 0);
            handleColor = in.readInt();
            guideColor = in.readInt();
            initialFrameScale = in.readFloat();
            angle = in.readFloat();
            isAnimationEnabled = (in.readInt() != 0);
            animationDuration = in.readInt();
            exifRotation = in.readInt();
            sourceUri = in.readParcelable(Uri.class.getClassLoader());
            saveUri = in.readParcelable(Uri.class.getClassLoader());
            compressFormat = (Bitmap.CompressFormat) in.readSerializable();
            compressQuality = in.readInt();
            isDebug = (in.readInt() != 0);
            outputMaxWidth = in.readInt();
            outputMaxHeight = in.readInt();
            outputWidth = in.readInt();
            outputHeight = in.readInt();
            isHandleShadowEnabled = (in.readInt() != 0);
            inputImageWidth = in.readInt();
            inputImageHeight = in.readInt();
            outputImageWidth = in.readInt();
            outputImageHeight = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flag) {
            super.writeToParcel(out, flag);
            out.writeParcelable(image, flag);
            out.writeSerializable(mode);
            out.writeInt(backgroundColor);
            out.writeInt(overlayColor);
            out.writeInt(frameColor);
            out.writeSerializable(guideShowMode);
            out.writeSerializable(handleShowMode);
            out.writeInt(showGuide ? 1 : 0);
            out.writeInt(showHandle ? 1 : 0);
            out.writeInt(handleSize);
            out.writeInt(touchPadding);
            out.writeFloat(minFrameSize);
            out.writeFloat(customRatioX);
            out.writeFloat(customRatioY);
            out.writeFloat(frameStrokeWeight);
            out.writeFloat(guideStrokeWeight);
            out.writeInt(isCropEnabled ? 1 : 0);
            out.writeInt(handleColor);
            out.writeInt(guideColor);
            out.writeFloat(initialFrameScale);
            out.writeFloat(angle);
            out.writeInt(isAnimationEnabled ? 1 : 0);
            out.writeInt(animationDuration);
            out.writeInt(exifRotation);
            out.writeParcelable(sourceUri, flag);
            out.writeParcelable(saveUri, flag);
            out.writeSerializable(compressFormat);
            out.writeInt(compressQuality);
            out.writeInt(isDebug ? 1 : 0);
            out.writeInt(outputMaxWidth);
            out.writeInt(outputMaxHeight);
            out.writeInt(outputWidth);
            out.writeInt(outputHeight);
            out.writeInt(isHandleShadowEnabled ? 1 : 0);
            out.writeInt(inputImageWidth);
            out.writeInt(inputImageHeight);
            out.writeInt(outputImageWidth);
            out.writeInt(outputImageHeight);
        }

        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
            public SavedState createFromParcel(final Parcel inParcel) {
                return new SavedState(inParcel);
            }

            public SavedState[] newArray(final int inSize) {
                return new SavedState[inSize];
            }
        };
    }
}
