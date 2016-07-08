package com.dc.androidtool.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片工具类
 */
public class ImageFactory {


    /**
         * 根据路径得到图片Bitmap
        *
         * @param imgPath
        * @return
          */
     public Bitmap getBitmap(String imgPath) {
                // Get bitmap through image path
               BitmapFactory.Options newOpts = new BitmapFactory.Options(); //为了避免大图omm
               newOpts.inJustDecodeBounds = false;  //true时，并不会真正返回
                newOpts.inPurgeable = true;
              newOpts.inInputShareable = true;//inPurgeable和inInputShareable：让系统能及时回收内存
              // Do not compress没有压缩
               newOpts.inSampleSize = 1;
                newOpts.inPreferredConfig = Bitmap.Config.RGB_565; //16位，2字节，为了降低内存消耗，节约1半
               return BitmapFactory.decodeFile(imgPath, newOpts);
          }


    /**
         * Store bitmap into specified image path
         *
       * @param bitmap
       * @param outPath
       * @throws FileNotFoundException
       */
        public void storeImage(Bitmap bitmap, String outPath) throws FileNotFoundException {
               FileOutputStream os = new FileOutputStream(outPath);
               bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            ////30 是压缩率，表示压缩70%; 如果不压缩是100，表示压缩率为0
        }


    /**
          * Compress image by pixel, this will modify image width/height.
     * 压缩图片，通过修改图片宽高 （比例压缩）
         * Used to get thumbnail
        *
        * @param imgPath image path
        * @param pixelW target pixel of width
       * @param pixelH target pixel of height
       * @return
     */
      public Bitmap ratio(String imgPath, float pixelW, float pixelH) {

              BitmapFactory.Options newOpts = new BitmapFactory.Options();
              // 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
               newOpts.inJustDecodeBounds = true;
              newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
              // Get bitmap info, but notice that bitmap is null now
                Bitmap bitmap = BitmapFactory.decodeFile(imgPath,newOpts);
                newOpts.inJustDecodeBounds = false;
                int w = newOpts.outWidth;
                int h = newOpts.outHeight;
               // 想要缩放的目标尺寸
                float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
                float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
               // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
               int be = 1;//be=1表示不缩放
              if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
                     be = (int) (newOpts.outWidth / ww);
                  } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
                      be = (int) (newOpts.outHeight / hh);
                  }
              if (be <= 0) be = 1;
                newOpts.inSampleSize = be;//设置缩放比例
               // 开始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false了
             bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
              // 压缩好比例大小后再进行质量压缩
       //        return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
               return bitmap;
           }


    /**
          * Compress by quality,  and generate image to the path specified
         * 质量压缩形成图片到指定位置
          * @param image
        * @param outPath
         * @param maxSize target will be compressed to be smaller than this size.(kb)
          * @throws IOException
         */
        public void compressAndGenImage(Bitmap image, String outPath, int maxSize) throws IOException {
              ByteArrayOutputStream os = new ByteArrayOutputStream();
              // scale
                int options = 100;
               // Store the bitmap into output stream(no compress)
                image.compress(Bitmap.CompressFormat.JPEG, options, os);
               // Compress by loop
               while ( os.toByteArray().length / 1024 > maxSize) {  //单位kb
                       // Clean up os
                      os.reset();
                    // interval 10
                        options -= 10;
                       image.compress(Bitmap.CompressFormat.JPEG, options, os); //压缩
                   }

               // Generate compressed image file
               FileOutputStream fos = new FileOutputStream(outPath);  //输出到指定路径
               fos.write(os.toByteArray());
              fos.flush();
               fos.close();
           }


    /**
          *
         *  质量压缩形成图片到指定位置
        * @param imgPath
          * @param outPath
        * @param maxSize target will be compressed to be smaller than this size.(kb)
          * @param needsDelete  压缩后是否要删除原始的路径
         * @throws IOException
          */
       public void compressAndGenImage(String imgPath, String outPath, int maxSize, boolean needsDelete) throws IOException {
               compressAndGenImage(getBitmap(imgPath), outPath, maxSize);
               // Delete original file
              if (needsDelete) {
                      File file = new File(imgPath);
                       if (file.exists()) {
                              file.delete();
                           }
                  }
           }


    /**
        * 尺寸压缩到指定位置
       *
         *
        * @param outPath
         * @param pixelW target pixel of width
        * @param pixelH target pixel of height
          * @throws FileNotFoundException
         */
        public void ratioAndGenThumb(String imgPath, String outPath, float pixelW, float pixelH) throws FileNotFoundException {
               Bitmap bitmap = ratio(imgPath, pixelW, pixelH);

               storeImage( bitmap, outPath);  //保存压缩后的图片到路径
            }



    /**
         *
         *比例压缩图片后是否要 删除原路径
         *
         * @param outPath
        * @param pixelW target pixel of width
         * @param pixelH target pixel of height
         * @param needsDelete Whether delete original file after compress
          * @throws FileNotFoundException
         */
       public void ratioAndGenThumb(String imgPath, String outPath, float pixelW, float pixelH, boolean needsDelete) throws FileNotFoundException {
              Bitmap bitmap = ratio(imgPath, pixelW, pixelH);
              storeImage( bitmap, outPath);  //保存到输出路径

              // Delete original file
                       if (needsDelete) {
                                File file = new File (imgPath);
                               if (file.exists()) {
                                      file.delete();  //删除原路径
                                }
                         }
          }

/*
* 图片尺寸压缩：
* */
public static void compressPicture(String srcPath, String desPath) {
           FileOutputStream fos = null;
          BitmapFactory.Options op = new BitmapFactory.Options();

          // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
           op.inJustDecodeBounds = true;
           Bitmap bitmap = BitmapFactory.decodeFile(srcPath, op);
           op.inJustDecodeBounds = false;

          // 缩放图片的尺寸
           float w = op.outWidth;
           float h = op.outHeight;
          float hh = 1024f;//
          float ww = 1024f;//
            // 最长宽度或高度1024
          float be = 1.0f;
           if (w > h && w > ww) {
                   be = (float) (w / ww);
              } else if (w < h && h > hh) {
                be = (float) (h / hh);
             }
          if (be <= 0) {
                   be = 1.0f;
             }
        op.inSampleSize = (int) be;// 设置缩放比例,这个数字越大,图片大小越小.
          // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
           bitmap = BitmapFactory.decodeFile(srcPath, op);
           int desWidth = (int) (w / be);
            int desHeight = (int) (h / be);
          bitmap = Bitmap.createScaledBitmap(bitmap, desWidth, desHeight, true);
           try {
                   fos = new FileOutputStream(desPath);
                   if (bitmap != null) {
                         bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                     }
               } catch (FileNotFoundException e) {
                  e.printStackTrace();
               }
     }




}
