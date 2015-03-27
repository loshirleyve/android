package com.yun9.mobile.framework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


/**
 * 
 * 图片处理工具类
 * 
 *
 * */

public class UtilImageCompress {

	
	
	/**
	 * 
	 *  按宽高分辨率获取bitmap图片
	 * 
	 * @param srcPath	文件路径
	 * @param height	输出bitmap 的高度
	 * @param width		输出bitmap 的宽度
	 * @return bitmap 
	 */
	public static Bitmap getBitmap(String srcPath, int height, int width) {  
        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
        newOpts.inJustDecodeBounds = true;  
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空  
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
        float hh = height;//这里设置高度为800  
        float ww = width;//这里设置宽度为480  
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
        int be = 1;//be=1表示不缩放  
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
            be = (int) (newOpts.outWidth / ww);  
        } else if (w < h && h > hh) {//如果高度高的话根据高度固定大小缩放  
            be = (int) (newOpts.outHeight / hh);  
        }  
        if (be <= 0)  
            be = 1;  
        newOpts.inSampleSize = be;//设置缩放比例  
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
//        return compressBitmap(bitmap);//压缩好比例大小后再进行质量压缩  
        return bitmap;
    }  
	

	/**
	 * 压缩图片(默认压成 100KB)
	 * 
	 * @param image  bitmap
	 * @return bitmap 新的bitmap
	 */
	public static Bitmap compressBitmap(Bitmap image) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
        int options = 100;  
        while ( (baos.toByteArray().length / 1024 ) > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
            baos.reset();//重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
            options -= 10;//每次都减少10  
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
        return bitmap;  
    } 
	
	
	/**
	 * @param bmp
	 * @param file
	 */
	public static void compressBitmapToFile(Bitmap bmp,File file,int num){  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        int options = 80;//个人喜欢从80开始,  
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);  
        while (baos.toByteArray().length / 1024 > num) {   
            baos.reset();  
            options -= 10;  
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);  
        }  
        try {  
            FileOutputStream fos = new FileOutputStream(file);  
            fos.write(baos.toByteArray());  
            fos.flush();  
            fos.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    } 
	
	
	/**
	 * 文件压缩
	 * @param srcPath
	 * @param height
	 * @param width
	 * @return
	 */

	public static File compressPicFile(String srcPath, int height, int width, int num){
		
		File oldFile = new File(srcPath);
		String picName = UtilFile.getFileNameNoEx(oldFile.getName());
		String suffix = UtilFile.getExtensionName(oldFile.getName());
		suffix = "." + suffix;
		// 文件名最少要6个字节
		if(picName.length() < 6){
			picName = picName + "-vaild";
		}
		File picFile = null;
		try {
			picFile = File.createTempFile(picName, suffix);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		Bitmap bm = getBitmap(srcPath, height, width);
		compressBitmapToFile(bm, picFile, num);
		return picFile;
		
	}
	
	
	/**
	 * 图片压缩
	 * @param srcPath
	 * @param activity
	 * @return
	 */
	public static File compressPicFile(String srcPath, Activity activity){
		// 默认大小
		int num = 100;	
		return compressPicFile(srcPath, activity, num);
	}
	
	public static File compressPicFile(String srcPath, Activity activity, int num){
		int height = UtilDeviceInfo.getDeviceHeightPixels(activity);
		int width = UtilDeviceInfo.getDeviceWidthPixels(activity);
		return compressPicFile(srcPath, height, width, num);
	}
	
	
	/**
	 * 图片压缩
	 * @param srcPath
	 * @param context
	 * @return
	 */
	public static File compressPicFile(String srcPath, Context context){
		// 默认大小(K)
		int num = 100;	
		return compressPicFile(srcPath, context, num);
	}
	
	public static File compressPicFile(String srcPath, Context context, int num){
		int height = UtilDeviceInfo.getDeviceHeightPixels(context);
		int width = UtilDeviceInfo.getDeviceWidthPixels(context);
		return compressPicFile(srcPath, height, width, num);
	}
	
}
