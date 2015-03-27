package com.yun9.mobile.camera.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.yun9.mobile.MainApplication;
import com.yun9.mobile.R;
import com.yun9.mobile.camera.activity.CameraActivity;
import com.yun9.mobile.camera.activity.ImageBrowseActivity;
import com.yun9.mobile.camera.adapter.NetWorkFragmentAdapter;
import com.yun9.mobile.camera.domain.DmLocalPhoto;
import com.yun9.mobile.camera.domain.DmNetAlbum;
import com.yun9.mobile.camera.domain.DmNetPhoto;
import com.yun9.mobile.camera.domain.DmImageItem;
import com.yun9.mobile.camera.enums.ModeAlbum;
import com.yun9.mobile.camera.imageInterface.CameraCallBack;
import com.yun9.mobile.camera.impl.ImplComparatorDomainNetAlbum;
import com.yun9.mobile.camera.interfaces.A4FragmentAlbum;
import com.yun9.mobile.camera.interfaces.F4ActivityAlbum;
import com.yun9.mobile.camera.presenter.PresenterNetWorkAlbumFragment;
import com.yun9.mobile.camera.uiface.UiFaceNetWorkAlbumFragment;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.dlg.nifty.DlgCommon;
import com.yun9.mobile.framework.dlg.prodlg.ProDlgForce;
import com.yun9.mobile.framework.dlg.sweetAlert.DlgAlertCommon;
import com.yun9.mobile.framework.dlg.sweetAlert.DlgProTip;
import com.yun9.mobile.framework.file.FileFactory;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.model.FileByUserId;
import com.yun9.mobile.framework.model.FileInfo;
import com.yun9.mobile.framework.session.SessionManager;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.ProgressDialogutil;
import com.yun9.mobile.framework.util.UtilImageCompress;
import com.yun9.mobile.msg.model.MyMsgCard;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author lhk
 *
 */
@SuppressLint("ValidFragment")
public class NetWorkAlbumFragment extends BaseFragment implements UiFaceNetWorkAlbumFragment, F4ActivityAlbum{
    protected static final String DEFAULT_PHOTONUM = "10";
	private static final String TAG = NetWorkAlbumFragment.class.getSimpleName();
	private View view;
    private Map<Integer,String> imageMaps;
    
    private NetWorkFragmentAdapter mAdapter;
    private boolean isShowChk = false;
    private ModeAlbum MODEALBUM_DEFAULT = ModeAlbum.BROWSE;
    /**
     * 相册模式，默认为浏览模式
     */
    private ModeAlbum mModeAlbum = MODEALBUM_DEFAULT;
    
	/**
	 * 网络相册
	 */
    private List<DmNetPhoto> netPhotos;
    private ImplComparatorDomainNetAlbum comparator;
    private FileFactory fileFactory;
    /**
     * 逻辑处理
     */
    private PresenterNetWorkAlbumFragment presenter;
    
    
	/**
	 * 下拉刷新数量
	 */
	private int refreshNum = 0;
	
	/**
	 * 加载数量
	 */
	private int loadNum = 0;
    
	private A4FragmentAlbum A4Fragment;
	private GridView gv;
	private DlgProTip dlgProTip;
	private DlgAlertCommon dlgAlert;
	@Override
	public void onAttach(Activity activity) {
		A4Fragment = (A4FragmentAlbum) activity;
		super.onAttach(activity);
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
		view = inflater.inflate(R.layout.fragment_network, null);
		findId();
		init();
		setEvent();
		return view;
    }

    private void setEvent() {
    	 // 上拉和下拉
        gridViewP2refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>()
        {
        	
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView)
            {
            	getLastestPhoto(DEFAULT_PHOTONUM);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView)
            {
            	loadMoreNetPhoto(getLastdownid(), DEFAULT_PHOTONUM);
            }
        });
        
        gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(position == 0){
					go2Camera();
				}else if(ModeAlbum.BROWSE == mModeAlbum){
					go2Browse(position);
				}else if(ModeAlbum.CHOICE == mModeAlbum){
					doChoiceMode(position, view);
				}
			}
		});
        
        gv.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {
				if(position == 0){
					return true;
				}else{
					showDeleteDlg(position);
				}
				return true;
			}
		});
	}

    private void init()
    {
    	dlgAlert = new DlgAlertCommon(getActivity());
    	dlgProTip = new DlgProTip(getActivity());
    	mModeAlbum = A4Fragment.getAlbumMode();
        isShowChk = true;
        
        initNetPhotos();
    }
    
    private void initNetPhotos(){
    	fileFactory = BeanConfig.getInstance().getBeanContext().get(FileFactory.class);
    	
        comparator = new ImplComparatorDomainNetAlbum();
        netPhotos = new ArrayList<DmNetPhoto>();
        // 相机占着第0个位置
        DmNetPhoto camera = new DmNetPhoto();
        camera.isCamera(true);
        addNetPhotos(camera);
        int num = addNetPhotos(A4Fragment.getChosenNetPhotos());
//        A4Fragment.reduceSelectablPicNum(num);
        
        mAdapter = new NetWorkFragmentAdapter(getActivity(), netPhotos, imageMaps, isShowChk);
        gv.setAdapter(mAdapter);
        
        // 拿云端上最新的图片
        getLastestPhoto(DEFAULT_PHOTONUM);
      
    }
    


    
    /**
     * 删除图片对话框
     * @param position
     */
    private void showDeleteDlg(int position){
		final DmNetPhoto demainFile = netPhotos.get(position);
		final FileByUserId fileInfo = demainFile.getFileInfo();
		
    	dlgAlert.setTitle("删除图片");
    	dlgAlert.setContent("" + fileInfo.getName());
    	dlgAlert.setConfirmOnClick(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dlgProTip.show();
				FileFactory fileFactory = BeanConfig.getInstance().getBeanContext().get(FileFactory.class);
				fileFactory.deleteUserFile(fileInfo.getId(), new AsyncHttpResponseCallback() {
					@Override
					public void onSuccess(Response response) {
						if(demainFile.isChecked()){
							A4Fragment.removeChosenNetPhoto(demainFile);
							mAdapter.reduceCheckedNum();
						}
						removeNetPhotos(demainFile);
						dlgProTip.dismiss();
						mAdapter.notifyDataSetChanged();
						showToast("删除成功");
					}
					
					@Override
					public void onFailure(Response response) {
						dlgProTip.dismiss();
						showToast("删除失败");
					}
				});
				dlgAlert.dismiss();
			}
		});
    	dlgAlert.show();
    }
    
    /**
     * 拍照，并将图片上传到云端
     */
    protected void go2Camera() {
    	
    	CameraActivity.newInsatnce(getActivity(), new CameraCallBack()
    	{
          @Override
          public void ImageUrlCall(final DmImageItem imageItem)
          {
              if(imageItem != null)
              {
            	  uploadPhoto(imageItem.getImageUrl());
              }
          }
      });		
	}

    
    /**
     * 图片上传
     */
    private void uploadPhoto(String path){
    	dlgProTip.show();
	  
      FileFactory fileFactory = BeanConfig.getInstance().getBeanContext().get(FileFactory.class);
      File uploadFile = null;
      if(A4Fragment.isOrigin()){
    	  uploadFile = new File(path);
      }else{
    	  uploadFile = UtilImageCompress.compressPicFile(path, getActivity());
      }
      if(uploadFile == null){
    	  showToast("文件压缩失败");
    	  dlgProTip.dismiss();
    	  return ;
      }
      fileFactory.uploadImgFileUserLevel(uploadFile, new AsyncHttpResponseCallback()
      {
          @Override
          public void onSuccess(Response response)
          {
        	  dlgProTip.dismiss();
              showToast("图片上传成功");
              getLastestPhoto(DEFAULT_PHOTONUM);
          }

          @Override
          public void onFailure(Response response)
          {
        	  dlgProTip.dismiss();
              Toast.makeText(getActivity(), "上传图片失败,请稍后再试", Toast.LENGTH_SHORT).show();
          }
      });
    }
    
	/**
	 * 选择图片
	 * @param position
	 */
	protected void doChoiceMode(int position, View view) {
		boolean stat = mAdapter.getCheckedStat(position);
		boolean isWillChecked = !stat;
		DmNetPhoto photo = (DmNetPhoto)mAdapter.getItem(position);
		if(isWillChecked){
			if(A4Fragment.getSelectablePicNum() <= 0){
				showToast("最多可以选择" + A4Fragment.getMaxChoosePicNum() + "张");
    			return ;
			}else{
				A4Fragment.addChosenNetPhoto(photo);
				mAdapter.setChecked(position, true, view);
			}
		}else{
			A4Fragment.removeChosenNetPhoto(photo);
			mAdapter.setChecked(position, false, view);
		}
	}


	/**
	 * 浏览模式下，浏览图片
	 * @param position
	 */
	protected void go2Browse(int position) {
    	
    	ArrayList<String> imageUrl = getImageUrls();
        Intent intent = new Intent(getActivity(),ImageBrowseActivity.class);
        intent.putExtra(ImageBrowseActivity.EXTRA_IMAGE_URLS, imageUrl);
        intent.putExtra(ImageBrowseActivity.EXTRA_IMAGE_INDEX, position - 1);
        intent.putExtra(ImageBrowseActivity.EXTRA_IMAGE_FROM, ImageBrowseActivity.EXTRA_IMAGE_FROM_NETALBUM);
        getActivity().startActivity(intent);
		
	}

	/**
     * 获取云端最新的图片
     */
    private void getLastestPhoto(String num){
    	
        fileFactory.getLatestNetPhoto(num, new AsyncHttpResponseCallback()
        {
            @SuppressWarnings("unchecked")
			@Override
            public void onSuccess(Response response)
            {
            	List<FileByUserId> netWorkList = (List<FileByUserId>) response.getPayload();
                if(AssertValue.isNotNullAndNotEmpty(netWorkList)){
                 	netPhotoListAdd(netPhotos, netWorkList);
                	mAdapter.notifyDataSetChanged();
                	
                }
                refreshUIAfterOnRefreshOK();
                gridViewP2refresh.onRefreshComplete();
            }

            @Override
            public void onFailure(Response response)
            {
            	gridViewP2refresh.onRefreshComplete();
                Toast.makeText(getActivity(), "获取图片失败", Toast.LENGTH_LONG).show();
            }
        });
    }
    
    
    /**
     * 载入更多图片
     * @param lastdownid
     * @param num
     */
    private void loadMoreNetPhoto(String lastdownid, String num){
    	fileFactory.loadMoreNetPhoto(lastdownid, num, new AsyncHttpResponseCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Response response) {
				List<FileByUserId> netWorkList = (List<FileByUserId>) response.getPayload();
				if (AssertValue.isNotNullAndNotEmpty(netWorkList)) {
					netPhotoListAdd(netPhotos, netWorkList);
					mAdapter.notifyDataSetChanged();
				
				}
				afterLoadOK();
				gridViewP2refresh.onRefreshComplete();			
			}
			
			@Override
			public void onFailure(Response response) {
				gridViewP2refresh.onRefreshComplete();
                Toast.makeText(getActivity(), "加载图片失败", Toast.LENGTH_LONG).show();				
			}
		});
    }
    
  
    private void findId()
    {
        gridViewP2refresh = (PullToRefreshGridView) view.findViewById(R.id.pull_netWork);
        gridViewP2refresh.setMode(PullToRefreshBase.Mode.BOTH);
        gv = gridViewP2refresh.getRefreshableView();
    }

    /**
     * 提供给上层调用的接口，获取选择的图片
     * @return
     */
    public Map<Integer, String> getImageMaps()
    {
        return imageMaps;
    }

    
    /**
     * 获取list里最老的图片id
     * @return
     */
    private String getLastdownid(){
    	String id = "0";
    	if(AssertValue.isNotNullAndNotEmpty(netPhotos) && netPhotos.size() > 1){
    		int size = netPhotos.size();
    		id =  netPhotos.get(size - 1).getFileInfo().getId();
    	}
    	return id;
    }
    
	/**
	 * 将获取到的图片加入到已经存在的list
	 * @param oldList
	 * @param addList
	 */
	@SuppressWarnings("unchecked")
	private void netPhotoListAdd(List<DmNetPhoto> oldList, List<FileByUserId> addList){
		refreshNum = 0;
		loadNum = 0;
		if (AssertValue.isNotNullAndNotEmpty(addList)&& AssertValue.isNotNull(oldList)){
			for (FileByUserId fileInfo : addList) {
				DmNetPhoto domain = new DmNetPhoto();
				domain.setFileInfo(fileInfo);
				if(addNetPhotos(domain)){
					refreshNum++;
					loadNum++;
				}
			}
			
		 	Collections.sort(oldList, comparator);
		}
	}
	
	
	/**
	 * 下拉刷新成功后更新UI
	 */
	protected void refreshUIAfterOnRefreshOK() {
		if(refreshNum == 0){
			Toast.makeText(getActivity(), "没有新的了待会再试试吧。",Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(getActivity(), "刷新了" + refreshNum + "张图片。", Toast.LENGTH_SHORT).show();;
		}
		
		refreshNum = 0;
	}
	
	/**
	 * 加载成功后更新UI
	 */
	protected void afterLoadOK() {
		if(loadNum == 0){
			Toast.makeText(getActivity(), "已经到底了。", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(getActivity(), "加载了" + loadNum + "张图片", Toast.LENGTH_SHORT).show();
		}
		loadNum = 0;
	}

	public ArrayList<String> getImageUrls() {
		ArrayList<String> imageUrls = new ArrayList<String>();
		if(AssertValue.isNotNullAndNotEmpty(netPhotos)){
			for(int i = 1; i < netPhotos.size(); i++){
				imageUrls.add(netPhotos.get(i).getFileInfo().getId());
			}
		}
		return imageUrls;
	}

	/* （非 Javadoc）
	 * 模式变化
	 * @see com.yun9.mobile.camera.interfaces.NotifyModeChanged#notifyModeChanged(com.yun9.mobile.camera.enums.ModeAlbum)
	 */
	@Override
	public void notifyModeChanged(ModeAlbum mode) {
		mModeAlbum = mode;
		if(mModeAlbum == ModeAlbum.BROWSE){
			isShowChk = false;
			mAdapter.setShowChk(isShowChk);
		}else if(mModeAlbum == ModeAlbum.CHOICE){
			isShowChk = true;
			mAdapter.setShowChk(isShowChk);
		}
		mAdapter.notifyDataSetChanged();
	}

    private boolean addNetPhotos(DmNetPhoto photo){
    	if(!netPhotos.contains(photo)){
    		netPhotos.add(photo);
    		return true;
    	}
    	return false;
    }
    
    @SuppressWarnings("unchecked")
	private int addNetPhotos(List<DmNetPhoto> photos){
    	int num = 0;
    	if(!AssertValue.isNotNullAndNotEmpty(photos)){
    		return num;
    	}
    	for(DmNetPhoto photo : photos){
    		if(!netPhotos.contains(photo)){
        		netPhotos.add(photo);
        		num++;
        	}
    	}
    	Collections.sort(netPhotos, comparator);
		return num;
    }
    
    public boolean removeNetPhotos(DmNetPhoto netPhoto) {
		if(netPhotos.contains(netPhoto)){
			netPhotos.remove(netPhoto);
			return true;
		}
		return false;
	}
	
	public boolean removeNetPhotos(List<DmNetPhoto> photos) {
		if(!AssertValue.isNotNullAndNotEmpty(photos)){
			return false;
		}
		for(DmNetPhoto photo : photos){
			removeNetPhotos(photo);
		}
		this.mAdapter.notifyDataSetChanged();
		return true;
	}
	
	
	private void showToast(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
	}

	public void resetSelectablePhoto() {
		for(DmNetPhoto  photo : this.netPhotos){
			photo.setChecked(false);
		}
		
		this.mAdapter.notifyDataSetChanged();
	}

	
}
