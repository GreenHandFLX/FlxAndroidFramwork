package com.xinxi.ergatebesh.business.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.xinxi.ergatebesh.App;
import com.xinxi.ergatebesh.R;
import com.xinxi.ergatebesh.business.menu.MenuActivity;
import com.xinxi.ergatebesh.common.Base.BaseActivity;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    private App mApp;
    private Spinner mSp1;
    private String [] logmethod;
    private ArrayAdapter<String> adapter;
    private String dropdownSpinner;
    private EditText username;
    private EditText password;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        initData();
        initEvent();
    }

    public void initViews(){
        mSp1 = (Spinner)this.findViewById(R.id.more_lang_Spinner);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.submitBtn);
    }

    public void initData(){
        logmethod = getResources().getStringArray(R.array.MoreLanguage);
        adapter = new ArrayAdapter<String>(LoginActivity.this,R.layout.msimple_spinner_item,logmethod);
        mSp1.setAdapter(adapter);
    }

    public void initEvent(){
        loginBtn.setOnClickListener(v -> {
            Intent intent=new Intent(this, MenuActivity.class);
            startActivity(intent);
        });
    }
}