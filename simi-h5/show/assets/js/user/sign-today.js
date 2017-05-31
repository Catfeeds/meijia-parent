var userId = getUrlParam("user_id");


//获取每日的一条管理谚语
function getOpAutoFeed() {
	$.ajax({
		type : "GET",
		url : appRootUrl + "op/op_auto_feed.json?auto_type=1",
		dataType : "json",
		cache : false,
		async : true,
		success : function(data) {
			var datas = data.data;
			var content = datas.content;
			$("#opAutoFeedContent").html(content);
		}
	});
}

//获取今日签到情况
function getTodaySign() {
	if (userId == undefined || userId == "" || userId == 0) return false;
	$.ajax({
		type : "GET",
		url : appRootUrl + "user/get_day_sign.json?user_id=" + userId,
		dataType : "json",
		cache : true,
		async : true,
		success : function(data) {
			var datas = data.data;
			var signed = datas.signed;
			console.log("signed ==" + signed);
			$("#signed").val(signed);
			if (signed == "1") {
				$("#btn-day-sign").html("已签到");
			}  else if (signed == "0") {
				$("#btn-day-sign").html("点我签到 领取奖励");
			}
			
			var alarmDaySign = datas.alarm_day_sign;
			if (alarmDaySign == 1) {
				$("#alarmDaySignCheckBox").uCheck('check');
			}
		}
	});
}

//获取一周签到情况
function getDaySignList() {
	if (userId == undefined || userId == "" || userId == 0) return false;
	$.ajax({
		type : "GET",
		url : appRootUrl + "user/day_sign_list.json?user_id=" + userId,
		dataType : "json",
		cache : true,
		async : true,
		success : function(data) {
			var result = data.data;
			
			var tableHtml = " <table class=\"am-table am-table-bordered am-table-radius am-table-striped am-table-centered\">";
			
			var trHeader1 = "<tr>";
			var trValue1 = "<tr>";
			var trHeader2 = "<tr>";
			var trValue2 = "<tr>";
			$.each(result, function(i, item) {
				var week = item.week;
				var day = item.day;
				var dayStr = item.dayStr;
				var signed = item.signed;
				
				if (day <= 4) {
					trHeader1+="<td><strong>"+week+"</strong><br>"+dayStr+"</td>";
					if (signed == 0) {
						trValue1+="<td><button type=\"button\" class=\"am-btn am-btn-default am-radius\">未签</button></td>";
					} else {
						trValue1+="<td><button type=\"button\" class=\"am-btn am-btn-warning am-radius\">已签</button></td>";
					}
				} else {
					trHeader2+="<td><strong>"+week+"</strong><br>"+dayStr+"</td>";
					if (signed == 0) {
						trValue2+="<td><button type=\"button\" class=\"am-btn am-btn-default am-radius\">未签</button></td>";
					} else {
						trValue2+="<td><button type=\"button\" class=\"am-btn am-btn-warning am-radius\">已签</button></td>";
					}
				}
			});
			trHeader1+= "</tr>";
			trValue1+= "</tr>";
			trHeader2+= "<td><strong>领取<br>一周奖励</strong></td></tr>";
			trValue2+= "<td><button type=\"button\" class=\"am-btn am-btn-default am-radius\">抽奖</button></td></tr>";
			
			tableHtml+=trHeader1;
			tableHtml+=trValue1;
			tableHtml+=trHeader2;
			tableHtml+=trValue2;
			
			tableHtml+="</table>";
			
			$("#sign-list").html(tableHtml);
		}
	});
}

getOpAutoFeed();
getTodaySign();
getDaySignList();

//今日签到动作
function daySign() {
	if (userId == undefined || userId == "" || userId == 0) return false;
	var signed = $("#signed").val();
	if (signed == 1) return false;
	
	$.ajax({
		type : "POST",
		url : appRootUrl + "user/day_sign.json?user_id=" + userId,
		dataType : "json",
		cache : true,
		async : true,
		success : function(data) {
			var status = data.status;
			
			if (status == 999) {
				alert(data.msg);
				return false;
			}
			var datas = data.data;
			
			
			$("#signed").val(1);
			$("#btn-day-sign").val("已签到");
			
			$("#day-sign-success").modal('open');
			
		}
	});
}


//设置签到提醒.
function setAlarmDaySign() {
	console.log("setAlarmDaySign");
	if (userId == undefined || userId == "" || userId == 0) return false;
	var action = "remove";
	
	if ($("#alarmDaySignCheckBox").is(':checked') ) {
		action = "add";
	}
	
	console.log("action = " + action);
	
	
	var params = {};
	params.user_id = userId;
	params.action = action;
	
	$.ajax({
		type : "POST",
		url : appRootUrl + "user/set_alarm_day_sign.json",
		data : params,
		dataType : "json",
		cache : false,
		async : true,
		success : function(data) {
			var status = data.status;
		}
	});
}
