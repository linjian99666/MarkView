package com.github.markview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author : linjian
 * @date 2019-10-22 16:16
 * @description :书签view
 */
public class MarkView extends View {

    /**
     * 默认字体大小(14sp)
     */
    private static final int DEFAULT_TEXT_SIZE = 14;

    /**
     * 默认显示的最大文字数量(超出限制则结尾显示省略号)
     */
    private static final int DEFAULT_MAX_TEXT_NUM = 4;

    /**
     * 默认背景色
     */
    private static final int DEFAULT_BACKGROUND_COLOR = 0xFF0066FF;

    /**
     * 默认文字颜色
     */
    private static final int DEFAULT_TEXT_COLOR = 0xFFFFFFFF;

    /**
     * 默认标签底部三角形高度与控件宽度的比值(取宽度一半0.5)
     */
    private static final float DEFAULT_TRIANGLE_RATIO = 0.4f;

    private Paint backGroundPaint;
    private Paint textPaint;
    private int textSize;
    private int maxTextNum;
    private int backgroundColor;
    private int textColor;

    /**
     * 文字的实际高度
     */
    private int textHeight;

    /**
     * 行高
     */
    private int lineHeight;

    /**
     * 底部三角形的高度
     */
    private int triangleHeight;

    /**
     * 文字内容
     */
    private String text;

    /**
     * 文字基准线
     */
    private int baseLineY;

    /**
     * 是否根据文字内容自适应高度
     */
    private boolean isAutoHeight;
    private float ratio;


    public MarkView(Context context) {
        super(context);
        init(context, null);
    }

    public MarkView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MarkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化属性参数
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MarkView);
        textSize = a.getDimensionPixelSize(R.styleable.MarkView_mv_textSize, spToPx(DEFAULT_TEXT_SIZE));
        maxTextNum = a.getInt(R.styleable.MarkView_mv_maxTextNum, DEFAULT_MAX_TEXT_NUM);
        backgroundColor = a.getColor(R.styleable.MarkView_mv_backgroundColor, DEFAULT_BACKGROUND_COLOR);
        textColor = a.getColor(R.styleable.MarkView_mv_textColor, DEFAULT_TEXT_COLOR);
        text = a.getString(R.styleable.MarkView_mv_text);
        isAutoHeight = a.getBoolean(R.styleable.MarkView_mv_isAutoHeight, true);
        ratio = a.getFloat(R.styleable.MarkView_mv_ratio, DEFAULT_TRIANGLE_RATIO);
        a.recycle();
    }

    /**
     * 初始化基本画笔
     */
    private void initPaint() {
        // 创建背景画笔
        backGroundPaint = new Paint();
        // 设置画笔颜色为蓝色
        backGroundPaint.setColor(backgroundColor);
        // 设置画笔宽度为10px
        backGroundPaint.setStrokeWidth(10f);
        // 设置抗锯齿边界更为平滑
        backGroundPaint.setAntiAlias(true);
        // 设置画笔模式为填充
        backGroundPaint.setStyle(Paint.Style.FILL);

        // 创建文字画笔
        textPaint = new Paint();
        textPaint.setStrokeWidth(4f);
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        //设置字体大小
        textPaint.setTextSize(textSize);
        //设置字体
        textPaint.setTypeface(Typeface.DEFAULT);
        // 设置文字对齐方式
        textPaint.setTextAlign(Paint.Align.CENTER);

        // 计算文字的高度
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        textHeight = Math.round(fontMetrics.descent - fontMetrics.ascent);
        lineHeight = Math.round(fontMetrics.bottom - fontMetrics.top);
        baseLineY = Math.round(Math.abs(textPaint.ascent() + textPaint.descent()) / 2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 初始化基本画笔
        initPaint();

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int minWidth = textSize + paddingLeft + paddingRight;
        int width = getMySize(widthMeasureSpec, minWidth);
        triangleHeight = Math.round(width * ratio);
        int minHeight;
        if (isAutoHeight && !TextUtils.isEmpty(text) && text.length() <= maxTextNum) {
            minHeight = text.length() * textHeight + getPaddingTop() + getPaddingBottom() + triangleHeight;
        } else {
            minHeight = maxTextNum * textHeight - textHeight / 2 + getPaddingTop() + getPaddingBottom() + triangleHeight;
        }
        setMeasuredDimension(width, getMySize(heightMeasureSpec, minHeight));
    }

    /**
     * 测量控件的大小
     *
     * @param measureSpec
     * @param minSize
     * @return
     */
    private int getMySize(int measureSpec, int minSize) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            // match_parent
            case MeasureSpec.UNSPECIFIED:
                result = specSize;
                break;
            // 精确值
            case MeasureSpec.EXACTLY:
                result = Math.max(specSize, minSize);
                break;
            // wrap_content
            case MeasureSpec.AT_MOST:
            default:
                result = minSize;
                break;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取view的宽高
        int width = getWidth();
        int height = getHeight();

        // 画书签背景
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(0, height);
        path.lineTo(width / 2, height - triangleHeight);
        path.lineTo(width, height);
        path.lineTo(width, 0);
        path.close();
        canvas.drawPath(path, backGroundPaint);

        // 画文字
        int startY = baseLineY + textHeight / 2 + getPaddingTop();
        Log.e("MarkView", "startY=" + startY);
        if (!TextUtils.isEmpty(text)) {
            for (int i = 0; i < text.length(); i++) {
                if (text.length() <= maxTextNum) {
                    canvas.drawText(text.substring(i, i + 1), width / 2, startY + textHeight * i, textPaint);
                } else {
                    if (i < maxTextNum - 1) {
                        canvas.drawText(text.substring(i, i + 1), width / 2, startY + textHeight * i, textPaint);
                    } else {
                        canvas.drawText("···", width / 2, startY + textHeight * i - textHeight / 4, textPaint);
                        break;
                    }
                }
            }
        }
        canvas.save();
    }

    /**
     * 设置文字
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    /**
     * 设置背景色
     *
     * @param backgroundColor
     */
    public void setMarkBackgroudColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        invalidate();
    }

    /**
     * 设置文字颜色
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
        invalidate();
    }

    /**
     * 设置文字大小
     *
     * @param textSize
     */
    public void setTextSize(int textSize) {
        this.textSize = spToPx(textSize);
        invalidate();
    }

    /**
     * 设置最大文字数量
     *
     * @param maxTextNum
     */
    public void setMaxTextNum(int maxTextNum) {
        this.maxTextNum = maxTextNum;
        invalidate();
    }

    /**
     * 设置自适应高度
     *
     * @param autoHeight
     */
    public void setAutoHeight(boolean autoHeight) {
        this.isAutoHeight = autoHeight;
        invalidate();
    }

    /**
     * sp 转 px
     *
     * @param spValue
     * @return
     */
    public int spToPx(float spValue) {
        float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
