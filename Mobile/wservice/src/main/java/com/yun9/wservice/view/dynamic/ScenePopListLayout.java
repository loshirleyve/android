package com.yun9.wservice.view.dynamic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/6/24.
 */
public class ScenePopListLayout extends JupiterRelativeLayout {

    private ListView hotTopicLV;

    private ListView sceneLV;

    public ScenePopListLayout(Context context) {
        super(context);
    }

    public ScenePopListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScenePopListLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.popup_scene_list;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        hotTopicLV = (ListView) findViewById(R.id.hot_top_lv);
        sceneLV = (ListView) findViewById(R.id.scene_lv);
    }

    public ListView getHotTopicLV() {
        return hotTopicLV;
    }

    public ListView getSceneLV() {
        return sceneLV;
    }
}
