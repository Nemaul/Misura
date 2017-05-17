    package com.misura.tpi.misura;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.net.Uri;
import android.widget.ImageView;

import java.text.DecimalFormat;

public class Results_Display extends AppCompatActivity {

    public double roundTwoDecimals(double to_round) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(to_round));
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resuslts_display);
        Bundle extras = getIntent().getExtras();
        double fetch_area = extras.getDouble("calc_area");
        double rnd_area = roundTwoDecimals(fetch_area);
        String disp_area = Double.toString(rnd_area);
        TextView disp = (TextView) findViewById(R.id.textViewdata);
        disp.setText(disp_area+"dm2");
        Uri uri = getIntent().getParcelableExtra("imageUri");
        ImageView viewimage = (ImageView)findViewById(R.id.imageView1);
        viewimage.setImageURI(uri);




        }





    }

