package com.yun9.wservice.view.camera;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Leon on 15/6/26.
 */
public class CameraActivity extends Activity {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;

    private Uri fileUri;

    private String photoDir = "Yun9";

    private CameraCommand command;

    private FileBean fileBean;

    private static Logger logger = Logger.getLogger(CameraActivity.class);

    public static void start(Activity activity, CameraCommand command) {
        Intent intent = new Intent(activity, CameraActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        intent.putExtras(bundle);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (CameraCommand) getIntent().getSerializableExtra(CameraCommand.PARAM_COMMAND);

        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getPhotoPath())) {
            photoDir = command.getPhotoPath();
        }

        // create a file to save the image
        this.fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        if (this.fileUri == null) {
            Toast.makeText(this, "存储空间不足", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }


    private Uri getOutputMediaFileUri(int type) {
        File file = getOutputMediaFile(type);
        if (file == null) {
            return null;
        }
        return Uri.fromFile(file);
    }

    /**
     * Create a File for saving an image or video
     */
    private File getOutputMediaFile(int type) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), photoDir);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                logger.d("failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    public static void upDataCamera(String fileName, Activity activity) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(fileName));
        intent.setData(uri);
        activity.sendBroadcast(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String imageUrl = this.fileUri.getPath();
                upDataCamera(imageUrl, this);

                FileBean fileBean = new FileBean(new File(imageUrl));
                Intent intent = new Intent();
                intent.putExtra(CameraCommand.PARAM_IMAGE, fileBean);
                setResult(CameraCommand.RESULT_CODE_OK, intent);
            } else if (resultCode == RESULT_CANCELED) {
                setResult(CameraCommand.RESULT_CODE_CANCEL);
            } else {
                setResult(CameraCommand.RESULT_CODE_ERROR);
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
}
