package com.simi.service.impl.msg;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.msg.MsgMapper;
import com.simi.po.model.msg.Msg;
import com.simi.service.async.NoticeAppAsyncService;
import com.simi.service.msg.MsgService;
import com.simi.service.user.UsersService;
import com.simi.vo.MsgSearchVo;
import com.simi.vo.msg.MsgVo;
import com.simi.vo.msg.OaMsgVo;

@Service
public class MsgServiceImpl implements MsgService {
	@Autowired
	private MsgMapper msgMapper;
	
	@Autowired
	private NoticeAppAsyncService noticeAsynvService;
	
	@Autowired
	private UsersService userService;
	
	
	@Override
	public Msg initMsg() {
		Msg record = new Msg();
	    
	    record.setMsgId(0L);
	    record.setTitle("");
	    record.setSummary("");
	    record.setGotoUrl("");
	    record.setUserType(0L);
	    record.setAppType("");
	    record.setSendTime(0L);		// 发送时间
	    record.setIsSend((short)0L);
	    record.setIsEnable((short)1L);
	    record.setContent("");
	    record.setAddTime(TimeStampUtil.getNow() / 1000);
	    
	    record.setCategory("");
	    record.setAction("");
	    record.setParams("");
		return record;
	}

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
	public List<Msg> selectMsgListBySearchVo(MsgSearchVo searchVo, int pageNo,
			int pageSize) {

		PageHelper.startPage(pageNo,pageSize);
		List<Msg> list = msgMapper.selectUserListByUserType(searchVo);
		
		return list;
	}
	
	@Override
	public MsgVo getMsgList(Msg msg) {

		MsgVo vo = new MsgVo();
		
		vo.setMsgId(msg.getMsgId());
		vo.setTitle(msg.getTitle());
		vo.setGotoUrl(msg.getGotoUrl());
		vo.setSummary(msg.getSummary());
		
		Long addTime = msg.getAddTime()*1000;
		vo.setAddTimeStr(TimeStampUtil.timeStampToDateStr(addTime, "MM-dd HH:mm"));
	
		return vo;
	}
	
	
	@Override
	public OaMsgVo initOaMsgVo(Msg msg) {
		
		OaMsgVo oaMsgVo = new OaMsgVo();
		
		BeanUtilsExp.copyPropertiesIgnoreNull(msg, oaMsgVo);
		
		oaMsgVo.setSendWay((short)1);	//1= 保存并立即发送  0= 发送测试人员
		oaMsgVo.setSendTestUser("");
		oaMsgVo.setSelectUserType(0L);	// 0 = 普通用户   1= 秘书 2=服务商  3=全部用户
		
		return oaMsgVo;
	}
	
	@Override
	public List<Msg> selectMsgBySearchVo(MsgSearchVo searchVo) {
		return msgMapper.selectMsgBySearchVo(searchVo);
	}
}
	

