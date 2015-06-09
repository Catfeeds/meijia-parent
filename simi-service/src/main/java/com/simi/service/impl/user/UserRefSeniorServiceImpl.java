package com.simi.service.impl.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.admin.AdminAccountService;
import com.simi.service.user.UserRefSeniorService;
import com.simi.vo.admin.SeniorSearchVo;
import com.simi.vo.user.UserRefSeniorVo;
import com.simi.po.dao.user.UserRefSeniorMapper;
import com.simi.po.dao.user.UsersMapper;
import com.simi.po.model.admin.AdminAccount;
import com.simi.po.model.user.UserRefSenior;
import com.simi.po.model.user.Users;
import com.meijia.utils.TimeStampUtil;

@Service
public class UserRefSeniorServiceImpl implements UserRefSeniorService {

	@Autowired
	private AdminAccountService adminAccountService;

	@Autowired
	private UserRefSeniorMapper userRefSeniorMapper;

	@Autowired
	private UsersMapper usersMapperr;

	@Override
	public UserRefSenior selectByPrimaryKey(Long id) {
		return userRefSeniorMapper.selectByPrimaryKey(id);
	}

	@Override
	public int insert(UserRefSenior record) {
		return userRefSeniorMapper.insert(record);
	}

	@Override
	public int updateByPrimaryKeySelective(UserRefSenior record) {
		return userRefSeniorMapper.updateByPrimaryKeySelective(record);
	}

	/*
	 * 初始化用户对象
	 */
	@Override
	public UserRefSenior initUserRefSenior() {
		UserRefSenior record = new UserRefSenior();
		record.setId(0L);
		record.setUserId(0L);
		record.setUserMobile("");
		record.setSeniorId(0L);
		record.setAddTime(TimeStampUtil.getNow() / 1000);
		return record;
	}

	/**
	 * 购买管家卡后，分配真人管家
	 */
	@Override
	public Boolean allotSenior(Users user) {


Long userId = user.getId();

		//如果之前已经分配过管家，则不需要再分配
		UserRefSenior record = userRefSeniorMapper.selectByUserId(userId);
		if (record != null) {
			return true;
		}

		Long adminId = 0L;

		List<HashMap> statBySenior = userRefSeniorMapper.statBySeniorId();

		if (statBySenior == null || statBySenior.size() <= 0) {
			return false;
		}
		String seniorId = statBySenior.get(0).get("seniorId").toString();
		adminId = Long.valueOf(seniorId);



		record = this.initUserRefSenior();
		record.setUserId(userId);
		record.setSeniorId(adminId);
		record.setUserMobile(user.getMobile());

		userRefSeniorMapper.insert(record);
		return true;
	}

	@Override
	public PageInfo searchVoListPage(SeniorSearchVo seniorSearchVo, int pageNo,
			int pageSize) {

		String userMobile = seniorSearchVo.getUserMobile();

		Map<String, Object> conditions = new HashMap<String, Object>();
		if (userMobile != null && !userMobile.isEmpty()) {
			conditions.put("userMobile", userMobile.trim());
		}
		PageHelper.startPage(pageNo, pageSize);
		List<UserRefSenior> list = userRefSeniorMapper
				.searchListByConditions(conditions);

		if (list != null && list.size() > 0) {
			List<UserRefSeniorVo> userRefSeniorVoList = this
					.getSeniorViewList(list);
			for (int i = 0; i < list.size(); i++) {
				if (userRefSeniorVoList.get(i) != null) {
					list.set(i, userRefSeniorVoList.get(i));
				}
			}
		}

		PageInfo result = new PageInfo(list);
		return result;
	}

	@Override
	public List<UserRefSeniorVo> getSeniorViewList(List<UserRefSenior> list) {
		List<Long> userIds = new ArrayList<Long>();
		List<Long> seniorIds = new ArrayList<Long>();
		UserRefSenior item = null;
		// 将UserRefSenior中的userId放到List集合中
		for (int i = 0; i < list.size(); i++) {
			item = list.get(i);
			userIds.add(item.getUserId());
			seniorIds.add(item.getSeniorId());
		}
		// 根据userIds查询出对应的Users
		List<Users> usersList = usersMapperr.selectByUserIds(userIds);
		List<AdminAccount> adminAccounts = adminAccountService.selectByIds(seniorIds);
		List<UserRefSeniorVo> result = new ArrayList<UserRefSeniorVo>();
		Long userId = 0L;
		Long seniorId = 0L;
		// 将UserRefSenior中的userId和Users中的Id进行比较，相同则为userName赋值
		for (int i = 0; i < list.size(); i++) {
			item = list.get(i);
			userId = item.getUserId();
			seniorId = item.getSeniorId();

			UserRefSeniorVo vo = new UserRefSeniorVo();
			BeanUtils.copyProperties(item, vo);

			String userName = "";
			Users users = null;
			for (int n = 0; n < usersList.size(); n++) {
				users = usersList.get(n);
				if (users.getId().equals(userId)) {
					userName = users.getName();
					break;
				}
			}

			//根据seniorId获得AdminAccount表中的名字
			String seniorName ="";
			AdminAccount adminAccount = null;
			for (int n = 0; n < adminAccounts.size(); n++) {
				adminAccount = adminAccounts.get(n);
				if (adminAccount.getId().equals(seniorId)) {
					seniorName = adminAccount.getName();
					break;
				}
			}

			vo.setSeniorName(seniorName);
			vo.setUserName(userName);
			result.add(vo);
		}
		return result;
	}

	@Override
	public List<UserRefSenior> selectBySeniorId(Long seniorId) {
		return userRefSeniorMapper.selectBySeniorId(seniorId);
	}

}