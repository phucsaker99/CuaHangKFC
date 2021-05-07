package com.example.nhom11detai9.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom11detai9.R;
import com.example.nhom11detai9.dao.AppDatabase;
import com.example.nhom11detai9.databinding.ItemListFoodBinding;
import com.example.nhom11detai9.databinding.ItemOrderBinding;
import com.example.nhom11detai9.model.Food;
import com.example.nhom11detai9.model.Note;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Note> arr;
    private onItemClickedListener listener;

    public OrderAdapter(LayoutInflater inflater, onItemClickedListener listener) {
        this.inflater = inflater;
        this.listener = listener;
    }

    public void setArr(List<Note> arr) {
        this.arr = arr;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderBinding binding = ItemOrderBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Note note = arr.get(position);
        holder.binding.txtFood.setText("Món: " + note.getName());
        if (note.getDescription().equals("")) {
            holder.binding.txtDescription.setVisibility(View.GONE);
        } else {
            holder.binding.txtDescription.setText("Mô tả: "+ note.getDescription());
        }
        holder.binding.txtPrice.setText("Giá: "+note.getPrice() + " đ");
        if (note.getType().equals("Gà Rán")){
            holder.binding.imgFood.setBackgroundResource(R.drawable.image_chicken);
        }else if (note.getType().equals("Cơm")){
            holder.binding.imgFood.setBackgroundResource(R.drawable.image_rice);
        }else {
            holder.binding.imgFood.setBackgroundResource(R.drawable.image_soda);
        }

        if (listener != null) {
            holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppDatabase.getInstance(inflater.getContext()).getNoteDao().delete(note);
                    listener.onItemDeleteClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemOrderBinding binding;

        public ViewHolder(@NonNull ItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface onItemClickedListener {
        void onItemDeleteClick(int position);
    }
}
