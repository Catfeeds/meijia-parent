package com.simi.service.impl.dict;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.dict.CouponService;
import com.simi.vo.dict.CouponSearchVo;
import com.simi.po.dao.dict.DictCouponsMapper;
import com.simi.po.model.dict.DictCoupons;
import com.meijia.utils.TimeStampUtil;

@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
	private DictCouponsMapper dictCouponsMapper;


	/*
	 * 获取表dict_coupons的所有数据
	 * @param
	 * @return  List<DictServiceTypes>
	 */
	@Override
	public List<DictCoupons> selectAll() {
		return dictCouponsMapper.selectAll();
	}

	@Override
	public List<DictCoupons> selectByIds(List<Long> ids) {
		return dictCouponsMapper.selectByIds(ids);
	}

	@Override
	public DictCoupons selectByPrimaryKey(Long id) {
		return dictCouponsMapper.selectByPrimaryKey(id);
	}

	@Override
	public DictCoupons selectByCardPasswd(String cardPasswd) {
		return dictCouponsMapper.selectByCardPasswd(cardPasswd);
	}

	@Override
	public int insert(DictCoupons record) {
		return dictCouponsMapper.insert(record);
	}

	@Override
	public int updateByPrimaryKeySelective(DictCoupons record) {
		return dictCouponsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
    public int updateByPrimaryKey(DictCoupons record) {
		return dictCouponsMapper.updateByPrimaryKey(record);
	}

	@Override
	public DictCoupons initCoupon(String cardNo, String cardPasswd) {

		DictCoupons po = new DictCoupons();
		po.setId(0L);
		po.setCardNo(cardNo);
		po.setCardPasswd(cardPasswd);
		po.setValue(new BigDecimal(0));
		po.setRangType((short) 0);
		po.setRangFrom((short) 0);
		po.setServiceType("0");
		po.setIntroduction("");
		po.setDescription("");
		po.setExpTime(0L);
		po.setAddTime(TimeStampUtil.getNow() / 1000);
		po.setUpdateTime(TimeStampUtil.getNow() / 1000);
		return po;
	}

	@Override
	public PageInfo searchVoListPage(CouponSearchVo searchVo,
			int pageNo, int pageSize) {
		HashMap<String,Object> conditions = new HashMap<String,Object>();
		String cardNo = searchVo.getCardNo();
		 String cardPasswd = searchVo.getCardPasswd();

		if(cardPasswd !=null && !cardPasswd.isEmpty() ){
			conditions.put("cardPasswd", cardPasswd.trim());
		}
		if(cardNo !=null && !cardNo.isEmpty()){
			conditions.put("cardNo",cardNo.trim());
		}

		 PageHelper.startPage(pageNo, pageSize);
         List<DictCoupons> list = dictCouponsMapper.selectByListPage(conditions);
        /*
         if(list!=null && list.size()!=0){
             List<OrderViewVo> orderViewList = this.getOrderViewList(list);

             for(int i = 0; i < list.size(); i++) {
            	 if (orderViewList.get(i) != null) {
            		 list.set(i, orderViewList.get(i));
            	 }
             }
         }*/
        PageInfo result = new PageInfo(list);
		return result;
	}

	@Override
	public List<DictCoupons> selectAllByCardNo() {
		return dictCouponsMapper.selectAllByCardNo();
	}

	@Override
	public int insertSelective(DictCoupons record) {
		return dictCouponsMapper.insertSelective(record);
	}


}
