package com.yun9.mobile.product.utils;

import java.util.ArrayList;
import java.util.List;

import com.yun9.mobile.framework.model.server.sale.ModelProductDetailInfo;
import com.yun9.mobile.framework.model.server.sale.ModelProductInfo;
import com.yun9.mobile.product.entity.OrderAttachmentsInfo;
import com.yun9.mobile.product.entity.ProductInfo;

/**
 * 产品信息工具类
 * @author Kass
 */
public class ProductUtil {
	/**
	 * 获取产品信息列表
	 * @param list
	 * @return
	 */
	public static List<ProductInfo> getProductInfo(List<ModelProductInfo> list) {
		List<ProductInfo> productList = new ArrayList<ProductInfo>();
		ProductInfo productInfo;
		for (int i = 0; i < list.size(); i ++) {
			productInfo = new ProductInfo();
			productInfo.setImgid(list.get(i).getImgid());
			productInfo.setId(list.get(i).getId());
			productInfo.setCompany(list.get(i).getInstname());
			productInfo.setName(list.get(i).getName());
			productInfo.setPrice(list.get(i).getSaleprice() + "元/" + list.get(i).getCodename());
			productInfo.setIntroduce(list.get(i).getIntroduce());
			productList.add(productInfo);
		}
		return productList;
	}
	
	/**
	 * 获取产品服务时间
	 * @param times
	 * @param cycle
	 * @return
	 */
	public static String getProductTimes(int times, String cycle) {
		String productTimes = null;
		if ("onece".equals(cycle)) {
			productTimes = times + "次";
		} else if ("specifyday".equals(cycle)) {
			productTimes = "指定日期";
		} else if ("day".equals(cycle)) {
			productTimes = times + "天";
		} else if ("week".equals(cycle)) {
			productTimes = times + "周";
		} else if ("month".equals(cycle)) {
			productTimes = times + "个月";
		} else if ("quarter".equals(cycle)) {
			productTimes = times + "季";
		} else if ("year".equals(cycle)) {
			productTimes = times + "年";
		}
		return productTimes;
	}
	
	/**
	 * 获取产品类型
	 * @param type
	 * @return
	 */
	public static String getProductType(String type) {
		String productType = null;
		if ("service".equals(type)) {
			productType = "服务产品";
		} else if ("entity".equals(type)) {
			productType = "实体产品";
		}
		return productType;
	}
	
	/**
	 * 获取产品付款要求
	 * @param payRequire
	 * @return
	 */
	public static String getProductPayRequire(String payRequire) {
		String productPayRequire = null;
		if ("prepay".equals(payRequire)) {
			productPayRequire = "预付款";
		} else if ("afterpay".equals(payRequire)) {
			productPayRequire = "后付款";
		}
		return productPayRequire;
	}
	
	public static OrderAttachmentsInfo getOrderAttachmentsInfo(ModelProductDetailInfo productDetailInfo) {
		OrderAttachmentsInfo orderAttachmentsInfo = new OrderAttachmentsInfo();
		orderAttachmentsInfo.setAccess("");
		orderAttachmentsInfo.setAttachkey("");
		orderAttachmentsInfo.setAttachname("");
		orderAttachmentsInfo.setCreateby("");
		orderAttachmentsInfo.setDemoimageurl("");
		orderAttachmentsInfo.setInputdesc("");
		orderAttachmentsInfo.setInputfileid("");
		orderAttachmentsInfo.setInputfiletype("");
		orderAttachmentsInfo.setInputtype("");
		orderAttachmentsInfo.setInputvalue("");
		orderAttachmentsInfo.setOrderid("");
		orderAttachmentsInfo.setRemark("");
		orderAttachmentsInfo.setUpdateby("");
		return orderAttachmentsInfo;
	}

}
