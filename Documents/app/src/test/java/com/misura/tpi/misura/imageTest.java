package com.misura.tpi.misura;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
/**
 * Created by Miguel on 13/04/2017.
 */

public class ImageTest {

    public static void printPixelARGB(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
    }

    public static void marchThroughImage(BufferedImage image) {

        int w = image.getWidth();
        int h = image.getHeight();
        System.out.println("width, height: " + w + ", " + h);

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                System.out.println("x,y: " + j + ", " + i);
                int pixel = image.getRGB(j, i);
                printPixelARGB(pixel);
                System.out.println("");
            }
        }
    }


    public static void main(String[] args) {
        try {
            // get the BufferedImage, using the ImageIO class
            BufferedImage image =  ImageIO.read(new File("foto.jpg"));
            marchThroughImage(image);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}