package com.example.nhom11detai9.fragment.admin;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.nhom11detai9.R;
import com.example.nhom11detai9.adapter.FoodManagerAdapter;
import com.example.nhom11detai9.dao.AppDatabase;
import com.example.nhom11detai9.databinding.AddFoodBinding;
import com.example.nhom11detai9.databinding.FragmentFoodManagerBinding;
import com.example.nhom11detai9.model.Food;
import com.example.nhom11detai9.util.Checkout;
import java.util.ArrayList;
import java.util.List;

public class FoodManagerFragment extends Fragment implements View.OnClickListener {
    private FragmentFoodManagerBinding binding;
    private AddFoodBinding addFoodBinding;
    private static final int REQUEST_IMAGE = 1;
    private String image = null;
    private FoodManagerAdapter adapter;
    List<Food> arr = null;
    private ArrayAdapter adapterList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_manager, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initEvent();
    }

    private void initEvent() {
        binding.btnAdd.setOnClickListener(this);
        arr = AppDatabase.getInstance(getActivity()).getFoodDao().getAll();
        adapter = new FoodManagerAdapter(getLayoutInflater());
        Toast.makeText(getContext(), ""+arr.size(), Toast.LENGTH_LONG).show();
        binding.lvFoodManager.setAdapter(adapter);
        adapter.setArr(arr);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        ArrayList<String> list = new ArrayList<>();
        addFoodBinding = AddFoodBinding.inflate(getLayoutInflater());
        list.add("Gà Rán");
        list.add("Cơm");
        list.add("Thức Uống");
        adapterList = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, list);
        addFoodBinding.spinnerType.setAdapter(adapterList);
        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(addFoodBinding.getRoot())
                .show();
        addFoodBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = addFoodBinding.editName.getText().toString();
                String type = addFoodBinding.spinnerType.getSelectedItem().toString();
                String price = addFoodBinding.editPrice.getText().toString();
                String description = addFoodBinding.editDescription.getText().toString();

                if (Checkout.isStringEmpty(name, type, price)){
                    Toast.makeText(getActivity(), "Bạn cần nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
                    return;
                }

                Food food = new Food(name,description, type, price);
                AppDatabase.getInstance(getActivity()).getFoodDao().insert(food);
                arr = AppDatabase.getInstance(getActivity()).getFoodDao().getAll();
                adapter.setArr(arr);
                dialog.dismiss();
            }
        });
    }
}
