package com.simi.service.impl.sec;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.BeanUtilsExp;
import com.simi.po.dao.user.UserRef3rdMapper;
import com.simi.po.model.user.TagUsers;
import com.simi.po.model.user.Tags;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.Users;
import com.simi.service.dict.DictService;
import com.simi.service.sec.SecService;
import com.simi.service.user.TagsService;
import com.simi.service.user.TagsUsersService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UserRefSecService;
import com.simi.service.user.UsersService;
import com.simi.vo.sec.SecViewVo;

@Service
public class SecServiceImpl implements SecService {

	@Autowired
	private UsersService userService;	
	
	@Autowired
	private DictService dictService;

	@Autowired
	private UserRefSecService userRefSecService;

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private UserRef3rdService userRef3rdService;	

	@Autowired
	private UserRef3rdMapper userRef3rdMapper;
	
	@Autowired
	private TagsService tagsService;
	
	@Autowired
	private TagsUsersService tagsUsersService;	

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
		List<Tags> tagsAll = tagsService.selectAll();
		List<TagUsers> tagUsers = tagsUsersService.selectByUserIds(userIds);
				
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
			
			//设置tags
			List<Tags> tags = new ArrayList<Tags>();
			for (TagUsers tu : tagUsers) {
				for (Tags t : tagsAll) {
					if (tu.getTagId().equals(t.getTagId())) {
						tags.add(t);
						break;
					}
				}
			}
			
			
			result.add(vo);
		}
		
		return result;
	}		

}
