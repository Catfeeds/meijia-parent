package com.simi.common;

public class ConstantMsg {


	//服务类型 1 = 做饭 2 = 洗衣 3 = 家电清洗 4 = 保洁 5 = 擦玻璃 6 = 管道疏通 7 = 新居开荒
	public static String SERVICE_TYPE_1_DINNER ="做饭";
	public static String SERVICE_TYPE_2_WASH ="洗衣";
	public static String SERVICE_TYPE_3_TEL ="家电清洗";
	public static String SERVICE_TYPE_4_CLEAN="保洁";
	public static String SERVICE_TYPE_5_BOLI ="擦玻璃";
	public static String SERVICE_TYPE_6_GRAP ="管道疏通";
	public static String SERVICE_TYPE_7_HOUSE ="新居开荒";

	public static String SUCCESS_0_MSG = "ok";

	public static String ERROR_100_MSG = "服务器错误";
	public static String ERROR_101_MSG = "缺失必选参数 (%s)";
	public static String ERROR_102_MSG = "参数值非法，需为 (%s)，实际为 (%s)";
	public static String ERROR_103_MSG = "手机号重复，修改失败";
	public static String ERROR_104_MSG = "手机号为空，修改失败";

	//验证码错误提示
	public static String ERROR_999_MSG_1 = "秘书不存在";
	public static String ERROR_999_MSG_2 = "验证码错误,请重新输入";
	public static String ERROR_999_MSG_3 = "该手机号未曾获取过验证码";
	public static String ERROR_999_MSG_4 = "验证码超时,请重新获取验证码";
	public static String ERROR_999_MSG_5 = "您的余额不足，请及时充值哦!";
	public static String ERROR_999_MSG_6 = "抱歉，你之前添加过该地址了。";
	public static String ERROR_999_MSG_8 = "验证码超时，请重新获取验证码";

	public static String ERROR_999_MSG_7 = "您的服务即将开始，现在无法取消订单。有问题请与我们的客服人员联系：）";
	public static String ERROR_999_MSG_10 = "查询没有数据";

	//优惠券错误提示
	public static String COUPON_INVALID_MSG = "兑换码不正确哦！";
	public static String COUPON_EXP_TIME_MSG = "要兑换的优惠券已过期，换一个吧。";
	public static String COUPON_IS_USED_MSG = "优惠券序列号已被兑换，换一个吧。";
	public static String COUPON_SERVICE_TYPE_INVALID_MSG = "您选择的优惠券类型不能支付本次服务，请换一张哦！";

	//意见反馈状态
	public static String FEED_BACK_SUCCESS="意见反馈成功";
	public static String FEED_BACK_FALSE = "意见反馈失败";

	//用户地址不存在
	public static String USER_NOT_EXIST_MG = "用户不存在";
	
	//已经是好友，不需要重复添加
	public static String USER_IS_FRIEND = "已经是好友，不需要重复添加";
	
	//已经申请过对方为好友
	public static String USER_IS_REQ = "您已申请过此用户为好友";
	
	//服务人员不存在
	public static String PARTNER_NOT_EXIST_MG = "服务人员不存在";
	
	//用户未登陆
	public static String USER_NOT_LOGIN ="你还未登陆，请先进行登陆，再进行此操作";
	
	//服务商不存在
	public static String PARTNERS_NOT_EXIST_MG = "本业务只向服务商提供，您还未注册店铺，是否要注册店铺？";
	
	//您所在店铺还未认证通过，请耐心等待或致电4001691615查询
	public static String PARTNERS_NOT_THROUGH = " 您申请的店铺还未审核通过，我们将会尽快处理，您也可直接致电4001691615问询，谢谢您的关注！";
	
	//公司不存在
	public static String XCOMPANY_NOT_EXIST = "公司不存在";
	
	//您是用户，不允许注册商品。
	public static String USERS_NOT_REGIETER_STORE = "本业务只向服务商提供，您已注册过用户，是否要注册店铺？";
	
	//您是秘书，不允许注册商品。
	public static String SEC_NOT_REGIETER_STORE = "您已经是秘书,若想要继续，请注册店铺！";
		
	
	//公司名称已存在
	public static String XCOMPANY_NAME_EXIST = "公司名称已存在";
	
	//卡片不存在
	public static String CADR_NOT_EXIST_MG = "卡片不存在";
	//用户已存在
	public static String USER_EXIST_MG = "用户已存在";
	
	//手机号已存在
	public static String MOBILE_EXIST_MG = "手机号已注册";
	
	//秘书已存在
	public static String SEC_EXIST_MG = "秘书已存在,不需要重新注册！";
	
	//用户已注册是否注册秘书
	public static String USER_EXIST_SEC_MG = "用户已注册是否注册秘书";
	
	//用户地址不存在
	public static String USER_ADDR_NOT_EXIST_MG = "用户地址不存在";

	public static String ORDER_PAY_WAIT_MSG = "支付WAIT_BUYER_PAY,支付中间状态！";
	//支付的提示信息
	public static String ORDER_PAY_NOT_SUCCESS_MSG = "支付不成功";

	//积分兑换的提示
	public static String USER_SOCRE_NOT_ENOUGH_MSG = "积分不足,无法兑换";

	//分享获得积分的提示
	public static String USER_SOCRE_SHARE_FRIENDS_MSG = "已做过分享,请换个途径分享！";

	//用户地址不存在
	public static String ORDER_NO_NOT_EXIST_MG = "订单号不存在";

	//订单已经取消
	public static String ORDER_IS_CANCELED_MG = "订单号已经取消";

	//用户地址不存在
	public static String IM_USER_NOT_EXIST_MG = "指定的IM通讯账号不存在";

	//手机号不合法
	public static String MIBILE_IS_INVALID_MG = "手机号不正确";
	
	//第三方登录提示未绑定
	public static String USER_REF_3RD_NO_BIND = "账号未绑定,请先绑定账号！";
}
