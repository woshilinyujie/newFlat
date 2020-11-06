package com.wl.wlflatproject.MView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

/**
 * @Author: HSL
 * @Time: 2019/7/29 16:52
 * @E-mail: xxx@163.com
 * @Description: 月视图~
 */
public class FlatMonthView extends MonthView {

    private int mRadius;
    private Paint mCustomSelectedPaint;
    /**
     * 24节气画笔
     */
    private Paint mSolarTermTextPaint = new Paint();

    public FlatMonthView(Context context) {
        super(context);
        mCustomSelectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCustomSelectedPaint.setStrokeWidth(2);
        mCustomSelectedPaint.setStyle(Paint.Style.STROKE);
        mCustomSelectedPaint.setColor(Color.parseColor("#999999"));


        mSolarTermTextPaint.setColor(Color.parseColor("#31ABD3"));
        mSolarTermTextPaint.setAntiAlias(true);
        mSolarTermTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onPreviewHook() {
        //适用于本平板
        mRadius = 34;
        mSolarTermTextPaint.setTextSize(mCurMonthLunarTextPaint.getTextSize());
    }

    /**
     * 如果需要点击Scheme没有效果，则return true
     *
     * @param canvas    canvas
     * @param calendar  日历日历calendar
     * @param x         日历Card x起点坐标
     * @param y         日历Card y起点坐标
     * @param hasScheme hasScheme 非标记的日期
     * @return false 则不绘制onDrawScheme，因为这里背景色是互斥的
     */
    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, calendar.isCurrentDay() ? mSelectedPaint : mCustomSelectedPaint);
        return true;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        // TODO: 2019/7/29
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        int top = y - mItemHeight / 6;
        if (calendar.isCurrentMonth() && calendar.isCurrentDay() && !isSelected) {
            canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
        }
        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentMonth() && calendar.isCurrentDay() ? mCurDayTextPaint : mSelectTextPaint);
            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10,
                    calendar.isCurrentMonth() && calendar.isCurrentDay() ? mCurDayLunarTextPaint : mSelectedLunarTextPaint);
        } else if (hasScheme) {
            // TODO: 2019/7/29
        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentMonth() && calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
            String mark = calendar.getSolarTerm() + calendar.getTraditionFestival() + calendar.getGregorianFestival();
            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10,
                    calendar.isCurrentMonth() && calendar.isCurrentDay() ? mCurDayLunarTextPaint : !TextUtils.isEmpty(mark) && calendar.isCurrentMonth()
                            ? mSolarTermTextPaint : calendar.isCurrentMonth() ?
                            mCurMonthLunarTextPaint : mOtherMonthLunarTextPaint);
        }
    }
}
