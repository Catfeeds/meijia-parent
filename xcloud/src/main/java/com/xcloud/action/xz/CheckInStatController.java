package com.xcloud.action.xz;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.DateUtil;
import com.meijia.utils.ExcelUtil;
import com.meijia.utils.Week;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.op.OpChannel;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyStaff;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.service.xcloud.XcompanyCheckinStatService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserSearchVo;
import com.simi.vo.xcloud.CompanyCheckinSearchVo;
import com.simi.vo.xcloud.UserCompanySearchVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;

@Controller
@RequestMapping(value = "/xz/checkin")
public class CheckInStatController extends BaseController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private XCompanySettingService xCompanySettingService;

	@Autowired
	private XcompanyStaffService xcompanyStaffService;

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private XcompanyDeptService xcompanyDeptService;

	@Autowired
	private XcompanyCheckinStatService xcompanyCheckInStatService;

	@AuthPassport
	@RequestMapping(value = "stat-list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model, CompanyCheckinSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();

		int cyear = DateUtil.getYear();
		int cmonth = DateUtil.getMonth();
		searchVo.setCompanyId(companyId);
		if (searchVo.getCyear() == 0)
			searchVo.setCyear(cyear);
		if (searchVo.getCmonth() == 0)
			searchVo.setCmonth(cmonth);

		// 年度选择框
		List<Integer> selectYears = new ArrayList<Integer>();
		selectYears.add(cyear - 1);
		selectYears.add(cyear);
		model.addAttribute("selectYears", selectYears);
		// 月份选择框
		List<Integer> selectMonths = new ArrayList<Integer>();
		for (int i = 1; i <= 12; i++) {
			if (i > cmonth)
				break;
			selectMonths.add(i);
		}
		model.addAttribute("selectMonths", selectMonths);

		// 当月所有日期
		List<String> months = DateUtil.getAllDaysOfMonth(searchVo.getCyear(), searchVo.getCmonth());

		List<String> viewDays = new ArrayList<String>();
		for (int i = 0; i < months.size(); i++) {
			String dayStr = months.get(i);
			int day = Integer.valueOf(dayStr.substring(8));
			viewDays.add(String.valueOf(day));
		}
		model.addAttribute("viewDays", viewDays);

		// 日期对应的星期
		List<String> weeks = new ArrayList<String>();
		Date tmpDate = null;
		Week w = null;
		for (int i = 0; i < months.size(); i++) {
			tmpDate = DateUtil.parse(months.get(i));
			w = DateUtil.getWeek(tmpDate);
			String wName = w.getChineseName();
			weeks.add(wName.substring(2));
		}
		model.addAttribute("weeks", weeks);

		// 所有员工的明细情况
		List<HashMap<String, Object>> staffChekins = xcompanyCheckInStatService.getStaffCheckin(searchVo);
		model.addAttribute("staffChekins", staffChekins);
		model.addAttribute("searchModel", searchVo);
		return "xz/checkin-stat-list";
	}
	
	@AuthPassport
	@RequestMapping(value = "stat-total", method = RequestMethod.GET)
	public String statTotal(HttpServletRequest request, Model model, CompanyCheckinSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();

		int cyear = DateUtil.getYear();
		int cmonth = DateUtil.getMonth();
		searchVo.setCompanyId(companyId);
		if (searchVo.getCyear() == 0)
			searchVo.setCyear(cyear);
		if (searchVo.getCmonth() == 0)
			searchVo.setCmonth(cmonth);

		// 年度选择框
		List<Integer> selectYears = new ArrayList<Integer>();
		selectYears.add(cyear - 1);
		selectYears.add(cyear);
		model.addAttribute("selectYears", selectYears);
		// 月份选择框
		List<Integer> selectMonths = new ArrayList<Integer>();
		for (int i = 1; i <= 12; i++) {
			if (i > cmonth)
				break;
			selectMonths.add(i);
		}
		model.addAttribute("selectMonths", selectMonths);
		
		// 所有员工的明细情况
		List<HashMap<String, Object>> staffTotalChekins = xcompanyCheckInStatService.getStaffTotalCheckin(searchVo);

		model.addAttribute("staffTotalChekins", staffTotalChekins);
		model.addAttribute("searchModel", searchVo);
		return "xz/checkin-stat-total";
	}
	

	@RequestMapping(value = "checkin-stat-late", method = RequestMethod.GET)
	public AppResultData<Object> checkinStatLate(@RequestParam(value = "company_id", required = false, defaultValue = "0") Long companyId,
			@RequestParam(value = "user_id", required = false, defaultValue = "0") Long userId) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		UserCompanySearchVo searchVo = new UserCompanySearchVo();
		searchVo.setCompanyId(companyId);
		searchVo.setStatus((short) 1);
		List<XcompanyStaff> staffList = xcompanyStaffService.selectBySearchVo(searchVo);

		for (XcompanyStaff item : staffList) {
			xcompanyCheckInStatService.setCheckinFirst(companyId, item.getUserId());
		}
		return result;
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param searchVo
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	@RequestMapping(value = "/export-stat", method = { RequestMethod.GET })
	public String exportStat(HttpServletRequest request, HttpServletResponse response, CompanyCheckinSearchVo searchVo) throws Exception {

		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();
		String companyName = accountAuth.getCompanyName();

		int cyear = DateUtil.getYear();
		int cmonth = DateUtil.getMonth();
		searchVo.setCompanyId(companyId);
		if (searchVo.getCyear() == 0)
			searchVo.setCyear(cyear);
		if (searchVo.getCmonth() == 0)
			searchVo.setCmonth(cmonth);

		// 当月所有日期
		List<String> months = DateUtil.getAllDaysOfMonth(searchVo.getCyear(), searchVo.getCmonth());
		// 所有员工的统计情况

		List<HashMap<String, Object>> staffChekins = xcompanyCheckInStatService.getStaffCheckin(searchVo);
		
		
		String cpath = request.getSession().getServletContext().getRealPath("/WEB-INF") + "/attach/";
		String templateName = "考勤明细表.xls";
		
		InputStream in = new FileInputStream(cpath + templateName);

		HSSFWorkbook wb = (HSSFWorkbook) ExcelUtil.loadWorkbook(templateName, in);
		HSSFSheet sh = wb.getSheetAt(0);
		int rows = sh.getPhysicalNumberOfRows();

		// 单位和日期
		HSSFRow row3 = sh.getRow(3);
		// System.out.println(ExcelUtil.readCellValues(row4.getCell(1)));
		// 单位名称:
		HSSFCell cellCompanyName = row3.getCell(3);
		cellCompanyName.setCellValue(companyName);

		// 年度
		int year = com.meijia.utils.DateUtil.getYear();
		int month = com.meijia.utils.DateUtil.getMonth();
		String yearStr = String.valueOf(year);
		HSSFCell cellYear1 = row3.getCell(28);
		cellYear1.setCellValue(yearStr.substring(0, 1));
		HSSFCell cellYear2 = row3.getCell(29);
		cellYear2.setCellValue(yearStr.substring(1, 2));
		HSSFCell cellYear3 = row3.getCell(30);
		cellYear3.setCellValue(yearStr.substring(2, 3));
		HSSFCell cellYear4 = row3.getCell(31);
		cellYear4.setCellValue(yearStr.substring(3, 4));

		// 月份
		HSSFCell cellMonth = row3.getCell(33);
		cellMonth.setCellValue(month);

		// 日期表头
		HSSFRow row4 = sh.getRow(4);
		HSSFRow row5 = sh.getRow(5);
		
		int startIndex = 5;
		for (int i = 0; i < months.size(); i++) {
			String dayStr = months.get(i);
			int day = Integer.valueOf(dayStr.substring(8));
			HSSFCell cellDate = row4.getCell(startIndex);
			cellDate.setCellValue(day);

			Date tmpDate = com.meijia.utils.DateUtil.parse(months.get(i));
			Week w = com.meijia.utils.DateUtil.getWeek(tmpDate);
			String wName = w.getChineseName();
			HSSFCell cellWeek = row5.getCell(startIndex);
			cellWeek.setCellValue(wName.substring(2));
			startIndex++;
		}

		// 数据填入
		int startRow = 6;
		int totalStaffs = staffChekins.size();
		int endRow = totalStaffs * 2 - 1;
		ExcelUtil.insertRow(wb, sh, startRow, endRow);
		//先合并单元格.
		int rowNum = startRow;
		for(int i = 0; i < staffChekins.size(); i++) {
			HashMap<String, Object> item = staffChekins.get(i);
			
			int j = 0;
			
			for (j = 0; j < 2; j++) {
				rowNum = rowNum + j;
				HSSFRow rowData = sh.getRow(rowNum);
				
				
				//序号
				HSSFCell cellNo = rowData.getCell(1);
				cellNo.setCellValue(item.get("no").toString());
				
				//姓名
				HSSFCell cellName = rowData.getCell(2);
				cellName.setCellValue(item.get("name").toString());
				
				if (j == 0) {
					//合并单元格
					sh.addMergedRegion(new CellRangeAddress(rowNum, rowNum + 1, 1, 1));
					sh.addMergedRegion(new CellRangeAddress(rowNum, rowNum + 1, 2, 2));
					
					//上午
					HSSFCell cellAm = rowData.getCell(3);
					cellAm.setCellValue("上午");
					
					int cellStartIndex = 5;
					List<HashMap<String, String>> dataAm = (List<HashMap<String, String>>) item.get("dataAm");
					for (int k = 0; k < dataAm.size(); k++) {
						HashMap<String, String> itemAm = (HashMap<String, String>) dataAm.get(k);
						HSSFCell cellAMStatus = rowData.getCell(cellStartIndex + k);

						String status = itemAm.get("status").toString();
						cellAMStatus.setCellValue(status);
					}
					
				} else {
					
					HSSFCell cellPm = rowData.getCell(3);	
					cellPm.setCellValue("下午");
					rowNum++;
					
					int cellStartIndex = 5;
					List<HashMap<String, String>> dataPm = (List<HashMap<String, String>>) item.get("dataPm");
					for (int k = 0; k < dataPm.size(); k++) {
						HashMap<String, String> itemPm =  (HashMap<String, String>) dataPm.get(k);
						HSSFCell cellPmStatus = rowData.getCell(cellStartIndex + k);
						String status = itemPm.get("status").toString();
						cellPmStatus.setCellValue(status);
					}
				}
			}
		}
		
		String fileName = "考勤明细表";
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		wb.write(os);
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}		

		return null;
	}
	
	/**
	 *
	 * @param request
	 * @param response
	 * @param searchVo
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	@RequestMapping(value = "/export-total", method = { RequestMethod.GET })
	public String exportTotal(HttpServletRequest request, HttpServletResponse response, CompanyCheckinSearchVo searchVo) throws Exception {

		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();

		int cyear = DateUtil.getYear();
		int cmonth = DateUtil.getMonth();
		searchVo.setCompanyId(companyId);
		if (searchVo.getCyear() == 0)
			searchVo.setCyear(cyear);
		if (searchVo.getCmonth() == 0)
			searchVo.setCmonth(cmonth);

		// 当月所有日期
		List<String> months = DateUtil.getAllDaysOfMonth(searchVo.getCyear(), searchVo.getCmonth());
		// 所有员工的统计情况

		List<HashMap<String, Object>> staffTotalChekins = xcompanyCheckInStatService.getStaffTotalCheckin(searchVo);
		
		
		String cpath = request.getSession().getServletContext().getRealPath("/WEB-INF") + "/attach/";
		String templateName = "考勤统计表.xls";
		
		InputStream in = new FileInputStream(cpath + templateName);

		HSSFWorkbook wb = (HSSFWorkbook) ExcelUtil.loadWorkbook(templateName, in);
		HSSFSheet sh = wb.getSheetAt(0);
		int rows = sh.getPhysicalNumberOfRows();

		
		// 年度
		int year = DateUtil.getYear();
		int month = DateUtil.getMonth();
		// 表头
		HSSFRow row0 = sh.getRow(0);

		HSSFCell cellHeader = row0.getCell(0);
		cellHeader.setCellValue(year + "年" + month + "月公司考勤统计表");

		// 数据填入
		int startRow = 4;
		int totalStaffs = staffTotalChekins.size();
		int endRow = totalStaffs;
		ExcelUtil.insertRow(wb, sh, startRow, endRow);
	
		//先合并单元格.
		int rowNum = startRow;
		for(int i = 0; i < staffTotalChekins.size(); i++) {
			HashMap<String, Object> item = staffTotalChekins.get(i);
			
			HSSFRow rowData = sh.getRow(rowNum);
			
			HSSFCell cell0 = rowData.getCell(0);
			cell0.setCellValue(item.get("no").toString());
			
			HSSFCell cell1 = rowData.getCell(1);
			cell1.setCellValue(item.get("name").toString());
			
			HSSFCell cell2 = rowData.getCell(2);
			cell2.setCellValue(item.get("deptName").toString());
			
			HSSFCell cell3 = rowData.getCell(3);
			cell3.setCellValue(item.get("totalCheckinDays").toString());
			
			HSSFCell cell4 = rowData.getCell(4);
			cell4.setCellValue(item.get("totalRestDays").toString());
			
			HSSFCell cell5 = rowData.getCell(5);
			cell5.setCellValue(item.get("totalOverTimeDays").toString());
			
			HSSFCell cell6 = rowData.getCell(6);
			cell6.setCellValue(item.get("totalLeavelType0").toString());
			
			HSSFCell cell7 = rowData.getCell(7);
			cell7.setCellValue(item.get("totalLeavelType1").toString());
			
			HSSFCell cell8 = rowData.getCell(8);
			cell8.setCellValue(item.get("totalLeavelType2").toString());
			
			HSSFCell cell9 = rowData.getCell(9);
			cell9.setCellValue(item.get("totalLeavelType3").toString());
			
			HSSFCell cell10 = rowData.getCell(10);
			cell10.setCellValue(item.get("totalLeavelType4").toString());
			
			HSSFCell cell11 = rowData.getCell(11);
			cell11.setCellValue(item.get("totalLeavelType5").toString());
			
			HSSFCell cell12 = rowData.getCell(12);
			cell12.setCellValue(item.get("totalLeavelType6").toString());
			
			HSSFCell cell13 = rowData.getCell(13);
			cell13.setCellValue(item.get("totalLate").toString());
			
			HSSFCell cell14 = rowData.getCell(14);
			cell14.setCellValue(item.get("totalEarly").toString());
			
			HSSFCell cell15 = rowData.getCell(15);
			cell15.setCellValue(item.get("totalAbsence").toString());
			
			HSSFCell cell16 = rowData.getCell(16);
			cell16.setCellValue(item.get("totalNoCheckin").toString());
			
			HSSFCell cell17 = rowData.getCell(17);
			cell17.setCellValue(item.get("isAllCheckin").toString());
			
			rowNum++;
		}
		


		
		String fileName = "考勤统计表";
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		wb.write(os);
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}		

		return null;
	}

}
