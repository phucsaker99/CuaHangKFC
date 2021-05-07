package com.example.nhom11detai9.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom11detai9.R;
import com.example.nhom11detai9.dao.AppDatabase;
import com.example.nhom11detai9.databinding.ItemFoodBinding;
import com.example.nhom11detai9.databinding.ItemListFoodBinding;
import com.example.nhom11detai9.fragment.client.OrderFragment;
import com.example.nhom11detai9.model.Food;
import com.example.nhom11detai9.model.Note;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Food> arr;

    public FoodAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void setArr(List<Food> arr) {
        this.arr = arr;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListFoodBinding binding = ItemListFoodBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Food food = arr.get(position);
        if (food.getType().equals("Gà Rán")){
            holder.binding.imgFood.setImageResource(R.drawable.image_chicken);
        }else if (food.getType().equals("Cơm")){
            holder.binding.imgFood.setImageResource(R.drawable.image_rice);
        }else {
            holder.binding.imgFood.setImageResource(R.drawable.image_soda);
        }
        holder.binding.txtPrice.setText(food.getPrice()+" đ");
        holder.binding.txtFood.setText(food.getName());
        if (food.getDescription().isEmpty()){
            holder.binding.txtDescription.setVisibility(View.GONE);
        }else {
            holder.binding.txtDescription.setText(food.getDescription());
        }
        holder.binding.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note note = new Note();
                note.setName(food.getName());
                note.setDescription(food.getDescription());
                note.setPrice(food.getPrice());
                note.setType(food.getType());
                AppDatabase.getInstance(inflater.getContext()).getNoteDao().insert(note);
                Toast.makeText(inflater.getContext(), "Thành công", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ItemListFoodBinding binding;

        public ViewHolder(@NonNull ItemListFoodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
