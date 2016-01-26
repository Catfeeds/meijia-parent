package com.simi.action.app.feed;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meijia.utils.ImgServerUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.feed.FeedComment;
import com.simi.po.model.feed.FeedImgs;
import com.simi.po.model.feed.FeedZan;
import com.simi.po.model.feed.Feeds;
import com.simi.po.model.user.Users;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.feed.FeedCommentService;
import com.simi.service.feed.FeedService;
import com.simi.service.feed.FeedZanService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.feed.FeedSearchVo;
import com.simi.service.feed.FeedImgsService;

;
@Controller
@RequestMapping(value = "/app/feed")
public class FeedController extends BaseController {
	@Autowired
	private UsersService userService;

	@Autowired
	private FeedImgsService feedImgsService;

	@Autowired
	private FeedService feedService;

	@Autowired
	private FeedZanService feedZanService;

	@Autowired
	private FeedCommentService feedCommentService;
	
	@Autowired
	private UserMsgAsyncService userMsgAsyncService;		

	// 动态添加接口
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "post_feed", method = RequestMethod.POST)
	public AppResultData<Object> postFeed(
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "title", required = false, defaultValue = "") String title, 
			@RequestParam(value = "feed_imgs", required = false) MultipartFile[] feedImgs, 
			@RequestParam(value = "lat", required = false, defaultValue = "") String lat,
			@RequestParam(value = "lng", required = false, defaultValue = "") String lng,
			@RequestParam(value = "poi_name", required = false, defaultValue = "") String poiName,
			@RequestParam(value = "feed_extra", required = false, defaultValue = "") String feedExtra) throws IOException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		Feeds record = feedService.initFeeds();
		record.setUserId(userId);
		record.setTitle(title);
		record.setLat(lat);
		record.setLng(lng);
		record.setPoiName(poiName);
		record.setFeedExtra(feedExtra);

		feedService.insert(record);
		Long fid = record.getFid();

		// 处理图片问题

		if (feedImgs != null && feedImgs.length > 0) {

			for (int i = 0; i < feedImgs.length; i++) {
				String url = Constants.IMG_SERVER_HOST + "/upload/";
				String fileName = feedImgs[i].getOriginalFilename();
				String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
				fileType = fileType.toLowerCase();
				String sendResult = ImgServerUtil.sendPostBytes(url, feedImgs[i].getBytes(), fileType);

				ObjectMapper mapper = new ObjectMapper();

				HashMap<String, Object> o = mapper.readValue(sendResult, HashMap.class);

				String ret = o.get("ret").toString();

				HashMap<String, String> info = (HashMap<String, String>) o.get("info");

				String imgUrl = Constants.IMG_SERVER_HOST + "/" + info.get("md5").toString();
				String imgMiddel = ImgServerUtil.getImgSize(imgUrl, "600", "600");
				String imgSmall = ImgServerUtil.getImgSize(imgUrl, "600", "300");
				FeedImgs feedImg = feedImgsService.initFeedImgs();
				feedImg.setFid(fid);
				feedImg.setUserId(userId);
				feedImg.setImgUrl(imgUrl);
				feedImg.setImgMiddle(imgMiddel);
				feedImg.setImgSmall(imgSmall);

				feedImgsService.insert(feedImg);

			}
		}
		
		//生成动态消息
		userMsgAsyncService.newFeedMsg(fid);
		
		result.setData(fid);

		return result;
	}
	
	
	// 动态图片上传接口
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "post_feed_imgs", method = RequestMethod.POST)
	public AppResultData<Object> postFeedImg(
			@RequestParam("fid") Long fid, @RequestParam("user_id") Long userId,
			@RequestParam("feed_imgs") MultipartFile[] feedImgs
		) throws IOException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		Feeds record = feedService.selectByPrimaryKey(fid);
		
		if (record == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("动态不存在");
			return result;
		}
		
		if (!userId.equals(record.getUserId())) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("只能修改自己发布的动态");
			return result;
		}
		
		// 处理图片问题

		if (feedImgs != null && feedImgs.length > 0) {

			for (int i = 0; i < feedImgs.length; i++) {
				String url = Constants.IMG_SERVER_HOST + "/upload/";
				String fileName = feedImgs[i].getOriginalFilename();
				String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
				fileType = fileType.toLowerCase();
				String sendResult = ImgServerUtil.sendPostBytes(url, feedImgs[i].getBytes(), fileType);

				ObjectMapper mapper = new ObjectMapper();

				HashMap<String, Object> o = mapper.readValue(sendResult, HashMap.class);

				String ret = o.get("ret").toString();

				HashMap<String, String> info = (HashMap<String, String>) o.get("info");

				String imgUrl = Constants.IMG_SERVER_HOST + "/" + info.get("md5").toString();
				String imgMiddel = ImgServerUtil.getImgSize(imgUrl, "600", "600");
				String imgSmall = ImgServerUtil.getImgSize(imgUrl, "600", "600");
				FeedImgs feedImg = feedImgsService.initFeedImgs();
				feedImg.setFid(fid);
				feedImg.setUserId(userId);
				feedImg.setImgUrl(imgUrl);
				feedImg.setImgMiddle(imgMiddel);
				feedImg.setImgSmall(imgSmall);

				feedImgsService.insert(feedImg);

			}
		}

		return result;
	}	

	// 卡片点赞接口
	/**
	 * @param fid
	 *            动态ID
	 * @param user_id
	 *            用户ID
	 *
	 * @return
	 */
	@RequestMapping(value = "post_zan", method = RequestMethod.POST)
	public AppResultData<Object> postZan(@RequestParam("fid") Long fid, @RequestParam("user_id") Long userId) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		FeedSearchVo searchVo = new FeedSearchVo();
		searchVo.setFid(fid);
		searchVo.setUserId(userId);
		FeedZan feedZan = feedZanService.selectBySearchVo(searchVo);

		if (feedZan == null) {
			feedZan = feedZanService.initFeedZan();
			feedZan.setFid(fid);
			feedZan.setUserId(userId);
			feedZanService.insert(feedZan);
		}

		int totalZan = feedZanService.totalByFid(fid);
		result.setData(totalZan);

		return result;
	}

	// 卡片评论接口
	/**
	 * @param fid
	 *            动态ID
	 * @param user_id
	 *            用户ID
	 *
	 * @return
	 */
	@RequestMapping(value = "post_comment", method = RequestMethod.POST)
	public AppResultData<Object> postComment(@RequestParam("fid") Long fid, @RequestParam("user_id") Long userId, @RequestParam("comment") String comment) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		FeedComment feedComment = feedCommentService.initFeedComment();
		feedComment.setFid(fid);
		feedComment.setUserId(userId);
		feedComment.setComment(comment);
		feedCommentService.insert(feedComment);
		return result;
	}

	// 卡片删除接口
	/**
	 * @param fid
	 *            动态ID
	 * @param user_id
	 *            用户ID
	 *
	 * @return
	 */
	@RequestMapping(value = "del", method = RequestMethod.POST)
	public AppResultData<Object> feedDel(@RequestParam("fid") Long fid, @RequestParam("user_id") Long userId) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		Feeds record = feedService.selectByPrimaryKey(fid);
		if (record == null)
			return result;

		if (!record.getUserId().equals(userId)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("只能删除自己创建的动态");
			return result;
		}

		// 删除卡片相应的点赞
		feedZanService.deleteByFid(fid);
		// 删除卡片相应的评论
		feedCommentService.deleteByFid(fid);
		// 删除卡片相应的图片
		feedImgsService.deleteByFid(fid);

		// 删除卡片实体
		feedService.deleteByPrimaryKey(fid);

		return result;
	}

	// 卡片评论删除接口
	/**
	 * @param fid 			动态ID
	 * @param user_id 		用户ID
	 * @param comment_id 	评论ID
	 *
	 * @return
	 */
	@RequestMapping(value = "del_comment", method = RequestMethod.POST)
	public AppResultData<Object> commentDel(@RequestParam("fid") Long fid, @RequestParam("user_id") Long userId, @RequestParam("comment_id") Long commentId) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		Feeds feed = feedService.selectByPrimaryKey(fid);
		if (feed == null) return null;
		
		FeedComment record = feedCommentService.selectByPrimaryKey(commentId);
		if (record == null)
			return result;

		// 1.只有动态发布者能删除评论，包括其他人的
		// 2.评论人本身可以删除自己的评论
		if (feed.getUserId().equals(userId) || record.getUserId().equals(userId)) {
			// 删除卡片相应的评论
			feedCommentService.deleteByPrimaryKey(commentId);
		} else {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("只有发布者和评论人本身能删除评论");
			return result;
		}
		return result;
	}

}
