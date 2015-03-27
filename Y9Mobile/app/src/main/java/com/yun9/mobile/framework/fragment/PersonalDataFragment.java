package com.yun9.mobile.framework.fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.camera.activity.CameraActivity;
import com.yun9.mobile.camera.domain.DmImageItem;
import com.yun9.mobile.camera.imageInterface.CameraCallBack;
import com.yun9.mobile.camera.imageInterface.ImageCallBack;
import com.yun9.mobile.camera.util.SingletonImageLoader;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.file.FileFactory;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.model.FileInfo;
import com.yun9.mobile.framework.model.UserQuery;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.session.SessionManager;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.UtilFile;
import com.yun9.mobile.framework.util.ResourceUtil;
import com.yun9.mobile.framework.util.TipsUtil;
import com.yun9.mobile.framework.util.UtilImageCompress;
import com.yun9.mobile.framework.view.TitleBarView;
import com.yun9.mobile.imageloader.MyImageLoader;
import com.yun9.mobile.roundimage.RoundImageView;

/**
 * 个人资料展示
 */
@SuppressLint("ValidFragment")
public class PersonalDataFragment extends Fragment implements
		View.OnClickListener, ImageCallBack {

	public static final String TAG = PersonalDataFragment.class
			.getCanonicalName();
	private View mView;

	private RelativeLayout rl_name;
	private RelativeLayout rl_gender;
	private RelativeLayout rl_birthday;
	private RelativeLayout rl_phone;
	private RelativeLayout rl_signature;
	private RoundImageView iv_headPic;

	private TextView tv_name;
	private TextView tv_gender;
	private TextView tv_birthday;
	private TextView tv_phone;
	private TextView tv_signature;
	private TextView tv_title;

	private ImageButton btn_back;

	private UserQuery user;
	private boolean isUser;
	private SingletonImageLoader imageLoader;
	private ProgressDialog progressDialog;

	public PersonalDataFragment(UserQuery user, boolean isUser) {
		this.user = user;
		this.isUser = isUser;
		imageLoader = SingletonImageLoader.getInstance();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_personal_data, null);
		return mView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		findId();
		init();
		setEvent();
	}

	private void init() {
		btn_back.setVisibility(View.VISIBLE);
		tv_title.setVisibility(View.VISIBLE);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String birthday = format.format(user.getBirthday());
		tv_name.setText(user.getName());
		tv_gender.setText(user.getSex());
		tv_birthday.setText(birthday);
		tv_phone.setText(user.getNo());
		tv_signature.setText(user.getSignature());
		tv_title.setText(R.string.ucardinfo);
		MyImageLoader.getInstance().displayImage(user.getHeaderfileid(),
				iv_headPic);
	}

	private void setEvent() {
		if (isUser) {
			iv_headPic.setOnClickListener(this);
			rl_name.setOnClickListener(this);
			rl_birthday.setOnClickListener(this);
			rl_gender.setOnClickListener(this);
			rl_phone.setOnClickListener(this);
			rl_signature.setOnClickListener(this);
			btn_back.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					getActivity().finish();
				}
			});
		} else {
			btn_back.setOnClickListener(this);
		}
	}

	private void findId() {
		rl_name = (RelativeLayout) mView.findViewById(R.id.personal_data_mName);
		rl_gender = (RelativeLayout) mView
				.findViewById(R.id.personal_data_gender);
		rl_birthday = (RelativeLayout) mView
				.findViewById(R.id.personal_data_birthday);
		rl_phone = (RelativeLayout) mView
				.findViewById(R.id.personal_data_phone);
		rl_signature = (RelativeLayout) mView
				.findViewById(R.id.personal_data_signature);

		iv_headPic = (RoundImageView) mView.findViewById(R.id.iv_head_pic);

		tv_name = (TextView) mView.findViewById(R.id.tv_personal_data_name);
		tv_gender = (TextView) mView.findViewById(R.id.tv_personal_data_gender);
		tv_birthday = (TextView) mView
				.findViewById(R.id.tv_personal_data_birthday);
		tv_phone = (TextView) mView.findViewById(R.id.tv_personal_data_phone);
		tv_signature = (TextView) mView
				.findViewById(R.id.tv_personal_data_signature);

		TitleBarView titleBarView = (TitleBarView) mView
				.findViewById(R.id.personal_data_title);
		btn_back = titleBarView.getBtnLeft();
		tv_title = titleBarView.getTvTitle();
	}

	@SuppressLint("UseSparseArrays")
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_head_pic:
			CameraActivity.newInsatnce(getActivity(), new CameraCallBack() {
				
				@Override
				public void ImageUrlCall(DmImageItem imageItem) {
					if(AssertValue.isNotNull(imageItem))
					{
						HashMap<Integer,String> localImageMaps=new HashMap<Integer, String>();
						localImageMaps.put(0, imageItem.getImageUrl());
						ImgUriCall(localImageMaps,null);
					}
				}
			});
			break;
		case R.id.personal_data_mName:

			break;
		case R.id.personal_data_gender:
			break;
		case R.id.personal_data_birthday:
			break;
		case R.id.personal_data_phone:
			break;
		case R.id.personal_data_signature:
			break;
		default:
			Log.i(TAG, "nothing be choose");
			break;
		}
	}

	private File fileYaSuo(String path) {

		File oldFile = new File(path);

		String picName = UtilFile.getFileNameNoEx(oldFile.getName());

		String suffix = UtilFile.getExtensionName(oldFile.getName());
		suffix = "." + suffix;
		// 文件名最少要6个字节
		picName = picName + "-vaild";
		File picFile = null;
		try {
			picFile = File.createTempFile(picName, suffix);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bitmap bm = UtilImageCompress.getBitmap(path, 800, 400);
		UtilImageCompress.compressBitmapToFile(bm, picFile, 50);

		picFile = UtilImageCompress.compressPicFile(path, getActivity());
		return picFile;
	}

	@Override
	public void ImgUriCall(Map<Integer, String> localImageMaps,
			Map<Integer, String> netWorkImageMap) {
		if (!((localImageMaps != null && localImageMaps.size() > 0) || (netWorkImageMap != null && netWorkImageMap
				.size() > 0))) {
			return;
		}
		progressDialog = TipsUtil.openDialog(null, false, this.getActivity());
		if (localImageMaps != null && localImageMaps.size() > 0) {
			FileFactory fileFactory = BeanConfig.getInstance().getBeanContext()
					.get(FileFactory.class);
			Collection<String> collection = localImageMaps.values();
			for (String images : collection) {

				File file = fileYaSuo(images);
				if (!AssertValue.isNotNull(file)) {
					continue;
				}
				fileFactory.uploadImgFileSystemLevel(file,
						new AsyncHttpResponseCallback() {

							@Override
							public void onSuccess(Response response) {
								FileInfo fileInfo = (FileInfo) response
										.getPayload();
								final String fileId = fileInfo.getId();
								Resource resource = ResourceUtil
										.get("SysUserUpdateHeaderfileidService");
								resource.param("headerfileid", fileId);
								resource.invok(new AsyncHttpResponseCallback() {
									@Override
									public void onSuccess(Response response) {
										progressDialog.dismiss();
										SessionManager manager = BeanConfig
												.getInstance().getBeanContext()
												.get(SessionManager.class);
										manager.getAuthInfo().getUserinfo()
												.setHeaderfileid(fileId);
										manager.setLocationParams();
										user.setHeaderfileid(fileId);
										MyImageLoader.getInstance().displayImage(user.getHeaderfileid(),
												iv_headPic);
									}

									@Override
									public void onFailure(Response response) {
										progressDialog.dismiss();
										TipsUtil.showToast("更新头像失败。",
												getActivity());
									}
								});
								return;
							}

							@Override
							public void onFailure(Response response) {
								progressDialog.dismiss();
								TipsUtil.showToast("上传头像失败。", getActivity());
							}
						});
				return;// 只取第一条，然后return
			}

		} else if (netWorkImageMap != null && netWorkImageMap.size() > 0) {
			Collection<String> collection = netWorkImageMap.values();
			for (final String list : collection) {
				Resource resource = ResourceUtil
						.get("SysUserUpdateHeaderfileidService");
				resource.param("headerfileid", list);
				resource.invok(new AsyncHttpResponseCallback() {
					@Override
					public void onSuccess(Response response) {
						progressDialog.dismiss();
						SessionManager manager = BeanConfig.getInstance()
								.getBeanContext().get(SessionManager.class);
						manager.getAuthInfo().getUserinfo()
								.setHeaderfileid(list);
						manager.setLocationParams();
						user.setHeaderfileid(list);
						MyImageLoader.getInstance().displayImage(user.getHeaderfileid(),
								iv_headPic);
					}

					@Override
					public void onFailure(Response response) {
						progressDialog.dismiss();
						TipsUtil.showToast("更新头像失败。", getActivity());
					}
				});
				return; // 只取第一条，然后return
			}
		}
	}
}
