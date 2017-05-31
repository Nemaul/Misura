package com.misura.tpi.misura;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.net.Uri;

import java.io.*;
import android.graphics.*;

public class Misura_main extends AppCompatActivity {


    public static final double ELEMENT_AREA_DM2 = 1.25663706144;

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

    public int leather(double r, double g, double b){
        if (r < 100 && g > 100 && b <100) //pared
            return 1;
        else if ( b >100) //iman
            return 0;
        return -1; //otros colores
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

                        int total_pix  = imageWidth*imageHeigth;


                        int valid_pixels = 0 ;
                        int magnet_pixels = -1;

                        long gr = 0, gg = 0, gb = 0;
                        for (int x = 0; x < imageWidth; x++) {
                            for (int y = 0; y < imageHeigth; y++) {
                                int index = y * imageWidth + x;
                                long r = (pix[index] >> 16) & 0xff;
                                long g = (pix[index] >> 8) & 0xff;
                                long b = pix[index] & 0xff;
                                gr += r;
                                gg += b;
                                gb += g;
                            }
                        }

                        System.out.println("gr:" + gr);
                        System.out.println("gr mu: " + gr/total_pix);

                        System.out.println("gg:" + gg);
                        System.out.println("gg mu: " + gg/total_pix);

                        System.out.println("gb:" + gb);
                        System.out.println("gb mu: " + gb/total_pix);

                        for (int x = 0 ; x< imageWidth; x++){
                            for(int y = 0; y< imageHeigth; y++){

                                int index  = y*imageWidth+x;
                                double r = ((pix[index] >> 16) & 0xff);
                                double g = ((pix[index] >> 8) & 0xff);
                                double b = (pix[index] & 0xff);

                                //standard deviation
                                double Sr  =  Math.sqrt(1/(total_pix-1) *  Math.pow((gr - (gr/total_pix)),2));
                                double Sg  =  Math.sqrt(1/(total_pix-1) *   Math.pow((gg - (gg/total_pix)),2));
                                double Sb  =  Math.sqrt(1/(total_pix-1) *  Math.pow((gb - (gb/total_pix)),2));

                                r = (r - (gr/total_pix))/Sr;
                                g = (r - (gg/total_pix))/Sg;
                                b = (r - (gb/total_pix))/Sb;


                                /*
                                System.out.println(r);
                                System.out.println(g);
                                System.out.println(b);
                                */

                                int desition = leather(r, g, b) ;

                                if (desition == -1){
                                    valid_pixels++;
                                }else{
                                    if(desition == 0)
                                    magnet_pixels++;
                                }
                            }
                        }

                        System.out.println("valid pixels:" + valid_pixels);
                        System.out.println("magnet pixels"  + magnet_pixels);
                        double disp_area = three_rule(valid_pixels,magnet_pixels);
                        System.out.println("Area: " + disp_area);
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
