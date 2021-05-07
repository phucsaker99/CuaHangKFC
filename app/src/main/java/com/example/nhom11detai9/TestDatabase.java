package com.example.nhom11detai9;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.nhom11detai9.dao.AppDatabase;
import com.example.nhom11detai9.databinding.ActivityTestDatabaseBinding;
import com.example.nhom11detai9.model.AccountAdmin;
import com.example.nhom11detai9.model.AccountClient;
import com.example.nhom11detai9.model.Food;

import java.util.List;

public class TestDatabase extends AppCompatActivity implements View.OnClickListener {
    ActivityTestDatabaseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_database);

        initEvent();
    }

    private void initEvent() {
        binding.btnClient.setOnClickListener(this);
        binding.btnAdmin.setOnClickListener(this);
        binding.btnFood.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_client:
                List<AccountClient> ds = AppDatabase.getInstance(this).getClientDao().getAll();
                ArrayAdapter<AccountClient> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ds);
                binding.lv.setAdapter(adapter);
                break;
            case R.id.btn_admin:
                List<AccountAdmin> ds1 = AppDatabase.getInstance(this).getAdminDao().getAll();
                ArrayAdapter<AccountAdmin> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ds1);
                binding.lv.setAdapter(adapter1);
                break;
            case R.id.btn_food:
                List<Food> ds2 = AppDatabase.getInstance(this).getFoodDao().getAll();
                ArrayAdapter<Food> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ds2);
                binding.lv.setAdapter(adapter2);
                break;
        }
    }
}
