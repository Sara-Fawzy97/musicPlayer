package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

public class list extends AppCompatActivity {

    ListView list;
    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        list=findViewById(R.id.list);

        permission();
    }

    public void permission(){

        Dexter.withActivity(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        display();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                        token.continuePermissionRequest();
                    }
                }).check();
    }

    public ArrayList<File>  songs(File file)
    {
        ArrayList<File>  arrayList=new ArrayList<>();
        File[] files = file.listFiles();

        for (File single: files){

            if(single.isDirectory() && !single.isHidden())
            {
                arrayList.addAll(songs(single));
            }
            else {
                if(single.getName().endsWith(".mp3") || single.getName().endsWith(".wav"))
                {arrayList.add(single);}
            }
        }


        return arrayList;
    }

    void display()
    {
          final ArrayList<File> mySongs= songs(Environment.getExternalStorageDirectory());

          items=new String[mySongs.size()];
          for(int i=0; i<mySongs.size();i++){

               items[i]=mySongs.get(i).getName().toString().replace(".mp3","").replace(".wav","");
          }
        ArrayAdapter <String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
          list.setAdapter(adapter);


          list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  String songName=list.getItemAtPosition(position).toString();

                  startActivity(new Intent(getApplicationContext(),MainActivity.class)
                  .putExtra("songs", mySongs).putExtra("songName",songName).putExtra("pos",position));
                  finish();
              }
          });
    }
}
