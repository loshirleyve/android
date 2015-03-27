package com.yun9.mobile.camera.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.yun9.mobile.camera.constant.ConstantAlbum;
import com.yun9.mobile.camera.domain.DmImageItem;
import com.yun9.mobile.camera.imageInterface.CameraCallBack;

/**
 * 相机
 */
public class CameraActivity extends Activity
{
    private DmImageItem imageItem;
    private CameraCallBack cameraCallBack;
    private static CameraCallBack staticCameraCallBack;
    
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private Uri fileUri;
    public static void newInsatnce(Context context, CameraCallBack cameraCallBack)
    {
    	staticCameraCallBack = cameraCallBack;
        Intent intent = new Intent(context, CameraActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        this.cameraCallBack = staticCameraCallBack;
        
        // create a file to save the image
        this.fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); 
        if(this.fileUri == null){
        	Toast.makeText(this, "存储空间不足", Toast.LENGTH_SHORT).show();
        	finish();
        	return ;
        }
        
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
            	String imageUrl = this.fileUri.getPath();
            	this.imageItem = new DmImageItem();
            	this.imageItem.setImageUrl(imageUrl);
            	upDataCamera(imageUrl, this);
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }

        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Video captured and saved to fileUri specified in the Intent
                Toast.makeText(this, "Video saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the video capture
            } else {
                // Video capture failed, advise user
            }
        }
        
        finish();
    }
    
    
    private static Uri getOutputMediaFileUri(int type){
    	File file = getOutputMediaFile(type);
    	if(file == null){
    		return null;
    	}
        return Uri.fromFile(file);
    }
    
    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED))
        { 
            return null;
        }
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), ConstantAlbum.albumDir);
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
    
    public static void upDataCamera(String fileName, Activity activity)
    {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(fileName));
        intent.setData(uri);
        activity.sendBroadcast(intent);
    }
    
    
    @Override
    public void finish() {
    	if(this.cameraCallBack != null){
    		this.cameraCallBack.ImageUrlCall(this.imageItem);
    	}
    	else
    	{
    		this.cameraCallBack.ImageUrlCall(null);
    	}
    	super.finish();
    }
    
}