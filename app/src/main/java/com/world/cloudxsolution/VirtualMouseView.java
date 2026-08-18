package com.world.cloudxsolution;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class VirtualMouseView extends View {

    private float cursorX = 500;
    private float cursorY = 500;
    private final Paint cursorPaint = new Paint();
    private boolean isVisible = true;

    public VirtualMouseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        cursorPaint.setColor(Color.RED);
        cursorPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isVisible) {
            canvas.drawCircle(cursorX, cursorY, 10, cursorPaint);
        }
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
