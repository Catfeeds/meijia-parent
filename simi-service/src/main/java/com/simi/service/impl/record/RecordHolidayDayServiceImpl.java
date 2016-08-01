package com.simi.service.impl.record;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.DateUtil;
import com.meijia.utils.HttpClientUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.record.RecordHolidayDayMapper;
import com.simi.po.model.record.RecordHolidayDay;
import com.simi.service.record.RecordHolidayDayService;

@Service
public class RecordHolidayDayServiceImpl implements RecordHolidayDayService {

	@Autowired
	RecordHolidayDayMapper recordHolidayDayMapper;

	@Override
	public RecordHolidayDay initRecordHolidayDay() {

		RecordHolidayDay record = new RecordHolidayDay();
		record.setId(0L);
		record.setYear(0);
		record.setCday("");

		record.setAddTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return recordHolidayDayMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(RecordHolidayDay record) {
		return recordHolidayDayMapper.insertSelective(record);
	}

	@Override
	public RecordHolidayDay selectByPrimarykey(Long id) {
		return recordHolidayDayMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public RecordHolidayDay selectByDay(String cday) {
		return recordHolidayDayMapper.selectByDay(cday);
	}
	
	@Override
	public List<RecordHolidayDay> selectByYear(int year) {
		return recordHolidayDayMapper.selectByYear(year);
	}

	@Override
	public int updateByPrimaryKeySelective(RecordHolidayDay record) {
		return recordHolidayDayMapper.updateByPrimaryKeySelective(record);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Boolean getHolidayByApi(int year) {

		String apiKey = "8cca3859e9b222f51c51698f8016e293";

		// 获取年度的假日列表url
		String yearUrl = "http://apis.baidu.com/xiaogg/holiday/holiday";
		Map<String, String> yearParams = new HashMap<String, String>();
		yearParams.put("d", String.valueOf(year));
		
		//正确结果为
//	    "2016": [
//	             "0207",
//	             "0208",
//	             "0209",
//	             "0210",
//	             "0211",
//	             "0212",
//	             "0213",
//	             "0404",
//	             "0501",
//	             "0502",
//	             "0609",
//	             "0610",
//	             "0611",
//	             "0915",
//	             "0916",
//	             "0917",
//	             "1001",
//	             "1002",
//	             "1003",
//	             "1004",
//	             "1005",
//	             "1006",
//	             "1007",
//	             "0101"
//	         ]
//	     }
		//错误结果为：
		//{"errNum":300202,"errMsg":"Missing apikey"}

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("apikey", apiKey);
		
		String yearRes = HttpClientUtil.get(yearUrl, yearParams, headers);
		
//		String yearRes = "{\"2016\":[\"0207\",\"0208\",\"0209\",\"0210\",\"0211\",\"0212\",\"0213\",\"0404\",\"0501\",\"0502\",\"0609\",\"0610\",\"0611\",\"0915\",\"0916\",\"0917\",\"1001\",\"1002\",\"1003\",\"1004\",\"1005\",\"1006\",\"1007\",\"0101\"]}";
		System.out.println(yearRes);
		
		JSONObject ja = JSONObject.fromObject(yearRes);
		if (ja.containsKey("errNum")) {
			System.out.println(ja.get("errNum") + ":" + ja.get("errMsg"));
			return false;
		}

		String list = ja.get(String.valueOf(year)).toString();

		JSONArray jsonArray = JSONArray.fromObject(list);
		
		Iterator it = jsonArray.iterator();
		
		while (it.hasNext()) {
			System.out.println(it.next().toString());
			String datePart = it.next().toString();
			String dateStr = String.valueOf(year) + datePart;
			Date date = DateUtil.parse(dateStr, "yyyyMMdd");
			String cday = DateUtil.formatDate(date);

			
			RecordHolidayDay record = this.selectByDay(cday);
			
			if (record == null) record = this.initRecordHolidayDay();
			record.setYear(year);
			record.setCday(cday);
			record.setAddTime(TimeStampUtil.getNowSecond());
			
			if (record.getId() > 0L) {
				this.updateByPrimaryKeySelective(record);
			} else {
				this.insertSelective(record);
			}
        }
		return true;
	}
}
