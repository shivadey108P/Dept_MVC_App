package com.example.dept_mvc_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText e1,e2,e3;
    CDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = findViewById(R.id.et1);
        e2 = findViewById(R.id.et2);
        e3 = findViewById(R.id.et3);
        db = new CDB(this);
    }

    public void onSave(View view){
        String dn,dl;
        CDept obj = new CDept(e2.getText().toString(),e3.getText().toString());
        db.addDept(obj);
        e2.setText("");
        e3.setText("");
        Toast.makeText(this, "Record Saved!",
                Toast.LENGTH_SHORT).show();
    }

    public void onFind(View view){
        int dno;
        CDept obj;
        try{
            dno = Integer.parseInt(e1.getText().toString());
            obj = db.getOneDepartment(dno);
            if(obj != null) {
                e2.setText(obj.dname);
                e3.setText(obj.dloc);
            }
            else{
                Toast.makeText(this, "No record found!",
                        Toast.LENGTH_SHORT).show();
                e1.setText("");
                e2.setText("");
                e3.setText("");
            }
        }catch (Exception e){
            Log.d("SELECT123",e.toString());
        }

    }
    public void onList(View view){
        List<CDept> rec = db.getAllValues();
        String str = "";
        String log1 = "Dept Id   Dept Name   Dept Location\n";
        str = str+log1;
        for(CDept cd : rec){
            String log = "   "+cd.dno+"              "+ cd.dname+"            "+ cd.dloc;
            log = log+"\n";
            str = str+log;
        }
        TextView t = findViewById(R.id.tv);
        t.setText(str);
    }
    public void onUpdate(View view){

    }
}