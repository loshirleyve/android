/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.yun9.jupiter.sample;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yun9.jupiter.sample.photoview.AUILSampleActivity;
import com.yun9.jupiter.sample.photoview.LauncherActivity;
import com.yun9.jupiter.sample.photoview.RotationSampleActivity;
import com.yun9.jupiter.sample.photoview.SimpleSampleActivity;
import com.yun9.jupiter.sample.photoview.ViewPagerActivity;
import com.yun9.jupiter.sample.ptr.PtrWithListActivity;
import com.yun9.jupiter.sample.viewpagerindicator.ListSamples;

public class MainActivity extends ListActivity {

    public static final String[] options = {"PhotoView","ICO","PTR","ViewPagerIndicator"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Class c;

        switch (position) {
            default:
            case 0:
                c = LauncherActivity.class;
                break;
            case 1:
                c = com.yun9.jupiter.sample.widget.MainActivity.class;
                break;
            case 2:
                c = PtrWithListActivity.class;
                break;
            case 3:
                c = ListSamples.class;
                break;
        }

        startActivity(new Intent(this, c));
    }

}
