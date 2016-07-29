package com.simi.action.app.feed;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meijia.utils.ImgServerUtil;
import com.meijia.utils.RegexUtil;
import com.meijia.utils.StringUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.feed.FeedComment;
import com.simi.po.model.feed.FeedImgs;
import com.simi.po.model.feed.FeedTags;
import com.simi.po.model.feed.FeedZan;
import com.simi.po.model.feed.Feeds;
import com.simi.po.model.user.Tags;
import com.simi.po.model.user.Users;
import com.simi.service.async.FeedMsgAsyncService;
import com.simi.service.async.UserScoreAsyncService;
import com.simi.service.async.UsersAsyncService;
import com.simi.service.feed.FeedCommentService;
import com.simi.service.feed.FeedService;
import com.simi.service.feed.FeedTagsService;
import com.simi.service.feed.FeedZanService;
import com.simi.service.user.TagsService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.feed.FeedSearchVo;
import com.simi.service.feed.FeedImgsService;

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
	private FeedMsgAsyncService feedMsgAsyncService;

	@Autowired
	private FeedTagsService feedTagsService;

	@Autowired
	private TagsService tagService;
	
	@Autowired
	private UserScoreAsyncService userScoreAsyncService;
	
	@Autowired
	private UsersAsyncService userAsyncService;

	// 动态添加接口
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "post_feed", method = RequestMethod.POST)
	public AppResultData<Object> postFeed(@RequestParam("user_id") Long userId,
			@RequestParam(value = "title", required = false, defaultValue = "") String title,
			@RequestParam(value = "feed_type", required = false, defaultValue = "1") Short feedType,
			@RequestParam(value = "feed_imgs", required = false) MultipartFile[] feedImgs,
			@RequestParam(value = "lat", required = false, defaultValue = "") String lat,
			@RequestParam(value = "lng", required = false, defaultValue = "") String lng,
			@RequestParam(value = "poi_name", required = false, defaultValue = "") String poiName,
			@RequestParam(value = "feed_extra", required = false, defaultValue = "") String feedExtra,
			@RequestParam(value = "tag_ids", required = false, defaultValue = "") String tagIds) throws IOException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		if (title.length() > 512) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("问题有点长，控制在512字内好吗亲？");
			return result;
		}
		
		Integer score = 0;
		// 如果为问答类，则需要判断用户的积分是否足够
		if (feedType.equals((short) 2)) {
			if (StringUtil.isEmpty(feedExtra))
				feedExtra = "0";
			if (!RegexUtil.isInteger(feedExtra))
				feedExtra = "0";
			if (!feedExtra.equals("0")) {
				score = Integer.valueOf(feedExtra);
				Integer userScore = u.getScore();
				if (userScore < score) {
					result.setStatus(Constants.ERROR_999);
					result.setMsg("金币不足!");
					return result;
				}
			}
		}

		Feeds record = feedService.initFeeds();
		record.setUserId(userId);
		record.setTitle(title);
		record.setFeedType(feedType);
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

				FeedImgs feedImg = feedImgsService.initFeedImgs();
				feedImg.setFid(fid);
				feedImg.setUserId(userId);
				feedImg.setFeedType(feedType);
				feedImg.setImgUrl(imgUrl);
				feedImgsService.insert(feedImg);

			}
		}

		// 处理标签
		if (StringUtil.isEmpty(tagIds)) {
			List<Tags> tags = tagService.selectByTagType((short) 3);
			String[] tagIdsAry = StringUtil.convertStrToArray(tagIds);
			for (int i = 0; i < tagIdsAry.length; i++) {
				if (StringUtil.isEmpty(tagIdsAry[i]))
					continue;
				Long tagId = Long.valueOf(tagIdsAry[i]);
				String tagName = "";
				for (Tags item : tags) {
					if (item.getTagId().equals(tagId)) {
						tagName = item.getTagName();
						break;
					}
				}

				FeedTags feedTag = feedTagsService.initFeedTags();

				feedTag.setFid(fid);
				feedTag.setUserId(userId);
				feedTag.setFeedType(feedType);
				feedTag.setTagId(tagId);
				feedTag.setTags(tagName);

				feedTagsService.insert(feedTag);

			}
		}
		
		//扣除积分
		userScoreAsyncService.consumeScore(userId, score, "qa", fid.toString(), "问题悬赏");
		
		// 生成动态消息
//		feedMsgAsyncService.newFeedMsg(fid);
		
		//统计用问答数
		userAsyncService.statUser(userId, "totalFeeds");

		result.setData(fid);

		return result;
	}

	// 动态图片上传接口
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "post_feed_imgs", method = RequestMethod.POST)
	public AppResultData<Object> postFeedImg(@RequestParam("fid") Long fid, 
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "feed_type", required = false, defaultValue = "1") Short feedType, 
			@RequestParam("feed_imgs") MultipartFile[] feedImgs)
			throws IOException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		Feeds record = feedService.selectByPrimaryKey(fid);

		if (fid == null)
			return result;

		if (record != null) {
			if (record.getFeedType().equals(feedType)) {
				if (!userId.equals(record.getUserId())) {
					result.setStatus(Constants.ERROR_999);
					result.setMsg("只能修改自己发布的信息");
					return result;
				}
			}
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

				FeedImgs feedImg = feedImgsService.initFeedImgs();
				feedImg.setFid(fid);
				feedImg.setUserId(userId);
				feedImg.setFeedType(feedType);
				feedImg.setImgUrl(imgUrl);
				feedImgsService.insert(feedImg);

			}
		}

		return result;
	}

	// 动态点赞接口
	/**
	 * @param fid
	 *            动态ID
	 * @param user_id
	 *            用户ID
	 *
	 * @return
	 */
	@RequestMapping(value = "post_zan", method = RequestMethod.POST)
	public AppResultData<Object> postZan(
			@RequestParam("fid") Long fid, 
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "comment_id", required = false, defaultValue = "0") Long commentId,
			@RequestParam(value = "feed_type", required = false, defaultValue = "1") Short feedType,
			@RequestParam(value = "action", required = false, defaultValue = "add") String action) {

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
		searchVo.setFeedType(feedType);
		searchVo.setCommentId(commentId);
		List<FeedZan> feedZans = feedZanService.selectBySearchVo(searchVo);

		if (action.equals("add")) {
			if (feedZans.isEmpty()) {
				FeedZan feedZan = feedZanService.initFeedZan();
				feedZan.setFid(fid);
				feedZan.setFeedType(feedType);
				feedZan.setUserId(userId);
				feedZan.setCommentId(commentId);
				feedZanService.insert(feedZan);
			}
			
			if (feedType.equals((short) 2)) {
				//针对赞进行消息提醒
				feedMsgAsyncService.newFeedZanMsg(userId, fid, feedType, commentId);
			}
		}

		if (action.equals("del")) {
			if (!feedZans.isEmpty()) {
				FeedZan feedZan = feedZans.get(0);
				feedZanService.deleteByPrimaryKey(feedZan.getId());
			}
		}

		searchVo = new FeedSearchVo();
		searchVo.setFid(fid);
		searchVo.setFeedType(feedType);
		searchVo.setCommentId(commentId);
		int totalZan = feedZanService.totalByFid(searchVo);
		result.setData(totalZan);


		return result;
	}

	// 获取点赞接口
	/**
	 * @param fid
	 *            动态ID
	 * @param user_id
	 *            用户ID
	 *
	 * @return
	 */
	@RequestMapping(value = "get_zan", method = RequestMethod.GET)
	public AppResultData<Object> getZan(
			@RequestParam("fid") Long fid, @RequestParam("user_id") Long userId,
			@RequestParam(value = "comment_id", required = false, defaultValue = "0") Long commentId,
			@RequestParam(value = "feed_type", required = false, defaultValue = "1") Short feedType) {

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
		searchVo.setFeedType(feedType);
		searchVo.setCommentId(commentId);
		List<FeedZan> feedZans = feedZanService.selectBySearchVo(searchVo);

		if (feedZans.isEmpty()) {
			return result;
		}

		FeedZan feedZan = feedZans.get(0);
		result.setData(feedZan);

		return result;
	}

	// 动态评论接口
	/**
	 * @param fid
	 *            动态ID
	 * @param user_id
	 *            用户ID
	 *
	 * @return
	 */
	@RequestMapping(value = "post_comment", method = RequestMethod.POST)
	public AppResultData<Object> postComment(
			@RequestParam("fid") Long fid, 
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "feed_type", required = false, defaultValue = "1") Short feedType, 
			@RequestParam("comment") String comment) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		Feeds feed = feedService.selectByPrimaryKey(fid);
		if (feed != null) {
			if (feed.getStatus().equals((short)2)) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("这是已关闭的信息.");
				return result;
			}
		}
		
		if (!StringUtil.isEmpty(comment) && comment.length() > 1024) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("答案有点长，控制在1024字内好吗亲？");
			return result;
		}

		FeedComment feedComment = feedCommentService.initFeedComment();
		feedComment.setFid(fid);
		feedComment.setUserId(userId);
		feedComment.setFeedType(feedType);
		feedComment.setComment(comment);
		feedCommentService.insert(feedComment);
		Long commentId = feedComment.getId();
		
		if (feedType.equals((short) 2)) {
			//答题奖励
			userScoreAsyncService.sendScore(userId, Constants.SCORE_QA_COMMENT, "qa", commentId.toString(), "答题奖励");
			
			//针对题主进行消息提醒
			feedMsgAsyncService.newFeedCommentMsg(userId, fid, feedType, commentId);
			
			//统计用问答数
			userAsyncService.statUser(userId, "totalFeeds");
		}
		
		
		
		return result;
	}

	// 动态、文档采纳完结接口
	@RequestMapping(value = "post_done", method = RequestMethod.POST)
	public AppResultData<Object> postDone(@RequestParam("fid") Long fid, @RequestParam("user_id") Long userId, @RequestParam("comment_id") Long commentId,
			@RequestParam(value = "feed_type", required = false, defaultValue = "1") Short feedType) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		Feeds feed = feedService.selectByPrimaryKey(fid);
		String feedExtra = feed.getFeedExtra();
		Integer score = 0;
		// 如果为问答类，则需要判断用户的积分是否足够
		if (StringUtil.isEmpty(feedExtra))
			feedExtra = "0";
		if (!RegexUtil.isInteger(feedExtra))
			feedExtra = "0";
		if (!feedExtra.equals("0")) {
			score = Integer.valueOf(feedExtra);
			Integer userScore = u.getScore();
			if (userScore < score) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("金币不足!");
				return result;
			}
		}

		FeedComment comment = feedCommentService.selectByPrimaryKey(commentId);
		if (comment == null) return result;
		Long commentUserId = comment.getUserId();
		if (userId.equals(commentUserId)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("您不能采纳自己的答案哦！");
			return result;
		}
		
		feed.setStatus((short) 1);
		feedService.updateByPrimaryKeySelective(feed);
		
		comment.setStatus((short) 1);
		feedCommentService.updateByPrimaryKeySelective(comment);		
		
		if (score > 0) {
			//问答用户增加金币，发起用户扣除金币
			//1.
			
			//答题奖励  //问题悬赏
			userScoreAsyncService.sendScore(commentUserId, score, "qa", commentId.toString(), "采纳问答");
		}
		
		if (feedType.equals((short) 2)) {
			//针对问题采纳进行消息提醒
			feedMsgAsyncService.newFeedDoneMsg(userId, fid, feedType, commentId);
		}
		
		return result;
	}
	
	// 动态、文档采纳完结接口
		@RequestMapping(value = "post_close", method = RequestMethod.POST)
		public AppResultData<Object> postClose(
				@RequestParam("fid") Long fid, 
				@RequestParam("user_id") Long userId, 
				@RequestParam(value = "feed_type", required = false, defaultValue = "1") Short feedType) {

			AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

			Users u = userService.selectByPrimaryKey(userId);

			// 判断是否为注册用户，非注册用户返回 999
			if (u == null) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
				return result;
			}

			Feeds feed = feedService.selectByPrimaryKey(fid);
			String feedExtra = feed.getFeedExtra();
			Integer score = 0;
			// 如果为问答类，则需要判断用户的积分是否足够
			if (StringUtil.isEmpty(feedExtra))
				feedExtra = "0";
			if (!RegexUtil.isInteger(feedExtra))
				feedExtra = "0";
			if (!feedExtra.equals("0")) {
				score = Integer.valueOf(feedExtra);
			}
			
			feed.setStatus((short) 2);
			feedService.updateByPrimaryKey(feed);
			
			if (score > 0) {
				userScoreAsyncService.sendScore(userId, score, "qa", fid.toString(), "问题悬赏关闭");
			}
			
			if (feedType.equals((short) 2)) {
				//针对问题关闭进行消息提醒
				feedMsgAsyncService.newFeedCloseMsg(userId, fid, feedType);
			}
			
			return result;
		}	
	
//	// 动态删除接口
//	/**
//	 * @param fid
//	 *            动态ID
//	 * @param user_id
//	 *            用户ID
//	 *
//	 * @return
//	 */
//	@RequestMapping(value = "del", method = RequestMethod.POST)
//	public AppResultData<Object> feedDel(@RequestParam("fid") Long fid, @RequestParam("user_id") Long userId,
//			@RequestParam(value = "feed_type", required = false, defaultValue = "1") Short feedType) {
//
//		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
//
//		Users u = userService.selectByPrimaryKey(userId);
//
//		// 判断是否为注册用户，非注册用户返回 999
//		if (u == null) {
//			result.setStatus(Constants.ERROR_999);
//			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
//			return result;
//		}
//
//		FeedSearchVo searchVo = new FeedSearchVo();
//		searchVo.setFid(fid);
//		searchVo.setFeedType(feedType);
//
//		Feeds record = feedService.selectByPrimaryKey(fid);
//		if (record == null)
//			return result;
//
//		if (!record.getUserId().equals(userId)) {
//			result.setStatus(Constants.ERROR_999);
//			result.setMsg("只能删除自己创建的信息");
//			return result;
//		}
//
//		// 删除动态相应的点赞
//		feedZanService.deleteBySearchVo(searchVo);
//		// 删除动态相应的评论
//		feedCommentService.deleteBySearchVo(searchVo);
//		// 删除动态相应的图片
//		feedImgsService.deleteBySearchVo(searchVo);
//		// 删除相应的标签
//		feedTagsService.deleteBySearchVo(searchVo);
//
//		// 删除动态实体
//		feedService.deleteByPrimaryKey(fid);
//
//		return result;
//	}	
//	
//	// 动态评论删除接口
//	/**
//	 * @param fid
//	 *            动态ID
//	 * @param user_id
//	 *            用户ID
//	 * @param comment_id
//	 *            评论ID
//	 *
//	 * @return
//	 */
//	@RequestMapping(value = "del_comment", method = RequestMethod.POST)
//	public AppResultData<Object> commentDel(@RequestParam("fid") Long fid, @RequestParam("user_id") Long userId, @RequestParam("comment_id") Long commentId,
//			@RequestParam(value = "feed_type", required = false, defaultValue = "1") Short feedType) {
//
//		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
//
//		Users u = userService.selectByPrimaryKey(userId);
//
//		// 判断是否为注册用户，非注册用户返回 999
//		if (u == null) {
//			result.setStatus(Constants.ERROR_999);
//			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
//			return result;
//		}
//
//		Feeds feed = feedService.selectByPrimaryKey(fid);
//		// if (feed == null) return null;
//
//		FeedComment record = feedCommentService.selectByPrimaryKey(commentId);
//		if (record == null)
//			return result;
//
//		// 1.只有动态发布者能删除评论，包括其他人的
//		// 2.评论人本身可以删除自己的评论
//		if (feed != null && feed.getUserId().equals(userId)) {
//			feedCommentService.deleteByPrimaryKey(commentId);
//		} else if (record.getUserId().equals(userId)) {
//			// 删除动态相应的评论
//			feedCommentService.deleteByPrimaryKey(commentId);
//		} else {
//			result.setStatus(Constants.ERROR_999);
//			result.setMsg("只有发布者和评论人本身能删除评论");
//			return result;
//		}
//		return result;
//	}	

}
