package com.yun9.wservice.view.doc;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.yun9.jupiter.util.AssertValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 15/6/13.
 */
public class LocalImageLoadAsyncTask extends AsyncTask<Void, Integer, List<LocalImageBean>> {

    private ContentResolver mCr;

    private Map<String, LocalImageBean> albums = new HashMap<>();

    private OnImageLoadCallback onImageLoadCallback;

    public LocalImageLoadAsyncTask(ContentResolver cr, OnImageLoadCallback callback) {
        this.mCr = cr;
        this.onImageLoadCallback = callback;
    }

    @Override
    protected void onPostExecute(List<LocalImageBean> localImageBeans) {
        super.onPostExecute(localImageBeans);
        if (AssertValue.isNotNull(onImageLoadCallback)) {
            onImageLoadCallback.onPostExecute(localImageBeans);
        }

    }

    public interface OnImageLoadCallback {
        public void onPostExecute(List<LocalImageBean> localImageBeans);
    }

    @Override
    protected List<LocalImageBean> doInBackground(Void... params) {

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
                String dateAdded = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                String thumbnailsPath = thumbnails.get(_id);
                if (!AssertValue.isNotNullAndNotEmpty(thumbnailsPath)) {
                    thumbnailsPath = path;
                }


                if (albums.containsKey(album) && AssertValue.isNotNull(albums.get(album))) {
                    LocalImageBean albumBean = albums.get(album);
                    LocalImageBean localImageBean = new LocalImageBean();

                    localImageBean.setId(_id);
                    localImageBean.setName(album);
                    localImageBean.setDateAdded(dateAdded);
                    localImageBean.setParentid(albumBean.getId());
                    localImageBean.setAbsolutePath(path);
                    localImageBean.setFilePath("file://" + path);
                    localImageBean.setThumbnailPath(thumbnailsPath);


                    albumBean.putChild(localImageBean);

                } else {
                    LocalImageBean albumBean = new LocalImageBean();
                    albumBean.setId(_id);
                    albumBean.setName(album);
                    albumBean.setDateAdded(dateAdded);
                    albumBean.setFilePath("file://" + path);
                    albumBean.setAbsolutePath(path);
                    albumBean.setThumbnailPath(thumbnailsPath);
                    albums.put(album, albumBean);

                    LocalImageBean localImageBean = new LocalImageBean();
                    localImageBean.setId(_id);
                    localImageBean.setName(album);
                    localImageBean.setDateAdded(dateAdded);
                    localImageBean.setParentid(albumBean.getId());
                    localImageBean.setAbsolutePath(path);
                    localImageBean.setFilePath("file://" + path);
                    localImageBean.setThumbnailPath(thumbnailsPath);
                    albumBean.putChild(localImageBean);
                }

            } while (cursor.moveToNext());
        }

        List<LocalImageBean> localImageBeans = new ArrayList<>();

        for (Map.Entry<String, LocalImageBean> entity : albums.entrySet()) {
            localImageBeans.add(entity.getValue());
        }

        return localImageBeans;
    }

}
