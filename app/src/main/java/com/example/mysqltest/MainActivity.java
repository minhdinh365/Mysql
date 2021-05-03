package com.example.mysqltest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_Connect, btn_Read, btn_Insert, btn_Edit, btn_Delete;
    EditText ed_Id, ed_Ten, ed_Namsinh, ed_Diachi;

    String classs = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://192.168.1.15:3306/DoAnAndroid?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B7&useSSL=false";
    String un = "sa";
    String password = "12345678a@";
    Statement st;
    ResultSet rs;
    ResultSetMetaData rsmd;
    Connection conn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Connect = (Button) findViewById(R.id.btn_Connect);
        btn_Read = (Button) findViewById(R.id.btn_Read);
        btn_Insert = (Button) findViewById(R.id.btn_Insert);
        btn_Edit = (Button) findViewById(R.id.btn_Edit);
        btn_Delete = (Button) findViewById(R.id.btn_Delete);
        ed_Id = (EditText) findViewById(R.id.ed_id);
        ed_Ten = (EditText) findViewById(R.id.ed_Ten);
        ed_Namsinh = (EditText) findViewById(R.id.ed_Namsinh);
        ed_Diachi = (EditText) findViewById(R.id.ed_Diachi);

        btn_Connect.setOnClickListener(this);
        btn_Read.setOnClickListener(this);
        btn_Insert.setOnClickListener(this);
        btn_Edit.setOnClickListener(this);
        btn_Delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_Connect:
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                try {

                    Class.forName(classs);
                    conn = DriverManager.getConnection(url, un, password);

                    Toast.makeText(this, "Connect Successfull!", Toast.LENGTH_SHORT).show();


                } catch (ClassNotFoundException e) {
            Toast.makeText(this, "Lỗi 1", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(this, "Lỗi 2" + e.getMessage(), Toast.LENGTH_SHORT).show();
            System.out.println("Lỗi: " + e.getMessage());

        }

                break;
//**********************************************************************************************************************
            case R.id.btn_Read:

                try {
                    st = conn.createStatement();
                    rs = st.executeQuery("select * from module");
                    rsmd = rs.getMetaData();

                    while (rs.next()){
                       Toast.makeText(this, rsmd.getColumnName(1) + ": " + rs.getString(1) + " " + rsmd.getColumnName(2) + ": " + rs.getString(2) , Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
//**********************************************************************************************************************
            case R.id.btn_Insert:
                try {
                    st.executeUpdate("insert into sinhvien(HoTen, NamSinh, DiaChi) values ('" + ed_Ten.getText() + "', '" + ed_Namsinh.getText() + "', '" + ed_Diachi.getText() + "')");
                    Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
//**********************************************************************************************************************
            case R.id.btn_Edit:
                try {
                    st.executeUpdate("update sinhvien set HoTen='"+ ed_Ten.getText() + "', NamSinh='" + ed_Namsinh.getText() + "', DiaChi='" + ed_Diachi.getText() + "' where id='" + Integer.valueOf(ed_Id.getText().toString()) +"'");
                    Toast.makeText(this, "Edited", Toast.LENGTH_SHORT).show();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Toast.makeText(this, "Edited", Toast.LENGTH_SHORT).show();
                break;
//**********************************************************************************************************************
            case R.id.btn_Delete:

                try {
                    st.executeUpdate("delete from sinhvien where id=" + Integer.valueOf(ed_Id.getText().toString()));
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                break;
//**********************************************************************************************************************
        }
    }
}
