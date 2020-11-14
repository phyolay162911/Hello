package com.example.listwithbaseadapter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.listwithbaseadapter.Model.LanguageInfo;
import com.example.listwithbaseadapter.Model.Utility.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ListView toDoList;
    FloatingActionButton fab;
    ArrayList<LanguageInfo> arrayList = new ArrayList<>();
    LanguageAdapter adapter;
    Integer tmpPosition;

    EditText txtLangName,txtDescription,txtReleaseDate,txtId;
    String name,desc,releasedate;Integer id,tmpListID;
    AlertDialog alertDialog;
    Button btnSave,btnCancel;
    LanguageInfo tmpLanguageInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toDoList = (ListView)findViewById(R.id.toDoList);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        //GenerateList();

        /*
        String[] nameList = {"Java","Android","C#","C++","PHP","Python","GO"};
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_item,nameList);
        toDoList.setAdapter(adapter);
        */
        try {
            GetAllData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        adapter = new LanguageAdapter(this,arrayList);
        toDoList.setAdapter(adapter);

        toDoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tmpLanguageInfo = arrayList.get(i);
                tmpListID = i;
                //showPopupDialog(tmpLanguageInfo);
                Toast.makeText(MainActivity.this,arrayList.get(i).getName(),Toast.LENGTH_LONG).show();
            }
        });

        fab.setOnClickListener(this);
    }

    protected void onRestart()
    {
        super.onRestart();
    }

    public void GetAllData() throws ParseException {
        DBHelper dbHelper = new DBHelper( MainActivity.this);
       arrayList =  dbHelper.GetAll();
    }
    private void GenerateList(){
        for(int i=0;i<15;i++){
            LanguageInfo info = new LanguageInfo();
            info.setId(i+1);
            info.setName("Language "+(i+1));
            Date d = new Date();
            info.setReleasedDate(d);
            info.setDescription(info.getName() +" "+"Lorem Ipsum is simply dummy text of the printing and typesetting industry");
            arrayList.add(info);
        }
    }

    private void showPopupDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.language_view,null);
        builder.setView(v);

        alertDialog = builder.create();
        alertDialog.show();
        //Showing

        txtId = (EditText) v.findViewById(R.id.txtId);
        txtLangName = (EditText) v.findViewById(R.id.txtLangName);
        txtDescription = (EditText) v.findViewById(R.id.txtDescription);
        txtReleaseDate = (EditText) v.findViewById(R.id.txtReleaseDate);

        btnSave = (Button) v.findViewById(R.id.btnSave);
        btnCancel = (Button) v.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }


    public void onClickOld(View view) {
        switch (view.getId()){
            case R.id.btnSave:
                //Code To Insert Here
                String name = txtLangName.getText().toString();
                String desc = txtDescription.getText().toString();
                String releaseDate = txtReleaseDate.getText().toString();

                int id = Integer.parseInt(txtId.getText().toString());
                if(id < 0){
                    id += arrayList.get(arrayList.size()-1).getId();
                }

                LanguageInfo info = new LanguageInfo();
                info.setId(id);
                info.setName(name);
                info.setDescription(desc);

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    info.setReleasedDate(df.parse(releaseDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                arrayList.add(info);

                alertDialog.hide();

                ;break;
            case R.id.btnCancel:

                ;break;
            case R.id.fab:
                showPopupDialog();break;
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSave:
                //Code To Insert Here
                String name = txtLangName.getText().toString();
                String desc = txtDescription.getText().toString();
                String releaseDate = txtReleaseDate.getText().toString();

                int id = Integer.parseInt(txtId.getText().toString());



                if(id < 0){
                    id += arrayList.get(arrayList.size()-1).getId();
                }

                LanguageInfo info = new LanguageInfo();
                info.setId(id);
                info.setName(name);
                info.setDescription(desc);

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    info.setReleasedDate(df.parse(releaseDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                DBHelper db = new DBHelper(this);
                if(tmpPosition < 0 )
                {
                    id = db.Insert(info);
                    info.setId(id);
                    arrayList.add(info);

                }
                else
                {
                    db.Update(info);
                }
                id = db.Insert(info);
                info.setId(id);

                arrayList.add(info);

                alertDialog.hide();

                ;break;
            case R.id.btnCancel:

                ;break;
            case R.id.fab:
                showPopupDialog();break;
        }
    }


}