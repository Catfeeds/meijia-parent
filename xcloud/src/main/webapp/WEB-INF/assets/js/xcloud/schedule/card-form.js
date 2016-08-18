
var hiddenNames = $("#selectUserNamesHidden").val();
if (hiddenNames != undefined) {
	$("#selectUserNames").html(hiddenNames);
}


// 初始化选择后的效果
$('#selected_users').tagsinput({

	itemValue : 'id',
	itemText : 'label',
	itemMobile : 'mobile',
	allowDuplicates : false,
	trimValue : true,
	maxChars : 8
});

// 表格中checkbox 选择触发事件
function setSelectUser(obj, userId, name, mobile) {
	console.log("setSelectUser");
	console.log(obj.checked);
    console.log("userId=" + userId);
    console.log("name =" + name);
    console.log("mobile = " + mobile);
	
    var isChecked = obj.checked;
    
    if (isChecked) {
		addSelectUser(userId, name, mobile);
		$('#selected_users').tagsinput('add', {
			id : userId,
			label : name,
			mobile : mobile,
		});
	} else {
		removeSelectUser(userId, name, mobile);
		$('#selected_users').tagsinput('remove', {
			id : userId,
			label : name,
			mobile : mobile,
		});
    }

}



// hidden selectUserIds 和 selectUserNames 处理添加的情况
function addSelectUser(userId, name, mobile) {
	console.log("addSelectUser");
	var selectUserIds = $("#selectUserIds").val();
	var selectUserNames = $("#selectUserNames").html();
	var selectUserNamesHidden = $("#selectUserNamesHidden").val();
	var selectUserMobiles = $("#selectUserMobiles").val();

	var hasExist = false;
	if (selectUserIds != "") {
		var selectUserAry = selectUserIds.split(',');
		for (var i = 0; i < selectUserAry.length; i++) {
			if (selectUserAry[i] == userId) {
				hasExist = true;
				break;
			}
		}
	}
	
	if (hasExist == false) {
		selectUserIds = selectUserIds + userId + ",";
		selectUserNames = selectUserNames + name + ",";
		selectUserMobiles = selectUserMobiles + mobile + ",";
		$("#selectUserIds").val(selectUserIds);
		$("#selectUserNames").html(selectUserNames);
		$("#selectUserNamesHidden").val(selectUserNames);
		$("#selectUserMobiles").val(selectUserMobiles);
	}

	console.log($("#selectUserIds").val());
	console.log($("#selectUserMobiles").val());
};

// hidden selectUserIds 和 selectUserNames 处理移除的情况
function removeSelectUser(userId, name, mobile) {
	var selectUserIds = $("#selectUserIds").val();
	var selectUserNames = $("#selectUserNames").html();
	var selectUserMobiles = $("#selectUserMobiles").val();
	var selectUserNamesHidden = $("#selectUserNamesHidden").val();
	
	var hasExist = false;
	var newSelectUserIds = "";
	var newSelectNames = "";
	var newSelectMobiles = "";
	if (selectUserIds != "") {
		var selectUserAry = selectUserIds.split(',');
		var selectNameAry = selectUserNames.split(',');
		var selectMobileAry = selectUserMobiles.split(',');
		for (var i = 0; i < selectUserAry.length; i++) {
			if (selectUserAry[i] != userId && selectUserAry[i] != "") {
				newSelectUserIds += selectUserAry[i] + ",";
				newSelectNames += selectNameAry[i] + ",";
				newSelectMobiles += selectMobileAry[i] + ",";
			}
		}
	}

	$("#selectUserIds").val(newSelectUserIds);
	$("#selectUserNames").html(newSelectNames);
	$("#selectUserNames").val(newSelectNames);
	$("#selectUserMobiles").val(newSelectMobiles);
	
	console.log($("#selectUserIds").val());
	console.log($("#selectUserMobiles").val());	
};

// tagsInput 删除元素完成后的触发事件
$('#selected_users').on('itemRemoved', function(event) {
	// event.item: contains the item
	console.log(event.item);
	if (event.item == undefined) return false;
	var item = event.item;
	var userId = item.id;
	var name = item.label;
	var mobile = item.mobile;

	removeSelectUser(userId, name, mobile);
	
//	$("#checkbox-"+userId).attr('checked',false);
});

var curFormDateTime = moment().add('m', 5).format('YYYY-MM-DD HH:mm');
$('.form_datetime').datetimepicker({
	language : 'zh-CN',
	format : 'yyyy-mm-dd hh:ii',
	autoclose : true,
	todayBtn : true,
	startDate : curFormDateTime,
	minuteStep : 1
});

$('#setNowSend').not('[data-switch-no-init]').bootstrapSwitch();

// 提交验证
$("#btn-card-submit").on('click', function(e) {

	var fv = formValidation();


	if (fv == false) return false;

	// 组建提交卡片接口数据
	var params = {}
	
	params.card_id = $("#cardId").val();
	params.card_type = $("#cardType").val();
	params.title = "";
	var userId = $("#userId").val();
	params.create_user_id = userId;
	params.user_id = userId;
	
	var serviceTime = $("#serviceTime").val();
	params.service_time = moment(serviceTime, "YYYY-MM-DD HH:mm:00")/1000;
	var serviceAddr = $("#serviceAddr").val();
	if (serviceAddr == undefined) serviceAddr =  "";
	params.service_addr = serviceAddr;
	
	params.service_content = $("#serviceContent").val();
	params.set_remind = $("#setRemind").val();
	
	var setNowSend = 0;
	var setNowSendSwitch = $("#setNowSend").bootstrapSwitch("state", $(this).data('switch-value'));
	if (setNowSendSwitch == true) setNowSend = 1;
	
	params.set_now_send = setNowSend;
	
	params.set_sec_do = 0;
	params.set_sec_remarks = "";
	params.card_extra = "";
	params.status = 1;
	
	//接收人
	var attends = [];
	
	var selectUserIds = $("#selectUserIds").val();
	var selectUserNames = $("#selectUserNames").html();
	var selectUserMobiles = $("#selectUserMobiles").val();
	var selectUserAry = selectUserIds.split(',');
	var selectNameAry = selectUserNames.split(',');
	var selectMobileAry = selectUserMobiles.split(',');
	for (var i = 0; i < selectUserAry.length; i++) {
		if (selectUserAry[i] != userId && selectUserAry[i] != "") {

			attends.push({user_id:selectUserAry[i], mobile:selectMobileAry[i], name : selectNameAry[i] });
		}
	}	
	params.attends = JSON.stringify(attends);
	
	
	console.log(params);
	
	$.ajax({
		type : "POST",
		url : appRootUrl + "/card/post_card.json", // 发送给服务器的url
		data : params,
		dataType : "json",
		async : false,
		success : function(data) {
			if (data.status == "999") {
				alert(data.msg);
				return false;
			}

			if (data.status == 0) {
				location.href = "/xcloud/schedule/list";
			}
		}
	})
	
});

function formValidation() {
	var selectUserIds = $("#selectUserIds").val();

	var selectFlag = true;
	if (selectUserIds == "") {
		selectFlag = false;
		alert("请选择人员!");
	}

	var form = $('#card-form');

	var formValidity = $('#card-form').validator().data('amui.validator').validateForm().valid

	if (formValidity && selectFlag) {
		// done, submit form
		console.log("ok");
		// 判断提醒时间点可以提醒.
		// 1. 获取提醒时间
		var serviceTime = $("#serviceTime").val();
		var serviceTimeLong = moment(serviceTime, "YYYY-MM-DD HH:mm:00");
		var nowTimeLong = moment();
		
		// 2. 获取提醒设置
		var setRemind = $("#setRemind").val();
		
		var setRemindMin = getRemindMin(setRemind);
		
		var lastRemindTime = serviceTimeLong - setRemindMin * 60 * 1000;
		
		if (lastRemindTime <= nowTimeLong) {
			alert("时间选择必须大于当前时间.");
			return false;
		}
		
		return true;
		// form.submit();
	} else {
		// fail
		console.log("fail");
		return false;
	};
}

// 根据提醒设置返回提前提醒的分钟数
function getRemindMin(setRemind) {
	var remindMin = 0;
	switch (setRemind) {
		case "0":
			remindMin = 0;
			break;
		case "1":
			remindMin = 0;
			break;
		case "2":
			remindMin = 5;
			break;
		case "3":
			remindMin = 15;
			break;
		case "4":
			remindMin = 30;
			break;
		case "5":
			remindMin = 60;
			break;
		case "6":
			remindMin = 2 * 60;
			break;
		case "7":
			remindMin = 6 * 60;
			break;
		case "8":
			remindMin = 24 * 60;
			break;
		case "9":
			remindMin = 48 * 60;
			break;
		default:
			remindMin = 0;
	}

	return remindMin;
}