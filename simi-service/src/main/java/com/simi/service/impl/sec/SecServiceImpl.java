package com.simi.service.impl.sec;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.huanxin.EasemobIMUsers;
import com.simi.common.Constants;
import com.simi.po.dao.sec.SecMapper;
import com.simi.po.dao.sec.SecRef3rdMapper;
import com.simi.po.dao.user.UserRef3rdMapper;
import com.simi.po.model.sec.Sec;
import com.simi.po.model.sec.SecRef3rd;
import com.simi.po.model.user.UserFriends;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.Users;
import com.simi.service.dict.DictService;
import com.simi.service.sec.SecService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UserRefSecService;
import com.simi.service.user.UsersService;
import com.simi.vo.sec.SecInfoVo;
import com.simi.vo.sec.SecViewVo;
import com.simi.vo.sec.SecVo;
import com.simi.vo.user.UserFriendViewVo;

@Service
public class SecServiceImpl implements SecService {

	@Autowired
	private UsersService userService;	
	
	@Autowired
	private SecRef3rdMapper secRef3rdMapper;

	@Autowired
	private SecMapper secMapper;

	@Autowired
	private DictService dictService;

	@Autowired
	private UserRefSecService userRefSecService;

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private UserRef3rdService userRef3rdService;	

	@Override
	public Long insertSelective(Sec record) {

		return secMapper.insert(record);

	}

	@Autowired
	private UserRef3rdMapper userRef3rdMapper;

	@Override
	public PageInfo searchVoListPage(int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		List<Sec> list = secMapper.selectByListPage();

		List<Sec> listNew = new ArrayList<Sec>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Sec sec = (Sec) iterator.next();

			String imgUrl = sec.getHeadImg();

			if (imgUrl.length() > 0) {

				String extensionName = imgUrl
						.substring(imgUrl.lastIndexOf("."));
				String beforName = imgUrl.substring(0,
						(imgUrl.lastIndexOf(".")));
				String newImgUrl = beforName + "_small" + extensionName;
				sec.setHeadImg(newImgUrl);
			}

			SecVo secNew = new SecVo();
			try {
				BeanUtils.copyProperties(secNew, sec);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			SecRef3rd secRef3rd = secRef3rdMapper.selectBySecIdForIm(sec
					.getId());
			if (secRef3rd == null) {
				secNew.setUsername("");
				secNew.setPassword("");
				secNew.setImgUrlNew("");
			} else {
				secNew.setUsername(secRef3rd.getUsername());
				secNew.setPassword(secRef3rd.getPassword());
				secNew.setImgUrlNew("");
			}

			listNew.add(secNew);

		}

		for (int i = 0; i < list.size(); i++) {
			if (listNew.get(i) != null) {
				list.set(i, listNew.get(i));
			}
		}

		PageInfo result = new PageInfo(list);
		return result;

	}

	/**
	 * 秘书对应的订单信息列表显示
	 */

	/**
	 * 注册环信用户
	 */
	@Override
	public SecRef3rd genImSec(Sec sec) {
		SecRef3rd record = new SecRef3rd();
		Long secId = sec.getId();
		SecRef3rd secRef3rd = secRef3rdMapper.selectBySecIdForIm(secId);
		if (secRef3rd != null) {
			return secRef3rd;
		}

		// 如果不存在则新增.并且存入数据库

		String username = "simi-sec-" + sec.getId().toString();
		String defaultPassword = com.meijia.utils.huanxin.comm.Constants.DEFAULT_PASSWORD;
		String nickName = sec.getNickName();
		ObjectNode datanode = JsonNodeFactory.instance.objectNode();
		datanode.put("username", username);
		datanode.put("password", defaultPassword);
		if (sec.getName() != null && sec.getName().length() > 0) {
			datanode.put("nickname", sec.getName());
		}
		// datanode.put("nickname", nickName);
		ObjectNode createNewIMUserSingleNode = EasemobIMUsers
				.createNewIMUserSingle(datanode);

		JsonNode statusCode = createNewIMUserSingleNode.get("statusCode");
		if (!statusCode.toString().equals("200"))
			return record;

		JsonNode entity = createNewIMUserSingleNode.get("entities");
		String uuid = entity.get(0).get("uuid").toString();
		// username = entity.get(0).get("username").toString();

		record.setId(0L);
		record.setSecId(secId);
		record.setRefType(Constants.IM_PROVIDE);
		record.setMobile(sec.getMobile());
		record.setUsername(username);
		record.setPassword(defaultPassword);
		record.setRefPrimaryKey(uuid);
		record.setAddTime(TimeStampUtil.getNowSecond());
		secRef3rdMapper.insert(record);
		return record;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return secMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Sec initSec() {
		Sec sec = new Sec();
		sec.setId(0L);
		sec.setName("");
		sec.setMobile("");
		sec.setNickName("");
		sec.setSex("");
		sec.setBirthDay(null);
		sec.setHeadImg("");
		sec.setCityId(0L);
		sec.setStatus(null);
		sec.setAddTime(TimeStampUtil.getNow() / 1000);
		sec.setUpdateTime(0L);

		return sec;
	}

	// 判断秘书名称是否重复
	@Override
	public Sec selectByUserNameAndOtherId(String name, Long id) {
		HashMap map = new HashMap();
		map.put("name", name);
		map.put("id", id);
		return secMapper.selectByNameAndOtherId(map);
	}

	@Override
	public Sec selectByMobile(String mobile) {

		return secMapper.selectByMobile(mobile);

	}

	@Override
	public Sec selectVoBySecId(Long secId) {
		return secMapper.selectVoBySecId(secId);
	}

	@Override
	public int updateByPrimaryKeySelective(Sec record) {
		return secMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 获取秘书第三方账号信息
	 * 
	 * @param Long
	 *            secId 秘书ID
	 * @return SecRef3rd
	 */
	@Override
	public SecRef3rd selectBySecIdForIm(Long secId) {
		return secRef3rdMapper.selectBySecIdForIm(secId);
	}

	@Override
	public Sec getUserById(Long secId) {

		return secMapper.selectByPrimaryKey(secId);
	}

	@Override
	public SecInfoVo changeSecToVo(Sec sec) {

		SecInfoVo secInfoVo = new SecInfoVo();
		try {
			BeanUtils.copyProperties(secInfoVo, sec);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		secInfoVo.setBirthDay(DateUtil.getDefaultDate(sec.getBirthDay()));

		if (secInfoVo.getCityId() > 0L) {

			String cityNameString = dictService.getCityName(secInfoVo
					.getCityId());
			secInfoVo.setCityName(cityNameString);
		}
		
		SecRef3rd secRef3rd = this.selectBySecIdForIm(sec.getId());
		secInfoVo.setImUserName(secRef3rd.getUsername());
		secInfoVo.setImPassword(secRef3rd.getPassword());

		return secInfoVo;

	}
	
	
	@Override
	public List<SecViewVo> changeToSecViewVos(List<Users> userList) {
		List<SecViewVo> result = new ArrayList<SecViewVo>();
		
		if (userList.isEmpty()) return result;
		
		List<Long> userIds = new ArrayList<Long>();
		for (Users item: userList) {
			if (!userIds.contains(item.getId()))
				userIds.add(item.getId());
		}
		
		List<UserRef3rd> userRef3Rds = userRef3rdService.selectByUserIds(userIds);
				
		Users item = null;
		for (int i =0; i < userList.size(); i++) {
			item = userList.get(i);
			SecViewVo vo = new SecViewVo();
			
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			vo.setSecId(item.getId());
			vo.setDescription("");
			for(UserRef3rd ur : userRef3Rds) {
				if (ur.getUserId().equals(item.getId())) {
					vo.setImUserName(ur.getUsername());
					break;
				}
			}
			result.add(vo);
		}
		
		return result;
	}		

}
