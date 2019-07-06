package com.example.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    Button  pause,next,back,list;
    TextView name;
    SeekBar seek;

    MediaPlayer mediaPlayer;

Intent intent;
 String fname;
int position;
 ArrayList<File> mySongs=new ArrayList<>();
Thread updateSeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        pause=findViewById(R.id.pause);
        next=findViewById(R.id.next);
        back=findViewById(R.id.back);
        list=findViewById(R.id.list);

        name=findViewById(R.id.name);
        seek=findViewById(R.id.seekBar);


updateSeek=new Thread(){

    @Override
    public void run() {
        int totalDuration=mediaPlayer.getDuration();
        int currentPosition= 0;

        while(currentPosition<totalDuration)
        {
            try{
                 sleep(500);
                 currentPosition=mediaPlayer.getCurrentPosition();
                 seek.setProgress(currentPosition);

            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
};

         if(mediaPlayer!=null)
         {
             mediaPlayer.stop();
             mediaPlayer.release();
         }

         intent=getIntent();
         Bundle bundle= intent.getExtras();

         mySongs=(ArrayList) bundle.getParcelableArrayList("songs");
         fname= mySongs.get(position).getName().toString();

         String  songName= intent.getStringExtra("songName");

         name.setText(songName);
         name.setSelected(true);


         position=bundle.getInt("pos",0);

         Uri u=Uri.parse(mySongs.get(position).toString());

         mediaPlayer=MediaPlayer. create(getApplicationContext(),u);

         mediaPlayer.start();
         seek.setMax(mediaPlayer.getDuration());


         seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
             @Override
             public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

             }

             @Override
             public void onStartTrackingTouch(SeekBar seekBar) {

             }

             @Override
             public void onStopTrackingTouch(SeekBar seekBar) {

                 mediaPlayer.seekTo(seekBar.getProgress());
             }
         });


////ending onCreate method
    }


//// In class
    public void list(View view){
        intent=new Intent(this,list.class);
        startActivity(intent);
        finish();
    }

    public void pause_btn(View view)
    {
        seek.setMax(mediaPlayer.getDuration());

        if(mediaPlayer.isPlaying())
        {
            pause.setBackgroundResource(R.drawable.play);
            mediaPlayer.pause();
        }
        else
        {
            pause.setBackgroundResource(R.drawable.pause);
            mediaPlayer.start();
        }
    }

    public void next_btn(View view)
    {
        mediaPlayer.stop();
        mediaPlayer.release();
        position=((position+1)%mySongs.size());

        Uri u= Uri.parse(mySongs.get(position).toString());

        mediaPlayer=MediaPlayer.create(getApplicationContext(),u);

        fname=mySongs.get(position).getName().toString();
        name.setText(fname);
        mediaPlayer.start();

    }

    public void back_btn(View view)
    {
        mediaPlayer.stop();
        mediaPlayer.release();
        position=((position-1)%mySongs.size());

        Uri u=Uri.parse(mySongs.get(position).toString());

        mediaPlayer=MediaPlayer.create(getApplicationContext(),u);

        fname=mySongs.get(position).getName().toString();
        name.setText(fname);

        mediaPlayer.start();

    }
//closing class
}
