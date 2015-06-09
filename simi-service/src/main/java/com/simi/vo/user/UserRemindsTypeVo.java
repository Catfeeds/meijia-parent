package com.simi.vo.user;

/**
 * 用户提醒记录转换类
 * @author kerry
 *
 */
public class UserRemindsTypeVo {

	    private String mobile;

	    private Long remindId;

	    private String remindTitle;

	    private String  startDate;

	    private String startTime;

	    private Short  cycleType;

	    private String crond;

	    private String remindToName;

	    private String remindToMobile;

	    private String remarks;

	    private String remindType;

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public Long getRemindId() {
			return remindId;
		}

		public void setRemindId(Long remindId) {
			this.remindId = remindId;
		}

		public String getRemindTitle() {
			return remindTitle;
		}

		public void setRemindTitle(String remindTitle) {
			this.remindTitle = remindTitle;
		}

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getStartTime() {
			return startTime;
		}

		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}


		public String getRemindToName() {
			return remindToName;
		}

		public void setRemindToName(String remindToName) {
			this.remindToName = remindToName;
		}

		public String getRemindToMobile() {
			return remindToMobile;
		}

		public void setRemindToMobile(String remindToMobile) {
			this.remindToMobile = remindToMobile;
		}

		public String getRemarks() {
			return remarks;
		}

		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

		public String getRemindType() {
			return remindType;
		}

		public void setRemindType(String remindType) {
			this.remindType = remindType;
		}







		public UserRemindsTypeVo(String mobile, Long remindId,
				String remindTitle, String startDate, String startTime,
				Short cycleType, String crond, String remindToName,
				String remindToMobile, String remarks, String remindType) {
			super();
			this.mobile = mobile;
			this.remindId = remindId;
			this.remindTitle = remindTitle;
			this.startDate = startDate;
			this.startTime = startTime;
			this.cycleType = cycleType;
			this.crond = crond;
			this.remindToName = remindToName;
			this.remindToMobile = remindToMobile;
			this.remarks = remarks;
			this.remindType = remindType;
		}

		public String getCrond() {
			return crond;
		}

		public void setCrond(String crond) {
			this.crond = crond;
		}

		public Short getCycleType() {
			return cycleType;
		}

		public void setCycleType(Short cycleType) {
			this.cycleType = cycleType;
		}

		public UserRemindsTypeVo() {
			super();
		}










}
