package com.example.codraw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Căn lề theo system insets (status bar, nav bar...)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Xử lý khi bấm vào danh mục "Phong cảnh"
        LinearLayout phongCanhCategory = findViewById(R.id.item_category_phong_canh);
        if (phongCanhCategory != null) {
            phongCanhCategory.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, PhongCanhActivity.class);
                startActivity(intent);
            });
        }
        //Xử lý khi bấm vào danh mục Chân Dung
        LinearLayout chanDungCategory = findViewById(R.id.item_category_chan_dung);
        if (chanDungCategory != null) {
            chanDungCategory.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, ChanDungActivity.class);
                startActivity(intent);
            });
        }
        // xu li ainimation
        LinearLayout hoatHinhCategory = findViewById(R.id.item_category_hoat_hinh);
        if (hoatHinhCategory != null) {
            hoatHinhCategory.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, AnimationActivity.class);
                startActivity(intent);
            });
        }
        //trừu tượng
        LinearLayout truuTuongCategory = findViewById(R.id.item_category_truu_tuong);
        if (truuTuongCategory != null) {
            truuTuongCategory.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, TruuTuongActivity.class);
                startActivity(intent);
            });
        }
        // nút tạo mới
        Button createBtn = findViewById(R.id.button);
        createBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CreateNewActivity.class);
            startActivity(intent);
        });



    }
}
