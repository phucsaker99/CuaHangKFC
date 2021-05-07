package com.example.nhom11detai9.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.nhom11detai9.R;
import com.example.nhom11detai9.databinding.ActivityOverallBinding;

public class OverallActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityOverallBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_overall);

        initView();
    }

    private void initView() {
        binding.btnAdmin.setOnClickListener(this);
        binding.btnClient.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_admin:
                setAdmin();
                break;
            case R.id.btn_client:
                setClient();
                break;
        }
    }

    //Khi click vào button admin sẽ show màn hình đăng nhập, đăng ký admin
    private void setAdmin() {
        //Gọi SharedPreferences để tạo ra key choose = 1
        SharedPreferences shareAdmin = getSharedPreferences("Choose", MODE_PRIVATE);
        SharedPreferences.Editor editor = shareAdmin.edit();
        editor.putInt("choose", 1);
        editor.apply();

        //Sau đó show màn hình đăng ký đăng nhập
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    //Khi click vào button client sẽ show màn hình đăng nhập, đăng ký admin
    private void setClient() {
        //Gọi SharedPreferences để tạo ra key choose = 2
        SharedPreferences shareClient = getSharedPreferences("Choose", MODE_PRIVATE);
        SharedPreferences.Editor editor = shareClient.edit();
        editor.putInt("choose", 2);
        editor.apply();

        //Sau đó show màn hình đăng ký đăng nhập
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
