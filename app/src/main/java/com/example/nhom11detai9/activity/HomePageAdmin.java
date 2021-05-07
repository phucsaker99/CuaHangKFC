package com.example.nhom11detai9.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.nhom11detai9.R;
import com.example.nhom11detai9.databinding.ActivityHomePageAdminBinding;
import com.example.nhom11detai9.fragment.admin.FoodManagerFragment;
import com.example.nhom11detai9.fragment.admin.OrderManagerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageAdmin extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ActivityHomePageAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_page_admin);

        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new FoodManagerFragment()).commit();
        binding.navAdmin.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.nav_food:
                selectedFragment = new FoodManagerFragment();
                break;
            case R.id.nav_order:
                selectedFragment = new OrderManagerFragment();
                break;
            case R.id.nav_exit:
                showDialogExit();
                return false;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();
        return true;
    }

    private void showDialogExit() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setIcon(R.drawable.ic_notification)
                .setMessage("Bạn có muốn đẳng xuất không")
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(HomePageAdmin.this, OverallActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        dialog.create();
        dialog.show();
    }
}
