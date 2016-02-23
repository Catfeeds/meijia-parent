package com.simi.action.app.card;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meijia.utils.GsonUtil;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.RegexUtil;
import com.meijia.utils.SmsUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.card.CardAttend;
import com.simi.po.model.card.CardComment;
import com.simi.po.model.card.CardImgs;
import com.simi.po.model.card.CardZan;
import com.simi.po.model.card.Cards;
import com.simi.po.model.user.UserFriendReq;
import com.simi.po.model.user.UserFriends;
import com.simi.po.model.user.UserRefSec;
import com.simi.po.model.user.Users;
import com.simi.service.async.CardAsyncService;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.async.UsersAsyncService;
import com.simi.service.card.CardAttendService;
import com.simi.service.card.CardCommentService;
import com.simi.service.card.CardImgsService;
import com.simi.service.card.CardService;
import com.simi.service.card.CardZanService;
import com.simi.service.dict.DictUtil;
import com.simi.service.user.UserFriendReqService;
import com.simi.service.user.UserFriendService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UserRefSecService;
import com.simi.service.user.UsersService;
import com.simi.utils.CardUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.UserFriendSearchVo;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.card.LinkManVo;

@Controller
@RequestMapping(value = "/app/card")
public class CardController extends BaseController {
	@Autowired
	private UsersService userService;
	
	@Autowired
	private UsersAsyncService usersAsyncService;	
	
	@Autowired
	private CardImgsService cardImgsService;
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private CardAttendService cardAttendService;	
	
	@Autowired
	private CardZanService cardZanService;	
		
	@Autowired
	private CardCommentService cardCommentService;	
	
	@Autowired
	private CardAsyncService cardAsyncService;	
	
	@Autowired
	private UserMsgAsyncService userMsgAsyncService;		
		
	@Autowired
	private UserRef3rdService userRef3rdService;
	
	@Autowired
	private UserRefSecService userRefSecService;
	
	@Autowired
	private UserFriendService userFriendService;
	
	@Autowired
	private UserFriendReqService userFriendReqService;

	// 卡片提交接口
	/**
	 *  @param card_id				卡片ID,  0 表示新增
	 *  @param card_type			卡片类型 0 = 通用(保留) 1 = 会议安排 2 = 通知公告 3 = 事务提醒 4 = 面试邀约 5 = 行程规划
	 *	@param create_user_id		创建卡片的用户ID
	 *  @param user_id  			用户ID
	 *	@param attends				参与人员，格式为json [{mobile:xxxx, name:张三}, {mobile:xxxx, name:李四}]
	 *	@param service_time			卡片发生时间， 时间戳格式，精确到秒
	 *	@param service_addr			卡片涉及的地址
	 *  @param service_content  	会议/提醒内容
	 *	@param set_remind			提醒设置 
									0 = 不提醒 
									1 = 按时提醒 
									2 = 5分钟 
									3 = 15分钟 
									4 = 提前30分钟
									5 = 提前一个小时
									6 = 提前2小时
									7 = 提前6小时
									8 = 提前一天
									9 = 提前两天
	 *	@param set_now_send			立即给相关人员消息 0 = 不发送 1 = 发送
	 *	@param set_sec_do			秘书处理 0 = 否 1 = 是
	 *	@param set_sec_remarks		给秘书捎句话
	 *  @param ticket_type			票务类型 0 = 无 1 = 飞机票 2 = 火车票（保留）
	 *  @param ticket_from_city_id 	去哪城市ID
	 *  @param ticket_to_city_id	到哪城市ID
	 *
	 *  @return  CardViewVo
	 */
	@RequestMapping(value = "post_card", method = RequestMethod.POST)
	public AppResultData<Object> postCard (
			@RequestParam(value = "card_id", required = false, defaultValue = "0") Long cardId,
			@RequestParam("card_type") Short cardType,
			@RequestParam(value = "title", required = false, defaultValue = "") String title,
			@RequestParam("create_user_id") Long createUserId,
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "attends", required = false, defaultValue = "") String attends,
			@RequestParam("service_time") Long serviceTime,
			@RequestParam(value = "service_addr", required = false, defaultValue = "") String serviceAddr,
			@RequestParam("service_content") String serviceContent,
			@RequestParam("set_remind") Short setRemind,
			@RequestParam("set_now_send") Short setNowSend,
			@RequestParam("set_sec_do") Short setSecDo,
			@RequestParam(value = "set_sec_remarks", required = false, defaultValue = "") String setSecRemarks,
			@RequestParam(value = "card_extra", required = false, defaultValue = "") String cardExtra,
			@RequestParam(value = "status", required = false, defaultValue = "1") Short status
			) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		Cards record = cardService.initCards();
		if (cardId > 0L) {
			record = cardService.selectByPrimaryKey(cardId);
		}
		
		record.setCardId(cardId);
		record.setCreateUserId(createUserId);
		record.setUserId(userId);
		record.setCardType(cardType);
		record.setTitle(title);
		record.setServiceTime(serviceTime);
		record.setServiceAddr(serviceAddr);
		record.setServiceContent(serviceContent);
		record.setSetRemind(setRemind);
		record.setSetNowSend(setNowSend);
		record.setSetSecDo(setSecDo);
		record.setSetSecRemarks(setSecRemarks);
		

		//卡片额外信息 - 差旅规划
		if (cardType.equals((short) 5) && !StringUtil.isEmpty(cardExtra)) {
			Map<String, Object> cardExtraMap = GsonUtil.GsonToMaps(cardExtra);
			
			String ticketType = cardExtraMap.get("ticket_type").toString();
			Long ticketFromCityId = Long.valueOf(cardExtraMap.get("ticket_from_city_id").toString());
			Long ticketToCityId = Long.valueOf(cardExtraMap.get("ticket_to_city_id").toString());
			
			cardExtraMap.put("ticket_type", ticketType);
			cardExtraMap.put("ticket_from_city_id", ticketFromCityId);
			cardExtraMap.put("ticket_to_city_id", ticketToCityId);
			
			String ticketFromCityName = DictUtil.getCityName(ticketFromCityId);
			String ticketToCityName = DictUtil.getCityName(ticketToCityId);
			cardExtraMap.put("ticket_from_city_name", ticketFromCityName);
			cardExtraMap.put("ticket_to_city_name", ticketToCityName);
			
			cardExtra = GsonUtil.GsonString(cardExtraMap);
		}
		
		//卡片额外信息 - 通用卡片
//		if (cardType.equals((short) 0) && !StringUtil.isEmpty(cardExtra)) {
//			Map<String, Object> cardExtraMap = GsonUtil.GsonToMaps(cardExtra);
//			
//			String poiLng = cardExtraMap.get("poi_lng").toString();
//			String poiLat = cardExtraMap.get("poi_lat").toString();
//			String poiName = cardExtraMap.get("poi_Name").toString();
//			
//			cardExtra = GsonUtil.GsonString(cardExtraMap);
//		}
		
		record.setCardExtra(cardExtra);
		if (!createUserId.equals(userId)) {
			Users createUser = userService.selectByPrimaryKey(createUserId);
			if (createUser.getUserType().equals((short)1)) {
				if (status.equals(1)) status = 2;
			}
		}
		
		record.setStatus(status);
		
		Users createUser = userService.selectByPrimaryKey(createUserId);
		String userName = createUser.getName().equals("") ? createUser.getMobile() : createUser.getName();
		String remarks = "";
		if (cardId > 0L) {
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			cardService.updateByPrimaryKeySelective(record);
			
			remarks = userName + "修改了卡片信息.";
		} else {
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			cardService.insert(record);
			cardId = record.getCardId();		
			
			remarks = userName + "创建了卡片信息.";
		}
		
		//添加卡片日志
		cardAsyncService.cardLog(userId, cardId, remarks);
		
//		System.out.println(attends);
		//处理attends 转换为List<LinkManVo>的概念.
		if (!StringUtil.isEmpty(attends)) {
			Gson gson = new Gson();
			List<LinkManVo> linkManList = gson.fromJson(attends, new TypeToken<List<LinkManVo>>(){}.getType() ); 
			System.out.println(linkManList.toString());
			if (linkManList != null) {
				
				//先删除掉后再新增
				cardAttendService.deleteByCardId(cardId);
				LinkManVo item = null;
				for (int i = 0; i < linkManList.size(); i++) {
					System.out.println(linkManList.get(i).toString());
					item = linkManList.get(i);
					String mobile = item.getMobile();
					mobile = mobile.replaceAll(" ", "");  
					
					Long newUserId = 0L;
					newUserId = item.getUser_id();
					
					if (newUserId == null || newUserId.equals(0L)) {
						if (StringUtil.isEmpty(mobile)) continue;
						if (!RegexUtil.isMobile(mobile)) continue;
					}
//					mobile = RegexUtil.checkMobile(mobile);
					
					//根据手机号找出对应的userID, 如果没有则直接新增用户.
					
					Users newUser = null;
					
					if (newUserId != null && newUserId > 0L) {
						newUser = userService.selectByPrimaryKey(newUserId);
					} else {
						newUser = userService.selectByMobile(mobile);
					}
					
					if (newUser == null) {
						newUser = userService.genUser(mobile, item.getName(), (short) 3);					
						usersAsyncService.genImUser(newUser.getId());
					}
					
					newUserId = newUser.getId();
					
					newUserId = newUser.getId();
					CardAttend cardAttend = cardAttendService.initCardAttend();
					cardAttend.setCardId(cardId);
					cardAttend.setUserId(newUserId);
					cardAttend.setMobile(mobile);
					cardAttend.setName(item.getName());
					cardAttendService.insert(cardAttend);
					
					
					//相互加为好友. 异步操作
					//usersAsyncService.addFriends(u, newUser);
					
					// 如果不是好友，则自动发出好友请求.
					UserFriendSearchVo searchVo = new UserFriendSearchVo();
					searchVo.setUserId(userId);
					searchVo.setFriendId(newUserId);
					UserFriends userFriend = userFriendService.selectByIsFirend(searchVo);	
					UserFriendReq userFriendReq = userFriendReqService.selectByIsFirend(searchVo);
					if (userFriendReq != null) {
						result.setStatus(Constants.ERROR_999);
						result.setMsg(ConstantMsg.USER_IS_REQ);
						return result;
					}
					if (userFriend == null && userFriendReq == null) {
						userFriendReq = userFriendReqService.initUserFriendReq();
						userFriendReq.setUserId(newUserId);
						userFriendReq.setFriendId(userId);
						userFriendReq.setAddTime(TimeStampUtil.getNowSecond());
						userFriendReq.setUpdateTime(TimeStampUtil.getNowSecond());
						userFriendReqService.insert(userFriendReq);
					}
				}
			}
		}
		
//		CardViewVo vo = cardService.changeToCardViewVo(record);
		
		//todo 1. 如果是立即给相关人员发送消息，则需要短信模板的通知.
		cardAsyncService.cardNotification(record);

		//todo 2. 如果是秘书处理，则需要给相应的秘书发送消息.
		if (record.getSetSecDo().equals((short)1)) {
			//找出对应的秘书信息
			UserRefSec userRefSec = userRefSecService.selectByUserId(userId);
			if (userRefSec != null) {
				Users secUser = userService.selectByPrimaryKey(userRefSec.getSecId());
				String secMobile = secUser.getMobile();
				if (RegexUtil.isMobile(secMobile)) {
					String[] content = new String[] { userName, CardUtil.getCardTypeName(record.getCardType()),  ""};
					HashMap<String, String> sendSmsResult = SmsUtil.SendSms(secMobile, "44658", content);
				}
			}
		}
		
		//生成卡片消息
		userMsgAsyncService.newCardMsg(cardId);

//		result.setData(vo);
		return result;
	}		
	
	// 卡片点赞接口
	/**
	 *  @param card_id				卡片ID,  0 表示新增
	 *  @param user_id  			用户ID
	 *
	 *  @return  CardViewVo
	 */
	@RequestMapping(value = "post_zan", method = RequestMethod.POST)
	public AppResultData<Object> postZan(
			@RequestParam("card_id") Long cardId,
			@RequestParam("user_id") Long userId
			) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		CardSearchVo searchVo = new CardSearchVo();
		searchVo.setCardId(cardId);
		searchVo.setUserId(userId);
		CardZan cardZan = cardZanService.selectBySearchCardVo(searchVo);
		
		if (cardZan == null) {
			cardZan = cardZanService.initCardZan();
			cardZan.setCardId(cardId);
			cardZan.setUserId(userId);
			cardZanService.insert(cardZan);
		}
		
		int totalZan = cardZanService.totalByCardId(cardId);
		result.setData(totalZan);
		
		return result;
	}
	
	// 卡片评论接口
	/**
	 *  @param card_id				卡片ID,  0 表示新增
	 *  @param user_id  			用户ID
	 *
	 *  @return  CardViewVo
	 */
	@RequestMapping(value = "post_comment", method = RequestMethod.POST)
	public AppResultData<Object> postComment(
			@RequestParam("card_id") Long cardId,
			@RequestParam("user_id") Long userId,
			@RequestParam("comment") String comment
			) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		CardSearchVo searchVo = new CardSearchVo();
		searchVo.setCardId(cardId);
		searchVo.setUserId(userId);
		CardComment cardComment = cardCommentService.initCardComment();
		cardComment.setCardId(cardId);
		cardComment.setUserId(userId);
		cardComment.setComment(comment);
		cardCommentService.insert(cardComment);
		return result;
	}	
	
	// 卡片取消接口
	/**
	 *  @param card_id				卡片ID,  0 表示新增
	 *  @param user_id  			用户ID
	 *
	 *  @return  CardViewVo
	 */
	@RequestMapping(value = "card_cancel", method = RequestMethod.POST)
	public AppResultData<Object> cardCancel(
			@RequestParam("card_id") Long cardId,
			@RequestParam("user_id") Long userId,
			@RequestParam("status") Short status,
			@RequestParam(value = "remarks", required = false, defaultValue = "") String remarks
			) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		Cards record = cardService.selectByPrimaryKey(cardId);
		if (record == null) return result;
		
		if (!record.getCreateUserId().equals(userId)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("只有创建任务卡片才能取消卡片。");
			return result;			
		}
		
		if (record.getStatus().equals((short)0)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("卡片已经取消过了.");
			return result;			
		}
		
		Long serviceTime = record.getServiceTime();
		if (serviceTime < TimeStampUtil.getNowSecond()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("卡片服务时间已到,不能取消.");
			return result;		
		}
		
		
		record.setStatus(status);
		record.setUpdateTime(TimeStampUtil.getNowSecond());
		cardService.updateByPrimaryKeySelective(record);
		
		//添加卡片log
		//添加卡片日志
		cardAsyncService.cardLog(userId, cardId, CardUtil.getStatusName(status));

		return result;
	}	
	
	// 秘书处理卡片接口
	/**
	 *  @param card_id				卡片ID,  0 表示新增
	 *  @param user_id  			用户ID
	 *
	 *  @return  CardViewVo
	 */
	@RequestMapping(value = "sec_do", method = RequestMethod.POST)
	public AppResultData<Object> secDo(
			@RequestParam("card_id") Long cardId,
			@RequestParam("sec_id") Long secId,
			@RequestParam("status") Short status,
			@RequestParam(value = "sec_remarks", required = false, defaultValue = "") String secRemarks
			) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.selectByPrimaryKey(secId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		Cards record = cardService.selectByPrimaryKey(cardId);
		if (record == null) return result;
		
		record.setStatus(status);
		record.setSecRemarks(secRemarks);
		cardService.updateByPrimaryKeySelective(record);
		
		//添加卡片log
		//添加卡片日志
		cardAsyncService.cardLog(secId, cardId, CardUtil.getStatusName(status));
		return result;
	}	
	/**
	 * 卡片图片上传接口
	 * @param request
	 * @param userId
	 * @param cardId
	 * @param files
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "post_user_img", method = RequestMethod.POST)
	public AppResultData<Object> userImg(HttpServletRequest request,
			@RequestParam("user_id") Long userId,
			@RequestParam("card_id")Long cardId,
			@RequestParam("file") MultipartFile[] files) throws IOException {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		Cards cards = cardService.selectByPrimaryKey(cardId);
        //是否有对应的卡片，没有则返回999
		if (cards == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.CADR_NOT_EXIST_MG);
			return result;
		}
		List<HashMap<String, String>> imgs = new ArrayList<HashMap<String, String>>();

		if (files != null && files.length > 0) {

			for (int i = 0; i < files.length; i++) {
				String url = Constants.IMG_SERVER_HOST + "/upload/";
				String fileName = files[i].getOriginalFilename();
				String fileType = fileName
						.substring(fileName.lastIndexOf(".") + 1);
				fileType = fileType.toLowerCase();
				String sendResult = ImgServerUtil.sendPostBytes(url,
						files[i].getBytes(), fileType);

				ObjectMapper mapper = new ObjectMapper();

				HashMap<String, Object> o = mapper.readValue(sendResult, HashMap.class);

				String ret = o.get("ret").toString();

				HashMap<String, String> info = (HashMap<String, String>) o.get("info");

				String imgUrl = Constants.IMG_SERVER_HOST + "/" + info.get("md5").toString();

				CardImgs cardImg = new CardImgs();
				cardImg.setImgId(0L);
				cardImg.setImgUrl(imgUrl);
				cardImg.setUserId(userId);
				cardImg.setAddTime(TimeStampUtil.getNowSecond());
				cardImgsService.insert(cardImg);

				HashMap<String, String> img = new HashMap<String, String>();
				img.put("user_id", userId.toString());
				img.put("img", imgUrl);
				img.put("img_100x100",
						ImgServerUtil.getImgSize(imgUrl, "100", "100"));
				img.put("img_200x200",
						ImgServerUtil.getImgSize(imgUrl, "200", "200"));

				imgs.add(img);
			}
		}

		result.setData(imgs);
		return result;
	}

}
