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
/**
 * Created by Miguel on 31/05/2017.
 */

public class Normalize_Pixels extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();

    }

    public int[] getAvg(int[] pix, int imageWidth, int imageHeigth) {
        //Lets find the sum of each color channel
        int ret_val[] = new int[3];
        int gr = 0, gg = 0, gb = 0;
        for (int x = 0; x < imageWidth; x++) {
            for (int y = 0; y < imageHeigth; y++) {
                int index = y * imageWidth + x;
                int r = (pix[index] >> 16) & 0xff;
                int g = (pix[index] >> 8) & 0xff;
                int b = pix[index] & 0xff;
                gr += r;
                gg += b;
                gb += g;
            }
        }


            ret_val[0] = gr;
            ret_val[1] = gg;
            ret_val[2] = gb;

        return ret_val;
    }
}
