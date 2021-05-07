package com.example.nhom11detai9.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom11detai9.databinding.ItemListFoodBinding;
import com.example.nhom11detai9.databinding.ItemTotalOrderBinding;
import com.example.nhom11detai9.model.Food;
import com.example.nhom11detai9.model.Order;

import java.util.List;

public class OrderTotalAdapter extends RecyclerView.Adapter<OrderTotalAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Order> arr;

    public OrderTotalAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void setArr(List<Order> arr) {
        this.arr = arr;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTotalOrderBinding binding = ItemTotalOrderBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = arr.get(position);
        holder.binding.txtName.setText("Khách hàng: "+order.getName());
        holder.binding.txtTotal.setText("Số món: "+order.getTotal());
        holder.binding.txtDescription.setText("Danh sách món: "+order.getDescription());
        holder.binding.txtAddress.setText("Địa chỉ: "+order.getAddress());
        holder.binding.txtPrice.setText("Tổng giá: "+order.getPrice());
        holder.binding.txtIndex.setText(""+(position+1));
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ItemTotalOrderBinding binding;

        public ViewHolder(@NonNull ItemTotalOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
