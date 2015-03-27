package com.yun9.mobile.camera.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import com.yun9.mobile.camera.adapter.AlbumPhoto4MsgAdapter;
import com.yun9.mobile.camera.domain.DmLocalPhoto;
import com.yun9.mobile.camera.domain.DmNetPhoto;
import com.yun9.mobile.camera.impl.ImplAlbumEntrance;
import com.yun9.mobile.camera.interfaces.AlbumEntrance;
import com.yun9.mobile.camera.interfaces.AlbumEntrance.AlbumCallBack;
import com.yun9.mobile.framework.util.AssertValue;
import java.util.ArrayList;
import java.util.List;

/**
 * 显示已选择的照片
 */
public class AlbumPhoto4MsgFragment extends Fragment 
{
    private GridView gridView;
    private AlbumPhoto4MsgAdapter mAdapter;
    private List<DmLocalPhoto> localPhotos;
    private List<DmNetPhoto> netPhotos;
    private boolean isOrigin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        gridView = new GridView(getActivity());
        gridView.setNumColumns(3);
        init();
        return gridView;
    }
    

    private void init(){
    	localPhotos = new ArrayList<DmLocalPhoto>();
		netPhotos = new ArrayList<DmNetPhoto>();
		mAdapter = new AlbumPhoto4MsgAdapter(getActivity(), localPhotos, netPhotos);
        gridView.setAdapter(mAdapter);
    }

    // 去相册选择图片
    public void go2ChosePhoto(){
    	
    	AlbumEntrance albumEntrance = new ImplAlbumEntrance(getActivity());
    	
    	albumEntrance.go2SelectAlbumPhoto(localPhotos, netPhotos, new AlbumCallBack() {
			@Override
			public void photoCallBack(List<DmLocalPhoto> listChosenDmLocalPhoto, List<DmNetPhoto> listChosenDmNetPhoto, boolean isOrigin) {
				AlbumPhoto4MsgFragment.this.localPhotos.clear();
				AlbumPhoto4MsgFragment.this.netPhotos.clear();
				AlbumPhoto4MsgFragment.this.isOrigin = isOrigin;
				// 没有图片
				if((!AssertValue.isNotNullAndNotEmpty(listChosenDmLocalPhoto)) && (!AssertValue.isNotNullAndNotEmpty(listChosenDmNetPhoto))){
					mAdapter.notifyDataSetChanged();
					return ;
				}
				// 加入选择的本地图片
				if(AssertValue.isNotNullAndNotEmpty(listChosenDmLocalPhoto)){
					addLocalPhotos(listChosenDmLocalPhoto);
				}
				
				// 加入选择的网络图片
				if(AssertValue.isNotNullAndNotEmpty(listChosenDmNetPhoto)){
					addNetPhotos(listChosenDmNetPhoto);
				}
				
				AlbumPhoto4MsgFragment.this.mAdapter.notifyDataSetChanged();
			}
		});
    
    }

    private void addLocalPhotos(List<DmLocalPhoto> photos){
    	if(AssertValue.isNotNullAndNotEmpty(photos)){
			for(DmLocalPhoto photo: photos){
				if(!localPhotos.contains(photo)){
					localPhotos.add(photo);
				}
			}
		}
    }
    
    private void addNetPhotos(List<DmNetPhoto> photos){
    	if(AssertValue.isNotNullAndNotEmpty(photos)){
			for(DmNetPhoto photo: photos){
				if(!netPhotos.contains(photo)){
					netPhotos.add(photo);
				}
			}
		}
    }
    

	public List<DmLocalPhoto> getLocalPhotos() {
		return localPhotos;
	}


	public List<DmNetPhoto> getNetPhotos() {
		return netPhotos;
	}


	public boolean isOrigin() {
		return isOrigin;
	}
}
