package com.simi.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meijia.utils.DateUtil;
import com.meijia.utils.GsonUtil;
import com.meijia.utils.MathBigDecimalUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.weather.WeatherDataVo;
import com.meijia.utils.weather.WeatherIndexVo;
import com.meijia.utils.weather.WeatherInfoVo;
import com.meijia.utils.weather.WeatherResultVo;
import com.meijia.utils.weather.WeatherUtil;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.dao.common.WeathersMapper;
import com.simi.po.model.common.Weathers;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.service.MathToolsService;
import com.simi.service.WeatherService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.vo.AppResultData;
import com.simi.vo.setting.InsuranceBaseVo;
import com.simi.vo.setting.InsuranceVo;
import com.simi.vo.setting.TaxVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;


@Service
public class MathToolsServiceImpl implements MathToolsService {
		
	@Autowired
	private XCompanySettingService xCompanySettingService;
	
	/**
	 * 计算五险一金
	 */
	@Override
	public AppResultData<Object> mathInsurance(Long cityId, Double shebao, Double gjj) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		if(cityId <= 0L || cityId == null){
			
			result.setStatus(Constants.ERROR_999);
			result.setMsg("城市不存在");
			return result;
		}
		
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();
		
		//社保公积金基数，setting_type = "insurance"
		searchVo.setSettingType(Constants.SETTING_TYPE_INSURANCE);
		
		searchVo.setCityId(cityId);

//		searchVo.setRegionId(regionId);

		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);
		
		if (list.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("地区数据不存在");
			return result;
		}
		
		XcompanySetting xcompanySetting = list.get(0);

		JSONObject setValue = (JSONObject) xcompanySetting.getSettingValue();

		InsuranceVo insuranceVo = JSON.toJavaObject(setValue, InsuranceVo.class);
		
		searchVo = new CompanySettingSearchVo();
		
		//社保公积金基数，setting_type = "insurance"
		searchVo.setSettingType(Constants.SETTING_TYPE_INSURANCE_BASE);
		
		searchVo.setCityId(cityId);

		list = xCompanySettingService.selectBySearchVo(searchVo);
		
		if (list.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("地区数据不存在");
			return result;
		}
		
		xcompanySetting = list.get(0);
		
		setValue = (JSONObject) xcompanySetting.getSettingValue();
		
		InsuranceBaseVo insuranceBaseVo = JSON.toJavaObject(setValue, InsuranceBaseVo.class);
		
		
		Map<String, String> data = new HashMap<String, String>();
		
		Double shebaoMin = Double.valueOf(insuranceBaseVo.getShebaoMin());
		Double shebaoMax = Double.valueOf(insuranceBaseVo.getShebaoMax());
		
		if (shebao < shebaoMin) shebao = shebaoMin;
		if (shebao > shebaoMax) shebao = shebaoMax;
		
		Double gjjMin = Double.valueOf(insuranceBaseVo.getGjjMin());
		Double gjjMax = Double.valueOf(insuranceBaseVo.getGjjMax());
		
		if (gjj < gjjMin) gjj = gjjMin;
		if (gjj > gjjMax) gjj = gjjMax;
		
		//个人部分.
		Double persionPio = Double.valueOf(insuranceVo.getPensionP());
		BigDecimal bpensionP = MathBigDecimalUtil.mul(new BigDecimal(shebao), new BigDecimal(persionPio/ 100));
		String pernsionP = MathBigDecimalUtil.round2(bpensionP);
		data.put("pensionP", pernsionP);
		data.put("pensionPio", insuranceVo.getPensionP());
		
		Double medicalPio = Double.valueOf(insuranceVo.getMedicalP());
		BigDecimal bmedicalP = MathBigDecimalUtil.mul(new BigDecimal(shebao), new BigDecimal(medicalPio/ 100));
		String medicalP = MathBigDecimalUtil.round2(bmedicalP);
		data.put("medicalP", medicalP);
		data.put("medicalPio", insuranceVo.getMedicalP());
		
		Double unemploymentPio = Double.valueOf(insuranceVo.getUnemploymentP());
		BigDecimal bunemploymentP = MathBigDecimalUtil.mul(new BigDecimal(shebao), new BigDecimal(unemploymentPio/ 100));
		String unemploymentP = MathBigDecimalUtil.round2(bunemploymentP);
		data.put("unemploymentP", unemploymentP);
		data.put("unemploymentPio", insuranceVo.getUnemploymentP());
		
		Double injuryPio = Double.valueOf(insuranceVo.getInjuryP());
		BigDecimal binjuryP = MathBigDecimalUtil.mul(new BigDecimal(shebao), new BigDecimal(injuryPio/ 100));
		String injuryP = MathBigDecimalUtil.round2(binjuryP);
		data.put("injuryP", injuryP);
		data.put("injuryPio", insuranceVo.getInjuryP());
		
		Double birthPio = Double.valueOf(insuranceVo.getBirthP());
		BigDecimal bbirthP = MathBigDecimalUtil.mul(new BigDecimal(shebao), new BigDecimal(birthPio/ 100));
		String birthP = MathBigDecimalUtil.round2(bbirthP);
		data.put("birthP", birthP);
		data.put("birthPio", insuranceVo.getBirthP());
		
		Double fundPio = Double.valueOf(insuranceVo.getFundP());
		BigDecimal bfundP = MathBigDecimalUtil.mul(new BigDecimal(shebao), new BigDecimal(fundPio/ 100));
		String fundP = MathBigDecimalUtil.round2(bfundP);
		data.put("fundP", fundP);
		data.put("fundPio", insuranceVo.getFundP());
		
		//个人合计
		Double totalPio = persionPio + unemploymentPio + injuryPio + birthPio + medicalPio;
		BigDecimal btotalP = MathBigDecimalUtil.mul(new BigDecimal(shebao), new BigDecimal(totalPio/ 100));
		btotalP = MathBigDecimalUtil.add(btotalP, bfundP);
		btotalP = MathBigDecimalUtil.add(btotalP, new BigDecimal(3));
		String totalP = MathBigDecimalUtil.round2(btotalP);
		data.put("totalP", totalP);
		
		//公司部分
		Double persionCio = Double.valueOf(insuranceVo.getPensionC());
		BigDecimal bpensionC = MathBigDecimalUtil.mul(new BigDecimal(shebao), new BigDecimal(persionCio/ 100));
		String pernsionC = MathBigDecimalUtil.round2(bpensionC);
		data.put("pensionC", pernsionC);
		data.put("persionCio", insuranceVo.getPensionC());
		
		Double medicalCio = Double.valueOf(insuranceVo.getMedicalC());
		BigDecimal bmedicalC = MathBigDecimalUtil.mul(new BigDecimal(shebao), new BigDecimal(medicalCio/ 100));
		String medicalC = MathBigDecimalUtil.round2(bmedicalC);
		data.put("medicalC", medicalC);
		data.put("medicalCio", insuranceVo.getMedicalC());
		
		Double unemploymentCio = Double.valueOf(insuranceVo.getUnemploymentC());
		BigDecimal bunemploymentC = MathBigDecimalUtil.mul(new BigDecimal(shebao), new BigDecimal(unemploymentCio/ 100));
		String unemploymentC = MathBigDecimalUtil.round2(bunemploymentC);
		data.put("unemploymentC", unemploymentC);
		data.put("unemploymentCio", insuranceVo.getUnemploymentC());
		
		Double injuryCio = Double.valueOf(insuranceVo.getInjuryC());
		BigDecimal binjuryC = MathBigDecimalUtil.mul(new BigDecimal(shebao), new BigDecimal(injuryCio/ 100));
		String injuryC = MathBigDecimalUtil.round2(binjuryC);
		data.put("injuryC", injuryC);
		data.put("injuryCio", insuranceVo.getInjuryC());
		
		Double birthCio = Double.valueOf(insuranceVo.getBirthC());
		BigDecimal bbirthC = MathBigDecimalUtil.mul(new BigDecimal(shebao), new BigDecimal(birthCio/ 100));
		String birthC = MathBigDecimalUtil.round2(bbirthC);
		data.put("birthC", birthC);
		data.put("birthCio", insuranceVo.getBirthC());
		
		Double fundCio = Double.valueOf(insuranceVo.getFundC());
		BigDecimal bfundC = MathBigDecimalUtil.mul(new BigDecimal(shebao), new BigDecimal(fundCio/ 100));
		String fundC = MathBigDecimalUtil.round2(bfundC);
		data.put("fundC", fundC);
		data.put("fundCio", insuranceVo.getFundC());
		
		
		//公司合计
		Double totalCio = persionCio + unemploymentCio + injuryCio + birthCio + medicalCio;
		BigDecimal btotalC = MathBigDecimalUtil.mul(new BigDecimal(shebao), new BigDecimal(totalCio/ 100));
		btotalC = MathBigDecimalUtil.add(btotalC, bfundC);
		btotalC = MathBigDecimalUtil.add(btotalC, new BigDecimal(3));
		String totalC = MathBigDecimalUtil.round2(btotalC);
		data.put("totalC", totalC);
		
		
		result.setData(data);
		
		return result;
	}
	

	
	
	/**
	 * 计算个人所得税
	 */
	@Override
	public AppResultData<Object> mathTaxPersion(Double salary, Double insurance, Double beginTax) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Map<String, String> data = new HashMap<String, String>();
		
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();

		searchVo.setSettingType(Constants.SETTING_TYPE_TAX_PERSION);

		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);

		if (list.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("数据不存在!");
		}
		
		
		
		//应纳税所得额
		Double taxBase = salary - insurance - beginTax;
		
		if (taxBase < 0 ) {
			data.put("taxNeed", "0");
			data.put("taxedSalary", String.valueOf(salary));
			result.setData(data);
			return result;
		}
		
		Double taxRio = 0.0;
		Double taxSs = 0.0;
		
		for (XcompanySetting item : list) {
			JSONObject setValue = (JSONObject) item.getSettingValue();
			TaxVo taxVo = JSON.toJavaObject(setValue, TaxVo.class);
			
			Double taxMin = 0.0;
			Double taxMax = 0.0;
			
			if (StringUtil.isEmpty(taxVo.getTaxMin())) {
				taxMax = Double.valueOf(taxVo.getTaxMax());
				if (taxBase > taxMax) {
					taxRio = Double.valueOf(taxVo.getTaxRio());
					taxSs = Double.valueOf(taxVo.getTaxSs());
					break;
				}
			} else if (StringUtil.isEmpty(taxVo.getTaxMax())) {
				taxMin = Double.valueOf(taxVo.getTaxMin());
				if (taxBase < taxMin) {
					taxRio = Double.valueOf(taxVo.getTaxRio());
					taxSs = Double.valueOf(taxVo.getTaxSs());
					break;
				}
			} else  {
				taxMin = Double.valueOf(taxVo.getTaxMin());
				taxMax = Double.valueOf(taxVo.getTaxMax());
				
				if (taxBase > taxMin && taxBase < taxMax) {
					taxRio = Double.valueOf(taxVo.getTaxRio());
					taxSs = Double.valueOf(taxVo.getTaxSs());
					break;
				}
			}
		}
		
		//应纳税额
		Double taxNeed = taxBase * (taxRio / 100)  - taxSs;
		String taxNeedStr = MathBigDecimalUtil.round2(new BigDecimal(taxNeed));
		
		
		Double taxedSalary = salary - taxNeed - insurance;
		String taxedSalaryStr = MathBigDecimalUtil.round2(new BigDecimal(taxedSalary));
		
		data.put("taxNeed", taxNeedStr);
		data.put("taxedSalary", taxedSalaryStr);
		result.setData(data);

		return result;
	}	
	
	/**
	 * 计算年终奖
	 */
	@Override
	public AppResultData<Object> mathTaxYear(Double money) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Map<String, String> data = new HashMap<String, String>();
		
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();

		searchVo.setSettingType(Constants.SETTING_TYPE_TAX_YEAR_AWARD);

		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);

		if (list.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("数据不存在!");
		}
		
		
		
		//应纳税所得额
		Double taxBase = money / 12;
		
		if (taxBase < 0 ) {
			data.put("taxNeed", "0");
			data.put("taxedSalary", String.valueOf(money));
			result.setData(data);
			return result;
		}
		
		Double taxRio = 0.0;
		Double taxSs = 0.0;
		
		for (XcompanySetting item : list) {
			JSONObject setValue = (JSONObject) item.getSettingValue();
			TaxVo taxVo = JSON.toJavaObject(setValue, TaxVo.class);
			
			Double taxMin = 0.0;
			Double taxMax = 0.0;
			
			if (StringUtil.isEmpty(taxVo.getTaxMin())) {
				taxMax = Double.valueOf(taxVo.getTaxMax());
				if (taxBase > taxMax) {
					taxRio = Double.valueOf(taxVo.getTaxRio());
					taxSs = Double.valueOf(taxVo.getTaxSs());
					break;
				}
			} else if (StringUtil.isEmpty(taxVo.getTaxMax())) {
				taxMin = Double.valueOf(taxVo.getTaxMin());
				if (taxBase < taxMin) {
					taxRio = Double.valueOf(taxVo.getTaxRio());
					taxSs = Double.valueOf(taxVo.getTaxSs());
					break;
				}
			} else  {
				taxMin = Double.valueOf(taxVo.getTaxMin());
				taxMax = Double.valueOf(taxVo.getTaxMax());
				
				if (taxBase > taxMin && taxBase < taxMax) {
					taxRio = Double.valueOf(taxVo.getTaxRio());
					taxSs = Double.valueOf(taxVo.getTaxSs());
					break;
				}
			}
		}
		
		//应纳税额
		Double taxNeed = taxBase * (taxRio / 100)  - taxSs;
		String taxNeedStr = MathBigDecimalUtil.round2(new BigDecimal(taxNeed));
		
		
		Double taxedSalary = taxBase - taxNeed;
		String taxedSalaryStr = MathBigDecimalUtil.round2(new BigDecimal(taxedSalary));
		
		data.put("taxNeed", taxNeedStr);
		data.put("taxedSalary", taxedSalaryStr);
		result.setData(data);

		return result;
	}
	
	/**
	 * 计算劳动报酬
	 */
	@Override
	public AppResultData<Object> mathTaxPay(Double money) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Map<String, String> data = new HashMap<String, String>();
		
		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();

		searchVo.setSettingType(Constants.SETTING_TYPE_TAX_PAY);

		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);

		if (list.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("数据不存在!");
		}
		
		//劳动报酬所得（4000元以下）的个人所得税额=（每次所得收入－800元）×20%
		//劳动报酬所得（4000元以上）的个人所得税额=[每次所得收入x（1-20%）]X适用税率-速算扣除数。
		
		
		
		if (money < 0 ) {
			data.put("taxNeed", "0");
			data.put("taxedSalary", String.valueOf(money));
			result.setData(data);
			return result;
		}
		
		Double taxRio = 0.0;
		Double taxSs = 0.0;
		
		Double taxNeed = 0.0;
		String taxNeedStr = "";
		Double taxedSalary = 0.0;
		String taxedSalaryStr = "";
		
		
		if (money < 4000 ) {
			taxNeed = (money - 800) * 0.2;
			taxNeedStr = MathBigDecimalUtil.round2(new BigDecimal(taxNeed));
			taxedSalary = money - taxNeed;
			taxedSalaryStr = MathBigDecimalUtil.round2(new BigDecimal(taxedSalary));

			data.put("taxNeed", taxNeedStr);
			data.put("taxedSalary", taxedSalaryStr);
			result.setData(data);
			return result;
		}
		
		//应纳税所得额
		Double taxBase = money * 0.8;
		
		
		
		for (XcompanySetting item : list) {
			JSONObject setValue = (JSONObject) item.getSettingValue();
			TaxVo taxVo = JSON.toJavaObject(setValue, TaxVo.class);
			
			Double taxMin = 0.0;
			Double taxMax = 0.0;
			
			if (StringUtil.isEmpty(taxVo.getTaxMin())) {
				taxMax = Double.valueOf(taxVo.getTaxMax());
				if (taxBase > taxMax) {
					taxRio = Double.valueOf(taxVo.getTaxRio());
					taxSs = Double.valueOf(taxVo.getTaxSs());
					break;
				}
			} else if (StringUtil.isEmpty(taxVo.getTaxMax())) {
				taxMin = Double.valueOf(taxVo.getTaxMin());
				if (taxBase < taxMin) {
					taxRio = Double.valueOf(taxVo.getTaxRio());
					taxSs = Double.valueOf(taxVo.getTaxSs());
					break;
				}
			} else  {
				taxMin = Double.valueOf(taxVo.getTaxMin());
				taxMax = Double.valueOf(taxVo.getTaxMax());
				
				if (taxBase > taxMin && taxBase < taxMax) {
					taxRio = Double.valueOf(taxVo.getTaxRio());
					taxSs = Double.valueOf(taxVo.getTaxSs());
					break;
				}
			}
		}
		
		//应纳税额
		taxNeed = taxBase * (taxRio / 100)  - taxSs;
		taxNeedStr = MathBigDecimalUtil.round2(new BigDecimal(taxNeed));
		
		
		taxedSalary = taxBase - taxNeed;
		taxedSalaryStr = MathBigDecimalUtil.round2(new BigDecimal(taxedSalary));
		
		data.put("taxNeed", taxNeedStr);
		data.put("taxedSalary", taxedSalaryStr);
		result.setData(data);

		return result;
	}

}