package com.yun9.mobile.camera.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.mobile.R;
import com.yun9.mobile.camera.adapter.MyFragmentPagerAdapter;
import com.yun9.mobile.camera.adapter.PhotoFolderAdapter;
import com.yun9.mobile.camera.dlg.PopAlbumOption;
import com.yun9.mobile.camera.domain.DmLocalAlbum;
import com.yun9.mobile.camera.domain.DmLocalPhoto;
import com.yun9.mobile.camera.domain.DmNetPhoto;
import com.yun9.mobile.camera.enums.ModeAlbum;
import com.yun9.mobile.camera.enums.ModeAlbum.ModelAlbum2Level;
import com.yun9.mobile.camera.fragment.LocalDocumentFragment;
import com.yun9.mobile.camera.fragment.LocalAlbumFragment;
import com.yun9.mobile.camera.fragment.NetDocumentFragment;
import com.yun9.mobile.camera.fragment.NetWorkAlbumFragment;
import com.yun9.mobile.camera.interfaces.A4FragmentAlbum;
import com.yun9.mobile.camera.interfaces.A4FragmentDocument;
import com.yun9.mobile.camera.interfaces.A4FragmentLocalDocument;
import com.yun9.mobile.camera.interfaces.A4FragmentNetDocument;
import com.yun9.mobile.camera.interfaces.Album4PopAlbumOption;
import com.yun9.mobile.camera.interfaces.AlbumEntrance.AlbumCallBack;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.dlg.sweetAlert.DlgProTip;
import com.yun9.mobile.framework.factory.command.FactoryCommandNetworkServiceYiDianTong;
import com.yun9.mobile.framework.file.FileFactory;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.interfaces.CallBackYiDianTong;
import com.yun9.mobile.framework.interfaces.command.CommandNetworkService;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.UtilDeviceInfo;
import com.yun9.mobile.framework.util.UtilImageCompress;
import com.yun9.mobile.framework.util.UtilPopWin;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 显现所有相册相片，选择相片
 */
public class AlbumActivity extends FragmentActivity implements A4FragmentAlbum, A4FragmentLocalDocument, A4FragmentNetDocument,
		Album4PopAlbumOption {

	private static final int IMAGES_FROM_DOCUMENT = 1;
	private static final int IMAGES_FROM_NETWORK = 2;
	private static final int IMAGES_FROM_LOCAL = 0;
	
	private ImageButton btnBack;
	private List<Fragment> fragmentList;
	private ViewPager vp_getPic;
	
	private Button btn_netWork;
	private Button btn_local;
	private View view_netWork;
	private View view_local;
	
	private Button btn_document;
	private View view_document;
	
	private NetWorkAlbumFragment netWorkFragment;
	private LocalAlbumFragment localFragment;
	private LocalDocumentFragment documentFragment;
	private NetDocumentFragment netDocumentFragment;
	
	private Button btnComplete;
	private PopupWindow albumsPopW;
	private TextView tvNetAlbumTitle;
	private LinearLayout llAlbumTitleMiddle;
	private ImageView icLocalAlbumArrow;
	
	private TextView tvLocalAlbumTitle;
	private View titleDocument;
	
	private ImageButton ibAlbumOption;
	private CheckBox cbOriginalPhoto;
	private TextView tvOrigin;
	/**
	 * 选择图片的最大数量， 默认6张
	 */
	public static int DEFAULT_MAXPHOTONUM = 6;
	private int maxChoosePicNum = DEFAULT_MAXPHOTONUM;
	/**
	 * 还能选择的图片数量
	 */
	private int selectablPicNum = maxChoosePicNum;

	/**
	 * 相册模式 默认为选择模式
	 */
	private ModeAlbum mModeAlbum = ModeAlbum.CHOICE;
	private ModelAlbum2Level model2Level = ModelAlbum2Level.DEFAULT;
	
	/**
	 * 当前展示的相册
	 */
	private DmLocalAlbum currentAlbum;
	private ListView albumListView;
	private List<DmLocalAlbum> listAlbum;

	private PhotoFolderAdapter albumAdapter;
	/**
	 * 相册 popwindow
	 */
	private View contentView;

	/**
	 * 被选择的图片集合
	 */
	private List<DmLocalPhoto> mLChosenLocalPhoto;
	private List<DmNetPhoto> mLChosenNetPhoto;

	public static List<DmLocalPhoto> staticlistChosenDmLocalPhoto;
	public static List<DmNetPhoto> staticListChosenDmNetPhoto;
	public static AlbumCallBack staticCallBack;
	
	
	public static final int FROM_SELECTED = 0x11001;
	public static final int FROM_BROWSE = 0x11002;
	public static final int FROM_NORMAL = 0x11003;
	public static final int FROM_UNKONW = 0x11000;
	
	
	
	public static final int LEVEL_DEFAULT = 0x12000;
	public static final int LEVEL_CHOICE_ONLY_NETWORK_PIC = 0x12001;
	
	public static final String FROM_STR = "From";
	public static final String FROM2LEVEL_STR = "level";
	private int from = FROM_BROWSE;
	private int level = LEVEL_DEFAULT;

	/**
	 * 最大选择图片数量
	 */
	public static final String MAXPHOTONUM_STR = "maxPhotoNum";
	private AlbumCallBack mSelectPhotoCallBack;

	private PopAlbumOption popAlbumOption;
	private DlgProTip dlgProTip;
	private FileFactory fileFactory;

	
	
	
	
	private int selectedDocuments;
	
	
	
	private final int HANDLER_SHOW_PREGRESS_DLG = 0x001;
	private final int HANDLER_CLOSE_PREGRESS_DLG = 0x002;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			if(HANDLER_SHOW_PREGRESS_DLG == what){
				dlgProTip.show();
			}else if(HANDLER_CLOSE_PREGRESS_DLG == what){
				dlgProTip.dismiss();
			}
		};
		
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 合法启动相册校验
		from = getIntent().getIntExtra(FROM_STR, FROM_UNKONW);
		level = getIntent().getIntExtra(FROM2LEVEL_STR, LEVEL_DEFAULT);
		
		if (FROM_UNKONW == from) {
			showToast("非法启动相册");
			super.finish();
		} else if (from == FROM_SELECTED) {
			if(level == LEVEL_DEFAULT){
				mSelectPhotoCallBack = staticCallBack;
			}else if(level == LEVEL_CHOICE_ONLY_NETWORK_PIC){
				mSelectPhotoCallBack = staticCallBack;
			}
		}
		setContentView(R.layout.activity_album);

		findId();
		setEvent();
		init();
	}

	private void findId() {
		tvOrigin = (TextView) findViewById(R.id.tvOrigin);
		cbOriginalPhoto = (CheckBox) findViewById(R.id.cbOriginalPhoto);
		ibAlbumOption = (ImageButton) findViewById(R.id.ibAlbumOption);
		// llBottom = (LinearLayout) findViewById(R.id.bottom);
		contentView = LayoutInflater.from(this).inflate(
				R.layout.fragment_photofolder, null);
		albumListView = (ListView) contentView.findViewById(R.id.listView);
		icLocalAlbumArrow = (ImageView) findViewById(R.id.icLocalAlbumArrow);
		llAlbumTitleMiddle = (LinearLayout) findViewById(R.id.llAlbumTitleMiddle);
		tvLocalAlbumTitle = (TextView) findViewById(R.id.tvLocalAlbumTitle);
		tvNetAlbumTitle = (TextView) findViewById(R.id.tvNetAlbumTitle);
		btnComplete = (Button) findViewById(R.id.btnComplete);
		vp_getPic = (ViewPager) findViewById(R.id.vp_getPic);
		
		
		btn_netWork = (Button) findViewById(R.id.btn_netWork);
		btn_local = (Button) findViewById(R.id.btn_local);
		view_netWork = findViewById(R.id.view_netWork);
		view_local = findViewById(R.id.view_local);
		
		btn_document = (Button) findViewById(R.id.btn_document);
		view_document = findViewById(R.id.view_document);
		
		btnBack = (ImageButton) findViewById(R.id.btn_get_return);

		titleDocument = findViewById(R.id.titleDocument);
	}

	private void setEvent() {

		// 原图勾选
		this.cbOriginalPhoto.setOnCheckedChangeListener(OnClickoriginalPhoto);

		// 相册选项
		this.ibAlbumOption.setOnClickListener(OnClickAlbumOption);

		// 完成，将选择的图片返回给调用者
		this.btnComplete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		this.llAlbumTitleMiddle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showAlbumPop();
			}
		});


		this.btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlbumActivity.this.finish();
			}
		});

		this.btn_netWork.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				vp_getPic.setCurrentItem(IMAGES_FROM_NETWORK);
			}
		});

		this.btn_local.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				vp_getPic.setCurrentItem(IMAGES_FROM_LOCAL);
			}
		});
		
		this.btn_document.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				vp_getPic.setCurrentItem(IMAGES_FROM_DOCUMENT);
			}
		});
	}

	private OnCheckedChangeListener OnClickoriginalPhoto = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			updateChkOriginal();
		}
	};

	private OnClickListener OnClickAlbumOption = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// popAlbumOption.showAsDropDown(v);
			popAlbumOption.showAtLocation(v, Gravity.TOP | Gravity.RIGHT, 10,
					230);
		}
	};

	private void init() {
		
		// 文档相关初始化
		
		
		
		
		this.popAlbumOption = new PopAlbumOption(this);
		this.dlgProTip = new DlgProTip(this);
		this.fileFactory = BeanConfig.getInstance().getBeanContext()
				.get(FileFactory.class);
		// 初始化传入被选中的图片
		initChosenPhoto();
		
		fragmentList = new ArrayList<Fragment>();
		
		netWorkFragment = new NetWorkAlbumFragment();
		localFragment = new LocalAlbumFragment();
		documentFragment = new LocalDocumentFragment();
		netDocumentFragment = new NetDocumentFragment();
		
		fragmentList.add(localFragment);
		fragmentList.add(documentFragment);
		fragmentList.add(netWorkFragment);
//		fragmentList.add(netDocumentFragment);
		
		vp_getPic.setOffscreenPageLimit(fragmentList.size()-1);
		vp_getPic.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
		vp_getPic.setOnPageChangeListener(new MyPageListner());
		vp_getPic.setCurrentItem(IMAGES_FROM_LOCAL);
		
		// 初始化相册
		initModeAlbum();
		initPopW();
		
		
		
		
		
		
		
		
	}

	private void initChosenPhoto() {
		int maxNum = 0;
		int selectableNum = 0;
		mLChosenNetPhoto = new ArrayList<DmNetPhoto>();
		mLChosenLocalPhoto = new ArrayList<DmLocalPhoto>();
		
		if(level == LEVEL_CHOICE_ONLY_NETWORK_PIC){
			model2Level = ModelAlbum2Level.CHOICE_ONLY_NETWORK_PIC;
		}else{
			model2Level = ModelAlbum2Level.DEFAULT;
		}
		
		if (from == FROM_SELECTED) {
			this.mModeAlbum = ModeAlbum.CHOICE;
			maxNum = getIntent().getIntExtra(MAXPHOTONUM_STR,
					DEFAULT_MAXPHOTONUM);
			selectableNum = maxNum;
			setMaxSelectPhotoNum(maxNum, selectableNum);
			if (AssertValue.isNotNullAndNotEmpty(staticListChosenDmNetPhoto)) {
				addChosenNetPhoto(staticListChosenDmNetPhoto);
			}
			if (AssertValue.isNotNullAndNotEmpty(staticlistChosenDmLocalPhoto)) {
				addChosenLocalPhoto(staticlistChosenDmLocalPhoto);
			}

		} else if (from == FROM_BROWSE) {
			this.mModeAlbum = ModeAlbum.BROWSE;
			maxNum = 6;
			selectableNum = maxNum;
			setMaxSelectPhotoNum(maxNum, selectableNum);
		} else if (from == FROM_NORMAL) {
			// this.mModeAlbum = ModeAlbum;
			maxNum = 6;
			selectableNum = maxNum;
			setMaxSelectPhotoNum(maxNum, selectableNum);
		}
	}

	private void setMaxSelectPhotoNum(int maxNum, int selectableNum) {
		maxChoosePicNum = maxNum;
		selectablPicNum = selectableNum;
	}

	private void initPopW() {
		if (listAlbum != null) {
			return;
		}
		listAlbum = localFragment.getAlbumList();
		albumAdapter = new PhotoFolderAdapter(AlbumActivity.this, listAlbum);
		albumListView.setAdapter(albumAdapter);
		albumListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				currentAlbum = listAlbum.get(position);
				localFragment.setCurrentAlbum(currentAlbum);
				localFragment.notifyAlbumChanged();
				albumsPopW.dismiss();
			}
		});
		albumsPopW = new PopupWindow(contentView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		albumsPopW.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				dissAlbumPop();
				showTitle4LocalAlbum();
			}
		});
		albumsPopW.setBackgroundDrawable(UtilPopWin.getDrawable(AlbumActivity.this));
		albumsPopW.setFocusable(true);
		albumsPopW.setOutsideTouchable(true);

		int height = albumsPopW.getHeight();
		int maxHeight = (int) (UtilDeviceInfo.getDeviceHeightPixels(this) * 0.5);
		if (height >= maxHeight) {
			height = maxHeight;
		}
		albumsPopW.setHeight(maxHeight);
	}


	private void showToast(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 初始化相册模式
	 */
	private void initModeAlbum() {
		setModeAlbumUI(mModeAlbum);
		showIndex(IMAGES_FROM_LOCAL);
	}

	/**
	 * 
	 * 根据viewpager 不同页， 对标题和index的显示
	 * @param index
	 */
	private void showIndex(int index){
		// control show Title
		showIndex4Title(index);
		
		// control show Index
		showIndex4Button(index);
	}
	
	private void showIndex4Button(int index) {
		if(IMAGES_FROM_LOCAL == index){
			isShowBtnIndexLocal(true);
			
			isShowBtnIndexNetAlbum(false);
			
			isShowBtnIndexDocument(false);
			
		}else if(IMAGES_FROM_NETWORK == index){
			
			isShowBtnIndexNetAlbum(true);
			
			isShowBtnIndexLocal(false);
			
			isShowBtnIndexDocument(false);
		}else if(IMAGES_FROM_DOCUMENT == index){
			
			isShowBtnIndexDocument(true);
			
			isShowBtnIndexNetAlbum(false);
			
			isShowBtnIndexLocal(false);
		}
	}
	private void isShowBtnIndexLocal(boolean isShow){
		if(isShow){
			btn_local.setTextColor(getResources().getColor(R.color.blue));
			view_local.setVisibility(View.VISIBLE);
		}else{
			btn_local.setTextColor(getResources().getColor(R.color.black));
			view_local.setVisibility(View.GONE);
		}
	}
	private void isShowBtnIndexNetAlbum(boolean isShow){
		if(isShow){
			btn_netWork.setTextColor(getResources().getColor(R.color.blue));
			view_netWork.setVisibility(View.VISIBLE);
		}else{
			btn_netWork.setTextColor(getResources().getColor(R.color.black));
			view_netWork.setVisibility(View.GONE);
		}
	}
	private void isShowBtnIndexDocument(boolean isShow){
		if(isShow){
			btn_document.setTextColor(getResources().getColor(R.color.blue));
			view_document.setVisibility(View.VISIBLE);
		}else{
			btn_document.setTextColor(getResources().getColor(R.color.black));
			view_document.setVisibility(View.GONE);
		}
	}
	
	
	private void showIndex4Title(int index) {
		if(IMAGES_FROM_LOCAL == index){
			showTitle4LocalAlbum();
			
			closeTitle4NetAlbum();
			closeTitle4Document();
		}else if(IMAGES_FROM_NETWORK == index){
			showTitle4NetAlbum();
			
			closeTitle4LocalAlbum();
			closeTitle4Document();
		}else if(IMAGES_FROM_DOCUMENT == index){
			showTitle4Document();
			
			closeTitle4NetAlbum();
			closeTitle4LocalAlbum();
		}
	}

	private void showTitle4Document() {
		titleDocument.setVisibility(View.VISIBLE);
	}
	private void closeTitle4Document(){
		titleDocument.setVisibility(View.GONE);
	}
	


	/**
	 * 设置每种模式对象的展示
	 * 
	 * @param modeAlbum
	 */
	protected void setModeAlbum2Interface(ModeAlbum modeAlbum) {
		setModeAlbumUI(modeAlbum);
		netWorkFragment.notifyModeChanged(modeAlbum);
		localFragment.notifyModeChanged(modeAlbum);
	}

	private void dissAlbumPop() {
		icLocalAlbumArrow.setImageResource(R.drawable.ic_arrow_down);
		if (albumsPopW != null && albumsPopW.isShowing()) {
			albumsPopW.dismiss();
		}
	}

	private void showAlbumPop() {
		icLocalAlbumArrow.setImageResource(R.drawable.ic_arrow_up);
		if (albumsPopW != null) {
			albumsPopW.showAsDropDown(llAlbumTitleMiddle);
		}
	}

	private void setModeAlbumUI(ModeAlbum modeAlbum) {
		if (ModeAlbum.BROWSE == modeAlbum) {
		} else if (ModeAlbum.CHOICE == modeAlbum) {
			upBtnCompleteContent();
		} else {

		}
	}

	private void upBtnCompleteContent() {
		CharSequence text = getString(R.string.viewpager_indicator,
				maxChoosePicNum - selectablPicNum, maxChoosePicNum);
		btnComplete.setText("完成 : " + text);
	}


	/**
	 * 改变模式
	 * 
	 * @param modeAlbum
	 */
	private void changeModeAlbum(ModeAlbum modeAlbum) {
		this.mModeAlbum = modeAlbum;
		localFragment.notifyModeChanged(this.mModeAlbum);
		netWorkFragment.notifyModeChanged(this.mModeAlbum);
	}

	class MyPageListner implements ViewPager.OnPageChangeListener {

		@Override
		public void onPageScrolled(int i, float v, int i2) {

		}

		@Override
		public void onPageSelected(int i) {
			switch (i) {
			case IMAGES_FROM_NETWORK:
				showIndex(IMAGES_FROM_NETWORK);

				break;
			case IMAGES_FROM_LOCAL:
				showIndex(IMAGES_FROM_LOCAL);
				dissAlbumPop();
				break;
			case IMAGES_FROM_DOCUMENT:
				showIndex(IMAGES_FROM_DOCUMENT);
				break;
			}
		}

		@Override
		public void onPageScrollStateChanged(int i) {

		}
	}

	private void showTitle4LocalAlbum() {
		llAlbumTitleMiddle.setVisibility(View.VISIBLE);
		currentAlbum = localFragment.getCurrentAlbum();
		if (currentAlbum != null) {
			String name = currentAlbum.getName_album();
			tvLocalAlbumTitle.setText(name);
		}
	}

	private void closeTitle4LocalAlbum() {
		llAlbumTitleMiddle.setVisibility(View.GONE);
	}

	public void showTitle4NetAlbum() {
		tvNetAlbumTitle.setVisibility(View.VISIBLE);
	}

	public void closeTitle4NetAlbum() {
		tvNetAlbumTitle.setVisibility(View.GONE);
	}

	public ModeAlbum getmModeAlbum() {
		return mModeAlbum;
	}

	public void setmModeAlbum(ModeAlbum mModeAlbum) {
		this.mModeAlbum = mModeAlbum;
	}

	@Override
	public ModeAlbum getAlbumMode() {
		return mModeAlbum;
	}

	@Override
	public int getMaxChoosePicNum() {
		return maxChoosePicNum;
	}

	@Override
	public int getSelectablePicNum() {
		return selectablPicNum;
	}

	private void reduceSelectablPicNum(int num) {
		if (selectablPicNum - num <= 0) {
			selectablPicNum = 0;
		} else {
			selectablPicNum -= num;
		}

		upBtnCompleteContent();
		updateChkOriginal();
	}

	private void updateChkOriginal() {
		String text = "原图";
		if (this.cbOriginalPhoto.isChecked()) {
			float num = 0.0f;
			if (AssertValue.isNotNullAndNotEmpty(this.mLChosenLocalPhoto)) {
				for (DmLocalPhoto photo : this.mLChosenLocalPhoto) {
					File file = new File(photo.getPath_absolute());
					if (file != null) {
						num += (float) file.length() / (1024 * 1024);
					}
				}

			}
			BigDecimal b = new BigDecimal(num);
			num = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
			text = "原图(" + num + " M)";
		} else {
			text = "原图";
		}
		this.tvOrigin.setText(text);
	}

	private void increaseSelectablePicNum(int num) {
		if (selectablPicNum + num >= maxChoosePicNum) {
			selectablPicNum = maxChoosePicNum;
		} else {
			selectablPicNum += num;
		}
		upBtnCompleteContent();
		updateChkOriginal();
	}

	@Override
	public void finish() {
		if (from == FROM_BROWSE) {

		} else if (from == FROM_SELECTED) {
			if (mSelectPhotoCallBack != null) {
				
				if(model2Level == ModelAlbum2Level.CHOICE_ONLY_NETWORK_PIC){
					
					// 谈对话框，上传文件，
					mSelectPhotoCallBack.photoCallBack(mLChosenLocalPhoto, mLChosenNetPhoto, isOrigin());
					
				}else{
					mSelectPhotoCallBack.photoCallBack(mLChosenLocalPhoto, mLChosenNetPhoto, isOrigin());
				}
			}
		} else {

		}
		super.finish();
	}

	private void addChosenNetPhoto(List<DmNetPhoto> photos) {
		for (DmNetPhoto photo : photos) {
			addChosenNetPhoto(photo);
		}
	}

	private void addChosenLocalPhoto(List<DmLocalPhoto> photos) {
		for (DmLocalPhoto photo : photos) {
			addChosenLocalPhoto(photo);
		}
	}

	@Override
	public void addChosenNetPhoto(DmNetPhoto photo) {
		if (!mLChosenNetPhoto.contains(photo)) {
			mLChosenNetPhoto.add(photo);
			reduceSelectablPicNum(1);
		}
	}

	@Override
	public void removeChosenNetPhoto(DmNetPhoto photo) {
		if (mLChosenNetPhoto.contains(photo)) {
			mLChosenNetPhoto.remove(photo);
			increaseSelectablePicNum(1);
		}
	}

	public void removeAllChosenNetPhoto() {
		if (AssertValue.isNotNullAndNotEmpty(this.mLChosenNetPhoto)) {
			increaseSelectablePicNum(this.mLChosenNetPhoto.size());
			this.mLChosenNetPhoto.clear();
		}
	}

	@Override
	public void addChosenLocalPhoto(DmLocalPhoto photo) {
		if (!mLChosenLocalPhoto.contains(photo)) {
			mLChosenLocalPhoto.add(photo);
			reduceSelectablPicNum(1);
		}

	}

	@Override
	public void removeChosenLocalPhoto(DmLocalPhoto photo) {
		if (mLChosenLocalPhoto.contains(photo)) {
			mLChosenLocalPhoto.remove(photo);
			increaseSelectablePicNum(1);
		}

	}

	public List<DmLocalPhoto> getmLChosenLocalPhoto() {
		return mLChosenLocalPhoto;
	}

	public List<DmNetPhoto> getmLChosenNetPhoto() {
		return mLChosenNetPhoto;
	}

	@Override
	public List<DmLocalPhoto> getChosenLocalPhotos() {
		return mLChosenLocalPhoto;
	}

	@Override
	public List<DmNetPhoto> getChosenNetPhotos() {
		return mLChosenNetPhoto;
	}

	@Override
	public void updateCurrentAlbum() {
		if (vp_getPic.getCurrentItem() == IMAGES_FROM_LOCAL) {
			showTitle4LocalAlbum();
		}
	}

	@Override
	public void popCencelOnClick(View view) {
		resetSelectalePhotos();
		updateChkOriginal();
	}

	/**
	 * 重置可选图片
	 */
	private void resetSelectalePhotos() {
		this.setMaxSelectPhotoNum(this.maxChoosePicNum, this.maxChoosePicNum);
		this.mLChosenLocalPhoto.clear();
		this.mLChosenNetPhoto.clear();

		this.localFragment.resetSelectablePhoto();
		this.netWorkFragment.resetSelectablePhoto();

		upBtnCompleteContent();
	}

	@Override
	public void popChoiceOnClick(View view) {
		changeModeAlbum(ModeAlbum.CHOICE);
	}

	@Override
	public void popBrowseOnClick(View view) {
		changeModeAlbum(ModeAlbum.BROWSE);
	}

	
	
	
	private class UploadDocument implements Runnable{

		CountDownLatch latch;
		/**
		 * @param callBack
		 * @param latch
		 */
		public UploadDocument(CountDownLatch latch) {
			super();
			this.latch = latch;
		}

		@Override
		public void run() {
			uploadLocalDocument(new CallBackYiDianTong() {
				@Override
				public void onSuccess(Object response) {
//					System.out.println("response = " + (Integer)response);
					Integer num = (Integer)response;
					if(num != null){
						setUploadDocumentSuccessNum(num);
					}else {
						setUploadDocumentSuccessNum(0);
					}
					
					latch.countDown();
				}
				
				@Override
				public void onFailure(Object response) {
					latch.countDown();
				}
			});
		}
		
		private void uploadLocalDocument(CallBackYiDianTong callBack) {
			if(documentFragment != null){
				documentFragment.uploadDocument(callBack);
			}else {
//				setUploadDocumentSuccessNum(0);
//				latch.countDown();
			}
		}
	}
	
	
	private class UploadPic implements Runnable{

		CountDownLatch latch;
		CountDownLatch mLatch;
		List<DmLocalPhoto> photos; 
		CommandNetworkService command;
		
		/**
		 * @param latch
		 * @param photos
		 */
		public UploadPic(CountDownLatch latch, List<DmLocalPhoto> photos) {
			super();
			this.latch = latch;
			this.photos = photos;
			
			init();
		}

		private void init(){
			command = new FactoryCommandNetworkServiceYiDianTong().creatCommandNetworkService();
			mLatch = new CountDownLatch(photos.size());
		}
		

		@Override
		public void run() {
			ExecutorService pool = Executors.newFixedThreadPool(photos.size() + 1);
			
			for (final DmLocalPhoto photo : photos) {
				pool.execute(new Runnable() {
					@Override
					public void run() {
						File uploadFile = null;
						String path = photo.getPath_absolute();
						if (isOrigin()) {
							uploadFile = new File(path);
						} else {
							uploadFile = UtilImageCompress.compressPicFile(path, AlbumActivity.this);
						}
						
						command.uploadImgFileUserLevelSync(uploadFile, new AsyncHttpResponseCallback() {
							@Override
							public void onSuccess(Response response) {
								addUploadPicSuccessNum(1);
								mLatch.countDown();
								
							}
							
							@Override
							public void onFailure(Response response) {
								mLatch.countDown();
							}
						});
					}
				});
			}
			
			try {
				mLatch.await();
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
			pool.shutdown();
			
			
			latch.countDown();
			
		}
	}
	
	
	private int uploadPicSuccessNum;
	private int uploadDocumentSuccessNum;
	
	private synchronized void addUploadPicSuccessNum(int num){
		uploadPicSuccessNum += num;
	}
	
	private synchronized void setUploadPicSuccessNum(int num){
		uploadPicSuccessNum = num;
	}
	
	private synchronized void setUploadDocumentSuccessNum(int num){
		uploadDocumentSuccessNum = num;
	}
	
	
	
	
	@Override
	public void popUploadOnClick(View view) {
//		uploadLocalPic();
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				setUploadPicSuccessNum(0);
				setUploadDocumentSuccessNum(0);
				final CountDownLatch latch = new CountDownLatch(2);
				handler.sendEmptyMessage(HANDLER_SHOW_PREGRESS_DLG);
				ExecutorService pool = Executors.newFixedThreadPool(2);
				
				pool.execute(new UploadDocument(latch));
				
				pool.execute(new UploadPic(latch, mLChosenLocalPhoto));
				
				try {
					latch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				pool.shutdown();
				handler.sendEmptyMessage(HANDLER_CLOSE_PREGRESS_DLG);
				
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						showToast("上传图片：" + uploadPicSuccessNum + "    文档 :" + uploadDocumentSuccessNum);
					}
				});
			}
		}).start();
	}

	

	private void uploadLocalPic() {
		
		if (!AssertValue.isNotNullAndNotEmpty(this.mLChosenLocalPhoto)) {
			showToast("没选择本地图片");
			return;
		}

		if (!AssertValue.isNotNullAndNotEmpty(this.mLChosenNetPhoto)) {
			uploadLocalPhoto(this.mLChosenLocalPhoto);
		} else {
			showToast("只能上传本地相册的图片到云相册");
		}
	}

	private void uploadLocalPhoto(List<DmLocalPhoto> localPhotos) {
		if (!AssertValue.isNotNullAndNotEmpty(localPhotos)) {
			return;
		}
		initShowProDlgCond(localPhotos.size());
		dlgProTip.show();

		for (DmLocalPhoto photo : this.mLChosenLocalPhoto) {
			// uploadPhoto(photo.getPath_absolute());
			String params = new String(photo.getPath_absolute());
			new UploadFileAsyncTask().execute(params);
		}
	}

	private class UploadFileAsyncTask extends AsyncTask<String, Void, File> {
		@Override
		protected File doInBackground(String... params) {
			String path = params[0];

			File uploadFile = null;
			if (isOrigin()) {
				uploadFile = new File(path);
			} else {
				uploadFile = UtilImageCompress.compressPicFile(path,AlbumActivity.this);
			}
			return uploadFile;
		}

		@Override
		protected void onPostExecute(File result) {
			uploadPhoto(result);
		}
	}

	private void doLoadPhoto(File file) {
		this.fileFactory.uploadImgFileUserLevel(file, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				suceess2CloseProDlg();
			}

			@Override
			public void onFailure(Response response) {
				fail2CloseProDlg();
			}
		});
	}

	private void uploadPhoto(File file) {
		File uploadFile = file;

		if (uploadFile == null) {
			showToast("文件压缩失败");
			fail2CloseProDlg();
			return;
		}

		doLoadPhoto(uploadFile);
	}

	
	
	
	
	private int deleteNetPicSuccessNum;
	private int deleteNetDocumentSuccessNum;
	
	private synchronized void setDeleteNetPicSuccessNum(int num){
		deleteNetPicSuccessNum = num;
	}
	
	private synchronized void setDeleteNetDocumentSuccessNum(int num){
		deleteNetDocumentSuccessNum = num;
	}
	
	private class DeleteNetDocument implements Runnable{

		CountDownLatch latch;
		
		/**
		 * @param latch
		 */
		public DeleteNetDocument(CountDownLatch latch) {
			super();
			this.latch = latch;
		}

		private void countDown(){
			latch.countDown();
		}
		
		@Override
		public void run() {
			if(netDocumentFragment != null){
				netDocumentFragment.deleteSelectedNetFileSync(new CallBackYiDianTong() {
					@Override
					public void onSuccess(Object response) {
						
						setDeleteNetDocumentSuccessNum((Integer)response);
						countDown();
					}
					
					@Override
					public void onFailure(Object response) {
						
						countDown();
					}
				});
			}else{
				countDown();
			}
		}
		
	}
	
	private class DeleteNetPic implements Runnable{

		CountDownLatch latch;
		
		/**
		 * @param latch
		 */
		public DeleteNetPic(CountDownLatch latch) {
			super();
			this.latch = latch;
		}



		@Override
		public void run() {
			
		}
		
	}
	
	
	
	private void doPopDeleteOnClick(View view){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODU show ProDlg
				handler.sendEmptyMessage(HANDLER_SHOW_PREGRESS_DLG);
				int num = 1;
				CountDownLatch latch = new CountDownLatch(num);
				ExecutorService pool = Executors.newFixedThreadPool(num);
				 
				setDeleteNetDocumentSuccessNum(0);
				setDeleteNetPicSuccessNum(0);
				// 删除选择的云端文档
				pool.execute(new DeleteNetDocument(latch));
				 
				// 删除选择的云端图片
//				pool.execute(new DeleteNetPic(latch));
				 
				try {
					latch.await();
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				 
				 pool.shutdown();
				
				 // TODU close ProDlg
				 handler.sendEmptyMessage(HANDLER_CLOSE_PREGRESS_DLG);
					
				 runOnUiThread(new Runnable() {
					@Override
					public void run() {
						showToast("删除云图片：" + deleteNetPicSuccessNum + "    云文档 :" + deleteNetDocumentSuccessNum);
					}
				});
			}
		});
	}
	
	@Override
	public void popDeleteOnClick(View view) {
		
//		doPopDeleteOnClick(view);
		
		if (!AssertValue.isNotNullAndNotEmpty(this.mLChosenNetPhoto)) {
			showToast("没选择图片");
		}

		if (AssertValue.isNotNullAndNotEmpty(this.mLChosenNetPhoto)
				&& !AssertValue.isNotNullAndNotEmpty(this.mLChosenLocalPhoto)) {
			deleteNetPhoto(this.mLChosenNetPhoto);
		} else {
			showToast("只能删除云相册的图片");
		}
	}

	private void deleteNetPhoto(final List<DmNetPhoto> photos) {
		if (!AssertValue.isNotNullAndNotEmpty(photos)) {
			return;
		}

		List<String> ids = new ArrayList<String>();
		for (DmNetPhoto photo : photos) {
			ids.add(photo.getFileInfo().getId());
		}
		this.dlgProTip.show();
		this.fileFactory.deleteNetPhoto(ids, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				dlgProTip.dismiss();
				showToast("删除成功");
				netWorkFragment.removeNetPhotos(photos);
				removeAllChosenNetPhoto();

			}

			@Override
			public void onFailure(Response response) {
				dlgProTip.dismiss();
				showToast("删除失败");

			}
		});

	}

	@Override
	public boolean isOrigin() {
		return this.cbOriginalPhoto.isChecked();
	}

	private int showProDlgCond;

	private void initShowProDlgCond(int num) {
		this.showProDlgCond += num;
	}

	private void suceess2CloseProDlg() {
		this.showProDlgCond--;
		if (this.showProDlgCond <= 0) {
			this.showProDlgCond = 0;
			this.dlgProTip.dismiss();
			showToast("成功");
		}
	}

	private void fail2CloseProDlg() {
		this.showProDlgCond--;
		if (this.showProDlgCond <= 0) {
			this.showProDlgCond = 0;
			this.dlgProTip.dismiss();
			showToast("失败");
		}
	}

	@Override
	public ModelAlbum2Level getAlbumModeLevel() {
		return model2Level;
	}

	@Override
	public int getMaxChooseDocumentNum() {
		return 6;
	}

	@Override
	public int getSelectableDocumentNum() {
		return getMaxChooseDocumentNum() - selectedDocuments;
	}

	@Override
	public synchronized void notifyAddDocument(int num) {
		selectedDocuments += num;
		// TUDO 更新界面相关数据
	}

	@Override
	public synchronized void notifyRemoveDocument(int num) {
		
		selectedDocuments -= num;
		
		// TUDO 更新界面相关数据
	}

	public int getUploadPicSuccessNum() {
		return uploadPicSuccessNum;
	}

	public int getUploadDocumentSuccessNum() {
		return uploadDocumentSuccessNum;
	}


}
