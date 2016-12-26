package com.vibexie.batterylib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by vibexie on 2016/11/14.
 */
public class BatteryView extends View {
    //Some default settings
    private static final int SHELL_WIDTH = 10; //The width of the shell, dp
    private static final int SHELL_COLOR = Color.BLACK;
    private static final int POWER_COLOR = Color.GREEN;
    private static final int AMOUNT_COLOR = Color.RED;
    private static final int AMOUNT_TEXT_SIZE = 20; //sp

    private Context mContext;
    private float mWidth;
    private float mHeight;

    private Paint mShellPaint;
    private Paint mPowerPaint;
    private Paint mAountPaint;
    private int mPowerAmount;
    private float mShellWidth;

    public BatteryView(Context context) {
        super(context);
    }

    public BatteryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BatteryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;

        float shellWidth = sp2px(mContext, SHELL_WIDTH);
        int shellColor = SHELL_COLOR;
        int powerColor = POWER_COLOR;
        int amountColor = AMOUNT_COLOR;
        float amountTextSize = sp2px(mContext, AMOUNT_TEXT_SIZE);
        TypedArray arr = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.BatteryView, defStyle, 0);
        int arrSize = arr.length();
        for (int i = 0; i < arrSize; i ++) {
            int attr = arr.getIndex(i);

            if (attr == R.styleable.BatteryView_shellWidth) {
               shellWidth = arr.getDimension(attr, dp2Px(mContext, SHELL_WIDTH));

            } else if (attr == R.styleable.BatteryView_shellColor) {
                shellColor = arr.getColor(attr, SHELL_COLOR);

            } else if (attr == R.styleable.BatteryView_powerColor) {
                powerColor = arr.getColor(attr, POWER_COLOR);

            } else if (attr == R.styleable.BatteryView_amountColor) {
                amountColor = arr.getColor(attr, AMOUNT_COLOR);
            } else if (attr == R.styleable.BatteryView_amountTextSize) {
                amountTextSize = arr.getDimension(attr, sp2px(mContext, AMOUNT_TEXT_SIZE));
            }
        }

        mShellWidth = shellWidth;
        mShellPaint = new Paint();
        mShellPaint.setAntiAlias(true);
        mShellPaint.setColor(shellColor);
        mShellPaint.setStyle(Paint.Style.STROKE);
        mShellPaint.setStrokeWidth(mShellWidth);

        mPowerPaint = new Paint();
        mPowerPaint.setAntiAlias(true);
        mPowerPaint.setColor(powerColor);
        mPowerPaint.setStyle(Paint.Style.STROKE);

        mAountPaint = new Paint();
        mAountPaint.setAntiAlias(true);
        mAountPaint.setColor(amountColor);
        mAountPaint.setTextSize(amountTextSize);
        mAountPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getWidth();
        mHeight = getHeight();
        mPowerPaint.setStrokeWidth(mWidth - mShellWidth * 2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();//三角形
        path.moveTo(mShellWidth / 2, mShellWidth); //20也就是指针的高度
        path.lineTo(mShellWidth / 2, mHeight - mShellWidth / 2);
        path.lineTo(mWidth - mShellWidth / 2, mHeight - mShellWidth / 2);
        path.lineTo(mWidth  - mShellWidth / 2, mShellWidth);
        path.moveTo(mShellWidth, mShellWidth / 2);
        path.lineTo(mWidth - mShellWidth, mShellWidth / 2);
        canvas.drawPath(path, mShellPaint);

        path = new Path();
        path.moveTo(mWidth / 2, mHeight - mShellWidth);
        path.lineTo(mWidth / 2, mHeight - mShellWidth - ((mHeight - mShellWidth * 2) * mPowerAmount / 100f));
        canvas.drawPath(path, mPowerPaint);

        String text = mPowerAmount + "%";
        canvas.drawText(text, mWidth / 2 - getFontWeight(text) / 2, mHeight / 2 - getFontHeight() / 2, mAountPaint);
    }

    public static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private float getFontWeight(String text) {
        if (mAountPaint != null) {
            return mAountPaint.measureText(text);
        } else {
            return  0;
        }
    }

    private float getFontHeight() {
        if (mAountPaint != null) {
            Paint.FontMetrics fm = mAountPaint.getFontMetrics();
            return fm.descent - fm.ascent;
        } else {
            return 0;
        }
    }

    public void setmPowerAmount(int mPowerAmount) {
        this.mPowerAmount = mPowerAmount;
        invalidate();
    }

    public int getmPowerAmount() {
        return mPowerAmount;
    }
}
