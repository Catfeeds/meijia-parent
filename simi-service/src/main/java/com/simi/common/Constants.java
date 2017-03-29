package com.simi.common;

public class Constants {

	public static String URL_ENCODE = "UTF-8";
	public static int PAGE_MAX_NUMBER = 10;
	/**
	 * 验证码最大值
	 */
	public static int CHECK_CODE_MAX_LENGTH = 999999;
	
	//用户类型  0 = 普通用户  1= 客服 2 = 服务商
	public static short USER_TYPE_0 = 0;
	public static short USER_TYPE_1 = 1;
	public static short USER_TYPE_2 = 2;


	//'0' COMMENT '用户来源 0 = APP  1 = 微网站  2 = 管理后台 3 = 云平台'
	public static short USER_APP = 0;
	public static short USER_WWZ = 1;
	public static short USER_BACK = 2;
	public static short USER_XCOULD = 3;

	//是否使用 0 = 未使用 1= 已使用
	public static short IS_USER_0 = 0;
	public static short IS_USER_1 = 1;

	//地址默认 默认地址 1 = 默认  0 = 不默认
	public static int ADDRESS_DEFAULT_1 = 1;
	public static int ADDRESS_DEFAULT_NOT_0 = 0;
	

	//0 = 已关闭 1 = 待支付 2 = 已支付 3 = 处理中 7 = 待评价 9 = 已完成
	public static short ORDER_STATUS_0_CANCEL = 0;	
	public static short ORDER_STATUS_1_PAY_WAIT = 1;
	public static short ORDER_STATUS_2_PAY_DONE = 2;
	public static short ORDER_STATUS_3_PROCESSING = 3;
	public static short ORDER_STATUS_7_RATE_WAIT = 7;	
	public static short ORDER_STATUS_8_COMPLETE = 8;
	public static short ORDER_STATUS_9_CLOSE = 9;
	
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
	public static String GET_CODE_TEMPLE_ID= "109486";
	public static String GET_CODE_REMIND_ID= "10923";
	public static String GET_CODE_MAX_VALID= "30";//短信有效时间
	public static String NOTICE_CUSTOMER_Message_ID= "9280";
	public static String NOTICE_STAFF__Message_ID= "15284";

	public static String CANCEL_ORDER_SATUS= "cancel";

	
	//短信模板定义
	public static String SEC_REGISTER_ID= "109487";//用户注册秘书后给运营人员发短信
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
	public static String IMG_SERVER_HOST = "http://img.bolohr.com";
	
	//默认头像
	public static String DEFAULT_HEAD_IMG = "http://img.bolohr.com/6bf03aff8873e76bfa11e9a24f4df4c4";

	//默认头像底图
	public static String DEFAULT_HEAD_IMG_BACK = "http://img.bolohr.com/85ebd46f40d90977ee01ead3e71bd6fa";
	
	//菠萝人事logo
	public static String DEFAULT_LOGO = "http://img.bolohr.com/314a3ecee7f653526b1dcc661c10142c?p=0";
	
	
	/*
	 *   点击次数 -- linkType 字段 可选值
	 */
	public static String TOTAL_HIT_LINK_TYPE_DICT_AD = "dict_ad";	//广告位 点击
	public static String TOTAL_HIT_LINK_TYPE_SERVICE_PRICE = "service_price"; //服务报价类点击.
	
	/*
	 *  平台   推送  用户 类型
	 */
	public static Short OA_PUSH_USER_TYPE_0 = 0;	//普通用户,最近一个月登录过的
	public static Short OA_PUSH_USER_TYPE_1 = 1;	//秘书
	public static Short OA_PUSH_USER_TYPE_2 = 2;	//服务商
	public static Short OA_PUSH_USER_TYPE_3 = 3;	//全部用户
	
	//积分和经验值返回定义
	public static int SCORE_USER_REG = 100;			//用户注册
	public static int SCORE_DAY_SIGN = 30;			//签到
	public static int SCORE_CARD_CREATE = 10;		//创建卡片
	public static int SCORE_LEAVE = 1;				//请假申请
	public static int SCORE_CHECKIN = 1;			//云考勤
	public static int SCORE_COMPANY_CREATE = 300;	//创建公司/团队
	public static int SCORE_QA_COMMENT = 5;			//问题回答
	
	
	/*
	 *  imgs 表  link_type（图片类型） 字段 整理
	 */
	
	//身份证图片 正面
	public static String IMGS_LINK_TYPE_IDCARD_FRONT = "idCardFront";
	
	//身份证图片 背面
	public static String IMGS_LINK_TYPE_IDCARD_BACK = "idCardBack";
	
	//毕业证照片 
	public static String IMGS_LINK_TYPE_DEGREE = "degree";
	
	//建表时,规定的几种 图片类型
	public static String IMGS_LINK_TYPE_EXPRESS = "express";	//快递
	public static String IMGS_LINK_TYPE_FEED = "feed";			//动态
	public static String IMGS_LINK_TYPE_USER = "user";			//用户
	public static String IMGS_LINK_TYPE_CARD = "card";			//卡片
	
	
	/*
	 *  xcompany_setting 表  setting_type 字段
	 */
	public static String SETTING_TYPE_INSURANCE = "insurance";	//社保公积金基数分项配置
	public static String SETTING_TYPE_INSURANCE_BASE = "insurance_base"; //社保公积金基数配置
	public static String SETTING_TYPE_TAX_PERSION = "tax_persion"; //个人所得税率表.
	public static String SETTING_TYPE_TAX_YEAR_AWARD = "tax_year_award"; //年终奖所有配置.
	public static String SETTING_TYPE_TAX_PAY = "tax_pay"; //劳务报酬所得.
	
	public static String SETTING_CHICKIN_NET = "checkin-net"; //考勤地点设置
}
