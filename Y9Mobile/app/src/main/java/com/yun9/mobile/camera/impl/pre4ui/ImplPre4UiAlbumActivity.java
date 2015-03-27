package com.yun9.mobile.camera.impl.pre4ui;

import java.util.ArrayList;
import java.util.List;

import com.yun9.mobile.R;
import com.yun9.mobile.camera.activity.AlbumActivity;
import com.yun9.mobile.camera.adapter.PhotoFolderAdapter;
import com.yun9.mobile.camera.dlg.PopAlbumOption;
import com.yun9.mobile.camera.domain.DmLocalAlbum;
import com.yun9.mobile.camera.domain.DmLocalPhoto;
import com.yun9.mobile.camera.domain.DmNetPhoto;
import com.yun9.mobile.camera.enums.ModeAlbum;
import com.yun9.mobile.camera.interfaces.AlbumEntrance.AlbumCallBack;
import com.yun9.mobile.camera.interfaces.presenter4ui.Pre4uiAlbumActivity;
import com.yun9.mobile.camera.interfaces.ui4Presenter.Ui4PreAlbumActivity;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.UtilDeviceInfo;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;

public class ImplPre4UiAlbumActivity implements Pre4uiAlbumActivity{
	private Activity activity;
	private Ui4PreAlbumActivity ui4Pre;
	
	
	public static AlbumCallBack staticCallBack;
	public static final int FROM_SELECTED = 0x11001;
	public static final int FROM_BROWSE = 0x11002;
	public static final int FROM_UNKONW = 0x12000;
	public static final String FROM_STR = "From";
	private int from = FROM_BROWSE;
	private AlbumCallBack selectPhotoCallBack;
	
    /**
     * 被选择的图片集合
     */
    private List<DmLocalPhoto> chosenLocalPhoto;
    private List<DmNetPhoto> chosenNetPhoto;
	
    /**
     * 选择图片的最大数量， 默认6张
     */
    public static int DEFAULT_MAXPHOTONUM = 6;
    private int maxChoosePicNum = DEFAULT_MAXPHOTONUM;
    public static final String MAXPHOTONUM_STR = "maxPhotoNum";
    /**
     * 还能选择的图片数量
     */
    private int selectablPicNum = maxChoosePicNum;
    
    
    
    public static List<DmLocalPhoto> staticlistChosenDmLocalPhoto;
    public static List<DmNetPhoto> staticListChosenDmNetPhoto;
    
    private PopAlbumOption popAlbumOpiton;
    
    /**
     * 相册模式 默认为选择模式
     */
    private ModeAlbum mModeAlbum = ModeAlbum.CHOICE;
    
	/**
	 * @param activity
	 * @param ui4Pre
	 */
	public ImplPre4UiAlbumActivity(Activity activity, Ui4PreAlbumActivity ui4Pre) {
		super();
		this.activity = activity;
		this.ui4Pre = ui4Pre;
	}
	
	
	@Override
	public void work() {
		legalLaunchCheck();		
		
		init();
	}
	
	private void init(){
		popAlbumOpiton = new PopAlbumOption(activity);
		
		initChosenPhoto();
		
		initModeAlbum();
		
	}
	
	
	
	/**
     * 初始化相册模式
     */
    private void initModeAlbum() {
    	if(ModeAlbum.CHOICE == this.mModeAlbum){
    		ui4Pre.updateBtnCompleteContent(this.maxChoosePicNum, this.selectablPicNum);
    	}else if(ModeAlbum.BROWSE == this.mModeAlbum){
    		ui4Pre.showBottom(View.GONE);
    	}
    	ui4Pre.showTitle4LocalAlbum(View.GONE);
    	ui4Pre.showTitl4NetAlbum(View.VISIBLE);
    }
	
	
    private void initChosenPhoto(){
    	int maxNum = 0;
    	int selectableNum = 0;
       	this.chosenNetPhoto = new ArrayList<DmNetPhoto>();
       	this.chosenLocalPhoto = new ArrayList<DmLocalPhoto>();
    	if(this.from == FROM_SELECTED){
    		maxNum = this.activity.getIntent().getIntExtra(MAXPHOTONUM_STR, DEFAULT_MAXPHOTONUM);
    		selectableNum = maxNum;
        	setMaxSelectPhotoNum(maxNum,selectableNum);
    		if(AssertValue.isNotNullAndNotEmpty(staticListChosenDmNetPhoto)){
    			addChosenNetPhoto(staticListChosenDmNetPhoto);
    		}
    		if(AssertValue.isNotNullAndNotEmpty(staticlistChosenDmLocalPhoto)){
    			addChosenLocalPhoto(staticlistChosenDmLocalPhoto);
    		}
    		
    	}else if(from == FROM_BROWSE){
    		ui4Pre.showBottom(View.GONE);
    		maxNum = 100000;
    		selectableNum = maxNum;
        	setMaxSelectPhotoNum(maxNum,selectableNum);
    	}
    }
    
    private void setMaxSelectPhotoNum(int maxNum, int selectableNum){
    	maxChoosePicNum = maxNum;
    	selectablPicNum = selectableNum;
    }
	

 
    

    
    /**
	 * 检查是否合法启动
	 */
	public void legalLaunchCheck() {
		this.from = activity.getIntent().getIntExtra(FROM_STR, FROM_UNKONW);
        if(FROM_UNKONW == from){
        	this.ui4Pre.showToast("非法启动相册");
        	activity.finish();
        	return ;
        }else if(from == FROM_SELECTED){
        	this.selectPhotoCallBack = staticCallBack;
        }		
	}


	private void addChosenNetPhoto(List<DmNetPhoto> photos) {
		for(DmNetPhoto photo : photos){
			addChosenNetPhoto(photo);
		}
	}
	
	private void addChosenLocalPhoto(List<DmLocalPhoto> photos) {
		for(DmLocalPhoto photo : photos){
			addChosenLocalPhoto(photo);
		}
	}

	
	public void addChosenNetPhoto(DmNetPhoto photo) {
		if(this.chosenNetPhoto.contains(photo)){
			this.chosenNetPhoto.add(photo);
			reduceSelectablPicNum(1);
		}		
	}
	public void removeChosenNetPhoto(DmNetPhoto photo) {
		if(this.chosenNetPhoto.contains(photo)){
			this.chosenNetPhoto.remove(photo);
			increaseSelectablePicNum(1);
		}		
	}
	public void addChosenLocalPhoto(DmLocalPhoto photo) {
		if(!this.chosenLocalPhoto.contains(photo)){
			this.chosenLocalPhoto.add(photo);
			reduceSelectablPicNum(1);
		}
		
	}
	public void removeChosenLocalPhoto(DmLocalPhoto photo) {
		if(this.chosenLocalPhoto.contains(photo)){
			this.chosenLocalPhoto.remove(photo);
			increaseSelectablePicNum(1);
		}
	}
	
	private void reduceSelectablPicNum(int num) {
		if(selectablPicNum - num <= 0){
			selectablPicNum = 0;
		}else{
			selectablPicNum -= num;
		}
		ui4Pre.updateBtnCompleteContent(maxChoosePicNum, selectablPicNum);
	}

	private void increaseSelectablePicNum(int num) {
		if(selectablPicNum + num >= maxChoosePicNum){
			selectablPicNum = maxChoosePicNum;
		}else{
			selectablPicNum += num;
		}
		ui4Pre.updateBtnCompleteContent(maxChoosePicNum, selectablPicNum);
	}
	
	
	@Override
	public void completeChoiceOnClick() {
		if(this.from == FROM_BROWSE){
			
		}
		else if(this.from == FROM_SELECTED){
			if(this.selectPhotoCallBack != null){
//				this.selectPhotoCallBack.photoCallBack(this.chosenLocalPhoto, this.chosenNetPhoto，);
			}
		}
		activity.finish();
	}

	


	@Override
	public void popAlbumOptionOnClick() {
		
	}

}
