package com.simi.common;

import java.math.BigDecimal;

public class Constants {

	public static String URL_ENCODE = "UTF-8";
	public static int PAGE_MAX_NUMBER = 10;
	/**
	 * 验证码最大值
	 */
	public static int CHECK_CODE_MAX_LENGTH = 999999;

	//'0' COMMENT '用户来源 0 = APP  1 = 微网站  2 = 管理后台'
	public static short USER_APP = 0;
	public static short USER_NET = 1;
	public static short USER_BACK = 2;

	//充值卡类型:1,无忧管家;2,快乐管家;3,超级管家
	public static long DICT_CARD_TYPE_1 = 1;
	public static long DICT_CARD_TYPE_2 = 2;
	public static long DICT_CARD_TYPE_3 = 3;

	//是否使用 0 = 未使用 1= 已使用
	public static short IS_USER_0 = 0;
	public static short IS_USER_1 = 1;

	public static short IS_USER_PROMOTION_1 = 1;
	public static short IS_USER_PROMOTION_0 = 0;

	//是否包含保洁用品 0 = 不包含 1 = 包含'
	public static int CLEAN_TOOLS_0 = 0;
	public static int CLEAN_TOOLS_1 = 1;
	public static BigDecimal CLEAN_TOOLS_PRICE = new BigDecimal(30);;

	//地址默认 默认地址 1 = 默认  0 = 不默认
	public static int ADDRESS_DEFAULT_1 = 1;
	public static int ADDRESS_DEFAULT_NOT_0 = 0;

	//0 = 已取消 1 = 待支付 2 = 已支付 3 = 待服务 4 =  即将服务 5 = 待评价 6 = 已完成  7 = 已关闭
	public static short ORDER_STATS_0_CANCLEED = 0;
	public static short ORDER_STATS_1_PAYING = 1;
	public static short ORDER_STATS_2_PAID = 2;
	public static short ORDER_STATS_3_SERVICE = 3;
	public static short ORDER_STATS_4_SERVICEING = 4;
	public static short ORDER_STATS_5_REMARK = 5;
	public static short ORDER_STATS_6_COMPLETE = 6;
	public static short ORDER_STATS_7_CLOSE = 7;

	//订单评价 0=好  1=一般 2=差
	public static short ORDER_RATE_GOOD = 0;
	public static short ORDER_RATE_GENERAL = 1;
	public static short ORDER_RATE_BAD = 2;

	//服务类型 1 = 做饭 2 = 洗衣 3 = 家电清洗 4 = 保洁 5 = 擦玻璃 6 = 管道疏通 7 = 新居开荒
	public static short SERVICE_TYPE_1_DINNER = 1;
	public static short SERVICE_TYPE_2_WASH = 2;
	public static short SERVICE_TYPE_3_TEL = 3;
	public static short SERVICE_TYPE_4_CLEAN= 4;
	public static short SERVICE_TYPE_5_BOLI = 5;
	public static short SERVICE_TYPE_6_GRAP = 6;
	public static short SERVICE_TYPE_7_HOUSE = 7;

	//
	public static double BACK_MONEY_FEE = 0.0;

	//1 = 支付成功 2 = 退款成功
	public static short PAY_SUCCESS = 1;
	public static short BACK_SUCCESS = 2;

	//是否能取消订单. 0 = 不能取消 1 = 能取消
	public static short ORDER_CANCLE_NO = 0;
	public static short ORDER_CANCLE_YES = 1;

	public static int RATE_CORE = 10;
	public static int SHARE_CORE = 1;

	//0 = 发送失败 1 = 发送成功
	public static short SMS_SUCCESS= 0;
	public static short SMS_FAIL= 0;
	public static String SMS_SUCCESS_CODE= "000000";
	public static String SMS_STATUS_CODE= "statusCode";
	public static String SMS_STATUS_MSG= "msg";

	public static String PAY_SUCCESS_SMS_TEMPLE_ID= "9282";
	public static String GET_CODE_TEMPLE_ID= "8429";
	public static String GET_CODE_REMIND_ID= "10923";
	public static String GET_CODE_MAX_VALID= "30";//短信有效时间
	public static String NOTICE_CUSTOMER_Message_ID= "9280";
	public static String NOTICE_STAFF__Message_ID= "15284";

	public static String CANCEL_ORDER_SATUS= "cancel";

	//支付方式：-1未执行付款, 0 = 余额支付 1 = 支付宝 2 = 微信支付 3 = 智慧支付(保留,暂不开发)
	//4 = 上门刷卡（保留，暂不开发） 5 = 优惠券兑换
	public static Short PAY_TYPE_1_1 = -1;
	public static Short PAY_TYPE_0 = 0;
	public static Short PAY_TYPE_1 = 1;
	public static Short PAY_TYPE_2 = 2;
	public static Short PAY_TYPE_3 = 3;
	public static Short PAY_TYPE_4 = 4;
	public static short PAY_TYPE_5 = 5;

	public static int SUCCESS_0 = 0;
	public static int ERROR_999 = 999;
	public static int ERROR_100 = 100;
	public static int ERROR_101 = 101;
	public static int ERROR_102 = 102;
	public static int ERROR_103 = 103;
	public static int ERROR_104 = 104;

	//0 = 未支付 1 = 已支付
	public static Short ORDER_STATUS_0 = 0;
	public static Short ORDER_STATUS_1 = 1;

	//服务类型0=做饭1=洗衣2=家电清洗3=保洁4=擦玻璃5=管道疏通6=新居开荒

	//客服电话号码
	public static String SERVICE_CALL = "4001691615";
	//管家电话号码
	public static String GUANJIA_CALL = "4001691615";

	//获取积分的操作定义 0 = 订单获得积分 1 = 订单使用积分 2 = 分享获得积分 3= 评价获得积分 4=兑换使用积分
	public static Short ACTION_ORDRE = 0;
	public static Short ACTION_ORDER_USED = 1;
	public static Short ACTION_SHARE = 2;
	public static Short ACTION_ORDER_RATE = 3;
	public static Short ACTION_CONVERT_SCORE=4;

	public static BigDecimal PRICE_HOUR = new BigDecimal(0);
	public static BigDecimal PRICE_HOUR_DIS_COUNT = new BigDecimal(0);

	//积分获得和使用  0 = 获取  1= 使用
	public static Short CONSUME_SCORE_GET = 0;
	public static Short CONSUME_SCORE_USED = 1;

	//指定发送人员的邮箱
	public static String TO_EMAIL = "Kerry@zhirunjia.com";

	//是否已经返还积分 0 = 未返还 1 = 已返
	public static Short RETURN_SCORE_YES = 1;
	public static Short RETURN_SCORE_NO = 0;

	//消费类型
	public static Short ORDER_TYPE_0 = 0;  //订单支付
	public static Short ORDER_TYPE_1 = 1; //购买充值卡
	public static Short ORDER_TYPE_2 = 2; //购买管家卡
	public static Short ORDER_TYPE_3 = 3; //订单退款

	//优惠券过期时间
	public static Long COUPON_VALID_FOREVER = 0L;

	//优惠券类型 - 订单支付
	public static Short COUPON_TYPE_0 = 0;

	//优惠券类型 - 充值卡充值
	public static Short COUPON_TYPE_1 = 1;

	//优惠券类型 - 活动
	public static Short COUPON_TYPE_2 = 2;

	//派工状态, 0 = 无效
	public static Short DISPATCH_STAUTS_0 = 0;

	//派工状态, 1 = 有效
	public static Short DISPATCH_STAUTS_1 = 0;

	//IM服务提供商
	public static String IM_PROVIDE = "huanxin";

	//IM机器人账号和密码
	public static String ROBOT_FEMALE_USERNAME = "robot-amei";
	public static String ROBOT_FEMALE_NICKNAME = "阿美";
	public static String ROBOT_MALE_USERNAME = "robot-afu";
	public static String ROBOT_MALE_NICKNAME = "阿福";

	//新用户注册赠送优惠劵码
	public static String NEW_USER_COUPON_CARD_PASSWORD = "I0RBCOJL";
	//积分兑换优惠券密码
	public static String SCORE_CONVERT_COUPON_CARD_PASSWORD="CSNINL8B";





}
