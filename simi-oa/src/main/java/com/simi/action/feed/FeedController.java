package com.simi.action.feed;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.simi.action.admin.AdminController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.po.model.feed.FeedComment;
import com.simi.po.model.feed.Feeds;
import com.simi.po.model.user.Users;
import com.simi.service.feed.FeedCommentService;
import com.simi.service.feed.FeedImgsService;
import com.simi.service.feed.FeedService;
import com.simi.service.feed.FeedTagsService;
import com.simi.service.feed.FeedZanService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.feed.FeedCommentViewVo;
import com.simi.vo.feed.FeedSearchVo;
import com.simi.vo.feed.FeedVo;
import com.simi.vo.user.UserSearchVo;

@Controller
@RequestMapping(value = "/feed")
public class FeedController extends AdminController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private FeedService feedService;

	@Autowired
	private FeedCommentService feedCommentService;
	
	@Autowired
	private FeedZanService feedZanService;
	
	@Autowired
	private FeedTagsService feedTagService;
	
	@Autowired
	private FeedImgsService feedImgService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String userList(HttpServletRequest request, Model model, FeedSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_SIZE_NAME, ConstantOa.DEFAULT_PAGE_SIZE);

		if (searchVo == null)
			searchVo = new FeedSearchVo();

		searchVo.setFeedType((short) 2);

		PageInfo pageInfo = feedService.selectByListPage(searchVo, pageNo, pageSize);

		List<Feeds> feeds = pageInfo.getList();

		if (!feeds.isEmpty()) {
			for (int i = 0; i < feeds.size(); i++) {
				FeedVo vo = feedService.changeToFeedVo(feeds.get(i));
				feeds.set(i, vo);
			}

		}
		PageInfo result = new PageInfo(feeds);

		model.addAttribute("contentModel", result);

		return "feed/list";
	}

	@AuthPassport
	@RequestMapping(value = "/feedForm", method = { RequestMethod.GET })
	public String feedForm(HttpServletRequest request, Model model, @RequestParam(value = "fid") Long fid) {

		Feeds item = feedService.initFeeds();
		if (fid != null && fid > 0) {
			item = feedService.selectByPrimaryKey(fid);
		}

		FeedVo vo = feedService.changeToFeedVo(item);
		model.addAttribute("contentModel", vo);

		// 回复列表

		FeedSearchVo searchVo = new FeedSearchVo();
		searchVo.setFeedType((short) 2);
		searchVo.setFid(fid);

		List<FeedComment> feedComments = feedCommentService.selectBySearchVo(searchVo);
		List<FeedCommentViewVo> commentList = new ArrayList<FeedCommentViewVo>();
		if (!feedComments.isEmpty()) {
			commentList = feedCommentService.changeToFeedComments(feedComments, 0L);
		}
		model.addAttribute("commentList", commentList);

		// 马甲列表
		List<Long> userIds = new ArrayList<Long>();
		userIds.add(1286L);
		userIds.add(1287L);
		userIds.add(1288L);
		userIds.add(1289L);
		userIds.add(1290L);
		userIds.add(1291L);
		userIds.add(1292L);
		userIds.add(1293L);
		userIds.add(1476L);
		userIds.add(3440L);
		userIds.add(3439L);
		userIds.add(3416L);
		userIds.add(3415L);
		userIds.add(3414L);
		userIds.add(3413L);
		userIds.add(3412L);

		UserSearchVo usearchVo = new UserSearchVo();
		usearchVo.setUserIds(userIds);
		List<Users> userList = usersService.selectBySearchVo(usearchVo);
		model.addAttribute("userList", userList);

		return "feed/feedForm";
	}

	@AuthPassport
	@RequestMapping(value = "/feedAdd", method = { RequestMethod.GET })
	public String feedAdd(HttpServletRequest request, Model model) {

		Feeds item = feedService.initFeeds();

		model.addAttribute("contentModel", item);

		// 马甲列表
		List<Long> userIds = new ArrayList<Long>();
		userIds.add(1286L);
		userIds.add(1287L);
		userIds.add(1288L);
		userIds.add(1289L);
		userIds.add(1290L);
		userIds.add(1291L);
		userIds.add(1292L);
		userIds.add(1293L);
		userIds.add(1476L);
		userIds.add(3440L);
		userIds.add(3439L);
		userIds.add(3416L);
		userIds.add(3415L);
		userIds.add(3414L);
		userIds.add(3413L);
		userIds.add(3412L);

		UserSearchVo usearchVo = new UserSearchVo();
		usearchVo.setUserIds(userIds);
		List<Users> userList = usersService.selectBySearchVo(usearchVo);
		model.addAttribute("userList", userList);

		return "feed/feedAddForm";
	}

	@RequestMapping(value = "set_star", method = RequestMethod.POST)
	public AppResultData<Object> setStar(@RequestParam("fid") Long fid) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Feeds item = feedService.selectByPrimaryKey(fid);

		if (item != null) {
			Short featured = item.getFeatured();

			if (featured.equals((short) 0))
				item.setFeatured((short) 1);
			if (featured.equals((short) 1))
				item.setFeatured((short) 0);
			feedService.updateByPrimaryKeySelective(item);
		}

		return result;

	}

	@RequestMapping(value = "del", method = RequestMethod.POST)
	public AppResultData<Object> del(@RequestParam("fid") Long fid) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Feeds item = feedService.selectByPrimaryKey(fid);

		if (item != null) {
			Short feedType = item.getFeedType();
			FeedSearchVo searchVo = new FeedSearchVo();
			searchVo.setFid(fid);
			searchVo.setFeedType(feedType);

			// 删除评论
			feedCommentService.deleteBySearchVo(searchVo);
			
			// 删除点赞
			feedZanService.deleteBySearchVo(searchVo);

			// 删除图片
			feedImgService.deleteBySearchVo(searchVo);
			// 删除标签
			feedZanService.deleteBySearchVo(searchVo);

			// 删除问答
			feedService.deleteByPrimaryKey(fid);
			
		}

		return result;

	}

}
