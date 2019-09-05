package xyz.imxqd.arcview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class ArcView extends View {

    Paint paint;
    RectF contentRect;
    int startColor, endColor;
    float startAngle, sweepAngle;
    float strokeWidth;

    public ArcView(Context context) {
        super(context);
        init(null);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(10 * 3);
        paint.setStyle(Paint.Style.STROKE);
        startColor = Color.parseColor("#0018B0F2");
        endColor = Color.parseColor("#FF18B0F2");

        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ArcView);
            int cap = ta.getInt(R.styleable.ArcView_cap, 1);
            if (cap == 0) {
                paint.setStrokeCap(Paint.Cap.BUTT);
            } else if (cap == 1) {
                paint.setStrokeCap(Paint.Cap.ROUND);
            } else {
                paint.setStrokeCap(Paint.Cap.SQUARE);
            }
            strokeWidth = ta.getDimensionPixelSize(R.styleable.ArcView_stroke_width, 10 * 3);
            paint.setStrokeWidth(strokeWidth);
            startAngle = ta.getFloat(R.styleable.ArcView_start_angle, 270);
            sweepAngle = ta.getFloat(R.styleable.ArcView_sweep_angle, 270);
            startColor = ta.getColor(R.styleable.ArcView_stroke_color_start, Color.parseColor("#0018B0F2"));
            endColor = ta.getColor(R.styleable.ArcView_stroke_color_end, Color.parseColor("#FF18B0F2"));
            ta.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        contentRect = null;
    }

    public void setStrokeWidth(float width) {
        this.strokeWidth = width;
        paint.setStrokeWidth(width);
    }

    public void setStrokeCap(Paint.Cap cap) {
        paint.setStrokeCap(cap);
    }

    public int getStartColor() {
        return startColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
        postInvalidate();
    }

    public int getEndColor() {
        return endColor;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
        postInvalidate();
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
        postInvalidate();
    }

    public float getSweepAngle() {
        return sweepAngle;
    }

    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        if (contentRect == null) {
            contentRect = new RectF(paddingLeft + strokeWidth * 0.5f,
                    paddingTop  + strokeWidth * 0.5f,
                    width - paddingLeft - strokeWidth * 0.5f,
                    height - paddingTop - strokeWidth * 0.5f);
        }
        if (sweepAngle >= 0) {
            Shader gradient = new SweepGradient (contentRect.centerX(), contentRect.centerY(),
                    new int[] {startColor, endColor, startColor}, new float[]{0f, sweepAngle / 360f, 1f});
            Matrix gradientMatrix = new Matrix();
            gradientMatrix.preRotate(startAngle, contentRect.centerX(), contentRect.centerY());
            gradient.setLocalMatrix(gradientMatrix);
            paint.setShader(gradient);
        } else {
            Shader gradient = new SweepGradient (contentRect.centerX(), contentRect.centerY(),
                    new int[] {endColor, startColor, endColor}, new float[]{0f, sweepAngle / -360f, 1f});
            Matrix gradientMatrix = new Matrix();
            gradientMatrix.preRotate(startAngle + sweepAngle, contentRect.centerX(), contentRect.centerY());
            gradient.setLocalMatrix(gradientMatrix);
            paint.setShader(gradient);
        }

        canvas.drawArc(contentRect, startAngle, sweepAngle, false, paint);
    }
}
