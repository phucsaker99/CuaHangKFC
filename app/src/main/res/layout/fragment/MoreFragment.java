package com.example.detai9nhom11.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.detai9nhom11.R;
import com.example.detai9nhom11.dao.AppDatabase;
import com.example.detai9nhom11.databinding.FragmentMoreBinding;
import com.example.detai9nhom11.model.AccountClient;

public class MoreFragment extends Fragment implements View.OnClickListener {
    private FragmentMoreBinding binding;
    private int id = -1;
    private AccountClient client;

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
    }

    private void setSaveInformation() {
        SharedPreferences share = getActivity().getSharedPreferences("Client", Context.MODE_PRIVATE);
        id = share.getInt("id", -1);
        if (id != -1){
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
        }
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

    }
}
