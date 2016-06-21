package com.meijia.utils;

import com.meijia.utils.vendors.IdWorker;

public class OrderNoUtil {

	/*
	 * 生成服务订单号
	 * @return Long
	 */
	@SuppressWarnings("null")
	public static Long  genOrderNo() {
		final IdWorker idWorker1 = new IdWorker(0, 0);
		return idWorker1.nextId();
	}

	/*
	 * 生成充值卡订单号
	 * @return Long
	 */
	@SuppressWarnings("null")
	public static Long  getOrderCardNo() {
		final IdWorker idWorker2 = new IdWorker(1, 0);
		return idWorker2.nextId();
	}

	/*
	 * 生成管家卡订单号
	 * @return Long
	 */
	@SuppressWarnings("null")
	public static Long  getOrderSeniorNo() {
		final IdWorker idWorker3 = new IdWorker(2, 0);
		return idWorker3.nextId();
	}
	
	/**
	 * 生成唯一文件名，参考的就是订单号
	 * @return
	 */
	public static Long  getUploadFileName() {
		final IdWorker idWorker3 = new IdWorker(3, 0);
		return idWorker3.nextId();
	}	

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Long orderNo = OrderNoUtil.genOrderNo();
		Long orderCardNo = OrderNoUtil.getOrderCardNo();
		Long orderSeniorNo = OrderNoUtil.getOrderSeniorNo();

		System.out.println("orderNo = " + orderNo);
		System.out.println("orderCardNo = " + orderCardNo);
		System.out.println("orderSeniorNo = " + orderSeniorNo);
	}
}
