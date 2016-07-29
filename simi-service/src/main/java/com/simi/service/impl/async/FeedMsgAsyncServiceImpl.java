package com.simi.service.impl.async;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.meijia.utils.MobileUtil;
import com.meijia.utils.StringUtil;
import com.simi.po.model.feed.FeedComment;
import com.simi.po.model.feed.Feeds;
import com.simi.po.model.user.UserMsg;
import com.simi.po.model.user.Users;
import com.simi.service.async.FeedMsgAsyncService;
import com.simi.service.async.NoticeAppAsyncService;
import com.simi.service.feed.FeedCommentService;
import com.simi.service.feed.FeedService;
import com.simi.service.feed.FeedZanService;
import com.simi.service.user.UserMsgService;
import com.simi.service.user.UsersService;
import com.simi.utils.FeedUtil;
import com.simi.vo.feed.FeedSearchVo;

@Service
public class FeedMsgAsyncServiceImpl implements FeedMsgAsyncService {

	@Autowired
	public UsersService usersService;

	@Autowired
	private UserMsgService userMsgService;


	@Autowired
	private FeedService feedService;
	
	@Autowired
	private FeedZanService feedZanService;

	@Autowired
	private FeedCommentService feedCommentService;
	
	@Autowired
	private NoticeAppAsyncService noticeAppAsyncService;

	/**
	 * 新增动态时发送消息
	 */
	@Async
	@Override
	public Future<Boolean> newFeedMsg(Long fid) {

		Feeds feed = feedService.selectByPrimaryKey(fid);
		if (feed == null)
			return new AsyncResult<Boolean>(true);
		
		Short feedType = feed.getFeedType();
		
		String feedTypeName = FeedUtil.getFeedTypeName(feedType);

		Long userId = feed.getUserId();
		UserMsg record = userMsgService.initUserMsg();

		record.setUserId(userId);
		record.setFromUserId(userId);
		record.setToUserId(userId);
		record.setCategory("app");
		record.setAction("feed");
		record.setParams(fid.toString());
		record.setGotoUrl("");
		record.setTitle("发布" + feedTypeName);

		String title = feed.getTitle();

		if (title != null && title.length() > 20) {
			title = title.substring(0, 20);
		}

		if (title.length() == 0) {
			title = "你发布了新的" + feedTypeName;
		}
		
		record.setSummary(title);
		record.setIconUrl("http://123.57.173.36/images/icon/iconfont-dongtai.png");
		userMsgService.insert(record);

		return new AsyncResult<Boolean>(true);
	}
	
	/**
	 * 1.回答问题，给题主发送提醒
	 * 2.给题主产生日程
	 */
	@Async
	@Override
	public Future<Boolean> newFeedCommentMsg(Long commentUserId, Long fid, Short feedType, Long commentId) {

		Feeds feed = feedService.selectByPrimaryKey(fid);
		if (feed == null)
			return new AsyncResult<Boolean>(true);
		
		Long userId = feed.getUserId();
		Users u = usersService.selectByPrimaryKey(userId);
		String name = u.getName();
		if (StringUtil.isEmpty(name)) {
			name = MobileUtil.getMobileStar(u.getMobile());
		}
			
		Users commentUser = usersService.selectByPrimaryKey(commentUserId);
		String commentName = commentUser.getName();
		if (StringUtil.isEmpty(commentName)) {
			commentName = MobileUtil.getMobileStar(commentUser.getMobile());
		}
//		
		String title = feed.getTitle();
		if (title.length() > 20) {
			title = title.substring(0,20) + "...";
		}
		
		String msgContent = commentName + "回答了你的问题："+title+"，快去看看哦！ ";
		noticeAppAsyncService.pushMsgToDevice(userId, "互助问答", msgContent, "app", "feed", fid.toString(), "");
		
		//题主产生日程.
		UserMsg record = userMsgService.initUserMsg();

		record.setUserId(userId);
		record.setFromUserId(commentUserId);
		record.setToUserId(userId);
		record.setCategory("app");
		record.setAction("feed");
		record.setParams(fid.toString());
		record.setGotoUrl("");
		record.setTitle("互助问答");
		record.setSummary(msgContent);
		record.setIconUrl("http://123.57.173.36/images/icon/iconfont-dongtai.png");
		userMsgService.insert(record);

		return new AsyncResult<Boolean>(true);
	}	
	
	/**
	 * 1.给答主进行提醒.
	 */
	@Async
	@Override
	public Future<Boolean> newFeedZanMsg(Long userId, Long fid, Short feedType, Long commentId) {

		Feeds feed = feedService.selectByPrimaryKey(fid);
		if (feed == null)
			return new AsyncResult<Boolean>(true);
		
		Users u = usersService.selectByPrimaryKey(userId);
		String name = u.getName();
		if (StringUtil.isEmpty(name)) {
			name = MobileUtil.getMobileStar(u.getMobile());
		}
		FeedComment feedComment = feedCommentService.selectByPrimaryKey(commentId);
		
		Long commentUserId = feedComment.getUserId();
		
//		Users commentUser = usersService.selectByPrimaryKey(commentUserId);
//		
		String title = feed.getTitle();
		if (title.length() > 20) {
			title = title.substring(0,20) + "...";
		}
		
		String msgContent = "你在【"+title+"】的回答被"+name+"点赞喽~";
		noticeAppAsyncService.pushMsgToDevice(commentUserId, "互助问答", msgContent, "app", "feed", fid.toString(), "");
		
		return new AsyncResult<Boolean>(true);
	}
	
	
	/**
	 * 1.问题被采纳，给答主发送提醒
	 * 2.给答主产生日程.
	 */
	@Async
	@Override
	public Future<Boolean> newFeedDoneMsg(Long userId, Long fid, Short feedType, Long commentId) {

		Feeds feed = feedService.selectByPrimaryKey(fid);
		if (feed == null)
			return new AsyncResult<Boolean>(true);
		
		Users u = usersService.selectByPrimaryKey(userId);
		String name = u.getName();
		if (StringUtil.isEmpty(name)) {
			name = MobileUtil.getMobileStar(u.getMobile());
		}
			
		String title = feed.getTitle();
		if (title.length() > 20) {
			title = title.substring(0,20) + "...";
		}
		
		FeedComment feedComment = feedCommentService.selectByPrimaryKey(commentId);
		
		Long commentUserId = feedComment.getUserId();
		
//		Users commentUser = usersService.selectByPrimaryKey(commentUserId);
		
		String msgContent = "你在【"+title+"】的回答被"+name+"采纳，现在去看看~";
		noticeAppAsyncService.pushMsgToDevice(commentUserId, "互助问答", msgContent, "app", "feed", fid.toString(), "");
		
		//答主产生日程.
		UserMsg record = userMsgService.initUserMsg();

		record.setUserId(commentUserId);
		record.setFromUserId(userId);
		record.setToUserId(commentUserId);
		record.setCategory("app");
		record.setAction("feed");
		record.setParams(fid.toString());
		record.setGotoUrl("");
		record.setTitle("互助问答");
		record.setSummary(msgContent);
		record.setIconUrl("http://123.57.173.36/images/icon/iconfont-dongtai.png");
		userMsgService.insert(record);

		return new AsyncResult<Boolean>(true);
	}	
	
	/**
	 * 1.问题被关闭，给所有答主发送提醒
	 * 2.给答主产生日程.
	 */
	@Async
	@Override
	public Future<Boolean> newFeedCloseMsg(Long userId, Long fid, Short feedType) {

		Feeds feed = feedService.selectByPrimaryKey(fid);
		if (feed == null)
			return new AsyncResult<Boolean>(true);
		
		Users u = usersService.selectByPrimaryKey(userId);
		String name = u.getName();
		if (StringUtil.isEmpty(name)) {
			name = MobileUtil.getMobileStar(u.getMobile());
		}
			
		String title = feed.getTitle();
		if (title.length() > 20) {
			title = title.substring(0,20) + "...";
		}
		
		FeedSearchVo searchVo = new FeedSearchVo();
		searchVo.setFid(fid);
		searchVo.setFeedType(feedType);
		List<FeedComment> feedComments = feedCommentService.selectBySearchVo(searchVo);
		
		if (feedComments.isEmpty()) return new AsyncResult<Boolean>(true);
		
		String msgContent = "";
		for (FeedComment item : feedComments) {
			Long commentUserId = item.getUserId();
			
	//		Users commentUser = usersService.selectByPrimaryKey(commentUserId);
			
			msgContent = "【"+title+"】已被题主"+name+"关闭~";
			noticeAppAsyncService.pushMsgToDevice(commentUserId, "互助问答", msgContent, "app", "feed", fid.toString(), "");
			
			
		}
		
		msgContent = "你关闭了【"+title+"】";
		//答主产生日程.
		UserMsg record = userMsgService.initUserMsg();

		record.setUserId(userId);
		record.setFromUserId(userId);
		record.setToUserId(userId);
		record.setCategory("app");
		record.setAction("feed");
		record.setParams(fid.toString());
		record.setGotoUrl("");
		record.setTitle("互助问答");
		record.setSummary(msgContent);
		record.setIconUrl("http://123.57.173.36/images/icon/iconfont-dongtai.png");
		userMsgService.insert(record);

		return new AsyncResult<Boolean>(true);
	}		
}
