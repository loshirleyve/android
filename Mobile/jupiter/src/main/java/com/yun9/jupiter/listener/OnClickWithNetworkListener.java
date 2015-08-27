package com.yun9.jupiter.listener;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Toast;
import com.yun9.jupiter.R;
import com.yun9.jupiter.util.PublicHelp;

/**
 * Created by huangbinglong on 8/27/15.
 */
public class OnClickWithNetworkListener implements OnClickListener,AdapterView.OnItemClickListener{

    private OnClickListener onClickListener;

    private AdapterView.OnItemClickListener onItemClickListener;

    public OnClickWithNetworkListener() {
    }

    public OnClickWithNetworkListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public OnClickWithNetworkListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public final void onClick(View v) {
        if (!PublicHelp.isOpenNetwork(v.getContext())) {
            Toast.makeText(v.getContext(), R.string.jupiter_network_error,Toast.LENGTH_SHORT)
                    .show();
        } else if (onClickListener != null){
            onClickListener.onClick(v);
        } else {
            onClickWithNetwork(v);
        }
    }

    @Override
    public final void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!PublicHelp.isOpenNetwork(view.getContext())) {
            Toast.makeText(view.getContext(), R.string.jupiter_network_error,Toast.LENGTH_SHORT)
                    .show();
        } else if (onItemClickListener != null){
            onItemClickListener.onItemClick(parent, view, position, id);
        } else {
            ItemClickWithNetwork(parent,view,position,id);
        }
    }

    public void onClickWithNetwork(View v) {
        // 按需重写
    }

    public void ItemClickWithNetwork(AdapterView<?> parent, View view, int position, long id) {
        // 按需重写
    }
}
