package com.simi.common;

public class Constants {

	public static String URL_ENCODE = "UTF-8";
	public static int PAGE_MAX_NUMBER = 10;
	/**
	 * 验证码最大值
	 */
	public static int CHECK_CODE_MAX_LENGTH = 999999;

	//'0' COMMENT '用户来源 0 = APP  1 = 微网站  2 = 管理后台'
	public static short USER_APP = 0;
	public static short USER_WWZ = 1;
	public static short USER_BACK = 2;

	//是否使用 0 = 未使用 1= 已使用
	public static short IS_USER_0 = 0;
	public static short IS_USER_1 = 1;

	//地址默认 默认地址 1 = 默认  0 = 不默认
	public static int ADDRESS_DEFAULT_1 = 1;
	public static int ADDRESS_DEFAULT_NOT_0 = 0;
	
	//0 = 已关闭 1 = 待确认 2 = 已确认 3 = 待支付 4 = 已支付
	public static short ORDER_STATUS_0_CLOSE = 0;	
	public static short ORDER_STATUS_1_PAY_WAIT = 1;
	public static short ORDER_STATUS_2_PAY_DONE = 2;
	public static short ORDER_STATUS_3_PROCESSING = 3;
	public static short ORDER_STATUS_7_RATE_WAIT = 7;	
	public static short ORDER_STATUS_9_COMPLETE = 9;
	
	//订单评价 5颗星  1 - 5

	//1 = 支付成功 2 = 退款成功
	public static short PAY_SUCCESS = 1;
	public static short BACK_SUCCESS = 2;

	//0 = 发送失败 1 = 发送成功
	public static short SMS_SUCCESS= 0;
	public static short SMS_FAIL= 0;
	public static String SMS_SUCCESS_CODE= "000000";
	public static String SMS_STATUS_CODE= "statusCode";
	public static String SMS_STATUS_MSG= "msg";

	public static String PAY_SUCCESS_SMS_TEMPLE_ID= "9282";
	//public static String GET_CODE_TEMPLE_ID= "8429";
	public static String GET_CODE_TEMPLE_ID= "44375";
	public static String GET_CODE_REMIND_ID= "10923";
	public static String GET_CODE_MAX_VALID= "30";//短信有效时间
	public static String NOTICE_CUSTOMER_Message_ID= "9280";
	public static String NOTICE_STAFF__Message_ID= "15284";

	public static String CANCEL_ORDER_SATUS= "cancel";

	
	//短信模板定义
	public static String SEC_REGISTER_ID= "44653";//用户注册秘书后给运营人员发短信
	public static String SEC_REGISTER_USER_ID= "44655";//秘书审批后给用户发短信
	public static String SEC_TWO_MINUTE= "44659";//秘书2分钟未接受卡片--给秘书发短信提醒
	public static String SEC_THIRTY_MINUTE= "44660";//秘书30分钟未接受卡片--给运营人员发短信提醒
	public static String USER_COUPON_EXPTIME= "48146";//  优惠劵即将过期通知， 如果优惠劵离过期还有一天，则给用户发送短信.
	public static String USER_ORDER_MORE_1HOUR= "48136";//订单超时未支付通知用户
	//支付方式： 0 = 余额支付 1 = 支付宝 2 = 微信支付 3 = 智慧支付(保留,暂不开发)
	//4 = 上门刷卡（保留，暂不开发） 5 = 优惠券兑换
	public static Short PAY_TYPE_0 = 0;
	public static Short PAY_TYPE_1 = 1;
	public static Short PAY_TYPE_2 = 2;
	public static Short PAY_TYPE_3 = 3;
	public static Short PAY_TYPE_4 = 4;
	public static short PAY_TYPE_5 = 5;

	public static int SUCCESS_0 = 0;
	public static int ERROR_999 = 999;
	public static int ERROR_100 = 100;


	//0 = 未支付 1 = 已支付
	public static Short PAY_STATUS_0 = 0;
	public static Short PAY_STATUS_1 = 1;

	//客服电话号码
	public static String SERVICE_CALL = "4001691615";

	//获取积分的操作定义 0 = 订单获得积分 1 = 订单使用积分 2 = 分享获得积分 3= 评价获得积分 4=兑换使用积分
	public static Short ACTION_ORDRE = 0;
	public static Short ACTION_ORDER_USED = 1;
	public static Short ACTION_SHARE = 2;
	public static Short ACTION_ORDER_RATE = 3;
	public static Short ACTION_CONVERT_SCORE=4;

	//积分获得和使用  0 = 获取  1= 使用
	public static Short CONSUME_SCORE_GET = 0;
	public static Short CONSUME_SCORE_USED = 1;

	//是否已经返还积分 0 = 未返还 1 = 已返
	public static Short RETURN_SCORE_YES = 1;
	public static Short RETURN_SCORE_NO = 0;

	//消费类型
	public static Short ORDER_TYPE_0 = 0;  //订单支付
	public static Short ORDER_TYPE_1 = 1; //购买充值卡
	public static Short ORDER_TYPE_2 = 2; //购买私秘卡
	public static Short ORDER_TYPE_3 = 3; //订单退款

	//优惠券类型 - 订单支付
	public static Short COUPON_TYPE_0 = 0;

	//优惠券类型 - 充值卡充值
	public static Short COUPON_TYPE_1 = 1;

	//优惠券类型 - 活动
	public static Short COUPON_TYPE_2 = 2;

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
	
	//图片服务器地址
	public static String IMG_SERVER_HOST = "http://img.51xingzheng.cn";

}
