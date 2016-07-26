package com.simi.service.impl.partners;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.partners.PartnerUsersMapper;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.partners.PartnerUsers;
import com.simi.po.model.partners.Partners;
import com.simi.po.model.user.TagUsers;
import com.simi.po.model.user.Tags;
import com.simi.po.model.user.Users;
import com.simi.service.dict.DictUtil;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.partners.PartnerUserService;
import com.simi.service.partners.PartnersService;
import com.simi.service.user.TagsService;
import com.simi.service.user.TagsUsersService;
import com.simi.service.user.UserImgService;
import com.simi.service.user.UsersService;
import com.simi.vo.partners.PartnerUserSearchVo;
import com.simi.vo.partners.PartnerUserVo;

@Service
public class PartnerUserServiceImpl implements PartnerUserService {

	@Autowired
	private PartnerUsersMapper partnerUsersMapper;

	@Autowired
	private UsersService userService;

	@Autowired
	private PartnersService partnersService;

	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;

	@Autowired
	private TagsService tagsService;

	@Autowired
	private TagsUsersService tagsUsersService;

	@Autowired
	private UserImgService userImgService;

	@Override
	public PartnerUsers iniPartnerUsers() {
		PartnerUsers vo = new PartnerUsers();
		vo.setId(0L);
		vo.setPartnerId(0L);
		vo.setUserId(0L);
		vo.setServiceTypeId(0L);
		vo.setWeightType((short) 0);
		vo.setWeightNo((short) 0);
		vo.setTotalOrder((short) 0);
		vo.setTotalRate(new BigDecimal(0));
		vo.setTotalRateResponse(new BigDecimal(0));
		vo.setTotalRateAttitude(new BigDecimal(0));
		vo.setTotalRateMajor(new BigDecimal(0));
		vo.setResponseTime((short) 0);
		vo.setProvinceId(0L);
		vo.setCityId(0L);
		vo.setRegionId(0L);
		vo.setAddTime(TimeStampUtil.getNow() / 1000);
		return vo;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return partnerUsersMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(PartnerUsers record) {
		return partnerUsersMapper.insert(record);
	}

	@Override
	public int insertSelective(PartnerUsers record) {
		return partnerUsersMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(PartnerUsers record) {
		return partnerUsersMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(PartnerUsers record) {
		return partnerUsersMapper.updateByPrimaryKey(record);
	}

	@Override
	public PartnerUsers selectByPrimaryKey(Long id) {
		return partnerUsersMapper.selectByPrimaryKey(id);
	}

	@Override
	public PartnerUsers selectByUserId(Long userId) {
		return partnerUsersMapper.selectByUserId(userId);
	}

	@Override
	public PageInfo selectByListPage(PartnerUserSearchVo partnersSearchVo, int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		List<PartnerUsers> list = partnerUsersMapper.selectByListPage(partnersSearchVo);

		// List<PartnerUserVo> resultList = new ArrayList<PartnerUserVo>();
		for (int i = 0; i < list.size(); i++) {
			PartnerUsers item = list.get(i);
			PartnerUserVo vo = this.changeToVo(item);
			list.set(i, vo);
		}
		PageInfo pageInfo = new PageInfo(list);
		return pageInfo;

	}

	@Override
	public PartnerUserVo changeToVo(PartnerUsers partnerUser) {

		PartnerUserVo result = new PartnerUserVo();

		BeanUtilsExp.copyPropertiesIgnoreNull(partnerUser, result);

		Long userId = partnerUser.getUserId();
		Long partnerId = partnerUser.getPartnerId();
		Long serviceTypeId = partnerUser.getServiceTypeId();

		Users u = userService.selectByPrimaryKey(userId);
		Partners partner = partnersService.selectByPrimaryKey(partnerId);
		PartnerServiceType partnerServiceType = partnerServiceTypeService.selectByPrimaryKey(serviceTypeId);

		result.setCompanyName(partner.getCompanyName());
		result.setServiceTypeName(partnerServiceType.getName());
		result.setName(u.getName());
		result.setMobile(u.getMobile());
		result.setHeadImg(u.getHeadImg());
		// 权重类型名称
		if (result.getWeightType() == 0) {
			result.setWeightTypeName("默认");
		}
		if (result.getWeightType() == 1) {
			result.setWeightTypeName("推荐");
		}
		if (result.getWeightType() == 2) {
			result.setWeightTypeName("优惠");
		}
		if (result.getWeightType() == 3) {
			result.setWeightTypeName("新人");
		}
		result.setResponseTime(partnerUser.getResponseTime());
		result.setResponseTimeName(MeijiaUtil.getResponseTimeName(partnerUser.getResponseTime()));
		result.setIntroduction(u.getIntroduction());
		// 标签
		List<TagUsers> tagUsers = tagsUsersService.selectByUserId(userId);
		List<Tags> tags = tagsService.selectAll();
		List<Tags> userTags = new ArrayList<Tags>();
		for (TagUsers item : tagUsers) {
			for (Tags vo : tags) {
				if (item.getTagId().equals(vo.getTagId())) {
					userTags.add(vo);
					break;
				}
			}
		}
		result.setUserTags(userTags);

		// 图片
		// List<UserImgs> userImgs = userImgService.selectByUserId(userId);
		// if (userImgs == null) {
		// userImgs = new ArrayList<UserImgs>();
		// }
		// result.setUserImgs(userImgs);

		// 城市-区县
		String cityName = DictUtil.getCityName(partnerUser.getCityId());
		String regionName = DictUtil.getRegionName(partnerUser.getRegionId());
		result.setCityAndRegion(cityName + " " + regionName);

		return result;

	}

	@Override
	public PartnerUsers selectByServiceTypeIdAndPartnerId(Long serviceTypeId, Long partnerId) {

		return partnerUsersMapper.selectByServiceTypeIdAndPartnerId(serviceTypeId, partnerId);
	}

	@Override
	public List<PartnerUsers> selectByPartnerId(Long partnerId) {

		return partnerUsersMapper.selectByPartnerId(partnerId);
	}

	@Override
	public List<PartnerUsers> selectBySearchVo(PartnerUserSearchVo partnersSearchVo) {
		return partnerUsersMapper.selectBySearchVo(partnersSearchVo);
	}

	@Override
	public List<HashMap> totalUserByPartnerIds(List<Long> partnerIds) {
		return partnerUsersMapper.totalUserByPartnerIds(partnerIds);
	}

}