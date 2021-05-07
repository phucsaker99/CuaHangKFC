package com.example.nhom11detai9.fragment.client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.nhom11detai9.R;
import com.example.nhom11detai9.adapter.FoodAdapter;
import com.example.nhom11detai9.dao.AppDatabase;
import com.example.nhom11detai9.databinding.FragmentFoodBinding;
import com.example.nhom11detai9.model.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodFragment extends Fragment {
    private FragmentFoodBinding binding;
    private List<Food> arr;
    private FoodAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    private void initView() {
        arr = new ArrayList<>();
        adapter = new FoodAdapter(getLayoutInflater());
        arr = AppDatabase.getInstance(getContext()).getFoodDao().getAll();
        adapter.setArr(arr);
        binding.lvDetails.setAdapter(adapter);
    }
}
