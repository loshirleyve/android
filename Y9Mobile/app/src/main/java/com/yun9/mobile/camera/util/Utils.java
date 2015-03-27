package com.yun9.mobile.camera.util;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;

public class Utils
{
    private Context context;

    public Utils(Context context)
    {
        this.context = context;
    }

    /**
     * 获取本地全部图片
     * @return 图片集
     */
    public ArrayList<String> getImageUrl()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Uri uri = intent.getData();
        ArrayList<String> list = new ArrayList<String>();
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);//managedQuery(uri, proj, null, null, null);
        while (cursor.moveToNext())
        {
            String path = cursor.getString(0);
            list.add(new File(path).getAbsolutePath());
        }
        ArrayList<String> list1 = new ArrayList<String>();
        for (int i = list.size()-1;i >= 0;i--)
        {
            list1.add(list.get(i));
        }

        return list1;
    }


}
