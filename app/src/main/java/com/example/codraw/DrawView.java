package com.example.codraw;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View {

    private Paint paint;
    private Path path;
    private Bitmap bitmap;
    private Canvas drawCanvas;
    private float scaleFactor = 1.0f;

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.BLACK); // Màu mặc định
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(6f); // Độ dày nét vẽ

        path = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(bitmap);
        drawCanvas.drawColor(Color.WHITE); // nền trắng
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.scale(scaleFactor, scaleFactor);
        canvas.drawBitmap(bitmap, 0, 0, null); // vẽ bitmap đã lưu
        canvas.drawPath(path, paint);         // vẽ nét hiện tại
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX() / scaleFactor;
        float y = event.getY() / scaleFactor;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(path, paint); // ghi vào bitmap
                path.reset();                     // xoá path hiện tại
                break;
            default:
                return false;
        }

        invalidate(); // yêu cầu vẽ lại
        return true;
    }

    // Đổi màu bút vẽ
    public void setColor(int color) {
        paint.setColor(color);
    }

    // Chuyển sang chế độ tẩy
    public void useEraser() {
        paint.setColor(Color.WHITE);
    }

    // Phóng to / thu nhỏ
    public void zoom(float factor) {
        scaleFactor *= factor;
        invalidate();
    }

    // Xoá tất cả (reset bitmap)
    public void clear() {
        drawCanvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_OVER);
        invalidate();
    }

    // Trả về ảnh bitmap để lưu / chia sẻ
    public Bitmap getBitmap() {
        return bitmap.copy(Bitmap.Config.ARGB_8888, false);
    }
}
