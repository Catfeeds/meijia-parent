package com.simi.service.sec;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.sec.Sec;
import com.simi.po.model.sec.SecRef3rd;
import com.simi.po.model.user.Users;
import com.simi.vo.sec.SecInfoVo;
import com.simi.vo.sec.SecViewVo;

public interface SecService {

	Long insertSelective(Sec record);

	PageInfo searchVoListPage(int pageNo, int pageSize);

	int deleteByPrimaryKey(Long id);

	Sec initSec();

	Sec selectByUserNameAndOtherId(String name, Long id);

	Sec selectByMobile(String mobile);

	SecRef3rd genImSec(Sec sec);

	SecRef3rd selectBySecIdForIm(Long secId);

	Sec selectVoBySecId(Long secId);

	int updateByPrimaryKeySelective(Sec record);

	Sec getUserById(Long secId);

	SecInfoVo changeSecToVo(Sec sec);

	List<SecViewVo> changeToSecViewVos(List<Users> userList);







}
