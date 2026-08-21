package com.world.cloudxsolution.romstation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class VirtualMouseView extends View {

    private float cursorX = 500;
    private float cursorY = 500;
    private boolean isVisible = true;

    // Base pointer size; the on-screen arrow is drawn at this scale.
    private static final float POINTER_SCALE = 1.6f;

    private final Path pointerPath = new Path();
    private final Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint pulsePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float pulseProgress = 1f; // 1 = finished/idle
    private ValueAnimator pulseAnimator;

    public VirtualMouseView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Shadows via Paint require a software layer since the view is
        // otherwise hardware-accelerated by default.
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        fillPaint.setColor(Color.WHITE);
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setShadowLayer(6f, 0f, 2f, Color.argb(140, 0, 0, 0));

        outlinePaint.setColor(Color.rgb(30, 30, 30));
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setStrokeWidth(2.5f);
        outlinePaint.setStrokeJoin(Paint.Join.ROUND);

        pulsePaint.setStyle(Paint.Style.STROKE);
        pulsePaint.setColor(Color.rgb(30, 144, 255)); // dodger blue
        pulsePaint.setStrokeWidth(4f);

        buildPointerPath();
    }

    // Classic mouse-arrow silhouette, tip at (0,0), pointing up-left to down-right.
    private void buildPointerPath() {
        pointerPath.reset();
        pointerPath.moveTo(0f, 0f);
        pointerPath.lineTo(0f, 22f);
        pointerPath.lineTo(5.2f, 17.3f);
        pointerPath.lineTo(8.6f, 24.8f);
        pointerPath.lineTo(11.6f, 23.4f);
        pointerPath.lineTo(8.4f, 16.1f);
        pointerPath.lineTo(15f, 15.6f);
        pointerPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isVisible) return;

        if (pulseProgress < 1f) {
            float radius = 10f + pulseProgress * 26f;
            int alpha = (int) (200 * (1f - pulseProgress));
            pulsePaint.setAlpha(alpha);
            canvas.drawCircle(cursorX, cursorY, radius, pulsePaint);
        }

        canvas.save();
        canvas.translate(cursorX, cursorY);
        canvas.scale(POINTER_SCALE, POINTER_SCALE);
        canvas.drawPath(pointerPath, fillPaint);
        canvas.drawPath(pointerPath, outlinePaint);
        canvas.restore();
    }

    public void moveCursor(float dx, float dy) {
        cursorX += dx;
        cursorY += dy;

        // Clamp to screen bounds
        if (cursorX < 0) cursorX = 0;
        if (cursorX > getWidth()) cursorX = getWidth();
        if (cursorY < 0) cursorY = 0;
        if (cursorY > getHeight()) cursorY = getHeight();

        invalidate();
    }

    /** Plays a short expanding ring animation centered on the cursor, for click feedback. */
    public void pulseClick() {
        if (pulseAnimator != null) {
            pulseAnimator.cancel();
        }
        pulseProgress = 0f;
        pulseAnimator = ValueAnimator.ofFloat(0f, 1f);
        pulseAnimator.setDuration(220);
        pulseAnimator.setInterpolator(new DecelerateInterpolator());
        pulseAnimator.addUpdateListener(anim -> {
            pulseProgress = (float) anim.getAnimatedValue();
            invalidate();
        });
        pulseAnimator.start();
    }

    public float getCursorX() {
        return cursorX;
    }

    public float getCursorY() {
        return cursorY;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
        invalidate();
    }
}