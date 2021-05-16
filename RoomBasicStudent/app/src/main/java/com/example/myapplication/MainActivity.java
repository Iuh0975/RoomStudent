package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ISendUser {

    Button btnSave, btnDelete, btnCancel;
    RecyclerView rcvName;
    EditText edtName;
    UserDatabase db;
    List<Student> lstUser = new ArrayList<Student>();
    StudentAdapter adapter;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    Student userChon = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectView();
        db = UserDatabase.getInstance(this);
        rcvName.setHasFixedSize(true);
        getAllUser();
        insertUser();
        deleteUser();
    }

    public void getAllUser()
    {
        lstUser.clear();
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lstUser = db.mainDao().getAllUser();
        adapter = new StudentAdapter(lstUser, this);
        rcvName.setLayoutManager(layoutManager);
        rcvName.setAdapter(adapter);
    }



    public void insertUser()
    {
        Context context = this;
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtName.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(context, "Vui lòng nhập thông tin", Toast.LENGTH_LONG).show();
                }
                else
                {
                    String name = edtName.getText().toString().trim();
                    Student us = new Student(name);
                    db.mainDao().insert(us);
                    Toast.makeText(context, "Thêm student thành công", Toast.LENGTH_LONG).show();
                    hideSoftKeyboard();
                    getAllUser();
                    Clear();
                }
            }
        });
    }

    public void deleteUser()
    {
        Context context = this;
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userChon == null)
                    Toast.makeText(context, "Vui lòng chọn student cần xóa", Toast.LENGTH_LONG).show();
                else
                {
                    db.mainDao().delete(userChon);
                    Toast.makeText(context, "Xóa student thành công", Toast.LENGTH_LONG).show();
                    getAllUser();
                    Clear();
                }
            }
        });
    }

    void Clear()
    {
        userChon = null;
        edtName.setText(null);
    }

    void ConnectView()
    {
        btnSave = (Button)findViewById(R.id.btnAdd);
        btnDelete = (Button)findViewById(R.id.btnRemove);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        rcvName = (RecyclerView)findViewById(R.id.rcvName);
        edtName = (EditText)findViewById(R.id.name_edt);
    }

    @Override
    public void sendUser(Student user) {
        userChon = user;
        edtName.setText(user.getName());
    }
    public void hideSoftKeyboard(){
        try {
            InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }

    }
}