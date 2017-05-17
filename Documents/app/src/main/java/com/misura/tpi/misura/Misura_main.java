package com.misura.tpi.misura;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.net.Uri;

import java.io.*;
import android.graphics.*;

public class Misura_main extends AppCompatActivity {


    public static final double ELEMENT_AREA_DM2 = 5.53;
    public static final double ELEMENT_R_VAL = 1;
    public static final double ELEMENT_G_VAL = 115.5;
    public static final double ELEMENT_B_VAL = 188.5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misura_main);
    }

    public void camera_call(View view){
        Intent camcall = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(camcall);
    }

    public void get_image(View view) {
        /*Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);*/
        final int SELECT_PHOTO = 100;
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    public int leather(int r, int g, int b){
        if (r < 100 && g > 10 && b >100)
            return 1;
        else if (r < 100 && g > 10 && b >100)
            return 0;
        return -1;
    }


    public double three_rule(int valid, int element){
        return (ELEMENT_AREA_DM2 * valid) / element ;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);


                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                        Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);

                        System.out.println("Succesfully selected");

                        int imageWidth = yourSelectedImage.getWidth();
                        int imageHeigth = yourSelectedImage.getHeight();

                        int pix [] = new int[imageWidth*imageHeigth];
                        yourSelectedImage.getPixels(pix, 0, imageWidth, 0, 0, imageWidth, imageHeigth);

                        int valid_pixels = 0 ;
                        int magnet_pixels = 0;
                        int total_pix = imageHeigth*imageWidth;


                        for (int x = 0 ; x< imageWidth; x++){
                            for(int y = 0; y< imageHeigth; y++){
                                int index  = y*imageWidth+x;
                                int r = (pix[index] >> 16) & 0xff;
                                int g = (pix[index] >> 8) & 0xff;
                                int b = pix[index] & 0xff;

                                int desition = leather(r, g, b) ;
                                if (desition == 1){
                                    valid_pixels++;
                                }else{
                                    if(desition == 0)
                                    magnet_pixels++;
                                }
                            }
                        }
                        double disp_area = three_rule(valid_pixels,magnet_pixels);
                        /*System.out.println("The image has: "+ imageHeigth*imageWidth + " of resolution");
                        System.out.println("The image has: "+ valid_pixels + " valid pixels");
                        System.out.println("The image has: "+ element_pixels + " element pixels");
                        System.out.println("The piece has: "+ disp_area + "dm2");*/

                        Intent displayintent = new Intent(this, Results_Display.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("imageUri", selectedImage);
                        bundle.putDouble("calc_area", disp_area);
                        displayintent.putExtras(bundle);
                        startActivity(displayintent);

                    }
                    catch(FileNotFoundException nf){
                        nf.printStackTrace();
                        }

                }

    }





    }
