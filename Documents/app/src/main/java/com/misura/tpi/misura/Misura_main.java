package com.misura.tpi.misura;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Misura_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misura_main);
    }

    public void camera_call(View view){
        Intent camcall = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(camcall);
    }

    public void get_image() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);
    }



}
