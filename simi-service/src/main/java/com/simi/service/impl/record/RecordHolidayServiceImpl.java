package com.simi.service.impl.record;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.HttpClientUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.record.RecordHolidayMapper;
import com.simi.po.model.record.RecordHoliday;
import com.simi.service.record.RecordHolidayService;
import com.simi.vo.record.RecordHolidaySearchVo;

@Service
public class RecordHolidayServiceImpl implements RecordHolidayService {

	@Autowired
	RecordHolidayMapper recordHolidayMapper;

	@Override
	public RecordHoliday initRecordHoliday() {

		RecordHoliday record = new RecordHoliday();
		record.setId(0L);
		record.setYear(0);
		record.setHdate(0L);
		record.setName("");
		record.setHdesc("");
		record.setAddTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return recordHolidayMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(RecordHoliday record) {
		return recordHolidayMapper.insertSelective(record);
	}

	@Override
	public RecordHoliday selectByPrimarykey(Long id) {
		return recordHolidayMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(RecordHoliday record) {
		return recordHolidayMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<RecordHoliday> selectBySearchVo(RecordHolidaySearchVo searchVo) {
		return recordHolidayMapper.selectBySearchVo(searchVo);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(RecordHolidaySearchVo searchVo, int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		List<RecordHoliday> list = recordHolidayMapper.selectByListPage(searchVo);

		PageInfo result = new PageInfo(list);
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Boolean getHolidayByApi(int year) {

		String apiKey = "656a4b599d076835b07c759ab826bc12";

		// 获取年度的假日列表url
		String yearUrl = "http://japi.juhe.cn/calendar/year";
		Map<String, String> yearParams = new HashMap<String, String>();
		yearParams.put("year", String.valueOf(year));
		yearParams.put("key", apiKey);
		
		
		//正确结果为
		//{"error_code": 0,"reason": "Success","result": {"data":{ "holidaylist" : "[{\"name\":\"元旦\",\"startday\":\"2016-1-1\"},{\"name\":\"除夕\",\"startday\":\"2016-2-7\"},{\"name\":\"春节\",\"startday\":\"2016-2-7\"},{\"name\":\"清明节\",\"startday\":\"2016-4-4\"},{\"name\":\"劳动节\",\"startday\":\"2016-5-1\"},{\"name\":\"端午节\",\"startday\":\"2016-6-9\"},{\"name\":\"中秋节\",\"startday\":\"2016-9-15\"},{\"name\":\"国庆节\",\"startday\":\"2016-10-1\"}]" , "year" : "2016"}}}
		//错误结果为：
		//{"error_code": 217701,"reason": "No data returned"}
		String yearRes = HttpClientUtil.get(yearUrl, yearParams);

		JSONObject ja = JSONObject.fromObject(yearRes);
		if (ja.getInt("error_code") != 0) {
			System.out.println(ja.get("error_code") + ":" + ja.get("reason"));
			return false;
		}
		
		String result = ja.get("result").toString();
		JSONObject jb = JSONObject.fromObject(result);
		
		String data = jb.getString("data").toString();
		
		JSONObject jc = JSONObject.fromObject(data);
		String holidaylist = jc.getString("holidaylist");
		
		JSONArray jsonArray = JSONArray.fromObject(holidaylist);
		
		Iterator it = jsonArray.iterator();
		//获取假日的详情url
		String dateUrl = "http://japi.juhe.cn/calendar/day";
		
		while (it.hasNext())
        {
            JSONObject jsonObject = (JSONObject) it.next();
            System.out.println(jsonObject.getString("name")+ "-" + jsonObject.getString("startday"));
            String startDay = jsonObject.getString("startday");
            Long hdate = TimeStampUtil.getMillisOfDay(startDay) / 1000;            
            Map<String, String> dateParams = new HashMap<String, String>();
            dateParams.put("date", startDay);
            dateParams.put("key", apiKey);
            
            //返回结果
            //例如: {"error_code": 0,"reason": "Success","result": {"data":{ "holiday" : "端午节" , "avoid" : "动土.掘井.破土." , "animalsYear" : "猴" , "desc" : "6月9日至11日放假调休，共3天。6月12日（星期日）上班。" , "weekday" : "星期四" , "suit" : "嫁娶.纳采.订盟.祭祀.祈福.求嗣.开光.出火.出行.拆卸.修造.动土.进人口.入宅.移徙.安床.交易.立券.挂匾.纳财.入殓.安葬.启攒.除服.成服." , "lunarYear" : "丙申年" , "lunar" : "五月初五" , "year-month" : "2016-6" , "date" : "2016-6-9"}}}
            String dateRes = HttpClientUtil.get(dateUrl, dateParams);
            
            JSONObject dja = JSONObject.fromObject(dateRes);
            if (dja.getInt("error_code") != 0) {
            	System.out.println(dja.get("error_code") + ":" + dja.get("reason"));
    			continue;
            }
            
            JSONObject djb = JSONObject.fromObject(dja.getString("result"));
            JSONObject djc = JSONObject.fromObject(djb.getString("data"));

            if (djc.has("holiday") == false || djc.has("desc") == false) continue;
            
            String name = djc.getString("holiday");
            String hdesc = djc.getString("desc");
            
            System.out.println(String.valueOf(year) + "-" + startDay + "-" + name + "-" + hdesc);
        
            RecordHoliday record = this.initRecordHoliday();
            
            RecordHolidaySearchVo searchVo = new RecordHolidaySearchVo();
            searchVo.setHdate(hdate);
            
            List<RecordHoliday> list = this.selectBySearchVo(searchVo);
            if (!list.isEmpty()) record = list.get(0);
            
            record.setYear(year);
            record.setHdate(hdate);
            record.setName(name);
            record.setHdesc(hdesc);
            if (record.getId() > 0L) {
            	this.updateByPrimaryKeySelective(record);
            } else {
            	this.insertSelective(record);
            }
        }
		return true;
	}
}
