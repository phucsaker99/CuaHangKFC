package com.example.nhom11detai9.fragment.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.nhom11detai9.R;
import com.example.nhom11detai9.adapter.OrderTotalAdapter;
import com.example.nhom11detai9.dao.AppDatabase;
import com.example.nhom11detai9.databinding.FragmentOrderManagerBinding;
import com.example.nhom11detai9.model.Food;
import com.example.nhom11detai9.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderManagerFragment extends Fragment {
    private FragmentOrderManagerBinding binding;
    private OrderTotalAdapter adapter;
    private List<Order> arr = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_manager, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        adapter = new OrderTotalAdapter(getLayoutInflater());
        arr = AppDatabase.getInstance(getActivity()).getOrderDao().getAll();
        adapter.setArr(arr);
        binding.lvOrderManager.setAdapter(adapter);
        binding.txtTotal.setText("Tổng: "+arr.size()+" hóa đơn");
    }
}
