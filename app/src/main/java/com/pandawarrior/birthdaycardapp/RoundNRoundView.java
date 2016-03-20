package com.pandawarrior.birthdaycardapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by JT on 19/03/16.
 */
public class RoundNRoundView extends View {

    private Paint mSmallCirclePaint;
    private Paint mBigCirclePaint;
    private float mRotateDegree = 0.5f;
    private float mShadowRadius = 6f;
    private float mDegree = 0;
    private float mBigRadius = 10f;
    private float mSmallRadius = 6f;
    private float mBigStrokeWidth = 0.8f;
    private int planetColor = R.color.yellow;

    public RoundNRoundView(Context context) {
        super(context);
        //init(attrs);
    }

    public RoundNRoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundNRoundView);
        mRotateDegree = array.getFloat(R.styleable.RoundNRoundView_speed, 0.4f);
        planetColor = array.getColor(R.styleable.RoundNRoundView_planet_color, 4);
        array.recycle();
        init(attrs);
    }

    public RoundNRoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RoundNRoundView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attributeSet) {
        setDimension();
        setSmallCircle();
        setBigCircle();

        // mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        mDegree = 0;
    }

    private void setDimension() {
        float density = getResources().getDisplayMetrics().density;
        mShadowRadius *= density;
        mSmallRadius *= density;
        mBigRadius *= density;
        mBigStrokeWidth *= density;
    }

    private void setBigCircle() {
        mBigCirclePaint = new Paint();
        mBigCirclePaint.setColor(getResources().getColor(R.color.grey));
        mBigCirclePaint.setShadowLayer(mShadowRadius, 0, 0, Color.BLACK);
        mBigCirclePaint.setAntiAlias(true);
        mBigCirclePaint.setStrokeWidth(mBigStrokeWidth);
        mBigCirclePaint.setStyle(Paint.Style.STROKE);
    }

    private void setSmallCircle() {
        mSmallCirclePaint = new Paint();
        Log.d("TAG", "setSmallCircle: "+ planetColor);
        Log.d("TAG", "setSmallCircle: "+ R.color.colorPrimary);
        mSmallCirclePaint.setColor(planetColor);
        mSmallCirclePaint.setShadowLayer(mShadowRadius, 0, 0, Color.BLACK);
        mSmallCirclePaint.setAntiAlias(true);
        mSmallCirclePaint.setStrokeWidth(mBigStrokeWidth);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int height = hSpecSize;
        int width = wSpecSize;
        if (hSpecMode == MeasureSpec.EXACTLY) {
            height = hSpecSize;
        } else if (hSpecMode == MeasureSpec.AT_MOST) {
            height = (int) (mBigRadius * 2);
        }

        if (wSpecMode == MeasureSpec.EXACTLY) {
            width = hSpecSize;
        } else if (wSpecMode == MeasureSpec.AT_MOST) {
            width = (int) (mBigRadius * 2);
        }
        setMeasuredDimension(width, height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float canvasX = canvas.getWidth();
        float canvasY = canvas.getHeight();
        float bigCircleRadius = canvasX/2 - mSmallRadius;

        canvas.drawCircle(canvasX/2, canvasY/2, bigCircleRadius, mBigCirclePaint);

        canvas.save();
        canvas.rotate(mDegree, canvasX/2, canvasY/2);
        canvas.drawCircle(mSmallRadius, canvasY/2, mSmallRadius, mSmallCirclePaint);

        mDegree += mRotateDegree;
        if (mDegree > 360)
            mDegree = 0;
        canvas.restore();
        invalidate();
    }

}
