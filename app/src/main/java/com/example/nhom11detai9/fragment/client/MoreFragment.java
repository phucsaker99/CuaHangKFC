package com.example.nhom11detai9.fragment.client;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.nhom11detai9.activity.OverallActivity;
import com.example.nhom11detai9.dao.AppDatabase;
import com.example.nhom11detai9.databinding.FragmentMoreBinding;
import com.example.nhom11detai9.model.AccountClient;
import com.example.nhom11detai9.model.Order;
import java.util.List;

public class MoreFragment extends Fragment implements View.OnClickListener {
    private FragmentMoreBinding binding;
    private int id = -1;
    private AccountClient client = new AccountClient();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setSaveInformation();
        initEvent();
    }

    private void initEvent() {
        binding.btnEdit.setOnClickListener(this);
        binding.btnLogout.setOnClickListener(this);
        binding.btnHistory.setOnClickListener(this);
        binding.btnAddress.setOnClickListener(this);
        binding.btnKfc.setOnClickListener(this);
    }

    private void setSaveInformation() {
        SharedPreferences share = getActivity().getSharedPreferences("Client", Context.MODE_PRIVATE);
        id = share.getInt("id", -1);
        if (id != -1) {
            client = AppDatabase.getInstance(getActivity()).getClientDao().getClient(id);
            binding.edtName.setText(client.getName());
            binding.edtPlace.setText(client.getAddress());
            binding.edtSdt.setText(client.getPhone());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_edit:
                setEditInformation();
                break;
            case R.id.btn_address:
                setAddress();
                break;
            case R.id.btn_logout:
                setLogout();
                break;
            case R.id.btn_history:
                setHistory();
                break;
            case R.id.btn_kfc:
                setAbout();
                break;
        }
    }

    private void setHistory() {
        SharedPreferences share = getActivity().getSharedPreferences("Client", Context.MODE_PRIVATE);
        String name = share.getString("account", "");
        List<Order> orders = AppDatabase.getInstance(getContext()).getOrderDao().getAll();
        int tong = 0;
        String description = "";
        for (Order order :  orders){
            if (order.getName().equals(name)){
                tong +=1;
                description += "Hóa đơn: "+tong+"\t\t\t"+order.getTotal()+"\n"+"Tổng giá: "+order.getPrice()+"\n\n";
            }
        }

        Dialog dialog = new AlertDialog.Builder(getContext()).
                setTitle(tong+" Hóa đơn")
                .setMessage(description)
                .setIcon(R.drawable.ic_order)
                .create();
        dialog.show();
    }

    private void setAddress() {
        SharedPreferences share = getActivity().getSharedPreferences("Client", Context.MODE_PRIVATE);
        String address = share.getString("address", "");

        Dialog dialog = new AlertDialog.Builder(getContext()).
                setTitle("Địa chỉ nhận hàng")
                .setMessage("Nơi nhận hàng: "+ address)
                .setIcon(R.drawable.ic_place)
                .create();
        dialog.show();
    }

    private void setAbout() {
        Dialog dialog = new AlertDialog.Builder(getContext()).
                setTitle("CÔNG TY LIÊN DOANH TNHH KFC VIỆT NAM")
                .setMessage("Số 292 Bà Triệu, P. Lê Đại Hành, Q. Hai Bà Trưng, TP. Hà Nội." +
                        "\nĐiện thoại: (028) 38489828" +
                        "\nEmail: lienhe@kfcvietnam.com.vn" +
                        "\nMã số thuế: 0100773885" +
                        "\nNgày cấp: 29/10/1998 - Nơi cấp: Cục Thuế Thành Phố Hà Nội")
                .setIcon(R.drawable.kfc_circle)
                .create();
        dialog.show();
    }

    private void setLogout() {
        SharedPreferences share = getActivity().getSharedPreferences("Client", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putInt("id", -1);
        editor.apply();
        Intent intent = new Intent(getActivity(), OverallActivity.class);
        startActivity(intent);
    }

    private void setEditInformation() {

        if (binding.btnEdit.getText().equals("Sửa")) {
            binding.btnEdit.setText("Cập nhật");
            binding.edtName.setEnabled(true);
            binding.edtPlace.setEnabled(true);
            binding.edtSdt.setEnabled(true);
        } else {
            binding.btnEdit.setText("Sửa");
            setUpdate();
            binding.edtName.setEnabled(false);
            binding.edtPlace.setEnabled(false);
            binding.edtSdt.setEnabled(false);
        }
    }

    private void setUpdate() {
        client.setName(binding.edtName.getText().toString());
        client.setAddress(binding.edtPlace.getText().toString());
        client.setPhone(binding.edtSdt.getText().toString());

        SharedPreferences share = getActivity().getSharedPreferences("Client", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("address", client.getAddress());
        editor.apply();

        AppDatabase.getInstance(getActivity()).getClientDao().update(client);
        Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_LONG).show();
    }
}
