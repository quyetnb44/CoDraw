package com.example.codraw;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateNewActivity extends AppCompatActivity {

    private DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);

        drawView = findViewById(R.id.drawView);

        // Bắt sự kiện chọn màu
        findViewById(R.id.colorRed).setOnClickListener(v -> drawView.setColor(Color.RED));
        findViewById(R.id.colorOrange).setOnClickListener(v -> drawView.setColor(0xFFFF9800));
        findViewById(R.id.colorYellow).setOnClickListener(v -> drawView.setColor(0xFFFFFF00));
        findViewById(R.id.colorGreen).setOnClickListener(v -> drawView.setColor(Color.GREEN));
        findViewById(R.id.colorBlue).setOnClickListener(v -> drawView.setColor(Color.BLUE));
        findViewById(R.id.colorPurple).setOnClickListener(v -> drawView.setColor(0xFF9C27B0));
        findViewById(R.id.colorBlack).setOnClickListener(v -> drawView.setColor(Color.BLACK));

        // Toggle hiển thị bảng màu (nếu có nút)
        findViewById(R.id.btnColorPaletteToggle).setOnClickListener(v -> {
            View palette = findViewById(R.id.colorPalette);
            palette.setVisibility(palette.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        });
    }

    // ==== Các phương thức onClick gọi từ XML ====
    public void onPenClick(View view) {
        drawView.setColor(Color.BLACK);
    }

    public void onEraserClick(View view) {
        drawView.useEraser();
    }

    public void onZoomInClick(View view) {
        drawView.zoom(1.25f);
    }

    public void onZoomOutClick(View view) {
        drawView.zoom(0.8f);
    }

    public void onClearClick(View view) {
        drawView.clear();
    }

    public void onExportClick(View view) {
        exportImage();
    }

    public void onShareClick(View view) {
        shareImage();
    }

    // ==== Lưu ảnh ====
    private void exportImage() {
        Bitmap bitmap = drawView.getBitmap();
        String savedImageURL = MediaStore.Images.Media.insertImage(
                getContentResolver(), bitmap, "Drawing", "Drawn by CoDraw App");

        if (savedImageURL != null) {
            Toast.makeText(this, "Đã lưu ảnh!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Lưu ảnh thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    // ==== Chia sẻ ảnh ====
    private void shareImage() {
        Bitmap bitmap = drawView.getBitmap();
        String path = MediaStore.Images.Media.insertImage(
                getContentResolver(), bitmap, "Drawing", null);

        if (path == null) {
            Toast.makeText(this, "Không thể chia sẻ ảnh", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uri = Uri.parse(path);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Chia sẻ từ ứng dụng CoDraw");
        startActivity(Intent.createChooser(shareIntent, "Chia sẻ qua"));
    }
}
