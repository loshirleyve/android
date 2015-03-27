package com.yun9.mobile.msg.activity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.mobile.R;
import com.yun9.mobile.camera.domain.DmLocalPhoto;
import com.yun9.mobile.camera.domain.DmNetPhoto;
import com.yun9.mobile.camera.fragment.AlbumPhoto4MsgFragment;
import com.yun9.mobile.department.support.SelectContactUser;
import com.yun9.mobile.department.support.SelectContactUserFactory;
import com.yun9.mobile.department.support.UserConstant;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.constant.scope.ConstantScope;
import com.yun9.mobile.framework.file.FileFactory;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.interfaces.scope.MsgScopeCallBack;
import com.yun9.mobile.framework.location.LocationCallBack.LocationOutParam;
import com.yun9.mobile.framework.model.FileInfo;
import com.yun9.mobile.framework.model.Org;
import com.yun9.mobile.framework.model.User;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.resources.ResourceFactory;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.StringUtils;
import com.yun9.mobile.framework.util.TipsUtil;
import com.yun9.mobile.framework.util.UtilImageCompress;
import com.yun9.mobile.framework.view.ImageAndText;
import com.yun9.mobile.msg.interfaces.NewMsgCardIView;
import com.yun9.mobile.msg.presenter.NewMsgCardPresenter;
import com.yun9.mobile.position.iface.IAcquirePosition;
import com.yun9.mobile.position.iface.IAcquirePositionCallBack;
import com.yun9.mobile.position.impl.AcquirePosition;
import com.yun9.mobile.position.impl.PositionFactory;

public class NewMsgCard extends Activity implements NewMsgCardIView {

	private final int HANDLE_COMMIT = 0x102;

	private ImageView ivTopic;
	private ImageView ivSend;
	private EditText etText;
	private ResourceFactory resourceFactory;
	private ProgressDialog progressDialog;
	private ImageView ivUser;
	private ImageView imagePic;
	private ImageView imageMap;
	private TextView tvMap;
	private String content;
	private MyHandler handler;
	private List<Map<String, String>> userIds;
	private List<Map<String, String>> fileList;
	private String sn;
	private TextView tvCancel;

	/**
	 * 上传文件失败次数
	 */
	private int fileFailNum = 0;
	private int maxFailNum = 0;

	/**
	 * 提交消息的条件（文件上传成功后才能提交消息）
	 */
	private int commitCond = 0;
	private ImageAndText itLocate;
	private ImageAndText itScope;
	private TextView orgORuser;
	/**
	 * 业务处理类
	 */
	private NewMsgCardPresenter presenter;

	// 联系人页面选择的用户和组织
	private Map<String, User> selectedUsers;
	private Map<String, Org> selectedOrgs;
	private SpannableString sp;
	//@的联系人啊
	private Map<String, User> contactUsers;
	private AlbumPhoto4MsgFragment albumPhotoFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_msg_card);
		resourceFactory = BeanConfig.getInstance().getBeanContext().get(ResourceFactory.class);
		this.initView();

	}

	private void initView() {
		itScope = (ImageAndText) findViewById(R.id.itScope);
		orgORuser = (TextView) findViewById(R.id.orgORuser);
		itLocate = (ImageAndText) findViewById(R.id.itLocate);
		ivTopic = (ImageView) findViewById(R.id.ivTopic);
		ivSend = (ImageView) findViewById(R.id.send);
		ivUser = (ImageView) findViewById(R.id.user);
		etText = (EditText) findViewById(R.id.text);
		imagePic = (ImageView) findViewById(R.id.image_pic);
		//imageMap = (ImageView) findViewById(R.id.map);
		tvCancel = (TextView) findViewById(R.id.cancel);
		handler = new MyHandler();
		fileList = new ArrayList<Map<String, String>>();
		content = getIntent().getStringExtra("content");
		albumPhotoFragment = new AlbumPhoto4MsgFragment();
		getFragmentManager().beginTransaction().add(R.id.fragment_pic, albumPhotoFragment).commit();
		ivSend.setOnClickListener(sendOnClickListener);
		imagePic.setOnClickListener(imageGetPic);
		ivTopic.setOnClickListener(topicListener);
		itLocate.setOnClickListener(itLocateOnClickListener);
		itScope.setOnClickListener(ScopeOnClickListener);
		tvCancel.setOnClickListener(CancelClickListener);
		ivUser.setOnClickListener(addContactUser);
		
		if (AssertValue.isNotNullAndNotEmpty(content)) {
			etText.setText(content);
		}
		presenter = new NewMsgCardPresenter(NewMsgCard.this, etText);

		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("正在处理，请稍后......");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
	}

	OnClickListener CancelClickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			showBackDialog();
		}
	};

	OnClickListener ScopeOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			presenter.scopeOnClickListener();
		}

	};

	OnClickListener itLocateOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			presenter.itLocateOnClickListener();
		}

	};

	OnClickListener topicListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			presenter.topicOnClick();
		}

	};

	OnClickListener imageMapListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			IAcquirePosition defaultIAPosition = PositionFactory
					.createPosition(NewMsgCard.this);
			defaultIAPosition.go2GetPosition(AcquirePosition.MOD_DAKA,
					AcquirePosition.RADUIS_DEFAULT,
					new IAcquirePositionCallBack() {
						@Override
						public void onSuccess(OutParam outParm) {
							tvMap.setText(outParm.getAddr());
						}

						@Override
						public void onFailure() {

						}
					});
		}
	};

	OnClickListener imageGetPic = new OnClickListener() {
		@Override
		public void onClick(View view) {
			albumPhotoFragment.go2ChosePhoto();
		}
	};

	OnClickListener addContactUser = new OnClickListener() {
		@Override
		public void onClick(View v) {
			selectUserOrg();
		}
	};

	private void reset() {
		etText.setText("");
	}

	private OnClickListener sendOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			if (!AssertValue.isNotNullAndNotEmpty(etText.getText().toString())) {
				Toast.makeText(getApplicationContext(),
						R.string.msgcard_new_notnull, Toast.LENGTH_SHORT)
						.show();
				return;
			}
			String byte4String = checkByte4(etText.getText().toString());
			if (AssertValue.isNotNullAndNotEmpty(byte4String)) {
				TipsUtil.showToast("包含不支持的字符串："+byte4String, getApplicationContext());
				return;
			}
			List<String> userids = new ArrayList<String>();
			List<String> orgids = new ArrayList<String>();
			presenter.getIds(userids, orgids);
			String mode = presenter.getCurrentMode();
			if (mode==null || !mode.equals(ConstantScope.SCOPE_PRIVATE) && userids.size() < 1
						&& orgids.size() < 1) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						NewMsgCard.this);
				builder.setTitle("提示");
				if(mode==null)
				builder.setMessage("请选择分享范围");
				else if(mode!=null)
				builder.setMessage("请点击"+mode+"选择分享范围");
				builder.setPositiveButton("确定", null);
				builder.create().show();
				return;
			}
			// 填充网络图片字段
			fillNetPhotos();
			
			// 填充本地图片字段，先将图片上传，获得id后填充
			fillLocakPhotos();
		}

		private String checkByte4(String source) {
			String byte4String = null;
			try {
				byte4String = StringUtils.checkByte4(source, null);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return byte4String;
		}
	};

	
	/**
	 * 填充网络图片字段
	 */
	private void fillNetPhotos(){
		List<DmNetPhoto> photos = albumPhotoFragment.getNetPhotos();
		if(AssertValue.isNotNullAndNotEmpty(photos)){
			Map<String, String> images = null;
			for(DmNetPhoto photo : photos){
				images = new HashMap<String, String>();
				images.put("fileid", photo.getFileInfo().getId());
				images.put("desc", null);
				fileList.add(images);
			}
		}
	}
	
	/**
	 * 填充本地图片字段，先将图片上传，获得id后填充
	 */
	private void fillLocakPhotos(){
		
		List<DmLocalPhoto> photos = albumPhotoFragment.getLocalPhotos();
		if(AssertValue.isNotNullAndNotEmpty(photos)){
			FileFactory fileFactory = BeanConfig.getInstance().getBeanContext().get(FileFactory.class);
			progressDialog.show();
			for(DmLocalPhoto photo : photos){
				File file = null;
				if(albumPhotoFragment.isOrigin()){
					file = new File(photo.getPath_absolute());
				}else{
					file = UtilImageCompress.compressPicFile(photo.getPath_absolute(), this);
				}
				if (!AssertValue.isNotNull(file)) {
					continue;
				}
				maxFailNum++;
				commitCond++;
				fileFactory.uploadImgFileUserLevel(file, new AsyncHttpResponseCallback() {
					@Override
					public void onSuccess(Response response) {
						FileInfo fileInfo = (FileInfo) response.getPayload();
						Message msg = new Message();
						msg.what = 101;
						sn = fileInfo.getId();
						msg.obj = sn;
						handler.sendMessage(msg);
						commitCond--;
						if (commitCond == 0) {
							handler.sendEmptyMessage(HANDLE_COMMIT);
						}
					}

					@Override
					public void onFailure(Response response) {
						fileFailNum++;
						commitCond--;
						if (commitCond == 0) {
							handler.sendEmptyMessage(HANDLE_COMMIT);
						}
						Toast.makeText(NewMsgCard.this,"上传图片失败：" + fileFailNum,Toast.LENGTH_SHORT).show();
						if (fileFailNum == maxFailNum) {
							progressDialog.dismiss();
						}
					}
				});
			}
		}else {
			handler.sendEmptyMessage(HANDLE_COMMIT);
		}
		
	}
	
	private AsyncHttpResponseCallback sendAsyncHttpResponseCallback = new AsyncHttpResponseCallback() {

		@Override
		public void onSuccess(Response response) {
			Toast.makeText(getApplicationContext(),
					R.string.msgcard_new_success, Toast.LENGTH_SHORT).show();
			reset();
			progressDialog.dismiss();
			NewMsgCard.this.finish();
		}

		@Override
		public void onFailure(Response response) {
			progressDialog.dismiss();
			Toast.makeText(getApplicationContext(),
					"提交消息失败" + response.getCause(), Toast.LENGTH_SHORT).show();
			NewMsgCard.this.finish();
		}
	};

	@Override
	public void showToast(String msg) {

		Toast.makeText(NewMsgCard.this, msg, 0).show();
	}

	int num = 0;

	class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int what = msg.what;
			if (what == 101) {
				num++;
				Map<String, String> files = new HashMap<String, String>();
				files.put("fileid", msg.obj.toString());
				files.put("desc", null);
				fileList.add(files);
			}

			else if (HANDLE_COMMIT == what) {
				commit(fileList);
			}
		}
	}

	@Override
	public void locationState(String text, int id) {
		itLocate.setITText(text);
		itLocate.setITImage(id);
	}

	@Override
	public void scopeMode(String text, int id) {
		itScope.setITText(text);
		itScope.setITImage(id);
	}

	@Override
	public void setOrgORuser(List<String> userNames, List<String> orgNames) {
		String name = "";
		int m=0;
		if (AssertValue.isNotNullAndNotEmpty(userNames)) {
			name = "";
			if(userNames.size()>4)m=3;else m=userNames.size();
			for (int i = 0; i < m; i++) {
				name += userNames.get(i) + ";";
			}
			if(userNames.size()>4)name=name.substring(0,name.length()-1)+"...";
		}
		if (AssertValue.isNotNullAndNotEmpty(orgNames)) {
			name = "";
			if(orgNames.size()>4)m=3;else m=orgNames.size();
			for (int i = 0; i < m; i++) {
				name += orgNames.get(i) + ";";
			}
			if(orgNames.size()>4)name=name.substring(0,name.length()-1)+"...";
		}
		if(name!="" && name!=null)
		{
			orgORuser.setVisibility(View.VISIBLE);
			orgORuser.setText(name);
		}
		else
		{
			orgORuser.setVisibility(View.GONE);
		}
		
	}

	@Override
	public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			showBackDialog();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	private void showBackDialog() {
		
		if(AssertValue.isNotNullAndNotEmpty(albumPhotoFragment.getLocalPhotos())||AssertValue.isNotNullAndNotEmpty(albumPhotoFragment.getNetPhotos())||!etText.getText().toString().equals("")){
			
			AlertDialog.Builder builder = new AlertDialog.Builder(NewMsgCard.this);
			builder.setTitle("退出提示");
			builder.setMessage("确定取消所写的动态");
			builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface,
						int i) {
					NewMsgCard.this.finish();
				}
			});
			
			builder.setNegativeButton("取消", null);
			builder.create().show();
		}else{
			this.finish();
		}
	}

	private void commit(List<Map<String, String>> attachments) {
		Resource resource = resourceFactory.create("SysMsgCardSaveService");
		List<String> userids = new ArrayList<String>();
		List<String> orgids = new ArrayList<String>();
		presenter.getIds(userids, orgids);
		userIds = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		if (userids.size() > 0) {
			for (String userId : userids) {
				map = new HashMap<String, String>();
				map.put("userid", userId);
				map.put("type", "user");
				userIds.add(map);
			}
		}

		if (orgids.size() > 0) {
			for (String userId : orgids) {
				map = new HashMap<String, String>();
				map.put("userid", userId);
				map.put("type", "org");
				userIds.add(map);
			}
		}
		resource.param("users", userIds);
		resource.param("scope", "private");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		LocationOutParam location = presenter.getLocation();
		if (location != null) {
			resource.header("locationx", String.valueOf(location.getLatitude()));
			resource.header("locationy",
					String.valueOf(location.getLongitude()));
			resource.header("locationlabel", location.getAddr());
		}

		if (attachments == null) {
			attachments = new ArrayList<Map<String, String>>();
		}

		resource.param("subject", "测试标题");
		resource.param("content", etText.getText().toString());
		resource.param("source", "none");
		resource.param("actions", list);
		resource.param("attachments", attachments);
		resource.invok(sendAsyncHttpResponseCallback);
	}

	@Override
	public void showEditText(String msgShow) {
		etText.setText(msgShow);
	}

	@Override
	public void showEditText(SpannableString msgShow) {
		etText.setText(msgShow);

	}
	
	
	
	public void selectUserOrg() {
		if(contactUsers==null)
		{
			SelectContactUser selectContactUser = SelectContactUserFactory
					.create(this);
				selectContactUser.selectContactUser(new MsgScopeCallBack() {
					@Override
					public void onSuccess(int mode, Map<String, User> users,
							Map<String, Org> orgs) {
						contactUsers=users;
						for (String key : users.keySet()) {
							sp = new SpannableString("@"+users.get(key).getName()+" ");
							sp.setSpan(new ForegroundColorSpan(Color.BLUE),0 ,sp.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
							etText.getText().append(sp);
						}
					}
		
					@Override
					public void onFailure() {
					}
				}, null, null, UserConstant.SELECT_USER);
		}
		else
		{
			SelectContactUser selectContactUser = SelectContactUserFactory
					.create(this);
				selectContactUser.selectContactUser(new MsgScopeCallBack() {
					@Override
					public void onSuccess(int mode, Map<String, User> users,
							Map<String, Org> orgs) {
						contactUsers=users;
						for (String key : users.keySet()) {
							sp = new SpannableString("@"+users.get(key).getName()+" ");
							sp.setSpan(new ForegroundColorSpan(Color.BLUE),0 ,sp.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
							etText.getText().append(sp);
						}
					}
		
					@Override
					public void onFailure() {
					}
				}, contactUsers, null, UserConstant.SELECT_USER);
		}
		
	}
}
