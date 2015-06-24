package com.yun9.wservice.view.doc;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.FileUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 15/6/13.
 */
public class LocalImageLoadAsyncTask extends AsyncTask<Void, Integer, List<FileBean>> {

    private ContentResolver mCr;

    private List<FileBean> albums = new ArrayList<>();

    private OnImageLoadCallback onImageLoadCallback;

    public LocalImageLoadAsyncTask(ContentResolver cr, OnImageLoadCallback callback) {
        this.mCr = cr;
        this.onImageLoadCallback = callback;
    }

    @Override
    protected void onPostExecute(List<FileBean> fileBeans) {
        super.onPostExecute(fileBeans);
        if (AssertValue.isNotNull(onImageLoadCallback)) {
            onImageLoadCallback.onPostExecute(fileBeans);
        }

    }

    public interface OnImageLoadCallback {
        public void onPostExecute(List<FileBean> fileBeans);
    }

    @Override
    protected List<FileBean> doInBackground(Void... params) {

        Map<Integer, String> thumbnails = new HashMap<>();

        String[] projection = {MediaStore.Images.Thumbnails._ID, MediaStore.Images.Thumbnails.IMAGE_ID, MediaStore.Images.Thumbnails.DATA};
        Cursor curThumbnails = mCr.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null, null);

        if (curThumbnails != null && curThumbnails.moveToFirst()) {
            int image_id;
            String image_path;
            int image_idColumn = curThumbnails.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID);
            int dataColumn = curThumbnails.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
            do {
                image_id = curThumbnails.getInt(image_idColumn);
                image_path = curThumbnails.getString(dataColumn);

                thumbnails.put(image_id, image_path);
            } while (curThumbnails.moveToNext());
        }

        //获取原图
        Cursor cursor = mCr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, "date_modified DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int index = 0;
                int _id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String tempSize = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                long size = 0;
                if (AssertValue.isNotNullAndNotEmpty(tempSize)) {
                    size = Long.parseLong(tempSize);
                }

                String dateAdded = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                String thumbnailsPath = thumbnails.get(_id);
                if (!AssertValue.isNotNullAndNotEmpty(thumbnailsPath)) {
                    thumbnailsPath = path;
                }
                albums.add(new FileBean(_id + "", null, null, name, album, dateAdded, path, thumbnailsPath, FileBean.FILE_TYPE_IMAGE, size));

            } while (cursor.moveToNext());
        }

        return albums;
    }

}
