package com.simi.action.app.user;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserAddrs;
import com.simi.po.model.user.Users;
import com.simi.service.dict.CityService;
import com.simi.service.user.UserAddrsService;
import com.simi.service.user.UsersService;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/app/user")
public class UserAddressController extends BaseController {

	@Autowired
	private UsersService usersService;
	@Autowired
	private UserAddrsService userAddrsService;

	@Autowired
	private CityService cityService;

	// 10. 地址提交接口
	/**
	 * addr_id0表示新增，>0表示修改 mobile手机号 city_id城市ID cell_id小区ID address门牌号
	 * is_default 是否默认0=不默认1=默认
	 */
	@RequestMapping(value = "post_addrs", method = RequestMethod.POST)
	public AppResultData<UserAddrs> saveAddress(

			@RequestParam("user_id") Long userId,
			@RequestParam("addr_id") Long addrId,
			@RequestParam("poi_type") Short poiType,
			@RequestParam("name") String name,
			@RequestParam("address") String address,
			@RequestParam("longitude") String longitude,
			@RequestParam("latitude") String latitude,
			@RequestParam("city") String city,
			@RequestParam("uid") String uid,
			@RequestParam("addr") String addr,
			@RequestParam("is_default") Short isDefault,
			@RequestParam(value = "phone", required = false, defaultValue="") String phone,
			@RequestParam(value = "post_code", required = false, defaultValue="") String postCode,
			@RequestParam(value = "city_id", required = false, defaultValue="0") String cityId,
			@RequestParam(value = "cell_id", required = false, defaultValue="0") Long cellId
			) {

		try {
			addr = URLDecoder.decode(addr, Constants.URL_ENCODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			AppResultData<UserAddrs> result = new AppResultData<UserAddrs>(
					Constants.ERROR_100, ConstantMsg.ERROR_100_MSG,
					new UserAddrs());
			return result;
		}

		UserAddrs userAddrs = new UserAddrs();
		if (addrId > 0L) {
			//addrId > 0 表明改地址已经存在
			userAddrs = userAddrsService.selectByPrimaryKey(addrId);
		}

		//Users u = usersService.getUserByMobile(mobile);
		Users u  = usersService.selectByPrimaryKey(userId);
		userAddrs.setId(addrId);
		userAddrs.setUserId(u.getId());
		userAddrs.setMobile(u.getMobile());
		userAddrs.setAddr(addr);
		userAddrs.setLongitude(longitude);
		userAddrs.setLatitude(latitude);
		userAddrs.setPoiType(poiType);
		userAddrs.setName(name);
		userAddrs.setAddress(address);
		userAddrs.setCity(city);
		userAddrs.setUid(uid);
		userAddrs.setPhone(phone);
		userAddrs.setPostCode(postCode);
		userAddrs.setUpdateTime(TimeStampUtil.getNow() / 1000);
		userAddrs.setIsDefault(isDefault);
		userAddrs.setCellId(cellId);

		if (addrId.equals(0L)) {
			userAddrs.setAddTime(TimeStampUtil.getNow() / 1000);
		}

		// 如果当前的地址设置为默认，则取消其他默认，并设置当前为默认
		if (isDefault.equals((short)1)) {
			//todo  把当前用户的所有地址 is_default设置为0
			// update user_addr set is_default =0 where mobile = ?
			userAddrsService.updataDefaultByUserId(userId);
		}

		if (addrId > 0L) {
			userAddrsService.updateByPrimaryKey(userAddrs);
		} else {
			userAddrsService.insertSelective(userAddrs);
		}

		AppResultData<UserAddrs> result = new AppResultData<UserAddrs>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, userAddrs);
		return result;
	}

	// 11. 地址删除接口
	/**
	 * addr_id地址ID mobile手机号
	 */
	@RequestMapping(value = "post_del_addrs", method = RequestMethod.POST)
	public AppResultData<String> delAddress(
			@RequestParam("user_id") Long userId,
			@RequestParam("addr_id") int addr_id) {
		UserAddrs userAddrs = userAddrsService.selectByPrimaryKey(Long
				.valueOf(addr_id));

		AppResultData<String> result = new AppResultData<String>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		if (userAddrs == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_ADDR_NOT_EXIST_MG);
			return result;
		}

		userAddrsService.deleteByPrimaryKey(Long.valueOf(addr_id));

		return result;
	}

	// 11. 地址列表接口
	/**
	 * addr_id地址ID mobile手机号
	 */
	@RequestMapping(value = "get_addrs", method = RequestMethod.GET)
	public AppResultData<List> getAddress(@RequestParam("user_id") Long userId) {
		List<UserAddrs> list = userAddrsService.selectByUserId(userId);

		AppResultData<List> result = new AppResultData<List>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, list);
		return result;
	}
	//通过用户手机号获取地址列表接口
	@RequestMapping(value = "get_addrs_by_mobile", method = RequestMethod.GET)
	public AppResultData<List> getAddress(@RequestParam("mobile") String mobile) {
		Users users = usersService.selectByMobile(mobile);
		
		List<UserAddrs> list = userAddrsService.selectByUserId(users.getId());

		AppResultData<List> result = new AppResultData<List>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, list);
		return result;
	}
}
