package com.example.nhom11detai9.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.nhom11detai9.R;
import com.example.nhom11detai9.dao.AppDatabase;
import com.example.nhom11detai9.databinding.ActivityLoginBinding;
import com.example.nhom11detai9.model.AccountAdmin;
import com.example.nhom11detai9.model.AccountClient;
import com.example.nhom11detai9.util.Checkout;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private ActivityLoginBinding binding;
    private AccountClient client = new AccountClient();
    private AccountAdmin admin = new AccountAdmin();
    private int choose = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        choose = getSharedPreferences("Choose", MODE_PRIVATE).getInt("choose", -1);

        //If choose ==1 tức là mình đang phía admin ngược lại là khách hàng
        if (choose == 1){
            SharedPreferences share = getSharedPreferences("Admin", MODE_PRIVATE);
            binding.chkCheck.setChecked(share.getInt("checked", 0) == 1);
            binding.edtName.setText(share.getString("account", ""));
            binding.edtPassword.setText(share.getString("password", ""));
            Toast.makeText(this, ""+choose , Toast.LENGTH_LONG).show();
        }else {
            SharedPreferences share = getSharedPreferences("Client", MODE_PRIVATE);
            binding.chkCheck.setChecked(share.getInt("checked", 0) == 1);
            binding.edtName.setText(share.getString("account", ""));
            binding.edtPassword.setText(share.getString("password", ""));
        }
        initEvent();
    }

    private void initEvent() {
        binding.btnLogin.setOnClickListener(this);
        binding.btnSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                setLogin();
                break;
            case R.id.btn_signup:
                setSignUp();
                break;
        }
    }

    //Click button đăng nhập thì kiếm tra nếu tài khoản tồn tại thì đăng nhập
    //Nếu không thì show thông báo thông báo
    private void setLogin() {
        if (binding.edtRepassword.getVisibility() == View.VISIBLE) {
            binding.edtRepassword.setVisibility(View.GONE);
            return;
        }

        if (Checkout.isEmpty(binding.edtName, binding.edtPassword)) {
            return;
        }

        String account = binding.edtName.getText().toString();
        String password = binding.edtPassword.getText().toString();

        //Nếu choose == 1 thì kiếm tra bảng tài khoản nếu có thì đăng nhập vào trang quản lý Admin
        if (choose == 1){
            admin = AppDatabase.getInstance(this).getAdminDao().checkAccount(account, password);

            if (admin != null) {
                setCheckBox();
                setSaveInformation();
                Intent intent = new Intent(this, HomePageAdmin.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_LONG).show();
            }
        }
        //Nếu choose == 2 thì kiếm tra bảng tài khoản nếu có thì đăng nhập vào trang quản lý Client
        else {
            client = AppDatabase.getInstance(this).getClientDao().checkAccount(account, password);

            if (client != null) {
                setCheckBox();
                setSaveInformation();
                Intent intent = new Intent(this, HomePageClient.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_LONG).show();
            }
        }
    }

    //Kiểm tra nếu checkbox được chọn thì lưu vào SharedPreferences thông tin account và password
    //Để lần sau đăng nhập sẽ đc kiểm tra lại vào EditText
    private void setCheckBox() {
        if (choose == 1){
            SharedPreferences share = getSharedPreferences("Admin", MODE_PRIVATE);
            SharedPreferences.Editor editor = share.edit();
            if (binding.chkCheck.isChecked()) {
                editor.putString("account", admin.getAccount());
                editor.putString("password", admin.getPassword());
                editor.putInt("checked", 1);
            } else {
                editor.putString("account", "");
                editor.putString("password", "");
                editor.putInt("checked", 0);
            }
            editor.apply();
        }else {
            SharedPreferences share = getSharedPreferences("Client", MODE_PRIVATE);
            SharedPreferences.Editor editor = share.edit();
            if (binding.chkCheck.isChecked()) {
                editor.putString("account", client.getAccount());
                editor.putString("password", client.getPassword());
                editor.putInt("checked", 1);
            } else {
                editor.putString("account", "");
                editor.putString("password", "");
                editor.putInt("checked", 0);
            }
            editor.apply();
        }

    }

    //Lưu id của đối tượng vào SharedPreferences để thực hiện sau này
    private void setSaveInformation() {
        if (choose == 1){
            SharedPreferences share = this.getSharedPreferences("Admin", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = share.edit();
            if (admin != null) {
                editor.putInt("id", admin.getId());
                editor.apply();
            }
        }else {
            SharedPreferences share = this.getSharedPreferences("Client", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = share.edit();
            if (client != null) {
                editor.putInt("id", client.getId());
                editor.apply();
            }
        }

    }

    //Button kiểm tra đăng ký tài khoản
    private void setSignUp() {
        //Kiểm tra editText re-password tồn tại chưa nếu chưa thì show lên
        if (binding.edtRepassword.getVisibility() == View.GONE) {
            binding.edtRepassword.setVisibility(View.VISIBLE);
            return;
        }

        //Kiểm tra nếu EditText để trống thì show error
        if (Checkout.isEmpty(binding.edtName, binding.edtPassword, binding.edtRepassword)) {
            return;
        }

        if (binding.edtPassword.getText().toString().equals(binding.edtRepassword.getText().toString())) {
            if (choose == 1){
                admin.setAccount(binding.edtName.getText().toString());
                admin.setPassword(binding.edtPassword.getText().toString());
                if (AppDatabase.getInstance(this).getAdminDao().getAdmin(admin.getAccount()) != null) {
                    Toast.makeText(this, "Tài khoản đã tồn tại vui lòng điền admin khác", Toast.LENGTH_LONG).show();
                    Checkout.format(binding.edtName, binding.edtPassword, binding.edtRepassword);
                    return;
                } else {
                    if (admin.getId() > 0) {
                        AppDatabase.getInstance(this).getAdminDao().update(admin);
                    } else {
                        AppDatabase.getInstance(this).getAdminDao().insert(admin);
                    }
                }
            }else {
                client.setAccount(binding.edtName.getText().toString());
                client.setPassword(binding.edtPassword.getText().toString());
                if (AppDatabase.getInstance(this).getClientDao().getClient(client.getAccount()) != null) {
                    Toast.makeText(this, "Tài khoản đã tồn tại vui lòng điền user khác", Toast.LENGTH_LONG).show();
                    Checkout.format(binding.edtName, binding.edtPassword, binding.edtRepassword);
                    return;
                } else {
                    if (client.getId() > 0) {
                        AppDatabase.getInstance(this).getClientDao().update(client);
                    } else {
                        AppDatabase.getInstance(this).getClientDao().insert(client);
                    }
                }
            }
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
            binding.edtRepassword.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "Mật khẩu không khớp nhau vui lòng kiểm tra lại", Toast.LENGTH_LONG).show();
            Checkout.format(binding.edtName, binding.edtPassword, binding.edtRepassword);
        }
    }
}
