package com.yun9.mobile.msg.presenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.cache.MsgCardCache;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.resources.ResourceFactory;
import com.yun9.mobile.framework.session.SessionManager;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.SystemMethod;
import com.yun9.mobile.msg.adapter.MsgCardQueryGroup;
import com.yun9.mobile.msg.impl.ImplComparatorMsg;
import com.yun9.mobile.msg.interfaces.MsgCardListFragmentIView;
import com.yun9.mobile.msg.model.MyMsgCard;

public class MsgCardListFragmentPresenter {
	
	MsgCardListFragmentIView iView;
	private String limitRow = "20";
	private ArrayList<MyMsgCard> msgCards;

	private Activity activity;
	
	/**
	 * 默认是“我的动态”
	 */
	private String queryGroup;
	
	private String content;
	
	/**
	 * 下拉刷新数量
	 */
	private int refreshNum = 0;
	
	
	/**
	 * 加载数量
	 */
	private int loadNum = 0;
	

	/**
	 *  第一次启动页面时，如果缓存不存在，显示 loadView 如果msgCards 已经有了 则不需要loadview了
	 */
	private boolean initUI;

	/**
	 * @param iView
	 * @param msgCardAdapter
	 */
	public MsgCardListFragmentPresenter(Activity activity, MsgCardListFragmentIView iView) {
		super();
		this.activity = activity;
		this.iView = iView;

		init();
	}
	
	private void init() {
		msgCards = new ArrayList<MyMsgCard>();
		List<MyMsgCard> caCheList = MsgCardCache.getInstance().getQueryGroupCaches(queryGroup);
		if(AssertValue.isNotNullAndNotEmpty(caCheList))
		{
			msgCards.addAll(caCheList);
		}
	}

	

	public void onRefresh() {
		SessionManager sessionManager = BeanConfig.getInstance().getBeanContext().get(SessionManager.class);
		String userid = sessionManager.getAuthInfo().getUserinfo().getId();
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> header = new HashMap<String, Object>();
		
		String lastupid = getLastupid();
		header.put(Resource.HEADER.LASTUPID, lastupid);
		header.put(Resource.HEADER.LIMITROW, limitRow);
		param.put("userid", userid);
		if (AssertValue.isNotNullAndNotEmpty(content)) {
			param.put("content", content);
		} else if (AssertValue.isNotNullAndNotEmpty(queryGroup)){
			param.put("querygroup", queryGroup);
		}
		getMsgCards(param, header, new AsyncHttpResponseCallback() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Response response) {
				List<MyMsgCard> tempmMyMsgCard = (List<MyMsgCard>) response.getPayload();
				refreshNum = 0;
				ImplComparatorMsg comparator = new ImplComparatorMsg();
				if (AssertValue.isNotNullAndNotEmpty(tempmMyMsgCard)) {
					Collections.sort(tempmMyMsgCard, comparator);
					refreshNum = tempmMyMsgCard.size();
					tempmMyMsgCard = MsgCardCache.getInstance().cacheNewMsgCards(tempmMyMsgCard, queryGroup);
					msgCards.clear();
					msgCards.addAll(tempmMyMsgCard);
					Collections.sort(msgCards, comparator);
				}
				// 界面刷新
				refreshUIAfterOnRefreshOK();
		
			}
			@Override
			public void onFailure(Response response) {
				iView.showToast("获取数据失败");
				iView.onRefreshComplete();
			}
		});
	}
	
	
	/**
	 * 下拉刷新成功后更新UI
	 */
	protected void refreshUIAfterOnRefreshOK() {
		if(refreshNum == 0){
			iView.showToast("没有新的消息了待会再试试吧。");
		}else{
			iView.showToast("刷新了" + refreshNum + "条消息。");
		}
		controlUIShow();
		iView.notifyDataSetChanged();
		iView.onRefreshComplete();		
	}

	public void onLoadMore() {
		SessionManager sessionManager = BeanConfig.getInstance().getBeanContext().get(SessionManager.class);
		String userid = sessionManager.getAuthInfo().getUserinfo().getId();
		
		
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> header = new HashMap<String, Object>();
		
		String lastdownid = getLastdownid();
		if(!AssertValue.isNotNullAndNotEmpty(lastdownid)){
			// iView.showToast("不可能发生的事发生了");
		}
		
		header.put(Resource.HEADER.LASTDOWNID, lastdownid);
		header.put(Resource.HEADER.LIMITROW, limitRow);
		param.put("userid", userid);
		if (AssertValue.isNotNullAndNotEmpty(content)) {
			param.put("content", content);
		} else if (AssertValue.isNotNullAndNotEmpty(queryGroup)){
			param.put("querygroup", queryGroup);
		}
		
		getMsgCards(param, header, new AsyncHttpResponseCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Response response) {
				loadNum = 0;
				List<MyMsgCard> tempmMyMsgCard = (List<MyMsgCard>) response.getPayload();
				ImplComparatorMsg comparator = new ImplComparatorMsg();
				if (AssertValue.isNotNullAndNotEmpty(tempmMyMsgCard)) {
					Collections.sort(tempmMyMsgCard, comparator);
					loadNum = tempmMyMsgCard.size();
					tempmMyMsgCard = MsgCardCache.getInstance().cacheMoreMsgCards(tempmMyMsgCard, queryGroup);
					msgCards.clear();
					msgCards.addAll(tempmMyMsgCard);
					Collections.sort(msgCards, comparator);
					iView.notifyDataSetChanged();
					iView.onLoadMoreComplete();
				}
				afterLoadOK();
			}
			
			@Override
			public void onFailure(Response response) {
				iView.showToast("获取数据失败");
				iView.onLoadMoreComplete();			
			}
		});
	}
	
	/**
	 * 加载成功后更新UI
	 */
	protected void afterLoadOK() {
		if(loadNum == 0){
			iView.showToast("已经到底了");
		}else{
			iView.showToast("加载了" + loadNum + "条消息");
		}
		iView.notifyDataSetChanged();
		iView.onLoadMoreComplete();
	}

	private void getMsgCards(Map<String, Object> param, Map<String, Object> header, AsyncHttpResponseCallback callback){
		ResourceFactory resourceFactory = BeanConfig.getInstance().getBeanContext().get(ResourceFactory.class);
		Resource sysInstQueryResource = resourceFactory.create("SysMsgCardQueryMyCardService");
		sysInstQueryResource.setParams(param);
		sysInstQueryResource.setHeader(header);
		sysInstQueryResource.invok(callback);
	}
	
	
	private String getLastupid() {
		if (msgCards.size() > 0) {
			return msgCards.get(0).getMain().getId();
		}
		return "0";
	}
	
	private String getLastdownid(){
		String Lastdownid = null;
		if (AssertValue.isNotNullAndNotEmpty(msgCards)){
			Lastdownid = msgCards.get(msgCards.size()-1).getMain().getId();
		}
		return Lastdownid;
	}
	
	public void reload(String queryGroup,String content) {
		this.queryGroup = queryGroup;
		this.content = content;
		msgCards.clear();
		List<MyMsgCard> caCheList = MsgCardCache.getInstance().getQueryGroupCaches(queryGroup);
		if(AssertValue.isNotNullAndNotEmpty(caCheList))
		{
			msgCards.addAll(caCheList);
		}
		initUI();
	}


	public List<MyMsgCard> getMsgCards() {
		return msgCards;
	}

	public void initUI() {
		if(SystemMethod.isOpenNetwork(this.activity)){
			MsgCardCache.getInstance().clean();
			this.msgCards.clear();
			iView.isShowLoading(true);
			iView.isShowMsgUI(false);
			initUI = true;
			onRefresh();
		}else{
			iView.isShowLoading(false);
			iView.isShowMsgUI(true);
		}
	}
	
	// 只使用一次，在启动UI时，没有数据才使用
	private void controlUIShow(){
		if(initUI){
			iView.isShowLoading(false);
			iView.isShowMsgUI(true);
			initUI = !initUI;
		}
	}

	public String getQueryGroup() {
		return queryGroup;
	}

	public void setQueryGroup(String queryGroup) {
		this.queryGroup = queryGroup;
	}
}
