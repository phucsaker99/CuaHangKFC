package com.example.nhom11detai9.fragment.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.nhom11detai9.R;
import com.example.nhom11detai9.adapter.OrderAdapter;
import com.example.nhom11detai9.dao.AppDatabase;
import com.example.nhom11detai9.databinding.FragmentOrderBinding;
import com.example.nhom11detai9.model.Note;
import com.example.nhom11detai9.model.Order;
import com.example.nhom11detai9.util.Checkout;

import java.util.List;

public class OrderFragment extends Fragment implements View.OnClickListener, OrderAdapter.onItemClickedListener {
    private FragmentOrderBinding binding;
    public static List<Note> arr;
    public static OrderAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        SharedPreferences share = getActivity().getSharedPreferences("Client", Context.MODE_PRIVATE);
        binding.edtPlace.setText(share.getString("address", "Hà Nội"));
    }

    public void initView() {
        arr = AppDatabase.getInstance(getContext()).getNoteDao().getAll();
        adapter = new OrderAdapter(getLayoutInflater(), this);
        adapter.setArr(arr);
        binding.lvOrder.setAdapter(adapter);

        double tong = 0;
        binding.txtTotal.setText(arr.size() + " món");
        for (Note note : arr) {
            tong += Double.parseDouble(note.getPrice());
        }
        binding.txtPrice.setText(tong + " đ");

        binding.btnPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
       if ( Checkout.isEmpty(binding.edtPlace)){
            return;
        }

        if (arr.size() == 0) {
            Toast.makeText(getContext(), "Bạn chưa đặt có đơn hàng nào trong giỏ hàng", Toast.LENGTH_LONG).show();
            return;
        }

        String description = "";
        String price = binding.txtPrice.getText().toString();
        String total = binding.txtTotal.getText().toString();
        String place = binding.edtPlace.getText().toString();

        for (Note note : arr) {
            if (note.equals(arr.get(arr.size()-1))){
                description += note.getName();
            }else {
                description += note.getName() + ", ";
            }
        }

        SharedPreferences share = getActivity().getSharedPreferences("Client", Context.MODE_PRIVATE);
        String name = share.getString("account", "anonymous");

        insertOrder(name, description, place, price, total);
        binding.txtPrice.setText("0.0 đ");
        binding.txtTotal.setText("0 món");
    }

    private void insertOrder(String name, String description, String place, String price, String total) {
        Order order = new Order(name, description, place, price, total);

        AppDatabase.getInstance(getContext()).getOrderDao().insert(order);
        Toast.makeText(getContext(), "Đặt hàng thành công", Toast.LENGTH_LONG).show();
        arr.clear();
        AppDatabase.getInstance(getContext()).getNoteDao().deleteAll();
        adapter.setArr(arr);
    }


    @Override
    public void onItemDeleteClick(int position) {
        arr.remove(position);
        adapter.setArr(arr);

        double tong = 0;
        binding.txtTotal.setText(arr.size() + " món");
        for (Note note : arr) {
            tong += Double.parseDouble(note.getPrice());
        }
        binding.txtPrice.setText(tong + " đ");
    }
}
