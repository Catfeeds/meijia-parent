package com.simi.service.impl.promotion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.promotion.MsgService;
import com.simi.service.user.UserBaiduBindService;
import com.simi.vo.promotion.MsgSearchVo;
import com.simi.po.dao.promotion.MsgMapper;
import com.simi.po.model.promotion.Msg;
import com.simi.po.model.user.UserBaiduBind;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.baidu.BaiduUtil;
@Service
public class MsgServiceImpl implements MsgService {

	@Autowired
	private MsgMapper msgMapper;

	@Autowired
	private UserBaiduBindService userBaiduBindService;	
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return msgMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Msg record) {
		return msgMapper.insert(record);
	}

	@Override
	public int insertSelective(Msg record) {
		return msgMapper.insertSelective(record);
	}

	@Override
	public Msg selectByPrimaryKey(Long id) {
		return msgMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Msg record) {
		return msgMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Msg record) {
		return msgMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageInfo searchListPage(MsgSearchVo msgSearchVo,int pageNo, int pageSize) {
		
		Map<String, Object> conditions = new HashMap<String, Object>();
		String title = msgSearchVo.getTitle();
		String summary = msgSearchVo.getSummary();
		Short sendGroup = msgSearchVo.getSendGroup();
		
		if(title !=null && !title.isEmpty()){
			conditions.put("title",title.trim());
		}
		if(summary != null && !summary.isEmpty()){
			conditions.put("summary",summary.trim());
		}
		if(sendGroup !=null && sendGroup !=3 ){
			conditions.put("sendGroup",sendGroup);
		}
		
		 PageHelper.startPage(pageNo, pageSize);
         List<Msg> list = msgMapper.selectByListPage(conditions);
         PageInfo result = new PageInfo(list);
		return result;
	}

	@Override
	public Msg initMsg() {
		Msg record = new Msg();
		record.setId(0L);
		record.setTitle("");
		record.setSummary("");
		record.setContent("");
		record.setHtmlUrl("");
		record.setSendGroup((short)0);
		record.setIsEnable((short)1);
		record.setAddTime(TimeStampUtil.getNow()/1000);
		record.setUpdateTime(0L);
		return record;
	}

	@Override
	public Msg selectByMobile(String mobile) {

		return msgMapper.selectByMobile(mobile);

	}

/*	@Override
	public List<Msg> selectUsersByIds(List<Integer> ids) {
		return msgMapper.selectByIds(ids);
	}*/

	/**
	 * 消息进行百度推送
	 * @param msgId  消息ID
	 * 流程说明
	 * 
	 * 根据消息发送的用户群，进行分别处理
	 *   1 全部用户，则直接调用 BaiduUtil.IOSPushNotificationToAll
	 *   
	 *   2 部分用户群体，则需要
	 *   	1) 先查找出 user_ids 集合A
	 *      2）根据 集合A 查找表 user_baidu_bind 获得所有的设备ID 集合B
	 *      3) 集合B 根据设备类型分别得到 集合 IOS 和 集合 Android
	 *      4) 分别调用 百度推送 IOS 和 百度推送 Android 完成推送流程
	 *      
	 *      注意： Android 暂时未调试。
	 * @throws PushServerException 
	 * @throws PushClientException 
	 */
	@Override
	public int pushMsgFromBaidu(Long msgId) throws PushClientException, PushServerException {
		Msg msg = this.selectByPrimaryKey(msgId);
		int counts = 0;
		
		if (msg == null) {
			return 0;
		}
		
		String msgContent = msg.getSummary();
//		String htmlUrl = msg.getHtmlUrl();
//		if (htmlUrl.indexOf("http://") <= 0) {
//			htmlUrl = "http://www.yougeguanjia.com" + htmlUrl;
//		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("msgid", msg.getId().toString());
		
		//发送用户群 0 = 全部用户 1= 已注册未下单用户 2 = 下过单用户
		Short sendGroup = msg.getSendGroup();
		
		//发送给全部用户
		if (sendGroup.equals((short)0)) {
			counts = userBaiduBindService.totalBaiduBind();
			BaiduUtil.IOSPushNotificationToAll(msgContent, params);
			return counts;
		}
		
		//发送给 1= 已注册未下单用户
		if (sendGroup.equals((short)1)) {
			List<UserBaiduBind> listSend = userBaiduBindService.selectByNotOrdered();
			if (!listSend.isEmpty()) {
				return 0;
			}
			
			String[] channelIds = new String[]{};
			UserBaiduBind vo = null;
			for (int i = 0; i < listSend.size(); i++) {
				vo = listSend.get(i);
				channelIds[i] = vo.getChannelId();
			}
				
			if (channelIds.length > 0) {
				BaiduUtil.IOSPushNotificationToMultiDevice(channelIds, msgContent, params);
				counts = listSend.size();
				return counts ;
			}
		}
		//发送给 2 = 下过单用户
		if (sendGroup.equals((short)2)) {
			List<UserBaiduBind> listSend = userBaiduBindService.selectByOrdered();
			if (!listSend.isEmpty()) {
				return counts;
			}
			
			String[] channelIds = new String[]{};
			UserBaiduBind vo = null;
			for (int i = 0; i < listSend.size(); i++) {
				vo = listSend.get(i);
				channelIds[i] = vo.getChannelId();
			}
				
			if (channelIds.length > 0) {
				BaiduUtil.IOSPushNotificationToMultiDevice(channelIds, msgContent, params);
				counts = listSend.size();
				return counts;
			}			
		}	
		return counts;
	}
	
}
