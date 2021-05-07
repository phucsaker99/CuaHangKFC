package com.example.nhom11detai9.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.example.nhom11detai9.R;
import com.example.nhom11detai9.databinding.ActivityHomePageClientBinding;
import com.example.nhom11detai9.fragment.client.FoodFragment;
import com.example.nhom11detai9.fragment.client.MoreFragment;
import com.example.nhom11detai9.fragment.client.OrderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageClient extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ActivityHomePageClientBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_page_client);

        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new FoodFragment()).commit();
        binding.navClient.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.nav_food:
                selectedFragment = new FoodFragment();
                break;
            case R.id.nav_order:
                selectedFragment = new OrderFragment();
                break;
            case R.id.nav_more:
                selectedFragment = new MoreFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();
        return true;
    }
}
