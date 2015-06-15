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
public class ImageLoadAsyncTask extends AsyncTask<String, Integer, List<LocalImageBean>> {

    private ContentResolver mCr;

    private Map<String, LocalImageBean> albums = new HashMap<>();

    private OnImageLoadCallback onImageLoadCallback;

    public ImageLoadAsyncTask(ContentResolver cr, OnImageLoadCallback callback) {
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
    protected List<LocalImageBean> doInBackground(String... params) {
        //获取原图
        Cursor cursor = mCr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, "date_modified DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int index = 0;
                int _id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String dateAdded = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));

                if (albums.containsKey(album) && AssertValue.isNotNull(albums.get(album))) {
                    LocalImageBean albumBean = albums.get(album);
                    LocalImageBean localImageBean = new LocalImageBean();

                    localImageBean.setId(_id);
                    localImageBean.setName(album);
                    localImageBean.setDateAdded(dateAdded);
                    localImageBean.setParentid(albumBean.getId());
                    localImageBean.setAbsolutePath(path);
                    localImageBean.setFilePath("file://" + path);

                    albumBean.putChild(localImageBean);

                } else {
                    LocalImageBean albumBean = new LocalImageBean();
                    albumBean.setId(_id);
                    albumBean.setName(album);
                    albumBean.setDateAdded(dateAdded);
                    albumBean.setFilePath("file://" + path);
                    albumBean.setAbsolutePath(path);
                    albums.put(album, albumBean);

                    LocalImageBean localImageBean = new LocalImageBean();
                    localImageBean.setId(_id);
                    localImageBean.setName(album);
                    localImageBean.setDateAdded(dateAdded);
                    localImageBean.setParentid(albumBean.getId());
                    localImageBean.setAbsolutePath(path);
                    localImageBean.setFilePath("file://" + path);
                    albumBean.putChild(localImageBean);
                }

            } while (cursor.moveToNext());
        }

        List<LocalImageBean> localImageBeans = new ArrayList<>();

        for(Map.Entry<String,LocalImageBean> entity:albums.entrySet()){
            localImageBeans.add(entity.getValue());
        }

        return localImageBeans;
    }
}
