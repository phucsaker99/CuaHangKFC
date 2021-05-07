package com.example.nhom11detai9.adapter;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom11detai9.R;
import com.example.nhom11detai9.dao.AppDatabase;
import com.example.nhom11detai9.databinding.AddFoodBinding;
import com.example.nhom11detai9.databinding.ItemFoodBinding;
import com.example.nhom11detai9.model.Food;
import com.example.nhom11detai9.util.Checkout;

import java.util.ArrayList;
import java.util.List;

public class FoodManagerAdapter extends RecyclerView.Adapter<FoodManagerAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Food> arr;

    public FoodManagerAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void setArr(List<Food> arr) {
        this.arr = arr;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodBinding binding = ItemFoodBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Food food = arr.get(position);

        holder.binding.txtContent.setText(food.getName()+"\n"+food.getPrice()+" đ\n"+food.getDescription());
        if (food.getType().compareTo("Gà Rán")==0){
            holder.binding.imgFood.setImageResource(R.drawable.image_chicken);
        }else if (food.getType().compareTo("Cơm") == 0){
            holder.binding.imgFood.setImageResource(R.drawable.image_rice);
        }else {
            holder.binding.imgFood.setImageResource(R.drawable.image_soda);
        }
        holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase.getInstance(inflater.getContext()).getFoodDao().delete(food);
                arr = AppDatabase.getInstance(inflater.getContext()).getFoodDao().getAll();
                notifyDataSetChanged();
            }
        });

        holder.binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(position);
            }
        });
    }

    private void showDialog(final int postion) {
        ArrayList<String> list = new ArrayList<>();
        final AddFoodBinding addFoodBinding = AddFoodBinding.inflate(inflater);
        list.add("Gà Rán");
        list.add("Cơm");
        list.add("Thức Uống");
        final ArrayAdapter adapterList = new ArrayAdapter(inflater.getContext(), R.layout.support_simple_spinner_dropdown_item, list);
        addFoodBinding.spinnerType.setAdapter(adapterList);
        final AlertDialog dialog = new AlertDialog.Builder(inflater.getContext())
                .setView(addFoodBinding.getRoot())
                .show();
        addFoodBinding.editName.setText(arr.get(postion).getName());
        if (arr.get(postion).getType().compareTo("Gà Rán") == 0){
            addFoodBinding.spinnerType.setSelection(0);
        }else if(arr.get(postion).getType().compareTo("Cơm") == 0){
            addFoodBinding.spinnerType.setSelection(1);
        }else {
            addFoodBinding.spinnerType.setSelection(2);
        }
        addFoodBinding.editPrice.setText(arr.get(postion).getPrice());
        addFoodBinding.editDescription.setText(arr.get(postion).getDescription());

        addFoodBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = addFoodBinding.editName.getText().toString();
                String type = addFoodBinding.spinnerType.getSelectedItem().toString();
                String price = addFoodBinding.editPrice.getText().toString();
                String description = addFoodBinding.editDescription.getText().toString();

                if (Checkout.isStringEmpty(name, type, price)){
                    Toast.makeText(inflater.getContext(), "Bạn cần nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
                    return;
                }

                Food food = arr.get(postion);
                food.setName(name);
                food.setType(type);
                food.setPrice(price);
                food.setDescription(description);
                AppDatabase.getInstance(inflater.getContext()).getFoodDao().update(food);
                arr = AppDatabase.getInstance(inflater.getContext()).getFoodDao().getAll();
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemFoodBinding binding;

        public ViewHolder(ItemFoodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
