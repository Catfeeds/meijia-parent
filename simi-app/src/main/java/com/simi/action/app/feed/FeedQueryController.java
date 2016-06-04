package com.simi.action.app.feed;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.simi.vo.AppResultData;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.feed.FeedComment;
import com.simi.po.model.feed.Feeds;
import com.simi.po.model.user.Users;
import com.simi.service.feed.FeedCommentService;
import com.simi.service.feed.FeedImgsService;
import com.simi.service.feed.FeedService;
import com.simi.service.feed.FeedZanService;
import com.simi.service.user.UserFriendService;
import com.simi.service.user.UsersService;
import com.simi.vo.feed.FeedCommentViewVo;
import com.simi.vo.feed.FeedListVo;
import com.simi.vo.feed.FeedSearchVo;

@Controller
@RequestMapping(value = "/app/feed")
public class FeedQueryController extends BaseController {
	@Autowired
	private UsersService userService;

	@Autowired
	private UserFriendService userFriendService;

	@Autowired
	private FeedImgsService feedImgsService;

	@Autowired
	private FeedService feedService;

	@Autowired
	private FeedZanService feedZanService;

	@Autowired
	private FeedCommentService feedCommentService;

	// 卡片详情接口
	/**
	 * @param fid
	 *            动态ID
	 * @param user_id
	 *            用户ID
	 *
	 * @return FeedViewVo
	 */
	@RequestMapping(value = "get_detail", method = RequestMethod.GET)
	public AppResultData<Object> getDetail(
			@RequestParam("fid") Long fid, 
			@RequestParam(value = "feed_type", required = false, defaultValue = "1") Short feedType,
			@RequestParam(value = "user_id", required = false, defaultValue = "") Long userId) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		if (userId != null) {
			Users u = userService.selectByPrimaryKey(userId);

			// 判断是否为注册用户，非注册用户返回 999
			if (u == null) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
				return result;
			}
		}
		Feeds record = feedService.selectByPrimaryKey(fid);
		if (record == null) return result;
		FeedListVo vo = feedService.changeToFeedListVo(record);
		result.setData(vo);
		return result;
	}

	// 卡片列表接口
	/**
	 * @param user_id
	 *            用户ID
	 * @param feed_from
	 *            0 = 用户及好友所有的动态 1= 用户发布的动态
	 *
	 * @return List<FeedListVo>
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "get_list", method = RequestMethod.GET)
	public AppResultData<Object> getFeedList(
			@RequestParam(value = "user_id", required = false, defaultValue = "0") Long userId,
			@RequestParam(value = "feed_type", required = false, defaultValue = "1") Short feedType,
			@RequestParam(value = "feed_from", required = false, defaultValue = "0") Short feedFrom,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = null;
		if (userId > 0L) {
			u = userService.selectByPrimaryKey(userId);
	
			// 判断是否为注册用户，非注册用户返回 999
			if (u == null) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
				return result;
			}
		}

		FeedSearchVo searchVo = new FeedSearchVo();
		searchVo.setFeedType(feedType);
		if (feedFrom.equals((short) 1)) {
			searchVo.setUserId(userId);
		}

		PageInfo pageInfo = feedService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		List<Feeds> feeds = pageInfo.getList();
		List<FeedListVo> feedList = new ArrayList<FeedListVo>();
		if (!feeds.isEmpty()) {
			for (int i = 0; i < feeds.size(); i++) {
				FeedListVo vo = feedService.changeToFeedListVo(feeds.get(i));
				feedList.add(vo);
			}
			result.setData(feedList);
		}

		return result;
	}

	// 卡片评论列表接口
	/**
	 * @param fid
	 *            动态ID
	 * @param user_id
	 *            用户ID
	 * @param page
	 *            页码
	 *
	 * @return FeedViewVo
	 */
	@RequestMapping(value = "get_comment_list", method = RequestMethod.GET)
	public AppResultData<Object> getCommentList(
			@RequestParam("fid") Long fid, 
			@RequestParam(value = "user_id", required = false, defaultValue = "") Long userId,
			@RequestParam(value = "feed_type", required = false, defaultValue = "1") Short feedType,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		if (userId != null) {
			Users u = userService.selectByPrimaryKey(userId);

			// 判断是否为注册用户，非注册用户返回 999
			if (u == null) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
				return result;
			}
		}

		FeedSearchVo searchVo = new FeedSearchVo();
		searchVo.setFeedType(feedType);
		searchVo.setFid(fid);

		List<FeedComment> feedComments = feedCommentService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		if (!feedComments.isEmpty()) {
			List<FeedCommentViewVo> list = feedCommentService.changeToFeedComments(feedComments, userId);
			result.setData(list);
		}

		return result;
	}
}
